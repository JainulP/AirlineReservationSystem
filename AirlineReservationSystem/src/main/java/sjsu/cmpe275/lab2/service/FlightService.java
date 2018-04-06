package sjsu.cmpe275.lab2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sjsu.cmpe275.lab2.entity.Flight;
import sjsu.cmpe275.lab2.repository.FlightRepository;

import java.util.Optional;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    public Flight getFlight(String flightNumber){
            Optional<Flight> flight = flightRepository.findById(flightNumber);
            if(flight.isPresent())
                return flightRepository.findById(flightNumber).get();
            else
                return null;
    }

}
