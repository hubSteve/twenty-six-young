<template>
  <a-card style="margin: -25px; background-color: wheat;" :bordered="false">
    <a-col :md="6" :sm="6">
      <a-card :bordered="false" style="text-align: left; font-size: medium">
        <template class="ant-card-actions" slot="actions">
          <span>原创: <b>23</b></span>
          <span>喜欢: <b>23</b></span>
          <span>评论: <b>23</b></span>
        </template>
        <a-card-meta
          title="Steve"
          description="博客专家">
          <a-avatar slot="avatar" src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"/>
        </a-card-meta>
        <a-divider type="horizontal"/>
        <template class="ant-card-actions">
          <a-col :md="12" :sm="12">
            <span>等级: &nbsp;&nbsp; 23</span>
          </a-col>
          <a-col :md="12" :sm="12">
            <span>排名: &nbsp;&nbsp; 23</span>
          </a-col>
          <a-col :md="12" :sm="12">
            <span>访问: &nbsp;&nbsp;100万+</span>
          </a-col>
          <a-col :md="12" :sm="12">
            <span>积分: &nbsp;&nbsp;100万+</span>
          </a-col>
        </template>
      </a-card>
    </a-col>
    <a-col :md="17" :sm="17" style="padding: 0 5px 0 5px">
      <a-card :bordered="false" style="background-color: white">
        <div id="components-layout-demo-basic">
          <a-layout>
            <a-layout-header>
              <b style="margin-left: -47px">
                <a-button shape="circle">原</a-button>
                {{ article.title }}
              </b>
            </a-layout-header>
            <a-row
              :style="{textAlign: 'left', backgroundColor: 'white', color: 'lightslategrey', paddingBottom: '15px'}">
              <span style="font-size: medium">{{ article.createTime }}</span>
              <span style="font-size: medium; padding: 0 0 0 20px">
                  <a>{{ article.createAuthor }}</a>
                </span>
              <span style="font-size: medium; padding: 0 0 0 20px">阅读数:  {{ article.viewCount }}</span>
              <a-divider style="margin-bottom: 0px"/>
            </a-row>
            <a-layout-content>
              <span style="display: block; text-align: left" v-html="article.content">{{ article.content }}</span>
              <a-divider/>
            </a-layout-content>
            <a-layout-footer>
              <div>
                <a-comment>
                  <a-avatar
                    :src="avatarUrl"
                    slot="avatar"
                    :style="{ backgroundColor:  avatar}"
                  >
                    {{ username }}
                  </a-avatar>
                  <div slot="content">
                    <a-form-item>
                      <a-input placeholder="想对博主说点什么..." :rows="4" @change="handleChange" :value="value"/>
                    </a-form-item>
                    <a-form-item>
                      <a-button
                        htmlType="submit"
                        :loading="submitting"
                        @click="handleSubmit"
                        type="primary"
                        style="float: right"
                      >
                        评论
                      </a-button>
                    </a-form-item>
                  </div>
                </a-comment>
                <a-list
                  v-if="comments.length"
                  :dataSource="comments"
                  :header="`${comments.length} ${comments.length > 1 ? 'replies' : 'reply'}`"
                  itemLayout="horizontal"
                >
                  <a-list-item slot="renderItem" slot-scope="item, index">
                    <a-comment
                      @mouseout="showCommentButton(0,item.id)"
                      @mouseover="showCommentButton(1, item.id)"
                      :author="item.commentator"
                      :avatar="avatarUrl"
                      :content="item.comment"
                      :datetime="item.createTime"
                    >
                        <span :id="item.id" style="display: none;">
                          <a>回复</a>
                          <a-divider type="vertical"/>
                          <a><a-icon type="like"/></a>
                          <a-divider type="vertical"/>
                          <a>查看回复</a>
                        </span>
                    </a-comment>
                  </a-list-item>
                </a-list>
              </div>
            </a-layout-footer>
          </a-layout>
        </div>
      </a-card>
    </a-col>
    <div
      style="font-weight: bold;cursor: pointer; position: fixed; right: 92px; width: 40px; height: 40px; line-height: 40px; top: 82px; font-size: small"
    >
      <div
        v-for="(icon, index) in icons"
        :id="icon.key"
        @mouseleave="exchange('mark', index)"
        @mouseover="exchange('text', index)"
        class="ant-back-top-inner"
        style="background-color: white; margin-bottom: 2px">
        <span v-if="isIcon[index].boolean">
          <span v-if="icon.key === 'like'">
            <a-icon theme="filled" :type="icon.key"/>1</span>
          <a-icon v-else :type="icon.key"/>
        </span>
        <span v-else>{{icon.value}}</span>
      </div>
    </div>
  </a-card>
</template>

<script>
import moment from 'moment'
import $ from 'jquery'
import { filterObj } from '@/utils/objectfilter'
import ACol from 'ant-design-vue/es/grid/Col'
import ARow from 'ant-design-vue/es/grid/Row'
export default {
  name: 'ReadingArticle',
  components: { ARow, ACol },
  data () {
    return {
      columns: [
        {
          title: '文章ID',
          align: 'center ',
          dataIndex: 'javaId'
        }, {
          title: '评论内容',
          align: 'center',
          dataIndex: 'comment'
        }, {
          title: '回复内容',
          align: 'center',
          dataIndex: 'reply'
        }, {
          title: '回复者',
          align: 'center',
          dataIndex: 'replier'
        }, {
          title: '评论者',
          align: 'center',
          dataIndex: 'commentator'
        }, {
          title: '评论者头像',
          align: 'center',
          dataIndex: 'avatar'
        }
      ],
      article: {},
      comments: [],
      icons: [{key: 'like', value: '喜欢'},
        {key: 'message', value: '评论'},
        {key: 'left', value: '上一页'},
        {key: 'right', value: '下一页'}],
      isIcon: [{'boolean': true}, {'boolean': true}, {'boolean': true}, {'boolean': true}],
      commentReply: {},
      hidden: true,
      visible: false,
      articleAndTypesVO: {article: {}, type: ''},
      submitting: false,
      commentButtonHidden: true,
      value: '',
      content: '',
      uuid: '',
      avatar: '',
      avatarUrl: '',
      visibilityHeight: '-200',
      man: 'files/20190601/avatar-1559375334741-1559375443714.jpg',
      woman: '',
      username: '',
      moment,
      url: {
        imgView: this.HOST + '/image/handle/view',
        uuid: this.HOST + '/language/java/getUUUID',
        update: this.HOST + '/language/java/update',
        save: this.HOST + '/language/java/saveComment',
        query: this.HOST + '/language/java/queryComments'
      },
      ipagination: {
        current: 1,
        pageSize: 5,
        pageSizeOptions: ['5', '10', '20'],
          showTotal: (total, range) => {
          return range[0] + '-' + range[1] + '共' + total + '条'
        },
        showQuickJumper: true,
        showSizeChanger: true,
        total: 0
      },
      isorter: {
        column: 'createTime',
        order: 'desc'
      },
      config: {type: Object}
    }
  },
  mounted () {
    const _this = this
    _this.editor = UE.getEditor('editor', _this.config)
    _this.editor.addListener('ready', function () {
      _this.editor.setContent(this.article.content) // 确保UE加载完成后，放入内容。
    })
  },
  created () {
    console.log(this.$route.matched.pop().path)
    this.article = this.$route.params.article
    this.avatar = sessionStorage.getItem('avatar')
    this.username = sessionStorage.getItem('username')
    this.visitTimeCounter()
    this.loadData()
    this.getAvatarView()
  },
  methods: {
    loadData () {
      let java = this.article
      if (java) {
        let id = java.id
        this.$axios.get(this.url.query, {params: {id: id}}).then((res) => {
          if (res.success) {
            this.comments = res.result
            this.commentReply = Object.assign({}, res.result[0])
          } else {
            console.log(res.message)
          }
        })
      }
    },
    handleSubmit () {
      this.getUUID()
      if (!this.value) {
        return
      }
      let java = this.article
      if (java.createAuthor === this.username) {
        this.$message.warning('您不能评论自己')
        return
      } else {
        this.submitting = true
        setTimeout(() => {
          this.submitting = false
          this.commentReply = {
            javaId: java.id,
            comment: this.value,
            replier: '',
            commentator: this.username,
            avatar: '#f0000'
          }
          this.comments.push(this.commentReply)
          this.value = ''
          this.saveComment()
        }, 1000)
      }
      console.log(this.comments)
    },
    getUUID () {
      this.$nextTick(() => {
        this.$axios.get(this.url.uuid).then((res) => {
          if (res.success) {
            this.uuid = res.result
          }
        })
      })
    },
    getQueryParams () {
      // 获取查询条件
      var param = Object.assign(this.queryParam, this.isorter, this.filters)
      param.field = this.getQueryField()
      param.pageNo = this.ipagination.current
      param.pageSize = this.ipagination.pageSize
      return filterObj(param)
    },
    getQueryField () {
      // TODO 字段权限控制
      var str = 'id,'
      for (var a = 0, length = this.columns.length; a < length; a++) {
        str += ',' + this.columns[a].dataIndex
      }
      return str
    },
    saveComment () {
      this.$axios.post(this.url.save, this.commentReply).then((res) => {
        if (res.success) {
          this.loadData()
          console.log('评论成功')
        }
      })
    },
    visitTimeCounter () {
      setTimeout(this.commentArticle(1), 3000)
    },
    commentArticle (number) {
      var self = this
      self.articleAndTypesVO.article = self.article
      self.articleAndTypesVO.type = 'visit'
      console.log(self.articleAndTypesVO)
      self.$nextTick(() => {
        if (number === 1) {
          self.$axios.post(self.url.update, self.articleAndTypesVO).then((res) => {
            if (res.success) {
            }
          })
        }
      })
    },
    handleChange (e) {
      this.value = e.target.value
    },
    exchange (rule, index) {
      this.isIcon[index].boolean = rule === 'mark'
    },
    showCommentButton (code, id) {
      if (code === 0) {
        $(function () {
          $('#' + id).hide()
        })
      } else {
        $(function () {
          $('#' + id).show()
        })
      }
    },
    getAvatarView () {
      this.avatarUrl = this.url.imgView + '/' + this.man
    }
  }
}
</script>
<style scoped>
  #components-layout-demo-basic {
  }
  #components-layout-demo-basic .ant-layout-header,
  #components-layout-demo-basic .ant-layout-footer {
    background: white;
    font-size: x-large;
    text-align: left;
  }
  #components-layout-demo-basic .ant-layout-footer {
    line-height: 1.5;
  }
  #components-layout-demo-basic .ant-layout-sider {
    background: #3ba0e9;
    color: #fff;
    line-height: 120px;
  }
  #components-layout-demo-basic .ant-layout-content {
    background-color: white;
    color: black;
    font-size: large;
  }
  #components-layout-demo-basic > .ant-layout {
    margin-bottom: 48px;
  }
  #components-layout-demo-basic > .ant-layout:last-child {
    margin: 0;
  }
</style>
