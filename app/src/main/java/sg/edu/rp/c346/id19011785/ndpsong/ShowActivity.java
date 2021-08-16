package sg.edu.rp.c346.id19011785.ndpsong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {

    Button show5s;
    ArrayList<SGFood> foodAL;
    ListView foodLV;
    CustomAdapter adpt;
    Spinner spinner;
    boolean check = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        setTitle("Singapore Food - List of Food");

        foodLV = findViewById(R.id.lvSongs);
        show5s = findViewById(R.id.btnShow5S);
        DBHelper dbh = new DBHelper(ShowActivity.this);
        foodAL = dbh.getAllFood();

        spinner = findViewById(R.id.spinnerRec);

        adpt = new CustomAdapter(ShowActivity.this, R.layout.row, foodAL);
        foodLV.setAdapter(adpt);
        adpt.notifyDataSetChanged();
        dbh.close();
        
        show5s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ShowActivity.this);
                if (check == true) {
                    foodAL.clear();
                    foodAL.addAll(dbh.getAllFood(5));
                    adpt.notifyDataSetChanged();
                    check = false;
                }
                else if (check == false) {
                    foodAL.clear();
                    foodAL.addAll(dbh.getAllFood());
                    adpt.notifyDataSetChanged();
                    check = true;
                }
            }
        });

        // tried using the reommendations ; completed
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DBHelper dbh = new DBHelper(ShowActivity.this);
                switch (position) {
                    case 0:
                        foodAL.clear();
                        foodAL.addAll(dbh.getRecommends(1));
                        adpt.notifyDataSetChanged();
                        break;
                    case 1:
                        foodAL.clear();
                        foodAL.addAll(dbh.getRecommends(0));
                        adpt.notifyDataSetChanged();
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        foodLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SGFood target = foodAL.get(position);
                Intent i = new Intent(ShowActivity.this, ModifyActivity.class);
                i.putExtra("food", target);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        DBHelper dbh = new DBHelper(ShowActivity.this);
        foodAL.clear();
        foodAL.addAll(dbh.getAllFood());
        adpt.notifyDataSetChanged();

    }
}