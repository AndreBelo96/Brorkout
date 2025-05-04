package com.andrea.belotti.brorkout.plans_execution.view;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MemorizeConstants.GIORNATA;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MemorizeConstants.SCHEDA;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.app_starting_menu.view.StartingMenuActivity;
import com.andrea.belotti.brorkout.model.Scheda;
import com.andrea.belotti.brorkout.utils.constants.ExerciseConstants;

public class ExecutionScheduleActivity extends AppCompatActivity {

    private final String tag = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(tag, ExerciseConstants.TAG_START_ACTIVITY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execution_schedule);

        Bundle bundleInput = getIntent().getExtras();

        if (bundleInput == null) {
            Log.i(tag, ExerciseConstants.ERROR_BUNDLE);
            return;
        }

        if (bundleInput.getSerializable(SCHEDA) == null) {
            Log.i(tag, ExerciseConstants.DATA_ARGUMENT_NULL);
            return;
        }

        initActivity(bundleInput);

        ImageButton backButton = findViewById(R.id.buttonBack);

        backButton.setOnClickListener(v -> onBackButton());
    }

    private void initActivity(Bundle bundleInput) {
        Scheda planToExecute = (Scheda) bundleInput.getSerializable(SCHEDA);
        Integer dayToExecute = bundleInput.get(GIORNATA) != null ? bundleInput.getInt(GIORNATA) : 1;

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainerViewGestoreScheda, ExeExecutionFragment.newInstance(planToExecute, dayToExecute), "ExeExecutionFragment");
        fragmentTransaction.commit();
    }

    private void onBackButton() {
        Intent intent = new Intent(getBaseContext(), StartingMenuActivity.class);
        startActivity(intent);
    }
}