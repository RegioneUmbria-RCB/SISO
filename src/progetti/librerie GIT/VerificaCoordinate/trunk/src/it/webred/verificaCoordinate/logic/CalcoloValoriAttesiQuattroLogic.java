package it.webred.verificaCoordinate.logic;

import it.webred.verificaCoordinate.dto.request.VerificaCoordinateRequestDTO;
import it.webred.verificaCoordinate.dto.response.DatiAttesiDTO;
import it.webred.verificaCoordinate.dto.response.DatiAttesiNonResidenzialeDTO;
import it.webred.verificaCoordinate.dto.response.MappaleDTO;
import it.webred.verificaCoordinate.dto.response.VerificaCoordinateResponseDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

public class CalcoloValoriAttesiQuattroLogic extends CalcoloValoriAttesiLogic {
	
	private static final Logger log = Logger.getLogger(CalcoloValoriAttesiQuattroLogic.class.getName());
	
	private static final String SQL_CLASSE_MIN = "SELECT CLASSE_MIN " +
								"FROM DOCFA_CLASSI_MIN " +
								"WHERE FOGLIO = ? " +
								"AND CATEGORIA = ?";
	
	public CalcoloValoriAttesiQuattroLogic(String codEnte, Connection extConn) {
		super(codEnte, extConn);
	}
	
	public void calcola(VerificaCoordinateRequestDTO params, VerificaCoordinateResponseDTO resp) throws Exception {
		Connection conn = null;
		
		try {
			conn = getConnection();
			
			String foglio = params.getDatiCatastali().getFoglio();
			String categoriaEdilizia = params.getNonResidenziale().getCategoriaEdilizia();
			String postoAutoCoperto = params.getNonResidenziale().getC6() == null ? null : params.getNonResidenziale().getC6().getPostoAutoCoperto();
			
			MappaleDTO mappale = resp.getElencoMappale().getMappale().get(0);
			
			if (categoriaEdilizia == null || categoriaEdilizia.equals("")) {
				Exception e = new Exception("La Categoria Edilizia deve essere valorizzata");
				log.error(e.getMessage());
				setRespError(resp, e.getMessage());
				return;
			}
			
			DatiAttesiDTO datiAttesi = new DatiAttesiDTO();		
			
			if (categoriaEdilizia != null && !categoriaEdilizia.equalsIgnoreCase("C06")) {
				postoAutoCoperto = null;
			}

			//calcolo
			List<HashMap<String, String>> datiZona = getDatiZona(conn, foglio);
			for (HashMap<String, String> datoZona : datiZona) {
				String zona = datoZona.get(ZC);
				
				DatiAttesiNonResidenzialeDTO datiAttesiNonResidenziale = new DatiAttesiNonResidenzialeDTO();
				
				String classeMin = getClasseMin(conn, foglio, zona, categoriaEdilizia);
				if (categoriaEdilizia != null && categoriaEdilizia.equalsIgnoreCase("C06") && postoAutoCoperto != null && postoAutoCoperto.equalsIgnoreCase("S")){
					if (classeMin != null && !classeMin.equals(""))
						classeMin = String.valueOf(Integer.valueOf(classeMin).intValue() - 3);
				}

				if (classeMin != null && !classeMin.equals("")) {
					datiAttesiNonResidenziale.setClasseMediaRiferimento(classeMin);
				}
				
				datiAttesi.getDatiAttesiNonResidenziale().add(datiAttesiNonResidenziale);
			}

			mappale.setDatiAttesi(datiAttesi);
			
		} catch (Exception e) {
			log.error("Errore nel calcolo dei valori attesi (non residenziale)", e);
			throw e;
		} finally {
			try {
				closeConnection(conn, false);
			} catch (Exception e) {
				log.error("Errore nel calcolo dei valori attesi (non residenziale)", e);
			}
		}
	}
	
	private String getClasseMin(Connection conn, String foglio, String zc, String categoria) throws Exception {
		String classeMin = null;
		
		String sql = SQL_CLASSE_MIN;
		if (zc != null && !zc.equals("")) {
			sql += " AND ZC = ?";
		}
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, foglio);
		ps.setString(2, categoria);
		if (zc != null && !zc.equals("")) {
			ps.setString(3, zc);
		}
		ResultSet rs = ps.executeQuery();		
		while (rs.next()) {
			classeMin = rs.getString("CLASSE_MIN");
		}
		rs.close();
		ps.close();
		
		return classeMin;
	}

}
