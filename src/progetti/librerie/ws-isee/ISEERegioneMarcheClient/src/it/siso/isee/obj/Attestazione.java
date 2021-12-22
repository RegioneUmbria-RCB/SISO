package it.siso.isee.obj;

import java.io.Serializable;
import java.util.*;
 
public class Attestazione implements Serializable {

	private String codiceFiscaleDichiarante;
	private String numeroProtocolloDSU;
	private String dataPresentazione;
	private String dataValidita;
	private String protocolloMittente;
	
	
	private Ordinario ordinario;
	private List<Ridotto> ridotto = new ArrayList<Ridotto>();
	private VariazioneAttestazione variazione = null;
	private List<OmissioneDifformita> omissioneDifforma = new ArrayList<OmissioneDifformita>();
	
	
	
	
	public String getCodiceFiscaleDichiarante() {
		return codiceFiscaleDichiarante;
	}
	public void setCodiceFiscaleDichiarante(String codiceFiscaleDichiarante) {
		this.codiceFiscaleDichiarante = codiceFiscaleDichiarante;
	}
	public String getNumeroProtocolloDSU() {
		return numeroProtocolloDSU;
	}
	public void setNumeroProtocolloDSU(String numeroProtocolloDSU) {
		this.numeroProtocolloDSU = numeroProtocolloDSU;
	}
	public String getDataPresentazione() {
		return dataPresentazione;
	}
	public void setDataPresentazione(String dataPresentazione) {
		this.dataPresentazione = dataPresentazione;
	}
	public String getDataValidita() {
		return dataValidita;
	}
	public void setDataValidita(String dataValidita) {
		this.dataValidita = dataValidita;
	}
	public String getProtocolloMittente() {
		return protocolloMittente;
	}
	public void setProtocolloMittente(String protocolloMittente) {
		this.protocolloMittente = protocolloMittente;
	}
	public Ordinario getOrdinario() {
		return ordinario;
	}
	public void setOrdinario(Ordinario ordinario) {
		this.ordinario = ordinario;
	}
	public List<Ridotto> getRidotto() {
		return ridotto;
	}
	public void setRidotto(List<Ridotto> ridotto) {
		this.ridotto = ridotto;
	}
	
	public void addRidotto(Ridotto ridotto) {
		this.ridotto.add(ridotto);
	}
	
	
	public VariazioneAttestazione getVariazione() {
		return variazione;
	}
	public void setVariazione(VariazioneAttestazione variazione) {
		this.variazione = variazione;
	}
	public List<OmissioneDifformita> getOmissioneDifforma() {
		return omissioneDifforma;
	}
	public void setOmissioneDifforma(List<OmissioneDifformita> omissioneDifforma) {
		this.omissioneDifforma = omissioneDifforma;
	}
	
	public void addOmissioneDifforma(OmissioneDifformita omissioneDifforma) {
		this.omissioneDifforma.add(omissioneDifforma);
	}
}
