package com.example.nusrat.smarthealthmonitoringsystem;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Emergency extends AppCompatActivity {

    private String bpH,bpL,bSugar,date,Msg,ID,pha="+8801521316362",phb="+8801718853660",stomach ;
    private Button EmCallDoctor,EmCallCont,reportDoctor;
    private TextView msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        reportDoctor=findViewById(R.id.reportDoctor);

        Bundle extra= getIntent().getExtras();
        if(extra!=null)
        {
            bpH=extra.getString("pH");
            bpL=extra.getString("pL");
            bSugar=extra.getString("sugar");
            date=extra.getString("date");
            Msg=extra.getString("msg");
            ID=extra.getString("id");
            stomach=extra.getString("stomach");
        }
        msg=findViewById(R.id.msg);
        msg.setText(""+Msg);


        reportDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        EmCallDoctor=findViewById(R.id.emergencycallId);
        EmCallCont=findViewById(R.id.contactcallId);
        EmCallDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                // Send phone number to intent as data
                intent.setData(Uri.parse("tel:" + pha));
                // Start the dialer app activity with number
                startActivity(intent);

            }
        });


        EmCallCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                // Send phone number to intent as data
                intent.setData(Uri.parse("tel:" + phb));
                // Start the dialer app activity with number
                startActivity(intent);
            }
        });


        reportDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("PatientData/"+ID);
                //   ulpoadUserData ulpoadUserData= new ulpoadUserData(""+bpH,""+bpL,""+bSugar,getCurrentDate(),Msg);


                if(bpH.length()>0 && bpL.length()>0 && bSugar.length()>0)
                {
                    //ulpoadUserData ulpoadUserData= new ulpoadUserData(""+bpH,""+bpL,bSugar,getCurrentDate(),Msg,stomach);
                    //databaseReference.child(getCurrentDate()).setValue(ulpoadUserData);

                    databaseReference.child(getCurrentDate()).child("date").setValue(getCurrentDate());
                    databaseReference.child(getCurrentDate()).child("highPresure").setValue(bpH);
                    databaseReference.child(getCurrentDate()).child("lowPresure").setValue(bpL);
                    databaseReference.child(getCurrentDate()).child("msg").setValue(Msg);
                    databaseReference.child(getCurrentDate()).child("stomach").setValue(stomach);
                    databaseReference.child(getCurrentDate()).child("sugar").setValue(bSugar);
                    Toast.makeText(Emergency.this,"Successfully reported !!!",Toast.LENGTH_LONG).show();
                }

                if(bpH.length()>0 && bpL.length()>0 && bSugar.length()==0)
                {
                    //ulpoadUserData ulpoadUserData= new ulpoadUserData(""+bpH,""+bpL,getCurrentDate(),Msg,stomach);
                   // databaseReference.child(getCurrentDate()).setValue(ulpoadUserData);

                    databaseReference.child(getCurrentDate()).child("date").setValue(getCurrentDate());
                    databaseReference.child(getCurrentDate()).child("highPresure").setValue(bpH);
                    databaseReference.child(getCurrentDate()).child("lowPresure").setValue(bpL);
                    databaseReference.child(getCurrentDate()).child("msg").setValue(Msg);
                    databaseReference.child(getCurrentDate()).child("stomach").setValue(stomach);


                    Toast.makeText(Emergency.this,"Successfully reported !!!",Toast.LENGTH_LONG).show();
                }
                else if(bpH.length()==0 && bpL.length()==0 && bSugar.length()>0)
                {
                   // ulpoadUserData ulpoadUserData= new ulpoadUserData(""+bSugar,getCurrentDate(),Msg,stomach);
                   // databaseReference.child(getCurrentDate()).setValue(ulpoadUserData);

                    databaseReference.child(getCurrentDate()).child("date").setValue(getCurrentDate());
                    databaseReference.child(getCurrentDate()).child("msg").setValue(Msg);
                    databaseReference.child(getCurrentDate()).child("stomach").setValue(stomach);
                    databaseReference.child(getCurrentDate()).child("sugar").setValue(bSugar);


                    Toast.makeText(Emergency.this,"Successfully reported !!!",Toast.LENGTH_LONG).show();
                }

            }
        });



    }

    public String getCurrentDate()
    {
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
        String today;
        today=dateFormat.format(calendar.getTime());
        return today;

    }

}
