package com.example.studentapp.DAO.entities;

public class Student {
    private String name;
    private String lastName;
    private int id;
    private String email;
    private String date;
    private String city;

    public Student( int id,String name, String lastName, String email, String date, String city) {
        this.name = name;
        this.lastName = lastName;
        this.id = id;
        this.email = email;
        this.date = date;
        this.city = city;
    }

    public Student() {

    }


    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getDate() {
        return date;
    }

    public String getCity() {
        return city;
    }

}
