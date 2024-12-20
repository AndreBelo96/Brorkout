package com.andrea.belotti.brorkout.fragment.schedule_creator.schedulecreator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.activity.ScheduleCreatorActivity;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.fragment.collectdata.DataExeIncrFragment;
import com.andrea.belotti.brorkout.fragment.collectdata.DataExePirFragment;
import com.andrea.belotti.brorkout.fragment.collectdata.DataExeSerFragment;
import com.andrea.belotti.brorkout.fragment.collectdata.DataExeTenFragment;
import com.andrea.belotti.brorkout.fragment.schedule_creator.CreationPlanFragment;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.model.EsercizioIncrementale;
import com.andrea.belotti.brorkout.model.EsercizioPiramidale;
import com.andrea.belotti.brorkout.model.EsercizioSerie;
import com.andrea.belotti.brorkout.model.EsercizioTenuta;

import org.apache.commons.lang3.StringUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddExeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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
        Button saveButton = view.findViewById(R.id.salva);
        Button annullaButton = view.findViewById(R.id.annulla);

        ScheduleCreatorActivity activity = (ScheduleCreatorActivity) this.getActivity();

        saveButton.setOnClickListener(v -> {

            Esercizio esercizio = createExe(view, typeExePicker);

            if (dataNullOrEmpty(esercizio)) {
                Toast toast = Toast.makeText(getContext(), "Data null or empty", ExerciseConstants.ToastMessageConstants.DURATION);
                toast.show();
                return;
            }

            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            activity.setAddExeCreation(esercizio);
            deleteFragmentFromStack(fragmentTransaction);

            CreationPlanFragment.refreshPage(activity);

        });

        annullaButton.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            deleteFragmentFromStack(fragmentTransaction);
        });

        return view;
    }


    private void deleteFragmentFromStack(FragmentTransaction fragmentTransaction) {
        fragmentTransaction.remove(this);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.show(getActivity().getSupportFragmentManager().getFragments().get(0));//TODO non va bene
        fragmentTransaction.commit();
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
                esercizio.setRecuperoSerie(ExerciseConstants.recoverList[((NumberPicker) viewFragment.findViewById(R.id.textRecoverSeries)).getValue()]);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + typeExePicker.getSelectedItem().toString());
        }
        return esercizio;

    }

    private boolean dataNullOrEmpty(Esercizio esercizio) {

        return StringUtils.isEmpty(esercizio.getSerie()) ||
                StringUtils.isEmpty(esercizio.getRecupero()) ||
                StringUtils.isEmpty(esercizio.getName()) ||
                (StringUtils.isEmpty(esercizio.getRipetizioni()) && StringUtils.isEmpty(esercizio.getInizio()));
    }


}
