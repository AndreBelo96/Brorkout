package com.andrea.belotti.brorkout.plans_archive.view;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.ExeType.INCREMENTALE;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.ExeType.PIRAMIDALE;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.ExeType.SERIE;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.ExeType.TENUTA;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MemorizeConstants.ESERCIZIO;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.model.EsercizioIncrementale;
import com.andrea.belotti.brorkout.model.EsercizioPiramidale;
import com.andrea.belotti.brorkout.model.EsercizioSerie;
import com.andrea.belotti.brorkout.model.EsercizioTenuta;
import com.andrea.belotti.brorkout.utils.constants.ExerciseConstants;

public class SingleExeFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();

    private TextView title;
    private TextView type;
    private TextView set;
    private TextView rep;
    private TextView rec;
    private TextView elastic;
    private TextView ballast;
    private TextView indications;
    private TextView notes;

    public static SingleExeFragment newInstance(Esercizio esercizio) {
        SingleExeFragment fragment = new SingleExeFragment();
        Bundle args = new Bundle();

        if (TENUTA.equals(esercizio.getTipoEsercizio())) {
            EsercizioTenuta exeToPass = (EsercizioTenuta) esercizio;
            args.putSerializable(ESERCIZIO, exeToPass);
        } else if (SERIE.equals(esercizio.getTipoEsercizio())) {
            EsercizioSerie exeToPass = (EsercizioSerie) esercizio;
            args.putSerializable(ESERCIZIO, exeToPass);
        } else if (INCREMENTALE.equals(esercizio.getTipoEsercizio())) {
            EsercizioIncrementale exeToPass = (EsercizioIncrementale) esercizio;
            args.putSerializable(ESERCIZIO, exeToPass);
        } else if (PIRAMIDALE.equals(esercizio.getTipoEsercizio())) {
            EsercizioPiramidale exeToPass = (EsercizioPiramidale) esercizio;
            args.putSerializable(ESERCIZIO, exeToPass);
        }

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i(tag, ExerciseConstants.TAG_START_FRAGMENT);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_single_exe, container, false);

        // Retrieve data
        if (getArguments() == null) {
            Log.i(tag, ExerciseConstants.ERROR_ARGUMENT);
            return view;
        }

        Log.i(tag, ExerciseConstants.RETRIEVING_DATA);
        Esercizio esercizio = (Esercizio) getArguments().get(ESERCIZIO);
        Log.i(tag, ExerciseConstants.DATA_RETRIEVE);

        if (esercizio == null) {
            Log.e(tag, ExerciseConstants.DATA_ARGUMENT_NULL);
            return view;
        }

        initWidgets(view);
        setupWidgets(esercizio);

        return view;
    }

    private void initWidgets(View view) {
        title = view.findViewById(R.id.titoloText);
        type = view.findViewById(R.id.textType);
        set = view.findViewById(R.id.textSerie);
        rep = view.findViewById(R.id.textRipetizioni);
        rec = view.findViewById(R.id.textRecupero);
        elastic = view.findViewById(R.id.textElastico);
        ballast = view.findViewById(R.id.textZavorre);
        indications = view.findViewById(R.id.textIndicazioniCoach);
        notes = view.findViewById(R.id.textAppuntiAtleta);
    }

    private void setupWidgets(Esercizio esercizio) {

        // --------- Name ---------
        title.setText(esercizio.getName());

        // --------- Type ---------
        StringBuilder stringBuilderHelper =
                new StringBuilder("Tipo: ").append(esercizio.getTipoEsercizio());

        type.setText(stringBuilderHelper);

        // --------- Set ---------
        stringBuilderHelper.delete(0, stringBuilderHelper.length());
        stringBuilderHelper.append("Set: ")
                .append(esercizio.getSerieCompletate())
                .append("/")
                .append(esercizio.getSerie());

        set.setText(stringBuilderHelper);

        // --------- Repetitions ---------
        stringBuilderHelper.delete(0, stringBuilderHelper.length());
        stringBuilderHelper.append("Rep: ")
                .append(esercizio.getRipetizioni());

        rep.setText(stringBuilderHelper);

        // --------- Recover ---------
        stringBuilderHelper.delete(0, stringBuilderHelper.length());
        stringBuilderHelper.append("Rec: ")
                .append(esercizio.getRecupero());

        rec.setText(stringBuilderHelper);

        // --------- Elastic ---------
        stringBuilderHelper.delete(0, stringBuilderHelper.length());
        if (esercizio.getElastico() == null || esercizio.getElastico().isEmpty()) {
            stringBuilderHelper.append("Elastico: ")
                    .append("--");
        } else {
            stringBuilderHelper.append("Elastico: ")
                    .append(esercizio.getElastico());
        }

        elastic.setText(stringBuilderHelper);

        // --------- Ballast ---------
        stringBuilderHelper.delete(0, stringBuilderHelper.length());
        if (esercizio.getZavorre() == null || esercizio.getZavorre().isEmpty()) {
            stringBuilderHelper.append("Zavorra: ")
                    .append("--");
        } else {
            stringBuilderHelper.append("Zavorra: ")
                    .append(esercizio.getZavorre())
                    .append(" kg");
        }

        ballast.setText(stringBuilderHelper);

        // --------- Indications ---------
        stringBuilderHelper.delete(0, stringBuilderHelper.length());
        stringBuilderHelper.append("Indicazione coach: ")
                .append(esercizio.getIndicazioniCoach());

        indications.setText(stringBuilderHelper);

        // --------- Indications ---------
        stringBuilderHelper.delete(0, stringBuilderHelper.length());
        stringBuilderHelper.append("Appunti atleta: ")
                .append(esercizio.getAppuntiAtleta());

        notes.setText(stringBuilderHelper);
    }


}