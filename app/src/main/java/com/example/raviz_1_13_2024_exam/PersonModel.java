package com.example.raviz_1_13_2024_exam;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PersonModel{

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String person_id;

    public PersonModel(List<String> firstNames) {
    }

    private String first_name;
    private String last_name;
    private String mobile;
    private int age;
    private String address;
    private String email;

    public PersonModel(String person_id, String first_name, String last_name, String mobile, int age, String address, String email) {
        this.person_id = person_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.mobile = mobile;
        this.age = age;
        this.address = address;
        this.email = email;
    }


}
