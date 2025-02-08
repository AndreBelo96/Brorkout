package com.andrea.belotti.brorkout.plans_creation.view.schedulecreator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.plans_creation.view.PlanCreatorActivity;
import com.andrea.belotti.brorkout.utils.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.plans_creation.view.collect_data.DataExeIncrFragment;
import com.andrea.belotti.brorkout.plans_creation.view.collect_data.DataExePirFragment;
import com.andrea.belotti.brorkout.plans_creation.view.collect_data.DataExeSerFragment;
import com.andrea.belotti.brorkout.plans_creation.view.collect_data.DataExeTenFragment;
import com.andrea.belotti.brorkout.plans_creation.view.CreationPlanFragment;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.model.EsercizioIncrementale;
import com.andrea.belotti.brorkout.model.EsercizioPiramidale;
import com.andrea.belotti.brorkout.model.EsercizioSerie;
import com.andrea.belotti.brorkout.model.EsercizioTenuta;
import com.andrea.belotti.brorkout.entity.Giornata;
import com.andrea.belotti.brorkout.utils.ExeTypeCastUtil;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import static com.andrea.belotti.brorkout.utils.constants.ExerciseConstants.MemorizeConstants.GIORNATA;
import static com.andrea.belotti.brorkout.utils.AppMethodsUtils.deleteFragmentFromStack;

import java.util.Map;

public class ModifyExeFragment extends Fragment {

    Fragment typeExeFragment = null;
    Integer numeroEsercizio = null;

    public static ModifyExeFragment newInstance(Giornata gioranta, Integer numeroEsercizio) {
        ModifyExeFragment fragment = new ModifyExeFragment();
        Bundle args = new Bundle();
        args.putSerializable(GIORNATA, gioranta);
        args.putSerializable("NumeroEsercizio", numeroEsercizio);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_exe, container, false);

        Spinner typeExePicker = view.findViewById(R.id.choiceExerciseType);

        Giornata giornata = null;
        if (getArguments() != null) {

            giornata = (Giornata) getArguments().getSerializable(GIORNATA);
            numeroEsercizio = (Integer) getArguments().getSerializable("NumeroEsercizio");
            initExeView(giornata.getExercises().get(numeroEsercizio), view);
        }

        changeTypeExeFragment(typeExePicker, giornata.getExercises().get(numeroEsercizio));

        LinearLayout saveButton = view.findViewById(R.id.salva);
        LinearLayout annullaButton = view.findViewById(R.id.annulla);

        PlanCreatorActivity activity = (PlanCreatorActivity) this.getActivity();

        saveButton.setOnClickListener(v -> {

            Esercizio esercizio = createExe(view, typeExePicker);

            if (esercizio.isExeNotOk()) {
                Toast toast = Toast.makeText(getContext(), "Data null or empty", ExerciseConstants.ToastMessageConstants.DURATION);
                toast.show();
                return;
            }

            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            activity.setAddExeCreation(esercizio);
            deleteFragmentFromStack(fragmentTransaction, this, getActivity());

            CreationPlanFragment.addExeToPlan(activity, numeroEsercizio);

        });

        annullaButton.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            deleteFragmentFromStack(fragmentTransaction, this, getActivity());
        });

        return view;
    }



    private void changeTypeExeFragment(Spinner typeNumPicker, Esercizio esercizio) {

        // TODO Crea una mappa dove Serie = 0, incrementale = 1 -> etc etc a seconda dell'ordine di quello che si vede nella lista


        for (Map.Entry<Integer, String> entry : ExeTypeCastUtil.Exetype.map.entrySet()) {
            if (esercizio.getTipoEsercizio().equals(entry.getValue())) {
                typeNumPicker.setSelection(entry.getKey());
            }
        }



        typeNumPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = typeNumPicker.getSelectedItem().toString();

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();

                switch (selectedItem) {
                    case "Serie":
                        EsercizioSerie esercizioSerie = esercizio.getTipoEsercizio().equals("Serie") ? (EsercizioSerie) esercizio : null;
                        DataExeSerFragment dataExeSerFragment = DataExeSerFragment.newInstance(esercizioSerie);
                        fragmentTransaction.replace(R.id.fragmentContainerTipoEsercizio, dataExeSerFragment);
                        typeExeFragment = dataExeSerFragment;
                        break;
                    case "Incrementale":
                        EsercizioIncrementale esercizioIncrementale = esercizio.getTipoEsercizio().equals("Incrementale") ? (EsercizioIncrementale) esercizio : null;
                        DataExeIncrFragment dataExeIncrFragment = DataExeIncrFragment.newInstance(esercizioIncrementale);
                        fragmentTransaction.replace(R.id.fragmentContainerTipoEsercizio, dataExeIncrFragment);
                        typeExeFragment = dataExeIncrFragment;
                        break;
                    case "Piramidale":
                        EsercizioPiramidale esercizioPiramidale = esercizio.getTipoEsercizio().equals("Piramidale") ? (EsercizioPiramidale) esercizio : null;
                        DataExePirFragment dataExePirFragment = DataExePirFragment.newInstance(esercizioPiramidale);
                        fragmentTransaction.replace(R.id.fragmentContainerTipoEsercizio, dataExePirFragment);
                        typeExeFragment = dataExePirFragment;
                        break;
                    case "Tenuta":
                        EsercizioTenuta esercizioTenuta = esercizio.getTipoEsercizio().equals("Tenuta") ? (EsercizioTenuta) esercizio : null;
                        DataExeTenFragment dataExeTenFragment = DataExeTenFragment.newInstance(esercizioTenuta);
                        fragmentTransaction.replace(R.id.fragmentContainerTipoEsercizio, dataExeTenFragment);
                        typeExeFragment = dataExeTenFragment;
                        break;
                    default:
                        Toast toast = Toast.makeText(view.getContext(), "TIPO NON VALIDO", ExerciseConstants.ToastMessageConstants.DURATION);
                        toast.show();
                }
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // è selezionato di default serie, non si può non selezionare nulla
            }
        });
    }

    private Esercizio createExe(View view, Spinner typeExePicker) {

        View viewFragment = typeExeFragment.getView();

        Esercizio esercizio = initExe(typeExePicker, viewFragment);

        esercizio.setTipoEsercizio(((Spinner) view.findViewById(R.id.choiceExerciseType)).getSelectedItem().toString());
        esercizio.setName(((EditText) view.findViewById(R.id.textNomeEsercizio)).getText().toString());
        esercizio.setRecupero(((EditText) viewFragment.findViewById(R.id.recoverText)).getText().toString());
        esercizio.setZavorre(((EditText) view.findViewById(R.id.textZavorre)).getText().toString());
        esercizio.setElastico(((EditText) view.findViewById(R.id.textElastico)).getText().toString());
        esercizio.setSerieCompletate(0);
        esercizio.setSerie(((EditText) viewFragment.findViewById(R.id.textSerie)).getText().toString());
        esercizio.setVideo(((CheckBox) view.findViewById(R.id.checkBoxVideo)).isChecked());
        esercizio.setIndicazioniCoach(((EditText) view.findViewById(R.id.textIndicazioniEsercizio)).getText().toString());
        esercizio.setAppuntiAtleta("");

        return esercizio;
    }

    private Esercizio initExe(Spinner typeExePicker, View viewFragment) {
        Esercizio esercizio;

        switch (typeExePicker.getSelectedItem().toString()) {

            case "Incrementale":
                esercizio = new EsercizioIncrementale();
                esercizio.setInizio(((EditText) viewFragment.findViewById(R.id.repetitionStartText)).getText().toString());
                esercizio.setPicco(((EditText) viewFragment.findViewById(R.id.peakText)).getText().toString());
                esercizio.setRipetizioni(((EditText) viewFragment.findViewById(R.id.repetitionStartText)).getText().toString());
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
                esercizio.setRipetizioni(((EditText) viewFragment.findViewById(R.id.repetitionStartText)).getText().toString());
                esercizio.setRecuperoSerie(((EditText) viewFragment.findViewById(R.id.textRecoverSeries)).getText().toString());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + typeExePicker.getSelectedItem().toString());
        }
        return esercizio;

    }

    private void initExeView(Esercizio esercizio, View view) {

        EditText nomeEsercizio = view.findViewById(R.id.textNomeEsercizio);
        nomeEsercizio.setText(esercizio.getName());

        CheckBox checkBox = view.findViewById(R.id.checkBoxVideo);
        checkBox.setChecked(esercizio.getVideo());

        EditText indicazioni = view.findViewById(R.id.textIndicazioniEsercizio);
        indicazioni.setText(esercizio.getIndicazioniCoach());

        EditText zavorre = view.findViewById(R.id.textZavorre);
        zavorre.setText(esercizio.getZavorre());

        EditText elastico = view.findViewById(R.id.textElastico);
        elastico.setText(esercizio.getElastico());

    }

}
