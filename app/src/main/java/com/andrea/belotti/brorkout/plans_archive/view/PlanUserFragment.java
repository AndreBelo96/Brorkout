package com.andrea.belotti.brorkout.plans_archive.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.entity.Scheda;
import com.andrea.belotti.brorkout.entity.SchedaEntity;
import com.andrea.belotti.brorkout.entity.User;
import com.andrea.belotti.brorkout.plans_archive.ArchiveSingleton;
import com.andrea.belotti.brorkout.plans_archive.adapter.UserPlanAdapter;
import com.andrea.belotti.brorkout.repository.PlanRepository;
import com.andrea.belotti.brorkout.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class PlanUserFragment extends Fragment {

    UserRepository userRepository;

    public static PlanUserFragment newInstance() {

        return new PlanUserFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plan_user, container, false);

        LinearLayout myPlansBtn = view.findViewById(R.id.myPlansBtn);
        TextView athleteTitle = view.findViewById(R.id.titleAthlete);
        RecyclerView athletePlansListView = view.findViewById(R.id.recyclerViewAthletePlans);

        List<User> athletes = new ArrayList<>();
        List<String> athleteIds = new ArrayList<>();
        String coachId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        UserPlanAdapter adapter = new UserPlanAdapter(getContext(), athletes, getParentFragmentManager());
        athletePlansListView.setHasFixedSize(true);
        athletePlansListView.setLayoutManager(new LinearLayoutManager(getContext()));
        athletePlansListView.setAdapter(adapter);


        //TODO c'è sicuramente un modo migliore
        ValueEventListener athleteListListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        User user = dataSnapshot.getValue(User.class);
                        athletes.add(user);
                    }

                    adapter.notifyItemInserted(athletes.size());
                    Toast toast = Toast.makeText(getContext(), "Utente aggiunto", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getContext(), "Utente Insesistente", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        // plans listener
        ValueEventListener plansAthleteListListener = new ValueEventListener() {
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

                        if (!scheda.getIdUser().equals(scheda.getIdCreator())) {
                            athleteTitle.setVisibility(View.VISIBLE);
                            athletePlansListView.setVisibility(View.VISIBLE);
                            athleteIds.add(scheda.getIdUser());
                            userRepository.getAllByIdAthleteList(athleteIds, athleteListListener);
                        }
                    }

                } else {
                    Toast toast = Toast.makeText(getContext(), "Prova", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        PlanRepository.getInstance().getAthletePlansByCoachId(coachId, plansAthleteListListener);


        myPlansBtn.setOnClickListener(v -> {

            // TODO Mi serve il Singleton qui? -> Basterebbe passarlo nel newInstance
            ArchiveSingleton.getInstance().setChosenUserId(coachId);
            ArchiveSingleton.getInstance().setPath("mie");

            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerArchiveView, PlansFragment.newInstance(coachId));
            fragmentTransaction.commit();
        });







        return view;
    }
}