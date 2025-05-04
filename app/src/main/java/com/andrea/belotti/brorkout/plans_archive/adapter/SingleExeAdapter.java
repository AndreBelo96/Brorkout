package com.andrea.belotti.brorkout.plans_archive.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.plans_archive.view.SingleExeFragment;

import java.util.List;

public class SingleExeAdapter extends FragmentStateAdapter {

    List<Esercizio> exeList;

    public SingleExeAdapter(Fragment fragment, List<Esercizio> exeList) {
        super(fragment);
        this.exeList = exeList;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return SingleExeFragment.newInstance(exeList.get(position));
    }



    @Override
    public int getItemCount() {
        return exeList.size();
    }
}