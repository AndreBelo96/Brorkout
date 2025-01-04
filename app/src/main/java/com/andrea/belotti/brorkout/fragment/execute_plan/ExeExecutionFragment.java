package com.andrea.belotti.brorkout.fragment.execute_plan;

import static com.andrea.belotti.brorkout.constants.ExerciseConstants.MemorizeConstants.*;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.model.EsercizioTenuta;
import com.andrea.belotti.brorkout.model.Scheda;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ExeExecutionFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();

    private Integer countExe = 0;

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exe_execution, container, false);

        if (getArguments() == null) {
            return view;
        }

        Scheda plan = (Scheda) getArguments().get(SCHEDA);
        Integer day = (Integer) getArguments().get(GIORNATA);

        List<Esercizio> esercizioList = plan.getGiornate().get(day - 1).getEsercizi();
        int numeroEsercizi = plan.getGiornate().get(day - 1).getEsercizi().size();

        final MediaPlayer mpVaiUomo = MediaPlayer.create(this.getActivity(), R.raw.alarm);

        LinearLayout buttonRecover = view.findViewById(R.id.buttonRecover);
        TextView textButtonRecover = view.findViewById(R.id.textButtonRecover);
        LinearLayout nextExeButton = view.findViewById(R.id.buttonNextExe);
        LinearLayout previousExeButton = view.findViewById(R.id.buttonPreviousExe);
        LinearLayout buttonEndSchedule = view.findViewById(R.id.buttonEndSchedule);

        setExeOnView(view, esercizioList);

        TextView textRecover = view.findViewById(R.id.textRecover);
        TextView textNumSerie = view.findViewById(R.id.textNumSerie);
        TextView textVideo = view.findViewById(R.id.textVideo);
        TextView textNumRep = view.findViewById(R.id.textNumRep);
        EditText commentiAtleta = view.findViewById(R.id.commentiAtleta);

        String recover = textRecover.getText().toString();

        String[] subString = recover.split(" ");

        String recoverNumber = subString[1].replace("\"", "");

        final Long[] countDown = {Long.parseLong(recoverNumber) * 1000L};

        nextExeButton.setOnClickListener(v -> {
            esercizioList.get(countExe).setAppuntiAtleta(commentiAtleta.getText().toString());
            setNewExePage(numeroEsercizi, view, esercizioList, true);
        });

        previousExeButton.setOnClickListener(v -> {
            esercizioList.get(countExe).setAppuntiAtleta(commentiAtleta.getText().toString());
            setNewExePage(numeroEsercizi, view, esercizioList, false);
        });

        buttonEndSchedule.setOnClickListener(v -> {
            esercizioList.get(countExe).setAppuntiAtleta(commentiAtleta.getText().toString());
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerViewGestoreScheda, EndScheduleSummaryFragment.newInstance(plan, day));
            fragmentTransaction.commit();
        });

        buttonRecover.setOnClickListener(v -> {

            if (!isExeTenuta(esercizioList.get(countExe))) {
                setButtonClickable(false, previousExeButton, nextExeButton, buttonRecover);
            } else {
                LinearLayout buttonTenRecover = view.findViewById(R.id.buttonTenRecover);
                setButtonClickable(false, previousExeButton, nextExeButton, buttonTenRecover, buttonRecover);
            }
            if (esercizioList.get(countExe).getSerieCompletate() < Integer.parseInt(esercizioList.get(countExe).getSerie())) {
                countDown[0] = Long.parseLong(esercizioList.get(countExe).getRecupero()) * 1000L;

                new CountDownTimer(countDown[0], 1000) {

                    public void onTick(long countDown1) {
                        textButtonRecover.setText(countDown1 / 1000 + "\"");
                    }

                    public void onFinish() {
                        textButtonRecover.setText("Start");
                        mpVaiUomo.start();

                        setSerieEsercizio(esercizioList.get(countExe), textNumSerie);
                        setRepAfterSerie(esercizioList.get(countExe), textNumRep);

                        //Log.i(ExeExecutionFragment.class.getSimpleName(), "Numero attuale di serie completate: " + esercizioList.get(countExe).getSerieCompletate() + " - Numero di serie totali: " + esercizioList.get(countExe).getSerie());

                        if (esercizioList.get(countExe).getSerieCompletate() >= (Integer.parseInt(esercizioList.get(countExe).getSerie()))) {
                            setNewExePage(numeroEsercizi, view, esercizioList, true);
                        }

                        if (esercizioList.get(countExe).getSerieCompletate() >= ((Integer.parseInt(esercizioList.get(countExe).getSerie()))  - 1) &&
                                esercizioList.get(countExe).getVideo()) {
                            textVideo.setVisibility(View.VISIBLE);
                        }

                        if (!isExeTenuta(esercizioList.get(countExe))) {
                            setButtonClickable(true, previousExeButton, nextExeButton, buttonRecover);
                        } else {
                            LinearLayout buttonTenRecover = view.findViewById(R.id.buttonTenRecover);
                            setButtonClickable(true, previousExeButton, nextExeButton, buttonTenRecover, buttonRecover);
                        }
                    }
                }.start();

            } else {
                if (!isExeTenuta(esercizioList.get(countExe))) {
                    setButtonClickable(true, previousExeButton, nextExeButton, buttonRecover);
                } else {
                    LinearLayout buttonTenRecover = view.findViewById(R.id.buttonTenRecover);
                    setButtonClickable(true, previousExeButton, nextExeButton, buttonTenRecover, buttonRecover);
                }
            }
        });

        return view;
    }


    private void setExeOnView(View view, List<Esercizio> esercizioList) {
        TextView textNameExe = view.findViewById(R.id.textNomeEsercizio);
        TextView textNumSet = view.findViewById(R.id.textNumSerie);
        TextView textNumRep = view.findViewById(R.id.textNumRep);
        TextView textVideo = view.findViewById(R.id.textVideo);
        TextView textRecover = view.findViewById(R.id.textRecover);
        TextView textIndicazioniEsercizio = view.findViewById(R.id.textIndicazioniEsercizio);
        EditText textAppuntiAtleta = view.findViewById(R.id.commentiAtleta);
        TextView nextExerciseText = view.findViewById(R.id.textNextExercise);
        TextView previousExerciseText = view.findViewById(R.id.textPreviousExercise);

        textNameExe.setText(esercizioList.get(countExe).getName());
        textNumSet.setText(esercizioList.get(countExe).getSetForExecution());
        textNumRep.setText(esercizioList.get(countExe).getRepForExecution());
        textRecover.setText(esercizioList.get(countExe).getRecForExecution());

        if (countExe + 1 < esercizioList.size()) {
            nextExerciseText.setText(esercizioList.get(countExe + 1).getName());
        } else {
            nextExerciseText.setText("--");
        }

        if (countExe <= 0 ) {
            previousExerciseText.setText("--");
        } else {
            previousExerciseText.setText(esercizioList.get(countExe - 1).getName());
        }



        if (esercizioList.get(countExe).getSerieCompletate() >= Integer.parseInt(esercizioList.get(countExe).getSerie()) - 1 &&
                esercizioList.get(countExe).getVideo()) {
            textVideo.setVisibility(View.VISIBLE);
        } else {
            textVideo.setVisibility(View.GONE);
        }


        textIndicazioniEsercizio.setText("Indicazioni: " + esercizioList.get(countExe).getIndicazioniCoach());
        textAppuntiAtleta.setText(esercizioList.get(countExe).getAppuntiAtleta());

        setVisibilityOfTenutaExe(view, esercizioList.get(countExe));
    }

    private void setSerieEsercizio(Esercizio esercizio, TextView textNumSerie) {

        esercizio.setSerieCompletate(esercizio.getSerieCompletate() + 1);
        textNumSerie.setText(esercizio.getSetForExecution());
    }

    private void setRepAfterSerie(Esercizio esercizio, TextView textNumRipetizioni) {
        esercizio.setNumeroRipetizioniDopoSerie();
        textNumRipetizioni.setText(esercizio.getRepForExecution());
    }

    private void setButtonClickable(Boolean valueButton, LinearLayout... buttonList) {
        for (LinearLayout button : buttonList) {
            button.setClickable(valueButton);
        }
    }

    private void setNewExePage(Integer numeroEsercizi, View view, List<Esercizio> esercizioList, Boolean isNextExe) {
        if (Boolean.TRUE.equals(isNextExe)) {
            if ((countExe + 1) < numeroEsercizi) {
                ++countExe;
                setExeOnView(view, esercizioList);
            }
        } else {
            if ((countExe) > 0) {
                --countExe;
                setExeOnView(view, esercizioList);
            }
        }
    }

    private void setVisibilityOfTenutaExe(View view, Esercizio esercizio) {

        LinearLayout layoutTempoTenuta = view.findViewById(R.id.LayoutTempoTenuta);

        if (!isExeTenuta(esercizio)) {
            layoutTempoTenuta.setVisibility(View.GONE);
        } else {
            layoutTempoTenuta.setVisibility(View.VISIBLE);
            EsercizioTenuta esercizioTenuta = (EsercizioTenuta) esercizio;
            TextView textRecoverTenNumber = view.findViewById(R.id.textTenRecover);

            textRecoverTenNumber.setText("Tempo di tenuta: " + esercizioTenuta.getTempoEsecuzione());
            Long countDown = Long.parseLong(textRecoverTenNumber.getText().toString()) * 1000L;

            LinearLayout buttonTenuta = view.findViewById(R.id.buttonTenRecover);
            TextView textButtonTenRecover = view.findViewById(R.id.textButtonTenRecover);

            buttonTenuta.setOnClickListener(v -> {

                //setButtonClickable(false, previousExeButton, nextExeButton, buttonRecover, buttonTenuta);

                new CountDownTimer(countDown, 1000) {
                    public void onTick(long millisUntilFinished) {
                        textButtonTenRecover.setText(millisUntilFinished / 1000 + "\"");
                    }

                    public void onFinish() {
                        textButtonTenRecover.setText("Start");
                        //setButtonClickable(true, previousExeButton, nextExeButton, buttonRecover, buttonTenuta);
                    }
                }.start();

            });

        }
    }

    private Boolean isExeTenuta(Esercizio esercizio) {
        return esercizio instanceof EsercizioTenuta;
    }


}