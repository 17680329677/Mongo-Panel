package com.aispeech.dashboard.Utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: hezhe.du
 * @Date: 2019/7/18 0018 13:32
 */

@Component
public class OAuthUtil {

    private static String clientId;

    private static String clientSecret;

    private static String serverUrl;

    private static String authorizateUrl;

    private static String getTokenUrl;

    private static String refreshTokenUrl;

    private static String checkTokenUrl;

    private static String logoutUrl;

    private static String userInfoUrl;

    @Value("${CLIENT_ID}")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Value("${CLIENT_SECRET}")
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @Value("${OAUTH_SERVER_URL}")
    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    @PostConstruct
    public void init() {
        /* 授权地址 */
        authorizateUrl = serverUrl + "/oauth/authorize?response_type=code&client_id=" + clientId;
        /* 获取token地址 */
        getTokenUrl = serverUrl + "/oauth/token?grant_type=authorization_code&client_id=" + clientId + "&client_secret=" + clientSecret + "&code=";
        /* 刷新token地址 */
        refreshTokenUrl = serverUrl + "/oauth/token?grant_type=refresh_token&client_id=" + clientId + "&client_secret=" + clientSecret + "&refresh_token=";
        /* 检查token地址 */
        checkTokenUrl = serverUrl + "/oauth/check_token?token";
        /* 登出地址 */
        logoutUrl = serverUrl + "/oauth/logout";
        /* 用户信息接口地址 */
        userInfoUrl = serverUrl + "/user/info";
    }

    public static String getAuthorizateUrl() {
        return authorizateUrl;
    }

    public static String getGetTokenUrl() {
        return getTokenUrl;
    }

    public static String getRefreshTokenUrl() {
        return refreshTokenUrl;
    }

    public static String getCheckTokenUrl() {
        return checkTokenUrl;
    }

    public static String getLogoutUrl() { return logoutUrl; }

    public static String getUserInfoUrl() { return userInfoUrl; }
}
