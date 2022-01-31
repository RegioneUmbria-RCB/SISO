package it.webred.test;

import it.webred.permessi.AuthContext;
import it.webred.permessi.GestionePermessi;

import java.net.URL;
import java.security.Principal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * Classe per l'esecuzione dei test sui servizi web.
 * 
 * @author marcoric
 * @version $Revision: 1.1 $ $Date: 2007/05/16 07:31:04 $
 */
public class Test {

	public static void main(String[] args) throws Exception {

		String driverClass = "oracle.jdbc.driver.OracleDriver";
		String urlConn = "jdbc:oracle:thin:@172.16.2.61:1521:DBCAT";
		String userConn = "DIOGENE_F704";
		String pwdConn = "DIOGENE_F704";
		PreparedStatement st = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			Class.forName(driverClass);
			 conn = DriverManager.getConnection (urlConn, userConn, pwdConn);
			st = conn.prepareStatement("select * from sit_comune");
			rs = st.executeQuery();
			//st.close();
			while (rs.next())  {
				System.out.println(rs.getString(1));
				
			}
/*			Principal user = new Principal() {
				public String getName() {
					return "profiler";
				}
			};
			
			AuthContext authContext = new AuthContext(user, conn, "Portale Servizi", "Servizio 1");
			
			GestionePermessi.autorizzato( authContext, "S1 Permesso 1", true);
			Object[] userPwdValida = GestionePermessi.pwdValida(authContext, 90);
	*/
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			rs.close();
			st.close();
			conn.close();
		}
	}
}
