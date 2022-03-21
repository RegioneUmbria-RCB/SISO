package it.webred.ct.data.access.basic.catasto.dto;

import it.webred.ct.data.model.catasto.*;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.math.BigDecimal;
import java.util.*;

public class DettaglioCatastaleImmobileDTO extends CeTBaseObject {
	
	private static final long serialVersionUID = 1L;
	private String sezione;
	private String descrizioneCategoria;
	private Sitiuiu immobile;
	private List<DataVariazioneImmobileDTO> altreDateValidita;
	private List<SoggettoDTO> soggetti;
	private List<IndirizzoDTO> localizzazioneSIT;
	private List<IndirizzoDTO> localizzazioneCatasto;
	private List<SitiediVani> dettaglioVaniComma340;
	private List<PlanimetriaComma340DTO> planimetrieComma340;
	private Boolean nonANorma;    //Secondo le elaborazioni del comma340
	private List<SitRepTarsu> repTarsu;		//report completo TARSU
	
	public String getSezione() {
		return sezione;
	}
	public void setSezione(String sezione) {
		this.sezione = sezione;
	}
	
	public void setDescrizioneCategoria(String descrizioneCategoria) {
		this.descrizioneCategoria = descrizioneCategoria;
	}
	public String getDescrizioneCategoria() {
		return descrizioneCategoria;
	}
	public List<SoggettoDTO> getSoggetti() {
		return soggetti;
	}
	public List<DataVariazioneImmobileDTO> getAltreDateValidita() {
		return altreDateValidita;
	}
	public void setAltreDateValidita(
			List<DataVariazioneImmobileDTO> altreDateValidita) {
		this.altreDateValidita = altreDateValidita;
	}
	public void setSoggetti(List<SoggettoDTO> soggetti) {
		this.soggetti = soggetti;
	}
	
	public List<SitiediVani> getDettaglioVaniComma340() {
		return dettaglioVaniComma340;
	}
	public void setDettaglioVaniComma340(List<SitiediVani> dettaglioVaniComma340) {
		this.dettaglioVaniComma340 = dettaglioVaniComma340;
	}
	
	public Sitiuiu getImmobile() {
		return immobile;
	}
	public void setImmobile(Sitiuiu immobile) {
		this.immobile = immobile;
	}
	public List<IndirizzoDTO> getLocalizzazioneSIT() {
		return localizzazioneSIT;
	}
	public void setLocalizzazioneSIT(List<IndirizzoDTO> localizzazioneSIT) {
		this.localizzazioneSIT = localizzazioneSIT;
	}
	public List<IndirizzoDTO> getLocalizzazioneCatasto() {
		return localizzazioneCatasto;
	}
	public void setLocalizzazioneCatasto(List<IndirizzoDTO> localizzazioneCatasto) {
		this.localizzazioneCatasto = localizzazioneCatasto;
	}
	
	public void setPlanimetrieComma340(List<PlanimetriaComma340DTO> planimetrieComma340) {
		this.planimetrieComma340 = planimetrieComma340;
	}
	public List<PlanimetriaComma340DTO> getPlanimetrieComma340() {
		return planimetrieComma340;
	}
	public List<SitRepTarsu> getRepTarsu() {
		return repTarsu;
	}
	public void setRepTarsu(List<SitRepTarsu> repTarsu) {
		this.repTarsu = repTarsu;
	}
	
	public Integer getRepTarsuLocatari() {
		for (SitRepTarsu repTarsuRec : getRepTarsu()) {
			if (repTarsuRec.getLocatari() != null) {
				return new Integer(repTarsuRec.getLocatari().intValue());
			}
		}
		return null;
	}
	
	public Integer getRepTarsuUiuCiv() {
		for (SitRepTarsu repTarsuRec : getRepTarsu()) {
			if (repTarsuRec.getUiuCiv() != null) {
				return new Integer(repTarsuRec.getUiuCiv().intValue());
			}			
		}
		return null;
	}
	
	public Integer getRepTarsuAltriResCiv() {
		for (SitRepTarsu repTarsuRec : getRepTarsu()) {
			if (repTarsuRec.getAltriResCiv() != null) {
				return new Integer(repTarsuRec.getAltriResCiv().intValue());
			}			
		}
		return null;
	}
	
	public Boolean getRepTarsuLocatarioTarsu() {
		for (SitRepTarsu repTarsuRec : getRepTarsu()) {
			if (repTarsuRec.getLocatarioTarsu() != null) {
				return new Boolean(new BigDecimal(1).equals(repTarsuRec.getLocatarioTarsu()));
			}
		}
		return null;
	}
	
	public Integer getRepTarsuAltriResTarsu() {
		for (SitRepTarsu repTarsuRec : getRepTarsu()) {
			if (repTarsuRec.getAltriResTarsu() != null) {
				return new Integer(repTarsuRec.getAltriResTarsu().intValue());
			}
		}
		return null;
	}
	
	public Integer getRepTarsuLocatariTarsu() {
		for (SitRepTarsu repTarsuRec : getRepTarsu()) {
			if (repTarsuRec.getLocatariTarsu() != null) {
				return new Integer(repTarsuRec.getLocatariTarsu().intValue());
			}			
		}
		return null;
	}
	
	public void setNonANorma(Boolean nonANorma) {
		this.nonANorma = nonANorma;
	}
	public Boolean getNonANorma() {
		return nonANorma;
	}

}
