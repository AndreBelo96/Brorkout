package com.andrea.belotti.brorkout.fragment.archive_plan;

import static com.andrea.belotti.brorkout.constants.ExerciseConstants.MemorizeConstants.NODE;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.view.archive.ScheduleArchiveActivity;
import com.andrea.belotti.brorkout.adapter.PlanAdapter;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.model.nodes.Node;
import com.andrea.belotti.brorkout.model.nodes.PlanCompletedNode;

public class PlansFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();
    private Context context;
    private ScheduleArchiveActivity activity;
    private long parentId;

    public static PlansFragment newInstance(Node monthNode) {
        PlansFragment fragment = new PlansFragment();
        Bundle args = new Bundle();
        args.putSerializable(NODE, monthNode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(tag, ExerciseConstants.TAG_START_FRAGMENT);

        context = getContext();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plans, container, false);

        // da prendere nel bundle
        Node monthNode = new Node();

        if (getArguments() != null) {
            monthNode = (Node) getArguments().get(NODE);
        }

        parentId = monthNode.getParentId();
        activity = (ScheduleArchiveActivity) this.getActivity();

        // set recyclerView info
        RecyclerView recyclerView = view.findViewById(R.id.plans);
        PlanAdapter adapter = new PlanAdapter(context, monthNode.getData().toArray(new PlanCompletedNode[0]), activity, getParentFragmentManager());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        // back button
        LinearLayout buttonBack = view.findViewById(R.id.back);

        buttonBack.setOnClickListener(v -> {

            String path = activity.getPath();
            String sub[] = path.split("/");

            activity.setPath(sub[0] + "/");

            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerArchiveView, MonthsFragment.newInstance(activity.getYearNode()));
            fragmentTransaction.commit();

        });

        return view;
    }

}