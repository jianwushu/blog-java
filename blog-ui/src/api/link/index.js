import {request} from "@/utils/request";

export function getLinkList() {
  return request({
    url: '/blog/link/list',
    method: 'get',
  })
}
