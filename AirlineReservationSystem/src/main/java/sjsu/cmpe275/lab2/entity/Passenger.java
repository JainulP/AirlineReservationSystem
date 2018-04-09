package sjsu.cmpe275.lab2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.GenericGenerator;
import sjsu.cmpe275.lab2.utils.View;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement
@Entity
@Table(name = "PASSENGER")
public class Passenger {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "ID")
	@JsonView({View.ReservationView.class,View.PassengerView.class,View.FlightView.class})
	private String id;

	@Column(name = "FIRST_NAME")
	@JsonView({View.ReservationView.class,View.PassengerView.class,View.FlightView.class})
	private String firstname;

	@Column(name = "LAST_NAME")
	@JsonView({View.ReservationView.class,View.PassengerView.class,View.FlightView.class})
	private String lastname;

	@Column(name = "AGE")
	@JsonView({View.ReservationView.class,View.PassengerView.class,View.FlightView.class})
	private int age;

	@Column(name = "GENDER")
	@JsonView({View.ReservationView.class,View.PassengerView.class,View.FlightView.class})
	private String gender;

	@Column(name = "PHONE", unique = true)
	@JsonView({View.ReservationView.class,View.PassengerView.class,View.FlightView.class})
	private String phone;

	// reservation made by the passenger should also be deleted.
//	@JsonIgnore
	@JsonView(View.PassengerView.class)
	@OneToMany(mappedBy = "passenger", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Reservation> reservations;

	@JsonIgnore
	@ManyToMany(mappedBy = "passengers",cascade = CascadeType.ALL)
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

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public List<Flight> getFlights() {
		return flights;
	}

	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}
}
