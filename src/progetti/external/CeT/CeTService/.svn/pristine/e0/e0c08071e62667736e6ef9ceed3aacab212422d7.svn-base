package it.webred.ct.data.access.aggregator.elaborazioni.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Locale;

public class ClassamentoDTO extends CeTBaseObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private DecimalFormat twoDForm =  new DecimalFormat("0.00");
	
	private String categoria;
	private String classe;
	private String tariffa;
	private String renditaComplessiva;
	
	public ClassamentoDTO() {
		super();
		
		
		// TODO Auto-generated constructor stub
	}

	public ClassamentoDTO(String categoria, String classe, String tariffa,
			String renditaComplessiva) {
		
		super();
		
		twoDForm.setMaximumFractionDigits(2);
		twoDForm.setGroupingSize(3);
		
		this.categoria = categoria;
		this.classe = classe;
		this.tariffa = tariffa;
		this.renditaComplessiva = renditaComplessiva;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getTariffa() {
		return tariffa;
	}

	public void setTariffa(String tariffa) {
		this.tariffa = tariffa;
	}

	public String getRenditaComplessiva() {
		return renditaComplessiva;
	}

	public void setRenditaComplessiva(String renditaComplessiva) {
		this.renditaComplessiva = renditaComplessiva;
	}
	
	public void setRenditaComplessiva(Double renditaComplessiva) {
		this.setRenditaComplessiva(twoDForm.format(renditaComplessiva));
	}
	
	public void setTariffa(Double tariffa) {
		this.setTariffa(twoDForm.format(tariffa));
	}
	

}
