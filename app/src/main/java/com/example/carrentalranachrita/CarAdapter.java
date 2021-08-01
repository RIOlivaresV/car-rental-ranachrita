package com.example.carrentalranachrita;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrentalranachrita.Entities.Car;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder>{
    private ArrayList<Car> carArrayList;
    public SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);

    public CarAdapter(ArrayList<Car> carArrayList) {
        this.carArrayList = carArrayList;
    }

    public CarAdapter() {
        carArrayList = new ArrayList<Car>();
    }

    public void addCar(Car car){
        carArrayList.add(car);
    }

    public void addCarList(ArrayList<Car> cars){
        carArrayList = cars;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.carbox_layout, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder viewHolder, int i) {
        Car car =carArrayList.get(i);
        viewHolder.carYear.setText(String.valueOf(car.getYear()));
        viewHolder.carBrand.setText(car.getBrand());
        viewHolder.carModel.setText(car.getModel());
        viewHolder.carColor.setText(car.getColor());
        viewHolder.carOdometer.setText(car.getOdometer());
        viewHolder.carTransmission.setText(car.getTransmission());
        viewHolder.carFrom.setText(sdf.format(car.getFrom()));
        viewHolder.carTo.setText(sdf.format(car.getTo()));
        viewHolder.ratingBar.setIsIndicator(true);
        String driver = "The car's owner can be driver.";
        if (car.isOwnerDriver()){
            viewHolder.carDriver.setText(driver);
        }else {
            viewHolder.carDriver.setVisibility(View.INVISIBLE);
        }
        viewHolder.layout.setOnClickListener(v -> {
            Snackbar.make(viewHolder.itemView, "You click me", Snackbar.LENGTH_LONG).show();
        });
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView carBrand ;
        TextView carModel ;
        TextView carColor ;
        TextView carYear ;
        TextView carFrom ;
        TextView carTo ;
        TextView carOdometer ;
        TextView carTransmission ;
        TextView carDriver ;
        LinearLayout layout;
        RatingBar ratingBar;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            carBrand = (TextView) v.findViewById(R.id.carBrand);
            carModel = (TextView) v.findViewById(R.id.carModel);
            carColor =  (TextView) v.findViewById(R.id.carColor);
            carYear = (TextView) v.findViewById(R.id.carYear);
            carFrom = (TextView) v.findViewById(R.id.carFrom);
            carTo = (TextView) v.findViewById(R.id.carTo);
            carOdometer = (TextView) v.findViewById(R.id.carOdometer);
            carTransmission = (TextView) v.findViewById(R.id.carTrasnmission);
            carDriver = (TextView) v.findViewById(R.id.carEnableDriver);
            layout = (LinearLayout) v.findViewById(R.id.carItemList);
            ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
        }
    }

    @Override
    public int getItemCount() {
        return carArrayList.size();
    }
}
