package com.andrea.belotti.brorkout.fragment.schedule_archive;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.activity.ScheduleArchiveActivity;
import com.andrea.belotti.brorkout.activity.StartingMenuActivity;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.model.nodes.Node;
import com.andrea.belotti.brorkout.utils.ScheduleCreatingUtils;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import static android.content.Context.MODE_PRIVATE;
import static com.andrea.belotti.brorkout.utils.GenerateDrawableObjUtils.*;

public class YearsFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();
    private Context context;
    private ScheduleArchiveActivity activity;

    public static YearsFragment newInstance() {

        return new YearsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(tag, ExerciseConstants.TAG_START_FRAGMENT);

        context = getContext();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_years, container, false);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);

        Node rootNode = ScheduleCreatingUtils.getNodeFromPref(sharedPreferences);

        LinearLayout yearsLayout = view.findViewById(R.id.years);

        activity = (ScheduleArchiveActivity) this.getActivity();

        // back button
        LinearLayout buttonBack = view.findViewById(R.id.back);

        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(context, StartingMenuActivity.class);
            startActivity(intent);
        });


        if (!rootNode.isEmpty()) {
            initView(rootNode, yearsLayout);
        } else {

            Log.e(tag, "Nessuna scheda completata");
            initEmptyView(yearsLayout);
        }

        return view;
    }

    private void initEmptyView(LinearLayout plansCompletedLayout) {

        plansCompletedLayout.addView(createBasicTextView(context, "Nessuna scheda completata", 15f));
    }

    private void initView(Node rootNode, LinearLayout plansCompletedLayout) {

        List<Node> yearNodes = rootNode.getChildren();
        setYearButtons(yearNodes, plansCompletedLayout);
    }

    private void setYearButtons(List<Node> yearNodes, LinearLayout plansCompletedLayout) {

        for(Node year : yearNodes) {
            // Create button
            LinearLayout yearButton = createBasicButtonLayout(context, year.getName(), 20f);
            yearButton.setPadding(60, 20,60, 20);


            // Add button to layout
            plansCompletedLayout.addView(yearButton);

            yearButton.setOnClickListener( v-> {

                activity.setPath(year.getName() + "/");
                activity.setYearNode(year);

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerArchiveView, MonthsFragment.newInstance(year));
                fragmentTransaction.commit();
            });
        }

    }

}