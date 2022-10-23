
package com.github.se_bastiaan.torrentstreamer.sample;

import android.app.Activity;

import java.net.URL;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.ListView;
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
            ListView listTemp = null;
            @Override public void processFinish(@Nullable Object output) {
                System.out.println("[jcerr: processFinish]" + "Callback received:" + (String) output) ; // + urlstr);
                theView = (SearchView) ( theContext).findViewById(R.id.searchBox); //
                theView.setTooltipText( (String)output ); // hide the magnet link in the tooltip text
                listTemp = (ListView) ( theContext).findViewById(R.id.listView); //
                listTemp.setVisibility(View.VISIBLE);
                String resultList[] = {(String)output + " ", "Dummy", "Dummy1", "Dummy2", "DummyDummy"};
                ArrayAdapter<String> tempAdapter = new ArrayAdapter<String>(theContext, R.layout.activity_listview, R.id.textView, resultList);
                listTemp.setAdapter(tempAdapter);
            }
        });
        theTask.execute(urlstr);
    }
}

