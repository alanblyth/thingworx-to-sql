package elekta.thingworx_to_sql;

import static org.junit.Assert.*;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;

import com.thingworx.communications.client.ClientConfigurator;

public class BIThingClientTest {
	
	@Before
	public void setup() {
		BasicConfigurator.configure();
	}

	@Test
	public void test() throws Exception {
		ClientConfigurator config = new ClientConfigurator();
		BIThingClient client = new BIThingClient(config);
		
		String[] args = null;
		client.main(args);
	}

}