package com.andrea.belotti.brorkout.presenter.manager;

import android.content.Context;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.contract.manager.IntroContract;

public class IntroPresenter implements IntroContract.Presenter {


    private final IntroContract.View view;
    private final Context context;

    public IntroPresenter(IntroContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void onLoginCLick() {
        view.replaceWithLoginActivity(context.getResources().getString(R.string.LoginActivity));
    }

    @Override
    public void onSignUpClick() {
        view.replaceWithSignUpActivity(context.getResources().getString(R.string.SignUpActivity));
    }
}
