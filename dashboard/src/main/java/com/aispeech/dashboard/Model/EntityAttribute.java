package com.aispeech.dashboard.Model;

public class EntityAttribute {
    private ValueType type;
    private AttributeValue value;
    private String concept;

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public ValueType getType() {
        return type;
    }

    public void setType(ValueType type) {
        this.type = type;
    }

    public AttributeValue getValue() {
        if (value == null) {
            value = new AttributeValue();
        }
        return value;
    }

    public void setValue(AttributeValue value) {
        this.value = value;
    }
}
