package com.andrea.belotti.brorkout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.model.Giornata;
import com.andrea.belotti.brorkout.view.selection.SelectScheduleActivity;

import java.util.List;

public class DaySelectedAdapter extends RecyclerView.Adapter<DaySelectedAdapter.ViewHolder> {

    Context context;
    SelectScheduleActivity selectScheduleActivity;
    private List<Giornata> days;

    public DaySelectedAdapter(Context context, SelectScheduleActivity selectScheduleActivity) {
        this.context = context;
        this.selectScheduleActivity = selectScheduleActivity;
    }

    public void setDays(List<Giornata> days) {
        this.days = days;
    }

    @NonNull
    @Override
    public DaySelectedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_giornata_scelta, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DaySelectedAdapter.ViewHolder holder, int position) {

        holder.dayTitle.setText("Giornata " + (position + 1));

        StringBuilder exercises = new StringBuilder();

        for(Esercizio exe : days.get(position).getEsercizi()) {
            exercises.append(exe.getName()).append("\n");
        }

        if (exercises.toString().isEmpty()) {
            exercises.append("-----");
        }

        holder.exercises.setText(exercises.toString());

        holder.dayButton.setOnClickListener(v -> {
            selectScheduleActivity.selectDayToRun(position);
        });
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout dayButton;
        TextView dayTitle;
        TextView exercises;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dayButton = itemView.findViewById(R.id.day_button);
            dayTitle = itemView.findViewById(R.id.day_title);
            exercises = itemView.findViewById(R.id.exercise_list);

        }
    }
}
