package it.webred.verificaCoordinate.logic;

import java.nio.charset.CodingErrorAction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import it.webred.verificaCoordinate.dto.request.VerificaCoordinateRequestDTO;
import it.webred.verificaCoordinate.dto.response.CivicoDTO;
import it.webred.verificaCoordinate.dto.response.ElencoCivicoDTO;
import it.webred.verificaCoordinate.dto.response.ElencoVieDTO;
import it.webred.verificaCoordinate.dto.response.VerificaCoordinateResponseDTO;
import it.webred.verificaCoordinate.dto.response.ViaDTO;
import it.webred.verificaCoordinate.util.GestioneStringheVie;

public class ValidazioneDatiToponomasticiLogic extends VerificaCoordinateLogic {
	
	private static final Logger log = Logger.getLogger(ValidazioneDatiToponomasticiLogic.class.getName());
	
	private static final String SQL_NOME_VIA_LIKE = "SELECT * FROM SITIDSTR " +
								"WHERE NVL(DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND UPPER(NOME) LIKE ?";
	
	private static final String SQL_CIVICI_VIA = "SELECT SITIDSTR.PKID_STRA, SITIDSTR.PREFISSO, SITIDSTR.NOME, SITICIVI.CIVICO " +
								"FROM SITIDSTR, SITICIVI " +
								"WHERE SITIDSTR.PKID_STRA = SITICIVI.PKID_STRA " +
								"AND NVL(SITIDSTR.DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND NVL(SITICIVI.DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND SITICIVI.PKID_STRA = ?";

	
	public ValidazioneDatiToponomasticiLogic(String codEnte, Connection extConn) {
		super(codEnte, extConn);
	}

	public VerificaCoordinateResponseDTO verifica(VerificaCoordinateRequestDTO params) {
		VerificaCoordinateResponseDTO resp = new VerificaCoordinateResponseDTO();
		
		log.info("Inizio validazione dati toponomastici");
		Connection conn = null;
		try {
			conn = getConnection();
			
			resp = verificaNomeViaLike(conn, params);
			if (!isTopoValid(resp)) {
				return resp;
			}
			resp = verificaNomeVia(params, resp.getElencoVie());
			if (!isTopoValid(resp)) {
				return resp;
			}
			resp = verificaPrefisso(conn, params, resp.getElencoVie());
			if (!isTopoValid(resp)) {
				return resp;
			}
			resp = verificaCivicoEsponente(conn, params, resp.getElencoVie());
			if (!isTopoValid(resp)) {
				return resp;
			}
			
		} catch (Exception e) {
			resp = new VerificaCoordinateResponseDTO();
			setRespError(resp, "Si è verificato un errore nella validazione dei dati toponomastici: " + e.getMessage());
		} finally {
			try {
				closeConnection(conn, false);
			} catch (Exception e) {
				log.error("Errore nella validazione dei dati toponomastici", e);
			}
		}
		log.info("Fine validazione dati toponomastici");
		return resp;	
	}
	
	private VerificaCoordinateResponseDTO verificaNomeViaLike(Connection conn, VerificaCoordinateRequestDTO params) throws Exception {
		VerificaCoordinateResponseDTO resp = new VerificaCoordinateResponseDTO();
		String nomeVia = params.getDatiToponomastici().getNomeVia();
		if (nomeVia != null && !nomeVia.equals("")) {
			nomeVia = "%" + nomeVia.toUpperCase() + "%";
			PreparedStatement ps = conn.prepareStatement(SQL_NOME_VIA_LIKE);
			log.debug(SQL_NOME_VIA_LIKE);
			ps.setString(1, nomeVia);
			log.debug("param1="+ nomeVia);
			ResultSet rs = ps.executeQuery();
			ElencoVieDTO vie = new ElencoVieDTO();
			while (rs.next()) {
				ViaDTO via = new ViaDTO();
				via.setPrefisso(rs.getObject("PREFISSO") == null ? null : rs.getString("PREFISSO").trim());
				via.setNome(rs.getObject("NOME") == null ? null : rs.getString("NOME").trim());
				via.setCodiceVia(rs.getString("PKID_STRA"));
				vie.getVia().add(via);
			}
			rs.close();
			ps.close();
			log.debug("SQL ESEGUITO");
			if (vie.isSetVia()) {
				resp.setElencoVie(vie);
			} else {
				setRespError(resp, "Nessuna via trovata per il nome via specificato");
			}
		}
		return resp;
	}
	
	private VerificaCoordinateResponseDTO verificaNomeVia(VerificaCoordinateRequestDTO params, ElencoVieDTO vie) throws Exception {
		VerificaCoordinateResponseDTO resp = new VerificaCoordinateResponseDTO();
		String nomeVia = params.getDatiToponomastici().getNomeVia();
		boolean match = false;
		ViaDTO myVia = null;
		for (ViaDTO via : vie.getVia()) {
			String myNomeVia = via.getNome();
			if (nomeVia != null && myNomeVia != null && !nomeVia.equals("") && !myNomeVia.equals("") &&
			nomeVia.equalsIgnoreCase(myNomeVia)) {
				match = true;
				myVia = via;
				break;
			}
		}
		if (match) {
			vie = new ElencoVieDTO();
			vie.getVia().add(myVia);			
		} else {
			setRespInfo(resp, "Nessuna corrispondenza esatta, tra le vie trovate, per il nome via specificato");
		}
		resp.setElencoVie(vie);
		return resp;
	}
	
	private VerificaCoordinateResponseDTO verificaPrefisso(Connection conn, VerificaCoordinateRequestDTO params, ElencoVieDTO vie) throws Exception {
		VerificaCoordinateResponseDTO resp = new VerificaCoordinateResponseDTO();
		resp.setElencoVie(vie);
		String prefissoParam = params.getDatiToponomastici().getTipoArea();
		if (prefissoParam != null && !prefissoParam.equals("")) {
			prefissoParam = GestioneStringheVie.trovaPrefissoUnivoco(conn, prefissoParam);
			ViaDTO via = vie.getVia().get(0); //a questo punto dovrebbe esserci una ed una sola via...
			String prefissoVia = GestioneStringheVie.trovaPrefissoUnivoco(conn, via.getPrefisso());
			boolean ok = (prefissoParam != null && prefissoVia != null && !prefissoParam.equals("") && !prefissoVia.equals("") &&
			prefissoParam.equalsIgnoreCase(prefissoVia));
			if (!ok) {
				resp.setElencoVie(null);
				setRespError(resp, "Non c'è corrispondenza tra il prefisso via specificato e quello della via trovata per il nome via specificato");
			}
		}
		return resp;
	}
	
	private VerificaCoordinateResponseDTO verificaCivicoEsponente(Connection conn, VerificaCoordinateRequestDTO params, ElencoVieDTO vie) throws Exception {
		VerificaCoordinateResponseDTO resp = new VerificaCoordinateResponseDTO();
		String civicoParam = params.getDatiToponomastici().getCivico();
		if (civicoParam != null && !civicoParam.equals("")) {
			ViaDTO viaParam = vie.getVia().get(0); //a questo punto dovrebbe esserci una ed una sola via...
			String codiceVia = viaParam.getCodiceVia();
			PreparedStatement ps = conn.prepareStatement(SQL_CIVICI_VIA);
			log.debug(SQL_CIVICI_VIA);
			ps.setString(1, codiceVia);
			log.debug("param1="+ codiceVia );
			ResultSet rs = ps.executeQuery();
			ElencoCivicoDTO civici = new ElencoCivicoDTO();
			while (rs.next()) {
				CivicoDTO civico = new CivicoDTO();
				ViaDTO via = new ViaDTO();
				via.setPrefisso(rs.getObject("PREFISSO") == null ? null : rs.getString("PREFISSO").trim());
				via.setNome(rs.getObject("NOME") == null ? null : rs.getString("NOME").trim());
				via.setCodiceVia(rs.getString("PKID_STRA"));
				civico.setVia(via);
				civico.setNumero(rs.getObject("CIVICO") == null ? null : rs.getString("CIVICO").trim());
				civici.getCivico().add(civico);
			}
			rs.close();
			ps.close();
			log.debug("SQL ESEGUITO!");
			String esponenteParam = params.getDatiToponomastici().getEsponente();
			boolean hasEsponenteParam = (esponenteParam != null && !esponenteParam.equals(""));
			boolean match = false;
			for (CivicoDTO civico : civici.getCivico()) {
				String numero = civico.getNumero();
				if (hasEsponenteParam) {
					//prevedo varie tipologie di confronto
					String[] paramCtrls = new String[] {
						civicoParam + esponenteParam,
						civicoParam + "/" + esponenteParam,
						civicoParam + " / " + esponenteParam,
						civicoParam + "-" + esponenteParam,
						civicoParam + " - " + esponenteParam,
						civicoParam + " " + esponenteParam
					};
					for (String paramCtrl : paramCtrls) {
						if (numero != null && !numero.equals("") && numero.equalsIgnoreCase(paramCtrl)) {
							match = true;
							break;
						}
					}
					if (match) {
						break;
					}
				} else {
					if (numero != null && !numero.equals("") && numero.equalsIgnoreCase(civicoParam)) {
						match = true;
						break;
					}
				}
			}
			if (!match) {
				if (hasEsponenteParam) {
					setRespError(resp, "Nella via trovata non esistono civici con numero ed esponente specificati");
				} else {
					resp.setElencoCivico(civici);
					setRespError(resp, "Nella via trovata non esistono civici con il numero specificato");
				}
				resp.setElencoVie(null);
			} else {
				resp.setElencoVie(vie); //servirà per la validazione dati catastali
			}
		} else {
			resp.setElencoVie(vie); //servirà per la validazione dati catastali
		}
		
		return resp;
	}

}
