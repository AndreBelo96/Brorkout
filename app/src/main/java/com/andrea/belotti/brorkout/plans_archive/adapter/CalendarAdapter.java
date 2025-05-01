package com.andrea.belotti.brorkout.plans_archive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.model.CompleteState;
import com.andrea.belotti.brorkout.model.Giornata;
import com.andrea.belotti.brorkout.model.Scheda;
import com.andrea.belotti.brorkout.plans_archive.ArchiveSingleton;

import java.time.LocalDate;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {

    private final List<String> daysOfMonth;
    private final OnItemListener onItemListener;
    private final Context context;

    public CalendarAdapter(List<String> daysOfMonth, OnItemListener onItemListener, Context context) {
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
        this.context = context;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.16666666);
        return new CalendarViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        holder.dayOfMonth.setText(daysOfMonth.get(position));

        List<Scheda> plans = ArchiveSingleton.getInstance().getSelectedUserPlans();

        if  (plans == null || plans.isEmpty()) {
            return;
        }

        for (Scheda plan : plans) {
            for (Giornata day : plan.getGiornate()) {
                if (day.isUsed() && checkDate(LocalDate.parse(day.getUpdateDate()), position)) {
                    holder.eventLine.setBackgroundColor(getEventColor(day));
                    holder.eventLine.setVisibility(View.VISIBLE);
                    holder.setEventDay(day);
                    holder.setPlanName(plan.getNome());
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

    private boolean checkDate(LocalDate dayDate, int position) {
        LocalDate selectedDate = ArchiveSingleton.getInstance().getSelectedDate();
        return String.valueOf(dayDate.getDayOfMonth()).equals(daysOfMonth.get(position)) &&
                selectedDate.getMonth() == dayDate.getMonth() &&
                selectedDate.getYear() == dayDate.getYear();
    }

    private int getEventColor(Giornata day) {

        if (day.isDayComplete() == CompleteState.COMPLETE_OK) {
            return ContextCompat.getColor(context, R.color.exe_ok);
        } else if (day.isDayComplete() == CompleteState.INCOMPLETE_KO) {
            return ContextCompat.getColor(context, R.color.exe_ko);
        } else {
            return ContextCompat.getColor(context, R.color.exe_partial_ko);
        }
    }

    public interface OnItemListener {
        void onItemClick(Giornata eventDay, String planName);
    }
}
