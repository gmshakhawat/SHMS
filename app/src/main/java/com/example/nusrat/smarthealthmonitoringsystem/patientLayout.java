package com.example.nusrat.smarthealthmonitoringsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class patientLayout extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String name,age,email,phone,key,type,ID;
    private TextView nameText,idText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });



        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            key=bundle.getString("key");

        }






//        databaseReference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                String key2=dataSnapshot.getKey();
//
//                String v="";
//                if(key2.equals("name"))
//                {
//                    name=dataSnapshot.getValue(String.class);
//                    v=name;
//                }
//                else if(key2.equals("age"))
//                {
//                    age=dataSnapshot.getValue(String.class);
//                    v=age;
//                }
//                else if(key2.equals("phone"))
//                {
//                    phone=dataSnapshot.getValue(String.class);
//                    v=phone;
//                }
//                else if(key2.equals("email"))
//                {
//                    email=dataSnapshot.getValue(String.class);
//                    v=email;
//                }
//                else if(key2.equals("accessType"))
//                {
//                    type=dataSnapshot.getValue(String.class);
//                    v=type;
//                }
//
//                Log.i("key :",""+key+"\n key2 : : :"+v);
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navView=navigationView.inflateHeaderView(R.layout.nav_header_patient_layout);

        nameText=navView.findViewById(R.id.nevName);
        idText=navView.findViewById(R.id.navID);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users/"+key);
        Log.i("key :",""+key+"\n key2");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User userProfileData = dataSnapshot.getValue(User.class);
                name=userProfileData.getName();
                age=userProfileData.getAge();
                phone=userProfileData.getPhone();
                email=userProfileData.getEmail();
                type=userProfileData.getAccessType();
                ID=userProfileData.getId();

                idText.setText("ID - "+ID);
                nameText.setText(name);

                Log.i("key :",""+name+" "+age+" \nphone "+phone+" email "+email+" Type"+type);
                userProfile userProfile= com.example.nusrat.smarthealthmonitoringsystem.userProfile.newInstance(name,age,phone,email,type,ID,"P");
                getSupportFragmentManager().beginTransaction().replace(R.id.patientframeContainer,userProfile).commit();
                navigationView.setCheckedItem(R.id.patientProfile);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        Log.i("profile :",""+name+" "+age+" \nphone "+phone+" email "+email+" Type"+type);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.patient_layout, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;

        if (id == R.id.patientProfile) {
            userProfile userProfile= com.example.nusrat.smarthealthmonitoringsystem.userProfile.newInstance(name,age,phone,email,type,ID,"P");
            getSupportFragmentManager().beginTransaction().replace(R.id.patientframeContainer,userProfile).commit();

            // Handle the camera action
        }
        else if (id == R.id.patientDataInput)
        {
            patientDataInput patientDataInput= com.example.nusrat.smarthealthmonitoringsystem.patientDataInput.newInstance(key,ID);
            getSupportFragmentManager().beginTransaction().replace(R.id.patientframeContainer,patientDataInput).commit();


        }
        else if(id==R.id.patientAppointment)
        {

            patientTappointment patientTappointment= com.example.nusrat.smarthealthmonitoringsystem.patientTappointment.newInstance(ID,"PP");
            getSupportFragmentManager().beginTransaction().replace(R.id.patientframeContainer,patientTappointment).commit();

        }
        else if(id==R.id.patientAppointmentView)
        {

            ViewAppointment viewAppointment= com.example.nusrat.smarthealthmonitoringsystem.ViewAppointment.newInstance(ID,name);
            getSupportFragmentManager().beginTransaction().replace(R.id.patientframeContainer,viewAppointment).commit();

        }
        else if(id==R.id.patientDoctorListView)
        {


           Intent intent2 = new Intent(patientLayout.this, MainActivityDlist.class);
            intent2.putExtra("id",ID);

            startActivity(intent2);

        }
        else if(id==R.id.patientRequest)
        {

            Intent apReq= new Intent(patientLayout.this,MainActivityP.class);

            apReq.putExtra("name",ID);

            startActivity(apReq);

        }

        else if(id==R.id.patientDataView)
        {
            findPatientData findPatientData= com.example.nusrat.smarthealthmonitoringsystem.findPatientData.newInstance(ID,"p");
            getSupportFragmentManager().beginTransaction().replace(R.id.patientframeContainer,findPatientData).commit();

        }
        else if (id == R.id.patientLogout) {
            FirebaseAuth.getInstance().signOut();
            intent = new Intent(patientLayout.this, LogIn.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
