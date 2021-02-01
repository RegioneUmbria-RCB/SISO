package it.webred.ct.proc.ario.utils;

import it.webred.ct.proc.ario.fonti.DatoDwh;

import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

public class DropTable {

	private Properties props = null;
	protected static final Logger log = Logger.getLogger(DropTable.class.getName());

	
	
	public DropTable(){
		
		//Caricamento del file di properties dei caricatori		
		this.props = new Properties();
		try {
			InputStream is = this.getClass().getResourceAsStream("/tabelleDroppate.properties");
		    this.props.load(is);                     
		}catch(Exception e) {
		    log.error("Eccezione: "+e.getMessage(), e);			   
		}																
	}
	
	
	
	public boolean isDropping(DatoDwh classeFonte){
		
		
		String tab = classeFonte.getTable();
		
		String isDrop = this.props.getProperty(tab);
		
		boolean res = false;
		
		if(isDrop.equals("true")){
			res = true;
		}else{
			res = false;
		}
		
		return res;
		
	}
}
