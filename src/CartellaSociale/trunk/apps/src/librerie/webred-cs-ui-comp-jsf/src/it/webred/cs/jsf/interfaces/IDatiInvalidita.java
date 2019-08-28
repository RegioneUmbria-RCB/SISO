package it.webred.cs.jsf.interfaces;

import it.webred.jsf.interfaces.IDoppioCmbBox;

import java.math.BigDecimal;
import java.util.Date;

public interface IDatiInvalidita {
		
	public IDoppioCmbBox getIcd9D1Bean();
	
	public IDoppioCmbBox getIcd10D1Bean();
	
	public IDoppioCmbBox getIcd9D2Bean();
	
	public IDoppioCmbBox getIcd10D2Bean();
	
	public boolean isInvalidita();
	
	public boolean isInvaliditaInCorso();
	
	public Date getDataInvalidita();
	
	public Date getDataInvaliditaScadenza();
	
	public String getNote();
		
	public boolean isCertificazioneH();
	
	public Date getDataCertificazione();
	
	public Date getDataCertificazioneScadenza();
	
	public BigDecimal getInvaliditaPerc();
	
	public boolean isIndennitaFrequenza();
	
	public boolean isAccompagnamento();
	
	public boolean isLegge104();

}
