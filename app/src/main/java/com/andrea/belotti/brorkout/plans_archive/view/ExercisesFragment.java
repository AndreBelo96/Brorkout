package com.andrea.belotti.brorkout.plans_archive.view;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MemorizeConstants.GIORNATA;

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
import com.andrea.belotti.brorkout.utils.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.entity.Giornata;

public class ExercisesFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();
    private Context context;
    private ScheduleArchiveActivity activity;

    public static ExercisesFragment newInstance() {
        return new ExercisesFragment();
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
        Giornata day = new Giornata();
        if (getArguments() != null) {
            day = (Giornata) getArguments().get(GIORNATA);
        }

        // set recyclerView info
        RecyclerView recyclerView = view.findViewById(R.id.exercises);
        EsercizioAdapter adapter = new EsercizioAdapter(context, day.getExercises().toArray(new Esercizio[0]), activity, getParentFragmentManager());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        // back button
        LinearLayout buttonBack = view.findViewById(R.id.back);

        buttonBack.setOnClickListener(v -> {

            /*String path = activity.getPath();
            String sub[] = path.split("/");

            activity.setPath(sub[0] + "/" + sub[1] + "/" + sub[2] + "/");*/

            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerArchiveView, DaysFragment.newInstance());
            fragmentTransaction.commit();

        });


        return view;
    }

}