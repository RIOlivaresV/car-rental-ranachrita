package com.example.carrentalranachrita.Daos;

import com.example.carrentalranachrita.Entities.Car;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CarDao implements IDao<Car> {
    private String Reference = "car";
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference UserReference;

    public CarDao(){UserReference = firebaseDatabase.getReference(Reference);}

    @Override
    public boolean Insert(Car entity) {
        try {
            UserReference.push().setValue(entity);
            return  true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public DatabaseReference Select(Car entity) {
        try {
            return  UserReference;
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public DatabaseReference SelectAll() {
        try {
            return  UserReference;
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public Car Update(Car entity) {
        return null;
    }

    @Override
    public boolean Delete(Car entity) {
        return false;
    }
}
