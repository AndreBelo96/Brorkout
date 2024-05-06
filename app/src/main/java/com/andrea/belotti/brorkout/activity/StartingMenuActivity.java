package com.andrea.belotti.brorkout.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;
import com.andrea.belotti.brorkout.R;

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
        setContentView(R.layout.activity_starting_menu);

        String startWorkoutActivity = getResources().getString(R.string.UseSchedule);
        String scheduleCreatorActivity = getResources().getString(R.string.CreateSchedule);
        String optionActivity = getResources().getString(R.string.OptionSchedule);
        String personalAreaCreatorActivity = getResources().getString(R.string.PersonalArea);

        Context context = getApplicationContext();

        ImageButton startWorkoutBtn = findViewById(R.id.startWorkoutBtn);
        ImageButton createScheduleBtn = findViewById(R.id.createScheduleBtn);
        ImageButton personalAreaBtn = findViewById(R.id.personalArea);
        ImageButton optionBtn = findViewById(R.id.optionBtn);


        startWorkoutBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), ArchivioActivity.class);
            Toast toast = Toast.makeText(context, startWorkoutActivity, duration);
            toast.show();
            startActivity(intent);
        });

        createScheduleBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), ScheduleCreatorActivity.class);
            Toast toast = Toast.makeText(context, scheduleCreatorActivity, duration);
            toast.show();
            intent.putExtra("modifica", false);
            startActivity(intent);
        });

        personalAreaBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), PersonalAreaActivity.class);
            Toast toast = Toast.makeText(context, personalAreaCreatorActivity, duration);
            toast.show();
            startActivity(intent);
        });

        optionBtn.setOnClickListener(v -> {

            Toast toast = Toast.makeText(context, optionActivity, duration);
            toast.show();

        });

    }
}