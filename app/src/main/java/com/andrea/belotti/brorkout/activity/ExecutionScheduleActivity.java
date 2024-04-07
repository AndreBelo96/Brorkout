package com.andrea.belotti.brorkout.activity;

import android.os.Bundle;
import android.util.Log;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.fragment.ExeExecutionFragment;
import com.andrea.belotti.brorkout.model.Scheda;

import androidx.appcompat.app.AppCompatActivity;

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

                getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainerViewGestoreScheda,
                        ExeExecutionFragment.newInstance(schedaOutPut, giornoOutPut), "ExeExecutionFragment").commit();
            }
        }
    }
}