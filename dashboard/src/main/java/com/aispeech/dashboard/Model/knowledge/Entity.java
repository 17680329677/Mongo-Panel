package com.aispeech.dashboard.Model.knowledge;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashMap;
import java.util.Map;

public class Entity  {
    private String concept;
    private String id;
    @JsonProperty("kgId")
    private String uid;
    private String skillId;
    private String topicId;

    @JsonProperty("attributes")
    private Map<String, Object> attributeMap;

    public Entity() {
        this.attributeMap = new LinkedHashMap<>();
    }

    public Map<String, Object> getAttributeMap() {
        return attributeMap;
    }

    public <T> T getAttributeValue(String attributeName) {
        return (T)this.getAttributeMap().get(attributeName);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSkillId() {
        return skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    @Override
    public String toString () {
        return this.toString();
    }

}
