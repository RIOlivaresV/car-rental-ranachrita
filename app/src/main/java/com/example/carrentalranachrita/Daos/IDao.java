package com.example.carrentalranachrita.Daos;

public interface IDao<T> {
    public boolean Insert(T entity);
    public T Select(T entity);
    public boolean Delete(T entity);
}
