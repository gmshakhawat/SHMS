package com.example.nusrat.smarthealthmonitoringsystem;

import android.content.Context;
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
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewAppointment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewAppointment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewAppointment extends Fragment {

    private String id,name ;
    private ListView listid,listdate;

    private ArrayList<String> idArray=new ArrayList<>();
    private ArrayList<String> dateArray= new ArrayList<>();


    private OnFragmentInteractionListener mListener;

    public ViewAppointment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ViewAppointment newInstance(String id,String name) {
        ViewAppointment fragment = new ViewAppointment();
        Bundle args = new Bundle();
        args.putString("name",name);
        args.putString("id", id);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString("id");
            name=getArguments().getString("name");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewAppointment =inflater.inflate(R.layout.fragment_view_appointment, container, false);


        listdate=viewAppointment.findViewById(R.id.date);
        listid=viewAppointment.findViewById(R.id.ID);

        final ArrayAdapter<String> idAdapter =new  ArrayAdapter<String>(viewAppointment.getContext(),R.layout.support_simple_spinner_dropdown_item,idArray);
        idAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listid.setAdapter(idAdapter);

        final ArrayAdapter<String> dateAdapter =new  ArrayAdapter<String>(viewAppointment.getContext(),R.layout.support_simple_spinner_dropdown_item,dateArray);
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listdate.setAdapter(dateAdapter);
        String TB="";

        if(id.startsWith("P") || id.startsWith("p"))
        {
            TB=""+id;
        }
        else if(id.startsWith("D")|| id.startsWith("d"))
        {
            TB=""+name;
        }

        Log.i("TB",""+TB);

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Appointments/"+TB);
        Log.i("TB name","Appointments/"+TB);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(String.class);
                dateArray.add(value);
                dateAdapter.notifyDataSetChanged();

                String key=dataSnapshot.getKey();
                idArray.add(key);
                idAdapter.notifyDataSetChanged();

                Log.i("TB V",""+value);

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












        return viewAppointment;
    }






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
