package com.andrea.belotti.brorkout.fragment.collectdata;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;

import androidx.fragment.app.Fragment;

public class DataExePirFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(TAG, ExerciseConstants.TAG_START_FRAGMENT);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data_exe_pir, container, false);

        NumberPicker recuperoSeriePicker = view.findViewById(R.id.textRecoverSeries);
        recuperoSeriePicker.setMaxValue(ExerciseConstants.recoverList.length - 1);
        recuperoSeriePicker.setMinValue(0);
        recuperoSeriePicker.setValue(5);
        recuperoSeriePicker.setDisplayedValues(ExerciseConstants.recoverList);

        return view;
    }
}