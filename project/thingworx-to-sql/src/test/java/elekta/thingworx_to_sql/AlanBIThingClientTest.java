package elekta.thingworx_to_sql;

import static org.junit.Assert.*;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;

import com.thingworx.communications.client.ClientConfigurator;

import elekta.thingworx_to_sql.bithing.AlanBIThingClient;

public class AlanBIThingClientTest {
	
	@Before
	public void setup() {
		BasicConfigurator.configure();
	}

	@Test
	public void test() throws Exception {
		ClientConfigurator config = new ClientConfigurator();
		AlanBIThingClient client = new AlanBIThingClient(config);
		
		String[] args = null;
		client.main(args);
		//Thread.sleep(90000);
	}
}
