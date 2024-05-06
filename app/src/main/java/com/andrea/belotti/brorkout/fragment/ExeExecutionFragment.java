package com.andrea.belotti.brorkout.fragment;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.activity.StartingMenuActivity;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.model.EsercizioIncrementale;
import com.andrea.belotti.brorkout.model.EsercizioPiramidale;
import com.andrea.belotti.brorkout.model.EsercizioSerie;
import com.andrea.belotti.brorkout.model.EsercizioTenuta;
import com.andrea.belotti.brorkout.model.Scheda;

import java.util.ArrayList;
import java.util.List;

public class ExeExecutionFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    private Integer countExe = 0;
    //private Integer countSerie = 0;
    private final List<Integer> countSerieList = new ArrayList<>();
    private Integer countRipetizioni = 0; //TODO array

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

        Log.i(TAG, ExerciseConstants.TAG_START_FRAGMENT);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exe_execution, container, false);

        if (getArguments() == null) {
            return view;
        }

        Scheda scheda = (Scheda) getArguments().get("scheda");
        Integer giorno = (Integer) getArguments().get("giorno");

        List<Esercizio> esercizioList = scheda.getGiornate().get(giorno - 1).getEsercizi();
        Integer numeroEsercizi = scheda.getGiornate().get(giorno - 1).getEsercizi().size();

        for (int i = 0; i < numeroEsercizi; i++) {
            countSerieList.add(0);
        }

        setExeOnView(view, esercizioList);

        final MediaPlayer mpVaiUomo = MediaPlayer.create(this.getActivity(), R.raw.alarm);

        Button buttonRecover = view.findViewById(R.id.buttonRecover);
        Button nextExeButton = view.findViewById(R.id.buttonNextExe);
        Button previousExeButton = view.findViewById(R.id.buttonPreviousExe);
        Button buttonEndSchedule = view.findViewById(R.id.buttonEndSchedule);

        String numSerie = esercizioList.get(countExe).getSerie();
        TextView textRecoverNumber = view.findViewById(R.id.textRecoverNumber);
        TextView textNumSerie = view.findViewById(R.id.textNumSerie);
        TextView textVideo = view.findViewById(R.id.textVideo);

        final Long[] countDown = {Long.parseLong(textRecoverNumber.getText().toString()) * 1000l};

        nextExeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewExePage(numeroEsercizi, view, esercizioList, true);
            }
        });

        previousExeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewExePage(numeroEsercizi, view, esercizioList, false);
            }
        });

        buttonEndSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StartingMenuActivity.class);
                startActivity(intent);
            }
        });

        buttonRecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isExeTenuta(esercizioList.get(countExe))) {
                    setButtonClickable(false, previousExeButton, nextExeButton, buttonRecover);
                } else {
                    Button buttonTenRecover = view.findViewById(R.id.buttonTenRecover);
                    setButtonClickable(false, previousExeButton, nextExeButton, buttonRecover, buttonTenRecover);
                }
                if (countSerieList.get(countExe) < Integer.parseInt(esercizioList.get(countExe).getSerie())) {
                    countDown[0] = Long.parseLong(esercizioList.get(countExe).getRecupero()) * 1000l;

                    new CountDownTimer(countDown[0], 1000) {

                        public void onTick(long countDown) {
                            buttonRecover.setText(countDown / 1000 + "\"");
                        }

                        public void onFinish() {
                            buttonRecover.setText("Start");
                            mpVaiUomo.start();

                            setSerieEsercizio(esercizioList.get(countExe), textNumSerie);
                            setRepForTenutaIncrementale(esercizioList.get(countExe));

                            Log.i(ExeExecutionFragment.class.getSimpleName(), "Numero attuale di serie completate: " + countSerieList.get(countExe) + " - Numero di serie totali: " + esercizioList.get(countExe).getSerie());
                            if (countSerieList.get(countExe) >= (Integer.parseInt(esercizioList.get(countExe).getSerie()))) {
                                setNewExePage(numeroEsercizi, view, esercizioList, true);
                            }

                            if (countSerieList.get(countExe) >= (Integer.parseInt(numSerie) - 1) && esercizioList.get(countExe).getVideo()) {
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

        setVisibilityOfTenutaExe(view, esercizioList.get(countExe));

        textNameExe.setText(esercizioList.get(countExe).getNomeEsercizio());
        textNumSerie.setText("Serie: " + countSerieList.get(countExe) + "/" + esercizioList.get(countExe).getSerie());
        textNumRep.setText(esercizioList.get(countExe).getRipetizioneEsercizioString());

        if (countSerieList.get(countExe) >= Integer.parseInt(esercizioList.get(countExe).getSerie()) - 1 && esercizioList.get(countExe).getVideo()) {
            textVideo.setText("Video On");
        } else {
            textVideo.setText("");
        }

        textRecoverNumber.setText(esercizioList.get(countExe).getRecupero());
        textIndicazioniEsercizio.setText("Indicazioni: " + esercizioList.get(countExe).getIndicazioniCoach());

    }

    private String setRipetizioniEsercizio(Esercizio esercizio) {

        String ripetizioniText;

        if (esercizio instanceof EsercizioTenuta) {
            ripetizioniText = "Ripetizioni: " + ((EsercizioTenuta) esercizio).getRipetizioni();
        } else if (esercizio instanceof EsercizioSerie) {
            ripetizioniText = "Ripetizioni: " + ((EsercizioSerie) esercizio).getRipetizioni();
        } else if (esercizio instanceof EsercizioIncrementale) {
            countRipetizioni = Integer.parseInt(((EsercizioIncrementale) esercizio).getInizio());
            ripetizioniText = "Ripetizioni: " + countRipetizioni;
        } else if (esercizio instanceof EsercizioPiramidale) {
            countRipetizioni = Integer.parseInt(((EsercizioPiramidale) esercizio).getInizio());
            ripetizioniText = "Ripetizioni: " + countRipetizioni;
        } else {
            ripetizioniText = "";
        }

        return ripetizioniText;

    }

    private void setSerieEsercizio(Esercizio esercizio, TextView textNumSerie) {

        countSerieList.set(countExe, countSerieList.get(countExe) + 1);
        textNumSerie.setText("Serie: " + countSerieList.get(countExe) + "/" + esercizio.getSerie());

    }

    private void setRepForTenutaIncrementale(Esercizio esercizio) { //TODO da testare

        if (esercizio instanceof EsercizioIncrementale) {

            if (countRipetizioni < Integer.parseInt(((EsercizioIncrementale) esercizio).getPicco())) {
                countRipetizioni++;
            } else {
                countRipetizioni--;
            }
        }


        if (esercizio instanceof EsercizioPiramidale) {
            if (countRipetizioni < Integer.parseInt(((EsercizioPiramidale) esercizio).getPicco())) {
                countRipetizioni++;
            } else {
                countRipetizioni--;
            }
        }

        setRipetizioniEsercizio(esercizio);

    }

    private void setButtonClickable(Boolean valueButton, Button... buttonList) {
        for (Button button : buttonList) {
            button.setClickable(valueButton);
        }
    }

    private void setNewExePage(Integer numeroEsercizi, View view, List<Esercizio> esercizioList, Boolean isNextExe) {
        if (isNextExe) {
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
        FragmentContainerView fragmentTenutaExeView = view.findViewById(R.id.fragmentTenutaExeView);
        Button buttonRecover = view.findViewById(R.id.buttonRecover);
        Button nextExeButton = view.findViewById(R.id.buttonNextExe);
        Button previousExeButton = view.findViewById(R.id.buttonPreviousExe);

        if (!isExeTenuta(esercizio)) {
            fragmentTenutaExeView.setVisibility(View.INVISIBLE);
        } else {
            EsercizioTenuta esercizioTenuta = (EsercizioTenuta) esercizio;
            TextView textRecoverTenNumber = view.findViewById(R.id.textRecoverTenNumber);
            textRecoverTenNumber.setText(esercizioTenuta.getTempoEsecuzione());
            fragmentTenutaExeView.setVisibility(View.VISIBLE);

            Button buttonTenRecover = view.findViewById(R.id.buttonTenRecover);

            Long countDown = Long.parseLong(textRecoverTenNumber.getText().toString()) * 1000l;

            buttonTenRecover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    setButtonClickable(false, previousExeButton, nextExeButton, buttonRecover, buttonTenRecover);

                    new CountDownTimer(countDown, 1000) {
                        public void onTick(long millisUntilFinished) {
                            buttonTenRecover.setText(millisUntilFinished / 1000 + "\"");
                        }

                        public void onFinish() {
                            buttonTenRecover.setText("Start");
                            setButtonClickable(true, previousExeButton, nextExeButton, buttonRecover, buttonTenRecover);
                        }
                    }.start();

                }
            });
        }
    }


    private Boolean isExeTenuta(Esercizio esercizio) {
        return esercizio instanceof EsercizioTenuta;
    }


}