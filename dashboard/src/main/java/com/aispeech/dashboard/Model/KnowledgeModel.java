package com.aispeech.dashboard.Model;

import com.aispeech.dashboard.Model.knowledge.KnowledgeSchema;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeModel {
    private String knowledgeVersion;
    private KnowledgeSchema knowledgeSchema;
    private List<RuleModel> rules;
    public KnowledgeModel() {
        this.rules = new ArrayList<>();
    }

    public List<RuleModel> getRules() {
        return rules;
    }

    public void setRules(List<RuleModel> rules) {
        this.rules = rules;
    }

    public String getKnowledgeVersion() {
        return knowledgeVersion;
    }

    public void setKnowledgeVersion(String knowledgeVersion) {
        this.knowledgeVersion = knowledgeVersion;
    }

    public KnowledgeSchema getKnowledgeSchema() {
        return knowledgeSchema;
    }

    public void setKnowledgeSchema(KnowledgeSchema knowledgeSchema) {
        this.knowledgeSchema = knowledgeSchema;
    }

    }
