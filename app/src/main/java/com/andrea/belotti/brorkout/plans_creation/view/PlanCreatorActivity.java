package com.andrea.belotti.brorkout.plans_creation.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.app_starting_menu.view.StartingMenuActivity;
import com.andrea.belotti.brorkout.entity.Scheda;
import com.andrea.belotti.brorkout.entity.SchedaEntity;
import com.andrea.belotti.brorkout.entity.User;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.entity.Giornata;
import com.andrea.belotti.brorkout.plans_creation.contract.PlanCreatorContract;
import com.andrea.belotti.brorkout.plans_creation.presenter.PlanCreatorPresenter;
import com.andrea.belotti.brorkout.repository.PlanRepository;
import com.andrea.belotti.brorkout.plans_creation.CreateSingleton;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class PlanCreatorActivity extends AppCompatActivity implements PlanCreatorContract.View {

    // log
    private final String tag = this.getClass().getSimpleName();

    PlanRepository repo;

    CreateSingleton singletonProva;

    // shared variables between fragments
    private Esercizio addExeInCreation;
    private Giornata dayToCopy;
    private int selectedExe = -1;
    @Getter
    @Setter
    private Scheda planToCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(tag, "Starting activity");

        singletonProva = CreateSingleton.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_creator);

        Bundle inputData = getIntent().getExtras().getBundle("SchedaDati");

        ImageView backButton = findViewById(R.id.buttonBack);

        PlanCreatorPresenter presenter = new PlanCreatorPresenter(this, getBaseContext());

        repo = new PlanRepository();

        // TODO Controllare nel presenter -> scelta tra modifica scheda esistente -> quindi apsso a creazione scheda oppure nuova scheda e non modifica -> pagina iniziale
        if (getIntent().getExtras().getBoolean("modifica")) {

            Scheda schedaScelta = (Scheda) inputData.getSerializable("Scheda");

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerViewScheduleCreator, CreationPlanFragment.newInstance(schedaScelta.getNome(), schedaScelta.getGiornate().size(), schedaScelta));
            fragmentTransaction.commit();

        } else {
            //TODO controlal cosa succede, c'è un add -> replace(?)
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragmentContainerViewScheduleCreator, new CreationMenuFragment());
            fragmentTransaction.commit();
        }


        backButton.setOnClickListener(v -> {
            presenter.onBackClick();
        });

    }

    public void saveData(Scheda scheda) {

        // Retrieve user's list
        List<User> users = singletonProva.getUsersToShare();

        for (User user : users) {
            SchedaEntity dto = new SchedaEntity(scheda);
            dto.setIdUser(user.getId());

            // DATABASE
            repo.insertPlan(dto);
        }

    }

    public Esercizio getAddExeCreation() {
        return addExeInCreation;
    }

    public void setAddExeCreation(Esercizio addExeInCreation) {
        this.addExeInCreation = addExeInCreation;
    }

    public int getSelectedExe() {
        return selectedExe;
    }

    public void setSelectedExe(int selectedExe) {
        this.selectedExe = selectedExe;
    }

    public Giornata getDayToCopy() {
        return dayToCopy;
    }

    public void setDayToCopy(Giornata dayToCopy) {
        this.dayToCopy = dayToCopy;
    }

    @Override
    public void replaceWithStartingMenuActivity(String message) {
        Intent intent = new Intent(getBaseContext(), StartingMenuActivity.class);
        Toast toast = Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
        toast.show();
        startActivity(intent);
    }
}