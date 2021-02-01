package it.webred.rulengine.brick.loadDwh.utils;

import java.io.*;
import jxl.*;
import java.util.*;

public class XLSToCSVConverter implements FileConverter {
	
	public final static String SEPARATOR = "|";
	private int previousCentury = 40; //valore di default, anno a due cifre: 2039 ma 1940

	public void save(String filePathFrom, String filePathTo, String belfiore) throws Exception {
		
		String check = checkFilenames(filePathFrom, filePathTo);
		if (check != null && !check.equals("")) {
			throw new Exception(check);
		}
		
		System.out.println("Inizio conversione del file xls " + filePathFrom + " nel file csv " + filePathTo + ".");
		
		//la scrittura avviene a blocchi di 300 righe
		int start = 0;
		int rowsPerTime = 300;
		int end = start + rowsPerTime - 1;
		int fields = 0;
		while (fields > -1) {
			fields = saveRows(filePathFrom, filePathTo, start, end, fields);
			start += rowsPerTime;
			end += rowsPerTime;
		}		
	    
	    System.out.println("Conversione del file xls " + filePathFrom + " nel file csv " + filePathTo + " terminata correttamente.");
	}
	
	private int saveRows(String filePathFrom, String filePathTo, int start, int end, int fields)  throws Exception {
		
		int retVal = fields;
		
		Runtime.getRuntime().runFinalization();
		Runtime.getRuntime().gc();
		
		//file CSV		
		File f = new File(filePathTo);
		OutputStream os = (OutputStream)new FileOutputStream(f, start > 0);
		String encoding = "UTF8";
	    OutputStreamWriter osw = new OutputStreamWriter(os, encoding);
	    BufferedWriter bw = new BufferedWriter(osw);
	
	    //documento Excel
	    WorkbookSettings ws = new WorkbookSettings();
	    //ws.setLocale(new Locale("it", "IT"));
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
	        for (int i = start; i < myEnd; i++) {
	        	row = s.getRow(i);
	        	if (start == 0 && i == 0) {
	        		//intestazioni	        		
	        		fields = row.length;
	        		if (retVal > -1) {
	        			retVal = fields;
	        		}	        		
	        	}
		        if (row.length > 0) {
		        	bw.write(row[0].getContents());		        	
		            for (int j = 1; j < row.length; j++) {
		            	bw.write(SEPARATOR);
		            	String cont = row[j].getContents().trim();
		            	//campi data
		            	if (row[j].getType().equals(CellType.DATE) && cont.length() == 8) {
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
		            	bw.write(cont);
		            }		            
		        }
		        //eventuali campi a fine record non valorizzati
	            if (row.length < fields) {
	            	for (int j = row.length; j < fields; j++) {
	            		if (j > 0)	            			
	            			bw.write(SEPARATOR);
		            }
	            }
		        bw.newLine();
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
		} else if (!filePathTo.toUpperCase().endsWith(".CSV")) {
			if (!errMsg.equals("")) {
				errMsg += "; ";
			}
			errMsg += "Il file di destinazione non ha estensione .csv";
		}
		return errMsg;
	}

	public int getPreviousCentury() {
		return previousCentury;
	}

	public void setPreviousCentury(int previousCentury) {
		this.previousCentury = previousCentury;
	}
	
}
