package com.example.nusrat.smarthealthmonitoringsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class doctorLayout extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String name,age,email,phone,key,type,ID;
    private TextView nameText,idText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            key=bundle.getString("key");

        }

        View navView=navigationView.inflateHeaderView(R.layout.nav_header_doctor_layout);

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
                userProfile userProfile= com.example.nusrat.smarthealthmonitoringsystem.userProfile.newInstance(name,age,phone,email,type,ID,"D");
                getSupportFragmentManager().beginTransaction().replace(R.id.doctorframeContainer,userProfile).commit();
                navigationView.setCheckedItem(R.id.doctorProfile);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.doctor_layout, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_logout) {
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

        if (id == R.id.doctorProfile) {
            userProfile userProfile= com.example.nusrat.smarthealthmonitoringsystem.userProfile.newInstance(name,age,phone,email,type,ID,"D");
            getSupportFragmentManager().beginTransaction().replace(R.id.doctorframeContainer,userProfile).commit();

        }
        else if(id==R.id.doctorRequest)
        {

            Intent apReq= new Intent(doctorLayout.this,MainActivity.class);

            apReq.putExtra("name",name);


            startActivity(apReq);

//            appointmentRequest appointmentRequest= com.example.nusrat.smarthealthmonitoringsystem.appointmentRequest.newInstance(name);
//            getSupportFragmentManager().beginTransaction().replace(R.id.doctorframeContainer,appointmentRequest).commit();

        }
        else if(id==R.id.doctorAppointmentList)
        {

            ViewAppointment viewAppointment= com.example.nusrat.smarthealthmonitoringsystem.ViewAppointment.newInstance(ID,name);
            getSupportFragmentManager().beginTransaction().replace(R.id.doctorframeContainer,viewAppointment).commit();

        }
        else if(id==R.id.doctorPatientfind)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.doctorframeContainer, new findPatientData()).commit();

        }
        else if(id==R.id.doctorDegree)
        {
            addDegree addDegree= com.example.nusrat.smarthealthmonitoringsystem.addDegree.newInstance(ID,name);
            getSupportFragmentManager().beginTransaction().replace(R.id.doctorframeContainer,addDegree).commit();

        }
        else if (id == R.id.DoctorLogout) {
            FirebaseAuth.getInstance().signOut();
            intent = new Intent(doctorLayout.this, LogIn.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
