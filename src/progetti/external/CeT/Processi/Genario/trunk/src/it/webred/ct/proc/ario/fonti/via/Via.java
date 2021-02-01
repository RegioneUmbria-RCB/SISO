package it.webred.ct.proc.ario.fonti.via;

import it.webred.ct.proc.ario.bean.HashParametriConfBean;

public interface Via {
	
	/*
	 * Impostare a false se non viene gestita la via con viario
	 * Altrimenti leggere da propeties se il comune ha caricato o meno nel DWH i codici delle vie del viario
	 */
	public abstract boolean codiceVia(String codEnte, HashParametriConfBean paramConfBean) throws Exception;
}
