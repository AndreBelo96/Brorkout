package com.andrea.belotti.brorkout.plans_execution.view;

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
import com.andrea.belotti.brorkout.plans_execution.adapter.EndScheduleRecapAdapter;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.model.Scheda;
import com.andrea.belotti.brorkout.utils.constants.ExerciseConstants;

import java.util.List;

public class EndScheduleSummaryFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();

    private RecyclerView recyclerView;
    private TextView titleView;

    public static EndScheduleSummaryFragment newInstance(Scheda scheda, Integer giorni) {
        EndScheduleSummaryFragment fragment = new EndScheduleSummaryFragment();
        Bundle args = new Bundle();
        args.putSerializable(ExerciseConstants.MemorizeConstants.SCHEDA, scheda);
        args.putInt(ExerciseConstants.MemorizeConstants.NUMERO_GIORNATE, giorni);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i(tag, ExerciseConstants.TAG_START_FRAGMENT);

        View view = inflater.inflate(R.layout.fragment_end_schedule_summary, container, false);

        if (getArguments() == null) {
            Log.i(tag, ExerciseConstants.ERROR_ARGUMENT);
            return view;
        }

        Scheda scheda = (Scheda) getArguments().get(ExerciseConstants.MemorizeConstants.SCHEDA);
        Integer giorno = (Integer) getArguments().get(ExerciseConstants.MemorizeConstants.NUMERO_GIORNATE);

        if (scheda == null || giorno == null) {
            Log.i(tag, ExerciseConstants.DATA_ARGUMENT_NULL);
            return view;
        }

        initWidgets(view);

        titleView.setText(new StringBuilder(scheda.getNome()).append(" giorno ").append(giorno));

        createTableOfExercises(scheda.getGiornate().get(giorno).getExercises());

        return view;
    }

    private void initWidgets(View view) {
        titleView = view.findViewById(R.id.textTitoloEGiornata);
        recyclerView = view.findViewById(R.id.summary_end_plan_view);
    }

    private void createTableOfExercises(List<Esercizio> exeList) {
        // set recyclerView info
        EndScheduleRecapAdapter adapter = new EndScheduleRecapAdapter(getContext(), exeList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }


}
