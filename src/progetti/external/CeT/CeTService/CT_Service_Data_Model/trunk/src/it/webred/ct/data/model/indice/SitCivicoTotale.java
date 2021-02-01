package it.webred.ct.data.model.indice;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;


public class SitCivicoTotale implements Serializable {
	private static final long serialVersionUID = 1L;

	private IndicePK id;
	
	private String civLiv1;

	private String civLiv2;

	private String civLiv3;

	private BigDecimal fkVia;

	private String idOrigViaTotale;

	private String note;

	private BigDecimal rating;

	private String relDescr;

	private BigDecimal fkCivico;
	
	private BigDecimal validato;
	
	private String stato;
	
	private String anomalia;
	
	private String codiceCivOrig;
	
	private String idStorico;
	
	private Date dataFineVal;
	
	private Object civicoComp;
	
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

	public BigDecimal getValidato() {
		return validato;
	}

	public void setValidato(BigDecimal validato) {
		this.validato = validato;
	}

	public BigDecimal getFkCivico() {
		return fkCivico;
	}

	public void setFkCivico(BigDecimal fkCivico) {
		this.fkCivico = fkCivico;
	}

	public SitCivicoTotale() {
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


	public BigDecimal getFkVia() {
		return this.fkVia;
	}

	public void setFkVia(BigDecimal fkVia) {
		this.fkVia = fkVia;
	}

	public String getIdOrigViaTotale() {
		return this.idOrigViaTotale;
	}

	public void setIdOrigViaTotale(String idOrigViaTotale) {
		this.idOrigViaTotale = idOrigViaTotale;
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

	public String getCodiceCivOrig() {
		return codiceCivOrig;
	}

	public void setCodiceCivOrig(String codiceCivOrig) {
		this.codiceCivOrig = codiceCivOrig;
	}

	public String getIdStorico() {
		return idStorico;
	}

	public void setIdStorico(String idStorico) {
		this.idStorico = idStorico;
	}

	public Date getDataFineVal() {
		return dataFineVal;
	}

	public void setDataFineVal(Date dataFineVal) {
		this.dataFineVal = dataFineVal;
	}

	public Object getCivicoComp() {
		return civicoComp;
	}

	public void setCivicoComp(Object civicoComp) {
		this.civicoComp = civicoComp;
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