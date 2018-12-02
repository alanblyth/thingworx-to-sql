package sql;

import java.util.HashMap;
import java.util.Map;

public class TableDefinition {
	
	Map<String, DataType> columns = new HashMap<>();
	String tableName;
	
	public TableDefinition (String tableName) {
		
		this.tableName = tableName;
	}

	public void addColumn(String name, DataType type) {
		
		columns.put(name, type);
	}
	
	public String getCreateTableStatement() throws TableMustHaveColumnsException {
		
		if (columns.size() < 1) {
			
			throw new TableMustHaveColumnsException("Cannot create table with less than 1 columns!");
		}
		String statement = "CREATE TABLE " + tableName + " (";
		
		int count = 1;
		for(Map.Entry<String, DataType> entry : columns.entrySet()) {

		    statement += entry.getKey() + " " + entry.getValue();
		    if (count < columns.size()) {
		    	statement += ", ";
		    }
		    count++;
		}
		
		statement += ")";
		
		return statement;
	}
}
