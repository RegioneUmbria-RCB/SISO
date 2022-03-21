package it.webred.ss.data.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the SS_DIARIO database table.
 * 
 */
@Entity
@Table(name="SS_DIARIO")
@NamedQuery(name="SsDiario.findAll", query="SELECT c FROM SsDiario c")
public class SsDiario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SS_DIARIO_ID_GENERATOR", sequenceName="SQ_SS_DIARIO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SS_DIARIO_ID_GENERATOR")
	private Long id;
	
	private String nota;
	private Date data;
	private String autore;
	
	@ManyToOne
	@JoinColumn(name="ENTE")
	private SsOOrganizzazione ente;
	
	private Boolean pubblica;
	
	//bi-directional many-to-one association to SsAnagrafica
	@ManyToOne
	@JoinColumn(name="SOGGETTO")
	private SsAnagrafica soggetto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public Boolean getPubblica() {
		return pubblica;
	}

	public void setPubblica(Boolean pubblica) {
		this.pubblica = pubblica;
	}

	public SsAnagrafica getSoggetto() {
		return soggetto;
	}

	public void setSoggetto(SsAnagrafica soggetto) {
		this.soggetto = soggetto;
	}
	
	public SsOOrganizzazione getEnte() {
		return ente;
	}

	public void setEnte(SsOOrganizzazione ente) {
		this.ente = ente;
	}

	public String getFormattedData(){
		return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(data);
	}
	
}
