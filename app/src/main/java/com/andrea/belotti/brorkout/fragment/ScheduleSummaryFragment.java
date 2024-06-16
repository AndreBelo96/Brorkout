package com.andrea.belotti.brorkout.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.activity.ExecutionScheduleActivity;
import com.andrea.belotti.brorkout.activity.StartingMenuActivity;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.model.Scheda;

import java.util.List;

import androidx.fragment.app.Fragment;

public class ScheduleSummaryFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    private Scheda scheda;

    LinearLayout.LayoutParams wrapParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1.0f
    );

    private LinearLayout layoutElencoEsecizi;
    private Context context;

    public static ScheduleSummaryFragment newInstance(Scheda scheda, Integer giorno) {
        ScheduleSummaryFragment fragment = new ScheduleSummaryFragment();
        Bundle args = new Bundle();
        args.putSerializable("scheda", scheda);
        args.putInt("giorno", giorno);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(TAG, ExerciseConstants.TAG_START_FRAGMENT);

        View view =  inflater.inflate(R.layout.fragment_schedule_summary, container, false);
        layoutElencoEsecizi = null;
        context = getContext();

        if (getArguments() == null) {
            return view;
        }

        scheda = (Scheda) getArguments().get("scheda");
        Integer giorno = (Integer) getArguments().get("giorno");

        if (scheda != null && giorno != null) {
            createTableOfExercises(scheda.getGiornate().get(giorno - 1).getEsercizi(), view);
        }

        TextView giornataNumero = view.findViewById(R.id.giornataNumero);
        giornataNumero.setText(ExerciseConstants.GIORNATA +  giorno);
        Button backButton = view.findViewById(R.id.backButton);
        Button prevDayButton = view.findViewById(R.id.previousDay);
        Button nextDayButton = view.findViewById(R.id.nextDay);
        Button startSchedule = view.findViewById(R.id.startScheduleButton);

        final Integer[] giornoMostrato = {giorno};

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), StartingMenuActivity.class);
            startActivity(intent);
        });

        prevDayButton.setOnClickListener(v -> {
            if ((giornoMostrato[0] - 1) > 0) {
                giornoMostrato[0]--;
                giornataNumero.setText(ExerciseConstants.GIORNATA +  giornoMostrato[0]);
                layoutElencoEsecizi.removeAllViews();
                createTableOfExercises(scheda.getGiornate().get(giornoMostrato[0] - 1).getEsercizi(), view);
            } else {
                Log.i(TAG, "Impossibile trovare un esercizio precedente");
            }
        });

        nextDayButton.setOnClickListener(v -> {
            if ((giornoMostrato[0] - 1) < scheda.getGiornate().size() - 1) {
                giornoMostrato[0]++;
                giornataNumero.setText(ExerciseConstants.GIORNATA +  giornoMostrato[0]);
                layoutElencoEsecizi.removeAllViews();
                createTableOfExercises(scheda.getGiornate().get(giornoMostrato[0] - 1).getEsercizi(), view);
            } else {
                Log.i(TAG, "Impossibile trovare un esercizio successivo");
            }
        });

        startSchedule.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ExecutionScheduleActivity.class);
            intent.putExtra("scheda", scheda);
            intent.putExtra("giornoScelto", 1); // TODO faccio partire il giorno visualizzato o iniziale?
            startActivity(intent);
        });

        return view;
    }


    private void createTableOfExercises(List<Esercizio> exeList, View view) {
        // Layout
        layoutElencoEsecizi = view.findViewById(R.id.listExerciseLayout);

        for(Esercizio esercizio : exeList) {

            LinearLayout layoutExercise = new LinearLayout(context);
            layoutExercise.setOrientation(LinearLayout.HORIZONTAL);

            // TextView
            TextView resumeExeTextView = new TextView(context);
            resumeExeTextView.setTextSize(15);
            resumeExeTextView.setText(esercizio.toStringResumeExe());
            resumeExeTextView.setTextColor(0xFF7887AB);

            // Space
            Space exercicesSpacePrev = new Space(context);
            exercicesSpacePrev.setMinimumHeight(10);

            Space exercicesSpaceBack = new Space(context);
            exercicesSpaceBack.setMinimumHeight(10);

            // Line
            View lineView = new View(context);
            lineView.setLayoutParams(wrapParams);
            lineView.setMinimumHeight(3);
            lineView.setBackgroundColor(0xFF7887AB);

            // Adding to the list the exercise
            layoutElencoEsecizi.addView(resumeExeTextView);
            layoutElencoEsecizi.addView(exercicesSpacePrev);
            layoutElencoEsecizi.addView(lineView);
            layoutElencoEsecizi.addView(exercicesSpaceBack);
        }

    }

}