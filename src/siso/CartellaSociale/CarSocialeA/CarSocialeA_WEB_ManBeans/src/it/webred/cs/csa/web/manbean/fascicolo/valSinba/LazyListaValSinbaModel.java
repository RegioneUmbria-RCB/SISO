package it.webred.cs.csa.web.manbean.fascicolo.valSinba;

import it.webred.cs.csa.ejb.client.AccessTableSinbaSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.csa.ejb.dto.InfoExportSinbaDTO;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsDSinba;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.json.valSinba.IValSinba;
import it.webred.cs.json.valSinba.ValSinbaRowBean;
import it.webred.cs.json.valSinba.ver1.ValSinbaManBean;
import it.webred.ejb.utility.ClientUtility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class LazyListaValSinbaModel extends LazyDataModel<ValSinbaRowBean> {

	private static final long serialVersionUID = 1L;
	public static Logger logger = Logger.getLogger("carsociale.log");
	CsASoggettoLAZY soggetto;
	
	private List<CsDSinba> csDSinbas;
	
	private Long lastSinbaInsertedId;
	private Long lastSinbaExportedId;
	
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

	public List<CsDSinba> caricaListaSchede(){
		csDSinbas = new ArrayList<CsDSinba>();
		BaseDTO dto = new BaseDTO();
		CsUiCompBaseBean.fillEnte(dto);
		dto.setObj(soggetto.getCsACaso().getId());
	
		try {
			AccessTableSinbaSessionBeanRemote sinbaService = (AccessTableSinbaSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableSinbaSessionBean");
			
			csDSinbas = sinbaService.getListaSchedaSinbaByCaso(dto);
	
			// valorizzo id dell'ultimo sinba inserito
			List<CsDSinba> tempSinbas = new ArrayList<CsDSinba>();
			//SISO-777 è necessario trovare anche l'ultimo Sinba esportato per gestire la visualizzazione delle funzioni copia e modifica
			//come da test 19022019
			List<CsDSinba> tempSinbasExported = new ArrayList<CsDSinba>();
			if (csDSinbas != null){
				// rimuovo sinba esportati
				Iterator<CsDSinba> iterator = csDSinbas.iterator();
				while (iterator.hasNext()) {
					CsDSinba elem = iterator.next();
					if (elem.getDataExport() == null)
						tempSinbas.add(elem);
					else
						tempSinbasExported.add(elem);
	
				}
				Collections.sort(tempSinbas, new Comparator<CsDSinba>() {
	
					@Override
					public int compare(CsDSinba o1, CsDSinba o2) {
						// TODO Auto-generated method stub
						return o1.getCsDDiario().getDtIns().compareTo(o2.getCsDDiario().getDtIns());
					}
	
				});
				Collections.sort(tempSinbasExported, new Comparator<CsDSinba>() {
	
					@Override
					public int compare(CsDSinba o1, CsDSinba o2) {
						// TODO Auto-generated method stub
						return o1.getCsDDiario().getDtIns().compareTo(o2.getCsDDiario().getDtIns());
					}
	
				});
	
			}
	
			if(tempSinbas.size()>0){
				CsDSinba lastSinba = tempSinbas.get(tempSinbas.size() - 1);
				this.setLastSinbaInsertedId(lastSinba.getDiarioId());
			}
			else  this.setLastSinbaInsertedId(0L);
	
			if(tempSinbasExported.size()>0){
				CsDSinba lastSinba = tempSinbasExported.get(tempSinbasExported.size() - 1);
				this.setLastSinbaExportedId(lastSinba.getDiarioId());
			}
			else  this.setLastSinbaExportedId(0L);
	
			
			Collections.sort(csDSinbas, new Comparator<CsDSinba>() {
	
				@Override
				public int compare(CsDSinba o1, CsDSinba o2) {
					// TODO Auto-generated method stub
					return o2.getCsDDiario().getDtIns().compareTo(o1.getCsDDiario().getDtIns());
				}
	
			});
		} catch (Exception e) {
			logger.error(e);
		}
		
		return csDSinbas;
	}
	
	@Override
	public List<ValSinbaRowBean> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
		List<ValSinbaRowBean> lista = new LinkedList<ValSinbaRowBean>();
		List<CsDSinba> schede = this.caricaListaSchede();
			for (CsDSinba csDSinba : schede) {
				IValSinba bean; 
			    try{			
				
				ValSinbaRowBean row= new ValSinbaRowBean();
				
				/*Al momento non è necessario visto che non vengono prelevati dati direttamente dal JSON
				bean = ValSinbaManBean.initISchedaSinbaRow(csDSinba, csDSinba.getCsDDiario().getCsACaso().getCsASoggetto());
				bean.valorizzaRowList(row); //Campi del JSON
				*/
				
				row.setIdRow(csDSinba.getDiarioId());
				row.setDataValutazione(csDSinba.getCsDDiario().getDtAmministrativa());
				row.setDataExport(csDSinba.getDataExport());
				row.setIdentificazioneFlusso(csDSinba.getCsDExportSinba()!=null ? csDSinba.getCsDExportSinba().getIdentificazioneFlusso() : null);
				row.setDtModifica(CsUiCompBaseBean.getDataUltimaModifica(csDSinba.getCsDDiario()));
				row.setOpModifica(CsUiCompBaseBean.getOpUltimaModifica(csDSinba.getCsDDiario()));
				row.setScheda(csDSinba);		
				lista.add(row);
			    }catch(Exception e){
			    	logger.error("Errore creazione lista schede sinba",e);
			    }
			}

			int dataSize = lista.size();
			this.setRowCount(dataSize);
		
		return lista;
	}
	
	public IValSinba getLastValMultidim() throws Exception {
		
		List<CsDSinba> lst = this.caricaListaSchede();
		
		if(lst.isEmpty()){
			throw new Exception();
		}
		
		//ultimo in base alla data amministrativa desc
		Collections.sort(lst, Collections.reverseOrder(new Comparator<CsDSinba>() {

			@Override
			public int compare(CsDSinba o1, CsDSinba o2) {
				return o1.getCsDDiario().getDtIns().compareTo(o2.getCsDDiario().getDtIns());
			}
			
		}));
		
	    CsDSinba sinba = lst.get(0);	
		return ValSinbaManBean.initISchedaSinba(sinba);
				
	}

	@SuppressWarnings("rawtypes")
	protected void elaboraCriteriFiltro(Map filters, ErogazioniSearchCriteria searchCriteria) {
		// String cognome = (String) filters.get("dataProvv");
		// String nome = (String) filters.get("tipoProvv");

	}

	public boolean isEmpty() {
		return this.caricaListaSchede().isEmpty();
	}

	public Long getLastSinbaInsertedId() {
		return lastSinbaInsertedId;
	}

	public void setLastSinbaInsertedId(Long lastSinbaInsertedId) {
		this.lastSinbaInsertedId = lastSinbaInsertedId;
	}

	public Long getLastSinbaExportedId() {
		return lastSinbaExportedId;
	}

	public void setLastSinbaExportedId(Long lastSinbaExportedId) {
		this.lastSinbaExportedId = lastSinbaExportedId;
	}

	public List<CsDSinba> getCsDSinbas() {
		return csDSinbas;
	}

	public void setCsDSinbas(List<CsDSinba> csDSinbas) {
		this.csDSinbas = csDSinbas;
	}

}
