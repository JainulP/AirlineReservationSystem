package sjsu.cmpe275.lab2.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonView;

import sjsu.cmpe275.lab2.entity.PassengerEntity;
import sjsu.cmpe275.lab2.utils.View;

@XmlRootElement
public class Passengers {

	@JsonView({ View.FlightView.class, View.PassengerView.class })
	List<PassengerEntity> passenger;

	public Passengers() {

	}

	public Passengers(List<PassengerEntity> passengers) {
		this.passenger = passengers;
	}

	public List<PassengerEntity> getPassenger() {
		return passenger;
	}

	public void setPassenger(List<PassengerEntity> passenger) {
		this.passenger = passenger;
	}

}
