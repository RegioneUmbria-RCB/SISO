package it.webred.cs.csa.web.manbean.scheda.tribunale;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.web.manbean.scheda.SchedaValiditaCompUtils;
import it.webred.cs.data.model.CsTbMacroSegnal;
import it.webred.cs.data.model.CsTbMicroSegnal;
import it.webred.cs.data.model.CsTbMotivoSegnal;
import it.webred.cs.jsf.bean.ValiditaCompBaseBean;
import it.webred.cs.jsf.interfaces.IDatiTribunale;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

public class DatiTribunaleComp extends SchedaValiditaCompUtils implements IDatiTribunale {

	private AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
	
	private boolean TMCivile;
	private boolean TMAmministrativo;
	private boolean penaleMinorile;
	private boolean TO;
	private boolean NIS;
	private boolean procuraMinorile;
	private Long idMacroSegnalazione;
	private Long idMicroSegnalazione;
	private Long idMotivoSegnalazione;
	private Date primoContattoAG;
	private String numeroProtocollo;	
	
	private List<SelectItem> lstMacroSegnalazioni;
	private List<SelectItem> lstMicroSegnalazioni;
	private List<SelectItem> lstMotiviSegnalazioni;
	
	@Override
	public boolean isTMCivile() {
		return TMCivile;
	}
	
	public void setTMCivile(boolean tMCivile) {
		TMCivile = tMCivile;
	}
	
	@Override
	public boolean isTMAmministrativo() {
		return TMAmministrativo;
	}
	
	public void setTMAmministrativo(boolean tMAmministrativo) {
		TMAmministrativo = tMAmministrativo;
	}
	
	@Override
	public boolean isPenaleMinorile() {
		return penaleMinorile;
	}
	
	public void setPenaleMinorile(boolean penaleMinorile) {
		this.penaleMinorile = penaleMinorile;
	}
	
	@Override
	public boolean isTO() {
		return TO;
	}
	
	public void setTO(boolean tO) {
		TO = tO;
	}
	
	@Override
	public boolean isNIS() {
		return NIS;
	}
	
	public void setNIS(boolean nIS) {
		NIS = nIS;
	}
	
	@Override
	public boolean isProcuraMinorile() {
		return procuraMinorile;
	}
	
	public void setProcuraMinorile(boolean procuraMinorile) {
		this.procuraMinorile = procuraMinorile;
	}
	
	@Override
	public Long getIdMacroSegnalazione() {
		return idMacroSegnalazione;
	}
	
	public void setIdMacroSegnalazione(Long idMacroSegnalazione) {
		this.idMacroSegnalazione = idMacroSegnalazione;
	}
	
	@Override
	public Long getIdMicroSegnalazione() {
		return idMicroSegnalazione;
	}
	
	public void setIdMicroSegnalazione(Long idMicroSegnalazione) {
		this.idMicroSegnalazione = idMicroSegnalazione;
	}
	
	@Override
	public Long getIdMotivoSegnalazione() {
		return idMotivoSegnalazione;
	}
	
	public void setIdMotivoSegnalazione(Long idMotivoSegnalazione) {
		this.idMotivoSegnalazione = idMotivoSegnalazione;
	}
	
	@Override
	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}
	
	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	@Override
	public List<SelectItem> getLstMacroSegnalazioni() {
		
		if(lstMacroSegnalazioni == null){
			lstMacroSegnalazioni = new ArrayList<SelectItem>();
			lstMacroSegnalazioni.add(new SelectItem(null, "- seleziona -"));
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<CsTbMacroSegnal> lst = confService.getMacroSegnalazioni(bo);
			if (lst != null) {
				for (CsTbMacroSegnal obj : lst) {
					lstMacroSegnalazioni.add(new SelectItem(obj.getId(), obj.getDescrizione()));
				}
			}		
		}
		
		return lstMacroSegnalazioni;
	}
	
	public void setLstMacroSegnalazioni(List<SelectItem> lstMacroSegnalazioni) {
		this.lstMacroSegnalazioni = lstMacroSegnalazioni;
	}
	
	@Override
	public List<SelectItem> getLstMicroSegnalazioni() {
		
		if(lstMicroSegnalazioni == null){
			lstMicroSegnalazioni = new ArrayList<SelectItem>();
			lstMicroSegnalazioni.add(new SelectItem(null, "- seleziona -"));
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<CsTbMicroSegnal> lst = confService.getMicroSegnalazioni(bo);
			if (lst != null) {
				for (CsTbMicroSegnal obj : lst) {
					lstMicroSegnalazioni.add(new SelectItem(obj.getId(), obj.getDescrizione()));
				}
			}		
		}
		
		return lstMicroSegnalazioni;
	}
	
	public void setLstMicroSegnalazioni(List<SelectItem> lstMicroSegnalazioni) {
		this.lstMicroSegnalazioni = lstMicroSegnalazioni;
	}
	
	@Override
	public List<SelectItem> getLstMotiviSegnalazioni() {
		
		if(lstMotiviSegnalazioni == null){
			lstMotiviSegnalazioni = new ArrayList<SelectItem>();
			lstMotiviSegnalazioni.add(new SelectItem(null, "- seleziona -"));
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<CsTbMotivoSegnal> lst = confService.getMotivoSegnalazioni(bo);
			if (lst != null) {
				for (CsTbMotivoSegnal obj : lst) {
					SelectItem si = new SelectItem(obj.getId(), obj.getDescrizione());
					si.setDisabled(si.isDisabled());
					lstMotiviSegnalazioni.add(si);
				}
			}		
		}
		
		return lstMotiviSegnalazioni;
	}
	
	public void setLstMotiviSegnalazioni(List<SelectItem> lstMotiviSegnalazioni) {
		this.lstMotiviSegnalazioni = lstMotiviSegnalazioni;
	}

	public Date getPrimoContattoAG() {
		return primoContattoAG;
	}

	public void setPrimoContattoAG(Date primoContattoAG) {
		this.primoContattoAG = primoContattoAG;
	}	
	
	
}
