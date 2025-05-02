package com.andrea.belotti.brorkout.plans_archive.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.app_starting_menu.view.StartingMenuActivity;
import com.andrea.belotti.brorkout.utils.constants.ExerciseConstants;

public class ScheduleArchiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(this.getClass().getSimpleName(), ExerciseConstants.TAG_START_ACTIVITY);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_archive);

        ImageButton backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> onBackClick());
    }

    private void onBackClick() {
        Intent intent = new Intent(getBaseContext(), StartingMenuActivity.class);
        startActivity(intent);
    }
}