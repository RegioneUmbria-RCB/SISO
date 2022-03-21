package it.webred.ct.data.model.indice;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the SIT_SOGGETTO_UNICO database table.
 * 
 */
public class SitSoggettoUnico implements SitUnico,Serializable {
	private static final long serialVersionUID = 1L;

	private long idSoggetto;

	private String codComuneNascita;

	private String codComuneRes;

	private String codProvinciaNascita;

	private String codProvinciaRes;

	private String codfisc;

	private String cognome;

	private String ctrlUtil;

	private String denominazione;

	private String descComuneNascita;

	private String descComuneRes;

	private String descProvinciaNascita;

	private String descProvinciaRes;

	private Date dtIns;

	private Date dtNascita;

	private String nome;

	private String pi;

	private String sesso;

	private String tipoPersona;

	private BigDecimal validato;
		
	private BigDecimal rating;
			
	//private String codiceSoggetto;
	
	private String codiceSoggettoOrig;

	private BigDecimal fonteRif;
	
	
    public BigDecimal getFonteRif() {
		return fonteRif;
	}

	public void setFonteRif(BigDecimal fonteRif) {
		this.fonteRif = fonteRif;
	}

	public SitSoggettoUnico() {
    }

	public long getIdSoggetto() {
		return this.idSoggetto;
	}

	public void setIdSoggetto(long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public String getCodComuneNascita() {
		return this.codComuneNascita;
	}

	public void setCodComuneNascita(String codComuneNascita) {
		this.codComuneNascita = codComuneNascita;
	}

	public String getCodComuneRes() {
		return this.codComuneRes;
	}

	public void setCodComuneRes(String codComuneRes) {
		this.codComuneRes = codComuneRes;
	}

	public String getCodProvinciaNascita() {
		return this.codProvinciaNascita;
	}

	public void setCodProvinciaNascita(String codProvinciaNascita) {
		this.codProvinciaNascita = codProvinciaNascita;
	}

	public String getCodProvinciaRes() {
		return this.codProvinciaRes;
	}

	public void setCodProvinciaRes(String codProvinciaRes) {
		this.codProvinciaRes = codProvinciaRes;
	}

	public String getCodfisc() {
		return this.codfisc;
	}

	public void setCodfisc(String codfisc) {
		this.codfisc = codfisc;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCtrlUtil() {
		return this.ctrlUtil;
	}

	public void setCtrlUtil(String ctrlUtil) {
		this.ctrlUtil = ctrlUtil;
	}

	public String getDenominazione() {
		return this.denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getDescComuneNascita() {
		return this.descComuneNascita;
	}

	public void setDescComuneNascita(String descComuneNascita) {
		this.descComuneNascita = descComuneNascita;
	}

	public String getDescComuneRes() {
		return this.descComuneRes;
	}

	public void setDescComuneRes(String descComuneRes) {
		this.descComuneRes = descComuneRes;
	}

	public String getDescProvinciaNascita() {
		return this.descProvinciaNascita;
	}

	public void setDescProvinciaNascita(String descProvinciaNascita) {
		this.descProvinciaNascita = descProvinciaNascita;
	}

	public String getDescProvinciaRes() {
		return this.descProvinciaRes;
	}

	public void setDescProvinciaRes(String descProvinciaRes) {
		this.descProvinciaRes = descProvinciaRes;
	}

	public Date getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public Date getDtNascita() {
		return this.dtNascita;
	}

	public void setDtNascita(Date dtNascita) {
		this.dtNascita = dtNascita;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPi() {
		return this.pi;
	}

	public void setPi(String pi) {
		this.pi = pi;
	}

	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getTipoPersona() {
		return this.tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public BigDecimal getValidato() {
		return this.validato;
	}

	public void setValidato(BigDecimal validato) {
		this.validato = validato;
	}
	
	public long getId() {
		return getIdSoggetto();
	}

	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}

	/*public String getCodiceSoggetto() {
		return codiceSoggetto;
	}

	public void setCodiceSoggetto(String codiceSoggetto) {
		this.codiceSoggetto = codiceSoggetto;
	}*/

	public String getCodiceSoggettoOrig() {
		return codiceSoggettoOrig;
	}

	public void setCodiceSoggettoOrig(String codiceSoggettoOrig) {
		this.codiceSoggettoOrig = codiceSoggettoOrig;
	}
	
	public String getDenominazioneExt() {
		if(this.denominazione != null)
			return this.denominazione;
		else return this.cognome + " " + this.nome;
	}
	
}