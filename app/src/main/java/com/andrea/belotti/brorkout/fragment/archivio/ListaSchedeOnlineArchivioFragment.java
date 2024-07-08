package com.andrea.belotti.brorkout.fragment.archivio;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.constants.ExerciseConstants;
import com.andrea.belotti.brorkout.fragment.archivio.SceltaGiornoArchivioFragment;
import com.andrea.belotti.brorkout.model.Scheda;
import com.andrea.belotti.brorkout.utils.JsonGeneratorUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ListaSchedeOnlineArchivioFragment extends Fragment {

    private final String tag = this.getClass().getSimpleName();
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(tag, ExerciseConstants.TAG_START_FRAGMENT);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schede_list, container, false);

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Schedules").child("user").getRef();


        context = getContext();
        List<Scheda> schedaOnlineList = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    snapshot.getChildren().forEach(c -> {
                        String title = c.getKey().toString();

                        try {
                            Scheda scheda = JsonGeneratorUtil.generateScheduleFromJson(snapshot.child(title).child("DATA").getValue().toString());
                            schedaOnlineList.add(scheda);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    });

                    //TODO mettere fuori se possibile
                    if (!schedaOnlineList.isEmpty()) {
                        createView(schedaOnlineList, view);
                    } else {
                        Log.e(tag, "Lista schede online vuota");
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }

    private void createView(List<Scheda> schedaList, View view) {
        LinearLayout scheduleLayout;

        scheduleLayout = view.findViewById(R.id.scheduleListView);



        for (Scheda scheda : schedaList) {
            Button button = new Button(context);
            //text
            button.setText(scheda.getNome());
            button.setTextSize(15f);
            button.setTextColor(ExerciseConstants.Color.TEXT_BUTTON_COLOR);
            // button
            button.setBackground(ContextCompat.getDrawable(context,R.drawable.circled_button));
            button.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorbuttonbase));

            //margin
            //TODO da levare
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            button.setLayoutParams(params);

            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) button.getLayoutParams();
            marginLayoutParams.setMargins(0,0,0, 10);


            button.setOnClickListener(v -> {

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                SceltaGiornoArchivioFragment sceltaGiornoArchivioFragment = new SceltaGiornoArchivioFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("Scheda", scheda);
                sceltaGiornoArchivioFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragmentContainerArchivioView, sceltaGiornoArchivioFragment);
                fragmentTransaction.commit();

            });

            scheduleLayout.addView(button);

        }
    }


}