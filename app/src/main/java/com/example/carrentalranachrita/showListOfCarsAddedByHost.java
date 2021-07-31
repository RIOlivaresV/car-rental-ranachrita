package com.example.carrentalranachrita;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class showListOfCarsAddedByHost extends AppCompatActivity implements listOfCarsAddedByHostAdapter.ItemClickListener{

    Integer[] namesCar = {R.id.txtCarName,R.id.txtCarName,R.id.txtCarName};
    listOfCarsAddedByHostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_of_cars_added_by_host);


    RecyclerView recyclerView = findViewById(R.id.recyclerListOfCarsAddedByHost);

    recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new listOfCarsAddedByHostAdapter(this,namesCar);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

}

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getBaseContext(),"Selected" + (position + 1) + " " , Toast.LENGTH_LONG).show();

    }
}