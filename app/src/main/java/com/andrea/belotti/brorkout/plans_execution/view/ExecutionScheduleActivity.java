package com.andrea.belotti.brorkout.plans_execution.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.app_starting_menu.view.StartingMenuActivity;
import com.andrea.belotti.brorkout.entity.Scheda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class ExecutionScheduleActivity extends AppCompatActivity {

    //log
    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, "Starting activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execution_schedule);

        Bundle bundleInput = getIntent().getExtras();

        if (bundleInput != null) {
            if (bundleInput.getSerializable("scheda") != null) {

                Scheda schedaOutPut = (Scheda) bundleInput.getSerializable("scheda");
                Integer giornoOutPut = bundleInput.get("giorno") != null ? (Integer) bundleInput.get("giorno") : 1;

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.fragmentContainerViewGestoreScheda, ExeExecutionFragment.newInstance(schedaOutPut, giornoOutPut), "ExeExecutionFragment");
                fragmentTransaction.commit();
            }
        }

        ImageButton backButton = findViewById(R.id.buttonBack);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), StartingMenuActivity.class);
            startActivity(intent);
        });
    }
}