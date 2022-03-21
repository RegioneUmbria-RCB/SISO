package it.webred.rulengine.dwh.table;

import java.math.BigDecimal;
import it.webred.rulengine.dwh.def.DataDwh;

public class SitRttDettBollette extends TabellaDwh {

	private String codBolletta;
	private DataDwh dtIniServizio;
	private DataDwh dtFinServizio;
	private String desOggetto;
	private String ubicazione;
	private String categoria;
	private String codVoce;
	private String desVoce;
	private String valore;

	@Override
	public String getValueForCtrHash() {
		try {
			return codBolletta
					+ (dtIniServizio != null? dtIniServizio.getDataFormattata(): "")
					+ (dtFinServizio != null? dtFinServizio.getDataFormattata(): "")
					+ desOggetto
					+ ubicazione
					+ categoria
					+ codVoce
					+ desVoce
					+ valore;
		} catch (Exception e) {
			return null;
		}

	}

	public String getCodBolletta() {
		return codBolletta;
	}

	public void setCodBolletta(String codBolletta) {
		this.codBolletta = codBolletta;
	}

	public DataDwh getDtIniServizio() {
		return dtIniServizio;
	}

	public void setDtIniServizio(DataDwh dtIniServizio) {
		this.dtIniServizio = dtIniServizio;
	}

	public DataDwh getDtFinServizio() {
		return dtFinServizio;
	}

	public void setDtFinServizio(DataDwh dtFinServizio) {
		this.dtFinServizio = dtFinServizio;
	}

	public String getDesOggetto() {
		return desOggetto;
	}

	public void setDesOggetto(String desOggetto) {
		this.desOggetto = desOggetto;
	}

	public String getUbicazione() {
		return ubicazione;
	}

	public void setUbicazione(String ubicazione) {
		this.ubicazione = ubicazione;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getCodVoce() {
		return codVoce;
	}

	public void setCodVoce(String codVoce) {
		this.codVoce = codVoce;
	}

	public String getDesVoce() {
		return desVoce;
	}

	public void setDesVoce(String desVoce) {
		this.desVoce = desVoce;
	}

	public String getValore() {
		return valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

}
