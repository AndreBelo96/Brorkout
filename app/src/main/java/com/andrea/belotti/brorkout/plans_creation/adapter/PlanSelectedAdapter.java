package com.andrea.belotti.brorkout.plans_creation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.entity.Scheda;
import com.andrea.belotti.brorkout.plans_selection.view.SelectScheduleActivity;

import java.util.List;

public class PlanSelectedAdapter extends RecyclerView.Adapter<PlanSelectedAdapter.ViewHolder> {

    private List<Scheda> plans;
    Context context;
    SelectScheduleActivity selectScheduleActivity;

    public PlanSelectedAdapter(Context context, SelectScheduleActivity selectScheduleActivity) {
        this.context = context;
        this.selectScheduleActivity = selectScheduleActivity;
    }

    public void setPlans(List<Scheda> plans) {
        this.plans = plans;
    }

    @NonNull
    @Override
    public PlanSelectedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_plan_list_select, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanSelectedAdapter.ViewHolder holder, int position) {

        holder.planTitle.setText(plans.get(position).getNome());

        holder.card.setOnClickListener(v -> {
            selectScheduleActivity.setFragmentContainer(plans.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return plans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView planTitle;
        CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            planTitle = itemView.findViewById(R.id.text_title_item);
            card = itemView.findViewById(R.id.cardId);
        }
    }
}
