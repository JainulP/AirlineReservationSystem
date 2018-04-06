package sjsu.cmpe275.lab2.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="FLIGHT")
public class Flight {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "FLIGHT_NUMBER",unique = true)
    private String flightNumber;

    @Column(name="PRICE")
    private double price;

    @Column(name="ORIGIN")
    private String origin;

    @Column(name="DESTINATION_TO")
    private String destinationTo;

    @Column(name="DEPARTURE_TIME")
    private Date departureTime;

    @Column(name="ARRIVAL_TIME")
    private Date arrivalTime;

    @Column(name="SEATS_LEFT")
    private int seatsLeft;

    @Column(name="DESCRIPTION")
    private String description;

    @Embedded
    private Plane plane;  // Embedded

    @ManyToMany(mappedBy = "flights")
    private List<Reservation> reservations;

    @Transient
    private List<Passenger> passengers;

    public Flight() {
    }

    public Flight(double price, String origin, String destinationTo, Date departureTime, Date arrivalTime, int seatsLeft, String description, Plane plane) {
        this.price = price;
        this.origin = origin;
        this.destinationTo = destinationTo;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.seatsLeft = seatsLeft;
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

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
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

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
