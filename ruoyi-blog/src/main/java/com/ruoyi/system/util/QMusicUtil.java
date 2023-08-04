package com.ruoyi.system.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.common.utils.sign.Md5Utils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * @Author ilvyu.cn
 * @Date 2021/5/11 12:30
 * @Version 520.1314
 */
public class QMusicUtil {
    private final static String PLAY_LIST_URL = "https://c.y.qq.com/qzone/fcg-bin/fcg_ucc_getcdinfo_byids_cp.fcg";
    private final static String SONG_LYSIC_URL = "https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric_new.fcg";

    public static List<Map> parseMusic(String id){
        List<Map> musics = new ArrayList<>();
        String data = "platform=yqq&hostuin=0&sin=0&ein=29&sortid=5&needNewCode=0&disstid="+id+"&format=jsonp&type=1&g_tk=380961055&loginuin=2574652740&notice=0&inCharset=utf8&outCharset=utf-8&onlysong=0&utf8=1&json=1&type=1";
        Map<String,String> header = new HashMap<>();
        header.put("Referer","https://c.y.qq.com/");
        String s = sendGet(PLAY_LIST_URL, data, header);
        JSONObject jsonObject = JSONObject.parseObject(s.substring(13, s.length() - 1));
        s = jsonObject.getString("cdlist");
        JSONArray songlist = JSONObject.parseObject(s.substring(1, s.length() - 1)).getJSONArray("songlist");

        List<String> titles = new ArrayList<>();
        List<String> mids = new ArrayList<>();
        List<String> authors = new ArrayList<>();
        List<String> albums = new ArrayList<>();
        List<String> albummids = new ArrayList<>();
        for (Object o : songlist) {
            JSONObject json = (JSONObject)o;
            titles.add(json.getString("songname"));
            authors.add(((JSONObject)json.getJSONArray("singer").get(0)).getString("name"));
            mids.add("\""+json.getString("songmid")+"\"");
            albums.add(json.getString("albumname"));
            albummids.add(json.getString("albummid"));
        }

        List<String> vkeys = getVkey(mids);

        for (int i = 0; i < vkeys.size(); i++) {
            if (vkeys.get(i).equals("")) {
                continue;
            }
            Map<String, String> music = new HashMap<>();
            music.put("title", titles.get(i));
            music.put("author", authors.get(i));
            music.put("album", albums.get(i));
            music.put("url", "https://dl.stream.qqmusic.qq.com/" + vkeys.get(i));
            music.put("lyric", getLyric(mids.get(i)));
            music.put("cover", getCover(albummids.get(i)));
            musics.add(music);
        }
        return musics;
    }
    private static String getCover(String id){
        return "https://y.qq.com/music/photo_new/T002R500x500M000"+id+".jpg?n=1";
    }
    private static String getLyric(String mid){
        mid = mid.substring(1,mid.length()-1);
        String data = "_="+System.currentTimeMillis()+"&cv=4747474&ct=24&format=json&inCharset=utf-8&outCharset=utf-8&notice=0&platform=yqq.json&needNewCode=1&uin=0&g_tk_new_20200303=1382773418&g_tk=1382773418&loginUin=0&songmid="+mid;
        Map<String,String> header = new HashMap<>();
        header.put("Referer","https://y.qq.com");
        String s = sendGet(SONG_LYSIC_URL, data, header);
        JSONObject jsonObject = JSONObject.parseObject(s);
        String lyric = jsonObject.getString("lyric");
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decode = decoder.decode(lyric);
        try {
            lyric = new String(decode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return lyric;
    }
    private static String getGuid(){
        double guid = Math.random()*2147483647 * System.currentTimeMillis() % 100000100000L;
        return (int)guid+"";
    }
    private static List<String> getVkey(List<String> mids){
        String guid = getGuid();
        int[] songtype = new int[mids.size()];
        String data =
                "{\"comm\":{\"cv\":4747474,\"ct\":24,\"format\":\"json\",\"inCharset\"" +
                        ":\"utf-8\",\"outCharset\":\"utf-8\",\"notice\":0,\"platform\":\"yqq.json\"," +
                        "\"needNewCode\":1,\"uin\":0,\"g_tk_new_20200303\":40310571,\"g_tk\":40310571}," +
                        "\"req_1\":{\"module\":\"vkey.GetVkeyServer\",\"method\":\"CgiGetVkey\"," +
                        "\"param\":{\"guid\":\"$guid$\",\"songmid\":$mid$,\"songtype\":$songtype$," +
                        "\"uin\":\"0\",\"loginflag\":1,\"platform\":\"20\",\"ctx\":1}}}";
        data = data.replace("$guid$", guid).replace("$mid$", mids.toString()).replace("$songtype$", Arrays.toString(songtype));
        System.out.println(data);
        String sign = getSign(data);
        String url = "https://u.y.qq.com/cgi-bin/musics.fcg?_="+System.currentTimeMillis()+"&sign="+sign;
        String s = HttpUtils.sendPost(url, data);
        JSONObject jsonObject = JSONObject.parseObject(s);
        s = jsonObject.getJSONObject("req_1").getJSONObject("data").getString("midurlinfo");
        JSONArray objects = JSONObject.parseArray(s);
        List<String> vkeys = new ArrayList<>();
        for(Object s1: objects){
            JSONObject jsonObject1 = JSONObject.parseObject(String.valueOf(s1));
            String vkey = jsonObject1.getString("purl");
            vkeys.add(vkey);
        }
        return vkeys;
    }
    private static String getSign(String data){
        final String encNonce="CJBPACrRuNy7";
        final String signPrxfix="zza";
        return signPrxfix+uuidGenerate()+ Md5Utils.hash(encNonce+data);
    }
    private static String uuidGenerate(){
        final char[] dir="0234567890abcdefghijklmnopqrstuvwxyz".toCharArray();
        int minLen=10;
        int maxLen=16;
        Random ran=new Random(System.currentTimeMillis());
        int ranLen=ran.nextInt(maxLen-minLen)+minLen;
        StringBuilder sb=new StringBuilder(ranLen);
        for (int i=0;i<ranLen;i++){
            sb.append(dir[ran.nextInt(dir.length)]);
        }
        return sb.toString();
    }
    private static String sendGet(String url, String param, Map<String,String> header){
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try{
            String urlString = url+"?"+param;
            URL realUrl = new URL(urlString);
            URLConnection urlConnection = realUrl.openConnection();
            for (String s : header.keySet()) {
                urlConnection.setRequestProperty(s,header.get(s));
            }
            urlConnection.connect();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while((line=in.readLine())!=null){
                result.append(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
