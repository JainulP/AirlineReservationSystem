package sjsu.cmpe275.lab2.controller;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sjsu.cmpe275.lab2.entity.Flight;
import sjsu.cmpe275.lab2.entity.Reservation;
import sjsu.cmpe275.lab2.service.FlightService;
import sjsu.cmpe275.lab2.service.ReservationService;
import sjsu.cmpe275.lab2.utils.Utils;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    /*
	 * Return Reservation details in JSON format
	 */
    @RequestMapping(value = "/{number}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getReservationJson(@PathVariable("number") String reservationNumber) throws JSONException {
        Reservation reservation = reservationService.getReservation(reservationNumber);
        if (reservation == null)
            return new ResponseEntity<>(Utils.generateErrorResponse("BadRequest", 404, "Reservation with number " + reservationNumber + " does not exist"), HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(reservation, HttpStatus.OK);

    }

    /*
    * Return Reservation details in XML format
    */
    @RequestMapping(value = "/{number}", params = {"xml"}, method = RequestMethod.GET, produces = {MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getReservationXml(@PathVariable("number") String reservationNumber, @RequestParam(value = "xml") String isXml) {
        Reservation reservation = reservationService.getReservation(reservationNumber);
        if (reservation == null)
            return new ResponseEntity<>(Utils.generateErrorResponse("BadRequest", 404, "Reservation with number " + reservationNumber + " does not exist"), HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(reservation, HttpStatus.OK);

    }


}
