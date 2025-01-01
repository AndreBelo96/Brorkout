package com.andrea.belotti.brorkout.fragment.create_plan.schedulecreator;

import static com.andrea.belotti.brorkout.utils.AppMethodsUtils.deleteFragmentFromStack;

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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.fragment.create_plan.CreationPlanFragment;
import com.andrea.belotti.brorkout.fragment.create_plan.collectdata.DataExeIncrFragment;
import com.andrea.belotti.brorkout.fragment.create_plan.collectdata.DataExePirFragment;
import com.andrea.belotti.brorkout.fragment.create_plan.collectdata.DataExeSerFragment;
import com.andrea.belotti.brorkout.fragment.create_plan.collectdata.DataExeTenFragment;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.model.EsercizioIncrementale;
import com.andrea.belotti.brorkout.model.EsercizioPiramidale;
import com.andrea.belotti.brorkout.model.EsercizioSerie;
import com.andrea.belotti.brorkout.model.EsercizioTenuta;

public class AddExeFragment extends Fragment {

    Fragment typeExeFragment = null;

    public static AddExeFragment newInstance() {
        return new AddExeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_exe, container, false);

        Spinner typeExePicker = view.findViewById(R.id.choiceExerciseType);
        changeTypeExeFragment(typeExePicker);
        LinearLayout saveButton = view.findViewById(R.id.salva);
        LinearLayout undoButton = view.findViewById(R.id.annulla);

        saveButton.setOnClickListener(v -> {

            Esercizio esercizio = createExe(view, typeExePicker);

            if (esercizio.isExeNotOk()) {
                Toast toast = Toast.makeText(getContext(), "Data null or empty", ExerciseConstants.ToastMessageConstants.DURATION);
                toast.show();
                return;
            }

            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            CreationPlanFragment.addExeToPlan(esercizio);
            deleteFragmentFromStack(fragmentTransaction, this, getActivity());
        });

        undoButton.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            deleteFragmentFromStack(fragmentTransaction, this, getActivity());
        });

        return view;
    }


    private void changeTypeExeFragment(Spinner typeNumPicker) {
        typeNumPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = typeNumPicker.getSelectedItem().toString();

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();

                switch (selectedItem) {
                    case "Serie":
                        DataExeSerFragment dataExeSerFragment = new DataExeSerFragment();
                        fragmentTransaction.replace(R.id.fragmentContainerTipoEsercizio, dataExeSerFragment);
                        typeExeFragment = dataExeSerFragment;
                        break;
                    case "Incrementale":
                        DataExeIncrFragment dataExeIncrFragment = new DataExeIncrFragment();
                        fragmentTransaction.replace(R.id.fragmentContainerTipoEsercizio, dataExeIncrFragment);
                        typeExeFragment = dataExeIncrFragment;
                        break;
                    case "Piramidale":
                        DataExePirFragment dataExePirFragment = new DataExePirFragment();
                        fragmentTransaction.replace(R.id.fragmentContainerTipoEsercizio, dataExePirFragment);
                        typeExeFragment = dataExePirFragment;
                        break;
                    case "Tenuta":
                        DataExeTenFragment dataExeTenFragment = new DataExeTenFragment();
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


}
