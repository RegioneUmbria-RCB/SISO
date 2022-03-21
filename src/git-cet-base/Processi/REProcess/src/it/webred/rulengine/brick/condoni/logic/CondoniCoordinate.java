package it.webred.rulengine.brick.condoni.logic;

import it.webred.rulengine.brick.condoni.DiagnosticaAnomCondoni;
import it.webred.rulengine.brick.condoni.bean.CondoniCoordinateBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class CondoniCoordinate {
	private static final Logger log = Logger.getLogger(CondoniCoordinate.class.getName());
	private final static String TAB_CONDONI_COORDINATE = "MI_CONDONO_COOR_DIA";
	
	public static ArrayList<CondoniCoordinateBean> getCoordinate(long codCond, Connection conn) throws SQLException {
		String sql = "SELECT * FROM " + TAB_CONDONI_COORDINATE + " WHERE CODCONDONO =" + codCond;
		//log.debug(" sql coordinate: " + sql);
		ArrayList<CondoniCoordinateBean> listaCoordinate = null;
		PreparedStatement pst =null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()){
				CondoniCoordinateBean coor = new CondoniCoordinateBean();
				coor.setCodiceCondono(codCond);
				long foglio =0;
				if (rs.getObject("FOGLIO")== null)
					foglio =0;
				else
					foglio =rs.getLong("FOGLIO");
				String numero =rs.getString("MAPPALE");
				try {
					int numeroNum = Integer.parseInt(numero);
					if (numeroNum==0)
						numero=null;
				}catch(NumberFormatException nfe) {
					numero =null;
				}
				coor.setFoglio(foglio);
				coor.setNumero(numero);
				String sub =  rs.getString("SUB");
				try {
					int subNum = Integer.parseInt(sub);
					if (subNum==0)
						sub=null;
				}catch(NumberFormatException nfe) {
					sub =null;
				}
				coor.setSub(sub);
				if (listaCoordinate==null)
					listaCoordinate=new ArrayList<CondoniCoordinateBean>();
				listaCoordinate.add(coor);
			}
			rs.close();
			pst.close();
			
		}catch(SQLException sqle) {
			throw sqle;
		}
		finally {
			if (rs != null)
				rs.close();
			if (pst != null)
				pst.close();
		}
		return listaCoordinate;		
	}
	
}
