/* import Vue from 'vue' */
/* import {USER_INFO} from '@/store/mutation-types' */

const getters = {
  device: state => state.app.device,
  theme: state => state.app.theme,
  color: state => state.app.color,
  welcome: state => state.user.welcome,
  addRouters: state => state.permission.addRouters
}

export default getters
