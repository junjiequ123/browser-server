package com.platon.browser.engine.handler.delegation;

import com.alibaba.fastjson.JSON;
import com.platon.browser.config.BlockChainConfig;
import com.platon.browser.dto.CustomDelegation;
import com.platon.browser.dto.CustomNode;
import com.platon.browser.dto.CustomStaking;
import com.platon.browser.dto.CustomTransaction;
import com.platon.browser.engine.BlockChain;
import com.platon.browser.engine.handler.EventContext;
import com.platon.browser.engine.handler.EventHandler;
import com.platon.browser.engine.stage.StakingStage;
import com.platon.browser.exception.NoSuchBeanException;
import com.platon.browser.param.DelegateParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.platon.browser.engine.BlockChain.NODE_CACHE;

/**
 * @Auther: dongqile
 * @Date: 2019/8/17 20:09
 * @Description: 发起委托(委托)事件处理类
 */
@Component
public class DelegateHandler implements EventHandler {
    private static Logger logger = LoggerFactory.getLogger(DelegateHandler.class);
    @Autowired
    private BlockChain bc;
    @Autowired
    private BlockChainConfig chainConfig;
    @Override
    public void handle ( EventContext context ) {
        CustomTransaction tx = context.getTransaction();
        StakingStage stakingStage = context.getStakingStage();
        logger.debug("发起委托(委托)");
        DelegateParam param = tx.getTxParam(DelegateParam.class);
        try {
            CustomNode node = NODE_CACHE.getNode(param.getNodeId());
            try {
                //获取treemap中最新一条质押数据数据
                CustomStaking latestStaking = node.getLatestStaking();
                logger.debug("委托信息:{}", JSON.toJSONString(param));

                CustomDelegation delegation = latestStaking.getDelegations().get(tx.getFrom());
                //更新犹豫期金额
                //若已存在同地址，同节点，同块高的目标委托对象，则说明该地址对此节点有做过委托
                if (delegation != null) {
                    delegation.setDelegateHas(delegation.integerDelegateHas().add(param.integerAmount()).toString());
                    delegation.setIsHistory(CustomDelegation.YesNoEnum.NO.code);
                    //更新分析结果UpdateSet
                    stakingStage.updateDelegation(delegation);
                }
                //若不存在，则说明该地址有对此节点没有委托过
                if (delegation == null) {
                    CustomDelegation newCustomDelegation = new CustomDelegation();
                    newCustomDelegation.updateWithDelegateParam(param, tx);
                    newCustomDelegation.setStakingBlockNum(latestStaking.getStakingBlockNum());
                    // 添加至委托缓存
                    NODE_CACHE.addDelegation(newCustomDelegation);

                    //新增分析结果AddSet
                    stakingStage.insertDelegation(newCustomDelegation);
                }

                //交易数据tx_info补全
                param.setNodeName(latestStaking.getStakingName());
                param.setStakingBlockNum(latestStaking.getStakingBlockNum().toString());
                tx.setTxInfo(JSON.toJSONString(param));
            } catch (NoSuchBeanException e) {
                logger.error("{}", e.getMessage());
            }
        } catch (NoSuchBeanException e) {
            logger.error("无法获取节点信息: {}", e.getMessage());
        }
    }
}