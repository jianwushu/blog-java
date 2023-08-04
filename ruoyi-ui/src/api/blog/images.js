import request from '@/utils/request'

// 查询图片列表
export function listImages(query) {
  return request({
    url: '/system/images/list',
    method: 'get',
    params: query
  })
}

// 查询图片详细
export function getImages(id) {
  return request({
    url: '/system/images/' + id,

    method: 'get'
  })
}

// 新增图片
export function addImages(data) {
  return request({
    url: '/system/images',
    method: 'post',
    data: data
  })
}

// 修改图片
export function updateImages(data) {
  return request({
    url: '/system/images',
    method: 'put',
    data: data
  })
}

// 删除图片
export function delImages(id) {
  return request({
    url: '/system/images/' + id,
    method: 'delete'
  })
}

// 导出图片
export function exportImages(query) {
  return request({
    url: '/system/images/export',
    method: 'get',
    params: query
  })
}
