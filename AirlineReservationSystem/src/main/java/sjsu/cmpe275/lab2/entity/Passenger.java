package sjsu.cmpe275.lab2.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.GenericGenerator;
import sjsu.cmpe275.lab2.utils.View;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import java.util.List;

@XmlRootElement
@Entity
@Table(name = "PASSENGER")
public class Passenger {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "p_id")
	@JsonView({ View.ReservationView.class, View.PassengerView.class, View.FlightView.class })
	private String p_id;

	@Column(name = "FIRST_NAME")
	@JsonView({ View.ReservationView.class, View.PassengerView.class, View.FlightView.class })
	private String firstname;

	@Column(name = "LAST_NAME")
	@JsonView({ View.ReservationView.class, View.PassengerView.class, View.FlightView.class })
	private String lastname;

	@Column(name = "AGE")
	@JsonView({ View.ReservationView.class, View.PassengerView.class, View.FlightView.class })
	private int age;

	@Column(name = "GENDER")
	@JsonView({ View.ReservationView.class, View.PassengerView.class, View.FlightView.class })
	private String gender;

	@Column(name = "PHONE", unique = true)
	@JsonView({ View.ReservationView.class, View.PassengerView.class, View.FlightView.class })
	private String phone;

	// reservation made by the passenger should also be deleted.
	@JsonView(View.PassengerView.class)
	@OneToMany(mappedBy = "passenger", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	private List<Reservation> reservations;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "PASSENGER_FLIGHT", joinColumns = {
			@JoinColumn(name = "p_id", referencedColumnName = "p_id") }, inverseJoinColumns = {
					@JoinColumn(name = "FLIGHT_NUM", referencedColumnName = "FLIGHT_NUMBER") })
	private List<Flight> flights;

	public Passenger() {
	}

	public Passenger(String firstname, String lastname, int age, String gender, String phone) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
		this.gender = gender;
		this.phone = phone;
	}

	public String getP_id() {
		return p_id;
	}

	public void setP_id(String p_id) {
		this.p_id = p_id;
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

	@XmlTransient
	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	@XmlTransient
	public List<Flight> getFlights() {
		return flights;
	}

	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}
}
