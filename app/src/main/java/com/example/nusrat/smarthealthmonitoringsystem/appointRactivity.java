package com.example.nusrat.smarthealthmonitoringsystem;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class appointRactivity extends AppCompatActivity {


    private TextView name,date;
    private Button appButton;
    private ListView listView;

    private String mParam1;
    private String mParam2;
    private String drName;
    private Context context;

    private ArrayAdapter<String> nameAdapter;
    private ArrayAdapter<String> dateAdapter;

    private ArrayList<String> nameList=new ArrayList<>();
    private ArrayList<String> dateList=new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_ractivity);



        Bundle extra=getIntent().getExtras();
        if(extra!=null)
        {
            drName=extra.getString("name");

        }



 //       name=findViewById(R.id.pName);
//        date=findViewById(R.id.pApDate);
//        appButton=findViewById(R.id.appointmentRequestApprove);
        listView = (ListView) findViewById(R.id.tasklist);
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Request/"+drName);
        dateList.clear();
        nameList.clear();

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String key=dataSnapshot.getKey();
                dateList.add(key);
                String value=dataSnapshot.getValue(String.class);
                nameList.add(value);
                loadTaskList();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });










    }




    private void loadTaskList(){
        //ArrayList<String> taskList=dbHelper.getTaskList();


        Log.i("nameListD",""+dateList);


        if(nameAdapter==null){
            nameAdapter=new ArrayAdapter<String>(this,R.layout.appointment_request_view,R.id.pName,nameList);
            listView.setAdapter(nameAdapter);
            Log.i("nameList",""+nameList);
            //dateAdapter=new ArrayAdapter<String>(this,R.layout.appointment_request_view,R.id.pApDate,dateList);

        }
        else{
            nameAdapter.clear();
            nameAdapter.addAll(nameList);
            Log.i("nameListA",""+nameAdapter);
            nameAdapter.notifyDataSetChanged();
        }
    }


}
