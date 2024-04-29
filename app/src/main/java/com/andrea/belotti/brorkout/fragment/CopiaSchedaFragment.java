package com.andrea.belotti.brorkout.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.model.Scheda;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class CopiaSchedaFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    private static Context context;

    private static int duration = Toast.LENGTH_SHORT;

    public static CopiaSchedaFragment newInstance(List<Scheda> scheduleList, String nameSchedule) {
        CopiaSchedaFragment fragment = new CopiaSchedaFragment();
        Bundle args = new Bundle();
        args.putSerializable("ListaSchede", (Serializable) scheduleList);
        args.putString("titoloScheda", nameSchedule);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(TAG, ExerciseConstants.TAG_START_FRAGMENT);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scelta_scheda_archivio, container, false);

        context = getContext();
        List<Scheda> schedaList = new ArrayList<>();
        String scheduleName = "";

        if (getArguments() != null) {
            schedaList = (List<Scheda>) getArguments().get("ListaSchede");
            scheduleName = (String) getArguments().get("titoloScheda");
        }

        if (schedaList != null && !schedaList.isEmpty()) {
            createView(schedaList, scheduleName, view);
        } else {
            Log.e(TAG, "Lista schede vuota");
        }

        return view;
    }

    private void createView(List<Scheda> schedaList, String scheduleName, View view) {
        LinearLayout scheduleLayout = view.findViewById(R.id.scheduleView);


        for (Scheda scheda : schedaList) {
            Button button = new Button(context);
            button.setText(scheda.getNome());
            scheduleLayout.addView(button);

            button.setOnClickListener(v -> {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerViewScheduleCreator,ScheduleCreatorFragment.newInstance(scheda, scheduleName));
                fragmentTransaction.commit();
            });

        }
    }




}