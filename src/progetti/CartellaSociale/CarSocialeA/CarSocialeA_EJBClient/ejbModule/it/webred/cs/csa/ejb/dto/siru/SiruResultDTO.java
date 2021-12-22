package it.webred.cs.csa.ejb.dto.siru;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import it.webred.cs.data.model.CsExtraFseSiru;
import it.webred.cs.data.model.CsIInterventoPrFseSiru;

public class SiruResultDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private SiruOutputDTO siru;
	private List<String> errori = new ArrayList<String>();
	public SiruOutputDTO getSiru() {
		return siru;
	}
	public List<String> getErrori() {
		return errori;
	}
	public void setSiru(SiruOutputDTO siru) {
		this.siru = siru;
	}
	public void setErrori(List<String> errori) {
		this.errori = errori;
	}
	
	public CsIInterventoPrFseSiru getSiruInterventi(){
		CsIInterventoPrFseSiru jpa = new CsIInterventoPrFseSiru();
		if(errori.isEmpty() && siru!=null){
			jpa.setCittadinanza(siru.getCittadinanza());
			jpa.setCodAtecoAnno(siru.getCodAtecoAnno());
			jpa.setCodVulnerabilePa(siru.getCodVulnerabilePa());
			jpa.setComuneAziendaId(siru.getComuneAziendaId());
			jpa.setComuneDomId(siru.getComuneDomId());
			jpa.setComuneNascitaId(siru.getComuneNascitaId());
			jpa.setComuneResId(siru.getComuneResId());
			jpa.setCondMercatoIngresso(siru.getCondMercatoIngresso());
			jpa.setDataNascita(siru.getDataNascita());
			jpa.setDimensioneAziendaId(siru.getDimensioneAziendaId());
			jpa.setDurataRicerca(siru.getDurataRicerca());
			jpa.setFlagResidenza(siru.getFlagResidenza());
			jpa.setFrmGiuridicaCod(siru.getFrmGiuridicaCod());
			jpa.setNazioneNascitaId(siru.getNazioneNascitaId());
			jpa.setSesso(siru.getSesso());
			jpa.setStatoPartecipante(siru.getStatoPartecipante());
			jpa.setTipologiaLavoroId(siru.getTipologiaLavoroId());
			jpa.setTipoOrarioLavoroId(siru.getTipoOrarioLavoroId());
			jpa.setTitoloStudio(siru.getTitoloStudio());
		}
		return jpa;
	}
	
	public CsExtraFseSiru getSiruExtra(){
		CsExtraFseSiru jpa = new CsExtraFseSiru();
		if(errori.isEmpty() && siru!=null){			
			jpa.setCittadinanza(siru.getCittadinanza());
			jpa.setCodAtecoAnno(siru.getCodAtecoAnno());
			jpa.setCodVulnerabilePa(siru.getCodVulnerabilePa());
			jpa.setComuneAziendaId(siru.getComuneAziendaId());
			jpa.setComuneDomId(siru.getComuneDomId());
			jpa.setComuneNascitaId(siru.getComuneNascitaId());
			jpa.setComuneResId(siru.getComuneResId());
			jpa.setCondMercatoIngresso(siru.getCondMercatoIngresso());
			jpa.setDataNascita(siru.getDataNascita());
			jpa.setDimensioneAziendaId(siru.getDimensioneAziendaId());
			jpa.setDurataRicerca(siru.getDurataRicerca());
			jpa.setFlagResidenza(siru.getFlagResidenza());
			jpa.setFrmGiuridicaCod(siru.getFrmGiuridicaCod());
			jpa.setNazioneNascitaId(siru.getNazioneNascitaId());
			jpa.setSesso(siru.getSesso());
			jpa.setStatoPartecipante(siru.getStatoPartecipante());
			jpa.setTipologiaLavoroId(siru.getTipologiaLavoroId());
			jpa.setTipoOrarioLavoroId(siru.getTipoOrarioLavoroId());
			jpa.setTitoloStudio(siru.getTitoloStudio());
		}
		return jpa;
	}
	
	
}
