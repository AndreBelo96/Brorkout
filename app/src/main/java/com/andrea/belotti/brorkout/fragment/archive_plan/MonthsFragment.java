package com.andrea.belotti.brorkout.fragment.archive_plan;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.activity.ScheduleArchiveActivity;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.model.nodes.Node;

import java.util.List;

import static com.andrea.belotti.brorkout.constants.ExerciseConstants.MemorizeConstants.NODE;
import static com.andrea.belotti.brorkout.utils.GenerateDrawableObjUtils.createBasicCardView;
import static com.andrea.belotti.brorkout.utils.GenerateDrawableObjUtils.createBasicTextView;
import static com.andrea.belotti.brorkout.utils.GenerateDrawableObjUtils.createWrapButtonLayout;


public class MonthsFragment extends Fragment {


    private final String tag = this.getClass().getSimpleName();
    private Context context;

    private ScheduleArchiveActivity activity;

    public static MonthsFragment newInstance(Node yearNode) {
        MonthsFragment fragment = new MonthsFragment();
        Bundle args = new Bundle();
        args.putSerializable(NODE, yearNode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(tag, ExerciseConstants.TAG_START_FRAGMENT);

        context = getContext();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_months, container, false);

        // da prendere nel bundle
        Node yearNode = new Node();

        if (getArguments() != null) {
            yearNode = (Node) getArguments().get(NODE);
        }

        GridLayout monthsLayout = view.findViewById(R.id.months);

        activity = (ScheduleArchiveActivity) this.getActivity();

        if (!yearNode.isEmpty()) {
            initView(yearNode, monthsLayout);
        } else {

            Log.e(tag, "Nessuna scheda completata");
            initEmptyView(monthsLayout);
        }

        // back button
        LinearLayout buttonBack = view.findViewById(R.id.back);

        buttonBack.setOnClickListener(v -> {

            activity.setPath("");

            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerArchiveView, YearsFragment.newInstance());
            fragmentTransaction.commit();

        });

        return view;
    }

    private void initEmptyView(GridLayout monthsLayout) {
        monthsLayout.addView(createBasicTextView(context, "Nessuna scheda completata", 20f));
    }

    private void initView(Node yearNode, GridLayout monthsLayout) {

        List<Node> monthNodes = yearNode.getChildren();
        setMonthButtons(monthNodes, monthsLayout);
    }


    private void setMonthButtons(List<Node> monthNodes, GridLayout monthsLayout) {


        for (int i = 0; i<12; i++) {

            LinearLayout containerView = createBasicCardView(context);

            if (i < monthNodes.size()) {

                Node month = monthNodes.get(i);

                // Create button
                LinearLayout monthButton = createWrapButtonLayout(context, month.getName());

                // Add button to layout
                containerView.addView(monthButton);

                monthButton.setOnClickListener( v-> {

                    activity.setPath(activity.getPath() + month.getName() + "/");
                    activity.setMonthNode(month);

                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerArchiveView, PlansFragment.newInstance(month));
                    fragmentTransaction.commit();
                });

            }

            monthsLayout.addView(containerView);
        }

    }
}