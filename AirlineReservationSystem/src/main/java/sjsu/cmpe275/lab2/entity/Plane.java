package sjsu.cmpe275.lab2.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Embeddable
public class Plane {

    @Column(name="CAPACITY")
    private int capacity;

    @Column(name="MODEL")
    private String model;

    @Column(name="MANUFACTURER")
    private String manufacturer;

    @Column(name="YEAR")
    private int year;

    public Plane() {
    }

    public Plane(int capacity, String model, String manufacturer, int year) {
        this.capacity = capacity;
        this.model = model;
        this.manufacturer = manufacturer;
        this.year = year;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
