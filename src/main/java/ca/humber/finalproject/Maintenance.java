package ca.humber.finalproject;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Maintenance {

    @Id
    private int vin;
    private String servicetype;
    private String technicianname;
    private double cost;
    private LocalDate date;

    public Maintenance () {
    }

    public Maintenance(int vin, String servicetype, String technicianname, double cost, LocalDate date) {
        this.vin = vin;
        this.servicetype = servicetype;
        this.technicianname = technicianname;
        this.cost = cost;
        this.date = date;
    }

    public int getVin() {
        return vin;
    }

    public void setVin(int vin) {
        this.vin = vin;
    }

    public String getServicetype() {
        return servicetype;
    }

    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }

    public String getTechnicianname() {
        return technicianname;
    }

    public void setTechnicianname(String technicianname) {
        this.technicianname = technicianname;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Maintenance{" +
                "vin=" + vin +
                ", servicetype='" + servicetype + '\'' +
                ", technicianname='" + technicianname + '\'' +
                ", cost=" + cost +
                ", date=" + date +
                '}';
    }
}
