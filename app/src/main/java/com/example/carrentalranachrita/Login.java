package com.example.carrentalranachrita;

import android.app.Activity;
import android.content.Intent;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carrentalranachrita.Entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import org.jetbrains.annotations.NotNull;

import static androidx.navigation.Navigation.findNavController;

public class Login extends Fragment {



    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
// ...

    public Login() {
        // Required empty public constructor
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
//
    // TODO: Rename and change types and number of parameters
    public static Login newInstance() {
        Login fragment = new Login();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener(){


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {

                        return true;
                    }
                }
                return false;
            }
        });

        mAuth = FirebaseAuth.getInstance();

        EditText userEdit = view.findViewById(R.id.txtUser);
        EditText passwordEdit = view.findViewById(R.id.txtUserPassword);
        Button login = view.findViewById(R.id.btnLogin);
        login.setOnClickListener(v -> {
            String user = userEdit.getText().toString();
            String password = passwordEdit.getText().toString();
            if(user.isEmpty()){
                userEdit.setError("Please Enter Email.");
                Toast.makeText(view.getContext(), "Please Enter Email", Toast.LENGTH_LONG ).show();
                return;
            }
            if(password.isEmpty()){
                passwordEdit.setError("Please Enter Password.");
                Toast.makeText(view.getContext(), "Please Enter Password", Toast.LENGTH_LONG ).show();
                return;
            }
            mAuth.signInWithEmailAndPassword(user, password)
                    .addOnCompleteListener((Activity) view.getContext(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
//                                findNavController(view).navigate(R.id.carList);
                                //to decide what is the home view depending of the rol
//                                 findNavController(view).navigate(R.id.hostProfile);
//                                 findNavController(view).navigate(R.id.hostAddCar);
                                //***findNavController(view).navigate(R.id.carList);
//                                 findNavController(view).navigate(R.id.hostProfile);
                                // findNavController(view).navigate(R.id.showListOfCarsAddedByHost);
                                // findNavController(view).navigate(R.id.carList);
                                // findNavController(view).navigate(R.id.confirmBookingForCustomer);

                                DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
                                Query query =  mDatabaseReference.child("user").orderByChild("email").equalTo(mAuth.getCurrentUser().getEmail());

//                                Snackbar.make(view, "hello"+mAuth.getCurrentUser().getEmail(), Snackbar.LENGTH_LONG).show();
                                query.addValueEventListener(new ValueEventListener(){

                                                                @Override
                                                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                                                    for(DataSnapshot childSnapshot: snapshot.getChildren()){
                                                                        //Toast.makeText(getContext(), "inside", Toast.LENGTH_SHORT).show();
                                                                        User user = childSnapshot.getValue(User.class);
                                                                        if(user.getRol().equalsIgnoreCase("Host")){
                                                                            Toast.makeText(getContext(), "Welcome", Toast.LENGTH_SHORT).show();
                                                                            Intent intent = new Intent(getActivity(),Host_Activity.class);
                                                                            startActivity(intent);
                                                                        }
                                                                        else{
                                                                            Toast.makeText(getContext(), "Welcome", Toast.LENGTH_SHORT).show();
                                                                            Intent intent = new Intent(getActivity(),Customer_Activity.class);
                                                                            startActivity(intent);
                                                                    }
                                                                }}

                                                                @Override
                                                                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                                                }
                                                            });


                                Snackbar.make(view, "Welcome", Snackbar.LENGTH_LONG).show();
                            }else {
                                Snackbar.make(view, "Email or Password is not correct.", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });

        });

        Button register = view.findViewById(R.id.btnRegister);
      
        register.setOnClickListener(v -> {


            findNavController(view).navigate(R.id.registrationFragment);

        });
        return view;
    }

}