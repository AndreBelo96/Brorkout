package com.andrea.belotti.brorkout.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.model.Scheda;

import java.util.List;


public class EndScheduleSummaryFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    private Scheda scheda;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(TAG, ExerciseConstants.TAG_START_FRAGMENT);

        View view = inflater.inflate(R.layout.fragment_end_schedule_summary, container, false);

        scheda = (Scheda) getArguments().get("scheda");
        Integer giorno = (Integer) getArguments().get("giorno");

        if (scheda != null && giorno != null) {
            createTableOfExercises(scheda.getGiornate().get(giorno - 1).getEsercizi(), view);
        }

        return view;
    }

    private void createTableOfExercises(List<Esercizio> exeList, View view) {
        // Layout

    }

}
