package com.example.carrentalranachrita;

import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrentalranachrita.Daos.CarDao;
import com.example.carrentalranachrita.Daos.DaoCarImg;
import com.example.carrentalranachrita.Entities.Car;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CarDetail extends Fragment {

    private String CarID;

    public String getCarID() {
        return CarID;
    }

    public void setCarID(String carID) {
        CarID = carID;
    }

    public CarDetail() {
        // Required empty public constructor
    }

    public static CarDetail newInstance(String param1, String param2) {
        CarDetail fragment = new CarDetail();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        setCarID(args.getString("carId"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        View view = inflater.inflate(R.layout.fragment_car_detail, container, false);
        TextView carInput = view.findViewById(R.id.carNameDetailTextView);
        RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        TextView seatInput = view.findViewById(R.id.carSeatDetailtextView);
        TextView doorsInput = view.findViewById(R.id.carDoorDetailTextView);
        TextView typeInput = view.findViewById(R.id.carFuelTypeTextView);
        TextView conditionsInput = view.findViewById(R.id.carDescDetailTextView);
        TextView refoundInput = view.findViewById(R.id.fullRefundDetailTextView);
        CheckBox checkboxInput = view.findViewById(R.id.needDrivercheckBox);
        TextView ownerInput = view.findViewById(R.id.hostNameValueTextView);
        Button Submit = view.findViewById(R.id.bookButton);
        Button insurance =view.findViewById(R.id.addInsuranceButton);
        Button checkAvialability = view.findViewById(R.id.checkAvailabilityButton);
        TextView priceInput = view.findViewById(R.id.priceValueTextView);
        ProgressBar progressBar = view.findViewById(R.id.progressBarCarDetails);
        TextView dateStart = view.findViewById(R.id.startDetailEditTextDate);
        TextView dateEnd = view.findViewById(R.id.endDetailEditTextDate);
        ImageView carImgInput = view.findViewById(R.id.carImageDetailView);

        CarDao dao = new CarDao();
        dao.Select(getCarID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
                Car car = new Car();
                car = snapshot.getValue(Car.class);
                car.setId(snapshot.getKey());
                carInput.setText(car.getBrand()+" / "+car.getModel());
                seatInput.setText(car.getSeats()+" seats");
                doorsInput.setText(car.getDoors()+" doors");
                typeInput.setText(car.getTransmission());
                conditionsInput.setText(car.getDetails());
                Date start = car.getFrom();
                Calendar startCalendar = Calendar.getInstance();
                Date end = car.getTo();
                Calendar endCalendar = Calendar.getInstance();
                dateStart.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog dialog = new DatePickerDialog(v.getContext(),
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        startCalendar.set(Calendar.YEAR, year);
                                        startCalendar.set(Calendar.MONTH, month);
                                        startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                        dateStart.setText(sdf.format(startCalendar.getTime()));
                                    }
                                }, start.getYear(), start.getMonth(), start.getDay());
                        dialog.getDatePicker().setMaxDate(end.getTime());
                        dialog.getDatePicker().setMinDate(start.getTime());
                        dialog.show();

                    }
                });
                dateEnd.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog dialog = new DatePickerDialog(v.getContext(),
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                        endCalendar.set(Calendar.YEAR, year);
                                        endCalendar.set(Calendar.MONTH, month);
                                        endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                        dateEnd.setText(sdf.format(endCalendar.getTime()));
                                    }
                                }, end.getYear(), end.getMonth(), end.getDay());
                        dialog.getDatePicker().setMaxDate(end.getTime());
                        dialog.getDatePicker().setMinDate(start.getTime());
                        dialog.show();

                    }
                });
                Calendar refoundDate = Calendar.getInstance();
                refoundDate.setTime(car.getFrom());
                refoundDate.add(Calendar.DATE, -1);
                refoundInput.setText("Full refund before "+sdf.format(refoundDate.getTime()));
                checkboxInput.setChecked(car.isOwnerDriver());
                priceInput.setText("$"+car.getPrice() + " CAD - Per Night");
                ownerInput.setText(car.getHostId());
                checkAvialability.setOnClickListener(v -> {

                    Snackbar.make(view, "Awesome! Car is available in this period. ", Snackbar.LENGTH_LONG).show();
                });
                insurance.setOnClickListener(v ->{

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
                        carImgInput.setImageURI(Uri.fromFile(finalLocalFile));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Snackbar.make(view, "Something was wrong with your picture, refresh it.", Snackbar.LENGTH_LONG).show();
                    }
                });
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        return view;
    }
}