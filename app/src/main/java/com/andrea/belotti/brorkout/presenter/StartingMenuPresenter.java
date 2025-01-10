package com.andrea.belotti.brorkout.presenter;

import static com.andrea.belotti.brorkout.constants.ExerciseConstants.PersonalData.IMAGE_DATA;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.contract.StartingMenuContract;
import com.andrea.belotti.brorkout.utils.ImageUtils;

public class StartingMenuPresenter implements StartingMenuContract.Presenter {

    private final StartingMenuContract.View view;
    private final Context context;

    public StartingMenuPresenter(StartingMenuContract.View view, Context context) {
        this.view = view;
        this.context = context;
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
        SharedPreferences pref = view.getSharedPreferences();
        return pref.getString(ExerciseConstants.PersonalData.USERNAME, ExerciseConstants.PersonalData.USERNAME);
    }

    @Override
    public Bitmap getImage() {
        SharedPreferences pref = view.getSharedPreferences();
        if (ImageUtils.covertFromStringToBitmap(pref.getString(IMAGE_DATA, "")) != null) {
            return ImageUtils.covertFromStringToBitmap(pref.getString(IMAGE_DATA, ""));
        }
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.immagine_profilo_vuoto);
    }
}
