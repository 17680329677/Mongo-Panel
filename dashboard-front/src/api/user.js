import request from '@/utils/request'
import {getRefreshToken} from "../utils/auth";
import qs from 'qs'

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    // data: {
    //   "username": data.username,
    //   "password": data.password
    // },
    data: qs.stringify(data),
    // transformRequest: [function (data) {
    //   data = qs.stringify(data);
    //   return data;
    // }],
    headers: {
      // 'Authorization': 'Basic Y2xpZW50XzI6MTIzNDU2'
      'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8'
    }
  })
}

export function authorize() {
  return request({
    url: '/oauth/authorize',
    method: 'get',
    params: {
      response_type: 'code',
      client_id: 'client_1',
      redirect_uri: 'https://www.baidu.com'
    }
  })
}


export function getInfo() {
  return request({
    url: '/user/info',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}

export function refreshToken() {
  return request({
    url: '/oauth/token',
    method: 'post',
    params: {
      grant_type: 'refresh_token',
      refresh_token: getRefreshToken()
    },
    headers: {
      'Authorization': 'Basic Y2xpZW50XzI6MTIzNDU2'
    }
  })
}
