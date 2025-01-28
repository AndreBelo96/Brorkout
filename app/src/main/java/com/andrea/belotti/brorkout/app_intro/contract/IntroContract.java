package com.andrea.belotti.brorkout.app_intro.contract;

public interface IntroContract {

    interface Presenter {
        void onLoginCLick();

        void onSignUpClick();
    }

    interface View {
        void replaceWithLoginActivity(String message);

        void replaceWithSignUpActivity(String message);
    }
}
