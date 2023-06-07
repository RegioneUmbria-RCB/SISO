package it.webred.cs.csa.web.manbean.fascicolo.provvedimentiMinori;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.json.provvedimentiMinori.IProvvedimentiMinori;
import it.webred.cs.json.provvedimentiMinori.ProvvedimentiMinoriManBaseBean;
import it.webred.cs.json.provvedimentiMinori.ProvvedimentiMinoriRowBean;

public class LazyListaProvvedimentiModel extends LazyDataModel<ProvvedimentiMinoriRowBean> {

	private static final long serialVersionUID = 1L;
	public static Logger logger = Logger.getLogger("carsociale.log");
	CsASoggettoLAZY soggetto;
	// scrivere funzione setup e creazione lista
	// hide dialogo dopo salvattaggio
	private SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
	
	public void setup(CsASoggettoLAZY soggetto){
		this.soggetto = soggetto;
	}

	@Override
	public ProvvedimentiMinoriRowBean getRowData(String rowKey) {
		return null;
	}

	@Override
	public Object getRowKey(ProvvedimentiMinoriRowBean provvedimento) {
		return provvedimento.getIdRow();
	}

	private List<CsDValutazione> caricaListaProvvedimenti(){
		
		List<CsDValutazione> schede = new ArrayList<CsDValutazione>();
		AccessTableDiarioSessionBeanRemote diarioService = (AccessTableDiarioSessionBeanRemote) CsUiCompBaseBean.getCarSocialeEjb("AccessTableDiarioSessionBean");
		BaseDTO dto = new BaseDTO();
		CsUiCompBaseBean.fillEnte(dto);
		dto.setObj2(DataModelCostanti.TipoDiario.PROVVEDIMENTI_TRIBUNALE);

		if (this.soggetto != null) {
			dto.setObj(soggetto.getAnagraficaId());
		}
		schede = diarioService.getSchedeValutazioneSoggetto(dto);
		return schede;
	}
	
	@Override
	public List<ProvvedimentiMinoriRowBean> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
		List<ProvvedimentiMinoriRowBean> listaProvvedimenti = new LinkedList<ProvvedimentiMinoriRowBean>();
		List<CsDValutazione> schede = this.caricaListaProvvedimenti();
		for (CsDValutazione csDValutazione : schede) {
			IProvvedimentiMinori bean;
			try {
				bean = ProvvedimentiMinoriManBaseBean.initIProvvedimentiMinoriRow
						(csDValutazione, csDValutazione.getCsDDiario().getCsACaso().getCsASoggetto());
				ProvvedimentiMinoriRowBean row= new ProvvedimentiMinoriRowBean();
				bean.valorizzaRowList(row);
				row.setIdRow(csDValutazione.getDiarioId());
				row.setDtModifica(csDValutazione.getCsDDiario().getDtMod()!=null ? csDValutazione.getCsDDiario().getDtMod() : csDValutazione.getCsDDiario().getDtIns() );
				row.setOpModifica(csDValutazione.getCsDDiario().getUsrMod()!=null ? csDValutazione.getCsDDiario().getUsrMod() : csDValutazione.getCsDDiario().getUserIns() );
				
				//row.setScheda(csDValutazione);
				listaProvvedimenti.add(row);
			    }catch(Exception e){
			    	logger.error("Errore creazione lista provvedimenti tribunale",e);
			    }
			}

		int dataSize = listaProvvedimenti.size();
		this.setRowCount(dataSize);

		return listaProvvedimenti;
	}

	/*	private ProvvedimentiMinoriBean schedaJson(CsDValutazione schedaJson)
	{
		ProvvedimentiMinoriBean bean=null;
		String jsonObj = schedaJson.getCsDDiario().getCsDClob().getDatiClob();
		ObjectMapper objectMapper = new ObjectMapper();

		try {

			 bean= objectMapper.readValue(jsonObj, ProvvedimentiMinoriBean.class);

		} catch (Exception e) {
		
			CsUiCompBaseBean.addErrorFromProperties("Errore nel caricamento dei provvedimenti");
			CsUiCompBaseBean.logger.error(e.getMessage(), e);
		}
		return bean;
	}*/

	@SuppressWarnings("rawtypes")
	protected void elaboraCriteriFiltro(Map filters, ErogazioniSearchCriteria searchCriteria) {
		// String cognome = (String) filters.get("dataProvv");
		// String nome = (String) filters.get("tipoProvv");

	}

	public boolean isEmpty() {
		return this.caricaListaProvvedimenti().isEmpty();
	}

}
