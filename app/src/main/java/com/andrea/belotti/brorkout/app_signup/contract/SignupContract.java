package com.andrea.belotti.brorkout.app_signup.contract;

import android.app.Activity;

public interface SignupContract {

    interface Presenter {
        void onBackClick();
        void onLoginCLick();
        void onSignUpClick(String username, String email, String password, String repeatedPassword, boolean rememberMe);
    }

    interface View {
        void replaceWithStartingMenuActivity(String message);
        void replaceWithLoginActivity(String message);
        void replaceWithIntroActivity(String message);
        void writeMessage(String message);
        Activity getActivity();
    }
}
