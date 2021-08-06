package com.example.carrentalranachrita;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Host_Activity extends AppCompatActivity {

    public void logout()
    {
        Intent i = new Intent(Host_Activity.this,MainActivity.class);
        startActivity(i);

    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        BottomNavigationView navView = findViewById(R.id.bottomNav_view_host);




        //Pass the ID's of Different destinations
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.showCarRequestForHost, R.id.hostProfile, R.id.hostAddCar)
                .build();



        //Initialize NavController.
        NavController navController = Navigation.findNavController(this, R.id.main_host_Fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }


}