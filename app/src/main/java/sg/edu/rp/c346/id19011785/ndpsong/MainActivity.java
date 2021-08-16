package sg.edu.rp.c346.id19011785.ndpsong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tvName, tvDesc, tvPrice, tvStars;
    EditText etName, etDesc, etPrice;
    RatingBar starRating;
    Button btnInsert, btnShow;
    CheckBox cbRecommend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Singapore Food - Add New Food");

        tvName = findViewById(R.id.tvName);
        tvDesc = findViewById(R.id.tvDesc);
        tvPrice = findViewById(R.id.tvPrice);
        tvStars = findViewById(R.id.tvStars);

        etName = findViewById(R.id.etName);
        etDesc = findViewById(R.id.etDesc);
        etPrice = findViewById(R.id.etPrice);
        starRating = findViewById(R.id.starRatingB);
        cbRecommend = findViewById(R.id.checkBox);
        btnInsert = findViewById(R.id.btnInsert);
        btnShow = findViewById(R.id.btnShowList);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodName = etName.getText().toString().trim();
                String foodN = "";

                String foodDesc = etDesc.getText().toString().trim();
                String fDesc = "";

                String strPrice = etPrice.getText().toString().trim();
                double foodPrice = 0.0;

                int stars = (int)starRating.getRating();

                int checked = 0;

                if (cbRecommend.isChecked() == true){
                    checked = 1;
                }
                else {
                    checked = 0;
                }

                if (foodName.length() == 0 || foodDesc.length() == 0 || strPrice.length() == 0) {
                    Toast.makeText(MainActivity.this, "Please input Food Name, Description and Price!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    foodN = foodName;
                    fDesc = foodDesc;
                    foodPrice = Double.parseDouble(strPrice);
                }

                DBHelper dbh = new DBHelper(MainActivity.this);
                long insert_id = dbh.insertFood(foodN, fDesc, foodPrice, stars, checked);

                if (insert_id != -1) {
                    Toast.makeText(MainActivity.this, "Food Insert Successfully", Toast.LENGTH_LONG).show();
                    etName.setText("");
                    etDesc.setText("");
                    etPrice.setText("");
                    starRating.setRating(0);
                    cbRecommend.setChecked(false);
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