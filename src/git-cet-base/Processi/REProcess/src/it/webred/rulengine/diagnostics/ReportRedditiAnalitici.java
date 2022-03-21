package it.webred.rulengine.diagnostics;

import it.webred.ct.config.luoghi.LuoghiService;
import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.rulengine.Context;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.diagnostics.bean.DiagnosticConfigBean;

import it.webred.rulengine.type.def.DeclarativeType;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.dbutils.DbUtils;

public class ReportRedditiAnalitici extends ElaboraDiagnosticsNonStandard {
	Connection conn;

	private String enteID;
	private static final Map<String, String> modelloDescr = new HashMap<String, String>() {
		{
			put("U", "UNICO");
			put("3", "730");
			put("S", "770S");
		}
	};
	public static final List<String> listaFasce = Arrays.asList("15000",
			"28000", "55000", "75000");

	private int numeroFascie = 5;
	private String dich730Imponibile = "PL01400100001";
	private String dich730Imposta = "PL05100100001";
	private String con730Imponibile = "PL01400200001";
	private String con730Imposta = "PL05100200001";

	private WritableCellFormat timesBold;
	private WritableCellFormat times;
	private WritableCellFormat currency;
	private WritableCellFormat currencyBold; 
	private String inputFile;

	public ReportRedditiAnalitici() {
		super();

	}

	public ReportRedditiAnalitici(Connection connPar, Context ctxPar,
			List<RRuleParamIn> paramsPar) {
		super(connPar, ctxPar, paramsPar);

	}

	@Override
	protected void ElaborazioneNonStandard(DiagnosticConfigBean diaConfig,
			long idTestata) throws Exception {
		log.info("[ElaborazioneNonStandard] - Invoke class CtrlLocazioni ");
		enteID = this.getCodBelfioreEnte();
		conn = this.getConn();
		PreparedStatement pstmtBelfiore = null;
		PreparedStatement pstmtNoBelfiore = null;
		PreparedStatement pstmt730Dich = null;
		PreparedStatement pstmt730Con = null;
		PreparedStatement pstmt730DichBelfiore = null;
		PreparedStatement pstmt730ConBelfiore = null;
		String anno = (String) getParamValueByName("ANNO");
		String paramListaComuni = (String) getParamValueByName("Eventuali report comunali addizionali(espressi tramite Codice Belfiore e divisi da ',')"); 
		List<String> listaComuniUtente = new ArrayList<String>();
		if(paramListaComuni != null && !paramListaComuni.equals("")){
			String[] paramListaComuniArray = paramListaComuni.split(",");
			for(int i = 0; i < paramListaComuniArray.length; i++){
				listaComuniUtente.add(paramListaComuniArray[i].toUpperCase());
			}
		}
		
		ParameterService cdm = (ParameterService) ServiceLocator.getInstance()
				.getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
		ParameterSearchCriteria criteria = new ParameterSearchCriteria();
		criteria.setKey("dir.files.dati");
		AmKeyValueExt param = cdm.getAmKeyValueExt(criteria);
		// filtro i redditi per i soggetti della regione di appartenenza
		LuoghiService lService = (LuoghiService) ServiceLocator.getInstance()
				.getService("CT_Service", "CT_Config_Manager", "LuoghiServiceBean");
		List<String> listaComuniRegione = lService.getComuniRegione();
		String sqlListaComuni = "";
		for (String b : listaComuniRegione) {
			sqlListaComuni += ",'" + b + "'";
		}
		sqlListaComuni = sqlListaComuni.substring(1);

		String startTime = this.getProcessId().substring(
				this.getProcessId().length() - 13);
		String fileName = param.getValueConf() + "repRedd_" + startTime
				+ ".xls";
		try {
			
			// costruzione sql fasce
			String sqlFasce = "";
			sqlFasce += " WHEN (imponibile < " + listaFasce.get(0)
					+ " or imponibile is null) THEN 'da 0 a "
					+ listaFasce.get(0) + "'";
			for (int i = 0; i < listaFasce.size() - 1; i++) {
				String f1 = listaFasce.get(i);
				String f2 = listaFasce.get(i + 1);
				sqlFasce += " WHEN (imponibile >= " + f1
						+ " AND imponibile < " + f2 + ") THEN 'da " + f1
						+ " a " + f2 + "' ";
			}
			sqlFasce += " ELSE 'da "
					+ listaFasce.get(listaFasce.size() - 1) + "'";
			// costruzione union per riempire fasce vuote
			String sqlUnion = "";
			Iterator it = modelloDescr.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pairs = (Map.Entry) it.next();
				sqlUnion += " UNION SELECT '" + pairs.getKey()
						+ "', 'ZZZZZZZZZZZZZZZZ', 0, 0,'da 0 a "
						+ listaFasce.get(0) + "' from dual ";
				for (int i = 0; i < listaFasce.size() - 1; i++) {
					String f1 = listaFasce.get(i);
					String f2 = listaFasce.get(i + 1);
					sqlUnion += " UNION SELECT '" + pairs.getKey()
							+ "', 'ZZZZZZZZZZZZZZZZ', 0, 0,'da " + f1
							+ " a " + f2 + "' from dual ";
				}
				sqlUnion += " UNION SELECT '" + pairs.getKey()
						+ "', 'ZZZZZZZZZZZZZZZZ', 0, 0,'da "
						+ listaFasce.get(listaFasce.size() - 1)
						+ "' from dual ";
			}
			String sqlUnion730 = "";
			sqlUnion730 += " UNION SELECT '3', 'ZZZZZZZZZZZZZZZZ', 0, 0,'da 0 a "
					+ listaFasce.get(0) + "' from dual ";
			for (int i = 0; i < listaFasce.size() - 1; i++) {
				String f1 = listaFasce.get(i);
				String f2 = listaFasce.get(i + 1);
				sqlUnion730 += " UNION SELECT '3', 'ZZZZZZZZZZZZZZZZ', 0, 0,'da "
						+ f1 + " a " + f2 + "' from dual ";
			}
			sqlUnion730 += " UNION SELECT '3', 'ZZZZZZZZZZZZZZZZ', 0, 0,'da "
					+ listaFasce.get(listaFasce.size() - 1)
					+ "' from dual ";

			String sqlNoBelfiore = getProperty("sql.REPORT")
					.replace("@ANNO", anno).replace("@FASCE", sqlFasce)
					.replace("@UNION", sqlUnion)
					.replaceAll("@LISTABELFIORE", sqlListaComuni);
			String sql730Dich = getProperty("sql.REPORT_730DICH")
					.replace("@ANNO", anno).replace("@FASCE", sqlFasce)
					.replace("@UNION730", sqlUnion730)
					.replace("@730DICHIMPON", dich730Imponibile)
					.replace("@730DICHIMPOS", dich730Imposta)
					.replaceAll("@LISTABELFIORE", sqlListaComuni);
			String sql730Con = getProperty("sql.REPORT_730CON")
					.replace("@ANNO", anno).replace("@FASCE", sqlFasce)
					.replace("@UNION730", sqlUnion730)
					.replace("@730CONIMPON", con730Imponibile)
					.replace("@730CONIMPOS", con730Imposta)
					.replaceAll("@LISTABELFIORE", sqlListaComuni);
			
			try {

				pstmtNoBelfiore = conn.prepareStatement(sqlNoBelfiore);
				pstmt730Dich = conn.prepareStatement(sql730Dich);
				pstmt730Con = conn.prepareStatement(sql730Con);

			} catch (SQLException e) {
				log.error("ERRORE IN FASE DI CREAZIONE STATEMENT", e);
				throw e;
			}

			File xlsFile = new File(fileName);
			WorkbookSettings wbSettings = new WorkbookSettings();

			wbSettings.setLocale(new Locale("en", "EN"));

			WritableWorkbook workbook = Workbook.createWorkbook(xlsFile,
					wbSettings);

			workbook.createSheet("Report", 0);
			WritableSheet excelSheet = workbook.getSheet(0);
			createBaseStructure(excelSheet, anno);
			createContent(excelSheet, pstmtNoBelfiore.executeQuery(),
					pstmt730Dich.executeQuery(), pstmt730Con.executeQuery());

			//aggiungo un foglio per ogni comune specificato dall'utente
			for(int i = 1; i <= listaComuniUtente.size(); i++){
				String belfioreUtente = listaComuniUtente.get(i - 1);
				String sqlBelfioreUtente = "'"+belfioreUtente+"'";
				
				String sqlBelfiore = getProperty("sql.REPORT_BELFIORE")
						.replace("@ANNO", anno).replace("@FASCE", sqlFasce)
						.replace("@UNION", sqlUnion)
						.replaceAll("@BELFIORE", sqlBelfioreUtente);
				String sql730DichBelfiore = getProperty("sql.REPORT_730DICH_BELFIORE")
						.replace("@ANNO", anno).replace("@FASCE", sqlFasce)
						.replace("@UNION730", sqlUnion730)
						.replace("@730DICHIMPON", dich730Imponibile)
						.replace("@730DICHIMPOS", dich730Imposta)
						.replaceAll("@BELFIORE", sqlBelfioreUtente);
				String sql730ConBelfiore = getProperty("sql.REPORT_730CON_BELFIORE")
						.replace("@ANNO", anno).replace("@FASCE", sqlFasce)
						.replace("@UNION730", sqlUnion730)
						.replace("@730CONIMPON", con730Imponibile)
						.replace("@730CONIMPOS", con730Imposta)
						.replaceAll("@BELFIORE", sqlBelfioreUtente);
				
				try {

					pstmtBelfiore = conn.prepareStatement(sqlBelfiore);
					pstmt730DichBelfiore = conn.prepareStatement(sql730DichBelfiore);
					pstmt730ConBelfiore = conn.prepareStatement(sql730ConBelfiore);

				} catch (SQLException e) {
					log.error("ERRORE IN FASE DI CREAZIONE STATEMENT CON BELFIORE " + belfioreUtente, e);
					throw e;
				}
				
				workbook.createSheet(belfioreUtente, i);
				excelSheet = workbook.getSheet(i);
				
				createBaseStructure(excelSheet, anno);
				createContent(excelSheet, pstmtBelfiore.executeQuery(),
						pstmt730DichBelfiore.executeQuery(), pstmt730ConBelfiore.executeQuery());
			}
			
			workbook.write();
			workbook.close();

		} catch (Exception e) {
			// ritorno un file di errore
			log.error("ERRORE IN FASE DI CREAZIONE REPORT", e);
			File xlsFile = new File(fileName);
			WorkbookSettings wbSettings = new WorkbookSettings();
			wbSettings.setLocale(new Locale("en", "EN"));
			WritableWorkbook workbook = Workbook.createWorkbook(xlsFile,
					wbSettings);
			workbook.createSheet("Report", 0);
			addCaption(workbook.getSheet(0), 0, 0,
					"ERRORE IN FASE DI CREAZIONE REPORT");
			workbook.write();
			workbook.close();
		} finally {
			try {
				if (pstmtBelfiore != null)
					pstmtBelfiore.close();
				if (pstmtNoBelfiore != null)
					pstmtNoBelfiore.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		super.ElaborazioneNonStandard(diaConfig, idTestata);
	}

	private Object getParamValueByName(String namePar) {
		DeclarativeType param = this.getCtx().getDeclarativeType(namePar);
		if (param != null && param.getValue() != null) {
			log.debug("Parametro recuperato. type [" + param.getType()
					+ "]; value [" + param.getValue().toString() + "]");
			return param.getValue();
		} else
			return null;
	}

	private void createBaseStructure(WritableSheet sheet, String anno)
			throws WriteException {
		// Lets create a times font
		WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
		// Define the cell format
		times = new WritableCellFormat(times10pt);
		// Lets automatically wrap the cells
		times.setWrap(true);

		// Create a bold font with unterlines
		WritableFont times10ptBold = new WritableFont(WritableFont.TIMES, 10,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);
		timesBold = new WritableCellFormat(times10ptBold);
		// Lets automatically wrap the cells
		timesBold.setWrap(true);
		//currency format
		NumberFormat currencyFormat = new NumberFormat("###,###", NumberFormat.COMPLEX_FORMAT);
		currency = new WritableCellFormat(currencyFormat);
		currencyFormat = new NumberFormat("###,###", NumberFormat.COMPLEX_FORMAT);
		currencyBold = new WritableCellFormat(currencyFormat);
		currencyBold.setFont(times10ptBold);

		CellView cv = new CellView();
		cv.setFormat(times);
		cv.setSize(20);

		// Adjust column width
		sheet.setColumnView(0, 21);
		sheet.setColumnView(1, 21);
		sheet.setColumnView(2, 12);
		sheet.setColumnView(3, 18);
		sheet.setColumnView(4, 18);
		sheet.setColumnView(5, 12);
		sheet.setColumnView(6, 18);
		sheet.setColumnView(7, 18);
		sheet.setColumnView(8, 12);
		sheet.setColumnView(9, 18);
		sheet.setColumnView(10, 18);
		sheet.setRowView(2, 500);

		// Merging cells
		sheet.mergeCells(0, 0, 4, 0);
		addCaption(sheet, 0, 0,
				"ADDIZIONALE REGIONALE ALL'IRPEF - ANNO IMPOSTA " + anno);
		sheet.mergeCells(0, 1, 4, 1);
		addCaption(sheet, 0, 1,
				"IMPONIBILE E IMPOSTA PER MODELLO DI DICHIARAZIONE E CLASSE DI REDDITO");
		// Write a few headers
		addCaption(sheet, 0, 2, "Modello");
		addCaption(sheet, 1, 2, "Classe di reddito");
		addCaption(sheet, 2, 2, "Numero contribuenti");
		addCaption(sheet, 3, 2, "Reddito imponibile");
		addCaption(sheet, 4, 2, "Imposta");
		addCaption(sheet, 5, 2, "Dichiaranti");
		addCaption(sheet, 6, 2, "Dich. Reddito imponibile");
		addCaption(sheet, 7, 2, "Dich. Imposta");
		addCaption(sheet, 8, 2, "Coniugi");
		addCaption(sheet, 9, 2, "Con. Reddito imponibile");
		addCaption(sheet, 10, 2, "Con. Imposta");

	}

	private void createContent(WritableSheet sheet, ResultSet rs,
			ResultSet rs730Dich, ResultSet rs730Con) throws WriteException,
			RowsExceededException, SQLException {

		String modello = "";
		int i = 3;
		boolean dati730 = false;
		while (rs.next()) {
			String tipoModello = rs.getString("TIPO_MODELLO");
			String fascia = rs.getString("FASCIA");
			BigDecimal contribuenti = rs.getBigDecimal("NUM_CONTRIBUENTI");
			BigDecimal imponibile = rs.getBigDecimal("IMPONIBILE");
			BigDecimal imposta = rs.getBigDecimal("IMPOSTA");

			if (!modello.equals(tipoModello)) {
				if (!modello.equals("")) {
					// calcolo totale modello
					StringBuffer buf = new StringBuffer();
					buf.append("SUM(C" + (i - (numeroFascie - 1)) + ":C" + (i)
							+ ")");
					Formula f = new Formula(2, i, buf.toString(), currencyBold);
					sheet.addCell(f);
					buf = new StringBuffer();
					buf.append("SUM(D" + (i - (numeroFascie - 1)) + ":D" + (i)
							+ ")");
					f = new Formula(3, i, buf.toString(), currencyBold);
					sheet.addCell(f);
					buf = new StringBuffer();
					buf.append("SUM(E" + (i - (numeroFascie - 1)) + ":E" + (i)
							+ ")");
					f = new Formula(4, i, buf.toString(), currencyBold);
					sheet.addCell(f);
					addCaption(sheet, 0, i++,
							"Totale " + modelloDescr.get(modello));
				}
				// nuova sottosezione modello
				sheet.mergeCells(0, i, 0, i + (numeroFascie - 1));
				addLabel(sheet, 0, i, modelloDescr.get(tipoModello));
				modello = tipoModello;
			}

			addLabel(sheet, 1, i, fascia);
			addNumber(sheet, 2, i, contribuenti);
			addNumber(sheet, 3, i, imponibile);
			addNumber(sheet, 4, i, imposta);

			// aggiungo dichiarante e coniuge per 730
			if (!dati730 && tipoModello.equals("3")) {
				int j = 3;
				while (rs730Dich.next()) {
					contribuenti = rs730Dich.getBigDecimal("NUM_CONTRIBUENTI");
					imponibile = rs730Dich.getBigDecimal("IMPONIBILE");
					imposta = rs730Dich.getBigDecimal("IMPOSTA");
					addNumber(sheet, 5, j, contribuenti);
					addNumber(sheet, 6, j, imponibile);
					addNumber(sheet, 7, j, imposta);
					j++;
				}
				int k = 3;
				while (rs730Con.next()) {
					contribuenti = rs730Con.getBigDecimal("NUM_CONTRIBUENTI");
					imponibile = rs730Con.getBigDecimal("IMPONIBILE");
					imposta = rs730Con.getBigDecimal("IMPOSTA");
					addNumber(sheet, 8, k, contribuenti);
					addNumber(sheet, 9, k, imponibile);
					addNumber(sheet, 10, k, imposta);
					k++;
				}
				StringBuffer buf = new StringBuffer();
				buf.append("SUM(F" + (j - (numeroFascie - 1)) + ":F" + (j)
						+ ")");
				Formula f = new Formula(5, j, buf.toString(), currencyBold);
				sheet.addCell(f);
				buf = new StringBuffer();
				buf.append("SUM(G" + (j - (numeroFascie - 1)) + ":G" + (j)
						+ ")");
				f = new Formula(6, j, buf.toString(), currencyBold);
				sheet.addCell(f);
				buf = new StringBuffer();
				buf.append("SUM(H" + (j - (numeroFascie - 1)) + ":H" + (j)
						+ ")");
				f = new Formula(7, j, buf.toString(), currencyBold);
				sheet.addCell(f);
				buf = new StringBuffer();
				buf.append("SUM(I" + (j - (numeroFascie - 1)) + ":I" + (j)
						+ ")");
				f = new Formula(8, j, buf.toString(), currencyBold);
				sheet.addCell(f);
				buf = new StringBuffer();
				buf.append("SUM(J" + (j - (numeroFascie - 1)) + ":J" + (j)
						+ ")");
				f = new Formula(9, j, buf.toString(), currencyBold);
				sheet.addCell(f);
				buf = new StringBuffer();
				buf.append("SUM(K" + (j - (numeroFascie - 1)) + ":K" + (j)
						+ ")");
				f = new Formula(10, j, buf.toString(), currencyBold);
				sheet.addCell(f);

				dati730 = true;
			}

			i++;
		}

		// calcolo totale ultimo modello
		StringBuffer buf = new StringBuffer();
		buf.append("SUM(C" + (i - (numeroFascie - 1)) + ":C" + (i) + ")");
		Formula f = new Formula(2, i, buf.toString(), currencyBold);
		sheet.addCell(f);
		buf = new StringBuffer();
		buf.append("SUM(D" + (i - (numeroFascie - 1)) + ":D" + (i) + ")");
		f = new Formula(3, i, buf.toString(), currencyBold);
		sheet.addCell(f);
		buf = new StringBuffer();
		buf.append("SUM(E" + (i - (numeroFascie - 1)) + ":E" + (i) + ")");
		f = new Formula(4, i, buf.toString(), currencyBold);
		sheet.addCell(f);
		addCaption(sheet, 0, i++, "Totale " + modelloDescr.get(modello));
		//lascio 1 riga vuota
		i++;
		// calcolo totale complessivo
		buf = new StringBuffer();
		buf.append("SUM(C" + (3 + numeroFascie + 1) + ",C"
				+ (3 + (numeroFascie + 1) * 2) + ",C"
				+ (3 + (numeroFascie + 1) * 3) + ")");
		f = new Formula(2, i, buf.toString(), currencyBold);
		sheet.addCell(f);
		buf = new StringBuffer();
		buf.append("SUM(D" + (3 + numeroFascie + 1) + ",D"
				+ (3 + (numeroFascie + 1) * 2) + ",D"
				+ (3 + (numeroFascie + 1) * 3) + ")");
		f = new Formula(3, i, buf.toString(), currencyBold);
		sheet.addCell(f);
		buf = new StringBuffer();
		buf.append("SUM(E" + (3 + numeroFascie + 1) + ",E"
				+ (3 + (numeroFascie + 1) * 2) + ",E"
				+ (3 + (numeroFascie + 1) * 3) + ")");
		f = new Formula(4, i, buf.toString(), currencyBold);
		sheet.addCell(f);
		addCaption(sheet, 0, i++, "Totale complessivo");

	}

	private void addCaption(WritableSheet sheet, int column, int row, String s)
			throws RowsExceededException, WriteException {
		Label label;
		label = new Label(column, row, s, timesBold);
		sheet.addCell(label);
	}

	private void addNumber(WritableSheet sheet, int column, int row,
			BigDecimal decimal) throws WriteException, RowsExceededException {
		jxl.write.Number number;
		number = new jxl.write.Number(column, row,
				decimal != null ? decimal.doubleValue() : 0, currency);
		sheet.addCell(number);
	}

	private void addLabel(WritableSheet sheet, int column, int row, String s)
			throws WriteException, RowsExceededException {
		Label label;
		label = new Label(column, row, s, times);
		sheet.addCell(label);
	}

	private String getProperty(String propName) {
		String fileName = "it.webred.rulengine.diagnostics.ReportRedditiAnalitici.properties";
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
}
