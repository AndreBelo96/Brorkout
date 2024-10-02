package com.andrea.belotti.brorkout.fragment.schedule_archive;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.model.Giornata;

import java.util.List;

import static com.andrea.belotti.brorkout.constants.ExerciseConstants.GIORNATA;
import static com.andrea.belotti.brorkout.utils.GenerateDrawableObjUtils.createBasicTextView;

public class ExercisesFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();
    private Context context;

    public static ExercisesFragment newInstance(Giornata day) {
        ExercisesFragment fragment = new ExercisesFragment();
        Bundle args = new Bundle();
        args.putSerializable(GIORNATA, day);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(tag, ExerciseConstants.TAG_START_FRAGMENT);

        context = getContext();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercises, container, false);

        // da prendere nel bundle
        Giornata day = new Giornata();

        if (getArguments() != null) {
            day = (Giornata) getArguments().get(GIORNATA);
        }

        LinearLayout exercisesLayout = view.findViewById(R.id.exercises);

        if (day != null) {
            initView(day, exercisesLayout);
        } else {
            initEmptyView(exercisesLayout);
        }

        return view;
    }

    private void initEmptyView(LinearLayout monthsLayout) {
        Log.e(tag, "Nessuna scheda completata");
        monthsLayout.addView(createBasicTextView(context, "Nessuna scheda completata"));
    }

    private void initView(Giornata day, LinearLayout exercisesLayout) {
        List<Esercizio> exercises = day.getEsercizi();
        setExeText(exercises, exercisesLayout);
    }

    private void setExeText(List<Esercizio> exercises, LinearLayout exercisesLayout) {

        for (Esercizio exercise : exercises) {

            // Create button
            TextView exeTextView = createBasicTextView(context, exercise.getNomeEsercizio());

            // Add button to layout
            exercisesLayout.addView(exeTextView);

        }
    }


}