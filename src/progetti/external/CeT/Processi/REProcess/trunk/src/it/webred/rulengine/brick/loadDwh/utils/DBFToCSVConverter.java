package it.webred.rulengine.brick.loadDwh.utils;

import java.io.*;

public class DBFToCSVConverter implements FileConverter {
	
	public final static String SEPARATOR = "|";
	private int previousCentury = 40; //valore di default, anno a due cifre: 2039 ma 1940
	
	class FieldHeader {
		public String fieldName;
		public char dataType; 
		public int displacement; 
		public int length;
		public byte decimal;
	}

	public void save(String filePathFrom, String filePathTo, String belfiore) throws Exception {
		
		String check = checkFilenames(filePathFrom, filePathTo);
		if (check != null && !check.equals("")) {
			throw new Exception(check);
		}
		
		System.out.println("Inizio conversione del file dbf " + filePathFrom + " nel file csv " + filePathTo + ".");
		
		RandomAccessFile dbf = new RandomAccessFile(filePathFrom, "r");
		
		//informazioni dell'header
		int dbfType = dbf.readUnsignedByte();
	    int dbfLastUpdateYear = (byte)dbf.read();
	    int dbfLastUpdateMonth = (byte)dbf.read();
	    int dbfLastUpdateDay = (byte)dbf.read();
	    int dbfNumberRecs = readBackwardsInt(dbf);
	    int dbfDataPosition = readBackwardsUnsignedShort(dbf);
	    int dbfDataLength = readBackwardsUnsignedShort(dbf);
	    int dbfNumberFields = (dbfDataPosition - 33) / 32;
	    dbf.seek(32);
	    
	    // struttura campi
	    byte fieldNameBuffer[] = new byte[11];
	    int locn = 0;
	    FieldHeader[]  dbfFields = new FieldHeader[dbfNumberFields+1];
	    
	    //campo "deleted"
	    dbfFields[0] = new FieldHeader();
	    dbfFields[0].fieldName = "@DELETED@";
	    dbfFields[0].dataType = 'C';
	    dbfFields[0].displacement = 0;
	    locn += (dbfFields[0].length = 1);
	    dbfFields[0].decimal = 0;
	    
	    //ciclo di lettura dei campi
	    for (int i = 1; i <= dbfNumberFields; i++) {
	    	dbfFields[i] = new FieldHeader();
		    //nome
		    dbf.read(fieldNameBuffer);
		    dbfFields[i].fieldName = new String(fieldNameBuffer);
		    int posZero = dbfFields[i].fieldName.indexOf(0);
		    dbfFields[i].fieldName = dbfFields[i].fieldName.substring(0, posZero);
		    //tipo
		    dbfFields[i].dataType = (char)dbf.read();
		    //lunghezza
		    dbf.skipBytes(4);
		    dbfFields[i].displacement = locn;
		    locn += (dbfFields[i].length = dbf.read()); 
		    //decimali
		    if(dbfFields[i].dataType == 'N') {
		       dbfFields[i].decimal = (byte)dbf.read();
		    } else {
		       dbfFields[i].decimal = 0;
		       int len = (byte)dbf.read();
		       dbfFields[i].length += len * 256;
		    }
		    dbf.skipBytes(14);
      	}
	    
	    File f = new File(filePathTo);
		OutputStream os = (OutputStream)new FileOutputStream(f);
		String encoding = "UTF8";
	    OutputStreamWriter osw = new OutputStreamWriter(os, encoding);
	    BufferedWriter bw = new BufferedWriter(osw);
	    
	    String line = "";
	    //intestazioni
	    for (int i = 1; i < dbfFields.length; i++) {
	    	if (!line.trim().equals(""))
    			line += SEPARATOR;
	    	line += dbfFields[i].fieldName;
	    }
	    bw.write(line);
    	bw.newLine();
	    //record
	    for (int i = 0; i < dbfNumberRecs; i++) {
	    	line = "";
	    	for (int j = 1; j < dbfFields.length; j++) {
	    		dbf.seek(dbfDataPosition + (i * dbfDataLength) + dbfFields[j].displacement);
	    		byte dataBuffer[] = new byte[dbfFields[j].length];
	    		dbf.read(dataBuffer);
	    		String value = new String(dataBuffer).trim();
	    		if (dbfFields[j].dataType == 'D' && value.length() == 8) {
	    			//data
	    			//problema degli anni dal 2000 in avanti (che diventano 1900 ecc.)
	    			int annoDa = Integer.parseInt("19" + previousCentury);
	    			if (Integer.parseInt(value.substring(0, 4)) < annoDa) {
	    				value = "20" + value.substring(2, 8);
	    			}
	    			//formato dd/MM/yyyy
	    			value = value.substring(6, 8) + "/" + value.substring(4, 6) + "/" + value.substring(0, 4);
	    		} else if (dbfFields[j].dataType == 'N') {
	    			//numerico
	    			value = value.replace(".", ",");
	    		}
	    		if (value.indexOf(SEPARATOR) > -1) {
	    			//al momento non è gestito
	    			//value = "\"" + value + "\"";
            	}
	    		if (j > 1)
	    			line += SEPARATOR;
	    		line += value;
	    	}
	    	bw.write(line);
	    	bw.newLine();
	    }
	    
	    bw.flush();
	    bw.close();
	    
	    if (dbf != null)
	    	dbf.close();
	    
		System.out.println("Conversione del file dbf " + filePathFrom + " nel file csv " + filePathTo + " terminata correttamente.");

	}
	
	private int readBackwardsInt(RandomAccessFile dbf) throws Exception {
		int ch4 = dbf.read();
		int ch3 = dbf.read();
		int ch2 = dbf.read();
		int ch1 = dbf.read();
		if ((ch1 | ch2 | ch3 | ch4) < 0)
			throw new EOFException();
		int retVal = ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
		return retVal;
	}
	
	private int readBackwardsUnsignedShort(RandomAccessFile dbf) throws Exception {
		int ch2 = dbf.read();
		int ch1 = dbf.read();
		if ((ch1 | ch2) < 0)
			throw new EOFException();
		int retVal = (ch1 << 8) + (ch2 << 0);
		return retVal;
	}
	
	public String checkFilenames(String filePathFrom, String filePathTo) {
		String errMsg = "";
		if (filePathFrom == null || filePathFrom.equals("")) {
			errMsg = "File di origine dati non indicato";
		} else if (!filePathFrom.toUpperCase().endsWith(".DBF")) {
			errMsg = "Il file di origine dati non ha estensione .dbf";
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
