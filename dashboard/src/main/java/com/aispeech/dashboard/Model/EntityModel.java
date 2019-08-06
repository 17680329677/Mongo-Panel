package com.aispeech.dashboard.Model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class EntityModel {
    private static final Logger logger = LoggerFactory.getLogger(EntityModel.class);
    private String baId;
    private String fullId;
    private String skillId;
    private String skillVersion;
    private String topicId;
    private String concept;
    private HashMap<String, EntityAttribute> bav1;
    private HashMap<String, EntityAttribute> bav2;
    private HashMap<String, EntityAttribute> reverseEdges;


    private static Date convertStringToDate(String dateString) {
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        isoFormat.setTimeZone(TimeZone.getTimeZone("Asia/Chongqing"));
        Date date = null;
        try {
            date = isoFormat.parse(dateString);
        } catch (ParseException e) {

        }
        return date;
    }

    public String getBaId() {
        return baId;
    }

    public void setBaId(String baId) {
        this.baId = baId;
    }

    public String getFullId() {
        return fullId;
    }

    public void setFullId(String fullId) {
        this.fullId = fullId;
    }

    public String getSkillId() {
        return skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
    }

    public String getSkillVersion() {
        return skillVersion;
    }

    public void setSkillVersion(String skillVersion) {
        this.skillVersion = skillVersion;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public HashMap<String, EntityAttribute> getBav1() {
        return bav1;
    }

    public void setBav1(HashMap<String, EntityAttribute> bav1) {
        this.bav1 = bav1;
    }

    public HashMap<String, EntityAttribute> getBav2() {
        return bav2;
    }

    public void setBav2(HashMap<String, EntityAttribute> bav2) {
        this.bav2 = bav2;
    }

    public HashMap<String, EntityAttribute> getReverseEdges() {
        return reverseEdges;
    }

    public void setReverseEdges(HashMap<String, EntityAttribute> reverseEdges) {
        this.reverseEdges = reverseEdges;
    }


}
