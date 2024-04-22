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
    private static final String SUCCESS_CREATING_STRING = "Scheda salvata con successo";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(TAG, ExerciseConstants.TAG_START_FRAGMENT);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scelta_scheda_archivio, container, false);

        context = getContext();
        List<Scheda> schedaList = new ArrayList<>();

        if (getArguments() != null) {
            schedaList = (List<Scheda>) getArguments().get("ListaSchede");
        }

        if (schedaList != null && !schedaList.isEmpty()) {
            createView(schedaList, view);
        } else {
            Log.e(TAG, "Lista schede vuota");
        }

        return view;
    }

    private void createView(List<Scheda> schedaList, View view) {
        LinearLayout scheduleLayout = view.findViewById(R.id.scheduleView);


        for (Scheda scheda : schedaList) {
            Button button = new Button(context);
            button.setText(scheda.getNome());
            scheduleLayout.addView(button);


            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    SceltaGiornoArchivioFragment sceltaGiornoArchivioFragment = new SceltaGiornoArchivioFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Scheda", scheda);
                    sceltaGiornoArchivioFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragmentContainerArchivioView, sceltaGiornoArchivioFragment);
                    fragmentTransaction.commit();

                }
            });

        }
    }

    public static CopiaSchedaFragment newInstance(List<Scheda> param1) {
        CopiaSchedaFragment fragment = new CopiaSchedaFragment();
        Bundle args = new Bundle();
        args.putSerializable("ListaSchede", (Serializable) param1);
        fragment.setArguments(args);
        return fragment;
    }


}