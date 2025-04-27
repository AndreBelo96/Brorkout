package com.andrea.belotti.brorkout.utils;

import android.util.Log;

import com.andrea.belotti.brorkout.model.Giornata;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.model.InterfaceAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class JsonGeneratorUtil {

    // log
    private static final String TAG = "JsonGeneratorUtil";

    public static String generateJsonFromObject(Object o) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Esercizio.class, new InterfaceAdapter());
        Gson gson = builder.create();
        String json = gson.toJson(o);
        Log.i(TAG, "Json: " + json);
        return json;
    }


    public static Giornata generateGiornataFromJson(String jsonString) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Esercizio.class, new InterfaceAdapter());
        return builder.create().fromJson(jsonString, Giornata.class);
    }


}
