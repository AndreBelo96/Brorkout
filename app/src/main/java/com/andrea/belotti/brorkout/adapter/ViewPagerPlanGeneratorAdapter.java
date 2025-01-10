package com.andrea.belotti.brorkout.adapter;

import com.andrea.belotti.brorkout.view.creation.schedulecreator.ExeListFragment;
import com.andrea.belotti.brorkout.model.Scheda;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerPlanGeneratorAdapter extends FragmentStateAdapter {

    Scheda scheda;
    ExeListFragment exeFrg0;
    ExeListFragment exeFrg1;
    ExeListFragment exeFrg2;
    ExeListFragment exeFrg3;
    ExeListFragment exeFrg4;
    ExeListFragment exeFrg5;
    ExeListFragment exeFrg6;

    public ViewPagerPlanGeneratorAdapter(@NonNull Fragment fragment, Scheda scheda) {
        super(fragment);
        this.scheda = scheda;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                exeFrg0 = ExeListFragment.newInstance(scheda.getGiornate().get(0));
                return exeFrg0;
            case 1:
                exeFrg1 = ExeListFragment.newInstance(scheda.getGiornate().get(1));
                return exeFrg1;
            case 2:
                exeFrg2 = ExeListFragment.newInstance(scheda.getGiornate().get(2));
                return exeFrg2;
            case 3:
                exeFrg3 = ExeListFragment.newInstance(scheda.getGiornate().get(3));
                return exeFrg3;
            case 4:
                exeFrg4 = ExeListFragment.newInstance(scheda.getGiornate().get(4));
                return exeFrg4;
            case 5:
                exeFrg5 = ExeListFragment.newInstance(scheda.getGiornate().get(5));
                return exeFrg5;
            case 6:
                exeFrg6 = ExeListFragment.newInstance(scheda.getGiornate().get(6));
                return exeFrg6;
            default:
                return new Fragment();
        }

    }

    @Override
    public int getItemCount() {
        return scheda.getGiornate().size();
    }


}
