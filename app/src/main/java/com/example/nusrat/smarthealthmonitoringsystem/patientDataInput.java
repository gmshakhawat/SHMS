package com.example.nusrat.smarthealthmonitoringsystem;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class patientDataInput extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    private String key,id;
    private int bpHigh,bpLow,bsugar;
    private String bpHighs,bpLows,bsugars;

    private EditText pHigh,pLow,sugar;
    private Button submit;

    private int bpL,bpH,bS,bpLforLow,bpHforLow,bSforLow;
    private CheckBox isEmptyCheckbox;
    private String stomach;

    private OnFragmentInteractionListener mListener;

    public patientDataInput() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static patientDataInput newInstance(String key,String id) {
        patientDataInput fragment = new patientDataInput();
        Bundle args = new Bundle();
        args.putString("key", key);
        args.putString("id",id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            key = getArguments().getString("key");
            id=getArguments().getString("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View dataInput= inflater.inflate(R.layout.fragment_patient_data_input, container, false);

       pHigh=dataInput.findViewById(R.id.bpUpperId);
       pLow=dataInput.findViewById(R.id.bpLowerID);
       sugar=dataInput.findViewById(R.id.sugerID);
       submit=dataInput.findViewById(R.id.submitId);
       isEmptyCheckbox=dataInput.findViewById(R.id.isEmptyStomach);


        bpL=90; // for Low 70
        bpH=130;  //For Low 90
        bS=9;  //For low 4


        bpHforLow=90;
        bpLforLow=70;
        bSforLow=4;


            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    bpHighs=pHigh.getText().toString();
                    bpLows=pLow.getText().toString();
                    bsugars=sugar.getText().toString();
                    Log.i("bsugarL",""+bsugars.length() +" / "+bpHighs.length()+" / "+bpLows.length());
                    if(isEmptyCheckbox.isChecked()){
                        stomach="Yes";
                    }
                    else {
                        stomach="No";
                    }

                    if(bpHighs.length()!=0)
                    {
                        bpHigh=Integer.parseInt(bpHighs);
                    }
//                    else {
//                        bpHigh=9999;
//                    }


                    if(bpLows.length() !=0)
                    {
                        bpLow=Integer.parseInt(bpLows);
                    }
//                    else{
//                        bpLow=9999;
//                    }



                    if(bsugars.length() !=0)
                    {
                        bsugar=Integer.parseInt(bsugars);
                    }
//                    else{
//
//                        bsugar=9999;
//                    }






                    Log.i("bsugars",""+bsugars +" / "+bpHighs+" / "+bpLows);
                    Log.i("bsugar",""+bsugar +" / "+bpHigh+" / "+bpLow);

                    String mesg="";

                    if(((bpHigh<0)||(bpLow<0)||(bpHigh-bpLow)<0) || (bsugar<0) )
                    {
                        Toast.makeText(getContext(),"Please input Valid data !!!",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(bpHighs.length()!=0 && bpLows.length()==0)
                    {
                        Toast.makeText(getContext(),"Please input Lower BP  !!!",Toast.LENGTH_SHORT).show();
                        return;
                    }

                   if(bpLows.length()!=0 && bpHighs.length()==0)
                    {
                        Toast.makeText(getContext(),"Please input Upper BP  !!!",Toast.LENGTH_SHORT).show();
                        return;
                    }


                    if(bpHighs.length()==0 && bpLows.length()==0 && bsugar<=bSforLow)
                    {

                        //pH,sH-Em 1

                        Log.i("check1",""+bsugar +" / "+bpHigh+" / "+bpLow);

                        Intent intent= new Intent(getActivity(),Emergency.class);
                        intent.putExtra("pH",""+bpHighs);
                        intent.putExtra("pL",""+bpLows);
                        intent.putExtra("id",""+id);
                        intent.putExtra("sugar",""+bsugars);
                        intent.putExtra("date",""+getCurrentDate());
                        intent.putExtra("stomach",""+stomach); //
                        intent.putExtra("msg","Your Diabetes is Low ");

                        startActivity(intent);

                    }
                    else if(bpHigh<=bpHforLow && bpLow<=bpLforLow && bsugars.length()==0)
                    {

                        //pH,sH-Em2

                        Intent intent= new Intent(getActivity(),Emergency.class);
                        intent.putExtra("pH",""+bpHighs);
                        intent.putExtra("pL",""+bpLows);
                        intent.putExtra("id",""+id);
                        intent.putExtra("sugar",""+bsugars);
                        intent.putExtra("date",""+getCurrentDate());
                        intent.putExtra("stomach",""+stomach);
                        intent.putExtra("msg","Your Presure is Low ");

                        startActivity(intent);

                    }
                    else if(bpHigh<=bpHforLow && bpLow<=bpLforLow && bsugar<=bSforLow)
                    {

                        //pH,sH-Em3

                        Intent intent= new Intent(getActivity(),Emergency.class);
                        intent.putExtra("pH",""+bpHighs);
                        intent.putExtra("pL",""+bpLows);
                        intent.putExtra("id",""+id);
                        intent.putExtra("sugar",""+bsugars);
                        intent.putExtra("date",""+getCurrentDate());
                        intent.putExtra("stomach",""+stomach);
                        intent.putExtra("msg","Your Presure and Diabetes are Low ");

                        startActivity(intent);

                    }
                    else if(bpHigh<=bpHforLow && bpLow<=bpLforLow && bsugar>bSforLow && bsugar<=bS)
                    {

                        //pH,sH-Em4

                        Intent intent= new Intent(getActivity(),Emergency.class);
                        intent.putExtra("pH",""+bpHighs);
                        intent.putExtra("pL",""+bpLows);
                        intent.putExtra("id",""+id);
                        intent.putExtra("sugar",""+bsugars);
                        intent.putExtra("date",""+getCurrentDate());
                        intent.putExtra("stomach",""+stomach);
                        intent.putExtra("msg","Your Presure is Low but Diabetes is Normal ");

                        startActivity(intent);

                    }
                    else if(bpHigh<=bpHforLow && bpLow<=bpLforLow  && bsugar>bS)
                    {

                        //pH,sH-Em5

                        Intent intent= new Intent(getActivity(),Emergency.class);
                        intent.putExtra("pH",""+bpHighs);
                        intent.putExtra("pL",""+bpLows);
                        intent.putExtra("id",""+id);
                        intent.putExtra("sugar",""+bsugars);
                        intent.putExtra("date",""+getCurrentDate());
                        intent.putExtra("stomach",""+stomach);
                        intent.putExtra("msg","Your Presure is Low but Diabetes is High ");

                        startActivity(intent);

                    }

                    else if(  bpHigh>bpHforLow && bpLow>bpLforLow  && bpHigh>=bpH && bpLow>=bpL && bsugars.length()==0)
                    {

                        //pH,sH-Em6

                        Intent intent= new Intent(getActivity(),Emergency.class);
                        intent.putExtra("pH",""+bpHighs);
                        intent.putExtra("pL",""+bpLows);
                        intent.putExtra("id",""+id);
                        intent.putExtra("sugar",""+bsugars);
                        intent.putExtra("date",""+getCurrentDate());
                        intent.putExtra("stomach",""+stomach);
                        intent.putExtra("msg","Your Presure is High ");

                        startActivity(intent);

                    }
                    else if(bpHigh>bpHforLow && bpLow>bpLforLow  && bpHigh<bpH && bpLow<bpL && bsugars.length()==0)
                    {
                        //pH,sH-Em7

                        Intent intent= new Intent(getActivity(),Emergency.class);
                        intent.putExtra("pH",""+bpHighs);
                        intent.putExtra("pL",""+bpLows);
                        intent.putExtra("id",""+id);
                        intent.putExtra("sugar",""+bsugars);
                        intent.putExtra("date",""+getCurrentDate());
                        intent.putExtra("stomach",""+stomach);
                        intent.putExtra("msg","Your Presure is Normal ");

                        startActivity(intent);

                    }


                    else if(bpHigh>bpHforLow && bpLow>bpLforLow  && bsugar>bSforLow &&  bpHigh<bpH && bpLow<bpL && bsugar<=bS  && bpHighs.length()>0 && bpLows.length()>0 && bsugars.length()>0)
                    {
                        //pL.sL-Normal8

                        Intent intent= new Intent(getActivity(),Normal.class);
                        intent.putExtra("pH",""+bpHighs);
                        intent.putExtra("id",""+id);
                        intent.putExtra("pL",""+bpLows);
                        intent.putExtra("sugar",""+bsugars);
                        intent.putExtra("date",""+getCurrentDate());
                        intent.putExtra("stomach",""+stomach);
                        intent.putExtra("msg","Your Presure is Normal and Diabetes is also Normal");

                        startActivity(intent);



                    }
                    else if(bpHigh>bpHforLow && bpLow>bpLforLow  && bsugar<=bSforLow &&  bpHigh<bpH && bpLow<bpL && bpHighs.length()>0 && bpLows.length()>0 && bsugars.length()>0)
                    {
                        //pL.sL-Normal8

                        Intent intent= new Intent(getActivity(),Emergency.class);
                        intent.putExtra("pH",""+bpHighs);
                        intent.putExtra("id",""+id);
                        intent.putExtra("pL",""+bpLows);
                        intent.putExtra("sugar",""+bsugars);
                        intent.putExtra("date",""+getCurrentDate());
                        intent.putExtra("stomach",""+stomach);
                        intent.putExtra("msg","Your Presure is Normal But Diabetes is Low");

                        startActivity(intent);



                    }
                    else if(bpHigh>bpHforLow && bpLow>bpLforLow   && bsugar>bSforLow && bpHigh>=bpH && bpLow>=bpL && bsugar<bS && bpHighs.length()>0 && bpLows.length()>0 && bsugars.length()>0)
                    {
                        //pH,sL-Em9

                        Intent intent= new Intent(getActivity(),Emergency.class);
                        intent.putExtra("pH",""+bpHighs);
                        intent.putExtra("pL",""+bpLows);
                        intent.putExtra("id",""+id);
                        intent.putExtra("sugar",""+bsugars);
                        intent.putExtra("stomach",""+stomach);
                        intent.putExtra("date",""+getCurrentDate());
                        intent.putExtra("msg","Your Presure is High but Diabetes is Normal");

                        startActivity(intent);
                    }

                    else if(bpHigh>bpHforLow && bpLow>bpLforLow  && bsugar>bSforLow && bpHigh>=bpH && bpLow>=bpL && bsugar>=bS && bpHighs.length()>0 && bpLows.length()>0 && bsugars.length()>0)
                    {
                        //pH,sH-Em10

                        Intent intent= new Intent(getActivity(),Emergency.class);
                        intent.putExtra("pH",""+bpHighs);
                        intent.putExtra("pL",""+bpLows);
                        intent.putExtra("id",""+id);
                        intent.putExtra("sugar",""+bsugars);
                        intent.putExtra("stomach",""+stomach);
                        intent.putExtra("date",""+getCurrentDate());
                        intent.putExtra("msg","Your Presure is High and Diabetes is also High");

                        startActivity(intent);

                    }
                    else if(bpHigh>bpHforLow && bpLow>bpLforLow  && bsugar<=bSforLow && bpHigh>=bpH && bpLow>=bpL && bpHighs.length()>0 && bpLows.length()>0 && bsugars.length()>0)
                    {
                        //pH,sH-Em10

                        Intent intent= new Intent(getActivity(),Emergency.class);
                        intent.putExtra("pH",""+bpHighs);
                        intent.putExtra("pL",""+bpLows);
                        intent.putExtra("id",""+id);
                        intent.putExtra("sugar",""+bsugars);
                        intent.putExtra("stomach",""+stomach);
                        intent.putExtra("date",""+getCurrentDate());
                        intent.putExtra("msg","Your Presure is High and Diabetes is also Low");

                        startActivity(intent);

                    }

                    else  if(bpHigh>bpHforLow && bpLow>bpLforLow  && bsugar>bSforLow && bpHigh<bpH && bpLow<bpL && bsugar>bS && bpHighs.length()>0 && bpLows.length()>0 && bsugars.length()>0)
                    {
                        //pL,sH-Em11

                        Intent intent= new Intent(getActivity(),Emergency.class);
                        intent.putExtra("pH",""+bpHighs);
                        intent.putExtra("pL",""+bpLows);
                        intent.putExtra("id",""+id);
                        intent.putExtra("stomach",""+stomach);
                        intent.putExtra("sugar",""+bsugars);
                        intent.putExtra("date",""+getCurrentDate());
                        intent.putExtra("msg","Your Presure is Normal But Diabetes is High");

                        startActivity(intent);
                    }
                    else if( bsugar>bSforLow && bsugar<bS && bpHighs.length()==0 && bpLows.length()==0 && bsugars.length()>0)
                    {
                        //pH,sH-Em12

                        Intent intent= new Intent(getActivity(),Emergency.class);
                        intent.putExtra("pH",""+bpHighs);
                        intent.putExtra("pL",""+bpLows);
                        intent.putExtra("id",""+id);
                        intent.putExtra("stomach",""+stomach);
                        intent.putExtra("sugar",""+bsugars);
                        intent.putExtra("date",""+getCurrentDate());
                        intent.putExtra("msg","Your Diabetes is Normal ");

                        startActivity(intent);

                    }
                    else if( bsugar>bSforLow && bsugar>=bS && bpHighs.length()==0 && bpLows.length()==0 && bsugars.length()>0)
                    {
                        //pH,sH-Em13

                        Intent intent= new Intent(getActivity(),Emergency.class);
                        intent.putExtra("pH",""+bpHighs);
                        intent.putExtra("pL",""+bpLows);
                        intent.putExtra("id",""+id);
                        intent.putExtra("sugar",""+bsugars);
                        intent.putExtra("stomach",""+stomach);
                        intent.putExtra("date",""+getCurrentDate());
                        intent.putExtra("msg","Your Diabetes is High ");

                        startActivity(intent);

                    }








                }
            });






       return dataInput;
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



    public String getCurrentDate()
    {
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
        String today;
        today=dateFormat.format(calendar.getTime());
        return today;

    }



}
