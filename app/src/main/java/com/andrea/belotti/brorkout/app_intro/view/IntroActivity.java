package com.andrea.belotti.brorkout.app_intro.view;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.TAG_START_ACTIVITY;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.app_intro.contract.IntroContract;
import com.andrea.belotti.brorkout.app_intro.presenter.IntroPresenter;
import com.andrea.belotti.brorkout.app_login.view.LoginActivity;
import com.andrea.belotti.brorkout.app_signup.view.SignUpActivity;


public class IntroActivity extends AppCompatActivity implements IntroContract.View {

    // log
    private final String tag = this.getClass().getSimpleName();
    private IntroContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(tag, TAG_START_ACTIVITY);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // ---- Initialize variables ----
        presenter = new IntroPresenter(this, this.getBaseContext());

        LinearLayout loginBtn = findViewById(R.id.login);
        LinearLayout signUpBtn = findViewById(R.id.signup);

        loginBtn.setOnClickListener(v -> presenter.onLoginCLick());

        signUpBtn.setOnClickListener(v -> presenter.onSignUpClick());
    }

    @Override
    public void replaceWithLoginActivity(String message) {
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        Toast toast = Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
        toast.show();
        startActivity(intent);
    }

    @Override
    public void replaceWithSignUpActivity(String message) {
        Intent intent = new Intent(getBaseContext(), SignUpActivity.class);
        Toast toast = Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
        toast.show();
        startActivity(intent);
    }
}