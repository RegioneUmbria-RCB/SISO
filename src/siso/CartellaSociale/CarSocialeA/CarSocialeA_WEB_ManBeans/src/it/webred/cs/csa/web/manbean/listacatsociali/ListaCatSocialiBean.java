package it.webred.cs.csa.web.manbean.listacatsociali;

import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.configurazione.AccessTableCatSocialeSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.CasoSearchCriteria;
import it.webred.cs.csa.ejb.dto.ContatoreCasiDTO;
import it.webred.cs.csa.ejb.dto.PaginationDTO;
import it.webred.cs.csa.ejb.dto.prospettoSintesi.CasiCatSocialeBean;
import it.webred.cs.csa.ejb.dto.prospettoSintesi.CasiOperatoreBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.PermessiErogazioneInterventi;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsRelSettoreCatsoc;
import it.webred.cs.jsf.interfaces.IListaCatSociali;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ejb.utility.ClientUtility;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class ListaCatSocialiBean extends CsUiCompBaseBean implements IListaCatSociali {

	private List<CasiCatSocialeBean> lstCasiCatSociale;
	private List<CasiOperatoreBean> lstCasiOperatore;
	private String settore;
	private String organizzazione;
	private int idxSelected;
	private String modalHeader;

	private boolean visualCaricoLavoro;



	@PostConstruct
	public void init() {

		try {

			lstCasiCatSociale = new ArrayList<CasiCatSocialeBean>();
			AccessTableSoggettoSessionBeanRemote soggettiService = 
					(AccessTableSoggettoSessionBeanRemote) getCarSocialeEjb("AccessTableSoggettoSessionBean");

			CsOOperatoreSettore opSettore =getCurrentOpSettore();

			if (opSettore != null) {

				settore = opSettore.getCsOSettore().getNome();
				organizzazione = opSettore.getCsOSettore().getCsOOrganizzazione().getNome();

				// Recupero le cat.sociali configurate per il settore corrente
				BaseDTO bDto = new BaseDTO();
				fillEnte(bDto);
				bDto.setObj(opSettore.getCsOSettore().getId());
				lstCasiCatSociale = soggettiService.loadProspettoSintesi(bDto);
			}
			visualCaricoLavoro = checkPermesso(PermessiErogazioneInterventi.ITEM,
					DataModelCostanti.PermessiCartella.VISUALIZZAZIONE_CARICO_LAVORO);

		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}

	}



	public void caricaCaricoLavoro() {
		try {
			AccessTableCasoSessionBeanRemote casoService = (AccessTableCasoSessionBeanRemote) getCarSocialeEjb("AccessTableCasoSessionBean");

			CasiCatSocialeBean comp = lstCasiCatSociale.get(idxSelected);
			modalHeader = "Carico di lavoro - Cat.Sociale " + comp.getCatSocialeDesc();
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(comp.getCatSocialeId());
			lstCasiOperatore = casoService.loadCaricoLavoroByCatSocOrg(dto);

		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}

	}



	public List<CasiCatSocialeBean> getLstCasiCatSociale() {
		return lstCasiCatSociale;
	}



	public void setLstCasiCatSociale(List<CasiCatSocialeBean> lstCasiCatSociale) {
		this.lstCasiCatSociale = lstCasiCatSociale;
	}



	public int getIdxSelected() {
		return idxSelected;
	}



	public void setIdxSelected(int idxSelected) {
		this.idxSelected = idxSelected;
	}



	public List<CasiOperatoreBean> getLstCasiOperatore() {
		return lstCasiOperatore;
	}



	public void setLstCasiOperatore(List<CasiOperatoreBean> lstCasiOperatore) {
		this.lstCasiOperatore = lstCasiOperatore;
	}



	public boolean isVisualCaricoLavoro() {
		return visualCaricoLavoro;
	}



	public void setVisualCaricoLavoro(boolean visualCaricoLavoro) {
		this.visualCaricoLavoro = visualCaricoLavoro;
	}


	public String getModalHeader() {
		return modalHeader;
	}



	public void setModalHeader(String modalHeader) {
		this.modalHeader = modalHeader;
	}

	public String getSettore() {
		return settore;
	}

	public void setSettore(String settore) {
		this.settore = settore;
	}

	public String getOrganizzazione() {
		return organizzazione;
	}

	public void setOrganizzazione(String organizzazione) {
		this.organizzazione = organizzazione;
	}

	@Override
	public String getZonaSocialeLabel(){
		return getLabelZonaSociale();
	}
}
