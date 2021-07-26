package sg.edu.rp.c346.id19011785.ndpsong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ModifyActivity extends AppCompatActivity {

    TextView viewID, viewTitle, viewSinger, viewYear, viewStar;
    EditText editID, editTitle, editSinger, editYear;
    RadioGroup groupB;
    RadioButton b1, b2, b3, b4, b5;
    Button upBtn, delBtn, cancelBtn;
    Song song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        // FindViewById
        viewID = findViewById(R.id.TVID);
        viewTitle = findViewById(R.id.TVTitle);
        viewSinger = findViewById(R.id.TVSingers);
        viewYear = findViewById(R.id.TVYear);
        viewStar = findViewById(R.id.TVStar);
        editID = findViewById(R.id.idET);
        editTitle = findViewById(R.id.titleET);
        editSinger = findViewById(R.id.singersET);
        editYear = findViewById(R.id.yearET);
        groupB = findViewById(R.id.radioGroup);
        b1 = findViewById(R.id.radioB1);
        b2 = findViewById(R.id.radioB2);
        b3 = findViewById(R.id.radioB3);
        b4 = findViewById(R.id.radioB4);
        b5 = findViewById(R.id.radioB5);

        upBtn = findViewById(R.id.btnUpdate);
        delBtn = findViewById(R.id.btnDelete);
        cancelBtn = findViewById(R.id.btnCancel);

        Intent i = getIntent();
        song = (Song)i.getSerializableExtra("song");

        editID.setText(song.getId()+"");
        editTitle.setText(song.getTitle());
        editSinger.setText(song.getSingers());
        editYear.setText(song.getYears()+"");
        /*int stars = song.getStars();
        if (stars == 1) {
            b1.setChecked(true);
        }
        else if (stars == 2) {
            b2.setChecked(true);
        }
        else if (stars == 3) {
            b3.setChecked(true);
        }
        else if (stars == 4) {
            b4.setChecked(true);
        }
        else if (stars == 5) {
            b5.setChecked(true);
        }*/

        switch (song.getStars() ) {
            case 5:
                b5.setChecked(true);
                break;
            case 4:
                b4.setChecked(true);
                break;
            case 3:
                b3.setChecked(true);
                break;
            case 2:
                b2.setChecked(true);
                break;
            case 1:
                b1.setChecked(true);
        }

        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ModifyActivity.this);
                song.setTitle(editTitle.getText().toString().trim());
                song.setSingers(editSinger.getText().toString().trim());
                song.setYears(Integer.parseInt(editYear.getText().toString().trim()));
                int checkedRB = groupB.getCheckedRadioButtonId();
                if (checkedRB == R.id.radioB1) {
                    song.setStars(1);
                }
                else if (checkedRB == R.id.radioB2) {
                    song.setStars(2);
                }
                else if (checkedRB == R.id.radioB3) {
                    song.setStars(3);
                }
                else if (checkedRB == R.id.radioB4) {
                    song.setStars(4);
                }
                else if (checkedRB == R.id.radioB5) {
                    song.setStars(5);
                }

                int res = dbh.updateSong(song);
                if (res > 0) {
                    Toast.makeText(ModifyActivity.this, "Song Successfully Updated!", Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    Toast.makeText(ModifyActivity.this, "Unable to Update Song!", Toast.LENGTH_LONG).show();
                }
                dbh.close();
            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ModifyActivity.this);
                int res = dbh.deleteSong(song.getId());
                if (res > 0) {
                    Toast.makeText(ModifyActivity.this, "Song Successfully Deleted!", Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    Toast.makeText(ModifyActivity.this, "Unable to Delete Song!", Toast.LENGTH_LONG).show();
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}