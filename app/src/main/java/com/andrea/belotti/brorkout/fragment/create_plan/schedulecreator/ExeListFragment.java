package com.andrea.belotti.brorkout.fragment.create_plan.schedulecreator;

import static com.andrea.belotti.brorkout.constants.ExerciseConstants.MemorizeConstants.GIORNATA;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.activity.ScheduleCreatorActivity;
import com.andrea.belotti.brorkout.adapter.ItemEsercizioCreateAdapter;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.model.Giornata;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExeListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExeListFragment extends Fragment {


    Giornata giornata;

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

        RecyclerView layout = view.findViewById(R.id.exeListLayout);

        if (giornata.getEsercizi() != null) {
            initView(giornata, layout, getContext());
        }

        return view;
    }

    private void initView(Giornata giornata, RecyclerView exeRecyclerView, Context context) {

        ScheduleCreatorActivity activity = (ScheduleCreatorActivity) this.getActivity();

        // set recyclerView info
        ItemEsercizioCreateAdapter adapter = new ItemEsercizioCreateAdapter(context, giornata.getEsercizi().toArray(new Esercizio[0]), activity, getParentFragmentManager());
        exeRecyclerView.setHasFixedSize(true);
        exeRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        exeRecyclerView.setAdapter(adapter);

    }

}