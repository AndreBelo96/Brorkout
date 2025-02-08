package com.andrea.belotti.brorkout.plans_selection.view;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MemorizeConstants.GIORNATA;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MemorizeConstants.SCHEDA;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.entity.Scheda;
import com.andrea.belotti.brorkout.app_starting_menu.view.StartingMenuActivity;
import com.andrea.belotti.brorkout.plans_execution.view.ExecutionScheduleActivity;

public class SelectScheduleActivity extends AppCompatActivity {

    // log
    private final String tag = this.getClass().getSimpleName();

    private Scheda selectedPlan;

    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(tag, "Starting activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_schedule);

        context = getApplicationContext();

        ImageButton backButton = findViewById(R.id.buttonBack);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), StartingMenuActivity.class);
            startActivity(intent);
        });
    }

    public static void deleteData(String titoloScheda) {

        //TODO delete da db

    }


    public void setFragmentContainer(Scheda plan) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        selectedPlan = plan;
        fragmentTransaction.replace(R.id.fragmentContainerPlanList, SceltaGiornoArchivioFragment.newInstance(plan));
        fragmentTransaction.commit();
    }

    public void selectDayToRun(int day) {

        if (selectedPlan.getGiornate().get(day).getExercises().isEmpty()) {
            Log.e(tag, "Esercizi vuoti");
            Toast toast = Toast.makeText(context, "Modificare la scheda e inserire almeno un esercizio nella giornata selezionata", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        Intent intent = new Intent(context, ExecutionScheduleActivity.class);
        intent.putExtra(SCHEDA, selectedPlan);
        intent.putExtra(GIORNATA, day+1);
        startActivity(intent);
    }


}