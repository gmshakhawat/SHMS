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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class appointmentRequest extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private View apr;

    private TextView name,date;
    private Button appButton;
    private ListView listView;

    private String mParam1;
    private String mParam2;
    private String drName;
    private Context context;

    private ArrayAdapter<String> nameAdapter;
    private ArrayAdapter<String> dateAdapter;

    private ArrayList<String> nameList=new ArrayList<>();
    private ArrayList<String> dateList=new ArrayList<>();


    private OnFragmentInteractionListener mListener;

    public appointmentRequest() {
        // Required empty public constructor
    }

    public static appointmentRequest newInstance(String param1) {
        appointmentRequest fragment = new appointmentRequest();
        Bundle args = new Bundle();
        args.putString("drname", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            drName = getArguments().getString("drname");
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        apr= inflater.inflate(R.layout.fragment_appointment_request, container, false);

//        name=apr.findViewById(R.id.pName);
//        date=apr.findViewById(R.id.pApDate);
//        appButton=apr.findViewById(R.id.appointmentRequestApprove);
        listView = (ListView) apr.findViewById(R.id.tasklist);
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Request/"+drName);
        dateList.clear();
        nameList.clear();

        context=getContext();

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String key=dataSnapshot.getKey();
                dateList.add(key);
                String value=dataSnapshot.getValue(String.class);
                nameList.add(value);
                loadTaskList();



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







        return apr;
    }




    private void loadTaskList(){
        //ArrayList<String> taskList=dbHelper.getTaskList();


        Log.i("nameListD",""+dateList);


        if(nameAdapter==null){
            nameAdapter=new ArrayAdapter<String>(apr.getContext(),R.layout.appointment_request_view,R.id.pName,nameList);
            listView.setAdapter(nameAdapter);
            Log.i("nameList",""+nameList);
            dateAdapter=new ArrayAdapter<String>(context,R.layout.appointment_request_view,R.id.pName,dateList);

        }
        else{
            nameAdapter.clear();
            nameAdapter.addAll(nameList);
            Log.i("nameListA",""+nameAdapter);
            nameAdapter.notifyDataSetChanged();
        }
    }







    public void DeleteTask(View v) {
        View parent=(View)v.getParent();
        TextView taskview=(TextView)parent.findViewById(R.id.pName);
        String task=String.valueOf(taskview.getText());
        //dbHelper.deleteTask(task);
        loadTaskList();
       // Toast.makeText(MainActivity.this,"Item Deleted",Toast.LENGTH_SHORT).show();
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
