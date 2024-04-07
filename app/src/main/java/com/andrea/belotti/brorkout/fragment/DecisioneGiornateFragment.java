package com.andrea.belotti.brorkout.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.activity.StartingMenuActivity;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class DecisioneGiornateFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    private Boolean isCloud;
    private Boolean isPublic;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(TAG, ExerciseConstants.TAG_START_FRAGMENT);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_decisione_giornate, container, false);

        Button confirmDays = view.findViewById(R.id.confirm_button);
        EditText titoloScheda = view.findViewById(R.id.titoloScheda);
        Button backButton = view.findViewById(R.id.backButton);

        // Select days buttons
        final String[] giornata = {""};
        Button btn1 = view.findViewById(R.id.button1);
        Button btn2 = view.findViewById(R.id.button2);
        Button btn3 = view.findViewById(R.id.button3);
        Button btn4 = view.findViewById(R.id.button4);
        Button btn5 = view.findViewById(R.id.button5);
        Button btn6 = view.findViewById(R.id.button6);
        Button btn7 = view.findViewById(R.id.button7);

        List<Button> btnList = new ArrayList<>();
        btnList.add(btn1);
        btnList.add(btn2);
        btnList.add(btn3);
        btnList.add(btn4);
        btnList.add(btn5);
        btnList.add(btn6);
        btnList.add(btn7);

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

        btnList.forEach(b ->
                b.setOnClickListener(v -> {
                    giornata[0] = (String) b.getText();
                    setBasicColor(btnList);
                    b.setBackgroundColor(ExerciseConstants.Color.BUTTON_PRESSED_COLOR);
                }));

        confirmDays.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            ScheduleCreatorFragment scheduleCreatorFragment = new ScheduleCreatorFragment();
            String days = giornata[0];
            String nameSchedule = titoloScheda.getText().toString();
            Bundle bundle = new Bundle();
            bundle.putString("numeroGiornate", days);
            bundle.putString("titoloScheda", nameSchedule);
            scheduleCreatorFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragmentContainerViewScheduleCreator, scheduleCreatorFragment);
            fragmentTransaction.commit();
        });

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), StartingMenuActivity.class);
            startActivity(intent);
        });

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setBasicColor(List<Button> buttons) {
        buttons.forEach(b -> {
            b.setBackgroundColor(ExerciseConstants.Color.BUTTON_COLOR);
        });
    }

}