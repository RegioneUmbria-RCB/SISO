package it.webred.cs.json.abitazione;

import it.webred.classfactory.WebredClassFactory;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.json.SchedaValutazioneManBean;

public abstract class AbitazioneManBaseBean extends SchedaValutazioneManBean implements IAbitazione{
	
	private static final long serialVersionUID = 1L;
	
	public static IAbitazione initByVersion(String defaultVersion)
	{
		IAbitazione interfaccia = null;
		try {
			interfaccia = (IAbitazione) WebredClassFactory.newInstance("", IAbitazione.class, defaultVersion!=null ? defaultVersion : "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return interfaccia;
	}
	
	public static IAbitazione init(IAbitazione man){
		IAbitazione interfaccia = initByVersion("");
		interfaccia.init(man);
		return interfaccia;
	}
	
	public static IAbitazione initByModel(CsDValutazione val) throws Exception{
		IAbitazione interfaccia = null;
		if (val != null) {
			String jsonString = val.getCsDDiario().getCsDClob().getDatiClob();
			String className = val.getVersioneScheda();

			// la versione di default è utile se si vuole comunque
			// instanziare una versione intermedia tra la prima e la max
			String defaultVersion = "";
			interfaccia = (IAbitazione) WebredClassFactory.newInstance(className, IAbitazione.class, defaultVersion);
			
			// Initialize scheda barthel
			interfaccia.init(null, val);
		}
		return interfaccia;
	}
	
}
