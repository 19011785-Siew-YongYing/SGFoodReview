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
    Song data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        show5s = findViewById(R.id.btnShow5S);

        Intent i = getIntent();
        data = (Song)i.getSerializableExtra("song");

        songLV = findViewById(R.id.lvSongs);
        songAL = new ArrayList<Song>();
        songAA = new ArrayAdapter<Song>(this, android.R.layout.simple_list_item_1, songAL);
        songLV.setAdapter(songAA);

        songLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song target = songAL.get(position);
                Intent i = new Intent(ShowActivity.this, ModifyActivity.class);
                i.putExtra("data", target);
                startActivity(i);
            }
        });

    }
}