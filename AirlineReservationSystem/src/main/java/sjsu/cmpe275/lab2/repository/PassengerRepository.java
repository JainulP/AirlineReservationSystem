package sjsu.cmpe275.lab2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.repository.query.Param;
import sjsu.cmpe275.lab2.entity.PassengerEntity;

import java.util.List;

public interface PassengerRepository extends CrudRepository<PassengerEntity, String> {
	// list of passengers with the specified phone number
	@Query(value = "SELECT * FROM passenger WHERE phone = ?1", nativeQuery = true)
	PassengerEntity findByPhone(String phone);
}
