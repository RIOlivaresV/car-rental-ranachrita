package com.example.carrentalranachrita.Daos;

import com.example.carrentalranachrita.Entities.License;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DaoInsurence implements IDao<License>{
    private String Reference = "license";
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference UserReference;

    public DaoInsurence(){UserReference = firebaseDatabase.getReference(Reference);}

    @Override
    public boolean Insert(License entity) {
        try {
            UserReference.child("car").e.setValue(entity);
            return  true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public DatabaseReference Select(String id) {
        return null;
    }

    @Override
    public DatabaseReference SelectAll() {
        return null;
    }

    @Override
    public License Update(License entity) {
        return null;
    }

    @Override
    public boolean Delete(License entity) {
        return false;
    }
}
