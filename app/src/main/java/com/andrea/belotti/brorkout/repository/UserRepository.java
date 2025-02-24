package com.andrea.belotti.brorkout.repository;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.TableName.PLANS_TABLE;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.TableName.USERS_TABLE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.andrea.belotti.brorkout.entity.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class UserRepository {


    DatabaseReference userTableRef;

    // private static instance variable to hold the singleton instance
    private static volatile UserRepository INSTANCE = null;

    // private constructor to prevent instantiation of the class
    private UserRepository() {
        userTableRef =  FirebaseDatabase.getInstance().getReference(USERS_TABLE);
    }

    public static UserRepository getInstance() {
        // Check if the instance is already created
        if(INSTANCE == null) {
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

    public void getUsersByEmail(String email, ValueEventListener listener) {

        Query query = userTableRef.orderByChild("email").equalTo(email);

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

}
