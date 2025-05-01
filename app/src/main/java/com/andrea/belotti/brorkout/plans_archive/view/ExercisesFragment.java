package com.andrea.belotti.brorkout.plans_archive.view;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MemorizeConstants.GIORNATA;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.adapter.EsercizioAdapter;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.model.Giornata;
import com.andrea.belotti.brorkout.plans_archive.ArchiveSingleton;
import com.andrea.belotti.brorkout.utils.constants.ExerciseConstants;

public class ExercisesFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();

    private RecyclerView recyclerView;
    private TextView titleView;
    private LinearLayout buttonBack;

    public static ExercisesFragment newInstance(Giornata day) {
        ExercisesFragment fragment = new ExercisesFragment();
        Bundle args = new Bundle();
        args.putSerializable(GIORNATA, day);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i(tag, ExerciseConstants.TAG_START_FRAGMENT);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercises, container, false);

        // Check data
        if (getArguments() == null) {
            return view;
        }

        initWidgets(view);

        Giornata day = (Giornata) getArguments().get(GIORNATA);
        setTitle(day.getNumberOfDay());
        ScheduleArchiveActivity activity = (ScheduleArchiveActivity) this.getActivity();

        EsercizioAdapter adapter = new EsercizioAdapter(getContext(), day.getExercises().toArray(new Esercizio[0]), activity, getParentFragmentManager());
        recyclerView.setAdapter(adapter);

        buttonBack.setOnClickListener(v -> onClickBack());

        return view;
    }

    private void initWidgets(View view) {
        titleView = view.findViewById(R.id.userPlanDayTV);
        recyclerView = view.findViewById(R.id.exercises);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        buttonBack = view.findViewById(R.id.back);
    }

    private void setTitle(int day) {

        StringBuilder exeTile = new StringBuilder("Scheda ")
                .append(ArchiveSingleton.getInstance().getPlanName())
                .append(" di ")
                .append(ArchiveSingleton.getInstance().getSelectedUser().getUsername())
                .append(" Giornata ")
                .append(day);

        titleView.setText(exeTile);
    }

    private void onClickBack() {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerArchiveView, PlansCalendarFragment.newInstance(ArchiveSingleton.getInstance().getChosenUserId()));
        fragmentTransaction.commit();
    }

}