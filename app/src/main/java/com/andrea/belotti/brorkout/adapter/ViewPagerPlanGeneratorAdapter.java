package com.andrea.belotti.brorkout.adapter;

import com.andrea.belotti.brorkout.fragment.creator.newinterfacecreator.ExeListFragment;
import com.andrea.belotti.brorkout.model.Scheda;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerPlanGeneratorAdapter extends FragmentStateAdapter {

    Scheda scheda;

    public ViewPagerPlanGeneratorAdapter(@NonNull Fragment fragment, Scheda scheda) {
        super(fragment);
        this.scheda = scheda;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return switch (position) {
            case 0 -> ExeListFragment.newInstance(scheda.getGiornate().get(0));
            case 1 -> ExeListFragment.newInstance(scheda.getGiornate().get(1));
            case 2 -> ExeListFragment.newInstance(scheda.getGiornate().get(2));
            case 3 -> ExeListFragment.newInstance(scheda.getGiornate().get(3));
            case 4 -> ExeListFragment.newInstance(scheda.getGiornate().get(4));
            case 5 -> ExeListFragment.newInstance(scheda.getGiornate().get(5));
            case 6 -> ExeListFragment.newInstance(scheda.getGiornate().get(6));
            default -> new Fragment(); //TODO fragment di errore

        };

    }



    @Override
    public int getItemCount() {
        return 2;
    }
}
