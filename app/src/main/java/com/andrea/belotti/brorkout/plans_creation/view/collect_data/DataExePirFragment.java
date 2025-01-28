package com.andrea.belotti.brorkout.plans_creation.view.collect_data;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.utils.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.model.EsercizioPiramidale;

import androidx.fragment.app.Fragment;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MemorizeConstants.ESERCIZIO;

public class DataExePirFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    public static DataExePirFragment newInstance(EsercizioPiramidale esercizio) {
        DataExePirFragment fragment = new DataExePirFragment();
        Bundle args = new Bundle();
        args.putSerializable(ESERCIZIO, esercizio);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(TAG, ExerciseConstants.TAG_START_FRAGMENT);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data_exe_pir, container, false);

        EsercizioPiramidale esercizio = null;

        if (getArguments() != null) {
            esercizio = (EsercizioPiramidale) getArguments().getSerializable(ESERCIZIO);
        }

        if (esercizio != null) {

            EditText serie = view.findViewById(R.id.textSerie);
            EditText inizio = view.findViewById(R.id.repetitionStartText);
            EditText picco = view.findViewById(R.id.peakText);
            EditText recuperoSeriePicker = view.findViewById(R.id.textRecoverSeries);
            EditText recupero = view.findViewById(R.id.recoverText);

            serie.setText(esercizio.getSerie());
            inizio.setText(esercizio.getInizio());
            picco.setText(esercizio.getPicco());
            recuperoSeriePicker.setText(esercizio.getRecuperoSerie());
            recupero.setText(esercizio.getRecupero());
        }

        return view;
    }
}