package com.example.carrentalranachrita;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.carrentalranachrita.Daos.CarDao;
import com.example.carrentalranachrita.Entities.Car;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CarList extends Fragment{
    ImageView filterButton;

    public CarList() {
        // Required empty public constructor
    }

    public static CarList newInstance(String param1, String param2) {
        CarList fragment = new CarList();
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

        ArrayList<Car> carArrayList= new ArrayList<Car>() ;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_car_list, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.carListRecycleView);
        ProgressBar progressBar = view.findViewById(R.id.progressBarCarList);
        EditText search = (EditText) view.findViewById(R.id.searchEditText);
        CarDao dao = new CarDao();
        dao.SelectAll().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
                CarAdapter adapter = new CarAdapter();
                adapter.addView(view);
                carArrayList.clear();
                for (DataSnapshot child: snapshot.getChildren()) {
                    if (child.getValue() != null){
                        Car car = child.getValue(Car.class);
                        car.setId(child.getKey());
                        carArrayList.add(car);
                    }
                }
                adapter.addCarList(carArrayList);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Snackbar.make(getView(), "Something was wrong, please try again", Snackbar.LENGTH_LONG);
            }


        });


        return view;
    }

}