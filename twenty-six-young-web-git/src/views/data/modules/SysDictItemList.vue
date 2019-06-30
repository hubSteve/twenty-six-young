<template>
  <a-drawer
    title="字典值"
    :width="800"
    placement="right"
    :closable="true"
    @close="close"
    :visible="visible"
  >
    <a-card :bordered='false'>
      <!-- 查询区域 -->
      <div class='table-page-search-wrapper'>
        <a-form layout='inline'>
          <a-row :gutter='24'>

            <a-col :md='8' :sm='8'>
              <a-form-item label='itemText' :label-col="labelCol" :wrapper-col="wrapperCol">
                <a-input placeholder='请输入itemText' v-model='queryParam.itemText'></a-input>
              </a-form-item>
            </a-col>
            <a-col :md='8' :sm='8'>
              <a-form-item label='itemValue' :label-col="labelCol" :wrapper-col="wrapperCol">
                <a-input placeholder='请输入itemValue' v-model='queryParam.itemValue'></a-input>
               </a-form-item>
            </a-col>
            <a-col :md='8' :sm='8' >
            <span style='float: left;overflow: hidden;' class='table-page-search-submitButtons'>
              <a-button type='primary' @click='searchQuery' icon='search'>Search</a-button>
              <a-button type='primary' @click='searchReset' icon='reload' style='margin-left: 8px'>Reset</a-button>
            </span>
            </a-col>

          </a-row>
        </a-form>
      </div>

      <!-- 操作按钮区域 -->
      <div class='table-operator' style="margin-top: 20px">
        <a-button @click='handleAdd' type='primary' icon='plus'>Add</a-button>
        <a-dropdown v-if='selectedRowKeys.length > 0'>
          <a-menu slot='overlay'>
            <a-menu-item key='1' @click='batchDel'><a-icon type='delete'/>Delete</a-menu-item>
          </a-menu>
          <a-button style='margin-left: 8px'> Bulk Operation <a-icon type='down' /></a-button>
        </a-dropdown>
      </div>

      <!-- table区域-begin -->
      <div>
        <div class='ant-alert ant-alert-info' style='margin-bottom: 16px;'>
          <i class='anticon anticon-info-circle ant-alert-icon'></i> 已选择 <a style='font-weight: 600'>{{ selectedRowKeys.length }}</a>项
          <a style='margin-left: 24px' @click='onClearSelected'>清空</a>
        </div>

        <a-table
          ref='table'
          size='middle'
          bordered
          rowKey='id'
          :columns='columns'
          :dataSource='dataSource'
          :pagination='false'
          :loading='loading'
          :rowSelection='{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}'
          @change='handleTableChange'>

        <span slot='action' slot-scope='text, record'>
          <a @click='handleEdit(record)'>编辑</a>

          <a-divider type='vertical' />
          <a-dropdown>
            <a class='ant-dropdown-link'>更多 <a-icon type='down' /></a>
            <a-menu slot='overlay'>
              <a-menu-item>
                <a-popconfirm title='确定删除吗?' @confirm='() => handleDelete(record.id)'>
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

        </a-table>
      </div>
      <!-- table区域-end -->

      <!-- 表单区域 -->
      <sys-dict-item-modal ref='SysDictItemModal' @ok='modalFormOk'></sys-dict-item-modal>
    </a-card>
  </a-drawer>
</template>

<script>
import SysDictItemModal from './SysDictItemModal'
import {filterObj} from '@/utils/objectfilter'
export default {
  name: 'SysDictItemList',
  components: {
    SysDictItemModal
  },
  data () {
    return {
      description: '字典值表管理页面',
      // 表头
      columns: [
        {
          title: 'itemText',
          align: 'center',
          dataIndex: 'itemText'
        }, {
          title: 'itemValue',
          align: 'center',
          dataIndex: 'itemValue'
        }, {
          title: 'sortOrder',
          align: 'center',
          dataIndex: 'sortOrder'
        }, {
          title: 'description',
          align: 'center',
          dataIndex: 'description'
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' }
        }
      ],
      /* 查询条件 */
      queryParam: {},
      /* 数据源 */
      dataSource: [],
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
      superQueryParams: '',
      labelCol: {
        xs: { span: 9 },
        sm: { span: 9 }
      },
      wrapperCol: {
        xs: { span: 15 },
        sm: { span: 15 }
      },
      /* 分页参数 */
      ipagination: {
        current: 1,
        pageSize: 10,
        pageSizeOptions: ['10', '20', '30'],
        showTotal: (total, range) => {
          return range[0] + '-' + range[1] + ' total: ' + total + ' pieces'
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
      url: {
        list: this.HOST + '/main/sysDictItem/list',
        delete: this.HOST + '/main/sysDictItem/delete',
        deleteBatch: this.HOST + '/main/sysDictItem/deleteBatch',
        exportXlsUrl: this.HOST + 'main/sysDictItem/exportXls',
        importExcelUrl: this.HOST + 'main/sysDictItem/importExcel'
      },
      visible: false
    }
  },
  computed: {
  },
  methods: {
    showDrawer (value) {
      this.visible = true
      this.queryParam.dictId = value.id
      this.loadData(value)
    },
    loadData (arg) {
      if (!this.url.list) {
        this.$message.error('请设置url.list属性!')
        return
      }
      // var params = this.getQueryParams()// 查询条件
      // console.log(params)
      this.$axios.get(this.url.list, {params: {dictId: this.queryParam.dictId}}).then((res) => {
        if (res.success) {
          this.dataSource = res.result.records
          this.ipagination.total = res.result.total
        }
      })
    },
    close () {
      this.$emit('close')
      this.visible = false
    },
    getQueryParams () {
      // 获取查询条件
      var param = Object.assign(this.queryParam, this.isorter, this.filters)
      param.field = this.getQueryField()
      param.dictCode = this.queryParam.dictId
      param.pageNo = this.ipagination.current
      param.pageSize = this.ipagination.pageSize
      return filterObj(param)
    },
    getQueryField () {
      // TODO 字段权限控制
      var str = 'id'
      this.columns.forEach(function (value) {
        str += ',' + value.dataIndex
      })
      return str
    },
    initDictConfig () {
      console.log('--这是一个假的方法!')
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
      this.$refs.modalForm.edit(record)
      this.$refs.modalForm.title = '编辑'
    },
    handleAdd () {
      this.$refs.SysDictItemModal.add(this.queryParam.dictId)
      this.$refs.SysDictItemModal.title = '新增'
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
