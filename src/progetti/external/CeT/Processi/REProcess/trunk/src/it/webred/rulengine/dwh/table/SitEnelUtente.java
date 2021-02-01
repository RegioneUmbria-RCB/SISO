package it.webred.rulengine.dwh.table;

import java.util.Date;

import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.TipoXml;


public class SitEnelUtente extends TabellaDwh
{

	private String codiceFiscale  ;
	private String denominazione	 ;
	private String sesso ;
	private DataDwh dataNascita;
	private String comuneNascitaSede	 ;
	private String provinciaNascitaSede	 ;
	private String comuneDomFiscale	 ;
	private String provinciaDomFiscale	 ;
	private String qualificaTitolare;	
	
	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	@Override
	public String getValueForCtrHash() {
		try {
			return 	 codiceFiscale+ denominazione + 
			sesso + dataNascita.getDataFormattata() +
			comuneNascitaSede + provinciaNascitaSede + comuneDomFiscale +
			provinciaDomFiscale + qualificaTitolare;
		} catch (Exception e) {
			return null;
		}
		
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}


	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}


	public DataDwh getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(DataDwh dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getComuneNascitaSede() {
		return comuneNascitaSede;
	}

	public void setComuneNascitaSede(String comuneNascitaSede) {
		this.comuneNascitaSede = comuneNascitaSede;
	}

	public String getProvinciaNascitaSede() {
		return provinciaNascitaSede;
	}

	public void setProvinciaNascitaSede(String provinciaNascitaSede) {
		this.provinciaNascitaSede = provinciaNascitaSede;
	}

	public String getComuneDomFiscale() {
		return comuneDomFiscale;
	}

	public void setComuneDomFiscale(String comuneDomFiscale) {
		this.comuneDomFiscale = comuneDomFiscale;
	}

	public String getProvinciaDomFiscale() {
		return provinciaDomFiscale;
	}

	public void setProvinciaDomFiscale(String provinciaDomFiscale) {
		this.provinciaDomFiscale = provinciaDomFiscale;
	}

	public String getQualificaTitolare() {
		return qualificaTitolare;
	}

	public void setQualificaTitolare(String qualificaTitolare) {
		this.qualificaTitolare = qualificaTitolare;
	}

	
	
	








}
