package com.andrea.belotti.brorkout.view.creation.collect_data;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.model.EsercizioTenuta;

import androidx.fragment.app.Fragment;

import static com.andrea.belotti.brorkout.constants.ExerciseConstants.MemorizeConstants.ESERCIZIO;

public class DataExeTenFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    public static DataExeTenFragment newInstance(EsercizioTenuta esercizio) {
        DataExeTenFragment fragment = new DataExeTenFragment();
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
        View view = inflater.inflate(R.layout.fragment_data_exe_ten, container, false);

        EsercizioTenuta esercizio = null;

        if (getArguments() != null) {
            esercizio = (EsercizioTenuta) getArguments().getSerializable(ESERCIZIO);
        }

        if (esercizio != null) {

            EditText serie = view.findViewById(R.id.textSerie);
            EditText exeTime = view.findViewById(R.id.textExecutionTime);
            EditText ripetizioni = view.findViewById(R.id.textRipetizioni);
            EditText recupero = view.findViewById(R.id.recoverText);

            serie.setText(esercizio.getSerie());
            exeTime.setText(esercizio.getTempoEsecuzione());
            ripetizioni.setText(esercizio.getRipetizioni());
            recupero.setText(esercizio.getRecupero());
        }

        return view;
    }
}