package com.andrea.belotti.brorkout.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;

import androidx.appcompat.app.AppCompatActivity;

public class PersonalAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(this.getClass().getSimpleName(), "Starting activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_area);

        // SaveText
        EditText usernameET = findViewById(R.id.editTextUsername);
        EditText weightET = findViewById(R.id.editTextPeso);
        EditText heightET = findViewById(R.id.editTextAltezza);
        EditText fatPercentileET = findViewById(R.id.editTextGrassoCorporeo);

        // Buttons
        LinearLayout putImgBtn = findViewById(R.id.addImageBtn);
        LinearLayout saveBtn = findViewById(R.id.saveButton);
        ImageButton backButton = findViewById(R.id.backButton);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        initData(usernameET, weightET, heightET, fatPercentileET, sharedPreferences);

        putImgBtn.setOnClickListener(v -> {

            Log.i(this.getClass().getSimpleName(), "Choose image!");
            Toast toast = Toast.makeText(getApplicationContext(), "Immagine da caricare", ExerciseConstants.ToastMessageConstants.DURATION);
            toast.show();
        });


        saveBtn.setOnClickListener(v -> {

            Log.i(this.getClass().getSimpleName(), "Saving data...");

            String username = usernameET.getText().toString();
            String weight = weightET.getText().toString();
            String height = heightET.getText().toString();
            String fatPercentile = fatPercentileET.getText().toString();

            // To store data
            SharedPreferences.Editor myEdit = sharedPreferences.edit();

            myEdit.putString(ExerciseConstants.PersonalData.USERNAME, username);
            myEdit.putString(ExerciseConstants.PersonalData.WEIGHT, weight);
            myEdit.putString(ExerciseConstants.PersonalData.HEIGHT, height);
            myEdit.putString(ExerciseConstants.PersonalData.FAT_PERCENTILE, fatPercentile);

            // TODO salva nome immagine

            myEdit.apply();

            Toast toast = Toast.makeText(getApplicationContext(), "Dati salvati con successo", ExerciseConstants.ToastMessageConstants.DURATION);
            toast.show();

            Log.i(this.getClass().getSimpleName(), "Data saved...");
        });

        backButton.setOnClickListener(v -> {
            Log.i(this.getClass().getSimpleName(), "Home button");
            Intent intent = new Intent(getBaseContext(), StartingMenuActivity.class);
            startActivity(intent);
        });
    }

    private void initData(EditText usernameET,
                          EditText weightET,
                          EditText heightET,
                          EditText fatPercentileET,
                          SharedPreferences sharedPreferences) {

        Log.i(this.getClass().getSimpleName(), "Init activity");

        usernameET.setText(sharedPreferences.getString(ExerciseConstants.PersonalData.USERNAME, "Username"));
        weightET.setText(sharedPreferences.getString(ExerciseConstants.PersonalData.WEIGHT, ""));
        heightET.setText(sharedPreferences.getString(ExerciseConstants.PersonalData.HEIGHT, ""));
        fatPercentileET.setText(sharedPreferences.getString(ExerciseConstants.PersonalData.FAT_PERCENTILE, ""));

    }
}