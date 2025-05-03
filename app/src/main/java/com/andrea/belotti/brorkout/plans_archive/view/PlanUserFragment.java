package com.andrea.belotti.brorkout.plans_archive.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andrea.belotti.brorkout.GeneralSingleton;
import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.model.Scheda;
import com.andrea.belotti.brorkout.model.SchedaEntity;
import com.andrea.belotti.brorkout.model.User;
import com.andrea.belotti.brorkout.plans_archive.ArchiveSingleton;
import com.andrea.belotti.brorkout.plans_archive.adapter.UserPlanAdapter;
import com.andrea.belotti.brorkout.repository.PlanRepository;
import com.andrea.belotti.brorkout.repository.UserRepository;
import com.andrea.belotti.brorkout.utils.constants.ExerciseConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class PlanUserFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();

    private LinearLayout myPlansBtn;
    private TextView athleteTitle;
    private RecyclerView athletePlansListView;

    public static PlanUserFragment newInstance() {
        return new PlanUserFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i(tag, ExerciseConstants.TAG_START_FRAGMENT);

        View view = inflater.inflate(R.layout.fragment_plan_user, container, false);

        initWidgets(view);

        List<User> athletes = new ArrayList<>();
        String coachId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        UserPlanAdapter adapter = new UserPlanAdapter(getContext(), athletes, getParentFragmentManager());
        setupWidgets(adapter);

        PlanRepository.getInstance().getAthletePlansByCoachId(coachId, createPlansAthleteListListener(adapter, athletes));

        myPlansBtn.setOnClickListener(v -> onBackCLick(coachId));

        return view;
    }

    private void initWidgets(View view) {
        myPlansBtn = view.findViewById(R.id.myPlansBtn);
        athleteTitle = view.findViewById(R.id.titleAthlete);
        athletePlansListView = view.findViewById(R.id.recyclerViewAthletePlans);
    }

    private void setupWidgets(UserPlanAdapter adapter) {
        athletePlansListView.setHasFixedSize(true);
        athletePlansListView.setLayoutManager(new LinearLayoutManager(getContext()));
        athletePlansListView.setAdapter(adapter);
    }

    private void onBackCLick(String userId) {
        ArchiveSingleton.getInstance().setChosenUserId(userId);
        ArchiveSingleton.getInstance().setSelectedUserPlans(new ArrayList<>());
        ArchiveSingleton.getInstance().setSelectedUser(GeneralSingleton.getInstance().getLoggedUser());

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerArchiveView, PlansCalendarFragment.newInstance(userId));
        fragmentTransaction.commit();
    }

    private ValueEventListener createPlansAthleteListListener(UserPlanAdapter adapter, List<User> athletes) {

        List<String> athleteIds = new ArrayList<>();

        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        SchedaEntity plan = dataSnapshot.getValue(SchedaEntity.class);

                        Scheda scheda;
                        try {
                            scheda = new Scheda(plan);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }

                        if (!scheda.getIdUser().equals(scheda.getIdCreator())) {
                            athleteTitle.setVisibility(View.VISIBLE);
                            athletePlansListView.setVisibility(View.VISIBLE);
                            athleteIds.add(scheda.getIdUser());
                            UserRepository.getInstance().getAllByIdAthleteList(athleteIds, createAthleteListListener(adapter, athletes));
                        }
                    }

                } else {
                    Toast toast = Toast.makeText(getContext(), "Prova", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Not used
            }
        };
    }

    private ValueEventListener createAthleteListListener(UserPlanAdapter adapter, List<User> athletes) {
        return new ValueEventListener() {
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
                    Toast toast = Toast.makeText(getContext(), "Utente Inesistente", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Not used
            }
        };
    }

}