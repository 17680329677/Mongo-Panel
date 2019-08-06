package com.aispeech.dashboard.Model;

public class RuleModel {
    private String knowledgeVersion;
    private String ruleId;
    private String ruleDefinition;

    public RuleModel() {
    }

    public RuleModel(String ruleId, String ruleDefinition, String knowledgeVersion) {
        this.ruleId = ruleId;
        this.ruleDefinition = ruleDefinition;
        this.knowledgeVersion = knowledgeVersion;
    }

    public String getKnowledgeVersion() {
        return knowledgeVersion;
    }

    public void setKnowledgeVersion(String knowledgeVersion) {
        this.knowledgeVersion = knowledgeVersion;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleDefinition() {
        return ruleDefinition;
    }

    public void setRuleDefinition(String ruleDefinition) {
        this.ruleDefinition = ruleDefinition;
    }
}
