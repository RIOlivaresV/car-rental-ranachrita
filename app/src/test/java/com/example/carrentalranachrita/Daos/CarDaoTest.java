package com.example.carrentalranachrita.Daos;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.carrentalranachrita.Entities.Car;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CarDaoTest extends TestCase {

    private DatabaseReference mockedDatabaseReference;

    @Before
    public void before() {
        mockedDatabaseReference = Mockito.mock(DatabaseReference.class);

        FirebaseDatabase mockedFirebaseDatabase = Mockito.mock(FirebaseDatabase.class);
        when(mockedFirebaseDatabase.getReference()).thenReturn(mockedDatabaseReference);
    }

    @Test
//    Add car
    public void testInsert() throws ParseException {
        CarDao dao = mock(CarDao.class);
        Car car = new Car();
        car.setLocation("Burnaby");
        car.setBrand("BMW");
        car.setModel("X6");
        car.setColor("white");
        car.setOdometer("1800");
        car.setTransmission("Manual");
        car.setImagen("/file/picture.jpg");
        Date dateFrom = new SimpleDateFormat("yyyy/MM/dd").parse("2021/08/06");
        car.setFrom(dateFrom);
        Date dateTo = new SimpleDateFormat("yyyy/MM/dd").parse("2022/08/03");
        car.setFrom(dateTo);
        car.setDetails("This car is brand new");
        car.setPrice(100);
        car.setYear(2020);
        car.setDoors(4);
        car.setSeats(4);
        when(dao.Insert(car)).thenReturn(true);


    }

    @Test
//    Car Details
    public void testSelect() {
        CarDao dao = mock(CarDao.class);
        when(dao.Select("MgJZN8WN1lKuKFSIdp0")).thenReturn(mockedDatabaseReference);
    }

    @Test
//    Carlist test
    public void testSelectAll() {
        CarDao dao = mock(CarDao.class);
        when(dao.SelectAll()).thenReturn(mockedDatabaseReference);
    }

    @Test
    public void testUpdate() {
    }

    @Test
    public void testDelete() {
    }
}