package com.andrea.belotti.brorkout.plans_archive.presenter;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andrea.belotti.brorkout.model.Scheda;
import com.andrea.belotti.brorkout.model.SchedaEntity;
import com.andrea.belotti.brorkout.plans_archive.ArchiveSingleton;
import com.andrea.belotti.brorkout.plans_archive.adapter.CalendarAdapter;
import com.andrea.belotti.brorkout.plans_archive.contract.PlansCalendarContract;
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


public class PlansCalendarPresenter implements PlansCalendarContract.Presenter {

    private final PlansCalendarContract.View view;
    private final Context context;
    private LocalDate selectedDate;

    public PlansCalendarPresenter(PlansCalendarContract.View view, Context context) {
        this.view = view;
        this.context = context;
        this.selectedDate = LocalDate.now();
    }

    public void setMonthView(RecyclerView calendarRecyclerView, TextView monthYearText, String idUser) {
        monthYearText.setText(monthYearFromDate(selectedDate));
        List<String> daysInMonth = dayInMonthArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, view, context);
        ArchiveSingleton.getInstance().setSelectedDate(selectedDate);

        if (ArchiveSingleton.getInstance().getSelectedUserPlans().isEmpty()) {
            PlanRepository.getInstance().getAllByUserId(idUser, generateCalendarListener(calendarAdapter));
        }

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context.getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    public void previousMonthAction(RecyclerView calendarRecyclerView, TextView monthYearText, String idUser) {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView(calendarRecyclerView, monthYearText, idUser);
    }

    public void nextMonthAction(RecyclerView calendarRecyclerView, TextView monthYearText, String idUser) {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView(calendarRecyclerView, monthYearText, idUser);
    }

    @Override
    public void setAthleteName(TextView titleTV) {
        String user = ArchiveSingleton.getInstance().getSelectedUser().getUsername();
        titleTV.setText("Schede di " + user);
    }

    // ----- Private Methods -----

    private String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

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

    private ValueEventListener generateCalendarListener(CalendarAdapter calendarAdapter) {

        return new ValueEventListener() {
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
                        ArchiveSingleton.getInstance().getSelectedUserPlans().add(scheda);
                    }

                    calendarAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Non-compliant - method is empty
            }
        };

    }

}
