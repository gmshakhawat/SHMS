package com.example.nusrat.smarthealthmonitoringsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LogIn extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText email, pass;
    private Button logIn,reg,forgetPass;
    private Spinner LoginMode;
    private Intent intent;
    private CheckBox showPassword,checkBox;
    private SharedPreferences idsh;
    private SharedPreferences passsh;
    String sid,spass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.logemailid);
        pass = (EditText) findViewById(R.id.logpassid);

        logIn = (Button)findViewById(R.id.loginimgId);
        checkBox =(CheckBox)findViewById(R.id.checkBox);
        reg = (Button)findViewById(R.id.regimgId);
        LoginMode=findViewById(R.id.spinner);
        idsh=getSharedPreferences("id",MODE_PRIVATE);
        passsh=getSharedPreferences("pass",MODE_PRIVATE);
        forgetPass=findViewById(R.id.forgetPassword);


        ArrayAdapter<CharSequence> LoginList = ArrayAdapter.createFromResource(this,R.array.LoginModeList,android.R.layout.simple_spinner_item);
        LoginList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        LoginMode.setAdapter(LoginList);

        final CheckBox showPassword = (CheckBox) findViewById(R.id.showPass);
        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    // showPassword.setBackgroundColor(Color.parseColor("#000"));


                }
                else
                {
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });


        sid=idsh.getString("id","");
        spass=passsh.getString("pass","");
        email.setText(sid+"");
        pass.setText(spass+"");
        Log.i("siid","sid is "+sid);


        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SlogMail = email.getText().toString();
                String SlogPass = pass.getText().toString();
                String loginType=LoginMode.getSelectedItem().toString();
                if(loginType.equals("Login Mode"))
                {
                    Toast.makeText(LogIn.this,"Please Select Login Mode ",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!TextUtils.isEmpty(SlogMail) && !TextUtils.isEmpty(SlogPass)){

                    logInUser(SlogMail,SlogPass,loginType);

                    if(checkBox.isChecked())
                    {


                        SharedPreferences.Editor editID=idsh.edit();
                        editID.putString("id",SlogMail);
                        editID.apply();
                        editID.commit();

                        SharedPreferences.Editor editpass=passsh.edit();
                        editpass.putString("pass",SlogPass);
                        editpass.apply();
                        editpass.commit();

                        Toast.makeText(getApplicationContext(),"Password Saved!!!",Toast.LENGTH_LONG).show();


                    }
                }
            }
        });

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String em =email.getText().toString();

                if(em.length()>0)
                {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(em)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LogIn.this, "Check your Email: "+em+" for Password Reset Link!!!",Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(LogIn.this, "Sorry,Email: "+em+" is found wrong!!!",Toast.LENGTH_LONG).show();
                                }
                            }

                        });
                }

            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RegIntent = new Intent(LogIn.this,Register.class);
                startActivity(RegIntent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        //Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null) {
//            startActivity(intent);
//            finish();
//        }
    }

    private void logInUser(String slogMail, String slogPass, final String loginType) {
        mAuth.signInWithEmailAndPassword(slogMail, slogPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users/"+(FirebaseAuth.getInstance().getCurrentUser().getUid()).toString());

                            databaseReference.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                    String key=dataSnapshot.getKey();
                                    String accessType;

                                    if(key.equals("accessType"))
                                    {
                                        accessType=dataSnapshot.getValue(String.class);

                                        if(accessType.equals(loginType))
                                        {


                                            if(accessType.equals("Patient"))
                                            {
                                                intent = new Intent(LogIn.this, patientLayout.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                intent.putExtra("key",""+FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                                                startActivity(intent);
                                            }
                                            else if(accessType.equals("Doctor"))
                                            {
                                                intent = new Intent(LogIn.this, doctorLayout.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                intent.putExtra("key",""+FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                                                startActivity(intent);
                                            }
                                            else
                                            {
                                                FirebaseAuth.getInstance().signOut();
                                                Intent MainIntent = new Intent(LogIn.this, LogIn.class);
                                                startActivity(MainIntent);
                                                finish();
                                            }

                                        }
                                        else {
                                            Toast.makeText(LogIn.this,"You are not Registered as a "+loginType,Toast.LENGTH_SHORT).show();
                                            FirebaseAuth.getInstance().signOut();
                                            Intent MainIntent = new Intent(LogIn.this, LogIn.class);
                                            startActivity(MainIntent);
                                            finish();
                                        }



                                        finish();

                                    }
                                    else {
                                        FirebaseAuth.getInstance().signOut();
                                    }

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
                                    FirebaseAuth.getInstance().signOut();
                                    Intent MainIntent = new Intent(LogIn.this, LogIn.class);
                                    startActivity(MainIntent);
                                    finish();

                                }
                            });


                        } else {

                            Toast.makeText(LogIn.this,"       Try Again !!!\nWrong Email or Password !!!",Toast.LENGTH_SHORT).show();
                            // If sign in fails, display a message to the user
                        }
                    }
                });
    }
}
