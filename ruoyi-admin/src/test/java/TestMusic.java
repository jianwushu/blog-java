import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.common.utils.sign.Md5Utils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * @Author ilvyu.cn
 * @Date 2021/5/9 22:26
 * @Version 520.1314
 */
@SpringBootTest
public class TestMusic {

    @Test
    public void test1(){
        String url = "https://y.qq.com/n/ryqq/playlist/7975452023.html";
        Document doc = null;

        try {
            doc = Jsoup.connect(url).get();
            Elements lis = doc.select(".songlist__list li");
            List<String> titles = new ArrayList<>();
            List<String> mids = new ArrayList<>();
            List<String> authors = new ArrayList<>();
            List<String> albums = new ArrayList<>();
            List<String> albumids = new ArrayList<>();
            for(Element e: lis){
                titles.add(e.select(".songlist__songname_txt a").attr("title"));
                mids.add("\""+e.select(".songlist__songname_txt a").attr("href").substring(19)+"\"");
                authors.add(e.select(".playlist__author").attr("title"));
                albums.add(e.select(".songlist__album a").text());
                albumids.add(e.select(".songlist__album a").attr("href").substring(20));
            }
            List<String> vkeys = getVkey(mids);

//            List<Map> musics = new ArrayList<>();
//            for(int i=0;i<vkeys.size();i++){
//                Map<String,String> music = new HashMap<>();
//                music.put("title",titles.get(i));
//                music.put("author",authors.get(i));
//                music.put("album",albums.get(i));
//                music.put("url","https://dl.stream.qqmusic.qq.com/"+vkeys.get(i));
//                music.put("lyric",getLyric(mids.get(i)));
//                music.put("cover",getCover(albumids.get(i)));
//                musics.add(music);
//            }
//            System.out.println(musics);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String getCover(String id){
        return "https://y.qq.com/music/photo_new/T002R500x500M000"+id+".jpg?n=1";
    }
    private String getLyric(String mid){
        mid = mid.substring(1,mid.length()-1);
        String url = "https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric_new.fcg";
        String data = "_="+System.currentTimeMillis()+"&cv=4747474&ct=24&format=json&inCharset=utf-8&outCharset=utf-8&notice=0&platform=yqq.json&needNewCode=1&uin=0&g_tk_new_20200303=1382773418&g_tk=1382773418&loginUin=0&songmid="+mid;
        Map<String,String> header = new HashMap<>();
        header.put("Referer","https://y.qq.com");
        String s = sendGet(url, data, header);
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

    public String getGuid(){
        double guid = Math.random()*2147483647 * System.currentTimeMillis() % 100000100000l;
        return (int)guid+"";
    }

    public List<String> getVkey(List<String> mids){
        String guid = getGuid();
        int[] songtype = new int[mids.size()];
        String data =
                "{\"comm\":{\"cv\":4747474,\"ct\":24,\"format\":\"json\",\"inCharset\"" +
                ":\"utf-8\",\"outCharset\":\"utf-8\",\"notice\":0,\"platform\":\"yqq.json\"," +
                "\"needNewCode\":1,\"uin\":0,\"g_tk_new_20200303\":40310571,\"g_tk\":40310571}," +
                "\"req_1\":{\"module\":\"vkey.GetVkeyServer\",\"method\":\"CgiGetVkey\"," +
                "\"param\":{\"guid\":\"$guid$\",\"songmid\":$mid$,\"songtype\":$songtype$," +
                "\"uin\":\"0\",\"loginflag\":1,\"platform\":\"20\",\"ctx\":1}}}";
        data = data.replace("$guid$", guid).replace("$mid$", mids.toString()).replace("$songtype$",Arrays.toString(songtype));
        System.out.println(data);
        String sign = getSign(data);
        String url = "https://u.y.qq.com/cgi-bin/musics.fcg?_="+System.currentTimeMillis()+"&sign="+sign;
        String s = HttpUtils.sendPost(url, data);
        JSONObject jsonObject = JSONObject.parseObject(s);
        s = jsonObject.getJSONObject("req_1").getJSONObject("data").getString("midurlinfo");
        System.out.println(s);
        JSONArray objects = JSONObject.parseArray(s);
        List<String> vkeys = new ArrayList<>();
//        for(Object s1: objects){
//            JSONObject jsonObject1 = JSONObject.parseObject(String.valueOf(s1));
//            String vkey = jsonObject1.getString("purl");
//            vkeys.add(vkey);
//        }
        return vkeys;
    }


    public String getSign(String data){
        final String encNonce="CJBPACrRuNy7";
        final String signPrxfix="zza";
        return signPrxfix+uuidGenerate()+ Md5Utils.hash(encNonce+data);
    }

    public String getUrl(String mid){
        String url = "https://c.y.qq.com/base/fcgi-bin/fcg_music_express_mobile3.fcg";
        String data = "callback=musicJsonCallback&loginUin=2574652740&format=jsonp&platform=yqq&needNewCode=0&cid=205361747&guid="+getGuid()+"&uin=2574652740&songmid="+mid+"&filename=C400"+mid+".m4a";
        String s = HttpUtils.sendGet(url, data);
        System.out.println(s);
        return "123";
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

    @Test
    public void test2(){
        getUrl("003iHWbo0vq21B");
    }

    public String sendGet(String url, String param, Map<String,String> header){
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try{
            String urlString = param == null ? url : url+"?"+param;
            URL realUrl = new URL(urlString);
            URLConnection urlConnection = realUrl.openConnection();
            for (String s : header.keySet()) {
                urlConnection.setRequestProperty(s,header.get(s));
            }
//            urlConnection.setRequestProperty("Referer","https://y.qq.com");
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
