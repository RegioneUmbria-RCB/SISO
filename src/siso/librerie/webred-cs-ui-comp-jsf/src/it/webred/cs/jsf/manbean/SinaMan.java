package it.webred.cs.jsf.manbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.SelectEvent;

import it.webred.cs.csa.ejb.client.AccessTableSchedaSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSinaSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.sina.SinaEsegDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.CSIPs.FLAG_IN_CARICO;
import it.webred.cs.data.DataModelCostanti.PermessiFascicolo;
import it.webred.cs.data.DataModelCostanti.TipoSinaDomanda;
import it.webred.cs.data.model.ArTbPrestazioniInps;
import it.webred.cs.data.model.CsTbSinaDomanda;
import it.webred.cs.jsf.bean.erogazioneIntervento.InterventoErogazHistoryRowBean;
import it.webred.cs.jsf.interfaces.ISina;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.json.valMultidimensionale.ver2.ValMultidimensionaleBean;

public class SinaMan extends CsUiCompBaseBean implements Serializable, ISina {

	private static final long serialVersionUID = 1L;

	protected AccessTableSinaSessionBeanRemote sinaService = (AccessTableSinaSessionBeanRemote) getEjb("CarSocialeA",
			"CarSocialeA_EJB", "AccessTableSinaSessionBean");

	protected AccessTableSchedaSessionBeanRemote schedaService = (AccessTableSchedaSessionBeanRemote) getEjb(
			"CarSocialeA", "CarSocialeA_EJB", "AccessTableSchedaSessionBean");

	private boolean sovrascriviSinaCorrente = false;
	
	// DIARIO ID
	private Long diarioSinaId;
	private ValMultidimensionaleBean jsonCurrent = null;
	private Long casoId;
	private List<SinaEsegDTO> sinaCollegati = new ArrayList<SinaEsegDTO>();
	private List<SinaEsegDTO> sinaCollegabili = new ArrayList<SinaEsegDTO>();
	private List<SinaEsegDTO> sinaCollegabiliDaErogazioni = new ArrayList<SinaEsegDTO>();
	private Boolean ultimaErogExp = false;

	private List<CsTbSinaDomanda> lstSinaParams;
	private List<CsTbSinaDomanda> lstSinaParamInvalidita;

	private SinaEsegDTO sinaDTO;
	private List<ArTbPrestazioniInps> lstPrestazioniInps = new ArrayList<ArTbPrestazioniInps>();

	private Boolean renderValutaDopo = true;

	// gestione esportazione casellario
	public SinaMan() {
		lstSinaParams = new ArrayList<CsTbSinaDomanda>();
		lstPrestazioniInps = new ArrayList<ArTbPrestazioniInps>();
		lstSinaParamInvalidita = new ArrayList<CsTbSinaDomanda>();
		sinaDTO = new SinaEsegDTO();
		this.isDisabled();
	}

	// scheda multidimensionale
	public SinaMan(Long diarioId, ValMultidimensionaleBean jsonCurrent) {
		this.diarioSinaId = diarioId;
		this.jsonCurrent = jsonCurrent;
		loadSinaSI();
		loadSinaByDiarioId();
		this.renderValutaDopo(false);
	}
	
	private void renderValutaDopo(Boolean render){
		this.renderValutaDopo = render;
		if(!this.renderValutaDopo)
			this.getSinaDTO().setFlagValutaDopo(false);
	}

	// foglio intervento
	public SinaMan(Long casoId, Long mastId, String cf, Boolean ultimaErogazioneEsportata, Boolean renderValutaDopo) {

		this.casoId = casoId;
		this.ultimaErogExp = ultimaErogazioneEsportata;

		BaseDTO bdto = new BaseDTO();
		// carico i collegabili
		// ** mod. SISO-886 **//
		// ** il casoId potrebbe arrivare NULL nel caso in cui l'intervento sia
		// stato aperto fuori dal fascicolo **//
		if (this.possoCollegare() && casoId!=null) {

			bdto.setObj(casoId);
			fillEnte(bdto);
			sinaCollegabili = sinaService.findSinaByCaso(bdto);
		}

		// Recupero dei SINA collegabili da precedenti erogazioni (con master diverso dal quello corrente)
		if(mastId!=null) {
			
			if(!StringUtils.isBlank(cf)) {
				bdto = new BaseDTO();
				bdto.setObj(mastId);
				bdto.setObj2(cf);
				fillEnte(bdto);
				sinaCollegabiliDaErogazioni = sinaService.findSinaCollegabiliByCf(bdto);
			}
			this.loadSinaCollegati(mastId);
		}
		
		loadLastSinaCollegato();

		renderValutaDopo(renderValutaDopo);
	}
	
	private void loadSinaCollegati(Long mastId){
		if (mastId.longValue() != 0) {
			BaseDTO bsina = new BaseDTO();
			fillEnte(bsina);
			bsina.setObj(mastId);
			sinaCollegati = sinaService.findDiarioSinaByMastId(bsina);
		}
	}
	
	private void loadLastSinaCollegato(){
		SinaEsegDTO sina = null;
		if (sinaCollegati.size() > 0) {
			// i collegati sono ordinati per data desc, prendo l'ultimo salvato
			sina = sinaCollegati.get(0);

			// controllo se l'ultimo sina ha una erogazione con data superiore non estratta,
			// in tal caso è possibile la modifica
		}
		
		collegaSina(sina);
	}

	private void loadSinaSI() {
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		lstSinaParams = confService.getListaDomandaSina(dto);
		lstPrestazioniInps = sinaService.getPrestazioniInpsSina(dto); // Sottoinsieme delle prestazioni INPS
	}

	// TODO: verificare il corretto funzionamento
	private void loadSinaByDiarioId() {
		BaseDTO dto = new BaseDTO();
		CsUiCompBaseBean.fillEnte(dto);
		dto.setObj(diarioSinaId);
		dto.setObj2(lstSinaParams);
		SinaEsegDTO sinaEseg = sinaService.getSinaByDiarioId(dto);
		caricaPrestazioni(sinaEseg);
	}
	
	private void loadSinaSelected(SinaEsegDTO sinaEseg) {
		caricaPrestazioni(sinaEseg);
	}
	
	private void clonaSinaById(Long idSina) {
		BaseDTO dto = new BaseDTO();
		CsUiCompBaseBean.fillEnte(dto);
		dto.setObj(idSina);
		dto.setObj2(lstSinaParams);
		SinaEsegDTO sinaEseg = sinaService.clonaSinaById(dto);
		if(this.sovrascriviSinaCorrente) {
			sinaEseg.setSinaId(sinaDTO.getSinaId());
		}
		caricaPrestazioni(sinaEseg);
	}

	private void caricaPrestazioni(SinaEsegDTO sinaEseg) {
		sinaDTO = sinaEseg;

		if (sinaDTO == null)
			sinaDTO = SinaEsegDTO.nuovo(lstSinaParams);

		loadInvaliditaCivile();
	}

	private void loadInvaliditaCivile() {
		lstSinaParamInvalidita = new ArrayList<CsTbSinaDomanda>();
		for (CsTbSinaDomanda dom : lstSinaParams) {
			if (dom.getId() == TipoSinaDomanda.INVALIDITA_CIVILE) {
				lstSinaParamInvalidita.add(dom);
				lstSinaParams.remove(dom);
			}
		}
	}

	public void radioSelect() {
		HashMap<Long, String> risp = sinaDTO.getRispostas();
		if (jsonCurrent != null && risp.containsKey(Long.valueOf(7)) && risp.containsValue("20")) {
			jsonCurrent.setAssenzaReteFam(true);
		} else if (jsonCurrent != null && risp.containsKey(Long.valueOf(7)) && !risp.containsValue("20")) {
			jsonCurrent.setAssenzaReteFam(false);
		}
	}

	public void salva(Long idDiario, Date dataValutazione, Long visSecondoLivello) throws Exception {
		diarioSinaId = idDiario;
		sinaDTO.setData(dataValutazione);
		salva(visSecondoLivello);
	}

	public void salva(Long visSecondoLivello) throws Exception {
		CsUiCompBaseBean.fillEnte(sinaDTO);
		sinaDTO.setDiarioMultidimId(diarioSinaId);
		//sinaDTO.getDiario().setVisSecondoLivello(visSecondoLivello);
		//sinaDTO.prestazioniScelte(lstPrestazioniInps);
		/*
		 * if (sinaDTO.getSinaId() == null) sinaDTO = sinaService.saveSina(sinaDTO);
		 */
		Long sinaId = sinaService.saveSina(sinaDTO);
		
		BaseDTO dto = new BaseDTO();
		CsUiCompBaseBean.fillEnte(dto);
		dto.setObj(sinaId);
		dto.setObj2(sinaDTO.getLstSinaParams());
		sinaDTO = sinaService.getSinaById(dto);
		
		// SISO-1278 // devo confrontare i dati di invalidita presenti in cartella
		// sociale con quelli salvati nell'ultima valutazione multidimensionale
		if (sinaDTO.getSinaId() != null) {
			dto.setObj(sinaDTO.getDiarioMultidimId());
			dto.setObj2(sinaDTO.getLstSinaParamInvalidita());
			schedaService.verificaAllineamentoDatiInvalidita(dto);
		}
	}

	// SISO-985 - controllo sulle domande obbligatorie
	public List<String> valida() {
		List<String> messagges = new LinkedList<String>();
		if (sinaDTO.getData() == null)
			messagges.add("<b>Data valutazione</b> è un campo obbligatorio");

		Iterator<Long> set = sinaDTO.getRispostas().keySet().iterator();
		while (set.hasNext()) {
			Long id = set.next();

			if (id != 3 && id != 5 && id != 11 && sinaDTO.getRispostas().get(id) == null) {
				for (CsTbSinaDomanda dom : lstSinaParams) {
					if (dom.getId() == id) {
						messagges.add("<b>" + dom.getTesto() + ":</b> è un campo obbligatorio");
					}
				}
			}
		}

		return messagges;
	}

	// SISO-985
	public List<String> validaSinaErogazione(Integer pic, List<InterventoErogazHistoryRowBean> rows) {
		List<String> msg = null;

		// ** se il flag valuta dopo non è stato selezionato, sarà necessaria la verifica **//
		// ** che precedentemente veniva eseguita dal "required" del panel sina, ovvero:
		// ** - tutte le risposte sono obbligatorie tranne la 3 e la 5 **//
		boolean valutaDopo = this.sinaDTO.getFlagValutaDopo()!=null && this.sinaDTO.getFlagValutaDopo().booleanValue();
		if (this.sinaDTO != null && FLAG_IN_CARICO.SI.getCodice() == pic && !valutaDopo) {
			msg = this.valida();

			if (rows != null && sinaDTO.getData() != null) {
				for (InterventoErogazHistoryRowBean row : rows) {
					if (row.getStato().getTipo().equals(DataModelCostanti.TipoStatoErogazione.EROGATIVO)) {
						// se esiste una data di un erogazione (non esportata) antecedente al sina collegato ritorna errore
						if (!row.isEsportata() && !erogDataPrecedente(row.getDataErogazione())) {
							msg.add("Erogazioni non esportate con data precedente a scheda SINA: "+ddMMyyyy.format(row.getDataErogazione()));
							break;
						}
					}
				}
			}

		}

		return msg;
	}

	public Boolean salvaDaFglIntervento(Long mastId) {

		// skip se disabilitato
		if (isDisabled() || mastId <= 0)
			return true;

		CsUiCompBaseBean.fillEnte(sinaDTO);
		//sinaDTO.prestazioniScelte(lstPrestazioniInps);

		// salvataggio
		try {

			// se ho l'eseg mast valorizzato ed è uguale a quello di caricamento aggiorno dati SINA
			if (sinaDTO.getInterventoEsegMastId() == null || mastId.longValue() != sinaDTO.getInterventoEsegMastId().longValue()) {

				sinaDTO.setInterventoEsegMastId(mastId);
				sinaDTO.setDiarioMultidimId(null);
				// copia valori
				//sinaDTO.setSinaId(null);
			}
		
			sinaService.saveSina(sinaDTO);

		} catch (Exception e) {
			return false;
		}

		return true;
	}

	@Override
	public void copiaSina(Long idSina) {
		loadSinaSI();
		clonaSinaById(idSina);
	}

	/*
	 * public void collegaSina(Long id) { loadSinaSI(); loadSinaById(id); }
	 */
	
	public void collegaSina(SinaEsegDTO sina) { 
		loadSinaSI(); 
		loadSinaSelected(sina); 
	}
	
	/*
	 * public void caricaSina(Long id) { loadSinaSI(); loadSinaById(id); if
	 * (sinaDTO.getInterventoEsegMastId() != null)
	 * sinaDTO.setInterventoEsegMastId(null); }
	 */

	public void onRowSelect(SelectEvent event) {
		SinaEsegDTO sina = (SinaEsegDTO) event.getObject();
		collegaSina(sina);
	}

	public void nuovaValutazione() {
		loadSinaSI();
		loadInvaliditaCivile(); // Altrimenti errore visualizzazione menù inv civ
		sinaDTO = SinaEsegDTO.nuovo(lstSinaParams);
	}

	public Boolean erogDataPrecedente(Date dataErogazione) {

		int indexLast = sinaCollegati.size();

		Boolean verifica = true;

		// controlla corrente
		if (dataErogazione.before(sinaDTO.getData())) {
			verifica = false;
		}

		// controlla collegati, solo del piu vecchio dato che sono ordinati.
		SinaEsegDTO sinaLast = sinaCollegati.size() > 0 ? sinaCollegati.get(indexLast - 1) : null;
		if (sinaLast != null && !sinaLast.getSinaId().equals(sinaDTO.getSinaId())) {
			if (sinaLast.getData()!=null && dataErogazione.before(sinaLast.getData()))
				verifica = false;
		}

		return verifica;
	}

	public int collegabiliSize() {
		return this.sinaCollegabili.size();
	}
	
	public Boolean isDisabled() {

		Boolean disabilitato = false;
		Boolean nuovoSina = sinaDTO.getInterventoEsegMastId() == null;

		// se non è nuova e la erogazione è stata esportata, va bloccata
		if (!nuovoSina && ultimaErogExp) {
			return true;
		}
		
		
		disabilitato = !nuovoSina; 
		if (sinaCollegati.size() > 0) {
			disabilitato &= !sinaCollegati.get(0).getSinaId().equals(sinaDTO.getSinaId()); }
		 
		return disabilitato;
	}

	// SISO-783
	public Boolean possoCollegare() {
		String nav = (String) getSession().getAttribute("navigationHistory");
		if (nav!=null && nav.equalsIgnoreCase("erogazioniInterventi")) {
			return checkPermesso(PermessiFascicolo.ITEM, PermessiFascicolo.ACCESSO_ESTERNO_DATI_FASCICOLO);
		}
		return true;
	}

	public Date getSinaData() {
		return sinaDTO.getData();
	}

	@Override
	public Boolean isSinaSelected(SinaEsegDTO coll) {
		return coll.getSinaId().equals(sinaDTO.getSinaId());
	}
	
	public List<CsTbSinaDomanda> getLstSinaParams() {
		return lstSinaParams;
	}

	public void setLstSinaParams(List<CsTbSinaDomanda> lstSinaParams) {
		this.lstSinaParams = lstSinaParams;
	}

	public SinaEsegDTO getSinaDTO() {
		return sinaDTO;
	}

	public void setSinaDTO(SinaEsegDTO sinaDTO) {
		this.sinaDTO = sinaDTO;
	}

	public List<ArTbPrestazioniInps> getLstPrestazioniInps() {
		return lstPrestazioniInps;
	}

	public void setLstPrestazioniInps(List<ArTbPrestazioniInps> lstPrestazioniInps) {
		this.lstPrestazioniInps = lstPrestazioniInps;
	}

	public Long getCasoId() {
		return casoId;
	}

	public void setCasoId(Long casoId) {
		this.casoId = casoId;
	}
	
	public boolean isSovrascriviSinaCorrente() {
		return sovrascriviSinaCorrente;
	}

	public void setSovrascriviSinaCorrente(boolean sovrascriviSinaCorrente) {
		this.sovrascriviSinaCorrente = sovrascriviSinaCorrente;
	}

	public List<SinaEsegDTO> getSinaCollegati() {
		return sinaCollegati;
	}

	public void setSinaCollegati(List<SinaEsegDTO> sinaCollegati) {
		this.sinaCollegati = sinaCollegati;
	}

	public List<SinaEsegDTO> getSinaCollegabili() {
		return sinaCollegabili;
	}

	public void setSinaCollegabili(List<SinaEsegDTO> sinaCollegabili) {
		this.sinaCollegabili = sinaCollegabili;
	}

	public List<SinaEsegDTO> getSinaCollegabiliDaErogazioni() {
		return sinaCollegabiliDaErogazioni;
	}

	public void setSinaCollegabiliDaErogazioni(List<SinaEsegDTO> sinaCollegabiliDaErogazioni) {
		this.sinaCollegabiliDaErogazioni = sinaCollegabiliDaErogazioni;
	}

	public ValMultidimensionaleBean getJsonCurrent() {
		return jsonCurrent;
	}

	public void setJsonCurrent(ValMultidimensionaleBean jsonCurrent) {
		this.jsonCurrent = jsonCurrent;
	}

	public List<CsTbSinaDomanda> getLstSinaParamInvalidita() {
		return lstSinaParamInvalidita;
	}

	public void setLstSinaParamInvalidita(List<CsTbSinaDomanda> lstSinaParamInvalidita) {
		this.lstSinaParamInvalidita = lstSinaParamInvalidita;
	}

	public Boolean getRenderValutaDopo() {
		return renderValutaDopo;
	}

	public void setRenderValutaDopo(Boolean renderValutaDopo) {
		this.renderValutaDopo = renderValutaDopo;
	}
	
	@Override
	public String getLabelPanelCorrente() {
		if(sinaDTO!=null && sinaDTO.getSinaId()!=null)
			return "SINA num. "+sinaDTO.getSinaId();
		else
			return "Nuovo SINA";
		
	}
	
	@Override
	public void elimina(SinaEsegDTO sina) {
		if(sina!=null) {
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(sina);
			if(sinaService.canDeleteSina(dto)) {
				sinaService.deleteSina(dto);
				loadSinaCollegati(sina.getInterventoEsegMastId());
				if(sina.getSinaId().equals(sinaDTO.getSinaId())) {
					//Quello correntemente selezionato è stato eliminato, quindi ricarico l'ultimo presente
					loadLastSinaCollegato();
				}
			}else
				this.addWarning("Eliminazione SINA", "Non è possibile elimnare il SINA selezionato: se l'erogazione è stata esportata, la data della valutazione da eliminare deve essere successiva a quella dell'esportazione");
		}
	}
	
}
