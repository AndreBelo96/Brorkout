package com.andrea.belotti.brorkout.fragment.execute_plan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.activity.StartingMenuActivity;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.model.nodes.Node;
import com.andrea.belotti.brorkout.model.nodes.PlanCompletedNode;
import com.andrea.belotti.brorkout.model.Esercizio;
import com.andrea.belotti.brorkout.model.Scheda;
import com.andrea.belotti.brorkout.utils.JsonGeneratorUtil;
import com.andrea.belotti.brorkout.utils.ScheduleCreatingUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;
import static com.andrea.belotti.brorkout.constants.ExerciseConstants.Color.EXE_KO_COLOR;
import static com.andrea.belotti.brorkout.constants.ExerciseConstants.Color.EXE_OK_COLOR;
import static com.andrea.belotti.brorkout.constants.ExerciseConstants.Color.TEXT_BUTTON_COLOR;
import static com.andrea.belotti.brorkout.constants.ExerciseConstants.Size.NORMAL_SIZE;


public class EndScheduleSummaryFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();

    private Context context;

    LinearLayout.LayoutParams wrapParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1.0f
    );

    public static EndScheduleSummaryFragment newInstance(Scheda scheda, Integer giorni) {
        EndScheduleSummaryFragment fragment = new EndScheduleSummaryFragment();
        Bundle args = new Bundle();
        args.putSerializable(ExerciseConstants.MemorizeConstants.SCHEDA, scheda);
        args.putInt(ExerciseConstants.MemorizeConstants.NUMERO_GIORNATE, giorni);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(tag, ExerciseConstants.TAG_START_FRAGMENT);

        View view = inflater.inflate(R.layout.fragment_end_schedule_summary, container, false);
        context = getContext();

        TextView textTitolo = view.findViewById(R.id.textTitoloEGiornata);
        Button buttonBack = view.findViewById(R.id.buttonBackToMainMenu);

        Scheda scheda = null;
        Integer giorno = null;

        if (getArguments() != null) {
            scheda = (Scheda) getArguments().get(ExerciseConstants.MemorizeConstants.SCHEDA);
            giorno = (Integer) getArguments().get(ExerciseConstants.MemorizeConstants.NUMERO_GIORNATE);
        } else {
            Log.i(tag, ExerciseConstants.ERROR_ARGUMENT);
        }

        if (scheda != null && giorno != null) {
            createTableOfExercises(scheda.getGiornate().get(giorno - 1).getEsercizi(), view);
            savePlanLocal(scheda);
            textTitolo.setText(scheda.getNome() + " giorno " + giorno);
        } else {
            Log.i(tag, ExerciseConstants.DATA_ARGUMENT_NULL);
        }

        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), StartingMenuActivity.class);
            startActivity(intent);
        });

        return view;
    }

    private void createTableOfExercises(List<Esercizio> exeList, View view) {
        // Layout
        LinearLayout layoutElencoEsecizi = view.findViewById(R.id.layoutSummaryEndSchedule);

        for (Esercizio esercizio : exeList) {

            LinearLayout layoutExercise = new LinearLayout(context);
            layoutExercise.setOrientation(LinearLayout.VERTICAL);

            // TextView
            TextView resumeExeTextView = new TextView(context);
            resumeExeTextView.setTextSize(NORMAL_SIZE);
            resumeExeTextView.setText(esercizio.toStringResumeEndSchedule());
            resumeExeTextView.setTextColor(TEXT_BUTTON_COLOR);

            // Adding to the list the exercise
            layoutExercise.addView(resumeExeTextView);

            if (esercizio.getSerieCompletate() >= Integer.parseInt(esercizio.getSerie())) {
                layoutExercise.setBackgroundColor(EXE_OK_COLOR);
            } else {
                layoutExercise.setBackgroundColor(EXE_KO_COLOR);
            }

            // Space
            Space exercicesSpacePrev = new Space(context);
            exercicesSpacePrev.setMinimumHeight(10);

            Space exercicesSpaceBack = new Space(context);
            exercicesSpaceBack.setMinimumHeight(10);

            // Line
            View lineView = new View(context);
            lineView.setLayoutParams(wrapParams);
            lineView.setMinimumHeight(3);
            lineView.setBackgroundColor(TEXT_BUTTON_COLOR);

            //Adding to the layout the exe
            layoutElencoEsecizi.addView(layoutExercise);
            layoutElencoEsecizi.addView(exercicesSpacePrev);
            layoutElencoEsecizi.addView(lineView);
            layoutElencoEsecizi.addView(exercicesSpaceBack);

        }
    }


    private void savePlanLocal(Scheda scheda) {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);

        DateTimeFormatter dtfYear = DateTimeFormatter.ofPattern("yyyy");
        //DateTimeFormatter dtfMonth = DateTimeFormatter.ofPattern("mm");
        LocalDateTime now = LocalDateTime.now();

        Node rootNode = ScheduleCreatingUtils.getNodeFromPref(sharedPreferences);

        List<Node> yearNodes = new ArrayList<>();
        List<Node> monthNodes = new ArrayList<>();
        List<PlanCompletedNode> plansOfMonth = new ArrayList<>();

        // Se il nodo root dell'archivio è vuoto, devo creare la prima istanza del mio archivio
        if (rootNode.isEmpty()) {

            // Set del nodo root dell'archivio
            rootNode.setName("ROOT");
            rootNode.setId(0L);

            // crea nuovo nodo anno e aggiungilo all'oggetto
            Node actualYear = new Node(1L, dtfYear.format(now), 0L);

            // crea nuovo mese a aggiungilo alla lista
            Node actualMonth = new Node(2L, now.getMonth().name(), 1L);

            // creo un planNode partendo dalla scheda compeltata
            PlanCompletedNode plan = new PlanCompletedNode(3L, 2L, scheda.getNome(), scheda);

            plansOfMonth.add(plan);
            actualMonth.setData(plansOfMonth);

            monthNodes.add(actualMonth);

            actualYear.setChildren(monthNodes);
            yearNodes.add(actualYear);

            rootNode.setChildren(yearNodes);

        } else {
            // Esiste per forza un node all'interno della lista, non può essere vuota
            yearNodes = rootNode.getChildren();


            // controlle se esiste l'anno in cui sono
            Node actualYear = yearNodes.stream()
                    .filter(year -> dtfYear.format(now).equals(year.getName()))
                    .findFirst().orElse(null);

            // trovo il max Id usato finora
            Long maxId = ScheduleCreatingUtils.getMaxId(rootNode.getChildren());

            // se non esiste lo stesso anno in cui sono -> lo creo e creo i suoi figli
            if (actualYear == null) {
                // crea nuovo nodo anno e aggiungilo all'oggetto, gli metto il maxID + 1, il padre sarà sempre root
                actualYear = new Node((maxId + 1L), dtfYear.format(now), 0L);

                // crea nuovo mese a aggiungilo alla lista
                Node actualMonth = new Node((maxId + 2L), now.getMonth().name(), (maxId + 1L));

                // creo un planNode partendo dalla scheda compeltata
                PlanCompletedNode plan = new PlanCompletedNode((maxId + 3L), (maxId + 2L), scheda.getNome(), scheda);

                plansOfMonth.add(plan);
                actualMonth.setData(plansOfMonth);

                monthNodes.add(actualMonth);

                actualYear.setChildren(monthNodes);
                yearNodes.add(actualYear);

                rootNode.setChildren(yearNodes);

            } else { // se esiste lo stesso anno in cui sono -> utilizzo quello e cerco tra i mesi

                // se il mio nodo anno non ha figli allora ne creo io, sia il mese che la scheda
                if (!actualYear.hasChild()) {

                    // crea nuovo mese a aggiungilo alla lista
                    Node actualMonth = new Node((maxId + 1L), now.getMonth().name(), actualYear.getId());

                    // creo un planNode partendo dalla scheda compeltata
                    PlanCompletedNode plan = new PlanCompletedNode((maxId + 2L), (maxId + 1L), scheda.getNome(), scheda);

                    plansOfMonth.add(plan);
                    actualMonth.setData(plansOfMonth);

                    monthNodes.add(actualMonth);

                    actualYear.setChildren(monthNodes);
                    //change noderoot? da testare

                } else { // altrimenti cerco tra i mesi l'attuale

                    monthNodes = actualYear.getChildren();

                    // cerco se nei mesi esistenti salvati c'è anche quello in cui sono
                    Node actualMonth = monthNodes.stream()
                            .filter(month -> now.getMonth().name().equals(month.getName()))
                            .findFirst().orElse(null);

                    // se il mese odierno non esiste tra i salvati lo creo e creo il plan
                    if (actualMonth == null) {

                        actualMonth = new Node((maxId+ 1L), now.getMonth().name(), actualYear.getId());

                        // creo un planNode partendo dalla scheda compeltata
                        PlanCompletedNode plan = new PlanCompletedNode((maxId+ 2L), (maxId+ 1L), scheda.getNome(), scheda);

                        plansOfMonth.add(plan);
                        actualMonth.setData(plansOfMonth);

                        monthNodes.add(actualMonth);

                        actualYear.setChildren(monthNodes);

                        //change nodeyear? da testare
                    } else { // se invece esiste il mese in cui sono

                        plansOfMonth = actualMonth.getData();

                        // cerco se esiste la scheda che sto salvando tra quelle salvate
                        PlanCompletedNode actualPlan = plansOfMonth.stream()
                                .filter(plan -> plan.getName().equals(scheda.getNome()))
                                .findFirst()
                                .orElse(null);

                        if (actualPlan == null || actualPlan.isEmpty()) {

                            actualPlan = new PlanCompletedNode((maxId+1L), actualMonth.getId(), scheda.getNome(), scheda);
                            plansOfMonth.add(actualPlan);

                        } else {
                            plansOfMonth.remove(actualPlan);
                            actualPlan.setPlan(scheda);
                            plansOfMonth.add(actualPlan);
                        }

                    }

                }

            }
        }

        // Save rootNode
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString(ExerciseConstants.MemorizeConstants.ROOT, JsonGeneratorUtil.generateJsonFromObject(rootNode));
        myEdit.apply();

    }


}