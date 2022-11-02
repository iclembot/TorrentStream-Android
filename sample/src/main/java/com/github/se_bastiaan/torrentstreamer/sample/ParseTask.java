package com.github.se_bastiaan.torrentstreamer.sample;

import android.os.AsyncTask;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

public class ParseTask extends AsyncTask<String, Void, Object> {


    public AsyncResponse delegate = null; // Call back interface
    public Document doc = null;

    public ParseTask(AsyncResponse asyncResponse) {
        delegate = asyncResponse; // Assigning call back interface through constructor
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //  ProgressBar progress= findViewById(R.id.progress);
        //  progress.setIndeterminate(true);
    }

    @Override
    protected Object doInBackground(String... urls) {
        String firstUrl = "";
        try{
            String url= urls[0]; // "https://eztv.re/shows/451106/house-of-the-dragon/" ;// TODO: new String(urls[0]);
            doc = Jsoup.connect(url).get();
            firstUrl= doc.select("a[href^=magnet]").first().attr("href");
            System.out.println("[jcerr: doInBackground]" + " First URL: " + firstUrl);
                    //getElementsContainingText("magnet").get(0).html()+ url.toString() ); //TODO: needs to be finessed: The jSOUP query selector area
        } catch (IOException  e) {
            System.out.println("jcerr: doInBackground]" + e.getMessage());
        }
        return doc;
    }

    @Override
    protected void onPostExecute(Object docRef) {
      //  super.onPostExecute();
        // (ProgressBar).findViewById(R.id.progress).setIndeterminate(false);
        System.out.println("jcerr: onPostExecute] PostExec");
        delegate.processFinish(docRef);
    }
}