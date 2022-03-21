package it.webred.ct.data.model.redditi;

import it.webred.ct.data.model.annotation.IndiceEntity;
import it.webred.ct.data.model.annotation.IndiceKey;
import it.webred.ct.data.model.locazioni.LocazioneAPK;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the RED_DATI_ANAGRAFICI database table.
 * 
 */
@Entity
@Table(name="RED_DATI_ANAGRAFICI")
@IndiceEntity(sorgente="11")
@IdClass(RedDatiAnagraficiPK.class)
public class RedDatiAnagrafici implements Serializable {
	private static final long serialVersionUID = 1L;

	//@EmbeddedId
	//private RedDatiAnagraficiPK id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@IndiceKey(pos="1")
	private String ideTelematico;;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@IndiceKey(pos="2")
	private String codiceFiscaleDic;
	
	
	@Column(name="ANNO_IMPOSTA")
	private String annoImposta;

	private String cognome;

	@Column(name="COMUNE_NASCITA")
	private String comuneNascita;

	@Column(name="DATA_NASCITA")
	private String dataNascita;

	private String denominazione;

	@Column(name="FLAG_PERSONA")
	private String flagPersona;

	@Column(name="NATURA_GIURIDICA")
	private BigDecimal naturaGiuridica;

	private String nome;

	private BigDecimal onlus;

	private String sesso;

	@Column(name="SETTORE_ONLUS")
	private BigDecimal settoreOnlus;

	private BigDecimal situazione;

	private BigDecimal stato;

    @Transient
	private String  desComuneNascita;
		
    public RedDatiAnagrafici() {
    }

	public String getAnnoImposta() {
		return this.annoImposta;
	}

	public void setAnnoImposta(String annoImposta) {
		this.annoImposta = annoImposta;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getComuneNascita() {
		return this.comuneNascita;
	}

	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public String getDataNascita() {
		return this.dataNascita;
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getDenominazione() {
		return this.denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getFlagPersona() {
		return this.flagPersona;
	}

	public void setFlagPersona(String flagPersona) {
		this.flagPersona = flagPersona;
	}

	public BigDecimal getNaturaGiuridica() {
		return this.naturaGiuridica;
	}

	public void setNaturaGiuridica(BigDecimal naturaGiuridica) {
		this.naturaGiuridica = naturaGiuridica;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getOnlus() {
		return this.onlus;
	}

	public void setOnlus(BigDecimal onlus) {
		this.onlus = onlus;
	}

	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public BigDecimal getSettoreOnlus() {
		return this.settoreOnlus;
	}

	public void setSettoreOnlus(BigDecimal settoreOnlus) {
		this.settoreOnlus = settoreOnlus;
	}

	public BigDecimal getSituazione() {
		return this.situazione;
	}

	public void setSituazione(BigDecimal situazione) {
		this.situazione = situazione;
	}

	public BigDecimal getStato() {
		return this.stato;
	}

	public void setStato(BigDecimal stato) {
		this.stato = stato;
	}

	public String getIdeTelematico() {
		return ideTelematico;
	}

	public void setIdeTelematico(String ideTelematico) {
		this.ideTelematico = ideTelematico;
	}

	public String getCodiceFiscaleDic() {
		return codiceFiscaleDic;
	}

	public void setCodiceFiscaleDic(String codiceFiscaleDic) {
		this.codiceFiscaleDic = codiceFiscaleDic;
	}

	public String getDesComuneNascita() {
		return desComuneNascita;
	}

	public void setDesComuneNascita(String desComuneNascita) {
		this.desComuneNascita = desComuneNascita;
	}

	

}