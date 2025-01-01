package com.andrea.belotti.brorkout.utils;

import java.util.HashMap;
import java.util.Map;

public class ExeTypeCastUtil {

    public class Exetype {

        // Declaring the static map
        public static Map<Integer, String> map;

        // Instantiating the static map
        static
        {
            map = new HashMap<>();
            map.put(0, "Serie");
            map.put(1, "Incrementale");
            map.put(2, "Piramidale");
            map.put(3, "Tenuta");
        }

    }
}
