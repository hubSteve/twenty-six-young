<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="8">
            <a-form-item label="dictCode">
              <a-input placeholder="Please type in the dictCode" v-model="queryParam.dictCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="dictName">
              <a-input placeholder="Please type in the dictName" v-model="queryParam.dictName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8" >
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 4px">重置</a-button>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator" style="text-align: left">
      <a-button @click="handleAdd" type="primary" icon="plus" style="margin: 20px 0 20px 0">新增</a-button>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical"/>
          <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
            <a>删除</a>
          </a-popconfirm>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <sysDict-modal ref="modalForm" @ok="modalFormOk"></sysDict-modal>
    <sys-dict-item-list ref="dictItemList"></sys-dict-item-list>
  </a-card>
</template>

<script>
import { filterObj } from '@/utils/objectfilter'
import SysDictModal from './modules/SysDictModal'
import SysDictItemList from './modules/SysDictItemList'
export default {
  name: 'SysDictList',
  components: {
    SysDictModal,
    SysDictItemList
  },
  data () {
    return {
      description: '字典表管理页面',
      // 表头
      columns: [
        {
          title: '#',
          dataIndex: '',
          key: 'rowIndex',
          width: 60,
          align: 'center',
          customRender: function (t, r, index) {
            return parseInt(index) + 1
          }
        }, {
          title: 'dictCode',
          align: 'center',
          dataIndex: 'dictCode'
        }, {
          title: 'dictName',
          align: 'center',
          dataIndex: 'dictName'
        }, {
          title: 'description',
          align: 'center',
          dataIndex: 'description'
        }, {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: this.HOST + '/main/sysDict/list',
        delete: this.HOST + '/main/sysDict/delete',
        deleteBatch: this.HOST + '/main/sysDict/deleteBatch'
      },
      /* 查询条件 */
      queryParam: {},
      /* 数据源 */
      dataSource: [],
      /* 分页参数 */
      ipagination: {
        current: 1,
        pageSize: 10,
        pageSizeOptions: ['10', '20', '30'],
        showTotal: (total, range) => {
          return range[0] + '-' + range[1] + ' 共' + total + '条'
        },
        showQuickJumper: true,
        showSizeChanger: true,
        total: 0
      },
      /* 排序参数 */
      isorter: {
        column: 'createTime',
        order: 'desc'
      },
      /* 筛选参数 */
      filters: {},
      /* table加载状态 */
      loading: false,
      /* table选中keys */
      selectedRowKeys: [],
      /* table选中records */
      selectionRows: [],
      /* 查询折叠 */
      toggleSearchStatus: false,
      /* 高级查询条件生效状态 */
      superQueryFlag: false,
      /* 高级查询条件 */
      superQueryParams: ''
    }
  },
  computed: {
  },
  created () {
    this.loadData()
    // 初始化字典配置 在自己页面定义
    this.initDictConfig()
  },
  methods: {
    loadData (arg) {
      if (!this.url.list) {
        this.$message.error('请设置url.list属性!')
        return
      }
      // 加载数据 若传入参数1则加载第一页的内容
      if (arg === 1) {
        this.ipagination.current = 1
      }
      var params = this.getQueryParams()
      this.$axios.get(this.url.list, params).then((res) => {
        if (res.success) {
          this.dataSource = res.result.records
          this.ipagination.total = res.result.total
          console.log(this.dataSource)
        } else {
          console.log('加载失败')
        }
      })
    },
    initDictConfig () {
    },
    handleSuperQuery (arg) {
      // 高级查询方法
      if (!arg) {
        this.superQueryParams = ''
        this.superQueryFlag = false
      } else {
        this.superQueryFlag = true
        this.superQueryParams = JSON.stringify(arg)
      }
      this.loadData()
    },
    getQueryParams () {
      // 获取查询条件
      let sqp = {}
      if (this.superQueryParams) {
        sqp['superQueryParams'] = encodeURI(this.superQueryParams)
      }
      var param = Object.assign(sqp, this.queryParam, this.isorter, this.filters)
      param.field = this.getQueryField()
      param.pageNo = this.ipagination.current
      param.pageSize = this.ipagination.pageSize
      return filterObj(param)
    },
    getQueryField () {
      // TODO 字段权限控制
      var str = 'id,'
      this.columns.forEach(function (value) {
        str += ',' + value.dataIndex
      })
      return str
    },

    onSelectChange (selectedRowKeys, selectionRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectionRows = selectionRows
    },
    onClearSelected () {
      this.selectedRowKeys = []
      this.selectionRows = []
    },
    searchQuery () {
      this.loadData(1)
    },
    superQuery () {
      this.$refs.superQueryModal.show()
    },
    searchReset () {
      this.queryParam = {}
      this.loadData(1)
    },
    batchDel: function () {
      if (!this.url.deleteBatch) {
        this.$message.error('请设置url.deleteBatch属性!')
        return
      }
      if (this.selectedRowKeys.length <= 0) {
        this.$message.warning('请选择一条记录！')
        return;
      } else {
        var ids = ''
        for (var a = 0; a < this.selectedRowKeys.length; a++) {
          ids += this.selectedRowKeys[a] + ','
        }
        var that = this
        this.$confirm({
          title: '确认删除',
          content: '是否删除选中数据?',
          onOk: function () {
            that.$axios.delete(that.url.deleteBatch, {ids: ids}).then((res) => {
              if (res.success) {
                that.$message.success(res.message)
                that.loadData()
                that.onClearSelected()
              } else {
                that.$message.warning(res.message)
              }
            })
          }
        })
      }
    },
    handleDelete: function (id) {
      if (!this.url.delete) {
        this.$message.error('请设置url.delete属性!')
        return
      }
      var that = this
      that.$axios.delete(that.url.delete, {id: id}).then((res) => {
        if (res.success) {
          that.$message.success(res.message)
          that.loadData()
        } else {
          that.$message.warning(res.message)
        }
      })
    },
    handleEdit: function (record) {
      this.$refs.dictItemList.showDrawer(record)
    },
    handleAdd: function () {
      this.$refs.modalForm.add()
      this.$refs.modalForm.title = '新增'
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
    handleToggleSearch () {
      this.toggleSearchStatus = !this.toggleSearchStatus
    },
    modalFormOk () {
      // 新增/修改 成功时，重载列表
      this.loadData()
    },
    handleDetail: function (record) {
      this.$refs.modalForm.edit(record)
      this.$refs.modalForm.title = '详情'
      this.$refs.modalForm.disableSubmit = true
    }
  }
}
</script>
<style scoped>
</style>
