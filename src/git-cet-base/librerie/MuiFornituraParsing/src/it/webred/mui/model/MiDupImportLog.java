package it.webred.mui.model;

import it.webred.mui.input.MuiFornituraParser;
import it.webred.mui.input.RowField;

public class MiDupImportLog extends MiDupImportLogBase {
    
	private String codice = null;
	private String variante =  null;
	
    /** default constructor */
    public MiDupImportLog() {
    	super();
    }
   /** full constructor */
    public MiDupImportLog(MiDupFornitura miDupFornitura, String idNota, MiDupNotaTras miDupNotaTras, Integer tipoRecord, String tabellaRecord, String colonnaRegolaInfranta, CodiceErroreImport codiceRegolaInfranta, MiDupFabbricatiIdentifica miDupFabbricatiIdentifica, MiDupFabbricatiInfo miDupFabbricatiInfo, MiDupFornituraInfo miDupFornituraInfo, MiDupIndirizziSog miDupIndirizziSog, MiDupSoggetti miDupSoggetti, MiDupTerreniInfo miDupTerreniInfo, MiDupTitolarita miDupTitolarita) {
    	super(miDupFornitura,  idNota,  miDupNotaTras, tipoRecord, tabellaRecord, colonnaRegolaInfranta,  codiceRegolaInfranta,  miDupFabbricatiIdentifica,  miDupFabbricatiInfo,  miDupFornituraInfo,  miDupIndirizziSog,  miDupSoggetti, miDupTerreniInfo, miDupTitolarita);
    	RowField rf = MuiFornituraParser.getNextField(codiceRegolaInfranta.getCodiceRegolaInfranta(),'.');
    	if(rf != null){
	    	setCodice(rf.field);
	    	setVariante(rf.remaining);
    	}
    }
    
    public Class getClasseRegolaInfranta(){
    	if(getTabellaRecord() != null){
    		String cname= getTabellaRecord();
    		cname = cname.toUpperCase().charAt(0)+ (cname.length() > 1 ? cname.substring(1) : "");
    		String res = "it.webred.mui.model." + cname;
    		try {
				return Class.forName(res);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
	    		return null;
			}
    	}
    	else{
    		return null;
    	}
    }
    
    public String getPropertyRegolaInfranta(){
    	return javafyName(getColonnaRegolaInfranta()) ;
    }
    
    public boolean getFlagBloccante(){
    	return  getCodiceRegolaInfranta().getFlagBloccante();
    }
    
    public String getNote(){
    	return (getCodiceRegolaInfranta().getDescrizione() != null ? getCodiceRegolaInfranta().getDescrizione().replaceAll(":colonna",getColonnaRegolaInfranta()).replaceAll(":tabella",getTabellaRecord()) : "") ;
    }
    
    private static String javafyName(String dbname){
    	StringBuffer sb = new StringBuffer();
//    	dbname =  dbname.toUpperCase().charAt(0)
//		+ (dbname.length() > 1 ? dbname.substring(1) : "");
    	boolean useUpperCase = false;
    	for(int i = 0; i< dbname.length();i++){
    		if(dbname.charAt(i) == '_'){
    			if(i==1){
    				sb = new StringBuffer(sb.toString().toUpperCase());
    			}
    			useUpperCase = true;
    		}
    		else{
    			if(useUpperCase){
    				sb.append( dbname.toUpperCase().charAt(i));
    			}
    			else{
    				sb.append( dbname.toLowerCase().charAt(i));
    			}
    			useUpperCase = false;
    		}
    	}
    	return sb.toString();
    }
	public String getVariante() {
		return variante;
	}
	protected void setVariante(String variante) {
		this.variante = variante;
	}
	public String getCodice() {
		return (codice != null ? codice: getCodiceRegolaInfranta().getCodiceRegolaInfranta());
	}
	protected void setCodice(String codice) {
		this.codice = codice;
	}
	public void setCodiceRegolaInfranta(CodiceErroreImport codiceRegolaInfranta){
		super.setCodiceRegolaInfranta(codiceRegolaInfranta);
    	RowField rf = MuiFornituraParser.getNextField(codiceRegolaInfranta.getCodiceRegolaInfranta(),'.');
    	if(rf != null){
	    	setCodice(rf.field);
	    	setVariante(rf.remaining);
    	}
  	}
}
