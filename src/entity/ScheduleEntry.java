package entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ScheduleEntry {
    private LocalDate date;
    private LocalTime time;

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    private List<Patient> patients;

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}
