
package com.github.se_bastiaan.torrentstreamer.sample;

import android.app.Activity;

import java.net.URL;

import android.widget.SearchView;

import android.content.Context;

import androidx.annotation.Nullable;


public class ezParser {
    public URL url;
    public Activity theContext;
    public ezParser(Context context) {
        theContext = (Activity) context;
    }

    public void parseURL(String urlstr) {

        ParseTask theTask=new ParseTask(new AsyncResponse() {
            SearchView theView = null;
            @Override public void processFinish(@Nullable Object output) {
                System.out.println("[jcerr: processFinish]" + "Callback received:" + (String) output) ; // + urlstr);
                theView = (SearchView) ( theContext).findViewById(R.id.searchBox); //
                theView.setTooltipText( (String)output ); // hide the magnet link in the tooltip text
            }
        });
        theTask.execute(urlstr);
    }
}

