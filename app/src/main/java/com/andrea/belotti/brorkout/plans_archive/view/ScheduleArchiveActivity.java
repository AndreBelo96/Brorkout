package com.andrea.belotti.brorkout.plans_archive.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.andrea.belotti.brorkout.R;
import com.andrea.belotti.brorkout.model.Giornata;
import com.andrea.belotti.brorkout.model.nodes.Node;
import com.andrea.belotti.brorkout.model.nodes.PlanCompletedNode;

import androidx.appcompat.app.AppCompatActivity;

public class ScheduleArchiveActivity extends AppCompatActivity {

    // resources to save
    private Node yearNode;
    private Node monthNode;
    private PlanCompletedNode planCompletedNode;
    private Giornata day;


    private TextView pathArchive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(this.getClass().getSimpleName(), "Starting activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_archive);

        ImageButton backButton = findViewById(R.id.backButton);

        pathArchive = findViewById(R.id.pathArchive);

        /*backButton.setOnClickListener(v -> {
            Log.i(this.getClass().getSimpleName(), "Home button");
            Intent intent = new Intent(getBaseContext(), StartingMenuActivity.class);
            startActivity(intent);
        });*/

    }

    // Getter and Setter
    public void setPath(String path) {
        pathArchive.setText(path);
    }

    public String getPath() {
        return pathArchive.getText().toString();
    }

    public Node getYearNode() {
        return yearNode;
    }

    public void setYearNode(Node yearNode) {
        this.yearNode = yearNode;
    }

    public Node getMonthNode() {
        return monthNode;
    }

    public void setMonthNode(Node monthNode) {
        this.monthNode = monthNode;
    }

    public PlanCompletedNode getPlanCompletedNode() {
        return planCompletedNode;
    }

    public void setPlanCompletedNode(PlanCompletedNode planCompletedNode) {
        this.planCompletedNode = planCompletedNode;
    }

    public Giornata getDay() {
        return day;
    }

    public void setDay(Giornata day) {
        this.day = day;
    }

    public TextView getPathArchive() {
        return pathArchive;
    }

    public void setPathArchive(TextView pathArchive) {
        this.pathArchive = pathArchive;
    }
}