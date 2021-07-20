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

        editID.setText(String.valueOf(song.getId()));
        editTitle.setText(song.getTitle());
        editSinger.setText(song.getSingers());
        editYear.setText(song.getYears());
        int stars = song.getStars();
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
        }

        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ModifyActivity.this);
                song.setTitle(editTitle.getText().toString());
                song.setSingers(editSinger.getText().toString());
                song.setYears(Integer.parseInt(editYear.getText().toString()));
                song.setStars(groupB.getCheckedRadioButtonId());
                dbh.updateSong(song);
                dbh.close();
                finish();
            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ModifyActivity.this);
                dbh.deleteSong(song.getId());
                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

    }
}