package com.andrea.belotti.brorkout.app_starting_menu.view;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MainMenuConstants.INTENT_DATA_MODIFY_CREATOR;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.PREFERENCES_NAME;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.TAG_START_ACTIVITY;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.app_intro.view.IntroActivity;
import com.andrea.belotti.brorkout.app_starting_menu.contract.StartingMenuContract;
import com.andrea.belotti.brorkout.app_starting_menu.presenter.StartingMenuPresenter;
import com.andrea.belotti.brorkout.plans_archive.view.ScheduleArchiveActivity;
import com.andrea.belotti.brorkout.plans_creation.view.PlanCreatorActivityView;
import com.andrea.belotti.brorkout.app_personal_area.view.PersonalAreaActivity;
import com.andrea.belotti.brorkout.plans_selection.view.SelectScheduleActivity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Andrea Belotti <andreablt96@hotmail.it>
 * @version 1.0
 * @since 1.0
 */
public class StartingMenuActivity extends AppCompatActivity implements StartingMenuContract.View {

    private static final String TAG = StartingMenuActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, TAG_START_ACTIVITY);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // ---- Initialize variables ----
        StartingMenuContract.Presenter presenter = new StartingMenuPresenter(this, this.getBaseContext(), getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE));

        LinearLayout startWorkoutBtn = findViewById(R.id.startWorkoutBtn);
        LinearLayout createScheduleBtn = findViewById(R.id.createScheduleBtn);
        LinearLayout archiveBtn = findViewById(R.id.archivioBtn);
        LinearLayout personalAreaBtn = findViewById(R.id.personalArea);
        LinearLayout optionBtn = findViewById(R.id.optionBtn);
        TextView username = findViewById(R.id.userNameText);
        CircleImageView image = findViewById(R.id.profile_image);
        ImageView logoutBtn = findViewById(R.id.logoutBtn);

        // ---- Set Variables ----
        username.setText(presenter.getUsername());
        image.setImageBitmap(presenter.getImage());

        // ---- Click Listeners ----
        logoutBtn.setOnClickListener(v -> presenter.onLogoutClick());
        startWorkoutBtn.setOnClickListener(v -> presenter.onSelectionClick());
        createScheduleBtn.setOnClickListener(v -> presenter.onCreatePlanClick());
        archiveBtn.setOnClickListener(v -> presenter.onArchiveClick());
        personalAreaBtn.setOnClickListener(v -> presenter.onPersonalAreaClick());
        optionBtn.setOnClickListener(v -> presenter.onOptionClick());
    }

    @Override
    public void replaceWithScheduleArchiveActivity(String message) {
        Intent intent = new Intent(getBaseContext(), ScheduleArchiveActivity.class);
        Toast toast = Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
        toast.show();
        startActivity(intent);
    }

    @Override
    public void replaceWithScheduleCreatorActivity(String message) {
        Intent intent = new Intent(getBaseContext(), PlanCreatorActivityView.class);
        intent.putExtra(INTENT_DATA_MODIFY_CREATOR, false);
        Toast toast = Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
        toast.show();
        startActivity(intent);
    }

    @Override
    public void replaceWithSelectionPlanActivity(String message) {
        Intent intent = new Intent(getBaseContext(), SelectScheduleActivity.class);
        Toast toast = Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
        toast.show();
        startActivity(intent);
    }

    @Override
    public void replaceWithPersonalAreaActivity(String message) {
        Intent intent = new Intent(getBaseContext(), PersonalAreaActivity.class);
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
    public void replaceWithOptionActivity(String message) {
        Toast toast = Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

}