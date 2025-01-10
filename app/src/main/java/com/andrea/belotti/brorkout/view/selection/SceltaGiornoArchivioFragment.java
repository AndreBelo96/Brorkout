package com.andrea.belotti.brorkout.view.selection;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.adapter.DaySelectedAdapter;
import com.andrea.belotti.brorkout.view.creation.ScheduleCreatorActivity;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.model.Scheda;
import com.andrea.belotti.brorkout.utils.ScheduleCreatingUtils;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;
import static com.andrea.belotti.brorkout.constants.ExerciseConstants.MemorizeConstants.SCHEDA;


public class SceltaGiornoArchivioFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();

    // Storing data into SharedPreferences
    private static android.content.SharedPreferences sharedPreferences;

    private static Context context;
    private static final int duration = Toast.LENGTH_SHORT;
    private static final String SUCCESS_CREATING_STRING = "Scheda salvata con successo";


    public static SceltaGiornoArchivioFragment newInstance(Scheda scheda) {
        SceltaGiornoArchivioFragment fragment = new SceltaGiornoArchivioFragment();
        Bundle args = new Bundle();
        args.putSerializable(SCHEDA, scheda);
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(tag, ExerciseConstants.TAG_START_FRAGMENT);


        sharedPreferences = this.getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);

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
            fragmentTransaction.replace(R.id.fragmentContainerArchivioView, ManagerListFragment.newInstance(ScheduleCreatingUtils.createListaSchede(sharedPreferences)));
            fragmentTransaction.commit();
        });

        deletePlanBtn.setOnClickListener(v -> {
            SelectScheduleActivity.deleteData(finalSchedaScelta.getNome());

            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerArchivioView, ListaSchedeLocalArchivioFragment.newInstance(ScheduleCreatingUtils.createListaSchede(sharedPreferences)));
            fragmentTransaction.commit();
        });

        modifyPlanBtn.setOnClickListener(v -> {

            Bundle bundle = new Bundle();
            bundle.putSerializable("Scheda", finalSchedaScelta);

            Intent intent = new Intent(context, ScheduleCreatorActivity.class);
            Toast toast = Toast.makeText(context, "robe", duration);
            toast.show();
            intent.putExtra("modifica", true);
            intent.putExtra("SchedaDati", bundle);
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