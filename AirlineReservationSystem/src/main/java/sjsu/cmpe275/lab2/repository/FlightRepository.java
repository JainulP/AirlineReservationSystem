package sjsu.cmpe275.lab2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sjsu.cmpe275.lab2.entity.FlightEntity;

public interface FlightRepository extends CrudRepository<FlightEntity,String> {
	@Query(value = "SELECT flight_num FROM passenger_flight WHERE id = ?1", nativeQuery = true)
	List<String> findByPassengerId(String passengerId);
}
