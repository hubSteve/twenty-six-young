<template>
  <div id="loginBody">
    <a-card id="card-body" :bordered="false" hoverable v-if="login">
      <a-row>
      <span style="font-size: large; margin-left: 10px"><strong>Sign in</strong></span>
      </a-row>
      <a-row>
        <span style="margin-left: 10px">Welcome to <a>26 age</a></span>
      </a-row>
          <a-form :form="form" :style="{marginTop: '10px'}">
            <a-form-item
              :label-col="formItemLayout.labelCol"
              :wrapper-col="formItemLayout.wrapperCol"
              label="username"
            >
              <a-input
                v-decorator="['username',{rules: [{ required: true, message: 'Please input your username' }]}]"
                placeholder="Please input your name"
              >
                <a-icon
                  slot="prefix"
                  type="user"
                  style="color:rgba(0,0,0,.25)"
                />
              </a-input>
            </a-form-item>
            <a-form-item
              :label-col="formItemLayout.labelCol"
              :wrapper-col="formItemLayout.wrapperCol"
              label="password"
            >
              <a-input
                v-decorator="['password',{rules: [{ required: true, message: 'Please input your password' }]}]"
                type="password"
                placeholder="Please input your nickname"
              >
                <a-icon
                  slot="prefix"
                  type="lock"
                  style="color:rgba(0,0,0,.25)"
                />
              </a-input>
            </a-form-item>
            <a-form-item :label-col="formTailLayout.labelCol" :wrapper-col="formTailLayout.wrapperCol">
              <a-checkbox :checked="checkNick" @change="handleChangeCheck">
                Keep me signed in
              </a-checkbox>
            </a-form-item>
            <a-form-item
              :label-col="formTailLayout.labelCol"
              :wrapper-col="formTailLayout.wrapperCol"
            >
              <span>No account?
                <a @click="createAccount">Create one</a>
              </span>
              <a-row>
                  <a-button type="primary" @click="check">Check</a-button>
              </a-row>
            </a-form-item>
          </a-form>
        </a-card>
    <a-card id="sign-body" v-else>
      <a-row>
        <a-button title="👈 Back to the login page" @click="backToLoginPage" shape="circle">
          <a-icon type="arrow-left"/>
        </a-button>
        <a-button
          title="🖊 Reset the fields' value"
          shape="circle" style="float: right"
          type="dashed"
          @click="resetFieldsValue"
        >
          <a-icon type="redo"/>
        </a-button>
      </a-row>
      <a-form :form="form">
      <a-form-item
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
        label="username"
      >
        <a-input
          v-model="username"
          placeholder="please enter your username"
        />
      </a-form-item>

      <a-form-item
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
        label="password"
        has-feedback
      >
        <a-input
          v-decorator="['password', {rules: [{ required: true, message: 'Please input your real password!' }]}]"
          placeholder="please enter your password"
        />
      </a-form-item>

        <a-form-item
          :label-col="labelCol"
          :wrapper-col="wrapperCol"
          label="phone"
        >
          <a-input v-decorator="['phone',{ rules: [{required: true, message: 'Please input your phone number!',}]}]"
            placeholder="please enter your phone number"
          />
        </a-form-item>
<!--        <a-form-item
          :label-col="labelCol"
          :wrapper-col="wrapperCol"
          :defaultValue="1"
          label="gender"
          has-feedback
        >
          <a-select
            v-decorator="['gender', {rules: [{ required: true, message: 'Please choose your real gender!' }]}]"
            placeholder="please choose your gender">
            <a-select-option :value="1">man</a-select-option>
            <a-select-option :value="0">women</a-select-option>
          </a-select>
        </a-form-item>-->

       <a-form-item
         :label-col="labelCol"
         :wrapper-col="wrapperCol"
         label="avator"
         has-feedback>
         <a-upload
           listType="picture-card"
           class="avatar-uploader"
           :data="{'isup':1}"
           :showUploadList="false"
           :action="uploadAction"
           :beforeUpload="beforeUpload"
           @change="handleChange"
         >
           <img v-if="imageUrl" :src="imageUrl" alt="avatar" style="width: 80px"/>
           <div v-else>
             <a-icon :type="loading ? 'loading' : 'plus'" />
             <div class="ant-upload-text">Upload</div>
           </div>
         </a-upload>
       </a-form-item>
    </a-form>
      <a-row style="text-align: right">
        <a-button type="primary" @click="accountRegister">Login</a-button>
      </a-row>
    </a-card>
    <route-view></route-view>
  </div>
</template>

<script>
import ACol from 'ant-design-vue/es/grid/Col'
import ARow from 'ant-design-vue/es/grid/Row'
import AFormItem from 'ant-design-vue/es/form/FormItem'
function getBase64 (img, callback) {
  const reader = new FileReader()
  reader.addEventListener('load', () => callback(reader.result))
  reader.readAsDataURL(img)
}
const formItemLayout = {
  labelCol: { span: 6 },
  wrapperCol: { span: 14 }
}
const formTailLayout = {
  labelCol: { span: 6 },
  wrapperCol: { span: 14, offset: 4 }
}
export default {
  components: {AFormItem, ARow, ACol},
  data () {
    return {
      // Object
      model: {},
      login: true,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 15 }
      },
      loading: false,
      uploadLoading: false,
      imageUrl: '',
      avatar: '',
      loginModel: {username: '', password: ''},
      url: {
        fileUpload: this.HOST + '/image/handle/upload',
        login: this.HOST + '/common/login/loginIn',
        register: this.HOST + '/common/login/register'
      },
      // Array
      // Other
      username: '',
      formItemLayout,
      formTailLayout,
      form: this.$form.createForm(this)
    }
  },
  computed: {
    uploadAction: function () {
      return this.url.fileUpload
    }
  },
  methods: {
    check  () {
      this.form.validateFields(
        (err, value) => {
          if (!err) {
            var self = this
            self.loginModel.username = value.username
            self.loginModel.password = value.password
            self.$axios.post(self.url.login, self.loginModel).then((res) => {
              if (res.success) {
                // let user = res.result
                sessionStorage.setItem('loginStatus', true)
                sessionStorage.setItem('username', res.result.username)
                sessionStorage.setItem('avatar', res.result.avatar)
                self.$router.push({name: 'Home'})
                self.$message.success(res.message)
              } else {
                self.$message.warning(res.message)
              }
            })
          }
        }
      )
    },
    handleChange (info) {
      if (info.file.status === 'uploading') {
        this.loading = true
        return
      }
      if (info.file.status === 'done') {
        // Get this url from response in real world.
        getBase64(info.file.originFileObj, (imageUrl) => {
          this.imageUrl = imageUrl
          this.avatar = info.file.response.message
          console.log(info)
          this.loading = false
        })
      }
    },
    beforeUpload (file) {
      const isJPG = file.type === 'image/jpeg'
      if (!isJPG) {
        this.$message.error('You can only upload JPG file!')
      }
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isLt2M) {
        this.$message.error('Image must smaller than 2MB!')
      }
      return isJPG && isLt2M
    },
    createAccount () {
      this.login = false
    },
    handleChangeCheck  (e) {
      this.checkNick = e.target.checked
      this.$nextTick(() => {
      })
    },
    accountRegister () {
      this.form.validateFields(
        (err, value) => {
          if (!err) {
            var self = this
            let user = Object.assign(this.model, value)
            if (self.username) {
              user.avatar = self.avatar
              user.username = self.username
              self.$axios.post(self.url.register, user).then((res) => {
                if (res.success) {
                  self.login = true
                  self.$message.success(res.message)
                } else {
                  self.$message.warning(res.message)
                }
              })
            } else {
              self.$message.warning('您还没有输入用户名!')
            }
          }
        }
      )
    },
    resetFieldsValue () {
      this.form.resetFields()
    },
    backToLoginPage () {
      this.login = true
    }
  }
}
</script>
<style scoped>
  #loginBody {
    position: fixed;
    width: 100%;
    height: 100%;
    background-color: dodgerblue;
    margin-top: -60px;
  }
  #card-body{
    width: 450px;
    height: 400px;
    margin: 80px 0 0 35%;
    text-align: left;
    overflow: hidden;
    padding: 20px;
    box-shadow: gray 0 0 5px 0;
  }
  #sign-body{
    width: 450px;
    text-align: left;
    margin: 20px 0 0 35%;
    padding: 20px;
    box-shadow: gray 0 0 5px 0;
  }
</style>
