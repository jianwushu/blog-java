import request from '@/utils/request'

// 查询分类列表
export function listMeta(query) {
  return request({
    url: '/blog/meta/list',
    method: 'get',
    params: query
  })
}
export function listMetaOfCategory(query) {
  query.type = "category"
  return request({
    url: '/blog/meta/list',
    method: 'get',
    params: query
  })
}
// 查询标签列表
export function listMetaOfTagById(query) {
  query.type = "tag"
  return request({
    url: '/blog/meta/list',
    method: 'get',
    params: query
  })
}

// 查询分类和标签详细
export function getMeta(id) {
  return request({
    url: '/blog/meta/' + id,
    method: 'get'
  })
}

// 新增分类和标签
export function addMeta(data) {
  return request({
    url: '/blog/meta',
    method: 'post',
    data: data
  })
}

// 修改分类和标签
export function updMeta(data) {
  return request({
    url: '/blog/meta',
    method: 'put',
    data: data
  })
}

// 删除分类和标签
export function delMeta(id) {
  return request({
    url: '/blog/meta/' + id,
    method: 'delete'
  })
}

// 导出分类和标签
export function exportMeta(query) {
  return request({
    url: '/blog/meta/export',
    method: 'get',
    params: query
  })
}
