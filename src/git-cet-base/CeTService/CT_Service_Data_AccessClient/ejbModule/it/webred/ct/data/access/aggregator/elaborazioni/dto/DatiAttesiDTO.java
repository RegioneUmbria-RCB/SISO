package it.webred.ct.data.access.aggregator.elaborazioni.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DatiAttesiDTO extends CeTBaseObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private DecimalFormat twoDForm =  new DecimalFormat("0.00");
	
	private String valoreCommercialeMq;
	private String valoreCommerciale;
	private String renditaMinima;
	private String tariffaPerVano;
	private List<ClassamentoDTO> classamenti;
	private List<ClasseMaxCategoriaDTO> classiMaxCategoria;
	private String classeMaxFrequente;
	
	public DatiAttesiDTO() {
		super();
		valoreCommercialeMq ="0";
		valoreCommerciale = "0";
		renditaMinima ="0";
		tariffaPerVano ="0";
		classamenti = new ArrayList<ClassamentoDTO>();
		classiMaxCategoria = new ArrayList<ClasseMaxCategoriaDTO>();
		
		twoDForm.setMaximumFractionDigits(2);
		twoDForm.setGroupingSize(3);
		
	}

	public String getValoreCommercialeMq() {
		return valoreCommercialeMq;
	}

	public void setValoreCommercialeMq(String valoreCommercialeMq) {
		this.valoreCommercialeMq = valoreCommercialeMq;
	}

	public String getValoreCommerciale() {
		return valoreCommerciale;
	}

	public void setValoreCommerciale(String valoreCommerciale) {
		this.valoreCommerciale = valoreCommerciale;
	}

	public String getRenditaMinima() {
		return renditaMinima;
	}

	public void setRenditaMinima(String renditaMinima) {
		this.renditaMinima = renditaMinima;
	}

	public String getTariffaPerVano() {
		return tariffaPerVano;
	}

	public void setTariffaPerVano(String tariffaPerVano) {
		this.tariffaPerVano = tariffaPerVano;
	}

	public List<ClassamentoDTO> getClassamenti() {
		return classamenti;
	}

	public void setClassamenti(List<ClassamentoDTO> classamenti) {
		this.classamenti = classamenti;
	}

	public List<ClasseMaxCategoriaDTO> getClassiMaxCategoria() {
		return classiMaxCategoria;
	}

	public void setClassiMaxCategoria(List<ClasseMaxCategoriaDTO> classiMaxCategoria) {
		this.classiMaxCategoria = classiMaxCategoria;
	}

	public String getClasseMaxFrequente() {
		return classeMaxFrequente;
	}

	public void setClasseMaxFrequente(String classeMaxFrequente) {
		this.classeMaxFrequente = classeMaxFrequente;
	}
	
	//Conversione da Double a String, con troncamento a due cifre decimali
	public void setValoreCommercialeMq(Double valoreCommercialeMq) {
		String s = twoDForm.format(valoreCommercialeMq);
		//s = s.replace(',', '.');
		this.setValoreCommercialeMq(s);
	}
	
	public void setTariffaPerVano(Double tariffaPerVano) {
		String s = twoDForm.format(tariffaPerVano);
		//s = s.replace(',', '.');
		this.setTariffaPerVano(s);
	}
	
	public void setRenditaMinima(Double renditaMinima) {
		String s = twoDForm.format(renditaMinima);
		//s = s.replace(',', '.');
		this.setRenditaMinima(s);
	}
	
	public void setValoreCommerciale(Double valoreCommerciale) {
		String s = twoDForm.format(valoreCommerciale);
		//s = s.replace(',', '.');
		this.setValoreCommerciale(s);
	}
}
