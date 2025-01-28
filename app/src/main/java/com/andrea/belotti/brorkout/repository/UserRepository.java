package com.andrea.belotti.brorkout.repository;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.TableName.USERS_TABLE;

import androidx.annotation.NonNull;

import com.andrea.belotti.brorkout.entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.atomic.AtomicReference;

public class UserRepository {


    DatabaseReference userTableRef;

    public UserRepository() {
        userTableRef =  FirebaseDatabase.getInstance().getReference(USERS_TABLE);
    }

    public void saveUser(String id, User user) {
        userTableRef.child(id)
                .setValue(user);
    }

    public User getUsersByEmail(String email) {

        Query query = userTableRef.orderByChild("email").equalTo(email);

        // TODO https://stackoverflow.com/questions/30659569/wait-until-firebase-retrieves-data
        // Passa un listener che aggiorna la recycler view degli utenti, altrimenti dice nessun User
        // 1. SELECT * FROM Users where email = email
        AtomicReference<User> friend = new AtomicReference<>(new User());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    snapshot.getChildren().forEach(c -> {
                        friend.set(c.getValue(User.class));
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        return friend.get();

    }

}
