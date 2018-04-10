package sjsu.cmpe275.lab2.dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonView;

import sjsu.cmpe275.lab2.entity.Flight;
import sjsu.cmpe275.lab2.entity.ReservationEntity;
import sjsu.cmpe275.lab2.utils.View;

@XmlRootElement
public class Passenger {
	@JsonView({ View.ReservationView.class, View.PassengerView.class, View.FlightView.class })
	private String id;
	@JsonView({ View.ReservationView.class, View.PassengerView.class, View.FlightView.class })
	private String firstname;
	@JsonView({ View.ReservationView.class, View.PassengerView.class, View.FlightView.class })
	private String lastname;
	@JsonView({ View.ReservationView.class, View.PassengerView.class, View.FlightView.class })
	private int age;
	@JsonView({ View.ReservationView.class, View.PassengerView.class, View.FlightView.class })
	private String gender;
	@JsonView({ View.ReservationView.class, View.PassengerView.class, View.FlightView.class })
	private String phone;
	@JsonView(View.PassengerView.class)
	private List<ReservationEntity> reservations;

	public Passenger() {

	}

	public Passenger(String id, String firstname, String lastname, int age, String gender, String phone,
			List<ReservationEntity> reservations) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
		this.gender = gender;
		this.phone = phone;
		for (int i = 0; i < reservations.size(); i++) {
			reservations.get(i).setPassenger(null);
			List<Flight> flights = reservations.get(i).getFlights();
			for (int j = 0; j < flights.size(); j++) {
				flights.get(j).setPassengers(null);
			}
		}
		this.reservations = reservations;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<ReservationEntity> getReservations() {
		return reservations;
	}

	public void setReservations(List<ReservationEntity> reservations) {
		this.reservations = reservations;
	}

}
