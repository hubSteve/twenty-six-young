<template>
    <a-modal
      v-model="visible"
      title="Title"
      :loading="loading"
      onOk="handleOk">
      <a-upload
        listType="picture-card"
        class="avatar-uploader"
        :data="{'isup':1}"
        :showUploadList="false"
        :action="uploadAction"
        :beforeUpload="beforeUpload"
        @change="handleChange"
      >
        <img v-if="imageUrl" :src="imageUrl" alt="avatar" style="width: 100px"/>
        <div v-else>
          <a-icon :type="loading ? 'loading' : 'plus'" />
          <div class="ant-upload-text">Upload</div>
        </div>
      </a-upload>
    </a-modal>
</template>

<script>
function getBase64 (img, callback) {
  const reader = new FileReader()
  reader.addEventListener('load', () => callback(reader.result))
  reader.readAsDataURL(img)
}
export default {
  name: 'LoginModel',
  data () {
    return {
      visible: false,
      loading: false,
      uploadLoading: false,
      imageUrl: '',
      avatar: '',
      url: {
        fileUpload: this.HOST + '/image/handle/upload',
        imgView: '/26young/image/handle/view'
      }
    }
  },
  computed: {
    uploadAction: function () {
      return this.url.fileUpload
    }
  },
  created () {
  },
  methods: {
    showModal () {
      this.visible = true
    },
    handleOk (e) {
      this.loading = true
      setTimeout(() => {
        this.visible = false
        this.loading = false
      }, 3000)
    },
    handleCancel (e) {
      this.visible = false
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
    getAvatarView () {
      return this.url.imgView + '/' + this.avatar
    }
  }
}
</script>

<style scoped>
  .avatar-uploader > .ant-upload {
    width: 128px;
    height: 128px;
  }
  .ant-upload-select-picture-card i {
    font-size: 32px;
    color: #999;
  }

  .ant-upload-select-picture-card .ant-upload-text {
    margin-top: 8px;
    color: #666;
  }
</style>
