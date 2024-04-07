package com.andrea.belotti.brorkout.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.fragment.DecisioneGiornateFragment;
import com.andrea.belotti.brorkout.fragment.ScheduleCreatorFragment;
import com.andrea.belotti.brorkout.model.Scheda;
import com.andrea.belotti.brorkout.utils.JsonGeneratorUtil;

public class ScheduleCreatorActivity extends AppCompatActivity {

    // log
    private final String TAG = this.getClass().getSimpleName();

    // Storing data into SharedPreferences
    private static SharedPreferences sharedPreferences;
    private static Context context;

    private static int duration = Toast.LENGTH_SHORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, "Starting activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_creator);

        Bundle datipassati = getIntent().getExtras().getBundle("SchedaDati");


        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        context = getApplicationContext();


        if (getIntent().getExtras().getBoolean("modifica")) {
            ScheduleCreatorFragment scheduleCreatorFragment = new ScheduleCreatorFragment();
            Scheda finalSchedaScelta = (Scheda) datipassati.getSerializable("Scheda");
            Bundle bundle = new Bundle();
            bundle.putString("numeroGiornate", String.valueOf(finalSchedaScelta.getGiornate().size()));
            bundle.putString("titoloScheda", finalSchedaScelta.getNome());
            bundle.putSerializable("Scheda", finalSchedaScelta);
            scheduleCreatorFragment.setArguments(bundle);

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerViewScheduleCreator, scheduleCreatorFragment);
            fragmentTransaction.commit();
        } else {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragmentContainerViewScheduleCreator, new DecisioneGiornateFragment());
            fragmentTransaction.commit();
        }

    }


    public static void saveData(Scheda scheda) {

        // Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        // Storing the key and its value as the data fetched from edittext
        myEdit.putString(scheda.getNome(), JsonGeneratorUtil.generateJsonFromSchedule(scheda));

        // Once the changes have been made,
        // we need to commit to apply those changes made,
        // otherwise, it will throw an error
        myEdit.commit();
    }


}