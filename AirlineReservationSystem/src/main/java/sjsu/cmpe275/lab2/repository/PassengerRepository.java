package sjsu.cmpe275.lab2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.repository.query.Param;
import sjsu.cmpe275.lab2.entity.Passenger;

public interface PassengerRepository extends CrudRepository<Passenger, String> {
//    @Query(value = "SELECT first_name from passenger p,reservation r where passenger_id='1'",
//            nativeQuery = true)
//    Passenger getPassengerById(@Param("Id") String Id);
}
