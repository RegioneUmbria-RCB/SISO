package it.webred.cs.csa.web.manbean.export;

/**
 * Selettore versione dello schema per esportazione
 *
 */
public enum SchemaVersion {
	/**
	 * versione 2015, presto deprecata
	 */
	PSA_2015, 
	/**
	 * versione 2016 SENZA informazioni SINA (provvisorio) 
	 */
	PSA_2016_PS, 
	/**
	 * versione 2016 completa
	 */
	PSA_2016_PS_SINA,
	/**
	 * versione 2018
	 */
	PSA_2018_PS_SINA,
	
	SINBA_2018;
}
