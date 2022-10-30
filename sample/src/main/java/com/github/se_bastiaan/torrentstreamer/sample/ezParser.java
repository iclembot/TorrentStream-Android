
package com.github.se_bastiaan.torrentstreamer.sample;

import android.app.Activity;

import java.net.URL;

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
                String[] resultList =null;
                System.out.println("[jcerr: Number of elements:" + firstUrl);
                if (elements != null) {
                    resultList = new String[elements.size()];
                    System.out.println("[jcerr: loop limit:" + elements.size());
                    for (int i=0; i<elements.size();i++){
                            System.out.println("[jcerr: loop index:" + i);
                            Element element = elements.get(i);
                        if (element != null) {
                            resultList[i] = "#" + i + element.attr("title");
                            System.out.println("[jcerr: inner html: " + element.attr("href") ); //;
                        }
                    }
                }
                ArrayAdapter<String> tempAdapter = new ArrayAdapter<>(theContext, R.layout.activity_listview, R.id.textView, resultList);
                listTemp.setAdapter(tempAdapter);
            }
        });
        theTask.execute(urlstr);
    }
}

