package com.andrea.belotti.brorkout.fragment.executor;

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
        args.putSerializable("scheda", scheda);
        args.putInt("giorno", giorno);
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

        Scheda scheda = (Scheda) getArguments().get("scheda");
        Integer giorno = (Integer) getArguments().get("giorno");

        List<Esercizio> esercizioList = scheda.getGiornate().get(giorno - 1).getEsercizi();
        int numeroEsercizi = scheda.getGiornate().get(giorno - 1).getEsercizi().size();

        final MediaPlayer mpVaiUomo = MediaPlayer.create(this.getActivity(), R.raw.alarm);

        Button buttonRecover = view.findViewById(R.id.buttonRecover);
        Button nextExeButton = view.findViewById(R.id.buttonNextExe);
        ImageButton previousExeButton = view.findViewById(R.id.buttonPreviousExe);
        Button buttonEndSchedule = view.findViewById(R.id.buttonEndSchedule);

        setExeOnView(view, esercizioList);

        String numSerie = esercizioList.get(countExe).getSerie();
        TextView textRecoverNumber = view.findViewById(R.id.textRecoverNumber);
        TextView textNumSerie = view.findViewById(R.id.textNumSerie);
        TextView textVideo = view.findViewById(R.id.textVideo);
        TextView textNumRep = view.findViewById(R.id.textNumRep);
        EditText commentiAtleta = view.findViewById(R.id.commentiAtleta);

        final Long[] countDown = {Long.parseLong(textRecoverNumber.getText().toString()) * 1000L};

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
            fragmentTransaction.replace(R.id.fragmentContainerViewGestoreScheda, EndScheduleSummaryFragment.newInstance(scheda, giorno));
            fragmentTransaction.commit();
        });

        buttonRecover.setOnClickListener(v -> {

            if (!isExeTenuta(esercizioList.get(countExe))) {
                setButtonClickable(false, previousExeButton, nextExeButton, buttonRecover);
            } else {
                Button buttonTenRecover = view.findViewById(R.id.buttonTenRecover);
                setButtonClickable(false, previousExeButton, nextExeButton, buttonRecover, buttonTenRecover);
            }
            if (esercizioList.get(countExe).getSerieCompletate() < Integer.parseInt(esercizioList.get(countExe).getSerie())) {
                countDown[0] = Long.parseLong(esercizioList.get(countExe).getRecupero()) * 1000L;

                new CountDownTimer(countDown[0], 1000) {

                    public void onTick(long countDown1) {
                        buttonRecover.setText(countDown1 / 1000 + "\"");
                    }

                    public void onFinish() {
                        buttonRecover.setText("Start");
                        mpVaiUomo.start();

                        setSerieEsercizio(esercizioList.get(countExe), textNumSerie);
                        setRepAfterSerie(esercizioList.get(countExe), textNumRep);

                        Log.i(ExeExecutionFragment.class.getSimpleName(), "Numero attuale di serie completate: " + esercizioList.get(countExe).getSerieCompletate() + " - Numero di serie totali: " + esercizioList.get(countExe).getSerie());
                        if (esercizioList.get(countExe).getSerieCompletate() >= (Integer.parseInt(esercizioList.get(countExe).getSerie()))) {
                            setNewExePage(numeroEsercizi, view, esercizioList, true);
                        }

                        if (esercizioList.get(countExe).getSerieCompletate() >= (Integer.parseInt(numSerie) - 1) && esercizioList.get(countExe).getVideo()) {
                            textVideo.setText("Video On");
                        }

                        if (!isExeTenuta(esercizioList.get(countExe))) {
                            setButtonClickable(true, previousExeButton, nextExeButton, buttonRecover);
                        } else {
                            Button buttonTenRecover = view.findViewById(R.id.buttonTenRecover);
                            setButtonClickable(true, previousExeButton, nextExeButton, buttonRecover, buttonTenRecover);
                        }
                    }
                }.start();

            } else {
                if (!isExeTenuta(esercizioList.get(countExe))) {
                    setButtonClickable(true, previousExeButton, nextExeButton, buttonRecover);
                } else {
                    Button buttonTenRecover = view.findViewById(R.id.buttonTenRecover);
                    setButtonClickable(true, previousExeButton, nextExeButton, buttonRecover, buttonTenRecover);
                }
            }
        });

        return view;
    }


    private void setExeOnView(View view, List<Esercizio> esercizioList) {
        TextView textNameExe = view.findViewById(R.id.textNomeEsercizio);
        TextView textNumSerie = view.findViewById(R.id.textNumSerie);
        TextView textNumRep = view.findViewById(R.id.textNumRep);
        TextView textVideo = view.findViewById(R.id.textVideo);
        TextView textRecoverNumber = view.findViewById(R.id.textRecoverNumber);
        TextView textIndicazioniEsercizio = view.findViewById(R.id.textIndicazioniEsercizio);
        EditText textAppuntiAtleta = view.findViewById(R.id.commentiAtleta);
        Button nextExeButton = view.findViewById(R.id.buttonNextExe);

        textNameExe.setText(esercizioList.get(countExe).getName());
        textNumSerie.setText("Serie: " + esercizioList.get(countExe).getSerieCompletate() + "/" + esercizioList.get(countExe).getSerie());
        textNumRep.setText(esercizioList.get(countExe).getRipetizioneEsercizioString());
        if (countExe + 1 < esercizioList.size()) {
            nextExeButton.setText(esercizioList.get(countExe + 1).getName());
        } else {
            nextExeButton.setText("FINE");
        }

        if (esercizioList.get(countExe).getSerieCompletate() >= Integer.parseInt(esercizioList.get(countExe).getSerie()) - 1 && esercizioList.get(countExe).getVideo()) {
            textVideo.setText("Video On");
        } else {
            textVideo.setText("");
        }

        textRecoverNumber.setText(esercizioList.get(countExe).getRecupero());
        textIndicazioniEsercizio.setText("Indicazioni: " + esercizioList.get(countExe).getIndicazioniCoach());
        textAppuntiAtleta.setText(esercizioList.get(countExe).getAppuntiAtleta());

        setVisibilityOfTenutaExe(view, esercizioList.get(countExe));
    }

    private void setSerieEsercizio(Esercizio esercizio, TextView textNumSerie) {

        esercizio.setSerieCompletate(esercizio.getSerieCompletate() + 1);
        textNumSerie.setText("Serie: " + esercizio.getSerieCompletate() + "/" + esercizio.getSerie());
    }

    private void setRepAfterSerie(Esercizio esercizio, TextView textNumRipetizioni) {
        esercizio.setNumeroRipetizioniDopoSerie();
        textNumRipetizioni.setText(esercizio.getRipetizioneEsercizioString());
    }

    private void setButtonClickable(Boolean valueButton, ImageButton imageButton ,Button... buttonList) {
        imageButton.setClickable(valueButton);
        for (Button button : buttonList) {
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
            TextView textRecoverTenNumber = view.findViewById(R.id.textRecoverTenNumber);

            textRecoverTenNumber.setText(esercizioTenuta.getTempoEsecuzione());
            Long countDown = Long.parseLong(textRecoverTenNumber.getText().toString()) * 1000L;

            Button buttonTenuta = view.findViewById(R.id.buttonTenRecover);

            buttonTenuta.setOnClickListener(v -> {

                //setButtonClickable(false, previousExeButton, nextExeButton, buttonRecover, buttonTenuta);

                new CountDownTimer(countDown, 1000) {
                    public void onTick(long millisUntilFinished) {
                        buttonTenuta.setText(millisUntilFinished / 1000 + "\"");
                    }

                    public void onFinish() {
                        buttonTenuta.setText("Start");
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