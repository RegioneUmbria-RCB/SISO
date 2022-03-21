package it.webred.cs.csa.web.manbean.fascicolo.provvedimentiMinori;

import it.webred.classfactory.WebredClassFactory;
import it.webred.cs.csa.web.manbean.fascicolo.provvedimentiMinori.ver1.ProvvedimentiMinoriManBean;
import it.webred.cs.csa.web.manbean.fascicolo.provvedimentiMinori.ver1.tabs.DatiProvvedimentoMan;
import it.webred.cs.csa.web.manbean.fascicolo.provvedimentiMinori.ver1.tabs.IntSpecialistiTempiChiusureMan;
import it.webred.cs.csa.web.manbean.fascicolo.provvedimentiMinori.ver1.tabs.InterventiTutelaMan;
import it.webred.cs.csa.web.manbean.fascicolo.provvedimentiMinori.ver1.tabs.PrescrizioniSocioEducativeMan;
import it.webred.cs.csa.web.manbean.fascicolo.provvedimentiMinori.ver1.tabs.PrescrizioniSpecialisticheMan;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.json.SchedaValutazioneManBean;
import it.webred.cs.json.OrientamentoLavoro.IOrientamentoLavoro;

public abstract class ProvvedimentiMinoriManBaseBean extends SchedaValutazioneManBean implements IProvvedimentiMinori {

	private static final long serialVersionUID = 1L;
	protected CsASoggettoLAZY soggetto;

	public static IProvvedimentiMinori initIProvvedimentiMinori(String defaultVersion,CsASoggettoLAZY soggetto)
	{
		IProvvedimentiMinori provvementiMinori = null;
		try {
			provvementiMinori = (IProvvedimentiMinori) WebredClassFactory.newInstance("", IProvvedimentiMinori.class, defaultVersion);
			provvementiMinori.setIdCaso(soggetto.getCsACaso().getId());
			provvementiMinori.setSoggettoFascicolo(soggetto);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return provvementiMinori;
	}

	public static IProvvedimentiMinori initIProvvedimentiMinori(CsDValutazione val,CsASoggettoLAZY soggetto) throws Exception {
		IProvvedimentiMinori interfaccia = null;
		if (val != null) {
			String className = val.getVersioneScheda();

			// la versione di default è utile se si vuole comunque
			// instanziare una versione intermedia tra la prima e la max
			String defaultVersion = "";
			interfaccia = (IProvvedimentiMinori) WebredClassFactory.newInstance(className, IProvvedimentiMinori.class, defaultVersion);
			interfaccia.setIdCaso(soggetto.getCsACaso().getId());
			interfaccia.setSoggettoFascicolo(soggetto);
			// Initialize scheda barthel
			interfaccia.init(null, val);
		}
		return interfaccia;
	}
	
	public static IProvvedimentiMinori initIProvvedimentiMinoriRow(CsDValutazione val,CsASoggettoLAZY soggetto) throws Exception {
		IProvvedimentiMinori interfaccia = null;
		if (val != null) {
			String className = val.getVersioneScheda();

			// la versione di default è utile se si vuole comunque
			// instanziare una versione intermedia tra la prima e la max
			String defaultVersion = "";
			interfaccia = (IProvvedimentiMinori) WebredClassFactory.newInstance(className, IProvvedimentiMinori.class, defaultVersion);
			interfaccia.setIdCaso(soggetto.getCsACaso().getId());
			interfaccia.setSoggettoFascicolo(soggetto);
			// Initialize scheda barthel
			interfaccia.initRowList(null, val);
		}
		return interfaccia;
	}
	
	//Serve per il Task Griornaliero
	public static IProvvedimentiMinori initIProvvedimentiMinoriTask(CsDValutazione val,CsASoggettoLAZY soggetto) throws Exception {
		IProvvedimentiMinori interfaccia = null;
		if (val != null) {
			String className = val.getVersioneScheda();
			String defaultVersion = "";
			interfaccia = (IProvvedimentiMinori) WebredClassFactory.newInstance(className, IProvvedimentiMinori.class, defaultVersion);
			interfaccia.setIdCaso(soggetto.getCsACaso().getId());
			//interfaccia.setSoggettoFascicolo(soggetto);
			interfaccia.initRowListTask(null, val);
		}
		return interfaccia;
	}
  

	
}
