package com.andrea.belotti.brorkout.fragment.create_plan.collectdata;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.model.EsercizioPiramidale;

import androidx.fragment.app.Fragment;

import static com.andrea.belotti.brorkout.constants.ExerciseConstants.MemorizeConstants.ESERCIZIO;

public class DataExePirFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    public static DataExePirFragment newInstance(EsercizioPiramidale esercizio) {
        DataExePirFragment fragment = new DataExePirFragment();
        Bundle args = new Bundle();
        args.putSerializable(ESERCIZIO, esercizio);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(TAG, ExerciseConstants.TAG_START_FRAGMENT);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data_exe_pir, container, false);

        //EditText recuperoSeriePicker = view.findViewById(R.id.textRecoverSeries);
        /*recuperoSeriePicker.setMaxValue(ExerciseConstants.recoverList.length - 1);
        recuperoSeriePicker.setMinValue(0);
        recuperoSeriePicker.setValue(5);
        recuperoSeriePicker.setDisplayedValues(ExerciseConstants.recoverList);*/

        return view;
    }
}