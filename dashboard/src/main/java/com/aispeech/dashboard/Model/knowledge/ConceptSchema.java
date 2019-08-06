package com.aispeech.dashboard.Model.knowledge;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ksimple on 2018/6/27.
 */
public class ConceptSchema {
    @JsonProperty("attributes")
    private Map<String, AttributeSchema> attributeSchemaMap;

    private String name;

    public ConceptSchema() {
        this.attributeSchemaMap = new LinkedHashMap<>();
    }

    public Map<String, AttributeSchema> getAttributeSchemaMap() {
        return attributeSchemaMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!KnowledgeSchema.NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException("name: [" + name + "]");
        }
        this.name = name;
    }

    public void setAttributeSchemaMap(Map<String, AttributeSchema> attributeSchemaMap) {
        this.attributeSchemaMap = attributeSchemaMap;
    }
}
