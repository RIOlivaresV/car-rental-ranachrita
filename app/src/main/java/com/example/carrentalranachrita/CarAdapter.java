package com.example.carrentalranachrita;

import android.app.AlertDialog;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrentalranachrita.Daos.DaoCarImg;
import com.example.carrentalranachrita.Entities.Car;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static androidx.navigation.Navigation.findNavController;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder>{
    private ArrayList<Car> carArrayList;
    public SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
    private View currentView;

    public CarAdapter(ArrayList<Car> carArrayList) {
        this.carArrayList = carArrayList;
    }

    public CarAdapter() {
        carArrayList = new ArrayList<Car>();
    }

    public void addView(View view){
        this.currentView = view;
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
        viewHolder.image.setClickable(true);
        viewHolder.price.setText("$"+car.getPrice()+" per day");
        viewHolder.image.setOnClickListener(v -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(currentView.getContext());
            alertDialog.setTitle("Delete Car");
            alertDialog.setMessage("Do you want to delete "+car.getBrand()+" - "+car.getModel());
            alertDialog.setCancelable(true);
            alertDialog.setNegativeButton("No", (dialog, which) -> {
                dialog.dismiss();
            });
            alertDialog.show();
        });
        viewHolder.ratingBar.setIsIndicator(true);
        String driver = "The car's owner can be driver.";
        if (car.isOwnerDriver()){
            viewHolder.carDriver.setText(driver);
        }else {
            viewHolder.carDriver.setVisibility(View.GONE);
        }
        viewHolder.layout.setOnClickListener(v -> {
            Bundle arg = new Bundle();
            arg.putString("carId", car.getId());
            findNavController(currentView).navigate(R.id.carListToDetails, arg);
        });

        StorageReference imgRef = new DaoCarImg().SelectPiture(car.getHostId().replace("@", ""), car);

        File localFile = null;
        try {
            localFile = File.createTempFile(String.valueOf(car.getCreatedDate().hashCode()), "jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        File finalLocalFile = localFile;

        imgRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                viewHolder.carPicture.setImageURI(Uri.fromFile(finalLocalFile));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Snackbar.make(currentView, "Something was wrong with your picture, refresh it.", Snackbar.LENGTH_LONG).show();
            }
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
        ImageView image;
        ImageView carPicture;
        TextView price;

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
            image = (ImageView) v.findViewById(R.id.btnDeleteCar);
            price = (TextView) v.findViewById(R.id.pricetTextView);
            carPicture = (ImageView) v.findViewById(R.id.carImageView);
        }
    }

    @Override
    public int getItemCount() {
        return carArrayList.size();
    }
}
