package it.webred.rulengine.brick.loadDwh.load.tributi.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
//import java.util.Iterator;
//import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import it.webred.rulengine.brick.loadDwh.utils.FileConverter;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.dwh.DwhUtils;

public class TributiFileConverter implements FileConverter {
	
	private static final Logger log = Logger.getLogger(TributiFileConverter.class.getName());
	
	private String codEnte = null;
	private String desEnte = null;
	
	private String dataFornituraICI = null;
	private String dataFornituraVersamentiICI = null;
	private String dataFornituraTARSU = null;
	
	private static final String SUFF_FILE_DICH_ICI = "dichiarazioniici.txt";
	private static final String SUFF_FILE_PAG_ICI = "pagamentiici.txt";
	private static final String SUFF_FILE_SOGG = "soggetti.txt";
	private static final String SUFF_FILE_TARSU = "tarsu.txt";
	private static final String SUFF_FILE_VIE = "viariotributi.txt";
	
	private static final String[] SUFF_FILE_TAB_DICH_ICI = new String[]{"thsddetici.txt"};
	private static final String[] SUFF_FILE_TAB_PAG_ICI = new String[]{"thsdpici.txt", "thsdpicii.txt"};
	private static final String[] SUFF_FILE_TAB_SOGG = new String[]{"thsdsogg.txt"};
	private static final String[] SUFF_FILE_TAB_TARSU = new String[]{"thsdorsu.txt"};
	private static final String[] SUFF_FILE_TAB_VIE = new String[]{"thsdvie.txt"};
	
	private static final String TAB_TEMP_DICH_ICI = "THSDDETICI";
	private static final String TAB_TEMP_PAG_ICI = "THSDPICI";
	private static final String TAB_TEMP_SOGG = "THSDSOGG";
	private static final String TAB_TEMP_TARSU = "THSDORSU";
	private static final String TAB_TEMP_VIE = "THSDVIE";
	
	private static final String TIP_REC_TESTA = "0";
	private static final String VER_ICI = "3";
	private static final String VER_VERSAMENTI_ICI = "3";
	private static final String VER_TARSU = "3";
	private static final String TIP_REC_CODA = "9";
	
	private static final String COD_TRI_ICI = "ICI";
	private static final String COD_TRI_VERSAMENTI_ICI = "VICI";
	private static final String COD_TRI_TARSU = "TARSU";
	
	private static final String TIP_REC_ANA_SOGG_TARSU = "TRTRIBTARSUSOGG";
	private static final String TIP_REC_DICH_OGG_TARSU = "TRTRIBTARSUDICH";
	//private static final String TIP_REC_ULT_SOGG_TARSU = "TRTRIBTARSUSOGGULT";
	private static final String TIP_REC_VIARIO_UFF_TARSU = "TRTRIBTARSUVIA";
	private static final String TIP_REC_RID_TAR_TARSU = "TRTRIBTARRIDUZ";
	
	private static final String TIP_REC_ANA_SOGG_ICI = "TRTRIBICISOGG";
	private static final String TIP_REC_DICH_OGG_ICI = "TRTRIBICIDICH";
	//private static final String TIP_REC_ULT_SOGG_ICI = "TRTRIBICISOGGULT";
	private static final String TIP_REC_VIARIO_UFF_ICI = "TRTRIBICIVIA";
	private static final String TIP_REC_RID_TAR_ICI = "TRTRIBICIRIDUZ";
	
	private static final String TIP_REC_VERSAMENTI_ICI = "TRTRIBVICI";
	
	private static final String SQL_ANA_SOGG_TARSU = "SELECT * FROM " + TAB_TEMP_SOGG + " S " +
													"WHERE EXISTS(SELECT 1 FROM " + TAB_TEMP_TARSU + " T WHERE T.COD_CNT = S.COD_CNT) " +
													"AND S.DATI_ORI = ?";
	private static final String SQL_DICH_OGG_TARSU = "SELECT * FROM " + TAB_TEMP_TARSU + " T " + 
													"WHERE EXISTS(SELECT 1 FROM " + TAB_TEMP_SOGG + " S WHERE T.COD_CNT = S.COD_CNT " +
													"AND S.DATI_ORI = ?)";
	//private static final String SQL_ULT_SOGG_TARSU = "?"; non implementata
	private static final String SQL_VIARIO_UFF_TARSU = "SELECT DISTINCT COD_VIA, DES_VIA FROM " + TAB_TEMP_TARSU + " T " + 
													"WHERE EXISTS(SELECT 1 FROM " + TAB_TEMP_SOGG + " S WHERE T.COD_CNT = S.COD_CNT " +
													"AND S.DATI_ORI = ?)";
	private static final String SQL_RID_TAR_TARSU = "SELECT COD_OGG, PRC_RID FROM " + TAB_TEMP_TARSU + " T " + 
													"WHERE PRC_RID IS NOT NULL " + 
													"AND TO_NUMBER(PRC_RID, DECODE(INSTR(PRC_RID, ','), 0, '99999', '999D99'), 'NLS_NUMERIC_CHARACTERS = '', ''') <> 0 " +
													"AND EXISTS(SELECT 1 FROM " + TAB_TEMP_SOGG + " S WHERE T.COD_CNT = S.COD_CNT " +
													"AND S.DATI_ORI = ?)";
	
	private static final String SQL_ANA_SOGG_ICI = "SELECT * FROM " + TAB_TEMP_SOGG + " S " +
													"WHERE EXISTS(SELECT 1 FROM " + TAB_TEMP_DICH_ICI + " I WHERE I.COD_CNT = S.COD_CNT " +
													"AND I.DATI_ORI = S.DATI_ORI) AND S.DATI_ORI = ?";
	private static final String SQL_DICH_OGG_ICI = "SELECT * FROM " + TAB_TEMP_DICH_ICI + " WHERE DATI_ORI = ?";
	//private static final String SQL_ULT_SOGG_ICI = "?"; non implementata
	private static final String SQL_VIARIO_UFF_ICI = "SELECT DISTINCT COD_VIA, DES_VIA FROM " + TAB_TEMP_DICH_ICI + " WHERE DATI_ORI = ?";
	private static final String SQL_RID_TAR_ICI = "SELECT COD_OGG, MESI_RID FROM " + TAB_TEMP_DICH_ICI +
													" WHERE MESI_RID IS NOT NULL AND TO_NUMBER(MESI_RID) <> 0 AND DATI_ORI = ?";
	
	private static final String SQL_VERSAMENTI_ICI = "SELECT * FROM " + TAB_TEMP_PAG_ICI + " WHERE DATI_ORI = ?";
	
	//nel caso di presenza del file del viario
	private static final String SQL_VIARIO = "SELECT * FROM " + TAB_TEMP_VIE;
	
	private static final String SEPARATORE = "|";
	
	private static final String CTRL_IN_ICI = "I";
	private static final String CTRL_IN_TARSU = "T";
	
	private static final String SQL_CTRL_ID_ORIG_SOGG_IN_ICI = "SELECT S.COD_CNT FROM " + TAB_TEMP_SOGG + " S " +
														"WHERE EXISTS(SELECT 1 FROM " + TAB_TEMP_DICH_ICI + " I WHERE I.COD_CNT = S.COD_CNT " +
														"AND I.DATI_ORI = S.DATI_ORI) AND S.COD_CNT = ? AND S.DATI_ORI = ?";
	private static final String SQL_CTRL_ID_ORIG_SOGG_IN_TARSU = "SELECT S.COD_CNT FROM " + TAB_TEMP_SOGG + " S " +
														"WHERE EXISTS(SELECT 1 FROM " + TAB_TEMP_TARSU + " T WHERE T.COD_CNT = S.COD_CNT) " +
														"AND S.COD_CNT = ? AND S.DATI_ORI = ?";
	
	private static final HashMap<String, String> SQLS_CTRL_ID_ORIG_SOGG_U = new HashMap<String, String>();
	static {
		SQLS_CTRL_ID_ORIG_SOGG_U.put(CTRL_IN_ICI, SQL_CTRL_ID_ORIG_SOGG_IN_ICI);
		SQLS_CTRL_ID_ORIG_SOGG_U.put(CTRL_IN_TARSU, SQL_CTRL_ID_ORIG_SOGG_IN_TARSU);
	}
	
	//connessione usata se i dati (vecchio tracciato) si trovano non in file di testo ma in tabelle (es. Milano schema TRIBUTI)
	Connection connTables = null;
	
	//specifica se è fornito o meno il file del viario
	boolean viario;
	
	//private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
	
	private static final DecimalFormat DF = new DecimalFormat();
	static {		
		DF.setGroupingUsed(false);
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator(',');		
		DF.setDecimalFormatSymbols(dfs);
	}
	
	public Connection getConnTables() {
		return connTables;
	}

	public void setConnTables(Connection connTables) {
		this.connTables = connTables;
	}

	public boolean isViario() {
		return viario;
	}

	public void setViario(boolean viario) {
		this.viario = viario;
	}

	public void save(String filePathFrom, String filePathTo, String belfiore) throws Exception {
		//i percorsi sono in questo caso le CARTELLE da cui leggere e in cui salvare i file...
		
		Connection conn = null;
		Statement st = null;
		
		String msgDichICI = null;
		String msgPagICI = null;
		String msgSogg = null;
		String msgTARSU = null;
		String msgVie = null;
		String msgTables = null;
		
		try {
			conn = RulesConnection.getConnection("DWH_" + belfiore );
			conn.setAutoCommit(false);
			codEnte = getCodEnte(conn);
			desEnte = getDesEnte(conn);
			
			//verifico i nomi dei file di testo, se i dati non si trovano (come invece ad es. per Milano schema TRIBUTI) in tabelle 
			if (connTables == null) {
				String check = checkFilenames(filePathFrom, filePathTo);
				if (check != null && !check.equals("")) {
					throw new Exception(check);
				}
			} else {
				String data = new SimpleDateFormat("yyyyMMdd").format(new Date());
				dataFornituraICI = data;
				dataFornituraVersamentiICI = data;
				dataFornituraTARSU = data;
			}
			
			log.info("Inizio conversione dei file tributi dal vecchio al nuovo tracciato.");
			
			String createTableDichICI = getProperty("dichICI.create_table");
			String createTablePagICI = getProperty("pagICI.create_table");
			String createTableSogg = getProperty("sogg.create_table");
			String createTableTARSU = getProperty("tarsu.create_table");
			String createTableVie = getProperty("vie.create_table");
			
			String createIndexDichICI = getProperty("dichICI.create_index");
			String createIndexSogg = getProperty("sogg.create_index");
			String createIndexTARSU = getProperty("tarsu.create_index");
			String createIndexVie = getProperty("vie.create_index");
			
			String deleteTableDichICI = getProperty("dichICI.delete_table");
			String deleteTablePagICI = getProperty("pagICI.delete_table");
			String deleteTableSogg = getProperty("sogg.delete_table");
			String deleteTableTARSU = getProperty("tarsu.delete_table");
			String deleteTableVie = getProperty("vie.delete_table");
			
			String dropTableDichICI = getProperty("dichICI.drop_table");
			String dropTablePagICI = getProperty("pagICI.drop_table");
			String dropTableSogg = getProperty("sogg.drop_table");
			String dropTableTARSU = getProperty("tarsu.drop_table");
			String dropTableVie = getProperty("vie.drop_table");
			
			String fileDichICI = null;
			String filePagICI = null;
			String fileSogg = null;
			String fileTARSU = null;
			String fileVie = null;

			String[] fs = it.webred.utils.FileUtils.cercaFileDaElaborare(filePathFrom);
			for (int i = 0; i < fs.length; i++) {
				if (fs[i].toLowerCase().startsWith(codEnte.toLowerCase()) && 
					fs[i].toLowerCase().endsWith(SUFF_FILE_DICH_ICI)) {
					fileDichICI = fs[i];
				}
				if (fs[i].toLowerCase().startsWith(codEnte.toLowerCase()) && 
					fs[i].toLowerCase().endsWith(SUFF_FILE_PAG_ICI)) {
					filePagICI = fs[i];
				}
				if (fs[i].toLowerCase().startsWith(codEnte.toLowerCase()) && 
					fs[i].toLowerCase().endsWith(SUFF_FILE_SOGG)) {
					fileSogg = fs[i];
				}
				if (fs[i].toLowerCase().startsWith(codEnte.toLowerCase()) && 
					fs[i].toLowerCase().endsWith(SUFF_FILE_TARSU)) {
					fileTARSU = fs[i];
				}
				if (fs[i].toLowerCase().startsWith(codEnte.toLowerCase()) && 
					fs[i].toLowerCase().endsWith(SUFF_FILE_VIE)) {
					fileVie = fs[i];
				}
			}
			
			if (fileDichICI == null) {
				for (int i = 0; i < fs.length; i++) {
					for (String suff : SUFF_FILE_TAB_DICH_ICI) {
						if (fs[i].toLowerCase().endsWith(suff)) {
							fileDichICI = fs[i];
						}
					}					
				}
			}
			if (filePagICI == null) {
				for (int i = 0; i < fs.length; i++) {
					for (String suff : SUFF_FILE_TAB_PAG_ICI) {
						if (fs[i].toLowerCase().endsWith(suff)) {
							filePagICI = fs[i];
						}
					}					
				}
			}
			if (fileSogg == null) {
				for (int i = 0; i < fs.length; i++) {
					for (String suff : SUFF_FILE_TAB_SOGG) {
						if (fs[i].toLowerCase().endsWith(suff)) {
							fileSogg = fs[i];
						}
					}					
				}
			}
			if (fileTARSU == null) {
				for (int i = 0; i < fs.length; i++) {
					for (String suff : SUFF_FILE_TAB_TARSU) {
						if (fs[i].toLowerCase().endsWith(suff)) {
							fileTARSU = fs[i];
						}
					}					
				}
			}
			if (fileVie == null) {
				for (int i = 0; i < fs.length; i++) {
					for (String suff : SUFF_FILE_TAB_VIE) {
						if (fs[i].toLowerCase().endsWith(suff)) {
							fileVie = fs[i];
						}
					}					
				}
			}

			//drop preliminare tabelle temporanee (nel caso in cui una precedente esecuzione sia rimasta sospesa)
			//dichiarazioni ICI
			try {
				st = conn.createStatement();
				st.execute(dropTableDichICI);
			} catch (SQLException e1) {
			}
			finally {
				try {
					if (st!=null)
						st.close();
				} catch (SQLException e1) {
				}
			}
			//pagamenti ICI
			try {
				st = conn.createStatement();
				st.execute(dropTablePagICI);
			} catch (SQLException e1) {
			}
			finally {
				try {
					if (st!=null)
						st.close();
				} catch (SQLException e1) {
				}
			}
			//soggetti
			try {
				st = conn.createStatement();
				st.execute(dropTableSogg);
			} catch (SQLException e1) {
			}
			finally {
				try {
					if (st!=null)
						st.close();
				} catch (SQLException e1) {
				}
			}
			//TARSU
			try {
				st = conn.createStatement();
				st.execute(dropTableTARSU);
			} catch (SQLException e1) {
			}
			finally {
				try {
					if (st!=null)
						st.close();
				} catch (SQLException e1) {
				}
			}
			//vie
			try {
				st = conn.createStatement();
				st.execute(dropTableVie);
			} catch (SQLException e1) {
			}
			finally {
				try {
					if (st!=null)
						st.close();
				} catch (SQLException e1) {
				}
			}
			
			//dichiarazioni ICI
			//creazione tabella temporanea
			try {
				st = conn.createStatement();
				st.execute(createTableDichICI);
			} catch (SQLException e1) {
				//se la tabella esiste già, la svuoto
				try {
					st.close();
					st = conn.createStatement();
					st.execute(deleteTableDichICI);
				} catch (SQLException e2) {
				}
			}
			finally {
				try {
					if (st!=null)
						st.close();
				} catch (SQLException e1) {
				}
			}
			//creazione indice
			try {
				st = conn.createStatement();
				st.execute(createIndexDichICI);
			} catch (SQLException e1) {
			}
			finally {
				try {
					if (st!=null)
						st.close();
				} catch (SQLException e1) {
				}
			}
			
			if (fileDichICI != null) {
				//scrittura dati in tabella temporanea
				msgDichICI = leggiFile(conn, fileDichICI, filePathFrom, TAB_TEMP_DICH_ICI, false);
				// creazione cartella per file elaborati
				if(!new File(filePathFrom + "ELABORATI/").exists()) {
					new File(filePathFrom + "ELABORATI/").mkdir();
				}
				//spostamento del file nella cartella elaborati
				new File(filePathFrom + fileDichICI).renameTo(new File(filePathFrom + "ELABORATI/" + fileDichICI));
			}			
			
			//pagamenti ICI
			//creazione tabella temporanea
			try {
				st = conn.createStatement();
				st.execute(createTablePagICI);
			} catch (SQLException e1) {
				//se la tabella esiste già, la svuoto
				try {
					st.close();
					st = conn.createStatement();
					st.execute(deleteTablePagICI);
				} catch (SQLException e2) {
				}
			}
			finally {
				try {
					if (st!=null)
						st.close();
				} catch (SQLException e1) {
				}
			}
			
			if (filePagICI != null) {
				//scrittura dati in tabella temporanea
				msgPagICI = leggiFile(conn, filePagICI, filePathFrom, TAB_TEMP_PAG_ICI, false);
				// creazione cartella per file elaborati
				if(!new File(filePathFrom + "ELABORATI/").exists()) {
					new File(filePathFrom + "ELABORATI/").mkdir();
				}
				//spostamento del file nella cartella elaborati
				new File(filePathFrom + filePagICI).renameTo(new File(filePathFrom + "ELABORATI/" + filePagICI));
			}	
			
			//soggetti
			//creazione tabella temporanea
			try {
				st = conn.createStatement();
				st.execute(createTableSogg);
			} catch (SQLException e1) {
				//se la tabella esiste già, la svuoto
				try {
					st.close();
					st = conn.createStatement();
					st.execute(deleteTableSogg);
				} catch (SQLException e2) {
				}
			}
			finally {
				try {
					if (st!=null)
						st.close();
				} catch (SQLException e1) {
				}	
			}
			//creazione indice
			try {
				st = conn.createStatement();
				st.execute(createIndexSogg);
			} catch (SQLException e1) {
			}
			finally {
				try {
					if (st!=null)
						st.close();
				} catch (SQLException e1) {
				}
			}
			
			if (fileSogg != null) {
				//scrittura dati in tabella temporanea
				msgSogg = leggiFile(conn, fileSogg, filePathFrom, TAB_TEMP_SOGG, false);
				// creazione cartella per file elaborati
				if(!new File(filePathFrom + "ELABORATI/").exists()) {
					new File(filePathFrom + "ELABORATI/").mkdir();
				}
				//spostamento del file nella cartella elaborati
				new File(filePathFrom + fileSogg).renameTo(new File(filePathFrom + "ELABORATI/" + fileSogg));
			}
			
			//TARSU
			//creazione tabella temporanea
			try {
				st = conn.createStatement();
				st.execute(createTableTARSU);
			} catch (SQLException e1) {
				//se la tabella esiste già, la svuoto
				try {
					st.close();
					st = conn.createStatement();
					st.execute(deleteTableTARSU);
				} catch (SQLException e2) {
				}
			}
			finally {
				try {
					if (st!=null)
						st.close();
				} catch (SQLException e1) {
				}
			}
			//creazione indice
			try {
				st = conn.createStatement();
				st.execute(createIndexTARSU);
			} catch (SQLException e1) {
			}
			finally {
				try {
					if (st!=null)
						st.close();
				} catch (SQLException e1) {
				}
			}
			
			if (fileTARSU != null) {
				//scrittura dati in tabella temporanea
				msgTARSU = leggiFile(conn, fileTARSU, filePathFrom, TAB_TEMP_TARSU, false);
				// creazione cartella per file elaborati
				if(!new File(filePathFrom + "ELABORATI/").exists()) {
					new File(filePathFrom + "ELABORATI/").mkdir();
				}
				//spostamento del file nella cartella elaborati
				new File(filePathFrom + fileTARSU).renameTo(new File(filePathFrom + "ELABORATI/" + fileTARSU));
			}
			
			//vie
			//creazione tabella temporanea
			try {
				st = conn.createStatement();
				st.execute(createTableVie);
			} catch (SQLException e1) {
				//se la tabella esiste già, la svuoto
				try {
					st.close();
					st = conn.createStatement();
					st.execute(deleteTableVie);
				} catch (SQLException e2) {
				}
			}
			finally {
				try {
					if (st!=null)
						st.close();
				} catch (SQLException e1) {
				}
			}
			//creazione indice
			try {
				st = conn.createStatement();
				st.execute(createIndexVie);
			} catch (SQLException e1) {
			}
			finally {
				try {
					if (st!=null)
						st.close();
				} catch (SQLException e1) {
				}
			}
			
			if (fileVie != null) {
				//scrittura dati in tabella temporanea
				msgVie = leggiFile(conn, fileVie, filePathFrom, TAB_TEMP_VIE, false);
				// creazione cartella per file elaborati
				if(!new File(filePathFrom + "ELABORATI/").exists()) {
					new File(filePathFrom + "ELABORATI/").mkdir();
				}
				//spostamento del file nella cartella elaborati
				new File(filePathFrom + fileVie).renameTo(new File(filePathFrom + "ELABORATI/" + fileVie));
			}
			
			//copia dei dati se questi si trovano non in file di testo ma in tabelle (es. Milano schema TRIBUTI)
			//N.B.: non più utilizzato, si scrivono i file nuovo tracciato direttamente dalle tabelle dello schema TRIBUTI
			/*if (connTables != null) {
				msgTables = leggiTabelle(conn, filePathFrom);
			}*/
			
			//scrittura file nuovo tracciato da tabelle temporanee
			//o dalle tabelle dello schema TRIBUTI (vedi commento precedente)
			scriviNewFile(conn, filePathTo);
			
			//si chiude qui la connTables, che prima veniva chiusa in leggiTabelle()
			if (connTables != null && !connTables.isClosed())
				connTables.close();
			
			//drop tabelle temporanee
			//dichiarazioni ICI
			try {
				st = conn.createStatement();
				st.execute(dropTableDichICI);
			} catch (SQLException e1) {
			}
			finally {
				try {
					if (st!=null)
						st.close();
				} catch (SQLException e1) {
				}
			}
			//pagamenti ICI
			try {
				st = conn.createStatement();
				st.execute(dropTablePagICI);
			} catch (SQLException e1) {
			}
			finally {
				try {
					if (st!=null)
						st.close();
				} catch (SQLException e1) {
				}
			}
			//soggetti
			try {
				st = conn.createStatement();
				st.execute(dropTableSogg);
			} catch (SQLException e1) {
			}
			finally {
				try {
					if (st!=null)
						st.close();
				} catch (SQLException e1) {
				}
			}
			//TARSU
			try {
				st = conn.createStatement();
				st.execute(dropTableTARSU);
			} catch (SQLException e1) {
			}
			finally {
				try {
					if (st!=null)
						st.close();
				} catch (SQLException e1) {
				}
			}
			//vie
			try {
				st = conn.createStatement();
				st.execute(dropTableVie);
			} catch (SQLException e1) {
			}
			finally {
				try {
					if (st!=null)
						st.close();
				} catch (SQLException e1) {
				}
			}
			
			DbUtils.close(st);
			DbUtils.commitAndClose(conn);
			
		} catch (Exception e) {
			log.error("Errore durante la conversione dei file tributi dal vecchio al nuovo tracciato: " + e.getMessage(), e);
			try {
				DbUtils.rollbackAndClose(conn);
			} catch (Exception e1) {
				log.error(e1);
			}			
			throw(e);
		}
	    
		String msg = "Conversione dei file tributi dal vecchio al nuovo tracciato terminata";
		boolean trovatiErrori = false;
		if (msgDichICI != null && !msgDichICI.equals("")) {
			trovatiErrori = true;
			msg += ". Errore nel salvataggio dati dichiarazioni ICI: " + msgDichICI;
		}
		if (msgPagICI != null && !msgPagICI.equals("")) {
			trovatiErrori = true;
			msg += ". Errore nel salvataggio dati pagamenti ICI: " + msgPagICI;
		}
		if (msgSogg != null && !msgSogg.equals("")) {
			trovatiErrori = true;
			msg += ". Errore nel salvataggio dati soggetti: " + msgSogg;
		}
		if (msgTARSU != null && !msgTARSU.equals("")) {
			trovatiErrori = true;
			msg += ". Errore nel salvataggio dati TARSU: " + msgTARSU;
		}
		if (msgVie != null && !msgVie.equals("")) {
			trovatiErrori = true;
			msg += ". Errore nel salvataggio dati viario: " + msgVie;
		}
		if (msgTables != null && !msgTables.equals("")) {
			trovatiErrori = true;
			msg += ". Errore nella copia dei dati vecchio tracciato da tabelle DB: " + msgTables;
		}
		if (!trovatiErrori) {
			msg += " correttamente.";
			log.info(msg);
		} else {
			msg += ".";
			log.error(msg);
		}
	}
	
	public String checkFilenames(String filePathFrom, String filePathTo) {
		//i percorsi sono in questo caso le CARTELLE da cui leggere e in cui salvare i file...
		String errMsg = "";
		if (filePathFrom == null || filePathFrom.equals("")) {
			errMsg = "Percorso dei file con vecchio tracciato non indicato";
		}
		if (filePathTo == null || filePathTo.equals("")) {
			if (!errMsg.equals("")) {
				errMsg += "; ";
			}
			errMsg += "Percorso di destinazione dei file con nuovo tracciato non indicato";
		}
		try {
			String[] fs = it.webred.utils.FileUtils.cercaFileDaElaborare(filePathFrom);
			
			boolean trovatoDichICI = false;
			boolean trovatoPagICI = false;
			boolean trovatoSogg = false;
			boolean trovatoTARSU = false;
			viario = false;
			
			for (int i = 0; i < fs.length; i++) {
				if (fs[i].toLowerCase().startsWith(codEnte.toLowerCase()) && 
					fs[i].toLowerCase().endsWith(SUFF_FILE_DICH_ICI)) {
					trovatoDichICI = true;
					dataFornituraICI = getDataFornitura(fs[i]);
				}
				if (fs[i].toLowerCase().startsWith(codEnte.toLowerCase()) && 
					fs[i].toLowerCase().endsWith(SUFF_FILE_PAG_ICI)) {
					trovatoPagICI = true;
					dataFornituraVersamentiICI = getDataFornitura(fs[i]);
				}
				if (fs[i].toLowerCase().startsWith(codEnte.toLowerCase()) && 
					fs[i].toLowerCase().endsWith(SUFF_FILE_SOGG)) {
					trovatoSogg = true;
				}
				if (fs[i].toLowerCase().startsWith(codEnte.toLowerCase()) && 
					fs[i].toLowerCase().endsWith(SUFF_FILE_TARSU)) {
					trovatoTARSU = true;
					dataFornituraTARSU = getDataFornitura(fs[i]);
				}
				if (fs[i].toLowerCase().startsWith(codEnte.toLowerCase()) && 
					fs[i].toLowerCase().endsWith(SUFF_FILE_VIE)) {
					viario = true;
				}
			}
			
			if (!trovatoDichICI) {
				for (int i = 0; i < fs.length; i++) {
					for (String suff : SUFF_FILE_TAB_DICH_ICI) {
						if (fs[i].toLowerCase().endsWith(suff)) {
							trovatoDichICI = true;
							dataFornituraICI = new SimpleDateFormat("yyyyMMdd").format(new Date(new File(filePathFrom + "/" + fs[i]).lastModified()));
						}
					}					
				}
			}
			if (!trovatoPagICI) {
				for (int i = 0; i < fs.length; i++) {
					for (String suff : SUFF_FILE_TAB_PAG_ICI) {
						if (fs[i].toLowerCase().endsWith(suff)) {
							trovatoPagICI = true;
							dataFornituraVersamentiICI = new SimpleDateFormat("yyyyMMdd").format(new Date(new File(filePathFrom + "/" + fs[i]).lastModified()));
						}
					}					
				}
			}
			if (!trovatoSogg) {
				for (int i = 0; i < fs.length; i++) {
					for (String suff : SUFF_FILE_TAB_SOGG) {
						if (fs[i].toLowerCase().endsWith(suff)) {
							trovatoSogg = true;
						}
					}					
				}
			}
			if (!trovatoTARSU) {
				for (int i = 0; i < fs.length; i++) {
					for (String suff : SUFF_FILE_TAB_TARSU) {
						if (fs[i].toLowerCase().endsWith(suff)) {
							trovatoTARSU = true;
							dataFornituraTARSU = new SimpleDateFormat("yyyyMMdd").format(new Date(new File(filePathFrom + "/" + fs[i]).lastModified()));
						}
					}					
				}
			}
			if (!viario) {
				for (int i = 0; i < fs.length; i++) {
					for (String suff : SUFF_FILE_TAB_VIE) {
						if (fs[i].toLowerCase().endsWith(suff)) {
							viario = true;
						}
					}					
				}
			}
			
			if (!trovatoDichICI) {
				if (!errMsg.equals("")) {
					errMsg += "; ";
				}
				errMsg += "File dichiarazioni ICI non trovato";
			}
			if (!trovatoPagICI) {
				if (!errMsg.equals("")) {
					errMsg += "; ";
				}
				errMsg += "File pagamenti ICI non trovato";
			}
			if (!trovatoSogg) {
				if (!errMsg.equals("")) {
					errMsg += "; ";
				}
				errMsg += "File soggetti non trovato";
			}
			if (!trovatoTARSU) {
				if (!errMsg.equals("")) {
					errMsg += "; ";
				}
				errMsg += "File TARSU non trovato";
			}
			if (errMsg.toLowerCase().endsWith("non trovato")) {
				errMsg += ". IN CASO DI FILE MANCANTI, DEVONO ESSERE INSERITI FILE VUOTI.";
			}
		} catch (Exception e) {
			errMsg = e.getMessage();
		}
		return errMsg;
	}
	
	private String getProperty(String propName) {		
		String p =  DwhUtils.getProperty(this.getClass(), propName);
		return p;
	}
	
	private String getCodEnte(Connection conn) throws Exception {
		String codEnte = null;		
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		try {
			pstmt = conn.prepareStatement("SELECT CODENT FROM SIT_ENTE");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				codEnte = rs.getString("CODENT");
			}			
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (rs!=null)
					rs.close();
				if (pstmt!=null)
					pstmt.close();
			} catch (SQLException e1) {
			}
		}
		return codEnte;
	}
	
	private String getDesEnte(Connection conn) throws Exception {
		String desEnte = null;		
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		try {
			pstmt = conn.prepareStatement("SELECT DESCRIZIONE FROM SIT_ENTE");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				desEnte = rs.getString("DESCRIZIONE");
			}			
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (rs!=null)
					rs.close();
				if (pstmt!=null)
					pstmt.close();
			} catch (SQLException e1) {
			}
		}
		return desEnte;
	}
	
	private String getCodBelfiore(Connection conn, String codIstat) throws Exception {
		String codBelfiore = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		try {
			pstmt = conn.prepareStatement("SELECT CODI_FISC_LUNA FROM SITICOMU WHERE ISTATP || ISTATC = ?");
			pstmt.setString(1, codIstat);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				codBelfiore = rs.getString("CODI_FISC_LUNA");
			}			
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (rs!=null)
					rs.close();
				if (pstmt!=null)
					pstmt.close();
			} catch (SQLException e1) {
			}
		}		
		return codBelfiore;
	}
	
	private String getIdOrigSoggU(Connection conn, String codCnt, String provenienza, String tipoControllo) throws Exception {
		//dato non valorizzato... cancellare la riga seguente per recuperare il dato da query
		if (true) return "";
		////////////////////////////////////////////
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		try {
			pstmt = conn.prepareStatement(SQLS_CTRL_ID_ORIG_SOGG_U.get(tipoControllo));
			pstmt.setString(1, codCnt);
			pstmt.setString(2, provenienza);
			rs = pstmt.executeQuery();
			if (!rs.next()) {
				codCnt = "";
			}			
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (rs!=null)
					rs.close();
				if (pstmt!=null)
					pstmt.close();
			} catch (SQLException e1) {
			}
		}		
		return codCnt;
	}
	
	private String leggiFile(Connection conn, String file, String cartellaDati, String tempTable, boolean saltaIntestazione) throws Exception {
		
		String errMsg = "";
		BufferedReader fileIn = null;
		List<String> errori = new ArrayList<String>();
		try {
			if(new File(cartellaDati + "ELABORATI/" + file).exists()) {
				new File(cartellaDati + file).delete();
				errMsg = "Scartato file " + file + " perché già elaborato";				
				return errMsg;
			}
			fileIn = new BufferedReader(new FileReader(cartellaDati + file));
			
			String currentLine = null;
			int riga = 1;
			boolean primaRiga = true;
			String insertSql = null;
			while ((currentLine = fileIn.readLine()) != null) {				
				
				if (saltaIntestazione && primaRiga) {
					primaRiga = false;
					continue;
				}
				
				List<String> campi = Arrays.asList(currentLine.split("\\" + SEPARATORE, -1));
				
				if (tempTable.equalsIgnoreCase(TAB_TEMP_PAG_ICI)) {
					//in questo caso ci può essere un campo in più che nella versione definitiva del tracciato è stato eliminato
					//(numero bollettino duplicato)
					if (campi.size() == 19) {
						//il metodo remove dà eccezione, quindi:
						ArrayList<String> campiNew = new ArrayList<String>();
						for (int i = 0; i < campi.size(); i++) {
							if (i != 14) {
								campiNew.add(campi.get(i));
							}
						}
						campi = campiNew;
					}
				}
						
				if (riga == 1) {
					StringBuffer s = new StringBuffer();
					s.append("INSERT INTO ");
					s.append(tempTable);
					s.append(" VALUES(");
					for (int ii = 0; ii < campi.size(); ii++){
						s.append(ii == campi.size() - 1? "?)" : "?,");
					}
					insertSql = s.toString();	
				}
				riga++;
				
				if (((riga - 1) % 10000) == 1) {
					log.info("LETTURA DA FILE " + file + "; RECORD LETTI: " + (riga - 1));
				}
				
				PreparedStatement ps = null;
				try {
					ps = conn.prepareStatement(insertSql);
					for (int ii = 0; ii < campi.size(); ii++) {
						//è importante la trim perché i campi, nonostante il separatore "|", sembrano essere a lunghezza fissa...
						ps.setString(ii + 1, campi.get(ii).trim());
					}
					ps.executeUpdate();
				} catch (Exception e) {
					errori.add(currentLine);
				}  finally {
					if (ps!=null)
						ps.close();
				}
				primaRiga = false;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (fileIn!=null)
				fileIn.close();
			if (errori.size() > 0) {
				PrintWriter pw = new PrintWriter(cartellaDati + "ELABORATI/" + file + ".err");
				for (int ii = 0; ii < errori.size(); ii++) {
					pw.println(errori.get(ii));
				}
				pw.close();
				errMsg = "Errore di inserimento! Prodotto file " + file + ".err";
			}
		}
		return errMsg;
	}
	
	//metodo non più utilizzato, si scrivono i file nuovo tracciato direttamente dalle tabelle dello schema TRIBUTI
	/*private String leggiTabelle(Connection conn, String cartellaDati) throws Exception {
		
		String errMsg = "";
		List<String> errori = new ArrayList<String>();
		PreparedStatement selectPst = null;
		ResultSet rs = null;
		try {
			String[] tables = new String[] {TAB_TEMP_DICH_ICI, TAB_TEMP_TARSU, TAB_TEMP_PAG_ICI, TAB_TEMP_SOGG};
			String selectSql = null;			
			for (String table : tables) {
				selectSql = "SELECT * FROM " + table;
				//per test
				//non si effettua il caricamento di THSDPICI che contiene più di 30 milioni di record...
				if (table.equalsIgnoreCase(TAB_TEMP_PAG_ICI)) {
					selectSql += " WHERE 1 = 0";
				}
				//fine per test
				selectPst = connTables.prepareStatement(selectSql);
				rs = selectPst.executeQuery();
				LinkedHashMap<String, String> fields = new LinkedHashMap<String, String>();
				int conta = 0;
				log.info("INIZIO LETTURA DA TABELLA " + table);
				while (rs.next()) {
					conta++;
					if ((conta % 10000) == 1) {
						log.info("LETTURA DA TABELLA " + table + "; RECORD LETTI: " + conta);
					}
					PreparedStatement insertPst = null;
					String rec = "";
					try {
						String insertSql = "";
						String insertFields = "";
						String insertValues = "";
						for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
							String key = rs.getMetaData().getColumnName(i + 1);
							Object valueObj = rs.getObject(i + 1);
							String value = null;
							if (valueObj != null) {
								if (valueObj instanceof Date) {
									value = SDF.format((Date)valueObj);
								} else if (valueObj instanceof Number) {
									value = DF.format(((Number)valueObj).doubleValue());
								} else if (valueObj instanceof String) {
									value = ((String)valueObj).trim();
								} else {
									value = valueObj.toString().trim();
								}
								
								//caso particolare di flag che da "SI" e "NO" devono essere trasformati in "S" e "N"
								//cambiano anche i nomi dei campi
								if (table.equalsIgnoreCase(TAB_TEMP_DICH_ICI) && 
								(key.equalsIgnoreCase("FLG_POS_31_12") ||
								key.equalsIgnoreCase("FLG_ESE_31_12") ||
								key.equalsIgnoreCase("FLG_RID_31_12") ||
								key.equalsIgnoreCase("FLG_ABI_PRI_31_12"))) {
									key = key.replace("_31_12", "3112");
									value = value.substring(0, 1);
								}
								
								//casi in cui cambiano solo i nomi dei campi
								if (table.equalsIgnoreCase(TAB_TEMP_DICH_ICI) && 
								(key.equalsIgnoreCase("FOG"))) {
									key = "FOGLIO";
								}
								if (table.equalsIgnoreCase(TAB_TEMP_DICH_ICI) && 
								(key.equalsIgnoreCase("PRC_POS"))) {
									key = "PRC_POSS";
								}
								if (table.equalsIgnoreCase(TAB_TEMP_TARSU) && 
								(key.equalsIgnoreCase("FOG"))) {
									key = "FOGLIO";
								}
								if (table.equalsIgnoreCase(TAB_TEMP_TARSU) && 
								(key.equalsIgnoreCase("NUM"))) {
									key = "NUMERO";
								}
								if (table.equalsIgnoreCase(TAB_TEMP_SOGG) && 
								(key.equalsIgnoreCase("CMN_NSC"))) {
									key = "COM_NSC";
								}
								
								//partita IVA (deve essere sempre di 11 caratteri)
								if (key.equalsIgnoreCase("PAR_IVA")) {
									while (value.length() < 11) {
										value = "0" + value;
									}
								}
							}
							fields.put(key, value);
							if (!rec.equals("")) {
								rec += SEPARATORE;
							}
							rec += value;
						}
						
						Iterator<String> it = fields.keySet().iterator();
						while (it.hasNext()) {
							String key = it.next();
							
							if (insertFields.equals("")) {
								insertFields = "INSERT INTO " + table + " (";
							} else {
								insertFields += ", ";
							}
							insertFields += key;
							
							if (insertValues.equals("")) {
								insertValues = " VALUES (";
							} else {
								insertValues += ", ";
							}
							insertValues += "?";
						}
						
						insertFields += ")";
						insertValues += ")";
						insertSql = insertFields + insertValues;
						
						insertPst = conn.prepareStatement(insertSql);
						it = fields.keySet().iterator();
						int idx = 0;
						while (it.hasNext()) {
							String key = it.next();
							insertPst.setString(idx + 1, fields.get(key) == null ? null : fields.get(key).trim());
							idx++;
						}
						insertPst.executeUpdate();
					} catch (Exception e) {
						errori.add(rec);
					} finally {
						if (insertPst != null)
							insertPst.close();
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs!=null)
				rs.close();
			if (selectPst!=null)
				selectPst.close();
			if (connTables != null && !connTables.isClosed())
				connTables.close();
			if (errori.size() > 0) {
				// creazione cartella per file elaborati (in questo caso serve solo per il file degli errori)
				if(!new File(cartellaDati + "ELABORATI/").exists()) {
					new File(cartellaDati + "ELABORATI/").mkdir();
				}
				String nomeFile = "tabelle_" + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
				PrintWriter pw = new PrintWriter(cartellaDati + "ELABORATI/" + nomeFile + ".err");
				for (int ii = 0; ii < errori.size(); ii++) {
					pw.println(errori.get(ii));
				}
				pw.close();
				errMsg = "Errore di inserimento! Prodotto file " + nomeFile + ".err";
			}
		}
		return errMsg;
	}*/
	
	private String getDataFornitura(String nomeFile) throws Exception {
		String dataFornitura = null;
		String[] splitNomeFile = nomeFile.split("\\_", -1);
		boolean err = false;
		if (splitNomeFile == null || splitNomeFile.length != 3) {
			err = true;
		} else {
			String dataInSplit = splitNomeFile[1];
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			try {
				java.util.Date ctrlData = sdf.parse(dataInSplit);
				dataFornitura = sdf.format(ctrlData);
			} catch (Exception e) {
				err = true;
			}			
		}
		if (err) {
			throw new Exception("Data fornitura non trovata per nome file: " + nomeFile);
		}		
		return dataFornitura;
	}
	
	private void scriviNewFile(Connection conn, String filePathTo) throws Exception {
		
		Connection myConnTables = null;
		if (connTables != null) {
			myConnTables = connTables;
		} else {
			myConnTables = conn;
		}
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {		
			
			String sql = null;
			
			//leggo quali sono le provenienze			
			ArrayList<String> provenienzeICI = new ArrayList<String>();
			ArrayList<String> provenienzePagICI = new ArrayList<String>();
			ArrayList<String> provenienzeTARSU = new ArrayList<String>();
			
			String sqlProvenienzeICI = "SELECT DISTINCT DATI_ORI FROM " + TAB_TEMP_DICH_ICI;
			String sqlProvenienzePagICI = "SELECT DISTINCT DATI_ORI FROM " + TAB_TEMP_PAG_ICI;
			String sqlProvenienzeTARSU = "SELECT DISTINCT DATI_ORI FROM " + TAB_TEMP_SOGG + " S " +
										"WHERE EXISTS(SELECT 1 FROM " + TAB_TEMP_TARSU + " T WHERE T.COD_CNT = S.COD_CNT)";
			
			sql = sqlProvenienzeICI;
			pst = myConnTables.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				if (rs.getObject("DATI_ORI") != null) {
					provenienzeICI.add(rs.getString("DATI_ORI"));
				}
			}
			if (rs!=null)
				rs.close();
			if (pst!=null)
				pst.close();
			
			sql = sqlProvenienzePagICI;
			pst = myConnTables.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				if (rs.getObject("DATI_ORI") != null) {
					provenienzePagICI.add(rs.getString("DATI_ORI"));
				}
			}
			if (rs!=null)
				rs.close();
			if (pst!=null)
				pst.close();
			
			sql = sqlProvenienzeTARSU;
			pst = myConnTables.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				if (rs.getObject("DATI_ORI") != null) {
					provenienzeTARSU.add(rs.getString("DATI_ORI"));
				}
			}
			if (rs!=null)
				rs.close();
			if (pst!=null)
				pst.close();

			PrintWriter pw = null;
			int conta = 0;
			
			//file ICI
			for (String provenienza : provenienzeICI) {
				String nomeFileICI = COD_TRI_ICI + "_" + provenienza + "_" + codEnte + "_" + dataFornituraICI + ".txt";
				pw = new PrintWriter(filePathTo + nomeFileICI);
				pw.println(getRecordTesta(dataFornituraICI, VER_ICI, provenienza));

				log.info("ESECUZIONE QUERY: " + SQL_ANA_SOGG_ICI + "; PROVENIENZA: " + provenienza);
				sql = SQL_ANA_SOGG_ICI;
				pst = myConnTables.prepareStatement(sql);
				pst.setString(1, provenienza);
				rs = pst.executeQuery();
				conta = 0;
				while (rs.next()) {
					pw.println(getRecordAnaSoggICI(myConnTables, conn, rs));
					conta++;
					if ((conta % 10000) == 1) {
						log.info("QUERY: " + SQL_ANA_SOGG_ICI + "; RECORD LETTI: " + conta);
					}
				}
				if (rs!=null)
					rs.close();
				if (pst!=null)
					pst.close();
				
				log.info("ESECUZIONE QUERY: " + SQL_DICH_OGG_ICI + "; PROVENIENZA: " + provenienza);
				sql = SQL_DICH_OGG_ICI;
				pst = myConnTables.prepareStatement(sql);
				pst.setString(1, provenienza);
				rs = pst.executeQuery();
				conta = 0;
				while (rs.next()) {
					pw.println(getRecordDichOggICI(rs));
					conta++;
					if ((conta % 10000) == 1) {
						log.info("QUERY: " + SQL_DICH_OGG_ICI + "; RECORD LETTI: " + conta);
					}
				}
				if (rs!=null)
					rs.close();
				if (pst!=null)
					pst.close();
				
				if (viario) {
					log.info("ESECUZIONE QUERY: " + SQL_VIARIO + "; PROVENIENZA: " + provenienza);
					sql = SQL_VIARIO;
					pst = myConnTables.prepareStatement(sql);
					rs = pst.executeQuery();
					conta = 0;
					while (rs.next()) {
						pw.println(getRecordViario(rs, TIP_REC_VIARIO_UFF_ICI));
						conta++;
						if ((conta % 10000) == 1) {
							log.info("QUERY: " + SQL_VIARIO + "; RECORD LETTI: " + conta);
						}
					}
					if (rs!=null)
						rs.close();
					if (pst!=null)
						pst.close();
				} else {
					//se non c'è il file delle vie le tabelle delle vie non vengono riempite;
					//saranno visualizzati i campi des_ind estesi
					
					/*log.info("ESECUZIONE QUERY: " + SQL_VIARIO_UFF_ICI + "; PROVENIENZA: " + provenienza);
					sql = SQL_VIARIO_UFF_ICI;
					pst = myConnTables.prepareStatement(sql);
					pst.setString(1, provenienza);
					rs = pst.executeQuery();
					conta = 0;
					while (rs.next()) {
						pw.println(getRecordViarioUffICI(rs));
						conta++;
						if ((conta % 10000) == 1) {
							log.info("QUERY: " + SQL_VIARIO_UFF_ICI + "; RECORD LETTI: " + conta);
						}
					}
					if (rs!=null)
						rs.close();
					if (pst!=null)
						pst.close();*/
				}
				
				log.info("ESECUZIONE QUERY: " + SQL_RID_TAR_ICI + "; PROVENIENZA: " + provenienza);
				sql = SQL_RID_TAR_ICI;
				pst = myConnTables.prepareStatement(sql);
				pst.setString(1, provenienza);
				rs = pst.executeQuery();
				conta = 0;
				while (rs.next()) {
					pw.println(getRecordRidTarICI(rs));
					conta++;
					if ((conta % 10000) == 1) {
						log.info("QUERY: " + SQL_RID_TAR_ICI + "; RECORD LETTI: " + conta);
					}
				}
				if (rs!=null)
					rs.close();
				if (pst!=null)
					pst.close();
				
				pw.println(getRecordCoda(dataFornituraICI));
				pw.close();
			}
			
			//file versamenti ICI
			for (String provenienza : provenienzePagICI) {
				String nomeFileVersamentiICI = COD_TRI_VERSAMENTI_ICI + "_" + provenienza + "_" + codEnte + "_" + dataFornituraVersamentiICI + ".txt";
				pw = new PrintWriter(filePathTo + nomeFileVersamentiICI);
				pw.println(getRecordTesta(dataFornituraVersamentiICI, VER_VERSAMENTI_ICI, provenienza));

				log.info("ESECUZIONE QUERY: " + SQL_VERSAMENTI_ICI + "; PROVENIENZA: " + provenienza);
				sql = SQL_VERSAMENTI_ICI;
				pst = myConnTables.prepareStatement(sql);
				pst.setString(1, provenienza);
				rs = pst.executeQuery();
				conta = 0;
				while (rs.next()) {
					conta++;
					pw.println(getRecordVersamentiICI(conta, myConnTables, rs));
					if ((conta % 10000) == 1) {
						log.info("QUERY: " + SQL_VERSAMENTI_ICI + "; RECORD LETTI: " + conta);
					}
				}
				if (rs!=null)
					rs.close();
				if (pst!=null)
					pst.close();
				
				pw.println(getRecordCoda(dataFornituraVersamentiICI));
				pw.close();
			}
			
			
			//file TARSU
			for (String provenienza : provenienzeTARSU) {
				String nomeFileTARSU = COD_TRI_TARSU + "_" + provenienza + "_" + codEnte + "_" + dataFornituraTARSU + ".txt";
				pw = new PrintWriter(filePathTo + nomeFileTARSU);
				pw.println(getRecordTesta(dataFornituraTARSU, VER_TARSU, provenienza));

				log.info("ESECUZIONE QUERY: " + SQL_ANA_SOGG_TARSU + "; PROVENIENZA: " + provenienza);
				sql = SQL_ANA_SOGG_TARSU;
				pst = myConnTables.prepareStatement(sql);
				pst.setString(1, provenienza);
				rs = pst.executeQuery();
				conta = 0;
				while (rs.next()) {
					pw.println(getRecordAnaSoggTARSU(myConnTables, conn, rs));
					conta++;
					if ((conta % 10000) == 1) {
						log.info("QUERY: " + SQL_ANA_SOGG_TARSU + "; RECORD LETTI: " + conta);
					}
				}
				if (rs!=null)
					rs.close();
				if (pst!=null)
					pst.close();
				
				log.info("ESECUZIONE QUERY: " + SQL_DICH_OGG_TARSU + "; PROVENIENZA: " + provenienza);
				sql = SQL_DICH_OGG_TARSU;
				pst = myConnTables.prepareStatement(sql);
				pst.setString(1, provenienza);
				rs = pst.executeQuery();
				conta = 0;
				while (rs.next()) {
					pw.println(getRecordDichOggTARSU(rs));
					conta++;
					if ((conta % 10000) == 1) {
						log.info("QUERY: " + SQL_DICH_OGG_TARSU + "; RECORD LETTI: " + conta);
					}
				}
				if (rs!=null)
					rs.close();
				if (pst!=null)
					pst.close();
				
				if (viario) {
					log.info("ESECUZIONE QUERY: " + SQL_VIARIO + "; PROVENIENZA: " + provenienza);
					sql = SQL_VIARIO;
					pst = myConnTables.prepareStatement(sql);
					rs = pst.executeQuery();
					conta = 0;
					while (rs.next()) {
						pw.println(getRecordViario(rs, TIP_REC_VIARIO_UFF_TARSU));
						conta++;
						if ((conta % 10000) == 1) {
							log.info("QUERY: " + SQL_VIARIO + "; RECORD LETTI: " + conta);
						}
					}
					if (rs!=null)
						rs.close();
					if (pst!=null)
						pst.close();
				} else {
					//se non c'è il file delle vie le tabelle delle vie non vengono riempite;
					//saranno visualizzati i campi des_ind estesi
					
					/*log.info("ESECUZIONE QUERY: " + SQL_VIARIO_UFF_TARSU + "; PROVENIENZA: " + provenienza);
					sql = SQL_VIARIO_UFF_TARSU;
					pst = myConnTables.prepareStatement(sql);
					pst.setString(1, provenienza);
					rs = pst.executeQuery();
					conta = 0;
					while (rs.next()) {
						pw.println(getRecordViarioUffTARSU(rs));
						conta++;
						if ((conta % 10000) == 1) {
							log.info("QUERY: " + SQL_VIARIO_UFF_TARSU + "; RECORD LETTI: " + conta);
						}
					}
					if (rs!=null)
						rs.close();
					if (pst!=null)
						pst.close();*/
				}				
				
				log.info("ESECUZIONE QUERY: " + SQL_RID_TAR_TARSU + "; PROVENIENZA: " + provenienza);
				sql = SQL_RID_TAR_TARSU;
				pst = myConnTables.prepareStatement(sql);
				pst.setString(1, provenienza);
				rs = pst.executeQuery();
				conta = 0;
				while (rs.next()) {
					pw.println(getRecordRidTarTARSU(rs));
					conta++;
					if ((conta % 10000) == 1) {
						log.info("QUERY: " + SQL_RID_TAR_TARSU + "; RECORD LETTI: " + conta);
					}
				}
				if (rs!=null)
					rs.close();
				if (pst!=null)
					pst.close();
				
				pw.println(getRecordCoda(dataFornituraTARSU));
				pw.close();
			}			
			
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (rs!=null)
					rs.close();
				if (pst!=null)
					pst.close();
			} catch (SQLException e1) {
			}
		}		
	}
	
	private String getRecordTesta(String dataFornitura, String versione, String provenienza) throws Exception {
		String rec = TIP_REC_TESTA;
		rec += SEPARATORE;
		rec += codEnte.toUpperCase();
		rec += SEPARATORE;
		rec += "COMUNE DI " + desEnte;
		rec += SEPARATORE;
		//la data arriva in formato yyyyMMdd ed è convertita in formato dd/MM/yyyy
		rec += new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyyMMdd").parse(dataFornitura));
		rec += SEPARATORE;
		rec += versione;
		rec += SEPARATORE;
		rec += provenienza;
		return rec;
	}
	
	private String getRecordCoda(String dataFornitura) throws Exception {
		String rec = TIP_REC_CODA;
		rec += SEPARATORE;
		rec += codEnte.toUpperCase();
		rec += SEPARATORE;
		rec += "COMUNE DI " + desEnte;
		rec += SEPARATORE;
		//la data arriva in formato yyyyMMdd ed è convertita in formato dd/MM/yyyy
		rec += new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyyMMdd").parse(dataFornitura));
		return rec;
	}
	
	private String getRecordAnaSoggICI(Connection myConnTables, Connection conn, ResultSet rs) throws Exception {
		String rec = TIP_REC_ANA_SOGG_ICI;
		rec += SEPARATORE;
		rec += getRecordAnaSogg(myConnTables, conn, rs, CTRL_IN_TARSU);		
		return rec;
	}
	
	private String getRecordDichOggICI(ResultSet rs) throws Exception {
		String rec = TIP_REC_DICH_OGG_ICI;
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("COD_OGG") != null ? rs.getString("COD_OGG") : "";
		} else {
			rec += rs.getObject("COD_OGG") != null ? DF.format(((Number)rs.getObject("COD_OGG")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		String codCnt = "";
		if (connTables == null) {
			codCnt = rs.getObject("COD_CNT") != null ? rs.getString("COD_CNT") : "";
		} else {
			codCnt = rs.getObject("COD_CNT") != null ? DF.format(((Number)rs.getObject("COD_CNT")).doubleValue()) : "";
		}
		rec += codCnt;
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("YEA_DEN") != null ? rs.getString("YEA_DEN") : "";
		} else {
			rec += rs.getObject("YEA_DEN") != null ? DF.format(((Number)rs.getObject("YEA_DEN")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("NUM_DEN") != null ? rs.getString("NUM_DEN") : "";
		} else {
			rec += rs.getObject("NUM_DEN") != null ? DF.format(((Number)rs.getObject("NUM_DEN")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("YEA_RIF") != null ? rs.getString("YEA_RIF") : "";
		} else {
			rec += rs.getObject("YEA_RIF") != null ? DF.format(((Number)rs.getObject("YEA_RIF")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		String tipDen = "";
		if (connTables == null) {
			tipDen = rs.getObject("TIP_DEN") != null ? rs.getString("TIP_DEN") : "";
		} else {
			tipDen = rs.getObject("TIP_DEN") != null ? DF.format(((Number)rs.getObject("TIP_DEN")).doubleValue()) : "";
		}		
		int intTipDen = tipDen.equals("") ? -1 : Integer.parseInt(tipDen);
		rec += tipDen;
		rec += SEPARATORE;
		String descTipDen = "";
		switch (intTipDen) {
			case 1:
				descTipDen = "INSERIMENTO DENUNCIA";
			case 5:
				descTipDen = "VARIAZIONE DENUNCIA";
			case 10:
				descTipDen = "VAR. 95 DENUNCIA ICI";
			case 11:
				descTipDen = "VAR. POST.95 DENUNCIA";
			case 12:
				descTipDen = "ADEMPIMENTI UNICI NOTAI";
			default:
				descTipDen = "";
		}
		rec += descTipDen;
		rec += SEPARATORE;
		rec += rs.getObject("TIP_CONT_DIC") != null && rs.getString("TIP_CONT_DIC").equalsIgnoreCase("C") ? codCnt : "";
		rec += SEPARATORE;
		rec += rs.getObject("SEZ") != null ? rs.getString("SEZ") : "";
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("FOGLIO") != null ? rs.getString("FOGLIO") : "";
		} else {
			rec += rs.getObject("FOG") != null ? rs.getString("FOG") : "";
		}
		rec += SEPARATORE;
		rec += rs.getObject("NUM") != null ? rs.getString("NUM") : "";
		rec += SEPARATORE;
		rec += rs.getObject("SUB") != null ? rs.getString("SUB") : "";
		rec += SEPARATORE;
		rec += rs.getObject("CAT") != null ? rs.getString("CAT") : "";
		rec += SEPARATORE;
		rec += rs.getObject("CLS") != null ? rs.getString("CLS") : "";
		rec += SEPARATORE;
		String tipVal = "";
		if (connTables == null) {
			tipVal = rs.getObject("TIP_VAL") != null ? rs.getString("TIP_VAL") : "";
		} else {
			tipVal = rs.getObject("TIP_VAL") != null ? DF.format(((Number)rs.getObject("TIP_VAL")).doubleValue()) : "";
		}
		int intTipVal = tipVal.equals("") ? -1 : Integer.parseInt(tipVal);
		rec += tipVal;
		rec += SEPARATORE;
		String descTipVal = "";
		switch (intTipVal) {
			case 1:
				descTipVal = "Rendita";
			case 3:
				descTipVal = "Valore";
			default:
				descTipVal = "";
		}
		rec += descTipVal;
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("VAL_IMM") != null ? rs.getString("VAL_IMM") : "";
		} else {
			rec += rs.getObject("VAL_IMM") != null ? DF.format(((Number)rs.getObject("VAL_IMM")).doubleValue()) : "";
		}		
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("PRC_POSS") != null ? rs.getString("PRC_POSS") : "";
		} else {
			rec += rs.getObject("PRC_POS") != null ? DF.format(((Number)rs.getObject("PRC_POS")).doubleValue()) : "";
		}		
		rec += SEPARATORE;
		rec += rs.getObject("CAR_IMM") != null ? rs.getString("CAR_IMM") : "";
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("DTR_ABI_PRI") != null ? rs.getString("DTR_ABI_PRI") : "";
		} else {
			rec += rs.getObject("DTR_ABI_PRI") != null ? DF.format(((Number)rs.getObject("DTR_ABI_PRI")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("NUM_MOD") != null ? rs.getString("NUM_MOD") : "";
		} else {
			rec += rs.getObject("NUM_MOD") != null ? DF.format(((Number)rs.getObject("NUM_MOD")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("NUM_RIGA") != null ? rs.getString("NUM_RIGA") : "";
		} else {
			rec += rs.getObject("NUM_RIGA") != null ? DF.format(((Number)rs.getObject("NUM_RIGA")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("SUF_RIGA") != null ? rs.getString("SUF_RIGA") : "";
		} else {
			rec += rs.getObject("SUF_RIGA") != null ? DF.format(((Number)rs.getObject("SUF_RIGA")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("FLG_IMM_STO") != null ? rs.getString("FLG_IMM_STO") : "";
		} else {
			rec += rs.getObject("FLG_IMM_STO") != null ? DF.format(((Number)rs.getObject("FLG_IMM_STO")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("MESI_POS") != null ? rs.getString("MESI_POS") : "";
		} else {
			rec += rs.getObject("MESI_POS") != null ? DF.format(((Number)rs.getObject("MESI_POS")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("MESI_ESE") != null ? rs.getString("MESI_ESE") : "";
		} else {
			rec += rs.getObject("MESI_ESE") != null ? DF.format(((Number)rs.getObject("MESI_ESE")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("MESI_RID") != null ? rs.getString("MESI_RID") : "";
		} else {
			rec += rs.getObject("MESI_RID") != null ? DF.format(((Number)rs.getObject("MESI_RID")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("FLG_POS3112") != null ? rs.getString("FLG_POS3112") : "";
			rec += SEPARATORE;
			rec += rs.getObject("FLG_ESE3112") != null ? rs.getString("FLG_ESE3112") : "";
			rec += SEPARATORE;
			rec += rs.getObject("FLG_RID3112") != null ? rs.getString("FLG_RID3112") : "";
			rec += SEPARATORE;
			rec += rs.getObject("FLG_ABI_PRI3112") != null ? rs.getString("FLG_ABI_PRI3112") : "";
		} else {
			rec += rs.getObject("FLG_POS_31_12") != null ? rs.getString("FLG_POS_31_12").substring(0, 1) : "";
			rec += SEPARATORE;
			rec += rs.getObject("FLG_ESE_31_12") != null ? rs.getString("FLG_ESE_31_12").substring(0, 1) : "";
			rec += SEPARATORE;
			rec += rs.getObject("FLG_RID_31_12") != null ? rs.getString("FLG_RID_31_12").substring(0, 1) : "";
			rec += SEPARATORE;
			rec += rs.getObject("FLG_ABI_PRI_31_12") != null ? rs.getString("FLG_ABI_PRI_31_12").substring(0, 1) : "";
		}		
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("YEA_PRO") != null ? rs.getString("YEA_PRO") : "";
		} else {
			rec += rs.getObject("YEA_PRO") != null ? DF.format(((Number)rs.getObject("YEA_PRO")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		rec += rs.getObject("NUM_PRO") != null ? rs.getString("NUM_PRO") : "";
		rec += SEPARATORE;
		rec += rs.getObject("FLG_TRF") != null ? rs.getString("FLG_TRF") : "";
		rec += SEPARATORE;
		rec += rs.getObject("DES_VIA") != null ? rs.getString("DES_VIA") : "";
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("COD_VIA") != null ? rs.getString("COD_VIA") : "";
		} else {
			rec += rs.getObject("COD_VIA") != null ? DF.format(((Number)rs.getObject("COD_VIA")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("NUM_CIV") != null ? rs.getString("NUM_CIV") : "";
		} else {
			rec += rs.getObject("NUM_CIV") != null ? DF.format(((Number)rs.getObject("NUM_CIV")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		rec += rs.getObject("ESP_CIV") != null ? rs.getString("ESP_CIV") : "";
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("NUM_INT") != null ? rs.getString("NUM_INT") : "";
		} else {
			rec += rs.getObject("NUM_INT") != null ? DF.format(((Number)rs.getObject("NUM_INT")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		rec += rs.getObject("TMS_AGG") != null ? rs.getString("TMS_AGG") : "";
		rec += SEPARATORE;
		rec += rs.getObject("TMS_BON") != null ? rs.getString("TMS_BON") : "";
		
		return rec;
	}
	
	private String getRecordViarioUffICI(ResultSet rs) throws Exception {
		String rec = TIP_REC_VIARIO_UFF_ICI;
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("COD_VIA") != null ? rs.getString("COD_VIA") : "";
		} else {
			rec += rs.getObject("COD_VIA") != null ? DF.format(((Number)rs.getObject("COD_VIA")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		rec += rs.getObject("DES_VIA") != null ? rs.getString("DES_VIA") : "";
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		rec += "";
		
		return rec;
	}
	
	private String getRecordRidTarICI(ResultSet rs) throws Exception {
		String rec = TIP_REC_RID_TAR_ICI;
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("COD_OGG") != null ? rs.getString("COD_OGG") : "";
		} else {
			rec += rs.getObject("COD_OGG") != null ? DF.format(((Number)rs.getObject("COD_OGG")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("MESI_RID") != null ? rs.getString("MESI_RID") : "";
		} else {
			rec += rs.getObject("MESI_RID") != null ? DF.format(((Number)rs.getObject("MESI_RID")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		rec += "Numero mesi riduzione";
		
		return rec;
	}
	
	private String getRecordVersamentiICI(int idx, Connection myConnTables, ResultSet rs) throws Exception {
		String rec = TIP_REC_VERSAMENTI_ICI;
		rec += SEPARATORE;
		String idOrigVici = dataFornituraVersamentiICI;
		String strIdx = "" + idx;
		while (strIdx.length() < 8) {
			strIdx = "0" + strIdx;
		}
		idOrigVici += strIdx;
		rec += idOrigVici;
		rec += SEPARATORE;
		String codCnt = "";
		if (connTables == null) {
			codCnt = rs.getObject("COD_CNT") != null ? rs.getString("COD_CNT") : "";
		} else {
			codCnt = rs.getObject("COD_CNT") != null ? DF.format(((Number)rs.getObject("COD_CNT")).doubleValue()) : "";
		}
		rec += codCnt;
		rec += SEPARATORE;
		String provenienza = rs.getObject("DATI_ORI") != null ? rs.getString("DATI_ORI") : "";
		rec += getIdOrigSoggU(myConnTables, codCnt, provenienza, CTRL_IN_TARSU);
		rec += SEPARATORE;
		//record TDSOGG vuoto (34 campi)
		for (int i = 0; i < 34; i++) {
			rec += "";
			rec += SEPARATORE;
		}
		if (connTables == null) {
			rec += rs.getObject("YEA_RIF") != null ? rs.getString("YEA_RIF") : "";
		} else {
			rec += rs.getObject("YEA_RIF") != null ? DF.format(((Number)rs.getObject("YEA_RIF")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		rec += rs.getObject("DAT_PAG") != null ? rs.getString("DAT_PAG") : "";
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("IMP_PAG_EU") != null ? rs.getString("IMP_PAG_EU") : "";
		} else {
			rec += rs.getObject("IMP_PAG_EU") != null ? DF.format(((Number)rs.getObject("IMP_PAG_EU")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("IMP_TER_AGR_EU") != null ? rs.getString("IMP_TER_AGR_EU") : "";
		} else {
			rec += rs.getObject("IMP_TER_AGR_EU") != null ? DF.format(((Number)rs.getObject("IMP_TER_AGR_EU")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("IMP_ARE_FAB_EU") != null ? rs.getString("IMP_ARE_FAB_EU") : "";
		} else {
			rec += rs.getObject("IMP_ARE_FAB_EU") != null ? DF.format(((Number)rs.getObject("IMP_ARE_FAB_EU")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("IMP_ABI_PRI_EU") != null ? rs.getString("IMP_ABI_PRI_EU") : "";
		} else {
			rec += rs.getObject("IMP_ABI_PRI_EU") != null ? DF.format(((Number)rs.getObject("IMP_ABI_PRI_EU")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("IMP_ALT_FAB_EU") != null ? rs.getString("IMP_ALT_FAB_EU") : "";
		} else {
			rec += rs.getObject("IMP_ALT_FAB_EU") != null ? DF.format(((Number)rs.getObject("IMP_ALT_FAB_EU")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("IMP_DTR_EU") != null ? rs.getString("IMP_DTR_EU") : "";
		} else {
			rec += rs.getObject("IMP_DTR_EU") != null ? DF.format(((Number)rs.getObject("IMP_DTR_EU")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("NUM_FAB") != null ? rs.getString("NUM_FAB") : "";
		} else {
			rec += rs.getObject("NUM_FAB") != null ? DF.format(((Number)rs.getObject("NUM_FAB")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		rec += rs.getObject("FLG_ACC_SAL") != null ? rs.getString("FLG_ACC_SAL") : "";
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("NUM_BOLL") != null ? rs.getString("NUM_BOLL") : "";
		} else {
			rec += rs.getObject("NUM_BOLL") != null ? DF.format(((Number)rs.getObject("NUM_BOLL")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		rec += rs.getObject("TIP_PAG") != null ? rs.getString("TIP_PAG") : "";
		rec += SEPARATORE;
		rec += rs.getObject("TMS_AGG") != null ? rs.getString("TMS_AGG") : "";
		rec += SEPARATORE;
		rec += rs.getObject("FLG_TRF") != null ? rs.getString("FLG_TRF") : "";
		rec += SEPARATORE;
		rec += rs.getObject("TMS_BON") != null ? rs.getString("TMS_BON") : "";
		
		return rec;
	}
	
	private String getRecordAnaSoggTARSU(Connection myConnTables, Connection conn, ResultSet rs) throws Exception {
		String rec = TIP_REC_ANA_SOGG_TARSU;
		rec += SEPARATORE;
		rec += getRecordAnaSogg(myConnTables, conn, rs, CTRL_IN_ICI);		
		return rec;
	}
	
	private String getRecordDichOggTARSU(ResultSet rs) throws Exception {
		String rec = TIP_REC_DICH_OGG_TARSU;
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("COD_OGG") != null ? rs.getString("COD_OGG") : "";
		} else {
			rec += rs.getObject("COD_OGG") != null ? DF.format(((Number)rs.getObject("COD_OGG")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("COD_CNT") != null ? rs.getString("COD_CNT") : "";
		} else {
			rec += rs.getObject("COD_CNT") != null ? DF.format(((Number)rs.getObject("COD_CNT")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		rec += rs.getObject("DES_CLS_RSU") != null ? rs.getString("DES_CLS_RSU") : "";
		rec += SEPARATORE;
		rec += rs.getObject("SEZ") != null ? rs.getString("SEZ") : "";
		rec += SEPARATORE;		
		if (connTables == null) {
			rec += rs.getObject("FOGLIO") != null ? rs.getString("FOGLIO") : "";
		} else {
			rec += rs.getObject("FOG") != null ? rs.getString("FOG") : "";
		}
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("NUMERO") != null ? rs.getString("NUMERO") : "";
		} else {
			rec += rs.getObject("NUM") != null ? rs.getString("NUM") : "";
		}
		rec += SEPARATORE;
		rec += rs.getObject("SUB") != null ? rs.getString("SUB") : "";
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("SUP_TOT") != null ? rs.getString("SUP_TOT") : "";
		} else {
			rec += rs.getObject("SUP_TOT") != null ? DF.format(((Number)rs.getObject("SUP_TOT")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		rec += rs.getObject("DAT_INI") != null ? rs.getString("DAT_INI") : "";
		rec += SEPARATORE;
		rec += rs.getObject("DAT_FIN") != null ? rs.getString("DAT_FIN") : "";
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("TIP_OGG") != null ? rs.getString("TIP_OGG") : "";
		} else {
			rec += rs.getObject("TIP_OGG") != null ? DF.format(((Number)rs.getObject("TIP_OGG")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		rec += rs.getObject("DES_TIP_OGG") != null ? rs.getString("DES_TIP_OGG") : "";
		rec += SEPARATORE;
		rec += rs.getObject("DES_VIA") != null ? rs.getString("DES_VIA") : "";
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("COD_VIA") != null ? rs.getString("COD_VIA") : "";
		} else {
			rec += rs.getObject("COD_VIA") != null ? DF.format(((Number)rs.getObject("COD_VIA")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("NUM_CIV") != null ? rs.getString("NUM_CIV") : "";
		} else {
			rec += rs.getObject("NUM_CIV") != null ? DF.format(((Number)rs.getObject("NUM_CIV")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		rec += rs.getObject("ESP_CIV") != null ? rs.getString("ESP_CIV") : "";
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("NUM_INT") != null ? rs.getString("NUM_INT") : "";
		} else {
			rec += rs.getObject("NUM_INT") != null ? DF.format(((Number)rs.getObject("NUM_INT")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		rec += rs.getObject("TMS_AGG") != null ? rs.getString("TMS_AGG") : "";
		rec += SEPARATORE;
		rec += rs.getObject("TMS_BON") != null ? rs.getString("TMS_BON") : "";
		
		return rec;
	}
	
	private String getRecordViario(ResultSet rs, String tipRec) throws Exception {
		String rec = tipRec;
		rec += SEPARATORE;
		rec += rs.getObject("COD_VIA") != null ? rs.getString("COD_VIA") : "";
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		rec += rs.getObject("SEDIME") != null ? rs.getString("SEDIME") : "";
		rec += SEPARATORE;
		rec += rs.getObject("DESCRIZIONE") != null ? rs.getString("DESCRIZIONE") : "";
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		rec += "";
		
		return rec;
	}
	
	private String getRecordViarioUffTARSU(ResultSet rs) throws Exception {
		String rec = TIP_REC_VIARIO_UFF_TARSU;
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("COD_VIA") != null ? rs.getString("COD_VIA") : "";
		} else {
			rec += rs.getObject("COD_VIA") != null ? DF.format(((Number)rs.getObject("COD_VIA")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		rec += rs.getObject("DES_VIA") != null ? rs.getString("DES_VIA") : "";
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		rec += "";
		
		return rec;
	}
	
	private String getRecordRidTarTARSU(ResultSet rs) throws Exception {
		String rec = TIP_REC_RID_TAR_TARSU;
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("COD_OGG") != null ? rs.getString("COD_OGG") : "";
		} else {
			rec += rs.getObject("COD_OGG") != null ? DF.format(((Number)rs.getObject("COD_OGG")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("PRC_RID") != null ? rs.getString("PRC_RID") : "";
		} else {
			rec += rs.getObject("PRC_RID") != null ? DF.format(((Number)rs.getObject("PRC_RID")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		rec += "P";
		rec += SEPARATORE;
		rec += "";
		
		return rec;
	}
	
	private String getRecordAnaSogg(Connection myConnTables, Connection conn, ResultSet rs, String tipoControllo) throws Exception {		
		String codCnt = "";
		if (connTables == null) {
			codCnt = rs.getObject("COD_CNT") != null ? rs.getString("COD_CNT") : "";
		} else {
			codCnt = rs.getObject("COD_CNT") != null ? DF.format(((Number)rs.getObject("COD_CNT")).doubleValue()) : "";
		}		
		String rec = codCnt;
		rec += SEPARATORE;
		String provenienza = rs.getObject("DATI_ORI") != null ? rs.getString("DATI_ORI") : "";
		rec += getIdOrigSoggU(myConnTables, codCnt, provenienza, tipoControllo);
		rec += SEPARATORE;
		rec += rs.getObject("COD_FSC") != null ? rs.getString("COD_FSC") : "";
		rec += SEPARATORE;
		String parIva = null;
		if (connTables == null) {
			parIva = rs.getObject("PAR_IVA") != null ? rs.getString("PAR_IVA") : null;
		} else {
			parIva = rs.getObject("PAR_IVA") != null ? DF.format(((Number)rs.getObject("PAR_IVA")).doubleValue()) : null;
		}		
		if (parIva != null) {
			while (parIva.length() < 11) {
				parIva = "0" + parIva;
			}
		}
		rec += parIva != null ? parIva : "";
		rec += SEPARATORE;
		rec += rs.getObject("COGNOME") == null ? (rs.getObject("DENOM") != null ? rs.getString("DENOM") : "") : rs.getString("COGNOME");
		rec += SEPARATORE;
		rec += rs.getObject("NOME") != null ? rs.getString("NOME") : "";
		rec += SEPARATORE;
		rec += rs.getObject("SESSO") != null ? rs.getString("SESSO") : "";
		rec += SEPARATORE;
		rec += rs.getObject("TIP_CNT") != null ? rs.getString("TIP_CNT") : "";
		rec += SEPARATORE;
		rec += rs.getObject("DT_NSC") != null ? rs.getString("DT_NSC") : "";
		rec += SEPARATORE;
		String codIstCmnNsc = "";
		if (connTables == null) {
			codIstCmnNsc = rs.getObject("COD_IST_CMN_NSC") != null ? rs.getString("COD_IST_CMN_NSC") : "";
		} else {
			codIstCmnNsc = rs.getObject("COD_IST_CMN_NSC") != null ? DF.format(((Number)rs.getObject("COD_IST_CMN_NSC")).doubleValue()) : "";
		}
		rec += codIstCmnNsc;
		rec += SEPARATORE;
		String codBelfiore = getCodBelfiore(conn, codIstCmnNsc);
		rec += codBelfiore != null && codBelfiore.equalsIgnoreCase(codEnte) ? codBelfiore : "";
		rec += SEPARATORE;
		rec += codIstCmnNsc;
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("COM_NSC") != null ? rs.getString("COM_NSC") : "";
		} else {
			rec += rs.getObject("CMN_NSC") != null ? rs.getString("CMN_NSC") : "";
		}
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		String codIstCmnRes = "";
		if (connTables == null) {
			codIstCmnRes = rs.getObject("COD_IST_CMN_RES") != null ? rs.getString("COD_IST_CMN_RES") : "";
		} else {
			codIstCmnRes = rs.getObject("COD_IST_CMN_RES") != null ? DF.format(((Number)rs.getObject("COD_IST_CMN_RES")).doubleValue()) : "";
		}
		rec += codIstCmnRes;
		rec += SEPARATORE;
		codBelfiore = getCodBelfiore(conn, codIstCmnRes);
		boolean resInComune = codBelfiore != null && codBelfiore.equalsIgnoreCase(codEnte);
		rec += resInComune ? codBelfiore : "";
		rec += SEPARATORE;
		rec += codIstCmnRes;
		rec += SEPARATORE;
		rec += rs.getObject("DES_CMN_RES") != null ? rs.getString("DES_CMN_RES") : "";
		rec += SEPARATORE;
		String capRes = null;
		if (connTables == null) {
			capRes = rs.getObject("CAP_RES") != null ? rs.getString("CAP_RES") : null;
		} else {
			capRes = rs.getObject("CAP_RES") != null ? DF.format(((Number)rs.getObject("CAP_RES")).doubleValue()) : null;
		}		
		if (capRes != null) {
			while (capRes.length() < 5) {
				capRes = "0" + capRes;
			}
		}
		rec += capRes != null ? capRes : "";
		rec += SEPARATORE;		
		rec += "";
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		rec += rs.getObject("DES_VIA_RES") != null ? rs.getString("DES_VIA_RES") : "";
		rec += SEPARATORE;
		if (connTables == null) {
			rec += rs.getObject("COD_VIA_RES") != null ? rs.getString("COD_VIA_RES") : "";
		} else {
			rec += rs.getObject("COD_VIA_RES") != null ? DF.format(((Number)rs.getObject("COD_VIA_RES")).doubleValue()) : "";
		}
		rec += SEPARATORE;
		String numCivRes = "";
		if (resInComune) {
			if (connTables == null) {
				numCivRes = rs.getObject("NUM_CIV_RES") != null ? rs.getString("NUM_CIV_RES") : "";
			} else {
				numCivRes = rs.getObject("NUM_CIV_RES") != null ? DF.format(((Number)rs.getObject("NUM_CIV_RES")).doubleValue()) : "";
			}
		}
		rec += numCivRes;
		rec += SEPARATORE;
		rec += resInComune ? (rs.getObject("ESP_CIV_RES") != null ? rs.getString("ESP_CIV_RES") : "") : "";
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		rec += "";
		rec += SEPARATORE;
		String numIntRes = "";
		if (resInComune) {
			if (connTables == null) {
				numIntRes = rs.getObject("NUM_INT_RES") != null ? rs.getString("NUM_INT_RES") : "";
			} else {
				numIntRes = rs.getObject("NUM_INT_RES") != null ? DF.format(((Number)rs.getObject("NUM_INT_RES")).doubleValue()) : "";
			}
		}
		rec += numIntRes;
		rec += SEPARATORE;
		rec += !resInComune ? (rs.getObject("DES_VIA_RES") != null ? rs.getString("DES_VIA_RES") : "") : "";
		rec += SEPARATORE;
		rec += !resInComune ? numCivRes : "";
		rec += SEPARATORE;
		rec += rs.getObject("TMS_AGG") != null ? rs.getString("TMS_AGG") : "";
		rec += SEPARATORE;
		rec += rs.getObject("FLG_TRF") != null ? rs.getString("FLG_TRF") : "";
		rec += SEPARATORE;
		rec += rs.getObject("TMS_BON") != null ? rs.getString("TMS_BON") : "";
		
		return rec;
	}

}
