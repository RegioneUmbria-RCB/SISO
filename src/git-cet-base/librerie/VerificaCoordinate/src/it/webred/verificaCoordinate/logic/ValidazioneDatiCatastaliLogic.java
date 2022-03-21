package it.webred.verificaCoordinate.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import it.webred.verificaCoordinate.dto.request.VerificaCoordinateRequestDTO;
import it.webred.verificaCoordinate.dto.response.ElencoMappaleDTO;
import it.webred.verificaCoordinate.dto.response.ElencoUiuDTO;
import it.webred.verificaCoordinate.dto.response.ElencoVieDTO;
import it.webred.verificaCoordinate.dto.response.MappaleDTO;
import it.webred.verificaCoordinate.dto.response.UiuDTO;
import it.webred.verificaCoordinate.dto.response.VerificaCoordinateResponseDTO;

import org.apache.log4j.Logger;

public class ValidazioneDatiCatastaliLogic extends VerificaCoordinateLogic {
	
	private static final Logger log = Logger.getLogger(ValidazioneDatiCatastaliLogic.class.getName());	
	
	private static final String SQL_UIU_CIVICO = "SELECT DISTINCT SU.FOGLIO, " +
								"REMOVE_LEAD_ZERO(SU.PARTICELLA) AS PARTICELLA, " +
								"SU.UNIMM, " +
								"SU.CLASSE, " +
								"SU.CATEGORIA, " +
								"SU.RENDITA, " +
								"SU.DATA_INIZIO_VAL " +
								"FROM SITIUIU SU, SITICIVI_UIU SCU, SITICIVI SC, SITIDSTR SD " +
								"WHERE NVL(SU.DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND NVL(SCU.DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND NVL(SC.DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND NVL(SD.DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND SU.COD_NAZIONALE = ? " +
								"AND SU.PKID_UIU = SCU.PKID_UIU " +
								"AND SCU.PKID_CIVI = SC.PKID_CIVI " +
								"AND SC.PKID_STRA = SD.PKID_STRA " +
								"AND SD.PKID_STRA = ? " +
								"AND REMOVE_LEAD_ZERO(SC.CIVICO) = ?";
	
	private static final String SQL_MAPPALI_VIA = "SELECT DISTINCT SU.FOGLIO, " +
								"REMOVE_LEAD_ZERO(SU.PARTICELLA) AS PARTICELLA, " +
								"SU.DATA_INIZIO_VAL " +
								"FROM SITIUIU SU, SITICIVI_UIU SCU, SITICIVI SC, SITIDSTR SD " +
								"WHERE NVL(SU.DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND NVL(SCU.DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND NVL(SC.DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND NVL(SD.DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND SU.COD_NAZIONALE = ? " +
								"AND SU.PKID_UIU = SCU.PKID_UIU " +
								"AND SCU.PKID_CIVI = SC.PKID_CIVI " +
								"AND SC.PKID_STRA = SD.PKID_STRA " +
								"AND SD.PKID_STRA = ?";
	
	private static final DecimalFormat DF = new DecimalFormat();
	static {
		DF.setGroupingUsed(false);
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator(',');
		DF.setDecimalFormatSymbols(dfs);
		DF.setMaximumFractionDigits(2);
	}
	

	public ValidazioneDatiCatastaliLogic(String codEnte, Connection extConn) {
		super(codEnte, extConn);
	}
	
	public VerificaCoordinateResponseDTO verifica(VerificaCoordinateRequestDTO params, ElencoVieDTO vie) {		
		VerificaCoordinateResponseDTO resp = new VerificaCoordinateResponseDTO();
		
		log.info("Inizio validazione dati catastali");
		Connection conn = null;
		try {
			conn = getConnection();
			
			String tipoCatasto = params.getDatiCatastali().getTipoCatasto().value();
			String foglio = params.getDatiCatastali().getFoglio();
			String mappale = padding(params.getDatiCatastali().getMappale(), 5, '0', true);
			String subalterno = params.getDatiCatastali().getSubalterno();
			
			boolean ok = false;
			if (tipoCatasto.equalsIgnoreCase("Terreni")) {
				ok = isOkTerreni(conn, foglio, mappale, subalterno);
			} else if (tipoCatasto.equalsIgnoreCase("Fabbricati")) {
				ok = isOkFabbricati(conn, foglio, mappale, subalterno);
			}
			
			if (!ok) {
				setRespError(resp, "Non esistono dati per i parametri catastali specificati");				
				if (tipoCatasto.equalsIgnoreCase("Terreni")) {
					//TODO come??????
				} else if (tipoCatasto.equalsIgnoreCase("Fabbricati")) {
					setRespMappaliUiuFab(resp, conn, params, vie);
				}
			} else {
				resp.setElencoVie(vie); //servirà per la verifica della congruenza
			}
		} catch (Exception e) {
			resp = new VerificaCoordinateResponseDTO();
			setRespError(resp, "Si è verificato un errore nella validazione dei dati catastali: " + e.getMessage());
		} finally {
			try {
				closeConnection(conn, false);
			} catch (Exception e) {
				log.error("Errore nella validazione dei dati catastali", e);
			}
		}
		log.info("Fine validazione dati catastali");
		return resp;
	}
	
	private boolean isOkTerreni(Connection conn, String foglio, String mappale, String subalterno) throws Exception {
		boolean ok = false;
		boolean okAlfa = false;
		boolean okCart = false;
		
		String sql = SQL_COUNT_CAT_ALFA_TER;
		if (subalterno != null && !subalterno.equals("")) {
			sql += " AND SUB = ?";
		}
		PreparedStatement ps = conn.prepareStatement(sql);
		log.debug(sql);
		String ente = codEnte.equalsIgnoreCase("DEFAULT") ? DEF_COD_ENTE : codEnte.toUpperCase();
		ps.setString(1, ente);
		ps.setInt(2, Integer.parseInt(foglio));
		log.debug("param1="+ ente);
		ps.setString(3, mappale);
		log.debug("param2="+ mappale);
		if (subalterno != null && !subalterno.equals("")) {
			ps.setString(4, subalterno);
			log.debug("param3="+ subalterno);
		}
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			okAlfa = rs.getObject("CONTA") != null && rs.getInt("CONTA") > 0;
		}
		rs.close();
		ps.close();
		log.debug("SQL ESEGUITO!");

		
		sql = SQL_COUNT_CAT_CART_TER;
		ps = conn.prepareStatement(sql);
		log.debug(sql);
		ente =codEnte.equalsIgnoreCase("DEFAULT") ? DEF_COD_ENTE : codEnte.toUpperCase();
		ps.setString(1, ente);
		log.debug("param1="+ ente);
		ps.setInt(2, Integer.parseInt(foglio));
		log.debug("param1="+ Integer.parseInt(foglio));
		ps.setString(3, mappale);
		log.debug("param3="+ mappale);
		
		rs = ps.executeQuery();
		while (rs.next()) {
			okCart = rs.getObject("CONTA") != null && rs.getInt("CONTA") > 0;
		}
		rs.close();
		ps.close();
		log.debug("SQL ESEGUITO!");

		
		ok = okAlfa && okCart;
		return ok;
	}
	
	private boolean isOkFabbricati(Connection conn, String foglio, String mappale, String subalterno) throws Exception {
		boolean ok = false;
		boolean okAlfa = false;
		boolean okCart = false;
		
		String sql = SQL_COUNT_CAT_ALFA_URB;
		if (subalterno != null && !subalterno.equals("")) {
			sql += " AND UNIMM = ?";
		}
		PreparedStatement ps = conn.prepareStatement(sql);
		log.debug(sql);
		String ente = codEnte.equalsIgnoreCase("DEFAULT") ? DEF_COD_ENTE : codEnte.toUpperCase();
		ps.setString(1, codEnte.equalsIgnoreCase("DEFAULT") ? DEF_COD_ENTE : codEnte.toUpperCase());
		log.debug("param1="+ ente);
		ps.setInt(2, Integer.parseInt(foglio));
		log.debug("param2="+ foglio);
		ps.setString(3, mappale);
		log.debug("param3="+ mappale);
		if (subalterno != null && !subalterno.equals("")) {
			ps.setInt(4, Integer.parseInt(subalterno));
			log.debug("param4="+ subalterno);
		}
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			okAlfa = rs.getObject("CONTA") != null && rs.getInt("CONTA") > 0;
		}
		rs.close();
		ps.close();
		log.debug("SQL ESEGUITO!");

		
		sql = SQL_COUNT_CAT_CART_FAB;
		ps = conn.prepareStatement(sql);
		log.debug(sql);
		ente =codEnte.equalsIgnoreCase("DEFAULT") ? DEF_COD_ENTE : codEnte.toUpperCase();
		ps.setString(1, codEnte.equalsIgnoreCase("DEFAULT") ? DEF_COD_ENTE : codEnte.toUpperCase());
		log.debug("param1="+ ente);
		ps.setInt(2, Integer.parseInt(foglio));
		log.debug("param2="+ Integer.parseInt(foglio));
		ps.setString(3, mappale);
		log.debug("param3="+ mappale);
		rs = ps.executeQuery();
		while (rs.next()) {
			okCart = rs.getObject("CONTA") != null && rs.getInt("CONTA") > 0;
		}
		rs.close();
		ps.close();
		log.debug("SQL ESEGUITO!");

		
		ok = okAlfa && okCart;
		return ok;
	}
	
	private void setRespMappaliUiuFab(VerificaCoordinateResponseDTO resp, Connection conn, VerificaCoordinateRequestDTO params, ElencoVieDTO vie) throws Exception {
		//a questo punto dovrebbe esserci una ed una sola via
		String codiceVia = vie.getVia().get(0).getCodiceVia();
		String civico = params.getDatiToponomastici().getCivico();
		String esponente = params.getDatiToponomastici().getEsponente();
		String sql = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		if (civico != null && !civico.equals("")) {
			sql = SQL_UIU_CIVICO;
			if (esponente != null && !esponente.equals("")) {
				sql = sql.replace("AND REMOVE_LEAD_ZERO(SC.CIVICO) = ?", "AND REMOVE_LEAD_ZERO(SC.CIVICO) IN(");
				String[] civEsps = new String[] {
					civico + esponente,
					civico + "/" + esponente,
					civico + " / " + esponente,
					civico + "-" + esponente,
					civico + " - " + esponente,
					civico + " " + esponente
				};
				int idx = 0;
				for (String civEsp : civEsps) {
					civEsp = "'" + civEsp + "'";
					if (idx > 0) {
						sql += ", ";
					}
					sql += civEsp;
					idx++;
				}
				sql += ")";				
			}
			ps = conn.prepareStatement(sql);
			log.debug(sql);
			String ente = codEnte.equalsIgnoreCase("DEFAULT") ? DEF_COD_ENTE : codEnte.toUpperCase();
			ps.setString(1, ente);
			log.debug("param1="+ ente);
			ps.setInt(2, Integer.parseInt(codiceVia));
			log.debug("Param2="+Integer.parseInt(codiceVia));
			if (esponente == null || esponente.equals("")) {
				ps.setString(3, civico.toUpperCase());
				log.debug("Param3="+civico.toUpperCase());
			}
			rs = ps.executeQuery();
			ElencoUiuDTO elencoUiu = new ElencoUiuDTO();
			while (rs.next()) {
				UiuDTO uiu = new UiuDTO();
				uiu.setFoglio(rs.getString("FOGLIO"));
				uiu.setParticella(rs.getString("PARTICELLA"));
				uiu.setSubalterno(rs.getString("UNIMM"));
				uiu.setClasse(rs.getString("CLASSE"));
				uiu.setCategoria(rs.getString("CATEGORIA"));
				uiu.setRendita(rs.getObject("RENDITA") == null ? null : DF.format(rs.getDouble("RENDITA")));
				uiu.setDataInizioVal(rs.getObject("DATA_INIZIO_VAL") == null ? null : new Long(rs.getTimestamp("DATA_INIZIO_VAL").getTime()));
				elencoUiu.getUiu().add(uiu);
			}
			if (elencoUiu.isSetUiu()) {
				resp.setElencoUiu(elencoUiu);
			} else {
				setRespError(resp, "Nessuna UIU trovata per il civico specificato");
			}
			log.debug("SQL ESEGUITO!");

		} else {
			sql = SQL_MAPPALI_VIA;
			ps = conn.prepareStatement(sql);
			log.debug(sql);
			String ente = codEnte.equalsIgnoreCase("DEFAULT") ? DEF_COD_ENTE : codEnte.toUpperCase();
			ps.setString(1, ente);
			log.debug("param1="+ ente);
			ps.setInt(2, Integer.parseInt(codiceVia));
			log.debug("param2="+ Integer.parseInt(codiceVia));
			rs = ps.executeQuery();
			ElencoMappaleDTO mappali = new ElencoMappaleDTO();
			while (rs.next()) {
				MappaleDTO mappale = new MappaleDTO();
				mappale.setFoglio(rs.getString("FOGLIO"));
				mappale.setParticella(rs.getString("PARTICELLA"));
				mappale.setDataInizioVal(rs.getObject("DATA_INIZIO_VAL") == null ? null : new Long(rs.getTimestamp("DATA_INIZIO_VAL").getTime()));
				setMappale(conn, mappale);
				mappali.getMappale().add(mappale);
			}
			if (mappali.isSetMappale()) {
				resp.setElencoMappale(mappali);
			} else {
				setRespError(resp, "Nessun mappale trovato per la via specificata");
			}
			log.debug("SQL ESEGUITO!");

		}
		rs.close();
		ps.close();
	}
	
}
