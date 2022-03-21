package it.webred.rulengine.brick.elab.xRedditiAnalitici;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.NotFoundAck;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.utils.StringUtils;

public class InterventiDatiPregressiRedAn extends Command implements Rule {
	
	private static final Logger log = Logger.getLogger(InterventiDatiPregressiRedAn.class.getName());
	
	private static final String ID_FONTE = "31";
	private static final String WHERE_KEY_CONF = "where.redan";
	private static final String WHERE_KEY_CONF_REPLACE = "@@@" + WHERE_KEY_CONF + "@@@";
	private static final String BASE_SQL = "SELECT DISTINCT R.*, A.ANNO_IMPOSTA FROM RED_AN_RIGA R, RED_AN_DATI_ANAGRAFICI A " +
											WHERE_KEY_CONF_REPLACE +
											" AND R.IDE_TELEMATICO = A.IDE_TELEMATICO " +
											"ORDER BY A.ANNO_IMPOSTA, R.IDE_TELEMATICO";
	
	private static final String BASE_SQL_ANNO = "SELECT DISTINCT R.* FROM RED_AN_RIGA_XXXX R " +
											WHERE_KEY_CONF_REPLACE;
	
	private static final String CF_CON_SQL = "SELECT * FROM RED_AN_RIGA " +
											"WHERE TIPO_RECORD = 'E' " +
											"AND IDE_TELEMATICO = ?";
	
	private static final SimpleDateFormat SDF_ID = new SimpleDateFormat("yyyyMMdd hhmmss");
	private static final String TABLE_RB_NAME = "RED_AN_FABBRICATI";
	private static final String TABLE_RA_NAME = "RED_AN_TERRENI";
	
	private String processId;
	private String annoTabella;
	
	//QUADRO RA
	private Properties RAProperties = null; 
	private boolean readRA;
	private Map<String, String> mapDatiRA = new HashMap<String, String>();
	private PreparedStatement pstmtRA = null;
	
	//QUADRO RB
	private Properties RBProperties = null; 
	private boolean readRB;
	private Map<String, String> mapDatiRB = new HashMap<String, String>();
	private PreparedStatement pstmtRB = null;
	
	public InterventiDatiPregressiRedAn(BeanCommand bc){
		super(bc);
	}
	
	public InterventiDatiPregressiRedAn(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}
	
	@Override
	public CommandAck run(Context ctx) throws CommandException {		
		
		processId = ctx.getProcessID();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			ParameterService ps = (ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");			
			AmKeyValueExt param = ps.getAmKeyValueExtByKeyFonteComune(WHERE_KEY_CONF, ctx.getBelfiore(), ID_FONTE);
			String whereRedan = param == null ? null : param.getValueConf().trim().toUpperCase();
			if (whereRedan == null || whereRedan.equals("")) {
				return (new NotFoundAck("PROCEDURA DI INTERVENTI SUI DATI DEI REDDITI ANALITICI NON ESEGUIBILE; VALORIZZARE LA PROPRIETA\' DI CONFIGURAZIONE " + WHERE_KEY_CONF));
			}
			
			conn = ctx.getConnection("DWH_" + ctx.getBelfiore());
			conn.setAutoCommit(false);
			
			pstmtRB = conn.prepareStatement(getSqlRB());
			pstmtRA = conn.prepareStatement(getSqlRA());
			
			if (!whereRedan.startsWith("WHERE ")) {
				whereRedan = "WHERE " + whereRedan;
			}
			
			String currentLine = null;
			String ideTel = null;
			String annoImp = null;
			String annoCorrente = null;
			int cont = 1;
			int contTot = 1;
			
			String sql = BASE_SQL.replace(WHERE_KEY_CONF_REPLACE, whereRedan);
			annoTabella = null;
			String whereRedanTest = whereRedan.replace(" ", "").replace("'", "");
			if (whereRedanTest.startsWith("WHERER.ANNO_IMPOSTA=") && whereRedanTest.length() == 24) {
				annoTabella = whereRedanTest.substring(20, 24);
				log.debug("PROCEDURA DI INTERVENTI SUI DATI DEI REDDITI ANALITICI RICHIESTA PER ANNO: " + annoTabella);
				sql = BASE_SQL_ANNO.replace(WHERE_KEY_CONF_REPLACE, whereRedan).replace("XXXX", annoTabella);
			}
			log.debug("PROCEDURA DI INTERVENTI SUI DATI DEI REDDITI ANALITICI ESEGUITA CON QUERY: " + sql);
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				currentLine = rs.getString("RIGA") == null ? null : rs.getString("RIGA").trim();
				ideTel = rs.getString("IDE_TELEMATICO") == null ? null : rs.getString("IDE_TELEMATICO").trim();
				annoImp = rs.getString("ANNO_IMPOSTA") == null ? null : rs.getString("ANNO_IMPOSTA").trim();
				if (currentLine != null && ideTel != null && annoImp != null) {
					//load properties per anno
					if (annoCorrente == null || !annoCorrente.equals(annoImp)) {
						readRA = loadRAProperties("quadroRA_A" + annoImp + "M3_C");
						readRB = loadRBProperties("quadroRB_A" + annoImp + "M3_C");						
						annoCorrente = annoImp;
					}
					
					elabora(conn, annoImp, ideTel, currentLine);
					
					cont++;
					contTot++;
					if(cont > 10000){
						cont = 1;
						conn.commit();
						log.debug("Istante di esecuzione commit ogni 10000 righe (totale " + contTot + " righe) interventi sui dati dei redditi analitici: " + new Date(System.currentTimeMillis()));
					}
				}
			}
			
			//riunisco in un solo record i fabbricati e i terreni che provenendo da righe diverse (moduli multipli) sono stati riportati su record diversi con id duplicato 
			mergeFabbDuplicati(conn);
			mergeTerrDuplicati(conn);
			
			conn.commit();
			
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error(e1);
			}
			
			log.error("Errore: ", e);
			ErrorAck ea = new ErrorAck(e.getMessage());
			return (ea);
		} finally {
			try {
				DbUtils.close(rs);
				DbUtils.close(pstmt);
				DbUtils.close(pstmtRB);
				DbUtils.close(pstmtRA);
				DbUtils.close(conn);
			} catch (SQLException e) {
				log.error(e);
			}
		}
		
		return (new ApplicationAck("INTERVENTI SUI DATI DEI REDDITI ANALITICI ESEGUITI CON SUCCESSO"));
	}
	
	protected boolean loadRBProperties(String filename) {
		String fileName = filename + ".properties";
		RBProperties = new Properties();
		try {
			RBProperties.load(getClass().getResourceAsStream(fileName));
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	protected boolean loadRAProperties(String filename) {
		String fileName = filename + ".properties";
		RAProperties = new Properties();
		try {
			RAProperties.load(getClass().getResourceAsStream(fileName));
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	protected String getRBProperty(String propName) {
		try {
			String p = RBProperties.getProperty(propName);
		return p;
		} catch (Exception e) {
			return null;
		}
	}
	
	protected String getRAProperty(String propName) {
		try {
			String p = RAProperties.getProperty(propName);
		return p;
		} catch (Exception e) {
			return null;
		}
	}
	
	protected void elabora(Connection conn, String annoImp, String ideTel, String currentLine) throws Exception {
		if (ideTel != null) {
			String cfConiuge = null;
			
			if(readRB) {
				String cfStart = getRBProperty("E.codfisc.start");
				if (cfStart != null){
					int cfEnd = Integer.valueOf(getRBProperty("E.codfisc.end"));
					cfConiuge = getCfConiuge(conn, ideTel, Integer.valueOf(cfStart), cfEnd);
				}
				if (cfConiuge != null) {
					elaboraQuadroRB(currentLine, annoImp, ideTel, cfConiuge);
				}
			}
			
			if(readRA) {
				String cfStart = getRAProperty("E.codfisc.start");
				if (cfStart != null){
					int cfEnd = Integer.valueOf(getRAProperty("E.codfisc.end"));
					cfConiuge = getCfConiuge(conn, ideTel, Integer.valueOf(cfStart), cfEnd);
				}
				if (cfConiuge != null) {
					elaboraQuadroRA(currentLine, annoImp, ideTel, cfConiuge);
				}
			}
		}
		
	}
	
	protected String getCfConiuge(Connection conn, String ideTel, int codFiscStart, int codFiscEnd) throws Exception {
		String sql = CF_CON_SQL;
		if (annoTabella != null) {
			sql = sql.replace("RED_AN_RIGA", "RED_AN_RIGA_" + annoTabella);
		}
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, ideTel);
		ResultSet rs = pstmt.executeQuery();
		String riga = null;
		while (rs.next()) {
			riga = rs.getString("RIGA") == null ? null : rs.getString("RIGA").trim();
		}
		DbUtils.close(rs);
		DbUtils.close(pstmt);
		return riga == null ? null : riga.substring(codFiscStart, codFiscEnd);
	}
	
	protected boolean containsOnlyNumbers(String str) {
		if(str !=null){
		    for (int i = 0; i < str.length(); i++) {
		      if (!Character.isDigit(str.charAt(i)))
		        return false;
		    }
		}
		return true;
	}
	
	protected void elaboraQuadroRB(String currentLine, String annoImp, String ideTel, String cfConiuge) {
		String fabbCorrenteNum = null;
		String fabbModNum = null;
		
		String fabbUltimoNum = null;
		String fabbUltimoMod = null;
		
		String modello = currentLine.substring(11, 12);
	
		int rigaFStart = Integer.valueOf(getRBProperty("rigafabb.start"));
		String rigaFabbricati = currentLine.substring(rigaFStart);
		String rigaFStartWith1 = getRBProperty("rigafabb.startwith1");
		String rigaFStartWith2 = getRBProperty("rigafabb.startwith2");
		String rigaFStartWith3 = getRBProperty("rigafabb.startwith3");
		if(rigaFabbricati.startsWith(rigaFStartWith1) 
				|| rigaFabbricati.startsWith(rigaFStartWith2)
				|| rigaFabbricati.startsWith(rigaFStartWith3)){
			
			String split = getRBProperty("rigafabb.split");
			String[] rb = rigaFabbricati.split(split);
			for(int j = 1; j<rb.length; j++){
				try{
					fabbCorrenteNum = rb[j].length()>2?rb[j].substring(0,3):rb[j];
					String datoNum = rb[j].length()>5?rb[j].substring(3,6):rb[j];
					fabbModNum = rb[j].length()>10?rb[j].substring(6,11):rb[j];
					if(fabbCorrenteNum.startsWith("0") && datoNum.startsWith("0")){
						Integer datoCorrenteNum = new Integer(datoNum);
												
						if(j>1){
							 fabbUltimoNum = rb[j-1].substring(0,3);
							 fabbUltimoMod = rb[j-1].substring(6,11);
							if(!fabbCorrenteNum.equals(fabbUltimoNum) || !fabbModNum.equals(fabbUltimoMod)){
								salvaFabbricato(ideTel, annoImp, cfConiuge, modello, fabbUltimoNum, mapDatiRB, fabbUltimoMod);
								mapDatiRB = new HashMap<String, String>();
							}
						}
						
						//mappatura dati
						String data = rb[j].length()>30? rb[j].substring(12, 30): rb[j].substring(12);
						while (data.startsWith("+") || data.startsWith("0"))
							data = data.substring(1);
						
						String col = getRBProperty("col." + datoCorrenteNum);
						if(col != null){
							if("possesso".equals(col) && containsOnlyNumbers(data))
								data = String.valueOf((new Double(data).doubleValue()/100000));
							mapDatiRB.put(col, data.trim());
						}
						
					}
					
					}catch(Exception e){
						log.debug("elaboraQuadroRB - Riga Fabbricato:"+rigaFabbricati);
						log.debug("elaboraQuadroRB - Elaborazione Split (cod."+split+") riga Fabbricato in errore (non bloccante):" +rb[j]);
						log.debug("elaboraQuadroRB - "+e.getMessage());
					}
				}					
				salvaFabbricato(ideTel, annoImp, cfConiuge, modello, fabbUltimoNum, mapDatiRB, fabbUltimoMod);
				mapDatiRB = new HashMap<String, String>();					
		}
	}
	
	protected void elaboraQuadroRA(String currentLine, String annoImp, String ideTel, String cfConiuge) {
		String terrCorrenteNum = null;
		String terrModNum=null;
		
		String terrUltimoNum = null;
		String terrUltimoMod = null;
		
		String modello = currentLine.substring(11, 12);
		
		int rigaFStart = Integer.valueOf(getRAProperty("rigaterr.start"));
		String rigaTerreni = currentLine.substring(rigaFStart);
		String rigaFStartWith1 = getRAProperty("rigaterr.startwith1");
		String rigaFStartWith2 = getRAProperty("rigaterr.startwith2");
		String rigaFStartWith3 = getRAProperty("rigaterr.startwith3");
		if(rigaTerreni.startsWith(rigaFStartWith1) 
				|| rigaTerreni.startsWith(rigaFStartWith2)
				|| rigaTerreni.startsWith(rigaFStartWith3)){
			
			String split = getRAProperty("rigaterr.split");
			String[] rb = rigaTerreni.split(split);
			for(int j = 1; j<rb.length; j++){
				try{
					terrCorrenteNum = rb[j].length()>2?rb[j].substring(0,3):rb[j];
					String datoNum = rb[j].length()>5?rb[j].substring(3,6):rb[j];
					terrModNum =rb[j].length()>10?rb[j].substring(6,11):rb[j];
					if(terrCorrenteNum.startsWith("0") && datoNum.startsWith("0")){
						Integer datoCorrenteNum = new Integer(datoNum);
						
						if(j>1){
							terrUltimoNum = rb[j-1].substring(0,3);
							terrUltimoMod = rb[j-1].substring(6,11);
							if(!terrCorrenteNum.equals(terrUltimoNum) || !terrModNum.equals(terrUltimoMod)){
								salvaTerreno(ideTel, annoImp, cfConiuge, modello, terrUltimoNum, mapDatiRA,terrUltimoMod);
								mapDatiRA = new HashMap<String, String>();
							}
						}
						
						//mappatura dati
						String data = rb[j].length()>30? rb[j].substring(12, 30): rb[j].substring(12);
						while (data.startsWith("+") || data.startsWith("0"))
							data = data.substring(1);
						
						String col = getRAProperty("col." + datoCorrenteNum);
						if(col != null){
							if("possesso".equals(col) && containsOnlyNumbers(data))
								data = String.valueOf((new Double(data).doubleValue()/100000));
							mapDatiRA.put(col, data.trim());
						}
						
					}
					
					}catch(Exception e){
						log.debug("elaboraQuadroRA - Riga Terreno:"+rigaTerreni);
						log.debug("elaboraQuadroRA - Elaborazione Split (cod."+split+") riga Terreno in errore (non bloccante):" +rb[j]);
						log.debug("elaboraQuadroRA - "+e.getMessage());
					}
					
				}
				salvaTerreno(ideTel, annoImp, cfConiuge, modello, terrCorrenteNum, mapDatiRA,terrModNum);
				mapDatiRA = new HashMap<String, String>();
		}
	}
	
	protected void salvaFabbricato(String ideTel, String annoImp, String codFisc, String tipoModello, String num, Map<String, String> mapDati, String modulo){
		if(num!=null && modulo!=null && containsOnlyNumbers(mapDati.get("giorni"))) {
			try {
				Integer riga = new Integer(num);
				//Inserisco solo i righi RB1...RB8 (gli altri, es. in UNICO sono informazioni diverse)
				if(riga<=8){
					String idOrig = getHash(annoImp+" "+tipoModello+" "+ideTel+" "+codFisc + " " + num + " " + modulo);
				
					pstmtRB.clearParameters();
					pstmtRB.setString(5, ideTel);
					pstmtRB.setString(6, codFisc);
					pstmtRB.setString(7, tipoModello);
					pstmtRB.setString(8, num);
					pstmtRB.setString(9, modulo);
					pstmtRB.setString(10, mapDati.get("rendita"));
					pstmtRB.setString(11, mapDati.get("utilizzo"));
					pstmtRB.setString(12, mapDati.get("giorni"));
					pstmtRB.setString(13, mapDati.get("possesso"));
					pstmtRB.setString(14, mapDati.get("canloc"));
					pstmtRB.setString(15, mapDati.get("casipart"));
					pstmtRB.setString(16, mapDati.get("cont"));
					pstmtRB.setString(17, mapDati.get("imponibile"));
					pstmtRB.setString(18, mapDati.get("aquila")); // Campo dichiarazioni anno imposta 2010
					pstmtRB.setString(19, mapDati.get("comune"));
					pstmtRB.setString(20, mapDati.get("ici"));
					pstmtRB.setString(21, annoImp);
					
					/*Filtro comune rimosso:i record di tipo continuazione potrebbero non avere il comune valorizzato*/
					/*Caricare tutti i fabbricati*/
					
				//	if(!filtroComune || checkDomicilio(belfiore, comune) ){
					int lastParams = setDefaultParameters(pstmtRB, 21, idOrig, getHash(idOrig));

					pstmtRB.setString(++lastParams, mapDati.get("codCanone"));
					pstmtRB.setString(++lastParams, mapDati.get("cedolareSecca"));
					pstmtRB.setString(++lastParams, mapDati.get("impTasOrd"));
					pstmtRB.setString(++lastParams, mapDati.get("impCedSec21"));
					pstmtRB.setString(++lastParams, mapDati.get("impCedSec19"));
					pstmtRB.setString(++lastParams, mapDati.get("eseIMU"));
					pstmtRB.setString(++lastParams, mapDati.get("abitazionePrincipale"));
					pstmtRB.setString(++lastParams, mapDati.get("immobiliNonLocati"));
					pstmtRB.setString(++lastParams, mapDati.get("abitazionePrincipaleNonIMU"));
					
					pstmtRB.executeUpdate();
				}
				
			}catch(NumberFormatException nfe){
				//log.warn("Errore: ", nfe);
			}catch (SQLException e) {
				log.error("Errore: ", e);
			}
		}
	}
	
	protected void salvaTerreno(String ideTel, String annoImp, String codFisc, String tipoModello, String num, Map<String, String> mapDati, String modulo){
		
		if(num!=null && modulo!=null && containsOnlyNumbers(mapDati.get("giorni"))) {
			String idOrig = getHash(annoImp+" "+tipoModello+" "+ideTel+" "+codFisc + " " + num + " " + modulo);
			try {
				
				pstmtRA.clearParameters();
				pstmtRA.setString(5, ideTel);
				pstmtRA.setString(6, codFisc);
				pstmtRA.setString(7, tipoModello);
				pstmtRA.setString(8, num);
				pstmtRA.setString(9, modulo);
				pstmtRA.setString(10, mapDati.get("dominicale"));
				pstmtRA.setString(11, mapDati.get("titolo"));
				pstmtRA.setString(12, mapDati.get("agrario"));
				pstmtRA.setString(13, mapDati.get("giorni"));
				pstmtRA.setString(14, mapDati.get("possesso"));
				pstmtRA.setString(15, mapDati.get("canloc"));
				pstmtRA.setString(16, mapDati.get("casipart"));
				pstmtRA.setString(17, mapDati.get("cont"));
				pstmtRA.setString(18, mapDati.get("domimponibile"));
				pstmtRA.setString(19, mapDati.get("agrimponibile"));
				pstmtRA.setString(20, annoImp);
				
				int lastParams = setDefaultParameters(pstmtRA, 20, idOrig, getHash(idOrig));
				
				pstmtRA.setString(++lastParams, mapDati.get("eseIMU"));
				pstmtRA.setString(++lastParams, mapDati.get("colDirIAP"));
				pstmtRA.setString(++lastParams, mapDati.get("domNonImponibile"));
	
				pstmtRA.executeUpdate();
				
			}catch(NumberFormatException nfe){
				//log.warn("Errore: ", nfe);
			}catch (SQLException e) {
				log.error("idOrig:"+idOrig+"-Errore: ", e);
			}
		}
	}
	
	protected int setDefaultParameters(PreparedStatement pstm, int lastParam, String idOrig, String hash) throws SQLException{
		
		String idExt = idOrig + "   " + ID_FONTE;
		String id = SDF_ID.format(new Date()) + StringUtils.padding(idExt, 55, ' ', true) ;
		
		pstm.setString(1, id);
		pstm.setString(2, idExt);
		pstm.setString(3, idOrig);
		pstm.setLong(4, new Long(ID_FONTE).longValue());
		
		pstm.setDate(++lastParam, new java.sql.Date(new Date().getTime()));
		pstm.setDate(++lastParam, null);
		pstm.setDate(++lastParam, new java.sql.Date(new Date().getTime()));
		pstm.setDate(++lastParam, new java.sql.Date(new Date().getTime()));
		pstm.setDate(++lastParam, null);
		pstm.setString(++lastParam, hash);
		pstm.setDate(++lastParam, null);
		pstm.setDate(++lastParam, null);
		pstm.setLong(++lastParam, new Long(0));
		pstm.setString(++lastParam, processId);
		
		return lastParam;
	}
	
	protected String getSqlRA(){
		String sqlRa = "insert into "
				+ TABLE_RA_NAME
				+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return sqlRa;
	}
	
	
	protected String getSqlRB(){
		String sqlRb = "insert into "
				+ TABLE_RB_NAME
				+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return sqlRb;
	}
	
	protected String getHash(String value){
		MessageDigest md = null;
		String hash = null;
		
		try{
			md = MessageDigest.getInstance("SHA");
		}
		catch (NoSuchAlgorithmException e){
			log.error("Errore: ", e);
		}
		
		if (value != null) {
			md.update(value.getBytes());
	
			byte[] b = md.digest();
			hash = new String(StringUtils.toHexString(b));	
		}
		
		return hash;
	}
	
	protected void mergeTerrDuplicati(Connection conn) {
		Statement st1 = null;
		Statement st2 = null;
		ResultSet rs1 = null;
		List<String> lstId = new ArrayList<String>();
		HashMap<String,String> mapDati = new HashMap<String,String>();
		
		log.debug("Merge Terreni duplicati in corso...");
		try{
			st1 = conn.createStatement();
			st2 = conn.createStatement();
			pstmtRA = conn.prepareStatement(getSqlRA());
			
			rs1 = st1.executeQuery("select distinct id_ext from " + TABLE_RA_NAME + " group by id_ext having count(id_ext)>1");
			while (rs1.next())
				lstId.add(rs1.getString("id_ext"));
			
			log.debug("Trovati " + lstId.size() + " terreni duplicati.");
			
			for(String id : lstId){
				rs1 = st1.executeQuery("select * from " + TABLE_RA_NAME + " where id_ext = '" + id + "'");
				String ideTel = null;
				String annoImp = null;
				String tipoModello = null;
				String codFisc = null;
				String num = null;
				String modulo = null;
				
				boolean merge = true;
				while (rs1.next()) {
					
					ideTel = rs1.getString("ide_telematico");
					annoImp = rs1.getString("anno_imposta");
					codFisc = rs1.getString("codice_fiscale");
					num = rs1.getString("num_terr");
					modulo = rs1.getString("modulo");
					tipoModello = rs1.getString("tipo_modello");
					
					merge = merge && elaboraDato("dominicale", rs1.getString("red_dominicale"), mapDati);
					merge = merge && elaboraDato("titolo", rs1.getString("titolo"), mapDati);
					merge = merge && elaboraDato("agrario", rs1.getString("red_agrario"), mapDati);
					merge = merge && elaboraDato("giorni", rs1.getString("giorni"), mapDati);
					merge = merge && elaboraDato("possesso", rs1.getString("possesso"), mapDati);
					merge = merge && elaboraDato("canloc", rs1.getString("canone_aff"), mapDati);
					merge = merge && elaboraDato("casipart", rs1.getString("casi_part"), mapDati);
					merge = merge && elaboraDato("cont", rs1.getString("continuazione"), mapDati);
					merge = merge && elaboraDato("domimponibile", rs1.getString("dom_imponibile"), mapDati);
					merge = merge && elaboraDato("agrimponibile", rs1.getString("agr_imponibile"), mapDati);
				
				}
				
				if(merge){
					//rimuovo le righe
					String qry0 = "delete from " + TABLE_RA_NAME + " where id_ext = '" + id + "'";
					st2.executeUpdate(qry0);
					log.debug(qry0);
					//inserisco la nuova riga fusa	
					salvaTerreno(ideTel, annoImp, codFisc, tipoModello, num, mapDati, modulo);
				}
				mapDati = new HashMap<String,String>();
			}
			
			log.debug("Merge Terreni duplicati concluso!");
			
		}catch(Exception e){
			log.error("Errore: ", e);
		}finally{
			try {
				DbUtils.close(rs1);
				DbUtils.close(st1);
				DbUtils.close(st2);
				DbUtils.close(pstmtRA);
			} catch (SQLException e) {
				log.error(e);
			}
		}
	}
	
	protected void mergeFabbDuplicati(Connection conn){
		Statement st1 = null;
		Statement st2 = null;
		ResultSet rs1 = null;
		List<String> lstId = new ArrayList<String>();
		HashMap<String,String> mapDati = new HashMap<String,String>();
		
		log.debug("Merge Fabbricati duplicati in corso...");
		
		try{
			st1 = conn.createStatement();
			st2 = conn.createStatement();
			pstmtRB = conn.prepareStatement(getSqlRB());
			
			rs1 = st1.executeQuery("select distinct id_ext from " + TABLE_RB_NAME +" group by id_ext having count(id_ext)>1");
			while (rs1.next())
				lstId.add(rs1.getString("id_ext"));
			
			log.debug("Trovati " + lstId.size() + " fabbricati duplicati.");
			
			for(String id : lstId){
				rs1 = st1.executeQuery("select * from " + TABLE_RB_NAME + " where id_ext = '" + id + "'");
				String ideTel = null;
				String annoImp = null;
				String tipoModello = null;
				String codFisc = null;
				String num = null;
				String modulo = null;
				
				boolean merge = true;
				while (rs1.next()) {
					
					ideTel = rs1.getString("ide_telematico");
					annoImp = rs1.getString("anno_imposta");
					codFisc = rs1.getString("codice_fiscale");
					num = rs1.getString("num_fabb");
					modulo = rs1.getString("modulo");
					tipoModello = rs1.getString("tipo_modello");
					
					merge = merge && elaboraDato("rendita", rs1.getString("rendita"), mapDati);
					merge = merge && elaboraDato("utilizzo", rs1.getString("utilizzo"), mapDati);
					merge = merge && elaboraDato("giorni", rs1.getString("giorni"), mapDati);
					merge = merge && elaboraDato("possesso", rs1.getString("possesso"), mapDati);
					merge = merge && elaboraDato("canloc", rs1.getString("canone_loc"), mapDati);
					merge = merge && elaboraDato("casipart", rs1.getString("casi_part"), mapDati);
					merge = merge && elaboraDato("cont", rs1.getString("continuazione"), mapDati);
					merge = merge && elaboraDato("imponibile", rs1.getString("imponibile"), mapDati);
					merge = merge && elaboraDato("comune", rs1.getString("comune"), mapDati);
					merge = merge && elaboraDato("ici", rs1.getString("ici"), mapDati);
					merge = merge && elaboraDato("aquila", rs1.getString("cedolare_aq"), mapDati);
				
				}
				
				if(merge){
					//rimuovo le righe
					String qry1 = "delete from " + TABLE_RB_NAME + " where id_ext = '" + id + "'";
					st2.executeUpdate(qry1);
					log.debug(qry1);
					//inserisco la nuova riga fusa	
					salvaFabbricato(ideTel, annoImp, codFisc, tipoModello, num, mapDati, modulo);
				}
				mapDati = new HashMap<String,String>();
			}
			
			log.debug("Merge Fabbricati duplicati concluso!");
			
		}catch(Exception e){
			log.error("Errore: ", e);
		}finally{
			try {
				DbUtils.close(rs1);
				DbUtils.close(st1);
				DbUtils.close(st2);
				DbUtils.close(pstmtRB);
			} catch (SQLException e) {
				log.error(e);
			}
		}
	}
	
	protected boolean elaboraDato (String nomeDato, String campoRs, HashMap<String,String> mapDati) {
		boolean merge = true;
		if (mapDati.get(nomeDato) == null && campoRs != null)
			mapDati.put(nomeDato, campoRs);
		else if (mapDati.get(nomeDato) != null && campoRs != null && !mapDati.get(nomeDato).equals(campoRs))
			merge = false;
		
		return merge;
	}

}
