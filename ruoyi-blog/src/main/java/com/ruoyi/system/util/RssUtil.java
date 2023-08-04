package com.ruoyi.system.util;


import com.ruoyi.system.entity.RssBean;
import com.ruoyi.system.entity.vo.RssBeanVo;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author ilvyu.cn
 * @Date 2021/3/24 20:04
 * @Version 520.1314
 */

public class RssUtil {

    public static String RSS_DOM_ROOT = "//channel";

    public static String RSS_DOM_ROOT_TITLE = "//channel/title";

    public static String RSS_DOM_ROOT_LINK = "//channel/link";

    public static String RSS_DOM_ROOT_DESCRIPTION = "//channel/description";

    public static String RSS_DOM_ITEM_ROOT = "//channel/item";

    public static String RSS_DOM_ITEM_ROOT_TITLE = "title";

    public static String RSS_DOM_ITEM_ROOT_LINK = "link";

    public static String RSS_DOM_ITEM_ROOT_PUBDATE = "pubDate";

    public static String RSS_DOM_ITEM_ROOT_DESCRIPTION = "description";

    private static Document document = null;

    /**
     *  解析Rss源获取文档
     * @param url
     * @throws DocumentException
     */
    private static void parseUrl(URL url) throws DocumentException {
        SAXReader reader = new SAXReader();
        document = reader.read(url);
    }

    /**
     * 获取文档指定标签集合
     * @param tag
     * @return
     * @throws DocumentException
     */
    private static List getXmlInfo(String tag) throws DocumentException {
        List info = new ArrayList();
        info = document.selectNodes(tag);
        return info;
    }

    /**
     *  获取Rss源文档信息
     * @param uri
     * @return
     */
    public static RssBean getRssBean(String uri) {

        RssBeanVo rss = new RssBeanVo();
        URL url;
        try {
            url = new URL(uri);
            parseUrl(url);
            /**
             * 解析Rss地址信息 网站信息
             */
            List listRoot = getXmlInfo(RSS_DOM_ROOT);
            for (Iterator iter = listRoot.iterator(); iter.hasNext(); ) {
                Element element = (Element) iter.next();
                Node title = element.selectSingleNode(RSS_DOM_ROOT_TITLE);
                Node link = element.selectSingleNode(RSS_DOM_ROOT_LINK);
                Node description = element
                        .selectSingleNode(RSS_DOM_ROOT_DESCRIPTION);
                rss.setTitle(title.getText());
                rss.setDescription(description.getText());
                rss.setLink(link.getText());
            }

            /**
             * 解析xml中item标签下数据  主要数据
             */
            List list = getXmlInfo(RSS_DOM_ITEM_ROOT);
            for (Iterator iter = list.iterator(); iter.hasNext(); ) {
                Element element = (Element) iter.next();
                Node title = element
                        .selectSingleNode(RSS_DOM_ITEM_ROOT_TITLE);
                Node link = element
                        .selectSingleNode(RSS_DOM_ITEM_ROOT_LINK);
                Node time = element
                        .selectSingleNode(RSS_DOM_ITEM_ROOT_PUBDATE);
                Node description = element
                        .selectSingleNode(RSS_DOM_ITEM_ROOT_DESCRIPTION);
                RssBean rs = new RssBean();
                rs.setTitle(title.getText());
                if(time!=null){
                    rs.setPubDate(time.getText());
                }
                rs.setDescription(description.getText());
                rs.setLink(link.getText());
                rss.getRssBeans().add(rs);
            }
            System.out.println("网站名："+rss.getTitle()+",网址："+rss.getLink()+" rss数据解析成功");
            return rss;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
