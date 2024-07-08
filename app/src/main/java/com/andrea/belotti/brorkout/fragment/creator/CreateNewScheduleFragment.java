package com.andrea.belotti.brorkout.fragment.creator;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.constants.StringOutputConstants;
import com.andrea.belotti.brorkout.fragment.creator.newinterfacecreator.CreationPlanFragment;
import com.andrea.belotti.brorkout.utils.ScheduleCreatingUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import static com.andrea.belotti.brorkout.constants.ExerciseConstants.Color.BUTTON_PRESSED_COLOR;


public class CreateNewScheduleFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(tag, ExerciseConstants.TAG_START_FRAGMENT);
        Context context = getContext();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_new_schedule, container, false);

        Button confirmScheduleData = view.findViewById(R.id.confirm_button);
        EditText titoloScheda = view.findViewById(R.id.titoloScheda);
        Button backButton = view.findViewById(R.id.backButton);
        Button prova = view.findViewById(R.id.prova_button);

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


        btnList.forEach(b ->
                b.setOnClickListener(v -> {
                    giornata[0] = (String) b.getText();
                    ScheduleCreatingUtils.setBasicColor(btnList, context);
                    b.setTextColor(BUTTON_PRESSED_COLOR);
                    b.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorlist)); //blue_700
                }));

        confirmScheduleData.setOnClickListener(v -> {

            String days = giornata[0];
            String scheduleName = titoloScheda.getText().toString();

            if (scheduleName.isEmpty()) {
                Log.e(tag, "Titolo scheda vuoto");
                Toast toast = Toast.makeText(context, "Titolo scheda vuoto", StringOutputConstants.shortDuration);
                toast.show();
                return;
            }
            if (days.isEmpty()) {
                Log.e(tag, "Numero di giorni non selezionato");
                Toast toast = Toast.makeText(context, "Numero di giorni non selezionato", StringOutputConstants.shortDuration);
                toast.show();
                return;
            }

            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerViewScheduleCreator, ScheduleCreatorFragment.newInstance(scheduleName, days));
            fragmentTransaction.commit();

        });


        prova.setOnClickListener(v -> {

            String days = giornata[0];
            String scheduleName = titoloScheda.getText().toString();

            if (scheduleName.isEmpty()) {
                Log.e(tag, "Titolo scheda vuoto");
                Toast toast = Toast.makeText(context, "Titolo scheda vuoto", StringOutputConstants.shortDuration);
                toast.show();
                return;
            }
            if (days.isEmpty()) {
                Log.e(tag, "Numero di giorni non selezionato");
                Toast toast = Toast.makeText(context, "Numero di giorni non selezionato", StringOutputConstants.shortDuration);
                toast.show();
                return;
            }

            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerViewScheduleCreator, CreationPlanFragment.newInstance(scheduleName, Integer.parseInt(days)));
            fragmentTransaction.commit();

        });

        backButton.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerViewScheduleCreator, new CreationMenuFragment());
            fragmentTransaction.commit();
        });

        return view;
    }


}