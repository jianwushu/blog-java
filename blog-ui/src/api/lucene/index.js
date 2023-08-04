import {request} from "@/utils/request";
export function search(query) {
  return request({
    url: '/blog/search',
    method: 'get',
    params: query
  })
}
