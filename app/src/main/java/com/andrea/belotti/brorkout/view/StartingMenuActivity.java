package com.andrea.belotti.brorkout.view;

import static com.andrea.belotti.brorkout.constants.ExerciseConstants.MainMenuConstants.INTENT_DATA_MODIFY_CREATOR;
import static com.andrea.belotti.brorkout.constants.ExerciseConstants.PREFERENCES_NAME;
import static com.andrea.belotti.brorkout.constants.ExerciseConstants.TAG_START_ACTIVITY;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.contract.StartingMenuContract;
import com.andrea.belotti.brorkout.presenter.StartingMenuPresenter;
import com.andrea.belotti.brorkout.view.archive.ScheduleArchiveActivity;
import com.andrea.belotti.brorkout.view.creation.ScheduleCreatorActivity;
import com.andrea.belotti.brorkout.view.personal_area.PersonalAreaActivity;
import com.andrea.belotti.brorkout.view.selection.SelectScheduleActivity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Andrea Belotti <andreablt96@hotmail.it>
 * @version 1.0
 * @since 1.0
 */
public class StartingMenuActivity extends AppCompatActivity implements StartingMenuContract.View {

    private static final String TAG = StartingMenuActivity.class.getSimpleName();
    private StartingMenuContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, TAG_START_ACTIVITY);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // ---- Initialize variables ----
        presenter = new StartingMenuPresenter(this, this.getBaseContext());

        LinearLayout startWorkoutBtn = findViewById(R.id.startWorkoutBtn);
        LinearLayout createScheduleBtn = findViewById(R.id.createScheduleBtn);
        LinearLayout archiveBtn = findViewById(R.id.archivioBtn);
        LinearLayout personalAreaBtn = findViewById(R.id.personalArea);
        LinearLayout optionBtn = findViewById(R.id.optionBtn);
        TextView username = findViewById(R.id.userNameText);
        CircleImageView image = findViewById(R.id.profile_image);

        // ---- Set Variables ----
        username.setText(presenter.getUsername());
        image.setImageBitmap(presenter.getImage());

        // ---- Click Listeners ----
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
        Intent intent = new Intent(getBaseContext(), ScheduleCreatorActivity.class);
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
    public void replaceWithOptionActivity(String message) {
        Toast toast = Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public SharedPreferences getSharedPreferences() {
        return getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
    }

}