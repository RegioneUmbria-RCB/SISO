package it.webred.mui.model;

import it.webred.mui.input.MuiFornituraParser;
import it.webred.mui.input.RowField;

public class CodiceErroreImport extends CodiceErroreImportBase {

	public String toString(){
		return getCodiceRegolaInfranta();
	}
	
	public String getCodiceBase(){
    	RowField rf = MuiFornituraParser.getNextField(getCodiceRegolaInfranta(),'.');
    	if(rf != null){
    		return rf.field;
    	}
    	else{
    		return null;
    	}
		
	}
	public String getVariante(){
    	RowField rf = MuiFornituraParser.getNextField(getCodiceRegolaInfranta(),'.');
    	if(rf != null){
    		return rf.remaining;
    	}
    	else{
    		return null;
    	}
	}

    public Boolean getFlagIsVariante() {
        return getCodiceBase() != null;
    }
}
