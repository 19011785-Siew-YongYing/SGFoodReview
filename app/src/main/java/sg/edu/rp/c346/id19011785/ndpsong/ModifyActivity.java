package sg.edu.rp.c346.id19011785.ndpsong;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class ModifyActivity extends AppCompatActivity {

    TextView viewID, viewName, viewDesc, viewPrice, viewStar;
    EditText editID, editName, editDesc, editPrice;
    RatingBar ratebStars;
    Button upBtn, delBtn, cancelBtn;
    SGFood food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        setTitle("Singapore Food - Modify");

        // FindViewById
        viewID = findViewById(R.id.TVID);
        viewName = findViewById(R.id.TVFName);
        viewDesc = findViewById(R.id.TVFDesc);
        viewPrice = findViewById(R.id.TVFPrice);
        viewStar = findViewById(R.id.TVStar);
        editID = findViewById(R.id.idET);
        editID.setEnabled(false);
        editName = findViewById(R.id.nameET);
        editDesc = findViewById(R.id.fDescET);
        editPrice = findViewById(R.id.yearET);
        ratebStars = findViewById(R.id.rbStar);

        upBtn = findViewById(R.id.btnUpdate);
        delBtn = findViewById(R.id.btnDelete);
        cancelBtn = findViewById(R.id.btnCancel);

        Intent i = getIntent();
        food = (SGFood) i.getSerializableExtra("food");

        editID.setText(food.getId()+"");
        editID.setEnabled(false);
        editName.setText(food.getName());
        editDesc.setText(food.getDesc());
        editPrice.setText(food.getPrice()+"");
        ratebStars.setRating(food.getStars());

        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ModifyActivity.this);
                food.setName(editName.getText().toString().trim());
                food.setDesc(editDesc.getText().toString().trim());
                food.setPrice(Double.parseDouble(editPrice.getText().toString().trim()));
                food.setStars((int) ratebStars.getRating());

                int res = dbh.updateFood(food);
                if (res > 0) {
                    Toast.makeText(ModifyActivity.this, "Successfully Updated", Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    Toast.makeText(ModifyActivity.this, "Unable to Update Food Item!", Toast.LENGTH_LONG).show();
                }
                dbh.close();
            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(ModifyActivity.this);
                myBuilder.setTitle("WARNING!"); // WS mentioned "Danger" as the title.
                myBuilder.setMessage("Are you sure you want to delete:\n" + food.getName());
                myBuilder.setCancelable(false);

                myBuilder.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper dbh = new DBHelper(ModifyActivity.this);
                        int res = dbh.deleteFood(food.getId());
                        if (res > 0) {
                            Toast.makeText(ModifyActivity.this, "Successfully Deleted!", Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else {
                            Toast.makeText(ModifyActivity.this, "Unable to Delete Food Item!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                myBuilder.setPositiveButton("CANCEL", null);
                AlertDialog myDialog =myBuilder.create();
                myDialog.show();

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(ModifyActivity.this);
                myBuilder.setTitle("WARNING!"); // WS mentioned "Danger" as the title.
                myBuilder.setMessage("Are you sure you want to discard the changes?");
                myBuilder.setCancelable(false);

                myBuilder.setNegativeButton("DISCARD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                myBuilder.setPositiveButton("DO NOT DISCARD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        food.setName(editName.getText().toString().trim());
                        food.setDesc(editDesc.getText().toString().trim());
                        food.setPrice(Double.parseDouble(editPrice.getText().toString().trim()));
                        food.setStars((int) ratebStars.getRating());
                    }
                });

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

    }
}