package com.andrea.belotti.brorkout.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.andrea.belotti.brorkout.R;

import androidx.appcompat.app.AppCompatActivity;

public class PersonalAreaActivity extends AppCompatActivity {

    //log
    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, "Starting activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_area);

        Button backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), StartingMenuActivity.class);
                startActivity(intent);
            }
        });
    }
}