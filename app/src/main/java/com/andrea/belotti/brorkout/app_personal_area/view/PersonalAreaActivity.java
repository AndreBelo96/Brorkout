package com.andrea.belotti.brorkout.app_personal_area.view;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.PersonalData.IMAGE_DATA;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.PreferencesConstants.USERNAME_PREFERENCES;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.andrea.belotti.brorkout.GeneralSingleton;
import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.app_personal_area.contract.PersonalAreaContract;
import com.andrea.belotti.brorkout.app_personal_area.presenter.PersonalAreaPresenter;
import com.andrea.belotti.brorkout.app_starting_menu.view.StartingMenuActivity;
import com.andrea.belotti.brorkout.utils.AppMethodsUtils;
import com.andrea.belotti.brorkout.utils.ImageUtils;
import com.andrea.belotti.brorkout.utils.constants.ExerciseConstants;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalAreaActivity extends AppCompatActivity implements PersonalAreaContract.View {

    CircleImageView image;
    SharedPreferences sharedPreferences;
    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // do your operation from here...
                    if (data != null && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        Bitmap selectedImageBitmap = null;
                        try {
                            selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(this.getClass().getSimpleName(), "Starting activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_area);

        PersonalAreaPresenter presenter = new PersonalAreaPresenter(
                this,
                this.getBaseContext(),
                getSharedPreferences("MySharedPref", MODE_PRIVATE)
        );

        // SaveText
        TextView friendId = findViewById(R.id.friendIdText);
        EditText usernameET = findViewById(R.id.editTextUsername);
        EditText weightET = findViewById(R.id.editTextPeso);
        EditText heightET = findViewById(R.id.editTextAltezza);
        EditText fatPercentileET = findViewById(R.id.editTextGrassoCorporeo);

        friendId.setText(GeneralSingleton.getInstance().getLoggedUser().getFriendCode());

        image = findViewById(R.id.profile_image);

        // Buttons
        LinearLayout putImgBtn = findViewById(R.id.addImageBtn);
        LinearLayout saveBtn = findViewById(R.id.saveButton);
        ImageButton backButton = findViewById(R.id.backButton);
        ImageView copyIdFriend = findViewById(R.id.friendIdCopy);

        Log.i(this.getClass().getSimpleName(), "Init data of activity");
        presenter.initData(usernameET, weightET, heightET, fatPercentileET, image);

        putImgBtn.setOnClickListener(v -> {
            Log.i(this.getClass().getSimpleName(), "Choose image!");
            imageChooser();
        });

        copyIdFriend.setOnClickListener(v -> {
            AppMethodsUtils.setClipboard(getBaseContext(), friendId.getText().toString());
            Toast toast = Toast.makeText(getBaseContext(), "Codice amico copiato correttamente", Toast.LENGTH_SHORT);
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

            myEdit.putString(USERNAME_PREFERENCES, username);
            myEdit.putString(ExerciseConstants.PersonalData.WEIGHT, weight);
            myEdit.putString(ExerciseConstants.PersonalData.HEIGHT, height);
            myEdit.putString(ExerciseConstants.PersonalData.FAT_PERCENTILE, fatPercentile);

            myEdit.apply();

            Toast toast = Toast.makeText(getApplicationContext(), "Dati salvati con successo", ExerciseConstants.ToastMessageConstants.DURATION);
            toast.show();

            Log.i(this.getClass().getSimpleName(), "Data saved...");
        });

        backButton.setOnClickListener(v -> presenter.onBackClick());

    }

    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(i);
    }

    @Override
    public void replaceWithStartingMenuActivity(String message) {
        Log.i(this.getClass().getSimpleName(), message);
        Intent intent = new Intent(getBaseContext(), StartingMenuActivity.class);
        startActivity(intent);
    }

}