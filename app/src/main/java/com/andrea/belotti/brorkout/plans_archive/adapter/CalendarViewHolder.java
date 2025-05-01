package com.andrea.belotti.brorkout.plans_archive.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.model.Giornata;

import lombok.Setter;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public final TextView dayOfMonth;
    public final View eventLine;
    @Setter
    private Giornata eventDay;
    @Setter
    private String planName;
    private final CalendarAdapter.OnItemListener onItemListener;

    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener) {
        super(itemView);
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        eventLine = itemView.findViewById(R.id.eventLine);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onItemListener.onItemClick(eventDay,planName);
    }

}
