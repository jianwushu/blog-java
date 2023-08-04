import request from '@/utils/request'

// 查询文章列表
export function listBlog(query) {
  return request({
    url: '/blog/blog/list',
    method: 'get',
    params: query
  })
}

// 查询文章详细
export function getBlog(id) {
  return request({
    url: '/blog/blog/' + id,
    method: 'get'
  })
}

// 新增文章
export function addBlog(data) {
  return request({
    url: '/blog/blog',
    method: 'post',
    data: data
  })
}

// 修改文章
export function updBlog(data) {
  return request({
    url: '/blog/blog',
    method: 'put',
    data: data
  })
}

// 删除文章
export function delBlog(id) {
  return request({
    url: '/blog/blog/' + id,
    method: 'delete'
  })
}

// 导出文章
export function exportBlog(query) {
  return request({
    url: '/blog/blog/export',
    method: 'get',
    params: query
  })
}
