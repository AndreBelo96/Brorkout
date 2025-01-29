package com.andrea.belotti.brorkout.plans_creation.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.entity.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShareFriendItemAdapter extends RecyclerView.Adapter<ShareFriendItemAdapter.ViewHolder> {

    private final List<User> users;
    private final Context context;

    public ShareFriendItemAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public ShareFriendItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_info_share, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @Override
    public void onBindViewHolder(@NonNull ShareFriendItemAdapter.ViewHolder holder, int position) {

        holder.email.setText(users.get(position).getEmail());
        holder.username.setText(users.get(position).getUsername());

        // settaggio immagine se salvata

        holder.delete.setOnClickListener(v -> {
            users.remove(position);
            //this.notifyItemRemoved(position); -> non aggiorna la lista, capire
            this.notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView email;
        TextView username;
        CircleImageView planImg;
        ImageView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            email = itemView.findViewById(R.id.text_email_plan);
            username = itemView.findViewById(R.id.text_username_plan);
            planImg = itemView.findViewById(R.id.profile_image);
            delete = itemView.findViewById(R.id.delete_user_image);
        }
    }
}
