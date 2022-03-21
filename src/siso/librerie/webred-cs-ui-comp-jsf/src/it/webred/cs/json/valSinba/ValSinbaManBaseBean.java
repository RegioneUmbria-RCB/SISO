package it.webred.cs.json.valSinba;

import it.webred.classfactory.WebredClassFactory;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTablePaiAffidoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSinbaSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.domini.AccessTableDominiPaiSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsDSinba;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.json.SchedaValutazioneManBean;

import java.util.List;

import org.primefaces.context.RequestContext;

public abstract class ValSinbaManBaseBean extends SchedaValutazioneManBean implements IValSinba {

	private static final long serialVersionUID = 1L;
	protected CsASoggettoLAZY soggetto;
	protected List<String> codiciPrestazioniDb;	// SISO-777
	protected List<String> codiciPrestazioniJson;	// SISO-777
	
	protected AccessTableSchedaSessionBeanRemote schedaService = 
			(AccessTableSchedaSessionBeanRemote)getCarSocialeEjb("AccessTableSchedaSessionBean");
	
	protected AccessTableDiarioSessionBeanRemote diarioService = (AccessTableDiarioSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB",
			"AccessTableDiarioSessionBean");

	protected AccessTableSoggettoSessionBeanRemote soggettoService = 
			(AccessTableSoggettoSessionBeanRemote) getCarSocialeEjb("AccessTableSoggettoSessionBean");

	protected AccessTableSinbaSessionBeanRemote sinbaService = 
			(AccessTableSinbaSessionBeanRemote) getCarSocialeEjb("AccessTableSinbaSessionBean");

	protected AccessTablePaiAffidoSessionBeanRemote paiService = 
			(AccessTablePaiAffidoSessionBeanRemote) getCarSocialeEjb("AccessTablePaiAffidoSessionBean");

	protected AccessTableDominiPaiSessionBeanRemote  dominiService = 
			(AccessTableDominiPaiSessionBeanRemote) getCarSocialeEjb("AccessTableDominiPaiSessionBean");

	public static IValSinba initISchedaSinba(String defaultVersion,CsASoggettoLAZY soggetto)
	{
		IValSinba interfaccia = null;
		try {
			interfaccia = (IValSinba) WebredClassFactory.newInstance("", IValSinba.class, defaultVersion);
			interfaccia.init(soggetto); 
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return interfaccia;
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
	
	public static IValSinba initISchedaSinba(CsDSinba val) throws Exception {
		IValSinba interfaccia = null;
		if (val != null) {
			
			String className = val.getCsDValutazione().getVersioneScheda();

			// la versione di default è utile se si vuole comunque
			// instanziare una versione intermedia tra la prima e la max
			String defaultVersion = "";
			interfaccia = (IValSinba) WebredClassFactory.newInstance(className, IValSinba.class, defaultVersion);
			interfaccia.setIdCaso(val.getCsDDiario().getCsACaso().getId());
			interfaccia.setSoggettoFascicolo(val.getCsDDiario().getCsACaso().getCsASoggetto());
			
			// Initialize scheda barthel
			interfaccia.init(val);
		}
		return interfaccia;
	}
	
	public static IValSinba initISchedaSinbaRow(CsDSinba val,CsASoggettoLAZY soggetto) throws Exception {
		IValSinba interfaccia = null;
		if (val != null) {
			String className = val.getCsDValutazione().getVersioneScheda();

			// la versione di default è utile se si vuole comunque
			// instanziare una versione intermedia tra la prima e la max
			String defaultVersion = "";
			interfaccia = (IValSinba) WebredClassFactory.newInstance(className, IValSinba.class, defaultVersion);
			interfaccia.setIdCaso(soggetto.getCsACaso().getId());
			interfaccia.setSoggettoFascicolo(soggetto);
			// Initialize scheda barthel
			interfaccia.initRowList(val);
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
	
	@Override
	public List<String> getCodiciPrestazioniDb() {
		return codiciPrestazioniDb;
	}

	@Override
	public List<String> getCodiciPrestazioniJson() {
		return codiciPrestazioniJson;
	}
	
	//SISO-777 i codici delle prestazioni erogate vengono estratti per il soggetto in riferimento ad una data e allo stato
	public static List<String> leggiCodiciPrestazione(String cf, String dtIniRiferimento, String dtFineRiferimento, String tipo){
		AccessTableSinbaSessionBeanRemote sinbaService = 
				(AccessTableSinbaSessionBeanRemote) getCarSocialeEjb("AccessTableSinbaSessionBean");
		
		BaseDTO bdto = new BaseDTO();
		fillEnte(bdto);
		bdto.setObj(cf);
		bdto.setObj2(dtIniRiferimento);
		bdto.setObj3(dtFineRiferimento);
		bdto.setObj4(tipo);
		
		List<String> codiciPrestazioni = sinbaService.findCodiciPrestazione(bdto);
		return codiciPrestazioni;
	}
	
	@Override
	public void visualizzaPnlAggiornaPrestazioni(){
		
		RequestContext context = RequestContext.getCurrentInstance();
 	    context.execute("PF('aggiornaPrestazioni').show();");
		
 	}
	
	
	//SISO-1203 i codice Esito Progetto
		public String leggiCodiceEsito(String motivoChiusura){
			AccessTableSinbaSessionBeanRemote sinbaService = 
					(AccessTableSinbaSessionBeanRemote) getCarSocialeEjb("AccessTableSinbaSessionBean");
			
			BaseDTO bdto = new BaseDTO();
			fillEnte(bdto);
			bdto.setObj(motivoChiusura);
					
			String codiceSinbaMotivoChiusura = confService.findCodiceSinbaMotivoChiusura(bdto);
			return codiceSinbaMotivoChiusura;
		}
}
