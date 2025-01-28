package com.andrea.belotti.brorkout.plans_selection.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.adapter.PlanSelectedAdapter;
import com.andrea.belotti.brorkout.utils.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.entity.Scheda;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        SelectScheduleActivity activity = (SelectScheduleActivity) this.getActivity();

        List<Scheda> schedaLocalList = new ArrayList<>();

        if (getArguments() != null) {
            schedaLocalList = (List<Scheda>) getArguments().get("ListaSchede");
        }

        if (schedaLocalList != null && !schedaLocalList.isEmpty()) {

            // set recyclerView info
            RecyclerView recyclerView = view.findViewById(R.id.scheduleListView);
            PlanSelectedAdapter adapter = new PlanSelectedAdapter(context, getParentFragmentManager(), activity);
            adapter.setPlans(schedaLocalList);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(adapter);

        } else {
            Log.e(tag, "Lista schede locali vuota");
        }

        return view;
    }

}