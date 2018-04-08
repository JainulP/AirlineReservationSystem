package sjsu.cmpe275.lab2.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement
@Entity
@Table(name="RESERVATION")
public class Reservation {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "RESERVATION_NUMBER")
    private String reservationNumber;

    @ManyToOne
    @JoinColumn(name="PASSENGER_ID")
    private Passenger passenger;

    @Column(name="PRICE")
    private double price;//sum of each flight's price

    @ManyToMany
    @JoinTable(name="RESERVATION_FLIGHT",
    joinColumns = {@JoinColumn(name="RESERVATION_NUM",referencedColumnName = "RESERVATION_NUMBER")},
    inverseJoinColumns = {@JoinColumn(name="FLIGHT_NUM", referencedColumnName ="FLIGHT_NUMBER" )})
    private List<Flight> flights;

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }
}
