package com.andrea.belotti.brorkout.fragment.select_plan;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.activity.SelectScheduleActivity;
import com.andrea.belotti.brorkout.activity.ExecutionScheduleActivity;
import com.andrea.belotti.brorkout.activity.ScheduleCreatorActivity;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.constants.StringOutputConstants;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.model.Giornata;
import com.andrea.belotti.brorkout.model.Scheda;
import com.andrea.belotti.brorkout.utils.ScheduleCreatingUtils;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import static android.content.Context.MODE_PRIVATE;
import static com.andrea.belotti.brorkout.constants.ExerciseConstants.MemorizeConstants.SCHEDA;


public class SceltaGiornoArchivioFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();

    // Storing data into SharedPreferences
    private static android.content.SharedPreferences sharedPreferences;

    private static Context context;
    private static final int duration = Toast.LENGTH_SHORT;
    private static final String SUCCESS_CREATING_STRING = "Scheda salvata con successo";


    public static SceltaGiornoArchivioFragment newInstance(Scheda scheda) {
        SceltaGiornoArchivioFragment fragment = new SceltaGiornoArchivioFragment();
        Bundle args = new Bundle();
        args.putSerializable(SCHEDA, scheda);
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(tag, ExerciseConstants.TAG_START_FRAGMENT);


        sharedPreferences = this.getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scelta_giorno_archivio, container, false);

        context = getContext();
        Scheda schedaScelta = null;
        Button deleteSchedaBtn = view.findViewById(R.id.eliminaScheda);
        Button modifySchedaBtn = view.findViewById(R.id.modifySchedule);
        Button backSceltaSchede = view.findViewById(R.id.backSceltaSchede);

        if (getArguments() != null) {
            schedaScelta = (Scheda) getArguments().get(SCHEDA);
        }

        if (schedaScelta != null) {
            createView(schedaScelta, view);
        } else {
            Log.e(tag, "Scheda vuota");
        }

        Scheda finalSchedaScelta = schedaScelta;
        backSceltaSchede.setOnClickListener(v -> {

            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerArchivioView, ManagerListFragment.newInstance(ScheduleCreatingUtils.createListaSchede(sharedPreferences)));
            fragmentTransaction.commit();

        });

        deleteSchedaBtn.setOnClickListener(v -> {
            SelectScheduleActivity.deleteData(finalSchedaScelta.getNome());

            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerArchivioView, ListaSchedeLocalArchivioFragment.newInstance(ScheduleCreatingUtils.createListaSchede(sharedPreferences)));
            fragmentTransaction.commit();
        });

        modifySchedaBtn.setOnClickListener(v -> {

            Bundle bundle = new Bundle();
            bundle.putSerializable("Scheda", finalSchedaScelta);

            Intent intent = new Intent(context, ScheduleCreatorActivity.class);
            Toast toast = Toast.makeText(context, "robe", duration);
            toast.show();
            intent.putExtra("modifica", true);
            intent.putExtra("SchedaDati", bundle);
            startActivity(intent);
        });

        return view;
    }


    private void createView(Scheda scheda, View view) {
        LinearLayout scheduleLayout = view.findViewById(R.id.scheduleView);
        int count = 1;

        for (Giornata giornata : scheda.getGiornate()) {
            Button button = new Button(context);
            button.setText("Giornata " + count);
            scheduleLayout.addView(button);

            for (Esercizio esercizio : giornata.getEsercizi()) {
                TextView exerciseName = new TextView(context);
                exerciseName.setText(esercizio.getName());
                exerciseName.setTextColor(Color.rgb(3,169,244));
                scheduleLayout.addView(exerciseName);
            }

            int finalCount = count;
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if (scheda.getGiornate().get(finalCount-1).getEsercizi() == null ||
                            scheda.getGiornate().get(finalCount-1).getEsercizi().isEmpty()) {
                        Log.e(tag, "Esercizi vuoti");
                        Toast toast = Toast.makeText(context, "Modificare la scheda e inserire almeno un esercizio nella giornata selezionata", StringOutputConstants.shortDuration);
                        toast.show();
                        return;
                    }

                    Intent intent = new Intent(getContext(), ExecutionScheduleActivity.class);
                    intent.putExtra("scheda", scheda);
                    intent.putExtra("giorno", finalCount);
                    Toast toast = Toast.makeText(context, SUCCESS_CREATING_STRING, duration);
                    toast.show();
                    startActivity(intent);
                }
            });

            count++;
        }
    }

}