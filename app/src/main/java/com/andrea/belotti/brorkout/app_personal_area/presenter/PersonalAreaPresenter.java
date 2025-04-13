package com.andrea.belotti.brorkout.app_personal_area.presenter;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.PersonalData.IMAGE_DATA;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.PreferencesConstants.USERNAME_PREFERENCES;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.EditText;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.app_personal_area.contract.PersonalAreaContract;
import com.andrea.belotti.brorkout.utils.ImageUtils;
import com.andrea.belotti.brorkout.utils.constants.ExerciseConstants;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalAreaPresenter implements PersonalAreaContract.Presenter {

    private final PersonalAreaContract.View view;
    private final SharedPreferences sharedPreferences;
    private final Context context;

    public PersonalAreaPresenter(PersonalAreaContract.View view, Context context, SharedPreferences sharedPreferences) {
        this.view = view;
        this.context = context;
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void onBackClick() {
        view.replaceWithStartingMenuActivity(context.getResources().getString(R.string.StartingMenuActivity));
    }

    @Override
    public void initData(EditText usernameET, EditText weightET, EditText heightET, EditText fatPercentileET, CircleImageView image) {

        usernameET.setText(sharedPreferences.getString(USERNAME_PREFERENCES, "Username"));
        weightET.setText(sharedPreferences.getString(ExerciseConstants.PersonalData.WEIGHT, ""));
        heightET.setText(sharedPreferences.getString(ExerciseConstants.PersonalData.HEIGHT, ""));
        fatPercentileET.setText(sharedPreferences.getString(ExerciseConstants.PersonalData.FAT_PERCENTILE, ""));

        if (ImageUtils.covertFromStringToBitmap(sharedPreferences.getString(IMAGE_DATA, "")) != null) {
            image.setImageBitmap(ImageUtils.covertFromStringToBitmap(sharedPreferences.getString(IMAGE_DATA, "")));
        }
    }






}
