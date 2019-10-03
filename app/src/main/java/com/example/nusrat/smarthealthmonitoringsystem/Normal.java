package com.example.nusrat.smarthealthmonitoringsystem;

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

public class Normal extends AppCompatActivity {
    private String bpH,bpL,bSugar,date,Msg,ID ,stomach;
    private Button appointmentButton,reportDoctor;
    private TextView msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);


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
        appointmentButton=findViewById(R.id.appoinmentId);
        appointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patientTappointment patientTappointment= com.example.nusrat.smarthealthmonitoringsystem.patientTappointment.newInstance(ID,"PP");
                getSupportFragmentManager().beginTransaction().replace(R.id.normalcontainer,patientTappointment).commit();
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
                    Toast.makeText(Normal.this,"Successfully reported !!!",Toast.LENGTH_LONG).show();
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


                    Toast.makeText(Normal.this,"Successfully reported !!!",Toast.LENGTH_LONG).show();
                }
                else if(bpH.length()==0 && bpL.length()==0 && bSugar.length()>0)
                {
                    // ulpoadUserData ulpoadUserData= new ulpoadUserData(""+bSugar,getCurrentDate(),Msg,stomach);
                    // databaseReference.child(getCurrentDate()).setValue(ulpoadUserData);

                    databaseReference.child(getCurrentDate()).child("date").setValue(getCurrentDate());
                    databaseReference.child(getCurrentDate()).child("msg").setValue(Msg);
                    databaseReference.child(getCurrentDate()).child("stomach").setValue(stomach);
                    databaseReference.child(getCurrentDate()).child("sugar").setValue(bSugar);


                    Toast.makeText(Normal.this,"Successfully reported !!!",Toast.LENGTH_LONG).show();
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
