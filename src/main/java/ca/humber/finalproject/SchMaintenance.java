package ca.humber.finalproject;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class SchMaintenance {

    @Id
    private int id;
    private int vin;
    private String servicetype;
    private String technicianname;
    private LocalDate date;
    private String status;
    private String station;

    public SchMaintenance() {
    }

    public SchMaintenance(int id, int vin, String servicetype, String technicianname, LocalDate date, String status, String station) {
        this.id = id;
        this.vin = vin;
        this.servicetype = servicetype;
        this.technicianname = technicianname;
        this.date = date;
        this.status = status;
        this.station = station;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    @Override
    public String toString() {
        return "SchMaintenance{" +
                "id=" + id +
                ", vin=" + vin +
                ", servicetype='" + servicetype + '\'' +
                ", technicianname='" + technicianname + '\'' +
                ", date=" + date +
                ", status='" + status + '\'' +
                ", station='" + station + '\'' +
                '}';
    }
}
