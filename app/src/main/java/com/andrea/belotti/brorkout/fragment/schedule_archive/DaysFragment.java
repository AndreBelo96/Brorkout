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
import com.andrea.belotti.brorkout.model.Giornata;
import com.andrea.belotti.brorkout.model.Scheda;
import com.andrea.belotti.brorkout.model.nodes.PlanCompletedNode;
import com.andrea.belotti.brorkout.utils.ScheduleCreatingUtils;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.andrea.belotti.brorkout.constants.ExerciseConstants.MemorizeConstants.SCHEDA;
import static com.andrea.belotti.brorkout.utils.GenerateDrawableObjUtils.createBasicButtonLayout;
import static com.andrea.belotti.brorkout.utils.GenerateDrawableObjUtils.createBasicTextView;


public class DaysFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();
    private Context context;

    private ScheduleArchiveActivity activity;


    public static DaysFragment newInstance(PlanCompletedNode plan) {
        DaysFragment fragment = new DaysFragment();
        Bundle args = new Bundle();
        args.putSerializable(SCHEDA, plan);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(tag, ExerciseConstants.TAG_START_FRAGMENT);

        context = getContext();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_days, container, false);

        // da prendere nel bundle
        PlanCompletedNode planNode = new PlanCompletedNode();

        if (getArguments() != null) {
            planNode = (PlanCompletedNode) getArguments().get(SCHEDA);
        }

        LinearLayout daysLayout = view.findViewById(R.id.days);

        activity = (ScheduleArchiveActivity) this.getActivity();

        if (planNode.getPlan() != null) {
            initView(planNode, daysLayout);
        } else {
            initEmptyView(daysLayout);
        }

        // back button
        LinearLayout buttonBack = view.findViewById(R.id.back);

        buttonBack.setOnClickListener(v -> {

            String path = activity.getPath();
            String sub[] = path.split("/");

            activity.setPath(sub[0] + "/" + sub[1] + "/");

            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerArchiveView, PlansFragment.newInstance(activity.getMonthNode()));
            fragmentTransaction.commit();

        });


        return view;
    }

    private void initEmptyView(LinearLayout monthsLayout) {
        Log.e(tag, "Nessuna scheda completata");
        monthsLayout.addView(createBasicTextView(context, "Nessuna scheda completata", 20f));
    }

    private void initView(PlanCompletedNode planNode, LinearLayout daysLayout) {
        List<Giornata> days = planNode.getPlan().getGiornate();
        setDayButtons(days, daysLayout, planNode.getParentId());
    }


    private void setDayButtons(List<Giornata> days, LinearLayout daysLayout, Long monthParentId) {

        for (Giornata day : days) {

            // Create button
            LinearLayout dayButton = createBasicButtonLayout(context, "Giornata: " + day.getNumeroGiornata(), 20f);

            dayButton.setPadding(40,15,40,15);

            // Add button to layout
            daysLayout.addView(dayButton);

            dayButton.setOnClickListener(v -> {

                activity.setPath(activity.getPath() + day.getNumeroGiornata() + "/");
                activity.setDay(day);

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerArchiveView, ExercisesFragment.newInstance(day));
                fragmentTransaction.commit();
            });
        }

    }


}