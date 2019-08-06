package com.aispeech.dashboard.Model.knowledge;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by ksimple on 2018/6/27.
 */
public class AttributeSchema {
    private static final Logger logger = LoggerFactory.getLogger(AttributeSchema.class);
    private String name;
    private String subject;
    private List<Map<String, Object>> singleAnswerTemplate;
    private List<Map<String, Object>> multipleAnswerTemplate;
    private String type;
    private TypeCategory typeCategory;
    private boolean isIdentifer;

    public List<Map<String, Object>> getSingleAnswerTemplate() {
        return singleAnswerTemplate;
    }

    public void setSingleAnswerTemplate(List<Map<String, Object>> singleAnswerTemplate) {
        this.singleAnswerTemplate = singleAnswerTemplate;
    }

    public List<Map<String, Object>> getMultipleAnswerTemplate() {
        return multipleAnswerTemplate;
    }

    public void setMultipleAnswerTemplate(List<Map<String, Object>> multipleAnswerTemplate) {
        this.multipleAnswerTemplate = multipleAnswerTemplate;
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

    public String getType() {
        return type;
    }


    @Override
    public String toString() {
        return this.toString();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isIdentifer() {
        return isIdentifer;
    }

    @JsonProperty("isIdentifer")
    public void setIdentifer(boolean identifer) {
        this.isIdentifer = identifer;
    }

    @JsonIgnore
    public TypeCategory getTypeCategory() {
        return typeCategory;
    }

    public enum TypeCategory {
        string,
        stringArray,
        text,
        textArray,
        date,
        dateArray,
        reference,
        image,
        url
    }

    public void setTypeCategory(TypeCategory typeCategory) {
        this.typeCategory = typeCategory;
    }
}
