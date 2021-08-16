package sg.edu.rp.c346.id19011785.sgfood;

import android.content.Context;
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
    ArrayList<SGFood> foodAL;

    public CustomAdapter (Context context, int res, ArrayList<SGFood> objects) {
        super (context, res, objects);

        parent_context = context;
        layout_id = res;
        foodAL = objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowV = inflater.inflate(layout_id, parent, false);

        TextView foodN = rowV.findViewById(R.id.textViewName);
        TextView foodDesc = rowV.findViewById(R.id.textViewDesc);
        TextView foodPrice = rowV.findViewById(R.id.textViewPrice);
        RatingBar foodStarRB = rowV.findViewById(R.id.starRBar);
        ImageView delish = rowV.findViewById(R.id.ivDelish);
        ImageView rec = rowV.findViewById(R.id.ivRec);

        SGFood current = foodAL.get(position);
        foodN.setText(current.getName());
        foodDesc.setText(current.getDesc());
        String fP = String.format("%.2f", current.getPrice());
        foodPrice.setText(fP + "");
        foodStarRB.setRating(current.getStars());

        if (current.getStars() >= 3 && current.getRec() == 1) {
            delish.setVisibility(View.VISIBLE);
            rec.setVisibility(View.VISIBLE);
        }
        else if (current.getStars() < 3 && current.getRec() == 1) {
            delish.setVisibility(View.INVISIBLE);
            rec.setVisibility(View.VISIBLE);
        }
        else if (current.getStars() >= 3 && current.getRec() == 0) {
            delish.setVisibility(View.VISIBLE);
            rec.setVisibility(View.INVISIBLE);
        }
        else {
            delish.setVisibility(View.INVISIBLE);
            rec.setVisibility(View.INVISIBLE);
        }

        return rowV;
    }
}
