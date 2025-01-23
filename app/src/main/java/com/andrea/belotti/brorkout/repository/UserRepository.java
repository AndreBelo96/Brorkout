package com.andrea.belotti.brorkout.repository;

import static com.andrea.belotti.brorkout.constants.ExerciseConstants.TableName.USERS_TABLE;

import android.util.Log;

import androidx.annotation.NonNull;

import com.andrea.belotti.brorkout.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class UserRepository {


    DatabaseReference userTable;

    public UserRepository() {
        userTable =  FirebaseDatabase.getInstance().getReference(USERS_TABLE);
    }

    public void saveUser(String id, User user) {
        userTable.child(id)
                .setValue(user);
    }

    public List<User> getUsers() {
        userTable.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    HashMap<String, User>  utenti = (HashMap<String, User>) task.getResult().getValue();
                }
            }



        });
        return null;
    }

}
