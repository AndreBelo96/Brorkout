package com.andrea.belotti.brorkout.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.plans_creation.view.PlanCreatorActivity;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.utils.ScheduleCreatingUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemEsercizioCreateAdapter extends RecyclerView.Adapter<ItemEsercizioCreateAdapter.ViewHolder>
        implements EserciziCreazioneCallback.ListaEserciziTouchHelperContract {

    Context context;
    PlanCreatorActivity activity;
    FragmentManager fragmentManager;
    private List<Esercizio> esercizi;
    private List<CardView> cardViewList = new ArrayList<>();

    public ItemEsercizioCreateAdapter(Context context, PlanCreatorActivity activity, FragmentManager fragmentManager) {
        this.context = context;
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }

    public void setEsercizi(List<Esercizio> esercizi) {
        this.esercizi = esercizi;
    }

    @NonNull
    @Override
    public ItemEsercizioCreateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_list_exercises, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemEsercizioCreateAdapter.ViewHolder holder, int position) {

        cardViewList.add(holder.cardView);

        holder.cardView.setBackgroundResource(R.drawable.blue_top_button);
        holder.exeName.setText(esercizi.get(position).getName());
        holder.exeSerie.setText("Set: " + esercizi.get(position).getSerieCompletate() + "/" + esercizi.get(position).getSerie());
        holder.exeRipetizioni.setText("Rep: " + esercizi.get(position).getRipetizioni());
        holder.exeRecupero.setText("Rec: " + esercizi.get(position).getRecupero() + " \"");

        holder.cardView.setOnClickListener(v -> {

            if (position == activity.getSelectedExe()) {
                activity.setSelectedExe(-1);
                holder.cardView.setBackgroundResource(R.drawable.blue_top_button);
            } else {
                ScheduleCreatingUtils.setCardViewBasicColor(cardViewList);
                activity.setSelectedExe(position);
                holder.cardView.setBackgroundResource(R.drawable.basic_button_pressed_bg);
            }
        });

    }

    @Override
    public int getItemCount() {
        return esercizi.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @Override
    public void onRowMove(int from, int to) {
        if (from < to) {
            for (int i = from; i < to; i++) {
                Collections.swap(esercizi, i, i + 1);
            }
        } else {
            for (int i = from; i > to; i--) {
                Collections.swap(esercizi, i, i - 1);
            }
        }
        notifyItemMoved(from, to);
    }

    @Override
    public void onRowSelected(ViewHolder myViewHolder) {
        myViewHolder.cardView.setBackgroundResource(R.drawable.basic_button_pressed_bg);
    }

    @Override
    public void onRowClear(ViewHolder myViewHolder) {
        myViewHolder.cardView.setBackgroundResource(R.drawable.blue_top_button);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView exeName;
        TextView exeSerie;
        TextView exeRipetizioni;
        TextView exeRecupero;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            exeName = itemView.findViewById(R.id.textName_item);
            exeSerie = itemView.findViewById(R.id.textSerie_item);
            exeRipetizioni = itemView.findViewById(R.id.textRipetizioni_item);
            cardView = itemView.findViewById(R.id.cardId);
            exeRecupero = itemView.findViewById(R.id.textRecupero_item);
        }
    }
}
