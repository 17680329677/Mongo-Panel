package com.aispeech.dashboard.Service;

import com.aispeech.dashboard.Model.ResultModel;

/**
 * @Author: hezhe.du
 * @Date: 2019/7/18 0018 13:27
 */

public interface OAuthService {

    ResultModel getToken(String code);   // 根据code获取token

    boolean checkToken(String token);   // 检查token的有效性

    ResultModel refreshToken(String refreshToken);   // 刷新token

    ResultModel getUserInfo(String accessToken);     // 获取当前用户信息

    ResultModel logout();   // 用户登出
}
