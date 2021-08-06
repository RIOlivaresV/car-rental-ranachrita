package com.example.carrentalranachrita.Daos;

import com.example.carrentalranachrita.Entities.Booking;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BookingDao implements IDao<Booking>{

    private String Reference = "car";
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference UserReference;

    public BookingDao(){UserReference = firebaseDatabase.getReference(Reference);}


    @Override
    public boolean Insert(Booking entity) {
        try {
            UserReference.child("car").setValue(entity);
            return  true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public DatabaseReference Select(String id) {
        try {
            return  UserReference.child(id);
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
    public Booking Update(Booking entity) {
        return null;
    }

    @Override
    public boolean Delete(Booking entity) {
        return false;
    }
}
