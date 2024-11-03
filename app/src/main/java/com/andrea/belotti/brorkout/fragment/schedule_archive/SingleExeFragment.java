package com.andrea.belotti.brorkout.fragment.schedule_archive;

import static com.andrea.belotti.brorkout.constants.ExerciseConstants.ExeType.INCREMENTALE;
import static com.andrea.belotti.brorkout.constants.ExerciseConstants.ExeType.PIRAMIDALE;
import static com.andrea.belotti.brorkout.constants.ExerciseConstants.ExeType.SERIE;
import static com.andrea.belotti.brorkout.constants.ExerciseConstants.ExeType.TENUTA;
import static com.andrea.belotti.brorkout.constants.ExerciseConstants.MemorizeConstants.ESERCIZIO;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.activity.ScheduleArchiveActivity;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.model.EsercizioIncrementale;
import com.andrea.belotti.brorkout.model.EsercizioPiramidale;
import com.andrea.belotti.brorkout.model.EsercizioSerie;
import com.andrea.belotti.brorkout.model.EsercizioTenuta;

/**
 * A simple {@link Fragment} subclass.
 * The newInstance method create an instance of this fragment.
 */
public class SingleExeFragment extends Fragment {

    private ScheduleArchiveActivity activity;

    public static SingleExeFragment newInstance(Esercizio esercizio) {
        SingleExeFragment fragment = new SingleExeFragment();
        Bundle args = new Bundle();

        if (TENUTA.equals(esercizio.getTipoEsercizio())) {
            EsercizioTenuta esercizioToPass = (EsercizioTenuta) esercizio;
            args.putSerializable(ESERCIZIO, esercizioToPass);
        } else if (SERIE.equals(esercizio.getTipoEsercizio())) {
            EsercizioSerie esercizioToPass = (EsercizioSerie) esercizio;
            args.putSerializable(ESERCIZIO, esercizioToPass);
        } else if (INCREMENTALE.equals(esercizio.getTipoEsercizio())) {
            EsercizioIncrementale esercizioToPass = (EsercizioIncrementale) esercizio;
            args.putSerializable(ESERCIZIO, esercizioToPass);
        } else if (PIRAMIDALE.equals(esercizio.getTipoEsercizio())) {
            EsercizioPiramidale esercizioToPass = (EsercizioPiramidale) esercizio;
            args.putSerializable(ESERCIZIO, esercizioToPass);
        }

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_single_exe, container, false);

        activity = (ScheduleArchiveActivity) this.getActivity();

        // retrieve data
        Esercizio esercizio;
        if (getArguments() != null) {
            esercizio = (Esercizio) getArguments().get(ESERCIZIO);
        } else {
            return view;
        }


        TextView title = view.findViewById(R.id.titoloText);
        title.setText(esercizio.getName());

        TextView set = view.findViewById(R.id.textSerie);
        set.setText("Set: " + esercizio.getSerieCompletate() + "/" + esercizio.getSerie());

        TextView rep = view.findViewById(R.id.textRipetizioni);
        rep.setText("Rep: " + esercizio.getRipetizioni());

        TextView rec = view.findViewById(R.id.textRecupero);
        rec.setText("Rec: " + esercizio.getRecupero());

        TextView elastico = view.findViewById(R.id.textElastico);

        if (esercizio.getElastico() == null || esercizio.getElastico().isEmpty()) {
            elastico.setText("Elastico --");
        } else {
            elastico.setText(esercizio.getElastico());
        }

        TextView zavorra = view.findViewById(R.id.textZavorre);

        if (esercizio.getZavorre() == null || esercizio.getZavorre().isEmpty()) {
            zavorra.setText("Zavorra --");
        } else {
            zavorra.setText(esercizio.getZavorre());
        }

        TextView indicazioni = view.findViewById(R.id.textIndicazioniCoach);
        indicazioni.setText("Indicazione coach: " + esercizio.getIndicazioniCoach());

        TextView appunti = view.findViewById(R.id.textAppuntiAtleta);
        appunti.setText("Appunti atleta: " + esercizio.getAppuntiAtleta());

        // back button
        LinearLayout buttonBack = view.findViewById(R.id.back);

        buttonBack.setOnClickListener(v -> {

            String path = activity.getPath();
            String sub[] = path.split("/");

            activity.setPath(sub[0] + "/" + sub[1] + "/" + sub[2] + "/" + sub[3] + "/");

            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerArchiveView, ExercisesFragment.newInstance(activity.getDay()));
            fragmentTransaction.commit();

        });

        return view;
    }


}