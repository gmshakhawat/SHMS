package com.example.nusrat.smarthealthmonitoringsystem;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class checkActivity extends AppCompatActivity {


    private String id,drName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        Bundle extra=getIntent().getExtras();
        if(extra!=null)
        {
            id=extra.getString("id");
            drName=extra.getString("drName");

        }


        patientTappointment patientTappointment= com.example.nusrat.smarthealthmonitoringsystem.patientTappointment.newInstance(id,drName);
        getSupportFragmentManager().beginTransaction().replace(R.id.patientframeContainer,patientTappointment).commit();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
