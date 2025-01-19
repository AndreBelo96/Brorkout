package com.andrea.belotti.brorkout.utils;

import android.text.TextUtils;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public class AppMethodsUtils {

    public static void deleteFragmentFromStack(@NonNull FragmentTransaction fragmentTransaction, Fragment fragmentToRemove, FragmentActivity activity) {
        fragmentTransaction.remove(fragmentToRemove);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.show(activity.getSupportFragmentManager().getFragments().get(0));//TODO non va bene
        fragmentTransaction.commit();
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
