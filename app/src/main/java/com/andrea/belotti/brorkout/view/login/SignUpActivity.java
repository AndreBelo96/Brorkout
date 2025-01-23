package com.andrea.belotti.brorkout.view.login;

import static com.andrea.belotti.brorkout.constants.ExerciseConstants.PREFERENCES_NAME;
import static com.andrea.belotti.brorkout.constants.ExerciseConstants.TAG_START_ACTIVITY;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.contract.login.SignupContract;
import com.andrea.belotti.brorkout.presenter.login.SignupPresenter;
import com.andrea.belotti.brorkout.view.manager.IntroActivity;
import com.andrea.belotti.brorkout.view.manager.StartingMenuActivity;

public class SignUpActivity extends AppCompatActivity implements SignupContract.View{

    private final String tag = this.getClass().getSimpleName();
    private SignupContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(tag, TAG_START_ACTIVITY);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        SharedPreferences pref = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);

        // ---- Initialize variables ----
        presenter = new SignupPresenter(this, this.getBaseContext(), pref);

        LinearLayout backBtn = findViewById(R.id.back_button);
        EditText username = findViewById(R.id.editTextUsername);
        EditText email = findViewById(R.id.editTextEmail);
        EditText password = findViewById(R.id.editTextPassword);
        EditText repeatPassword = findViewById(R.id.editTextPasswordRepeated);
        CheckBox rememberMe = findViewById(R.id.remember_me_radio_btn);
        LinearLayout signupBtn = findViewById(R.id.signup);
        TextView loginBtn = findViewById(R.id.login);

        // ---- Listener ----
        backBtn.setOnClickListener(v -> presenter.onBackClick());
        signupBtn.setOnClickListener(v -> presenter.onSignUpClick(username.getText().toString(), email.getText().toString(), password.getText().toString(), repeatPassword.getText().toString(), rememberMe.isChecked()));
        loginBtn.setOnClickListener(v -> presenter.onLoginCLick());

    }

    @Override
    public void replaceWithStartingMenuActivity(String message) {
        Intent intent = new Intent(getBaseContext(), StartingMenuActivity.class);
        Toast toast = Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
        toast.show();
        startActivity(intent);
    }

    @Override
    public void replaceWithLoginActivity(String message) {
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        Toast toast = Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
        toast.show();
        startActivity(intent);
    }

    @Override
    public void replaceWithIntroActivity(String message) {
        Intent intent = new Intent(getBaseContext(), IntroActivity.class);
        Toast toast = Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
        toast.show();
        startActivity(intent);
    }

    @Override
    public void writeMessage(String message) {
        Toast toast = Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}