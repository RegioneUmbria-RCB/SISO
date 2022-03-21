package it.webred.cs.json.isee;

import it.webred.classfactory.WebredClassFactory;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.jsf.manbean.ProtocolloDsuMan;
import it.webred.cs.json.SchedaValutazioneManBean;

public abstract class IseeManBaseBean  extends SchedaValutazioneManBean implements IIseeJson {

	private static final long serialVersionUID = 1L;
	
	protected static ProtocolloDsuMan protDsuMan;
	
	public IseeManBaseBean(){
		protDsuMan= new ProtocolloDsuMan();
	}

	public static IIseeJson initByVersion(String defaultVersion) {
		IIseeJson man = null;
		try {
			man = (IIseeJson) WebredClassFactory.newInstance("", IIseeJson.class, defaultVersion != null ? defaultVersion : "");

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return man;
	}

	public static IIseeJson initByModel(CsDValutazione val) throws Exception {
		IIseeJson man = null;
		if (val != null) {

			String className = val.getVersioneScheda();

			// la versione di default è utile se si vuole comunque
			// instanziare una versione intermedia tra la prima e la max
			String defaultVersion = "";
			man = (IIseeJson) WebredClassFactory.newInstance(className, IIseeJson.class, defaultVersion);
			man.init(null, val);
			
			protDsuMan.setCodfisc(val.getCsDDiario().getCsACaso().getCsASoggetto().getCsAAnagrafica().getCf());
			protDsuMan.setIdRichiesta(val.getCsDDiario().getCsACaso().getIdentificativo().toString());
		}
		return man;
	}

	@Override
	public ProtocolloDsuMan getProtDsuMan() {
		return protDsuMan;
	}

	public void setProtDsuMan(ProtocolloDsuMan protDsuMan) {
		this.protDsuMan = protDsuMan;
	}
	
	
}
