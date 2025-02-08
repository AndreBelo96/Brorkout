package com.andrea.belotti.brorkout.utils;

import android.util.Log;

import com.andrea.belotti.brorkout.entity.Giornata;
import com.andrea.belotti.brorkout.model.nodes.Node;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.model.InterfaceAdapter;
import com.andrea.belotti.brorkout.entity.Scheda;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    public static Scheda generateScheduleFromJson(String jsonString) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Esercizio.class, new InterfaceAdapter());
        Gson gson = builder.create();
        return gson.fromJson(jsonString, Scheda.class);
    }


    public static Giornata generateGiornataFromJson(String jsonString) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Esercizio.class, new InterfaceAdapter());
        return builder.create().fromJson(jsonString, Giornata.class);
    }

    public static Node generateNodeFromJson(String jsonString) throws JsonProcessingException {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Esercizio.class, new InterfaceAdapter());
        Gson gson = builder.create();
        Node scheda = gson.fromJson(jsonString, Node.class);
        return scheda;
    }
}
