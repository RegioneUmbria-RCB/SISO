package it.webred.cs.jsf.interfaces;

import it.webred.cs.jsf.manbean.IterDialogMan;
import it.webred.cs.jsf.manbean.IterInfoStatoMan;

public interface IPresaInCarico {
	
	public IterInfoStatoMan getCasoInfo();
	
	public IterDialogMan getIterDialogMan();
	
	//public ActionListener getCloseDialog();
	
	public void inizializeAndRefresh();
}
