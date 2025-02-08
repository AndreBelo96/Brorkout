package com.andrea.belotti.brorkout.repository;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.TableName.PLANS_TABLE;

import androidx.annotation.NonNull;

import com.andrea.belotti.brorkout.entity.Scheda;
import com.andrea.belotti.brorkout.entity.SchedaEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class PlanRepository {

    DatabaseReference plansTableRef;

    public PlanRepository() {
        plansTableRef = FirebaseDatabase.getInstance().getReference(PLANS_TABLE);
    }

    public void updatePlan(String id, Scheda plan) {
        plansTableRef.child(id)
                .setValue(plan);
    }

    public void insertPlan(SchedaEntity plan) {

        UUID uuid = UUID.randomUUID();

        plan.setId(uuid.toString());

        plansTableRef.child(uuid.toString())
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

}
