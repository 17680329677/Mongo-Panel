package com.aispeech.dashboard.Controller;

import com.aisp.horizontal.helper.LogHelper;
import com.aispeech.dashboard.Model.ResultModel;
import com.aispeech.dashboard.Model.enums.ResultCodeEnum;
import com.aispeech.dashboard.Service.OAuthService;
import com.aispeech.dashboard.Service.impl.OAuthServiceImpl;
import com.aispeech.dashboard.Utils.OAuthUtil;
import com.aispeech.dashboard.Utils.ResultUtil;
import javafx.beans.binding.ObjectExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: hezhe.du
 * @Date: 2019/7/17 0017 14:45
 */

@Controller
@RequestMapping("/oauth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    OAuthService oAuthService;

    @RequestMapping("/authorization")
    public void login(HttpServletResponse response) throws IOException {
        response.sendRedirect(OAuthUtil.getAuthorizateUrl());
    }

    @GetMapping("/token")
    @ResponseBody
    public ResultModel getAccessToken(@RequestParam("code")String code) {
        return oAuthService.getToken(code);
    }

    @GetMapping("/info")
    @ResponseBody
    public ResultModel getUserInfo(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");
            String accessToken = authHeader.split(" ")[1];
            logger.info("get request header info success: {}", authHeader);
            return oAuthService.getUserInfo(accessToken);
        } catch (Exception e) {
            logger.warn("get request header info failed: {}", LogHelper.toString(e));
            return ResultUtil.error(ResultCodeEnum.GetUserInfoError);
        }
    }

    @GetMapping("/refresh_token")
    @ResponseBody
    public ResultModel refreshToken(@RequestParam("refresh_token")String refreshToken) {
        return oAuthService.refreshToken(refreshToken);
    }

    @GetMapping("/logout")
    @ResponseBody
    public ResultModel logout() { return oAuthService.logout(); }
}
