package com.sist.news;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainClass {
    public static void main(String[] args) {
        try {
            String searchKey = URLEncoder.encode("영화", "UTF-8");
            URL url = new URL("http://newssearch.naver.com/search.naver?where=rss&query=" + searchKey);
            JAXBContext jc = JAXBContext.newInstance(Rss.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            Rss rss = (Rss) unmarshaller.unmarshal(url);
            int i = 1;
            for (Item item : rss.getChannel().getItem()) {
                String author = item.getAuthor();
                String description = item.getDescription();
                String link = item.getLink();
                String title = item.getTitle();
                System.out.println(i + ", " + author + ", " + description + ", " + link + ", " + title);
                i++;
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
