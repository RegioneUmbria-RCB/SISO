package it.webred.cs.jsf.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import it.webred.cs.data.DataModelCostanti.DatiSociali.TIPO_PROTEZIONE_GIURIDICA;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.jsf.manbean.ComponenteAltroMan;

public class ProtezioneGiuridicaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String tipo;
	private ComponenteAltroMan componente;
	private String numDecreto;
	private Date dataDecreto;
	
	public ProtezioneGiuridicaBean(Long idSoggetto, String tipo, List<CsAComponente> listaComponenti) {
		this.tipo = tipo;
		componente = new ComponenteAltroMan(idSoggetto, listaComponenti);
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ComponenteAltroMan getComponente() {
		return componente;
	}
	public void setComponente(ComponenteAltroMan componente) {
		this.componente = componente;
	}
	public String getNumDecreto() {
		return numDecreto;
	}
	public void setNumDecreto(String numDecreto) {
		this.numDecreto = numDecreto;
	}
	public Date getDataDecreto() {
		return dataDecreto;
	}
	public void setDataDecreto(Date dataDecreto) {
		this.dataDecreto = dataDecreto;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public boolean isEmpty() {
		return componente.isEmpty() && StringUtils.isBlank(numDecreto) && dataDecreto==null;
	}
	public String getLabel() {
		if(TIPO_PROTEZIONE_GIURIDICA.SOSTEGNO.getCodice().equalsIgnoreCase(tipo)) return TIPO_PROTEZIONE_GIURIDICA.SOSTEGNO.getDescrizione();
		else if(TIPO_PROTEZIONE_GIURIDICA.CURATELA.getCodice().equalsIgnoreCase(tipo)) return TIPO_PROTEZIONE_GIURIDICA.CURATELA.getDescrizione();
		else if(TIPO_PROTEZIONE_GIURIDICA.TUTELA.getCodice().equalsIgnoreCase(tipo)) return TIPO_PROTEZIONE_GIURIDICA.TUTELA.getDescrizione();
		else return "Non definito";
	}

}
