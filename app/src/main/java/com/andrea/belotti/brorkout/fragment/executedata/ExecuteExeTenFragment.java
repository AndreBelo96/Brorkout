package com.andrea.belotti.brorkout.fragment.executedata;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;

public class ExecuteExeTenFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, ExerciseConstants.TAG_START_FRAGMENT);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_execute_exe_ten, container, false);
    }
}