package com.example.nusrat.smarthealthmonitoringsystem;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivityP extends AppCompatActivity {

    DbHelper dbHelper;
    ArrayAdapter<String> mAdapter;
    ListView listView;
    String drName,st;
    Button del;

    private ArrayAdapter<String> nameAdapter;
    private ArrayAdapter<String> dateAdapter;

    private TextView cancel,aprove;

    private ArrayList<String> nameList=new ArrayList<>();
    private ArrayList<String> dateList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//       // setSupportActionBar(toolbar);

//        cancel=findViewById(R.id.cancelText);
//        aprove=findViewById(R.id.aproveText);



        Bundle extra=getIntent().getExtras();
        if(extra!=null)
        {
            drName=extra.getString("name");

        }

        dataShow();




        Log.i("drName",""+drName);


        listView = (ListView) findViewById(R.id.tasklist);




    }


    private void dataShow()
    {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Request/"+drName);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String key=dataSnapshot.getKey();
                nameList.add(key);

                String value=dataSnapshot.getValue(String.class);
                dateList.add(key+"_"+value);               /// Dr Thomas._____.2019-3-12

                Log.i("drNameL",""+dateList);
                Log.i("drNameN",""+nameList);

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
       // ArrayList<String> taskList=dbHelper.getTaskList();



        if(mAdapter==null){

            mAdapter=new ArrayAdapter<String>(this,R.layout.listitem_p,R.id.pName,dateList);

            listView.setAdapter(mAdapter);
            Log.i("drNameNDif",""+nameList);
            Log.i("drNameDDif",""+dateList);
        }
        else{
//           mAdapter.clear();
//           mAdapter.addAll(dateList);
//           mAdapter.notifyDataSetChanged();
//            Log.i("drNameNDel",""+nameList);
//            Log.i("drNameDDel",""+dateList);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    public void cancelAp(View v) {
        Log.i("drNameND",""+nameList);
        Log.i("drNameDD",""+dateList);
        View parent=(View)v.getParent();

        TextView taskview=(TextView)parent.findViewById(R.id.pName);
        String task=taskview.getText().toString();

        Button c1=parent.findViewById(R.id.appointmentRequestCancel);
        c1.setVisibility(View.GONE);

        TextView cancel1=parent.findViewById(R.id.cancelText);
        cancel1.setVisibility(View.VISIBLE);
        String[] arr = task.split("_");
        deleteData((arr[0]));
        Toast.makeText(MainActivityP.this,"Appointment of "+arr[0]+" in "+arr[1]+"has been Canceled",Toast.LENGTH_SHORT).show();
    }




    private void deleteData(String name){

        DatabaseReference drData=FirebaseDatabase.getInstance().getReference("Request/"+drName+"/"+name);
        DatabaseReference pData=FirebaseDatabase.getInstance().getReference("Request/"+name+"/"+drName);
        drData.removeValue();
        pData.removeValue();




    }
}

