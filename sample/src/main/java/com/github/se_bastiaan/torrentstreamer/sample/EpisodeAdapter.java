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