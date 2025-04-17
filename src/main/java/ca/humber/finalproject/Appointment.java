package ca.humber.finalproject;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;
@Entity
public class Appointment {

    @Id
    private int clientid;
    private String servicetype;
    private String servicestation;
    private LocalDate date;
    private String notes;

    public Appointment() {
    }

    public Appointment(int clientid, String servicetype, String servicestation, LocalDate date, String notes) {
        this.clientid = clientid;
        this.servicetype = servicetype;
        this.servicestation = servicestation;
        this.date = date;
        this.notes = notes;
    }

    public int getClientid() {
        return clientid;
    }

    public void setClientid(int clientid) {
        this.clientid = clientid;
    }

    public String getServicetype() {
        return servicetype;
    }

    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }

    public String getServicestation() {
        return servicestation;
    }

    public void setServicestation(String servicestation) {
        this.servicestation = servicestation;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "clientid=" + clientid +
                ", servicetype='" + servicetype + '\'' +
                ", servicestation='" + servicestation + '\'' +
                ", date=" + date +
                ", notes='" + notes + '\'' +
                '}';
    }
}
