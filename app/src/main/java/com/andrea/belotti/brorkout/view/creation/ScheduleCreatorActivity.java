package com.andrea.belotti.brorkout.view.creation;

import static com.andrea.belotti.brorkout.constants.ExerciseConstants.MemorizeConstants.SCHEDA;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.view.StartingMenuActivity;
import com.andrea.belotti.brorkout.fragment.create_plan.CreationMenuFragment;
import com.andrea.belotti.brorkout.fragment.create_plan.CreationPlanFragment;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.model.MetaData;
import com.andrea.belotti.brorkout.model.Scheda;
import com.andrea.belotti.brorkout.utils.JsonGeneratorUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;


public class ScheduleCreatorActivity extends AppCompatActivity {

    // Storing data into SharedPreferences
    private static SharedPreferences sharedPreferences;
    // log
    private final String tag = this.getClass().getSimpleName();
    // shared variables between fragments
    private Esercizio addExeInCreation;


    private int selectedExe = -1;

    @Getter
    @Setter
    private Scheda planToCreate;
    private boolean isLocal = true;
    private boolean isPrivate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(tag, "Starting activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_creator);

        Bundle inputData = getIntent().getExtras().getBundle("SchedaDati");
        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        if (getIntent().getExtras().getBoolean("modifica")) {

            Scheda schedaScelta = (Scheda) inputData.getSerializable("Scheda");

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerViewScheduleCreator, CreationPlanFragment.newInstance(schedaScelta.getNome(), schedaScelta.getGiornate().size(), schedaScelta));
            fragmentTransaction.commit();

        } else {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragmentContainerViewScheduleCreator, new CreationMenuFragment());
            fragmentTransaction.commit();
        }

        ImageView backButton = findViewById(R.id.buttonBack);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), StartingMenuActivity.class);
            startActivity(intent);
        });

    }

    public void saveData(Scheda scheda) {

        MetaData metaData = new MetaData(LocalDate.now(), LocalDate.now());
        if (!isLocal()) {
            // DATABASE
            //add Firebase Database stuff
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Schedules");
            myRef.child("user").child(scheda.getNome()).child("DATA").setValue(JsonGeneratorUtil.generateJsonFromObject(scheda));
            myRef.child("user").child(scheda.getNome()).child("METADATA").setValue(JsonGeneratorUtil.generateJsonFromObject(metaData));
        }

        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString(SCHEDA + "_" + scheda.getNome(), JsonGeneratorUtil.generateJsonFromObject(scheda));
        myEdit.apply();
    }

    public Esercizio getAddExeCreation() {
        return addExeInCreation;
    }

    public void setAddExeCreation(Esercizio addExeInCreation) {
        this.addExeInCreation = addExeInCreation;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public int getSelectedExe() {
        return selectedExe;
    }

    public void setSelectedExe(int selectedExe) {
        this.selectedExe = selectedExe;
    }
}