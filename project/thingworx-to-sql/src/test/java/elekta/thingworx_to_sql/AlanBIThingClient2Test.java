package elekta.thingworx_to_sql;

import static org.junit.Assert.*;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;

import com.thingworx.communications.client.ClientConfigurator;

import elekta.thingworx_to_sql.bithing.AlanBIThingClient2;
import elekta.thingworx_to_sql.rest.RestClient;

public class AlanBIThingClient2Test {
	
	@Before
	public void setup() {
		BasicConfigurator.configure();
	}

	@Test
	public void test() throws Exception {
		ClientConfigurator config = new ClientConfigurator();
		AlanBIThingClient2 client = new AlanBIThingClient2(config);
		
		String randomStatusString = RandomStringUtils.randomAlphabetic(10);
		client.updateStatus(randomStatusString); //Now modify this to ONLY update status
		
		RestClient restClient = new RestClient("http://localhost:80/Thingworx", "69691649-caa5-41dd-a9ca-e1cdd565c1a0");
		
		String newStatus = restClient.getStringProperty("AlanBIThing01", "Status");
		System.out.println("Returned Status: " + newStatus);
		
		assertEquals(randomStatusString, newStatus);
	}
}