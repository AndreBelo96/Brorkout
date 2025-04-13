package com.andrea.belotti.brorkout.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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

    public static void setClipboard(Context context, String text) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Testo copiato", text);
        clipboardManager.setPrimaryClip(clip);
    }

    public static String generate8CharString(String data) {
        while (data.length() < 8) {
            data = "0" + data;
        }
        return data;
    }

}
