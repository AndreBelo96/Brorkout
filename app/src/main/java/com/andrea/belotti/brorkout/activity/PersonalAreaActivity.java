package com.andrea.belotti.brorkout.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.utils.ImageUtils;

import java.io.IOException;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.andrea.belotti.brorkout.constants.ExerciseConstants.PersonalData.IMAGE_DATA;

public class PersonalAreaActivity extends AppCompatActivity {

    CircleImageView image;
    SharedPreferences sharedPreferences;

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

        image = findViewById(R.id.profile_image);

        // Buttons
        LinearLayout putImgBtn = findViewById(R.id.addImageBtn);
        LinearLayout saveBtn = findViewById(R.id.saveButton);
        ImageButton backButton = findViewById(R.id.backButton);

        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        initData(usernameET, weightET, heightET, fatPercentileET, image, sharedPreferences);

        putImgBtn.setOnClickListener(v -> {

            Log.i(this.getClass().getSimpleName(), "Choose image!");

            imageChooser();
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
                          CircleImageView image,
                          SharedPreferences sharedPreferences) {

        Log.i(this.getClass().getSimpleName(), "Init activity");

        usernameET.setText(sharedPreferences.getString(ExerciseConstants.PersonalData.USERNAME, "Username"));
        weightET.setText(sharedPreferences.getString(ExerciseConstants.PersonalData.WEIGHT, ""));
        heightET.setText(sharedPreferences.getString(ExerciseConstants.PersonalData.HEIGHT, ""));
        fatPercentileET.setText(sharedPreferences.getString(ExerciseConstants.PersonalData.FAT_PERCENTILE, ""));

        if (ImageUtils.covertFromStringToBitmap(sharedPreferences.getString(IMAGE_DATA, "")) != null) {
            image.setImageBitmap(ImageUtils.covertFromStringToBitmap(sharedPreferences.getString(IMAGE_DATA, "")));
        }

    }


    void imageChooser() {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(i);
    }


    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // do your operation from here...
                    if (data != null && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        Bitmap selectedImageBitmap = null;
                        try {
                            selectedImageBitmap
                                    = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(),
                                    selectedImageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        image.setImageBitmap(selectedImageBitmap);
                        // To store data
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putString(IMAGE_DATA, ImageUtils.convertImgToString(selectedImageBitmap));
                        myEdit.apply();
                    }
                }
            });



}