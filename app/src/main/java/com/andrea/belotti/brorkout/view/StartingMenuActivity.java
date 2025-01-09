package com.andrea.belotti.brorkout.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.view.personal_area.PersonalAreaActivity;
import com.andrea.belotti.brorkout.view.archive.ScheduleArchiveActivity;
import com.andrea.belotti.brorkout.view.selection.SelectScheduleActivity;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.utils.ImageUtils;
import com.andrea.belotti.brorkout.view.creation.ScheduleCreatorActivity;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.andrea.belotti.brorkout.constants.ExerciseConstants.PersonalData.IMAGE_DATA;

/**
 * @author      Andrea Belotti <andreablt96@hotmail.it>
 * @version     1.0
 * @since       1.0
 */
public class StartingMenuActivity extends AppCompatActivity {

    // log
    private final String tag = this.getClass().getSimpleName();

    int duration = Toast.LENGTH_SHORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(tag, "Starting activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        String optionActivity = getResources().getString(R.string.OptionSchedule);

        Context context = getApplicationContext();

        LinearLayout startWorkoutBtn = findViewById(R.id.startWorkoutBtn);
        LinearLayout createScheduleBtn = findViewById(R.id.createScheduleBtn);
        LinearLayout archivioBtn = findViewById(R.id.archivioBtn);
        LinearLayout personalAreaBtn = findViewById(R.id.personalArea);
        LinearLayout optionBtn = findViewById(R.id.optionBtn);

        TextView username = findViewById(R.id.userNameText);
        CircleImageView image = findViewById(R.id.profile_image);

        // init val
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        username.setText(sharedPreferences.getString(ExerciseConstants.PersonalData.USERNAME, "Username"));

        if (ImageUtils.covertFromStringToBitmap(sharedPreferences.getString(IMAGE_DATA, "")) != null) {
            image.setImageBitmap(ImageUtils.covertFromStringToBitmap(sharedPreferences.getString(IMAGE_DATA, "")));
        }
        // init dell'immagine + fare metodo a parte

        startWorkoutBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), SelectScheduleActivity.class);
            startActivity(intent);
        });

        createScheduleBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), ScheduleCreatorActivity.class);
            intent.putExtra("modifica", false);
            startActivity(intent);
        });

        archivioBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), ScheduleArchiveActivity.class);
            startActivity(intent);
        });

        personalAreaBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), PersonalAreaActivity.class);
            startActivity(intent);
        });

        optionBtn.setOnClickListener(v -> {
            Toast toast = Toast.makeText(context, optionActivity, duration);
            toast.show();
        });

    }
}