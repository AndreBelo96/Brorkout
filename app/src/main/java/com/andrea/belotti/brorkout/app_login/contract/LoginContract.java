package com.andrea.belotti.brorkout.app_login.contract;

import android.app.Activity;

public interface LoginContract {

    interface Presenter {
        void onLoginCLick(String email, String password, boolean rememberMe);
        void onSignUpClick();
        void onBackClick();
    }

    interface View {
        void replaceWithStartingMenuActivity(String message);
        void replaceWithSignupActivity(String message);
        void replaceWithIntroActivity(String message);
        void writeMessage(String message);
        Activity getActivity();
    }
}
