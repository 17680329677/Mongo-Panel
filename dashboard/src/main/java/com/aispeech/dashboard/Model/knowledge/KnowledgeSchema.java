package com.aispeech.dashboard.Model.knowledge;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class KnowledgeSchema {
    public static Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z][_A-Za-z0-9]*$");
    @JsonProperty("concepts")
    private Map<String, ConceptSchema> conceptSchemaMap;
    private Map<String, EnumSchema> enums;

    public KnowledgeSchema() {
        this.conceptSchemaMap = new LinkedHashMap<>();
        this.enums = new LinkedHashMap<>();
    }

    public Map<String, ConceptSchema> getConceptSchemaMap() {
        return conceptSchemaMap;
    }

    public Map<String, EnumSchema> getEnums() {
        return enums;
    }

    public void setConceptSchemaMap(Map<String, ConceptSchema> conceptSchemaMap) {
        this.conceptSchemaMap = conceptSchemaMap;
    }

    public void setEnums(Map<String, EnumSchema> enums) {
        this.enums = enums;
    }
}
