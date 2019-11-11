package com.platon.browser.complement.converter.statistic;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platon.browser.common.collection.dto.EpochMessage;
import com.platon.browser.common.complement.cache.NetworkStatCache;
import com.platon.browser.common.complement.cache.NodeCache;
import com.platon.browser.common.queue.collection.event.CollectionEvent;
import com.platon.browser.common.utils.CalculateUtils;
import com.platon.browser.complement.dao.mapper.StatisticBusinessMapper;
import com.platon.browser.config.BlockChainConfig;
import com.platon.browser.dao.entity.NetworkStat;
import com.platon.browser.elasticsearch.dto.Block;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StatisticsNetworkConverter {
	
	@Autowired
	private NetworkStatCache networkStatCache;
    @Autowired
    private NodeCache nodeCache;
    @Autowired
    private BlockChainConfig chainConfig;
    @Autowired
    private StatisticBusinessMapper statisticBusinessMapper;
    
	
    public void convert(CollectionEvent event, Block block, EpochMessage epochMessage) throws Exception {
        long startTime = System.currentTimeMillis();

		// 网络统计
        NetworkStat networkStat = networkStatCache.getNetworkStat();
        networkStat.setNodeId(block.getNodeId());
        networkStat.setNodeName(nodeCache.getNode(block.getNodeId()).getNodeName());
        networkStat.setBlockReward(new BigDecimal(epochMessage.getBlockReward()));
        networkStat.setStakingReward(new BigDecimal(epochMessage.getStakeReward()));
        networkStat.setAddIssueBegin(CalculateUtils.calculateAddIssueBegin(chainConfig.getAddIssuePeriodBlockCount(), epochMessage.getIssueEpochRound()));
        networkStat.setAddIssueEnd(CalculateUtils.calculateAddIssueEnd(chainConfig.getAddIssuePeriodBlockCount(), epochMessage.getIssueEpochRound()));
        networkStat.setNextSettle(CalculateUtils.calculateNextSetting(chainConfig.getSettlePeriodBlockCount(), epochMessage.getSettleEpochRound(), epochMessage.getCurrentBlockNumber()));
        networkStat.setNodeOptSeq(networkStat.getNodeOptSeq());
        statisticBusinessMapper.networkChange(networkStat);

        log.debug("处理耗时:{} ms",System.currentTimeMillis()-startTime);
    }
}