package it.webred.verificaCoordinate.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import it.webred.verificaCoordinate.dto.request.VerificaCoordinateRequestDTO;
import it.webred.verificaCoordinate.dto.response.ElencoVieDTO;
import it.webred.verificaCoordinate.dto.response.ErrorDTO;
import it.webred.verificaCoordinate.dto.response.InfoDTO;
import it.webred.verificaCoordinate.dto.response.MappaleDTO;
import it.webred.verificaCoordinate.dto.response.OkDTO;
import it.webred.verificaCoordinate.dto.response.VerificaCoordinateResponseDTO;

public class VerificaCoordinateLogic extends VerificaCoordinateBaseLogic {
	private static final Logger log = Logger.getLogger(VerificaCoordinateLogic.class.getName());
	
	protected static final String SQL_COUNT_CAT_ALFA_TER = "SELECT COUNT(1) AS CONTA FROM SITITRKC " +
	"WHERE NVL(DATA_FINE, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
	"AND COD_NAZIONALE = ? AND FOGLIO = ? AND PARTICELLA = ?";

	protected static final String SQL_COUNT_CAT_CART_TER = "SELECT COUNT(1) AS CONTA FROM SITIPART " +
	"WHERE NVL(DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
	"AND COD_NAZIONALE = ? AND FOGLIO = ? AND PARTICELLA = ?";

	protected static final String SQL_COUNT_CAT_ALFA_URB = "SELECT COUNT(1) AS CONTA FROM SITIUIU " +
	"WHERE NVL(DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
	"AND COD_NAZIONALE = ? AND FOGLIO = ? AND PARTICELLA = ?";

	protected static final String SQL_COUNT_CAT_CART_FAB = "SELECT COUNT(1) AS CONTA FROM SITISUOLO " +
	"WHERE NVL(DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
	"AND COD_NAZIONALE = ? AND FOGLIO = ? AND PARTICELLA = ?";
	
	
	public VerificaCoordinateLogic(String codEnte, Connection extConn) {
		super(codEnte, extConn);
	}
	
	public VerificaCoordinateResponseDTO verifica(VerificaCoordinateRequestDTO params) {		
		VerificaCoordinateResponseDTO resp = new VerificaCoordinateResponseDTO();
		//controllo formale della richiesta
		ControllaRichiestaLogic ctrlLogic = new ControllaRichiestaLogic(codEnte, extConn);
		resp = ctrlLogic.controllaRichiesta(params);
		if (isCtrlValid(resp)) {
			//validazione dei dati toponomastici
			ValidazioneDatiToponomasticiLogic topoLogic = new ValidazioneDatiToponomasticiLogic(codEnte, extConn);
			resp = topoLogic.verifica(params);
			if (isTopoValid(resp)) {
				//validazione dei dati catastali
				ElencoVieDTO vie = resp.getElencoVie();
				ValidazioneDatiCatastaliLogic catLogic =  new ValidazioneDatiCatastaliLogic(codEnte, extConn);
				resp = catLogic.verifica(params, vie);
				if (isCatValid(resp)) {
					//verifica della congruenza tra dati toponomastici e dati catastali
					vie = resp.getElencoVie();
					VerificaCongruenzaLogic congrLogic = new VerificaCongruenzaLogic(codEnte, extConn);
					resp = congrLogic.verifica(params, vie);
				}
			}
		}
		return resp;
	}
	
	protected void setRespError(VerificaCoordinateResponseDTO resp, String errDesc) {
		ErrorDTO err = new ErrorDTO();
		err.setDesc(errDesc);
		resp.getError().add(err);
	}
	
	protected void setRespOk(VerificaCoordinateResponseDTO resp, String okDesc) {
		OkDTO ok = new OkDTO();
		ok.setDesc(okDesc);
		resp.setOk(ok);
	}
	
	protected void setRespInfo(VerificaCoordinateResponseDTO resp, String infoDesc) {
		InfoDTO info = new InfoDTO();
		info.setDesc(infoDesc);
		resp.setInfo(info);
	}
	
	protected boolean isCtrlValid(VerificaCoordinateResponseDTO resp) {
		if (resp.getError() != null && resp.getError().size() > 0) {
			return false;
		}
		return true;
	}
	
	protected boolean isTopoValid(VerificaCoordinateResponseDTO resp) {
		if (resp.getError() != null && resp.getError().size() > 0) {
			return false;
		} else if (resp.getInfo() != null && resp.getElencoVie() != null 
					&& resp.getElencoVie().getVia() != null && resp.getElencoVie().getVia().size() > 0) {
			return false;
		}
		return true;
	}
	
	protected boolean isCatValid(VerificaCoordinateResponseDTO resp) {
		if (resp.getError() != null && resp.getError().size() > 0) {
			return false;
		}
		return true;
	}
	
	protected String padding(String s, int n, char c, boolean paddingLeft) {
	    if (s == null)
	    	s = "";
		StringBuffer str = new StringBuffer(s);
	    int strLength = str.length();
	    if (n > 0 && n > strLength) {
	    	for (int i = 0; i <= n ; i ++) {
	    		if (paddingLeft) {
	    			if (i < n - strLength) str.insert(0, c);
	    		} else {
	    			if (i > strLength) str.append(c);
	    		}
	    	}
	    }
	    return str.toString();
	}
	
	protected void setMappale(Connection conn, MappaleDTO mappale) throws Exception {
		boolean okUrb = false;
		boolean okTer = false;
		boolean okCart = false;
		
		//verifica catasto alfanumerico urbano
		String sql = SQL_COUNT_CAT_ALFA_URB;
		PreparedStatement ps = conn.prepareStatement(sql);
		String ente = codEnte.equalsIgnoreCase("DEFAULT") ? DEF_COD_ENTE : codEnte.toUpperCase();
		ps.setString(1, ente);
		ps.setInt(2, Integer.parseInt(mappale.getFoglio()));
		ps.setString(3, padding(mappale.getParticella(), 5, '0', true));
		log.debug(sql);
		log.debug(ente + "-" + Integer.parseInt(mappale.getFoglio()) +"-" + padding(mappale.getParticella(), 5, '0', true) );
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			okUrb = rs.getObject("CONTA") != null && rs.getInt("CONTA") > 0;
		}
		rs.close();
		ps.close();
		log.debug("SQL ESEGUITO!");
		//verifica catasto alfanumerico terreni
		sql = SQL_COUNT_CAT_ALFA_TER;
		ps = conn.prepareStatement(sql);
		ente = codEnte.equalsIgnoreCase("DEFAULT") ? DEF_COD_ENTE : codEnte.toUpperCase();
		ps.setString(1, ente);
		ps.setInt(2, Integer.parseInt(mappale.getFoglio()));
		ps.setString(3, padding(mappale.getParticella(), 5, '0', true));
		log.debug(sql);
		log.debug(ente + "-" + Integer.parseInt(mappale.getFoglio()) +"-" + padding(mappale.getParticella(), 5, '0', true) );

		rs = ps.executeQuery();
		while (rs.next()) {
			okTer = rs.getObject("CONTA") != null && rs.getInt("CONTA") > 0;
		}
		rs.close();
		ps.close();
		log.debug("SQL ESEGUITA!");
		
		
		//verifica cartografia
		sql = SQL_COUNT_CAT_CART_FAB;
		ps = conn.prepareStatement(sql);
		ps.setString(1, ente);
		ps.setInt(2, Integer.parseInt(mappale.getFoglio()));
		ps.setString(3, padding(mappale.getParticella(), 5, '0', true));
		log.debug(sql);
		log.debug(ente + "-" + Integer.parseInt(mappale.getFoglio()) +"-" + padding(mappale.getParticella(), 5, '0', true) );

		rs = ps.executeQuery();
		while (rs.next()) {
			okCart = rs.getObject("CONTA") != null && rs.getInt("CONTA") > 0;
		}
		rs.close();
		ps.close();
		log.debug("SQL ESEGUITO!");
		
		if (!okCart) {
			sql = SQL_COUNT_CAT_CART_TER;
			ps = conn.prepareStatement(sql);
			ps.setString(1, codEnte.equalsIgnoreCase("DEFAULT") ? DEF_COD_ENTE : codEnte.toUpperCase());
			ps.setInt(2, Integer.parseInt(mappale.getFoglio()));
			ps.setString(3, padding(mappale.getParticella(), 5, '0', true));
			log.debug(sql);
			log.debug(ente + "-" + Integer.parseInt(mappale.getFoglio()) +"-" + padding(mappale.getParticella(), 5, '0', true) );
			rs = ps.executeQuery();
			while (rs.next()) {
				okCart = rs.getObject("CONTA") != null && rs.getInt("CONTA") > 0;
			}
			rs.close();
			ps.close();
			log.debug("SQL ESEGUITO!");
		}		
		
		mappale.setCensUrbano(okUrb ? "S" : "N");
		mappale.setCensTerreni(okTer ? "S" : "N");
		mappale.setCartografia(okCart ? "S" : "N");
	}

}
