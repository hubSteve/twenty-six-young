<template>
  <a-modal
    :title="title"
    :width="800"
    :ok=false
    :visible="visible"
    :confirmLoading="confirmLoading"
    :okButtonProps="{ props: {disabled: disableSubmit} }"
    style="margin-top: -65px"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">

    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="文章主题"
          :hidden="false"
          hasFeedback >
          <a-input id="departName" placeholder="请输入主题" v-decorator="['title', {} ]"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="图片">
          <a-input v-decorator="['picPath',{}]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="分类id">
          <a-input placeholder="请输入传真" v-decorator="['categoryId', {}]"  />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="联系方式">
          <a-input placeholder="请输入联系方式" v-decorator="['contact', {}]"  />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="主题介绍">
          <a-textarea placeholder="请输入文章主题介绍" v-decorator="['presentation', {}]"  />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="全部内容">
          <a-textarea placeholder="请输入文章的全部内容" v-decorator="['content', {}]"  />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import pick from 'lodash'
import ATextarea from 'ant-design-vue/es/input/TextArea'
export default {
  name: 'JavaLanguageModal',
  components: { ATextarea },
  data () {
    return {
      title: '操作',
      visible: false,
      disableSubmit: false,
      model: {},
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      confirmLoading: false,
      form: this.$form.createForm(this),
      validatorRules: {
        /* departName:{rules: [{ required: true, message: '请输入机构/部门名称!' }]},
        orgCode:{rules: [{ required: true, message: '请输入机构编码!' }]},
        mobile:{rules: [{validator:this.validateMobile}]} */
      },
      url: {
        add: this.HOST + '/language/java/add'
      }
    }
  },
  created () {
  },
  methods: {
    add () {
      this.edit({})
    },
    edit (record) {
      this.form.resetFields()
      this.model = Object.assign({}, {})
      this.visible = true
      this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.model, 'title', 'picPath', 'categoryId', 'contact',
          'description', 'content', 'viewCount', 'commentCount', 'href', 'avatar'))
      })
    },
    close () {
      this.$emit('close')
      this.disableSubmit = false
      this.visible = false
    },
    handleOk () {
      const that = this
      // 触发表单验证
      this.form.validateFields((err, values) => {
        if (!err) {
          that.confirmLoading = true
          let formData = Object.assign(this.model, values)
          // 时间格式化
          console.log(formData)
          that.$axios.post(this.url.add, formData).then((res) => {
            if (res.success) {
              that.$message.success(res.message)
              that.$emit('ok')
            } else {
              that.$message.warning(res.message)
            }
          }).finally(() => {
            that.confirmLoading = false
            that.close()
          })
        }
      })
    },
    handleCancel () {
      this.close()
    }
  }
}
</script>

<style scoped>
</style>
