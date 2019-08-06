package com.aispeech.dashboard.Model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 命令模型
 * @Author: hezhe.du
 * @Date: 2019/6/10 0010 15:52
 */

@Data
public class Command {

    /** 数据库 */
    @NotEmpty(message = "数据库不能为空")
    private String database;

    /** 集合 */
    @NotEmpty(message = "集合不能为空")
    private String collection;

    /** 操作类型 */
    @NotEmpty(message = "操作不能为空")
    private String operate;

    /** 诊断标志 */
    private boolean diagnostic = false;

    /** 过滤条件 */
    private String filter;

    /** 查询limit参数 */
    private Integer limit;

    /** 查询skip参数 */
    private Integer skip;
}
