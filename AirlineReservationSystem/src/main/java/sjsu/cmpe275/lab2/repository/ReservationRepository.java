package sjsu.cmpe275.lab2.repository;

import org.springframework.data.repository.CrudRepository;
import sjsu.cmpe275.lab2.entity.Reservation;

public interface ReservationRepository extends CrudRepository<Reservation,String> {
    //Reservation findByReservationNumber(String number);
}
