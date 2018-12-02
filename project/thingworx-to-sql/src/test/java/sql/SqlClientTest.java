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
	

	@Test
	public void dropDatabaseDoesntExist() throws Exception {
		
		String databaseName = "dropDatabaseDoesntExist";

		SqlClient sqlClient = new SqlClient();
		
		sqlClient.setUsername("sa");
		sqlClient.setPassword("Secure!PassW0rd");
		sqlClient.setDatabaseUrl("jdbc:sqlserver://localhost");
		
		sqlClient.connect();
		
		try {
			sqlClient.dropDatabase(databaseName);			
			fail("Expected exception not encountered");
		}
		catch (Exception e) {
			
		}
	}
	
	@Test
	public void setDatabase() throws Exception {
		
		String databaseName = "setDatabase";

		SqlClient sqlClient = new SqlClient();
		
		sqlClient.setUsername("sa");
		sqlClient.setPassword("Secure!PassW0rd");
		sqlClient.setDatabaseUrl("jdbc:sqlserver://localhost");
		
		sqlClient.connect();
		
		sqlClient.createDatabase(databaseName);
		sqlClient.setDatabase(databaseName);
		assertEquals(databaseName, sqlClient.getCurrentDatabase());
		sqlClient.dropDatabase(databaseName);
	}
	
	@Test
	public void createAndDropTableOneColumn() throws Exception {
		
		String databaseName = "createAndDropTable";
		String tableName = "createAndDropTable";
		SqlClient sqlClient = new SqlClient();
		
		sqlClient.setUsername("sa");
		sqlClient.setPassword("Secure!PassW0rd");
		sqlClient.setDatabaseUrl("jdbc:sqlserver://localhost");
		
		sqlClient.connect();
		try {
			sqlClient.createDatabase(databaseName);
			
			TableDefinition table = new TableDefinition(tableName);
			table.addColumn("column2", DataType.VARCHAR);
			sqlClient.createTable(table);
			sqlClient.dropTable(databaseName);
			sqlClient.dropDatabase(databaseName);			
		}
		catch (Exception e) {
			sqlClient.dropDatabase(databaseName);
			throw e;
		}
	}

	@Test
	public void createAndDropTableNoColumns() throws Exception {
		
		String databaseName = "createAndDropTableNoColumns";
		String tableName = "createAndDropTableNoColumns";
		SqlClient sqlClient = new SqlClient();
		
		sqlClient.setUsername("sa");
		sqlClient.setPassword("Secure!PassW0rd");
		sqlClient.setDatabaseUrl("jdbc:sqlserver://localhost");
		
		sqlClient.connect();
		try {
			sqlClient.createDatabase(databaseName);
			
			TableDefinition table = new TableDefinition(tableName);
			sqlClient.createTable(table);
			fail("Expected exception not encountered!");		
		}
		catch (TableMustHaveColumnsException e) {
			//Expected Exception
			System.out.println("Found Excepion!");
			sqlClient.dropDatabase(databaseName);
		}
		catch (Exception e) {
			sqlClient.dropDatabase(databaseName);
			throw e;
		}
	}
}
