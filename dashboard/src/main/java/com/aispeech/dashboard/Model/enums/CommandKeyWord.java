package com.aispeech.dashboard.Model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: hezhe.du
 * @Date: 2019/6/10 0010 14:43
 */

@Getter
@AllArgsConstructor
public enum CommandKeyWord {

    USE("use", "创建数据库"),
    DROPDATABASE("dropDatabase", "删除数据库"),
    CREATECOLLECTION("createCollection", "创建集合"),
    DROP("drop", "删除"),
    INSERT("insert", "插入"),
    UPDATE("update", "更新"),
    REMOVE("remove", "移除数据"),
    CREATEINDEX("createIndex", "创建索引");

    private String opt;

    private String type;
}
