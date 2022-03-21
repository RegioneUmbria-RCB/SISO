package it.webred.cs.json.valMultidimensionale;

import it.webred.classfactory.WebredClassFactory;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsTbSchedaMultidim;
import it.webred.cs.json.SchedaValutazioneManBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public abstract class ValMultidimensionaleManBaseBean extends SchedaValutazioneManBean implements IValMultidimensionale {

	private static final long serialVersionUID = 1L;
	protected CsASoggettoLAZY soggetto;
	
	protected AccessTableSchedaSessionBeanRemote schedaService = 
			(AccessTableSchedaSessionBeanRemote)getCarSocialeEjb("AccessTableSchedaSessionBean");
	
	protected AccessTableDiarioSessionBeanRemote diarioService = (AccessTableDiarioSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB",
			"AccessTableDiarioSessionBean");


	public static IValMultidimensionale initISchedaMultidimensionale(String defaultVersion,CsASoggettoLAZY soggetto)
	{
		IValMultidimensionale scheda = null;
		try {
			scheda = (IValMultidimensionale) WebredClassFactory.newInstance("", IValMultidimensionale.class, defaultVersion);
			scheda.setIdCaso(soggetto.getCsACaso().getId());
			scheda.setSoggettoFascicolo(soggetto);
			scheda.init(scheda);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return scheda;
	}

	public static IValMultidimensionale initISchedaMultidimensionale(CsDValutazione val,CsASoggettoLAZY soggetto) throws Exception {
		IValMultidimensionale interfaccia = null;
		if (val != null) {
			String className = val.getVersioneScheda();

			// la versione di default è utile se si vuole comunque
			// instanziare una versione intermedia tra la prima e la max
			String defaultVersion = "";
			interfaccia = (IValMultidimensionale) WebredClassFactory.newInstance(className, IValMultidimensionale.class, defaultVersion);
			interfaccia.setIdCaso(soggetto.getCsACaso().getId());
			interfaccia.setSoggettoFascicolo(soggetto);
			
			// Initialize scheda barthel
			interfaccia.init(null, val);
		}
		return interfaccia;
	}
	
	public static IValMultidimensionale initISchedaMultidimensionaleRow(CsDValutazione val,CsASoggettoLAZY soggetto) throws Exception {
		IValMultidimensionale interfaccia = null;
		if (val != null) {
			String className = val.getVersioneScheda();//caricamento degl'elementi della lista schede

			// la versione di default è utile se si vuole comunque
			// instanziare una versione intermedia tra la prima e la max
			String defaultVersion = "";
			interfaccia = (IValMultidimensionale) WebredClassFactory.newInstance(className, IValMultidimensionale.class, defaultVersion);
			interfaccia.setIdCaso(soggetto.getCsACaso().getId());
			interfaccia.setSoggettoFascicolo(soggetto);
			// Initialize scheda barthel
			interfaccia.initRowList(null, val);
		}
		return interfaccia;
	}

	private List<SelectItem> elaboraListaSI(List<CsTbSchedaMultidim> lst){
		List<SelectItem> siList = new ArrayList<SelectItem>();
		for(CsTbSchedaMultidim tb : lst){
			SelectItem si = new SelectItem(tb.getCodice(), tb.getDescrizione());
			si.setDisabled(!tb.getAbilitato());
			siList.add(si);
		}
		return siList;
	}

	protected void loadListaSI(String tipo, List<CsTbSchedaMultidim> tooltip, List<SelectItem> valori){
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(tipo);
		
		List<CsTbSchedaMultidim> tooltipTmp = confService.getParamsSchedaMultidim(dto);
		if(tooltip!=null) tooltip.addAll(tooltipTmp);
		if(valori!=null)  valori.addAll(elaboraListaSI(tooltipTmp));
	}
	

	
	protected String getLabelByCod(String tipo, Object codice){
		
		String s = "";
		String sCodice = codice!=null ? codice.toString() : null;
		
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(tipo);
		dto.setObj2(sCodice);
		CsTbSchedaMultidim sm = confService.getParamSchedaMultidim(dto);
		if(sm!=null){
			s = sm.getDescrizione();
			s+= (sm.getTooltip()!=null && !sm.getTooltip().isEmpty()) ? " ["+sm.getTooltip()+"]" : "";
		}
		return s;
	}
	
	@Override
	public void setSoggettoFascicolo(CsASoggettoLAZY soggetto) {
		this.soggetto=soggetto;
		//setIdCaso(soggetto.getCsACaso().getId());
		loadCommonList();
	}
	
	protected abstract void loadCommonList();
	
}
