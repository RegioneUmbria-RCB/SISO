package it.webred.rulengine.diagnostics.bean;

public class DiagnosticConfigBean {
	
	private int 		idCatalogoDia;	
	private String 		tableNameTestata = "DIA_TESTATA";
	private String		tableNameDettaglio = "";
	private String		tableClass = "";
	private String		seqForDettaglio = "";
	private boolean 	isExecute = true;
	private String		standard = "S";
	private String		fieldTableDettaglioForFK = "";
	private int			numTipoGestione = 1;
	private String		numTipoGestioneValue = "";
	
	public DiagnosticConfigBean(){}
	
	public DiagnosticConfigBean(int idDia){
		idCatalogoDia = idDia;	
	}
	
	public int getNumTipoGestione() {
		return numTipoGestione;
	}

	public void setNumTipoGestione(int numTipoGestione) {
		this.numTipoGestione = numTipoGestione;
	}

	public String getNumTipoGestioneValue() {
		return numTipoGestioneValue;
	}

	public void setNumTipoGestioneValue(String numTipoGestioneValue) {
		this.numTipoGestioneValue = numTipoGestioneValue;
	}

	public String getFieldTableDettaglioForFK() {
		return fieldTableDettaglioForFK;
	}

	public void setFieldTableDettaglioForFK(String fieldTableDettaglioForFK) {
		this.fieldTableDettaglioForFK = fieldTableDettaglioForFK;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public int getIdCatalogoDia() {
		return idCatalogoDia;
	}
	public String getTableClass() {
		return tableClass;
	}

	public void setTableClass(String tableClass) {
		this.tableClass = tableClass;
	}

	public void setIdCatalogoDia(int idCatalogoDia) {
		this.idCatalogoDia = idCatalogoDia;
	}	
	
	public String getSeqForDettaglio() {
		if (seqForDettaglio.length() == 0)
			return "SEQ_"+tableNameDettaglio;
		else
			return seqForDettaglio;
	}

	public void setSeqForDettaglio(String seqForDettaglio) {
		this.seqForDettaglio = seqForDettaglio;
	}

	/**
	 * Ritorna il nome della tabella di testata comune per tutte le diagnostiche.
	 * Se non impostato nel file xml ritornerà sempre DIA_TESTATA, altrimenti il valore associato alla
	 * proprietà tabTestata del file diagnostiche.xml. 
	 * @return Default DIA_TESTATA
	 */
	public String getTableNameTestata() {
		return tableNameTestata;
	}
	public void setTableNameTestata(String tableNameTestata) {
		this.tableNameTestata = tableNameTestata;
	}
	public String getTableNameDettaglio() {
		return tableNameDettaglio;
	}
	public void setTableNameDettaglio(String tableNameDettaglio) {
		this.tableNameDettaglio = tableNameDettaglio;
	}	
	public void setExecute(boolean isExecute) {
		this.isExecute = isExecute;
	}
	public boolean isExecute() {
		return isExecute && isValorizzata();
	}
	public boolean isValorizzata(){
		boolean ret = true;
		if (standard.equals("S")){
			ret = ret && (tableNameDettaglio.trim().length() > 0 ? true : false);
			ret = ret && (tableClass.trim().length() > 0 ? true : false);
		}
		else
			ret = ret && (numTipoGestioneValue.trim().length() == 0 && numTipoGestione != 1 ? false : true);
						
		return ret;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(" (IdCatalogoDia>"+getIdCatalogoDia()+")");
		sb.append(" (Tabella Testata>"+getTableNameTestata()+")");
		sb.append(" (Tabella Dettaglio>"+getTableNameDettaglio()+")");
		sb.append(" (Campo FK Tab.Dettaglio >"+getFieldTableDettaglioForFK()+")");
		sb.append(" (Tabella Class>"+getTableClass()+")");
		sb.append(" (Sequence Dettaglio>"+getSeqForDettaglio()+")");	
		sb.append(" (D. isExecute>"+isExecute()+")");	
		sb.append(" (D. Standard>"+getStandard()+")");	
		sb.append(" (D. Tipo Gestione>"+getNumTipoGestione()+")");
		sb.append(" (D. Tipo Gestione Value>"+getNumTipoGestioneValue()+")");
		
		return sb.toString();
	}
}
