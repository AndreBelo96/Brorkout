package com.andrea.belotti.brorkout.plans_archive.adapter;

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
import com.andrea.belotti.brorkout.entity.Scheda;
import com.andrea.belotti.brorkout.plans_archive.ArchiveSingleton;
import com.andrea.belotti.brorkout.plans_archive.view.DaysFragment;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {

    public List<Scheda> plans;
    Context context;
    FragmentManager fragmentManager;

    public PlanAdapter(Context context, List<Scheda> plans, FragmentManager fragmentManager) {
        this.plans= plans;
        this.context = context;
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

        holder.planName.setText(plans.get(position).getNome());
        holder.dayText.setText("Giornate presenti: " + plans.get(position).getNumeroGiornate());

        holder.card.setOnClickListener(v -> {

            ArchiveSingleton.getInstance().setChosenPlan(plans.get(position));
            ArchiveSingleton.getInstance().setPath(ArchiveSingleton.getInstance().getPath() + "/" + plans.get(position).getNome());

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerArchiveView, DaysFragment.newInstance());
            fragmentTransaction.commit();
        });
    }

    @Override
    public int getItemCount() {
        return plans.size();
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
