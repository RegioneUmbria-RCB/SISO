package it.webred.cs.json.valMultidimensionale;

import it.webred.classfactory.WebredClassFactory;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsTbSchedaMultidim;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
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

	
	/*Rete Familiare*/
	protected List<CsAComponente> famConvComponentes;
	protected List<CsAComponente> famNonConvComponentes;
	protected List<CsAComponente> famAltriComponentes;
	
	protected List<CsAComponente> dialogFamComponentes;
	protected List<CsAComponente> dialogSelectedFamComponentes;
	
	protected boolean isConvivente;
	protected boolean isParente;
	

	public static IValMultidimensionale initISchedaMultidimensionale(String defaultVersion,CsASoggettoLAZY soggetto)
	{
		IValMultidimensionale scheda = null;
		try {
			scheda = (IValMultidimensionale) WebredClassFactory.newInstance("", IValMultidimensionale.class, defaultVersion);
			scheda.setIdCaso(soggetto.getCsACaso().getId());
			scheda.setSoggettoFascicolo(soggetto, true);
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
			interfaccia.setSoggettoFascicolo(soggetto, true);
			
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
			interfaccia.setSoggettoFascicolo(soggetto, false);
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
	public void setSoggettoFascicolo(CsASoggettoLAZY soggetto, boolean loadDatiComuni) {
		this.soggetto=soggetto;
		//setIdCaso(soggetto.getCsACaso().getId());
		if(loadDatiComuni) {
			loadCommonList();
			loadCommonListaRisorse();
		}
	}
	
	protected abstract void loadCommonList();
	
	private void loadCommonListaRisorse(){

		if (soggetto != null) {
			List<CsAComponente> famAllComponentes = CsUiCompBaseBean.caricaParenti(soggetto.getAnagraficaId(), null);
			logger.debug("loadCommonList ValMultidimensionale "+ soggetto.getAnagraficaId());
			if(famAllComponentes != null) {
				famNonConvComponentes = new ArrayList<CsAComponente>();
				famConvComponentes = new ArrayList<CsAComponente>();
				famAltriComponentes = new ArrayList<CsAComponente>();
				
				for (CsAComponente it : famAllComponentes) {
					if (it.getCsTbTipoRapportoCon().getParente()) {
						if (it.getConvivente())
							famConvComponentes.add(it);
						else 
							famNonConvComponentes.add(it);
					} else {
						famAltriComponentes.add(it);
					}
				}
			}
		}
	}

	@Override
	public List<CsAComponente> getDialogFamComponentes() {
		return dialogFamComponentes;
	}

	@Override
	public List<CsAComponente> getDialogSelectedFamComponentes() {
		return dialogSelectedFamComponentes;
	}

	@Override
	public void setDialogSelectedFamComponentes(List<CsAComponente> dialogSelectedFamComponentes) {
		this.dialogSelectedFamComponentes = dialogSelectedFamComponentes;
	}

	@Override
	public List<CsAComponente> getFamConvComponentes() {
		return famConvComponentes;
	}

	@Override
	public List<CsAComponente> getFamNonConvComponentes() {
		return famNonConvComponentes;
	}

	@Override
	public List<CsAComponente> getFamAltriComponentes() {
		return famAltriComponentes;
	}
	
	public boolean isConvivente() {
		return isConvivente;
	}
	
	public boolean isParente() {
		return isParente;
	}
}
