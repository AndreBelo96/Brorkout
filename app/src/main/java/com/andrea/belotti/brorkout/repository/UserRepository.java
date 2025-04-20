package com.andrea.belotti.brorkout.repository;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.TableName.USERS_TABLE;

import androidx.annotation.NonNull;

import com.andrea.belotti.brorkout.GeneralSingleton;
import com.andrea.belotti.brorkout.entity.User;
import com.andrea.belotti.brorkout.utils.AppMethodsUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserRepository {


    // private static instance variable to hold the singleton instance
    private static volatile UserRepository INSTANCE = null;
    DatabaseReference userTableRef;

    // private constructor to prevent instantiation of the class
    private UserRepository() {
        userTableRef = FirebaseDatabase.getInstance().getReference(USERS_TABLE);
    }

    public static UserRepository getInstance() {
        // Check if the instance is already created
        if (INSTANCE == null) {
            // synchronize the block to ensure only one thread can execute at a time
            synchronized (UserRepository.class) {
                // check again if the instance is already created
                if (INSTANCE == null) {
                    // create the singleton instance
                    INSTANCE = new UserRepository();
                }
            }
        }
        // return the singleton instance
        return INSTANCE;
    }


    public void saveUser(String id, User user) {
        userTableRef.child(id)
                .setValue(user);
    }

    public void getUsersByEmail(String friendCode, ValueEventListener listener) {

        Query query = userTableRef.orderByChild("friendCode").equalTo(AppMethodsUtils.generate8CharString(friendCode));

        query.addListenerForSingleValueEvent(listener);

    }

    public void getById(String userId, ValueEventListener listener) {

        userTableRef.orderByChild("id").equalTo(userId).addListenerForSingleValueEvent(listener);

    }

    public void getAllByIdAthleteList(List<String> userIds, ValueEventListener listener) {

        for (String userId : userIds) {
            userTableRef.orderByChild("id").equalTo(userId).addListenerForSingleValueEvent(listener);
        }

    }

    public void getById(String userId) {

        Query query = userTableRef.orderByChild("id").equalTo(userId);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        GeneralSingleton.getInstance().setLoggedUser(dataSnapshot.getValue(User.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
