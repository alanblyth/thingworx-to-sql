package elekta.thingworx_to_sql.rest;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import recordtypes.Thing;
import recordtypes.ThingTemplate;
import recordtypes.User;

public class RestClient {
	
	public static final String PATH_THINGTEMPLATES = "/ThingTemplates";
	public static final String PATH_THINGS = "/Things";
	public static final String PATH_USERS = "/Users";

	public static String ROWS = "rows";
	private String hostname;
	private String apiKey;
	
    static Logger log = Logger.getLogger(RestClient.class);
	
	public RestClient(String hostname, String apiKey) {
		
		this.hostname = hostname;
		this.apiKey = apiKey;
	}
	
	public Map<String, Thing> getThings() throws ClientProtocolException, IOException {

		String url = hostname + PATH_THINGS;

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);

		setStandardThingWorxHeaders(request);
		HttpResponse response = client.execute(request);

		BufferedReader rd = new BufferedReader(
			new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		JSONObject responseJson = new JSONObject(result.toString());
		Map<String, Thing> things = new HashMap<>();
		
		System.out.println(result);
		
		JSONArray rows = responseJson.getJSONArray(ROWS);

		for (int i = 0; i < rows.length(); i++) {
				
				JSONObject obj = rows.getJSONObject(i);
				
				Thing newThing = new Thing();
				newThing.setAvatar(obj.getString(Thing.AVATAR));
				newThing.setDescription(obj.getString(Thing.DESCRIPTION));
				newThing.setHomeMashup(obj.getString(Thing.HOMEMASHUP));
				newThing.setSystemObject(obj.getBoolean(Thing.ISSYSTEMOBJECT));
				newThing.setName(obj.getString(Thing.NAME));
				things.put(newThing.getName(), newThing);
			}
		return things;
	}
	
	public Map<String, ThingTemplate> getThingTemplates() throws UnsupportedOperationException, IOException {
		
		String url = hostname + PATH_THINGTEMPLATES;

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);

		setStandardThingWorxHeaders(request);
		HttpResponse response = client.execute(request);

		BufferedReader rd = new BufferedReader(
			new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		JSONObject responseJson = new JSONObject(result.toString());
		Map<String, ThingTemplate> thingTemplates = new HashMap<>();
		
		System.out.println(result);
		
		JSONArray rows = responseJson.getJSONArray(ROWS);

		for (int i = 0; i < rows.length(); i++) {
				
				JSONObject obj = rows.getJSONObject(i);
				
				ThingTemplate newThingTemplate = new ThingTemplate();
				newThingTemplate.setAvatar(obj.getString(ThingTemplate.AVATAR));
				newThingTemplate.setDescription(obj.getString(ThingTemplate.DESCRIPTION));
				newThingTemplate.setHomeMashup(obj.getString(ThingTemplate.HOMEMASHUP));
				newThingTemplate.setSystemObject(obj.getBoolean(ThingTemplate.ISSYSTEMOBJECT));
				newThingTemplate.setName(obj.getString(ThingTemplate.NAME));
				thingTemplates.put(newThingTemplate.getName(), newThingTemplate);
			}
		return thingTemplates;
	}
	
	public Map<String, User> getUsers() throws ClientProtocolException, IOException {

		String url = hostname + PATH_USERS;

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);

		setStandardThingWorxHeaders(request);
		HttpResponse response = client.execute(request);

		BufferedReader rd = new BufferedReader(
			new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		JSONObject responseJson = new JSONObject(result.toString());
		Map<String, User> users = new HashMap<>();
		
		System.out.println(result);
		
		JSONArray rows = responseJson.getJSONArray(ROWS);

		for (int i = 0; i < rows.length(); i++) {
				
				JSONObject obj = rows.getJSONObject(i);
				
				User user = new User();
				user.setAvatar(obj.getString(Thing.AVATAR));
				user.setDescription(obj.getString(Thing.DESCRIPTION));
				user.setHomeMashup(obj.getString(Thing.HOMEMASHUP));
				user.setSystemObject(obj.getBoolean(Thing.ISSYSTEMOBJECT));
				user.setName(obj.getString(Thing.NAME));
				users.put(user.getName(), user);
			}
		return users;
	}

	private void setStandardThingWorxHeaders(HttpGet request) {
		request.addHeader("Content-Type", "application/json");
		request.addHeader("Accept", "application/json");
		request.addHeader("x-thingworx-session", "true");
		request.addHeader("appKey", apiKey);
	}
	
	public String getStringProperty(String thingName, String propertyName) throws UnsupportedOperationException, IOException {
		
		String url = getUrlToProperty(thingName, propertyName);

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);

		setStandardThingWorxHeaders(request);
		HttpResponse response = client.execute(request);

		BufferedReader rd = new BufferedReader(
			new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return getSingleValueFromResponse(result.toString(), propertyName);
	}

	private String getUrlToProperty(String thingName, String propertyName) {
		
		String url = hostname + "/Things/" + thingName + "/Properties/" + propertyName;
		return url;
	}
	
	private String getSingleValueFromResponse(String response, String propertyName) {
		
		JSONObject object = new JSONObject(response);
		return object.getJSONArray(ROWS).getJSONObject(0).getString(propertyName);
	}
}
