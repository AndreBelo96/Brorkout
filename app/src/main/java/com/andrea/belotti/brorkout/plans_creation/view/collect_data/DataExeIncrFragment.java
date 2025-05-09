package com.andrea.belotti.brorkout.plans_creation.view.collect_data;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.utils.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.model.EsercizioIncrementale;

import androidx.fragment.app.Fragment;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MemorizeConstants.ESERCIZIO;

public class DataExeIncrFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    public static DataExeIncrFragment newInstance(EsercizioIncrementale esercizio) {
        DataExeIncrFragment fragment = new DataExeIncrFragment();
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
        View view = inflater.inflate(R.layout.fragment_data_exe_incr, container, false);

        EsercizioIncrementale esercizio = null;

        if (getArguments() != null) {
            esercizio = (EsercizioIncrementale) getArguments().getSerializable(ESERCIZIO);
        }

        if (esercizio != null) {
            EditText serie = view.findViewById(R.id.textSerie);
            EditText ripetizioniInizio = view.findViewById(R.id.repetitionStartText);
            EditText ripetizioniPicco = view.findViewById(R.id.peakText);
            EditText recupero = view.findViewById(R.id.recoverText);

            serie.setText(esercizio.getSerie());
            ripetizioniInizio.setText(esercizio.getInizio());
            ripetizioniPicco.setText(esercizio.getPicco());
            recupero.setText(esercizio.getRecupero());
        }

        return view;
    }
}