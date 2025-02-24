package com.andrea.belotti.brorkout.plans_creation.presenter;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.andrea.belotti.brorkout.entity.User;
import com.andrea.belotti.brorkout.plans_creation.adapter.ShareFriendItemAdapter;
import com.andrea.belotti.brorkout.plans_creation.contract.CreationMenuContract;
import com.andrea.belotti.brorkout.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CreationMenuPresenter implements CreationMenuContract.Presenter {

    private final CreationMenuContract.View view;
    private final Context context;

    public CreationMenuPresenter(CreationMenuContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void onNewPlanClick() {

    }

    @Override
    public void onCopyPlanClick() {

    }

    @Override
    public ShareFriendItemAdapter retrieveUserListFromDB() {
        List<User> users = new ArrayList<>();
        FirebaseUser currentFireBaseUser = FirebaseAuth.getInstance().getCurrentUser();


        // CurrentUser listener
        ValueEventListener currentUserListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        User user = dataSnapshot.getValue(User.class);
                        users.add(user);
                    }

                    Toast toast = Toast.makeText(context, "Utente aggiunto", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(context, "Utente Insesistente", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        UserRepository.getInstance().getById(currentFireBaseUser.getUid(), currentUserListener);


        return new ShareFriendItemAdapter(context, users);
    }
}
