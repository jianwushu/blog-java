import {request} from "@/utils/request";

export function getOptionsList(query) {
  return request({
    url: '/blog/option/map',
    method: 'get',
    params: query
  })
}
