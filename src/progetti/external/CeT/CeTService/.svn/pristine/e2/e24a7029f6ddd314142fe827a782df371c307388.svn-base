package it.webred.ct.data.access.basic.ruolo.tarsu.dto;

import it.webred.ct.data.access.basic.versamenti.bp.dto.VersamentoTarBpDTO;
import it.webred.ct.data.model.ruolo.tarsu.SitRuoloTarsu;
import it.webred.ct.data.model.ruolo.tarsu.SitRuoloTarsuImm;
import it.webred.ct.data.model.ruolo.tarsu.SitRuoloTarsuRata;
import it.webred.ct.data.model.ruolo.tarsu.SitRuoloTarsuSt;
import it.webred.ct.data.model.ruolo.tarsu.SitRuoloTarsuStSg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RuoloTarsuDTO implements Serializable{
	
	private SitRuoloTarsu ruolo;
	private List<SitRuoloTarsuImm> immobili;
	private List<RataDTO> rate;
	private List<SitRuoloTarsuSt> listaSt;
	private List<SitRuoloTarsuStSg> listaStSg;
	private List<String> listaNomiPdf;
	
	private List<VersamentoTarBpDTO> listaVersBp;
	
	public SitRuoloTarsu getRuolo() {
		return ruolo;
	}
	public void setRuolo(SitRuoloTarsu ruolo) {
		this.ruolo = ruolo;
	}
	public List<SitRuoloTarsuImm> getImmobili() {
		return immobili;
	}
	public void setImmobili(List<SitRuoloTarsuImm> immobili) {
		this.immobili = immobili;
	}
	public List<SitRuoloTarsuSt> getListaSt() {
		return listaSt;
	}
	public void setListaSt(List<SitRuoloTarsuSt> listaSt) {
		this.listaSt = listaSt;
	}
	public List<SitRuoloTarsuStSg> getListaStSg() {
		return listaStSg;
	}
	public void setListaStSg(List<SitRuoloTarsuStSg> listaStSg) {
		this.listaStSg = listaStSg;
	}
	public List<String> getListaNomiPdf() {
		return listaNomiPdf;
	}
	public void setListaNomiPdf(List<String> listaNomiPdf) {
		this.listaNomiPdf = listaNomiPdf;
	}
	public List<RataDTO> getRate() {
		return rate;
	}
	public void setRate(List<RataDTO> rate) {
		this.rate = rate;
		this.listaVersBp = new ArrayList<VersamentoTarBpDTO>();
		for(RataDTO r : rate){
			if(r.getVersamenti()!=null)
				this.listaVersBp.addAll(r.getVersamenti());
		}
	}
	public List<VersamentoTarBpDTO> getListaVersBp() {
		return listaVersBp;
	}
}
