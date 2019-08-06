const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.access_token,
  avatar: state => state.auth.avatar,
  name: state => state.auth.name,
  roles: state => state.auth.roles,
  login: state => state.auth.login_status,
  permission_routes: state => state.permission.routes
}
export default getters
