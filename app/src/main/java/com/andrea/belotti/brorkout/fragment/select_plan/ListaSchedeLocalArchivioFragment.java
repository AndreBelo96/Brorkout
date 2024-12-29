package com.andrea.belotti.brorkout.fragment.select_plan;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.model.Scheda;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ListaSchedeLocalArchivioFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();
    private Context context;



    public static ListaSchedeLocalArchivioFragment newInstance(List<Scheda> param1) {
        ListaSchedeLocalArchivioFragment fragment = new ListaSchedeLocalArchivioFragment();
        Bundle args = new Bundle();
        args.putSerializable("ListaSchede", (Serializable) param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(tag, ExerciseConstants.TAG_START_FRAGMENT);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schede_list, container, false);

        context = getContext();
        List<Scheda> schedaLocalList = new ArrayList<>();

        if (getArguments() != null) {
            schedaLocalList = (List<Scheda>) getArguments().get("ListaSchede");
        }

        if (schedaLocalList != null && !schedaLocalList.isEmpty()) {
            createView(schedaLocalList, view);
        } else {
            Log.e(tag, "Lista schede locali vuota");
        }

        return view;
    }

    private void createView(List<Scheda> schedaList, View view) {
        LinearLayout scheduleLayout;

        scheduleLayout = view.findViewById(R.id.scheduleListView);

        for (Scheda scheda : schedaList) {
            Button button = new Button(context);

            //text
            button.setText(scheda.getNome());
            button.setTextSize(15f);
            button.setTextColor(ExerciseConstants.Color.TEXT_BUTTON_COLOR);
            // button
            button.setBackground(ContextCompat.getDrawable(context,R.drawable.circled_button));
            button.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorbuttonbase));

            //TODO da levare
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            button.setLayoutParams(params);

             ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) button.getLayoutParams();
             marginLayoutParams.setMargins(0,0,0, 10);

             button.setOnClickListener(v -> {

                FragmentTransaction fragmentTransaction = getParentFragment().getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerArchivioView, SceltaGiornoArchivioFragment.newInstance(scheda));
                fragmentTransaction.commit();

            });

            scheduleLayout.addView(button);

        }
    }


}