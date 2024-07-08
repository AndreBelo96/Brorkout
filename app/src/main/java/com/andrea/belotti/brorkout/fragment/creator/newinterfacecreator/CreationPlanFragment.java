package com.andrea.belotti.brorkout.fragment.creator.newinterfacecreator;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.activity.ScheduleCreatorActivity;
import com.andrea.belotti.brorkout.adapter.ViewPagerPlanGeneratorAdapter;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.model.Giornata;
import com.andrea.belotti.brorkout.model.Scheda;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.andrea.belotti.brorkout.constants.ExerciseConstants.MemorizeConstants.*;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreationPlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreationPlanFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();
    private static Scheda scheda = new Scheda();

    TabLayout tabLayout;
    static ViewPager2 viewPager2;
    static ViewPagerPlanGeneratorAdapter viewPagerPlanGeneratorAdapter;

    public static CreationPlanFragment newInstance(String scheduleTitle, Integer giorni) {
        CreationPlanFragment fragment = new CreationPlanFragment();
        Bundle args = new Bundle();
        args.putInt(NUMERO_GIORNATE, giorni);
        args.putString(TITOLO_SCHEDA, scheduleTitle);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(tag, "Starting Fragment");

        super.onCreate(savedInstanceState);

        int numeroGiornate = 0;
        String titoloScheda = "";
        Scheda datiScheda = null;

        if (getArguments() != null) {
            numeroGiornate = getArguments().getInt(NUMERO_GIORNATE);
            titoloScheda = getArguments().getString(TITOLO_SCHEDA);
        }

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_creation_plan, container, false);

        Context context = getContext();

        // Initialize plan with data
        scheda = initWorkoutPlan(titoloScheda, numeroGiornate, datiScheda);

        tabLayout = view.findViewById(R.id.tabDayListLayout);
        viewPager2 = view.findViewById(R.id.viewDayListPager);

        Button addButton = view.findViewById(R.id.addButton);
        Button modifyButton = view.findViewById(R.id.modifyButton);
        Button deleteButton = view.findViewById(R.id.deleteButton);
        Button copyButton = view.findViewById(R.id.copyButton);
        Button pasteButton = view.findViewById(R.id.pasteButton);
        Button createSchedule = view.findViewById(R.id.create);

        viewPagerPlanGeneratorAdapter = new ViewPagerPlanGeneratorAdapter(this, scheda);

        viewPager2.setAdapter(viewPagerPlanGeneratorAdapter);

        initTabLayout(tabLayout, numeroGiornate, context);

        ScheduleCreatorActivity activity = (ScheduleCreatorActivity) this.getActivity();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

        addButton.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            //fragmentTransaction.setCustomAnimations(enter1, exit1, popEnter1, popExit1) //TODO
            fragmentTransaction.add(R.id.fragmentContainerViewScheduleCreator, AddExeFragment.newInstance());
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.hide(this);
            fragmentTransaction.commit();
        });

        modifyButton.setOnClickListener(v -> {

            List<Esercizio> actualExeList = scheda.getGiornate().get(tabLayout.getSelectedTabPosition()).getEsercizi();

            if(activity.getSelectedExe() == null || actualExeList.size() <= activity.getSelectedExe()) {
                Toast toast = Toast.makeText(getContext(), "Exe not selected", ExerciseConstants.ToastMessageConstants.DURATION);
                toast.show();
                return;
            }

            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragmentContainerViewScheduleCreator, ModifyExeFragment.newInstance(scheda.getGiornate().get(tabLayout.getSelectedTabPosition()), activity.getSelectedExe()));
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.hide(this);
            fragmentTransaction.commit();
        });

        deleteButton.setOnClickListener(v -> {
            assert activity != null;
            List<Esercizio> actualExeList = scheda.getGiornate().get(tabLayout.getSelectedTabPosition()).getEsercizi();

            if(activity.getSelectedExe() == null || actualExeList.size() <= activity.getSelectedExe()) {
                Toast toast = Toast.makeText(getContext(), "Exe not selected", ExerciseConstants.ToastMessageConstants.DURATION);
                toast.show();
                return;
            }

            int exe = activity.getSelectedExe();

            // remove selected exe in the current exeList
            actualExeList.remove(exe);
            //set selected exercise to null
            activity.setSelectedExe(null);
            //refresh viewPager2
            viewPager2.setAdapter(viewPagerPlanGeneratorAdapter);
        });

        return view;
    }

    private Scheda initWorkoutPlan(String titoloScheda, int numeroGiornate, Scheda datiScheda) {

        Scheda scheda = new Scheda();

        if (datiScheda != null && StringUtils.isEmpty(titoloScheda)) {
            return datiScheda;
        } else if (datiScheda != null && StringUtils.isNotEmpty(titoloScheda)) {
            scheda.setNome(titoloScheda);
            scheda.setGiornate(datiScheda.getGiornate());
        } else {
            scheda.setNome(titoloScheda);
            List<Giornata> giornateList = new ArrayList<>();
            for (int i = 1; i <= numeroGiornate; i++) {
                Giornata g = new Giornata();
                List<Esercizio> exeList = new ArrayList<>();
                g.setEsercizi(exeList);
                giornateList.add(g);
            }
            scheda.setGiornate(giornateList);
        }

        return scheda;
    }

    private void initTabLayout(TabLayout tabLayout, int numeroGiornate, Context context) {

        for(int i = 0; i < numeroGiornate; i++) {
            TabItem tabItem = new TabItem(context);
            tabLayout.addView(tabItem);
            tabLayout.getTabAt(i).setText(""+(i+1));
        }
    }

    public static void refreshPage(ScheduleCreatorActivity activity) {
        scheda.getGiornate().get(viewPager2.getCurrentItem()).getEsercizi().add(activity.getAddExeCreation());
        viewPager2.setAdapter(viewPagerPlanGeneratorAdapter);
    }

    public static void refreshPage(ScheduleCreatorActivity activity, Integer numberExe) {
        scheda.getGiornate().get(viewPager2.getCurrentItem()).getEsercizi().set(numberExe, activity.getAddExeCreation());
        viewPager2.setAdapter(viewPagerPlanGeneratorAdapter);
    }

}