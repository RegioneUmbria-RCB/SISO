package it.webred.ct.data.model.anagrafe;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SIT_D_CIVICO_V database table.
 * 
 */
@Entity
@Table(name="SIT_D_CIVICO_V")
public class SitDCivicoV implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	private BigDecimal cap;

	@Column(name="CIV_LIV1")
	private String civLiv1;

	@Column(name="CIV_LIV2")
	private String civLiv2;

	@Column(name="CIV_LIV3")
	private String civLiv3;

	@Column(name="CTR_HASH")
	private String ctrHash;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_EXP_DATO")
	private Date dtExpDato;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_FINE_DATO")
	private Date dtFineDato;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_FINE_VAL")
	private Date dtFineVal;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_INIZIO_DATO")
	private Date dtInizioDato;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_INIZIO_VAL")
	private Date dtInizioVal;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_INS")
	private Date dtIns;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_MOD")
	private Date dtMod;

	@Column(name="FK_ENTE_SORGENTE")
	private BigDecimal fkEnteSorgente;

	@Column(name="FLAG_DT_VAL_DATO")
	private BigDecimal flagDtValDato;

	@Column(name="ID_EXT")
	private String idExt;

	@Column(name="ID_EXT_D_FRAZIONE")
	private String idExtDFrazione;

	@Column(name="ID_EXT_D_SEZIONE_ELETTORALE")
	private String idExtDSezioneElettorale;

	@Column(name="ID_EXT_D_VIA")
	private String idExtDVia;

	@Column(name="ID_ORIG")
	private String idOrig;

    public SitDCivicoV() {
    }

	public BigDecimal getCap() {
		return this.cap;
	}

	public void setCap(BigDecimal cap) {
		this.cap = cap;
	}

	public String getCivLiv1() {
		return this.civLiv1;
	}

	public void setCivLiv1(String civLiv1) {
		this.civLiv1 = civLiv1;
	}

	public String getCivLiv2() {
		return this.civLiv2;
	}

	public void setCivLiv2(String civLiv2) {
		this.civLiv2 = civLiv2;
	}

	public String getCivLiv3() {
		return this.civLiv3;
	}

	public void setCivLiv3(String civLiv3) {
		this.civLiv3 = civLiv3;
	}

	public String getCtrHash() {
		return this.ctrHash;
	}

	public void setCtrHash(String ctrHash) {
		this.ctrHash = ctrHash;
	}

	public Date getDtExpDato() {
		return this.dtExpDato;
	}

	public void setDtExpDato(Date dtExpDato) {
		this.dtExpDato = dtExpDato;
	}

	public Date getDtFineDato() {
		return this.dtFineDato;
	}

	public void setDtFineDato(Date dtFineDato) {
		this.dtFineDato = dtFineDato;
	}

	public Date getDtFineVal() {
		return this.dtFineVal;
	}

	public void setDtFineVal(Date dtFineVal) {
		this.dtFineVal = dtFineVal;
	}

	public Date getDtInizioDato() {
		return this.dtInizioDato;
	}

	public void setDtInizioDato(Date dtInizioDato) {
		this.dtInizioDato = dtInizioDato;
	}

	public Date getDtInizioVal() {
		return this.dtInizioVal;
	}

	public void setDtInizioVal(Date dtInizioVal) {
		this.dtInizioVal = dtInizioVal;
	}

	public Date getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public Date getDtMod() {
		return this.dtMod;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	public BigDecimal getFkEnteSorgente() {
		return this.fkEnteSorgente;
	}

	public void setFkEnteSorgente(BigDecimal fkEnteSorgente) {
		this.fkEnteSorgente = fkEnteSorgente;
	}

	public BigDecimal getFlagDtValDato() {
		return this.flagDtValDato;
	}

	public void setFlagDtValDato(BigDecimal flagDtValDato) {
		this.flagDtValDato = flagDtValDato;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdExt() {
		return this.idExt;
	}

	public void setIdExt(String idExt) {
		this.idExt = idExt;
	}

	public String getIdExtDFrazione() {
		return this.idExtDFrazione;
	}

	public void setIdExtDFrazione(String idExtDFrazione) {
		this.idExtDFrazione = idExtDFrazione;
	}

	public String getIdExtDSezioneElettorale() {
		return this.idExtDSezioneElettorale;
	}

	public void setIdExtDSezioneElettorale(String idExtDSezioneElettorale) {
		this.idExtDSezioneElettorale = idExtDSezioneElettorale;
	}

	public String getIdExtDVia() {
		return this.idExtDVia;
	}

	public void setIdExtDVia(String idExtDVia) {
		this.idExtDVia = idExtDVia;
	}

	public String getIdOrig() {
		return this.idOrig;
	}

	public void setIdOrig(String idOrig) {
		this.idOrig = idOrig;
	}

	public String getCivicoFormatted() {
		String civicoFormatted = (getCivLiv1() == null )? "": " " + removeLeadingZero(getCivLiv1().trim());
		civicoFormatted += (getCivLiv2() == null )? "": " " + removeLeadingZero(getCivLiv2().trim());
		civicoFormatted += (getCivLiv3() == null )? "": " " + removeLeadingZero(getCivLiv3().trim());	
		
		return civicoFormatted.trim();
	}
	
	private static String removeLeadingZero(String str) {
		if (str==null || str.length() == 0)
			return str; 
		
		String retVal=new String(str);
		int i=0;
		while (i<str.length()) {
			if(str.charAt(i)=='0' && str.length() >i+1 ) {
				retVal=str.substring(i+1);
			}else
				break;
			i++;
		}
		return retVal;
	
	}
	
}