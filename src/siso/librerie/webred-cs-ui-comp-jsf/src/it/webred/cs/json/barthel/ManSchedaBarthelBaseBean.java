package it.webred.cs.json.barthel;

import it.webred.classfactory.WebredClassFactory;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSessionBeanRemote;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.json.SchedaValutazioneManBean;

public abstract class ManSchedaBarthelBaseBean extends SchedaValutazioneManBean implements ISchedaBarthel {

	private static final long serialVersionUID = 1L;
	protected CsASoggettoLAZY soggetto;
	
	protected AccessTableSchedaSessionBeanRemote schedaService = 
			(AccessTableSchedaSessionBeanRemote)getCarSocialeEjb("AccessTableSchedaSessionBean");
	
	protected AccessTableDiarioSessionBeanRemote diarioService = (AccessTableDiarioSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB",
			"AccessTableDiarioSessionBean");


/*
 * NON UTILIZZARE SE SI INTENDE ASSOCIARE LA SCHEDA BARTHEL ALLA SCHEDA MULTIDIMENSIONALE
 * 	public static ISchedaBarthel initSchedaBarthel(String defaultVersion,CsASoggettoLAZY soggetto)
	{
		ISchedaBarthel scheda = null;
		try {
			scheda = (ISchedaBarthel) WebredClassFactory.newInstance("", ISchedaBarthel.class, defaultVersion);
			scheda.setIdCaso(soggetto.getCsACaso().getId());
			scheda.setSoggettoFascicolo(soggetto);
		
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return scheda;
	}*/

	public static ISchedaBarthel initSchedaBarthel(CsDValutazione val, CsASoggettoLAZY soggetto) throws Exception {
		ISchedaBarthel interfaccia = null;
		if (val != null) {
			String className = val.getVersioneScheda();

			// la versione di default è utile se si vuole comunque
			// instanziare una versione intermedia tra la prima e la max
			String defaultVersion = "";
			interfaccia = (ISchedaBarthel) WebredClassFactory.newInstance(className, ISchedaBarthel.class, defaultVersion);
			interfaccia.setIdCaso(soggetto.getCsACaso().getId());
			interfaccia.setSoggettoFascicolo(soggetto);
			
			// Initialize scheda barthel
			interfaccia.init(null, val);
		}
		return interfaccia;
	}
	
	public static ISchedaBarthel initSchedaBarthel(CsDValutazione parent, CsDValutazione val, CsASoggettoLAZY soggetto) throws Exception {
		ISchedaBarthel interfaccia = null;
		String className = "";
		if (val != null) 
			className = val.getVersioneScheda();

			// la versione di default è utile se si vuole comunque
			// instanziare una versione intermedia tra la prima e la max
			String defaultVersion = "";
			interfaccia = (ISchedaBarthel) WebredClassFactory.newInstance(className, ISchedaBarthel.class, defaultVersion);
			
			
			interfaccia.setIdCaso(soggetto.getCsACaso().getId());
			interfaccia.setSoggettoFascicolo(soggetto);
			
			interfaccia.init(parent, val);
		
		return interfaccia;
	}
	
	@Override
	public void setSoggettoFascicolo(CsASoggettoLAZY soggetto) {
		this.soggetto=soggetto;
		loadCommonList();
	}
	
	protected abstract void loadCommonList();
	
	/*public static ISchedaBarthel initRow(CsDValutazione val,CsASoggettoLAZY soggetto) throws Exception {
		ISchedaBarthel interfaccia = null;
		if (val != null) {
			String className = val.getVersioneScheda();

			// la versione di default è utile se si vuole comunque
			// instanziare una versione intermedia tra la prima e la max
			String defaultVersion = "";
			interfaccia = (ISchedaBarthel) WebredClassFactory.newInstance(className, ISchedaBarthel.class, defaultVersion);
			interfaccia.setIdCaso(soggetto.getCsACaso().getId());
			interfaccia.setSoggettoFascicolo(soggetto);
			// Initialize scheda barthel
			interfaccia.initRowList(null, val);
		}
		return interfaccia;
	}*/
	
}
