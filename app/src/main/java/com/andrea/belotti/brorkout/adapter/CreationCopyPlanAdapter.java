package com.andrea.belotti.brorkout.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.activity.ScheduleCreatorActivity;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.constants.StringOutputConstants;
import com.andrea.belotti.brorkout.fragment.schedule_creator.CreationMenuFragment;
import com.andrea.belotti.brorkout.model.Scheda;
import com.andrea.belotti.brorkout.utils.ScheduleCreatingUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreationCopyPlanAdapter extends RecyclerView.Adapter<CreationCopyPlanAdapter.ViewHolder> {

    private Scheda[] plans;
    private View view;
    private Context context;
    private CreationMenuFragment fragment;

    public List<CardView> getCardViewList() {
        return cardViewList;
    }

    private List<CardView> cardViewList = new ArrayList<>();

    public CreationCopyPlanAdapter(View view, Context context, Scheda[] plans, CreationMenuFragment fragment) {
        this.view = view;
        this.context = context;
        this.plans = plans;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public CreationCopyPlanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_copy_plan, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @Override
    public void onBindViewHolder(@NonNull CreationCopyPlanAdapter.ViewHolder holder, int position) {


        holder.planName.setText(plans[position].getNome());
        cardViewList.add(holder.cardView);

        holder.cardView.setOnClickListener(v -> {
            fragment.setSelectedPlan(plans[position]);
            ScheduleCreatingUtils.setCardViewBasicColor(cardViewList);
            holder.cardView.setBackgroundResource(R.drawable.basic_button_pressed_bg);
        });

        holder.planImg.setOnClickListener(v-> {
            fragment.setInfoPlan(plans[position], view, context);
        });

    }

    @Override
    public int getItemCount() {
        return plans.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView planName;
        ImageView planImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardId);
            planName = itemView.findViewById(R.id.text_name_plan);
            planImg = itemView.findViewById(R.id.img_more_info_plan);

            cardView.setBackgroundResource(R.drawable.blue_top_button);
        }
    }
}
