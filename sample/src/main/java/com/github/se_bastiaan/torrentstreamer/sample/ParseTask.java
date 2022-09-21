package com.github.se_bastiaan.torrentstreamer.sample;

import android.os.AsyncTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

public class ParseTask extends AsyncTask<String, Void, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //  ProgressBar progress= findViewById(R.id.progress);
        //  progress.setIndeterminate(true);
    }

    @Override
    protected String doInBackground(String... urls) {
        String firstUrl = "";
        try{
            String url= "https://eztv.re/shows/451106/house-of-the-dragon/" ;// TODO: new String(urls[0]);
            Document doc = Jsoup.connect(url).get();
            firstUrl= doc.select("a[href^=magnet]").first().attr("href");
            System.out.println("First URL: " + firstUrl);
                    //getElementsContainingText("magnet").get(0).html()+ url.toString() ); //TODO: needs to be finessed: The jSOUP query selector area
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return firstUrl;
    }

    @Override
    protected void onPostExecute(String magLink) {
        // (ProgressBar).findViewById(R.id.progress).setIndeterminate(false);
        System.out.println("PostExec: " + magLink);
    }
}