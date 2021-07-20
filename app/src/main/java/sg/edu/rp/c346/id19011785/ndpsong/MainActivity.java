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
    RadioButton rb1, rb2, rb3, rb4, rb5;
    Button btnInsert, btnShow;

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
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        rb5 = findViewById(R.id.rb5);
        btnInsert = findViewById(R.id.btnInsert);
        btnShow = findViewById(R.id.btnShowList);

        // Able to insert songs now
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String songTitle = etTitle.getText().toString();
                String songSinger = etSingers.getText().toString();
                String strYear = etYear.getText().toString();
                String songT = "";
                String songS = "";
                int songY = 0;
                if (songTitle.length() == 0 || songSinger.length() == 0 || strYear.length() == 0) {
                    Toast.makeText(MainActivity.this, "Please input Song Title, Singer or Year!", Toast.LENGTH_LONG).show();
                }
                else {
                    songT = songTitle;
                    songS = songSinger;
                    songY = Integer.parseInt(strYear);
                }

                int checkedButton = starRBGrp.getCheckedRadioButtonId();
                int stars = 1;
                if (checkedButton == R.id.rb1) {
                    stars = 1;
                }
                else if (checkedButton == R.id.rb2) {
                    stars = 2;
                }
                else if (checkedButton == R.id.rb3) {
                    stars = 3;
                }
                else if (checkedButton == R.id.rb4) {
                    stars = 4;
                }
                else if (checkedButton == R.id.rb5) {
                    stars = 5;
                }
                else {
                    Toast.makeText(MainActivity.this, "Please input stars", Toast.LENGTH_LONG).show();
                }
                DBHelper dbh = new DBHelper(MainActivity.this);
                long insert_id = dbh.insertSong(songT, songS, songY, stars);

                if (insert_id != -1) {
                    Toast.makeText(MainActivity.this, "Song Insert Successfully", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Unable to show the list of songs ; will try again
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent show = new Intent(MainActivity.this, ShowActivity.class);
                startActivity(show);
            }
        });
    }
}