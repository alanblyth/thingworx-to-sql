package sql;

import java.sql.*;

import org.apache.log4j.Logger;

import elekta.thingworx_to_sql.rest.RestClient;


public class SqlClient {
	//STEP 1. Import required packages

	   // JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";  
	   private String dbUrl;

	   //  Database credentials
	   private String username;
	   private String password;
	   
	   Connection conn = null;

	   
	    static Logger log = Logger.getLogger(SqlClient.class);
	   
	   public void setDatabaseUrl(String databaseUrl) {
		   
		   this.dbUrl = databaseUrl;
	   }
	   
	   public void setUsername(String username) {
		   
		   this.username = username;
	   }
	   
	   public void setPassword (String password) {
		   
		   this.password = password;
	   }
	   
	   public void connect() throws Exception {
		   
	      //STEP 2: Register JDBC driver
	      Class.forName(JDBC_DRIVER);

	      log.info("Connecting to database...");
	      conn = DriverManager.getConnection(dbUrl, username, password);
	   }
	   
	   public void createDatabase(String databaseName) throws ClassNotFoundException, SQLException {

		   //STEP 4: Execute a query
		   log.info("Creating database...");
		   Statement stmt = conn.createStatement();
		   String sql = "CREATE DATABASE " + databaseName;
		   stmt.executeUpdate(sql);
		   log.info("Database created successfully...");
	   }
	   
	   public void dropDatabase(String databaseName) throws ClassNotFoundException, SQLException {

		   //STEP 4: Execute a query
		   log.info("Dropping database...");
		   Statement stmt = conn.createStatement();
		   String sql = "DROP DATABASE " + databaseName;
		   stmt.executeUpdate(sql);
		   log.info("Database dropped successfully...");
	   }
	   
	   public void example2() {
		   
		      Connection conn = null;
		      Statement stmt = null;
		      try{
		         //STEP 2: Register JDBC driver
		         Class.forName("com.mysql.jdbc.Driver");

		         //STEP 3: Open a connection
		         System.out.println("Connecting to database...");
		         conn = DriverManager.getConnection(dbUrl, username, password);

		         //STEP 4: Execute a query
		         System.out.println("Creating database...");
		         stmt = conn.createStatement();
		         
		         String sql = "CREATE DATABASE STUDENTS";
		         stmt.executeUpdate(sql);
		         System.out.println("Database created successfully...");
		      }catch(SQLException se){
		         //Handle errors for JDBC
		         se.printStackTrace();
		      }catch(Exception e){
		         //Handle errors for Class.forName
		         e.printStackTrace();
		      }finally{
		         //finally block used to close resources
		         try{
		            if(stmt!=null)
		               stmt.close();
		         }catch(SQLException se2){
		         }// nothing we can do
		         try{
		            if(conn!=null)
		               conn.close();
		         }catch(SQLException se){
		            se.printStackTrace();
		         }//end finally try
		      }//end try
		      System.out.println("Goodbye!");
	   }
	   
	   public void example1() {
	   Connection conn = null;
	   Statement stmt = null;
	   try{
	      //STEP 2: Register JDBC driver
	      Class.forName("com.mysql.jdbc.Driver");

	      //STEP 3: Open a connection
	      System.out.println("Connecting to database...");
	      conn = DriverManager.getConnection(dbUrl,username,password);

	      //STEP 4: Execute a query
	      System.out.println("Creating statement...");
	      stmt = conn.createStatement();
	      String sql;
	      sql = "SELECT id, first, last, age FROM Employees";
	      ResultSet rs = stmt.executeQuery(sql);

	      //STEP 5: Extract data from result set
	      while(rs.next()){
	         //Retrieve by column name
	         int id  = rs.getInt("id");
	         int age = rs.getInt("age");
	         String first = rs.getString("first");
	         String last = rs.getString("last");

	         //Display values
	         System.out.print("ID: " + id);
	         System.out.print(", Age: " + age);
	         System.out.print(", First: " + first);
	         System.out.println(", Last: " + last);
	      }
	      //STEP 6: Clean-up environment
	      rs.close();
	      stmt.close();
	      conn.close();
	   }catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            stmt.close();
	      }catch(SQLException se2){
	      }// nothing we can do
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }//end finally try
	   }//end try
	   System.out.println("Goodbye!");
	}//end main
//end FirstExample
	
}
