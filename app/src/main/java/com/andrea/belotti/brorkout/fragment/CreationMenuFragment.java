package com.andrea.belotti.brorkout.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.activity.StartingMenuActivity;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.model.Scheda;
import com.andrea.belotti.brorkout.utils.ScheduleCreatingUtils;

import static android.content.Context.MODE_PRIVATE;

import java.util.List;

public class CreationMenuFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();

    private Boolean isCloud;
    private Boolean isPublic;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(tag, ExerciseConstants.TAG_START_FRAGMENT);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_creation_menu, container, false);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        List<Scheda> schedaList = ScheduleCreatingUtils.createListaSchede(sharedPreferences);

        Button newSchedule = view.findViewById(R.id.new_schedule_button);
        Button copyScheduleBtn = view.findViewById(R.id.copy_schedule_button);
        Button backButton = view.findViewById(R.id.backButton);

        // Db switches
        Switch switchDb = view.findViewById(R.id.switchDb);
        Switch switchPrivate = view.findViewById(R.id.switchPrivate);
        isCloud = switchDb.isChecked();
        isPublic = switchPrivate.isChecked();

        if (!isCloud) {
            switchPrivate.setVisibility(View.INVISIBLE);
        }

        switchDb.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                switchDb.setText(ExerciseConstants.DataBase.CLOUD);
                switchPrivate.setVisibility(View.VISIBLE);
            } else {
                switchDb.setText(ExerciseConstants.DataBase.LOCAL);
                switchPrivate.setVisibility(View.INVISIBLE);
            }

            isCloud = isChecked;
        });

        switchPrivate.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                switchPrivate.setText(ExerciseConstants.DataBase.PUBLIC);
            } else {
                switchPrivate.setText(ExerciseConstants.DataBase.PRIVATE);
            }

            isPublic = isChecked;
        });


        newSchedule.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            CreateNewScheduleFragment createNewScheduleFragment = new CreateNewScheduleFragment();
            fragmentTransaction.replace(R.id.fragmentContainerViewScheduleCreator, createNewScheduleFragment);
            fragmentTransaction.commit();
        });

        copyScheduleBtn.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerViewScheduleCreator, CopyScheduleFragment.newInstance(schedaList));
            fragmentTransaction.commit();
        });

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), StartingMenuActivity.class);
            startActivity(intent);
        });

        return view;
    }


}