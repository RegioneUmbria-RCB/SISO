package it.umbriadigitale.interscambio.data.wrapper;

/**
 * Wrapper dei dati relativi allo scambio di messaggi (es: idMessaggio, tipologiaProcesso...) di Interscambio Cartella Sociale
 * 
 * @author Iacopo Sorce
 */
public class MessageDataWrapper {
	private String id;
	private String dataCreazioneText;
	private String idInterazione;
	private String tipologiaProcesso;
	private String tipologiaModalitaProcessamento;
	private String acceptAckCode;
	private String idMittente;
	private String idDestinatario;
	
	
	public MessageDataWrapper(String id, String dataCreazioneText, String idInterazione, String tipologiaProcesso,
			String tipologiaModalitaProcessamento, String acceptAckCode, String idMittente, String idDestinatario) {
		
		this.id = id;
		this.dataCreazioneText = dataCreazioneText;
		this.idInterazione = idInterazione;
		this.tipologiaProcesso = tipologiaProcesso;
		this.tipologiaModalitaProcessamento = tipologiaModalitaProcessamento;
		this.acceptAckCode = acceptAckCode;
		this.idMittente = idMittente;
		this.idDestinatario = idDestinatario;
	}
	
	
	public String getId() {
		return id;
	}

	public String getDataCreazioneText() {
		return dataCreazioneText;
	}

	public String getIdInterazione() {
		return idInterazione;
	}

	public String getTipologiaProcesso() {
		return tipologiaProcesso;
	}

	public String getTipologiaModalitaProcessamento() {
		return tipologiaModalitaProcessamento;
	}

	public String getAcceptAckCode() {
		return acceptAckCode;
	}

	public String getIdMittente() {
		return idMittente;
	}

	public String getIdDestinatario() {
		return idDestinatario;
	}
}
