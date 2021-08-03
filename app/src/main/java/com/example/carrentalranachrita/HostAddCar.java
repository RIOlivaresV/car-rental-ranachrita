package com.example.carrentalranachrita;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrentalranachrita.Daos.CarDao;
import com.example.carrentalranachrita.Daos.DaoCarImg;
import com.example.carrentalranachrita.Entities.Booking;
import com.example.carrentalranachrita.Entities.Car;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static androidx.navigation.Navigation.findNavController;

public class HostAddCar extends Fragment {

    private final int PICK_IMAGE_REQUEST = 25;
    private Uri imgPath;
    public SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
    public SimpleDateFormat noSpace = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
    private  EditText imageInput;
    public HostAddCar() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HostAddCar newInstance(String param1, String param2) {
        HostAddCar fragment = new HostAddCar();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1999; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }

        // Inflate the layout for this fragment
        Calendar avialabilityFrom = Calendar.getInstance();
        Calendar avialibilityTo = Calendar.getInstance();
        View view = inflater.inflate(R.layout.fragment_host_add_car, container, false);
        Spinner yearInput = (Spinner)  view.findViewById(R.id.spinnerYear);
        EditText brandInput = (EditText) view.findViewById(R.id.txtBrand);
        imageInput = (EditText) view.findViewById(R.id.txtUploadImage);
        EditText modelInput = (EditText) view.findViewById(R.id.txtModel);
        EditText colorInput = (EditText) view.findViewById(R.id.txtCarColor);
        EditText odometerInput = (EditText) view.findViewById(R.id.txtOdometer);
        ImageView uploadInput = (ImageView) view.findViewById(R.id.uploadBtn);
        EditText carAvailabilityInput = (EditText)  view.findViewById(R.id.txtCarAvailability);
        EditText carDetailInput = (EditText) view.findViewById(R.id.txtCarDetail);
        CheckBox ownerAsDriverInput = (CheckBox) view.findViewById(R.id.driverAvailableBtn);
        CheckBox insureInput = (CheckBox) view.findViewById(R.id.insuranceBtn);
        Button addCarButton = (Button) view.findViewById(R.id.addCarBtn);
        RadioButton radioAuto = (RadioButton)view.findViewById(R.id.automaticBtn);
        RadioButton radioManual = (RadioButton)view.findViewById(R.id.manualBtn);
        EditText priceInput = (EditText) view.findViewById(R.id.txtPrice);
        EditText locationInput = (EditText) view.findViewById(R.id.txtLocationHost);
        EditText seatInput = (EditText) view.findViewById(R.id.txtSeat);
        EditText doorInput = (EditText) view.findViewById(R.id.txtDoor);

        uploadInput.setOnClickListener(v -> {
            this.choosePhoto();
        });

        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, years);
        yearInput.setAdapter(adapterYear);
        Calendar today = Calendar.getInstance();
        carAvailabilityInput.setOnClickListener(v -> {
            MaterialDatePicker.Builder<Pair<Long, Long>> build = MaterialDatePicker.Builder.dateRangePicker();
            MaterialDatePicker.Builder<Pair<Long, Long>> pairBuilder = build.setSelection(new Pair(today.getTimeInMillis(),today.getTimeInMillis() ));
            MaterialDatePicker picker = pairBuilder.build();
            picker.addOnPositiveButtonClickListener(selection -> {
                Pair selectedDates = (Pair) selection;
                Log.d("dates ", selectedDates.first.toString());
                final Pair<Date, Date> rangeDate = new Pair<>(new Date((Long) selectedDates.first), new Date((Long) selectedDates.second));
                avialabilityFrom.setTime(rangeDate.first);
                avialabilityFrom.add(Calendar.DATE, 1);
                avialibilityTo.setTime(rangeDate.second);
                avialibilityTo.add(Calendar.DATE, 1);
               carAvailabilityInput.setText(sdf.format(avialabilityFrom.getTime())+" - "+sdf.format(avialibilityTo.getTime()));
               Log.d("dates ", avialabilityFrom.toString());
                Log.d("dates ", avialibilityTo.toString());
            });
            picker.show(getActivity().getSupportFragmentManager(), picker.toString());
        });
        addCarButton.setOnClickListener(v -> {
            Car newCar = new Car();
            try{
                String location = locationInput.getText().toString();
                String brand = brandInput.getText().toString();
                String model = modelInput.getText().toString();
                String color = colorInput.getText().toString();
                String  odometer = odometerInput.getText().toString();
                String  price = priceInput.getText().toString();
                String seat = seatInput.getText().toString();
                String door = doorInput.getText().toString();

                if (yearInput == null){
                    ((TextView)yearInput.getSelectedView()).setError("Year is needed");
                }
                if(location.isEmpty()){
                    locationInput.setError("Location is required");
                    Toast.makeText(view.getContext(), "Location is required ", Toast.LENGTH_LONG ).show();
                    return;
                }
                if(brand.isEmpty()){
                    brandInput.setError("Brand is required");
                    Toast.makeText(view.getContext(), "Brand is required ", Toast.LENGTH_LONG ).show();
                    return;
                }
                if(model.isEmpty()){
                    modelInput.setError("Model is required");
                    Toast.makeText(view.getContext(), "Model is required ", Toast.LENGTH_LONG ).show();
                    return;
                }
                if(seat.isEmpty()){
                    seatInput.setError("Seats number is required");
                    Toast.makeText(view.getContext(), "Seats number is required ", Toast.LENGTH_LONG ).show();
                    return;
                }
                if(door.isEmpty()){
                    doorInput.setError("Doors number is required");
                    Toast.makeText(view.getContext(), "Doors number is required ", Toast.LENGTH_LONG ).show();
                    return;
                }
                if(color.isEmpty()){
                    colorInput.setError("Color is required");
                    Toast.makeText(view.getContext(), "Color is required ", Toast.LENGTH_LONG ).show();
                    return;
                }
                if (odometer.isEmpty()){
                    odometerInput.setError("Odometer is required");
                    Toast.makeText(view.getContext(), "Odometer is required", Toast.LENGTH_LONG ).show();
                    return;
                }
                if (!radioManual.isChecked() && !radioAuto.isChecked()){
                    radioManual.setError("Select transmission");
                    Toast.makeText(view.getContext(), "Select transmission ", Toast.LENGTH_LONG ).show();
                    if (radioManual.isChecked()){
                        newCar.setTransmission("Manual");
                    }else {
                        newCar.setTransmission("Automatic");
                    }
                    return;
                }
                if(price.isEmpty()){
                    priceInput.setError("Price is required");
                    Toast.makeText(view.getContext(), "Price is required", Toast.LENGTH_LONG ).show();
                    return;
                }

                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

                newCar.setYear(Integer.parseInt(yearInput.getSelectedItem().toString()));
                newCar.setBrand(brandInput.getText().toString());
                newCar.setModel(modelInput.getText().toString());
                newCar.setColor(colorInput.getText().toString());
                newCar.setOdometer(odometerInput.getText().toString());
                newCar.setFrom(avialabilityFrom.getTime());
                newCar.setTo(avialibilityTo.getTime());
                newCar.setOwnerDriver(ownerAsDriverInput.isChecked());
                newCar.setPrice(Double.parseDouble(priceInput.getText().toString()));
                newCar.setDetails(carDetailInput.getText().toString());
                newCar.setHostId(currentFirebaseUser.getEmail());
                newCar.setImagen(convertLocalImgToFirebase(imgPath, newCar));
                newCar.setModel(seatInput.getText().toString());
                newCar.setModel(doorInput.getText().toString());
//            newCar.setBooking(new ArrayList<Booking>());

                CarDao dao = new CarDao();
                dao.Insert(newCar);
                Snackbar.make(view, "Your cars was added.", Snackbar.LENGTH_LONG);
                findNavController(view).navigate(R.id.carList);
            }
            catch (Exception e){
                Toast.makeText(view.getContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }






        });
        return view;
    }

    private void choosePhoto(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
                // Get the Image from data

                if(data.getClipData()!=null){
                    ClipData mClipData = data.getClipData();
                    imgPath = mClipData.getItemAt(0).getUri();
                }
                if(data.getData()!= null){
                    imgPath = data.getData();
                }
                imageInput.setText(imgPath.getPath());
            } else {
                Toast.makeText(getContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String convertLocalImgToFirebase(Uri imgPath, Car car) {
        String result = "";

        String name = car.getBrand()+noSpace.format(car.getCreatedDate());
        if(imgPath != null){
            StorageReference fileRef = new DaoCarImg().SelectAllPictures().child(car.getHostId().replace("@", "")).child(name+".jpg");
            fileRef.putFile(imgPath);
            result = car.getHostId().replace("@", "") + "/"+name+".jpg";

        }
        else{
            result = "default.png";
        }
        return result;
    }
}