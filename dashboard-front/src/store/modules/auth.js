import { getToken, getInfo, logout } from '@/api/auth'
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
  getToken({ commit }, code) {
    return new Promise((resolve, reject) => {
      getToken(code).then(response => {
        commit('SET_ACCESS_TOKEN', response.data.access_token)
        commit('SET_REFRESH_TOKEN', response.data.refresh_token)
        setAccessToken(response.data.access_token)
        setRefreshToken(response.data.refresh_token)
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
        if (!response.data) {
          reject('Verification failed, please Login again.')
        }
        // roles must be a non-empty array
        if (!response.data.roles || response.data.roles.length <= 0) {
          reject('getInfo: roles must be a non-null array!')
        }
        commit('SET_ROLES', response.data.roles)
        commit('SET_NAME', response.data.name)
        commit('SET_AVATAR', response.data.avatar)
        commit('SET_LOGIN_STATUS', true)
        resolve(response.data)
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
