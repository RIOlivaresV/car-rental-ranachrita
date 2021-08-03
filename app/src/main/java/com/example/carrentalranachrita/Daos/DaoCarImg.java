package com.example.carrentalranachrita.Daos;

import com.example.carrentalranachrita.Entities.Car;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DaoCarImg implements IDao<String> {
    public SimpleDateFormat noSpace = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
    private String Reference = "car";
    private StorageReference storage = FirebaseStorage.getInstance().getReference("images");
    private StorageReference UserReference;

    public DaoCarImg() { UserReference = storage.child(Reference);}

    @Override
    public boolean Insert(String entity) {
        return false;
    }

    public StorageReference SelectPiture(String idHost, Car car) {
        try {
            return  UserReference.child(idHost).child(car.getBrand()+noSpace.format(car.getCreatedDate())+".jpg");
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public DatabaseReference Select(String id) {
        return null;
    }

    public StorageReference SelectAllPictures() {
        try {
            return  UserReference;
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public DatabaseReference SelectAll() {
        return null;
    }

    @Override
    public String Update(String entity) {
        return null;
    }

    @Override
    public boolean Delete(String entity) {
        return false;
    }
}
