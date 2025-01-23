package com.andrea.belotti.brorkout.view.login;

import static com.andrea.belotti.brorkout.constants.ExerciseConstants.PREFERENCES_NAME;
import static com.andrea.belotti.brorkout.constants.ExerciseConstants.PreferencesConstants.EMAIL_PREFERENCES;
import static com.andrea.belotti.brorkout.constants.ExerciseConstants.PreferencesConstants.PASSWORD_PREFERENCES;
import static com.andrea.belotti.brorkout.constants.ExerciseConstants.PreferencesConstants.REMEMBER_ME_PREFERENCES;
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
import com.andrea.belotti.brorkout.contract.login.LoginContract;
import com.andrea.belotti.brorkout.presenter.login.LoginPresenter;
import com.andrea.belotti.brorkout.view.manager.IntroActivity;
import com.andrea.belotti.brorkout.view.manager.StartingMenuActivity;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private final String tag = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(tag, TAG_START_ACTIVITY);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences pref = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);

        // ---- Initialize variables ----
        LoginContract.Presenter presenter = new LoginPresenter(this, this.getBaseContext(), pref);

        LinearLayout backBtn = findViewById(R.id.back_button);
        EditText email = findViewById(R.id.editTextEmail);
        EditText password = findViewById(R.id.editTextPassword);
        CheckBox rememberMe = findViewById(R.id.remember_me_radio_btn);
        LinearLayout loginBtn = findViewById(R.id.login);
        TextView signUpBtn = findViewById(R.id.signup);

        // ---- Set data ----
        email.setText(pref.getString(EMAIL_PREFERENCES, ""));
        password.setText(pref.getString(PASSWORD_PREFERENCES, ""));
        rememberMe.setChecked(pref.getBoolean(REMEMBER_ME_PREFERENCES, false));

        // ---- Listener ----
        backBtn.setOnClickListener(v -> presenter.onBackClick());
        signUpBtn.setOnClickListener(v -> presenter.onSignUpClick());
        loginBtn.setOnClickListener(v -> presenter.onLoginCLick(email.getText().toString(), password.getText().toString(), rememberMe.isChecked()));

    }

    @Override
    public void replaceWithStartingMenuActivity(String message) {
        Intent intent = new Intent(getBaseContext(), StartingMenuActivity.class);
        Toast toast = Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
        toast.show();
        startActivity(intent);
    }

    @Override
    public void replaceWithSignupActivity(String message) {
        Intent intent = new Intent(getBaseContext(), SignUpActivity.class);
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