package ca.humber.finalproject;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Service {

    @Id
    private int id;
    private int vin;
    private String type;
    private LocalDate date;
    private double cost;
    private String status;
    private String name;
    private String station;

    public Service() {
    }

    public Service(int id, int vin, String type, LocalDate date, double cost, String status, String name, String station) {
        this.id = id;
        this.vin = vin;
        this.type = type;
        this.date = date;
        this.cost = cost;
        this.status = status;
        this.name = name;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", vin=" + vin +
                ", type='" + type + '\'' +
                ", date=" + date +
                ", cost=" + cost +
                ", status='" + status + '\'' +
                ", name='" + name + '\'' +
                ", station='" + station + '\'' +
                '}';
    }
}
