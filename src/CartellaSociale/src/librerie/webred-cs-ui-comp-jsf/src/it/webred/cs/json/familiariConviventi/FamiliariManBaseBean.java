package it.webred.cs.json.familiariConviventi;

import it.webred.classfactory.WebredClassFactory;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.json.SchedaValutazioneManBean;
import it.webred.cs.json.abitazione.IAbitazione;

public abstract class FamiliariManBaseBean extends SchedaValutazioneManBean implements IFamConviventi{
	
	private static final long serialVersionUID = 1L;
	
	public static IFamConviventi initByVersion(String defaultVersion)
	{
		IFamConviventi interfaccia = null;
		try {
			interfaccia = (IFamConviventi) WebredClassFactory.newInstance("", IFamConviventi.class, defaultVersion!=null ? defaultVersion : "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return interfaccia;
	}
	

	public static IFamConviventi initByModel(CsDValutazione val) throws Exception{
		IFamConviventi interfaccia = null;
		if (val != null) {
			String className = val.getVersioneScheda();

			// la versione di default è utile se si vuole comunque
			// instanziare una versione intermedia tra la prima e la max
			String defaultVersion = "";
			interfaccia = (IFamConviventi) WebredClassFactory.newInstance(className, IFamConviventi.class, defaultVersion);
			
			// Initialize scheda barthel
			interfaccia.init(null, val);
		}
		return interfaccia;
	}
	
	public static IFamConviventi init(IFamConviventi man){
		IFamConviventi interfaccia = initByVersion("");
		interfaccia.init(man);
		return interfaccia;
	}

}
