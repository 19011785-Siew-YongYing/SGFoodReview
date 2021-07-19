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

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String songTitle = etTitle.getText().toString();
                String songSinger = etSingers.getText().toString();
                int songYear = Integer.parseInt(etYear.getText().toString());
                int checkedButton = starRBGrp.getCheckedRadioButtonId();
                if (checkedButton == R.id.radioB1) {
                    song.setStars(1);
                }
                else if (checkedButton == R.id.radioB2) {
                    song.setStars(2);
                }
                else if (checkedButton == R.id.radioB3) {
                    song.setStars(3);
                }
                else if (checkedButton == R.id.radioB4) {
                    song.setStars(4);
                }
                else if (checkedButton == R.id.radioB5) {
                    song.setStars(5);
                }
                else {
                    Toast.makeText(MainActivity.this, "Please select stars!", Toast.LENGTH_SHORT).show();
                }

                DBHelper dbh = new DBHelper(MainActivity.this);
                long insert_id = dbh.insertSong(songTitle, songSinger, songYear, checkedButton);

                if (insert_id != -1) {
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
}