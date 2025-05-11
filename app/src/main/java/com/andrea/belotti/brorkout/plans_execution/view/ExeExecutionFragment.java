package com.andrea.belotti.brorkout.plans_execution.view;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MemorizeConstants.GIORNATA;
import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MemorizeConstants.SCHEDA;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.model.Scheda;
import com.andrea.belotti.brorkout.model.SchedaEntity;
import com.andrea.belotti.brorkout.plans_execution.presenter.ExeExecutionPresenter;
import com.andrea.belotti.brorkout.repository.PlanRepository;
import com.andrea.belotti.brorkout.utils.constants.ExerciseConstants;

import java.time.LocalDate;
import java.util.List;

public class ExeExecutionFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();

    private Integer indexActualExe = 0;
    private ExeExecutionPresenter presenter;

    private LinearLayout buttonRecover;
    private TextView textButtonRecover;
    private LinearLayout nextExeButton;
    private LinearLayout previousExeButton;
    private LinearLayout buttonEndSchedule;
    private TextView textNameExe;
    private TextView textNumSet;
    private TextView textNumRep;
    private TextView textVideo;
    private TextView textRecover;
    private TextView textIndicazioniEsercizio;
    private EditText textAppuntiAtleta;
    private TextView nextExerciseText;
    private TextView previousExerciseText;

    // ----- Tenuta variable ----- //
    private LinearLayout layoutTempoTenuta;
    private TextView textRecoverTenNumber;
    private LinearLayout buttonTenuta;
    private TextView textButtonTenRecover;


    public static ExeExecutionFragment newInstance(Scheda scheda, Integer giorno) {
        ExeExecutionFragment fragment = new ExeExecutionFragment();
        Bundle args = new Bundle();
        args.putSerializable(SCHEDA, scheda);
        args.putInt(GIORNATA, giorno);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(tag, ExerciseConstants.TAG_START_FRAGMENT);

        View view = inflater.inflate(R.layout.fragment_exe_execution, container, false);

        if (getArguments() == null) {
            return view;
        }

        Scheda plan = (Scheda) getArguments().get(SCHEDA);
        Integer day = (Integer) getArguments().get(GIORNATA);

        if (plan == null || day == null) {
            Log.e(tag, ExerciseConstants.DATA_ARGUMENT_NULL);
            return view;
        }

        presenter = new ExeExecutionPresenter();
        List<Esercizio> esercizioList = plan.getGiornate().get(day - 1).getExercises();

        initWidgets(view);

        // ----- Setup exercises widgets ----- //
        setupExeWidgets(esercizioList);

        // ----- Click listeners ----- //
        previousExeButton.setOnClickListener(v -> onPreviousExerciseClick(esercizioList));

        nextExeButton.setOnClickListener(v -> onNextExerciseClick(esercizioList));

        buttonEndSchedule.setOnClickListener(v -> onEndSchedule(esercizioList.get(indexActualExe), plan, day));

        buttonRecover.setOnClickListener(v -> onRecoveryClick(esercizioList));

        return view;
    }

    // ----- Init and setup ----- //

    private void initWidgets(View view) {
        buttonRecover = view.findViewById(R.id.buttonRecover);
        textButtonRecover = view.findViewById(R.id.textButtonRecover);
        nextExeButton = view.findViewById(R.id.buttonNextExe);
        previousExeButton = view.findViewById(R.id.buttonPreviousExe);
        buttonEndSchedule = view.findViewById(R.id.buttonEndSchedule);
        textNameExe = view.findViewById(R.id.textNomeEsercizio);
        textNumSet = view.findViewById(R.id.textNumSerie);
        textNumRep = view.findViewById(R.id.textNumRep);
        textVideo = view.findViewById(R.id.textVideo);
        textRecover = view.findViewById(R.id.textRecover);
        textIndicazioniEsercizio = view.findViewById(R.id.textIndicazioniEsercizio);
        textAppuntiAtleta = view.findViewById(R.id.commentiAtleta);
        nextExerciseText = view.findViewById(R.id.textNextExercise);
        previousExerciseText = view.findViewById(R.id.textPreviousExercise);

        // ----- TENUTA VARIABLE ----- //
        layoutTempoTenuta = view.findViewById(R.id.LayoutTempoTenuta);
        textRecoverTenNumber = view.findViewById(R.id.textTenRecover);
        buttonTenuta = view.findViewById(R.id.buttonTenRecover);
        textButtonTenRecover = view.findViewById(R.id.textButtonTenRecover);
    }

    private void setupExeWidgets(List<Esercizio> esercizioList) {

        Esercizio actualExercise = esercizioList.get(indexActualExe);

        textNameExe.setText(actualExercise.getName());
        textNumSet.setText(actualExercise.getSetForExecution());
        textNumRep.setText(actualExercise.getRepForExecution());
        textRecover.setText(actualExercise.getRecForExecution());
        nextExerciseText.setText(presenter.setNextExerciseName(esercizioList, indexActualExe));
        previousExerciseText.setText(presenter.setPreviousExerciseName(esercizioList, indexActualExe));
        textVideo.setVisibility(presenter.setVideoVisibility(actualExercise));
        textIndicazioniEsercizio.setText(new StringBuilder("Indicazioni: ")
                .append(esercizioList.get(indexActualExe).getIndicazioniCoach())
        );
        textAppuntiAtleta.setText(esercizioList.get(indexActualExe).getAppuntiAtleta());
        layoutTempoTenuta.setVisibility(View.GONE);

        if (actualExercise.isExeTypeTenuta()) {
            setVisibilityOfTenutaExe(actualExercise);
        }
    }

    private void setVisibilityOfTenutaExe(Esercizio esercizio) {

        textRecoverTenNumber.setText(new StringBuilder("Tempo di tenuta: ").append(esercizio.getTempoEsecuzione()));

        Long countDown = Long.parseLong(esercizio.getTempoEsecuzione()) * 1000L;

        buttonTenuta.setOnClickListener(v -> {

            new CountDownTimer(countDown, 1000) {
                public void onTick(long millisUntilFinished) {
                    String time = (millisUntilFinished / 1000) + "";
                    textButtonTenRecover.setText(new StringBuilder(time).append("\""));
                }

                public void onFinish() {
                    textButtonTenRecover.setText(new StringBuilder("Start"));
                }
            }.start();

        });

        layoutTempoTenuta.setVisibility(View.VISIBLE);

    }

    // ----- Click listeners ----- //

    private void onNextExerciseClick(List<Esercizio> esercizioList) {
        esercizioList.get(indexActualExe).setAppuntiAtleta(textAppuntiAtleta.getText().toString());
        if ((indexActualExe + 1) < esercizioList.size()) {
            ++indexActualExe;
            setupExeWidgets(esercizioList);
        }
    }

    private void onPreviousExerciseClick(List<Esercizio> esercizioList) {
        esercizioList.get(indexActualExe).setAppuntiAtleta(textAppuntiAtleta.getText().toString());
        if ((indexActualExe) > 0) {
            --indexActualExe;
            setupExeWidgets(esercizioList);
        }
    }

    private void onEndSchedule(Esercizio actualExercise, Scheda plan, Integer indexDay) {
        actualExercise.setAppuntiAtleta(textAppuntiAtleta.getText().toString());

        // update data to DB
        plan.setUpdateDate(LocalDate.now().toString());

        plan.getGiornate().get(indexDay - 1).setUpdateDate(LocalDate.now().toString());
        plan.getGiornate().get(indexDay - 1).setUsed(true);


        PlanRepository.getInstance().updatePlan(plan.getId(), new SchedaEntity(plan));

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerViewGestoreScheda, EndScheduleSummaryFragment.newInstance(plan, indexDay - 1));
        fragmentTransaction.commit();
    }

    private void onRecoveryClick(List<Esercizio> esercizioList) {

        final MediaPlayer mpVaiUomo = MediaPlayer.create(this.getActivity(), R.raw.alarm);
        String recover = textRecover.getText().toString();
        String[] subString = recover.split(" ");
        String recoverNumber = subString[1].replace("\"", "");
        final Long[] countDown = {Long.parseLong(recoverNumber) * 1000L};

        final Esercizio[] actualExercise = {esercizioList.get(indexActualExe)};

        updateButtonClickable(actualExercise[0], false);

        if (esercizioList.get(indexActualExe).getSerieCompletate() < Integer.parseInt(esercizioList.get(indexActualExe).getSerie())) {
            countDown[0] = Long.parseLong(esercizioList.get(indexActualExe).getRecupero()) * 1000L;

            new CountDownTimer(countDown[0], 1000) {

                public void onTick(long countDown1) {
                    String time = (countDown1 / 1000) + "";
                    textButtonRecover.setText(new StringBuilder(time).append("\""));
                }

                public void onFinish() {
                    mpVaiUomo.start();

                    textButtonRecover.setText(new StringBuilder("Start"));

                    setSerieEsercizio(esercizioList.get(indexActualExe));
                    setRepAfterSerie(esercizioList.get(indexActualExe));

                    if (esercizioList.get(indexActualExe).getSerieCompletate() >= (Integer.parseInt(esercizioList.get(indexActualExe).getSerie()))) {
                        onNextExerciseClick(esercizioList);
                    }

                    if (esercizioList.get(indexActualExe).getSerieCompletate() >= ((Integer.parseInt(esercizioList.get(indexActualExe).getSerie())) - 1) && esercizioList.get(indexActualExe).getVideo()) {
                        textVideo.setVisibility(View.VISIBLE);
                    }

                    updateButtonClickable(actualExercise[0], true);
                }
            }.start();

        } else {
            updateButtonClickable(actualExercise[0], true);
        }
    }

    private void updateButtonClickable(Esercizio actualExercise, boolean clickable) {
        if (actualExercise.isExeTypeTenuta()) {
            setButtonClickable(clickable, previousExeButton, nextExeButton, buttonRecover);
        } else {
            setButtonClickable(clickable, previousExeButton, nextExeButton, buttonTenuta, buttonRecover);
        }
    }

    private void setButtonClickable(Boolean valueButton, LinearLayout... buttonList) {
        for (LinearLayout button : buttonList) {
            button.setClickable(valueButton);
        }
    }

    private void setSerieEsercizio(Esercizio esercizio) {
        esercizio.setSerieCompletate(esercizio.getSerieCompletate() + 1);
        textNumSet.setText(esercizio.getSetForExecution());
    }

    private void setRepAfterSerie(Esercizio esercizio) {
        esercizio.setNumeroRipetizioniDopoSerie();
        textNumRep.setText(esercizio.getRepForExecution());
    }


}