package it.webred.cs.jsf.bean.erogazioneIntervento;

import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class InterventoErogazHistoryHeaderBean extends CsUiCompBaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static String COLUMN_DATA_EROG = "Data";
	private static String COLUMN_STATO = "Stato";
	private static String COLUMN_NOTE = "Note";
	private static String COLUMN_EROGANTE = "Erogante";

	protected List<String> fixedColumns;
	protected HashMap<String, List<Long>> attributes; //Mappa gli attributi agli stati
	protected List<String> columns;
	
	public InterventoErogazHistoryHeaderBean() {
		
	}

	public void initialize(HashMap<Long,List<InterventoErogazAttrBean>> mappaErog) {
		this.fixedColumns = new LinkedList<String>();
		this.fixedColumns.add(COLUMN_DATA_EROG);
		//SISO-775 riga erogante viene tolta dato che è sempre uguale
		//this.fixedColumns.add(COLUMN_EROGANTE);
		this.fixedColumns.add(COLUMN_NOTE);
		this.fixedColumns.add(COLUMN_STATO);
		
		columns = new LinkedList<String>();
		columns.addAll(fixedColumns);

		this.attributes = new HashMap<String,List<Long>>();
		Iterator<Long> it = mappaErog.keySet().iterator();
		while(it.hasNext()){
			Long id = it.next();
			List<InterventoErogazAttrBean> attrsErog = mappaErog.get(id);
			for (InterventoErogazAttrBean interventoErogazAttr : attrsErog) {
				String column = interventoErogazAttr.getLabel();
				
				List<Long> listaStati = attributes.get(column);
				if(listaStati==null) listaStati = new ArrayList<Long>();
				
				listaStati.add(id);
				
				attributes.put(column, listaStati);
				
			}
		}
		
		columns.addAll(attributes.keySet());
	}
	
	public List<String> getColumns() {
		return columns;
	}

	public List<String> getFixedColumns() {
		return fixedColumns;
	}

	public HashMap<String, List<Long>> getAttributes() {
		return attributes;
	}

	public void setAttributes(HashMap<String, List<Long>> attributes) {
		this.attributes = attributes;
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
