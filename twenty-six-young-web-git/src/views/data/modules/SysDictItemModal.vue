<template>
  <a-drawer
      :title='title'
      :width='800'
      placement='right'
      :closable='false'
      @close='close'
      :visible='visible'
  >

    <a-spin :spinning='confirmLoading'>
      <a-form :form='form'>

        <a-form-item
          :hidden="true"
          :labelCol='labelCol'
          :wrapperCol='wrapperCol'
          label='dictId'>
          <a-input :readOnly="true" placeholder='请输入dictId' v-model="dictId" />
        </a-form-item>
        <a-form-item
          :labelCol='labelCol'
          :wrapperCol='wrapperCol'
          label='itemText'>
          <a-input placeholder='请输入itemText' v-decorator="['itemText', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol='labelCol'
          :wrapperCol='wrapperCol'
          label='itemValue'>
          <a-input placeholder='请输入itemValue' v-decorator="['itemValue', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol='labelCol'
          :wrapperCol='wrapperCol'
          label='sortOrder'>
          <a-input placeholder='请输入sortOrder' v-decorator="['sortOrder', {}]" />
        </a-form-item>
        <a-form-item
          :labelCol='labelCol'
          :wrapperCol='wrapperCol'
          label='description'>
          <a-input placeholder='请输入description' v-decorator="['description', {}]" />
        </a-form-item>

      </a-form>
    </a-spin>
    <a-button type='primary' @click='handleOk'>确定</a-button>
    <a-button type='primary' @click='handleCancel'>取消</a-button>
  </a-drawer>
</template>

<script>
import pick from 'lodash'
export default {
  name: 'SysDictItemModal',
  data () {
    return {
      title: '操作',
      visible: false,
      model: {},
      dictId: '',
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
      },
      url: {
        add: this.HOST + '/main/sysDictItem/add',
        edit: this.HOST + '/main/sysDictItem/edit'
      }
    }
  },
  created () {
  },
  methods: {
    add (dictId) {
      this.dictId = dictId
      this.edit({})
    },
    edit (record) {
      this.form.resetFields()
      this.model = Object.assign({}, record)
      this.visible = true
      this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.model, 'itemText', 'itemValue', 'sortOrder', 'description'))
        // 时间格式化
      })
    },
    close () {
      this.$emit('close')
      this.visible = false
    },
    handleOk () {
      const that = this
      // 触发表单验证
      this.form.validateFields((err, values) => {
        if (!err) {
          that.confirmLoading = true
          let httpurl = ''
          if (!this.model.id) {
            httpurl += this.url.add
          } else {
            httpurl += this.url.edit
          }
          let formData = Object.assign(this.model, values)
          // 时间格式化
          formData.dictId = that.dictId
          console.log(httpurl)
          that.$axios.post(httpurl, formData).then((res) => {
            if (res.success) {
              that.$message.success(res.message)
              that.$emit('ok')
            } else {
              that.$message.warning(res.message);
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
/** Button按钮间距 */
  .ant-btn {
    margin-left: 30px;
    margin-bottom: 30px;
    float: right;
  }
</style>
