package sg.edu.rp.c346.id19011785.ndpsong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {

    Button show5s;
    ArrayList<Song> songAL;
    ListView songLV;
    //ArrayAdapter<Song> songAA;
    CustomAdapter adpt; // Lesson 10 - Custom List View
    boolean check = true;

    Spinner spinner;
    ArrayList<String> yrs;
    ArrayAdapter<String> aaYrs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        songLV = findViewById(R.id.lvSongs);
        show5s = findViewById(R.id.btnShow5S);
        spinner = findViewById(R.id.spinnerYear);
        DBHelper dbh = new DBHelper(ShowActivity.this);
        songAL = dbh.getAllSongs();
        yrs = dbh.getYears();

        /*songAA = new ArrayAdapter<Song>(this, android.R.layout.simple_list_item_1, songAL);
        songLV.setAdapter(songAA);
        songAA.notifyDataSetChanged();*/
        // Lesson 10
        adpt = new CustomAdapter(ShowActivity.this, R.layout.row, songAL);
        songLV.setAdapter(adpt);
        adpt.notifyDataSetChanged();

        aaYrs = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, yrs);
        spinner.setAdapter(aaYrs);
        //songAA.notifyDataSetChanged();
        // Lesson 10
        adpt.notifyDataSetChanged();
        dbh.close();
        
        show5s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ShowActivity.this);
                if (check == true) {
                    songAL.clear();
                    songAL.addAll(dbh.getAllSongs(5));
                    //songAA.notifyDataSetChanged();
                    adpt.notifyDataSetChanged();
                    check = false;
                }
                else if (check == false) {
                    songAL.clear();
                    songAL.addAll(dbh.getAllSongs());
                    //songAA.notifyDataSetChanged();
                    adpt.notifyDataSetChanged();
                    check = true;
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DBHelper dbh = new DBHelper(ShowActivity.this);
                songAL.clear();
                songAL.addAll(dbh.getSongsByYear(Integer.valueOf(yrs.get(position))));
                //songAA.notifyDataSetChanged();
                adpt.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        songLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song target = songAL.get(position);
                Intent i = new Intent(ShowActivity.this, ModifyActivity.class);
                i.putExtra("song", target);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        DBHelper dbh = new DBHelper(ShowActivity.this);
        songAL.clear();
        songAL.addAll(dbh.getAllSongs());
        //songAA.notifyDataSetChanged();
        adpt.notifyDataSetChanged();

        yrs.clear();
        yrs.addAll(dbh.getYears());
        aaYrs.notifyDataSetChanged();
    }
}