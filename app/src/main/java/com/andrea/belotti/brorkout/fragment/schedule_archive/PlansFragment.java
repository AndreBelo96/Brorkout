package com.andrea.belotti.brorkout.fragment.schedule_archive;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.activity.ScheduleArchiveActivity;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.model.nodes.Node;
import com.andrea.belotti.brorkout.model.nodes.PlanCompletedNode;
import com.andrea.belotti.brorkout.utils.ScheduleCreatingUtils;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.andrea.belotti.brorkout.constants.ExerciseConstants.MemorizeConstants.NODE;
import static com.andrea.belotti.brorkout.utils.GenerateDrawableObjUtils.createBasicButtonLayout;
import static com.andrea.belotti.brorkout.utils.GenerateDrawableObjUtils.createBasicTextView;


public class PlansFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();
    private Context context;

    private ScheduleArchiveActivity activity;

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

        LinearLayout plansLayout = view.findViewById(R.id.plans);

        activity = (ScheduleArchiveActivity) this.getActivity();

        if (!monthNode.isEmpty()) {
            initView(monthNode, plansLayout);
        } else {

            Log.e(tag, "Nessuna scheda completata");
            initEmptyView(plansLayout);
        }

        return view;
    }

    private void initEmptyView(LinearLayout monthsLayout) {
        monthsLayout.addView(createBasicTextView(context, "Nessuna scheda completata"));
    }

    private void initView(Node monthNode, LinearLayout plansLayout) {
        List<PlanCompletedNode> planNodes = monthNode.getData();
        setPlanButtons(planNodes, plansLayout, monthNode.getParentId());
    }

    private void setPlanButtons(List<PlanCompletedNode> planNodes, LinearLayout plansLayout, Long yearParentId) {

        for(PlanCompletedNode plan : planNodes) {

            if (!plan.isEmpty()) {

                // Create button
                LinearLayout planButton = createBasicButtonLayout(context, plan.getName());

                // Add button to layout
                plansLayout.addView(planButton);

                planButton.setOnClickListener(v -> {

                    activity.setPath(activity.getPath() + plan.getName() + "/");

                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerArchiveView, DaysFragment.newInstance(plan));
                    fragmentTransaction.commit();
                });
            }
        }

        LinearLayout buttonBack = createBasicButtonLayout(context, "Back");

        buttonBack.setOnClickListener(v -> {

            String path = activity.getPath();
            String sub[] = path.split("/");

            SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
            Node rootNode = ScheduleCreatingUtils.getNodeFromPref(sharedPreferences);

            Node year = (Node) rootNode.findChildById(yearParentId, rootNode.getChildren());

            activity.setPath(sub[0] + "/");

            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerArchiveView, MonthsFragment.newInstance(year));
            fragmentTransaction.commit();

        });


        plansLayout.addView(buttonBack);

    }



}