package com.andrea.belotti.brorkout.view.selection;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.adapter.PlanSelectedAdapter;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.model.Scheda;
import com.andrea.belotti.brorkout.utils.JsonGeneratorUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListaSchedeOnlineArchivioFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(tag, ExerciseConstants.TAG_START_FRAGMENT);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schede_list, container, false);

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Schedules").child("user").getRef();
        SelectScheduleActivity activity = (SelectScheduleActivity) this.getActivity();

        context = getContext();
        List<Scheda> schedaOnlineList = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    snapshot.getChildren().forEach(c -> {
                        String title = c.getKey().toString();

                        try {
                            Scheda scheda = JsonGeneratorUtil.generateScheduleFromJson(snapshot.child(title).child("DATA").getValue().toString());
                            schedaOnlineList.add(scheda);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    });

                    //TODO mettere fuori se possibile
                    if (!schedaOnlineList.isEmpty()) {

                        // set recyclerView info
                        RecyclerView recyclerView = view.findViewById(R.id.scheduleListView);
                        PlanSelectedAdapter adapter = new PlanSelectedAdapter(context, getParentFragmentManager(), activity);
                        adapter.setPlans(schedaOnlineList);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView.setAdapter(adapter);

                    } else {
                        Log.e(tag, "Lista schede online vuota");
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }




}