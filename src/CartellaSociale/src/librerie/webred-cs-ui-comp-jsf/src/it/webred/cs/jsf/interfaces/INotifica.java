package it.webred.cs.jsf.interfaces;

import org.primefaces.model.menu.MenuModel;

/**
 * @author Andrea
 *
 */
public interface INotifica {

	public void onPulisciLista() throws Exception;
	
	public void onLeggiTutti() throws Exception;
	
	public void onSelectMenuItem(Long idAlert) throws Exception;
	
	public Integer getNumeroNotifiche() throws Exception;
	
	public MenuModel getMenu() throws Exception;
	
	public boolean isNotificheRendered() throws Exception;
	
}