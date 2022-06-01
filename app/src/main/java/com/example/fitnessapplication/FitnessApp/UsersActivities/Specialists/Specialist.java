package com.example.fitnessapplication.FitnessApp.UsersActivities.Specialists;

public class Specialist {
    private String uid;
    private String name;
    private String email;
    private String aboutMe;
    private String image;
    private String typeOfSpecialist;
    private String description;

    public Specialist() {
    }

    public Specialist(String uid, String name, String email, String aboutMe, String image, String typeOfSpecialist, String description) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.aboutMe = aboutMe;
        this.image = image;
        this.typeOfSpecialist = typeOfSpecialist;
        this.description = description;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTypeOfSpecialist() {
        return typeOfSpecialist;
    }

    public void setTypeOfSpecialist(String typeOfSpecialist) {
        this.typeOfSpecialist = typeOfSpecialist;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
