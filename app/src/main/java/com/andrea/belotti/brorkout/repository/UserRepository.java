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

    public void getUsersByEmail(String email, ValueEventListener listener) {

        Query query = userTableRef.orderByChild("email").equalTo(email);

        query.addListenerForSingleValueEvent(listener);

    }

}
