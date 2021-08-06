package com.example.carrentalranachrita;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link hostProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class hostProfile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    TextView txtHostFirstName,txtHostSurname,txtHostPhoneNumber,txtHostEmail,txtHostPassword;
    public hostProfile() {
        // Required empty public constructor
        databaseReference = FirebaseDatabase.getInstance().getReference();


    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment hostProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static hostProfile newInstance(String param1, String param2) {
        hostProfile fragment = new hostProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_host_profile, container, false);

        // Inflate the layout for this fragment

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("user");

        //init views

        txtHostFirstName = view.findViewById(R.id.txtHostFirstName);
        txtHostSurname = view.findViewById(R.id.txtHostSurname);
        txtHostEmail = view.findViewById(R.id.txtHostEmail);
        txtHostPhoneNumber = view.findViewById(R.id.txtHostPhoneNumber);
        txtHostPassword = view.findViewById(R.id.txtHostPassword);

        firebaseAuth = FirebaseAuth.getInstance();

        DatabaseReference msDatabaseReference = FirebaseDatabase.getInstance().getReference();DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        Query query =  msDatabaseReference.child("user").orderByChild("email").equalTo(firebaseAuth.getCurrentUser().getEmail());

        // Query query = databaseReference.orderByChild("email").equalTo(currentFirebaseUser.getEmail());


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull @NonNull DataSnapshot snapshot) {

                for(DataSnapshot childSnapshot: snapshot.getChildren())
                {
                    // get data

                    String name =""+ childSnapshot.child("name").getValue();
                    String lastName =""+ childSnapshot.child("lastName").getValue();
                    String email =""+ childSnapshot.child("email").getValue();
                    String phoneNumber =""+ childSnapshot.child("phoneNumber").getValue();
                    String password =""+ childSnapshot.child("password").getValue();

                    // set data

                    txtHostFirstName.setText(name);
                    txtHostSurname.setText(lastName);
                    txtHostEmail.setText(email);
                    txtHostPhoneNumber.setText(phoneNumber);
                    txtHostPassword.setText("******");




                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }


}