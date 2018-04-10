package sjsu.cmpe275.lab2.dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonView;

import sjsu.cmpe275.lab2.entity.FlightEntity;
import sjsu.cmpe275.lab2.entity.PassengerEntity;
import sjsu.cmpe275.lab2.utils.View;

@XmlRootElement
public class Reservation {

	@JsonView({ View.ReservationView.class, View.PassengerView.class })
	private String reservationNumber;

	@JsonView({ View.ReservationView.class })
	private PassengerEntity passenger;

	@JsonView({ View.ReservationView.class, View.PassengerView.class })
	private double price;// sum of each flight's price

	@JsonView({ View.ReservationView.class, View.PassengerView.class })
	private List<FlightEntity> flights;
	public Reservation() {
		
	}
	public Reservation(String reservationNumber, PassengerEntity passenger, double price, List<FlightEntity> flights) {
		super();
		this.reservationNumber = reservationNumber;
		this.passenger = passenger;
		this.price = price;
		for (int i = 0; i < flights.size(); i++) {
			flights.get(i).setPassengers(null);
		}
		this.flights = flights;
	}

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

	public List<FlightEntity> getFlights() {
		return flights;
	}

	public void setFlights(List<FlightEntity> flights) {
		this.flights = flights;
	}

}
