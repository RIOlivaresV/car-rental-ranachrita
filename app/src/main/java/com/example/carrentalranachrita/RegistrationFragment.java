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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioButtonRole);
        RadioButton radioButtonCustomer = (RadioButton) view.findViewById(R.id.radioButtonCustomer);
        RadioButton radioButtonHost = (RadioButton) view.findViewById(R.id.radioButtonHost);



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
               //String rol = r.getText().toString();

               // get selected radio button from radioGroup
               String rol = String.valueOf(radioGroup.getCheckedRadioButtonId());


               if (name.isEmpty()){
                   nameEdit.setError("Name is required");
                   Toast.makeText(view.getContext(), "Name is required ", Toast.LENGTH_LONG ).show();
                   return;
               }

               if (lastName.isEmpty()){
                   lastNameEdit.setError("Last name is required");
                   Toast.makeText(view.getContext(), "Last name is required ", Toast.LENGTH_LONG ).show();
                   return;
               }

               if (email.isEmpty()){
                   emailEdit.setError("Email is required");
                   Toast.makeText(view.getContext(), "Email is required ", Toast.LENGTH_LONG ).show();
                   return;
               }
               else if(!isEmailValid(email)){
                   emailEdit.setError("Email is not in correct format");
                   Toast.makeText(view.getContext(), "Email is not in correct format ", Toast.LENGTH_LONG ).show();
                   return;
               }

               if (phoneNummber.isEmpty()) {
                   phoneNumberEdit.setError("Phone number is required");
                   Toast.makeText(view.getContext(), "Phone number is required ", Toast.LENGTH_LONG).show();
                   return;
               }
               else if(phoneNummber.length() != 10 ){
                   phoneNumberEdit.setError("Phone number is not correct");
                   Toast.makeText(view.getContext(), "Phone number is not correct ", Toast.LENGTH_LONG ).show();
                   return;
               }

               if(!radioButtonCustomer.isChecked() && !radioButtonHost.isChecked() ){
                   radioButtonCustomer.setError("Role is required");
                   Toast.makeText(view.getContext(), "Select Role is required ", Toast.LENGTH_LONG ).show();
                   return;
               }
               if (radioButtomId == 0){
                   int lastChildPos=radioGroup.getChildCount()-1;
                   ((RadioButton)radioGroup.getChildAt(lastChildPos)).setError("Role is required");
                   Toast.makeText(view.getContext(), "Select Role is required ", Toast.LENGTH_LONG ).show();
                   return;
               }

               if (password.isEmpty()){
                   passwordEdit.setError("Password is required");
                   Toast.makeText(view.getContext(), "Password is required ", Toast.LENGTH_LONG ).show();
                   return;
               }
               else if(password.length()<8){
                   passwordEdit.setError("Password must be at least 8 Characters");
                   Toast.makeText(view.getContext(), "Password must be at least 8 Characters ", Toast.LENGTH_LONG ).show();
                   return;
               }

                   if (!password.equals(confirmPassword)){
                       confirmPasswordEdit.setError("Password does not match");
                       Toast.makeText(view.getContext(), "Password does not match ", Toast.LENGTH_LONG ).show();
                       return;
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
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}