package entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ScheduleModel {
    private List<ScheduleEntry> scheduleEntries;

    public ScheduleModel(List<ScheduleEntry> scheduleEntries) {
        this.scheduleEntries = scheduleEntries;
    }


    public List<ScheduleEntry> getScheduleEntries() {
        return scheduleEntries;
    }

    public void setScheduleEntries(List<ScheduleEntry> scheduleEntries) {
        this.scheduleEntries = scheduleEntries;
    }

    public List<Patient> getPatientsForDate(LocalDate date) {
        List<Patient> patients = new ArrayList<>();
        for (ScheduleEntry entry : scheduleEntries) {
            if (entry.getDate().equals(date)) {
                patients.addAll(entry.getPatients());
            }
        }
        return patients;
    }
    public void addPatient(Patient patient, LocalDate date) {
        for (ScheduleEntry entry : scheduleEntries) {
            if (entry.getDate().equals(date)) {
                entry.getPatients().add(patient);
                return;
            }
        }

        ScheduleEntry newEntry = new ScheduleEntry();
        newEntry.setDate(date);
        newEntry.setPatients(new ArrayList<>());
        newEntry.getPatients().add(patient);
        scheduleEntries.add(newEntry);
    }

    public void removePatient(String name, LocalDate dob, LocalDate date) {
        for (ScheduleEntry entry : scheduleEntries) {
            if (entry.getDate().equals(date)) {
                List<Patient> patients = entry.getPatients();
                Iterator<Patient> iterator = patients.iterator();
                while (iterator.hasNext()) {
                    Patient patient = iterator.next();
                    if (patient.getName().equals(name) && patient.getDob().equals(dob)) {
                        iterator.remove();
                        return;
                    }
                }
            }
        }
    }
}