import {request} from "@/utils/request";

let pre = '/blog/blog'
//分页查询文章列表数据
export function getList(query) {
  return request({
    url: pre + '/list',
    method: 'get',
    params: query
  })
}
export function getBlogCount() {
  return request({
    url: pre + '/count',
    method: 'get',
  })
}
export function getArchive() {
  return request({
    url: pre + '/archive',
    method: 'get',
  })
}
export function updView(query) {
  return request({
    url: pre + '/view',
    method: 'get',
    params: query
  })
}

