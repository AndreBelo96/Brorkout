package com.andrea.belotti.brorkout.plans_creation.view;

import static com.andrea.belotti.brorkout.utils.GenerateDrawableObjUtils.createBasicTextView;
import static com.andrea.belotti.brorkout.utils.GenerateDrawableObjUtils.createHorizotalLinearLayout;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.adapter.CreationCopyPlanAdapter;
import com.andrea.belotti.brorkout.entity.Scheda;
import com.andrea.belotti.brorkout.entity.User;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.entity.Giornata;
import com.andrea.belotti.brorkout.plans_creation.adapter.ShareFriendItemAdapter;
import com.andrea.belotti.brorkout.repository.PlanRepository;
import com.andrea.belotti.brorkout.repository.UserRepository;
import com.andrea.belotti.brorkout.utils.ScheduleCreatingUtils;
import com.andrea.belotti.brorkout.plans_creation.CreateSingleton;
import com.andrea.belotti.brorkout.utils.constants.ExerciseConstants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CreationMenuFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();
    CreateSingleton singletonProva;
    private Boolean isNew = false;
    private Boolean isCopy = false;
    private Scheda selectedPlan = null;
    private String day = "";
    private LinearLayout treePlanContainer;
    private LinearLayout infoPlanContainer;
    private LinearLayout copyPlanContainer;
    // # Share #
    private LinearLayout shareSelectionLayout;
    private LinearLayout shareInfoContainer;
    private RecyclerView usersSharePlanContainer;
    private LinearLayout createPlanButton;
    private PlanRepository planRepo;
    private UserRepository repoUser;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i(tag, ExerciseConstants.TAG_START_FRAGMENT);

        singletonProva = CreateSingleton.getInstance();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_creation_menu, container, false);

        Context context = getContext();

        planRepo = new PlanRepository();
        repoUser = new UserRepository();

        // ------------------------ Initialize Variables ------------------------

        // Initialize Title text
        EditText planTitle = view.findViewById(R.id.titoloScheda);

        // Initialize New Schedule layout
        LinearLayout newSchedule = view.findViewById(R.id.new_schedule_button);
        LinearLayout buttonDaysContainer = view.findViewById(R.id.dayNumberContainer);
        LinearLayout buttonDay1 = view.findViewById(R.id.buttonDay1);
        LinearLayout buttonDay2 = view.findViewById(R.id.buttonDay2);
        LinearLayout buttonDay3 = view.findViewById(R.id.buttonDay3);
        LinearLayout buttonDay4 = view.findViewById(R.id.buttonDay4);
        LinearLayout buttonDay5 = view.findViewById(R.id.buttonDay5);
        LinearLayout buttonDay6 = view.findViewById(R.id.buttonDay6);
        LinearLayout buttonDay7 = view.findViewById(R.id.buttonDay7);

        // Initialize Copy Schedule layout
        LinearLayout copyScheduleBtn = view.findViewById(R.id.copy_schedule_button);
        copyPlanContainer = view.findViewById(R.id.copy_plan_container);
        RecyclerView plansContainer = view.findViewById(R.id.list_plans_container);

        // Initialize info-plan layout
        infoPlanContainer = view.findViewById(R.id.info_container);
        treePlanContainer = view.findViewById(R.id.tree_container);
        LinearLayout backInfoButton = view.findViewById(R.id.back_info_button);

        // Initialize Share layout
        shareSelectionLayout = view.findViewById(R.id.sharedLayout);
        CheckBox isPersonalBtn = view.findViewById(R.id.isPersonal);
        LinearLayout shareBtn = view.findViewById(R.id.share_btn);

        // Initialize share-plan layout
        shareInfoContainer = view.findViewById(R.id.share_container);
        EditText email = view.findViewById(R.id.email_amico);
        ImageView selectFriendBtn = view.findViewById(R.id.select_friend_btn);
        usersSharePlanContainer = view.findViewById(R.id.users_container);
        LinearLayout backShareButton = view.findViewById(R.id.back_share_button);

        List<User> users = new ArrayList<>();
        FirebaseUser currentFireBaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // CurrentUser listener
        ValueEventListener currentUserListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        User user = dataSnapshot.getValue(User.class);
                        users.add(user);
                    }

                    Toast toast = Toast.makeText(getContext(), "Utente aggiunto", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getContext(), "Utente Insesistente", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        repoUser.getById(currentFireBaseUser.getUid(), currentUserListener);

        ShareFriendItemAdapter shareAdapter = new ShareFriendItemAdapter(getContext(), users);

        // Share listener
        ValueEventListener shareListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        User user = dataSnapshot.getValue(User.class);

                        if (users.stream().anyMatch(u -> user.getEmail().equals(u.getEmail()))) {
                            Toast toast = Toast.makeText(getContext(), "Utente già presente nella lsita", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                        }

                        users.add(user);
                    }

                    shareAdapter.notifyItemInserted(users.size());
                    Toast toast = Toast.makeText(getContext(), "Utente aggiunto", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getContext(), "Utente Insesistente", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        usersSharePlanContainer.setHasFixedSize(true);
        usersSharePlanContainer.setLayoutManager(new LinearLayoutManager(context));
        usersSharePlanContainer.setAdapter(shareAdapter);


        // Initialize Create Button
        createPlanButton = view.findViewById(R.id.confirm_button);


        // ------------------------ Set Variables ------------------------

        List<LinearLayout> daysButtonList = new ArrayList<>();
        daysButtonList.add(buttonDay1);
        daysButtonList.add(buttonDay2);
        daysButtonList.add(buttonDay3);
        daysButtonList.add(buttonDay4);
        daysButtonList.add(buttonDay5);
        daysButtonList.add(buttonDay6);
        daysButtonList.add(buttonDay7);

        CreationCopyPlanAdapter adapter;



        adapter = new CreationCopyPlanAdapter(
                view,
                context,
                planRepo.getByIdCreator(currentFireBaseUser.getUid().toString()),
                this);
        plansContainer.setHasFixedSize(true);
        plansContainer.setLayoutManager(new LinearLayoutManager(context));
        plansContainer.setAdapter(adapter);


        // ---------------------- ClickListeners ----------------------

        // ---------------------- New Schedule Click Listeners ----------------------
        newSchedule.setOnClickListener(v -> {
            isNew = !isNew;
            isCopy = false;

            if (isNew) {
                newSchedule.setBackgroundResource(R.drawable.basic_button_pressed_bg);
                buttonDaysContainer.setVisibility(View.VISIBLE);
                createPlanButton.setVisibility(View.VISIBLE);
                shareSelectionLayout.setVisibility(View.VISIBLE);
            } else {
                newSchedule.setBackgroundResource(R.drawable.blue_top_button); //TODO metodo
                buttonDaysContainer.setVisibility(View.GONE);
                createPlanButton.setVisibility(View.GONE);
                shareSelectionLayout.setVisibility(View.GONE);
                day = "";
                ScheduleCreatingUtils.setBasicColor(daysButtonList);
            }

            copyScheduleBtn.setBackgroundResource(R.drawable.blue_top_button);
            copyPlanContainer.setVisibility(View.GONE);
            infoPlanContainer.setVisibility(View.GONE);
            shareInfoContainer.setVisibility(View.GONE);
            selectedPlan = null;
            ScheduleCreatingUtils.setCardViewBasicColor(adapter.getCardViewList());

        });

        daysButtonList.forEach(b -> b.setOnClickListener(v -> {
            day = ((TextView) b.getChildAt(0)).getText().toString();
            ScheduleCreatingUtils.setBasicColor(daysButtonList);
            b.setBackgroundResource(R.drawable.basic_button_pressed_bg);
        }));

        // ---------------------- Copy Schedule Click Listeners ----------------------
        copyScheduleBtn.setOnClickListener(v -> {

            isCopy = !isCopy;
            isNew = false;

            if (isCopy) {
                copyScheduleBtn.setBackgroundResource(R.drawable.basic_button_pressed_bg);
                createPlanButton.setVisibility(View.VISIBLE);
                shareSelectionLayout.setVisibility(View.VISIBLE);
                copyPlanContainer.setVisibility(View.VISIBLE);
                infoPlanContainer.setVisibility(View.GONE);
                shareInfoContainer.setVisibility(View.GONE);
            } else {
                copyScheduleBtn.setBackgroundResource(R.drawable.blue_top_button);
                createPlanButton.setVisibility(View.GONE);
                shareSelectionLayout.setVisibility(View.GONE);
                copyPlanContainer.setVisibility(View.GONE);
                infoPlanContainer.setVisibility(View.GONE);
                shareInfoContainer.setVisibility(View.GONE);
                ScheduleCreatingUtils.setCardViewBasicColor(adapter.getCardViewList());
                selectedPlan = null;
            }

            newSchedule.setBackgroundResource(R.drawable.blue_top_button);
            buttonDaysContainer.setVisibility(View.GONE);
            infoPlanContainer.setVisibility(View.GONE);
            shareInfoContainer.setVisibility(View.GONE);
            day = "";
            ScheduleCreatingUtils.setBasicColor(daysButtonList);

        });

        // ---------------------- Info Schedule Click Listeners ----------------------
        backInfoButton.setOnClickListener(v -> {
            infoPlanContainer.setVisibility(View.GONE);
            copyPlanContainer.setVisibility(View.VISIBLE);
            createPlanButton.setVisibility(View.VISIBLE);
            shareSelectionLayout.setVisibility(View.VISIBLE);
        });

        // ---------------------- Share Click Listeners ----------------------

        isPersonalBtn.setOnClickListener(v -> {

            if (isPersonalBtn.isChecked()) {
                shareBtn.setVisibility(View.INVISIBLE);
                users.clear();
                repoUser.getById(currentFireBaseUser.getUid(), currentUserListener);
            } else {
                shareBtn.setVisibility(View.VISIBLE);
                users.clear();
            }
        });

        shareBtn.setOnClickListener(v -> {
            shareInfoContainer.setVisibility(View.VISIBLE);
            shareSelectionLayout.setVisibility(View.GONE);
            createPlanButton.setVisibility(View.GONE);
        });

        backShareButton.setOnClickListener(v -> {
            shareInfoContainer.setVisibility(View.GONE);
            shareSelectionLayout.setVisibility(View.VISIBLE);
            createPlanButton.setVisibility(View.VISIBLE);
        });

        selectFriendBtn.setOnClickListener(v -> {

            repoUser.getUsersByEmail(email.getText().toString(), shareListener);

        });

        // ---------------------- Create Click Listeners ----------------------
        createPlanButton.setOnClickListener(v -> {

            if (isNew) {
                String scheduleName = planTitle.getText().toString();

                if (scheduleName.isEmpty()) {
                    Log.e(tag, "Titolo scheda vuoto");
                    Toast toast = Toast.makeText(context, "Titolo scheda vuoto", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if (day.isEmpty()) {
                    Log.e(tag, "Numero di giorni non selezionato");
                    Toast toast = Toast.makeText(context, "Numero di giorni non selezionato", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                // Save Users in singleton
                singletonProva.setUsersToShare(users);

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerViewScheduleCreator, CreationPlanFragment.newInstance(scheduleName, Integer.parseInt(day)));
                fragmentTransaction.commit();
            }

            if (isCopy) {

                String scheduleName = planTitle.getText().toString();

                if (scheduleName.isEmpty()) {
                    Log.e(tag, "Titolo scheda vuoto");
                    Toast toast = Toast.makeText(context, "Titolo scheda vuoto", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                if (selectedPlan == null) {
                    Log.e(tag, "Scheda da clonare non scelta");
                    Toast toast = Toast.makeText(context, "Scheda da clonare non scelta", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerViewScheduleCreator, CreationPlanFragment.newInstance(scheduleName, selectedPlan.getGiornate().size(), selectedPlan));
                fragmentTransaction.commit();
            }

        });

        return view;
    }

    public void setInfoPlan(Scheda plan, View view, Context context) {
        TextView planTitle = view.findViewById(R.id.plan_title);
        planTitle.setText(plan.getNome());

        treePlanContainer.removeAllViews();

        for (Giornata day : plan.getGiornate()) {
            LinearLayout dayLayout = createHorizotalLinearLayout(context, 0, 0, 0, 0);
            dayLayout.setGravity(Gravity.CENTER);
            TextView dayName = createBasicTextView(context, "Giornata " + day.getNumberOfDays(), 20f);

            ImageView dayImageView = new ImageView(context);
            dayImageView.setBackgroundResource(R.drawable.point_tree_view);
            dayImageView.setPadding(0, 0, 0, 0);

            dayLayout.addView(dayImageView);
            dayLayout.addView(dayName);

            treePlanContainer.addView(dayLayout);

            for (Esercizio exe : day.getExercises()) {
                LinearLayout exeLayout = createHorizotalLinearLayout(context, 0, 40, 0, 0);
                TextView exeName = createBasicTextView(context, "- " + exe.getName(), 18f);

                exeLayout.addView(exeName);

                treePlanContainer.addView(exeLayout);
            }

        }


        shareSelectionLayout.setVisibility(View.GONE);
        copyPlanContainer.setVisibility(View.GONE);
        createPlanButton.setVisibility(View.GONE);
        infoPlanContainer.setVisibility(View.VISIBLE);

    }

    public void setSelectedPlan(Scheda selectedPlan) {
        this.selectedPlan = selectedPlan;
    }


}