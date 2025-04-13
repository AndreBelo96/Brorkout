package com.andrea.belotti.brorkout.utils.constants;

import android.widget.LinearLayout;
import android.widget.Toast;

public final class ExerciseConstants {

    public static final String TAG_START_ACTIVITY = "Starting activity";
    public static final String TAG_START_FRAGMENT = "Starting fragment";
    public static final String TAG_LOGIN_ACTIVITY = "Login Activity";
    public static final String ERROR_ARGUMENT = "Arguments is null";
    public static final String DATA_ARGUMENT_NULL = "A data in arguments is null";
    public static final String PREFERENCES_NAME = "MySharedPref";

    private static final String UTILITY_CLASS = "Utility class";



    private ExerciseConstants() {
        throw new IllegalStateException(UTILITY_CLASS);
    }

    public static final class PersonalData {

        public static final String WEIGHT = "weight";
        public static final String HEIGHT = "height";
        public static final String FAT_PERCENTILE = "fat_percentile";
        public static final String IMAGE_DATA = "image_data";

        private PersonalData() {
        }

    }

    public static final class ExeType {

        public static final String INCREMENTALE = "Incrementale";
        public static final String SERIE = "Serie";
        public static final String TENUTA = "Tenuta";
        public static final String PIRAMIDALE = "Piramidale";

        private ExeType() {
        }

    }

    public static final class GridLayoutDimension {

        public static final int MONTHS_NUMBER = 12;
        public static final int DAYS_NUMBER = 7;

        private GridLayoutDimension() {
        }

    }

    public class MainMenuConstants {

        public static final String INTENT_DATA_MODIFY_CREATOR = "modifica";
    }

    public class TableName {
        public static final String USERS_TABLE = "Users";
        public static final String PLANS_TABLE = "Plans";
        public static final String METADATA_TABLE = "Metadata";
    }

    public class PreferencesConstants {
        public static final String EMAIL_PREFERENCES = "Email";
        public static final String PASSWORD_PREFERENCES = "Password";
        public static final String REMEMBER_ME_PREFERENCES = "RememberMe";
        public static final String USERNAME_PREFERENCES = "Username";
    }

    public class LoginConstants {
        public static final String NOT_VALID_EMAIL = "Campo email non valido";
        public static final String NOT_VALID_PASSWORD = "Campo password non valido";
        public static final String SUCCESSFULLY_SIGN_IN = "Successfully signed in";
        public static final String UNSUCCESSFULLY_SIGN_IN = "Sign in failed!";
        public static final int MIN_PASSWORD_CHAR = 8;
    }

    public final class DataBase {

        public static final String CLOUD = "Cloud";
        public static final String LOCAL = "Local";
        public static final String PUBLIC = "Public";
        public static final String PRIVATE = "Private";

        private DataBase() {
            throw new IllegalStateException(UTILITY_CLASS);
        }
    }

    public final class Color {

        public static final int BUTTON_COLOR = 0xFF162955;
        public static final int BUTTON_PRESSED_COLOR = 0xFF62739A;
        public static final int TEXT_BUTTON_COLOR = 0xFF162955;
        public static final int EXE_OK_COLOR = 0x71735F2E;
        public static final int EXE_KO_COLOR = 0x71733B2E;

        private Color() {
            throw new IllegalStateException(UTILITY_CLASS);
        }

    }

    public final class Size {

        public static final float NORMAL_SIZE = 20f;

        private Size() {
            throw new IllegalStateException(UTILITY_CLASS);
        }

    }

    public final class Layout {
        LinearLayout.LayoutParams wrapParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        );

        LinearLayout.LayoutParams matchParents = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f
        );
        //TODO
        //wrapParams.setMargins(2, 0, 2, 0);
    }

    public final class MemorizeConstants {

        public static final String NUMERO_GIORNATE = "numeroGiornate";
        public static final String TITOLO_SCHEDA = "titoloScheda";
        public static final String SCHEDA = "Scheda";
        public static final String ROOT = "ROOT";
        public static final String LISTA_SCHEDE = "ListaSchede";
        public static final String GIORNATA = "Giornata";
        public static final String ESERCIZIO = "Esercizio";
        public static final String ESERCIZI = "ListaEsercizi";
        public static final String ESERCIZIO_SCELTO = "EsercizioScelto";
        public static final String ID_USER = "UserId";
        public static final String NODE = "Node";

        private MemorizeConstants() {
            throw new IllegalStateException(UTILITY_CLASS);
        }
    }

    public final class ToastMessageConstants {

        public static final int DURATION = Toast.LENGTH_SHORT;
    }


}
