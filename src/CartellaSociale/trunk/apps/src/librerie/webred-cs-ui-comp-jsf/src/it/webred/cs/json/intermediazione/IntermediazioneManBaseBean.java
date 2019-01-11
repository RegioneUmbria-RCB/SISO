package it.webred.cs.json.intermediazione;

import it.webred.classfactory.WebredClassFactory;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.jsf.manbean.ComuneProvenienzaMan;
import it.webred.cs.json.SchedaValutazioneManBean;
import it.webred.cs.json.familiariConviventi.IFamConviventi;

public abstract class IntermediazioneManBaseBean extends SchedaValutazioneManBean implements IIntermediazioneAb{
	
	private static final long serialVersionUID = 1L;

	protected ComuneProvenienzaMan comuneMan;
	protected boolean disableMotivoAltro = true;
	
	public boolean isDisableMotivoAltro() {
		return disableMotivoAltro; 
	}

	public void setDisableMotivoAltro(boolean disableMotivoAltro) {
		this.disableMotivoAltro = disableMotivoAltro;
	}

	public IntermediazioneManBaseBean(){
		comuneMan = new ComuneProvenienzaMan();
	}
	
	public ComuneProvenienzaMan getComuneMan() {
		return comuneMan;
	}
	public void setComuneMan(ComuneProvenienzaMan comuneMan) {
		this.comuneMan = comuneMan;
	}
	
	public static IIntermediazioneAb initByVersion(String defaultVersion) {
		IIntermediazioneAb man = null;
		try {
			man = (IIntermediazioneAb) WebredClassFactory.newInstance("", IIntermediazioneAb.class, defaultVersion!=null ? defaultVersion : "");
		
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return man;
	}

	public static IIntermediazioneAb initByModel(CsDValutazione val) throws Exception {
		IIntermediazioneAb man = null;
		if (val != null) {
		
			String className = val.getVersioneScheda();

			// la versione di default è utile se si vuole comunque
			// instanziare una versione intermedia tra la prima e la max
			String defaultVersion = "";
			
			man = (IIntermediazioneAb)WebredClassFactory.newInstance(className, IIntermediazioneAb.class, defaultVersion);

			// Initialize scheda barthel
			man.init(null, val);
		}
		return man;
	}
	
	public static IIntermediazioneAb init(IIntermediazioneAb man){
		IIntermediazioneAb interfaccia = initByVersion("");
		interfaccia.init(man);
		return interfaccia;
	}

}
