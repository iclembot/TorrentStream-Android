
package com.github.se_bastiaan.torrentstreamer.sample;

import java.io.IOException;
import android.os.AsyncTask;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import android.widget.ProgressBar;



public class ezParser {
    public URL url;

    public ezParser(){
    }

    public void parseURL(String urlstr) {
        new ParseTask().execute(urlstr);
    }
}

