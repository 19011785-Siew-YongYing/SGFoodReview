package sg.edu.rp.c346.id19011785.ndpsong;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
        TextView songStars = rowV.findViewById(R.id.textViewStars);

        songT.setText(songAL.get(position).getTitle());
        songS.setText(songAL.get(position).getSingers());
        songYrR.setText(String.valueOf(songAL.get(position).getYears()));
        songStars.setText(songAL.get(position).toString());

        return rowV;

    }
}
