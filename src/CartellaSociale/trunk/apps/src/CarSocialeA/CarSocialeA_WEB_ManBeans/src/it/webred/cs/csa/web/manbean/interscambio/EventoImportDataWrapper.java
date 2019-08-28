package it.webred.cs.csa.web.manbean.interscambio;

import org.hl7.v3.request.PRSSIN001004ZZ;

public class EventoImportDataWrapper {
	private String mittente;
	private String destinatario;
	private String nome;
	private String cognome;
	private String codiceFiscale;
	
	
	public EventoImportDataWrapper(PRSSIN001004ZZ request) {
		// sender.device.id[0]@extension -- dev'esserci un solo sender.device.id
		mittente = request.getSender().getDevice().getId().get(0).getExtension();
		
		// receiver[0].device.id[0]@extension -- dev'esserci un solo receiver, e al suo interno un solo device.id
		destinatario = request.getReceiver().get(0).getDevice().getId().get(0).getExtension();
		
		// TODO al momento non c'è un modo semplice di ricavare nome, cognome e codiceFiscale
		nome = "-";
		cognome = "-";
		codiceFiscale = "-";
	}
	
	
	public String getMittente() {
		return mittente;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}
}
