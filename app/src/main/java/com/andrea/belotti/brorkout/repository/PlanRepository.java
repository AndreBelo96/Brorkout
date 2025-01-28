package com.andrea.belotti.brorkout.repository;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.TableName.PLANS_TABLE;

import androidx.annotation.NonNull;

import com.andrea.belotti.brorkout.entity.Scheda;
import com.andrea.belotti.brorkout.entity.SchedaDTO;
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

    DatabaseReference plansDataRef;

    public PlanRepository() {
        plansDataRef = FirebaseDatabase.getInstance().getReference(PLANS_TABLE);
    }

    public void updatePlan(String id, Scheda plan) {
        plansDataRef.child(id)
                .setValue(plan);
    }

    public void insertPlan(SchedaDTO plan) {

        UUID uuid = UUID.randomUUID();

        plansDataRef.child(uuid.toString())
                .setValue(plan);
    }


    public List<Scheda> getByIdCreator(String idCreator) {

        Query query = plansDataRef.orderByChild("idCreator").equalTo(idCreator);

        List<Scheda> plans = new ArrayList<>();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                plans.clear();

                if (snapshot.exists()) {
                    snapshot.getChildren().forEach(c -> {

                        SchedaDTO schedaDTO = c.getValue(SchedaDTO.class);

                        Scheda scheda = null;
                        try {
                            scheda = new Scheda(schedaDTO);
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

    public List<Scheda> getByIdUser(String idUser) {

        Query query = plansDataRef.orderByChild("idUser").equalTo(idUser);

        List<Scheda> plans = new ArrayList<>();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                plans.clear();

                if (snapshot.exists()) {
                    snapshot.getChildren().forEach(c -> {

                        SchedaDTO schedaDTO = c.getValue(SchedaDTO.class);

                        Scheda scheda = null;
                        try {
                            scheda = new Scheda(schedaDTO);
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

}
