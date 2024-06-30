package com.andrea.belotti.brorkout.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.ViewPagerAdapter;
import com.andrea.belotti.brorkout.model.Scheda;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManagerListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManagerListFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapter viewPagerAdapter;
    List<Scheda> schedaLocalList = new ArrayList<>();

    public static ManagerListFragment newInstance(List<Scheda> param1) {
        ManagerListFragment fragment = new ManagerListFragment();
        Bundle args = new Bundle();
        args.putSerializable("ListaSchede", (Serializable) param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(tag, "Starting Fragment");

        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            schedaLocalList = (List<Scheda>) getArguments().get("ListaSchede");
        }

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manager_list, container, false);

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager2 = view.findViewById(R.id.viewPager);

        viewPagerAdapter = new ViewPagerAdapter(this, schedaLocalList);

        viewPager2.setAdapter(viewPagerAdapter);

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


        return view;
    }


    public void changeFragment(Scheda scheda) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerArchivioView, SceltaGiornoArchivioFragment.newInstance(scheda));
        fragmentTransaction.commit();
    }


}