package com.andrea.belotti.brorkout.plans_creation.view;

import static com.andrea.belotti.brorkout.utils.JsonGeneratorUtil.generateJsonFromObject;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MemorizeConstants.SCHEDA;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.entity.SchedaDTO;
import com.andrea.belotti.brorkout.model.Giornata;
import com.andrea.belotti.brorkout.app_starting_menu.view.StartingMenuActivity;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.entity.Scheda;
import com.andrea.belotti.brorkout.plans_creation.contract.PlanCreatorContract;
import com.andrea.belotti.brorkout.plans_creation.presenter.PlanCreatorPresenter;
import com.andrea.belotti.brorkout.repository.PlanRepository;
import com.andrea.belotti.brorkout.utils.JsonGeneratorUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;



public class PlanCreatorActivity extends AppCompatActivity implements PlanCreatorContract.View {

    // Storing data into SharedPreferences
    private static SharedPreferences sharedPreferences;
    // log
    private final String tag = this.getClass().getSimpleName();

    PlanRepository repo;

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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_creator);

        // TODO fare nel singleton
        Bundle inputData = getIntent().getExtras().getBundle("SchedaDati");
        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

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

        // MAPPING
        SchedaDTO dto = new SchedaDTO();

        List<String> giornate = new ArrayList<>();

        for (Giornata day : scheda.getGiornate()) {
            giornate.add(generateJsonFromObject(day));
        }

        // TODO Fai costruttore

        dto.setGiornate(giornate);
        dto.setNumeroGiornate(scheda.getNumeroGiornate());
        dto.setNome(scheda.getNome());
        dto.setCreationDate(scheda.getCreationDate());
        dto.setUpdateDate(scheda.getUpdateDate());
        dto.setIdCreator(scheda.getIdCreator());
        dto.setIdUser(scheda.getIdUser());

        // DATABASE
        repo.insertPlan(dto);

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