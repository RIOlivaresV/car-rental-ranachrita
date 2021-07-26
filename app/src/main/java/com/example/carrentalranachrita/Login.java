package com.example.carrentalranachrita;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.fragment.*;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

import static androidx.navigation.Navigation.findNavController;

public class Login extends Fragment {

    private FirebaseAuth mAuth;
    public Login() {
        // Required empty public constructor
    }

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
        mAuth = FirebaseAuth.getInstance();

        EditText userEdit = view.findViewById(R.id.txtUser);
        EditText passwordEdit = view.findViewById(R.id.txtUserPassword);
        Button login = view.findViewById(R.id.btnLogin);
        login.setOnClickListener(v -> {
            String user = userEdit.getText().toString();
            String password = passwordEdit.getText().toString();
            mAuth.signInWithEmailAndPassword(user, password)
                    .addOnCompleteListener((Activity) view.getContext(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                findNavController(view).navigate(R.id.showListOfCarsAddedByHost);
                                Snackbar.make(view, "Welcome", Snackbar.LENGTH_LONG).show();
                            }else {
                                Snackbar.make(view, "Something wrong is happening, please try again", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });

        });

        Button register = view.findViewById(R.id.btnRegister);
      
        return view;
    }

}