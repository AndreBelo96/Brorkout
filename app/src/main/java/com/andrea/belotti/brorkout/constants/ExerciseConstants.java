package com.andrea.belotti.brorkout.constants;

import android.widget.LinearLayout;

public final class ExerciseConstants {

    private static final String UTILITY_CLASS = "Utility class";

    private ExerciseConstants() {
        throw new IllegalStateException(UTILITY_CLASS);
    }

    public static final String[] recoverList = {"5","10","15","20","25","30","35","40","45","50","55","60","65","70","75","80","85","90"};

    public static final  String TAG_START_ACTIVITY = "Starting sctivity";

    public static final  String TAG_START_FRAGMENT = "Starting fragment";

    public static final  String GIORNATA = "Giornata: ";

    public final class DataBase {

        private DataBase() {
            throw new IllegalStateException(UTILITY_CLASS);
        }

        public static final  String CLOUD = "Cloud";
        public static final  String LOCAL = "Local";
        public static final  String PUBLIC = "Public";
        public static final  String PRIVATE = "Private";
    }

    public final class Color {

        private Color() {
            throw new IllegalStateException(UTILITY_CLASS);
        }

        public static final int BUTTON_COLOR = 0xFF162955;
        public static final int BUTTON_PRESSED_COLOR = 0xFF061539;
        public static final int TEXT_BUTTON_COLOR = 0xFF7887AB;
        public static final int EXE_OK_COLOR = 0x71735F2E;
        public static final int EXE_KO_COLOR = 0x71733B2E;
    }

    public final class Size {

        private Size() {
            throw new IllegalStateException(UTILITY_CLASS);
        }

        public static final float NORMAL_SIZE = 20f;

    }

    public final class Layout {
        LinearLayout.LayoutParams wrapParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        );
        //TODO
        //wrapParams.setMargins(2, 0, 2, 0);
    }

    public final class MemorizeConstants {

        private MemorizeConstants() {
            throw new IllegalStateException(UTILITY_CLASS);
        }

        public static final String NUMERO_GIORNATE = "numeroGiornate";
        public static final String TITOLO_SCHEDA = "titoloScheda";
        public static final String SCHEDA = "Scheda";
        public static final String TENUTA = "Tenuta";
    }

}
