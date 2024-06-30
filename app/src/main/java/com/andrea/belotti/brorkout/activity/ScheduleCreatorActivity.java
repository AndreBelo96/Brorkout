package com.andrea.belotti.brorkout.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.fragment.creator.ScheduleCreatorFragment;
import com.andrea.belotti.brorkout.fragment.creator.CreationMenuFragment;
import com.andrea.belotti.brorkout.model.MetaData;
import com.andrea.belotti.brorkout.model.Scheda;
import com.andrea.belotti.brorkout.utils.JsonGeneratorUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

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

        Bundle inputData = getIntent().getExtras().getBundle("SchedaDati");

        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);



        if (getIntent().getExtras().getBoolean("modifica")) {
            ScheduleCreatorFragment scheduleCreatorFragment = new ScheduleCreatorFragment();
            Scheda finalSchedaScelta = (Scheda) inputData.getSerializable("Scheda");
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

        ImageButton backButton = findViewById(R.id.buttonBack);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), StartingMenuActivity.class);
            startActivity(intent);
        });

    }


    public static void saveData(Scheda scheda) {

        MetaData metaData = new MetaData(LocalDate.now(), LocalDate.now());
        // DATABASE
        //add Firebase Database stuff
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Schedules");
        myRef.child("user").child(scheda.getNome()).child("DATA").setValue(JsonGeneratorUtil.generateJsonFromSchedule(scheda));
        myRef.child("user").child(scheda.getNome()).child("METADATA").setValue(JsonGeneratorUtil.generateJsonFromSchedule(metaData));

        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString(scheda.getNome(), JsonGeneratorUtil.generateJsonFromSchedule(scheda));
        myEdit.apply();
    }


}