package com.example.carrentalranachrita.Daos;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public interface IDao<T> {
    public boolean Insert(T entity);
    public DatabaseReference Select(String id);
    public DatabaseReference SelectAll();
    public T Update(T entity);
    public boolean Delete(T entity);
}
