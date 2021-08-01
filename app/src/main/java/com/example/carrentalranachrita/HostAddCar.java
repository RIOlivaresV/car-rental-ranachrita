package com.example.carrentalranachrita;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.carrentalranachrita.Daos.CarDao;
import com.example.carrentalranachrita.Entities.Booking;
import com.example.carrentalranachrita.Entities.Car;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HostAddCar extends Fragment {


    public SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd, yyyy HH:mm:ss z ", Locale.ENGLISH);
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
        for (int i = 1900; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }

        // Inflate the layout for this fragment
        Calendar avialabilityFrom = Calendar.getInstance();
        Calendar avialibilityTo = Calendar.getInstance();
        View view = inflater.inflate(R.layout.fragment_host_add_car, container, false);
        Spinner yearInput = (Spinner)  view.findViewById(R.id.spinnerYear);
        EditText brandInput = (EditText) view.findViewById(R.id.txtBrand);
        EditText modelInput = (EditText) view.findViewById(R.id.txtModel);
        EditText colorInput = (EditText) view.findViewById(R.id.txtCarColor);
        EditText odometerInput = (EditText) view.findViewById(R.id.txtOdometer);
        Button uploadInput = (Button) view.findViewById(R.id.uploadBtn);
        EditText carAvailabilityInput = (EditText)  view.findViewById(R.id.txtCarAvailability);
        EditText carDetailInput = (EditText) view.findViewById(R.id.txtCarDetail);
        CheckBox ownerAsDriverInput = (CheckBox) view.findViewById(R.id.driverAvailableBtn);
        CheckBox insureInput = (CheckBox) view.findViewById(R.id.insuranceBtn);
        Button addCarButton = (Button) view.findViewById(R.id.addCarBtn);
        RadioButton radioAuto = (RadioButton)view.findViewById(R.id.automaticBtn);
        RadioButton radioManual = (RadioButton)view.findViewById(R.id.manualBtn);

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
               carAvailabilityInput.setText(avialabilityFrom.getTime().toString()+" - "+avialibilityTo.getTime().toString());
               Log.d("dates ", avialabilityFrom.toString());
                Log.d("dates ", avialibilityTo.toString());
            });
            picker.show(getActivity().getSupportFragmentManager(), picker.toString());
        });
        addCarButton.setOnClickListener(v -> {
            Car newCar = new Car();

            if (!radioAuto.isChecked()){
                if (!radioManual.isChecked()){
                    radioManual.setError("Please, select one transmission");
                }else {
                    newCar.setTransmission("Manual");
                }
            }else{
                newCar.setTransmission("Automatic");
            }
            if (modelInput == null){
                modelInput.setError("Please, select a model");
            }
            if (brandInput.getText().toString().isEmpty()){
                ((TextView)brandInput).setError("Please, select a brand");
            }
            if (yearInput == null){
                ((TextView)yearInput.getSelectedView()).setError("Year is needed");
            }
            if (colorInput.getText().toString().isEmpty()){
                colorInput.setError("Color is needed");
            }
            if (odometerInput == null){
                odometerInput.setError("Odometer is needed");
            }

            newCar.setYear(Integer.parseInt(yearInput.getSelectedItem().toString()));
            newCar.setBrand(brandInput.getText().toString());
            newCar.setModel(modelInput.getText().toString());
            newCar.setColor(colorInput.getText().toString());
            newCar.setOdometer(odometerInput.getText().toString());
            newCar.setFrom(avialabilityFrom.getTime());
            newCar.setTo(avialibilityTo.getTime());
            newCar.setOwnerDriver(ownerAsDriverInput.isChecked());
//            newCar.setBooking(new ArrayList<Booking>());

            CarDao dao = new CarDao();
            dao.Insert(newCar);
            Snackbar.make(view, "Your cars was added.", Snackbar.LENGTH_LONG);
        });
        return view;
    }
}