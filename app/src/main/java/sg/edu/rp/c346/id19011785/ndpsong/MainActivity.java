package sg.edu.rp.c346.id19011785.ndpsong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView tvTitle, tvSingers, tvYear, tvStars;
    EditText etTitle, etSingers, etYear;
    RadioGroup starRBGrp;
    Button btnInsert, btnShow;
    ArrayList<Song> al;
    Song song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = findViewById(R.id.tvTitle);
        tvSingers = findViewById(R.id.tvSingers);
        tvYear = findViewById(R.id.tvYear);
        tvStars = findViewById(R.id.tvStars);

        etTitle = findViewById(R.id.etTitle);
        etSingers = findViewById(R.id.etSingers);
        etYear = findViewById(R.id.etYear);

        starRBGrp = findViewById(R.id.starRBGroup);
        btnInsert = findViewById(R.id.btnInsert);
        btnShow = findViewById(R.id.btnShowList);

        // Unable to test out because it keeps crashing (will try to try again)
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String songTitle = etTitle.getText().toString();
                String songSinger = etSingers.getText().toString();
                int songYear = Integer.parseInt(etYear.getText().toString());
                int checkedButton = getStars();
                /*if (songT.length() == 0 || songS.length() == 0 || songY.length() == 0) {
                    Toast.makeText(MainActivity.this, "Please input Song Title, Singer or Year", Toast.LENGTH_LONG).show();
                }
                else {
                    songTitle = songT;
                    songSinger = songS;
                    songYear = ;
                }*/
                DBHelper dbh = new DBHelper(MainActivity.this);
                long insert_id = dbh.insertSong(songTitle, songSinger, songYear, checkedButton);

                if (insert_id != -1) {
                    al.clear();
                    al.addAll(dbh.getAllSongs());
                    Toast.makeText(MainActivity.this, "Song Insert Successfully", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ShowActivity.class);
                startActivity(i);
            }
        });
    }

    private int getStars() {
        int stars = 1;
        if (starRBGrp.getCheckedRadioButtonId() == R.id.rb1) {
            stars = 1;
        }
        else if (starRBGrp.getCheckedRadioButtonId() == R.id.rb2) {
            stars = 2;
        }
        else if (starRBGrp.getCheckedRadioButtonId() == R.id.rb3) {
            stars = 3;
        }
        else if (starRBGrp.getCheckedRadioButtonId() == R.id.rb4) {
            stars = 4;
        }
        else if (starRBGrp.getCheckedRadioButtonId() == R.id.rb5) {
            stars = 5;
        }
        else {
            Toast.makeText(MainActivity.this, "Please input stars", Toast.LENGTH_LONG).show();
        }
        return stars;
    }
}