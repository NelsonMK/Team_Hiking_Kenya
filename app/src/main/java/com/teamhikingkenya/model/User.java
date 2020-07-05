package com.teamhikingkenya.model;

public class User {

    private int id;
    private String first_name;
    private String last_name;
    private String phone;
    private String email;
    private String gender;
    private String date_of_birth;
    private String location;
    private int class_id;
    private int status;
    private int archive;

    public User() {}

    public User(int id, String first_name, String last_name, String phone, String email, String gender, String date_of_birth, String location,
                int class_id, int status, int archive){
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.date_of_birth = date_of_birth;
        this.location = location;
        this.class_id = class_id;
        this.status = status;
        this.archive = archive;
    }

    public int getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public String getLocation() {
        return location;
    }

    public int getClass_id() {
        return class_id;
    }

    public int getStatus() {
        return status;
    }

    public int getArchive() {
        return archive;
    }
}
