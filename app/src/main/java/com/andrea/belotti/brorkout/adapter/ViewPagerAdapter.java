package com.andrea.belotti.brorkout.adapter;

import com.andrea.belotti.brorkout.plans_selection.view.ListaSchedeLocalArchivioFragment;
import com.andrea.belotti.brorkout.plans_selection.view.ListaSchedeOnlineArchivioFragment;
import com.andrea.belotti.brorkout.entity.Scheda;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    List<Scheda> schedaList;

    public ViewPagerAdapter(@NonNull Fragment fragment, List<Scheda> schedaList) {
        super(fragment);
        this.schedaList = schedaList;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return switch (position) {
            case 0 -> ListaSchedeLocalArchivioFragment.newInstance(schedaList);
            case 1 -> new ListaSchedeOnlineArchivioFragment();
            default -> new Fragment(); //TODO fragment di errore
        };

    }



    @Override
    public int getItemCount() {
        return 2;
    }
}
