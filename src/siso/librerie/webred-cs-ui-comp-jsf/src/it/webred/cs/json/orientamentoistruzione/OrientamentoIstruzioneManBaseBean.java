package it.webred.cs.json.orientamentoistruzione;

import it.webred.classfactory.WebredClassFactory;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.json.SchedaValutazioneManBean;
import it.webred.cs.json.familiariConviventi.IFamConviventi;

public abstract class OrientamentoIstruzioneManBaseBean extends SchedaValutazioneManBean implements IOrientamentoIstruzione {

	private static final long serialVersionUID = -7222493175083819177L;

	public OrientamentoIstruzioneManBaseBean() {
	}

	public static IOrientamentoIstruzione initByVersion(String defaultVersion) {
		IOrientamentoIstruzione man = null;
		try {
			man = (IOrientamentoIstruzione) WebredClassFactory.newInstance("", IOrientamentoIstruzione.class, defaultVersion != null ? defaultVersion : "");

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return man;
	}
	
	public static IOrientamentoIstruzione initByModel(CsDValutazione val) throws Exception {
		IOrientamentoIstruzione man = null;
		if (val != null) {

			String className = val.getVersioneScheda();

			// la versione di default è utile se si vuole comunque
			// instanziare una versione intermedia tra la prima e la max
			String defaultVersion = "";

			man = (IOrientamentoIstruzione) WebredClassFactory.newInstance(className, IOrientamentoIstruzione.class, defaultVersion);

			man.init(null, val);
		}
		return man;
	}
	
	public static IOrientamentoIstruzione init(IOrientamentoIstruzione man){
		IOrientamentoIstruzione interfaccia = initByVersion("");
		interfaccia.init(man);
		return interfaccia;
	}
}
