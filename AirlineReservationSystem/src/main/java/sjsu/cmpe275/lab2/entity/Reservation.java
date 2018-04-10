package sjsu.cmpe275.lab2.entity;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.repository.Query;
import sjsu.cmpe275.lab2.utils.View;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement
@Entity
@Table(name = "RESERVATION")
//@NamedNativeQuery(name= "Reservation.searchForReservation" ,query = "SELECT DISTINCT r.reservation_number FROM PASSENGER p, RESERVATION r, RESERVATION_FLIGHT rf, FLIGHT f,PASSENGER_FLIGHT pf " +
//        "WHERE p.id = :passengerId AND f.flight_number = :flightNumber  AND p.id = pf.id AND pf.flight_num = f.flight_number AND rf.flight_num = f.flight_number " +
//        "AND f.origin = COALESCE(:origin,f.origin) and f.destination_to = COALESCE(:destination,f.destination_to)",
//        resultClass = Reservation.class)
public class Reservation {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "RESERVATION_NUMBER")
    @JsonView({View.ReservationView.class,View.PassengerView.class})
    private String reservationNumber;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="id")
    @JsonView({View.ReservationView.class})
    private PassengerEntity passenger;

    @Column(name="PRICE")
    @JsonView({View.ReservationView.class,View.PassengerView.class})
    private double price;//sum of each flight's price

    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="RESERVATION_FLIGHT",
    joinColumns = {@JoinColumn(name="RESERVATION_NUM",referencedColumnName = "RESERVATION_NUMBER")},
    inverseJoinColumns = {@JoinColumn(name="FLIGHT_NUM", referencedColumnName ="FLIGHT_NUMBER" )})
    @JsonView({View.ReservationView.class,View.PassengerView.class})
    private List<Flight> flights;

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public PassengerEntity getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerEntity passenger) {
        this.passenger = passenger;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @JsonView({View.PassengerView.class,View.ReservationView.class})
    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }
}
