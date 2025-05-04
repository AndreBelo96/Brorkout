package com.andrea.belotti.brorkout.plans_execution.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.utils.AppMethodsUtils;

import java.util.List;

public class EndScheduleRecapAdapter extends RecyclerView.Adapter<EndScheduleRecapAdapter.ViewHolder> {

    private final List<Esercizio> esercizi;
    Context context;

    public EndScheduleRecapAdapter(Context context, List<Esercizio> esercizi) {
        this.context = context;
        this.esercizi = esercizi;
    }

    @NonNull
    @Override
    public EndScheduleRecapAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_list_exercises, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @Override
    public void onBindViewHolder(@NonNull EndScheduleRecapAdapter.ViewHolder holder, int position) {


        holder.cardView.setCardBackgroundColor(AppMethodsUtils.setExeColor(esercizi.get(position).isExeComplete(), context));
        holder.exeName.setText(esercizi.get(position).getName());
        holder.exeSerie.setText(new StringBuilder("Set: ")
                .append(esercizi.get(position).getSerieCompletate())
                .append("/")
                .append(esercizi.get(position).getSerie()));
        holder.exeRipetizioni.setText(new StringBuilder("Rep: ")
                .append(esercizi.get(position).getRipetizioni()));
        holder.exeRecupero.setText(new StringBuilder("Rec: ")
                .append(esercizi.get(position).getRecupero())
                .append(" \""));

        holder.exeImg.setImageResource(AppMethodsUtils.setImageExe());

    }

    @Override
    public int getItemCount() {
        return esercizi.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView exeName;
        TextView exeSerie;
        TextView exeRipetizioni;
        TextView exeRecupero;
        ImageView exeImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            exeName = itemView.findViewById(R.id.textName_item);
            exeImg = itemView.findViewById(R.id.img_item);
            exeSerie = itemView.findViewById(R.id.textSerie_item);
            exeRipetizioni = itemView.findViewById(R.id.textRipetizioni_item);
            cardView = itemView.findViewById(R.id.cardId);
            exeRecupero = itemView.findViewById(R.id.textRecupero_item);
        }
    }


}
