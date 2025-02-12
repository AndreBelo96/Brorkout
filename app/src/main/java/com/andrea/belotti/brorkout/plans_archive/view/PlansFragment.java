package com.andrea.belotti.brorkout.plans_archive.view;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MemorizeConstants.ID_USER;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MemorizeConstants.NUMERO_GIORNATE;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MemorizeConstants.SCHEDA;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MemorizeConstants.TITOLO_SCHEDA;

import android.content.Context;
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

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.entity.Scheda;
import com.andrea.belotti.brorkout.entity.SchedaEntity;
import com.andrea.belotti.brorkout.plans_archive.adapter.PlanAdapter;
import com.andrea.belotti.brorkout.plans_archive.ArchiveSingleton;
import com.andrea.belotti.brorkout.repository.PlanRepository;
import com.andrea.belotti.brorkout.utils.constants.ExerciseConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PlansFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();
    PlanRepository planRepository;

    public static PlansFragment newInstance(String idUser) {
        PlansFragment fragment = new PlansFragment();
        Bundle args = new Bundle();
        args.putString(ID_USER, idUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(tag, ExerciseConstants.TAG_START_FRAGMENT);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plans, container, false);

        planRepository = new PlanRepository();

        // Retrieve data
        if (getArguments() == null) {
            return view;
        }

        String idUser = getArguments().getString(ID_USER);

        // Initialize data
        LinearLayout buttonBack = view.findViewById(R.id.back);
        RecyclerView recyclerView = view.findViewById(R.id.plans);
        TextView title = view.findViewById(R.id.titlePlan);

        List<Scheda> athletePlans = new ArrayList<>();

        PlanAdapter adapter = new PlanAdapter(getContext(), athletePlans, getParentFragmentManager());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        //Listener // TODO si può fare uno stampino per generarli? grazie me del futuro -> ne uso di molto simili, cambia solo il nome dell'adatper e della lista
        ValueEventListener athletePlansListener = new ValueEventListener() {
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
                        athletePlans.add(scheda);
                    }
                    adapter.notifyItemInserted(athletePlans.size());
                } else {
                    Toast toast = Toast.makeText(getContext(), "No schede", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        planRepository.getAllByUserId(idUser, athletePlansListener);

        buttonBack.setOnClickListener(v -> {
            ArchiveSingleton.getInstance().setPath("");

            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerArchiveView, PlanUserFragment.newInstance());
            fragmentTransaction.commit();
        });

        return view;
    }

}