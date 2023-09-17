package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;

public class Program {

	public static void main(String[] args) {
		
		/*this program is to insert new information in the 
		database table*/
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Connection conn  = null;
		PreparedStatement st = null; //this will insert new information
		
	try {
		conn = DB.getConnection();
			
		st = conn.prepareStatement(
			"INSERT INTO seller "
			+"(Name, Email, BirthDate, BaseSalary, DepartmentId) "
			+ "VALUES"
			+ "(?, ?, ?, ?, ?)",
			Statement.RETURN_GENERATED_KEYS); 
		//this is a placeHolder: "?", one for each information a need
		st.setString(1, "Eduardo"); //"1" position of the "?", after the "," the information that will be inserted
		st.setString(2, "eduardo.d.liberato@success.com");
		st.setDate(3, new java.sql.Date(sdf.parse("16/03/2000").getTime()));
		st.setDouble(4, 36000.00);
		st.setInt(5, 4);
		
		int rowsAffected = st.executeUpdate(); //update the information
		
		//to show the Id key generated:
		if (rowsAffected > 0) {
			ResultSet rs = st.getGeneratedKeys();
			while(rs.next()) {
				int id = rs.getInt(1);
				System.out.println("Done! Id = " + id);
			}
		} else {
			System.out.println("No rown affected!");
		}
		
	} 
	catch (SQLException e) {			
		e.printStackTrace();
	} 
	catch (ParseException e) {			
		e.printStackTrace();
	}
	finally {
		DB.closeStatement(st);
		DB.closeConnection(); //the connection is always the last to close
	}
}
}
