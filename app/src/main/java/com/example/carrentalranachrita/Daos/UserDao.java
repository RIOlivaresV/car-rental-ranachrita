package com.example.carrentalranachrita.Daos;

import com.example.carrentalranachrita.Entities.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Entity;

public class UserDao implements IDao<User>{
    private String Reference = "user";
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference UserReference;

    public UserDao() {
        UserReference = firebaseDatabase.getReference(Reference);
    }

    @Override
    public boolean Insert(User entity) {
        try {
            UserReference.push().setValue(entity);
            return  true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public User Select(User entity) {
        try {

        }catch (Exception e){

        }
        return null;
    }

    @Override
    public boolean Delete(User entity) {
        return false;
    }
}