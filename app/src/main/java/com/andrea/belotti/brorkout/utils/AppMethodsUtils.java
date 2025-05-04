package com.andrea.belotti.brorkout.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.model.CompleteState;

import java.util.Random;

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

    public static int setExeColor(CompleteState exeState, Context context) {
        if (exeState == CompleteState.INCOMPLETE_KO) {
            return ContextCompat.getColor(context, R.color.exe_ko);
        } else if (exeState == CompleteState.COMPLETE_OK) {
            return ContextCompat.getColor(context, R.color.exe_ok);
        } else {
            return ContextCompat.getColor(context, R.color.exe_partial_ko);
        }
    }

    public static int setImageExe() {

        int randomNum = new Random().nextInt(5 - 2) + 1;

        return switch (randomNum) {
            case 1 -> R.drawable.ic_pull_up;
            case 2 -> R.drawable.ic_squat;
            case 3 -> R.drawable.ic_verticale;
            case 4 -> R.drawable.ic_push_up;
            case 5 -> R.drawable.ic_dip;
            default -> 0;
        };
    }

}
