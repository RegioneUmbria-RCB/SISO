package it.webred.cs.csa.web.manbean.scheda.contributi;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.data.model.CsTbBuono;
import it.webred.cs.data.model.CsTbEsenzioneRiduzione;
import it.webred.cs.jsf.interfaces.IContributi;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

@ManagedBean
@SessionScoped
public class ContributiBean extends CsUiCompBaseBean implements IContributi{

	private AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
	
	private boolean primaErogazione;
	private boolean interventoPre;
	private boolean rinnovo;
	private boolean richRespinta;
	private boolean sospensione;
	
	private Long idBuono;
	private Long idEsenzione;
	private Long idRiduzione;
	
	private List<SelectItem> lstBuoni;
	private List<SelectItem> lstEsenzioniRiduzioni;

	@Override
	public boolean isPrimaErogazione() {
		return primaErogazione;
	}

	public void setPrimaErogazione(boolean primaErogazione) {
		this.primaErogazione = primaErogazione;
	}

	@Override
	public boolean isInterventoPre() {
		return interventoPre;
	}

	public void setInterventoPre(boolean interventoPre) {
		this.interventoPre = interventoPre;
	}

	@Override
	public boolean isRinnovo() {
		return rinnovo;
	}

	public void setRinnovo(boolean rinnovo) {
		this.rinnovo = rinnovo;
	}

	@Override
	public boolean isRichRespinta() {
		return richRespinta;
	}

	public void setRichRespinta(boolean richRespinta) {
		this.richRespinta = richRespinta;
	}

	@Override
	public boolean isSospensione() {
		return sospensione;
	}

	public void setSospensione(boolean sospensione) {
		this.sospensione = sospensione;
	}

	@Override
	public Long getIdBuono() {
		return idBuono;
	}

	public void setIdBuono(Long idBuono) {
		this.idBuono = idBuono;
	}

	@Override
	public Long getIdEsenzione() {
		return idEsenzione;
	}

	public void setIdEsenzione(Long idEsenzione) {
		this.idEsenzione = idEsenzione;
	}

	@Override
	public Long getIdRiduzione() {
		return idRiduzione;
	}

	public void setIdRiduzione(Long idRiduzione) {
		this.idRiduzione = idRiduzione;
	}

	@Override
	public List<SelectItem> getLstEsenzioniRiduzioni() {
		
		if(lstEsenzioniRiduzioni == null){
			lstEsenzioniRiduzioni = new ArrayList<SelectItem>();
			lstEsenzioniRiduzioni.add(new SelectItem(null, "- seleziona -"));
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<CsTbEsenzioneRiduzione> lst = confService.getEsenzioniRiduzioni(bo);
			if (lst != null) {
				for (CsTbEsenzioneRiduzione obj : lst) {
					lstEsenzioniRiduzioni.add(new SelectItem(obj.getId(), obj.getDescrizione()));
				}
			}		
		}
		
		return lstEsenzioniRiduzioni;
	}

	public void setLstEsenzioniRiduzioni(List<SelectItem> lstEsenzioniRiduzioni) {
		this.lstEsenzioniRiduzioni = lstEsenzioniRiduzioni;
	}

	public void setLstBuoni(List<SelectItem> lstBuoni) {
		this.lstBuoni = lstBuoni;
	}

	@Override
	public List<SelectItem> getLstBuoni() {
		
		if(lstBuoni == null){
			lstBuoni = new ArrayList<SelectItem>();
			lstBuoni.add(new SelectItem(null, "- seleziona -"));
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<CsTbBuono> lst = confService.getBuoni(bo);
			if (lst != null) {
				for (CsTbBuono obj : lst) {
					lstBuoni.add(new SelectItem(obj.getId(), obj.getDescrizione()));
				}
			}		
		}
		
		return lstBuoni;
	}
	
}
