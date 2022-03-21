package it.webred.cs.csa.web.manbean.fascicolo.colloquio;

import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ColloquioDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.fascicolo.colloquio.ListaDatiColloquioDTO;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloCompSecondoLivello;
import it.webred.cs.csa.web.manbean.report.ReportBean;
import it.webred.cs.data.model.CsDColloquio;
import it.webred.cs.jsf.bean.colloquio.ColloquioRowBean;
import it.webred.cs.jsf.bean.colloquio.DatiColloquioBean;
import it.webred.cs.jsf.interfaces.IColloquio;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

public class ColloquioBean extends FascicoloCompSecondoLivello implements IColloquio {

/*********** Start Colloquio ******************/
	protected String username;
	private List<ColloquioRowBean> listaColloquios;
	
	private ColloquioRowBean selectedRow = new ColloquioRowBean();
	private DatiColloquioBean datiColloquio = new DatiColloquioBean();

	private List<String> infoRiservato;
	
	private boolean isSoggetto;
	
	private List<SelectItem> nameTipoColloquios = new LinkedList<SelectItem>();
	private List<SelectItem> nameDiarioDoves = new LinkedList<SelectItem>();
	private List<SelectItem> nameDiarioConchis = new LinkedList<SelectItem>();

	private ReportBean reportS;
	
	public ColloquioRowBean getSelectedRow() {
		return selectedRow;
	}
	
	public void setSelectedRow(ColloquioRowBean row) throws Exception {
		selectedRow = row;
		onSelectedColloquio(row);
	}

	public DatiColloquioBean getDatiColloquio() {
		return datiColloquio;
	}
	
	@Override
	public List<ColloquioRowBean> getListaColloquios() {
		return listaColloquios;
	}

	@Override
	public boolean isSoggetto() {
		isSoggetto = getSession().getAttribute("soggetto") == null ? true :false;
		return isSoggetto;
	}
	
	protected void aggiornaListaColloqui(List<ListaDatiColloquioDTO> listaColl) throws Exception {
		
		listaColloquios = new ArrayList<ColloquioRowBean>();
		if(listaColl != null) {
			for(ListaDatiColloquioDTO coll: listaColl) {
				ColloquioRowBean bean = new ColloquioRowBean();
				bean.Initialize(coll);
				listaColloquios.add(bean);
			}
		}
	}

	protected void onSelectedColloquio(ColloquioRowBean row) throws Exception{
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(row.getDiarioId());
		
		CsDColloquio coll = diarioService.findColloquioById(dto);
		datiColloquio.Initialize( coll, row.getUsernameOp(), row.isRiservato(), row.isAbilitato4riservato() );
	}

	@Override
	public void OnNewColloquio() throws Exception{
		
		BaseDTO dto = new BaseDTO();
		fillEnte(dto); 
 
		CsDColloquio csColl = new CsDColloquio();
		
		//csColl.setCsCTipoColloquio(tipoColloquios!=null ? tipoColloquios.get(0) : null);
		//csColl.setCsCDiarioConchi(diarioConchis!=null ? diarioConchis.get(0) : null);
		//csColl.setCsCDiarioDove(diarioDoves.get(0));

		datiColloquio.Initialize( csColl, dto.getUserId(), getColloquioRiservatoDefault(), true );
	}

	@Override
	public void initializeData(Object data) {
		
		try{
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			
			if (nameTipoColloquios==null || nameTipoColloquios.isEmpty()) {
				List<KeyValueDTO> tipoColloquios = confService.getTipoColloquios(dto);
				nameTipoColloquios = convertiLista(tipoColloquios);
			}
			
			if (nameDiarioDoves==null || nameDiarioDoves.isEmpty()) {
				List<KeyValueDTO> diarioDoves = confService.getDiarioDoves(dto);
				nameDiarioDoves = convertiLista(diarioDoves);
			}
			
			if (nameDiarioConchis==null || nameDiarioConchis.isEmpty()) {
				List<KeyValueDTO> diarioConchis = confService.getDiarioConchis(dto);
				nameDiarioConchis = convertiLista(diarioConchis);
			}
			
			List<ListaDatiColloquioDTO> colloqui = (List<ListaDatiColloquioDTO>) data;
			
			//loadListaColloqui(dto); /*In siso SISO-812 era stata commentata la parte seguente ma era stata introdotta per il miglioramento performance in SISO-576!
			dto.setObj(idCaso);
			if (colloqui == null)
				colloqui = diarioService.findColloquiByCaso(dto);
		    aggiornaListaColloqui(colloqui);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addErrorFromProperties("caricamento.error");
		}
	}
		
	@Override
	public void onChangeDiarioConChi(){
		if(datiColloquio.getDiarioConChiSelected()!=9999)
			this.datiColloquio.getColloquio().setDiarioConChiAltro(null);
	}


	@Override
	protected void save(){	
		try {

			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			
			ColloquioDTO c = new ColloquioDTO();
			c.setColloquio(datiColloquio.getColloquio());
			c.setDiarioConChiSelected(datiColloquio.getDiarioConChiSelected());
			c.setDiarioDoveSelected(datiColloquio.getDiarioDoveSelected());
			c.setDiarioTipoSelected(datiColloquio.getDiarioTipoSelected());
			c.setAbilitato4riservato(datiColloquio.isAbilitato4riservato());
			c.setRiservato(datiColloquio.isRiservato());
			c.setVisSecondoLivello(this.toSaveSecondoLivello ? getCurrentOpSettore().getCsOSettore().getId() : null);
			
			dto.setObj(idCaso);
			dto.setObj2(c);
			dto.setObj3(getCurrentOpSettore());
			
			diarioService.salvaColloquio(dto);
			
			//Aggiorno la lista dei risultati
			dto.setObj2(null);
			dto.setObj3(null);
			List<ListaDatiColloquioDTO> colloqui = diarioService.findColloquiByCaso(dto);
			aggiornaListaColloqui(colloqui);
			addInfo("Info", "Salvataggio Diario avvenuto con successo.");
			
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);	
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("Errore", "Errore nel salvataggio del Diario");
		}
	}

	public List<String> getInfoRiservato() {
		infoRiservato = new ArrayList<String>();
		
		infoRiservato.add("Gestore del diario (inserimento/modifica)");
		infoRiservato.add("Responsabile del Caso");
		infoRiservato.add("Responsabile del Settore");
		
		return infoRiservato;
	}

	@Override
	public void esportaStampa(ColloquioRowBean row) throws Exception {
	    onSelectedColloquio(row);
		reportS = new ReportBean();
		reportS.esportaDiario(datiColloquio);
	}

	@Override
	public List<SelectItem> getNameTipoColloquios() {
		return this.nameTipoColloquios;
	}

	@Override
	public List<SelectItem> getNameDiarioDoves() {
		return this.nameDiarioDoves;
	}

	@Override
	public List<SelectItem> getNameDiarioConchis() {
		return this.nameDiarioConchis;
	}


}


