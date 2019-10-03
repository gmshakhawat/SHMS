package com.example.nusrat.smarthealthmonitoringsystem;

public class User {
    public String name, email,age,phone,accessType,id;

    public User(){

    }

    public User(String name, String email, String age, String phone, String accessType,String id) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.phone = phone;
        this.accessType = accessType;
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }
}