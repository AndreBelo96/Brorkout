package com.andrea.belotti.brorkout.plans_archive.view;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MemorizeConstants.GIORNATA;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MemorizeConstants.SCHEDA;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.adapter.EsercizioAdapter;
import com.andrea.belotti.brorkout.plans_archive.ArchiveSingleton;
import com.andrea.belotti.brorkout.utils.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.entity.Giornata;

public class ExercisesFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();
    private Context context;
    private ScheduleArchiveActivity activity;

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

        activity = (ScheduleArchiveActivity) this.getActivity();

        // retrieve data
        if (getArguments() == null) {
            return view;
        }

        Giornata day = (Giornata) getArguments().get(GIORNATA);

        // set recyclerView info
        RecyclerView recyclerView = view.findViewById(R.id.exercises);
        EsercizioAdapter adapter = new EsercizioAdapter(context, day.getExercises().toArray(new Esercizio[0]), activity, getParentFragmentManager());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        // back button
        LinearLayout buttonBack = view.findViewById(R.id.back);

        buttonBack.setOnClickListener(v -> {

            String path = ArchiveSingleton.getInstance().getPath();
            String[] sub = path.split("/");

            ArchiveSingleton.getInstance().setPath(sub[0] + "/" + sub[1] + "/");

            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerArchiveView, DaysFragment.newInstance(ArchiveSingleton.getInstance().getChosenPlan()));
            fragmentTransaction.commit();

        });


        return view;
    }

}