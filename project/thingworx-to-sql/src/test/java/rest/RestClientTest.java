package rest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

import elekta.thingworx_to_sql.rest.RestClient;
import recordtypes.Thing;
import recordtypes.ThingTemplate;
import recordtypes.User;

public class RestClientTest {

	private static final String EXAMPLETHING_MASHUP = "mashup";
	private static final String EXAMPLETHING_DESCRIPTION = "desc1";
	private static final String EXAMPLETHING_AVATAR = "/Thingworx/Things/AlanBIThing01/Avatar";
	private static final String EXAMPLETHING_NAME = "AlanBIThing01";
	
	private static final String EXAMPLEUSER_MASHUP = "mashup";
	private static final String EXAMPLEUSER_DESCRIPTION = "desc1";
	private static final String EXAMPLEUSER_AVATAR = "/Thingworx/Things/AlanBIThing01/Avatar";
	private static final String EXAMPLEUSER_NAME = "AlanBIThing01";
	
	private static final String APPKEY = "69691649-caa5-41dd-a9ca-e1cdd565c1a0";

	RestClient restClient;
	static WireMockServer wireMockServer;
	
	@Rule
	public WireMockRule wireMockRule = new WireMockRule(8089); // No-args constructor defaults to port 8080
	
	@BeforeClass
	public static void setUpClass() {
		
		BasicConfigurator.configure();
		wireMockServer = new WireMockServer();
		wireMockServer.start();
	}
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void getThingTemplatesFromLocalHost() throws UnsupportedOperationException, IOException {
		
		restClient = new RestClient("http://localhost:80/Thingworx", APPKEY);
		Map<String, ThingTemplate> templates = restClient.getThingTemplates();
		System.out.println(templates);
	}
	
	@Test
	public void getThingsFromLocalHost() throws UnsupportedOperationException, IOException {
		
		restClient = new RestClient("http://localhost:80/Thingworx", APPKEY);
		Map<String, Thing> things = restClient.getThings();
		System.out.println(things);
	}
	
	@Test
	public void getUsersFromLocalHost() throws UnsupportedOperationException, IOException {
		
		restClient = new RestClient("http://localhost:80/Thingworx", APPKEY);
		Map<String, User> users = restClient.getUsers();
		System.out.println(users);
	}
	
	@Test
	public void getThingsFromWireMock() throws UnsupportedOperationException, IOException {
		
		addStubForGetThings();
		
		restClient = new RestClient("http://localhost:8089", APPKEY);
		Map<String, Thing> things = restClient.getThings();
		
	    verify(getRequestedFor(urlMatching(RestClient.PATH_THINGS))
	            .withHeader("Content-Type", matching("application/json"))
	    		.withHeader("Accept", matching("application/json"))
	    		.withHeader("x-thingworx-session", matching("true"))
	    		.withHeader("appKey", matching(APPKEY))
	            );
	    
	    assertEquals(1, things.size());
	    
	    Thing responseThing = things.get(EXAMPLETHING_NAME);
	    assertEquals(EXAMPLETHING_AVATAR, responseThing.getAvatar());
	    assertEquals(EXAMPLETHING_DESCRIPTION, responseThing.getDescription());
	    assertEquals(EXAMPLETHING_MASHUP, responseThing.getHomeMashup());
	    assertEquals(EXAMPLETHING_NAME, responseThing.getName());
	}

	private void addStubForGetThings() {
		stubFor(get(urlEqualTo(RestClient.PATH_THINGS))
				.willReturn(aResponse()
						.withBody(getThingRequestBody())
				        .withStatus(200)
				        .withHeader("Content-Type", "application/json;charset=UTF-8")));
	}

	@Test
	public void getThingTemplatesFromWireMock() throws UnsupportedOperationException, IOException {
		
		addStubForGetThingTemplates();
		
		restClient = new RestClient("http://localhost:8089", APPKEY);
		Map<String, ThingTemplate> templates = restClient.getThingTemplates();
		
	    verify(getRequestedFor(urlMatching(RestClient.PATH_THINGTEMPLATES))
	            .withHeader("Content-Type", matching("application/json"))
	    		.withHeader("Accept", matching("application/json"))
	    		.withHeader("x-thingworx-session", matching("true"))
	    		.withHeader("appKey", matching(APPKEY))
	            );
	    
	    assertEquals(1, templates.size());
	    
	    ThingTemplate responseThing = templates.get(EXAMPLETHING_NAME);
	    assertEquals(EXAMPLETHING_AVATAR, responseThing.getAvatar());
	    assertEquals(EXAMPLETHING_DESCRIPTION, responseThing.getDescription());
	    assertEquals(EXAMPLETHING_MASHUP, responseThing.getHomeMashup());
	    assertEquals(EXAMPLETHING_NAME, responseThing.getName());
	}
	
	private void addStubForGetThingTemplates() {
		stubFor(get(urlEqualTo(RestClient.PATH_THINGTEMPLATES))
				.willReturn(aResponse()
						.withBody(getUserRequestBody())
				        .withStatus(200)
				        .withHeader("Content-Type", "application/json;charset=UTF-8")));
	}

	@Test
	public void getUsersFromWireMock() throws UnsupportedOperationException, IOException {
		
		addStubForGetUsers();
		
		restClient = new RestClient("http://localhost:8089", APPKEY);
		Map<String, User> users = restClient.getUsers();
		
	    verify(getRequestedFor(urlMatching(RestClient.PATH_USERS))
	            .withHeader("Content-Type", matching("application/json"))
	    		.withHeader("Accept", matching("application/json"))
	    		.withHeader("x-thingworx-session", matching("true"))
	    		.withHeader("appKey", matching(APPKEY))
	            );
	    
	    assertEquals(1, users.size());

	    User responseThing = users.get(EXAMPLETHING_NAME);
	    assertEquals(EXAMPLEUSER_AVATAR, responseThing.getAvatar());
	    assertEquals(EXAMPLEUSER_DESCRIPTION, responseThing.getDescription());
	    assertEquals(EXAMPLEUSER_MASHUP, responseThing.getHomeMashup());
	    assertEquals(EXAMPLEUSER_NAME, responseThing.getName());
	}

	private void addStubForGetUsers() {
		stubFor(get(urlEqualTo(RestClient.PATH_USERS))
				.willReturn(aResponse()
						.withBody(getThingTemplateRequestBody())
				        .withStatus(200)
				        .withHeader("Content-Type", "application/json;charset=UTF-8")));
	}
	
	String getThingTemplateRequestBody() {
		
		JSONArray array = new JSONArray();
		JSONObject thingTemplate = new JSONObject();
		thingTemplate.put(ThingTemplate.AVATAR, EXAMPLETHING_AVATAR);
		thingTemplate.put(ThingTemplate.DESCRIPTION, EXAMPLETHING_DESCRIPTION);
		
		thingTemplate.put(ThingTemplate.HOMEMASHUP, EXAMPLETHING_MASHUP);
		thingTemplate.put(ThingTemplate.ISSYSTEMOBJECT, false);
		thingTemplate.put(ThingTemplate.NAME, EXAMPLETHING_NAME);
		thingTemplate.put(ThingTemplate.TAGS, new JSONArray());
		
		array.put(thingTemplate);
		
		return new JSONObject().put(RestClient.ROWS, array).toString();
	}
	
	String getThingRequestBody() {
		
		JSONArray array = new JSONArray();
		JSONObject thing = new JSONObject();
		thing.put(Thing.AVATAR, EXAMPLETHING_AVATAR);
		thing.put(Thing.DESCRIPTION, EXAMPLETHING_DESCRIPTION);
		
		thing.put(Thing.HOMEMASHUP, EXAMPLETHING_MASHUP);
		thing.put(Thing.ISSYSTEMOBJECT, false);
		thing.put(Thing.NAME, EXAMPLETHING_NAME);
		thing.put(Thing.TAGS, new JSONArray());
		
		array.put(thing);
		
		return new JSONObject().put(RestClient.ROWS, array).toString();
	}
	
	String getUserRequestBody() {
		
		JSONArray array = new JSONArray();
		JSONObject user = new JSONObject();
		user.put(User.AVATAR, EXAMPLEUSER_AVATAR);
		user.put(User.DESCRIPTION, EXAMPLEUSER_DESCRIPTION);
		
		user.put(User.HOMEMASHUP, EXAMPLEUSER_MASHUP);
		user.put(User.ISSYSTEMOBJECT, false);
		user.put(User.NAME, EXAMPLEUSER_NAME);
		user.put(User.TAGS, new JSONArray());
		
		array.put(user);
		
		return new JSONObject().put(RestClient.ROWS, array).toString();
	}
}
