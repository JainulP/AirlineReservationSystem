package sjsu.cmpe275.lab2.entity;

import java.util.List;


import javax.xml.bind.annotation.XmlRootElement;

import sjsu.cmpe275.lab2.dto.Reservation;

@XmlRootElement
public class Reservations {
	List<Reservation> reservation;

	public List<Reservation> getReservations() {
		return reservation;
	}

	public void setReservations(List<Reservation> reservation) {
		this.reservation = reservation;
	}

}
