package com.andrea.belotti.brorkout.view.selection;

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
import com.andrea.belotti.brorkout.constants.StringOutputConstants;
import com.andrea.belotti.brorkout.model.Giornata;
import com.andrea.belotti.brorkout.model.Scheda;
import com.andrea.belotti.brorkout.utils.ScheduleCreatingUtils;
import com.andrea.belotti.brorkout.view.StartingMenuActivity;
import com.andrea.belotti.brorkout.view.execution.ExecutionScheduleActivity;

public class SelectScheduleActivity extends AppCompatActivity {

    // Storing data into SharedPreferences
    private static SharedPreferences sharedPreferences;
    // log
    private final String tag = this.getClass().getSimpleName();

    private Scheda selectedPlan;

    Context context;

    public static void deleteData(String titoloScheda) { //TODO delete da db
        // Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        // Storing the key and its value as the data fetched from edittext
        myEdit.remove(titoloScheda);

        // we need to commit to apply those changes made,
        // otherwise, it will throw an error
        myEdit.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(tag, "Starting activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_schedule);

        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        context = getApplicationContext();

        //TODO questa classe viwnw inizializzata 2 volte, una di default una con questa e il newInstance vedi se è possibile farla una volta
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerArchivioView, ManagerListFragment.newInstance(ScheduleCreatingUtils.createListaSchede(sharedPreferences)));
        fragmentTransaction.commit();

        ImageButton backButton = findViewById(R.id.buttonBack);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), StartingMenuActivity.class);
            startActivity(intent);
        });
    }

    public void setFragmentContainer(Scheda plan) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        selectedPlan = plan;
        fragmentTransaction.replace(R.id.fragmentContainerArchivioView, SceltaGiornoArchivioFragment.newInstance(plan));
        fragmentTransaction.commit();
    }

    public void selectDayToRun(int day) {

        if (selectedPlan.getGiornate().get(day).getEsercizi().isEmpty()) {
            Log.e(tag, "Esercizi vuoti");
            Toast toast = Toast.makeText(context, "Modificare la scheda e inserire almeno un esercizio nella giornata selezionata", StringOutputConstants.shortDuration);
            toast.show();
            return;
        }

        Intent intent = new Intent(context, ExecutionScheduleActivity.class);
        intent.putExtra("scheda", selectedPlan);
        intent.putExtra("giorno", day+1);
        startActivity(intent);
    }


}