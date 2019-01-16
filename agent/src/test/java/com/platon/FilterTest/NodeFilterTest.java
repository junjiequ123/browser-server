package com.platon.FilterTest;

import com.alibaba.fastjson.JSON;
import com.platon.TestBase;
import com.platon.browser.client.Web3jClient;
import com.platon.browser.common.dto.agent.CandidateDto;
import com.platon.browser.common.util.CalculatePublicKey;
import com.platon.browser.dao.entity.Block;
import com.platon.browser.dao.entity.NodeRanking;
import com.platon.browser.dao.entity.NodeRankingExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.platon.contracts.CandidateContract;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * User: dongqile
 * Date: 2019/1/11
 * Time: 15:00
 */
@RunWith(SpringRunner.class)
public class NodeFilterTest extends TestBase {

    private static Logger logger = LoggerFactory.getLogger(NodeFilterTest.class);


    @Test
    public void NodeFilterTest () {
        try {
            CandidateContract candidateContract = web3jClient.getCandidateContract();
            Web3j web3j = Web3jClient.getWeb3jClient();
            DefaultBlockParameter defaultBlockParameter = new DefaultBlockParameterNumber(new BigInteger(String.valueOf(400L)));
            EthBlock ethBlock = web3j.ethGetBlockByNumber(defaultBlockParameter, true).send();
            String nodeInfoList = candidateContract.CandidateList(new BigInteger(String.valueOf(400L))).send();
            List <EthBlock.TransactionResult> list = ethBlock.getBlock().getTransactions();
            List <Transaction> list1 = new ArrayList <>();
            List <TransactionReceipt> list2 = new ArrayList <>();
            for (EthBlock.TransactionResult transactionResult : list) {
                Transaction txList = (Transaction) transactionResult.get();
                EthTransaction ethTransaction = web3j.ethGetTransactionByHash(txList.getHash()).send();
                Optional <Transaction> value = ethTransaction.getTransaction();
                Transaction transaction = value.get();
                list1.add(transaction);
                EthGetTransactionReceipt ethGetTransactionReceipt = web3j.ethGetTransactionReceipt(transaction.getHash()).send();
                Optional <TransactionReceipt> transactionReceipt = ethGetTransactionReceipt.getTransactionReceipt();
                TransactionReceipt receipt = transactionReceipt.get();
                list2.add(receipt);
            }
            Block block = blockFilter.blockAnalysis(ethBlock, list2, list1);
            nodeFilter.build(nodeInfoList, ethBlock.getBlock().getNumber().longValue(), ethBlock, block.getBlockReward());
            NodeRankingExample nodeRankingExample = new NodeRankingExample();
            nodeRankingExample.createCriteria().andChainIdEqualTo(chainId).andIsValidNotEqualTo(1);
            List <NodeRanking> nodeRankings = nodeRankingMapper.selectByExample(nodeRankingExample);
            BigInteger a = CalculatePublicKey.testBlock(ethBlock);
            logger.info(a.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void nodeIdEqualsPublicKeyTest () {
        try {
            CandidateContract candidateContract = web3jClient.getCandidateContract();
            Web3j web3j = Web3jClient.getWeb3jClient();
            DefaultBlockParameter defaultBlockParameter = new DefaultBlockParameterNumber(new BigInteger(String.valueOf(7773L)));
            EthBlock ethBlock = web3j.ethGetBlockByNumber(defaultBlockParameter, true).send();
            String nodeInfoList = candidateContract.CandidateList(new BigInteger(String.valueOf(7773L))).send();
            List <CandidateDto> list = JSON.parseArray(nodeInfoList, CandidateDto.class);
            BigInteger a = CalculatePublicKey.testBlock(ethBlock);
            String node38793 = new BigInteger("71f7b5f7294a9a1fb6a1b796478d81d00986ea1481307b10237d6f292c9bee72663bef4eec06a5003f17c89bf072a442a4a288031fab80cc03dc0dfd84330349",16).toString();
            logger.info(node38793);
            logger.info(a.toString());
             list.forEach(candidateDto -> {
                 String n = new BigInteger(candidateDto.getCandidateId(),16).toString();
                 candidateDto.getCandidateId();
                 logger.info(n);
             });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}