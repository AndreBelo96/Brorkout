package com.andrea.belotti.brorkout.presenter.login;

import static com.andrea.belotti.brorkout.constants.ExerciseConstants.PersonalData.IMAGE_DATA;
import static com.andrea.belotti.brorkout.constants.ExerciseConstants.PreferencesConstants.USERNAME_PREFERENCES;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.contract.manager.StartingMenuContract;
import com.andrea.belotti.brorkout.utils.ImageUtils;

public class StartingMenuPresenter implements StartingMenuContract.Presenter {

    private final StartingMenuContract.View view;
    private final Context context;
    private final SharedPreferences pref;

    public StartingMenuPresenter(StartingMenuContract.View view, Context context, SharedPreferences pref) {
        this.view = view;
        this.context = context;
        this.pref = pref;
    }

    @Override
    public void onArchiveClick() {
        view.replaceWithScheduleArchiveActivity(context.getResources().getString(R.string.ArchiveSchedule));
    }

    @Override
    public void onCreatePlanClick() {
        view.replaceWithScheduleCreatorActivity(context.getResources().getString(R.string.CreateSchedule));
    }

    @Override
    public void onPersonalAreaClick() {
        view.replaceWithPersonalAreaActivity(context.getResources().getString(R.string.PersonalArea));
    }

    @Override
    public void onSelectionClick() {
        view.replaceWithSelectionPlanActivity(context.getResources().getString(R.string.SelectSchedule));
    }

    @Override
    public void onOptionClick() {
        view.replaceWithOptionActivity(context.getResources().getString(R.string.OptionSchedule));
    }

    @Override
    public String getUsername() {
        return pref.getString(USERNAME_PREFERENCES, USERNAME_PREFERENCES);
    }

    @Override
    public Bitmap getImage() {
        Bitmap personalImage = ImageUtils.covertFromStringToBitmap(pref.getString(IMAGE_DATA, ""));

        return personalImage != null ? personalImage : BitmapFactory.decodeResource(context.getResources(), R.drawable.immagine_profilo_vuoto);
    }
}
