
package com.github.se_bastiaan.torrentstreamer.sample;

import android.app.Activity;

import java.net.URL;
import java.util.ArrayList;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.ListView;
import android.content.Context;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
            String firstUrl="";

            /* TODO: replace standard ArrayAdapter with custom adapter that can store title, magnet link and (eventually) thumbnail
                TODO: for reference on custom adapters: https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView
                  TODO: eventually will want to make thumbnail part of each row or header, will require using query at doc level to grab show link, then jsoup it
                   */
            @Override public void processFinish(@Nullable Object output) {
                Document doc= (Document) output;
                Elements elements=null;
                if (doc != null) {
                    firstUrl= doc.select("a[href^=magnet]").first().attr("href");
                    elements = doc.select("a[href^=magnet]");
                    System.out.println("[jcerr: doc not null");
                }

                System.out.println("[jcerr: processFinish]" + "Callback received:" + firstUrl) ; // + urlstr);
                theView = (SearchView) (theContext).findViewById(R.id.searchBox); //
                theView.setTooltipText( firstUrl ); // hide the magnet link in the tooltip text
                ListView listTemp = (ListView) (theContext).findViewById(R.id.listView); //
                listTemp.setVisibility(View.VISIBLE);
//                String[] epList =null;
//                ArrayAdapter<String> tempAdapter = new ArrayAdapter<>(theContext, R.layout.activity_listview, R.id.textView, epList);
//                listTemp.setAdapter(tempAdapter);
                ArrayList<Episode> epList =new ArrayList<Episode>();
                EpisodeAdapter tempAdapter = new EpisodeAdapter(theContext, epList); // added type because compiler complained about unchecked type
                listTemp.setAdapter(tempAdapter);
                System.out.println("[jcerr: Number of elements:" + firstUrl);
                if (elements != null) {
                    System.out.println("[jcerr: loop limit:" + elements.size());
                    for (int i=0; i<elements.size();i++){
                            System.out.println("[jcerr: loop index:" + i);
                            Element element = elements.get(i);
                        if (element != null) {
                            Episode tempEp = new Episode( "#" + i + element.attr("title"), element.attr("href") );
                            tempAdapter.add(tempEp);
                        }
                    }
                }

            }
        });
        theTask.execute(urlstr);
    }
}

