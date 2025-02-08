package com.andrea.belotti.brorkout.plans_execution.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.adapter.EndScheduleRecapAdapter;
import com.andrea.belotti.brorkout.entity.Scheda;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.utils.constants.ExerciseConstants;

import java.util.List;


public class EndScheduleSummaryFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();

    private Context context;

    public static EndScheduleSummaryFragment newInstance(Scheda scheda, Integer giorni) {
        EndScheduleSummaryFragment fragment = new EndScheduleSummaryFragment();
        Bundle args = new Bundle();
        args.putSerializable(ExerciseConstants.MemorizeConstants.SCHEDA, scheda);
        args.putInt(ExerciseConstants.MemorizeConstants.NUMERO_GIORNATE, giorni);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(tag, ExerciseConstants.TAG_START_FRAGMENT);

        View view = inflater.inflate(R.layout.fragment_end_schedule_summary, container, false);
        context = getContext();

        TextView textTitolo = view.findViewById(R.id.textTitoloEGiornata);

        Scheda scheda = null;
        Integer giorno = null;

        if (getArguments() != null) {
            scheda = (Scheda) getArguments().get(ExerciseConstants.MemorizeConstants.SCHEDA);
            giorno = (Integer) getArguments().get(ExerciseConstants.MemorizeConstants.NUMERO_GIORNATE);
        } else {
            Log.i(tag, ExerciseConstants.ERROR_ARGUMENT);
        }

        if (scheda != null && giorno != null) {
            createTableOfExercises(scheda.getGiornate().get(giorno).getExercises(), view);
            textTitolo.setText(scheda.getNome() + " giorno " + giorno);

        } else {
            Log.i(tag, ExerciseConstants.DATA_ARGUMENT_NULL);
        }

        return view;
    }

    private void createTableOfExercises(List<Esercizio> exeList, View view) {

        // set recyclerView info
        RecyclerView recyclerView = view.findViewById(R.id.summary_end_plan_view);
        EndScheduleRecapAdapter adapter = new EndScheduleRecapAdapter(context, exeList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }


}
