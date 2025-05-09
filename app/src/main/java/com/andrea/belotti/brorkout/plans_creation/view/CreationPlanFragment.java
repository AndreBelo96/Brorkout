package com.andrea.belotti.brorkout.plans_creation.view;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MemorizeConstants.GIORNATA;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MemorizeConstants.SCHEDA;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.adapter.ViewPagerPlanGeneratorAdapter;
import com.andrea.belotti.brorkout.model.Scheda;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.plans_creation.CreateSingleton;
import com.andrea.belotti.brorkout.plans_creation.view.schedulecreator.AddExeFragment;
import com.andrea.belotti.brorkout.plans_creation.view.schedulecreator.ModifyExeFragment;
import com.andrea.belotti.brorkout.plans_execution.view.ExecutionScheduleActivity;
import com.andrea.belotti.brorkout.repository.PlanRepository;
import com.andrea.belotti.brorkout.utils.constants.ExerciseConstants;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public class CreationPlanFragment extends Fragment {

    static ViewPager2 viewPager2;
    static ViewPagerPlanGeneratorAdapter viewPagerPlanGeneratorAdapter;
    private static Scheda scheda = new Scheda();
    private final String tag = this.getClass().getSimpleName();
    TabLayout tabLayout;

    public static CreationPlanFragment newInstance(Scheda scheda) {
        CreationPlanFragment fragment = new CreationPlanFragment();
        Bundle args = new Bundle();
        args.putSerializable(SCHEDA, scheda);
        fragment.setArguments(args);
        return fragment;
    }

    public static void addExeToPlan(Esercizio exe) {
        int dayToAddExe = viewPager2.getCurrentItem();

        scheda.getGiornate().get(dayToAddExe).getExercises().add(exe);

        viewPager2.setAdapter(viewPagerPlanGeneratorAdapter);
        viewPager2.setCurrentItem(dayToAddExe);
    }

    public static void addExeToPlan(Integer numberExe) {
        scheda.getGiornate().get(viewPager2.getCurrentItem()).getExercises().set(numberExe, CreateSingleton.getInstance().getAddExeInCreation());
        viewPager2.setAdapter(viewPagerPlanGeneratorAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(tag, "Starting Fragment");

        super.onCreate(savedInstanceState);


        Scheda datiScheda = null;

        if (getArguments() != null) {
            datiScheda = (Scheda) getArguments().getSerializable(SCHEDA);
        }

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_creation_plan, container, false);

        Context context = getContext();

        // Initialize plan with data
        scheda = datiScheda;

        tabLayout = view.findViewById(R.id.tabDayListLayout);
        viewPager2 = view.findViewById(R.id.viewDayListPager);

        ImageView addButton = view.findViewById(R.id.addButton);
        ImageView modifyButton = view.findViewById(R.id.modifyButton);
        ImageView deleteButton = view.findViewById(R.id.deleteButton);
        ImageView copyButton = view.findViewById(R.id.copyButton);
        ImageView pasteButton = view.findViewById(R.id.pasteButton);
        LinearLayout createSchedule = view.findViewById(R.id.create_plan);

        viewPagerPlanGeneratorAdapter = new ViewPagerPlanGeneratorAdapter(this, scheda); // TODO testare

        viewPager2.setAdapter(viewPagerPlanGeneratorAdapter);

        initTabLayout(tabLayout, datiScheda.getNumeroGiornate(), context);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // TODO document why this method is empty
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // TODO document why this method is empty
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

        addButton.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            //fragmentTransaction.setCustomAnimations(enter1, exit1, popEnter1, popExit1) //TODO
            fragmentTransaction.add(R.id.fragmentContainerViewScheduleCreator, AddExeFragment.newInstance());
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.hide(this);
            fragmentTransaction.commit();
        });

        modifyButton.setOnClickListener(v -> {

            List<Esercizio> tabDayExercicesList = scheda.getGiornate().get(tabLayout.getSelectedTabPosition()).getExercises();

            int exeToModify = CreateSingleton.getInstance().getSelectedExe();


            if (tabDayExercicesList.size() <= exeToModify || exeToModify < 0) {
                Toast toast = Toast.makeText(getContext(), "Exe not selected", ExerciseConstants.ToastMessageConstants.DURATION);
                toast.show();
                return;
            }

            // set selected exercise to null
            CreateSingleton.getInstance().setSelectedExe(-1);

            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragmentContainerViewScheduleCreator, ModifyExeFragment.newInstance(scheda.getGiornate().get(tabLayout.getSelectedTabPosition()), exeToModify));
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.hide(this);
            //viewPagerPlanGeneratorAdapter.resetButtonList(tabLayout.getSelectedTabPosition());
            fragmentTransaction.commit();

            // Refresh viewPager2
            viewPager2.setAdapter(viewPagerPlanGeneratorAdapter);
        });

        deleteButton.setOnClickListener(v -> {

            List<Esercizio> tabDayExercicesList = scheda.getGiornate().get(tabLayout.getSelectedTabPosition()).getExercises();

            int exeToDelete = CreateSingleton.getInstance().getSelectedExe();

            if (tabDayExercicesList.size() <= exeToDelete || exeToDelete < 0) {
                Toast toast = Toast.makeText(getContext(), "Exe not selected", ExerciseConstants.ToastMessageConstants.DURATION);
                toast.show();
                return;
            }

            // remove selected exercise in the current day
            tabDayExercicesList.remove(exeToDelete);

            // set selected exercise to null
            CreateSingleton.getInstance().setSelectedExe(-1);

            // Refresh viewPager2
            viewPager2.setAdapter(viewPagerPlanGeneratorAdapter);
        });

        copyButton.setOnClickListener(v -> {
            int currentDaySelect = viewPager2.getCurrentItem();
            CreateSingleton.getInstance().setDayToCopy(scheda.getGiornate().get(currentDaySelect));

            Toast toast = Toast.makeText(getContext(), "Giornata copiata con successo!", ExerciseConstants.ToastMessageConstants.DURATION);
            toast.show();
        });

        pasteButton.setOnClickListener(v -> {

            if (CreateSingleton.getInstance().getDayToCopy() == null) {
                Toast toast = Toast.makeText(getContext(), "Non è stato salvato nulla da incollare", ExerciseConstants.ToastMessageConstants.DURATION);
                toast.show();
                return;
            }

            int currentDaySelect = viewPager2.getCurrentItem();
            scheda.getGiornate().set(currentDaySelect, CreateSingleton.getInstance().getDayToCopy());
            viewPager2.setAdapter(viewPagerPlanGeneratorAdapter);
            viewPager2.setCurrentItem(currentDaySelect);
        });

        createSchedule.setOnClickListener(v -> {

            if (scheda.getGiornate().stream().anyMatch(day -> day.getExercises() == null || day.getExercises().isEmpty())) {
                Log.e(tag, "Esercizi vuoti");
                Toast toast = Toast.makeText(context, "Inserire almeno un esercizio per giornata", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            //TODO non va bene cosi
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            scheda.setCreationDate(LocalDate.now().toString());
            scheda.setUpdateDate(LocalDate.now().toString());
            scheda.setIdCreator(user.getUid());
            scheda.setIdUser(user.getUid());

            UUID uuid = UUID.randomUUID();
            scheda.setId(uuid.toString());

            //Salvataggio a DB e in locale
            PlanRepository.getInstance().saveData(scheda);

            Intent intent = new Intent(getActivity(), ExecutionScheduleActivity.class);
            intent.putExtra(SCHEDA, scheda);
            intent.putExtra(GIORNATA, 1);
            startActivity(intent);
        });

        return view;
    }

    private void initTabLayout(TabLayout tabLayout, int numeroGiornate, Context context) {

        for (int i = 0; i < numeroGiornate; i++) {
            TabItem tabItem = new TabItem(context);
            tabLayout.addView(tabItem);
            tabLayout.getTabAt(i).setText("" + (i + 1));
        }
    }

}