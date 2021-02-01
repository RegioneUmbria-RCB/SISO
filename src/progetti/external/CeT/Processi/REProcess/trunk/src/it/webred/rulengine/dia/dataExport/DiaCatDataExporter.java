package it.webred.rulengine.dia.dataExport;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.webred.dataexporter.export.PstmtDataExporter;
import it.webred.rulengine.Utils;

public class DiaCatDataExporter extends PstmtDataExporter {

	protected int idCatalogoDia;
	protected Date dataRif;
	protected HashMap<String, Object> datiDiaLogExec;
	protected String elencoValori[];
	
	public static final String DATA_ESEC = "DATA_ESEC";
	public static final String DATA_RIF = "DATA_RIF";
	public static final String NUM_REC = "NUM_REC";
	public static final String PATH_FILE = "PATH_FILE";
	
	public DiaCatDataExporter() {
		super();
		setRowsMaxNumXLS(Integer.parseInt(Utils.getProperty("ROWS_MAX_NUM_XLS.valore")));
		setColsMaxNumXLS(Integer.parseInt(Utils.getProperty("COLS_MAX_NUM_XLS.valore")));
		setAutoZipFileSize(Long.parseLong(Utils.getProperty("AUTO_ZIP_FILE_SIZE.valore")));
		setSheetMaxNumXLS(Integer.parseInt(Utils.getProperty("SHEET_MAX_NUM_XLS.valore")));
	}
	
	public DiaCatDataExporter(int idCatalogoDia, Date dataRif, boolean setFile, boolean setZIPFile) {
		super();
		java.util.Date dtNow = new Date();
		setRowsMaxNumXLS(Integer.parseInt(Utils.getProperty("ROWS_MAX_NUM_XLS.valore")));
		setColsMaxNumXLS(Integer.parseInt(Utils.getProperty("COLS_MAX_NUM_XLS.valore")));
		setAutoZipFileSize(Long.parseLong(Utils.getProperty("AUTO_ZIP_FILE_SIZE.valore")));
		setSheetMaxNumXLS(Integer.parseInt(Utils.getProperty("SHEET_MAX_NUM_XLS.valore")));
		String xlsTemplatePath = getDiaProperty("dia." + idCatalogoDia + ".template");
		if (xlsTemplatePath == null) {
			xlsTemplatePath = getDiaProperty("dia.template");
		}
		if (xlsTemplatePath != null) {
			setXlsTemplatePath(xlsTemplatePath);
		}
		datiDiaLogExec = new HashMap<String, Object>();
		this.idCatalogoDia = idCatalogoDia;
		if (dataRif != null) {
			this.dataRif = dataRif;
		} else {
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(dtNow);
			gc = new GregorianCalendar(gc.get(GregorianCalendar.YEAR), gc.get(GregorianCalendar.MONTH), gc.get(GregorianCalendar.DAY_OF_MONTH));
			this.dataRif = gc.getTime();
		}
		datiDiaLogExec.put(DATA_RIF, this.dataRif);
		if (!setFile && setZIPFile) {
			setFile = true;
		}
		String path = null;
		String nomeFile = null;
		if (setFile) {
			path = Utils.getProperty("DIA.PATH_FILE.valore");
			if (!new File(path).exists()) {
				new File(path).mkdir();
			}
			path += "/";
			
			datiDiaLogExec.put(DATA_ESEC, dtNow);
			nomeFile = idCatalogoDia + "_" + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(dtNow);
			fileType = Utils.getProperty("DIA.TIPO_FILE.valore");
			filePath = path + nomeFile + fileType;
			datiDiaLogExec.put(PATH_FILE, filePath);
			writeHeaders = fileType.equalsIgnoreCase(XLS);
		}
		if (setZIPFile) {
			zipFilePath = path + nomeFile + ".zip";			
		}
	}
	
	protected HashMap<String, Object> getResultSet(Connection conn) throws Exception {
		HashMap<String, Object> retVal = new HashMap<String, Object>();
		PreparedStatement pstmt = conn.prepareStatement("select c.sqlcommandprop from dia_catalogo c where c.idcatalogodia = ?");
		pstmt.setInt(1, idCatalogoDia);
		ResultSet rs_qry = pstmt.executeQuery();
		if (!rs_qry.next())
			throw new Exception("Errore nell'esportazione della diagnostica con id: " + idCatalogoDia);
		String sqlCommandProp = rs_qry.getString("sqlcommandprop").trim();
		rs_qry.close();
		if (sqlCommandProp != null && sqlCommandProp.trim().startsWith("PROPERTY@")) {
			String codEnte = "";
			String nomeProp = "" + idCatalogoDia;
			if (sqlCommandProp.trim().length() > "PROPERTY@".length()) {
				nomeProp = sqlCommandProp.trim().substring(sqlCommandProp.trim().indexOf("PROPERTY@") + "PROPERTY@".length());
			}
			ResultSet rsEnte = conn.prepareStatement("select codent from sit_ente").executeQuery();
			while (rsEnte.next()) {
				codEnte = rsEnte.getString("codent");
			}
			selectSql = getDiaProperty("dia." + nomeProp + "." + codEnte);
			if (selectSql == null) {
				selectSql = getDiaProperty("dia." + nomeProp);
			}
		} else {
			selectSql = sqlCommandProp;
		}
		if (selectSql == null || selectSql.trim().equals("")) {
			throw new Exception("Impossibile esportare la diagnostica con id: " + idCatalogoDia + "; query di esportazione non definita");
		} else {
			selectSql = selectSql.trim();
		}
		// rimuovo eventuali commenti sql
		int fromindex = 0;
		while (selectSql.indexOf("/*",fromindex) > -1) {
			if(selectSql.indexOf("/*",fromindex) != selectSql.indexOf("/*+",fromindex)) {
				selectSql = selectSql.substring(0, selectSql.indexOf("/*",fromindex)) + selectSql.substring(selectSql.indexOf("*/",fromindex) + 2);
			    fromindex = selectSql.indexOf("*/",fromindex) +2;
			} else {
				fromindex = selectSql.indexOf("*/", selectSql.indexOf("/*+",fromindex)+3)+2;
			}
		}
		selectSql += "\n ";
		while (selectSql.indexOf("--") > -1) {
			selectSql = selectSql.substring(0, selectSql.indexOf("--")) + selectSql.substring(selectSql.indexOf("\n", selectSql.indexOf("--")));
		}
		pstmt.cancel();
		pstmt = conn.prepareStatement("select * from dia_catalogo_params where idCatalogoDia = ? ORDER BY IDPARAMS");
		pstmt.setInt(1, idCatalogoDia);
		ResultSet rs_para = pstmt.executeQuery();
		pstmt.cancel();
		// scorporo la group by 
		String group = "";
		if (selectSql.toLowerCase().lastIndexOf("group by") > -1) {
			group = selectSql.substring(selectSql.toLowerCase().lastIndexOf("group by"));
			selectSql = selectSql.substring(0, selectSql.toLowerCase().lastIndexOf("group by"));
		}
		// scorporo eventuale order
		String order = "";
		if (selectSql.toLowerCase().lastIndexOf("order") > -1) {
			order = selectSql.substring(selectSql.toLowerCase().lastIndexOf("order"));
			selectSql = selectSql.substring(0, selectSql.toLowerCase().lastIndexOf("order"));
		}		
		if (selectSql.toLowerCase().lastIndexOf("where") > -1 && elencoValori != null && elencoValori.length > 0)
			selectSql = selectSql.substring(0, selectSql.toLowerCase().lastIndexOf("where") + 5) + " 1=1 and " + selectSql.substring(selectSql.toLowerCase().lastIndexOf("where") + 5);

		Pattern p = Pattern.compile("\\s(and|or).*?(\\sand|\\sor|$)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

		int finposition = 0;
		if (elencoValori != null) {
			for (int i = 0; i < elencoValori.length; i++) {
				Matcher m = p.matcher(selectSql);
				m.find(finposition);
				boolean trovato = true;
				while(m.group(0).indexOf("?") <0) {
					trovato =true;
					if (m.find(m.start(2)-1)) continue; else trovato=false; break;
				}
				if (elencoValori[i].equals("")) {
					selectSql = selectSql.substring(0, m.start(1)) + " /* " + selectSql.substring(m.start(1), m.start(2)) + " */ " + selectSql.substring(m.start(2));
				}
				if (trovato)
				finposition = m.start(2)-1;
			}
		}
		selectSql += " " + order;
		selectSql += " " + group;
		
		//esecuzione della query
		pstmt.cancel();
		pstmt = conn.prepareStatement("select * from dia_catalogo_params where idCatalogoDia = ? ORDER BY IDPARAMS");
		pstmt.setInt(1, idCatalogoDia);
		rs_para = pstmt.executeQuery();
		pstmt.cancel();
		pstmt = conn.prepareStatement(selectSql);
		int k = 0;
		if (elencoValori != null) {
			for (int i = 0; i < elencoValori.length; i++) {
				rs_para.next();
				if (elencoValori[i] != null && !elencoValori[i].equals("")) {
					String val = elencoValori[i];
					callPreparedStatementSetMethod(pstmt, rs_para.getString("tipo"), ++k, val, rs_para.getInt("uselike") < 1 ? false : true);
				}
			}
		}

		//prima esecuzione per conteggio
		ResultSet rsConta = pstmt.executeQuery();
		int numRec = 0;
		while (rsConta.next()) {
			numRec++;
		}
		datiDiaLogExec.put(NUM_REC, new Integer(numRec));		
		
		//seconda esecuzione per lettura dati
		ResultSet rsDati = pstmt.executeQuery();
		tableName = rsDati.getMetaData().getTableName(1);
		if (tableName == null || tableName.trim().equals("")) {
			int idx = selectSql.toUpperCase().lastIndexOf("WHERE");
			String parte = "";
			if (idx > -1) {
				parte = selectSql.substring(0, idx).trim();
			} else {
				parte = selectSql;
			}
			idx = parte.toUpperCase().lastIndexOf("FROM");
			if (idx > -1) {
				parte = parte.substring(idx + "FROM".length()).trim();
				idx = parte.indexOf(" ");
				if (idx > -1) {
					tableName = parte.substring(0, idx);
				} else {
					tableName = parte;
				}
			}
		}
		
		if (tableName == null || tableName.trim().equals("")) {
			tableName = "TABELLA 1";
		}
		
		tableName = tableName.toUpperCase();
		
		retVal.put(STMT, pstmt);
		retVal.put(RS, rsDati);
		retVal.put(REC_COUNT, new Integer(numRec));
		return retVal;
	}
	
	private void callPreparedStatementSetMethod(PreparedStatement st, String tipo, int column, String value, boolean uselike)
	throws IllegalArgumentException, SQLException {	
		if (tipo.equalsIgnoreCase("stringa")) {
			if (value == null || value.equalsIgnoreCase("null2")) {
				st.setNull(column, Types.CHAR);
			} else {
				if (uselike)
					st.setString(column, "%" + value + "%");
				else
					st.setString(column, value);
			}
		} else if (tipo.equalsIgnoreCase("intero")) {
			try {
				if (value == null || value.trim().equalsIgnoreCase("null2")) {
					st.setNull(column, Types.NUMERIC);
				} else {
					st.setInt(column, Integer.parseInt(value.trim()));
				}
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException(value + " non è un intero valido.");
			}		
		} else if (tipo.equalsIgnoreCase("decimale")) {
			try {
				if (value == null || value.trim().equalsIgnoreCase("null2")) {
					st.setNull(column, Types.NUMERIC);
				} else {
					st.setBigDecimal(column, new BigDecimal(value.trim()));
				}
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException(value + " non è un decimale valido.");
			}
		} else if (tipo.equals("data")) {
			try {
				if (value == null || value.trim().equalsIgnoreCase("null2")) {
					st.setNull(column, Types.DATE);
				} else {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					st.setDate(column, new java.sql.Date(sdf.parse(value.trim()).getTime()));
				}
			} catch (Exception nfe) {
				throw new IllegalArgumentException(value + " non è una data valida.");
			}
		} else
			throw new IllegalArgumentException("Tipo " + tipo + " sconosciuto.");	
	}
	
	private String getDiaProperty(String propName) {
        String fileName =  "dia.properties";
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream is = cl.getResourceAsStream(fileName);
        Properties props = new Properties();        
        try {
        	props.load(is);
        } catch (Exception e) {
			return null;
		}        
        String p = props.getProperty(propName);
        return p;
	}

	public int getIdCatalogoDia() {
		return idCatalogoDia;
	}

	public void setIdCatalogoDia(int idCatalogoDia) {
		this.idCatalogoDia = idCatalogoDia;
	}

	public Date getDataRif() {
		return dataRif;
	}

	public void setDataRif(Date dataRif) {
		this.dataRif = dataRif;
	}

	public HashMap<String, Object> getDatiDiaLogExec() {
		return datiDiaLogExec;
	}

	public String[] getElencoValori() {
		return elencoValori;
	}

	public void setElencoValori(String[] elencoValori) {
		this.elencoValori = elencoValori;
	}
	
}
