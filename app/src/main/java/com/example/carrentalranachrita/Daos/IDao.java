package com.example.carrentalranachrita.Daos;

import java.util.List;

public interface IDao<T> {
    public boolean Insert(T entity);
    public T Select(T entity);
    public List<T> SelectAll();
    public T Update(T entity);
    public boolean Delete(T entity);
}
