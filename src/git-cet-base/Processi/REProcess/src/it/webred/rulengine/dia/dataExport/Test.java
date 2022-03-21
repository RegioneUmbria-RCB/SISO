package it.webred.rulengine.dia.dataExport;

import java.sql.Connection;
import java.sql.DriverManager;

public class Test {
	
	public static void main(String[] args) {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			conn = DriverManager.getConnection ("jdbc:oracle:thin:@praga:1521:praga", "dbtotale", "dbtotale");
			DiaCatDataExporter diaCat = new DiaCatDataExporter(114, null, true, true);
			diaCat.export(conn);
			DiaCatDataExporter diaCatParam = new DiaCatDataExporter(115, null, true, true);
			diaCatParam.setElencoValori(new String[] {"5"});
			diaCatParam.export(conn);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}				
		}
	}
	

}
