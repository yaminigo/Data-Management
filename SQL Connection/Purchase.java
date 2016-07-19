import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;



public class Purchase {
	
    public static void main(String[] args) {
      Connection connect = null;
  	  Statement statement = null;
  	  ResultSet resultSet = null;
  	  try{
    	Class.forName("com.mysql.jdbc.Driver");
  	  }
  	 catch (Exception ex) {
         
     }
        // Setup the connection with the DB
  	  try{
  		connect = DriverManager
  	            .getConnection("jdbc:mysql://localhost/inf551?"
  	                + "user=inf551&password=inf551");
  	// Statements allow to issue SQL queries to the database
        statement = connect.createStatement();
        // Result set get the result of the SQL query
        PreparedStatement ps = connect.prepareStatement("select distinct(product) from inf551.Purchase where buyer = ?");
        ps.setString(1,args[0]);
        resultSet = ps.executeQuery();
        int i =0;
        while (resultSet.next()) {
        	i =1;
    		String name = resultSet.getString(1);
    		System.out.println(name);
        }
        
        if( i == 0)
        	System.out.println("No data Found");
  	  }
  	catch (SQLException ex) {
	    // handle any errors
	    System.out.println("SQLException: " + ex.getMessage());
	    System.out.println("SQLState: " + ex.getSQLState());
	    System.out.println("VendorError: " + ex.getErrorCode());
	}     
    	
    }
}