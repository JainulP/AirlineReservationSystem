package sjsu.cmpe275.lab2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import sjsu.cmpe275.lab2.utils.View;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import java.util.List;

@XmlRootElement
@Entity
@Table(name = "FLIGHT")
public class FlightEntity {

    @Id
    @Column(name = "FLIGHT_NUMBER",unique = true)
    @JsonView({View.ReservationView.class,View.PassengerView.class,View.FlightView.class})
    private String flightNumber;

    @Column(name="PRICE")
    @JsonView({View.ReservationView.class,View.PassengerView.class,View.FlightView.class})
    private double price;

    @Column(name="ORIGIN")
    @JsonView({View.ReservationView.class,View.PassengerView.class,View.FlightView.class})
    private String origin;

    @Column(name="DESTINATION_TO")
    @JsonView({View.ReservationView.class,View.PassengerView.class,View.FlightView.class})
    private String destinationTo;

    @Column(name="DEPARTURE_TIME")
    @JsonView({View.ReservationView.class,View.PassengerView.class,View.FlightView.class})
    private String departureTime;

    @Column(name="ARRIVAL_TIME")
    @JsonView({View.ReservationView.class,View.PassengerView.class,View.FlightView.class})
    private String arrivalTime;

    @Column(name="SEATS_LEFT")
    @JsonView({View.ReservationView.class,View.PassengerView.class,View.FlightView.class})
    private int seatsLeft;

    @Column(name="DESCRIPTION")
    @JsonView({View.ReservationView.class,View.PassengerView.class,View.FlightView.class})
    private String description;

    @Embedded
    @JsonView({View.ReservationView.class,View.PassengerView.class,View.FlightView.class})
    private Plane plane;  // Embedded

   @JsonIgnore
    //@ManyToMany(mappedBy = "flights")
    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="RESERVATION_FLIGHT",
            joinColumns = {@JoinColumn(name="FLIGHT_NUM", referencedColumnName ="FLIGHT_NUMBER" )},
            inverseJoinColumns = {@JoinColumn(name="RESERVATION_NUM",referencedColumnName = "RESERVATION_NUMBER")})
    //@JsonView({View.ReservationView.class,View.PassengerView.class})
    private List<ReservationEntity> reservations;

    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="PASSENGER_FLIGHT",
            joinColumns = {@JoinColumn(name="FLIGHT_NUM", referencedColumnName ="FLIGHT_NUMBER")},
            inverseJoinColumns = {@JoinColumn(name="id", referencedColumnName ="id" )})
   // @ManyToMany(mappedBy = "flights")
    @JsonView(View.FlightView.class)
    private List<PassengerEntity> passengers;

    public FlightEntity() {
    }

    //public Flight(String flightNumber,double price, String origin, String destinationTo, String departureTime, String arrivalTime, int seatsLeft, String description, Plane plane) {
    public FlightEntity(String flightNumber,double price, String origin, String destinationTo, String departureTime, String arrivalTime, String description, Plane plane) {
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

//   @JsonIgnore
    @JsonView(View.FlightView.class)
    public List<PassengerEntity> getPassengers() {
        return passengers;
    }

    @JsonView(View.FlightView.class)
    public void setPassengers(List<PassengerEntity> passengers) {
        this.passengers = passengers;
    }
   @XmlTransient
    public List<ReservationEntity> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationEntity> reservations) {
        this.reservations = reservations;
    }
}
