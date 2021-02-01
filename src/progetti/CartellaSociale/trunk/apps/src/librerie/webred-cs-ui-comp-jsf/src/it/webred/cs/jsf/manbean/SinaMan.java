package it.webred.cs.jsf.manbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;

import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSinaSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.SinaDTO;
import it.webred.cs.csa.ejb.dto.SinaEsegDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.CSIPs.FLAG_IN_CARICO;
import it.webred.cs.data.DataModelCostanti.PermessiFascicolo;
import it.webred.cs.data.model.ArTbPrestazioniInps;
import it.webred.cs.data.model.CsADatiInvalidita;
import it.webred.cs.data.model.CsDSina;
import it.webred.cs.data.model.CsDSinaEseg;
import it.webred.cs.data.model.CsIInterventoEsegMast;
import it.webred.cs.data.model.CsTbSinaDomanda;
import it.webred.cs.data.model.CsAInvCiv;
import it.webred.cs.jsf.bean.erogazioneIntervento.InterventoErogazHistoryRowBean;
import it.webred.cs.jsf.interfaces.ISina;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.json.valMultidimensionale.ver2.ValMultidimensionaleBean;

public class SinaMan extends CsUiCompBaseBean implements Serializable, ISina {

//	@EJB
//	private AccessTableAlertSessionBeanRemote alertService;
//	@EJB
//	private AccessTableSchedaSessionBeanRemote schedaService;

	private static final long serialVersionUID = 1L;

	protected AccessTableConfigurazioneSessionBeanRemote configService = (AccessTableConfigurazioneSessionBeanRemote) getCarSocialeEjb(
			"AccessTableConfigurazioneSessionBean");

	protected AccessTableSinaSessionBeanRemote sinaService = (AccessTableSinaSessionBeanRemote) getEjb("CarSocialeA",
			"CarSocialeA_EJB", "AccessTableSinaSessionBean");

	protected AccessTableAlertSessionBeanRemote alertService = (AccessTableAlertSessionBeanRemote) getEjb("CarSocialeA",
			"CarSocialeA_EJB", "AccessTableAlertSessionBean");

	protected AccessTableSchedaSessionBeanRemote schedaService = (AccessTableSchedaSessionBeanRemote) getEjb(
			"CarSocialeA", "CarSocialeA_EJB", "AccessTableSchedaSessionBean");

	// DIARIO ID
	private Long diarioSinaId;
	private ValMultidimensionaleBean jsonCurrent = null;
	private Long casoId;
	private List<CsDSina> sinaCollegati = new ArrayList<CsDSina>();
	private List<CsDSina> sinaCollegabili = new ArrayList<CsDSina>();
	private List<CsDSina> sinaCollegabiliDaErogazioni = new ArrayList<CsDSina>();
	private CsDSina sinaSelezionato = new CsDSina();
	private Boolean ultimaErogExp = false;

	private List<CsTbSinaDomanda> lstSinaParams;
	private List<CsTbSinaDomanda> lstSinaParamInvalidita;

	private SinaEsegDTO sinaDTO = new SinaEsegDTO();
	private List<ArTbPrestazioniInps> lstPrestazioniInps = new ArrayList<ArTbPrestazioniInps>();

	// ** mod. SISO-886 **//
	private Boolean exportMode = false;

	// gestione esportazione casellario
	public SinaMan() {
		lstSinaParams = new ArrayList<CsTbSinaDomanda>();
		lstPrestazioniInps = new ArrayList<ArTbPrestazioniInps>();
		lstSinaParamInvalidita = new ArrayList<CsTbSinaDomanda>();
		sinaDTO = new SinaEsegDTO();
		sinaDTO.setLstPrestazioniInpsScelte(new ArrayList<String>());

		CsDSina sina = new CsDSina();
		sina.setCsIInterventoEsegMast(new CsIInterventoEsegMast());
		sinaDTO.setCsDSina(sina);
		this.isDisabled();
	}

	// scheda multidimensionale
	public SinaMan(Long diarioId, ValMultidimensionaleBean jsonCurrent) {
		this.diarioSinaId = diarioId;
		this.jsonCurrent = jsonCurrent;
		loadSinaSI();
		loadSinaByDiarioId();
	}

	// foglio intervento
	public SinaMan(Long casoId, Long mastId, String cf, Boolean uexp) {

		this.casoId = casoId;
		this.ultimaErogExp = uexp;

		BaseDTO bdto = new BaseDTO();
		// carico i collegabili
		// ** mod. SISO-886 **//
		// ** il casoId potrebbe arrivare NULL nel caso in cui l'intervento sia
		// stato aperto fuori dal fascicolo **//
		if (this.possoCollegare()) {

			bdto.setObj(casoId);
			fillEnte(bdto);
			sinaCollegabili = sinaService.findSinaByCaso(bdto);
		}

		// Recupero dei SINA collegabili da precedenti erogazioni (con master diverso
		// dal quello corrente)
		bdto = new BaseDTO();
		bdto.setObj(mastId);
		bdto.setObj2(cf);
		fillEnte(bdto);
		sinaCollegabiliDaErogazioni = sinaService.findSinaCollegabiliByCf(bdto);

		if (mastId.longValue() != 0) {
			BaseDTO bsina = new BaseDTO();
			fillEnte(bsina);
			bsina.setObj(mastId);
			sinaCollegati = sinaService.findDiarioSinaByMastId(bsina);
		}

		Long idSina = null;
		if (sinaCollegati.size() > 0) {
			// i collegati sono ordinati per data desc, prendo l'ultimo salvato
			idSina = sinaCollegati.get(0).getId();

			// controllo se l'ultimo sina ha una erogazione con data superiore non estratta,
			// in tal caso è possibile la modifica
		}

		sinaSelezionato.setId(idSina);

		collegaSina(idSina);

		if (sinaDTO.getCsDSina().getData() == null) {
			// SISO-985: un nuovo SINA non deve avere la data odierna come data di default
			// sinaDTO.getCsDSina().setData(new Date());
		}
	}

	private void loadSinaSI() {
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);

		lstSinaParams = configService.getListaDomandaSina(dto);
		lstPrestazioniInps = sinaService.getPrestazioniInpsSina(dto); // Sottoinsieme delle prestazioni INPS
	}

	// TODO: verificare il corretto funzionamento
	void loadSinaByDiarioId() {
		BaseDTO dto = new BaseDTO();
		CsUiCompBaseBean.fillEnte(dto);
		dto.setObj(diarioSinaId);
		dto.setObj2(lstSinaParams);
		SinaEsegDTO sinaEseg = sinaService.getSinaByDiarioId(dto);
		caricaPrestazioni(sinaEseg);
	}

	void loadSinaById(Long idSina) {
		BaseDTO dto = new BaseDTO();
		CsUiCompBaseBean.fillEnte(dto);
		dto.setObj(idSina);
		dto.setObj2(lstSinaParams);
		SinaEsegDTO sinaEseg = sinaService.getSinaById(dto);
		// CsDSina sina = sinaService.newSina(dto);
		caricaPrestazioni(sinaEseg);
	}

	private void caricaPrestazioni(SinaEsegDTO sinaEseg) {
		sinaDTO = sinaEseg;

		if (sinaDTO == null)
			sinaDTO = SinaEsegDTO.create(new CsDSina(), lstSinaParams);
		if (!sinaDTO.getCsDSina().getArTbPrestazioniInps().isEmpty()) {
			List<String> temp = new ArrayList<String>();
			for (ArTbPrestazioniInps p : sinaEseg.getCsDSina().getArTbPrestazioniInps()) {
				temp.add(p.getCodice());
			}
			sinaDTO.setLstPrestazioniInpsScelte(temp);
		}
		loadInvaliditaCivile();
	}

	private void loadInvaliditaCivile() {
		lstSinaParamInvalidita = new ArrayList<CsTbSinaDomanda>();
		for (CsTbSinaDomanda dom : lstSinaParams) {
			if (dom.getId() == Long.parseLong("10")) {
				lstSinaParamInvalidita.add(dom);
				lstSinaParams.remove(dom);
			}
		}
	}

	// TODO: verificare il corretto funzionamento
	public CsTbSinaDomanda getCurrentSina(long id) {
		try {
			return sinaDTO.getDomandaEsegById(id);
		} catch (Exception e) {
			CsTbSinaDomanda domanda = new CsTbSinaDomanda();
			domanda.setTesto("prova");
			return domanda;
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
		sinaDTO.getCsDSina().setData(dataValutazione);
		salva(visSecondoLivello);
	}

	public void salva(Long visSecondoLivello) throws Exception {
		CsUiCompBaseBean.fillEnte(sinaDTO);
		sinaDTO.associaDiario(diarioSinaId);
		sinaDTO.getCsDSina().getCsDDiario().setVisSecondoLivello(visSecondoLivello);
		sinaDTO.prestazioniScelte(lstPrestazioniInps);
		if (sinaDTO.getCsDSina().getId() == null)
			sinaDTO = sinaService.saveNewSina(sinaDTO);

		sinaService.saveSina(sinaDTO);

		// SISO-1278 // devo confrontare i dati di invalidita presenti in cartella
		// sociale con quelli salvati nell'ultima valutazione multidimensionale
		if (sinaDTO.getCsDSina() != null) {
			List<Long> lstCsInvalidita = new ArrayList<Long>();
			List<CsADatiInvalidita> csDatiInvalidita = new ArrayList<CsADatiInvalidita>();

			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(sinaDTO.getCsDSina().getCsDDiario().getCsACaso().getCsASoggetto().getAnagraficaId());
			csDatiInvalidita = schedaService.findDatiInvaliditaBySoggettoId(dto);

			if (csDatiInvalidita != null) {

				for (CsADatiInvalidita datiCs : csDatiInvalidita) {
					if (sinaDTO.getCsDSina().getCsDDiario().getDtAmministrativa().before(datiCs.getDataFineApp())
							&& sinaDTO.getCsDSina().getCsDDiario().getDtAmministrativa()
									.after(datiCs.getDataInizioApp())) {

						if (datiCs.getCsAInvCivs() != null && datiCs.getCsAInvCivs().size() > 0) {
							for (CsAInvCiv invCivs : datiCs.getCsAInvCivs()) {

								lstCsInvalidita.add(invCivs.getSinaRispostaId());
							}
							break;

						}
					}
				}
				if (sinaDTO.getLstSinaParamInvalidita() != null ) {
					
				
					Collections.sort(sinaDTO.getLstSinaParamInvalidita());
					Collections.sort(lstCsInvalidita);
					if (!sinaDTO.getLstSinaParamInvalidita().equals(lstCsInvalidita)) {

						dto = new BaseDTO();
						fillEnte(dto);
						dto.setObj(sinaDTO.getCsDSina().getCsDDiario().getCsACaso().getCsASoggetto());
						dto.setObj2(sinaDTO.getCsDSina().getCsDDiario().getCsOOperatoreSettore());
						dto.setObj3(DataModelCostanti.TipiAlertCod.MULTIDIM);
						dto.setObj4("una nuova valutazione multidimensionale con dati invalidita differenti da quelli salvati in cartella sociale");
						dto.setObj5(Boolean.TRUE);
						alertService.addAlertNuovoInserimentoToResponsabileCaso(dto);
					}
				 
				}

			}
		}
	}

	// SISO-985 - controllo sulle domande obbligatorie
	public List<String> valida() {
		List<String> messagges = new LinkedList<String>();
		if (sinaDTO.getCsDSina().getData() == null)
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

		// ** se il flag valuta dopo non è stato selezionato, sarà necessaria la
		// verifica **//
		// ** che precedentemente veniva eseguita dal "required" del panel sina, ovvero:
		// **//
		// ** - tutte le risposte sono obbligatorie tranne la 3 e la 5 **//
		if (this.sinaDTO != null && !this.sinaDTO.getCsDSina().getFlagValutaDopo()
				&& FLAG_IN_CARICO.SI.getCodice() == pic) {
			msg = this.valida();

			if (rows != null && sinaDTO.getCsDSina().getData() != null) {
				for (InterventoErogazHistoryRowBean row : rows) {
					if (row.getStato().getTipo().equals(DataModelCostanti.TipoStatoErogazione.EROGATIVO)) {
						// se esiste una data di un erogazione antecedente a TUTTI i sina collegati
						// ritorna errore
						if (!erogDataPrecedente(row.getDataErogazione())) {
							msg.add("Erogazioni con data precedente a scheda SINA");
							break;
						}
					}
				}
			}

		}

		return msg;
	}

	public Boolean salvaDaFglIntervento(CsIInterventoEsegMast mast) {

		// skip se disabilitato
		if (isDisabled() || mast.getId() <= 0)
			return true;

		CsUiCompBaseBean.fillEnte(sinaDTO);
		sinaDTO.prestazioniScelte(lstPrestazioniInps);

		// salvataggio
		try {

			// se ho l'eseg mast valorizzato ed è uguale a quello di caricamento aggiorno
			// dati SINA
			if (sinaDTO.getInterventoEsegMast() == null || mast.getId() != sinaDTO.getInterventoEsegMast().getId()) {

				sinaDTO.getCsDSina().setCsIInterventoEsegMast(mast);
				sinaDTO.getCsDSina().setCsDDiario(null);
				// copia valori
				sinaDTO.getCsDSina().setId(null);
				sinaDTO = sinaService.saveNewSina(sinaDTO);
			}
			sinaService.saveSina(sinaDTO);

		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public void collegaSina(Long id) {
		loadSinaSI();
		loadSinaById(id);
	}

	public void caricaSina(Long id) {
		loadSinaSI();
		loadSinaById(id);
		if (sinaDTO.getCsDSina().getCsIInterventoEsegMast() != null)
			sinaDTO.getCsDSina().setCsIInterventoEsegMast(null);
		sinaSelezionato = new CsDSina();
	}

	public void onRowSelect(SelectEvent event) {
		CsDSina sina = (CsDSina) event.getObject();
		collegaSina(sina.getId());
	}

	public void nuovaValutazione() {
		loadSinaSI();
		loadInvaliditaCivile(); // Altrimenti errore visualizzazione menù inv civ
		sinaDTO = SinaEsegDTO.create(new CsDSina(), lstSinaParams);
		// SISO-985: un nuovo SINA non deve avere la data odierna come data di default
		// sinaDTO.getCsDSina().setData(new Date());
		sinaSelezionato = new CsDSina();
	}

	public Boolean erogDataPrecedente(Date dataErogazione) {

		int indexLast = sinaCollegati.size();

		Boolean verifica = true;

		// controlla corrente
		if (dataErogazione.before(sinaDTO.getCsDSina().getData())) {
			verifica = false;
		}

		// controlla collegati, solo del piu vecchio dato che sono ordinati.
		CsDSina sinaLast = sinaCollegati.size() > 0 ? sinaCollegati.get(indexLast - 1) : null;
		if (sinaLast != null && !sinaLast.getId().equals(sinaDTO.getCsDSina().getId())) {
			if (sinaLast.getData()!=null && dataErogazione.before(sinaLast.getData()))
				verifica = false;
		}

		return verifica;
	}

	public int collegabiliSize() {
		return this.sinaCollegabili.size();
	}

	public Boolean isDisabled() {

		Boolean toReturn = sinaDTO.getCsDSina().getCsIInterventoEsegMast() != null;

		// se non è nuova e la erogazione è stata esportata, va bloccata
		if (toReturn && ultimaErogExp) {
			return true;
		}

		if (sinaCollegati.size() > 0) {
			toReturn &= !sinaCollegati.get(0).getId().equals(sinaDTO.getCsDSina().getId());
		}
		return toReturn;
	}

	// SISO-783
	public Boolean possoCollegare() {
		String nav = (String) getSession().getAttribute("navigationHistory");
		if (nav.equalsIgnoreCase("erogazioniInterventi")) {
			return checkPermesso(PermessiFascicolo.ITEM, PermessiFascicolo.ACCESSO_ESTERNO_DATI_FASCICOLO);
		}
		return true;
	}

	public Date getSinaData() {
		return sinaDTO.getCsDSina().getData();
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

	public List<CsDSina> getSinaCollegati() {
		return sinaCollegati;
	}

	public void setSinaCollegati(List<CsDSina> sinaCollegati) {
		this.sinaCollegati = sinaCollegati;
	}

	public List<CsDSina> getSinaCollegabili() {
		return sinaCollegabili;
	}

	public void setSinaCollegabili(List<CsDSina> sinaCollegabili) {
		this.sinaCollegabili = sinaCollegabili;
	}

	public List<CsDSina> getSinaCollegabiliDaErogazioni() {
		return sinaCollegabiliDaErogazioni;
	}

	public void setSinaCollegabiliDaErogazioni(List<CsDSina> sinaCollegabiliDaErogazioni) {
		this.sinaCollegabiliDaErogazioni = sinaCollegabiliDaErogazioni;
	}

	public ValMultidimensionaleBean getJsonCurrent() {
		return jsonCurrent;
	}

	public void setJsonCurrent(ValMultidimensionaleBean jsonCurrent) {
		this.jsonCurrent = jsonCurrent;
	}

	public CsDSina getSinaSelezionato() {
		return sinaSelezionato;
	}

	public void setSinaSelezionato(CsDSina sinaSelezionato) {
		this.sinaSelezionato = sinaSelezionato;
	}

	public List<CsTbSinaDomanda> getLstSinaParamInvalidita() {
		return lstSinaParamInvalidita;
	}

	public void setLstSinaParamInvalidita(List<CsTbSinaDomanda> lstSinaParamInvalidita) {
		this.lstSinaParamInvalidita = lstSinaParamInvalidita;
	}

	// ** mod. SISO-886 **//
	// ** viene impostato (per gestire il sina) quando ci si trova in "Esporta
	// casellario" **//
	public Boolean getExportMode() {
		return exportMode;
	}

	public void setExportMode(Boolean eMode) {
		this.exportMode = eMode;
	}

}
