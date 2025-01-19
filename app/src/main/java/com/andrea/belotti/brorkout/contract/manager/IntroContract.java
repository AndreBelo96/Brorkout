package com.andrea.belotti.brorkout.contract.manager;

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
