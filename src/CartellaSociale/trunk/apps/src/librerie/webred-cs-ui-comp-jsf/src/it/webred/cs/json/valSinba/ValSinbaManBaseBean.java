package it.webred.cs.json.valSinba;

import it.webred.classfactory.WebredClassFactory;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSessionBeanRemote;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.json.SchedaValutazioneManBean;

public abstract class ValSinbaManBaseBean extends SchedaValutazioneManBean implements IValSinba {

	private static final long serialVersionUID = 1L;
	protected CsASoggettoLAZY soggetto;
	
	protected AccessTableConfigurazioneSessionBeanRemote configService = 
			(AccessTableConfigurazioneSessionBeanRemote)getCarSocialeEjb( "AccessTableConfigurazioneSessionBean");
	
	protected AccessTableSchedaSessionBeanRemote schedaService = 
			(AccessTableSchedaSessionBeanRemote)getCarSocialeEjb("AccessTableSchedaSessionBean");
	
	protected AccessTableDiarioSessionBeanRemote diarioService = (AccessTableDiarioSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB",
			"AccessTableDiarioSessionBean");


	public static IValSinba initISchedaSinba(String defaultVersion,CsASoggettoLAZY soggetto)
	{
		IValSinba scheda = null;
		try {
			scheda = (IValSinba) WebredClassFactory.newInstance("", IValSinba.class, defaultVersion);
			scheda.setIdCaso(soggetto.getCsACaso().getId());
			scheda.setSoggettoFascicolo(soggetto);
		
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return scheda;
	}

	public static IValSinba initISchedaSinba(CsDValutazione val,CsASoggettoLAZY soggetto) throws Exception {
		IValSinba interfaccia = null;
		if (val != null) {
			String className = val.getVersioneScheda();

			// la versione di default è utile se si vuole comunque
			// instanziare una versione intermedia tra la prima e la max
			String defaultVersion = "";
			interfaccia = (IValSinba) WebredClassFactory.newInstance(className, IValSinba.class, defaultVersion);
			interfaccia.setIdCaso(soggetto.getCsACaso().getId());
			interfaccia.setSoggettoFascicolo(soggetto);
			
			// Initialize scheda barthel
			interfaccia.init(null, val);
		}
		return interfaccia;
	}
	
	public static IValSinba initISchedaSinbaRow(CsDValutazione val,CsASoggettoLAZY soggetto) throws Exception {
		IValSinba interfaccia = null;
		if (val != null) {
			String className = val.getVersioneScheda();

			// la versione di default è utile se si vuole comunque
			// instanziare una versione intermedia tra la prima e la max
			String defaultVersion = "";
			interfaccia = (IValSinba) WebredClassFactory.newInstance(className, IValSinba.class, defaultVersion);
			interfaccia.setIdCaso(soggetto.getCsACaso().getId());
			interfaccia.setSoggettoFascicolo(soggetto);
			// Initialize scheda barthel
			interfaccia.initRowList(null, val);
		}
		return interfaccia;
	}

	
	@Override
	public void setSoggettoFascicolo(CsASoggettoLAZY soggetto) {
		this.soggetto=soggetto;
		//setIdCaso(soggetto.getCsACaso().getId());
		loadCommonList();
	}
	
	protected abstract void loadCommonList();
	
}
