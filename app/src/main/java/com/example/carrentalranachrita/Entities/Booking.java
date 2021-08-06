package com.example.carrentalranachrita.Entities;

import java.util.Date;

public class Booking {
    private  String DateStart;
    private String TimeStart;
    private String CustomerId;
    private String DateEnd;
    private String TimeEnd;
    private Insurence Insurence;
    private Rate Rate;

    public String getDateStart() {
        return DateStart;
    }

    public void setDateStart(String dateStart) {
        DateStart = dateStart;
    }

    public String getTimeStart() {
        return TimeStart;
    }

    public void setTimeStart(String timeStart) {
        TimeStart = timeStart;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getDateEnd() {
        return DateEnd;
    }

    public void setDateEnd(String dateEnd) {
        DateEnd = dateEnd;
    }

    public String getTimeEnd() {
        return TimeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        TimeEnd = timeEnd;
    }

    public com.example.carrentalranachrita.Entities.Insurence getInsurence() {
        return Insurence;
    }

    public void setInsurence(com.example.carrentalranachrita.Entities.Insurence insurence) {
        Insurence = insurence;
    }

    public com.example.carrentalranachrita.Entities.Rate getRate() {
        return Rate;
    }

    public void setRate(com.example.carrentalranachrita.Entities.Rate rate) {
        Rate = rate;
    }
}
