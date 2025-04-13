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
import com.andrea.belotti.brorkout.entity.Giornata;
import com.andrea.belotti.brorkout.entity.Scheda;
import com.andrea.belotti.brorkout.entity.User;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.plans_creation.adapter.ShareFriendItemAdapter;
import com.andrea.belotti.brorkout.plans_creation.contract.CreationMenuContract;
import com.andrea.belotti.brorkout.plans_creation.presenter.CreationMenuPresenter;
import com.andrea.belotti.brorkout.repository.PlanRepository;
import com.andrea.belotti.brorkout.repository.UserRepository;
import com.andrea.belotti.brorkout.utils.constants.ExerciseConstants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CreationMenuFragmentView extends Fragment implements CreationMenuContract.View {

    private final String tag = this.getClass().getSimpleName();

    private Scheda selectedPlan = null;
    private LinearLayout treePlanContainer;
    private LinearLayout infoPlanContainer;
    private LinearLayout copyPlanContainer;
    // # Share #
    private LinearLayout shareSelectionLayout;
    private LinearLayout createPlanButton;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i(tag, ExerciseConstants.TAG_START_FRAGMENT);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_creation_menu, container, false);

        // ------------------------ Initialize Variables ------------------------

        CreationMenuPresenter presenter = new CreationMenuPresenter(this);

        RecyclerView usersSharePlanContainer;
        LinearLayout shareInfoContainer;

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
        EditText friendCode = view.findViewById(R.id.friend_code);
        ImageView selectFriendBtn = view.findViewById(R.id.select_friend_btn);
        usersSharePlanContainer = view.findViewById(R.id.users_container);
        LinearLayout backShareButton = view.findViewById(R.id.back_share_button);

        // ------------------------ Logic to move in the presenter ------------------------

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

        UserRepository.getInstance().getById(currentFireBaseUser.getUid(), currentUserListener);
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
        usersSharePlanContainer.setLayoutManager(new LinearLayoutManager(getContext()));
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

        CreationCopyPlanAdapter adapter = new CreationCopyPlanAdapter(view, getContext(), PlanRepository.getInstance().getByIdCreator(currentFireBaseUser.getUid().toString()), this);

        plansContainer.setHasFixedSize(true);
        plansContainer.setLayoutManager(new LinearLayoutManager(getContext()));
        plansContainer.setAdapter(adapter);


        // ---------------------- ClickListeners ----------------------

        // ---------------------- New Schedule Click Listeners ----------------------

        newSchedule.setOnClickListener(v -> presenter.onNewPlanClick(newSchedule, buttonDaysContainer, createPlanButton, shareSelectionLayout, copyScheduleBtn, copyPlanContainer, infoPlanContainer, shareInfoContainer, daysButtonList, adapter));

        daysButtonList.forEach(b -> b.setOnClickListener(v -> presenter.onDayButtonClick(b, daysButtonList)));

        // ---------------------- Copy Schedule Click Listeners ----------------------
        copyScheduleBtn.setOnClickListener(v -> presenter.onCopyPlanClick(newSchedule, buttonDaysContainer, createPlanButton, shareSelectionLayout, copyScheduleBtn, copyPlanContainer, infoPlanContainer, shareInfoContainer, daysButtonList, adapter));

        // ---------------------- Info Schedule Click Listeners ----------------------
        backInfoButton.setOnClickListener(v -> presenter.onBackInfoClick(copyPlanContainer, infoPlanContainer, createPlanButton, shareSelectionLayout));

        // ---------------------- Share Click Listeners ----------------------

        isPersonalBtn.setOnClickListener(v -> presenter.onPersonalPlanClick(users, shareBtn, isPersonalBtn, currentFireBaseUser, currentUserListener));

        shareBtn.setOnClickListener(v -> presenter.onShareClick(createPlanButton, shareSelectionLayout, shareInfoContainer));

        backShareButton.setOnClickListener(v -> presenter.onBackShareClick(createPlanButton, shareSelectionLayout, shareInfoContainer));

        selectFriendBtn.setOnClickListener(v -> UserRepository.getInstance().getUsersByEmail(friendCode.getText().toString(), shareListener));

        // ---------------------- Create Click Listeners ----------------------
        createPlanButton.setOnClickListener(v -> presenter.onCreatePlanClick(users, planTitle));

        return view;
    }

    @Override
    public void logTagOnScreen(String message) {
        Log.e(tag, message);
        Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void replaceFragment(CreationPlanFragment fragment) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerViewScheduleCreator, fragment);
        fragmentTransaction.commit();
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