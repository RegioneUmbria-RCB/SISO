package it.webred.ss.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the SS_SCHEDA_ACCESSO database table.
 * 
 */
@Entity
@Table(name="SS_SCHEDA_ACCESSO")
@NamedQuery(name="SsSchedaAccesso.findAll", query="SELECT c FROM SsSchedaAccesso c")
public class SsSchedaAccesso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SS_SCHEDA_ACCESSO_ID_GENERATOR", sequenceName="SQ_SS_SCHEDA_ACCESSO", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SS_SCHEDA_ACCESSO_ID_GENERATOR")
	private Long id;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA")//opzionale: se non presente assume che abbia lo stesso nome nella tabella
	private Date data;
	
	private String modalita;
	
	private String operatore;
	
	private String interlocutore;
	
	private String descrizione;
	
	private String motivo;
	
	@Column(name="MOTIVO_DESC")
	private String motivoDesc;

	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="REL_UPO_UFFICIO_ID", referencedColumnName="UFFICIO_ID"),
		@JoinColumn(name="REL_UPO_PUNTO_CONTATTO_ID", referencedColumnName="PUNTO_CONTATTO_ID"),
		@JoinColumn(name="REL_UPO_ORGANIZZAZIONE_ID", referencedColumnName="ORGANIZZAZIONE_ID")
		})
	private SsRelUffPcontOrg ssRelUffPcontOrg;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getModalita() {
		return modalita;
	}

	public void setModalita(String modalita) {
		this.modalita = modalita;
	}

	public String getOperatore() {
		return operatore;
	}

	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}

	public String getInterlocutore() {
		return interlocutore;
	}

	public void setInterlocutore(String interlocutore) {
		this.interlocutore = interlocutore;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getMotivoDesc() {
		return motivoDesc;
	}

	public void setMotivoDesc(String motivoDesc) {
		this.motivoDesc = motivoDesc;
	}

	public SsRelUffPcontOrg getSsRelUffPcontOrg() {
		return ssRelUffPcontOrg;
	}

	public void setSsRelUffPcontOrg(SsRelUffPcontOrg ssRelUffPcontOrg) {
		this.ssRelUffPcontOrg = ssRelUffPcontOrg;
	}
}