package com.andrea.belotti.brorkout.app_signup.presenter;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.LoginConstants.MIN_PASSWORD_CHAR;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.LoginConstants.NOT_VALID_EMAIL;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.LoginConstants.NOT_VALID_PASSWORD;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.LoginConstants.SUCCESSFULLY_SIGN_IN;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.LoginConstants.UNSUCCESSFULLY_SIGN_IN;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.PreferencesConstants.EMAIL_PREFERENCES;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.PreferencesConstants.PASSWORD_PREFERENCES;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.PreferencesConstants.REMEMBER_ME_PREFERENCES;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.PreferencesConstants.USERNAME_PREFERENCES;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.TAG_LOGIN_ACTIVITY;
import static com.andrea.belotti.brorkout.utils.AppMethodsUtils.isValidEmail;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.TableName.METADATA_TABLE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.app_signup.contract.SignupContract;
import com.andrea.belotti.brorkout.model.User;
import com.andrea.belotti.brorkout.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupPresenter implements SignupContract.Presenter {

    private final SignupContract.View view;
    private final Context context;
    private final SharedPreferences pref;
    private final FirebaseAuth firebaseAuth;

    public SignupPresenter(SignupContract.View view, Context context, SharedPreferences pref) {
        this.view = view;
        this.context = context;
        this.pref = pref;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onSignUpClick(String username, String email, String password, String repeatedPassword, boolean rememberMe) {

        if (email.isEmpty() || !isValidEmail(email)) {
            view.writeMessage(NOT_VALID_EMAIL);
            return;
        }

        if (password.isEmpty() || password.length() < MIN_PASSWORD_CHAR) {
            view.writeMessage(NOT_VALID_PASSWORD);
            return;
        }

        if (repeatedPassword.isEmpty() || !password.equals(repeatedPassword)) {
            view.writeMessage(NOT_VALID_PASSWORD);
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(view.getActivity(), task -> {
                    if (task.isSuccessful()) {

                        view.writeMessage(SUCCESSFULLY_SIGN_IN);
                        Log.d(TAG_LOGIN_ACTIVITY, "signUpWithEmail:success");

                        final SharedPreferences.Editor editor = pref.edit();

                        if (rememberMe) {
                            editor.putString(EMAIL_PREFERENCES, email);
                            editor.putString(PASSWORD_PREFERENCES, password);
                            editor.putBoolean(REMEMBER_ME_PREFERENCES, true);
                        }

                        DatabaseReference friendTableRef =
                                FirebaseDatabase.getInstance().getReference(METADATA_TABLE).child("friend_id").child("latest");
                        
                        // Attach a listener to read the data at our posts reference
                        friendTableRef.get().addOnCompleteListener(taskFriend -> {

                            if (taskFriend.isSuccessful()) {

                                if (taskFriend.getResult().exists()) {

                                    DataSnapshot dataSnapshot = taskFriend.getResult();
                                    long friendId = (long)dataSnapshot.getValue();

                                    User user = User.createUser(username, email, (friendId + 1));
                                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                                    assert currentUser != null;

                                    String id = currentUser.getUid();

                                    user.setId(id);

                                    UserRepository.getInstance().saveUser(id, user);
                                    friendTableRef.setValue(friendId + 1);

                                    UserRepository.getInstance().getById(firebaseAuth.getCurrentUser().getUid());

                                    editor.putString(USERNAME_PREFERENCES, username);
                                    editor.apply();

                                    view.replaceWithStartingMenuActivity(context.getResources().getString(R.string.StartingMenuActivity));

                                }
                            }
                        });

                    } else {
                        view.writeMessage(UNSUCCESSFULLY_SIGN_IN);
                        Log.w(TAG_LOGIN_ACTIVITY, "signUpWithEmail:failure", task.getException());
                    }
                });


    }

    @Override
    public void onBackClick() {
        view.replaceWithIntroActivity(context.getResources().getString(R.string.IntroActivity));
    }

    @Override
    public void onLoginCLick() {
        view.replaceWithLoginActivity(context.getResources().getString(R.string.LoginActivity));
    }

}
