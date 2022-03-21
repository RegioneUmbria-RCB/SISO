package it.webred.cs.json.mediazioneculturale;

import it.webred.classfactory.WebredClassFactory;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.json.SchedaValutazioneManBean;
import it.webred.cs.json.orientamentoistruzione.IOrientamentoIstruzione;

public abstract class MediazioneCultManBaseBean extends SchedaValutazioneManBean implements IMediazioneCult {

	private static final long serialVersionUID = -8956164424643220542L;

	public MediazioneCultManBaseBean() {
	}

	public static IMediazioneCult initByVersion(String defaultVersion) {
		IMediazioneCult man = null;
		try {
			man = (IMediazioneCult) WebredClassFactory.newInstance("", IMediazioneCult.class, defaultVersion != null ? defaultVersion : "");

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return man;
	}

	public static IMediazioneCult initByModel(CsDValutazione val) throws Exception {
		IMediazioneCult man = null;
		if (val != null) {

			String className = val.getVersioneScheda();

			// la versione di default è utile se si vuole comunque
			// instanziare una versione intermedia tra la prima e la max
			String defaultVersion = "";
			man = (IMediazioneCult) WebredClassFactory.newInstance(className, IMediazioneCult.class, defaultVersion);
			man.init(null, val);
		}
		return man;
	}

	public static IMediazioneCult init(IMediazioneCult man){
		IMediazioneCult interfaccia = initByVersion("");
		interfaccia.init(man);
		return interfaccia;
	}
	
}
