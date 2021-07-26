package sg.edu.rp.c346.id19011785.ndpsong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
                String songTitle = etTitle.getText().toString().trim();
                String songT = "";

                String songSinger = etSingers.getText().toString().trim();
                String songS = "";

                String strYear = etYear.getText().toString().trim();
                int songY = 0;

                if (songTitle.length() == 0 || songSinger.length() == 0 || strYear.length() == 0) {
                    Toast.makeText(MainActivity.this, "Please input Song Title, Singer or Year Released!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    songT = songTitle;
                    songS = songSinger;
                    songY = Integer.parseInt(strYear);
                }

                //int checkedButton = starRBGrp.getCheckedRadioButtonId();
                int stars = getStars();
                /*if (checkedButton == R.id.rb1) {
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
                }*/
                DBHelper dbh = new DBHelper(MainActivity.this);
                long insert_id = dbh.insertSong(songT, songS, songY, stars);

                if (insert_id != -1) {
                    Toast.makeText(MainActivity.this, "Song Insert Successfully", Toast.LENGTH_LONG).show();
                    etTitle.setText("");
                    etSingers.setText("");
                    etYear.setText("");
                    starRBGrp.clearCheck();
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

    // referred to solutions
    private int getStars() {
        int stars = 1;
        switch (starRBGrp.getCheckedRadioButtonId()) {
            case R.id.rb1:
                stars = 1;
                break;

            case R.id.rb2:
                stars = 2;
                break;

            case R.id.rb3:
                stars = 3;
                break;

            case R.id.rb4:
                stars = 4;
                break;

            case R.id.rb5:
                stars = 5;
                break;
        }
        return stars;
    }

    // Some Enhancements after solving PS
    @Override
    protected void onPause() {
        super.onPause();

        String inpTitle = etTitle.getText().toString().trim();
        String inpSingers = etSingers.getText().toString().trim();
        String inpYear = etYear.getText().toString().trim();
        String storeT = "";
        String storeS = "";
        int storeYr = 0;
        //int storeStar = getStars();
        if (inpTitle.length() > 0 || inpSingers.length() > 0 || inpYear.length() > 0) {
            storeT = inpTitle;
            storeS = inpSingers;
            storeYr = Integer.parseInt(inpYear);
        }

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefEdit = prefs.edit();

        // storing String values first ; radio button still figuring out
        prefEdit.putString("title", storeT);
        prefEdit.putString("singers", storeS);
        prefEdit.putInt("yearR", storeYr);

        prefEdit.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences pref = getPreferences(MODE_PRIVATE);

        String strTitle = pref.getString("title", "");
        String strSingers = pref.getString("singers" , "");
        int yearR = pref.getInt("yearR", 0);

        etTitle.setText(strTitle);
        etSingers.setText(strSingers);
        etYear.setText(String.valueOf(yearR));
    }
}