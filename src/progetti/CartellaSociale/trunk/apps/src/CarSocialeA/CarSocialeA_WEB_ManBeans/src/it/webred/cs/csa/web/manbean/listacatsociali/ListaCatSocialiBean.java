package it.webred.cs.csa.web.manbean.listacatsociali;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableCatSocialeSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.CasoSearchCriteria;
import it.webred.cs.csa.ejb.dto.ContatoreCasiDTO;
import it.webred.cs.csa.ejb.dto.PaginationDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.PermessiErogazioneInterventi;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOperatoreTipoOperatore;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsRelSettoreCatsoc;
import it.webred.cs.data.model.CsTbTipoOperatore;
import it.webred.cs.jsf.bean.CasiCatSocialeBean;
import it.webred.cs.jsf.bean.CasiOperatoreBean;
import it.webred.cs.jsf.interfaces.IListaCatSociali;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ejb.utility.ClientUtility;

@ManagedBean
@ViewScoped
public class ListaCatSocialiBean extends CsUiCompBaseBean implements IListaCatSociali {

	private List<CasiCatSocialeBean> lstCasiCatSociale;
	private List<CasiOperatoreBean> lstCasiOperatore;
	private CsOSettore settore;
	private int idxSelected;
	private String modalHeader;

	private boolean visualCaricoLavoro;



	@PostConstruct
	public void init() {

		try {

			lstCasiCatSociale = new ArrayList<CasiCatSocialeBean>();
			AccessTableCatSocialeSessionBeanRemote catSocialeService = (AccessTableCatSocialeSessionBeanRemote) ClientUtility
					.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableCatSocialeSessionBean");
			AccessTableSoggettoSessionBeanRemote soggettiService = (AccessTableSoggettoSessionBeanRemote) ClientUtility
					.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");

			CsOOperatoreSettore opSettore =getCurrentOpSettore();

			if (opSettore != null) {

				settore = opSettore.getCsOSettore();

				// Recupero le cat.sociali configurate per il settore corrente
				BaseDTO bDto = new BaseDTO();
				fillEnte(bDto);
				bDto.setObj(opSettore.getCsOSettore().getId());
				List<CsRelSettoreCatsoc> listaCatSociali = catSocialeService.findRelSettoreCatsocBySettore(bDto);

				CasoSearchCriteria searchCriteria = new CasoSearchCriteria();
				PaginationDTO dto = new PaginationDTO();
				fillEnte(dto);
				dto.setObj(searchCriteria);

				// Per ciascuna cat.sociale conteggio il numero di casi
				// registrati/in carico/chiusi dell'organizzazione
				for (CsRelSettoreCatsoc settCatSoc : listaCatSociali) {

					CasiCatSocialeBean comp = new CasiCatSocialeBean();
					comp.setCatSociale(settCatSoc.getCsCCategoriaSociale());
					comp.setSettore(settCatSoc.getCsOSettore());

					searchCriteria.setWithResponsabile(false);
					searchCriteria.setIdLastIter(null);
					searchCriteria.setIdCatSociale(settCatSoc.getCsCCategoriaSociale().getId());
					// Ricerco le categorie sociali del settore selezionato,
					// altrimenti becco tutto anche le altre org.
					searchCriteria.setIdSettore(settCatSoc.getCsOSettore().getId());
					searchCriteria.setIdOrganizzazione(opSettore.getCsOSettore().getCsOOrganizzazione().getId());

					ContatoreCasiDTO numCasi = soggettiService.getCasiPerCategoriaCount(dto);

					searchCriteria.setWithResponsabile(true);
					searchCriteria.setIdLastIter(null);
					ContatoreCasiDTO numCasiInCarico = soggettiService.getCasiPerCategoriaCount(dto);
					searchCriteria.setWithResponsabile(false);
					searchCriteria.setIdLastIter(DataModelCostanti.IterStatoInfo.CHIUSO);
					ContatoreCasiDTO numCasiChiusi = soggettiService.getCasiPerCategoriaCount(dto);

					comp.setCasi(numCasi);
					comp.setCasiInCarico(numCasiInCarico);
					comp.setCasiChiusi(numCasiChiusi);
					lstCasiCatSociale.add(comp);
				}
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
			AccessTableOperatoreSessionBeanRemote opService = (AccessTableOperatoreSessionBeanRemote) ClientUtility
					.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableOperatoreSessionBean");
			AccessTableCasoSessionBeanRemote casoService = (AccessTableCasoSessionBeanRemote) ClientUtility
					.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableCasoSessionBean");

			lstCasiOperatore = new ArrayList<CasiOperatoreBean>();
			CasiCatSocialeBean comp = lstCasiCatSociale.get(idxSelected);
			modalHeader = "Carico di lavoro - Cat.Sociale " + comp.getCatSociale().getDescrizione();
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(comp.getCatSociale().getId());
			List<CsOOperatore> listaOperatori = opService.getOperatoriByCatSocialeOrg(dto);

			for (CsOOperatore op : listaOperatori) {
				boolean operatorCanBeAdded = false; // SISO-640

				Iterator<CsOOperatoreSettore> itSett = op.getCsOOperatoreSettores().iterator();
				List<String> orgs = new ArrayList<String>();
				while (itSett.hasNext()) {
					CsOOperatoreSettore s = (CsOOperatoreSettore) itSett.next();
					String org = s.getCsOSettore().getCsOOrganizzazione().getNome();
					
					// SISO-640					
					dto.setObj(s.getId());
					dto.setObj2(comp.getCatSociale().getId());
					List<CsOOperatoreTipoOperatore> tipiOperatore = opService.findTipiOperatore(dto);
					
					for(CsOOperatoreTipoOperatore oto : tipiOperatore) {
						CsTbTipoOperatore csTbTipoOperatore = oto.getCsTbTipoOperatore();
						String descrizioneTipoOperatore = csTbTipoOperatore.getDescrizione();
						operatorCanBeAdded |=    descrizioneTipoOperatore.equals("Assistente sociale") 
								                || descrizioneTipoOperatore.equals("COP")
								                || descrizioneTipoOperatore.equals("Educatore Professionale");
					}
					// -----------------------------------------------------------------------~ SISO-640
					if (!orgs.contains(org))
						orgs.add(org);
				}
				String os = "";
				for (String s : orgs)
					os += s + ", ";
				os = os.substring(0, os.length() - 2);

				CasiOperatoreBean oComp = new CasiOperatoreBean();
				oComp.setOperatore(op); 
				oComp.setOrganizzazioni(os);
				if(operatorCanBeAdded){
				
					dto.setObj(op.getId());
					// SISO-640
					dto.setObj3(true);
					Integer numeroCasiEnte  = casoService.countCasiByResponsabileCatSociale(dto);
				
					if (numeroCasiEnte > 0) {
						oComp.setNumCasiEnte(numeroCasiEnte);
						
						dto.setObj3(false);
						Integer numeroCasiAltro = casoService.countCasiByResponsabileCatSociale(dto);
						oComp.setNumCasiAltro(numeroCasiAltro);
						lstCasiOperatore.add(oComp);
					}else {
						logger.info(String.format("operatore %d <%s> saltato: non ha casi in carico", oComp.getOperatore().getId(), oComp.getOperatore().getDenominazione()));
					}
				}else{
					logger.info(String.format("operatore %d <%s> saltato: non appartenente alla tipologia ['Assistente sociale','COP','Educatore Professionale']", oComp.getOperatore().getId(), oComp.getOperatore().getDenominazione()));
				}
			// -----------------------------------------------------------------------~ SISO-640
			}

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



	public CsOSettore getSettore() {
		return settore;
	}

	public void setSettore(CsOSettore settore) {
		this.settore = settore;
	}



	public String getModalHeader() {
		return modalHeader;
	}



	public void setModalHeader(String modalHeader) {
		this.modalHeader = modalHeader;
	}

	@Override
	public String getZonaSocialeLabel(){
		return getLabelZonaSociale();
	}
}
