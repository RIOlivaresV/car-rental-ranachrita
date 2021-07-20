package com.example.carrentalranachrita;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.carrentalranachrita.Daos.UserDao;
import com.example.carrentalranachrita.Entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static androidx.navigation.Navigation.findNavController;

public class RegistrationFragment extends Fragment {


    private FirebaseAuth mAuth;
    private ProgressBar progress;
    public RegistrationFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();
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
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        mAuth = FirebaseAuth.getInstance();

        progress = (ProgressBar) view.findViewById(R.id.progressBar);
        EditText nameEdit = (EditText) view.findViewById(R.id.txtName);
        EditText lastNameEdit = (EditText) view.findViewById(R.id.txtLastName);
        EditText emailEdit = (EditText) view.findViewById(R.id.txtEmail);
        EditText phoneNumberEdit = (EditText) view.findViewById(R.id.txtPhoneNumber);
        EditText passwordEdit = (EditText) view.findViewById(R.id.txtUserPassword);
        EditText confirmPasswordEdit = (EditText) view.findViewById(R.id.txtConfirmPassword);
        Button signUp = (Button) view.findViewById(R.id.btnSignUp);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioButtonRol);


        signUp.setOnClickListener(v -> {
           try {
               progress.setVisibility(View.VISIBLE);
               int radioButtomId = radioGroup.getCheckedRadioButtonId();
               View radiobuttom = radioGroup.findViewById(radioButtomId);
               int idx = radioGroup.indexOfChild(radiobuttom);
               RadioButton r = (RadioButton) radioGroup.getChildAt(idx);
               String name = nameEdit.getText().toString();
               String lastName = lastNameEdit.getText().toString();
               String email = emailEdit.getText().toString();
               String phoneNummber = phoneNumberEdit.getText().toString();
               String password = passwordEdit.getText().toString();
               String confirmPassword = confirmPasswordEdit.getText().toString();
               String rol = r.getText().toString();


               if (name.isEmpty()){
                   nameEdit.setError("Name is required");
                   return;
               }

               if (lastName.isEmpty()){
                   lastNameEdit.setError("Last name is required");
                   return;
               }

               if (email.isEmpty()){
                   emailEdit.setError("Email is required");
                   return;
               }

               if (phoneNummber.isEmpty()){
                   phoneNumberEdit.setError("Phone number is required");
                   return;
               }

               if (radioButtomId == 0){
                   int lastChildPos=radioGroup.getChildCount()-1;
                   ((RadioButton)radioGroup.getChildAt(lastChildPos)).setError("Rol is required");
                   return;
               }

               if (password.isEmpty()){
                   passwordEdit.setError("Name is required");
                   return;
               } else{
                   if (!password.equals(confirmPassword)){
                       confirmPasswordEdit.setError("Password does not match");
                       return;
                   }
               }

               User user = new User(name, lastName,email, phoneNummber, password, rol );
               mAuth.createUserWithEmailAndPassword(email, password)
                       .addOnCompleteListener((Activity) view.getContext(), new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                               if (task.isSuccessful()){
                                   UserDao dao = new UserDao();
                                   Boolean isSuccess = dao.Insert(user);
                                   if (isSuccess){
                                       findNavController(view).navigate(R.id.backLogin);
                                       Toast.makeText(view.getContext(), "User created", Toast.LENGTH_LONG ).show();
                                   }
                               }else{
                                   Toast.makeText(view.getContext(), "Sign up failed: "+task.getException(), Toast.LENGTH_LONG ).show();
                               }
                               progress.setVisibility(View.GONE);
                           }
                       });
           } catch (Exception e){
               Toast.makeText(view.getContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
           }

        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        progress.setVisibility(View.GONE);
    }
}