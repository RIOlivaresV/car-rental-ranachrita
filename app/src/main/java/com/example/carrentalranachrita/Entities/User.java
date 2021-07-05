package com.example.carrentalranachrita.Entities;

public class User {
    private String Id;
    private String Name;
    private String LastName;
    private String Email;
    private String PhoneNumber;
    private String Password;
    private String Rol;

    public User() {
    }

    public User(String name, String lastName, String email, String phoneNumber, String password, String rol) {
        Name = name;
        LastName = lastName;
        Email = email;
        PhoneNumber = phoneNumber;
        Password = password;
        Rol = rol;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRol() {
        return Rol;
    }

    public void setRol(String rol) {
        Rol = rol;
    }
}
