package com.andrea.belotti.brorkout.plans_creation.view;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MainMenuConstants.INTENT_DATA_MODIFY_CREATOR;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MemorizeConstants.SCHEDA;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.app_starting_menu.view.StartingMenuActivity;
import com.andrea.belotti.brorkout.model.Scheda;
import com.andrea.belotti.brorkout.plans_creation.contract.PlanCreatorActivityContract;
import com.andrea.belotti.brorkout.plans_creation.presenter.PlanCreatorActivityPresenter;

import java.util.Objects;


public class PlanCreatorActivityView extends AppCompatActivity implements PlanCreatorActivityContract.View {

    // log
    private final String tag = this.getClass().getSimpleName();


    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(tag, "Starting activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_creator);

        // Retrieve data from bundle
        boolean isUpdate = Objects.requireNonNull(getIntent().getExtras()).getBoolean(INTENT_DATA_MODIFY_CREATOR);
        Scheda chosenPlan = isUpdate ? getIntent().getExtras().getSerializable(SCHEDA, Scheda.class) : null;

        ImageView backButton = findViewById(R.id.buttonBack);

        PlanCreatorActivityPresenter presenter = new PlanCreatorActivityPresenter(this, getBaseContext());

        presenter.setFragmentActivityCreate(isUpdate, chosenPlan);

        //Click listeners
        backButton.setOnClickListener(v -> presenter.onBackClick());

    }

    @Override
    public void replaceWithStartingMenuActivity(String message) {
        Intent intent = new Intent(getBaseContext(), StartingMenuActivity.class);
        Toast toast = Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
        toast.show();
        startActivity(intent);
    }

    @Override
    public void setFragmentInView(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainerViewScheduleCreator, fragment);
        fragmentTransaction.commit();
    }
}