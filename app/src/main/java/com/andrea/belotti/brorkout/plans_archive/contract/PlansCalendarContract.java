package com.andrea.belotti.brorkout.plans_archive.contract;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.andrea.belotti.brorkout.plans_archive.adapter.CalendarAdapter;

public interface PlansCalendarContract {

    interface Presenter {
        void setMonthView(RecyclerView calendarRecyclerView, TextView monthYearText, String idUser);
        void previousMonthAction(RecyclerView calendarRecyclerView, TextView monthYearText, String idUser);
        void nextMonthAction(RecyclerView calendarRecyclerView, TextView monthYearText, String idUser);
    }

    interface View extends CalendarAdapter.OnItemListener {

    }

}
