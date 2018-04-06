package sjsu.cmpe275.lab2.controller;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sjsu.cmpe275.lab2.entity.Flight;
import sjsu.cmpe275.lab2.service.FlightService;
import sjsu.cmpe275.lab2.utils.Utils;

@RestController
@RequestMapping("/flight")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @RequestMapping(value="/{flightNumber}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getFlightJson(@PathVariable("flightNumber") String flightNumber) throws JSONException {
        Flight flight = flightService.getFlight(flightNumber);
        if(flight == null)
            return new ResponseEntity<Object>(Utils.generateErrorResponse("BadRequest",404,"Sorry, the requested flight with number " + flightNumber + " does not exist"),HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Object>(flight,HttpStatus.OK);

    }

}
