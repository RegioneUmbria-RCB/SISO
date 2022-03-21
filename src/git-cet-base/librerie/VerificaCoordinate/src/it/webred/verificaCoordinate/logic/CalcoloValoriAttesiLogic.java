package it.webred.verificaCoordinate.logic;

import it.webred.verificaCoordinate.dto.request.VerificaCoordinateRequestDTO;
import it.webred.verificaCoordinate.dto.response.VerificaCoordinateResponseDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.apache.log4j.Logger;

public class CalcoloValoriAttesiLogic extends VerificaCoordinateLogic {
	
	private static final Logger log = Logger.getLogger(CalcoloValoriAttesiLogic.class.getName());
	
	protected static final String SQL_ZONA_FOGLIO = "SELECT * " +
								"FROM DOCFA_FOGLI_MICROZONA " +
								"WHERE FOGLIO = ?";
	
	protected static final String ZC = "ZC";
	protected static final String OLD_MICROZONA = "OLD_MICROZONA";
	protected static final String NEW_MICROZONA = "NEW_MICROZONA";
	
	
	public CalcoloValoriAttesiLogic(String codEnte, Connection extConn) {
		super(codEnte, extConn);
	}
	
	public void calcola(VerificaCoordinateRequestDTO params, VerificaCoordinateResponseDTO resp) throws Exception {
		log.info("Inizio calcolo valori attesi");
		CalcoloValoriAttesiLogic valAttLogic = null;
		String categoriaEdiliziaRes = params.getResidenziale() != null ? params.getResidenziale().getCategoriaEdilizia() : null;
		String categoriaEdiliziaNonRes = params.getNonResidenziale() != null ? params.getNonResidenziale().getCategoriaEdilizia() : null;
		if (categoriaEdiliziaRes != null) {
			valAttLogic = new CalcoloValoriAttesiUnoLogic(codEnte, extConn);
		} else if (categoriaEdiliziaNonRes != null) {
			valAttLogic = new CalcoloValoriAttesiQuattroLogic(codEnte, extConn);
		} else if (params.getResidenziale() != null || params.getNonResidenziale() != null) {
			Exception e = new Exception("La Categoria Edilizia deve essere valorizzata");
			log.error(e.getMessage());
			setRespError(resp, e.getMessage());
		}
		if (valAttLogic == null) {
			return;
		}
		valAttLogic.calcola(params, resp);
		log.info("Fine calcolo valori attesi");
	}
	
	public List<HashMap<String, String>> getDatiZona(Connection conn, String foglio) throws Exception {
		List<HashMap<String, String>> datiZona = new ArrayList<HashMap<String, String>>();	

		String sql = SQL_ZONA_FOGLIO;
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, foglio);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			HashMap<String, String> datoZona = new HashMap<String, String>();	
			datoZona.put(ZC, rs.getString(ZC));
			datoZona.put(OLD_MICROZONA, rs.getString(OLD_MICROZONA));
			datoZona.put(NEW_MICROZONA, rs.getString(NEW_MICROZONA));
			datiZona.add(datoZona);
		}
		rs.close();
		ps.close();
		
		return datiZona;
	}

}
