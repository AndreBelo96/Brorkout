package com.andrea.belotti.brorkout.plans_archive.view;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MemorizeConstants.ID_USER;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.model.Giornata;
import com.andrea.belotti.brorkout.model.Scheda;
import com.andrea.belotti.brorkout.model.SchedaEntity;
import com.andrea.belotti.brorkout.plans_archive.ArchiveSingleton;
import com.andrea.belotti.brorkout.plans_archive.adapter.CalendarAdapter;
import com.andrea.belotti.brorkout.repository.PlanRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PlansCalendarFragment extends Fragment implements CalendarAdapter.OnItemListener {

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private String idUser;
    private LocalDate selectedDate; // TODO si può estrarre -> presenter?

    public static PlansCalendarFragment newInstance(String idUser) {
        PlansCalendarFragment fragment = new PlansCalendarFragment();
        Bundle args = new Bundle();
        args.putString(ID_USER, idUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plans_calendar, container, false);

        if (getArguments() == null) {
            return view;
        }

        idUser = getArguments().getString(ID_USER);

        initWidgets(view);
        selectedDate = LocalDate.now();
        setMonthView();

        LinearLayout previous = view.findViewById(R.id.previous);
        LinearLayout next = view.findViewById(R.id.next);
        previous.setOnClickListener(v -> previousMonthAction());
        next.setOnClickListener(v -> nextMonthAction());

        return view;
    }

    private void initWidgets(View view) {
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        monthYearText = view.findViewById(R.id.monthYearTV);
    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(selectedDate));
        List<String> daysInMonth = dayInMonthArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this, getContext());
        ArchiveSingleton.getInstance().setSelectedDate(selectedDate);

        if (ArchiveSingleton.getInstance().getUserSelectedPlans().isEmpty()) {

            ValueEventListener calendarListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            SchedaEntity plan = dataSnapshot.getValue(SchedaEntity.class);

                            Scheda scheda;
                            try {
                                scheda = new Scheda(plan);
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                            ArchiveSingleton.getInstance().getUserSelectedPlans().add(scheda);
                        }

                        calendarAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };

            PlanRepository.getInstance().getAllByUserId(idUser, calendarListener);
        }

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    // TODO si può estrarre -> presenter?
    private String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    // TODO si può estrarre -> presenter?
    private List<String> dayInMonthArray(LocalDate selectedDate) {

        List<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(selectedDate);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);

        int dayOfWeek = setItalianDayOfWeek(firstOfMonth.getDayOfWeek().getValue());

        for (int i = 1; i <= 42; i++) {

            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("");
            } else {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return daysInMonthArray;
    }

    public void previousMonthAction() {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction() {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    // TODO si può estrarre -> presenter?
    private int setItalianDayOfWeek(int value) {

        return switch (value) {
            case 0 -> 7;
            case 1 -> 0;
            case 2 -> 1;
            case 3 -> 2;
            case 4 -> 3;
            case 5 -> 4;
            case 6 -> 5;
            default -> -1;
        };

    }

    @Override
    public void onItemClick(Giornata day) {

        if (day == null) {
            Toast toast = Toast.makeText(getContext(), "Non sono presenti schede in questa giornata", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        ArchiveSingleton.getInstance().setChosenDay(day);

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerArchiveView, ExercisesFragment.newInstance(day));
        fragmentTransaction.commit();

    }
}