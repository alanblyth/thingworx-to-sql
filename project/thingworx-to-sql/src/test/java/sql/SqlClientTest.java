package sql;

import static org.junit.Assert.*;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SqlClientTest {
	

	@BeforeClass
	static public void setUpClass() {
		
		BasicConfigurator.configure();
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void createAndDropDatabase() throws Exception {
		
		String databaseName = "createAndDropDatabase";

		SqlClient sqlClient = new SqlClient();
		
		sqlClient.setUsername("sa");
		sqlClient.setPassword("Secure!PassW0rd");
		sqlClient.setDatabaseUrl("jdbc:sqlserver://localhost");
		
		sqlClient.connect();
		
		sqlClient.createDatabase(databaseName);
		sqlClient.dropDatabase(databaseName);
	}

}
