package sg.edu.rp.c346.id19011785.ndpsong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {

    Button show5s;
    ArrayList<Song> songAL;
    ListView songLV;
    ArrayAdapter<Song> songAA;
    Song songs;
    boolean check = false;

    // Enhancement for spinner is not completed because I could not test out other functions
    // Will try the enhancement soon and commit to GitHub

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        show5s = findViewById(R.id.btnShow5S);

        Intent i = getIntent();
        songs = (Song) i.getSerializableExtra("song");

        DBHelper dbh = new DBHelper(ShowActivity.this);

        songLV = findViewById(R.id.lvSongs);
        songAL = new ArrayList<Song>();
        songAL.addAll(dbh.getAllSongs());
        songAA = new ArrayAdapter<Song>(this, android.R.layout.simple_list_item_1, songAL);
        songLV.setAdapter(songAA);
        
        show5s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ShowActivity.this);
                songAL.clear();
                int star = songs.getStars();
                if (check == false) {
                    songAL.addAll(dbh.getAllSongs());
                    songAA.notifyDataSetChanged();
                    check = true;
                }
                else {
                    songAL.addAll(dbh.getAllSongs(5));
                    songAA.notifyDataSetChanged();
                    check = false;
                }
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
        show5s.performClick();
    }
}