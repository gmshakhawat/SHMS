package com.example.nusrat.smarthealthmonitoringsystem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mFireDb;

    private EditText UserText;
    private EditText EmailText;
    private EditText PasswordText;
    private EditText AgeText;
    private CheckBox PatientId;
    private EditText userNID,userBC;
    private CheckBox DoctorId;
    private int idCount;


    private String S_username ;
   private String S_Email ;
    private String S_Pass ;

    private String accessType,age,phone,nid,bc;



    private EditText Phone;


    private Button ConfirmRegBtn;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser=mAuth.getCurrentUser();
        idCount=getID("Users");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

       // FirebaseApp.initializeApp();



//        FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.getApplicationDefault())
//                .setServiceAccountId("my-client-idCount@my-project-idCount.iam.gserviceaccount.com")
//                .build();
//        FirebaseApp.initializeApp(options);

        mAuth = FirebaseAuth.getInstance();

        UserText = (EditText) findViewById(R.id.nameId);
        EmailText = (EditText) findViewById(R.id.emailId);
        PasswordText = (EditText) findViewById(R.id.passwordId);
        AgeText = (EditText) findViewById(R.id.ageId);
        PatientId=(CheckBox)findViewById(R.id.PatientId);
        userNID=findViewById(R.id.userNID);
        userBC=findViewById(R.id.userBC);
        DoctorId=(CheckBox)findViewById(R.id.DoctorId);

        Phone=findViewById(R.id.phonenumberId);



        ConfirmRegBtn = (Button) findViewById(R.id.signinId);

        ConfirmRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    S_username = UserText.getText().toString();
                 S_Email = EmailText.getText().toString();
                 S_Pass = PasswordText.getText().toString();


                age=AgeText.getText().toString();
                phone=Phone.getText().toString();
                nid=userNID.getText().toString();
                bc=userBC.getText().toString();

                        if(bc.length()>0 && nid.length()>0)
                        {
                            Toast.makeText(Register.this,"Input only one , NID or Birth Certificate ",Toast.LENGTH_SHORT).show();
                        }
                        else if(bc.length()==0 && nid.length()==0)
                        {
                            Toast.makeText(Register.this,"Please Input  NID or Birth Certificate Number",Toast.LENGTH_SHORT).show();

                        }
                        else if(nid.length()>0 && bc.length()==0)
                        {
                            DatabaseReference niddata=FirebaseDatabase.getInstance().getReference("NID");
                            niddata.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(!dataSnapshot.hasChild(nid)){

                                        accessType="";


                                        if(!PatientId.isChecked() && !DoctorId.isChecked())
                                        {
                                            Toast.makeText(Register.this,"Select one, Doctor or Patient ",Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        else if(PatientId.isChecked() && DoctorId.isChecked())
                                        {
                                            Toast.makeText(Register.this,"Select only one, Doctor or Patient ",Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        else if(PatientId.isChecked() && !DoctorId.isChecked())
                                        {
                                            accessType="Patient";
                                        }
                                        else if(DoctorId.isChecked() && !PatientId.isChecked())
                                        {
                                            accessType="Doctor";
                                        }

                                        String ID=accessType.charAt(0)+""+idCount;

                                        Log.i("IDcount",""+ID);
                                        Log.i("IDcount id",""+idCount);



                                        //Toast.makeText(Register.this,"Clicked",Toast.LENGTH_SHORT).show();

                                        // String customToken = FirebaseAuth.getInstance().createCustomToken(uid, additionalClaims);

                                        if(!TextUtils.isEmpty(S_username)|| !TextUtils.isEmpty(S_Email)||!TextUtils.isEmpty(S_Pass)){

                                            reg_user (S_username,S_Email,S_Pass,age,phone,accessType,ID,"nid");
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(Register.this,"You have Already registered with this NID ",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            Log.i("enterInto","NID");
                        }
                        else if(bc.length()>0 && nid.length()==0)
                        {
                            DatabaseReference bcData=FirebaseDatabase.getInstance().getReference("BC");
                            Log.i("enterInto","BC");

                            bcData.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(!dataSnapshot.hasChild(nid)){

                                        accessType="";


                                        if(!PatientId.isChecked() && !DoctorId.isChecked())
                                        {
                                            Toast.makeText(Register.this,"Select one, Doctor or Patient ",Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        else if(PatientId.isChecked() && DoctorId.isChecked())
                                        {
                                            Toast.makeText(Register.this,"Select only one, Doctor or Patient ",Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        else if(PatientId.isChecked() && !DoctorId.isChecked())
                                        {
                                            accessType="Patient";
                                        }
                                        else if(DoctorId.isChecked() && !PatientId.isChecked())
                                        {
                                            accessType="Doctor";
                                        }

                                        String ID=accessType.charAt(0)+""+idCount;

                                        Log.i("IDcount",""+ID);
                                        Log.i("IDcount id",""+idCount);



                                        //Toast.makeText(Register.this,"Clicked",Toast.LENGTH_SHORT).show();

                                        // String customToken = FirebaseAuth.getInstance().createCustomToken(uid, additionalClaims);

                                        if(!TextUtils.isEmpty(S_username)|| !TextUtils.isEmpty(S_Email)||!TextUtils.isEmpty(S_Pass)){

                                            reg_user (S_username,S_Email,S_Pass,age,phone,accessType,ID,"bc");
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(Register.this,"You have Already registered with this Birth Certificate ",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        }


            }
        });
    }



    private void reg_user(final String name, final String email, final String password, final String age, final  String phone, final String accessType, final String ID,final String st)
    {


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            User user = new User(
                                    name,
                                    email,
                                    age,
                                    phone,
                                    accessType,
                                    ID
                            );


                            DatabaseReference data=FirebaseDatabase.getInstance().getReference("Users");
                                    data.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                   // progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Register.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                                        DatabaseReference KeyList=FirebaseDatabase.getInstance().getReference("KeyList");
                                        KeyList.child(ID).setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                        if(st.equals("nid"))
                                        {
                                            DatabaseReference doctorList=FirebaseDatabase.getInstance().getReference("NID");
                                            doctorList.child(nid).setValue(name);

                                        } else if(st.equals("bc"))
                                        {
                                            DatabaseReference doctorList=FirebaseDatabase.getInstance().getReference("BC");
                                            doctorList.child(bc).setValue(name);

                                        }


                                        if(accessType.equals("Doctor"))
                                        {
                                            DatabaseReference doctorList=FirebaseDatabase.getInstance().getReference("DoctorList");
                                            doctorList.child(ID).setValue(name);
                                        }

                                        Intent intent = new Intent(Register.this,LogIn.class);
                                        startActivity(intent);

                                    } else {
                                        //display a failure message
                                        Toast.makeText(Register.this, "Failure to Register", Toast.LENGTH_LONG).show();

                                    }
                                }
                            });




                        } else {
                            Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

















//
//        mAuth.signInWithCustomToken(mCustomToken)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.i("SignIn" ,"signInWithCustomToken:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            //updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w("SignIn", "signInWithCustomToken:failure", task.getException());
//                            Toast.makeText(Register.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            //updateUI(null);
//                        }
//                    }
//                });







    }


    private int getID(String TableName)
    {

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference(TableName);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                idCount =(int) dataSnapshot.getChildrenCount()+1;
                Log.i("IDcount fun",""+idCount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return idCount;
    }


    private void register_User(final String s_username, final String s_email, final String s_pass) {
        mAuth.createUserWithEmailAndPassword(s_email, s_pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = current_user.getUid();

                            mFireDb = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                            HashMap<String, String> userMap = new HashMap<>();
                            userMap.put("name", s_username);
                            userMap.put("email",s_email);
                            userMap.put("pass",s_pass);

                            mFireDb.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Intent mainIntent = new Intent(Register.this, MainActivity.class);
                                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(mainIntent);
                                        finish();
                                        Toast.makeText(Register.this,"Success",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user
                        }
                    }
                });
    }
}
