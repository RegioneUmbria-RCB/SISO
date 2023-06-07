package it.webred.cs.data.model;

import it.webred.cs.data.model.view.CsVistaPai;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CS_PAI_MAST_SOGG")
public class CsPaiMastSogg implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CS_PAI_MAST_SOGG_ID_GENERATOR", sequenceName = "SQ_PAI_MAST_SOGG", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CS_PAI_MAST_SOGG_ID_GENERATOR")
	private Long id;

	@Column(name = "CF")
	private String cf;

	@Column(name = "DIARIO_ID")
	private Long diarioId;

	@Column(name = "COGNOME")
	private String cognome;

	@Column(name = "NOME")
	private String nome;

	@Column(name = "CITTADINANZA")
	private String cittadinanza;

	@Column(name = "FLAG_INTESTATARIO")
	private Boolean riferimento;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INS")
	private Date dtIns;

	@Column(name = "USER_INS")
	private String userIns;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_MOD")
	private Date dtMod;

	@Column(name = "USER_MOD")
	private String userMod;

	@Column(name = "COMUNE_RESIDENZA")
	private String comuneResidenza;

	@Column(name = "NAZIONE_RESIDENZA")
	private String nazioneResidenza;

	@Column(name = "VIA_RESIDENZA")
	private String viaResidenza;

	@Column(name = "CITTADINANZA_2")
	private String cittadinanza2;

	@Column(name = "SESSO")
	private String sesso;

	@Column(name="ANNO_NASCITA")
	private Integer annoNascita;
	
	 @Column(name="COMUNE_NASCITA")
	 private String comuneNascita; //JSON
	 
	 @Column(name="NAZIONE_NASCITA")  						
	 private String nazioneNascita;
	
	 @Column(name="FLAG_NAZ_RES_NON_DEFINITA")
	 private Boolean nazioneResidenzaNonDefinita;
		
	@MapsId("diarioId")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "DIARIO_ID", referencedColumnName = "DIARIO_ID")
	private CsDPai pai;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DIARIO_ID", referencedColumnName = "DIARIO_ID" , updatable = false, insertable = false)
	private CsVistaPai vistaPai;

	@ManyToOne
	@JoinColumn(name = "CASO_ID", updatable = false, insertable = false)
	private CsACaso caso;
	
	@Column(name="CASO_ID")
	private Long casoId;

	public CsACaso getCaso() {
		return caso;
	}

	public void setCaso(CsACaso caso) {
		this.caso = caso;
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

	public String getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public Boolean getRiferimento() {
		return riferimento;
	}

	public void setRiferimento(Boolean riferimento) {
		this.riferimento = riferimento;
	}

	public Date getDtIns() {
		return dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public String getUserIns() {
		return userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public Date getDtMod() {
		return dtMod;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	public String getUserMod() {
		return userMod;
	}

	public void setUserMod(String userMod) {
		this.userMod = userMod;
	}

	public String getComuneResidenza() {
		return comuneResidenza;
	}

	public void setComuneResidenza(String comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}

	public String getNazioneResidenza() {
		return nazioneResidenza;
	}

	public void setNazioneResidenza(String nazioneResidenza) {
		this.nazioneResidenza = nazioneResidenza;
	}

	public String getViaResidenza() {
		return viaResidenza;
	}

	public void setViaResidenza(String viaResidenza) {
		this.viaResidenza = viaResidenza;
	}

	public String getCittadinanza2() {
		return cittadinanza2;
	}

	public void setCittadinanza2(String cittadinanza2) {
		this.cittadinanza2 = cittadinanza2;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public CsDPai getPai() {
		return pai;
	}

	public void setPai(CsDPai pai) {
		this.pai = pai;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public Long getDiarioId() {
		return diarioId;
	}

	public void setDiarioId(Long diarioId) {
		this.diarioId = diarioId;
	}

	public Integer getAnnoNascita() {
		return annoNascita;
	}

	public void setAnnoNascita(Integer annoNascita) {
		this.annoNascita = annoNascita;
	}

	public Long getCasoId() {
		return casoId;
	}

	public void setCasoId(Long casoId) {
		this.casoId = casoId;
	}

	public String getDenominazione(){
		String denominazione = "";
		if (cognome!=null || nome!=null)
			denominazione = cognome + " " + nome;
		return denominazione.trim();
	}

	public String getComuneNascita() {
		return comuneNascita;
	}

	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public String getNazioneNascita() {
		return nazioneNascita;
	}

	public void setNazioneNascita(String nazioneNascita) {
		this.nazioneNascita = nazioneNascita;
	}

	public Boolean getNazioneResidenzaNonDefinita() {
		return nazioneResidenzaNonDefinita;
	}

	public void setNazioneResidenzaNonDefinita(Boolean nazioneResidenzaNonDefinita) {
		this.nazioneResidenzaNonDefinita = nazioneResidenzaNonDefinita;
	}

}
