package it.webred.cs.csa.web.manbean.fascicolo.valSinba;

import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.json.valMultidimensionale.ValMultidimensionaleRowBean;
import it.webred.cs.json.valMultidimensionale.ver1.ValMultidimensionaleManBean;
import it.webred.cs.json.valSinba.IValSinba;
import it.webred.cs.json.valSinba.ValSinbaRowBean;
import it.webred.cs.json.valSinba.ver1.ValSinbaManBean;
import it.webred.ejb.utility.ClientUtility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import org.jboss.logging.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class LazyListaValSinbaModel extends LazyDataModel<ValSinbaRowBean> {

	private static final long serialVersionUID = 1L;
	public static Logger logger = Logger.getLogger("carsociale.log");
	CsASoggettoLAZY soggetto;
	// scrivere funzione setup e creazione lista
	// hide dialogo dopo salvattaggio
	private SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");

	public void setup(CsASoggettoLAZY soggetto)
	{
		this.soggetto = soggetto;
	}

	@Override
	public ValSinbaRowBean getRowData(String rowKey) {
		return null;
	}

	@Override
	public Object getRowKey(ValSinbaRowBean scheda) {
		return scheda.getIdRow();
	}

	private List<CsDValutazione> caricaListaSchede(){
		
		List<CsDValutazione> schede = new ArrayList<CsDValutazione>();
		AccessTableDiarioSessionBeanRemote diarioService;
		try {
			diarioService = (AccessTableDiarioSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableDiarioSessionBean");
			BaseDTO dto = new BaseDTO();
			CsUiCompBaseBean.fillEnte(dto);
			dto.setObj2(DataModelCostanti.TipoDiario.VALUTAZIONE_SINBA);

			if (this.soggetto != null)
			{
				dto.setObj(soggetto.getAnagraficaId());
			}

			schede = diarioService.getSchedeValutazioneSoggetto(dto);
			
		} catch (NamingException e) {
			logger.error(e);
		}

		return schede;
	}
	
	@Override
	public List<ValSinbaRowBean> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
		List<ValSinbaRowBean> lista = new LinkedList<ValSinbaRowBean>();
		List<CsDValutazione> schede = this.caricaListaSchede();
			for (CsDValutazione csDValutazione : schede) {
				IValSinba bean; 
			    try{			
				bean = ValSinbaManBean.initISchedaSinbaRow
						(csDValutazione, csDValutazione.getCsDDiario().getCsACaso().getCsASoggetto());
				ValSinbaRowBean row= new ValSinbaRowBean();
				bean.valorizzaRowList(row); //Campi del JSON
				row.setIdRow(csDValutazione.getDiarioId());
				row.setDtModifica(csDValutazione.getCsDDiario().getDtMod()!=null ? csDValutazione.getCsDDiario().getDtMod() : csDValutazione.getCsDDiario().getDtIns() );
				row.setOpModifica(csDValutazione.getCsDDiario().getUsrMod()!=null ? csDValutazione.getCsDDiario().getUsrMod() : csDValutazione.getCsDDiario().getUserIns() );
				row.setScheda(csDValutazione);		
				lista.add(row);
			    }catch(Exception e){
			    	logger.error("Errore creazione lista schede multidimensionali",e);
			    }
			}

			int dataSize = lista.size();
			this.setRowCount(dataSize);
		
		return lista;
	}
	
	public IValSinba getLastValMultidim() throws Exception {
		
		List<CsDValutazione> lst = this.caricaListaSchede();
		if(lst.isEmpty()){
			throw new Exception();
		}
		
		//ultimo in base alla data amministrativa desc
		Collections.sort(lst, Collections.reverseOrder(new Comparator<CsDValutazione>() {

			@Override
			public int compare(CsDValutazione o1, CsDValutazione o2) {
				return o1.getCsDDiario().getDtAmministrativa().compareTo(o2.getCsDDiario().getDtAmministrativa());
			}
			
		}));
		
	    CsDValutazione v = lst.get(0);		
		return ValSinbaManBean.initISchedaSinba(v, v.getCsDDiario().getCsACaso().getCsASoggetto());
				
	}

	@SuppressWarnings("rawtypes")
	protected void elaboraCriteriFiltro(Map filters, ErogazioniSearchCriteria searchCriteria) {
		// String cognome = (String) filters.get("dataProvv");
		// String nome = (String) filters.get("tipoProvv");

	}

	public boolean isEmpty() {
		return this.caricaListaSchede().isEmpty();
	}

}
