<template>

  <div style="background-color: white">

    <a-card>

      <a-col :md="4" :sm="4">

        <a-card>
        </a-card>

      </a-col>

      <a-col :md="20" :sm="20">

        <a-form :form="form">

          <a-card :style="{backgroundColor: writeBoardColor}">

            <!-- 文章标题 -->
            <a-row style="text-align: left">
              <a-form-item>
                <a-input style="width: 100%;" v-decorator="['title', validatorRules.title ]" placeholder="请输入文章标题"/>
              </a-form-item>
            </a-row>

            <!--<a-col :md="4" :sm="4">-->
            <!--<a-button @click="getUEContent">获取数据</a-button>-->
            <!--</a-col>-->
            <a-row class="docMain" style="z-index: 0; width: 100%">

              <u-editor :default-msg="defaultMsg" :config="config" ref="UEditor"></u-editor>
              <span class="tips">
              Tips: 附件大小不超过10M
            </span>
            </a-row>

            <a-row style="text-align: left; margin-top: 10px">
              <span>个人标签:</span>
              <a-button-group v-for="(item, index) in tags" :key="index">
                <a-button style="height: 25px">{{ item }}</a-button>
                <a-icon
                  style="height: 25px; cursor: pointer; margin-right: 5px; color: red"
                  type="close"
                  @click="deleteTag(index)"
                />
              </a-button-group>
              <a-input
                v-if="writable"
                :style="{width: widths + 'px', border: none, height: '25px'}"
                v-model="value"
                v-focus
                @keydown.enter="tagInput($event, 'down')"
                @blur.prevent="tagInput($event, 'blur')"
              />
              <a-button v-if="tags.length < 5 || tags.length === 0" @click="isTagWritable" style="height: 25px;" type="primary" icon="plus"/>
            </a-row>

            <a-row style="text-align: left; margin-top: 10px">
              <span style="color: gray; margin-left: 60px">最多添加5个标签</span>
            </a-row>

            <a-row :md="24" :sm="24" style="text-align: left;">
              <a-form-item>
                <span>个人分类: </span>
                <a-select
                  defaultValue="['L01']"
                  style="width: 150px"
                  v-decorator="['categoryId', { initialValue: 'L01', rules: [{required: true, message: '别忘记选择个人分类!'}]}  ]">
                  <a-select-option  v-for="(lang, index) in dictMap.computer_lang" :key="index" :value="lang.itemValue">
                    {{ lang.itemText }}
                  </a-select-option>
              </a-select>
              </a-form-item>
            </a-row>

            <a-row :md="24" :sm="24" style="text-align: left;">
              <a-col :md="6" :sm="6">
                <a-form-item>
                  <span>文章分类: </span>
                  <a-select
                    style="width: 150px"
                    v-decorator="['articleType', { initialValue: 'Original', rules: [{required: true, message: '别忘记选择文章分类!'}]} ]">
                    <a-select-option v-for="(type, index) in dictMap.articleType" :key="index" :value="type.itemValue">
                      {{ type.itemText }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>

              <a-col :md="6" :sm="6">
                <a-form-item>
                  <span>博客分类: </span>
                  <a-select
                    style="width: 150px"
                    v-decorator="['blogType', { initialValue: 'ArtificialIntelligence', rules: [{required: true, message: '别忘记选择博客分类!'}]}  ]">
                    <a-select-option v-for="(type, index) in dictMap.blogType" :key="index" :value="type.itemValue">
                      {{ type.itemText }}
                    </a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
            </a-row>

            <a-row style="text-align: left">

              <a-button title="发布" class="submitButton" @click="save" type="primary">发布博客</a-button>

            </a-row>

          </a-card>
        </a-form>

      </a-col>

    </a-card>

  </div>

</template>

<script>

import Vue from 'vue'
import UEditor from '../../components/UEditor'
import ARow from 'ant-design-vue/es/grid/Row'
import ACol from 'ant-design-vue/es/grid/Col'
import AFormItem from 'ant-design-vue/es/form/FormItem'
Vue.directive('focus', {
  // 当被绑定的元素插入到 DOM 中时……
  inserted: function (el) {
    // 聚焦元素
    el.focus()
  }
})
export default {
  name: 'WritingArticle',
  components: { AFormItem, ACol, ARow, UEditor },
  data () {
    return {
      // boolean
      writable: false,
      // str
      widths: 30,
      value: '',
      title: '',
      docMain: '',
      docTitle: '',
      defaultMsg: '',
      docDescription: '',
      writeBoardColor: '#dadada',
      createAuthor: '',
      // arr
      tags: [],
      // object
      model: {},
      labelCol: {
        xs: { span: 2 },
        sm: { span: 2 }
      },
      tagInputTrigger: false,
      wrapperCol: {
        xs: { span: 22 },
        sm: { span: 22 }
      },
      config: {
        initialFrameWidth: 900,
        initialFrameHeight: 350
      },
      form: this.$form.createForm(this),
      url: {
        add: this.HOST + '/language/java/add',
        dict: this.HOST + '/language/java/getDicts'
      },
      validatorRules: {
        title: {rules: [{required: true, message: '别忘记输入文章标题!'}]},
        categoryId: {rules: [{required: true, message: '请选择个人分类!'}]},
        articleType: {rules: [{required: true, message: '请选择文章分类!'}]},
        blogType: {rules: [{required: true, message: '请选择博客分类!'}]}
      },
      dictMap: {computer_lang: {}, articleType: {}, blogType: {}}
    }
  },
  created () {
    this.createAuthor = sessionStorage.getItem('username')
    this.loadDictionaries()
  },
  directives: {
    focus: {
      // 指令的定义
      inserted: function (el) {
        el.focus()
      }
    }
  },
  methods: {
    getUEContent () {
    },
    save () {
      let content = this.$refs.UEditor.getUEContent()
      console.log('content', content)
      const that = this
      // 触发表单验证
      this.form.validateFields((err, values) => {
        if (!err) {
          that.confirmLoading = true
          let formData = Object.assign(this.model, values)
          formData.content = content
          formData.articleTags = that.tags.join(',')
          formData.createAuthor = that.createAuthor
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
          })
        }
      })
    },
    onChange (value) {
      this.value = value
    },
    tagInput (e, code) {
      if (code === 'blur') {
        if (!this.tagInputTrigger) {
          this.tagInputByEvent(e)
          this.tagInputTrigger = false
        }
      } else if (code === 'down') {
        this.tagInputByEvent(e)
        this.tagInputTrigger = true
      }
    },
    tagInputByEvent (e) {
      let value = e.target.value
      let _this = this
      let size = _this.tags.length
      if (size < 5) {
        if (value) {
          _this.tags.push(value)
          _this.writable = false
        } else {
          _this.writable = false
        }
      } else {
        _this.writable = false
      }
    },
    addTag () {
      this.writable = true
    },
    isTagWritable () {
      let _this = this
      let size = _this.tags.length
      if (size < 5) {
        this.value = ''
        this.writable = true
      }
    },
    deleteTag (index) {
      let _this = this
      let tags = _this.tags
      let newTags = []
      for (let i = 0, size = tags.length; i < size; i++) {
        if (index !== i) {
          newTags.push(tags[i])
        }
      }
      _this.tags = newTags
    },
    loadDictionaries () {
      let map = this.dictMap
      let _this = this
      for (let key in map) {
        _this.$axios.get(_this.url.dict, {params: {dictCode: key}}).then((res) => {
          if (res.success) {
            map[key] = res.result
          }
        })
      }
    }
  },
  watch: {
    'value': function (value) {
      var n = value.match(/\d/g)
      var e = value.match(/[a-z]/ig)
      var c = value.match(/[^ -~]/g)
      this.widths = 30
      let nLen = n === null ? 0 : n.length
      let eLen = e === null ? 0 : e.length
      let cLen = c === null ? 0 : c.length
      let length = value.length
      if (length !== 0) {
        this.widths = this.widths + cLen * 14 + eLen * 6 + nLen * 7
      }
    }
  }
}
</script>

<style scoped>
  .tips{
    color: red;
    float: left;
  }
  .submitButton{
    margin: 0 0 0 60px;
  }
</style>
