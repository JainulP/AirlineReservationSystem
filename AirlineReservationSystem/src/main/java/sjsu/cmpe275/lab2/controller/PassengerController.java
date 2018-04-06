package sjsu.cmpe275.lab2.controller;

import javax.websocket.server.PathParam;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sjsu.cmpe275.lab2.model.Greeting;

@RestController
@RequestMapping("/passenger/{id}")
public class PassengerController {
	@SuppressWarnings("null")
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getPassengerJson(@PathVariable("id") Long id) throws JSONException {
		JSONObject temp = new JSONObject();
		System.out.println(id);
		temp.put("value", id);
		return new ResponseEntity(temp.toString(), HttpStatus.CREATED);
	}
}