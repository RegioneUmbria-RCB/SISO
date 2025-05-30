package it.webred.cs.csa.ejb.dto.alert;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class TipoAlertBean implements Serializable{
	private static final long serialVersionUID = 1L;
		
	protected String labelTipo;
	protected List<AlertBean> listaAlert = new LinkedList<AlertBean>();
	
	public String getLabelTipo() {
		return labelTipo;
	}
	public void setLabelTipo(String labelTipo) {
		this.labelTipo = labelTipo;
	}
	public List<AlertBean> getListaAlert() {
		return listaAlert;
	}
}
