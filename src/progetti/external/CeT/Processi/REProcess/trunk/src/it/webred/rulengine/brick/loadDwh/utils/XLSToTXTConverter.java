package it.webred.rulengine.brick.loadDwh.utils;

import it.webred.rulengine.brick.loadDwh.utils.bean.TxtFileField;
import it.webred.utils.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

public abstract class XLSToTXTConverter extends XLSToCSVConverter {
	
	protected ArrayList<TxtFileField> tracciato = new ArrayList<TxtFileField>();
	protected boolean scriviIntest = false;
	
	@Override
	public void save(String filePathFrom, String filePathTo, String belfiore) throws Exception {
		if (tracciato != null && tracciato.size() > 0) {
			filePathTo = getSpecFilePathTo(filePathFrom, filePathTo, belfiore);
		}
		super.save(filePathFrom, filePathTo, belfiore);
	}
	
	@Override
	protected int saveRows(String filePathFrom, String filePathTo, int start, int end, int fields)  throws Exception {
		
		int retVal = fields;
		
		//file TXT		
		File f = new File(filePathTo);
		OutputStream os = (OutputStream)new FileOutputStream(f, start > 0);
		String encoding = "Cp1252";
	    OutputStreamWriter osw = new OutputStreamWriter(os, encoding);
	    BufferedWriter bw = new BufferedWriter(osw);
	
	    //documento Excel
	    WorkbookSettings ws = new WorkbookSettings();
	    //ws.setLocale(new Locale("it", "IT"));
	    ws.setEncoding(encoding);
	    ws.setLocale(Locale.getDefault());
	    Workbook w = Workbook.getWorkbook(new File(filePathFrom),ws);
	
	    //fogli	      
	    for (int sheet = 0; sheet < w.getNumberOfSheets(); sheet++) {	    	
	    	Sheet s = w.getSheet(sheet);	
	    	//il nome del foglio non deve essere inserito
	    	/*bw.write(s.getName());
	        bw.newLine();*/
	
	        Cell[] row = null;	        
	        //celle
	        int myEnd = end + 1;
	        if (s.getRows() <= myEnd) {
	        	myEnd = s.getRows();
	        	retVal = -1;
	        }
	        
	        boolean doNewLine = false;
	        for (int i = start; i < myEnd; i++) {
	        	row = s.getRow(i);
	        	if (start == 0 && i == 0) {
	        		if (scriviIntest) {
	        			String intests = "";
		        		for (TxtFileField tff : tracciato) {
	        				if (!intests.equals("")) {
		        				intests += SEPARATOR;
		        			}
		        			intests += tff.getName();
	        			}
		        		bw.write(intests);
		        		doNewLine = true;
	        		}
	        		for (TxtFileField tff : tracciato) {
	        			if (tff.getIdx() < 0) {
	        				int newIdx = -1;
	        				for (int j = 0; j < row.length; j++) {
	        					String intest = row[j].getContents().trim();
	        					if (intest.equalsIgnoreCase(tff.getName())) {
	        						newIdx = j;
	        						break;
	        					}
	        				}	        				
	        				tff.setIdx(newIdx);
	        			}
	        		}
	        		continue;
	        	}
	        	if (doNewLine || start > 0) {
        			bw.newLine();
        		}
        		if (row.length > 0) {
        			for (TxtFileField tff : tracciato) {
        				int idx = tff.getIdx();
        				String cont = row[idx].getContents().trim();
		            	//campi data
		            	if (row[idx].getType().equals(CellType.DATE) && cont.length() == 8) {
		            		int anno = Integer.parseInt(cont.substring(6, 8));
		            		if (anno < previousCentury) {
		            			cont = cont.substring(0, 6) + "20" + cont.substring(6, 8);
		            		} else {
		            			cont = cont.substring(0, 6) + "19" + cont.substring(6, 8);
		            		}
		            	}
		            	if (cont.indexOf(SEPARATOR) > -1) {
		            		//al momento non è gestito
		            		//cont = "\"" + cont + "\"";
		            	}
		            	if (cont != null && cont.length() > tff.getLength()) {
		            		cont = cont.substring(0, tff.getLength());
		            	} else {
		            		cont = StringUtils.padding(cont, tff.getLength(), tff.getPaddingChar(), tff.isPaddingLeft());
		            	}		            	
		            	bw.write(cont);        				
        			}	            
		        }
        		doNewLine = true;
		    }
	    }
	    bw.flush();
	    bw.close();
	    
	    if (w != null)
	    	w.close();
	    
	    return retVal;
	    
	}
	
	public String checkFilenames(String filePathFrom, String filePathTo) {
		String errMsg = "";
		if (filePathFrom == null || filePathFrom.equals("")) {
			errMsg = "File di origine dati non indicato";
		} else if (!filePathFrom.toUpperCase().endsWith(".XLS")) {
			errMsg = "Il file di origine dati non ha estensione .xls";
		}
		if (filePathTo == null || filePathTo.equals("")) {
			if (!errMsg.equals("")) {
				errMsg += "; ";
			}
			errMsg += "File di destinazione non indicato";
		} else if (!filePathTo.toUpperCase().endsWith(".TXT")) {
			if (!errMsg.equals("")) {
				errMsg += "; ";
			}
			errMsg += "Il file di destinazione non ha estensione .txt";
		}
		return errMsg;
	}
	
	protected String getSpecFilePathTo(String filePathFrom, String filePathTo, String belfiore) {
		return filePathTo;
	}
	
}
