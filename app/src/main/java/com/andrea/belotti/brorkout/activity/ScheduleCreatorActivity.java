package com.andrea.belotti.brorkout.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.fragment.CreationMenuFragment;
import com.andrea.belotti.brorkout.fragment.ScheduleCreatorFragment;
import com.andrea.belotti.brorkout.model.Scheda;
import com.andrea.belotti.brorkout.utils.JsonGeneratorUtil;

public class ScheduleCreatorActivity extends AppCompatActivity {

    // log
    private final String tag = this.getClass().getSimpleName();

    // Storing data into SharedPreferences
    private static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(tag, "Starting activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_creator);

        Bundle datipassati = getIntent().getExtras().getBundle("SchedaDati");
        
        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);


        if (getIntent().getExtras().getBoolean("modifica")) {
            ScheduleCreatorFragment scheduleCreatorFragment = new ScheduleCreatorFragment();
            Scheda finalSchedaScelta = (Scheda) datipassati.getSerializable("Scheda");
            Bundle bundle = new Bundle();
            bundle.putString("numeroGiornate", String.valueOf(finalSchedaScelta.getGiornate().size()));
            bundle.putString("titoloScheda", finalSchedaScelta.getNome());
            bundle.putSerializable("Scheda", finalSchedaScelta);
            scheduleCreatorFragment.setArguments(bundle);

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerViewScheduleCreator, scheduleCreatorFragment); //TODO da vedere se basta scheda
            fragmentTransaction.commit();
        } else {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragmentContainerViewScheduleCreator, new CreationMenuFragment());
            fragmentTransaction.commit();
        }

    }


    public static void saveData(Scheda scheda) {

        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString(scheda.getNome(), JsonGeneratorUtil.generateJsonFromSchedule(scheda));
        myEdit.commit();
    }


}