package com.andrea.belotti.brorkout.plans_archive.view;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MemorizeConstants.ID_USER;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.model.Giornata;
import com.andrea.belotti.brorkout.plans_archive.ArchiveSingleton;
import com.andrea.belotti.brorkout.plans_archive.adapter.CalendarAdapter;
import com.andrea.belotti.brorkout.plans_archive.contract.PlansCalendarContract;
import com.andrea.belotti.brorkout.plans_archive.presenter.PlansCalendarPresenter;
import com.andrea.belotti.brorkout.utils.constants.ExerciseConstants;

public class PlansCalendarFragment extends Fragment implements CalendarAdapter.OnItemListener, PlansCalendarContract.View {

    private final String tag = this.getClass().getSimpleName();

    private TextView monthYearText;
    private TextView titleText;
    private RecyclerView calendarRecyclerView;
    private LinearLayout backButton;
    private LinearLayout previous;
    private LinearLayout next;

    public static PlansCalendarFragment newInstance(String idUser) {
        PlansCalendarFragment fragment = new PlansCalendarFragment();
        Bundle args = new Bundle();
        args.putString(ID_USER, idUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i(tag, ExerciseConstants.TAG_START_FRAGMENT);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plans_calendar, container, false);

        if (getArguments() == null) {
            Log.i(tag, ExerciseConstants.ERROR_ARGUMENT);
            return view;
        }

        Log.i(tag, ExerciseConstants.RETRIEVING_DATA);
        String idUser = getArguments().getString(ID_USER);
        Log.i(tag, ExerciseConstants.DATA_RETRIEVE);

        if (idUser == null || idUser.isEmpty()) {
            Log.e(tag, ExerciseConstants.DATA_ARGUMENT_NULL);
        }

        PlansCalendarPresenter presenter = new PlansCalendarPresenter(this, getContext());

        initWidgets(view);
        presenter.setMonthView(calendarRecyclerView, monthYearText, idUser);
        presenter.setAthleteName(titleText);

        previous.setOnClickListener(v -> presenter.previousMonthAction(calendarRecyclerView, monthYearText, idUser));
        next.setOnClickListener(v -> presenter.nextMonthAction(calendarRecyclerView, monthYearText, idUser));

        backButton.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerArchiveView, PlanUserFragment.newInstance());
            fragmentTransaction.commit();
        });

        return view;
    }

    private void initWidgets(View view) {
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        monthYearText = view.findViewById(R.id.monthYearTV);
        titleText = view.findViewById(R.id.titleTV);
        backButton = view.findViewById(R.id.back);
        previous = view.findViewById(R.id.previous);
        next = view.findViewById(R.id.next);
    }


    @Override
    public void onItemClick(Giornata day, String planName) {

        if (day == null) {
            Toast toast = Toast.makeText(getContext(), ExerciseConstants.ArchiveConstants.NO_EXE_DAY, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        ArchiveSingleton.getInstance().setChosenDay(day);
        ArchiveSingleton.getInstance().setPlanName(planName);

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerArchiveView, ExercisesFragment.newInstance(day));
        fragmentTransaction.commit();

    }
}