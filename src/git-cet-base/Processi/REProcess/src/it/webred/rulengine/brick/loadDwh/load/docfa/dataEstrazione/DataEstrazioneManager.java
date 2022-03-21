package it.webred.rulengine.brick.loadDwh.load.docfa.dataEstrazione;


import it.webred.rulengine.brick.loadDwh.load.docfa.Caricatore;
import it.webred.rulengine.brick.loadDwh.load.docfa.TracciatoXml;

import org.apache.log4j.Logger;


/**
 * Classe manager delle implementazioni di DataEstrazione.
 * @author marcoric
 * @version $Revision: 1.1 $ $Date: 2007/05/16 07:31:55 $
 */
public class DataEstrazioneManager {

	private static final Logger	log	= Logger.getLogger(DataEstrazioneManager.class.getName());

	/**
	 * Restituisce una istanza di DataEstrazione.
	 * <p>
	 * Viene reperita dal parametro classeDataEstrazione se non nullo, oppure viene richiesta al caricatore.
	 * <p>
	 * Nel caso che la classe non fosse reperibile viene restituita quella di default.
	 * @param caricatore
	 * @param classeDataEstrazione
	 * @return
	 */
	public static Long getDataEstrazione(Caricatore caricatore, String classeDataEstrazione, TracciatoXml tracciatoxml) 
	throws Exception
	{
		DataEstrazione de = null;

				if (classeDataEstrazione == null) {
					if (caricatore == null) {
						de = new DefaultDataEstrazione();
					}
					else {
						String classeDataEstrazione1 = caricatore.getDefaultDataEstrazione();
						if(classeDataEstrazione1!=null )
						{
							DataEstrazione declass =(DataEstrazione) Class.forName(classeDataEstrazione1).newInstance();
							de=declass;
						}
					}
				} else {
					try {
						DataEstrazione declass =(DataEstrazione) Class.forName(classeDataEstrazione).newInstance();
						de =  declass;
					} catch (Exception e) {
						log.debug("Classe DataEstrazione " + classeDataEstrazione + "non trovata. restituita la classe di default");
						//log.debug("Classe DataEstrazione " + classeDataEstrazione + "non trovata. restituita la classe di default", e);
						de =  new DefaultDataEstrazione();
					}
				}
				if (de==null) {
					return (new DefaultDataEstrazione()).getDataEstrazione(null); 
				}
				else {
					Long d = de.getDataEstrazione(caricatore.getRiferimentoDS(tracciatoxml));
					return d;
				}
					
				

	}
	

	
}
