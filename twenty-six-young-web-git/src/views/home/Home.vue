<template>

  <a-card :bordered="false" style="background-color: #f0f2f5; margin: -25px -25px -25px -25px; padding-left: -50px">

    <a-col :md="2" :sm="2">
      <!-- 左侧菜单 -->
      <div id="leftSlider" :style="{position: position, bottom: '0'}">
        <div style="margin-bottom: 3px;border: 1px solid #00b7ee; width: 90px; height: 30px; background-color: #00b7ee; color: white; line-height: 30px">测试</div>
        <div
          v-for="type in dictMap.blogType"
          :id="type.itemValue"
          @click="leftSliderClick(type.itemValue)"
          @mouseover="leftSliderHandle(type.itemValue, '1')"
          @mouseleave="leftSliderHandle(type.itemValue, '2')"
          style="cursor: pointer;border: 0.1px; width: 90px; height: 30px; background-color: white; line-height: 30px">
          {{ type.itemText }}
        </div>
      </div>
   </a-col>

    <!-- 中间部分 -->
    <a-col :md="16" :sm="16" style="padding-left: 10px;">
      <a-card hoverable :bordered="false">
        <a-carousel arrows dotsClass="slick-dots slick-thumb" :vertical="true" autoplay style="margin-right: 40px">
          <div v-for="item in 4" :key="item">
            <img :src="baseUrl+'abstract0'+item+'.jpg'" style="height: 300px; width: 700px;"/>
          </div>
        </a-carousel>
      </a-card>

      <a-card hoverable :bordered="false" style="text-align: left; overflow: hidden">
        <a-list
          itemLayout="vertical"
          size="large"
          :pagination="false"
          :dataSource="listData"
        >
          <a-list-item :style="{fontSize: 'large'}" slot="renderItem" slot-scope="item, index" key="item.title">
            <template slot="actions">
              <a @click="authorDetail(item.createAuthor)">
                {{ item.createAuthor }}
              </a>
              发布于 {{ item.createTime }}
              <span :key="index" style="margin-left: 150px">
          <!--<a-icon type="like" style="margin-right: 8px" @click="likeArticle(item)" />-->
                <!--{{ item.likeCount }}-->
                <!--<a-divider type="vertical"/>-->
                <!--<a-icon type="dislike" style="margin-right: 8px" @click="dislikeArticle(item)" />-->
                <router-link :to="{name:'ReadingArticle', params:{article: item}}">
                阅读数: {{ item.viewCount }}
                </router-link>

                <a-divider type="vertical"/>
                <router-link :to="{name:'ReadingArticle', params:{article: item}}">
                评论数: {{ item.commentCount }}
                </router-link>
              </span>
            </template>
            <!--<img slot="extra" width="272" alt="logo" src="https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png" />-->
            <a-list-item-meta
            >
              <a slot="title" style="font-size: large" :href="item.href"><b>{{item.title}}</b></a>
            </a-list-item-meta>
            <span v-html="item.content.substring(0, 110)" style="font-size: medium;">
            </span>
            <a>...</a>
          </a-list-item>
          <div slot="footer"><b>ant design vue</b> footer part</div>
        </a-list>
      </a-card>
    </a-col>

    <!-- 右侧部分 -->
    <a-col :md="6" :sm="6" :style="{paddingLeft: '8px'}">

      <div >
        <a-card :bordered="false" >
          <span style="border-left: solid red 6px; padding: 3px;margin-left: -156px">今日推荐</span>
          <a-list
            itemLayout="horizontal"
            :dataSource="data"
          >
            <a-list-item slot="renderItem" slot-scope="item, index" :style="{textAlign: 'left'}">
              <a-list-item-meta>
                <a slot="title" style="word-break: break-word" href="https://vue.ant.design/">{{item.title}}</a>
                <a-avatar shape="square" slot="avatar"
                          src="https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png"/>
              </a-list-item-meta>
            </a-list-item>
          </a-list>
        </a-card>

        <a-card title="今日标签" style="margin: 8px 0 0 0;">
          <a-card-grid style="width:33.3%;">
            <a>Content</a></a-card-grid>
          <a-card-grid style="width:33.4%;text-align:center">Content</a-card-grid>
          <a-card-grid style="width:33.3%;text-align:center">Content</a-card-grid>
          <a-card-grid style="width:33.3%;text-align:center">Content</a-card-grid>
          <a-card-grid style="width:33.4%;text-align:center">Content</a-card-grid>
          <a-card-grid style="width:33.3%;text-align:center">Content</a-card-grid>
        </a-card>
      </div>
    </a-col>
    <div id="components-back-top-demo-custom">
      <a-back-top
        @click="articleWriting"
        @mouseover="editExchange('text')"
        @mouseleave="editExchange('mark')"
        :visibilityHeight="visibilityHeightEdit"
        style="margin-right: -60px; line-height: 40px;"
      >
        <div v-if="edit" class="ant-back-top-inner">
          <a-icon type="form"/>
        </div>
        <div v-else class="ant-back-top-inner" style="font-size: 2px; background-color: #ca0c16; color: white;">编辑</div>
      </a-back-top>
      <a-back-top
        style="margin: -10px -60px 45px 0;"
        :visibilityHeight="visibilityHeightBack"
        @mouseleave="upExchange('mark')"
        @mouseover="upExchange('text')"
      >
        <div v-if="upToTop" class="ant-back-top-inner" style="line-height: 40px;">
          <a-icon type="up"/>
        </div>
        <div v-else class="ant-back-top-inner" style="font-size: 2px; background-color: #ca0c16; color: white;">返回<br/>顶部
        </div>
      </a-back-top>
    </div>
  </a-card>
</template>
<script>
import $ from 'jquery'
import {filterObj} from '@/utils/objectfilter'
const baseUrl = 'https://raw.githubusercontent.com/vueComponent/ant-design-vue/master/components/vc-slick/assets/img/react-slick/'
const data = [
  {
    title: '中国是怎么崛起的?'
  },
  {
    title: 'Brian Goetz: “Stewardship: the Sobering Parts”'
  },
  {
    title: 'w.githubusercontent.com/vueComponent/ant-design'
  },
  {
    title: 'Ant Design Title 4'
  }
]
export default {
  components: { },
  data () {
    return {
      columns: [
        {
          title: '主题',
          align: 'center ',
          dataIndex: 'title'
        }, {
          title: '图片路径',
          align: 'center',
          dataIndex: 'picPath'
        }, {
          title: '分类id',
          align: 'center',
          dataIndex: 'categoryId',
          scopedSlots: {customRender: 'avatarslot'}
        }, {
          title: '联系方式',
          align: 'center',
          dataIndex: 'contact'
        }, {
          title: '展示内容',
          align: 'center',
          dataIndex: 'description'
        }, {
          title: '全部内容',
          align: 'center',
          dataIndex: 'content'
        }, {
          title: '邮箱',
          align: 'center',
          dataIndex: 'email'
        }, {
          title: '主题跳转URL',
          align: 'center',
          dataIndex: 'href'
        }, {
          title: '头像',
          align: 'center',
          dataIndex: 'href'
        }, {
          title: '操作',
          dataIndex: 'action',
          scopedSlots: {customRender: 'action'},
          align: 'center',
          width: 170
        }
      ],
      data,
      position: 'none',
      starTheme: 'outlined',
      likeTheme: 'outlined',
      messageTheme: 'outlined',
      visibilityHeightEdit: '-200',
      visibilityHeightBack: '300',
      articleAndTypesVO: {article: {}, type: {}},
      listData: [],
      pagination: {
        onChange: (page) => {
          console.log(page)
        },
        pageSize: 20
      },
      actions: [
        { id: '1', type: 'star', text: '156' },
        { id: '2', type: 'like', text: '156' },
        { id: '3', type: 'message', text: '2' }
      ],
      queryParam: {},
      ipagination: {
        current: 1,
        pageSize: 20,
        pageSizeOptions: ['5', '10', '20'],
        showTotal: (total, range) => {
          return range[0] + '-' + range[1] + '共' + total + '条'
        },
        showQuickJumper: true,
        showSizeChanger: true,
        total: 0
      },
      upToTop: true,
      edit: true,
      isorter: {
        column: 'createTime',
        order: 'desc'
      },
      baseUrl,
      dictMap: {blogType: {}},
      url: {
        dict: this.HOST + '/language/java/getDicts',
        list: this.HOST + '/language/java/list',
        update: this.HOST + '/language/java/update',
        queryInfo: this.HOST + '/language/java/queryVisitInfoByIpOrUsername'
      }
    }
  },
  mounted: function () {
    window.addEventListener('scroll', this.handleScroll, false)// 监听（绑定）滚轮滚动事件
  },
  created () {
    console.log(this.$route.query)
    this.loadData()
    this.loadDictionaries()
  },
  methods: {
    loadData () {
      let that = this
      let params = that.getQueryParams()
      this.$nextTick(() => {
        that.$axios.get(that.url.list, params).then((res) => {
          if (res.success) {
            this.listData = res.result.records
          }
        })
      })
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
    },
    queryInfo () {
      this.$axios.get(this.url.queryInfo).then((res) => {
        if (res.success) {
          console.log(res.result.length)
        }
      })
    },
    handleTableChange (pagination, filters, sorter) {
      // 分页、排序、筛选变化时触发
      // TODO 筛选
      if (Object.keys(sorter).length > 0) {
        this.isorter.column = sorter.field
        this.isorter.order = sorter.order === 'ascend' ? 'asc' : 'desc'
      }
      this.ipagination = pagination
      this.loadData()
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
    articleWriting () {
      this.$router.push({name: 'WritingArticle'})
    },
    getImgUrl (i) {
      return `${baseUrl}abstract0${i + 1}.jpg`
    },
    likeArticle (item) {
      this.articleAndTypesVO.article = item
      this.articleAndTypesVO.type = 'like'
      this.updateData(this.articleAndTypesVO)
    },
    dislikeArticle (item) {
      this.articleAndTypesVO.article = item
      this.articleAndTypesVO.type = 'dislike'
      this.updateData(this.articleAndTypesVO)
    },
    updateData (articleAndTypesVO) {
      this.$axios.post(this.url.update, articleAndTypesVO).then((res) => {
        if (res.success) {
          this.loadData()
        } else {
          this.$message.warning(res.message)
        }
      })
    },
    upExchange (rule) {
      this.upToTop = rule === 'mark'
      console.log(this.upToTop)
    },
    editExchange (rule) {
      this.edit = rule === 'mark'
    },
    commentingArticle (item) {
    },
    authorDetail (author) {
    },
    handleScroll: function () {
      let clientHeight = document.documentElement.clientHeight || document.body.clientHeight
      // 设备/屏幕高度
      let scrollObj = document.getElementById('leftSlider') // 滚动区域
      let scrollTop = document.body.scrollTop + document.documentElement.scrollTop// div 到头部的距离
      let scrollHeight = scrollObj.offsetHeight // 滚动条的总高度
      // console.log('scrollHeight', scrollHeight)
      // console.log('scrollTop + ', scrollTop, clientHeight)
      // console.log("可视区高度"+document.documentElement.clientHeight+
      //   '-----------'+window.innerHeight); // 可视区域高度
      // console.log("滚动高度"+document.body.scrollTop+
      //   `------`+document.documentElement.scrollTop); // 滚动高度
      // console.log("文档高度"+document.body.offsetHeight); // 文档高度
      // // 滚动条到底部的条件
      if (scrollTop + 600 > scrollHeight) {
        // div 到头部的距离 + 屏幕高度 = 可滚动的总高度
        this.position = 'fixed'
      } else {
        this.position = 'initial'
        console.log('none')
      }
    },
    leftSliderHandle (id, rule) {
      if (rule === '1') {
        $(function () {
          $('#' + id).css({'background-color': '#00b7ee', 'color': 'white'})
        })
      } else {
        $(function () {
          $('#' + id).css({'background-color': 'white', 'color': 'black'})
        })
      }
    },
    leftSliderClick (id) {
    }
  },
  destroyed: function () {
    window.removeEventListener('scroll', this.handleScroll)//  离开页面清除（移除）滚轮滚动事件
  }
}
</script>
<style>
  #components-back-top-demo-custom .ant-back-top {
    bottom: 100px;
  }
  #components-back-top-demo-custom .ant-back-top-inner {
    height: 40px;
    width: 40px;
    background-color: white;
    color: black;
    text-align: center;
    font-size: 20px;
  }
/*  .ant-carousel >>> .slick-thumb {
    bottom: -45px;
  }
  .ant-carousel >>> .slick-thumb li {
    width: 60px;
    height: 45px;
  }
  .ant-carousel >>> .slick-thumb li img {
    width: 100%;
    height: 100%;
    filter: grayscale(100%);
  }
  .ant-carousel >>> .slick-thumb li.slick-active img{
    filter: grayscale(0%);
  }*/
</style>
<style scoped>
  body{
    background-color: cornflowerblue;
  }
</style>
