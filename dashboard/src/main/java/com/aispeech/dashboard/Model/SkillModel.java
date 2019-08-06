package com.aispeech.dashboard.Model;


import com.aispeech.dashboard.Model.enums.SkillStatus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class SkillModel {
    private String skillId;
    private String version;
    private String skillFullId;
    private String entityCollectionName;
    private SkillStatus status;
    private String productId;
    private long createdAt;
    private String createdAtUTC;
    private long updatedAt;
    private String updatedAtUTC;

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
        this.createdAtUTC = convertTimeStampToString(createdAt);
    }

    public String getCreatedAtUTC() {
        return createdAtUTC;
    }


    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
        this.updatedAtUTC = convertTimeStampToString(updatedAt);
    }

    public String getUpdatedAtUTC() {
        return updatedAtUTC;
    }

    public String getSkillId() {
        return skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSkillFullId() {
        return skillFullId;
    }

    public void setSkillFullId(String skillFullId) {
        this.skillFullId = skillFullId;
    }

    public String getEntityCollectionName() {
        return entityCollectionName;
    }

    public void setEntityCollectionName(String entityCollectionName) {
        this.entityCollectionName = entityCollectionName;
    }

    public SkillStatus getStatus() {
        return status;
    }

    public void setStatus(SkillStatus status) {
        this.status = status;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    private String convertTimeStampToString(long epochTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String utcDateString = sdf.format(new Date(epochTime == 0 ? System.currentTimeMillis() : epochTime));
        // Append the string "UTC" to the date
        if (!utcDateString.contains("UTC")) {
            utcDateString += " UTC";
        }
        return utcDateString;
    }
}
