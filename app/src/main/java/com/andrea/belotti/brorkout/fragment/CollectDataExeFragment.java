package com.andrea.belotti.brorkout.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.constants.StringOutputConstants;
import com.andrea.belotti.brorkout.fragment.collectdata.DataExeIncrFragment;
import com.andrea.belotti.brorkout.fragment.collectdata.DataExePirFragment;
import com.andrea.belotti.brorkout.fragment.collectdata.DataExeSerFragment;
import com.andrea.belotti.brorkout.fragment.collectdata.DataExeTenFragment;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.model.EsercizioIncrementale;
import com.andrea.belotti.brorkout.model.EsercizioPiramidale;
import com.andrea.belotti.brorkout.model.EsercizioSerie;
import com.andrea.belotti.brorkout.model.EsercizioTenuta;
import com.andrea.belotti.brorkout.model.Giornata;
import com.andrea.belotti.brorkout.utils.ScheduleCreatingUtils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class CollectDataExeFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    private int exePosition = 0;

    Context context;
    Fragment typeExeFragment = null;

    public void setGiornata(Giornata giornata) {
        this.giornata = giornata;
    }

    private Giornata giornata = new Giornata();

    String[] typeList;
    int duration = Toast.LENGTH_SHORT;

    private int numeroGiornata = 1;

    public void setNumeroGiornata(int numeroGiornata) {
        this.numeroGiornata = numeroGiornata;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, ExerciseConstants.TAG_START_FRAGMENT);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collect_data_exe, container, false);

        context = getContext();

        List<Esercizio> exerciesList = new ArrayList<>();
        typeList = getResources().getStringArray(R.array.exerciseType);

        Button createExeButton = view.findViewById(R.id.createExe);
        Button deleteExe = view.findViewById(R.id.deleteExe);
        Button copyDay = view.findViewById(R.id.copyDay);
        Button pasteDay = view.findViewById(R.id.pasteDay);
        Button deleteAllExe = view.findViewById(R.id.deleteSchedule);

        TextView numeroGiornataTW = view.findViewById(R.id.numeroGiornataId);
        numeroGiornataTW.setText("Giornata : " + numeroGiornata);
        Spinner typeNumPicker = view.findViewById(R.id.choiceExerciseType);
        LinearLayout linearLayoutSchedule = view.findViewById(R.id.layoutSchedule);

        giornata = ScheduleCreatorFragment.getGiornateList().get(numeroGiornata);
        viewExe(linearLayoutSchedule);
        exerciesList.addAll(giornata.getEsercizi());

        // Cambio del tipo di esercizio nel fragment
        changeTypeExeFragment(typeNumPicker);


        createExeButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(View v) {
                View viewFragment = typeExeFragment.getView();

                Esercizio esercizio = null;

                switch (typeNumPicker.getSelectedItem().toString()) {

                    case "Incrementale":
                        esercizio = new EsercizioIncrementale();
                        esercizio.setInizio(((EditText) viewFragment.findViewById(R.id.repetitionStartText)).getText().toString());
                        esercizio.setPicco(((EditText) viewFragment.findViewById(R.id.peakText)).getText().toString());
                        break;
                    case "Tenuta":
                        esercizio = new EsercizioTenuta();
                        esercizio.setRipetizioni(((EditText) viewFragment.findViewById(R.id.textRipetizioni)).getText().toString());
                        esercizio.setTempoEsecuzione(((EditText) viewFragment.findViewById(R.id.textExecutionTime)).getText().toString());
                        break;
                    case "Serie":
                        esercizio = new EsercizioSerie();
                        esercizio.setRipetizioni(((EditText) viewFragment.findViewById(R.id.textRipetizioni)).getText().toString());
                        break;
                    case "Piramidale":
                        esercizio = new EsercizioPiramidale();
                        esercizio.setInizio(((EditText) viewFragment.findViewById(R.id.repetitionStartText)).getText().toString());
                        esercizio.setPicco(((EditText) viewFragment.findViewById(R.id.peakText)).getText().toString());
                        esercizio.setRecuperoSerie(ExerciseConstants.recoverList[((NumberPicker) viewFragment.findViewById(R.id.textRecoverSeries)).getValue()]);
                        break;
                    default:
                        break;
                }

                esercizio.setNomeEsercizio(((EditText) view.findViewById(R.id.textNomeEsercizio)).getText().toString());
                esercizio.setRecupero(((EditText) view.findViewById(R.id.recoverText)).getText().toString());
                esercizio.setSerie(((EditText) viewFragment.findViewById(R.id.textSerie)).getText().toString());
                esercizio.setVideo(((CheckBox) view.findViewById(R.id.checkBoxVideo)).isChecked());
                esercizio.setIndicazioniCoach(((EditText) view.findViewById(R.id.textIndicazioniEsercizio)).getText().toString());
                esercizio.setAppuntiAtleta("");

                if (!StringUtils.isNumeric(esercizio.getRecupero())) {
                    Toast toast = Toast.makeText(context, StringOutputConstants.recoverError, duration);
                    toast.show();
                    return;
                }

                if (!StringUtils.isNumeric(esercizio.getSerie())) {
                    Toast toast = Toast.makeText(context, StringOutputConstants.serieError, duration);
                    toast.show();
                    return;
                }

                exerciesList.add(esercizio);

                giornata.setEsercizi(exerciesList);
                ScheduleCreatorFragment.setGiornateList(giornata, numeroGiornata);

                // Creation of schedule
                linearLayoutSchedule.removeAllViews();
                viewExe(linearLayoutSchedule);

                Toast toast = Toast.makeText(context, StringOutputConstants.successAddingExe, duration);
                toast.show();
            }
        });

        deleteExe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (giornata.getEsercizi().isEmpty() || exePosition >= giornata.getEsercizi().size()) {
                    Toast toast = Toast.makeText(context, StringOutputConstants.errorEmptyList, duration);
                    toast.show();
                    return;
                }

                giornata.getEsercizi().remove(exePosition);
                linearLayoutSchedule.removeViewAt(exePosition);
                updateButtonsId(linearLayoutSchedule);
                ScheduleCreatorFragment.setGiornateList(giornata, numeroGiornata);
                Toast toast = Toast.makeText(context, StringOutputConstants.successDeletingExe, duration);
                toast.show();
            }
        });

        copyDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScheduleCreatorFragment.setEserciziCopiaIncolla(giornata.getEsercizi());
                Toast toast = Toast.makeText(context, "giornata copiata", duration);
                toast.show();
            }
        });

        pasteDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutSchedule.removeAllViews();
                List<Esercizio> exeList = new ArrayList<>();
                exeList.addAll(ScheduleCreatorFragment.getEserciziCopiaIncolla());
                giornata.setEsercizi(exeList);
                ScheduleCreatorFragment.setGiornateList(giornata, numeroGiornata);
                viewExe(linearLayoutSchedule);
                Toast toast = Toast.makeText(context, "giornata incollata", duration);
                toast.show();
            }
        });

        deleteAllExe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                giornata.getEsercizi().clear();
                linearLayoutSchedule.removeAllViews();
                ScheduleCreatorFragment.setGiornateList(giornata, numeroGiornata);
                Toast toast = Toast.makeText(context, StringOutputConstants.successDeletingAllExe, duration);
                toast.show();
            }
        });

        return view;
    }


    private void changeTypeExeFragment(Spinner typeNumPicker) {
        typeNumPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = typeNumPicker.getSelectedItem().toString();

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                DataExeSerFragment dataExeSerFragment = new DataExeSerFragment();
                DataExePirFragment dataExePirFragment = new DataExePirFragment();
                DataExeIncrFragment dataExeIncrFragment = new DataExeIncrFragment();
                DataExeTenFragment dataExeTenFragment = new DataExeTenFragment();

                switch (selectedItem) {
                    case "Serie":
                        fragmentTransaction.replace(R.id.fragmentContainerTipoEsercizio, dataExeSerFragment);
                        typeExeFragment = dataExeSerFragment;
                        break;
                    case "Incrementale":
                        fragmentTransaction.replace(R.id.fragmentContainerTipoEsercizio, dataExeIncrFragment);
                        typeExeFragment = dataExeIncrFragment;
                        break;
                    case "Piramidale":
                        fragmentTransaction.replace(R.id.fragmentContainerTipoEsercizio, dataExePirFragment);
                        typeExeFragment = dataExePirFragment;
                        break;
                    case "Tenuta":
                        fragmentTransaction.replace(R.id.fragmentContainerTipoEsercizio, dataExeTenFragment);
                        typeExeFragment = dataExeTenFragment;
                        break;
                    default:
                        Toast toast = Toast.makeText(context, "TIPO NON VALIDO", duration);
                        toast.show();
                }
                fragmentTransaction.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void viewExe(LinearLayout linearLayoutSchedule) {

        final int[] i = {0};

        giornata.getEsercizi().forEach(elem -> {
            Button button = new Button(context);
            button.setId(i[0]);
            button.setText(elem.toStringUI());
            button.setTextSize(15f);
            button.setBackgroundColor(ExerciseConstants.Color.BUTTON_COLOR);
            button.setTextColor(ExerciseConstants.Color.TEXT_BUTTON_COLOR);

            button.setOnClickListener(v -> {
                ScheduleCreatingUtils.setBasicColor(getButtonList(linearLayoutSchedule));
                button.setBackgroundColor(ExerciseConstants.Color.BUTTON_PRESSED_COLOR);
                exePosition = button.getId();
            });

            i[0]++;
            linearLayoutSchedule.addView(button);
        });
    }

    private List<Button> getButtonList(LinearLayout linearLayoutSchedule) {
        List<Button> buttonList = new ArrayList<>();

        for (int i = 0; i < linearLayoutSchedule.getChildCount(); i++) {
            buttonList.add((Button) linearLayoutSchedule.getChildAt(i));
        }

        return buttonList;
    }

    private void updateButtonsId(LinearLayout linearLayoutSchedule) {

        for (int i = 0; i < linearLayoutSchedule.getChildCount(); i++) {
            ((Button) linearLayoutSchedule.getChildAt(i)).setId(i);
        }

    }

}