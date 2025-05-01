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

import com.andrea.belotti.brorkout.GeneralSingleton;
import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.model.User;
import com.andrea.belotti.brorkout.plans_archive.ArchiveSingleton;
import com.andrea.belotti.brorkout.plans_archive.view.PlansCalendarFragment;

import java.util.ArrayList;
import java.util.List;

public class UserPlanAdapter extends RecyclerView.Adapter<UserPlanAdapter.ViewHolder> {

    private List<User> users;
    private FragmentManager fragmentManager;
    Context context;

    public UserPlanAdapter(Context context, List<User> users, FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UserPlanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_list_user_plans, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserPlanAdapter.ViewHolder holder, int position) {

        String username = users.get(position).getUsername();

        holder.userName.setText("Schede di " + username);

        holder.card.setOnClickListener(v -> {

            String idUser = users.get(position).getId();

            ArchiveSingleton.getInstance().setChosenUserId(idUser);
            ArchiveSingleton.getInstance().setSelectedUserPlans(new ArrayList<>());
            ArchiveSingleton.getInstance().setSelectedUser(users.get(position));

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerArchiveView, PlansCalendarFragment.newInstance(idUser));
            fragmentTransaction.commit();
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.text_username_item);
            card = itemView.findViewById(R.id.cardId);

        }
    }
}
