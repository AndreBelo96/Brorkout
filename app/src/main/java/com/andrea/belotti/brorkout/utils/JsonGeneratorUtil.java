package com.andrea.belotti.brorkout.utils;

import android.util.Log;

import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.model.InterfaceAdapter;
import com.andrea.belotti.brorkout.model.Scheda;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class JsonGeneratorUtil {

    // log
    private static final String TAG = "JsonGeneratorUtil";

    public static String generateJsonFromSchedule(Scheda scheda) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Esercizio.class, new InterfaceAdapter());
        Gson gson = builder.create();
        String json = gson.toJson(scheda);
        Log.i(TAG, "Json: " + json);
        return json;
    }

    public static Scheda generateScheduleFromJson(String jsonString) throws JsonProcessingException {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Esercizio.class, new InterfaceAdapter());
        Gson gson = builder.create();
        Scheda scheda = gson.fromJson(jsonString, Scheda.class);
        return scheda;
    }
}
