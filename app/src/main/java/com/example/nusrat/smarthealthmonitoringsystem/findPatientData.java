package com.example.nusrat.smarthealthmonitoringsystem;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link findPatientData.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link findPatientData#newInstance} factory method to
 * create an instance of this fragment.
 */
public class findPatientData extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String id;
    private String status="d";

    private EditText patientId;
    private String pID,pname,pphone,pemail,page,key,ptype;
    private Button check;
    private ListView date,pl,ph,sugar,stomach;
    private ArrayList<String> dateArray= new ArrayList<>();
    private ArrayList<String> phArray= new ArrayList<>();
    private ArrayList<String> plArray= new ArrayList<>();
    private ArrayList<String> sugarArray= new ArrayList<>();
    private ArrayList<String> stomachArray = new ArrayList<>();


    private View afterCheckView,notFoundView,profileShowing;
    private OnFragmentInteractionListener mListener;

    public findPatientData() {
        // Required empty public constructor
    }



    // TODO: Rename and change types and number of parameters
    public static findPatientData newInstance(String id, String status) {
        findPatientData fragment = new findPatientData();
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("status", status);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString("id");
            status = getArguments().getString("status");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View findPatient= inflater.inflate(R.layout.fragment_find_patient_data, container, false);

        check=findPatient.findViewById(R.id.checkPatientDataButton);
        patientId=findPatient.findViewById(R.id.patientIDforSearch);
        ph=findPatient.findViewById(R.id.presureH);
        pl=findPatient.findViewById(R.id.presureL);
        sugar=findPatient.findViewById(R.id.sugar);
        date=findPatient.findViewById(R.id.date);
        afterCheckView=findPatient.findViewById(R.id.afterCheck);
        afterCheckView.setVisibility(View.INVISIBLE);



        notFoundView=findPatient.findViewById(R.id.inputField);
        profileShowing=findPatient.findViewById(R.id.profileShowingLayout);
        stomach=findPatient.findViewById(R.id.stomach);

        notFoundView.setVisibility(View.VISIBLE);
        profileShowing.setVisibility(View.VISIBLE);




        final ArrayAdapter<String> dateAdapter =new  ArrayAdapter<String>(findPatient.getContext(),R.layout.support_simple_spinner_dropdown_item,dateArray);
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        date.setAdapter(dateAdapter);

        final ArrayAdapter<String> phAdapter =new  ArrayAdapter<String>(findPatient.getContext(),R.layout.support_simple_spinner_dropdown_item,phArray);
        phAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ph.setAdapter(phAdapter);

        final ArrayAdapter<String> plAdapter =new  ArrayAdapter<String>(findPatient.getContext(),R.layout.support_simple_spinner_dropdown_item,plArray);
        plAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pl.setAdapter(plAdapter);

        final ArrayAdapter<String> sugarAdapter =new  ArrayAdapter<String>(findPatient.getContext(),R.layout.support_simple_spinner_dropdown_item,sugarArray);
        sugarAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sugar.setAdapter(sugarAdapter);

        final ArrayAdapter<String> stomachAdapter =new ArrayAdapter<>(findPatient.getContext(),R.layout.support_simple_spinner_dropdown_item,stomachArray);
        stomachAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stomach.setAdapter(stomachAdapter);




        dateArray.clear();
        phArray.clear();
        plArray.clear();
        sugarArray.clear();
        stomachArray.clear();


        if(status.equals("p"))
        {
            dateArray.clear();
            phArray.clear();
            plArray.clear();
            sugarArray.clear();
            notFoundView.setVisibility(View.GONE);
            profileShowing.setVisibility(View.GONE);



            pID=id;

            DatabaseReference findKey=FirebaseDatabase.getInstance().getReference("KeyList");
            findKey.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    String value=dataSnapshot.getKey();
                    if(value.equals(pID))
                    {
                        afterCheckView.setVisibility(View.VISIBLE);
                        key=dataSnapshot.getValue(String.class);
                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users/"+key);
                        Log.i("keyforfind :",""+key+"\n key2");

                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                User userProfileData = dataSnapshot.getValue(User.class);
                                pname=userProfileData.getName();
                                page=userProfileData.getAge();
                                pphone=userProfileData.getPhone();
                                pemail=userProfileData.getEmail();
                                ptype=userProfileData.getAccessType();
                                pID=userProfileData.getId();

                                Log.i("keyforfind Name :",""+pname+"\n key2");
                                userProfile userProfile= com.example.nusrat.smarthealthmonitoringsystem.userProfile.newInstance(pname,page,pphone,pemail,ptype,pID,"P");
                                getFragmentManager().beginTransaction().replace(R.id.showProfile,userProfile).commit();


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    else {
//                            afterCheckView.setVisibility(View.INVISIBLE);
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

                }
            });






            DatabaseReference userData= FirebaseDatabase.getInstance().getReference("PatientData/"+pID);

            userData.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot postSnapshot:dataSnapshot.getChildren()) {
                        ulpoadUserData ulpoadUserData = postSnapshot.getValue(ulpoadUserData.class);
                        afterCheckView.setVisibility(View.VISIBLE);


                        String bpH,bpL,bS,bDate,stomach;
                        bpH=ulpoadUserData.getHighPresure();
                        bDate=ulpoadUserData.getDate();
                        bpL=ulpoadUserData.getLowPresure();
                        bS=ulpoadUserData.getSugar();
                        stomach=ulpoadUserData.getStomach();

                        if(bpH!=null)
                        {
                            dateAdapter.notifyDataSetChanged();
                            phArray.add(bpH);
                        }

                        if(bpL!=null)
                        {
                            plArray.add(bpL);
                            plAdapter.notifyDataSetChanged();
                        }

                        if(bS!=null)
                        {
                            sugarArray.add(bS);
                            sugarAdapter.notifyDataSetChanged();
                        }
                        if(bDate!=null)
                        {

                            dateArray.add(bDate);
                            dateAdapter.notifyDataSetChanged();
                        }
                        if(stomach!=null)
                        {
                            stomachArray.add(stomach);
                            stomachAdapter.notifyDataSetChanged();
                        }


//                            bDate=ulpoadUserData.getDate();
//                            dateArray.add(bDate);
//                            dateAdapter.notifyDataSetChanged();
//                            phArray.add(bpH);
//                            phAdapter.notifyDataSetChanged();
//                            plArray.add(bpL);
//                            plAdapter.notifyDataSetChanged();
//                            sugarArray.add(bS);
//                            sugarAdapter.notifyDataSetChanged();

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });





            dateArray.clear();
            phArray.clear();
            plArray.clear();
            sugarArray.clear();
            stomachArray.clear();

        }


        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateArray.clear();
                phArray.clear();
                plArray.clear();
                sugarArray.clear();
                stomachArray.clear();
                notFoundView.setVisibility(View.VISIBLE);
                profileShowing.setVisibility(View.VISIBLE);


                pID=patientId.getText().toString().toUpperCase();

                DatabaseReference findKey=FirebaseDatabase.getInstance().getReference("KeyList");
                findKey.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        String value=dataSnapshot.getKey();
                        if(value.equals(pID))
                        {
                            afterCheckView.setVisibility(View.VISIBLE);
                            key=dataSnapshot.getValue(String.class);
                            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users/"+key);
                            Log.i("keyforfind :",""+key+"\n key2");

                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    User userProfileData = dataSnapshot.getValue(User.class);
                                    pname=userProfileData.getName();
                                    page=userProfileData.getAge();
                                    pphone=userProfileData.getPhone();
                                    pemail=userProfileData.getEmail();
                                    ptype=userProfileData.getAccessType();
                                    pID=userProfileData.getId();

                                    Log.i("keyforfind Name :",""+pname+"\n key2");
                                    userProfile userProfile= com.example.nusrat.smarthealthmonitoringsystem.userProfile.newInstance(pname,page,pphone,pemail,ptype,pID,"P");
                                    getFragmentManager().beginTransaction().replace(R.id.showProfile,userProfile).commit();


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        else {
//                            afterCheckView.setVisibility(View.INVISIBLE);
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

                    }
                });






                DatabaseReference userData= FirebaseDatabase.getInstance().getReference("PatientData/"+pID);

                userData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot postSnapshot:dataSnapshot.getChildren()) {
                            ulpoadUserData ulpoadUserData = postSnapshot.getValue(ulpoadUserData.class);
                            afterCheckView.setVisibility(View.VISIBLE);

                            String bpH,bpL,bS,bDate,stomach;
                            bpH=ulpoadUserData.getHighPresure();
                            stomach=ulpoadUserData.getStomach();
                            bDate=ulpoadUserData.getDate();
                            bpL=ulpoadUserData.getLowPresure();
                            bS=ulpoadUserData.getSugar();
                            if(bpH!=null)
                            {
                                dateAdapter.notifyDataSetChanged();
                                phArray.add(bpH);
                            }

                            if(bpL!=null)
                            {
                                plArray.add(bpL);
                                plAdapter.notifyDataSetChanged();
                            }

                            if(bS!=null)
                            {
                                sugarArray.add(bS);
                                sugarAdapter.notifyDataSetChanged();
                            }
                            if(bDate!=null)
                            {

                                dateArray.add(bDate);
                                dateAdapter.notifyDataSetChanged();
                            }
                            if(stomach!=null)
                            {
                                stomachArray.add(stomach);
                                stomachAdapter.notifyDataSetChanged();
                            }


//                            bDate=ulpoadUserData.getDate();
//                            dateArray.add(bDate);
//                            dateAdapter.notifyDataSetChanged();
//                            phArray.add(bpH);
//                            phAdapter.notifyDataSetChanged();
//                            plArray.add(bpL);
//                            plAdapter.notifyDataSetChanged();
//                            sugarArray.add(bS);
//                            sugarAdapter.notifyDataSetChanged();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





                dateArray.clear();
                phArray.clear();
                plArray.clear();
                sugarArray.clear();
                stomachArray.clear();


            }
        });








        return findPatient;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }




    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
