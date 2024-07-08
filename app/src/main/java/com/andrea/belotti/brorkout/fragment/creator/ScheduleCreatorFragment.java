package com.andrea.belotti.brorkout.fragment.creator;

import android.content.Context;
import android.os.Bundle;
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
import com.andrea.belotti.brorkout.constants.StringOutputConstants;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.model.Giornata;
import com.andrea.belotti.brorkout.model.Scheda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class ScheduleCreatorFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();

    private static final Map<Integer,Giornata> giornateList = new HashMap<>();
    private static List<Esercizio> eserciziCopiaIncolla = new ArrayList<>();
    private final Scheda scheda = new Scheda();

    LinearLayout.LayoutParams wrapParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1.0f
    );

    public static ScheduleCreatorFragment newInstance(String scheduleTitle, String giorni) {
        ScheduleCreatorFragment fragment = new ScheduleCreatorFragment();
        Bundle args = new Bundle();
        args.putString(ExerciseConstants.MemorizeConstants.NUMERO_GIORNATE, giorni);
        args.putString(ExerciseConstants.MemorizeConstants.TITOLO_SCHEDA, scheduleTitle);
        fragment.setArguments(args);
        return fragment;
    }

    public static ScheduleCreatorFragment newInstance(Scheda scheda, String scheduleName) {
        ScheduleCreatorFragment fragment = new ScheduleCreatorFragment();
        Bundle args = new Bundle();
        args.putString(ExerciseConstants.MemorizeConstants.NUMERO_GIORNATE, scheda.getGiornate().size()+"");
        args.putString(ExerciseConstants.MemorizeConstants.TITOLO_SCHEDA, scheduleName);
        args.putSerializable(ExerciseConstants.MemorizeConstants.SCHEDA, scheda);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(tag, ExerciseConstants.TAG_START_FRAGMENT);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule_creator, container, false);

        Context context = getContext();
        int numeroGiornate = 0;
        String titoloScheda = "";
        Scheda datiScheda = null;

        if (getArguments() != null && getArguments().getString(ExerciseConstants.MemorizeConstants.NUMERO_GIORNATE) != null) {
            titoloScheda = getArguments().getString(ExerciseConstants.MemorizeConstants.TITOLO_SCHEDA);
            numeroGiornate = Integer.parseInt(getArguments().getString(ExerciseConstants.MemorizeConstants.NUMERO_GIORNATE));
            datiScheda = (Scheda) getArguments().get(ExerciseConstants.MemorizeConstants.SCHEDA);
        }

        scheda.setNome(titoloScheda);

        if (datiScheda != null) {
            for (int i = 1; i <= numeroGiornate; i++) {
                giornateList.put(i, new Giornata(datiScheda.getGiornate().get(i-1).getEsercizi()));
            }
        } else {
            giornateList.clear();
            List<Esercizio> esercizioList = new ArrayList<>();
            for (int i = 1; i <= numeroGiornate; i++) {
                giornateList.put(i, new Giornata(esercizioList));
            }
        }

        Button createSchedule = view.findViewById(R.id.create);

        createSchedule.setOnClickListener(v -> {

            //TODO refactor da fare non va bene cosi, devi fare una variabile unica che viene riempita o svuotata al premere dei tasti dentro CollectDataExeFragment

            List<Giornata> giornataList = new ArrayList<>();

            for (int i = 1; i<= ScheduleCreatorFragment.getGiornateList().size(); i++) {
                giornataList.add(ScheduleCreatorFragment.getGiornateList().get(i));
            }

            // se non ci sono esecizi in nessun giorno non creo la scheda
            if (giornataList.stream().allMatch(g -> g.getEsercizi().isEmpty() || g.getEsercizi() == null)) { //TODO pensa a come gestire se tutti i giorni o solo uno
                Log.e(tag, "Esercizi vuoti");
                Toast toast = Toast.makeText(context, "Insierire almeno un esercizio", StringOutputConstants.shortDuration);
                toast.show();
                return;
            }

            scheda.setGiornate(giornataList);

            //Salvataggio a DB e in locale
            ScheduleCreatorActivity.saveData(scheda);

            getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainerViewScheduleCreator,
                    ScheduleSummaryFragment.newInstance(scheda,1),"ScheduleSummaryFragment").commit();


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

            button.setOnClickListener(v -> {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                CollectDataExeFragment collectDataExeFragment = new CollectDataExeFragment();
                collectDataExeFragment.setNumeroGiornata(finalI);
                if (giornateList.get(finalI) != null) {
                    collectDataExeFragment.setGiornata(giornateList.get(finalI));
                }

                fragmentTransaction.replace(R.id.fragmentContainerViewSingolaGiornata, collectDataExeFragment);
                fragmentTransaction.commit();
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