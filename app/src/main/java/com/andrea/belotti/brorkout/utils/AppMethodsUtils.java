package com.andrea.belotti.brorkout.utils;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

public class AppMethodsUtils {

    public static List<Button> getButtonList(LinearLayout linearLayoutSchedule) {
        List<Button> buttonList = new ArrayList<>();

        for (int i = 0; i < linearLayoutSchedule.getChildCount(); i++) {
            buttonList.add((Button) linearLayoutSchedule.getChildAt(i));
        }

        return buttonList;
    }

    public static List<ToggleButton> getToggleButtonList(LinearLayout linearLayoutSchedule) {
        List<ToggleButton> buttonList = new ArrayList<>();

        for (int i = 0; i < linearLayoutSchedule.getChildCount(); i++) {
            buttonList.add((ToggleButton) linearLayoutSchedule.getChildAt(i));
        }

        return buttonList;
    }
}
