package com.aispeech.dashboard.Controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: hezhe.du
 * @Date: 2019/6/11 0011 15:34
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/logout")
    public String logout() {
        JSONObject json = new JSONObject();
        json.put("code", 200);
        json.put("message", "success");
        return json.toJSONString();
    }
}
