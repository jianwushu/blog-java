import request from '@/utils/request'

// 查询友链列表
export function listLink(query) {
  return request({
    url: '/blog/link/list',
    method: 'get',
    params: query
  })
}

// 查询友链详细
export function getLink(linkId) {
  return request({
    url: '/blog/link/' + linkId,
    method: 'get'
  })
}

// 新增友链
export function addLink(data) {
  return request({
    url: '/blog/link',
    method: 'post',
    data: data
  })
}

// 修改友链
export function updLink(data) {
  return request({
    url: '/blog/link',
    method: 'put',
    data: data
  })
}

// 删除友链
export function delLink(linkId) {
  return request({
    url: '/blog/link/' + linkId,
    method: 'delete'
  })
}

// 导出友链
export function exportLink(query) {
  return request({
    url: '/blog/link/export',
    method: 'get',
    params: query
  })
}
