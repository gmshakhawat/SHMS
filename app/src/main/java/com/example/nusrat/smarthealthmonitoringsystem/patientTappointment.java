package com.example.nusrat.smarthealthmonitoringsystem;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link patientTappointment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link patientTappointment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class patientTappointment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String id,st;

    private EditText doctorId;
    private DatePicker datePicker;
    private Spinner doctorListSpinner;
    private Button appointmentButton;
    private TextView date,drNmaeView;
    private ArrayList<String> doctorList=new ArrayList<>();


    private String dID,ddate,pID,dName;



    private OnFragmentInteractionListener mListener;

    public patientTappointment() {
        // Required empty public constructor
    }


    public static patientTappointment newInstance(String id, String st) {
        patientTappointment fragment = new patientTappointment();
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("st", st);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString("id");
            st = getArguments().getString("st");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View appointment= inflater.inflate(R.layout.fragment_patient_tappointment, container, false);


        doctorListSpinner=appointment.findViewById(R.id.doctorListSpinner);

        appointmentButton=appointment.findViewById(R.id.patientAppointmentButtonId);
        drNmaeView=appointment.findViewById(R.id.doctorListfromView);

        if(st.length()>2)
        {
            drNmaeView.setVisibility(View.VISIBLE);
            drNmaeView.setText(st);
            doctorListSpinner.setVisibility(View.GONE);
            dName=drNmaeView.getText().toString();

        }
        else {
            drNmaeView.setVisibility(View.GONE);
            doctorListSpinner.setVisibility(View.VISIBLE);
        }

        final ArrayAdapter<String> doctorAdapter =new  ArrayAdapter<String>(appointment.getContext(),R.layout.support_simple_spinner_dropdown_item,doctorList);
        doctorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doctorListSpinner.setAdapter(doctorAdapter);

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("DoctorList");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(String.class);
                doctorList.add(value);
                doctorAdapter.notifyDataSetChanged();

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




        final DatePickerDialog[] datePickerDialog = new DatePickerDialog[1];

        date = (TextView) appointment.findViewById(R.id.date);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog

                datePickerDialog[0] = new DatePickerDialog(appointment.getContext(),new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(year + "-"+ (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog[0].show();

            }
        });









        appointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference appointmentDatabase =FirebaseDatabase.getInstance().getReference("Request");




                ddate=date.getText().toString();
                pID=id;


                if(st.equals("PP"))
                {
                    dName=doctorListSpinner.getSelectedItem().toString();
                }



                if(!ddate.equals(""))
                {
                    Log.i("checkApp","did"+ddate+"\n dNmae "+dName);
                        if(!dName.equals("Select Doctor"))
                        {
                            appointmentDatabase.child(pID).child(dName).setValue(ddate);
                            appointmentDatabase.child(dName).child(pID).setValue(ddate);
                            Toast.makeText(appointment.getContext(),"Appointment has been requested Successfully !!!",Toast.LENGTH_LONG).show();
                        }
                        else if( dName.equals("Select Doctor"))
                        {
                            Toast.makeText(appointment.getContext(),"Please Select a Doctor",Toast.LENGTH_LONG).show();
                            return;
                        }

                }
                else
                {
                    Toast.makeText(appointment.getContext(),"Please Select Appointment Date",Toast.LENGTH_LONG).show();
                }


            }
        });








//        date = (EditText) appointment.findViewById(R.id.datePicker);
//
//        date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//
//                        int s = month + 1;
//                        String a = dayOfMonth + "/" + s + "/" + year;
//                        date.setText(a);
//                        Log.i("Time",""+a);
//                    }
//                };
//
////                Time date;
////                date = new Time();
////                DatePickerDialog d = new DatePickerDialog(patientTappointment.this, dpd, date.year, date.month, date.monthDay);
////                d.show();
//
//
//            }
//
//
//        });






        return appointment;
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
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-YYYY");
        String today;
        today=dateFormat.format(calendar.getTime());
        return today;

    }
}
