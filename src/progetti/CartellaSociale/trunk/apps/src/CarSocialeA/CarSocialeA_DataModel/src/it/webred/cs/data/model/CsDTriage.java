package it.webred.cs.data.model;

import it.webred.cs.data.base.ICsDRelazioneChild;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the CS_D_TRIAGE database table.
 * 
 */
@Entity
@Table(name="CS_D_TRIAGE")
public class CsDTriage implements ICsDRelazioneChild {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="RELAZIONE_ID")
	private Long idRelazione;
	
	@Column(name="MORBILITA")
	private String morbilita;
	
	@Column(name="ALIMENTAZIONE")
	private String alimentazione;
	
	@Column(name="ALVO_DIURESI")
	private String alvoDiuresi;
	
	@Column(name="MOBILITA")
	private String mobilita;
	
	@Column(name="IGIENE_PERSONALE")
	private String igienePersonale;
	
	@Column(name="STATO_MENTALE")
	private String statoMentale;
	
	@Column(name="CON_CHI_VIVE")
	private String conChiVive;
	
	@Column(name="ASSISTENZA_DIRETTA")
	private String assistenzaDiretta;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_INS")
	private Date dataInserimento = new Date();
	
//	@OneToOne
//	private CsDRelazione csDRelazione;
	
	
	
	public Long getIdRelazione() {
		return idRelazione;
	}

	public void setIdRelazione(Long idRelazione) {
		this.idRelazione = idRelazione;
	}

	public String getMorbilita() {
		return morbilita;
	}

	public void setMorbilita(String morbilita) {
		this.morbilita = morbilita;
	}

	public String getAlimentazione() {
		return alimentazione;
	}

	public void setAlimentazione(String alimentazione) {
		this.alimentazione = alimentazione;
	}

	public String getAlvoDiuresi() {
		return alvoDiuresi;
	}

	public void setAlvoDiuresi(String alvoDiuresi) {
		this.alvoDiuresi = alvoDiuresi;
	}

	public String getMobilita() {
		return mobilita;
	}

	public void setMobilita(String mobilita) {
		this.mobilita = mobilita;
	}

	public String getIgienePersonale() {
		return igienePersonale;
	}

	public void setIgienePersonale(String igienePersonale) {
		this.igienePersonale = igienePersonale;
	}

	public String getStatoMentale() {
		return statoMentale;
	}

	public void setStatoMentale(String statoMentale) {
		this.statoMentale = statoMentale;
	}

	public String getConChiVive() {
		return conChiVive;
	}

	public void setConChiVive(String conChiVive) {
		this.conChiVive = conChiVive;
	}

	public String getAssistenzaDiretta() {
		return assistenzaDiretta;
	}

	public void setAssistenzaDiretta(String assistenzaDiretta) {
		this.assistenzaDiretta = assistenzaDiretta;
	}

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public CsDRelazione getCsDRelazione() {
		return null;
	}

	public void setCsDRelazione(CsDRelazione csDRelazione) {
		
	}

	@Override
	public Long getRelazioneId() {
		return this.idRelazione;
	}

	@Override
	public void setRelazioneId(Long diarioId) {
		this.idRelazione = diarioId;
	}

	

}
