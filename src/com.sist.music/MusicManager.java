package com.sist.music;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MusicManager {
    public List<MusicVO> musicAllData() {
        List<MusicVO> list = new ArrayList<MusicVO>();

        try {
            String url = "http://www.genie.co.kr/chart/top200";
            Document doc = Jsoup.connect(url).get();

            Elements poster = doc.select("a.cover img");
            Elements title = doc.select("td.info a.title");
            Elements singer = doc.select("td.info a.artist");

            for (int i=0; i<50; i++) {
                String strPoster = poster.get(i).attr("src");
                String strTitle = title.get(i).text();
                String strSinger = singer.get(i).text();
//                System.out.println(strPoster + " ," + strTitle + " ," + strSinger);
                MusicVO vo = new MusicVO();
                vo.setRank(i+1);
                vo.setTitle(strTitle);
                vo.setSinger(strSinger);
                vo.setPoster(strPoster);
                String key = youtubeKey(strTitle);
                vo.setKey(key);
                list.add(vo);
            }

//            System.out.println(doc.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public String youtubeKey(String title) {
        String key = "";
        try {
            Document doc = Jsoup.connect("https://www.youtube.com/results?search_query="+title).get();
            Pattern p = Pattern.compile("/watch\\?v=[^가-힣]+");
            Matcher m = p.matcher(doc.toString());
            while (m.find()) {
                String data = m.group();
                data = data.substring(data.indexOf("=")+1, data.indexOf("\""));
                key = data;
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return key;
    }

    public static void main(String[] args) {
        MusicManager manager = new MusicManager();
        List<MusicVO> musicVOList = manager.musicAllData();
        JSONArray array = new JSONArray();
        for (MusicVO vo : musicVOList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("rank", vo.getRank());
            jsonObject.put("title", vo.getTitle());
            jsonObject.put("singer", vo.getSinger());
            jsonObject.put("poster", vo.getPoster());
            jsonObject.put("key", vo.getKey());
            array.add(jsonObject);
        }
        JSONObject root = new JSONObject();
        root.put("datas", array);

        try {
            FileWriter fw = new FileWriter("c:\\weekDev\\music.json");
            fw.write(root.toJSONString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
