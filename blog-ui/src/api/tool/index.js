import {request} from "@/utils/request";
import axios from 'axios'
export function parseRssUri(query) {
  return request({
    url: '/blog/tool/rss',
    method: 'get',
    params: query
  })
}
export function parseQMusic(query) {
  return request({
    url: '/blog/tool/qmusic',
    method: 'get',
    params: query
  })
}
export function parseMusicUri(query) {
  return axios.get("https://api88.net/api/qqmusic/?key=151626a913b9f2ecba6be2db1db5c28f&id="+query+"&type=songlist")
}
