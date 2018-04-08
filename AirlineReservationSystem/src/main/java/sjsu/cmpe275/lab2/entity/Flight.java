package sjsu.cmpe275.lab2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.http.ResponseEntity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

@XmlRootElement
@Entity
@Table(name="FLIGHT")
public class Flight {

    @Id
    @Column(name = "FLIGHT_NUMBER",unique = true)
    private String flightNumber;

    @Column(name="PRICE")
    private double price;

    @Column(name="ORIGIN")
    private String origin;

    @Column(name="DESTINATION_TO")
    private String destinationTo;

    @Column(name="DEPARTURE_TIME")
    private String departureTime;

    @Column(name="ARRIVAL_TIME")
    private String arrivalTime;

    @Column(name="SEATS_LEFT")
    private int seatsLeft;

    @Column(name="DESCRIPTION")
    private String description;

    @Embedded
    private Plane plane;  // Embedded

    @JsonIgnore
    @ManyToMany(mappedBy = "flights")
    private List<Reservation> reservations;

    @ManyToMany
    @JoinTable(name="PASSENGER_FLIGHT",
            joinColumns = {@JoinColumn(name="FLIGHT_NUM", referencedColumnName ="FLIGHT_NUMBER")},
            inverseJoinColumns = {@JoinColumn(name="PASSENGER_ID", referencedColumnName ="ID" )})
    private List<Passenger> passengers;

    public Flight() {
    }

    //public Flight(String flightNumber,double price, String origin, String destinationTo, String departureTime, String arrivalTime, int seatsLeft, String description, Plane plane) {
    public Flight(String flightNumber,double price, String origin, String destinationTo, String departureTime, String arrivalTime, String description, Plane plane) {
        this.flightNumber = flightNumber;
        this.price = price;
        this.origin = origin;
        this.destinationTo = destinationTo;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        //this.seatsLeft = seatsLeft;
        this.description = description;
        this.plane = plane;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestinationTo() {
        return destinationTo;
    }

    public void setDestinationTo(String destinationTo) {
        this.destinationTo = destinationTo;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getSeatsLeft() {
        return seatsLeft;
    }

    public void setSeatsLeft(int seatsLeft) {
        this.seatsLeft = seatsLeft;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

//    public List<Reservation> getReservations() {
//        return reservations;
//    }
//
//    public void setReservations(List<Reservation> reservations) {
//        this.reservations = reservations;
//    }
}
