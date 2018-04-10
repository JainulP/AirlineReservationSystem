package sjsu.cmpe275.lab2.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Reservations {
	List<Reservation> reservations;

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

}
