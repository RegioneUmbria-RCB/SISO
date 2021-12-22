package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the CS_A_IMPORT_EXPORT database table.
 * 
 */
@Entity
@Table(name="CS_A_IMPORT_EXPORT")
@NamedQuery(name="CsAImportExport.findAll", query="SELECT e FROM CsAImportExport e")
public class CsAImportExport implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name="TIPO_EVENTO")
	private String tipoEvento;
	
	@Column(name="MITTENTE")
	private String mittente;
	
	@Column(name="DESTINATARIO")
	private String destinatario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_EVENTO")
	private Date dataEvento;
	
	@Column(name="NOME")
	private String nome;
	
	@Column(name="COGNOME")
	private String cognome;
	
	@Column(name="CODICE_FISCALE")
	private String codiceFiscale;
	
	@Column(name="ARCHIVIATO")
	private boolean archiviato;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_CANCELLAZIONE")
	private Date dataCancellazione;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name="CARTELLA_SOCIALE")
	private String cartellaSociale;
	
	@Id
	@SequenceGenerator(name="CS_A_IMPORT_EXPORT_ID_GENERATOR", sequenceName="SQ_IMPORT_EXPORT",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_A_IMPORT_EXPORT_ID_GENERATOR")
	@Column(name="ID")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="OPERATORE_SETTORE_ID")
	private CsOOperatoreSettore operatoreSettore;
	

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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public boolean isArchiviato() {
		return archiviato;
	}

	public void setArchiviato(boolean archiviato) {
		this.archiviato = archiviato;
	}

	public Date getDataCancellazione() {
		return dataCancellazione;
	}

	public void setDataCancellazione(Date dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCartellaSociale() {
		return cartellaSociale;
	}

	public void setCartellaSociale(String cartellaSociale) {
		this.cartellaSociale = cartellaSociale;
	}

	public CsOOperatoreSettore getOperatoreSettore() {
		return operatoreSettore;
	}

	public void setOperatoreSettore(CsOOperatoreSettore operatoreSettore) {
		this.operatoreSettore = operatoreSettore;
	}
}
