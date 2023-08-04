import {request} from "@/utils/request";
let pre = '/blog/comment'
export function getList(query) {
  return request({
    url: pre+'/tree',
    method: 'get',
    params: query
  })
}
export function getCommentCount() {
  return request({
    url: pre + '/count',
    method: 'get',
  })
}
export function addComment(data) {
  return request({
    url: pre + '/add',
    method: 'post',
    data: data
  })
}
