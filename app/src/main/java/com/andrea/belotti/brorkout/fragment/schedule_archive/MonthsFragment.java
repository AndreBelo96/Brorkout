package com.andrea.belotti.brorkout.fragment.schedule_archive;

import android.content.Context;
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

import java.util.List;

import static com.andrea.belotti.brorkout.constants.ExerciseConstants.MemorizeConstants.NODE;
import static com.andrea.belotti.brorkout.utils.GenerateDrawableObjUtils.createBasicButtonLayout;
import static com.andrea.belotti.brorkout.utils.GenerateDrawableObjUtils.createBasicTextView;


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

        LinearLayout monthsLayout = view.findViewById(R.id.months);

        activity = (ScheduleArchiveActivity) this.getActivity();

        if (!yearNode.isEmpty()) {
            initView(yearNode, monthsLayout);
        } else {

            Log.e(tag, "Nessuna scheda completata");
            initEmptyView(monthsLayout);
        }

        return view;
    }

    private void initEmptyView(LinearLayout monthsLayout) {
        monthsLayout.addView(createBasicTextView(context, "Nessuna scheda completata"));
    }

    private void initView(Node yearNode, LinearLayout monthsLayout) {

        List<Node> monthNodes = yearNode.getChildren();
        setMonthButtons(monthNodes, monthsLayout);
    }


    private void setMonthButtons(List<Node> monthNodes, LinearLayout monthsLayout) {

        for(Node month : monthNodes) {

            // Create button
            LinearLayout monthButton = createBasicButtonLayout(context, month.getName());

            // Add button to layout
            monthsLayout.addView(monthButton);

            monthButton.setOnClickListener( v-> {

                activity.setPath(activity.getPath() + month.getName() + "/");

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerArchiveView, PlansFragment.newInstance(month));
                fragmentTransaction.commit();
            });

        }

        LinearLayout buttonBack = createBasicButtonLayout(context, "Back");

        buttonBack.setOnClickListener(v -> {

            activity.setPath("");

            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerArchiveView, YearsFragment.newInstance());
            fragmentTransaction.commit();

        });


        monthsLayout.addView(buttonBack);
    }


}