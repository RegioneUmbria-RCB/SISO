package it.webred.cs.jsf.bean.erogazioneIntervento;

import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class InterventoErogazHistoryHeaderBean extends CsUiCompBaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static String COLUMN_DATA_EROG = "Data";
	private static String COLUMN_STATO = "Stato";
	private static String COLUMN_NOTE = "Note";
	private static String COLUMN_ORGANIZZAZIONE = "Organizzazione";

	protected List<String> fixedColumns;
	protected List<String> attributes;
	protected List<String> columns;
	
	public InterventoErogazHistoryHeaderBean() {
		
	}

	public void initialize(List<InterventoErogazAttrBean> attrsErog) {
		this.fixedColumns = new LinkedList<String>();
		this.fixedColumns.add(COLUMN_DATA_EROG);
		this.fixedColumns.add(COLUMN_STATO);
		this.fixedColumns.add(COLUMN_NOTE);
		this.fixedColumns.add(COLUMN_ORGANIZZAZIONE);

		this.attributes = new LinkedList<String>();
		for (InterventoErogazAttrBean interventoErogazAttr : attrsErog) {
			String column = interventoErogazAttr.getLabel();
			this.attributes.add(column);
		}
		
		columns = new LinkedList<String>();
		columns.addAll(fixedColumns);
		columns.addAll(attributes);
	}
	
	public List<String> getColumns() {
		return columns;
	}

	public List<String> getFixedColumns() {
		return fixedColumns;
	}

	public List<String> getAttributes() {
		return attributes;
	}

	public void toString(StringBuilder sb) {
		sb.append("***************** HEADER **********************\n");
		for (String columnName : this.columns) {
			sb.append(String.format("%s\t\t", columnName));
		}
		sb.append("\n**********************************************\n");
		sb.append("\n");
	}
 
}
