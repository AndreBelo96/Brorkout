package com.andrea.belotti.brorkout.adapter;

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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.plans_archive.view.ScheduleArchiveActivity;
import com.andrea.belotti.brorkout.plans_archive.view.TabSingleExeFragment;
import com.andrea.belotti.brorkout.utils.AppMethodsUtils;

import java.util.Arrays;

public class EsercizioAdapter extends RecyclerView.Adapter<EsercizioAdapter.ViewHolder> {

    public Esercizio[] esercizi;
    Context context;
    ScheduleArchiveActivity activity;
    FragmentManager fragmentManager;

    public EsercizioAdapter(Context context, Esercizio[] esercizi, ScheduleArchiveActivity activity, FragmentManager fragmentManager) {
        this.context = context;
        this.esercizi = esercizi;
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public EsercizioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_list_exercises, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @Override
    public void onBindViewHolder(@NonNull EsercizioAdapter.ViewHolder holder, int position) {

        holder.cardView.setCardBackgroundColor(AppMethodsUtils.setExeColor(esercizi[position].isExeComplete(), context));
        holder.exeName.setText(esercizi[position].getName());

        holder.exeSerie.setText(new StringBuilder("Set: ")
                .append(esercizi[position].getSerieCompletate())
                .append("/")
                .append(esercizi[position].getSerie()));
        holder.exeRipetizioni.setText(new StringBuilder("Rep: ")
                .append(esercizi[position].getRipetizioni()));
        holder.exeRecupero.setText(new StringBuilder("Rec: ")
                .append(esercizi[position].getRecupero())
                .append(" \""));

        holder.exeImg.setImageResource(AppMethodsUtils.setImageExe());
        holder.exeImg.setColorFilter(ContextCompat.getColor(context, R.color.blue_700), android.graphics.PorterDuff.Mode.MULTIPLY);

        holder.cardView.setOnClickListener(v -> {

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerArchiveView, TabSingleExeFragment.newInstance(Arrays.stream(esercizi).toList(), position));
            fragmentTransaction.commit();
        });

    }

    @Override
    public int getItemCount() {
        return esercizi.length;
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
