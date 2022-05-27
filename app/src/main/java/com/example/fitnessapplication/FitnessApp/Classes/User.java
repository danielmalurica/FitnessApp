package com.example.fitnessapplication.FitnessApp.Classes;

public class User {
    private String uid;
    private String email;
    private String name;
    private  int age;
    private int weight;
    private int height;
    private String gender;

    public User(String uid, String email, String name, int age, int weight, int height, String gender) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.gender=gender;
    }

    public User() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", height=" + height +
                ", gender='" + gender + '\'' +
                '}';
    }
}
