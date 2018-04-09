package sjsu.cmpe275.lab2.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.GenericGenerator;
import sjsu.cmpe275.lab2.utils.View;

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
    @JsonView({View.ReservationView.class,View.PassengerView.class})
    private String reservationNumber;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="p_id")
    @JsonView({View.ReservationView.class})
    private Passenger passenger;

    @Column(name="PRICE")
    @JsonView({View.ReservationView.class,View.PassengerView.class})
    private double price;//sum of each flight's price

    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="RESERVATION_FLIGHT",
    joinColumns = {@JoinColumn(name="RESERVATION_NUM",referencedColumnName = "RESERVATION_NUMBER")},
    inverseJoinColumns = {@JoinColumn(name="FLIGHT_NUM", referencedColumnName ="FLIGHT_NUMBER" )})
    @JsonView({View.ReservationView.class})
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
