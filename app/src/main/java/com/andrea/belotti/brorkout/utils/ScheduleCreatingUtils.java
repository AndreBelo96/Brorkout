package com.andrea.belotti.brorkout.utils;

import static com.andrea.belotti.brorkout.constants.ExerciseConstants.Color.TEXT_BUTTON_COLOR;
import static com.andrea.belotti.brorkout.constants.ExerciseConstants.MemorizeConstants.ROOT;
import static com.andrea.belotti.brorkout.constants.ExerciseConstants.MemorizeConstants.SCHEDA;

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
import com.andrea.belotti.brorkout.model.Scheda;
import com.andrea.belotti.brorkout.model.nodes.Node;
import com.andrea.belotti.brorkout.model.nodes.PlanCompletedNode;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;

public class ScheduleCreatingUtils {

    private ScheduleCreatingUtils() {
        throw new IllegalStateException("Utility class");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<Scheda> createListaSchede(SharedPreferences sharedPreferences) {
        List<Scheda> schedaList = new ArrayList<>();

        sharedPreferences.getAll().forEach((key, value) -> {
            if (key.startsWith(SCHEDA)) {
                String json = value.toString();
                schedaList.add(createSchedule(json));
            }
        });

        return schedaList;
    }

    //TODO controllo sui nullpointer
    public static Node getNodeFromPref(SharedPreferences sharedPreferences) {
        final Node[] rootNode = {new Node()};

        sharedPreferences.getAll().forEach((key, value) -> {
            if (key.startsWith(ROOT)) {
                String json = value.toString();
                rootNode[0] = createNode(json);
            }
        });

        return rootNode[0];
    }

    private static Node createNode(String node) {
        Node rootNode = new Node();
        try {
            rootNode = JsonGeneratorUtil.generateNodeFromJson(node);
        } catch (JsonProcessingException e) {
            Log.e("TAG", "ERROR CREATING NODE FROM LOCAL MEMORY");
        }
        return rootNode;
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

    public static void setUncheckedButton(List<ToggleButton> buttons, Context context, ToggleButton buttonSelected) {
        buttons.stream().filter(b -> !b.equals(buttonSelected)).forEach(b -> {
            b.setChecked(false);
            b.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorbuttonbase));
            b.setTextColor(TEXT_BUTTON_COLOR);
        });
    }

    public static Long getMaxId(List<Node> yearNodeList) {
        Long max = 0L;
        for (Node year : yearNodeList) {
            for (Node month : year.getChildren()) {
                for (PlanCompletedNode plan : month.getData()) {
                    if (plan.getId() > max) {
                        max = plan.getId();
                    }
                }
            }
        }
        return max;
    }

}
