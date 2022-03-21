package it.webred.ct.data.model.indice;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SIT_VIA_TOTALE database table.
 * 
 */
public class SitViaTotale implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private IndicePK id;
	
	private Date dtFineVal;

	private BigDecimal fkVia;

	private String idStorico;

	private String indirizzo;

	private String note;

	private BigDecimal rating;

	private String relDescr;

	private String sedime;
	
	private BigDecimal validato;

	private String stato;
	
	private String anomalia;
	
	private String codiceViaOrig;
	
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
	
	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}
	
	public SitViaTotale() {
    }

	
	public Date getDtFineVal() {
		return this.dtFineVal;
	}

	public void setDtFineVal(Date dtFineVal) {
		this.dtFineVal = dtFineVal;
	}

	public BigDecimal getFkVia() {
		return this.fkVia;
	}

	public void setFkVia(BigDecimal fkVia) {
		this.fkVia = fkVia;
	}

	public String getIdStorico() {
		return this.idStorico;
	}

	public void setIdStorico(String idStorico) {
		this.idStorico = idStorico;
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public String getSedime() {
		return this.sedime;
	}

	public void setSedime(String sedime) {
		this.sedime = sedime;
	}
	
    public BigDecimal getValidato() {
		return validato;
	}

	public void setValidato(BigDecimal validato) {
		this.validato = validato;
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

	public String getCodiceViaOrig() {
		return codiceViaOrig;
	}

	public void setCodiceViaOrig(String codiceViaOrig) {
		this.codiceViaOrig = codiceViaOrig;
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
	
}