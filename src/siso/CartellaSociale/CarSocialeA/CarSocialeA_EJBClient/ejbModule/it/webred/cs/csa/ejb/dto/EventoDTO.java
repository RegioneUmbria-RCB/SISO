package it.webred.cs.csa.ejb.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EventoDTO extends BaseDTO {
	
	private Long id;

	private String tipoEvento;

	private String mittente;

	private String destinatario;

	private Date dataEvento;
	private String dataEventoStringa;

	private String nomeSoggetto;

	private String cognomeSoggetto;

	private String codiceFiscale;
	
	private String cartellaSociale;
	
	private OperatoreDTO operatore;
	
	private String nomeCognomeOperatore;
	
	//COSTRUTTORI
	
	public EventoDTO(){
		
	}
	
	public EventoDTO(String tipoEvento, String mittente,
			String destinatario, String nomeSoggetto, String cognomeSoggetto,
			String codiceFiscale, String cartellaSociale){
		this.tipoEvento = tipoEvento;
		this.mittente = mittente;
		this.destinatario = destinatario;
		this.nomeSoggetto = nomeSoggetto;
		this.cognomeSoggetto = cognomeSoggetto;
		this.codiceFiscale = codiceFiscale;
		this.cartellaSociale = cartellaSociale;
	}
	
	public EventoDTO(String tipoEvento, String mittente,
			String destinatario, String nomeSoggetto, String cognomeSoggetto,
			String codiceFiscale, String cartellaSociale, OperatoreDTO operatore){
		
		this(tipoEvento, mittente, destinatario, nomeSoggetto, cognomeSoggetto, codiceFiscale, cartellaSociale);
		this.operatore = operatore;
	}
	

	public EventoDTO(Long id,String tipoEvento, String mittente,
			String destinatario, String nomeSoggetto, String cognomeSoggetto,
			String codiceFiscale, String cartellaSociale, Date dataEvento, OperatoreDTO operatore){
		this(tipoEvento, mittente, destinatario, nomeSoggetto, cognomeSoggetto, codiceFiscale, cartellaSociale, operatore);
		
		this.id = id;
		this.dataEvento = dataEvento;
		this.operatore = operatore; 
	}

	// GETTER & SETTER

	public String getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public String getMittente() {
		return mittente;
	}

	public void setMittente(String mittente) {
		this.mittente = mittente;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public Date getDataEvento() {
		return dataEvento;
	}

	public void setDataEvento(Date dataEvento) {
		this.dataEvento = dataEvento;
	}

	public String getNomeSoggetto() {
		return nomeSoggetto;
	}

	public void setNomeSoggetto(String nomeSoggetto) {
		this.nomeSoggetto = nomeSoggetto;
	}

	public String getCognomeSoggetto() {
		return cognomeSoggetto;
	}

	public void setCognomeSoggetto(String cognomeSoggetto) {
		this.cognomeSoggetto = cognomeSoggetto;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCartellaSociale() {
		return cartellaSociale;
	}

	public void setCartellaSociale(String cartellaSociale) {
		this.cartellaSociale = cartellaSociale;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OperatoreDTO getOperatore() {
		return operatore;
	}

	public void setOperatore(OperatoreDTO operatoreSettore) {
		this.operatore = operatoreSettore;
	}

	public String getNomeCognomeOperatore() {
		return nomeCognomeOperatore;
	}

	public void setNomeCognomeOperatore(String nomeCognomeOperatore) {
		this.nomeCognomeOperatore = nomeCognomeOperatore;
	}

	public String getDataEventoStringa() {
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("dd/MM/yyyy");
		String data = sdf.format(this.dataEvento);
		
		return data;
	}

}
