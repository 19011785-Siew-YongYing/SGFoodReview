package sg.edu.rp.c346.id19011785.ndpsong;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    Context parent_context;
    int layout_id;
    ArrayList<Song> songAL;

    public CustomAdapter (Context context, int res, ArrayList<Song> objects) {
        super (context, res, objects);

        parent_context = context;
        layout_id = res;
        songAL = objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowV = inflater.inflate(layout_id, parent, false);

        TextView songT = rowV.findViewById(R.id.textViewTitle);
        TextView songS = rowV.findViewById(R.id.textViewSinger);
        TextView songYrR = rowV.findViewById(R.id.textViewYrReleased);
        RatingBar songStarRB = rowV.findViewById(R.id.starRBar);
        ImageView songNew = rowV.findViewById(R.id.ivNew);

        Song current = songAL.get(position);
        songT.setText(current.getTitle());
        songS.setText(current.getSingers());
        songYrR.setText(current.getYears() + "");
        songStarRB.setRating(current.getStars());
        songNew.setImageResource(R.drawable.newsong);

        if (current.getYears() >= 2019) {
            songNew.setVisibility(View.VISIBLE);
        }
        else {
            songNew.setVisibility(View.INVISIBLE);
        }
        return rowV;
    }
}
