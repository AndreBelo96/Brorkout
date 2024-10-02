package com.andrea.belotti.brorkout.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.andrea.belotti.brorkout.R;

import androidx.appcompat.app.AppCompatActivity;

public class ScheduleArchiveActivity extends AppCompatActivity {
    TextView pathArchive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(this.getClass().getSimpleName(), "Starting activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_archive);

        ImageButton backButton = findViewById(R.id.backButton);

        pathArchive = findViewById(R.id.pathArchive);

        backButton.setOnClickListener(v -> {
            Log.i(this.getClass().getSimpleName(), "Home button");
            Intent intent = new Intent(getBaseContext(), StartingMenuActivity.class);
            startActivity(intent);
        });

    }

    public void setPath(String path) {
        pathArchive.setText(path);
    }

    public String getPath() {
        return pathArchive.getText().toString();
    }

}