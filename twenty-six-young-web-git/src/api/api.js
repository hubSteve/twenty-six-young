import { getAction } from '@/api/manage'

const getList = (params) => getAction('/26young/language/java/queryOne', params)

export {
  getList
}
