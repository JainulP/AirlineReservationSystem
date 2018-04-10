package sjsu.cmpe275.lab2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.repository.query.Param;
import sjsu.cmpe275.lab2.entity.PassengerEntity;

public interface PassengerRepository extends CrudRepository<PassengerEntity, String> {
}
