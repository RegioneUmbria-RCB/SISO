package it.webred.rulengine.db;


import java.sql.Connection;




@SuppressWarnings("unchecked")
public class RulesConnectionBase 
{
	
	protected static Connection reConn;
	
	public RulesConnectionBase(Connection conn){
		reConn=conn;
	}
	
}
