package sql;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TableDefinitionTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testOneColumn() throws TableMustHaveColumnsException {
		
		String tableName = "table1";
		TableDefinition tableDef = new TableDefinition(tableName);
		
		tableDef.addColumn("column1", DataType.VARCHAR);
		
		assertEquals("CREATE TABLE " + tableName + " (column1 VARCHAR)", tableDef.getCreateTableStatement());
	}

	@Test
	public void testTwoColumn() throws TableMustHaveColumnsException {
		
		String tableName = "table1";
		TableDefinition tableDef = new TableDefinition(tableName);
		
		tableDef.addColumn("column1", DataType.VARCHAR);
		tableDef.addColumn("column2", DataType.VARCHAR);
		
		assertEquals("CREATE TABLE " + tableName + " (column1 VARCHAR, column2 VARCHAR)", tableDef.getCreateTableStatement());
	}
}
