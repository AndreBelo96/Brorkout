package com.andrea.belotti.brorkout.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import com.andrea.belotti.brorkout.fragment.collectdata.DataExeIncrFragment;
import com.andrea.belotti.brorkout.fragment.collectdata.DataExePirFragment;
import com.andrea.belotti.brorkout.fragment.collectdata.DataExeSerFragment;
import com.andrea.belotti.brorkout.fragment.collectdata.DataExeTenFragment;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.model.EsercizioBaseModel;
import com.andrea.belotti.brorkout.model.EsercizioIncrementale;
import com.andrea.belotti.brorkout.model.EsercizioPiramidale;
import com.andrea.belotti.brorkout.model.EsercizioSerie;
import com.andrea.belotti.brorkout.model.EsercizioTenuta;
import com.andrea.belotti.brorkout.model.Giornata;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectDataExeFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    private final String recoverError = "Invalid data: recupero";
    private final String serieError = "Invalid data: serie";
    private final String repError = "Invalid data: ripetizioni";
    private final String successAddingExe = "Esercizio aggiunto alla scheda";
    private final String successDeletingAllExe = "Esercizi eliminati con successo";
    private final String successDeletingExe = "Ultimo esercizio eliminato con successo";
    private final String errorEmptyList = "La lista è vuota";

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

                EsercizioBaseModel esercizioBaseModel = new EsercizioBaseModel();

                esercizioBaseModel.setNomeExeStr(((EditText) view.findViewById(R.id.textNomeEsercizio)).getText().toString());
                esercizioBaseModel.setTypeStr(typeNumPicker.getSelectedItem().toString());
                esercizioBaseModel.setRecoverStr(ExerciseConstants.recoverList[((NumberPicker) viewFragment.findViewById(R.id.recoverText)).getValue()]);
                esercizioBaseModel.setSerieStr(((EditText) viewFragment.findViewById(R.id.textSerie)).getText().toString());
                esercizioBaseModel.setVideo(((CheckBox) view.findViewById(R.id.checkBoxVideo)).isChecked());
                esercizioBaseModel.setIndicazioniCoach(((EditText) view.findViewById(R.id.textIndicazioniEsercizio)).getText().toString());
                esercizioBaseModel.setAppuntiAtleta("");

                Map<String, Object> argsMap = argsFromExe(esercizioBaseModel.getTypeStr(), viewFragment);

                if (!StringUtils.isNumeric(esercizioBaseModel.getRecoverStr())) {
                    Toast toast = Toast.makeText(context, recoverError, duration);
                    toast.show();
                    return;
                }

                if (!StringUtils.isNumeric(esercizioBaseModel.getSerieStr())) {
                    Toast toast = Toast.makeText(context, serieError, duration);
                    toast.show();
                    return;
                }

                exerciesList.add(createExe(esercizioBaseModel, argsMap));

                giornata.setEsercizi(exerciesList);
                ScheduleCreatorFragment.setGiornateList(giornata, numeroGiornata);

                // Creation of schedule
                linearLayoutSchedule.removeAllViews();
                viewExe(linearLayoutSchedule);

                Toast toast = Toast.makeText(context, successAddingExe, duration);
                toast.show();
            }
        });

        deleteExe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!giornata.getEsercizi().isEmpty()) {
                    giornata.getEsercizi().remove(giornata.getEsercizi().size() - 1);
                    linearLayoutSchedule.removeViewAt(giornata.getEsercizi().size());
                    ScheduleCreatorFragment.setGiornateList(giornata, numeroGiornata);
                    Toast toast = Toast.makeText(context, successDeletingExe, duration);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(context, errorEmptyList, duration);
                    toast.show();
                }
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
                Toast toast = Toast.makeText(context, successDeletingAllExe, duration);
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

    private Esercizio createExe(EsercizioBaseModel esercizioBaseModel, Map<String, Object> argsMap) {
        Esercizio esercizio;

        if (StringUtils.equalsIgnoreCase(esercizioBaseModel.getTypeStr(), "Serie")) {
            esercizio = new EsercizioSerie(
                    esercizioBaseModel.getNomeExeStr(),
                    esercizioBaseModel.getTypeStr(),
                    esercizioBaseModel.getSerieStr(),
                    esercizioBaseModel.getRecoverStr(),
                    esercizioBaseModel.getVideo(),
                    esercizioBaseModel.getIndicazioniCoach(),
                    esercizioBaseModel.getAppuntiAtleta(),
                    ((EditText) argsMap.get("Ripetizioni")).getText().toString());
        }
        else if (StringUtils.equalsIgnoreCase(esercizioBaseModel.getTypeStr(), "Incrementale")) {
            esercizio = new EsercizioIncrementale(
                    esercizioBaseModel.getNomeExeStr(),
                    esercizioBaseModel.getTypeStr(),
                    esercizioBaseModel.getSerieStr(),
                    esercizioBaseModel.getRecoverStr(),
                    esercizioBaseModel.getVideo(),
                    esercizioBaseModel.getIndicazioniCoach(),
                    esercizioBaseModel.getAppuntiAtleta(),
                    ((EditText) argsMap.get("Inizio")).getText().toString(),
                    ((EditText) argsMap.get("Picco")).getText().toString());
        }
        else if (StringUtils.equalsIgnoreCase(esercizioBaseModel.getTypeStr(), "Piramidale")) {
            esercizio = new EsercizioPiramidale(
                    esercizioBaseModel.getNomeExeStr(),
                    esercizioBaseModel.getTypeStr(),
                    esercizioBaseModel.getSerieStr(),
                    esercizioBaseModel.getRecoverStr(),
                    esercizioBaseModel.getVideo(),
                    esercizioBaseModel.getIndicazioniCoach(),
                    esercizioBaseModel.getAppuntiAtleta(),
                    ((EditText) argsMap.get("Inizio")).getText().toString(),
                    ((EditText) argsMap.get("Picco")).getText().toString(),
                    ExerciseConstants.recoverList[((NumberPicker) argsMap.get("RecuperoSerie")).getValue()]);
        }
        else if (StringUtils.equalsIgnoreCase(esercizioBaseModel.getTypeStr(), "Tenuta")) {
            esercizio = new EsercizioTenuta(
                    esercizioBaseModel.getNomeExeStr(),
                    esercizioBaseModel.getTypeStr(),
                    esercizioBaseModel.getSerieStr(),
                    esercizioBaseModel.getRecoverStr(),
                    esercizioBaseModel.getVideo(),
                    esercizioBaseModel.getIndicazioniCoach(),
                    esercizioBaseModel.getAppuntiAtleta(),
                    ((EditText) argsMap.get("Ripetizioni")).getText().toString(),
                    ((EditText) argsMap.get("TempoEsecuzione")).getText().toString());
        }
        else {
            esercizio = null;
        }

        return esercizio;
    }

    private Map<String, Object> argsFromExe(String typeStr, View viewFragment) {
        Map<String, Object> map = new HashMap<>();

        if (StringUtils.equalsIgnoreCase(typeStr, "Serie")) {
            addIfNotNull(map, "Ripetizioni", viewFragment.findViewById(R.id.textRipetizioni));
        } else if (StringUtils.equalsIgnoreCase(typeStr, "Incrementale")) {
            addIfNotNull(map, "Inizio", viewFragment.findViewById(R.id.repetitionStartText));
            addIfNotNull(map, "Picco", viewFragment.findViewById(R.id.peakText));
        } else if (StringUtils.equalsIgnoreCase(typeStr, "Piramidale")) {
            addIfNotNull(map, "Inizio", viewFragment.findViewById(R.id.repetitionStartText));
            addIfNotNull(map, "Picco", viewFragment.findViewById(R.id.peakText));
            addIfNotNull(map, "RecuperoSerie", viewFragment.findViewById(R.id.textRecoverSeries));
        } else if (StringUtils.equalsIgnoreCase(typeStr, "Tenuta")) {
            addIfNotNull(map, "TempoEsecuzione", viewFragment.findViewById(R.id.textExecutionTime));
            addIfNotNull(map, "Ripetizioni", viewFragment.findViewById(R.id.textRipetizioni));
        } else {
            Toast toast = Toast.makeText(context, "MAPPA VUOTA", duration);
            toast.show();
            return null;
        }
        return map;
    }

    private static void addIfNotNull(Map<String, Object> map, String key, Object value) {
        if (value != null) {
            map.put(key, value);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void viewExe(LinearLayout linearLayoutSchedule) {
        giornata.getEsercizi().forEach(elem -> {
            TextView textView = new TextView(context);
            textView.setTextSize(15f);
            textView.setText(elem.toStringUI());
            linearLayoutSchedule.addView(textView);
        });
    }

}