package com.github.se_bastiaan.torrentstreamer.sample;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class EpisodeAdapter extends ArrayAdapter<Episode> {

    public EpisodeAdapter(Context context, ArrayList<Episode> episodes) {
        super(context, 0, episodes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Episode episode = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_episode, parent, false);
        }
        // adding event handler jc :
        // Cache row position inside the button using `setTag`
        convertView.setTag(position);
        // Attach the click event handler
        convertView.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               int position = (Integer) view.getTag();
                                               System.out.println("[jcerr: onClick-> id=" + view.getId() + " pos=" + position);
                                               // Access the row position here to get the correct data item
                                               Episode tempEpisode = getItem(position);
                                               // Do what you want here...
                                               parent.getRootView().findViewById(R.id.searchBox).setTooltipText(tempEpisode.link);
                                               System.out.println("[jcerr: episode.link= "+ tempEpisode.link);
                                           }
                                       });
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvHome = (TextView) convertView.findViewById(R.id.tvLink);
        // Populate the data into the template view using the data object
        tvName.setText(episode.title);
        tvHome.setText(episode.link);
        // Return the completed view to render on screen
        return convertView;
    }
}