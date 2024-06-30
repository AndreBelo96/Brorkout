package com.andrea.belotti.brorkout.fragment.creator;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.constants.StringOutputConstants;
import com.andrea.belotti.brorkout.model.Scheda;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class CopyScheduleFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();

    private Context context;

    public static CopyScheduleFragment newInstance(List<Scheda> scheduleList) {
        CopyScheduleFragment fragment = new CopyScheduleFragment();
        Bundle args = new Bundle();
        args.putSerializable("ListaSchede", (Serializable) scheduleList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(tag, ExerciseConstants.TAG_START_FRAGMENT);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_copy_schedule, container, false);
        Button backButton = view.findViewById(R.id.backButton);

        context = getContext();
        List<Scheda> schedaList = new ArrayList<>();
        EditText titoloScheda = view.findViewById(R.id.titoloScheda);

        if (getArguments() != null) {
            schedaList = (List<Scheda>) getArguments().get("ListaSchede");
        }

        if (schedaList != null && !schedaList.isEmpty()) {
            createView(schedaList, titoloScheda, view);
        } else {
            Log.e(tag, "Lista schede vuota");
        }

        backButton.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerViewScheduleCreator, new CreationMenuFragment());
            fragmentTransaction.commit();
        });

        return view;
    }

    private void createView(List<Scheda> schedaList, EditText titoloScheda, View view) {
        LinearLayout scheduleLayout = view.findViewById(R.id.scheduleView);


        for (Scheda scheda : schedaList) {
            Button button = new Button(context);
            button.setText(scheda.getNome());
            scheduleLayout.addView(button);

            button.setOnClickListener(v -> {
                String scheduleName = titoloScheda.getText().toString();

                if (scheduleName.isEmpty()) {
                    Log.e(tag, "Titolo scheda vuoto");
                    Toast toast = Toast.makeText(context, "Inserire un titolo alla scheda", StringOutputConstants.shortDuration);
                    toast.show();
                } else {
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerViewScheduleCreator, ScheduleCreatorFragment.newInstance(scheda, scheduleName));
                    fragmentTransaction.commit();
                }
            });

        }
    }




}