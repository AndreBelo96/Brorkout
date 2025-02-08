package com.andrea.belotti.brorkout.plans_selection.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.plans_creation.adapter.PlanSelectedAdapter;
import com.andrea.belotti.brorkout.entity.SchedaEntity;
import com.andrea.belotti.brorkout.repository.PlanRepository;
import com.andrea.belotti.brorkout.utils.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.entity.Scheda;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SelectSchedePersonaliFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();
    private Context context;

    PlanRepository planRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(tag, ExerciseConstants.TAG_START_FRAGMENT);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schede_list, container, false);

        context = getContext();
        SelectScheduleActivity activity = (SelectScheduleActivity) this.getActivity();

        List<Scheda> myPlans = new ArrayList<>();

        // TODO singleton
        planRepository = new PlanRepository();

        // TODO fai listener, fai Contract e presenter

        RecyclerView recyclerView = view.findViewById(R.id.scheduleListView);
        PlanSelectedAdapter adapter = new PlanSelectedAdapter(context, activity);
        adapter.setPlans(myPlans);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        ValueEventListener myPlanListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        SchedaEntity plan = dataSnapshot.getValue(SchedaEntity.class);

                        Scheda scheda = null;
                        try {
                            scheda = new Scheda(plan);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }

                        myPlans.add(scheda);
                    }

                    adapter.notifyItemInserted(myPlans.size());
                } else {
                    // TODO scheda vuota?
                    Toast toast = Toast.makeText(getContext(), "No schede", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = currentUser.getUid();

        planRepository.getAllByUserId(userId, myPlanListener);

        return view;
    }

}