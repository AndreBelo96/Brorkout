package com.andrea.belotti.brorkout.fragment.schedule_creator.schedulecreator;

import android.content.Context;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.activity.ScheduleCreatorActivity;
import com.andrea.belotti.brorkout.model.Giornata;
import com.andrea.belotti.brorkout.utils.ScheduleCreatingUtils;

import static com.andrea.belotti.brorkout.constants.ExerciseConstants.Color.BUTTON_PRESSED_COLOR;
import static com.andrea.belotti.brorkout.constants.ExerciseConstants.Color.TEXT_BUTTON_COLOR;
import static com.andrea.belotti.brorkout.constants.ExerciseConstants.MemorizeConstants.GIORNATA;
import static com.andrea.belotti.brorkout.utils.AppMethodsUtils.getToggleButtonList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExeListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExeListFragment extends Fragment {


    Giornata giornata;
    LinearLayout layout;
    public static ExeListFragment newInstance(Giornata gioranta) {
        ExeListFragment fragment = new ExeListFragment();
        Bundle args = new Bundle();
        args.putSerializable(GIORNATA, gioranta);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exe_list, container, false);

        if (getArguments() != null) {
            giornata = (Giornata) getArguments().getSerializable(GIORNATA);
        }

        layout = view.findViewById(R.id.exeListLayout);

        if (giornata.getEsercizi() != null) {
            initView(giornata, layout, getContext());
        }

        return view;
    }

    private void initView(Giornata giornata, LinearLayout layout, Context context) {

        ScheduleCreatorActivity activity = (ScheduleCreatorActivity) this.getActivity();
        final int[] i = {0};

        giornata.getEsercizi().forEach(exe -> {

            String buttonText = exe.getNomeEsercizio() + "\nSerie: " + exe.getSerie() + "x" + exe.getRipetizioni() + "\nRecupero: " + exe.getRecupero();

            ToggleButton button = new ToggleButton(context);
            button.setId(i[0]);

            button.setChecked(false);

            //text
            button.setText(buttonText);
            button.setTextOff(buttonText);
            button.setTextOn(buttonText);
            button.setTextSize(15f);
            button.setTextColor(TEXT_BUTTON_COLOR);

            // button
            button.setBackground(ContextCompat.getDrawable(context,R.drawable.circled_button));
            button.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorbuttonbase));

            //margin
            //TODO da levare
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            button.setLayoutParams(params);

            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) button.getLayoutParams();
            marginLayoutParams.setMargins(0,20,0, 0);

            button.setOnClickListener(v -> {

                if (button.isChecked()) {
                    activity.setSelectedExe(button.getId());
                    button.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.buttonPressed));
                    button.setTextColor(BUTTON_PRESSED_COLOR);
                    ScheduleCreatingUtils.setUncheckedButton(getToggleButtonList(layout), context, button);
                } else {
                    activity.setSelectedExe(null);
                    button.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.buttonUnpressed));
                    button.setTextColor(TEXT_BUTTON_COLOR);
                }

            });

            i[0]++;
            layout.addView(button);
        });

    }

    public void resetButton() {
        int count = layout.getChildCount();

        for (int i = 0; i < count; i++) {

            ToggleButton button = (ToggleButton) layout.getChildAt(i);
            button.setChecked(false);
        }

    }


}