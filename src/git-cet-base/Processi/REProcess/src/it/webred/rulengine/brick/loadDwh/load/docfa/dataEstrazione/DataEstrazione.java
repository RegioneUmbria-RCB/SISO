package it.webred.rulengine.brick.loadDwh.load.docfa.dataEstrazione;





/**
 * Interfacia che desrive una classe in grado di restituire una data.
 * Viene utilizzata da DataEstrazioneManager per restituire le classi per la lettura della data estrazione
 * <p>
 * di una fonte dati.
 * 
 * @author marcoric
 * @version $Revision: 1.1 $ $Date: 2007/05/16 07:31:55 $
 */
public interface DataEstrazione {


	public Long getDataEstrazione(Object riferimento) throws Exception;
	
}
