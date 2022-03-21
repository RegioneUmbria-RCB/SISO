package it.webred.rulengine.brick.elab.anaSan;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;
//import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.Utils;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.utils.StringUtils;

public class CreaFileAnagrafeSanitaria extends Command implements Rule {
	
	private static final Logger log = Logger.getLogger(CreaFileAnagrafeSanitaria.class.getName());
	
	private static final String DEF_DIR_EXP = "expFileAnaSan";
	private static final String DEF_DIR_EXP_DEST = "expFileAnaSanDest";
	private static final String DEF_MAILTO = "filippo.mazzini@umbriadigitale.it";
	
	private static final String ID_FONTE = "1";
	
	private static final String DEF_ANA_SAN_DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
	private static final String DEF_ANA_SAN_CONN_STRING = "jdbc:oracle:thin:@172.29.0.2:1521:MDBWEB";
	private static final String DEF_ANA_SAN_USER_NAME = "mdbweb";
	private static final String DEF_ANA_SAN_PASSWORD = "mdbweb";
	
	private static final String SQL_SELECT = "SELECT * FROM (" + 
											"SELECT DISTINCT T.*, V.SEDIME, V.DESCRIZIONE, V.SEDIME || ' ' || V. DESCRIZIONE AS INDIRIZZO, " +
											"NVL(T.MATRICOLA, " +
											"NVL(T.CODICE_FISCALE, " +
											"NVL((SELECT MIN(TT.CODICE_FISCALE) FROM RE_DEMOG_ANAGRAFE_2_1 TT " +
											"WHERE TT.COGNOME_NOME || '|' || TT.SESSO || '|' || TT.DATA_NASCITA || '|' || TT.COMUNE_NASCITA_COD " +
											"= T.COGNOME_NOME || '|' || T.SESSO || '|' || T.DATA_NASCITA || '|' || T.COMUNE_NASCITA_COD " +
											"AND TT.CODICE_FISCALE IS NOT NULL " +
											//casi di flag stato e codice evento da escludere a priori
											"AND NOT (TT.FLAG_STATO <> 'A' AND TT.CODICE_EVENTO = 'FAM') " +
											"AND NOT (TT.FLAG_STATO <> 'A' AND TT.CODICE_EVENTO = 'IFA') " +
											"AND NOT (TT.FLAG_STATO <> 'A' AND TT.CODICE_EVENTO = 'ALT') " +
											//
											"AND TT.CODICE_EVENTO IS NOT NULL " +
											"AND TT.PROG = (SELECT MIN(TTT.PROG) FROM RE_DEMOG_ANAGRAFE_2_1 TTT " +
											"WHERE TTT.COGNOME_NOME || '|' || TTT.SESSO || '|' || TTT.DATA_NASCITA || '|' || TTT.COMUNE_NASCITA_COD " +
											"= TT.COGNOME_NOME || '|' || TT.SESSO || '|' || TT.DATA_NASCITA || '|' || TT.COMUNE_NASCITA_COD " +
											"AND TTT.CODICE_FISCALE IS NOT NULL " +
											//casi di flag stato e codice evento da escludere a priori
											"AND NOT (TTT.FLAG_STATO <> 'A' AND TTT.CODICE_EVENTO = 'FAM') " +
											"AND NOT (TTT.FLAG_STATO <> 'A' AND TTT.CODICE_EVENTO = 'IFA') " +
											"AND NOT (TTT.FLAG_STATO <> 'A' AND TTT.CODICE_EVENTO = 'ALT') " +
											//
											"AND TTT.CODICE_EVENTO IS NOT NULL)), " +
											"T.COGNOME_NOME || '|' || T.SESSO || '|' || T.DATA_NASCITA || '|' || T.COMUNE_NASCITA_COD))) AS CHIAVE_PERS " +
											"FROM RE_DEMOG_ANAGRAFE_2_1 T " +
											"LEFT JOIN RE_DEMOG_VIE_1_0 V ON V.CODICE_VIA = T.CODICE_VIA AND V.PROCESSID = T.PROCESSID " +
											"WHERE SUBSTR(NVL(T.DATA_REGISTRAZIONE, '99991231'), 1, 6) <= ? " +
											//casi di flag stato e codice evento da escludere a priori
											"AND NOT (T.FLAG_STATO <> 'A' AND T.CODICE_EVENTO = 'FAM') " +
											"AND NOT (T.FLAG_STATO <> 'A' AND T.CODICE_EVENTO = 'IFA') " +
											"AND NOT (T.FLAG_STATO <> 'A' AND T.CODICE_EVENTO = 'ALT') " +
											//
											"AND T.CODICE_EVENTO IS NOT NULL " +
											"AND T.FLAG_INVIATO_ANASAN = 0) " +
											"ORDER BY CHIAVE_PERS, PROG";
	
	private static final String SQL_UPDATE_FL_INV_ANASAN = "UPDATE RE_DEMOG_ANAGRAFE_2_1 T " +
											"SET FLAG_INVIATO_ANASAN = 1 " +
											"WHERE SUBSTR(NVL(T.DATA_REGISTRAZIONE, '99991231'), 1, 6) <= ? " +
											//casi di flag stato e codice evento da escludere a priori
											"AND NOT (T.FLAG_STATO <> 'A' AND T.CODICE_EVENTO = 'FAM') " +
											"AND NOT (T.FLAG_STATO <> 'A' AND T.CODICE_EVENTO = 'IFA') " +
											"AND NOT (T.FLAG_STATO <> 'A' AND T.CODICE_EVENTO = 'ALT') " +
											//
											"AND T.CODICE_EVENTO IS NOT NULL " +
											"AND FLAG_INVIATO_ANASAN = 0";
	
	private static final String SQL_SELECT_COD_BELFIORE = "SELECT COD_NAZIONALE FROM AM_TAB_COMUNI WHERE COD_ISTAT_COMUNE = ?";
	
	private static final String SQL_CTRL_VIA_MATRICOLA = "SELECT CODICE_VIA, NUMERO_CIVICO, BARRATO, SUBBARRATO FROM (" +
														"SELECT ROWNUM AS N, T.* FROM (" +
														"SELECT * FROM RE_DEMOG_ANAGRAFE_2_1 WHERE MATRICOLA = ? AND CODICE_VIA IS NOT NULL ORDER BY PROG DESC) T) " +
														"WHERE N = 1";
	
	private static final String SQL_CTRL_VIA_CF = "SELECT CODICE_VIA, NUMERO_CIVICO, BARRATO, SUBBARRATO FROM (" +
														"SELECT ROWNUM AS N, T.* FROM (" +
														"SELECT * FROM RE_DEMOG_ANAGRAFE_2_1 WHERE CODICE_FISCALE = ? AND CODICE_VIA IS NOT NULL ORDER BY PROG DESC) T) " +
														"WHERE N = 1";
	
	private static final String SQL_CTRL_VIA_ANASAN_CF = "SELECT CM_CISTAT, ST_STRADA FROM (" +
														"SELECT ROWNUM AS N, T.* FROM (" +
														"SELECT CM_CISTAT, ST_STRADA, CASE WHEN PZ_FSTATO = 'A' THEN 1 ELSE 2 END ORD_STATO " +
														"FROM SA_PAZIENTE@ANASAN " +
														"INNER JOIN SA_STRADARIO@ANASAN " +
														"ON PZ_COM_RES = ST_COMUNE " +
														"AND PZ_IND_RES = ST_INDIR " +
														"INNER JOIN SA_COMUNE@ANASAN " +
														"ON PZ_COM_RES = CM_ID " +
														"WHERE PZ_CFIS = ? " +
														"ORDER BY ORD_STATO" +
														") T) WHERE N = 1";
	
	private static final String SQL_CTRL_VIA_ANASAN_DATI_ANA = "SELECT CM_CISTAT, ST_STRADA FROM (" +
															"SELECT ROWNUM AS N, T.* FROM (" +
															"SELECT COMRES.CM_CISTAT, ST_STRADA, CASE WHEN PZ_FSTATO = 'A' THEN 1 ELSE 2 END ORD_STATO " +
															"FROM SA_PAZIENTE@ANASAN " +
															"INNER JOIN SA_STRADARIO@ANASAN " +
															"ON PZ_COM_RES = ST_COMUNE " +
															"AND PZ_IND_RES = ST_INDIR " +
															"INNER JOIN SA_COMUNE@ANASAN COMRES " +
															"ON PZ_COM_RES = COMRES.CM_ID " +
															"INNER JOIN SA_COMUNE@ANASAN COMNAS " +
															"ON PZ_COM_NAS = COMNAS.CM_ID " +
															"WHERE PZ_COGN = ? " +
															"AND PZ_NOME = ? " +
															"AND PZ_SESSO = ? " +
															"AND PZ_DT_NAS = TO_DATE(?, 'DDMMYYYY') " +
															"AND COMNAS.CM_CISTAT = ? " +
															"ORDER BY ORD_STATO" +
															") T) WHERE N = 1";
	
	private static final String SQL_CTRL_COM_RES_ANASAN_CF = "SELECT CM_CISTAT FROM (" +
															"SELECT ROWNUM AS N, T.* FROM (" +
															"SELECT CM_CISTAT, CASE WHEN PZ_FSTATO = 'A' THEN 1 ELSE 2 END ORD_STATO " +
															"FROM SA_PAZIENTE@ANASAN " +
															"INNER JOIN SA_COMUNE@ANASAN " +
															"ON PZ_COM_RES = CM_ID " +
															"WHERE PZ_CFIS = ? " +
															"ORDER BY ORD_STATO" +
															") T) WHERE N = 1";
	
	private static final String SQL_CTRL_COM_RES_ANASAN_DATI_ANA = "SELECT CM_CISTAT FROM (" +
															"SELECT ROWNUM AS N, T.* FROM (" +
															"SELECT COMRES.CM_CISTAT, CASE WHEN PZ_FSTATO = 'A' THEN 1 ELSE 2 END ORD_STATO " +
															"FROM SA_PAZIENTE@ANASAN " +
															"INNER JOIN SA_COMUNE@ANASAN COMRES " +
															"ON PZ_COM_RES = COMRES.CM_ID " +
															"INNER JOIN SA_COMUNE@ANASAN COMNAS " +
															"ON PZ_COM_NAS = COMNAS.CM_ID " +
															"WHERE PZ_COGN = ? " +
															"AND PZ_NOME = ? " +
															"AND PZ_SESSO = ? " +
															"AND PZ_DT_NAS = TO_DATE(?, 'DDMMYYYY') " +
															"AND COMNAS.CM_CISTAT = ? " +
															"ORDER BY ORD_STATO" +
															") T) WHERE N = 1";
	
	private static final String SQL_DEL_EXPORT = "DELETE FROM ANASAN_EXPORT " + 
													"WHERE NOME_FILE_EXPORT IN (?, ?)";
	
	private static final String SQL_INS_EXPORT = "INSERT INTO ANASAN_EXPORT " +
													"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String SQL_SEL_EXPORT = "SELECT * " +
													"FROM ANASAN_EXPORT " +
													"WHERE NOME_FILE_EXPORT IN (?, ?) " +
													"ORDER BY COGNOME, NOME";
	
	private static final String SQL_CTRL_COM_NAS_CMIN = "SELECT * FROM SA_COMUNE@ANASAN WHERE CM_CMIN = ?";
	
	private static final String SQL_CTRL_CAP_ANASAN = "SELECT ST_DA, ST_A, ST_CAP " +
														"FROM SA_STRADARIO@ANASAN, SA_COMUNE@ANASAN " +
														"WHERE ST_COMUNE = CM_ID " + 
														"AND CM_CISTAT = ? " +
														"AND ST_STRADA = ? " +
														"AND NVL(LTRIM(RTRIM(ST_TIPO)), '-') <> ?";
	
	private static final String SQL_DESC_VIA_ANASAN = "SELECT ST_INDIR " +
														"FROM SA_STRADARIO@ANASAN, SA_COMUNE@ANASAN " +
														"WHERE ST_COMUNE = CM_ID " + 
														"AND CM_CISTAT = ? " +
														"AND ST_STRADA = ?";
	
	
	public CreaFileAnagrafeSanitaria(BeanCommand bc){
		super(bc);
	}
	
	public CreaFileAnagrafeSanitaria(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}
	
	@Override
	public CommandAck run(Context ctx) throws CommandException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		PreparedStatement pstmtInt = null;
		ResultSet rsInt = null;
		
		Connection connAnaSan = null;
		PreparedStatement pstmtAnaSan = null;
		ResultSet rsAnaSan = null;
		
		try {
			ParameterService ps = (ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
			
			AmKeyValueExt param = ps.getAmKeyValueExtByKeyFonteComune("dir.exp.file.anasan", ctx.getBelfiore(), ID_FONTE);
			String expPath = param == null ? null : param.getValueConf();
			if (expPath == null || expPath.trim().equals("")) {
				expPath = Utils.getConfigProperty("dir.files.datiDiogene") + File.separator + ctx.getBelfiore() + File.separator + DEF_DIR_EXP;
			}
			
			param = ps.getAmKeyValueExtByKeyFonteComune("dir.exp.dest.file.anasan", ctx.getBelfiore(), ID_FONTE);
			String expPathDest = param == null ? null : param.getValueConf();
			if (expPathDest == null || expPathDest.trim().equals("")) {
				expPathDest = Utils.getConfigProperty("dir.files.datiDiogene") + File.separator + ctx.getBelfiore() + File.separator + DEF_DIR_EXP_DEST;
			}
			
			//TODO mailto per eventuale successiva implementazione invio e-mail
			param = ps.getAmKeyValueExtByKeyFonteComune("mailto.anasan", ctx.getBelfiore(), ID_FONTE);
			String mailTo = param == null ? null : param.getValueConf();
			if (mailTo == null || mailTo.trim().equals("")) {
				mailTo = DEF_MAILTO;
			}
			
			//questo parametro si utilizza solo per forzature, normalmente è null
			param = ps.getAmKeyValueExtByKeyFonteComune("mese.rif.anasan", ctx.getBelfiore(), ID_FONTE);
			String annoMeseStrParam = param == null ? null : param.getValueConf();
			
			param = ps.getAmKeyValueExtByKeyFonteComune("anasan.driverClass", ctx.getBelfiore(), ID_FONTE);
			String anaSanDriCla = param == null ? null : param.getValueConf();
			if (anaSanDriCla == null || anaSanDriCla.trim().equals("")) {
				anaSanDriCla = DEF_ANA_SAN_DRIVER_CLASS;
			}
			
			param = ps.getAmKeyValueExtByKeyFonteComune("anasan.connString", ctx.getBelfiore(), ID_FONTE);
			String anaSanConStr = param == null ? null : param.getValueConf();
			if (anaSanConStr == null || anaSanConStr.trim().equals("")) {
				anaSanConStr = DEF_ANA_SAN_CONN_STRING;
			}
			
			param = ps.getAmKeyValueExtByKeyFonteComune("anasan.userName", ctx.getBelfiore(), ID_FONTE);
			String anaSanUsrNam = param == null ? null : param.getValueConf();
			if (anaSanUsrNam == null || anaSanUsrNam.trim().equals("")) {
				anaSanUsrNam = DEF_ANA_SAN_USER_NAME;
			}
			
			param = ps.getAmKeyValueExtByKeyFonteComune("anasan.password", ctx.getBelfiore(), ID_FONTE);
			String anaSanPwd = param == null ? null : param.getValueConf();
			if (anaSanPwd == null || anaSanPwd.trim().equals("")) {
				anaSanPwd = DEF_ANA_SAN_PASSWORD;
			}
			
			conn = ctx.getConnection("DWH_" + ctx.getBelfiore());
			conn.setAutoCommit(false);
			
			Class.forName(anaSanDriCla);
			connAnaSan = DriverManager.getConnection(anaSanConStr, anaSanUsrNam, anaSanPwd);
			
			File dir = new File(expPath);
			if (!dir.exists()) {
				if (!dir.mkdir()) {
					String errMsg = "Impossibile creare il percorso di destinazione del file";
					log.error("Errore: " + errMsg);
					ErrorAck ea = new ErrorAck(errMsg);
					return (ea);
				}
			}
			
			PrintWriter writer = null;
			PrintWriter writerOk = null;
			PrintWriter writerKo = null;
			String codComuneExp = null;
			
			Calendar gc = new GregorianCalendar();
			gc.add(Calendar.MONTH, -1);
			String annoMeseStr = new SimpleDateFormat("yyyyMM").format(gc.getTime());
			if (annoMeseStrParam != null && !annoMeseStrParam.trim().equals("")) {
				annoMeseStr = annoMeseStrParam;
			}
		    
			pstmt = conn.prepareStatement(SQL_SELECT);
			pstmt.setString(1, annoMeseStr);
			rs = pstmt.executeQuery();
			
			String chiavePers = null;
			String provNasPers = null;
			String linePers = null;
			String codEvePers = "   ";
			String dtEveCod = "        ";
			String dtEveAll = "        ";
			String codComPers = "000000";
			String statoPers = null;
			String indirPers = null;
			String matricolaPers = null;
			String cfPers = null;
			String dtRegPers = null;
			String codEveOrig = null;
			
			int prog = 0;
			int progOk = 0;
			int progKo = 0;
			
			String nomeFile = null;
			File fileOk = null;
			
			boolean forzaTutti = true; //true solo per test, poi rimettere false
			
			while (rs.next()) {
				if (writer == null && writerOk == null && writerKo == null && codComuneExp == null) {
					pstmtInt = conn.prepareStatement(SQL_SELECT_COD_BELFIORE);
					String codIstatComune = getStrValue(rs, "COMUNE_INVIO_COD", null, true, 6, ' ', true);
					pstmtInt.setString(1, codIstatComune);
					rsInt = pstmtInt.executeQuery();
					while (rsInt.next()) {
						codComuneExp = getStrValue(rsInt, "COD_NAZIONALE", null, true, 4, ' ', true);
					}						
					DbUtils.close(rsInt);
					DbUtils.close(pstmtInt);
					
					String meseAnnoStr = annoMeseStr.substring(4) + annoMeseStr.substring(0, 4);
					nomeFile = "com" + codIstatComune + meseAnnoStr;
					
					PreparedStatement pstmtDel = conn.prepareStatement(SQL_DEL_EXPORT);
					pstmtDel.setString(1, nomeFile + ".txt");
					pstmtDel.setString(2, nomeFile + "_scarti.txt");
					pstmtDel.executeUpdate();
					DbUtils.close(pstmtDel);
					
					File file = new File(dir, nomeFile + "_totale.txt");
					writer = new PrintWriter(file);
					fileOk = new File(dir, nomeFile + ".txt");
					writerOk = new PrintWriter(fileOk);
					File fileKo = new File(dir, nomeFile + "_scarti.txt");
					writerKo = new PrintWriter(fileKo);
				}
				
				if (chiavePers != null && !chiavePers.equals(rs.getString("CHIAVE_PERS"))) {
					if (forzaTutti || (codEvePers.trim().equals("NAS") || codEvePers.trim().equals("IMM") || codEvePers.trim().equals("EMI") || codEvePers.trim().equals("MOR")
					|| (dtRegPers != null && dtRegPers.length() == 8 && dtRegPers.substring(0, 6).equals(annoMeseStr)))) {
						//inserisco la persona precedente
						String dtEvePers = dtEveCod == null || dtEveCod.trim().equals("") ? dtEveAll : dtEveCod;
						String line = getStrValue(null, null, ++prog, true, 7, '0', true) +
										linePers.substring(0, 131) +
										codEvePers +
										dtEvePers +
										codComPers +
										linePers.substring(148);
						String[] ctrl = ctrl(codComuneExp, statoPers, codEvePers, codEveOrig, dtEvePers, line, indirPers, matricolaPers, cfPers, provNasPers, conn, connAnaSan, pstmtAnaSan, rsAnaSan);
						boolean isOk = ctrl[0].equalsIgnoreCase("OK");
						String codOk = ctrl[1];
						String codProvComEveNew = ctrl.length > 2 ? ctrl[2] : null;
						if (codProvComEveNew != null) {
							line = line.substring(0, 149) + codProvComEveNew + line.substring(155);
						}
						
						if (codOk.indexOf(",") != 5) {
							line = line.substring(0, 14) + getStrValue(null, null, codOk, true, 5, '0', true) + line.substring(19);
						} else {
							line = line.substring(0, 14) + codOk + line.substring(43);
						}
						
						String cap = line.substring(43, 48).trim();
						String comInvioCod = line.substring(7, 13).trim();
						String codVia = line.substring(14, 19).trim();
						String numCivBarrato = line.substring(20, 43).trim();
						String numCiv = null;
						String barrato = null;
						if (numCivBarrato.indexOf("/") == -1) {
							numCiv = numCivBarrato;
						} else {
							numCiv = numCivBarrato.substring(0, numCivBarrato.indexOf("/"));
							barrato = numCivBarrato.substring(numCivBarrato.indexOf("/") + 1, numCivBarrato.indexOf("/") + 2);
						}
						String capRes = getCapResidenza(conn, cap, comInvioCod, codVia, numCiv, barrato);
						line = line.substring(0, 43) + capRes + line.substring(48);						
						
						if (isOk) {							
							saveLine(conn, getStrValue(null, null, ++progOk, true, 7, '0', true), "1", null, nomeFile + ".txt", line.substring(7));
						} else {
							saveLine(conn, getStrValue(null, null, ++progKo, true, 7, '0', true), "0", ctrl[0], nomeFile + "_scarti.txt", line.substring(7));
						}
					}
					codEvePers = "   ";
					dtEveCod = "        ";
					dtEveAll = "        ";
					codComPers = "000000";
				}

				provNasPers = getStrValue(rs, "PROVINCIA_NASCITA", null, true, 3, '0', true);
				linePers = getLineFromRecord(conn, rs);
				
				String codEve = linePers.substring(131, 134).trim().toUpperCase();
				codEveOrig = codEve;
				String dtEve = linePers.substring(134, 142).trim();
				String codCom = linePers.substring(142, 148);
				if (codEve.trim().equals("IRR")) {
					codEve = "EMI";
				} else if (codEve.trim().equals("RDI")) {
					codEve = "IMM";
				}
				if (codEve.trim().equals("NAS") || codEve.trim().equals("IMM") || codEve.trim().equals("EMI") || codEve.trim().equals("MOR")) {
					codEvePers = codEve;
					dtEveCod = dtEve;
					codComPers = codCom;
				}
				dtEveAll = dtEve;

				chiavePers = rs.getString("CHIAVE_PERS");
				statoPers = rs.getString("FLAG_STATO");
				indirPers = rs.getString("INDIRIZZO");
				matricolaPers = rs.getString("MATRICOLA");
				cfPers = rs.getString("CODICE_FISCALE");
				dtRegPers = rs.getString("DATA_REGISTRAZIONE");
			}
			
			//inserisco l'ultimo
			if (chiavePers != null) {
				if (forzaTutti || (codEvePers.trim().equals("NAS") || codEvePers.trim().equals("IMM") || codEvePers.trim().equals("EMI") || codEvePers.trim().equals("MOR")
				|| (dtRegPers != null && dtRegPers.length() == 8 && dtRegPers.substring(0, 6).equals(annoMeseStr)))) {
					String dtEvePers = dtEveCod == null || dtEveCod.trim().equals("") ? dtEveAll : dtEveCod;
					String line = getStrValue(null, null, ++prog, true, 7, '0', true) +
							linePers.substring(0, 131) +
							codEvePers +
							dtEvePers +
							codComPers +
							linePers.substring(148);					
					String[] ctrl = ctrl(codComuneExp, statoPers, codEvePers, codEveOrig, dtEvePers, line, indirPers, matricolaPers, cfPers, provNasPers, conn, connAnaSan, pstmtAnaSan, rsAnaSan);
					boolean isOk = ctrl[0].equalsIgnoreCase("OK");
					String codOk = ctrl[1];
					String codProvComEveNew = ctrl.length > 2 ? ctrl[2] : null;
					if (codProvComEveNew != null) {
						line = line.substring(0, 149) + codProvComEveNew + line.substring(155);
					}
					
					if (codOk.indexOf(",") != 5) {
						line = line.substring(0, 14) + getStrValue(null, null, codOk, true, 5, '0', true) + line.substring(19);
					} else {
						line = line.substring(0, 14) + codOk + line.substring(43);
					}
					
					String cap = line.substring(43, 48).trim();
					String comInvioCod = line.substring(7, 13).trim();
					String codVia = line.substring(14, 19).trim();
					String numCivBarrato = line.substring(20, 43).trim();
					String numCiv = null;
					String barrato = null;
					if (numCivBarrato.indexOf("/") == -1) {
						numCiv = numCivBarrato;
					} else {
						numCiv = numCivBarrato.substring(0, numCivBarrato.indexOf("/"));
						barrato = numCivBarrato.substring(numCivBarrato.indexOf("/") + 1, numCivBarrato.indexOf("/") + 2);
					}
					String capRes = getCapResidenza(conn, cap, comInvioCod, codVia, numCiv, barrato);
					line = line.substring(0, 43) + capRes + line.substring(48);
					
					if (isOk) {
						saveLine(conn, getStrValue(null, null, ++progOk, true, 7, '0', true), "1", null, nomeFile + ".txt", line.substring(7));
					} else {					
						saveLine(conn, getStrValue(null, null, ++progKo, true, 7, '0', true), "0", ctrl[0], nomeFile + "_scarti.txt", line.substring(7));
					}
				}
			}
			
			DbUtils.close(rs);
			DbUtils.close(pstmt);
			prog = 0;
			progOk = 0;
			progKo = 0;			
			boolean newLine = false;
			boolean newLineOk = false;
			boolean newLineKo = false;
			pstmt = conn.prepareStatement(SQL_SEL_EXPORT);
			pstmt.setString(1, nomeFile + ".txt");
			pstmt.setString(2, nomeFile + "_scarti.txt");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String nomeFileExp = getStrValue(rs, "NOME_FILE_EXPORT", null, false, 0, ' ', false);
				String flagOk = getStrValue(rs, "FLAG_OK", null, false, 0, ' ', false);
				String riga = getStrValue(rs, "RIGA", null, false, 0, ' ', false);
				String msgErr = getStrValue(rs, "MSG_ERR", null, false, 0, ' ', false);
				
				if (nomeFileExp.equals(nomeFile + ".txt") && flagOk.equals("1")) {
					if (newLineOk) {
						writerOk.println();
					}
					writerOk.print(getStrValue(null, null, ++progOk, true, 7, '0', true) + riga);
					newLineOk = true;
				} else if (nomeFileExp.equals(nomeFile + "_scarti.txt") && flagOk.equals("0")) {
					if (newLineKo) {
						writerKo.println();
					}
					writerKo.print(getStrValue(null, null, ++progKo, true, 7, '0', true) + getRigaKo(conn, riga) + "   ----   " + msgErr);
					newLineKo = true;
				}
				
				if (newLine) {
					writer.println();
				}
				writer.print(getStrValue(null, null, ++prog, true, 7, '0', true) + riga);
				newLine = true;
			}			

			if (writerKo != null) writerKo.close();
			if (writerOk != null) writerOk.close();
			if (writer != null) writer.close();
			
			DbUtils.close(pstmt);
			pstmt = conn.prepareStatement(SQL_UPDATE_FL_INV_ANASAN);
			pstmt.setString(1, annoMeseStr);
			pstmt.executeUpdate();			
			
			DbUtils.close(rs);
			DbUtils.close(pstmt);
			
			//TODO (verificare se necessario)
			/*File dirDest = new File(expPathDest);
			if (!dirDest.exists()) {
				if (!dirDest.mkdir()) {
					String errMsg = "Impossibile creare il percorso di destinazione finale del file";
					log.error("Errore: " + errMsg);
					ErrorAck ea = new ErrorAck(errMsg);
					return (ea);
				}
			}			
			FileUtils.copyFile(fileOk, new File(expPathDest, nomeFile + ".txt"));*/
			
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
				DbUtils.close(rsAnaSan);
				DbUtils.close(pstmtAnaSan);
				DbUtils.close(connAnaSan);
				
				DbUtils.close(rsInt);
				DbUtils.close(pstmtInt);
				
				DbUtils.close(rs);
				DbUtils.close(pstmt);
				DbUtils.close(conn);
			} catch (SQLException e) {
				log.error(e);
			}
		}		
		
		return (new ApplicationAck("CREAZIONE FILE ESEGUITA CON SUCCESSO"));
	}
	
	protected String getLineFromRecord(Connection conn, ResultSet rs) throws Exception {
		String line = "";
		
		String[] cognomeNome = getStrValue(rs, "COGNOME_NOME", null, false, 0, ' ', false).split("/");
		String cognome = cognomeNome != null && cognomeNome.length > 0 ? cognomeNome[0].trim() : "";
		String nome = cognomeNome != null && cognomeNome.length > 1 ? cognomeNome[1].trim() : "";
		String sesso = getStrValue(rs, "SESSO", null, true, 1, ' ', false);
		String dtNascita = getStrValue(rs, "DATA_NASCITA", null, true, 8, '0', true);
		String dtNascitaTo = dtNascita.substring(6) + dtNascita.substring(4, 6) + dtNascita.substring(0, 4);
		String comNasCod = getCodComNasFromRs(conn, rs);
		String codFis = getStrValue(rs, "CODICE_FISCALE", null, true, 16, ' ', false);		
		if (comNasCod == null || comNasCod.substring(0, 3).equals("000")) {
			comNasCod = "000000";
		} else if (comNasCod != null && comNasCod.substring(0, 3).trim().equals("")) {
			comNasCod = "   000";
		} else if (comNasCod != null && codFis != null && codFis.substring(11, 12).equalsIgnoreCase("Z")) {
			comNasCod = comNasCod.substring(0, 3) + "000";
		}
		String staCiv = getStrValue(rs, "STATO_CIVILE", null, true, 2, '0', true);
		if (staCiv == null || staCiv.trim().equals("") || staCiv.trim().equals("00")) {
			staCiv = "09";
		}
		String codEve = getStrValue(rs, "CODICE_EVENTO", null, true, 3, ' ', false);
		if (codEve != null) {
			codEve = codEve.toUpperCase();
		}
		if (codEve == null ||
		(!codEve.trim().equals("NAS") && !codEve.trim().equals("IMM") && !codEve.trim().equals("EMI") && !codEve.trim().equals("MOR") && !codEve.trim().equals("IRR") && !codEve.trim().equals("RDI"))) {
			codEve = "   ";
		}
		//String dtEventoOrig = getStrValue(rs, "DATA_EVENTO", null, true, 8, '0', true);
		String dtEvento = getDataEvento(rs);
		String codProvEve = getProvinciaEvento(rs);
		String codComEve = getComuneEvento(rs);
		
		if (codEve.trim().equals("IMM") && 
		(codProvEve == null || codProvEve.trim().equals("") || codProvEve.trim().equals("000")) && 
		(codComEve == null || codComEve.trim().equals("") || codComEve.trim().equals("000"))) {		
			/*String codProvComRes = null;
			PreparedStatement pstmt = conn.prepareStatement(SQL_CTRL_COM_RES_ANASAN_CF);
			pstmt.setString(1, codFis);
			ResultSet rs1 = pstmt.executeQuery();
			while (rs1.next()) {
				codProvComRes = getStrValue(rs1, "CM_CISTAT", null, true, 6, ' ', false);
			}
			DbUtils.close(rs1);
			DbUtils.close(pstmt);
			
			if (codProvComRes == null) {
				pstmt = conn.prepareStatement(SQL_CTRL_COM_RES_ANASAN_DATI_ANA);
				pstmt.setString(1, cognome);
				pstmt.setString(2, nome);
				pstmt.setString(3, sesso);
				pstmt.setString(4, dtNascitaTo);
				pstmt.setString(5, comNasCod);
				rs1 = pstmt.executeQuery();
				while (rs1.next()) {
					codProvComRes = getStrValue(rs1, "CM_CISTAT", null, true, 6, ' ', false);
				}
				DbUtils.close(rs1);
				DbUtils.close(pstmt);
			}

			String codIstatComune = getStrValue(rs, "COMUNE_INVIO_COD", null, true, 6, ' ', false);
			if (codProvComRes != null && (codProvComRes.equals("999998") || codProvComRes.equals(codIstatComune))) {
				if (dtEvento == null || dtEvento.trim().equals("") || dtEvento.trim().equals("00000000")) {
					dtEvento = dtEventoOrig;
				}
				codProvEve = getStrValue(rs, "PROVINCIA_EVENTO_COD", null, true, 3, '0', false);
				codComEve = getStrValue(rs, "COMUNE_EVENTO_COD", null, true, 6, ' ', true).substring(3);				
			}*/
			
			//considerare semplicemente come RDI
			codEve = "RDI";
		}
		
		if (codEve.trim().equals("IRR") || codEve.trim().equals("RDI")) {
			codProvEve = "999";
			codComEve = "998";
		}
		
		if (codProvEve == null || codProvEve.trim().equals("") || codProvEve.equals("000")) {
			codComEve = "000";
		}
		
		if (codEve.equals("   ")) {
			dtEvento = "00000000";
			codProvEve = "000";
			codComEve = "000";
		}
		
		String codIstatNaz = getStrValue(rs, "NAZIONALITA_ISTAT", null, true, 3, ' ', false);
		if (codIstatNaz != null && (codIstatNaz.trim().equals("0") || codIstatNaz.trim().equals("1") || codIstatNaz.trim().equals("100"))) {
			codIstatNaz = "   ";
		}
		
		line = getStrValue(rs, "COMUNE_INVIO_COD", null, true, 6, ' ', true) +
				"#" +
				getStrValue(rs, "CODICE_VIA", null, true, 5, ' ', false) +
				"," +
				getNumeroCivicoFromRs(rs) +
				getStrValue(rs, "COMUNE_INVIO_CAP", null, true, 5, ' ', false) +
				getStrValue(null, null, cognome, true, 25, ' ', false) +
				getStrValue(null, null, nome, true, 25, ' ', false) +
				sesso +
				dtNascitaTo +
				comNasCod +
				codFis +
				staCiv +
				" " +
				"00" +
				"0000" +
				codEve +
				dtEvento.substring(6) + dtEvento.substring(4, 6) + dtEvento.substring(0, 4) +
				codProvEve +
				codComEve +
				"       " +
				"#" +
				codIstatNaz +
				"    " +
				getStrValue(rs, "NUMERO_FAMIGLIA", null, true, 10, '0', true) +
				getStrValue(rs, "FLAG_RUOLO_FAMIGLIA", null, true, 1, ' ', false) +
				" ";
				
		return line;
	}
	
	protected String getDataEvento(ResultSet rs) throws Exception {
		String codEve = getStrValue(rs, "CODICE_EVENTO", null, true, 3, ' ', false);
		if (codEve != null) {
			codEve = codEve.toUpperCase();
			if (codEve.trim().equals("NAS")) {
				return getStrValue(rs, "DATA_NASCITA", null, true, 8, '0', true);
			} else if (codEve.trim().equals("IMM")) {
				return getStrValue(rs, "DATA_IMMIGRAZIONE", null, true, 8, '0', true);
			} else if (codEve.trim().equals("EMI")) {
				return getStrValue(rs, "DATA_EMIGRAZIONE", null, true, 8, '0', true);
			} else if (codEve.trim().equals("MOR")) {
				return getStrValue(rs, "DATA_DECESSO", null, true, 8, '0', true);
			}			
		}
		return getStrValue(rs, "DATA_EVENTO", null, true, 8, '0', true);
	}
	
	protected String getProvinciaEvento(ResultSet rs) throws Exception {
		String codEve = getStrValue(rs, "CODICE_EVENTO", null, true, 3, ' ', false);
		if (codEve != null) {
			codEve = codEve.toUpperCase();
			String retVal = null;
			if (codEve.trim().equals("NAS")) {
				retVal = getStrValue(rs, "PROVINCIA_NASCITA_COD_ISTAT", null, true, 3, '0', true);
				if (retVal == null || retVal.trim().equals("") || retVal.trim().equals("000")) {
					retVal = getStrValue(rs, "PROVINCIA_NASCITA", null, true, 4, '0', true).substring(1);
				}
				return retVal;
			} else if (codEve.trim().equals("IMM")) {
				retVal = getStrValue(rs, "PROVINCIA_IMMIGRAZIONE_COD", null, true, 3, '0', true);
				if (retVal == null || retVal.trim().equals("") || retVal.trim().equals("000")) {
					retVal = getStrValue(rs, "PROVINCIA_IMMIGRAZIONE", null, true, 4, '0', true).substring(1);
				}
				return retVal;
			} else if (codEve.trim().equals("EMI")) {
				retVal = getStrValue(rs, "PROVINCIA_EMIGRAZIONE_COD", null, true, 3, '0', true);
				if (retVal == null || retVal.trim().equals("") || retVal.trim().equals("000")) {
					retVal = getStrValue(rs, "PROVINCIA_EMIGRAZIONE", null, true, 4, '0', true).substring(1);
				}
				return retVal;
			}		
		}
		return getStrValue(rs, "PROVINCIA_EVENTO_COD", null, true, 3, '0', false);
	}
	
	protected String getComuneEvento(ResultSet rs) throws Exception {
		String codEve = getStrValue(rs, "CODICE_EVENTO", null, true, 3, ' ', false);
		if (codEve != null) {
			codEve = codEve.toUpperCase();
			if (codEve.trim().equals("NAS")) {
				return getStrValue(rs, "COMUNE_NASCITA_COD", null, true, 7, '0', true).substring(4);
			} else if (codEve.trim().equals("IMM")) {
				return getStrValue(rs, "COMUNE_IMMIGRAZIONE_COD", null, true, 7, '0', true).substring(4);
			} else if (codEve.trim().equals("EMI")) {
				return getStrValue(rs, "COMUNE_EMIGRAZIONE_COD", null, true, 7, '0', true).substring(4);
			}			
		}
		return getStrValue(rs, "COMUNE_EVENTO_COD", null, true, 6, ' ', true).substring(3);
	}
	
	protected String getCodComNasFromRs(Connection conn, ResultSet rs) throws Exception {
		String comNasCod = getStrValue(rs, "COMUNE_NASCITA_COD", null, true, 6, ' ', true);
		String codFisSogg = getStrValue(rs, "CODICE_FISCALE", null, true, 16, ' ', false);		
		String provNas = getStrValue(rs, "PROVINCIA_NASCITA", null, true, 3, '0', true);
		
		boolean doCtrl = "0123456789".indexOf(codFisSogg.substring(12, 13))	> -1 &&
							"0123456789".indexOf(codFisSogg.substring(13, 14)) > -1 &&
							"0123456789".indexOf(codFisSogg.substring(14, 15)) > -1;
		if (!doCtrl && codFisSogg.substring(11, 12).equalsIgnoreCase("Z")) {
			comNasCod = getStrValue(null, null, provNas, true, 3, '0', true) + "000";
			return comNasCod;
		}
		
		if (comNasCod == null || comNasCod.trim().equals("") || comNasCod.startsWith("   ") || comNasCod.startsWith("000") || codFisSogg.substring(11, 12).equalsIgnoreCase("Z")) {
			if (provNas != null && !provNas.trim().equals("") && provNas.length() == 3) {
				String ctrProvNas = provNas.replace("0", " ")
						.replace("1", " ")
						.replace("2", " ")
						.replace("3", " ")
						.replace("4", " ")
						.replace("5", " ")
						.replace("6", " ")
						.replace("7", " ")
						.replace("8", " ")
						.replace("9", " ")
						.trim();
				if (ctrProvNas.equals("")) {
					PreparedStatement pstmt = conn.prepareStatement(SQL_CTRL_COM_NAS_CMIN);			
					pstmt.setString(1, provNas);
					ResultSet rs1 = pstmt.executeQuery();
					while (rs1.next()) {
						String ctrlCodCodFis = rs1.getObject("CM_CFIS") == null ? "" : rs1.getString("CM_CFIS").trim();
						String codFis = rs.getObject("CODICE_FISCALE") == null ? "" : rs.getString("CODICE_FISCALE").trim();
						if (codFis.length() == 16 && codFis.substring(11, 15).equalsIgnoreCase(ctrlCodCodFis)) {
							comNasCod = getStrValue(null, null, provNas, true, 3, '0', true) + "000";
						}
					}
					DbUtils.close(rs1);
					DbUtils.close(pstmt);
				}
			}
		}
		return comNasCod;
	}
	
	protected String getNumeroCivicoFromRs(ResultSet rs) throws Exception {
		String numCiv = getStrValue(rs, "NUMERO_CIVICO", null, true, 5, '0', true);
		if (numCiv.trim().toUpperCase().endsWith("SNC")) {
			numCiv = "00000";
		}
		if (numCiv != null && numCiv.length() == 5 && numCiv.startsWith("0")) {
			numCiv = numCiv.substring(1);
		}
		String barrato = getStrValue(rs, "BARRATO", null, false, 0, ' ', false).trim();
		if (!barrato.equals("") && !barrato.toUpperCase().equals("SNC")) {
			numCiv += ("/" + barrato);
		}
		String subbarrato = getStrValue(rs, "SUBBARRATO", null, false, 0, ' ', false).trim();
		if (!subbarrato.equals("") && !subbarrato.toUpperCase().equals("SNC")) {
			numCiv += ("/" + subbarrato);
		}
		numCiv = getStrValue(null, null, numCiv, true, 23, ' ', false);
		return numCiv;
	}
	
	protected String getStrValue(ResultSet rs, String fieldName, Object paramObj, boolean doPaddingOrSubstr, int n, char c, boolean paddingLeft) throws Exception {
		Object obj = null;
		String retVal = "";
		
		if (rs == null || fieldName == null || fieldName.trim().equals("")) {
			obj = paramObj;
		} else {
			obj = rs.getObject(fieldName);
		}		
		
		if (obj == null) {
			obj = "";
		}
		
		//TODO ALTRI TIPI DATI???
		
		//default (e String)
		retVal = obj.toString();
		
		if (doPaddingOrSubstr) {
			if (retVal.length() < n) {
				retVal = StringUtils.padding(retVal, n, c, paddingLeft);
			} else if (retVal.length() > n) {
				if (paddingLeft) {
					retVal = retVal.substring(retVal.length() - n);
				} else {
					retVal = retVal.substring(0, n);
				}
			}
		}
		
		return retVal;
	}
	
	protected String getCapResidenza(Connection conn, String cap, String comInvioCod, String codVia, String numCiv, String barrato) throws Exception {
		if (numCiv.trim().toUpperCase().endsWith("SNC")) {
			numCiv = "00000000";
		}

		cap = getStrValue(null, null, cap, true, 5, ' ', false);
		comInvioCod = getStrValue(null, null, comInvioCod, true, 6, ' ', true);
		codVia = getStrValue(null, null, codVia, true, 5, ' ', false);
		numCiv = getStrValue(null, null, numCiv, true, 8, '0', true);
		barrato = getStrValue(null, null, barrato, true, 1, ' ', true);
		
		PreparedStatement pstmt = conn.prepareStatement(SQL_CTRL_CAP_ANASAN);			
		pstmt.setString(1, comInvioCod);
		pstmt.setString(2, codVia);
		String noTipo = " ";
		if (!numCiv.equals("00000000")) {
			String ultimoCar = numCiv.substring(numCiv.length() - 1);
			if ("0123456789".indexOf(ultimoCar) > -1) {
				noTipo = Integer.parseInt(ultimoCar) % 2 == 0 ? "D" : "P";
			}
		}
		pstmt.setString(3, noTipo);
		ResultSet rs1 = pstmt.executeQuery();
		while (rs1.next()) {
			String stDa = getStrValue(null, null, getStrValue(rs1, "ST_DA", null, true, 8, '0', true).replace("/", ""), true, 8, '0', true);
			String stA = getStrValue(null, null, getStrValue(rs1, "ST_A", null, true, 8, '0', true).replace("/", ""), true, 8, '0', true);
			if (stA.equals("00000000")) {
				stA = "99999999";
			}
			String barratoDa = " ";
			String barratoA = "Z";
			String ultimoCarDa = stDa.substring(stDa.length() - 1);
			if ("0123456789".indexOf(ultimoCarDa) == -1) {
				barratoDa = ultimoCarDa;
				stDa = "0" + stDa.substring(0, stDa.length() - 1);
			}
			String ultimoCarA = stA.substring(stA.length() - 1);
			if ("0123456789".indexOf(ultimoCarA) == -1) {
				barratoA = ultimoCarA;
				stA = "0" + stA.substring(0, stA.length() - 1);
				if (stA.equals("09999999")) {
					stA = "99999999";
				}
			}
			
			String civBar = (numCiv + barrato).toUpperCase();
			String civBarDa = (stDa + barratoDa).toUpperCase();
			String civBarA = (stA + barratoA).toUpperCase();
			
			if (civBar.compareTo(civBarDa) >= 0 && civBar.compareTo(civBarA) <= 0) {
				cap = getStrValue(rs1, "ST_CAP", null, true, 5, ' ', false);
				break;
			}
		}
		DbUtils.close(rs1);
		DbUtils.close(pstmt);

		return cap;
	}
	
	protected String[] ctrl(String codComuneExp, String stato, String codEve, String codEveOrig, String dtEve, String line, String indirizzo, String matricola, String cf, String provNasPers, Connection conn, Connection connAnaSan, PreparedStatement pstmtAnaSan, ResultSet rsAnaSan) throws Exception {
		String codIstatLine = line.substring(7, 13).trim();
		
		String codVia = line.substring(14, 19).trim();
		
		String cognome = line.substring(48, 73).trim();
		String nome = line.substring(73, 98).trim();
		String sesso = line.substring(98, 99).trim();
		String dtNas = line.substring(99, 107).trim();
		String comNas = line.substring(107, 113).trim();
		
		String dtEveMsg = "-";
		if (dtEve != null && dtEve.length() == 8 && !dtEve.trim().equals("") && !dtEve.equals("00000000")) {
			dtEveMsg = dtEve.substring(0, 2) + "/" + dtEve.substring(2, 4) + "/" + dtEve.substring(4);
		}		
		
		String codProvComEve = line.substring(149, 155).trim();
		
		if (comNas.equals("") || comNas.equals("000") || comNas.equals("000000")) {
			return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEve.trim() + ", DATA EVENTO: " + dtEveMsg + ", COMUNE O STATO ESTERO DI NASCITA NON PRESENTE IN ANAGRAFE SANITARIA PER CFIS = " + (cf != null && cf.length() == 16 ? cf.substring(11, 15) : "") + ", CMIN = " + provNasPers, codVia};
		}
		
		if (codEve.trim().equals("IMM") && (codProvComEve == null || codProvComEve.equals("") || codProvComEve.equals("000000"))) {
			return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEve.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO DI IMMIGRAZIONE CON PROVINCIA E COMUNE EVENTO NON VALORIZZATI: SI TRATTA DI EVENTO DI RICOMPARSA DA IRREPERIBILITA'?", codVia};
		}
		
		String codProvComEveAnasan = null;
		boolean doAnasan = ((codEve.trim().equals("EMI") || codEve.trim().equals("MOR")) && (codProvComEve.equals("") || codProvComEve.equals("000"))) ||
				(stato.trim().equals("A") && codEve.trim().equals("IMM") && codEveOrig.trim().equals("RDI")) ||
				(stato.trim().equals("E") && codEve.trim().equals("IMM") && codEveOrig.trim().equals("RDI")) ||
				(stato.trim().equals("R") && codEve.trim().equals("EMI") && !codEveOrig.trim().equals("IRR")) ||
				(stato.trim().equals("I") && (codEveOrig.trim().equals("IRR") || codEveOrig.trim().equals("RDI")));
		if (doAnasan) {
			PreparedStatement pstmt = conn.prepareStatement(SQL_CTRL_COM_RES_ANASAN_CF);
			pstmt.setString(1, cf);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				codProvComEveAnasan = getStrValue(rs, "CM_CISTAT", null, true, 6, ' ', false);
			}
			DbUtils.close(rs);
			DbUtils.close(pstmt);
			
			if (codProvComEveAnasan == null) {
				pstmt = conn.prepareStatement(SQL_CTRL_COM_RES_ANASAN_DATI_ANA);
				pstmt.setString(1, cognome);
				pstmt.setString(2, nome);
				pstmt.setString(3, sesso);
				pstmt.setString(4, dtNas);
				pstmt.setString(5, comNas);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					codProvComEveAnasan = getStrValue(rs, "CM_CISTAT", null, true, 6, ' ', false);
				}
				DbUtils.close(rs);
				DbUtils.close(pstmt);
			}
		}
		
		if (stato.trim().equals("A")) {
			if (codEve.trim().equals("IMM") && codEveOrig.trim().equals("RDI")) {
				if (codProvComEveAnasan == null) {
					return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEveOrig.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO RICOMPARSA DA IRREPERIBILITA' SU PERSONA NON PRESENTE IN ANAGRAFE SANITARIA", codVia};
				} else if (codProvComEveAnasan != null && !codProvComEveAnasan.equals("999998")) {
					codProvComEveAnasan = null;
					return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEveOrig.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO RICOMPARSA DA IRREPERIBILITA' SU PERSONA CHE LA USL NON CONSIDERA \"IRREPERIBILE\"", codVia};
				}
			}
		} else if (stato.trim().equals("E")) {
			if (codEve.trim().equals("IMM") && codEveOrig.trim().equals("RDI")) {
				if (codProvComEveAnasan == null) {
					return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEveOrig.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO RICOMPARSA DA IRREPERIBILITA' SU PERSONA NON PRESENTE IN ANAGRAFE SANITARIA", codVia};
				} else if (codProvComEveAnasan != null && !codProvComEveAnasan.equals("999998")) {
					codProvComEveAnasan = null;
					return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEveOrig.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO RICOMPARSA DA IRREPERIBILITA' SU PERSONA CHE LA USL NON CONSIDERA \"IRREPERIBILE\"", codVia};
				}
			}
			if (codEve.trim().equals("NAS")) {
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEve.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO NASCITA SU PERSONA CHE IL COMUNE CONSIDERA EMIGRATA", codVia};
			} else if (codEve.trim().equals("MOR")) {
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEve.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO MORTE SU PERSONA CHE IL COMUNE CONSIDERA EMIGRATA", codVia};
			} else if (codEve.trim().equals("EMI") && codEveOrig.trim().equals("IRR")) {
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEveOrig.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO IRREPERIBILITA' SU PERSONA CHE IL COMUNE CONSIDERA EMIGRATA", codVia};
			} else if (codEve.equals("   ") && codEveOrig.trim().equals("IND")) {
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEveOrig.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO CAMBIO INDIRIZZO SU PERSONA CHE IL COMUNE CONSIDERA EMIGRATA", codVia};
			}
		} else if (stato.trim().equals("R")) {
			if (codEve.trim().equals("EMI") && !codEveOrig.trim().equals("IRR")) {
				if (codProvComEveAnasan == null) {
					return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEve.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO EMIGRAZIONE SU PERSONA CHE IL COMUNE CONSIDERA ISCRITTA ALL'AIRE E CHE NON E' PRESENTE IN ANAGRAFE SANITARIA", codVia};
				} else if (codProvComEveAnasan != null && !codProvComEveAnasan.equals(codIstatLine)) {
					codProvComEveAnasan = null;
					return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEve.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO EMIGRAZIONE SU PERSONA CHE IL COMUNE CONSIDERA ISCRITTA ALL'AIRE", codVia};
				}
				codProvComEveAnasan = codProvComEve; //in questo caso non deve cambiarlo, serve solo per il controllo
			} else if (codEve.trim().equals("IMM") && !codEveOrig.trim().equals("RDI")) {
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEve.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO IMMIGRAZIONE SU PERSONA CHE IL COMUNE CONSIDERA ISCRITTO AIRE", codVia};
			} else if (codEve.trim().equals("NAS")) {
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEve.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO NASCITA SU PERSONA CHE IL COMUNE CONSIDERA ISCRITTO AIRE", codVia};
			} else if (codEve.trim().equals("MOR")) {
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEve.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO MORTE SU PERSONA CHE IL COMUNE CONSIDERA ISCRITTO AIRE", codVia};
			} else if (codEve.trim().equals("EMI") && codEveOrig.trim().equals("IRR")) {
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEveOrig.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO IRREPERIBILITA' SU PERSONA CHE IL COMUNE CONSIDERA ISCRITTO AIRE", codVia};
			} else if (codEve.trim().equals("IMM") && codEveOrig.trim().equals("RDI")) {
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEveOrig.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO RICOMPARSA DA IRREPERIBILITA' SU PERSONA CHE IL COMUNE CONSIDERA ISCRITTO AIRE", codVia};
			} else if (codEve.equals("   ") && codEveOrig.trim().equals("IND")) {
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEveOrig.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO CAMBIO INDIRIZZO SU PERSONA CHE IL COMUNE CONSIDERA ISCRITTO AIRE", codVia};
			}
		} else if (stato.trim().equals("D")) {
			if (codEve.trim().equals("EMI") && !codEveOrig.trim().equals("IRR")) {
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEve.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO EMIGRAZIONE SU PERSONA CHE IL COMUNE CONSIDERA DECEDUTA", codVia};
			} else if (codEve.trim().equals("IMM") && !codEveOrig.trim().equals("RDI")) {
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEve.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO IMMIGRAZIONE SU PERSONA CHE IL COMUNE CONSIDERA DECEDUTA", codVia};
			} else if (codEve.trim().equals("NAS")) {
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEve.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO NASCITA SU PERSONA CHE IL COMUNE CONSIDERA DECEDUTA", codVia};
			} else if (codEve.trim().equals("EMI") && codEveOrig.trim().equals("IRR")) {
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEveOrig.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO IRREPERIBILITA' SU PERSONA CHE IL COMUNE CONSIDERA DECEDUTA", codVia};
			} else if (codEve.trim().equals("IMM") && codEveOrig.trim().equals("RDI")) {
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEveOrig.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO RICOMPARSA DA IRREPERIBILITA' SU PERSONA CHE IL COMUNE CONSIDERA DECEDUTA", codVia};
			} else if (codEve.equals("   ") && codEveOrig.trim().equals("IND")) {
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEveOrig.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO CAMBIO INDIRIZZO SU PERSONA CHE IL COMUNE CONSIDERA DECEDUTA", codVia};
			}
		} else if (stato.trim().equals("I")) {
			if (codEve.trim().equals("EMI") && !codEveOrig.trim().equals("IRR")) {
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEve.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO EMIGRAZIONE SU PERSONA CHE IL COMUNE CONSIDERA IRREPERIBILE", codVia};
			} else if (codEve.trim().equals("IMM") && !codEveOrig.trim().equals("RDI")) {
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEve.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO IMMIGRAZIONE SU PERSONA CHE IL COMUNE CONSIDERA IRREPERIBILE", codVia};
			} else if (codEve.trim().equals("NAS")) {
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEve.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO NASCITA SU PERSONA CHE IL COMUNE CONSIDERA IRREPERIBILE", codVia};
			} else if (codEve.trim().equals("EMI") && codEveOrig.trim().equals("IRR") && (codProvComEveAnasan == null || !codProvComEveAnasan.equals(codIstatLine))) {
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEveOrig.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO IRREPERIBILITA' SU PERSONA CHE IN ANAGRAFE SANITARIA NON E' PRESENTE O RISULTA RESIDENTE IN ALTRO COMUNE", codVia};
			} else if (codEve.trim().equals("IMM") && codEveOrig.trim().equals("RDI") && (codProvComEveAnasan != null && !codProvComEveAnasan.equals("999998"))) {
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEveOrig.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO RICOMPARSA DA IRREPERIBILITA' SU PERSONA CHE LA USL NON CONSIDERA \"IRREPERIBILE\"", codVia};
			} else if (codEve.equals("   ") && codEveOrig.trim().equals("IND")) {
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEveOrig.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO CAMBIO INDIRIZZO SU PERSONA CHE IL COMUNE CONSIDERA IRREPERIBILE", codVia};
			}
		} else {
			if (codEve.trim().equals("EMI") && !codEveOrig.trim().equals("IRR")) {
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEve.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO EMIGRAZIONE SU PERSONA CHE IL COMUNE CONSIDERA COME OCCASIONALE", codVia};
			} else if (codEve.trim().equals("IMM") && !codEveOrig.trim().equals("RDI")) {
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEve.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO IMMIGRAZIONE SU PERSONA CHE IL COMUNE CONSIDERA COME OCCASIONALE", codVia};
			} else if (codEve.trim().equals("NAS")) {
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEve.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO NASCITA SU PERSONA CHE IL COMUNE CONSIDERA COME OCCASIONALE", codVia};
			} else if (codEve.trim().equals("MOR")) {
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEve.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO MORTE SU PERSONA CHE IL COMUNE CONSIDERA COME OCCASIONALE", codVia};
			}else if (codEve.trim().equals("EMI") && codEveOrig.trim().equals("IRR")) {
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEveOrig.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO IRREPERIBILITA' SU PERSONA CHE IL COMUNE CONSIDERA COME OCCASIONALE", codVia};
			} else if (codEve.trim().equals("IMM") && codEveOrig.trim().equals("RDI")) {
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEveOrig.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO RICOMPARSA DA IRREPERIBILITA' SU PERSONA CHE IL COMUNE CONSIDERA COME OCCASIONALE", codVia};
			} else if (codEve.equals("   ") && codEveOrig.trim().equals("IND")) {
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEveOrig.trim() + ", DATA EVENTO: " + dtEveMsg + ", EVENTO CAMBIO INDIRIZZO SU PERSONA CHE IL COMUNE CONSIDERA COME OCCASIONALE", codVia};
			}
		}

		if ((codEve.trim().equals("EMI") || codEve.trim().equals("MOR")) && (codProvComEve.equals("") || codProvComEve.equals("000"))) {
			if (codProvComEveAnasan == null) {
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEve.trim() + ", DATA EVENTO: " + dtEveMsg + ", PROVINCIA E COMUNE EVENTO NON VALORIZZATI E SOGGETTO NON PRESENTE IN ANAGRAFE SANITARIA", codVia};
			} else if (codProvComEveAnasan != null && !codProvComEveAnasan.equals(codIstatLine)) {
				codProvComEveAnasan = null;
				return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEve.trim() + ", DATA EVENTO: " + dtEveMsg + ", PROVINCIA E COMUNE EVENTO NON VALORIZZATI E SOGGETTO CHE IN ANAGRAFE SANITARIA RISULTA RESIDENTE IN ALTRO COMUNE", codVia};
			}
		}

		if (codVia == null || StringUtils.padding(codVia, 5, '0', true).equals("00000")) {
			if (matricola != null && !matricola.trim().equals("")) {
				PreparedStatement pstmt = conn.prepareStatement(SQL_CTRL_VIA_MATRICOLA);
				pstmt.setString(1, matricola);
				ResultSet rs = pstmt.executeQuery();
				boolean trovato = false;
				while (rs.next()) {
					trovato = true;
					codVia = getStrValue(rs, "CODICE_VIA", null, true, 5, ' ', false) +
							"," +
							getNumeroCivicoFromRs(rs);
				}
				DbUtils.close(rs);
				DbUtils.close(pstmt);
				if (trovato) {
					return new String[]{"OK", codVia, codProvComEveAnasan};
				}
			}
		}
		
		if (codVia == null || StringUtils.padding(codVia, 5, '0', true).equals("00000")) {
			if (cf != null && !cf.trim().equals("")) {
				PreparedStatement pstmt = conn.prepareStatement(SQL_CTRL_VIA_CF);
				pstmt.setString(1, cf);
				ResultSet rs = pstmt.executeQuery();
				boolean trovato = false;
				while (rs.next()) {
					trovato = true;
					codVia = getStrValue(rs, "CODICE_VIA", null, true, 5, ' ', false) +
							"," +
							getNumeroCivicoFromRs(rs);
				}
				DbUtils.close(rs);
				DbUtils.close(pstmt);
				if (trovato) {
					return new String[]{"OK", codVia, codProvComEveAnasan};
				}
			}
		}
		
		if (codVia == null || StringUtils.padding(codVia, 5, '0', true).equals("00000")) {
			if (cf != null && !cf.trim().equals("")) {
				PreparedStatement pstmt = conn.prepareStatement(SQL_CTRL_VIA_ANASAN_CF);
				pstmt.setString(1, cf);
				ResultSet rs = pstmt.executeQuery();
				String cIstat = null;
				String codViaAnaSan = null;
				while (rs.next()) {
					cIstat = getStrValue(rs, "CM_CISTAT", null, true, 6, ' ', false);
					codViaAnaSan = getStrValue(rs, "ST_STRADA", null, true, 5, ' ', false);
				}
				DbUtils.close(rs);
				DbUtils.close(pstmt);
				
				if (cIstat != null) {
					if (codIstatLine.equals(cIstat)) {
						if (codViaAnaSan != null) {
							codVia = codViaAnaSan;
							return new String[]{"OK", codVia, codProvComEveAnasan};
						}						
					} else {
						if (codVia == null) {
							codVia = "";
						}
						codVia = StringUtils.padding(codVia, 5, '0', true);
						return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEve.trim() + ", DATA EVENTO: " + dtEveMsg + ", CODICE VIA NON VALORIZZATO E SOGGETTO CHE RISULTA EMIGRATO IN ANAGRAFE SANITARIA", codVia};
					}
				}
			}
		}
		
		if (codVia == null || StringUtils.padding(codVia, 5, '0', true).equals("00000")) {
			PreparedStatement pstmt = conn.prepareStatement(SQL_CTRL_VIA_ANASAN_DATI_ANA);			
			pstmt.setString(1, cognome);
			pstmt.setString(2, nome);
			pstmt.setString(3, sesso);
			pstmt.setString(4, dtNas);
			pstmt.setString(5, comNas);
			ResultSet rs = pstmt.executeQuery();
			String cIstat = null;
			String codViaAnaSan = null;
			while (rs.next()) {
				cIstat = getStrValue(rs, "CM_CISTAT", null, true, 6, ' ', false);
				codViaAnaSan = getStrValue(rs, "ST_STRADA", null, true, 5, ' ', false);
			}
			DbUtils.close(rs);
			DbUtils.close(pstmt);
			
			if (cIstat != null) {
				if (codIstatLine.equals(cIstat)) {
					if (codViaAnaSan != null) {
						codVia = codViaAnaSan;
						return new String[]{"OK", codVia, codProvComEveAnasan};
					}						
				} else {
					if (codVia == null) {
						codVia = "";
					}
					codVia = StringUtils.padding(codVia, 5, '0', true);
					return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEve.trim() + ", DATA EVENTO: " + dtEveMsg + ", CODICE VIA NON VALORIZZATO E SOGGETTO CHE RISULTA EMIGRATO IN ANAGRAFE SANITARIA", codVia};
				}
			}
		
		}
		
		if (codVia == null) {
			codVia = "";
		}
		
		if (StringUtils.padding(codVia, 5, '0', true).equals("00000")) {
			codVia = StringUtils.padding(codVia, 5, '0', true);
			return new String[]{"RECORD SCARTATO - CODICE EVENTO: " + codEve.trim() + ", DATA EVENTO: " + dtEveMsg + ", CODICE VIA NON VALORIZZATO E SOGGETTO NON PRESENTE IN ANAGRAFE SANITARIA", codVia};
		}

		//caso particolare in cui deve restare 999998 - segnalazione Asl Umbria 1 per Comune di Perugia, dicembre 2021
		if (stato.trim().equals("I") && codEve.trim().equals("EMI") && codEveOrig.trim().equals("IRR") && codProvComEve.equals("999998") && codProvComEveAnasan.equals(codIstatLine)) {
			codProvComEveAnasan = codProvComEve;
		}
		
		return new String[]{"OK", codVia, codProvComEveAnasan};
	}
	
	protected String getRigaKo(Connection conn, String riga) throws Exception {
		String cIstat = riga.substring(0, 6);
		String codVia = riga.substring(7, 12);
		String descVia = "VIA NON DEFINITA";
		
		PreparedStatement pstmt = conn.prepareStatement(SQL_DESC_VIA_ANASAN);			
		pstmt.setString(1, cIstat);
		pstmt.setString(2, codVia);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			descVia = getStrValue(rs, "ST_INDIR", null, false, 0, ' ', false);
		}
		DbUtils.close(rs);
		DbUtils.close(pstmt);
		
		riga = riga.substring(0, 12) + " - " + descVia + riga.substring(12);
		return riga;
	}
	
	protected void saveLine(Connection conn, String prog, String flagOk, String errMsg, String fileName, String line) throws Exception {
		PreparedStatement pstmtIns = conn.prepareStatement(SQL_INS_EXPORT);
		pstmtIns.setString(1, prog);
		pstmtIns.setString(2, line.substring(0, 3));
		pstmtIns.setString(3, line.substring(3, 6));
		pstmtIns.setString(4, line.substring(7, 12));
		pstmtIns.setString(5, line.substring(13, 36));
		pstmtIns.setString(6, line.substring(36, 41));
		pstmtIns.setString(7, line.substring(41, 66));
		pstmtIns.setString(8, line.substring(66, 91));
		pstmtIns.setString(9, line.substring(91, 92));
		pstmtIns.setString(10, line.substring(92, 100));
		pstmtIns.setString(11, line.substring(100, 103));
		pstmtIns.setString(12, line.substring(103, 106));
		pstmtIns.setString(13, line.substring(106, 122));
		pstmtIns.setString(14, line.substring(122, 124));
		pstmtIns.setString(15, line.substring(125, 127));
		pstmtIns.setString(16, line.substring(127, 131));
		pstmtIns.setString(17, line.substring(131, 134));
		pstmtIns.setString(18, line.substring(134, 142));
		pstmtIns.setString(19, line.substring(142, 145));
		pstmtIns.setString(20, line.substring(145, 148));
		pstmtIns.setString(21, line.substring(156, 159));
		pstmtIns.setString(22, line.substring(163, 173));
		pstmtIns.setString(23, line.substring(173, 174));
		pstmtIns.setString(24, flagOk);
		pstmtIns.setString(25, errMsg);
		pstmtIns.setString(26, fileName);
		pstmtIns.setString(27, line);
		pstmtIns.executeUpdate();
		DbUtils.close(pstmtIns);
	}
	
}
