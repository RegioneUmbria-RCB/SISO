package it.webred.rulengine.dwh.table;

import java.math.BigDecimal;

public class SitSclIstituti extends TabellaDwh {

	private String codIstituto;
	private String nomeIstituto;
	private String tipoIstituto;
	private BigDecimal nrIscritti;
	private String codIndirizzo;
	private String codComune;
	private String comune;
	private String toponimo;
	private String via;
	private String civico;
	private String lettera;

	@Override
	public String getValueForCtrHash() {
		try {
			return codIstituto
					+ nomeIstituto
					+ tipoIstituto
					+ (nrIscritti != null? nrIscritti.toString():"")
					+ codIndirizzo
					+ codComune
					+ comune
					+ toponimo
					+ via
					+ civico
					+ lettera;
		} catch (Exception e) {
			return null;
		}

	}

	public String getCodIstituto() {
		return codIstituto;
	}

	public void setCodIstituto(String codIstituto) {
		this.codIstituto = codIstituto;
	}

	public String getNomeIstituto() {
		return nomeIstituto;
	}

	public void setNomeIstituto(String nomeIstituto) {
		this.nomeIstituto = nomeIstituto;
	}

	public String getTipoIstituto() {
		return tipoIstituto;
	}

	public void setTipoIstituto(String tipoIstituto) {
		this.tipoIstituto = tipoIstituto;
	}

	public BigDecimal getNrIscritti() {
		return nrIscritti;
	}

	public void setNrIscritti(BigDecimal nrIscritti) {
		this.nrIscritti = nrIscritti;
	}

	public String getCodIndirizzo() {
		return codIndirizzo;
	}

	public void setCodIndirizzo(String codIndirizzo) {
		this.codIndirizzo = codIndirizzo;
	}

	public String getCodComune() {
		return codComune;
	}

	public void setCodComune(String codComune) {
		this.codComune = codComune;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getToponimo() {
		return toponimo;
	}

	public void setToponimo(String toponimo) {
		this.toponimo = toponimo;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getLettera() {
		return lettera;
	}

	public void setLettera(String lettera) {
		this.lettera = lettera;
	}
	
}
