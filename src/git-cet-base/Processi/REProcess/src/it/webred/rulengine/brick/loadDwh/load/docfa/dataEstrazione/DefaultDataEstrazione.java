package it.webred.rulengine.brick.loadDwh.load.docfa.dataEstrazione;


/**
 * Classe di default delle implementazioni di DataEstrazione
 * Restituisce la data del server. 
 * @author marcoric
 * @version $Revision: 1.1 $ $Date: 2007/05/16 07:31:55 $
 */
public class DefaultDataEstrazione implements DataEstrazione {

	public Long getDataEstrazione(Object riferimento) {
		return new Long(System.currentTimeMillis());
	}
	
}
