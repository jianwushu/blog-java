import request from '@/utils/request'

// 查询网站设置列表
export function listOption(query) {
  return request({
    url: '/blog/option/list',
    method: 'get',
    params: query
  })
}

// 查询网站设置详细
export function getOption(title) {
  return request({
    url: '/blog/option/' + title,
    method: 'get'
  })
}

// 新增网站设置
export function addOption(data) {
  return request({
    url: '/blog/option',
    method: 'post',
    data: data
  })
}

// 修改网站设置
export function updOption(data) {
  return request({
    url: '/blog/option',
    method: 'put',
    data: data
  })
}

// 删除网站设置
export function delOption(title) {
  return request({
    url: '/blog/option/' + title,
    method: 'delete'
  })
}

// 导出网站设置
export function exportOption(query) {
  return request({
    url: '/blog/option/export',
    method: 'get',
    params: query
  })
}
