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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class addDegree extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
   private String id,degr,name;

   private EditText degree;
   private Button addDeg;

    private OnFragmentInteractionListener mListener;

    public addDegree() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static addDegree newInstance(String id,String name) {
        addDegree fragment = new addDegree();
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString("id");
            name = getArguments().getString("name");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View deg= inflater.inflate(R.layout.fragment_add_degree, container, false);
        addDeg=deg.findViewById(R.id.addDegree);
        degree=deg.findViewById(R.id.degree);

        addDeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                degr=degree.getText().toString();
                final DatabaseReference degreeRef= FirebaseDatabase.getInstance().getReference("Degree/");



                degreeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.hasChild(id))
                        {
                            Log.i("doctor","id");
                            final DatabaseReference degreeRef2= FirebaseDatabase.getInstance().getReference("Degree/"+id);
                            degreeRef2.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                    String key=dataSnapshot.getKey();
                                    String degreeData;

                                    Log.i("doctor","id2");

                                    if(key.equals("degree"))
                                    {
                                        Log.i("doctor","id3");
                                        degreeData=dataSnapshot.getValue(String.class);

                                        degreeData=degreeData+" , "+degr;

                                        degreeRef2.child("degree").setValue(degreeData);

                                        Toast.makeText(getContext(),"Your Degree has beed Added Successfully !!! ",Toast.LENGTH_SHORT).show();

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

                        }
                        else {

                            Log.i("doctor","el");

                            degreeRef.child(id).child("name").setValue(name);
                            degreeRef.child(id).child("degree").setValue(degr);
                            Toast.makeText(getContext(),"Your Degree has beed Added Successfully !!! ",Toast.LENGTH_SHORT).show();


                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });







            }

        });

        return deg;
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
