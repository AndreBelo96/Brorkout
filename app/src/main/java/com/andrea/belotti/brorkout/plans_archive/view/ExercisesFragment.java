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
import com.andrea.belotti.brorkout.plans_archive.contract.ExercisesContract;
import com.andrea.belotti.brorkout.plans_archive.presenter.ExercisesPresenter;
import com.andrea.belotti.brorkout.utils.constants.ExerciseConstants;

public class ExercisesFragment extends Fragment implements ExercisesContract.View {

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

        // Retrieve data
        if (getArguments() == null) {
            Log.i(tag, ExerciseConstants.ERROR_ARGUMENT);
            return view;
        }

        Log.i(tag, ExerciseConstants.RETRIEVING_DATA);
        Giornata day = (Giornata) getArguments().get(GIORNATA);
        Log.i(tag, ExerciseConstants.DATA_RETRIEVE);

        if (day == null) {
            Log.e(tag, ExerciseConstants.DATA_ARGUMENT_NULL);
            return view;
        }

        ExercisesPresenter presenter = new ExercisesPresenter();

        initWidgets(view);
        presenter.setTitle(titleView, day.getNumberOfDay());
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

    private void onClickBack() {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerArchiveView, PlansCalendarFragment.newInstance(ArchiveSingleton.getInstance().getChosenUserId()));
        fragmentTransaction.commit();
    }

}