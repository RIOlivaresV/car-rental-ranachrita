package com.example.carrentalranachrita.Entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Car {
    private String Id;
    private String Model;
    private String Brand;
    private String Color;
    private String Odometer;
    private String Transmission;
    private String Imagen;
    private String Details;
    private int Year;
    private boolean OwnerDriver;
    private String HostId;
    private int Seats;
    private int Doors;
    private String Type;
    private Date From;
    private Date To;
    private double Price;
    private String Location;
    private ArrayList<Booking> Booking;
    private Date CreatedDate;
//
    public ArrayList<Booking> getBooking() {
        return Booking;
    }

    public Date getCreatedDate() {
        return CreatedDate;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public void setBooking(ArrayList<Booking> booking) {
        Booking = booking;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public Car() {
        Id = "";
        Model = "";
        Brand = "";
        Color = "";
        Odometer = "";
        Transmission = "";
        Imagen = "";
        Details = "";
        Year = 0;
        OwnerDriver = false;
        HostId = "";
        Seats = 0;
        Doors = 0;
        Type = "";
        From = new Date();
        To = new Date();
        CreatedDate = Calendar.getInstance().getTime();
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getOdometer() {
        return Odometer;
    }

    public void setOdometer(String odometer) {
        Odometer = odometer;
    }

    public String getTransmission() {
        return Transmission;
    }

    public void setTransmission(String transmission) {
        Transmission = transmission;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public boolean isOwnerDriver() {
        return OwnerDriver;
    }

    public void setOwnerDriver(boolean ownerDriver) {
        OwnerDriver = ownerDriver;
    }

    public String getHostId() {
        return HostId;
    }

    public void setHostId(String hostId) {
        HostId = hostId;
    }

    public int getSeats() {
        return Seats;
    }

    public void setSeats(int seats) {
        Seats = seats;
    }

    public int getDoors() {
        return Doors;
    }

    public void setDoors(int doors) {
        Doors = doors;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Date getFrom() {
        return From;
    }

    public void setFrom(Date from) {
        From = from;
    }

    public Date getTo() {
        return To;
    }

    public void setTo(Date to) {
        To = to;
    }
}
