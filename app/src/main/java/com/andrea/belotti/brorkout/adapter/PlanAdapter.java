package com.andrea.belotti.brorkout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.activity.ScheduleArchiveActivity;
import com.andrea.belotti.brorkout.fragment.archive_plan.DaysFragment;
import com.andrea.belotti.brorkout.model.nodes.PlanCompletedNode;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {

    public PlanCompletedNode[] plans;
    Context context;
    ScheduleArchiveActivity activity;
    FragmentManager fragmentManager;

    public PlanAdapter(Context context, PlanCompletedNode[] plans, ScheduleArchiveActivity activity, FragmentManager fragmentManager) {
        this.context = context;
        this.plans = plans;
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public PlanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_list_plans, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanAdapter.ViewHolder holder, int position) {

        holder.planName.setText(plans[position].getName());
        holder.dayText.setText("Giornate presenti: " + plans[position].getPlan().getNumeroGiornate());

        holder.card.setOnClickListener(v -> {

            activity.setPath(activity.getPath() +  plans[position].getName() + "/");
            activity.setPlanCompletedNode(plans[position]);

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerArchiveView, DaysFragment.newInstance( plans[position]));
            fragmentTransaction.commit();
        });
    }

    @Override
    public int getItemCount() {
        return plans.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView planName;
        TextView dayText;
        CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            planName = itemView.findViewById(R.id.textName_item);
            dayText = itemView.findViewById(R.id.textDays_item);
            card = itemView.findViewById(R.id.cardId);

        }
    }
}
