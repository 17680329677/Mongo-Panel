package com.aispeech.dashboard.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AttributeValue {
    private List<String> strings;
    private String text;
    private List<Date> dates;
    private List<Integer> dateYears;
    private List<Integer> dateMonths;
    private List<Integer> dateDays;
    private List<String> references;
    private List<String> referenceNames;
    private List<String> referenceConcepts;
    private List<String> identifiers;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getIdentifiers() {
        if (identifiers == null){
            identifiers = new ArrayList<>();
        }
        return identifiers;
    }

    public void setIdentifiers(List<String> identifiers) {
        this.identifiers = identifiers;
    }

    public List<String> getStrings() {
        if (strings == null){
            strings = new ArrayList<>();
        }
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }

    public List<Integer> getDateYears() {
        return dateYears;
    }

    public void setDateYears(List<Integer> dateYears) {
        this.dateYears = dateYears;
    }

    public List<Integer> getDateMonths() {
        return dateMonths;
    }

    public void setDateMonths(List<Integer> dateMonths) {
        this.dateMonths = dateMonths;
    }

    public List<Integer> getDateDays() {
        return dateDays;
    }

    public void setDateDays(List<Integer> dateDays) {
        this.dateDays = dateDays;
    }

    public List<String> getReferences() { return references; }

    public void setReferences(List<String> references) {
        this.references = references;
    }

    public List<String> getReferenceNames() {
        return referenceNames;
    }

    public void setReferenceNames(List<String> referenceNames) {
        this.referenceNames = referenceNames;
    }

    public List<String> getReferenceConcepts() {
        return referenceConcepts;
    }

    public void setReferenceConcepts(List<String> referenceConcepts) {
        this.referenceConcepts = referenceConcepts;
    }
}
