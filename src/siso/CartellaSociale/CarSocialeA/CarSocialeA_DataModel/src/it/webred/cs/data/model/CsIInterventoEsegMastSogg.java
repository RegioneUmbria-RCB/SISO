package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="CS_I_INTERVENTO_ESEG_MAST_SOGG")
public class CsIInterventoEsegMastSogg implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CsIInterventoEsegMastSoggPK id;
	
	@ManyToOne
	@JoinColumn(name="CASO_ID")
	private CsACaso caso;

	@Column
	private String cognome;

	@Column
	private String nome;
	
	@Column 
	private String cittadinanza;
	
	@Column(name="ANNO_NASCITA")
	private Integer annoNascita;
	
	@Column(name="COMUNE_NASCITA")
	private String comuneNascita; //JSON
 
	@Column(name="NAZIONE_NASCITA")
	private String nazioneNascita; 
	
	@Column(name="FLAG_RIFERIMENTO")
	private Boolean riferimento;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_INS")
	private Date dtIns;
	
	@Column(name="USER_INS")
	private String userIns;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_MOD")
	private Date dtMod;
	
	@Column(name="USER_MOD")
	private String userMod;
	
	
	//SISO-962
	 @Column(name="COMUNE_RESIDENZA")
	 private String comuneResidenza; //JSON
	 @Column(name="NAZIONE_RESIDENZA")
	 private String nazioneResidenza; 

	@Column(name="VIA_RESIDENZA")
	 private String viaResidenza;
	 @Column(name="CITTADINANZA_2")
	 private String secondaCittadinanza;

	 
	 @Column(name="SESSO")
	 private String sesso;
	 
	 @Column(name="DATI_VALIDI")
	 private Boolean datiValidi;
	
	@MapsId("masterId")
    @ManyToOne (optional=false, fetch = FetchType.LAZY)
    @JoinColumn(name="INT_ESEG_MAST_ID", referencedColumnName="id")
	private CsIInterventoEsegMast master;
	

	@Column(name="FLAG_NAZ_RES_NON_DEFINITA")
	private Boolean nazioneResidenzaNonDefinita;
		
	 public String getNazioneResidenza() {
		return nazioneResidenza;
	}

	public void setNazioneResidenza(String nazioneResidenza) {
		this.nazioneResidenza = nazioneResidenza;
	} 
	 
    public String getComuneResidenza() {
		return comuneResidenza;
	}

	public void setComuneResidenza(String comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}

	public String getViaResidenza() {
		return viaResidenza;
	}

	public void setViaResidenza(String viaResidenza) {
		this.viaResidenza = viaResidenza;
	}

	public String getSecondaCittadinanza() {
		return secondaCittadinanza;
	}

	public void setSecondaCittadinanza(String secondaCittadinanza) {
		this.secondaCittadinanza = secondaCittadinanza;
	}
	
	public CsIInterventoEsegMastSogg() {
		id = new CsIInterventoEsegMastSoggPK();
	}

	public CsACaso getCaso() {
		return caso;
	}

	public void setCaso(CsACaso caso) {
		this.caso = caso;
	}

	public Date getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public String getCognome() {
		return cognome;
	}

	public String getNome() {
		return nome;
	}

	public String getUserIns() {
		return userIns;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public String getCf() {
		return id.getCf();
	}

	public CsIInterventoEsegMast getMaster() {
		return master;
	}

	public void setCf(String cf) {
		id.setCf(cf);;
	}

	public void setMaster(CsIInterventoEsegMast master) {
		this.master = master;
	}

	public Boolean getRiferimento() {
		return riferimento;
	}

	public void setRiferimento(Boolean riferimento) {
		this.riferimento = riferimento;
	}

	public String getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public CsIInterventoEsegMastSoggPK getId() {
		return id;
	}

	public void setId(CsIInterventoEsegMastSoggPK id) {
		this.id = id;
	}

	public Integer getAnnoNascita() {
		return annoNascita;
	}

	public void setAnnoNascita(Integer annoNascita) {
		this.annoNascita = annoNascita;
	}

	public Date getDtMod() {
		return dtMod;
	}

	public String getUserMod() {
		return userMod;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	public void setUserMod(String userMod) {
		this.userMod = userMod;
	}
    //SISO-1138 
	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	//SISO-1138 FINE

	public Boolean getDatiValidi() {
		return datiValidi;
	}

	public void setDatiValidi(Boolean datiValidi) {
		this.datiValidi = datiValidi;
	}
	//SISO-1021

	public Boolean getNazioneResidenzaNonDefinita() {
		return nazioneResidenzaNonDefinita;
	}

	public void setNazioneResidenzaNonDefinita(Boolean nazioneResidenzaNonDefinita) {
		this.nazioneResidenzaNonDefinita = nazioneResidenzaNonDefinita;
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
}