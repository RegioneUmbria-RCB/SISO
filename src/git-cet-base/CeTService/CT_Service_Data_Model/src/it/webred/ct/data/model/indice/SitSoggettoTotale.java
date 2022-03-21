package it.webred.ct.data.model.indice;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SIT_SOGGETTO_TOTALE database table.
 * 
 */
public class SitSoggettoTotale implements Serializable {
	private static final long serialVersionUID = 1L;

	private IndicePK id;
	
	private String codComuneNascita;

	private String codComuneRes;

	private String codProvinciaNascita;

	private String codProvinciaRes;

	private String codfisc;

	private String cognome;

	private String denominazione;

	private String descComuneNascita;

	private String descComuneRes;

	private String descProvinciaNascita;

	private String descProvinciaRes;

	private Date dtInizioVal;

	private Date dtNascita;

	//private Object info;

	private String nome;

	private String note;

	private String pi;

	private BigDecimal rating;

	private String relDescr;

	private String sesso;

	private String tipoPersona;

	private BigDecimal fkSoggetto;
	
	private BigDecimal validato;

	private String stato;
	
	private String anomalia;
	
	private String codiceSoggOrig;
	
	private BigDecimal attendibilita;
	
	private String idStorico;
	
	private Date dtFineVal;
	
	
	private String processId;
	private Date dtInizioDato;
	private Date dtFineDato;
	private Date dtExpDato;
	private String provenienza;
	private Date dataRegistrazione;
	
	private String field1;
	private String field2;
	private String field3;
	private String field4;
	private String field5;
	private String field6;
	private String field7;
	private String field8;
	private String field9;
	private String field10;
	private String field11;
	private String field12;
	private String field13;
	private String field14;
	private String field15;
	private String field16;
	private String field17;
	private String field18;
	private String field19;
	private String field20;
	private String field21;
	private String field22;
	private String field23;
	private String field24;
	
	private String flagRuntimeNonAgganciato;
	
	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}


	public Date getDtInizioDato() {
		return dtInizioDato;
	}

	public void setDtInizioDato(Date dtInizioDato) {
		this.dtInizioDato = dtInizioDato;
	}

	public Date getDtFineDato() {
		return dtFineDato;
	}

	public void setDtFineDato(Date dtFineDato) {
		this.dtFineDato = dtFineDato;
	}

	public Date getDtExpDato() {
		return dtExpDato;
	}

	public void setDtExpDato(Date dtExpDato) {
		this.dtExpDato = dtExpDato;
	}

	public String getProvenienza() {
		return provenienza;
	}

	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}

	public Date getDataRegistrazione() {
		return dataRegistrazione;
	}

	public void setDataRegistrazione(Date dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	public String getField3() {
		return field3;
	}

	public void setField3(String field3) {
		this.field3 = field3;
	}

	public String getField4() {
		return field4;
	}

	public void setField4(String field4) {
		this.field4 = field4;
	}

	public String getField5() {
		return field5;
	}

	public void setField5(String field5) {
		this.field5 = field5;
	}

	public String getField6() {
		return field6;
	}

	public void setField6(String field6) {
		this.field6 = field6;
	}

	public String getField7() {
		return field7;
	}

	public void setField7(String field7) {
		this.field7 = field7;
	}

	public String getField8() {
		return field8;
	}

	public void setField8(String field8) {
		this.field8 = field8;
	}

	public String getField9() {
		return field9;
	}

	public void setField9(String field9) {
		this.field9 = field9;
	}

	public String getField10() {
		return field10;
	}

	public void setField10(String field10) {
		this.field10 = field10;
	}

	public String getField11() {
		return field11;
	}

	public void setField11(String field11) {
		this.field11 = field11;
	}

	public String getField12() {
		return field12;
	}

	public void setField12(String field12) {
		this.field12 = field12;
	}

	public String getField13() {
		return field13;
	}

	public void setField13(String field13) {
		this.field13 = field13;
	}

	public String getField14() {
		return field14;
	}

	public void setField14(String field14) {
		this.field14 = field14;
	}

	public String getField15() {
		return field15;
	}

	public void setField15(String field15) {
		this.field15 = field15;
	}

	public String getField16() {
		return field16;
	}

	public void setField16(String field16) {
		this.field16 = field16;
	}

	public String getField17() {
		return field17;
	}

	public void setField17(String field17) {
		this.field17 = field17;
	}

	public String getField18() {
		return field18;
	}

	public void setField18(String field18) {
		this.field18 = field18;
	}

	public String getField19() {
		return field19;
	}

	public void setField19(String field19) {
		this.field19 = field19;
	}

	public String getField20() {
		return field20;
	}

	public void setField20(String field20) {
		this.field20 = field20;
	}

	public String getField21() {
		return field21;
	}

	public void setField21(String field21) {
		this.field21 = field21;
	}

	public String getField22() {
		return field22;
	}

	public void setField22(String field22) {
		this.field22 = field22;
	}

	public String getField23() {
		return field23;
	}

	public void setField23(String field23) {
		this.field23 = field23;
	}

	public String getField24() {
		return field24;
	}

	public void setField24(String field24) {
		this.field24 = field24;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}
	
	public BigDecimal getValidato() {
		return validato;
	}

	public void setValidato(BigDecimal validato) {
		this.validato = validato;
	}

	public BigDecimal getFkSoggetto() {
		return fkSoggetto;
	}

	public void setFkSoggetto(BigDecimal fkSoggetto) {
		this.fkSoggetto = fkSoggetto;
	}

	public SitSoggettoTotale() {
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

	public Date getDtInizioVal() {
		return this.dtInizioVal;
	}

	public void setDtInizioVal(Date dtInizioVal) {
		this.dtInizioVal = dtInizioVal;
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

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPi() {
		return this.pi;
	}

	public void setPi(String pi) {
		this.pi = pi;
	}

	public BigDecimal getRating() {
		return this.rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}

	public String getRelDescr() {
		return this.relDescr;
	}

	public void setRelDescr(String relDescr) {
		this.relDescr = relDescr;
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

	public IndicePK getId() {
		return id;
	}

	public void setId(IndicePK id) {
		this.id = id;
	}

	public String getAnomalia() {
		return anomalia;
	}

	public void setAnomalia(String anomalia) {
		this.anomalia = anomalia;
	}

	public String getCodiceSoggOrig() {
		return codiceSoggOrig;
	}

	public void setCodiceSoggOrig(String codiceSoggOrig) {
		this.codiceSoggOrig = codiceSoggOrig;
	}

	public BigDecimal getAttendibilita() {
		return attendibilita;
	}

	public void setAttendibilita(BigDecimal attendibilita) {
		this.attendibilita = attendibilita;
	}

	public String getIdStorico() {
		return idStorico;
	}

	public void setIdStorico(String idStorico) {
		this.idStorico = idStorico;
	}

	public Date getDtFineVal() {
		return dtFineVal;
	}

	public void setDtFineVal(Date dtFineVal) {
		this.dtFineVal = dtFineVal;
	}

	public String getFlagRuntimeNonAgganciato() {
		return flagRuntimeNonAgganciato;
	}

	public void setFlagRuntimeNonAgganciato(String flagRuntimeNonAgganciato) {
		this.flagRuntimeNonAgganciato = flagRuntimeNonAgganciato;
	}
	
}