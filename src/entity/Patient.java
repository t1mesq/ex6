package entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Patient {
    private String name;
    private LocalDate dob;
    private String type;
    private String symptoms;
    private LocalTime time;

    public Patient(String name, LocalDate dob, String type, String symptoms, LocalTime time) {
        this.name = name;
        this.dob = dob;
        this.type = type;
        this.symptoms = symptoms;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}