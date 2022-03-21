package it.siso.isee.obj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OmissioneDifformita implements Serializable {

	private String tipoOmissioneDifformita;
	private String codiceFiscale;
	private String dataControlloAE;
	private List<Rapporto> rapporto = new ArrayList<Rapporto>();
	private RedditoAE redditoAE = null;
	
	
	
	public RedditoAE getRedditoAE() {
		return redditoAE;
	}
	public void setRedditoAE(RedditoAE redditoAE) {
		this.redditoAE = redditoAE;
	}
	public String getTipoOmissioneDifformita() {
		return tipoOmissioneDifformita;
	}
	public void setTipoOmissioneDifformita(String tipoOmissioneDifformita) {
		this.tipoOmissioneDifformita = tipoOmissioneDifformita;
	}
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getDataControlloAE() {
		return dataControlloAE;
	}
	public void setDataControlloAE(String dataControlloAE) {
		this.dataControlloAE = dataControlloAE;
	}
	public List<Rapporto> getRapporto() {
		return rapporto;
	}
	public void setRapporto(List<Rapporto> rapporto) {
		this.rapporto = rapporto;
	}
	
	public void addRapporto(Rapporto rapporto) {
		this.rapporto.add(rapporto);
	}
}
