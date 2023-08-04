import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

import Layout from "@/layout/"
import Index from "views/blog";
import Comment from "views/blog/comment";
import Album from "views/blog/album";
import Archive from "views/blog/archive"
import Category from "views/blog/category";
import Tag from "views/blog/tag";
import Subscribe from "views/blog/subscribe"
import About from "views/blog/about";
import Article from "views/blog/article";
import Search from "views/blog/search"

const routes = [

  {
    path: '',
    component: Layout,
    redirect: '',
    children: [
      {
        path: '',
        component: Index,
        meta:{
          title: '首页'
        }
      },{
        path: 'comment',
        component: Comment,
        meta:{
          title: '留言板'
        }
      },{
        path: 'album',
        component: Album,
        meta: {
          title: '相册'
        },
      },{
        path: 'archive',
        component: Archive,
        meta:{
          title: '归档'
        }
      },{
        path: 'category/:id',
        component: Category,
        meta:{
          title: '分类'
        }
      },{
        path: 'tag/:id',
        component: Tag,
        meta:{
          title: '标签'
        }
      },{
        path: 'archive',
        component: Archive,
        meta:{
          title: '归档'
        }
      },
      {
        path: 'subscribe',
        component: Subscribe,
        meta:{
          title: 'Rss订阅'
        }
      },{
        path: 'about',
        component: About,
        meta:{
          title: '关于我'
        }
      },{
        path: 'article/:id',
        component: Article
      },{
        path: 'search',
        component: Search
      }
    ]
  },
]
export default new Router({
  routes,
  mode: 'history',
  base: process.env.BASE_URL,
  //跳转页面置顶
  scrollBehavior(to,from,saveTop){
    if(saveTop){
      return saveTop;
    }else{
      return {x:0,y:0}
    }
  },
})
