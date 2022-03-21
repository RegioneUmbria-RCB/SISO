package it.webred.ct.proc.ario.fonti.soggetto;


import it.webred.ct.proc.ario.bean.HashParametriConfBean;

public interface Soggetto {
	/*
	 * Impostare a false se non viene gestita il soggetto originario
	 * Altrimenti leggere da propeties se il comune ha caricato o meno nel DWH i codici del soggetto di riferimento
	 */
	public abstract boolean codiceSoggetto(String codEnte, HashParametriConfBean paramConfBean) throws Exception;
}
