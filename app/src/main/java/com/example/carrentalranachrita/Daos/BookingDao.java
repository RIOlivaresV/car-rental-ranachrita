package com.example.carrentalranachrita.Daos;

import com.example.carrentalranachrita.Entities.Booking;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class BookingDao implements IDao<Booking>{

    private String Reference = "car";
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference UserReference;

    public BookingDao(){UserReference = firebaseDatabase.getReference(Reference);}

    public boolean InsertBooking(List<Booking> bookings, String carId){
        try {
            UserReference.child(carId).child("booking").setValue(bookings);
            return  true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean Insert(Booking entity) {
        try {
            UserReference.child("booking").setValue(entity);
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
