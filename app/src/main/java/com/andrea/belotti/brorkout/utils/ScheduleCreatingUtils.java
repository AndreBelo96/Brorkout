package com.andrea.belotti.brorkout.utils;

import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;

import com.andrea.belotti.brorkout.R;

import java.util.List;

public class ScheduleCreatingUtils {

    private ScheduleCreatingUtils() {
        throw new IllegalStateException("Utility class");
    }


    public static void setBasicColor(List<LinearLayout> buttons) {
        buttons.forEach(b -> {
            b.setBackgroundResource(R.drawable.blue_top_button);
        });
    }

    public static void setCardViewBasicColor(List<CardView> cards) {
        if (cards == null) {
            return;
        }

        cards.forEach(c -> {
            c.setBackgroundResource(R.drawable.blue_top_button);
        });
    }



}
