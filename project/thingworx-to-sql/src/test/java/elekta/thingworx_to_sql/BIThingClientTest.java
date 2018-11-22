package elekta.thingworx_to_sql;

import static org.junit.Assert.*;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;

import com.thingworx.communications.client.ClientConfigurator;
import com.thingworx.communications.common.SecurityClaims;

import elekta.thingworx_to_sql.bithing.BIThing;
import elekta.thingworx_to_sql.bithing.BIThingClient;
import elekta.thingworx_to_sql.rest.RestClient;

public class BIThingClientTest {
	
	private static final String ALAN_BI_THING01 = "AlanBIThing01";
	private static final String LOCAL_THINGWORX_INSTANCE = "http://localhost:80/Thingworx";
	private static final String INSOMNIA_APP_KEY = "69691649-caa5-41dd-a9ca-e1cdd565c1a0";
	ClientConfigurator biThingClientConfig = new ClientConfigurator();


	@Before
	public void setup() {
		BasicConfigurator.configure();
		
		biThingClientConfig.setUri("ws://localhost:80/Thingworx/WS");
    	biThingClientConfig.setReconnectInterval(15);

        String appKey = "69691649-caa5-41dd-a9ca-e1cdd565c1a0";
        SecurityClaims claims = SecurityClaims.fromAppKey(appKey);
        biThingClientConfig.setSecurityClaims(claims);

        biThingClientConfig.setName("AlanBIThingGateway");
        biThingClientConfig.setAsSDKType();
        biThingClientConfig.ignoreSSLErrors(true);
	}

	@Test
	public void setStatusProperty() throws Exception {
		BIThingClient client = new BIThingClient(biThingClientConfig);
		
		String randomStatusString = RandomStringUtils.randomAlphabetic(10);
		client.updateStatus(randomStatusString); //Now modify this to ONLY update status
		
		RestClient restClient = new RestClient(LOCAL_THINGWORX_INSTANCE, INSOMNIA_APP_KEY);
		
		String newStatus = restClient.getStringProperty(ALAN_BI_THING01, BIThing.STATUS_PROPERTY);
		System.out.println("Returned Status: " + newStatus);
		
		assertEquals(randomStatusString, newStatus);
	}
}