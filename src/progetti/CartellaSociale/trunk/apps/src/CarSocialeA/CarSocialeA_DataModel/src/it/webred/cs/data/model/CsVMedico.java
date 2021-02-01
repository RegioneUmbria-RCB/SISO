package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="V_MEDICI")
@NamedQuery(name="CsVMedico.findAll", query="SELECT c FROM CsVMedico c")
public class CsVMedico implements Serializable {
	private static final long serialVersionUID = 1L;
/*
	@Id
	@SequenceGenerator(name="V_MEDICI_ID_GENERATOR", sequenceName="SQ_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="V_MEDICI_ID_GENERATOR")
	private long id;
	*/
	
	@Id
	@Column(name="CODICE_REGIONALE")
	private String codiceRegionale;
	@Column(name="CODICE_FISCALE")
	private String codiceFiscale;
	private String cognome;
	private String nome;
	private String sesso;
	@Column(name="DATA_NASCITA")
	private Date dataNascita;
	@Column(name="CODICE_COMUNE_NASCITA")
	private String codiceComuneNascita;
	@Column(name="CODICE_REGIONE")
	private String codiceRegione;
	@Column(name="CODICE_ASL")
	private String codiceASL;
	@Column(name="CODICE_DISTRETTO")
	private String codiceDistretto;
	@Column(name="CODICE_SPECIALIZZAZIONE")
	private String codiceSpecializzazione;
	@Column(name="DATA_INIZIO_RAPPORTO")
	private Date dataInizioRapporto;
	@Column(name="DATA_FINE_RAPPORTO")
	private Date dataFineRapporto;
	private Long massimale;
	@Column(name="DATA_INSERIMENTO")
	private Date dataInserimento;
	@Column(name="DATA_AGGIORNAMENTO")
	private Date dataAggiornamento;
	public String getCodiceRegionale() {
		return codiceRegionale;
	}
	public void setCodiceRegionale(String codiceRegionale) {
		this.codiceRegionale = codiceRegionale;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
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
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public String getCodiceComuneNascita() {
		return codiceComuneNascita;
	}
	public void setCodiceComuneNascita(String codiceComuneNascita) {
		this.codiceComuneNascita = codiceComuneNascita;
	}
	public String getCodiceRegione() {
		return codiceRegione;
	}
	public void setCodiceRegione(String codiceRegione) {
		this.codiceRegione = codiceRegione;
	}
	public String getCodiceASL() {
		return codiceASL;
	}
	public void setCodiceASL(String codiceASL) {
		this.codiceASL = codiceASL;
	}
	public String getCodiceDistretto() {
		return codiceDistretto;
	}
	public void setCodiceDistretto(String codiceDistretto) {
		this.codiceDistretto = codiceDistretto;
	}
	public String getCodiceSpecializzazione() {
		return codiceSpecializzazione;
	}
	public void setCodiceSpecializzazione(String codiceSpecializzazione) {
		this.codiceSpecializzazione = codiceSpecializzazione;
	}
	public Date getDataInizioRapporto() {
		return dataInizioRapporto;
	}
	public void setDataInizioRapporto(Date dataInizioRapporto) {
		this.dataInizioRapporto = dataInizioRapporto;
	}
	public Date getDataFineRapporto() {
		return dataFineRapporto;
	}
	public void setDataFineRapporto(Date dataFineRapporto) {
		this.dataFineRapporto = dataFineRapporto;
	}
	public Long getMassimale() {
		return massimale;
	}
	public void setMassimale(Long massimale) {
		this.massimale = massimale;
	}
	public Date getDataInserimento() {
		return dataInserimento;
	}
	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}
	public Date getDataAggiornamento() {
		return dataAggiornamento;
	}
	public void setDataAggiornamento(Date dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}
	
	
	
	
	
}

