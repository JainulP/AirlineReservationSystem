package sjsu.cmpe275.lab2;

import org.json.JSONObject;
import org.junit.Test;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import static org.junit.Assert.assertEquals;

public class TestAirlineReservationSystem {
	@Test
	public void getFlightSuccess() {

		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.get("http://localhost:8080/flight/EE200")
					.header("accept", "application/json").asJson();

			JSONObject res = jsonResponse.getBody().getObject();
			String flightNumber = res.getString("flightNumber");
			String price = res.getString("price");
			String origin = res.getString("origin");
			String destinationTo = res.getString("destinationTo");
			String departureTime = res.getString("departureTime");
			String arrivalTime = res.getString("arrivalTime");
			String seatsLeft = res.getString("seatsLeft");
			String description = res.getString("description");
			assertEquals("EE200", flightNumber);
			assertEquals("120.0", price);
			assertEquals("BB", destinationTo);
			assertEquals("2018-06-06-20", departureTime);
			assertEquals("2018-06-06-22", arrivalTime);
			assertEquals("0", seatsLeft);
			assertEquals("AA", origin);
			assertEquals("EE", description);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void getFlightFailure() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.get("http://localhost:8080/flight/123")
					.header("accept", "application/json").asJson();

			JSONObject res = jsonResponse.getBody().getObject();
			JSONObject res1 = res.getJSONObject("BadRequest");
			System.out.println(res);
			String msg = res1.getString("msg");
			String code = res1.getString("code");

			assertEquals("Sorry, the requested flight with number 123 does not exist", msg);
			assertEquals("404", code);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void getPassengerSuccess() {

		try {
			HttpResponse<JsonNode> jsonResponse = Unirest
					.get("http://localhost:8080/passenger/7add0195-e733-41c0-870d-cc8183fb3071")
					.header("accept", "application/json").asJson();

			JSONObject res = jsonResponse.getBody().getObject();
			String id = res.getString("id");
			String firstname = res.getString("firstname");
			String lastname = res.getString("lastname");
			String age = res.getString("age");
			String gender = res.getString("gender");
			String phone = res.getString("phone");
			assertEquals("7add0195-e733-41c0-870d-cc8183fb3071", id);
			assertEquals("sansa", firstname);
			assertEquals("stark", lastname);
			assertEquals("14", age);
			assertEquals("female", gender);
			assertEquals("189099967990", phone);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void getPassengerFailure() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.get("http://localhost:8080/passenger/123")
					.header("accept", "application/json").asJson();

			JSONObject res = jsonResponse.getBody().getObject();
			JSONObject res1 = res.getJSONObject("BadRequest");
			System.out.println(res);
			String msg = res1.getString("msg");
			String code = res1.getString("code");

			assertEquals("Sorry, the requested passenger with id 123 does not exist", msg);
			assertEquals("404", code);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void getReservationFailure() {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest
					.get("http://localhost:8080/reservation/d9fcbcc0-45f4-4680-98427-08b718cf0a12")
					.header("accept", "application/json").asJson();

			JSONObject res = jsonResponse.getBody().getObject();
			JSONObject res1 = res.getJSONObject("BadRequest");
			System.out.println(res);
			String msg = res1.getString("msg");
			String code = res1.getString("code");

			assertEquals("Reservation with number d9fcbcc0-45f4-4680-98427-08b718cf0a12 does not exist", msg);
			assertEquals("404", code);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
