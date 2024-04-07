package com.andrea.belotti.brorkout.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.andrea.belotti.brorkout.R;

public class StartingMenuActivity extends AppCompatActivity {

    // log
    private final String TAG = this.getClass().getSimpleName();

    private String startWorkoutActivity;
    private String scheduleCreatorActivity;
    private String personalAreaCreatorActivity;
    private String optionActivity;

    int duration = Toast.LENGTH_SHORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, "Starting activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_menu);

        startWorkoutActivity = getResources().getString(R.string.UseSchedule);
        scheduleCreatorActivity = getResources().getString(R.string.CreateSchedule);
        optionActivity = getResources().getString(R.string.OptionSchedule);
        personalAreaCreatorActivity = getResources().getString(R.string.PersonalArea);

        Context context = getApplicationContext();

        ImageButton startWorkoutBtn = findViewById(R.id.startWorkoutBtn);
        ImageButton createScheduleBtn = findViewById(R.id.createScheduleBtn);
        ImageButton personalAreaBtn = findViewById(R.id.personalArea);
        ImageButton optionBtn = findViewById(R.id.optionBtn);


        startWorkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ArchivioActivity.class);
                Toast toast = Toast.makeText(context, startWorkoutActivity, duration);
                toast.show();
                startActivity(intent);
            }
        });

        createScheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ScheduleCreatorActivity.class);
                Toast toast = Toast.makeText(context, scheduleCreatorActivity, duration);
                toast.show();
                intent.putExtra("modifica", false);
                startActivity(intent);
            }
        });

        personalAreaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), PersonalAreaActivity.class);
                Toast toast = Toast.makeText(context, personalAreaCreatorActivity, duration);
                toast.show();
                startActivity(intent);
            }
        });

        optionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast toast = Toast.makeText(context, optionActivity, duration);
                toast.show();

            }
        });

    }
}