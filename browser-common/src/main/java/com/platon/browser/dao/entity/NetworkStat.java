package com.platon.browser.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class NetworkStat {
    private Integer id;

    private Long curNumber;

    private String curBlockHash;

    private String nodeId;

    private String nodeName;

    private Integer txQty;

    private Integer curTps;

    private Integer maxTps;

    private BigDecimal issueValue;

    private BigDecimal turnValue;

    private BigDecimal stakingDelegationValue;

    private BigDecimal stakingValue;

    private Integer doingProposalQty;

    private Integer proposalQty;

    private Integer addressQty;

    private BigDecimal blockReward;

    private BigDecimal stakingReward;

    private Long addIssueBegin;

    private Long addIssueEnd;

    private Long nextSettle;

    private Long nodeOptSeq;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCurNumber() {
        return curNumber;
    }

    public void setCurNumber(Long curNumber) {
        this.curNumber = curNumber;
    }

    public String getCurBlockHash() {
        return curBlockHash;
    }

    public void setCurBlockHash(String curBlockHash) {
        this.curBlockHash = curBlockHash == null ? null : curBlockHash.trim();
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId == null ? null : nodeId.trim();
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName == null ? null : nodeName.trim();
    }

    public Integer getTxQty() {
        return txQty;
    }

    public void setTxQty(Integer txQty) {
        this.txQty = txQty;
    }

    public Integer getCurTps() {
        return curTps;
    }

    public void setCurTps(Integer curTps) {
        this.curTps = curTps;
    }

    public Integer getMaxTps() {
        return maxTps;
    }

    public void setMaxTps(Integer maxTps) {
        this.maxTps = maxTps;
    }

    public BigDecimal getIssueValue() {
        return issueValue;
    }

    public void setIssueValue(BigDecimal issueValue) {
        this.issueValue = issueValue;
    }

    public BigDecimal getTurnValue() {
        return turnValue;
    }

    public void setTurnValue(BigDecimal turnValue) {
        this.turnValue = turnValue;
    }

    public BigDecimal getStakingDelegationValue() {
        return stakingDelegationValue;
    }

    public void setStakingDelegationValue(BigDecimal stakingDelegationValue) {
        this.stakingDelegationValue = stakingDelegationValue;
    }

    public BigDecimal getStakingValue() {
        return stakingValue;
    }

    public void setStakingValue(BigDecimal stakingValue) {
        this.stakingValue = stakingValue;
    }

    public Integer getDoingProposalQty() {
        return doingProposalQty;
    }

    public void setDoingProposalQty(Integer doingProposalQty) {
        this.doingProposalQty = doingProposalQty;
    }

    public Integer getProposalQty() {
        return proposalQty;
    }

    public void setProposalQty(Integer proposalQty) {
        this.proposalQty = proposalQty;
    }

    public Integer getAddressQty() {
        return addressQty;
    }

    public void setAddressQty(Integer addressQty) {
        this.addressQty = addressQty;
    }

    public BigDecimal getBlockReward() {
        return blockReward;
    }

    public void setBlockReward(BigDecimal blockReward) {
        this.blockReward = blockReward;
    }

    public BigDecimal getStakingReward() {
        return stakingReward;
    }

    public void setStakingReward(BigDecimal stakingReward) {
        this.stakingReward = stakingReward;
    }

    public Long getAddIssueBegin() {
        return addIssueBegin;
    }

    public void setAddIssueBegin(Long addIssueBegin) {
        this.addIssueBegin = addIssueBegin;
    }

    public Long getAddIssueEnd() {
        return addIssueEnd;
    }

    public void setAddIssueEnd(Long addIssueEnd) {
        this.addIssueEnd = addIssueEnd;
    }

    public Long getNextSettle() {
        return nextSettle;
    }

    public void setNextSettle(Long nextSettle) {
        this.nextSettle = nextSettle;
    }

    public Long getNodeOptSeq() {
        return nodeOptSeq;
    }

    public void setNodeOptSeq(Long nodeOptSeq) {
        this.nodeOptSeq = nodeOptSeq;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table network_stat
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public enum Column {
        id("id", "id", "INTEGER", false),
        curNumber("cur_number", "curNumber", "BIGINT", false),
        curBlockHash("cur_block_hash", "curBlockHash", "VARCHAR", false),
        nodeId("node_id", "nodeId", "VARCHAR", false),
        nodeName("node_name", "nodeName", "VARCHAR", false),
        txQty("tx_qty", "txQty", "INTEGER", false),
        curTps("cur_tps", "curTps", "INTEGER", false),
        maxTps("max_tps", "maxTps", "INTEGER", false),
        issueValue("issue_value", "issueValue", "DECIMAL", false),
        turnValue("turn_value", "turnValue", "DECIMAL", false),
        stakingDelegationValue("staking_delegation_value", "stakingDelegationValue", "DECIMAL", false),
        stakingValue("staking_value", "stakingValue", "DECIMAL", false),
        doingProposalQty("doing_proposal_qty", "doingProposalQty", "INTEGER", false),
        proposalQty("proposal_qty", "proposalQty", "INTEGER", false),
        addressQty("address_qty", "addressQty", "INTEGER", false),
        blockReward("block_reward", "blockReward", "DECIMAL", false),
        stakingReward("staking_reward", "stakingReward", "DECIMAL", false),
        addIssueBegin("add_issue_begin", "addIssueBegin", "BIGINT", false),
        addIssueEnd("add_issue_end", "addIssueEnd", "BIGINT", false),
        nextSettle("next_settle", "nextSettle", "BIGINT", false),
        nodeOptSeq("node_opt_seq", "nodeOptSeq", "BIGINT", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        updateTime("update_time", "updateTime", "TIMESTAMP", false);

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table network_stat
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table network_stat
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table network_stat
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table network_stat
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table network_stat
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table network_stat
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table network_stat
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table network_stat
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table network_stat
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table network_stat
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table network_stat
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table network_stat
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table network_stat
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table network_stat
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table network_stat
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER).toString();
            } else {
                return this.column;
            }
        }
    }
}