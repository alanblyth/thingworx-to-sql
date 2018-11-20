package elekta.thingworx_to_sql;

import static org.junit.Assert.*;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;

import com.thingworx.communications.client.ClientConfigurator;

public class AlanBIThingClient2Test {
	
	@Before
	public void setup() {
		BasicConfigurator.configure();
	}

	@Test
	public void test() throws Exception {
		ClientConfigurator config = new ClientConfigurator();
		AlanBIThingClient2 client = new AlanBIThingClient2(config);
		
		client.updateStatus("New Status!"); //Now modify this to ONLY update status
	}
}