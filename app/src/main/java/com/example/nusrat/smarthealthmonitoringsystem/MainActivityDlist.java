package com.example.nusrat.smarthealthmonitoringsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivityDlist extends AppCompatActivity {

    DbHelper dbHelper;
    ArrayAdapter<String> mAdapter;
    ListView listView,degList;
    String drName,st,id;
    Button del;

    FrameLayout frameLayout;

    private ArrayAdapter<String> nameAdapter;
    private ArrayAdapter<String> degAdapter;

    private TextView cancel,aprove;

    private ArrayList<String> nameList=new ArrayList<>();
    private ArrayList<String> dateList=new ArrayList<>();
    private ArrayList<String> allList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//       // setSupportActionBar(toolbar);

        cancel=findViewById(R.id.cancelText);
        aprove=findViewById(R.id.aproveText);

        frameLayout=findViewById(R.id.containerDlist);

        Bundle extra=getIntent().getExtras();
        if(extra!=null)
        {
            id=extra.getString("id");

        }


        dataShow();




        Log.i("drName","LIST");


        listView = (ListView) findViewById(R.id.tasklist);




    }

private String v,k;
    private void dataShow()
    {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Degree");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {




                String key=dataSnapshot.getKey();

                DatabaseReference dataCollect=FirebaseDatabase.getInstance().getReference("Degree/"+key);
                Log.i("drNameKey",""+key);

                dataCollect.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {



                        if(dataSnapshot.getKey().equals("degree"))
                        {
                            v=dataSnapshot.getValue(String.class);
                        }
                        else {
                            k=dataSnapshot.getValue(String.class);
                            allList.add(k+"\n\n"+v);

                        }

                        Log.i("drNameA",""+allList);


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
       // ArrayList<String> taskList=dbHelper.getTaskList();





        mAdapter=new ArrayAdapter<String>(this,R.layout.listitemdlist,R.id.pName,allList);
        listView.setAdapter(mAdapter);
        Log.i("drNameNDif",""+nameList);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }




    public void aproveAp(View v) {

        Log.i("drNameND",""+nameList);
        Log.i("drNameDD",""+dateList);
        View parent=(View)v.getParent();
        TextView taskview=(TextView)parent.findViewById(R.id.pName);
        String task=taskview.getText().toString();
        String[] arr = task.split("\n");
        String ddrName=arr[0];
        Log.i("dddd",""+ddrName);

//        frameLayout.setVisibility(View.VISIBLE);
//        listView.setVisibility(View.GONE);




//
//        patientTappointment patientTappointment= com.example.nusrat.smarthealthmonitoringsystem.patientTappointment.newInstance(id,ddrName);
//        getSupportFragmentManager().beginTransaction().replace(R.id.containerDlist,patientTappointment).commit();



        Intent apReq= new Intent(MainActivityDlist.this,checkActivity.class);
//
        apReq.putExtra("id",id);
        apReq.putExtra("drName",ddrName);

        startActivity(apReq);




    }



}

