package com.andrea.belotti.brorkout.app_login.presenter;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.LoginConstants.NOT_VALID_EMAIL;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.LoginConstants.NOT_VALID_PASSWORD;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.LoginConstants.SUCCESSFULLY_SIGN_IN;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.LoginConstants.UNSUCCESSFULLY_SIGN_IN;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.PreferencesConstants.EMAIL_PREFERENCES;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.PreferencesConstants.PASSWORD_PREFERENCES;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.PreferencesConstants.REMEMBER_ME_PREFERENCES;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.TAG_LOGIN_ACTIVITY;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.app_login.contract.LoginContract;
import com.andrea.belotti.brorkout.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPresenter implements LoginContract.Presenter {


    private final LoginContract.View view;
    private final Context context;
    private final SharedPreferences pref;


    public LoginPresenter(LoginContract.View view, Context context, SharedPreferences pref) {
        this.view = view;
        this.context = context;
        this.pref = pref;
    }

    @Override
    public void onLoginCLick(String email, String password, boolean rememberMe) {

        if (email.isEmpty()) {
            view.writeMessage(NOT_VALID_EMAIL);
            return;
        }

        if (password.isEmpty()) {
            view.writeMessage(NOT_VALID_PASSWORD);
            return;
        }

        // Initialize Firebase Auth
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(view.getActivity(), task -> {
                    if (task.isSuccessful()) {

                        view.writeMessage(SUCCESSFULLY_SIGN_IN);
                        Log.d(TAG_LOGIN_ACTIVITY, "signInWithEmail:success");

                        final SharedPreferences.Editor editor = pref.edit();

                        UserRepository.getInstance().getById(firebaseAuth.getCurrentUser().getUid());

                        if (rememberMe) {
                            editor.putString(EMAIL_PREFERENCES, email);
                            editor.putString(PASSWORD_PREFERENCES, password);
                            editor.putBoolean(REMEMBER_ME_PREFERENCES, true);
                            editor.apply();
                        }

                        view.replaceWithStartingMenuActivity(context.getResources().getString(R.string.StartingMenuActivity));
                    } else {

                        view.writeMessage(UNSUCCESSFULLY_SIGN_IN);
                        Log.w(TAG_LOGIN_ACTIVITY, "signInWithEmail:failure", task.getException());

                    }
                });
    }

    @Override
    public void onSignUpClick() {
        view.replaceWithSignupActivity(context.getResources().getString(R.string.SignUpActivity));
    }

    @Override
    public void onBackClick() {
        view.replaceWithIntroActivity(context.getResources().getString(R.string.IntroActivity));
    }

}
