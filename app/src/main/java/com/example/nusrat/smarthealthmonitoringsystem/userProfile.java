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
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link userProfile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link userProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class userProfile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String name,age,phone,email,type,id,st;
    private TextView pname,page,pemail,pphone,ptype,pid,degree;

    private View degView;



    private OnFragmentInteractionListener mListener;

    public userProfile() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static userProfile newInstance(String name, String age,String phone,String email,String type,String id,String st) {
        userProfile fragment = new userProfile();
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putString("age", age);
        args.putString("phone",phone);
        args.putString("email",email);
        args.putString("type",type);
        args.putString("id",id);
        args.putString("st",st);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString("name");
            age = getArguments().getString("age");
            phone = getArguments().getString("phone");
            email = getArguments().getString("email");
            type=getArguments().getString("type");
            id=getArguments().getString("id");
            st=getArguments().getString("st");
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View profileView= inflater.inflate(R.layout.fragment_user_profile, container, false);



        pname=profileView.findViewById(R.id.profileName);

        page=profileView.findViewById(R.id.profileAge);
        pphone=profileView.findViewById(R.id.profilePhone);
        pemail=profileView.findViewById(R.id.profileEmail);

        degView=profileView.findViewById(R.id.degreeLayout);
        degree=profileView.findViewById(R.id.profileDegree);

        if(st.equals("D"))
        {
            degView.setVisibility(View.VISIBLE);
            DatabaseReference degRef= FirebaseDatabase.getInstance().getReference("Degree/"+id);
            degRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    String key=dataSnapshot.getKey();
                    if(key.equals("degree"))
                    {
                        String value=dataSnapshot.getValue(String.class);

                        degree.setText(value);
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


        ptype=profileView.findViewById(R.id.profileType);
        pid=profileView.findViewById(R.id.profileID);

        pname.setText(": "+name);
        page.setText(": "+age);
        pphone.setText(": "+phone);
        pemail.setText(": "+email);
        ptype.setText("("+type+")");
        pid.setText("ID - "+id);

        Log.i("pname",""+name);

        return profileView;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
