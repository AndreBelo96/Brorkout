package com.andrea.belotti.brorkout.plans_selection.adapter;

import com.andrea.belotti.brorkout.plans_selection.view.SelectSchedePersonaliFragment;
import com.andrea.belotti.brorkout.plans_selection.view.ListaSchedeOnlineArchivioFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TabLayoutSelectScheduleAdapter extends FragmentStateAdapter {


    public TabLayoutSelectScheduleAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return switch (position) {
            case 0 -> new SelectSchedePersonaliFragment();
            case 1 -> new ListaSchedeOnlineArchivioFragment();
            default -> new Fragment(); //TODO fragment di errore
        };

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
