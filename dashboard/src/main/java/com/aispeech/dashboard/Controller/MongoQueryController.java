package com.aispeech.dashboard.Controller;

import com.aispeech.dashboard.DashboardApplication;
import com.aispeech.dashboard.Model.Command;
import com.aispeech.dashboard.Model.ResultModel;
import com.aispeech.dashboard.Model.enums.ResultCodeEnum;
import com.aispeech.dashboard.Service.MongoCommandService;
import com.aispeech.dashboard.Utils.ResultUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Component
@RestController
@RequestMapping("/mongo")
public class MongoQueryController {
    private static final Logger logger = LoggerFactory.getLogger(DashboardApplication.class);

    @Autowired
    MongoCommandService mongoCommandService;

    @GetMapping("/getCollectionList")
    public ResultModel getCollectionList() {
        List<String> collectionList = mongoCommandService.getCollectionList();
        return ResultUtil.success(ResultCodeEnum.OK, collectionList);
    }

    @GetMapping("/getCurrentKnowledgeVersion")
    public ResultModel getCurrentKnowledgeVersion() {
        String knowledgeVersion = mongoCommandService.getCurrentKnowledgeVersion();
        logger.info("command executed success: getCurrentKnowledgeVersion");
        return ResultUtil.success(ResultCodeEnum.OK, knowledgeVersion);
    }


    @PostMapping("/command/execute")
    public ResultModel commandExecute(@RequestBody @Valid Command command, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(ResultCodeEnum.ParamsError);
        }
        try {
            List doc = mongoCommandService.commandExecute(command);
            if (doc == null || doc.isEmpty()) {
                logger.error("mongo command execute result empty: {}", JSONObject.toJSONString(command));
                return ResultUtil.error(ResultCodeEnum.Result_Empty);
            } else {
                String jsonString = JSONObject.toJSONString(doc);
                Object result = JSONObject.parse(jsonString);
                logger.info("mongo command execute success {}", JSONObject.toJSONString(doc));
                return ResultUtil.success(ResultCodeEnum.OK, result);
            }
        } catch (Exception e) {
            logger.error("mongo command execute failed: {}", command);
            e.printStackTrace();
            return ResultUtil.error(ResultCodeEnum.NotExecuted);
        }
    }

    @GetMapping("/getEntityNumBySkill")
    public ResultModel getEntityNumBySkill(@RequestParam("skillId") String skillId,
                                           @RequestParam("skillVersion") String skillVersion) {
        try {
            List res = mongoCommandService.getEntityNumBySkill(skillId, skillVersion);
            logger.info("mongo command execute success: getEntityNumBySkill");
            if (res != null && !res.isEmpty()) {
                return ResultUtil.success(ResultCodeEnum.OK, res);
            } else {
                return ResultUtil.success(ResultCodeEnum.Result_Empty);
            }
        } catch (Exception e) {
            logger.error("mongo command execute failed: getEntityNumBySkill");
            return ResultUtil.error(ResultCodeEnum.NotExecuted);
        }
    }

    @GetMapping("/getAttributesByName")
    public ResultModel getAttributesByName() {
        try {
            List res = mongoCommandService.getAttributesByName();
            logger.info("mongo command execute success: getAttributesByName");
            if (res != null && !res.isEmpty()) {
                String jsonString = JSONObject.toJSONString(res);
                Object result = JSONObject.parse(jsonString);
                return ResultUtil.success(ResultCodeEnum.OK, result);
            } else {
                return ResultUtil.success(ResultCodeEnum.Result_Empty);
            }
        } catch (Exception e) {
            logger.error("mongo command execute failed: getAttributesByName");
            return ResultUtil.error(ResultCodeEnum.NotExecuted);
        }
    }
}
