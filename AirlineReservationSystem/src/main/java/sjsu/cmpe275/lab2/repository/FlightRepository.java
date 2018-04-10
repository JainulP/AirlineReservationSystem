package sjsu.cmpe275.lab2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sjsu.cmpe275.lab2.entity.Flight;

public interface FlightRepository extends CrudRepository<Flight,String> {
	@Query(value = "SELECT flight_num FROM passenger_flight WHERE p_id = ?1", nativeQuery = true)
	List<String> findByPassengerId(String passengerId);
}
