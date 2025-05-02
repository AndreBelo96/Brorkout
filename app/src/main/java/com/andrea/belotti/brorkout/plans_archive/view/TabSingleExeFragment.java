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
import com.andrea.belotti.brorkout.utils.constants.ExerciseConstants;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


public class TabSingleExeFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();

    private LinearLayout buttonBack;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

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

        Log.i(tag, ExerciseConstants.TAG_START_FRAGMENT);

        View view = inflater.inflate(R.layout.fragment_tab_single_exe, container, false);

        if (getArguments() == null) {
            Log.i(tag, ExerciseConstants.ERROR_ARGUMENT);
            return view;
        }

        Log.i(tag, ExerciseConstants.RETRIEVING_DATA);
        List<Esercizio> exes = (List<Esercizio>) getArguments().get(ESERCIZI);
        int chosenExe = getArguments().getInt(ESERCIZIO_SCELTO);
        Log.i(tag, ExerciseConstants.DATA_RETRIEVE);

        if (exes == null || exes.isEmpty()) {
            Log.e(tag, ExerciseConstants.DATA_ARGUMENT_NULL);
            return view;
        }

        SingleExeAdapter singleExeAdapter = new SingleExeAdapter(this, exes);

        initWidgets(view);
        setupWidgets(chosenExe, singleExeAdapter);
        initTabLayout(tabLayout, exes.size(), getContext());

        buttonBack.setOnClickListener(v -> onBackClick());

        return view;
    }

    private void initWidgets(View view) {
        buttonBack = view.findViewById(R.id.back);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager2 = view.findViewById(R.id.pager);
    }

    private void setupWidgets(int chosenExe, SingleExeAdapter singleExeAdapter) {
        viewPager2.setAdapter(singleExeAdapter);
        viewPager2.setCurrentItem(chosenExe);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Objects.requireNonNull(tabLayout.getTabAt(position)).select();
            }
        });
    }

    private void onBackClick() {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerArchiveView, ExercisesFragment.newInstance(ArchiveSingleton.getInstance().getChosenDay()));
        fragmentTransaction.commit();
    }

    private void initTabLayout(TabLayout tabLayout, int numberOfDays, Context context) {

        for (int i = 0; i < numberOfDays; i++) {
            TabItem tabItem = new TabItem(context);
            tabLayout.addView(tabItem);
        }
    }


}