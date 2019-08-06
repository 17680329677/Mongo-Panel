<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
import {refreshToken} from "@/api/auth"
import { mapGetters } from 'vuex'
import { setAccessToken, getRefreshToken, setRefreshToken, getAccessToken } from "./utils/auth";

export default {
  name: 'App',
  data() {
    return {
      timer: null
    }
  },

  computed:{
    ...mapGetters([
      'login'
    ])
  },

  watch: {
    login: function (newValue) { //li就是改变后的wifiList值
      if (newValue === true) {
        this.timer = setInterval(this.refreshToken, 10000)
      } else {
        clearInterval(this.timer)
      }
    }
  },

  methods: {
    refreshToken() {
      refreshToken(getRefreshToken()).then(res => {
        setAccessToken(res.data.access_token)
        setRefreshToken(res.data.refresh_token)
      }).catch(async () => {
        await this.$store.dispatch('auth/logout')
        this.$router.push('/toLogin')
      })
    }
  },
}
</script>
