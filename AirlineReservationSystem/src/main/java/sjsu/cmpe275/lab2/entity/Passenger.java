package sjsu.cmpe275.lab2.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="PASSENGER")
public class Passenger {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "ID")
    private String id;

    @Column(name="FIRST_NAME")
    private String firstname;

    @Column(name="LAST_NAME")
    private String lastname;

    @Column(name="AGE")
    private int age;

    @Column(name="GENDER")
    private String gender;

    @Column(name="PHONE", unique=true)
    private String phone;


    // reservation made by the passenger should also be deleted.
    @OneToMany(mappedBy = "passenger",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Reservation> reservations;


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
}
