package com.andrea.belotti.brorkout.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.view.archive.SingleExeFragment;

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
        SingleExeFragment exeFragment = SingleExeFragment.newInstance(exeList.get(position));
        return exeFragment;
    }



    @Override
    public int getItemCount() {
        return exeList.size();
    }
}