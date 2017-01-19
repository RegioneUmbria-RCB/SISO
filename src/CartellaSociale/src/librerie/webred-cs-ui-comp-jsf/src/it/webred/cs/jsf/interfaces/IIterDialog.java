package it.webred.cs.jsf.interfaces;

import it.webred.cs.jsf.bean.IterDialogCambioStatoBean;
import it.webred.cs.jsf.bean.IterDialogCasoBean;
import it.webred.cs.jsf.bean.IterDialogHistoryBean;

import javax.faces.event.ActionListener;
import javax.naming.NamingException;

/**
 * @author Andrea
 *
 */
public interface IIterDialog {
	public IterDialogCasoBean getCaso();
	
	public IterDialogHistoryBean getHistory();

	public IterDialogCambioStatoBean getCambioStato();
	
	public void openDialog(Long idCaso) throws NamingException;

	public ActionListener getCloseDialog();

	public void saveDialogWithAttributes() throws NamingException;
	
	public boolean isVisibile();
}
