package it.webred.cs.csa.web.manbean.fascicolo.colloquio;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ColloquioDTO;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloCompSecondoLivello;
import it.webred.cs.csa.web.manbean.fascicolo.initialize.InitColloquio;
import it.webred.cs.csa.web.manbean.report.ReportBean;
import it.webred.cs.data.model.CsCDiarioConchi;
import it.webred.cs.data.model.CsCDiarioDove;
import it.webred.cs.data.model.CsCTipoColloquio;
import it.webred.cs.data.model.CsDColloquio;
import it.webred.cs.data.model.CsDColloquioBASIC;
import it.webred.cs.jsf.bean.colloquio.ColloquioRowBean;
import it.webred.cs.jsf.bean.colloquio.DatiColloquioBean;
import it.webred.cs.jsf.interfaces.IColloquio;
import it.webred.dto.utility.KeyValuePairBean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

public class ColloquioBean extends FascicoloCompSecondoLivello implements IColloquio {

/*********** Start Colloquio ******************/
	protected String username;
	private List<ColloquioRowBean> listaColloquios;
	
	private ColloquioRowBean selectedRow = new ColloquioRowBean();
	private DatiColloquioBean datiColloquio = new DatiColloquioBean();

	private List<String> infoRiservato;
	
	private List<CsCTipoColloquio> tipoColloquios;
	private List<CsCDiarioDove> diarioDoves;
	private List<CsCDiarioConchi> diarioConchis;
	
	private boolean isSoggetto;
	
	private List<KeyValuePairBean<Long, String>> nameTipoColloquios = new LinkedList<KeyValuePairBean<Long, String>>();
	private List<KeyValuePairBean<Long, String>> nameDiarioDoves = new LinkedList<KeyValuePairBean<Long, String>>();
	private List<KeyValuePairBean<Long, String>> nameDiarioConchis = new LinkedList<KeyValuePairBean<Long, String>>();

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
	public List<KeyValuePairBean<Long, String>> getNameTipoColloquios() {
		return nameTipoColloquios;
	}

	@Override
	public List<KeyValuePairBean<Long,String>> getNameDiarioDoves() {
		return nameDiarioDoves;
	}

	@Override
	public List<KeyValuePairBean<Long,String>> getNameDiarioConchis() {
		return nameDiarioConchis;
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
	
	
	protected void loadListaColloqui(BaseDTO dto) throws Exception {
		loadListaColloqui(dto, InitColloquio.loadListaColloqui(dto));
	}

	protected void loadListaColloqui(BaseDTO dto, List<CsDColloquioBASIC> listaColl) throws Exception {
		
		listaColloquios = new ArrayList<ColloquioRowBean>();
		
		if(listaColl != null) {
			for(CsDColloquioBASIC coll: listaColl) {
				ColloquioRowBean bean = new ColloquioRowBean();
				String usernameMod = coll.getCsDDiario().getUsrMod();
				String usernameOp = usernameMod!=null? usernameMod : coll.getCsDDiario().getUserIns();
				if( StringUtils.isEmpty(usernameOp) )
					usernameOp = coll.getCsDDiario().getUserIns();
				bean.Initialize(coll, usernameOp );
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
		
		csColl.setCsCTipoColloquio(tipoColloquios!=null ? tipoColloquios.get(0) : null);
		csColl.setCsCDiarioConchi(diarioConchis!=null ? diarioConchis.get(0) : null);
		//csColl.setCsCDiarioDove(diarioDoves.get(0));

		datiColloquio.Initialize( csColl, dto.getUserId(), getColloquioRiservatoDefault(), true );
	}

	@Override
	public void initializeData(Object data) {
		
		try{
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			
			if(csASoggetto != null)
				dto.setObj(csASoggetto.getAnagraficaId());
			
			if (nameTipoColloquios==null || nameTipoColloquios.isEmpty()) {
				tipoColloquios = configService.getTipoColloquios(dto);
				for (CsCTipoColloquio item : tipoColloquios)
					nameTipoColloquios.add(new KeyValuePairBean<Long,String>(item.getId(), item.getDescrizione()));
			}
			
			if (nameDiarioDoves==null || nameDiarioDoves.isEmpty()) {
				diarioDoves = configService.getDiarioDoves(dto);
				for (CsCDiarioDove item : diarioDoves)
					nameDiarioDoves.add(new KeyValuePairBean<Long,String>(item.getId(), item.getDescrizione()));
			}
			
			if (nameDiarioConchis==null || nameDiarioConchis.isEmpty()) {
				diarioConchis = configService.getDiarioConchis(dto);
				for (CsCDiarioConchi item : diarioConchis)
					nameDiarioConchis.add(new KeyValuePairBean<Long,String>(item.getId(), item.getDescrizione()));
			}
			
			List<CsDColloquioBASIC> colloqui = (List<CsDColloquioBASIC>) data;
			
			//loadListaColloqui(dto); /*In siso SISO-812 era stata commentata la parte seguente ma era stata introdotta per il miglioramento performance in SISO-576!
			
			 if (colloqui == null) 
		    	loadListaColloqui(dto);
		    else
		    	loadListaColloqui(dto, colloqui);
			
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
			
			dto.setObj(c);
			dto.setObj2(csASoggetto);
			dto.setObj3(getCurrentOpSettore());
			
			diarioService.salvaColloquio(dto);
			
			//Aggiorno la lista dei risultati
			dto.setObj(csASoggetto.getAnagraficaId());
			loadListaColloqui(dto, diarioService.getColloquios(dto));
			addInfo("Info", "Salvataggio Diario avvenuto con successo.");
			
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);	
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("Error", "Errore nel salvataggio del Diario (Colloquio)!");
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


}


