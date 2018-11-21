package elekta.thingworx_to_sql.rest;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import elekta.thingworx_to_sql.bithing.AlanBIThing;

public class RestClient {
	
	private String hostname;
	private String apiKey;
	
    static Logger log = Logger.getLogger(RestClient.class);
	
	public RestClient(String hostname, String apiKey) {
		
		this.hostname = hostname;
		this.apiKey = apiKey;
	}
	
	public String getStringProperty(String thingName, String propertyName) throws UnsupportedOperationException, IOException {
		
		String url = hostname + "/Things/" + thingName + "/Properties/" + propertyName;

		log.info("Url: " + url);
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);

		// add request header
		request.addHeader("Content-Type", "application/json");
		request.addHeader("Accept", "application/json");
		request.addHeader("x-thingworx-session", "true");
		request.addHeader("appKey", apiKey);
		HttpResponse response = client.execute(request);

		System.out.println("Response Code : " 
	                + response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(
			new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		
		return getSingleValueFromResponse(result.toString(), propertyName);
	}
	
	private String getSingleValueFromResponse(String response, String propertyName) {
		
		JSONObject object = new JSONObject(response);
		return object.getJSONArray("rows").getJSONObject(0).getString(propertyName);
	}
}
