package com.andrea.belotti.brorkout.utils;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.Color.TEXT_BUTTON_COLOR;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MemorizeConstants.ROOT;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MemorizeConstants.SCHEDA;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.entity.Scheda;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
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
