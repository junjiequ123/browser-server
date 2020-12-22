package com.platon.browser.adjustment.service;

import com.platon.browser.adjustment.bean.AdjustParam;
import com.platon.browser.adjustment.bean.ValidatedContext;
import com.platon.browser.adjustment.context.AbstractAdjustContext;
import com.platon.browser.adjustment.context.DelegateAdjustContext;
import com.platon.browser.adjustment.context.StakingAdjustContext;
import com.platon.browser.adjustment.dao.AdjustmentMapper;
import com.platon.browser.config.BlockChainConfig;
import com.platon.browser.dao.entity.*;
import com.platon.browser.dao.mapper.DelegationMapper;
import com.platon.browser.dao.mapper.NodeMapper;
import com.platon.browser.dao.mapper.StakingMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Service
public class AdjustService {
    @Resource
    protected DelegationMapper delegationMapper;
    @Resource
    protected StakingMapper stakingMapper;
    @Resource
    protected NodeMapper nodeMapper;
    @Resource
    protected AdjustmentMapper adjustmentMapper;
    @Resource
    protected BlockChainConfig chainConfig;

    private static final Logger log = Logger.getLogger(AdjustService.class.getName());
    @Value("platon.account.adjust.log.file")
    private String adjustLogFile;
    @PostConstruct
    private void init(){
        File logFile = new File(adjustLogFile);
        if(logFile.exists()) {
            boolean deleted = logFile.delete();
            if(!deleted) log.warning("删除日志文件失败！");
        }
        try {
            log.setLevel(Level.INFO);
            FileHandler fileHandler = new FileHandler(adjustLogFile);
            fileHandler.setLevel(Level.INFO);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            log.addHandler(fileHandler);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证调账参数，返回验证结果
     * @param adjustParams
     * @return
     */
    public ValidatedContext validate(List<AdjustParam> adjustParams){
        ValidatedContext validatedContext = new ValidatedContext();
        if(adjustParams.isEmpty()) return validatedContext;
        // 针对每笔委托调账数据获取完整的【调账上下文数据】
        for (AdjustParam adjustParam : adjustParams) {
            AbstractAdjustContext aac=null;
            if("staking".equals(adjustParam.getOptType())){
                // 拼装质押调账需要的完整上下文数据
                StakingAdjustContext sac = new StakingAdjustContext();
                validatedContext.getStakingAdjustContextList().add(sac);
                aac=sac;
            }

            if("delegate".equals(adjustParam.getOptType())){
                // 根据<委托者地址,质押块高,节点ID>找到对应的委托信息
                DelegationKey delegationKey = new DelegationKey();
                delegationKey.setDelegateAddr(adjustParam.getAddr());
                delegationKey.setStakingBlockNum(Long.valueOf(adjustParam.getStakingBlockNum()));
                delegationKey.setNodeId(adjustParam.getNodeId());
                Delegation delegation = delegationMapper.selectByPrimaryKey(delegationKey);
                // 拼装委托调账需要的完整上下文数据
                DelegateAdjustContext dac = new DelegateAdjustContext();
                dac.setDelegation(delegation);
                validatedContext.getDelegateAdjustContextList().add(dac);
                aac=dac;
            }

            if(aac!=null){
                aac.setChainConfig(chainConfig);
                aac.setAdjustParam(adjustParam);
                // 根据<质押块高,节点ID>找到对应的质押信息
                StakingKey stakingKey = new StakingKey();
                stakingKey.setNodeId(adjustParam.getNodeId());
                stakingKey.setStakingBlockNum(Long.valueOf(adjustParam.getStakingBlockNum()));
                Staking staking = stakingMapper.selectByPrimaryKey(stakingKey);
                aac.setStaking(staking);
                // 根据<节点ID>找到对应的节点信息
                Node node = nodeMapper.selectByPrimaryKey(adjustParam.getNodeId());
                aac.setNode(node);
                // 校验上下文
                aac.validate();
            }
        }
        return validatedContext;
    }

    public void adjust(List<AdjustParam> adjustParams) {
        // 构造调账上下文并验证调账参数
        ValidatedContext validatedContext = validate(adjustParams);
        // 委托调账
        for (AbstractAdjustContext context : validatedContext.getDelegateAdjustContextList()) {
            if(StringUtils.isBlank(context.errorInfo())){
                // 调账上下文没有错误信息，则执行调账操作
                adjustmentMapper.adjustDelegateData(context.getAdjustParam());
                StringBuilder sb = new StringBuilder("============ ")
                        .append(context.getAdjustParam().getOptType())
                        .append("调账成功,调账上下文： ============\n")
                        .append(context.contextInfo());
                log.info(sb.toString());
            }else{
                // 调账上下文有错误信息，调账参数打印到错误文件
                log.warning(context.errorInfo());
            }
        }
        // 质押调账
        for (AbstractAdjustContext context : validatedContext.getStakingAdjustContextList()) {
            if(StringUtils.isBlank(context.errorInfo())){
                // 调账上下文没有错误信息，则执行调账操作
                adjustmentMapper.adjustStakingData(context.getAdjustParam());
                StringBuilder sb = new StringBuilder("============ ")
                        .append(context.getAdjustParam().getOptType())
                        .append("调账成功,调账上下文： ============\n")
                        .append(context.contextInfo());
                log.info(sb.toString());
            }else{
                // 调账上下文有错误信息，调账参数打印到错误文件
                log.warning(context.errorInfo());
            }
        }
    }
}