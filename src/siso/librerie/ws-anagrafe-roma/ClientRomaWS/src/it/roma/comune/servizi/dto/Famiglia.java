package it.roma.comune.servizi.dto;

import java.io.Serializable;

public class Famiglia implements Serializable {

	private String FlagFamigliaConvivenza;
	private DatiIndirizzo datiIndirizzo;
	private Componente[] componenti;
	private String CodiceFamiglia;
	
	public String getCodiceFamiglia() {
		return CodiceFamiglia;
	}
	public void setCodiceFamiglia(String codiceFamiglia) {
		CodiceFamiglia = codiceFamiglia;
	}
	
	public String getFlagFamigliaConvivenza() {
		return FlagFamigliaConvivenza;
	}
	public void setFlagFamigliaConvivenza(String flagFamigliaConvivenza) {
		FlagFamigliaConvivenza = flagFamigliaConvivenza;
	}
	public DatiIndirizzo getDatiIndirizzo() {
		return datiIndirizzo;
	}
	public void setDatiIndirizzo(DatiIndirizzo datiIndirizzo) {
		this.datiIndirizzo = datiIndirizzo;
	}
	public Componente[] getComponenti() {
		return componenti;
	}
	public void setComponenti(Componente[] componenti) {
		this.componenti = componenti;
	}
	
	
}
