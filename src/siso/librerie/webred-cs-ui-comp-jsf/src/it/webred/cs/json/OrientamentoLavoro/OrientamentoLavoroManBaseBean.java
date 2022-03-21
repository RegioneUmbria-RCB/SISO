package it.webred.cs.json.OrientamentoLavoro;

import it.webred.classfactory.WebredClassFactory;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.json.SchedaValutazioneManBean;

public abstract class OrientamentoLavoroManBaseBean extends SchedaValutazioneManBean  implements IOrientamentoLavoro {

	private static final long serialVersionUID = 1L;

	public static IOrientamentoLavoro initByVersion(String defaultVersion)
	{
		IOrientamentoLavoro lavoro = null;
		try {
			lavoro = (IOrientamentoLavoro) WebredClassFactory.newInstance("", IOrientamentoLavoro.class, defaultVersion!=null ? defaultVersion : "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return lavoro;
	}
	
	public static IOrientamentoLavoro initByModel(CsDValutazione val) throws Exception{
		IOrientamentoLavoro interfaccia = null;
		if (val != null) {
			String className = val.getVersioneScheda();

			// la versione di default è utile se si vuole comunque
			// instanziare una versione intermedia tra la prima e la max
			String defaultVersion = "";
			interfaccia = (IOrientamentoLavoro) WebredClassFactory.newInstance(className, IOrientamentoLavoro.class, defaultVersion);
			
			// Initialize scheda barthel
			interfaccia.init(null, val);
		}
		return interfaccia;
	}
	
	public static IOrientamentoLavoro init(IOrientamentoLavoro man){
		IOrientamentoLavoro interfaccia = initByVersion("");
		interfaccia.init(man);
		return interfaccia;
	}
}
