package com.andrea.belotti.brorkout.plans_selection.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.plans_selection.adapter.DaySelectedAdapter;
import com.andrea.belotti.brorkout.plans_creation.view.PlanCreatorActivityView;
import com.andrea.belotti.brorkout.utils.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.model.Scheda;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MainMenuConstants.INTENT_DATA_MODIFY_CREATOR;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MemorizeConstants.SCHEDA;


public class SceltaGiornoArchivioFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();

    private static Context context;

    public static SceltaGiornoArchivioFragment newInstance(Scheda scheda) {
        SceltaGiornoArchivioFragment fragment = new SceltaGiornoArchivioFragment();
        Bundle args = new Bundle();
        args.putSerializable(SCHEDA, scheda);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(tag, ExerciseConstants.TAG_START_FRAGMENT);


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scelta_giorno_archivio, container, false);

        context = getContext();
        Scheda schedaScelta = null;
        LinearLayout deletePlanBtn = view.findViewById(R.id.delete_plan_button);
        LinearLayout modifyPlanBtn = view.findViewById(R.id.modify_plan_button);
        LinearLayout backBtn = view.findViewById(R.id.back_button);

        SelectScheduleActivity activity = (SelectScheduleActivity) this.getActivity();

        if (getArguments() != null) {
            schedaScelta = (Scheda) getArguments().get(SCHEDA);
        }

        if (schedaScelta != null) {
            createView(schedaScelta, view, activity);
        } else {
            Log.e(tag, "Scheda vuota");
        }

        Scheda finalSchedaScelta = schedaScelta;

        backBtn.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerPlanList, new ManagerListFragment());
            fragmentTransaction.commit();
        });

        deletePlanBtn.setOnClickListener(v -> {
            SelectScheduleActivity.deleteData(finalSchedaScelta.getNome());

            // TODO probabilemnte devo salvare ID nella scheda
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerPlanList, new SelectSchedePersonaliFragment());
            fragmentTransaction.commit();
        });

        modifyPlanBtn.setOnClickListener(v -> {

            Intent intent = new Intent(context, PlanCreatorActivityView.class);
            Toast toast = Toast.makeText(context, "robe", Toast.LENGTH_SHORT);
            toast.show();
            intent.putExtra(INTENT_DATA_MODIFY_CREATOR, true);
            intent.putExtra(SCHEDA, finalSchedaScelta);
            startActivity(intent);
        });

        return view;
    }


    private void createView(Scheda scheda, View view, SelectScheduleActivity activity) {

        RecyclerView recyclerView = view.findViewById(R.id.scheduleView);
        DaySelectedAdapter adapter = new DaySelectedAdapter(context, activity);
        adapter.setDays(scheda.getGiornate());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

    }

}