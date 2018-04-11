package sjsu.cmpe275.lab2.utils;

import org.json.JSONObject;

public class Utils {

    /*
	 * Generates the JSON for any error geneated or any Bad Request
	 */
    public static String generateErrorResponse(String header, int code, String message){
        JSONObject response = new JSONObject();
        JSONObject error = new JSONObject();

        try{
            response.put(header, error);
            error.put("code", code);
            error.put("msg", message);
        }catch(Exception e){
            System.out.println("generateErrorResponse() catch");
        }

        return response.toString();
    }
}
