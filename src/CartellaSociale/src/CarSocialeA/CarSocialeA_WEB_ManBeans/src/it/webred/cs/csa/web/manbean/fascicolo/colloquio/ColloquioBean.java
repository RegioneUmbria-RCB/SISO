package it.webred.cs.csa.web.manbean.fascicolo.colloquio;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloCompBaseBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsCDiarioConchi;
import it.webred.cs.data.model.CsCDiarioDove;
import it.webred.cs.data.model.CsCTipoColloquio;
import it.webred.cs.data.model.CsDColloquio;
import it.webred.cs.data.model.CsDColloquioBASIC;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsTbTipoDiario;
import it.webred.cs.jsf.bean.colloquio.ColloquioRowBean;
import it.webred.cs.jsf.bean.colloquio.DatiColloquioBean;
import it.webred.cs.jsf.interfaces.IColloquio;
import it.webred.dto.utility.KeyValuePairBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

public class ColloquioBean extends FascicoloCompBaseBean implements IColloquio {

/*********** Start Colloquio ******************/
	protected String username;
	private List<ColloquioRowBean> listaColloquios;
	
	private ColloquioRowBean selectedRow = new ColloquioRowBean();
	private DatiColloquioBean datiColloquio = new DatiColloquioBean();

	private List<CsCTipoColloquio> tipoColloquios;
	private List<CsCDiarioDove> diarioDoves;
	private List<CsCDiarioConchi> diarioConchis;
	
	private boolean isSoggetto;
	
	private List<KeyValuePairBean<Long, String>> nameTipoColloquios = new LinkedList<KeyValuePairBean<Long, String>>();
	private List<KeyValuePairBean<Long, String>> nameDiarioDoves = new LinkedList<KeyValuePairBean<Long, String>>();
	private List<KeyValuePairBean<Long, String>> nameDiarioConchis = new LinkedList<KeyValuePairBean<Long, String>>();

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
		
		listaColloquios = new ArrayList<ColloquioRowBean>();
				
		List<CsDColloquioBASIC> listaColl = diarioService.getColloquios(dto);
	
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
		
		csColl.setRiservato("0");
		csColl.setCsCTipoColloquio(tipoColloquios.get(0));
		csColl.setCsCDiarioConchi(diarioConchis.get(0));
		//csColl.setCsCDiarioDove(diarioDoves.get(0));

		datiColloquio.Initialize( csColl, dto.getUserId(), true, true );
	}

	@Override
	public void initializeData() {
		
		try{
			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			
			if(csASoggetto != null)
				dto.setObj(csASoggetto.getAnagraficaId());
			
			tipoColloquios = diarioService.getTipoColloquios(dto);
			for (CsCTipoColloquio item : tipoColloquios)
				nameTipoColloquios.add(new KeyValuePairBean<Long,String>(item.getId(), item.getDescrizione()));
			
			diarioDoves = diarioService.getDiarioDoves(dto);
			for (CsCDiarioDove item : diarioDoves)
				nameDiarioDoves.add(new KeyValuePairBean<Long,String>(item.getId(), item.getDescrizione()));
			
			diarioConchis = diarioService.getDiarioConchis(dto);
			for (CsCDiarioConchi item : diarioConchis)
				nameDiarioConchis.add(new KeyValuePairBean<Long,String>(item.getId(), item.getDescrizione()));
		
			loadListaColloqui(dto);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addErrorFromProperties("caricamento.error");
		}
	}
	
	private String ripulisciTesto(String testoOrig){
		String testoFinale = testoOrig;
		while(testoFinale.contains("<xml>")){
			testoFinale = testoFinale.replaceAll("(?s)<!--.*?-->", "");
			int indexIni = testoFinale.indexOf("<xml>");
			int indexFin = testoFinale.indexOf("</xml>");
			if(indexFin>indexIni)
				testoFinale = testoFinale.substring(0, indexIni) + testoFinale.substring(indexFin+6);
		}
		return testoFinale;
	}
	
	@Override
	public void onChangeDiarioConChi(){
		if(datiColloquio.getDiarioConChiSelected()!=9999)
			this.datiColloquio.getColloquio().setDiarioConChiAltro(null);
	}

	@Override
	public void saveColloquio()  {
		
		try {

			BaseDTO colloquioDto = new BaseDTO();
			fillEnte(colloquioDto);
			
			CsCTipoColloquio tipoColloquio = null;
			CsCDiarioDove diarioDove = null; 
			CsCDiarioConchi diarioConchi = null; 

			// Set Tipo colloquio selected into colloquio
			for (CsCTipoColloquio item : tipoColloquios) {
				if (item.getId() == datiColloquio.getDiarioTipoSelected()) {
					tipoColloquio = item;
					break;
				}
			}

			// Set diario Dove selected into colloquio
			for (CsCDiarioDove item : diarioDoves) {
				if (item.getId() == datiColloquio.getDiarioDoveSelected()) {
					diarioDove = item;
					break;
				}
			}

			// Set diario Conchi selected into colloquio
			for (CsCDiarioConchi item : diarioConchis) {
				if (item.getId() == datiColloquio.getDiarioConChiSelected()) {
					diarioConchi = item;
					break;
				}
			}

			if (diarioDove != null ) 	datiColloquio.getColloquio().setCsCDiarioDove(diarioDove);
			else                     	datiColloquio.getColloquio().setCsCDiarioDove(null);
			if (diarioConchi != null ) 	datiColloquio.getColloquio().setCsCDiarioConchi(diarioConchi);
			if (tipoColloquio != null ) datiColloquio.getColloquio().setCsCTipoColloquio(tipoColloquio);

			if( datiColloquio.isAbilitato4riservato() ) {
				//selectedColloquio.getColloquio().setTestoDiario(selectedColloquio.getCampoTesto());
				datiColloquio.getColloquio().setRiservato(datiColloquio.isRiservato()?"1":"0");
			}
			
			//Ripulisco il contenuto testuale da tag relativi a formattazione word
			String testoDiario = datiColloquio.getColloquio().getTestoDiario();
			datiColloquio.getColloquio().setTestoDiario(ripulisciTesto(testoDiario));

			// New Colloquio
			if (datiColloquio.getColloquio().getDiarioId() == null) {

				IterDTO itDto = new IterDTO();
				fillEnte(itDto);
				itDto.setIdCaso(csASoggetto.getCsACaso().getId());

				CsACaso caso = casoService.findCasoById(itDto);

				BaseDTO tipoDiarioIdDTO = new BaseDTO();
				fillEnte(tipoDiarioIdDTO);

				CsTbTipoDiario tipo = new CsTbTipoDiario(); 
			    tipo.setId(new Long(DataModelCostanti.TipoDiario.COLLOQUIO_ID)); 
			    
				CsOOperatoreSettore opSettore = (CsOOperatoreSettore) getSession().getAttribute("operatoresettore");
				
				datiColloquio.getColloquio().getCsDDiario().setCsACaso(caso);
				datiColloquio.getColloquio().getCsDDiario().setCsTbTipoDiario(tipo);
				datiColloquio.getColloquio().getCsDDiario().setResponsabileCaso(this.getOperResponsabileCaso().getId());
				datiColloquio.getColloquio().getCsDDiario().setCsOOperatoreSettore(opSettore);
				datiColloquio.getColloquio().getCsDDiario().setDtIns(new Date());
				datiColloquio.getColloquio().getCsDDiario().setUserIns(tipoDiarioIdDTO.getUserId());
				
				colloquioDto.setObj(datiColloquio.getColloquio());
				diarioService.saveDiarioChild(colloquioDto);
				
				long diarioId = datiColloquio.getColloquio().getCsDDiario().getId();
				datiColloquio.getColloquio().setDiarioId(diarioId);
				
				//listaColloquios.add(selectedColloquio);
				
			} else {
				datiColloquio.getColloquio().getCsDDiario().setUsrMod(colloquioDto.getUserId());
				datiColloquio.getColloquio().getCsDDiario().setDtMod(new Date());
				colloquioDto.setObj(datiColloquio.getColloquio());

				diarioService.updateColloquio(colloquioDto);
			}
			
			//Aggiorno la lista dei risultati
			colloquioDto.setObj(csASoggetto.getAnagraficaId());
			loadListaColloqui(colloquioDto);
			
			addInfo("Info", "Salvataggio Diario avvenuto con successo.");
			
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);	
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("Error", "Errore nel salvataggio del Diario (Colloquio)!");
		}
	}
	
}


