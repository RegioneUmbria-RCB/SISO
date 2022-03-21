package it.webred.rulengine.brick.elab.anaSan;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

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
import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

public class ControlloViarioAnagrafeSanitaria extends Command implements Rule {
	
	private static final Logger log = Logger.getLogger(ControlloViarioAnagrafeSanitaria.class.getName());
	
	private static final String DEF_DIR_EXP_CTRL_VIARIO = "expCtrlViarioAnaSan";
	
	private static final String ID_FONTE = "1";

	public ControlloViarioAnagrafeSanitaria(BeanCommand bc){
		super(bc);
	}
	
	public ControlloViarioAnagrafeSanitaria(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}
	
	@Override
	public CommandAck run(Context ctx) throws CommandException {
		Connection conn = null;
		
		try {
			ParameterService ps = (ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");			
			AmKeyValueExt param = ps.getAmKeyValueExtByKeyFonteComune("dir.ctrl.viario.anasan", ctx.getBelfiore(), ID_FONTE);
			String expPathCtrlViario = param == null ? null : param.getValueConf();
			if (expPathCtrlViario == null || expPathCtrlViario.trim().equals("")) {
				expPathCtrlViario = Utils.getConfigProperty("dir.files.datiDiogene") + File.separator + ctx.getBelfiore() + File.separator + DEF_DIR_EXP_CTRL_VIARIO;
			}
			
			conn = ctx.getConnection("DWH_" + ctx.getBelfiore());
			conn.setAutoCommit(false);
			
			String nomeFile = ctx.getBelfiore() + "_" + new SimpleDateFormat("yyyyMMdd").format(new Date());
			boolean logCtrlViario = ctrlViario(ctx, conn, expPathCtrlViario, nomeFile);
			if (logCtrlViario) {
				//TODO INVIO E-MAIL CON XLS ALLEGATO
			}
			
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
				DbUtils.close(conn);
			} catch (SQLException e) {
				log.error(e);
			}
		}
		
		return (new ApplicationAck("CONTROLLO DEL VIARIO ESEGUITO CON SUCCESSO"));
	}
	
	protected boolean ctrlViario(Context ctx, Connection conn, String percorsoFile, String nomeFile) throws Exception {
		Statement stmt = null;
		ResultSet rs = null;		
        try {
            File f = new File(percorsoFile);
            if (!f.exists()) {
            	f.mkdir();
            }
            
	        String nomeFileLog = nomeFile + "_ctrl_viario" + ".xls";
	        HSSFWorkbook wb = new HSSFWorkbook();        
	        boolean trovato = false;
	        
	        for (int i = 1; i <= 4; i++) {
	        	if (i != 1 && i != 3) {
	        		//al momento sono eseguite solo le query 1 e 3
	        		continue;
	        	}
	        	String nomeFoglio = getProperty("ctrlViario.nome." + i);
	        	String intestazione = getProperty("ctrlViario.intestazione." + i);
	        	String sql = getProperty("ctrlViario.sql." + i);
	        	HSSFSheet sheet = wb.createSheet(nomeFoglio);
	        	stmt = conn.createStatement();
	        	rs = stmt.executeQuery(sql);
	        	int riga = 0;	        	
	        	int totNumCol = rs.getMetaData().getColumnCount();
	        	
	        	//INTESTAZIONE FOGLIO	        			
    			HSSFRow row = sheet.getRow(riga);
    			if (row == null) {
    				row = sheet.createRow(riga);
    			}
    			CellRangeAddress region = new CellRangeAddress(riga, riga, 0, totNumCol - 1);
    			sheet.addMergedRegion(region);
    			HSSFCell cell = row.getCell(0);
    			if (cell == null) {
    				cell = row.createCell(0);
    			}
    			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
    			HSSFCellStyle styleIntest = wb.createCellStyle();
    			HSSFFont fontIntest = wb.createFont();
    			fontIntest.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    			fontIntest.setFontName(HSSFFont.FONT_ARIAL);
    			fontIntest.setFontHeightInPoints((short)11);
    			styleIntest.setFont(fontIntest);
    			cell.setCellStyle(styleIntest);
    			cell.setCellValue(new HSSFRichTextString(intestazione));
    			row.setHeightInPoints((short)15);
    			riga++;
    			
    			//INTESTAZIONI CAMPI
    			row = sheet.getRow(riga);
    			if (row == null) {
    				row = sheet.createRow(riga);
    			}
    			for (int j = 0; j < totNumCol; j++) {
    				String intestCampo = rs.getMetaData().getColumnName(j + 1).toUpperCase(); //base 1
    				cell = row.getCell(j);
        			if (cell == null) {
        				cell = row.createCell(j);
        			}
        			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        			HSSFCellStyle style = wb.createCellStyle();
        			HSSFFont font = wb.createFont();
        			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        			font.setFontName(HSSFFont.FONT_ARIAL);
        			font.setFontHeightInPoints((short)10);
        			style.setFont(font);
        			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        			style.setFillBackgroundColor(HSSFColor.GREY_40_PERCENT.index);
        			cell.setCellStyle(style);
        			cell.setCellValue(new HSSFRichTextString(intestCampo));
    			}
    			
    			riga++;
	        	
	        	while (rs.next()) {
	        		trovato = true;
	        		//RECORD
	        		row = sheet.getRow(riga);
        			if (row == null) {
        				row = sheet.createRow(riga);
        			}
	        		for (int j = 0; j < totNumCol; j++) {
        				Object valore = rs.getObject(j + 1); //base 1
        				cell = row.getCell(j);
	        			if (cell == null) {
	        				cell = row.createCell(j);
	        			}
	        			HSSFCellStyle style = wb.createCellStyle();
	        			HSSFFont font = wb.createFont();
	        			font.setFontName(HSSFFont.FONT_ARIAL);
	        			font.setFontHeightInPoints((short)10);
	        			style.setFont(font);		
	        			if (valore instanceof Integer) {
	        				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	        				cell.setCellValue(new Double(((Integer)valore).intValue()));
	        			} else if (valore instanceof Double) {
	        				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	        				cell.setCellValue((Double)valore);
	        			} else if (valore instanceof BigDecimal) {
	        				cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	        				cell.setCellValue(new Double(((BigDecimal)valore).doubleValue()));
	        			} else if (valore instanceof Date) {
	        				short df = wb.createDataFormat().getFormat("dd/MM/yyyy");
	        				style.setDataFormat(df);
	        				cell.setCellValue((Date)valore);
	        			} else {
	        				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		        			cell.setCellValue(new HSSFRichTextString(valore == null ? "" : valore.toString()));
	        			}
	        			cell.setCellStyle(style);
        			}
	        		riga++;	        		
	        	}
	        	
	        	row = sheet.getRow(1); //riga con intestazioni campi
	        	if (row != null) {
		        	for (int colNum = 0; colNum < row.getLastCellNum(); colNum++) {
		        		sheet.autoSizeColumn(colNum);
		        	}
	        	}
	        	
	        	DbUtils.close(rs);
	        	DbUtils.close(stmt);
	        }
	        
	        FileOutputStream fileOut = new FileOutputStream(percorsoFile + "\\" + nomeFileLog);
			wb.write(fileOut);
			fileOut.close();
			
			return trovato;
			
        } catch (Exception e1) {
            throw e1;
        } finally {
	        try {
	        	DbUtils.close(rs);
	        	DbUtils.close(stmt);
	        } catch (SQLException e) {
	        	log.error(e);
	        }
        }
	}
	
	protected String getProperty(String propName) {		
		String p =  DwhUtils.getProperty(this.getClass(), propName);
		return p;
	}
	
}
