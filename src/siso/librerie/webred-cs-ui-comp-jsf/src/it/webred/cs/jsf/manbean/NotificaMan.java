package it.webred.cs.jsf.manbean;

import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.alert.AlertBean;
import it.webred.cs.csa.ejb.dto.alert.TipoAlertBean;
import it.webred.cs.data.DataModelCostanti.PermessiNotifiche;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.jsf.interfaces.INotifica;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ejb.utility.ClientUtility;
import it.webred.utilities.CommonUtils;
import it.webred.utils.StringUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.NamingException;

import org.apache.commons.lang.StringEscapeUtils;
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

	@PostConstruct
	public void initializeNotifica() {
		try {
			logger.debug("INIT initializeNotifica");
			CsOOperatoreSettore opSettore = getCurrentOpSettore();
			List<Long> tempLista = getListaIdSettoriAbilitatiOperatore();

			if(opSettore != null) {
				
				tipoAlerts = new LinkedList<TipoAlertBean>();
				numeroNotifiche = 0;
				
				HashMap<String, String> permessiGruppoSettore = (HashMap<String, String>) getSession().getAttribute("permessiGruppoSettore");
				
				AccessTableAlertSessionBeanRemote alertSessionBean = getAlertSessioBean();
				
				Boolean hasPermessoEnte = checkPermesso(PermessiNotifiche.ITEM, PermessiNotifiche.VISUALIZZA_NOTIFICHE_ORGANIZZAZIONE);
				Boolean hasPermessoSettore = checkPermesso(PermessiNotifiche.ITEM, PermessiNotifiche.VISUALIZZA_NOTIFICHE_SETTORE); 
				  
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(opSettore);
				dto.setObj2(hasPermessoEnte);
				dto.setObj3(hasPermessoSettore);
				dto.setObj4(tempLista);
				dto.setObj5(permessiGruppoSettore);
				
				tipoAlerts = alertSessionBean.getNotificheVisibili(dto);

				initializeNotificationMenu();
			}
			logger.debug("END initializeNotifica");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("Errore inizializzazione Notifiche", e.getMessage());
		}
	}

	protected void initializeNotificationMenu(){
		notificationMenu = new DefaultMenuModel();
		
		for(TipoAlertBean tipo : tipoAlerts ){
			DefaultSubMenu  submenu = new DefaultSubMenu( tipo.getLabelTipo());
			
			for(AlertBean alert : tipo.getListaAlert() ) {
				
				String descrizione = StringEscapeUtils.unescapeHtml(alert.getTitoloDescrizione());
				descrizione+= StringUtils.isEmpty(alert.getDescriSettOrg()) ? " - "+ alert.getDescriSettOrg() : "";
				
				DefaultMenuItem item = new DefaultMenuItem(descrizione);
				item.setCommand("#{cc.attrs.iNotifica.onSelectMenuItem (" + alert.getId() +")}");
				item.setAjax(false);
				item.setUpdate(":#{p:component('pnlNotifiche')}");
				item.setDisabled(alert.isDisabled());
				item.setTitle(StringEscapeUtils.unescapeHtml(alert.getTooltip()));
				if(!alert.isLetto()){
					item.setStyleClass("isNotLetto");
					if(!alert.isDisabled()) numeroNotifiche++;
				}
				
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

				DefaultMenuItem pulisciLista = new DefaultMenuItem("Pulisci Lista");
				pulisciLista.setCommand("#{cc.attrs.iNotifica.onPulisciLista}");
				pulisciLista.setStyleClass("buttonNotificaFooter");
				pulisciLista.setAjax(false);
				notificationMenu.addElement(pulisciLista);

				DefaultMenuItem leggiTutti = new DefaultMenuItem("Leggi Tutti");
				leggiTutti.setCommand("#{cc.attrs.iNotifica.onLeggiTutti}");
				leggiTutti.setStyleClass("buttonNotificaFooter");
				leggiTutti.setAjax(false);
				leggiTutti.setUpdate(":pnlNotifiche");
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

	protected AccessTableAlertSessionBeanRemote getAlertSessioBean() throws NamingException {
		AccessTableAlertSessionBeanRemote sessionBean = (AccessTableAlertSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableAlertSessionBean");
		return sessionBean;
	}
}
