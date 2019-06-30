<template>
  <a-card :bordered='false'>

    <!-- 查询区域 -->
    <div class='table-page-search-wrapper'>
      <a-form layout='inline'>
        <a-row :gutter='24'>

          <a-col :md='6' :sm='12'>
            <a-form-item label='账号'>
              <a-input placeholder='请输入账号查询' v-model='queryParam.username'></a-input>
            </a-form-item>
          </a-col>

          <a-col :md='6' :sm='8'>
            <span style='float: left;overflow: hidden' class='table-page-search-submitButtons'>
              <a-button type='primary' @click='searchQuery' icon='search'>查询</a-button>
              <a-button type='primary' @click='searchReset' icon='reload' style='margin-left: 8px'>重置</a-button>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class='table-operator' style='border-top: 5px; text-align: left; margin-top: 10px'>
      <a-button @click='handleAdd' type='primary' icon='plus'>添加用户</a-button>
      <a-dropdown v-if='10 > 0'>
        <a-menu slot='overlay' @click='handleMenuClick'>
          <a-menu-item key='1'>
            <a-icon type='delete' @click='batchDel'/>
            删除
          </a-menu-item>
        </a-menu>
        <a-button style='margin-left: 8px'>
          批量操作
          <a-icon type='down'/>
        </a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class='ant-alert ant-alert-info' style='margin-bottom: 16px'>
        <i class='anticon anticon-info-circle ant-alert-icon'></i>已选择<a style='font-weight: 600'></a>项
        <a style='margin-left: 24px' @click='onClearSelected'>清空</a>
      </div>

      <a-table
        ref='table'
        bordered
        size='middle'
        rowKey='id'
        :columns='columns'
        :dataSource='dataSource'
        :pagination='ipagination'
        :loading='loading'
        :rowSelection='{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}'
        @change='handleTableChange'>

        <template slot='avatarslot' slot-scope='text, record, index'>
          <div class='anty-img-wrap'>
            <img :src='getAvatarView(record.avatar)'/>
          </div>
        </template>

        <span slot='action' slot-scope='text, record'>
          <a @click='handleEdit(record)'>编辑</a>

          <a-divider type='vertical'/>

          <a-dropdown>
            <a class='ant-dropdown-link'>
              更多 <a-icon type='down'/>
            </a>
            <a-menu slot='overlay'>
              <a-menu-item>
                <a href='javascript:' @click='handleDetail(record)'>详情</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>
      </a-table>
    </div>
    <!-- table区域-end -->
    <java-language-modal ref='JavaLanguageModal' @ok="interactor"></java-language-modal>
  </a-card>
</template>

<script>
import { filterObj } from '@/utils/objectfilter'
import JavaLanguageModal from './modules/JavaLanguageModal'
export default {
  name: 'JavaLanguageList',
  components: {
    JavaLanguageModal
  },
  data () {
    return {
      description: '这是用户管理页面',
      queryParam: {},
      columns: [
        {
          title: '主题',
          align: 'center ',
          dataIndex: 'title'
        }, {
          title: '图片路径',
          align: 'center',
          dataIndex: 'picPath'
        },
        {
          title: '分类id',
          align: 'center',
          dataIndex: 'categoryId',
          scopedSlots: {customRender: 'avatarslot'}
        },
        {
          title: '联系方式',
          align: 'center',
          dataIndex: 'contact'
        },
        {
          title: '展示内容',
          align: 'center',
          dataIndex: 'presentContent'
        },
        {
          title: '全部内容',
          align: 'center',
          dataIndex: 'wholeContent'
        },
        {
          title: '邮箱',
          align: 'center',
          dataIndex: 'email'
        },
        {
          title: '浏览次数',
          align: 'center',
          dataIndex: 'viewCount'
        }, {
          title: '评论数量',
          align: 'center',
          dataIndex: 'commentCount'
        }, {
          title: '操作',
          dataIndex: 'action',
          scopedSlots: {customRender: 'action'},
          align: 'center',
          width: 170
        }
      ],
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
      }
    }
  },
  computed: {
  },
  created () {
    console.log(this.$route.params)
  },
  methods: {
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
    handleAdd () {
      this.$refs.JavaLanguageModal.add()
    },
    interactor () {
      // TODO 密码修改完成 不需要刷新页面，可以把datasource中的数据更新一下
    }
  }
}
</script>
<style scoped>
  /** Button按钮间距 */
  .ant-btn {
    margin-left: 3px
  }
  .ant-card-body {
    margin-bottom: 18px
  }

  .table-operator button {
    margin-bottom: 18px;
    margin-right: 5px
  }

  .ant-table-tbody .ant-table-row td {
    padding-top: 15px;
    padding-bottom: 15px
  }

  .anty-row-operator button {
    margin: 0 5px
  }

  .ant-btn-danger {
    background-color: #ffffff
  }

  .ant-modal-cust-warp {
    height: 100%
  }

  .ant-modal-cust-warp .ant-modal-body {
    height: calc(100% - 110px) !important;
    overflow-y: auto
  }

  .ant-modal-cust-warp .ant-modal-content {
    height: 90% !important;
    overflow-y: hidden
  }

  .anty-img-wrap {
    height: 25px;
    position: relative
  }

  .anty-img-wrap > img {
    max-height: 100%
  }
</style>
