package com.aispeech.dashboard.Service.impl;

import com.aispeech.dashboard.Model.ResultModel;
import com.aispeech.dashboard.Model.enums.ResultCodeEnum;
import com.aispeech.dashboard.Service.OAuthService;
import com.aispeech.dashboard.Utils.OAuthUtil;
import com.aispeech.dashboard.Utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: hezhe.du
 * @Date: 2019/7/18 0018 13:55
 */

@Service
public class OAuthServiceImpl implements OAuthService {

    private static final Logger logger = LoggerFactory.getLogger(OAuthServiceImpl.class);

    @Override
    public ResultModel getToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HashMap> response = null;
        try {
            response = restTemplate.exchange(OAuthUtil.getGetTokenUrl() + code, HttpMethod.POST, null , HashMap.class);
        } catch (Exception e) {
            logger.warn("get access token failed: {}", code);
            return ResultUtil.error(ResultCodeEnum.GetTokenError);
        }
        HashMap<String, String> result = new HashMap<>();
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            result.put("access_token", response.getBody().get("access_token").toString());
            result.put("refresh_token", response.getBody().get("refresh_token").toString());
            result.put("token_type", response.getBody().get("token_type").toString());
            logger.info("get authorize code success: {}", code);
            return ResultUtil.success(ResultCodeEnum.OK, result);
        } else {
            logger.warn("get authorize code failed: {}", code);
            return ResultUtil.error(ResultCodeEnum.GetTokenError);
        }
    }

    @Override
    public boolean checkToken(String token) {
        return false;
    }

    @Override
    public ResultModel refreshToken(String refreshToken) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HashMap> response = null;
        try {
            response = restTemplate.exchange(OAuthUtil.getRefreshTokenUrl() + refreshToken, HttpMethod.GET, null, HashMap.class);
        } catch (Exception e) {
            logger.warn("refresh token error: {}", refreshToken);
            return ResultUtil.error(ResultCodeEnum.NotExecuted);
        }
        HashMap<String, Object> result = new HashMap<>();
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            logger.info("refresh token success");
            result.put("access_token", response.getBody().get("access_token"));
            result.put("refresh_token", response.getBody().get("refresh_token"));
            return ResultUtil.success(ResultCodeEnum.OK, result);
        } else {
            logger.warn("refresh token result parse error");
            return ResultUtil.error(ResultCodeEnum.GetTokenError);
        }
    }

    @Override
    public ResultModel getUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<HashMap> response = null;
        try {
            String url = OAuthUtil.getUserInfoUrl();
            response = restTemplate.exchange(OAuthUtil.getUserInfoUrl(), HttpMethod.GET, requestEntity, HashMap.class);
        } catch (Exception e) {
            logger.warn("get user info error: {}", accessToken);
        }
        HashMap<String, Object> result = new HashMap<>();
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            HashMap<String, Object> data = (HashMap<String, Object>) response.getBody().get("data");
            result.put("name", data.get("personName"));
            List<String> roles = new ArrayList<>();
            roles.add("user");
            result.put("roles", roles);
            result.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            logger.info("get user info success: {}", data.get("user_name"));
            return ResultUtil.success(ResultCodeEnum.OK, result);
        } else {
            logger.warn("get user info error: {}", accessToken);
            return ResultUtil.error(ResultCodeEnum.GetUserInfoError);
        }
    }

    @Override
    public ResultModel logout() {
        RestTemplate restTemplate = new RestTemplate();
        String url = OAuthUtil.getLogoutUrl();
        ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            logger.info("log out success");
            return ResultUtil.success(ResultCodeEnum.OK);
        } else {
            logger.info("log out error");
            return ResultUtil.success(ResultCodeEnum.OK);
        }
    }
}
