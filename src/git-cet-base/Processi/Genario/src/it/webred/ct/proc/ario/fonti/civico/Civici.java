package it.webred.ct.proc.ario.fonti.civico;

import it.webred.ct.proc.ario.bean.HashParametriConfBean;

public interface Civici {
	/*
	 * Impostare a false se non viene gestita il civico originario
	 * Altrimenti leggere da propeties se il comune ha caricato o meno nel DWH i codici del civico di riferimento
	 */
	public abstract boolean codiceCivico(String codEnte, HashParametriConfBean paramConfBean) throws Exception;
}
