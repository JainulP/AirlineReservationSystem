package sjsu.cmpe275.lab2.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sjsu.cmpe275.lab2.entity.Flight;
import sjsu.cmpe275.lab2.entity.Passenger;
import sjsu.cmpe275.lab2.entity.Plane;
import sjsu.cmpe275.lab2.service.FlightService;
import sjsu.cmpe275.lab2.utils.Utils;

@RestController
public class FlightController {

    @Autowired
    private FlightService flightService;

    /*
	 * Return flight details in JSON format
	 */
    @RequestMapping(value = "/flight/{flightNumber}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getFlightJson(@PathVariable("flightNumber") String flightNumber) throws JSONException {
        Flight flight = flightService.getFlight(flightNumber);
        if (flight == null)
            return new ResponseEntity<>(Utils.generateErrorResponse("BadRequest", 404, "Sorry, the requested flight with number " + flightNumber + " does not exist"), HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(flight, HttpStatus.OK);

    }

    /*
	 * Return flight details in XML format
	 */
    @RequestMapping(value = "/flight/{flightNumber}", params = {"xml"}, method = RequestMethod.GET, produces = {MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getFlightXml(@PathVariable("flightNumber") String flightNumber, @RequestParam(value = "xml") String isXml) throws JSONException {
        Flight flight = flightService.getFlight(flightNumber);
        if (flight == null)
            return new ResponseEntity<>(Utils.generateErrorResponse("BadRequest", 404, "Sorry, the requested flight with number " + flightNumber + " does not exist"), HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(flight, HttpStatus.OK);

    }

    @RequestMapping(value = "/flight/{flightNumber}", method = RequestMethod.POST, produces = {MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> createOrUpdateFlight(@PathVariable("flightNumber") String flightNumber,
                                                  @RequestParam(value = "price") double price,
                                                  @RequestParam(value = "origin") String origin,
                                                  @RequestParam(value = "to") String destinationTo,
                                                  @RequestParam(value = "departureTime") String departureTime,
                                                  @RequestParam(value = "arrivalTime") String arrivalTime,
                                                  @RequestParam(value = "description") String description,
                                                  @RequestParam(value = "capacity") int capacity,
                                                  @RequestParam(value = "model") String model,
                                                  @RequestParam(value = "manufacturer") String manufacturer,
                                                  @RequestParam(value = "year") int year) throws JSONException {

        if (capacity < 0) {
            return new ResponseEntity<>(Utils.generateErrorResponse("BadRequest", 400, "Capacity can not be negative!"), HttpStatus.BAD_REQUEST);
        }
        if (arrivalTime.equals(departureTime)) {
            return new ResponseEntity<>(Utils.generateErrorResponse("BadRequest", 400, "Arrival and Departure Time can't be same!"), HttpStatus.BAD_REQUEST);
        }
        Flight flight = flightService.getFlight(flightNumber);
        if(flight == null){
           return new ResponseEntity<>(flightService.createOrUpdate(new Flight(flightNumber,price,origin,destinationTo,departureTime,arrivalTime,description,new Plane(capacity,model,manufacturer,year))),HttpStatus.OK);
        }
        else{
            if (flight.getPlane().getCapacity() < flight.getPassengers().size()) {
                return new ResponseEntity<>(Utils.generateErrorResponse("BadRequest", 400, "New capacity can't be less than " +
                        "the reservations for that flight!"), HttpStatus.BAD_REQUEST);
            }
            else {
                return new ResponseEntity<>(flightService.createOrUpdate(new Flight(flightNumber, price, origin, destinationTo, departureTime, arrivalTime, description, new Plane(capacity, model, manufacturer, year))), HttpStatus.OK);
            }
        }
    }

    @RequestMapping(value = "/airline/{flightNumber}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> deleteFlight(@PathVariable(value="flightNumber") String flightNumber) throws JSONException {
        Flight flight = flightService.getFlight(flightNumber);
        if(flight == null){
            return new ResponseEntity<>(Utils.generateErrorResponse("BadRequest", 404, "Sorry, the requested flight with number " + flightNumber + " does not exist"), HttpStatus.NOT_FOUND);
        }
        else
        {
            if(flight.getPassengers().size()>0){
                return new ResponseEntity<>(Utils.generateErrorResponse("BadRequest", 400, "Sorry, the requested flight with number " + flightNumber + " could not be deleted as it has one or more reservations"), HttpStatus.BAD_REQUEST);

            }
            else{
                flightService.deleteFlight(flightNumber);
                JSONObject response = new JSONObject();
                response.put("code",200);
                response.put("msg","Flight with number " + flightNumber + " is deleted successfully");
                return new ResponseEntity<>(response, HttpStatus.OK);

            }
        }

    }


}
