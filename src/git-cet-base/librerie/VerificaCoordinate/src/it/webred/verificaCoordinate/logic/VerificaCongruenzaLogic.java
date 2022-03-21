package it.webred.verificaCoordinate.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.apache.log4j.Logger;

import it.webred.verificaCoordinate.dto.request.VerificaCoordinateRequestDTO;
import it.webred.verificaCoordinate.dto.response.AttributoTypeDTO;
import it.webred.verificaCoordinate.dto.response.CoordinateDTO;
import it.webred.verificaCoordinate.dto.response.DatiPrgDTO;
import it.webred.verificaCoordinate.dto.response.DestFunzionaleDTO;
import it.webred.verificaCoordinate.dto.response.ElencoMappaleDTO;
import it.webred.verificaCoordinate.dto.response.ElencoUiuDTO;
import it.webred.verificaCoordinate.dto.response.ElencoVieDTO;
import it.webred.verificaCoordinate.dto.response.GrafDTO;
import it.webred.verificaCoordinate.dto.response.LinkDTO;
import it.webred.verificaCoordinate.dto.response.LinksDTO;
import it.webred.verificaCoordinate.dto.response.MappaleDTO;
import it.webred.verificaCoordinate.dto.response.PrgDTO;
import it.webred.verificaCoordinate.dto.response.RigaTypeDTO;
import it.webred.verificaCoordinate.dto.response.UiuDTO;
import it.webred.verificaCoordinate.dto.response.VerificaCoordinateResponseDTO;
import it.webred.verificaCoordinate.dto.response.VincoliTypeDTO;
import it.webred.verificaCoordinate.dto.response.VincoloTypeDTO;
import it.webred.verificaCoordinate.dto.response.ZonaDecentramentoDTO;
import it.webred.verificaCoordinate.dto.response.ZonaOmogeneaDTO;

public class VerificaCongruenzaLogic extends VerificaCoordinateLogic {
	
	private static final Logger log = Logger.getLogger(VerificaCongruenzaLogic.class.getName());
	
	private static final String SQL_SITIUIU = "SELECT SD.PKID_STRA AS CODICE_VIA, " +
								"REMOVE_LEAD_ZERO(SC.CIVICO) AS CIVICO " +
								"FROM SITIUIU SU, SITICIVI_UIU SCU, SITICIVI SC, SITIDSTR SD " +
								" WHERE SU.COD_NAZIONALE  = ? " +
								"       AND SU.FOGLIO = ? " +
								"       AND SU.PARTICELLA = ? " +
								"       AND SU.PKID_UIU = SCU.PKID_UIU " +
								"       AND SU.DATA_FINE_VAL = TO_DATE ('31/12/9999', 'DD/MM/YYYY') " +
								"       AND SCU.DATA_FINE_VAL = TO_DATE ('31/12/9999', 'DD/MM/YYYY') " +
								"       AND SC.PKID_CIVI  = SCU.PKID_CIVI " +
								"       AND SC.COD_NAZIONALE = SU.COD_NAZIONALE" +
								"       AND SC.DATA_FINE_VAL = TO_DATE ('31/12/9999', 'DD/MM/YYYY')" +
								"       AND SD.COD_NAZIONALE = SC.COD_NAZIONALE" +
								"       AND SC.PKID_STRA = SD.PKID_STRA" +
								"       AND SD.DATA_FINE_VAL = TO_DATE ('31/12/9999', 'DD/MM/YYYY')";
	
	private static final String SQL_SITISUOLO = "SELECT COUNT(1) AS CONTA " +
								"FROM SITICIVI SC, SITIDSTR SD, SITISUOLO SS " +
								"WHERE NVL(SC.DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND NVL(SD.DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND NVL(SS.DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND SC.PKID_STRA = SD.PKID_STRA " +
								"AND SDO_RELATE (SC.SHAPE, SDO_GEOM.SDO_BUFFER(SS.SHAPE, MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', 1313328, 2820083, 0.0050),MDSYS.SDO_DIM_ELEMENT('Y', 3930191, 5220322.5, 0.0050)), '3'), 'MASK=ANYINTERACT QUERYTYPE=WINDOW') = 'TRUE' " +
								"AND SC.COD_NAZIONALE = ? " +
								"AND SD.PKID_STRA = ? " +
								"AND SS.FOGLIO = ? " +
								"AND SS.PARTICELLA = ? " +
								"AND REMOVE_LEAD_ZERO(SC.CIVICO) = ?";
	
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
								"WHERE SU.DATA_FINE_VAL = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND SCU.DATA_FINE_VAL = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND SC.DATA_FINE_VAL = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND SD.DATA_FINE_VAL = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +									
								"AND SU.COD_NAZIONALE = ? " +
								"AND SD.COD_NAZIONALE = SU.COD_NAZIONALE " +
								"AND SC.COD_NAZIONALE = SD.COD_NAZIONALE " +
								"AND SU.PKID_UIU = SCU.PKID_UIU " +
								"AND SCU.PKID_CIVI = SC.PKID_CIVI " +
								"AND SC.PKID_STRA = SD.PKID_STRA " +								
								"AND SD.PKID_STRA = ?";
	
	private static final String SQL_SITIPART = "SELECT COUNT(1) AS CONTA " +
								"FROM SITICIVI SC, SITIDSTR SD, SITIPART SP " +
								"WHERE NVL(SC.DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND NVL(SD.DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND NVL(SP.DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND SC.PKID_STRA = SD.PKID_STRA " +
								"AND SDO_RELATE (SC.SHAPE, SDO_GEOM.SDO_BUFFER(SP.SHAPE, MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', 1313328, 2820083, 0.0050),MDSYS.SDO_DIM_ELEMENT('Y', 3930191, 5220322.5, 0.0050)), '3'), 'MASK=ANYINTERACT QUERYTYPE=WINDOW') = 'TRUE' " +
								"AND SC.COD_NAZIONALE = ? " +
								"AND SD.PKID_STRA = ? " +
								"AND SP.FOGLIO = ? " +
								"AND SP.PARTICELLA = ? " +
								"AND REMOVE_LEAD_ZERO(SC.CIVICO) = ?";
	
	private static final String SQL_MAPPALI_CIVICO_TER = "SELECT DISTINCT SP.FOGLIO, " +
								"REMOVE_LEAD_ZERO(SP.PARTICELLA) AS PARTICELLA, " +
								"SP.DATA_INIZIO_VAL " +
								"FROM SITICIVI SC, SITIDSTR SD, SITIPART SP " +
								"WHERE NVL(SC.DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND NVL(SD.DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND NVL(SP.DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND SC.PKID_STRA = SD.PKID_STRA " +
								"AND SDO_RELATE (SC.SHAPE, SDO_GEOM.SDO_BUFFER(SP.SHAPE, MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', 1313328, 2820083, 0.0050),MDSYS.SDO_DIM_ELEMENT('Y', 3930191, 5220322.5, 0.0050)), '3'), 'MASK=ANYINTERACT QUERYTYPE=WINDOW') = 'TRUE' " +
								"AND SC.COD_NAZIONALE = ? " +
								"AND SD.PKID_STRA = ? " +
								"AND REMOVE_LEAD_ZERO(SC.CIVICO) = ?";
	
	private static final String SQL_MAPPALI_VIA_TER = "SELECT DISTINCT SP.FOGLIO, " +
								"REMOVE_LEAD_ZERO(SP.PARTICELLA) AS PARTICELLA, " +
								"SP.DATA_INIZIO_VAL " +
								"FROM SITICIVI SC, SITIDSTR SD, SITIPART SP " +
								"WHERE NVL(SC.DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND NVL(SD.DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND NVL(SP.DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND SC.PKID_STRA = SD.PKID_STRA " +
								"AND SDO_RELATE (SC.SHAPE, SDO_GEOM.SDO_BUFFER(SP.SHAPE, MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', 1313328, 2820083, 0.0050),MDSYS.SDO_DIM_ELEMENT('Y', 3930191, 5220322.5, 0.0050)), '3'), 'MASK=ANYINTERACT QUERYTYPE=WINDOW') = 'TRUE' " +
								"AND SC.COD_NAZIONALE = ? " +
								"AND SD.PKID_STRA = ?";
	
	private static final String SQL_DT_INI_VAL_MAPPALE = "SELECT MAX(DATA_INIZIO_VAL) AS DATA_INIZIO_VAL " +
								"FROM [NOME_TABELLA] " +
								"WHERE NVL(DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND FOGLIO = ? " +
								"AND PARTICELLA = ?";
	
	private static final String SQL_ZONA_DECENTRAMENTO = "SELECT COD_DECENTRAMENTO FROM SIT_VIE_DECENTRAMENTO " +
								"WHERE CODVIA = ? " +
								"AND ((TIPO = 2 AND NUM_INIZIALE <= ? AND NUM_FINALE >= ? AND MOD(?, 2) = 0) " +
								"OR (TIPO = 3 AND NUM_INIZIALE <= ? AND NUM_FINALE >= ? AND MOD(?, 2) = 1) " +
								"OR TIPO = 1) " +
								"AND ABS(NUM_FINALE - 3) = (SELECT MIN(ABS(NUM_FINALE - 3)) FROM SIT_VIE_DECENTRAMENTO " +
								"WHERE CODVIA = ? " +
								"AND ((TIPO = 2 AND NUM_INIZIALE <= ? AND NUM_FINALE >= ? AND MOD(?, 2) = 0) " +
								"OR (TIPO = 3 AND NUM_INIZIALE <= ? AND NUM_FINALE >= ? AND MOD(?, 2) = 1) " +
								"OR TIPO = 1))";
	
	private static final String SQL_ZONA_DECENTRAMENTO_VIA = "SELECT DISTINCT COD_DECENTRAMENTO FROM SIT_VIE_DECENTRAMENTO " +
								"WHERE CODVIA = ?";
	
	private static final String SQL_GEO_ZONE_SITISUOLO = "SELECT DISTINCT Z.ZONA " +
								"FROM SITISUOLO SS, CST_F205_ZONE_DEC Z " +
								"WHERE NVL(SS.DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND SDO_RELATE (Z.SHAPE, SDO_GEOM.SDO_BUFFER(SS.SHAPE, MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', 1313328, 2820083, 0.0050),MDSYS.SDO_DIM_ELEMENT('Y', 3930191, 5220322.5, 0.0050)), '3'), 'MASK=ANYINTERACT QUERYTYPE=WINDOW') = 'TRUE' " +
								"AND SS.COD_NAZIONALE = ? " +
								"AND SS.FOGLIO = ? " +
								"AND SS.PARTICELLA = ?";
	
	private static final String SQL_GEO_ZONE_SITIPART = "SELECT DISTINCT Z.ZONA " +
								"FROM SITIPART SP, CST_F205_ZONE_DEC Z " +
								"WHERE NVL(SP.DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND SDO_RELATE (Z.SHAPE, SDO_GEOM.SDO_BUFFER(SP.SHAPE, MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', 1313328, 2820083, 0.0050),MDSYS.SDO_DIM_ELEMENT('Y', 3930191, 5220322.5, 0.0050)), '3'), 'MASK=ANYINTERACT QUERYTYPE=WINDOW') = 'TRUE' " +
								"AND SP.COD_NAZIONALE = ? " +
								"AND SP.FOGLIO = ? " +
								"AND SP.PARTICELLA = ?";
	
	private static final String SQL_PRG_ALF = "SELECT * " +
								"FROM CST_F205_PRG_UNION " +
								"WHERE FOGLIO = ? " +
								"AND LPAD(MAPPALE, 5, '0') = ?";
	
	private static final String SQL_PRG_CART_SITISUOLO = "SELECT DISTINCT PRG.FOGLIO, PRG.MAPPALE " +
								"FROM SITISUOLO SS, CST_F205_PRG_UNION PRG " +
								"WHERE NVL(SS.DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND SDO_RELATE (PRG.SHAPE, SDO_DIOGENE.GET_CENTROIDE(SS.SHAPE), 'MASK=ANYINTERACT QUERYTYPE=WINDOW') = 'TRUE' " +
								"AND SS.COD_NAZIONALE = ? " +
								"AND SS.FOGLIO = ? " +
								"AND SS.PARTICELLA = ?";
	
	private static final String SQL_PRG_CART_SITIPART = "SELECT DISTINCT PRG.FOGLIO, PRG.MAPPALE " +
								"FROM SITIPART SP, CST_F205_PRG_UNION PRG " +
								"WHERE NVL(SP.DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND SDO_RELATE (PRG.SHAPE, SDO_DIOGENE.GET_CENTROIDE(SP.SHAPE), 'MASK=ANYINTERACT QUERYTYPE=WINDOW') = 'TRUE' " +
								"AND SP.COD_NAZIONALE = ? " +
								"AND SP.FOGLIO = ? " +
								"AND SP.PARTICELLA = ?";
	
	private static final String SQL_DEST_FUNZ = "SELECT *" +
								"FROM CST_F205_PRG_DEC_DF " +
								"WHERE DEST_FUNZ = ?";
	
	private static final String SQL_UIU_GRAFFATA = "SELECT DISTINCT SEZ, FOGLIO, MAPPALE, SUB " +
								"FROM LOAD_CAT_UIU_ID " +
								"WHERE CODI_FISC_LUNA = ? " +
								"AND GRAFFATO = 'N' " +
								"AND ID_IMM IN (" +
								"SELECT ID_IMM " +
								"FROM LOAD_CAT_UIU_ID " +
								"WHERE CODI_FISC_LUNA = ? " +
								"AND FOGLIO = ? " +
								"AND MAPPALE = ? " +
								"AND SUB = ? " +
								"AND GRAFFATO = 'Y')";
	
	private static final String SQL_VIN_SITISUOLO = "SELECT V.* " +
								"FROM SITISUOLO SS, [NOME_TABELLA] V " +
								"WHERE NVL(SS.DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND SDO_RELATE (V.SHAPE, SDO_DIOGENE.GET_CENTROIDE(SS.SHAPE), 'MASK=ANYINTERACT QUERYTYPE=WINDOW') = 'TRUE' " +
								"AND UPPER(V.STATO) = 'ATTIVO' " +
								"AND SS.COD_NAZIONALE = ? " +
								"AND SS.FOGLIO = ? " +
								"AND SS.PARTICELLA = ?";
	
	private static final String SQL_VIN_SITIPART = "SELECT V.* " +
								"FROM SITIPART SP, [NOME_TABELLA] V " +
								"WHERE NVL(SP.DATA_FINE_VAL, TO_DATE('31/12/9999', 'DD/MM/YYYY')) = TO_DATE('31/12/9999', 'DD/MM/YYYY') " +
								"AND SDO_RELATE (V.SHAPE, SDO_DIOGENE.GET_CENTROIDE(SP.SHAPE), 'MASK=ANYINTERACT QUERYTYPE=WINDOW') = 'TRUE' " +
								"AND UPPER(V.STATO) = 'ATTIVO' " +
								"AND SP.COD_NAZIONALE = ? " +
								"AND SP.FOGLIO = ? " +
								"AND SP.PARTICELLA = ?";
	
	private static final String SQL_LAT_LONG = "SELECT T.Y LAT, T.X LON " +
								"FROM SITIPART_3D P, " +
								"TABLE (SDO_UTIL.GETVERTICES (SDO_CS.TRANSFORM (P.SHAPE_PP, " +
								"MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', 1313328, 2820083, 0.0050),MDSYS.SDO_DIM_ELEMENT('Y', 3930191, 5220322.5, 0.0050)), " +
								"8307))) T " +
								"WHERE P.COD_NAZIONALE = ? " +
								"AND P.FOGLIO = ? " +
								"AND P.PARTICELLA = ?";
	
	private static final String DESC_ALLEGATO_D = "Link alla nota della destinazione funzionale";
	private static final String DESC_ALLEGATO_A = "Link all'accessorio del prg";
	private static final String DESC_ALLEGATO_R = "Link al vincolo radar aeroporto di Linate";
	private static final String DESC_ALLEGATO_V = "Link al dettaglio del verde privato";
	private static final String DESC_ALLEGATO_Z = "Link alla nota della zona omogenea";
	
	private static final String TABELLA_VIN_ARCH = "CST_F205_VIN_ARCH";
	private static final String TABELLA_VIN_AMB = "CST_F205_VIN_AMB";
	private static final String TABELLA_VIN_MONUM = "CST_F205_VIN_MONUM";
	//private static final String TABELLA_VIN_NAV = "";
	
	private static final String VIN_ARCHEOLOGICO = "Archeologico";
	private static final String VIN_AMBIENTALE = "Ambientale";
	private static final String VIN_MONUMENTALE = "Monumentale";
	//private static final String VIN_NAVIGLI = "Navigli";
	
	private static final String NOME_ATTR_TIPO_VINCOLO = "TIPO_VINCOLO";
	private static final String NOME_ATTR_DATA_VINCOLO = "DATA_VINCOLO";
	private static final String NOME_ATTR_RIF_CATAST = "RIF_CATAST";
	private static final String NOME_ATTR_AMBITO = "AMBITO";
	private static final String NOME_ATTR_CODICE = "CODICE";
	private static final String NOME_ATTR_ALLEGATO = "ALLEGATO";
	private static final String NOME_ATTR_ARCHIVIO_E = "ARCHIVIO_E";
	/*private static final String NOME_ATTR_TIPOLOGIA = "TIPOLOGIA";
	private static final String NOME_ATTR_VINCOLO = "VINCOLO";
	private static final String NOME_ATTR_DESCRIZION = "DESCRIZION";
	private static final String NOME_ATTR_ALLEGATO_NAVIGLI = "ALLEGATO";*/

	private static final String LABEL_ATTR_TIPO_VINCOLO = "Tipologia del vincolo";
	private static final String LABEL_ATTR_DATA_VINCOLO = "Data di apposizione";
	private static final String LABEL_ATTR_RIF_CATAST = "Rimando al decreto";
	private static final String LABEL_ATTR_AMBITO = "Tipo di Ambito del vincolo";
	private static final String LABEL_ATTR_CODICE = "Codice Alfanumerico del vincolo";
	private static final String LABEL_ATTR_ALLEGATO = "Link";
	private static final String LABEL_ATTR_ARCHIVIO_E = "Archivio Sovrintendenza";
	/*private static final String LABEL_ATTR_TIPOLOGIA = "Descrizione Tipologia";
	private static final String LABEL_ATTR_VINCOLO = "Tipo di vincolo";
	private static final String LABEL_ATTR_DESCRIZION = "Descrizione";
	private static final String LABEL_ATTR_ALLEGATO_NAVIGLI = "Link alla norma generale";*/

	private static final DecimalFormat DF = new DecimalFormat();
	static {
		DF.setGroupingUsed(false);
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator(',');
		DF.setDecimalFormatSymbols(dfs);
		DF.setMaximumFractionDigits(2);
	}
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");

	
	public VerificaCongruenzaLogic(String codEnte, Connection extConn) {
		super(codEnte, extConn);
	}
	
	public VerificaCoordinateResponseDTO verifica(VerificaCoordinateRequestDTO params, ElencoVieDTO vie) {
		VerificaCoordinateResponseDTO resp = new VerificaCoordinateResponseDTO();
		
		log.info("Inizio verifica congruenza");
		Connection conn = null;
		try {
			conn = getConnection();
			
			String tipoCatasto = params.getDatiCatastali().getTipoCatasto().value();
			if (tipoCatasto.equalsIgnoreCase("Fabbricati")) {
				resp = verificaCongruenzaFabbricati(conn, params, vie);
			} else if (tipoCatasto.equalsIgnoreCase("Terreni")) {
				resp = verificaCongruenzaTerreni(conn, params, vie);
			}
			
		} catch (Exception e) {
			resp = new VerificaCoordinateResponseDTO();
			log.error("Errore verifica:" + params.toString(), e );
			setRespError(resp, "Si è verificato un errore nella verifica della congruenza dei dati: " + e.getMessage());
		} finally {
			try {
				closeConnection(conn, false);
			} catch (Exception e) {
				log.error("Errore nella verifica della congruenza dei dati", e);
			}
		}
		
		if (resp.getOk() != null && resp.getOk().getDesc() != null && !resp.getOk().getDesc().equals("") &&
		(resp.getError() == null || resp.getError().size() == 0)) {
			try {
				//valori attesi
				CalcoloValoriAttesiLogic valAttLogic = new CalcoloValoriAttesiLogic(codEnte, extConn);
				valAttLogic.calcola(params, resp);
			} catch (Exception e) {
				log.error("Errore nella lettura dei valori attesi", e);
			}
		}

		log.info("Fine verifica congruenza");
		return resp;
	}
	
	private VerificaCoordinateResponseDTO verificaCongruenzaFabbricati(Connection conn, VerificaCoordinateRequestDTO params, ElencoVieDTO vie) throws Exception {
		VerificaCoordinateResponseDTO resp = new VerificaCoordinateResponseDTO();
		
		log.info("Inizio verifica congruenza fabbricati");
		//parametri catastali
		String foglio = params.getDatiCatastali().getFoglio();
		String mappale = padding(params.getDatiCatastali().getMappale(), 5, '0', true);
		String subalterno = params.getDatiCatastali().getSubalterno();
		//parametri toponomastici
		String codiceVia = vie.getVia().get(0).getCodiceVia();
		String civico = params.getDatiToponomastici().getCivico();
		String esponente = params.getDatiToponomastici().getEsponente();
		
		String[] civEsps = null;
		if (civico != null && !civico.equals("")) {
			if (esponente == null || esponente.equals("")) {
				civEsps = new String[] {civico};
			} else {
				civEsps = new String[] {
					civico + esponente,
					civico + "/" + esponente,
					civico + " / " + esponente,
					civico + "-" + esponente,
					civico + " - " + esponente,
					civico + " " + esponente
				};
			}
		}		
		
		String sql = SQL_SITIUIU;
		if (subalterno != null && !subalterno.equals("")) {
			sql += " AND SU.UNIMM = ?";
		}
		PreparedStatement ps = conn.prepareStatement(sql);
		String code = codEnte.equalsIgnoreCase("DEFAULT") ? DEF_COD_ENTE : codEnte.toUpperCase();
		ps.setString(1, code);
		ps.setInt(2, Integer.parseInt(foglio));
		ps.setString(3, mappale);
		log.debug("SQL_SITIUIU=" + sql);
		log.debug(code + "-" +  foglio + "-" + mappale);
		
		if (subalterno != null && !subalterno.equals("")) {
			ps.setInt(4, Integer.parseInt(subalterno));
		}
		ResultSet rs = ps.executeQuery();
		log.debug("QUERY ESEGUITA");
		boolean ok = false;
		while (rs.next()) {
			if (civico != null && !civico.equals("")) {
				for (String civEsp : civEsps) {
					if (codiceVia.equalsIgnoreCase(rs.getString("CODICE_VIA")) &&
						civEsp.equalsIgnoreCase(rs.getString("CIVICO"))) {
						ok = true;
					}
				}
			} else {
				if (codiceVia.equalsIgnoreCase(rs.getString("CODICE_VIA"))) {
					ok = true;
				}
			}
		}
		rs.close();
		ps.close();
		log.debug("ciclo ESEGUITO");
		
		if (ok) {
			setOkMappale(conn, "SITIUIU", params, resp, vie);
		} else {
			if (civico != null && !civico.equals("")) {
				//verifica della congruenza nel catasto cartografico fabbricati
				sql = SQL_SITISUOLO;
				if (esponente != null && !esponente.equals("")) {
					sql = sql.replace("AND REMOVE_LEAD_ZERO(SC.CIVICO) = ?", "AND REMOVE_LEAD_ZERO(SC.CIVICO) IN(");
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
				ps.setString(1, code);
				ps.setInt(2, Integer.parseInt(codiceVia));
				ps.setInt(3, Integer.parseInt(foglio));
				ps.setString(4, mappale);
				log.info("SQL_SITISUOLO" + sql);
				log.info(code + "-" + codiceVia + "-" +  foglio + "-" + mappale);
				if (esponente == null || esponente.equals("")) {
					ps.setString(5, civico.toUpperCase());
				}
				rs = ps.executeQuery();
				ok = false;
				while (rs.next()) {
					ok = rs.getObject("CONTA") != null && rs.getInt("CONTA") > 0;
				}
				rs.close();
				ps.close();
				
				if (ok) {
					setOkMappale(conn, "SITISUOLO", params, resp, vie);
				} else {
					setRespError(resp, "La verifica della congruenza dei dati ha avuto esito negativo");
					if (subalterno != null && !subalterno.equals("")) {
						//recupero l'elenco delle uiu
						sql = SQL_UIU_CIVICO;
						if (esponente != null && !esponente.equals("")) {
							sql = sql.replace("AND REMOVE_LEAD_ZERO(SC.CIVICO) = ?", "AND REMOVE_LEAD_ZERO(SC.CIVICO) IN(");
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
						ps.setString(1, code);
						ps.setInt(2, Integer.parseInt(codiceVia));
						if (esponente == null || esponente.equals("")) {
							ps.setString(3, civico.toUpperCase());
						}
						log.debug("SQL_UIU_CIVICO" + sql);
						log.debug(code + "-" + codiceVia );
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
						//recupero l'elenco dei mappali
						sql = SQL_MAPPALI_VIA;
						if (esponente != null && !esponente.equals("")) {
							sql += " AND REMOVE_LEAD_ZERO(SC.CIVICO) IN(";
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
						} else {
							sql += " AND REMOVE_LEAD_ZERO(SC.CIVICO) = ?";
						}
						ps = conn.prepareStatement(sql);
						ps.setString(1, code);
						ps.setInt(2, Integer.parseInt(codiceVia));
						log.debug("SQL_MAPPALI_VIA" + sql);
						log.debug(code + "-" + codiceVia );
						if (esponente == null || esponente.equals("")) {
							ps.setString(3, civico.toUpperCase());
							log.debug("-" + civico.toUpperCase() );
						}
						rs = ps.executeQuery();
						ElencoMappaleDTO mappali = new ElencoMappaleDTO();
						while (rs.next()) {
							MappaleDTO mapp = new MappaleDTO();
							mapp.setFoglio(rs.getString("FOGLIO"));
							mapp.setParticella(rs.getString("PARTICELLA"));
							mapp.setDataInizioVal(rs.getObject("DATA_INIZIO_VAL") == null ? null : new Long(rs.getTimestamp("DATA_INIZIO_VAL").getTime()));
							setMappale(conn, mapp);
							mappali.getMappale().add(mapp);
						}						
						if (mappali.isSetMappale()) {
							resp.setElencoMappale(mappali);
						} else {
							setRespError(resp, "Nessun mappale trovato per la via specificata");
						}
						log.debug("SQL ESEGUITO");
					}
				}
			} else {
				setRespError(resp, "La verifica della congruenza dei dati ha avuto esito negativo");
				//recupero l'elenco dei mappali
				sql = SQL_MAPPALI_VIA;
				log.debug("La verifica della congruenza dei dati ha avuto esito negativo");
				log.debug("SQL_MAPPALI_VIA" + sql);
				ps = conn.prepareStatement(sql);
				ps.setString(1, codEnte.equalsIgnoreCase("DEFAULT") ? DEF_COD_ENTE : codEnte.toUpperCase());
				ps.setInt(2, Integer.parseInt(codiceVia));
				rs = ps.executeQuery();
				ElencoMappaleDTO mappali = new ElencoMappaleDTO();
				while (rs.next()) {
					MappaleDTO mapp = new MappaleDTO();
					mapp.setFoglio(rs.getString("FOGLIO"));
					mapp.setParticella(rs.getString("PARTICELLA"));
					mapp.setDataInizioVal(rs.getObject("DATA_INIZIO_VAL") == null ? null : new Long(rs.getTimestamp("DATA_INIZIO_VAL").getTime()));
					setMappale(conn, mapp);
					mappali.getMappale().add(mapp);
				}
				if (mappali.isSetMappale()) {
					resp.setElencoMappale(mappali);
				} else {
					setRespError(resp, "Nessun mappale trovato per la via specificata");
				}
				log.debug("SQL ESEGUITO!");
			}
		}
		log.info("Fine verifica congruenza fabbricati");
		
		return resp;
	}
	
	private VerificaCoordinateResponseDTO verificaCongruenzaTerreni(Connection conn, VerificaCoordinateRequestDTO params, ElencoVieDTO vie) throws Exception {
		VerificaCoordinateResponseDTO resp = new VerificaCoordinateResponseDTO();
		
		log.info("Inizio verifica congruenza terreni");
		//parametri catastali
		String foglio = params.getDatiCatastali().getFoglio();
		String mappale = padding(params.getDatiCatastali().getMappale(), 5, '0', true);
		//parametri toponomastici
		String codiceVia = vie.getVia().get(0).getCodiceVia();
		String civico = params.getDatiToponomastici().getCivico();
		String esponente = params.getDatiToponomastici().getEsponente();
		
		String[] civEsps = null;
		if (civico != null && !civico.equals("")) {
			if (esponente == null || esponente.equals("")) {
				civEsps = new String[] {civico};
			} else {
				civEsps = new String[] {
					civico + esponente,
					civico + "/" + esponente,
					civico + " / " + esponente,
					civico + "-" + esponente,
					civico + " - " + esponente,
					civico + " " + esponente
				};
			}
		}
		
		String sql = SQL_SITIPART;		
		if (esponente != null && !esponente.equals("")) {
			sql = sql.replace("AND REMOVE_LEAD_ZERO(SC.CIVICO) = ?", "AND REMOVE_LEAD_ZERO(SC.CIVICO) IN(");
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
		PreparedStatement ps = conn.prepareStatement(sql);
		String ente = codEnte.equalsIgnoreCase("DEFAULT") ? DEF_COD_ENTE : codEnte.toUpperCase();
		ps.setString(1, ente);		
		ps.setInt(2, Integer.parseInt(codiceVia));
		ps.setInt(3, Integer.parseInt(foglio));
		ps.setString(4, mappale);		
		if (esponente == null || esponente.equals("")) {
			ps.setString(5, civico.toUpperCase());
		}
		log.debug(sql);
		log.debug(ente + "-" + codiceVia + "-" + foglio + "-" + mappale);

		ResultSet rs = ps.executeQuery();
		boolean ok = false;
		while (rs.next()) {
			ok = rs.getObject("CONTA") != null && rs.getInt("CONTA") > 0;
		}
		rs.close();
		ps.close();
		log.debug("SQL ESEGUITO!");
		
		if (ok) {
			setOkMappale(conn, "SITIPART", params, resp, vie);
		} else {
			setRespError(resp, "La verifica della congruenza dei dati ha avuto esito negativo");			
			if (civico != null && !civico.equals("")) {
				sql = SQL_MAPPALI_CIVICO_TER;
				if (esponente != null && !esponente.equals("")) {
					sql = sql.replace("AND REMOVE_LEAD_ZERO(SC.CIVICO) = ?", "AND REMOVE_LEAD_ZERO(SC.CIVICO) IN(");
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
			} else {
				sql = SQL_MAPPALI_VIA_TER;
			}
			ps = conn.prepareStatement(sql);
			ente = codEnte.equalsIgnoreCase("DEFAULT") ? DEF_COD_ENTE : codEnte.toUpperCase();
			ps.setString(1, ente);			
			ps.setInt(2, Integer.parseInt(codiceVia));			
			if (civico != null && !civico.equals("") && (esponente == null || esponente.equals(""))) {
				ps.setString(3, civico.toUpperCase());
			}
			log.debug(sql);
			log.debug(ente + "-" + codiceVia + "-" + civico.toUpperCase());

			rs = ps.executeQuery();
			ElencoMappaleDTO mappali = new ElencoMappaleDTO();
			while (rs.next()) {
				MappaleDTO mapp = new MappaleDTO();
				mapp.setFoglio(rs.getString("FOGLIO"));
				mapp.setParticella(rs.getString("PARTICELLA"));
				mapp.setDataInizioVal(rs.getObject("DATA_INIZIO_VAL") == null ? null : new Long(rs.getTimestamp("DATA_INIZIO_VAL").getTime()));
				setMappale(conn, mapp);
				mappali.getMappale().add(mapp);
			}
			if (mappali.isSetMappale()) {
				resp.setElencoMappale(mappali);
			} else {
				setRespError(resp, "Nessun mappale trovato per " + 
									((civico != null && !civico.equals("")) ? "il civico specificato" : "la via specificata"));
			}
			rs.close();
			ps.close();
			log.debug("SQL ESEGUITO!");
		}
		log.info("Fine verifica congruenza terreni");
		
		return resp;
	}
	
	private void setOkMappale(Connection conn, String table, VerificaCoordinateRequestDTO params, VerificaCoordinateResponseDTO resp, ElencoVieDTO vie) throws Exception {
		setRespOk(resp, "La verifica della congruenza dei dati ha avuto esito positivo");
		
		ElencoMappaleDTO mappali = new ElencoMappaleDTO();
		MappaleDTO mappale = new MappaleDTO();
		mappale.setFoglio(params.getDatiCatastali().getFoglio());
		mappale.setParticella(params.getDatiCatastali().getMappale());
		setMappale(conn, table, mappale);
		mappali.getMappale().add(mappale);
		resp.setElencoMappale(mappali);
		
		log.info("Inizio ricerca zona decentramento");
		boolean ok = false;
		String codiceVia = vie.getVia().get(0).getCodiceVia();
		String civico = params.getDatiToponomastici().getCivico();
		String tipoCatasto = params.getDatiCatastali().getTipoCatasto().value();
		int intCodiceVia = -1;
		try {
			intCodiceVia = Integer.parseInt(codiceVia);
		} catch (Exception e) {}
		if (civico != null && !civico.equals("")) {			
			//zona decentramento per via - civico		
			int intCivico = -1;
			try {
				intCivico = Integer.parseInt(civico);
			} catch (Exception e) {}
			
			String sql = SQL_ZONA_DECENTRAMENTO;
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, intCodiceVia);
			ps.setInt(2, intCivico);
			ps.setInt(3, intCivico);
			ps.setInt(4, intCivico);
			ps.setInt(5, intCivico);
			ps.setInt(6, intCivico);
			ps.setInt(7, intCivico);
			ps.setInt(8, intCodiceVia);
			ps.setInt(9, intCivico);
			ps.setInt(10, intCivico);
			ps.setInt(11, intCivico);
			ps.setInt(12, intCivico);
			ps.setInt(13, intCivico);
			ps.setInt(14, intCivico);
			log.debug(sql);
			log.debug(intCodiceVia + "-" + intCivico + "(x6)" + intCodiceVia + "-" + intCivico + "(x6)");

			ResultSet rs = ps.executeQuery();			
			while (rs.next()) {
				String zona = rs.getString("COD_DECENTRAMENTO");
				if (zona != null && !zona.equals("")) {
					ZonaDecentramentoDTO zonaDecentramento = new ZonaDecentramentoDTO();
					zonaDecentramento.setZona(zona);
					mappale.setZonaDecentramento(zonaDecentramento);
					ok = true;
				}
			}
			rs.close();
			ps.close();
			log.debug("SQL ESEGUITO");
			
			
		} else {
			//zona decentramento per via
			String sql = SQL_ZONA_DECENTRAMENTO_VIA;
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, intCodiceVia);
			log.debug(sql);
			log.debug(intCodiceVia );

			ResultSet rs = ps.executeQuery();			
			while (rs.next()) {
				String zona = rs.getString("COD_DECENTRAMENTO");
				if (zona != null && !zona.equals("")) {
					ZonaDecentramentoDTO zonaDecentramento = new ZonaDecentramentoDTO();
					zonaDecentramento.setZona(zona);
					mappale.setZonaDecentramento(zonaDecentramento);
					ok = true;
				}
			}
			rs.close();
			ps.close();
			log.debug("SQL ESEGUITA!");
		}
		
		if (!ok) {
			//ricerca intersezione tra civico e geometrie delle zone
			String sql = null;
			if (tipoCatasto.equalsIgnoreCase("Fabbricati")) {
				sql = SQL_GEO_ZONE_SITISUOLO;
			} else if (tipoCatasto.equalsIgnoreCase("Terreni")) {
				sql = SQL_GEO_ZONE_SITIPART;
			}
			PreparedStatement ps = conn.prepareStatement(sql);
			String ente = codEnte.equalsIgnoreCase("DEFAULT") ? DEF_COD_ENTE : codEnte.toUpperCase();
			ps.setString(1, ente);
			ps.setInt(2, Integer.parseInt(mappale.getFoglio()));
			ps.setString(3, padding(mappale.getParticella(), 5, '0', true));
			log.debug(sql);
			log.debug(ente +"-"+ Integer.parseInt(mappale.getFoglio()) + "-"+  padding(mappale.getParticella(), 5, '0', true));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String zona = rs.getString("ZONA");
				if (zona != null && !zona.equals("")) {
					ZonaDecentramentoDTO zonaDecentramento = new ZonaDecentramentoDTO();
					zonaDecentramento.setZona(zona);
					mappale.setZonaDecentramento(zonaDecentramento);
				}
			}
			rs.close();
			ps.close();
			log.debug("SQL ESEGUITO!");
		}
		log.info("Fine ricerca zona decentramento");
		
		//dati PRG
		//interrogazione alfanumerica
		log.info("Inizio ricerca dati PRG");
		ok = getDatiPrgAlf(conn, mappale, mappale.getFoglio(), mappale.getParticella());
		if (!ok) {
			//interrogazione cartografica
			String sql = null;
			if (tipoCatasto.equalsIgnoreCase("Fabbricati")) {
				sql = SQL_PRG_CART_SITISUOLO;
			} else if (tipoCatasto.equalsIgnoreCase("Terreni")) {
				sql = SQL_PRG_CART_SITIPART;
			}
			PreparedStatement ps = conn.prepareStatement(sql);
			String ente = codEnte.equalsIgnoreCase("DEFAULT") ? DEF_COD_ENTE : codEnte.toUpperCase();
			ps.setString(1, ente);
			ps.setInt(2, Integer.parseInt(mappale.getFoglio()));
			ps.setString(3, padding(mappale.getParticella(), 5, '0', true));
			log.debug(sql);
			log.debug(ente +"-"+ Integer.parseInt(mappale.getFoglio()) + "-"+  padding(mappale.getParticella(), 5, '0', true));

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ok = getDatiPrgAlf(conn, mappale, rs.getString("FOGLIO"), rs.getString("MAPPALE"));
			}
			rs.close();
			ps.close();
			log.debug("SQL ESEGUITA!");
		}
		log.info("Fine ricerca dati PRG");
		
		//vincoli
		log.info("Inizio ricerca dati vincoli");
		getDatiVincoli(conn, mappale, tipoCatasto);
		log.info("Fine ricerca dati vincoli");		
		
		setGraffata(conn, params, resp);

		setCoordinateXY(conn, params, resp);
	}
	
	private void setGraffata(Connection conn, VerificaCoordinateRequestDTO params, VerificaCoordinateResponseDTO resp) throws Exception {
		String tipoCatasto = params.getDatiCatastali().getTipoCatasto().value();
		String subalterno = params.getDatiCatastali().getSubalterno();
		if (tipoCatasto.equalsIgnoreCase("Fabbricati") && subalterno != null && !subalterno.equals("")) {
			log.info("Inizio ricerca eventuali dati UIU principale se UIU graffata");
			String foglio = padding(params.getDatiCatastali().getFoglio(), 4, '0', true); //padding 4
			String mappale = padding(params.getDatiCatastali().getMappale(), 5, '0', true); //padding 5
			subalterno = padding(subalterno, 4, '0', true); //padding 4
			String sql = SQL_UIU_GRAFFATA;
			PreparedStatement ps = conn.prepareStatement(sql);
			String ente = codEnte.equalsIgnoreCase("DEFAULT") ? DEF_COD_ENTE : codEnte.toUpperCase();
			ps.setString(1, ente);
			ps.setString(2, ente);
			ps.setString(3, foglio);
			ps.setString(4, mappale);
			ps.setString(5, subalterno);
			log.debug(sql);
			log.debug(ente +"-"+ ente + "-"+  foglio + "-"+  mappale + "-"+  subalterno);

			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				GrafDTO graf = new GrafDTO();
				graf.setSezione(rs.getString("SEZ"));
				graf.setFoglio(rs.getString("FOGLIO"));
				graf.setParticella(rs.getString("MAPPALE"));
				graf.setUnimm(rs.getString("SUB"));
				resp.setGraf(graf);
			}
			rs.close();
			ps.close();
			log.debug("SQL ESEGUITA!");
			log.info("Fine ricerca eventuali dati UIU principale se UIU graffata");
		}
	}
	
	private void setCoordinateXY(Connection conn, VerificaCoordinateRequestDTO params, VerificaCoordinateResponseDTO resp) throws Exception {
		log.info("Inizio valorizzazione coordinate XY civico");
		
		int foglio = Integer.parseInt(params.getDatiCatastali().getFoglio());
		String particella = padding(params.getDatiCatastali().getMappale(), 5, '0', true); //padding 5
		
		String sql = SQL_LAT_LONG;
		PreparedStatement ps = conn.prepareStatement(sql);
		String ente = codEnte.equalsIgnoreCase("DEFAULT") ? DEF_COD_ENTE : codEnte.toUpperCase();
		ps.setString(1, ente);
		ps.setInt(2, foglio);
		ps.setString(3, particella);
		log.debug(sql);
		log.debug(ente + " - " + foglio + " - " + particella);
		
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			CoordinateDTO coord = new CoordinateDTO();
			coord.setLat(rs.getString("LAT"));
			coord.setLon(rs.getString("LON"));
			resp.setCoordinate(coord);
		}		
		rs.close();
		ps.close();
		
		log.debug("SQL ESEGUITA!");
		log.info("Fine valorizzazione coordinate XY civico");
	}

	private void setMappale(Connection conn, String table, MappaleDTO mappale) throws Exception {
		int foglio = Integer.parseInt(mappale.getFoglio());
		String particella = padding(mappale.getParticella(), 5, '0', true);
		String sql = SQL_DT_INI_VAL_MAPPALE.replace("[NOME_TABELLA]", table);
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, foglio);
		ps.setString(2, particella);
		log.debug(sql);
		log.debug(foglio + "-"+  particella);

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			mappale.setDataInizioVal(rs.getObject("DATA_INIZIO_VAL") == null ? null : new Long(rs.getTimestamp("DATA_INIZIO_VAL").getTime()));
		}		
		log.debug("SQL ESEGUITA!");
		setMappale(conn, mappale);
	}
	
	private boolean getDatiPrgAlf(Connection conn, MappaleDTO mappale, String foglio, String particella) throws Exception {
		boolean ok = false;
		if (foglio != null && !foglio.equals("") && particella != null && !particella.equals("")) {
			String sql = SQL_PRG_ALF;
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(foglio));
			ps.setString(2, padding(particella, 5, '0', true));
			log.debug(sql);
			log.debug(Integer.parseInt(foglio) + "-"+  padding(particella, 5, '0', true));

			ResultSet rs = ps.executeQuery();
			int index = 0;
			while (rs.next()) {
				if (index == 0) {
					long areaMappa = Math.round(rs.getDouble("AREA_MAPPA"));
					long areaMap1 = Math.round(rs.getDouble("AREA_MAP_1"));
					ok = (areaMappa == areaMap1);
				}
				if (ok) {
					PrgDTO prg = new PrgDTO();
					prg.setId(rs.getString("SE_ROW_ID"));
					prg.setDal(rs.getObject("VALIDO_DA") == null ? null : new Long(SDF.parse(rs.getString("VALIDO_DA")).getTime()));
					prg.setAl(rs.getObject("VALIDO_FIN") == null ? null : new Long(SDF.parse(rs.getString("VALIDO_FIN")).getTime()));
					DestFunzionaleDTO destFunzionale = getDestFunzionale(conn, rs);
					prg.setDestFunzionale(destFunzionale);
					ZonaOmogeneaDTO zonaOmogenea = getZonaOmogenea(conn, rs);
					prg.setZonaOmogenea(zonaOmogenea);
					LinksDTO links = getLinks(conn, rs);				
					prg.setLinks(links);
					if (mappale.getDatiPrg() == null) {
						mappale.setDatiPrg(new DatiPrgDTO());
					}
					mappale.getDatiPrg().getPrg().add(prg);		
				}						
				index++;
			}
			rs.close();
			ps.close();
			log.debug("SQL ESEGUITA!");
		}		
		return ok;
	}
	
	private void getDatiVincoli(Connection conn, MappaleDTO mappale, String tipoCatasto) throws Exception {
		String foglio = mappale.getFoglio();
		String particella = mappale.getParticella();
		if (foglio != null && !foglio.equals("") && particella != null && !particella.equals("")) {
			String[] tabelleVincoli = new String[] {TABELLA_VIN_ARCH, TABELLA_VIN_AMB, TABELLA_VIN_MONUM};
			VincoliTypeDTO vincoli = null;
			for (String tabellaVincoli : tabelleVincoli) {
				VincoloTypeDTO vincolo = null;
				String sql = null;
				if (tipoCatasto.equalsIgnoreCase("Fabbricati")) {
					sql = SQL_VIN_SITISUOLO;
				} else if (tipoCatasto.equalsIgnoreCase("Terreni")) {
					sql = SQL_VIN_SITIPART;
				}
				sql = sql.replace("[NOME_TABELLA]", tabellaVincoli);
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, codEnte.equalsIgnoreCase("DEFAULT") ? DEF_COD_ENTE : codEnte.toUpperCase());
				ps.setInt(2, Integer.parseInt(foglio));
				ps.setString(3, padding(particella, 5, '0', true));
				log.debug(sql);
				log.debug(Integer.parseInt(foglio) + "-"+  padding(particella, 5, '0', true));

				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					if (vincolo == null) {
						vincolo = new VincoloTypeDTO();
					}
					RigaTypeDTO riga = new RigaTypeDTO();
					AttributoTypeDTO attributo = null;
					if (tabellaVincoli.equalsIgnoreCase(TABELLA_VIN_ARCH)) {
						attributo = new AttributoTypeDTO();
						attributo.setNome(NOME_ATTR_TIPO_VINCOLO);
						attributo.setLabel(LABEL_ATTR_TIPO_VINCOLO);
						attributo.setValore(rs.getString("TIPO_VINCO"));
						riga.getAttributo().add(attributo);
						attributo = new AttributoTypeDTO();
						attributo.setNome(NOME_ATTR_DATA_VINCOLO);
						attributo.setLabel(LABEL_ATTR_DATA_VINCOLO);
						attributo.setValore(rs.getString("DATA_VINCO"));
						riga.getAttributo().add(attributo);
						attributo = new AttributoTypeDTO();
						attributo.setNome(NOME_ATTR_RIF_CATAST);
						attributo.setLabel(LABEL_ATTR_RIF_CATAST);
						attributo.setValore(rs.getString("RIF_CATAST"));
						riga.getAttributo().add(attributo);
					} else if (tabellaVincoli.equalsIgnoreCase(TABELLA_VIN_AMB)) {
						attributo = new AttributoTypeDTO();
						attributo.setNome(NOME_ATTR_TIPO_VINCOLO);
						attributo.setLabel(LABEL_ATTR_TIPO_VINCOLO);
						attributo.setValore(rs.getString("TIPO_VINCO"));
						riga.getAttributo().add(attributo);
						attributo = new AttributoTypeDTO();
						attributo.setNome(NOME_ATTR_AMBITO);
						attributo.setLabel(LABEL_ATTR_AMBITO);
						attributo.setValore(rs.getString("AMBITO"));
						riga.getAttributo().add(attributo);						
						attributo = new AttributoTypeDTO();
						attributo.setNome(NOME_ATTR_CODICE);
						attributo.setLabel(LABEL_ATTR_CODICE);
						attributo.setValore(rs.getString("CODICE"));
						riga.getAttributo().add(attributo);						
						attributo = new AttributoTypeDTO();
						attributo.setNome(NOME_ATTR_ALLEGATO);
						attributo.setLabel(LABEL_ATTR_ALLEGATO);
						attributo.setValore(rs.getString("ALLEGATO"));
						riga.getAttributo().add(attributo);
					} else if (tabellaVincoli.equalsIgnoreCase(TABELLA_VIN_MONUM)) {
						attributo = new AttributoTypeDTO();
						attributo.setNome(NOME_ATTR_TIPO_VINCOLO);
						attributo.setLabel(LABEL_ATTR_TIPO_VINCOLO);
						attributo.setValore(rs.getString("TIPO_VINCO"));
						riga.getAttributo().add(attributo);						
						attributo = new AttributoTypeDTO();
						attributo.setNome(NOME_ATTR_DATA_VINCOLO);
						attributo.setLabel(LABEL_ATTR_DATA_VINCOLO);
						attributo.setValore(rs.getString("DATA_VINCO"));
						riga.getAttributo().add(attributo);						
						attributo = new AttributoTypeDTO();
						attributo.setNome(NOME_ATTR_ARCHIVIO_E);
						attributo.setLabel(LABEL_ATTR_ARCHIVIO_E);
						attributo.setValore(rs.getString("ARCHIVIO_E"));
						riga.getAttributo().add(attributo);						
						attributo = new AttributoTypeDTO();
						attributo.setNome(NOME_ATTR_RIF_CATAST);
						attributo.setLabel(LABEL_ATTR_RIF_CATAST);
						attributo.setValore(rs.getString("RIF_CATAST"));
						riga.getAttributo().add(attributo);
					}
					vincolo.getRiga().add(riga);
				}
				rs.close();
				ps.close();
				log.debug("SQL ESEGUITA!");
				if (vincolo != null) {
					if (vincoli == null) {
						vincoli = new VincoliTypeDTO();
					}
					if (tabellaVincoli.equalsIgnoreCase(TABELLA_VIN_ARCH)) {
						vincolo.setTipo(VIN_ARCHEOLOGICO);
					} else if (tabellaVincoli.equalsIgnoreCase(TABELLA_VIN_AMB)) {
						vincolo.setTipo(VIN_AMBIENTALE);
					} else if (tabellaVincoli.equalsIgnoreCase(TABELLA_VIN_MONUM)) {
						vincolo.setTipo(VIN_MONUMENTALE);
					}
					vincoli.getVincolo().add(vincolo);
				}
			}
			mappale.setVincoli(vincoli);
		}		
	}
	
	private DestFunzionaleDTO getDestFunzionale(Connection conn, ResultSet rs) throws Exception {
		DestFunzionaleDTO destFunzionale = new DestFunzionaleDTO();
		destFunzionale.setCodice(rs.getString("DESTINAZIO"));
		String sql = SQL_DEST_FUNZ;
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, rs.getString("DESTINAZIO"));
		log.debug(sql);
		log.debug(rs.getString("DESTINAZIO"));

		ResultSet rs1 = ps.executeQuery();
		while (rs1.next()) {
			destFunzionale.setDescrizione(rs1.getString("LEGENDA"));
		}
		rs1.close();
		ps.close();
		log.debug("SQL ESEGUITA!");
		destFunzionale.setNota(rs.getString("NOTE_DESTI"));		
		return destFunzionale;
	}
	
	private ZonaOmogeneaDTO getZonaOmogenea(Connection conn, ResultSet rs) throws Exception {
		ZonaOmogeneaDTO zonaOmogenea = new ZonaOmogeneaDTO();
		zonaOmogenea.setCodice(rs.getString("ZONA_OMOGE"));
		//TODO decodifica descrizione zona omogenea
		zonaOmogenea.setNota(rs.getString("NOTE_ZONA_"));		
		return zonaOmogenea;
	}	
	
	private LinksDTO getLinks(Connection conn, ResultSet rs) throws Exception {
		LinksDTO links = new LinksDTO();
		
		String url = rs.getString("ALLEGATO_D");
		LinkDTO linkD = new LinkDTO();
		linkD.setUrl(url);
		linkD.setDescrizione(DESC_ALLEGATO_D);
		links.getLink().add(linkD);
		
		url = rs.getString("ALLEGATO_A");
		LinkDTO linkA = new LinkDTO();
		linkA.setUrl(url);
		linkA.setDescrizione(DESC_ALLEGATO_A);
		links.getLink().add(linkA);
		
		url = rs.getString("ALLEGATO_R");
		LinkDTO linkR = new LinkDTO();
		linkR.setUrl(url);
		linkR.setDescrizione(DESC_ALLEGATO_R);
		links.getLink().add(linkR);
		
		url = rs.getString("ALLEGATO_V");
		LinkDTO linkV = new LinkDTO();
		linkV.setUrl(url);
		linkV.setDescrizione(DESC_ALLEGATO_V);
		links.getLink().add(linkV);
		
		url = rs.getString("ALLEGATO_Z");
		LinkDTO linkZ = new LinkDTO();
		linkZ.setUrl(url);
		linkZ.setDescrizione(DESC_ALLEGATO_Z);
		links.getLink().add(linkZ);
		
		return links;
	}
	
}
