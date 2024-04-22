package com.andrea.belotti.brorkout.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.activity.ScheduleCreatorActivity;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.model.Giornata;
import com.andrea.belotti.brorkout.model.Scheda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ScheduleCreatorFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    private static Map<Integer,Giornata> giornateList = new HashMap<>();
    private static final String SUCCESS_CREATING_STRING = "Scheda salvata con successo";
    private static List<Esercizio> eserciziCopiaIncolla = new ArrayList<>();
    private Scheda scheda = new Scheda();

    int duration = Toast.LENGTH_SHORT;

    LinearLayout.LayoutParams wrapParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1.0f
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(TAG, ExerciseConstants.TAG_START_FRAGMENT);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule_creator, container, false);

        Context context = getContext();
        int numeroGiornate = 0;
        String titoloScheda = (String) getArguments().get("titoloScheda");
        scheda.setNome(titoloScheda);


        try {
            numeroGiornate = Integer.parseInt(getArguments().getString("numeroGiornate"));
        } catch (Exception e) {
            Toast toast = Toast.makeText(context, "Brutte robe", duration);
            toast.show();
        }

        Scheda datiScheda = (Scheda) getArguments().get("Scheda");

        if (datiScheda != null) {
            for (int i = 1; i <= numeroGiornate; i++) {
                giornateList.put(i, new Giornata(datiScheda.getGiornate().get(i-1).getEsercizi()));
            }
        } else {
            List<Esercizio> esercizioList = new ArrayList<>();
            for (int i = 1; i <= numeroGiornate; i++) {
                giornateList.put(i, new Giornata(esercizioList));
            }
        }


        Button createSchedule = view.findViewById(R.id.create);

        createSchedule.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                List<Giornata> giornataList = new ArrayList<>();
                for (int i = 1; i<= ScheduleCreatorFragment.getGiornateList().size(); i++) {
                    giornataList.add(ScheduleCreatorFragment.getGiornateList().get(i));
                }

                scheda.setGiornate(giornataList);
                ScheduleCreatorActivity.saveData(scheda);

                if (scheda != null) {
                    getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainerViewScheduleCreator,
                            ScheduleSummaryFragment.newInstance(scheda,1),"ScheduleSummaryFragment").commit();
                }

            }
        });

        createButtonList(numeroGiornate, view, context);

        return view;
    }


    private void createButtonList(int numButton, View view, Context context) {
        LinearLayout layoutElencoEsecizi = view.findViewById(R.id.daysLayout);
        wrapParams.setMargins(2, 0, 2, 0);
        for (int i = 1; i <= numButton; i++) {
            //gestione bottoni
            Button button = new Button(context);
            button.setText(i+"");
            //button.setLayoutParams(wrapParams); //TODO
            button.setTextSize(14);
            button.setBackgroundColor(ExerciseConstants.Color.BUTTON_COLOR);
            button.setTextColor(ExerciseConstants.Color.TEXT_BUTTON_COLOR);
            button.setLayoutParams(wrapParams);
            Integer finalI = i;

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    CollectDataExeFragment collectDataExeFragment = new CollectDataExeFragment();
                    collectDataExeFragment.setNumeroGiornata(finalI);
                    if (giornateList.get(finalI) != null) {
                        collectDataExeFragment.setGiornata(giornateList.get(finalI));
                    }

                    fragmentTransaction.replace(R.id.fragmentContainerViewSingolaGiornata, collectDataExeFragment);
                    fragmentTransaction.commit();
                }
            });

            layoutElencoEsecizi.addView(button);

        }

    }

    public static List<Esercizio> getEserciziCopiaIncolla() {
        return eserciziCopiaIncolla;
    }

    public static void setEserciziCopiaIncolla(List<Esercizio> eserciziCopiaIncolla) {
        ScheduleCreatorFragment.eserciziCopiaIncolla = eserciziCopiaIncolla;
    }

    public static void setGiornateList(Giornata giornata, Integer i) {
        giornateList.put(i, giornata);
    }
    public static Map<Integer, Giornata> getGiornateList() {
        return giornateList;
    }

}