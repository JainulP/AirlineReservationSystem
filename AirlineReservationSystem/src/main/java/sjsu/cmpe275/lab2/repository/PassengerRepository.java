package sjsu.cmpe275.lab2.repository;

import org.springframework.data.repository.CrudRepository;
import sjsu.cmpe275.lab2.entity.Passenger;

import java.util.List;

public interface PassengerRepository extends CrudRepository<Passenger,String>{


}
