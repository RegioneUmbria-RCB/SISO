package it.webred.cs.jsf.manbean;

import it.webred.cet.permission.CeTUser;
import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.csa.ejb.dto.retvalue.CsAlertDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.PermessiNotifiche;
import it.webred.cs.data.model.CsAlertBASIC;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.jsf.bean.AlertBean;
import it.webred.cs.jsf.bean.TipoAlertBean;
import it.webred.cs.jsf.interfaces.INotifica;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ejb.utility.ClientUtility;
import it.webred.utilities.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSeparator;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

@ManagedBean
@ViewScoped
public class NotificaMan extends CsUiCompBaseBean implements INotifica {
		
	private List<TipoAlertBean> tipoAlerts;
	private Integer numeroNotifiche;
	private DefaultMenuModel notificationMenu;
	private String nomeOperatore;
	private Long idSettoreUtente;
	private Long idOrganizzazione;
	private String settoreUtente;
	private String OrganizzazioneUtente;
	
//	protected AccessTableOperatoreSessionBeanRemote operatoreService = (AccessTableOperatoreSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableOperatoreSessionBean");
	
	@PostConstruct
	public void initializeNotifica() {
		try {
			if(nomeOperatore == null) {
				CeTUser user = getUser();
				nomeOperatore = user.getUsername();
			}
			CsOOperatoreSettore opSettore = getCurrentOpSettore();
			
			ArrayList<CsOOperatoreSettore> tempLista = getTempListaSett();

			if(opSettore != null) {
				idSettoreUtente = opSettore.getCsOSettore().getId();
				idOrganizzazione = opSettore.getCsOSettore().getCsOOrganizzazione().getId();
				tipoAlerts = new LinkedList<TipoAlertBean>();
				numeroNotifiche = 0;
				
				AccessTableAlertSessionBeanRemote alertSessionBean = getAlertSessioBean();
				AccessTableOperatoreSessionBeanRemote operatoreSessionBean = getOperatoreSessioBean();
				
				OperatoreDTO opDto = new OperatoreDTO();
				fillEnte(opDto);
				opDto.setUsername(nomeOperatore);
				long idOp = operatoreSessionBean.findOperatoreIdByUsername(opDto);
				
				boolean isEnte = checkPermesso(PermessiNotifiche.NOTIFICHE_ITEM, PermessiNotifiche.VISUALIZZA_NOTIFICHE_ORGANIZZAZIONE);
				boolean isSettore = checkPermesso(PermessiNotifiche.NOTIFICHE_ITEM, PermessiNotifiche.VISUALIZZA_NOTIFICHE_SETTORE); 
				
				IterDTO itDto = new IterDTO();
				fillEnte(itDto);
				itDto.setListaTipo(getListaTipo());
				itDto.setNomeOperatore(nomeOperatore);
				
				//setto il settore/org solo se ho il permesso di vedere le notifiche del settore/org 
				if (isSettore) itDto.setIdSettore(idSettoreUtente);
				if (isEnte) itDto.setIdOrganizzazione(idOrganizzazione);
				
				List<CsAlertBASIC> listaAlert = alertSessionBean.getNotificas(itDto);
		 
				for( CsAlertBASIC csa : listaAlert )
				{
					if(csa.getVisibile()){
						TipoAlertBean curTipoAlert = IsTipoAlertPresent(tipoAlerts, csa.getLabelTipo()); 
						
						if (curTipoAlert == null ) {
							curTipoAlert = new TipoAlertBean();
							curTipoAlert.setLabelTipo(csa.getLabelTipo());
							tipoAlerts.add(curTipoAlert);
						}
			
						boolean hasPermessoSettore = checkPermesso(PermessiNotifiche.NOTIFICHE_ITEM, DataModelCostanti.PermessiNotifiche.VISUALIZZA_NOTIFICHE_SETTORE);
						curTipoAlert.getListaAlert().add( new AlertBean( csa, idSettoreUtente, idOp, hasPermessoSettore, tempLista));
						
						if (!csa.getLetto())
							numeroNotifiche++;
					}
				}
		
				initializeNotificationMenu();
			}
		
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("Inizializzazione Fallita", "Inizializzazione del componente notifica fallito!");
		}
	}

	protected TipoAlertBean IsTipoAlertPresent(List<TipoAlertBean> listTipoAlert, String labelTipo){
		for (TipoAlertBean item : listTipoAlert)
			if(item.getLabelTipo().equalsIgnoreCase(labelTipo))
				return item;
		return null;
	}
	
	
	
	protected void initializeNotificationMenu(){
		notificationMenu = new DefaultMenuModel();
		
		for(TipoAlertBean tipo : tipoAlerts ){
			DefaultSubMenu  submenu = new DefaultSubMenu( tipo.getLabelTipo());
			
			for(AlertBean alert : tipo.getListaAlert() ) {
				
				DefaultMenuItem item = new DefaultMenuItem(alert.getTitoloDescrizione()+ " " +" - "+ alert.getDescriSettOrg());
				item.setCommand("#{cc.attrs.iNotifica.onSelectMenuItem (" + alert.getId() +")}");
				item.setAjax(false);
				item.setUpdate("@form");
				item.setDisabled(alert.isDisabled());
				item.setTitle(alert.getTooltip());
				
				if(!alert.isLetto())
					item.setStyleClass("isNotLetto");
				
				submenu.addElement(item);
			}
			notificationMenu.addElement(submenu);
		}
		
		//TODO: To try this!!!!!!
		List<Long> idAlertList = new LinkedList<Long>();
		for (TipoAlertBean tipoAlert : tipoAlerts) {
			for (AlertBean alert : tipoAlert.getListaAlert()) {
				if (!alert.isDisabled())
					idAlertList.add(alert.getId());
			}
		}
		
		if (tipoAlerts.size() != 0){
			if (idAlertList.size() != 0) {
				DefaultSeparator separator = new DefaultSeparator();
				notificationMenu.addElement(separator);

				DefaultMenuItem pulisciLista = new DefaultMenuItem(
						"Pulisci Lista");
				pulisciLista.setCommand("#{cc.attrs.iNotifica.onPulisciLista}");
				pulisciLista.setStyleClass("buttonNotificaFooter");
				pulisciLista.setAjax(false);
				notificationMenu.addElement(pulisciLista);

				DefaultMenuItem leggiTutti = new DefaultMenuItem("Leggi Tutti");
				leggiTutti.setCommand("#{cc.attrs.iNotifica.onLeggiTutti}");
				leggiTutti.setStyleClass("buttonNotificaFooter");
				leggiTutti.setAjax(false);
				leggiTutti.setUpdate("@form");
				notificationMenu.addElement(leggiTutti);
			}
		}
		else{
			DefaultMenuItem noMessages = new DefaultMenuItem("Non sono presenti messaggi");
			noMessages.setStyleClass("noMessages");
			noMessages.setDisabled(true);
			notificationMenu.addElement(noMessages);
		}
	}
	
	@Override
	public Integer getNumeroNotifiche() {
		return numeroNotifiche;
	}

	@Override
	public MenuModel getMenu() {
		return this.notificationMenu;
	}

	@Override
	public void onSelectMenuItem(Long idAlert) throws Exception {
		try {
			for (TipoAlertBean tipoAlert : tipoAlerts) {
				for (AlertBean alert : tipoAlert.getListaAlert()) {
					if (alert.getId().equals(idAlert)) {
						
						IterDTO itDto = new IterDTO();
						fillEnte(itDto);
						itDto.setIdAlert(idAlert);
						getAlertSessioBean().updateLettoById(itDto);

						if (CommonUtils.isNotEmptyString(alert.getUrl())) {
							getResponse().sendRedirect(alert.getUrl());
						} else {
							initializeNotifica();
						}
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("Errore nella selezione della notifica", "Errore nel click dell'item della notifica!");
		}
	}

	@Override
	public void onPulisciLista(){
		try {
			List<Long> idAlertList = new LinkedList<Long>();

			for (TipoAlertBean tipoAlert : tipoAlerts) {
				for (AlertBean alert : tipoAlert.getListaAlert()) {
					if (!alert.isDisabled())
						idAlertList.add(alert.getId());
				}
			}

			IterDTO itDto = new IterDTO();
			fillEnte(itDto);
			itDto.setIdAlertList(idAlertList);
			getAlertSessioBean().updatePulisciLista(itDto);

			initializeNotifica();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("Errore nell'azione di pulizia della lista", "Pulizia della Lista notifiche non avvenuta!");
		}
	}

	@Override
	public void onLeggiTutti(){
		try {
			List<Long> idAlertList = new LinkedList<Long>();
			for(TipoAlertBean tipoAlert : tipoAlerts ) {
				for (AlertBean alert : tipoAlert.getListaAlert() ) {
					if(!alert.isDisabled())
						idAlertList.add(alert.getId());
				}
			}
	
			IterDTO itDto = new IterDTO();
			fillEnte(itDto);
			itDto.setIdAlertList(idAlertList);
			getAlertSessioBean().updateLeggiAll(itDto);
			
			initializeNotifica();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("Errore nell'azione di lettura delle notifiche", "Lettura di tutte le notifiche NON avvenuta!");
		}
	}

	@Override
	public boolean isNotificheRendered() {
		if (numeroNotifiche == null || numeroNotifiche == 0)
			return false;
		return true;
	}
	
	protected List<String> getListaTipo() {
		List<String> listaTipo = new LinkedList<String>();
		HashMap<String, String> permessiGruppoSettore = (HashMap<String, String>) getSession().getAttribute("permessiGruppoSettore");
		if(permessiGruppoSettore != null) {
			for (String it : permessiGruppoSettore.values()) {
				int idx = it.lastIndexOf("@-@");
				String permesso = it.substring(idx+3);
				if( permesso.startsWith(PermessiNotifiche.VISUALIZZA_NOTIFICHE_TIPO) )
					listaTipo.add( "'" + permesso.replace(PermessiNotifiche.VISUALIZZA_NOTIFICHE_TIPO, "") + "'" );
			}
		}
			
		return listaTipo;
	}
	
	protected AccessTableOperatoreSessionBeanRemote getOperatoreSessioBean() throws NamingException {
		AccessTableOperatoreSessionBeanRemote sessionBean = (AccessTableOperatoreSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableOperatoreSessionBean");
		return sessionBean;
	}

	protected AccessTableAlertSessionBeanRemote getAlertSessioBean() throws NamingException {
		AccessTableAlertSessionBeanRemote sessionBean = (AccessTableAlertSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableAlertSessionBean");
		return sessionBean;
	}

	public String getSettoreUtente() {
		return settoreUtente;
	}

	public void setSettoreUtente(String settoreUtente) {
		this.settoreUtente = settoreUtente;
	}

	public String getOrganizzazioneUtente() {
		return OrganizzazioneUtente;
	}

	public void setOrganizzazioneUtente(String organizzazioneUtente) {
		OrganizzazioneUtente = organizzazioneUtente;
	}

}
