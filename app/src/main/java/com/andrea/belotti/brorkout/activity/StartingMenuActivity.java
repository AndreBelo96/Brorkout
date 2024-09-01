package com.andrea.belotti.brorkout.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;

import androidx.appcompat.app.AppCompatActivity;

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

        String archivioActivity = getResources().getString(R.string.ArchvioSchedule);
        String optionActivity = getResources().getString(R.string.OptionSchedule);

        Context context = getApplicationContext();

        LinearLayout startWorkoutBtn = findViewById(R.id.startWorkoutBtn);
        LinearLayout createScheduleBtn = findViewById(R.id.createScheduleBtn);
        LinearLayout archivioBtn = findViewById(R.id.archivioBtn);
        LinearLayout personalAreaBtn = findViewById(R.id.personalArea);
        LinearLayout optionBtn = findViewById(R.id.optionBtn);

        TextView username = findViewById(R.id.userNameText);

        // init val
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        username.setText(sharedPreferences.getString(ExerciseConstants.PersonalData.USERNAME, "Username"));
        // init dell'immagine + fare metodo a aprte

        startWorkoutBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), ArchivioActivity.class);
            startActivity(intent);
        });

        createScheduleBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), ScheduleCreatorActivity.class);
            intent.putExtra("modifica", false);
            startActivity(intent);
        });

        archivioBtn.setOnClickListener(v -> {

            Toast toast = Toast.makeText(context, archivioActivity, duration);
            toast.show();

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