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
import com.andrea.belotti.brorkout.activity.StartingMenuActivity;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.model.Scheda;

import java.util.List;

import androidx.fragment.app.Fragment;

import static com.andrea.belotti.brorkout.constants.ExerciseConstants.Color.EXE_KO_COLOR;
import static com.andrea.belotti.brorkout.constants.ExerciseConstants.Color.EXE_OK_COLOR;
import static com.andrea.belotti.brorkout.constants.ExerciseConstants.Color.TEXT_BUTTON_COLOR;
import static com.andrea.belotti.brorkout.constants.ExerciseConstants.Size.NORMAL_SIZE;


public class EndScheduleSummaryFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();

    private Context context;

    LinearLayout.LayoutParams wrapParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1.0f
    );

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
        Button buttonBack = view.findViewById(R.id.buttonBackToMainMenu);

        Scheda scheda = null;
        Integer giorno = null;

        if (getArguments() != null) {
            scheda = (Scheda) getArguments().get(ExerciseConstants.MemorizeConstants.SCHEDA);
            giorno = (Integer) getArguments().get(ExerciseConstants.MemorizeConstants.NUMERO_GIORNATE);
        } else {
            Log.i(tag, ExerciseConstants.ERROR_ARGUMENT);
        }

        if (scheda != null && giorno != null) {
            createTableOfExercises(scheda.getGiornate().get(giorno - 1).getEsercizi(), view);
            textTitolo.setText(scheda.getNome() + " giorno " + giorno);
        } else {
            Log.i(tag, ExerciseConstants.DATA_ARGUMENT_NULL);
        }

        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), StartingMenuActivity.class);
            startActivity(intent);
        });

        return view;
    }

    private void createTableOfExercises(List<Esercizio> exeList, View view) {
        // Layout
        LinearLayout layoutElencoEsecizi = view.findViewById(R.id.layoutSummaryEndSchedule);

        for(Esercizio esercizio : exeList) {

            LinearLayout layoutExercise = new LinearLayout(context);
            layoutExercise.setOrientation(LinearLayout.VERTICAL);

            // TextView
            TextView resumeExeTextView = new TextView(context);
            resumeExeTextView.setTextSize(NORMAL_SIZE);
            resumeExeTextView.setText(esercizio.toStringResumeEndSchedule());
            resumeExeTextView.setTextColor(TEXT_BUTTON_COLOR);

            // Adding to the list the exercise
            layoutExercise.addView(resumeExeTextView);

            if (esercizio.getSerieCompletate() >= Integer.parseInt(esercizio.getSerie())) {
                layoutExercise.setBackgroundColor(EXE_OK_COLOR);
            } else {
                layoutExercise.setBackgroundColor(EXE_KO_COLOR);
            }

            // Space
            Space exercicesSpacePrev = new Space(context);
            exercicesSpacePrev.setMinimumHeight(10);

            Space exercicesSpaceBack = new Space(context);
            exercicesSpaceBack.setMinimumHeight(10);

            // Line
            View lineView = new View(context);
            lineView.setLayoutParams(wrapParams);
            lineView.setMinimumHeight(3);
            lineView.setBackgroundColor(TEXT_BUTTON_COLOR);

            //Adding to the layout the exe
            layoutElencoEsecizi.addView(layoutExercise);
            layoutElencoEsecizi.addView(exercicesSpacePrev);
            layoutElencoEsecizi.addView(lineView);
            layoutElencoEsecizi.addView(exercicesSpaceBack);

        }
    }

}
