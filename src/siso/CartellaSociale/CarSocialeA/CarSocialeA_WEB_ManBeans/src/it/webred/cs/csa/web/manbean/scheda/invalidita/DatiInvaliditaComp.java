package it.webred.cs.csa.web.manbean.scheda.invalidita;

import it.webred.cs.csa.web.manbean.scheda.SchedaValiditaCompUtils;
import it.webred.cs.jsf.interfaces.IDatiInvalidita;
import it.webred.cs.data.model.CsTbSinaRisposta;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.component.html.HtmlSelectManyCheckbox;
import javax.faces.event.AjaxBehaviorEvent;

public class DatiInvaliditaComp extends SchedaValiditaCompUtils implements IDatiInvalidita{
		
	private boolean invalidita;
	private boolean invaliditaInCorso;
	private Date dataInvalidita;
	private Date dataInvaliditaScadenza;
	private String note;
	private boolean certificazioneH;
	private Date dataCertificazione;
	private Date dataCertificazioneScadenza;
	private boolean inValutazione;
	private boolean indennitaFrequenza;
	private BigDecimal invaliditaPerc;
	private boolean accompagnamento;
	private boolean legge104;
	
	private Icd9D1Bean icd9D1Bean = new Icd9D1Bean();
	private Icd10D1Bean Icd10D1Bean = new Icd10D1Bean();
	private Icd9D2Bean icd9D2Bean = new Icd9D2Bean();
	private Icd10D2Bean Icd10D2Bean = new Icd10D2Bean();

	//Dati Invalidita Sina
	private Long idFonteDerivazioneValutaz;
	private List<CsTbSinaRisposta> lstFonteDerivazioneValutaz;
	private List<CsTbSinaRisposta> lstInvCiv = new ArrayList<CsTbSinaRisposta>();
	private List<String> lstInvCivScelte;
	private boolean disabilitaCheckPercentuale;
	
	@Override
	public boolean isInvalidita() {
		return invalidita;
	}

	public void setInvalidita(boolean invalidita) {
		this.invalidita = invalidita;
	}

	public boolean isInValutazione() {
		return inValutazione;
	}

	public void setInValutazione(boolean inValutazione) {
		this.inValutazione = inValutazione;
	}
	
	public boolean isIndennitaFrequenza() {
		return indennitaFrequenza;
	}

	public void setIndennitaFrequenza(boolean indennitaFrequenza) {
		this.indennitaFrequenza = indennitaFrequenza;
	}
	
	@Override
	public boolean isInvaliditaInCorso() {
		return invaliditaInCorso;
	}

	public void setInvaliditaInCorso(boolean invaliditaInCorso) {
		this.invaliditaInCorso = invaliditaInCorso;
	}

	@Override
	public Date getDataInvalidita() {
		return dataInvalidita;
	}

	public void setDataInvalidita(Date dataInvalidita) {
		this.dataInvalidita = dataInvalidita;
	}

	@Override
	public Date getDataInvaliditaScadenza() {
		return dataInvaliditaScadenza;
	}

	public void setDataInvaliditaScadenza(Date dataInvaliditaScadenza) {
		this.dataInvaliditaScadenza = dataInvaliditaScadenza;
	}

	@Override
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public boolean isCertificazioneH() {
		return certificazioneH;
	}

	public void setCertificazioneH(boolean certificazioneH) {
		this.certificazioneH = certificazioneH;
	}

	@Override
	public Date getDataCertificazione() {
		return dataCertificazione;
	}

	public void setDataCertificazione(Date dataCertificazione) {
		this.dataCertificazione = dataCertificazione;
	}

	@Override
	public Date getDataCertificazioneScadenza() {
		return dataCertificazioneScadenza;
	}

	public void setDataCertificazioneScadenza(Date dataCertificazioneScadenza) {
		this.dataCertificazioneScadenza = dataCertificazioneScadenza;
	}

	@Override
	public BigDecimal getInvaliditaPerc() {
		return invaliditaPerc;
	}

	public void setInvaliditaPerc(BigDecimal invaliditaPerc) {
		this.invaliditaPerc = invaliditaPerc;
	}

	@Override
	public boolean isAccompagnamento() {
		return accompagnamento;
	}

	public void setAccompagnamento(boolean accompagnamento) {
		this.accompagnamento = accompagnamento;
	}

	@Override
	public boolean isLegge104() {
		return legge104;
	}

	public void setLegge104(boolean legge104) {
		this.legge104 = legge104;
	}

	
	public boolean isDisabilitaCheckPercentuale() {
		return disabilitaCheckPercentuale;
	}

	public void setDisabilitaCheckPercentuale(boolean disabilitaCheckPercentuale) {
		this.disabilitaCheckPercentuale = disabilitaCheckPercentuale;
	}

	@Override
	public Icd9D1Bean getIcd9D1Bean() {
		return icd9D1Bean;
	}

	public void setIcd9D1Bean(Icd9D1Bean icd9d1Bean) {
		icd9D1Bean = icd9d1Bean;
	}

	@Override
	public Icd10D1Bean getIcd10D1Bean() {
		return Icd10D1Bean;
	}

	public void setIcd10D1Bean(Icd10D1Bean icd10d1Bean) {
		Icd10D1Bean = icd10d1Bean;
	}

	@Override
	public Icd9D2Bean getIcd9D2Bean() {
		return icd9D2Bean;
	}

	public void setIcd9D2Bean(Icd9D2Bean icd9d2Bean) {
		icd9D2Bean = icd9d2Bean;
	}

	@Override
	public Icd10D2Bean getIcd10D2Bean() {
		return Icd10D2Bean;
	}

	public void setIcd10D2Bean(Icd10D2Bean icd10d2Bean) {
		Icd10D2Bean = icd10d2Bean;
	}
	//SISO-1278
	public Long getIdFonteDerivazioneValutaz() {
		return idFonteDerivazioneValutaz;
	}

	public void setIdFonteDerivazioneValutaz(Long idFonteDerivazioneValutaz) {
		this.idFonteDerivazioneValutaz = idFonteDerivazioneValutaz;
	}

	@Override
	public List<CsTbSinaRisposta> getlstFonteDerivazioneValutaz() {
		return lstFonteDerivazioneValutaz;
	}

	public void setlstFonteDerivazioneValutaz(List<CsTbSinaRisposta> lstFonteDerivazioneValutaz) {
		this.lstFonteDerivazioneValutaz = lstFonteDerivazioneValutaz;
	}

	@Override
	public List<CsTbSinaRisposta> getlstInvCiv() {
		return lstInvCiv;
	}

	public void setlstInvCiv(List<CsTbSinaRisposta> lstInvCiv) {
		this.lstInvCiv = lstInvCiv;
	}

	public List<String> getLstInvCivScelte() {
		return lstInvCivScelte;
	}

	public void setLstInvCivScelte(List<String> lstInvCivScelte) {
		this.lstInvCivScelte = lstInvCivScelte;
	}



	
	
}
