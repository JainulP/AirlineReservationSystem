package sjsu.cmpe275.lab2.dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import sjsu.cmpe275.lab2.entity.PassengerEntity;
import sjsu.cmpe275.lab2.entity.Plane;
import sjsu.cmpe275.lab2.entity.ReservationEntity;
import sjsu.cmpe275.lab2.utils.View;
@XmlRootElement
public class Flight {

	@JsonView({ View.ReservationView.class, View.PassengerView.class, View.FlightView.class })
	private String flightNumber;

	@JsonView({ View.ReservationView.class, View.PassengerView.class, View.FlightView.class })
	private double price;

	@JsonView({ View.ReservationView.class, View.PassengerView.class, View.FlightView.class })
	private String origin;

	@JsonView({ View.ReservationView.class, View.PassengerView.class, View.FlightView.class })
	private String destinationTo;

	@JsonView({ View.ReservationView.class, View.PassengerView.class, View.FlightView.class })
	private String departureTime;

	@JsonView({ View.ReservationView.class, View.PassengerView.class, View.FlightView.class })
	private String arrivalTime;

	@JsonView({ View.ReservationView.class, View.PassengerView.class, View.FlightView.class })
	private int seatsLeft;

	@JsonView({ View.ReservationView.class, View.PassengerView.class, View.FlightView.class })
	private String description;

	@JsonView({ View.ReservationView.class, View.PassengerView.class, View.FlightView.class })
	private Plane plane; // Embedded

	@JsonView(View.FlightView.class)
	private Passengers passengers;

	public Flight() {

	}

	public Flight(String flightNumber, double price, String origin, String destinationTo, String departureTime,
			String arrivalTime, int seatsLeft, String description, Plane plane, List<PassengerEntity> passengers) {
		super();
		this.flightNumber = flightNumber;
		this.price = price;
		this.origin = origin;
		this.destinationTo = destinationTo;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.seatsLeft = seatsLeft;
		this.description = description;
		this.plane = plane;
		Passengers passengersTemp = new Passengers(passengers);
		this.passengers = passengersTemp;
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

	public Passengers getPassengers() {
		return passengers;
	}

	public void setPassengers(Passengers passengers) {
		this.passengers = passengers;
	}

	
}
