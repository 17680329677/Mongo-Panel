import request from '@/utils/request'

export function getToken(code) {
  return request({
    url: '/oauth/token',
    method: 'get',
    params: {
      code: code
    }
  })
}

export function getInfo() {
  return request({
    url: '/oauth/info',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/oauth/logout',
    method: 'get'
  })
}

export function refreshToken(refreshToken) {
  return request({
    url: '/oauth/refresh_token',
    method: 'get',
    params: {
      refresh_token: refreshToken
    }
  })
}
