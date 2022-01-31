package it.webred;

import it.webred.utils.DateFormat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class MantisUtil {

	 public static void    main(String[] args) {
		    System.out.println("MySQL Connect Example.");
		    Connection conn = null;
		    String url = "jdbc:mysql://172.24.0.34:3306/";
		    String dbName = "BUGTRACKER";
		    String driver = "com.mysql.jdbc.Driver";
		    String userName = "root"; 
		    String password = "git";
		    PreparedStatement ps = null;
		    
		    Scanner in = new Scanner(System.in);
	        System.out.print("Please enter bugId : ");
	        String bugId = in.nextLine();      
	        System.out.println("You entered : " + bugId);
	        System.out.print("Please enter data (dd/mm/yyyy) : ");
	        String dt = in.nextLine();  
	        System.out.print("Please enter hour (HH:mm) : ");
	        String ora = in.nextLine();  
	        String dataora = dt + " " + ora;
		    try {
		      Class.forName(driver).newInstance();
		      conn = DriverManager.getConnection(url+dbName,userName,password);
		      System.out.println("Connected to the database");
		      
		      SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		      
		      Date data = df.parse(dataora);
		     
		      
		      System.out.println("Aggiorno CT-CKLT-" + bugId + ".doc'");
		      
		      ps =conn.prepareStatement("UPDATE BUGTRACKER.mantis_bug_file_table SET DATE_ADDED = ? WHERE BUG_ID=? AND FILENAME='CT-CKLT-" + bugId + ".doc'");
		      ps.setLong(1, data.getTime()/1000);
		      ps.setInt(2, new Integer(bugId));
		      int row = ps.executeUpdate();
		      if (row<=0) 
		    	  throw new Exception("Non ho aggiornato nulla!!!!");
		      
		      
		      ps =conn.prepareStatement("UPDATE BUGTRACKER.mantis_bug_history_table SET DATE_MODIFIED = ? WHERE BUG_ID=? AND OLD_VALUE='CT-CKLT-" + bugId + ".doc'");
		      ps.setLong(1, data.getTime()/1000);
		      ps.setInt(2, new Integer(bugId));
		      row = ps.executeUpdate();
		      
		      if (row<=0) 
		    	  throw new Exception("Non ho aggiornato nulla!!!!");
		      
		      
		    } catch (Exception e) {
		      e.printStackTrace();
		    } finally {
		    	System.out.println("FINITO!");
		    	if (ps!=null)
					try {
						ps.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		    	if (conn!=null)
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    		
		    }
		  }
	
}
