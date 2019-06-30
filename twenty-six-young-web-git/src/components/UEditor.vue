<template>
    <div id="UEditor">
      <script id="editor" type="text/plain"></script>
    </div>
</template>

<script>
export default {
  name: 'UEditor',
  data () {
    return {
      editor: null
    }
  },
  props: {//     <UEditor :defaultMsg = defaultMsg :config = config ref = "ue"></UEditor>
  //     defaultMsg: '这里是UEditor测试',
  //   config: {
  //     initialFrameWidth: 1200,
  //     initialFrameHeight: 350
  //   },
    defaultMsg: {
      type: String
    },
    config: {type: Object}
  },
  mounted () {
    const _this = this
    _this.editor = UE.getEditor('editor', _this.config)
    _this.editor.addListener('ready', function () {
      _this.editor.setContent(_this.defaultMsg) // 确保UE加载完成后，放入内容。
    })
    // UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl
    // UE.Editor.prototype.getActionUrl = function (action) {
    //   console.log(action)
    //   if (action === 'uploadimage' || action === 'uploadscrawl' || action === 'uploadimage') {
    //     return 'http://localhost:8080/26young/image/handle/uEditor'// 此处写自定义的图片上传路径
    //   } else if (action === 'uploadvideo') {
    //     return ''
    //   } else {
    //     return this._bkGetActionUrl.call(this, action)
    //   }
    // }
    console.log(_this.editor)
  },
  watch: {
  },
  created () {
    // this.getEditor()
  },
  methods: {
    getUEContent () {
      return this.editor.getContent()
    },
    // getEditor () {
    //   UE.getEditor('editor', this.config)
    //   UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl
    //   UE.Editor.prototype.getActionUrl = function (action) {
    //     console.log('action===>', action)
    //     if (action === 'uploadimage' || action === 'uploadscrawl' || action === 'uploadimage') {
    //       return 'http://localhost:8080/26young/image/handle/uEditor'// 此处写自定义的图片上传路径
    //     } else if (action === 'uploadvideo') {
    //       return ''
    //     } else {
    //       return this._bkGetActionUrl.call(this, action)
    //     }
    //   }
    // }
  },
  destroyed () {
    this.editor.destroy()
  }
}
</script>

<style scoped>

</style>
