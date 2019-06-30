<template>
  <a-layout :style="{marginTop: '-65px'}" id="components-layout-demo-fixed" class="layout">
    <a-layout-header
      class="menu-header"
      :style="{ position: 'fixed', zIndex: 1, width: '100%', backgroundColor: 'white'}"
    >
      <div class="logo" style=" ">
        <img src='../assets/logo.png' width="40px" height="30px"/>
      </div>
      <a-menu theme mode="horizontal" :defaultOpenKeys="['1']" :defaultSelectedKeys="[selectedKey]"
              :style="{ lineHeight: '78px', textAlign: 'left', borderBottom: 'none' }">
        <a-menu-item key="1">
          <router-link :to="{name:'Home'}">
            <a-icon type="home"/>
            首页
          </router-link>
        </a-menu-item>
        <a-menu-item key="2" v-if="username === 'Steve'">
          <router-link :to="{name: 'JavaLanguageList', params:{name : name}}">
            <a-icon type="setting"/>
            数据管理
          </router-link>
        </a-menu-item>

        <a-menu-item key="3">
          <router-link :to="{}">
            <a-icon type="code"/>
            阅读
          </router-link>
        </a-menu-item>

        <a-menu-item key="4">
          <router-link :to="{}">
            <a-icon type="laptop"/>
            Vue
          </router-link>
        </a-menu-item>

        <a-menu-item key="5">
          <router-link :to="{}">
            <a-icon type="global"/>
            English
          </router-link>
        </a-menu-item>

        <a-sub-menu
          key="sub3"
          :style="{float: 'right'}"
        >
          <span slot="title">
            <a-avatar :src="avatar">
            {{ username }}
          </a-avatar>
          </span>
          <a-menu-item key="7" href="javascript:;" :style="{textAlign: 'center'}">
            <a @click="logout">
              <a-icon type="logout"/>
              退出登陆</a>
          </a-menu-item>
          <a-menu-item key="8" href="javascript:;" :style="{textAlign: 'center'}">
            <a>
              <a-icon type="user"/>
              个人中心</a>
          </a-menu-item>
          <a-menu-item key="9" href="javascript:;" :style="{textAlign: 'center'}">
            <a @click="setting()">
              <a-icon type="setting"/>
              账号设置</a>
          </a-menu-item>
        </a-sub-menu>

        <a-menu-item key="10" v-if="username === 'Steve'">
          <router-link :to="{name: 'SysDictList', params:{name : name}}">
            <a-icon type="setting"/>
            字典管理
          </router-link>
        </a-menu-item>
        <template>
          <a-input-search
            :style="{width: '200px', marginLeft: '20px'}"
            placeholder="Elastic Search"
            @search="onSearch"
          />
        </template>
      </a-menu>
    </a-layout-header>
    <a-layout>
      <a-layout-content :style="{ padding: '0 50px', marginTop: '64px'  }">
        <div :style="{ background: '#fff', padding: '24px', minHeight: '380px' }">
          <router-view></router-view>
        </div>
      </a-layout-content>
      <a-layout-footer :style="{ textAlign: 'center' }">
        Ant Design ©2018 Created by Ant UED
      </a-layout-footer>
    </a-layout>
    <login-model ref="loginModel" @ok="setMenuKey"></login-model>
  </a-layout>
</template>

<script>
import LoginModel from './LoginModel'
export default {
  components: { LoginModel },
  data () {
    return {
      path: '',
      collapsed: false,
      name: '数据管理',
      username: '',
      avatar: '',
      selectedKey: '1',
      url: {
        imgView: this.HOST + '/image/handle/view'
      }
    }
  },
  created () {
    this.username = sessionStorage.getItem('username')
    this.avatar = this.url.imgView + '/' + sessionStorage.getItem('avatar')
  },
  methods: {
    logout () {
      sessionStorage.clear()
      this.$router.push({name: 'Login'})
    },
    setting () {
      this.$refs.loginModel.showModal()
    },
    setMenuKey (title) {
      if (title === 'java') {
        this.selectedKey = '3'
        console.log(this.selectedKey)
      }
    },
    onSearch () {
      console.log('测试搜索')
    },
    // watch: {
    //   path: function (newPath, oldPath) {
    //     console.log(newPath, oldPath)
    //   }
    // }
  }
}
</script>

<style scoped>
  #components-layout-demo-fixed .logo {
    width: 120px;
    height: 31px;
    background: rgba(255,255,255,.2);
    margin: 16px 24px 16px 0;
    float: left;
  }
  .menu-header {
    box-shadow:1px 1px 3px gray;
    width:300px;
    height:80px;
    margin:0 auto
  }
  .noUnderLine {
  }
</style>
