package com.andrea.belotti.brorkout.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.fragment.archivio.ManagerListFragment;
import com.andrea.belotti.brorkout.utils.ScheduleCreatingUtils;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class ArchivioActivity extends AppCompatActivity {

    // log
    private final String tag = this.getClass().getSimpleName();

    // Storing data into SharedPreferences
    private static SharedPreferences sharedPreferences;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(tag, "Starting activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archivio);

        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);



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

    public static void deleteData(String titoloScheda) { //TODO delete da db
        // Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        // Storing the key and its value as the data fetched from edittext
        myEdit.remove(titoloScheda);

        // we need to commit to apply those changes made,
        // otherwise, it will throw an error
        myEdit.commit();
    }




}