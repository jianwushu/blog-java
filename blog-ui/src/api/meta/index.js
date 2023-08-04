import {request} from "@/utils/request";

//分页查询文章列表数据
export function getMetaList(query) {
  return request({
    url: '/blog/meta/list',
    method: 'get',
    params: query
  })
}
