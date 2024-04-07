package com.andrea.belotti.brorkout.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.fragment.SceltaSchedaArchivioFragment;
import com.andrea.belotti.brorkout.model.Scheda;
import com.andrea.belotti.brorkout.utils.JsonGeneratorUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ArchivioActivity extends AppCompatActivity {

    // log
    private final String TAG = this.getClass().getSimpleName();

    // Storing data into SharedPreferences
    private static SharedPreferences sharedPreferences;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, "Starting activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archivio);

        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        Button backButton = findViewById(R.id.buttonBack);
        List<Scheda> schedaList = createListaSchede();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainerArchivioView, SceltaSchedaArchivioFragment.newInstance(schedaList));
        fragmentTransaction.commit();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), StartingMenuActivity.class);
                startActivity(intent);
            }
        });
    }

    private static Scheda createSchedule(String schedule) {
        Scheda scheda = new Scheda();
        try {
            scheda = JsonGeneratorUtil.generateScheduleFromJson(schedule);
        } catch (JsonProcessingException e) {
            Log.e("TAG", e.getMessage());
        }
        return scheda;
    }

    public static void deleteData(String titoloScheda) {
        // Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        // Storing the key and its value as the data fetched from edittext
        myEdit.remove(titoloScheda);

        // we need to commit to apply those changes made,
        // otherwise, it will throw an error
        myEdit.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<Scheda> createListaSchede() {
        List<Scheda> schedaList = new ArrayList<>();

        sharedPreferences.getAll().forEach((key, value) -> {
            String json = value.toString();
            schedaList.add(createSchedule(json));
        });

        return schedaList;
    }


}