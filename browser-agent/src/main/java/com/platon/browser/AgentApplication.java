package com.platon.browser;

import com.alaya.protocol.core.methods.response.PlatonBlock;
import com.platon.browser.bean.EpochMessage;
import com.platon.browser.bootstrap.bean.InitializationResult;
import com.platon.browser.bootstrap.service.ConsistencyService;
import com.platon.browser.bootstrap.service.InitializationService;
import com.platon.browser.bean.ReceiptResult;
import com.platon.browser.enums.AppStatus;
import com.platon.browser.publisher.BlockEventPublisher;
import com.platon.browser.service.block.BlockService;
import com.platon.browser.service.epoch.EpochService;
import com.platon.browser.service.receipt.ReceiptService;
import com.platon.browser.utils.AppStatusUtil;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

@Slf4j
//@EnableRetry
@Configuration
@EnableScheduling
@SpringBootApplication
@EnableEncryptableProperties
@MapperScan(basePackages = {
	"com.platon.browser"
})
public class AgentApplication implements ApplicationRunner {
	// 区块服务
	@Resource
	private BlockService blockService;
	// 交易服务
	@Resource
	private ReceiptService receiptService;
	// 区块事件发布服务
	@Resource
	private BlockEventPublisher blockEventPublisher;
	// 周期服务
	@Resource
	private EpochService epochService;
	// 启动一致性检查服务
    @Resource
    private ConsistencyService consistencyService;
    // 启动初始化服务
    @Resource
	private InitializationService initializationService;

	public static void main(String[] args) {
		SpringApplication.run(AgentApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if(AppStatusUtil.isStopped()) return;
		// 把应用置为BOOTING开机状态
		AppStatusUtil.setStatus(AppStatus.BOOTING);
        // 进入一致性开机自检子流程
        consistencyService.post();
		// 进入应用初始化子流程
		InitializationResult initialResult = initializationService.init();
		// 启动自检和初始化完成后,把应用置为RUNNING运行状态,让定时任务可以执行业务逻辑
		AppStatusUtil.setStatus(AppStatus.RUNNING);
		// 已采最高块号
		long collectedNumber = initialResult.getCollectedBlockNumber();
		// 前一个块号
		long preBlockNum;
		// 进入区块采集主流程
		while (true) {
			try {
				preBlockNum=collectedNumber++;
				// 检查区块号是否合法
				blockService.checkBlockNumber(collectedNumber);
				// 异步获取区块
				CompletableFuture<PlatonBlock> blockCF = blockService.getBlockAsync(collectedNumber);
				// 异步获取交易回执
				CompletableFuture<ReceiptResult> receiptCF = receiptService.getReceiptAsync(collectedNumber);
				// 获取周期切换消息
				EpochMessage epochMessage = epochService.getEpochMessage(collectedNumber);
				blockEventPublisher.publish(blockCF, receiptCF,epochMessage);

				if(preBlockNum!=0L&&(collectedNumber-preBlockNum!=1)) throw new AssertionError();
			}catch (Exception e){
				log.error("程序因错误而停止:",e);
				break;
			}
		}
	}
}
