package com.andrea.belotti.brorkout.plans_archive.view;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MemorizeConstants.ESERCIZI;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MemorizeConstants.ESERCIZIO_SCELTO;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.adapter.SingleExeAdapter;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.plans_archive.ArchiveSingleton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.List;


public class TabSingleExeFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();

    public static TabSingleExeFragment newInstance(List<Esercizio> exes, int chosenExe) {
        TabSingleExeFragment fragment = new TabSingleExeFragment();
        Bundle args = new Bundle();
        args.putSerializable(ESERCIZI, (Serializable) exes);
        args.putInt(ESERCIZIO_SCELTO, chosenExe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(tag, "Starting Fragment...");

        super.onCreate(savedInstanceState);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_single_exe, container, false);

        if (getArguments() == null) {
            Log.i(tag, "No Data!!");
            return view;
        }

        Log.i(tag, "Retrieve Data...");
        List<Esercizio> exes = (List<Esercizio>) getArguments().get(ESERCIZI);
        int chosenExe = getArguments().getInt(ESERCIZIO_SCELTO);
        Log.i(tag, "Data Retrieve!!");

        if (exes == null || exes.isEmpty()) {
            Log.e(tag, "Unexpected Data Value!!");
            return view;
        }

        ScheduleArchiveActivity activity = (ScheduleArchiveActivity) this.getActivity();

        if (activity == null) {
            Log.e(tag, "Unexpected Activity Value!!");
            return view;
        }

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager2 viewPager2 = view.findViewById(R.id.pager);
        SingleExeAdapter singleExeAdapter = new SingleExeAdapter(this, exes);

        initTabLayout(tabLayout, exes.size(), getContext());

        viewPager2.setAdapter(singleExeAdapter);
        viewPager2.setCurrentItem(chosenExe);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

        // Back button
        LinearLayout buttonBack = view.findViewById(R.id.back);

        buttonBack.setOnClickListener(v -> {

            String path = ArchiveSingleton.getInstance().getPath();
            String[] sub = path.split("/");

            ArchiveSingleton.getInstance().setPath(sub[0] + "/" + sub[1] + "/" + sub[2] + "/");

            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerArchiveView, ExercisesFragment.newInstance(ArchiveSingleton.getInstance().getChosenDay()));
            fragmentTransaction.commit();

        });

        return view;
    }

    private void initTabLayout(TabLayout tabLayout, int numberOfDays, Context context) {

        for (int i = 0; i < numberOfDays; i++) {
            TabItem tabItem = new TabItem(context);
            tabLayout.addView(tabItem);
        }
    }

}