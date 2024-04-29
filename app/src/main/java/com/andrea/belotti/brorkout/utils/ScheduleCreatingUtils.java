package com.andrea.belotti.brorkout.utils;

import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Button;

import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.model.Scheda;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;

public class ScheduleCreatingUtils {

    private ScheduleCreatingUtils() {
        throw new IllegalStateException("Utility class");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<Scheda> createListaSchede(SharedPreferences sharedPreferences) {
        List<Scheda> schedaList = new ArrayList<>();

        sharedPreferences.getAll().forEach((key, value) -> {
            String json = value.toString();
            schedaList.add(createSchedule(json));
        });

        return schedaList;
    }

    private static Scheda createSchedule(String schedule) {
        Scheda scheda = new Scheda();
        try {
            scheda = JsonGeneratorUtil.generateScheduleFromJson(schedule);
        } catch (JsonProcessingException e) {
            Log.e("TAG", e.getMessage());
        }
        return scheda;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void setBasicColor(List<Button> buttons) {
        buttons.forEach(b -> b.setBackgroundColor(ExerciseConstants.Color.BUTTON_COLOR));
    }
}
