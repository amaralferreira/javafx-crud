package com.javafxcrud.model;

import java.util.Calendar;
import java.util.Random;

public class Person {
    
    private String firstName;
    private String lastName;
    private String street;
    private int postalCode;
    private String city;
    private Calendar birthday;

    public Person() {
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = "some street";
        this.postalCode = 1234;
        this.city = "some city";
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, new Random().nextInt(11));
        this.birthday = calendar;
    }

    //@ColumnAnnotationType(message = "ERRO")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Calendar getBirthday() {
        return birthday;
    }

    public void setBirthday(Calendar birthday) {
        this.birthday = birthday;
    }

}
