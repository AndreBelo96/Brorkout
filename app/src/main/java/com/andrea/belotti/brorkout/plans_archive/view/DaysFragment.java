package com.andrea.belotti.brorkout.plans_archive.view;

import static com.andrea.belotti.brorkout.utils.GenerateDrawableObjUtils.createBasicButtonLayout;
import static com.andrea.belotti.brorkout.utils.GenerateDrawableObjUtils.createBasicCardView;
import static com.andrea.belotti.brorkout.utils.GenerateDrawableObjUtils.createBasicTextView;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.GridLayoutDimension.DAYS_NUMBER;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MemorizeConstants.SCHEDA;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.entity.Giornata;
import com.andrea.belotti.brorkout.entity.Scheda;
import com.andrea.belotti.brorkout.plans_archive.ArchiveSingleton;
import com.andrea.belotti.brorkout.utils.constants.ExerciseConstants;

import java.util.List;


public class DaysFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();
    private Context context;

    public static DaysFragment newInstance(Scheda plan) {
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

        if (getArguments() == null) {
            return view;
        }

        Scheda plan = (Scheda) getArguments().getSerializable(SCHEDA);

        GridLayout daysLayout = view.findViewById(R.id.days);


        if (plan != null) {
            initView(plan, daysLayout);
        } else {
            initEmptyView(daysLayout);
        }

        // back button
        LinearLayout buttonBack = view.findViewById(R.id.back);

        buttonBack.setOnClickListener(v -> {

            String path = ArchiveSingleton.getInstance().getPath();
            String[] sub = path.split("/");

            ArchiveSingleton.getInstance().setPath(sub[0] + "/");

            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerArchiveView, PlansFragment.newInstance(ArchiveSingleton.getInstance().getChosenUserId()));
            fragmentTransaction.commit();

        });


        return view;
    }

    private void initEmptyView(GridLayout daysLayout) {
        Log.e(tag, "Nessuna scheda completata");
        daysLayout.addView(createBasicTextView(context, "Nessuna scheda completata", 20f));
    }

    private void initView(Scheda plan, GridLayout daysLayout) {
        List<Giornata> days = plan.getGiornate();
        setDayButtons(days, daysLayout);
    }


    private void setDayButtons(List<Giornata> days, GridLayout daysLayout) {

        for (int i = 0; i < DAYS_NUMBER; i++) {

            LinearLayout containerView = createBasicCardView(context);

            if (i < days.size()) {

                Giornata day = days.get(i);

                // Create button
                LinearLayout dayButton = createBasicButtonLayout(context, "Giornata: " + day.getNumberOfDays(), 20f);
                dayButton.setPadding(60, 30, 60, 30);

                // Add button to layout
                containerView.addView(dayButton);

                dayButton.setOnClickListener(v -> {

                    ArchiveSingleton.getInstance().setChosenDay(day);
                    ArchiveSingleton.getInstance().setPath(ArchiveSingleton.getInstance().getPath() + "/" + day.getNumberOfDays());

                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerArchiveView, ExercisesFragment.newInstance(day));
                    fragmentTransaction.commit();
                });
            }

            daysLayout.addView(containerView);
        }

    }


}