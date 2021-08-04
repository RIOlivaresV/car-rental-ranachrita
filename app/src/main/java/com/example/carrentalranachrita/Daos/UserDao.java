package com.example.carrentalranachrita.Daos;

import com.example.carrentalranachrita.Entities.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Entity;

import java.util.List;

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
    public DatabaseReference Select(User entity) {
        try {
            return UserReference;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public DatabaseReference SelectAll() {
        return null;
    }

    @Override
    public User Update(User entity) {
        return null;
    }

    @Override
    public boolean Delete(User entity) {
        return false;
    }

}
