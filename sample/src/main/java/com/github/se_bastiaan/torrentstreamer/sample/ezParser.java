package com.github.se_bastiaan.torrentstreamer.sample;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ezParser extends AsyncTask<Void, Void, String> {

    public void ezParser(){

    }

    public void parseTest() {
    Document document = Jsoup.parse("<html><head><title>Web Scraping</title></head></html>");
    System.out.println(document.title());
    }

    public void parseURL(String urlstr) {
        try{
            URL url = new URL(urlstr);  // "https://eztv.re/shows/2090/the-handmaids-tale/");
            //   Document doc = Jsoup.connect("https://eztv.re/shows/451106/house-of-the-dragon/").get();
            Document doc = Jsoup.connect(urlstr).get();
//            String title = doc.title();
            System.out.println(doc.getElementsContainingText("magnet").get(0).html()+ url );
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
