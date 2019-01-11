package it.webred.ss.web.bean.lista;

import it.webred.ss.ejb.dto.DatiSchedaListDTO;
import it.webred.ss.web.bean.SegretariatoSocBaseBean;

import java.math.BigDecimal;
import java.util.Date;

public class Scheda extends SegretariatoSocBaseBean {
	
	private BigDecimal identificativo;
	private Long id;
	private Date dataAccesso;
	private String cognome = "-";
	private String nome = "-";
	private Date dataNascita;
	private String tipo = "-";
	private String stato = "-";
	private String operatore = "-";
	private Long ufficioId;
	private String ufficio = "-";
	private String puntoContatto = "-";
	private Date dataModifica;
	private String privacy = null;
	private Date dataInvio;
	private Date dataRicezione;
	private String nomeZonaInviante;
	private String statoCS;
	private String origZonaSociale;
	private String cf;
	private String descOrganizzazione;
	
	/*public Scheda(Date dataAccesso, String cognome, String nome,
			Date dataNascita, String tipo, String stato, String operatore) {
		super();
		this.dataAccesso = dataAccesso;
		this.cognome = cognome;
		this.nome = nome;
		this.dataNascita = dataNascita;
		this.tipo = tipo;
		this.stato = stato;
		this.operatore = operatore;
		
	}
*/

	public Scheda(DatiSchedaListDTO dto){
		super();
		this.identificativo = dto.getIdentificativo();
		this.id=dto.getId();
		this.cognome=dto.getCognomeUtente();
		this.nome=dto.getNomeUtente();
		this.dataAccesso=dto.getDataAccesso();
		this.dataNascita=dto.getDataNascita();
		this.tipo=dto.getTipo();
		this.operatore=dto.getOperatore();
		this.ufficioId=dto.getUfficioId();
		this.ufficio=dto.getUfficio();
		this.puntoContatto=dto.getpContatto();
		this.dataModifica = dto.getDataModifica();
		this.dataInvio=dto.getDataInvio();
		this.dataRicezione=dto.getDataRicezione();
		this.nomeZonaInviante=dto.getNomeZonaInviante();
		this.privacy = dto.getDataPrivacy()!=null ? this.getMessaggioPrivacy(dto.getDataPrivacy()) : null;
		this.statoCS = dto.getStatoCS();
		this.origZonaSociale = dto.getNomeZonaInviante();
		this.cf = dto.getCfUtente();
		this.descOrganizzazione = dto.getDescOrganizzazione();
	}

	public boolean isRicevuta(){
		return (this.dataRicezione!=null);
	}
	
/*	public Scheda(SsScheda scheda, SsSchedaAccesso accesso) {
		if(scheda != null){
			id = scheda.getId();
			if(scheda.getTipo() != null){
				tipo = readTipoScheda(scheda.getTipo());
			}
		}
		if(accesso != null){
			dataAccesso = accesso.getData();
			operatore = accesso.getOperatore();
			setUfficio(accesso.getSsRelUffPcontOrg().getSsUfficio().getNome());
			setPuntoContatto(accesso.getSsRelUffPcontOrg().getSsPuntoContatto().getNome());
		}
		
	}

	public Scheda(SsScheda scheda) {
		id = scheda.getId();
		dataAccesso = scheda.getAccesso().getData();
		operatore = scheda.getAccesso().getOperatore();
		ufficio = scheda.getAccesso().getSsRelUffPcontOrg().getSsUfficio().getNome();
		tipo = readTipoScheda(scheda.getTipo());
		puntoContatto = scheda.getAccesso().getSsRelUffPcontOrg().getSsPuntoContatto().getNome();
	}
	
	public Scheda(SsScheda scheda, SsSchedaSegnalato segnalato) {
		id = scheda.getId();
		tipo = readTipoScheda(scheda.getTipo());
		
		if(scheda.getAccesso() != null){
			dataAccesso = scheda.getAccesso().getData();
			operatore = scheda.getAccesso().getOperatore();
			ufficio = scheda.getAccesso().getSsRelUffPcontOrg().getSsUfficio().getNome();
			puntoContatto = scheda.getAccesso().getSsRelUffPcontOrg().getSsPuntoContatto().getNome();
		}
	
		if(segnalato != null){
			dataNascita = segnalato.getAnagrafica().getData_nascita();
			nome = segnalato.getAnagrafica().getNome();
			cognome = segnalato.getAnagrafica().getCognome();
		}
	}*/
	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataAccesso() {
		return dataAccesso;
	}

	public void setDataAccesso(Date dataAccesso) {
		this.dataAccesso = dataAccesso;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getIntervento() {
		return tipo;
	}

	public void setIntervento(String intervento) {
		this.tipo = intervento;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getOperatore() {
		return operatore;
	}

	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}

	public String getUfficio() {
		return ufficio;
	}

	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}
	
	public boolean equals(Object scheda){
		return this.id.equals(((Scheda)scheda).id);
	}

	public String getPuntoContatto() {
		return puntoContatto;
	}

	public void setPuntoContatto(String puntoContatto) {
		this.puntoContatto = puntoContatto;
	}
	
	public String getDenominazione(){
		return (this.cognome!=null ? this.cognome : "")+" "+ (this.nome!=null ? this.nome : "");
	}


	public String getTipo() {
		return tipo;
	}


	public Date getDataModifica() {
		return dataModifica;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}


	public Long getUfficioId() {
		return ufficioId;
	}


	public void setUfficioId(Long ufficioId) {
		this.ufficioId = ufficioId;
	}


	public String getPrivacy() {
		return privacy;
	}


	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}


	public BigDecimal getIdentificativo() {
		return identificativo;
	}


	public void setIdentificativo(BigDecimal identificativo) {
		this.identificativo = identificativo;
	}


	public Date getDataInvio() {
		return dataInvio;
	}


	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}


	public String getNomeZonaInviante() {
		return nomeZonaInviante;
	}


	public void setNomeZonaInviante(String nomeZonaInviante) {
		this.nomeZonaInviante = nomeZonaInviante;
	}

	public Date getDataRicezione() {
		return dataRicezione;
	}

	public void setDataRicezione(Date dataRicezione) {
		this.dataRicezione = dataRicezione;
	}

	public String getStatoCS() {
		return statoCS;
	}

	public void setStatoCS(String statoCS) {
		this.statoCS = statoCS;
	}

	public String getOrigZonaSociale() {
		return origZonaSociale;
	}

	public void setOrigZonaSociale(String origZonaSociale) {
		this.origZonaSociale = origZonaSociale;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getDescOrganizzazione() {
		return descOrganizzazione;
	}

	public void setDescOrganizzazione(String descOrganizzazione) {
		this.descOrganizzazione = descOrganizzazione;
	}

}
