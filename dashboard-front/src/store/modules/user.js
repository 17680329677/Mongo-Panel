import { login, logout, getInfo, refreshToken } from '@/api/user'
import { getAccessToken, setAccessToken, removeAccessToken, getRefreshToken, setRefreshToken, removeRefreshToken } from '@/utils/auth'
import { resetRouter } from '@/router'

const state = {
  access_token: getAccessToken(),
  refresh_token: getRefreshToken(),
  name: '',
  avatar: '',
  roles: [],
  login_status: false
}

const mutations = {
  SET_ACCESS_TOKEN: (state, token) => {
    state.access_token = token
  },
  SET_REFRESH_TOKEN: (state, token) => {
    state.refresh_token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles
  },
  SET_LOGIN_STATUS: (state, status) => {
    state.login_status = status
  }
}


const actions = {
  // user login
  login({ commit }, userInfo) {
    const { username, password } = userInfo
    return new Promise((resolve, reject) => {
      login({ username: username.trim(), password: password }).then(response => {
        // const { data } = response
        let token = response.token;
        commit('SET_ACCESS_TOKEN', token.value)
        commit('SET_REFRESH_TOKEN', token.refreshToken.value)
        setAccessToken(response.token.value)
        setRefreshToken(response.token.refreshToken.value)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo().then(response => {
        const { data } = response

        if (!data) {
          reject('Verification failed, please Login again.')
        }

        const { roles, name, avatar } = data

        // roles must be a non-empty array
        if (!roles || roles.length <= 0) {
          reject('getInfo: roles must be a non-null array!')
        }

        commit('SET_ROLES', roles)
        commit('SET_NAME', name)
        commit('SET_AVATAR', avatar)
        commit('SET_LOGIN_STATUS', true)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      logout().then(() => {
        commit('SET_ACCESS_TOKEN', '')
        commit('SET_REFRESH_TOKEN', '')
        commit('SET_ROLES', [])
        commit('SET_LOGIN_STATUS', false)
        removeAccessToken()
        removeRefreshToken()
        resetRouter()
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      commit('SET_ACCESS_TOKEN', '')
      commit('SET_REFRESH_TOKEN', '')
      commit('SET_ROLES', [])
      removeAccessToken()
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  // getters,
  mutations,
  actions
}
