package com.andrea.belotti.brorkout.repository;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.TableName.PLANS_TABLE;

import androidx.annotation.NonNull;

import com.andrea.belotti.brorkout.model.Scheda;
import com.andrea.belotti.brorkout.model.SchedaEntity;
import com.andrea.belotti.brorkout.model.User;
import com.andrea.belotti.brorkout.plans_creation.CreateSingleton;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class PlanRepository {

    DatabaseReference plansTableRef;

    // private static instance variable to hold the singleton instance
    private static volatile PlanRepository INSTANCE = null;

    // private constructor to prevent instantiation of the class
    private PlanRepository() {
        plansTableRef = FirebaseDatabase.getInstance().getReference(PLANS_TABLE);
    }

    public static PlanRepository getInstance() {
        // Check if the instance is already created
        if(INSTANCE == null) {
            // synchronize the block to ensure only one thread can execute at a time
            synchronized (PlanRepository.class) {
                // check again if the instance is already created
                if (INSTANCE == null) {
                    // create the singleton instance
                    INSTANCE = new PlanRepository();
                }
            }
        }
        // return the singleton instance
        return INSTANCE;
    }


    public void updatePlan(String id, SchedaEntity plan) {
        plansTableRef.child(id)
                .setValue(plan);
    }

    public void insertPlan(SchedaEntity plan) {

        plansTableRef.child(plan.getId())
                .setValue(plan);
    }

    public List<Scheda> getByIdCreator(String idCreator) {

        Query query = plansTableRef.orderByChild("idCreator").equalTo(idCreator);

        List<Scheda> plans = new ArrayList<>();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                plans.clear();

                if (snapshot.exists()) {
                    snapshot.getChildren().forEach(c -> {

                        SchedaEntity schedaEntity = c.getValue(SchedaEntity.class);

                        Scheda scheda = null;
                        try {
                            scheda = new Scheda(schedaEntity);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }

                        plans.add(scheda);

                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return plans;

    }

    public void getAllByUserId(String userId, ValueEventListener listener) {

        Query query = plansTableRef.orderByChild("idUser").equalTo(userId);

        query.addListenerForSingleValueEvent(listener);

    }

    public void getAthletePlansByCoachId(String coachId, ValueEventListener listener) {

        Query query = plansTableRef.orderByChild("idCreator").equalTo(coachId);

        query.addListenerForSingleValueEvent(listener);


    }

    public void saveData(Scheda scheda) {

        for (User user : CreateSingleton.getInstance().getUsersToShare()) {
            SchedaEntity dto = new SchedaEntity(scheda);
            dto.setIdUser(user.getId());

            // DATABASE
            INSTANCE.insertPlan(dto);
        }

    }

}
