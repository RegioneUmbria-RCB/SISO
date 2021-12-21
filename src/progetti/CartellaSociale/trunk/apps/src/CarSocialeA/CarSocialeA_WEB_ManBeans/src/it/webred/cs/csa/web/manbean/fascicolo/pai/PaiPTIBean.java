package it.webred.cs.csa.web.manbean.fascicolo.pai;

import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTablePaiPTISessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSinbaSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.AlertDTO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneDettaglioDTO;
import it.webred.cs.csa.ejb.dto.pai.CsPaiMastSoggDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.ArCsPaiInfoSinteticheDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.ArCsPaiPTIDocumentoDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.CsPaiPTIDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.CsPaiPtiRevisioniDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.InserimentoConsuntivazioneDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.PaiPTIFaseEnum;
import it.webred.cs.csa.ejb.dto.pai.pti.PaiPTIMotivoRevisioneEnum;
import it.webred.cs.csa.ejb.dto.pai.pti.PaiPTITipoProrogaEnum;
import it.webred.cs.csa.ejb.dto.pai.pti.RichiestaDisponibilitaPaiPtiDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.StrutturaDisponibilitaDTO;
import it.webred.cs.csa.web.manbean.fascicolo.erogazioniInterventi.ErogInterventoRowBean;
import it.webred.cs.csa.web.manbean.fascicolo.valSinba.LazyListaValSinbaModel;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.ArRpTipologiaServizio;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsDSinba;
import it.webred.cs.data.model.CsFlgIntervento;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsTbTipoMinore;
import it.webred.cs.data.model.TipoStruttura;
import it.webred.cs.data.model.VStrutturaArea;
import it.webred.cs.jsf.bean.DatiInterventoBean;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.json.valSinba.IValSinba;
import it.webred.cs.json.valSinba.ValSinbaManBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

public class PaiPTIBean extends CsUiCompBaseBean implements Serializable {

	private static final long serialVersionUID = -3003751447281251067L;
	protected AccessTablePaiPTISessionBeanRemote paiPTIService = (AccessTablePaiPTISessionBeanRemote) getCarSocialeEjb("AccessTablePaiPTISessionBean");
	protected AccessTableInterventoSessionBeanRemote interventoService = (AccessTableInterventoSessionBeanRemote) getCarSocialeEjb("AccessTableInterventoSessionBean");
	protected AccessTableOperatoreSessionBeanRemote operatoreService = (AccessTableOperatoreSessionBeanRemote) getCarSocialeEjb("AccessTableOperatoreSessionBean");
	protected AccessTableSinbaSessionBeanRemote sinbaService = (AccessTableSinbaSessionBeanRemote) getCarSocialeEjb("AccessTableSinbaSessionBean");
	protected AccessTableAlertSessionBeanRemote alertService = (AccessTableAlertSessionBeanRemote) getCarSocialeEjb("AccessTableAlertSessionBean");
	private CsPaiPTIDTO pti;

	private List<StrutturaDisponibilitaDTO> lstStrutture;
	private List<StrutturaDisponibilitaDTO> lstStruttureFiltered;
	private List<StrutturaDisponibilitaDTO> dtStruttureSelezionate;

	// struttura temp per action
	private StrutturaDisponibilitaDTO strutturaSelezionata;
	private StrutturaDisponibilitaDTO strutturaAccettataDaEnte;
	private CsPaiMastSoggDTO minore;
	private ArCsPaiPTIDocumentoDTO documentoSinba;
	private ArCsPaiPTIDocumentoDTO documentoSinbaDaSalvare;
	private DefaultStreamedContent file;

	private boolean disabledStruttureSelection = false;
	private Long idFase;

	private List<SelectItem> listaTipiStruttura;
	private List<SelectItem> listaCaseManager = new ArrayList<SelectItem>();

	private LazyListaValSinbaModel lazyListaSchedeValSinbaModel;
	public IValSinba sinbaManBean;

	public boolean isModifica = false;

	private List<InserimentoConsuntivazioneDTO> lstConsuntivazioni;
	private List<InserimentoConsuntivazioneDTO> lstConsuntivazioniErogate;
	private List<SelectItem> lstMotiviRevisione = new ArrayList<SelectItem>();
	private List<SelectItem> lstTipoProroga = new ArrayList<SelectItem>();
	private Boolean visualizzaMotiviRevisione = false;
	private List<CsPaiPtiRevisioniDTO> lstRevisioni = new ArrayList<CsPaiPtiRevisioniDTO>();
	private List<ArCsPaiPTIDocumentoDTO> listDocumentiPAI = new ArrayList<ArCsPaiPTIDocumentoDTO>();
	private List<ArCsPaiPTIDocumentoDTO> listDocumentiPTI = new ArrayList<ArCsPaiPTIDocumentoDTO>();
	private ArCsPaiInfoSinteticheDTO infoSintetichePai = new ArCsPaiInfoSinteticheDTO();
	private ArCsPaiPTIDocumentoDTO selectedPaiPTI; // per la row expansion
	private ArCsPaiInfoSinteticheDTO infoSintetichePaiSelected;
	private List<SelectItem> lstTipoMinoreBeneficiario = new ArrayList<SelectItem>();

    public Integer annoNascitaMinore = null;
    private Boolean flagCaricaDisStrutture = false;
    private Date dataUltimaConsuntivazione; 
    public Long tipoStruttura;
    
	@SuppressWarnings("deprecation")
	public void nuovo(CsASoggettoLAZY soggetto) throws Exception {
		
		nuovo(soggetto.getCsAAnagrafica().getDataNascita().getYear());
		
		CsDSinba csDSi = caricaValutazioneSinba(soggetto);

		// TODO---qui devo caricare la valutazione Multidimensionale esistente oppure
		// allegare un file

		if (csDSi != null && csDSi.getDiarioId() !=null) {
			sinbaManBean = ValSinbaManBaseBean.initISchedaSinba(csDSi);
//		//TODO---qui devo caricare la valutazione Multidimensionale esistente oppure allegare un file
			// Devo trovare i diarii con id tipodiario sinba e prendere quello con data
			// amministraziva più recente
			this.pti.setDiarioSinbaId(csDSi.getDiarioId());
		} 
		
	}
	
   
	
	public void nuovo(int annoNascitaMinore) throws Exception {
		reset();
		readListaTipoMinoreBeneficiario();
		this.annoNascitaMinore = annoNascitaMinore;
//		this.minore = minore;
	}

	public void reset() {
		this.pti = new CsPaiPTIDTO();
		this.pti.setCodRouting(getCurrentOpSettore().getCsOSettore().getCsOOrganizzazione().getCodRouting());
		this.pti.setFlgEmergenza(false);
		this.idFase = null;
		this.lstStrutture = new ArrayList<StrutturaDisponibilitaDTO>();
		this.strutturaSelezionata = null;
		this.dtStruttureSelezionate = new ArrayList<StrutturaDisponibilitaDTO>();
		this.lstConsuntivazioni = new ArrayList<InserimentoConsuntivazioneDTO>();
		this.lstConsuntivazioniErogate = new ArrayList<InserimentoConsuntivazioneDTO>();
		this.lstRevisioni = new ArrayList<CsPaiPtiRevisioniDTO>();
		this.isModifica = false;
		this.strutturaAccettataDaEnte = null;
		this.documentoSinbaDaSalvare = null;
		this.documentoSinba = null;
		
	}

	public void initializeValMultiDimensionale(CsASoggettoLAZY soggetto) {
		this.lazyListaSchedeValSinbaModel = new LazyListaValSinbaModel();
		this.lazyListaSchedeValSinbaModel.setup(soggetto);
	}
	
	public void struttRichDispRowSelect(SelectEvent event) {
		if (this.pti.getFlgEmergenza()) {
			if (this.dtStruttureSelezionate != null && this.dtStruttureSelezionate.size()>1) {
				dtStruttureSelezionate.remove(event.getObject());
			}
		}
	}
	
	public void struttRichDispCheckRowSelect(SelectEvent event) {
		if (this.pti.getFlgEmergenza()) {
			if (this.dtStruttureSelezionate != null && this.dtStruttureSelezionate.size()>1) {
				dtStruttureSelezionate.clear();
				
				dtStruttureSelezionate.add((StrutturaDisponibilitaDTO)event.getObject());
			}
		}
	}
	public void accettaRichiesta() throws Exception {

		// cambio stato alla richiesta accettata e invalido le altre richieste di
		// disponibilita
		popolaRichiesteDisponibilita(minore);
		Long idRichiestaAccettata = strutturaSelezionata.getRichiesteDisponibilita().getId();
		accettaRichDisp(idRichiestaAccettata);
		// salvo la nuova fase
		BaseDTO bdto = new BaseDTO();
		fillEnte(bdto);
		bdto.setObj(pti);
		bdto.setObj2(PaiPTIFaseEnum.STRUTT_OK.getId());

		try {
			/// devo salvare il file che la struttura puo aver allegato
			if (strutturaSelezionata.getRichiesteDisponibilita().getDocumento() != null) {

				byte[] doc = strutturaSelezionata.getRichiesteDisponibilita().getDocumento();
				InputStream is = new ByteArrayInputStream(doc);

				String mimeType;
				mimeType = URLConnection.guessContentTypeFromStream(is);
				is.close();

				String nomeDoc = strutturaSelezionata.getRichiesteDisponibilita().getNomeDocumento();
				ArCsPaiPTIDocumentoDTO documentoDTO = new ArCsPaiPTIDocumentoDTO();
				documentoDTO.setCodRouting(this.pti.getCodRouting());
				documentoDTO.setIdPaiPTI(this.pti.getId());
				documentoDTO.setDocumento(doc);
				documentoDTO.setNome(nomeDoc);
				documentoDTO.setDtIns(new Date());
				documentoDTO.setUsrIns(getCurrentUsername());
				documentoDTO.setTipoDocumento("PAI_DOC");
				documentoDTO.setTipo(mimeType);
				documentoDTO.setValidoDa(new Date());

				this.pti.getDocumentiPTI().add(documentoDTO);

			}
            this.pti.setIdStruttura(this.strutturaSelezionata.getIdStruttura());
			this.pti = this.paiPTIService.savePTI(bdto);
			this.idFase = this.pti.getFaseAttuale().getIdStato();

			for (StrutturaDisponibilitaDTO struttura : dtStruttureSelezionate) {
				if (struttura.getRichiesteDisponibilita() != null && struttura.getRichiesteDisponibilita().getId()
						.equals(strutturaSelezionata.getRichiesteDisponibilita().getId())) {
					struttura.getRichiesteDisponibilita().setStatoRichiesta("ACCETTATA");
				}
			}

			for (StrutturaDisponibilitaDTO stru : lstStrutture) {
				if (stru.getRichiesteDisponibilita() != null && stru.getRichiesteDisponibilita().getId()
						.equals(strutturaSelezionata.getRichiesteDisponibilita().getId())) {
					stru.getRichiesteDisponibilita().setStatoRichiesta("ACCETTATA");
				}

			}
			addInfo("Accettazione Richiesta", "Operazione conclusa con successo");
		} catch (Exception e) {
			addError("Salvataggio PTI", "Errore salvataggio PTI");
			logger.error("Errore salvataggio PTI", e);
		}

		System.out.println("test accettazione richiesta");
	}

	public void rifiutaRichiesta() throws Exception {

		// cambio stato alla richiesta rifutata
		popolaRichiesteDisponibilita(minore);
		Long idRichiestaRifiutata = strutturaSelezionata.getRichiesteDisponibilita().getId();
		rifiutaRichDisp(idRichiestaRifiutata);
		// LA fase rimane la stessa
		BaseDTO bdto = new BaseDTO();
		fillEnte(bdto);
		bdto.setObj(pti);
		bdto.setObj2(PaiPTIFaseEnum.RICH_DISP.getId());

		try {
			CsPaiPTIDTO paiPTIDTO = this.paiPTIService.savePTI(bdto);
			this.pti = paiPTIDTO;
			this.idFase = this.pti.getFaseAttuale().getIdStato();

			for (StrutturaDisponibilitaDTO struttura : dtStruttureSelezionate) {
				if (struttura.getRichiesteDisponibilita() != null && struttura.getRichiesteDisponibilita().getId()
						.equals(strutturaSelezionata.getRichiesteDisponibilita().getId())) {
					struttura.getRichiesteDisponibilita().setStatoRichiesta("RIFIUTATA");
				}
			}

			for (StrutturaDisponibilitaDTO stru : lstStrutture) {
				if (stru.getRichiesteDisponibilita() != null && stru.getRichiesteDisponibilita().getId()
						.equals(strutturaSelezionata.getRichiesteDisponibilita().getId())) {
					stru.getRichiesteDisponibilita().setStatoRichiesta("RIFIUTATA");
				}
			}

			addInfo("Rifiuto Richiesta", "Operazione conclusa con successo");
		} catch (Exception e) {
			addError("Salvataggio PTI", "Errore salvataggio PTI");
			logger.error("Errore salvataggio PTI", e);
		}

	}

	public void caricaPTI(Long diarioPaiId, CsPaiMastSoggDTO minore) {
		try {
			setModifica(false);
			readListaTipoMinoreBeneficiario();
			lstStrutture = new ArrayList<StrutturaDisponibilitaDTO>();
			dtStruttureSelezionate = new ArrayList<StrutturaDisponibilitaDTO>();
			List<StrutturaDisponibilitaDTO> elencoStruttureDisponibilita = new ArrayList<StrutturaDisponibilitaDTO>();
			this.pti = findPTIByPai(diarioPaiId);
			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			
			if (this.pti.getDiarioSinbaId() != null) {
				
				dto.setObj(this.pti.getDiarioSinbaId());
				this.sinbaManBean = ValSinbaManBaseBean.initISchedaSinba(sinbaService.getSchedaSinbaById(dto));

			}
			else {
				BaseDTO dtoSogg = new BaseDTO();
				fillEnte(dtoSogg);
				dtoSogg.setObj(minore.getCf());
				CsASoggettoLAZY sogg = soggettoService.getSoggettoByCF(dtoSogg);
				
				if(sogg!=null) {
					CsDSinba csDSi = caricaValutazioneSinba(sogg);
		
					if (csDSi != null && csDSi.getDiarioId() !=null) {
						sinbaManBean = ValSinbaManBaseBean.initISchedaSinba(csDSi);
			
						this.pti.setDiarioSinbaId(csDSi.getDiarioId());
					} 
					if (this.pti.getDiarioSinbaId() != null) {
						dto.setObj(this.pti.getDiarioSinbaId());
						this.sinbaManBean = ValSinbaManBaseBean.initISchedaSinba(sinbaService.getSchedaSinbaById(dto));
					}
				}

			}

			this.idFase = this.pti.getFaseAttuale().getIdStato();
			this.setLstRevisioni(findRevisioniPTI(this.pti.getId()));

			elencoStruttureDisponibilita = findElencoStruttureDisponibilita(this.pti.getId());
			
			for (StrutturaDisponibilitaDTO strutturaDisp : elencoStruttureDisponibilita) {
				if (strutturaDisp.getRichiesteDisponibilita() != null) {
					dtStruttureSelezionate.add(strutturaDisp);
					if (strutturaDisp.getRichiesteDisponibilita().getStatoRichiesta().equals("ACCETTATA")) {

						strutturaAccettataDaEnte = strutturaDisp;
						strutturaSelezionata = strutturaDisp;
						// se esiste una richdisp accettata carico i documenti da AR_CS_PAI_PTI_DOCUMENTO
						
					}
				}

				lstStrutture.add(strutturaDisp);
			}
			this.minore = minore;
			this.annoNascitaMinore = minore.getAnnoNascita();
			setLstConsuntivazioni(findConsuntivazioniDaStruttura());
			setLstConsuntivazioniErogate(findConsuntivazioniErogate());
			if(lstConsuntivazioniErogate !=null && lstConsuntivazioniErogate.size() >0  ) {
				dataUltimaConsuntivazione = lstConsuntivazioniErogate.get(lstConsuntivazioni.size()-1).getDataA();
			}
		
			caricaSelectedRichiestaDocumenti();

		} catch (Exception e) {
			logger.error("Errore Caricamento PTI "+ e.getMessage(), e);
		}
	}

	private CsPaiPTIDTO findPTIByPai(Long diarioPaiId) {

		BaseDTO bdto = new BaseDTO();
		bdto.setObj(diarioPaiId);
		fillEnte(bdto);

		try {
			return paiPTIService.findPTIByDiarioPaiId(bdto);

		} catch (Exception e) {
			addError("Inizializzazione PTI", "PTI non inizializzato");
			logger.error("Errore inizializzazione PTI", e);
		}
		return new CsPaiPTIDTO();
	}

	private List<CsPaiPtiRevisioniDTO> findRevisioniPTI(Long idPaiPTI) {

		BaseDTO bdto = new BaseDTO();
		bdto.setObj(idPaiPTI);
		fillEnte(bdto);

		try {
			return paiPTIService.findRevisioniPTI(bdto);

		} catch (Exception e) {
			addError("Inizializzazione PTI", "PTI non inizializzato");
			logger.error("Errore inizializzazione PTI", e);
		}
		return null;
	}

	private boolean maggiorenne(Integer annoNascita) {
		boolean mag = false;
		Date dataRif = new Date();
		if (dataRif != null && annoNascita != null) {
			Calendar calRif = Calendar.getInstance();
			calRif.setTime(dataRif);
		

			int diffAnni = calRif.get(Calendar.YEAR) - annoNascita;

			if (diffAnni > 21) 
				mag = true;
			
		}
		return mag;
	}
	
	public String validaPTIPai(Integer annoNascita) {
	
		if (this.pti.getPeriodoInsPianificazioneA().before(this.pti.getPeriodoInsPianificazioneDa())) {
			return "Periodo di inserimento pianificato DA deve essere antecedente il Periodo di inserimento pianificato A";
		}

		
		if (this.pti.getFlgAreaPenale() && (this.pti.getPeriodoInsPianificazioneDa() == null
				|| this.pti.getPeriodoInsPianificazioneA() == null)) {
			return "Il periodo di inserimento pianificato per un minore proveniente dall’area penale è un dato obbligatorio";
		}

         if (this.maggiore25(annoNascita)) {
			
			return "Il beneficiario a più di 25 anni. Non può usufruire di un progetto di tipo PTI";
		
		}
		
		if (!this.pti.getFlgEmergenza()) {
			boolean esisteDocSinba = false;
			if (this.pti.getId() != null) {
				if (this.pti.getDocumentiPTI().size() > 0) {
					for (ArCsPaiPTIDocumentoDTO documento : this.pti.getDocumentiPTI()) {
						if (documento.getTipoDocumento().equalsIgnoreCase("SINBA_DOC")) {
							esisteDocSinba = true;
							break;
						}
					}
				}
				if(this.documentoSinbaDaSalvare != null) {//CASO DEL PTI EQUIVALENTE)
					esisteDocSinba= true;
				}
			}
			else {
				if(this.documentoSinbaDaSalvare != null) {
					esisteDocSinba= true;
				}
			}
			if (this.pti.getDiarioSinbaId() != null || !esisteDocSinba) {
				
				return "Per un PTI non in emergenza è necessario caricare una Valutazione Multidimensionale";
			}
		}
		return null;
	}

	public void salva(Long diarioPaiId, CsPaiMastSoggDTO minore, DualListModel<DatiInterventoBean> pickListInterventi,
			DualListModel<ErogInterventoRowBean> pickListErogazioni, Date dataDiarioDa, Date dataDiarioA,
			CsPaiPtiRevisioniDTO ptiRevisione, boolean isCLosed) throws Exception {
		// Qua vanno ricaricate le strutture selezionate :

		pti.setDiarioPaiId(diarioPaiId);
		popolaRichiesteDisponibilita(minore);

		BaseDTO bdto = new BaseDTO();
		fillEnte(bdto);
		bdto.setObj(pti);

		/**
		 * calcolo della fase in base alla fase precedente e ai dati inseriti
		 */
		if (idFase == null) {
//			da capire se utilizzare una fase anche per l'emergenza
//			if (PTI.getFlgEmergenza()) {
//				idFase = PaiPTIFaseEnum.INS.getId();
//			}

			if (!pti.getRichiesteDisponibilita().isEmpty()) {
				if (pti.getFlgEmergenza()) {
					idFase = PaiPTIFaseEnum.STRUTT_OK.getId();
				} else {
					idFase = PaiPTIFaseEnum.RICH_DISP.getId();
				}

			} else {
				idFase = PaiPTIFaseEnum.INS.getId();
			}
		} else if (idFase == PaiPTIFaseEnum.INS.getId()) {
			if (!pti.getRichiesteDisponibilita().isEmpty()) {
				if (pti.getFlgEmergenza()) {
					idFase = PaiPTIFaseEnum.STRUTT_OK.getId();
				} else {
					idFase = PaiPTIFaseEnum.RICH_DISP.getId();
				}
			}
		} else if (idFase == PaiPTIFaseEnum.RICH_DISP.getId()) {
			// gestione eventuale erogazione

		} else if (idFase.equals(PaiPTIFaseEnum.STRUTT_OK.getId())) {
			if (pickListInterventi != null && pickListInterventi.getTarget() != null) {

				for (DatiInterventoBean i : pickListInterventi.getTarget()) {
					for (CsFlgIntervento f : i.getListaFogli()) {
						if (f.getCsDDiario().getDtChiusuraDa() == null) {
//							
							idFase = PaiPTIFaseEnum.EROG_OK.getId();
						} else {// nel caso in cui mi eliminino una erogazione per me lo stato torna su
								// STRUTT_OK
							idFase = PaiPTIFaseEnum.STRUTT_OK.getId();
						}

					}
				}

			}

			// vado a vedere se esistono erogazioni avviate
			// NON DEVO CONTROLLARE IL TIPO DI INTERVENTO PERCHè IL SOLO POSSIBILE è
			// sTRUTTURE RESIDENZIALI controllo se esiste erogazione aperta
			if (pickListErogazioni != null && pickListErogazioni.getTarget() != null) {
				// per ogni mast controllo le erogazioni associate
				for (ErogInterventoRowBean erb : pickListErogazioni.getTarget()) {
					for (ErogazioneDettaglioDTO ed : erb.getLstInterventiEseguiti()) {
						//
						if (ed.getStatoErogazione().getTipo().equalsIgnoreCase("E")
								&& between(ed.getDataErogazione(), dataDiarioDa, dataDiarioA)) {

//							if (!ed.getStatoErogazione().getFlagChiudi()) { //posso anche non vedere se chiusa o aperta
							idFase = PaiPTIFaseEnum.EROG_OK.getId();
							break;
						} else {// nel caso in cui mi eleiminino una erogazione per me lo stato torna su
								// STRUTT_OK
							idFase = PaiPTIFaseEnum.STRUTT_OK.getId();
						}
					}
				}
			}

		} else if (idFase.equals(PaiPTIFaseEnum.EROG_OK.getId())) {
			// Se la dataa del diario è valorizzata vuol dire che sto chiudendo il PTI
			// quindi la fase diventa CHIUSO_OK
			if (dataDiarioA != null) {
				idFase = PaiPTIFaseEnum.CHIUSA_OK.getId();
			}
		}

		if (isModifica && this.pti.getFlgEmergenza()) {
			this.pti.setFlgEmergenza(false);
		}
		bdto.setObj2(idFase);
		fillEnte(bdto);

		try {
			this.pti = this.paiPTIService.savePTI(bdto);
			// Salvataggio file Sinba

			if (this.documentoSinbaDaSalvare != null)
				this.salvaDocumentoSinba();
			
			if (isCLosed) {
				if (this.pti.getIdStruttura() != null) {
					addAlertStruttura(minore, this.pti.getIdStruttura(), "CHIUSURA");
				}
			} else {
				addAlertUpdPTI(minore, "CASE_MANAGER", "AGGIORNAMENTO");

				if (this.pti.getIdStruttura() != null) {
					addAlertUpdPTI(minore, "STRUTTURA", "AGGIORNAMENTO");
				}

			}
			
			
			if (ptiRevisione != null) {
				// salvaRevisione

				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				ptiRevisione.setDtIns(new Date());
				ptiRevisione.setUserIns(getCurrentUsername());
				dto.setObj(ptiRevisione);
				this.paiPTIService.savePtiRevisione(dto);
				this.setVisualizzaMotiviRevisione(false);
				
				//REVISIONE PTI :qui va aggiunto l'alert per Case Manager e per Struttura se selezionata
				addAlertUpdPTI(minore, "CASE_MANAGER", "REVISIONE");
				
				if (this.pti.getIdStruttura() != null) {
					addAlertUpdPTI(minore, "STRUTTURA", "REVISIONE");
				}
			}

			// reset
			this.pti = null;
						
			addInfo("Salvataggio PTI", "PTI Salvato correttamente");
			logger.debug("PTI Salvato correttamente");
		} catch (Exception e) {
			addError("Salvataggio PTI", "Errore salvataggio PTI");
			logger.error("Errore salvataggio PTI", e);
		}
	}

	private static boolean between(Date d, Date min, Date max) {
		if (d == null)
			return true;

		if (max != null && min == null)
			return !d.after(max);
		if (min != null && max == null)
			return !d.before(min);
		if (min != null && max != null)
			return (!d.before(min) && !d.after(max));

		return false;
	}

	private void popolaRichiesteDisponibilita(CsPaiMastSoggDTO minore) throws Exception {
		CsOOperatore op = null;

		List<RichiestaDisponibilitaPaiPtiDTO> elencoDisponibilita = pti.getRichiesteDisponibilita();

		if (elencoDisponibilita == null) {
			elencoDisponibilita = new ArrayList<RichiestaDisponibilitaPaiPtiDTO>();
		}

		// Decodifica del opertatore Case MAnager
		if (this.pti.getIdCaseManager() != null ) {//		&& (this.pti.getCaseManager() == null || this.pti.getCaseManager().isEmpty())) {
			OperatoreDTO odto = new OperatoreDTO();
			fillEnte(odto);
			odto.setIdOperatore(this.pti.getIdCaseManager());
			op = operatoreService.findOperatoreById(odto);
		}
		this.pti.setCaseManager(op.getDenominazione());


		//Decodifica Tipo Minore
		if(this.pti.getTipoMinore() != null) {
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(this.pti.getTipoMinore());
			CsTbTipoMinore csTbTipoMinore = confService.getTipoMinoreById(dto);
			this.pti.setDescTipoMinore(csTbTipoMinore.getTipoMinore());
		}
		
//		if (this.struttureSelezionate.size() > 0) {
//			for (StrutturaDisponibilitaDTO strutturaDispSelected : struttureSelezionate) {
		if (this.dtStruttureSelezionate.size() > 0) {
			for (StrutturaDisponibilitaDTO strutturaDispSelected : dtStruttureSelezionate) {
				if (strutturaDispSelected.getRichiesteDisponibilita() != null) {
					// add vecchia disponibilita
//						RichiestaDisponibilitaPaiPtiDTO richiestaDisp = this
//								.findDisponibilitaById(strutturaDispSelected.getRichiesteDisponibilita().getId());
					elencoDisponibilita.add(strutturaDispSelected.getRichiesteDisponibilita());
				} else {
					// nuova richiesta disponibilita aggiunta
					elencoDisponibilita.add(createNewRichDisp(minore,strutturaDispSelected.getIdStruttura()));
				}

			}
		}
		pti.setRichiesteDisponibilita(elencoDisponibilita);
	}

	private RichiestaDisponibilitaPaiPtiDTO createNewRichDisp(CsPaiMastSoggDTO minore, Long idStruttura) throws Exception {
		RichiestaDisponibilitaPaiPtiDTO disponibilita = new RichiestaDisponibilitaPaiPtiDTO(minore);
		disponibilita.setIdStruttura(idStruttura);
		disponibilita.setDataRichiesta(new Date());
		disponibilita.setStatoRichiesta("INVIATA");
		if (this.pti.getFlgEmergenza()) {
			disponibilita.setStatoRichiesta("ACCETTATA");
			this.pti.setIdStruttura(idStruttura);
			//TODO add Alert 
			addAlertStruttura(minore, idStruttura, "ACCETTATA");
			addAlertCaseManager(minore, idStruttura, "ACCETTATA");
		}
		else {
			addAlertStruttura(minore, idStruttura, "APERTURA");
		}
		disponibilita.setDataInizioPermamenza(this.pti.getPeriodoInsPianificazioneDa());
		disponibilita.setDataFinePermanenza(this.pti.getPeriodoInsPianificazioneA());
		disponibilita.setRichAttiva(true);
		return disponibilita;
	}
	
	private void accettaRichDisp(Long idRichiestaAccettata) throws Exception {
		for (RichiestaDisponibilitaPaiPtiDTO rich : this.pti.getRichiesteDisponibilita()) {
			if (rich.getId().equals(idRichiestaAccettata)) {
				rich.setStatoRichiesta("ACCETTATA");

				// aggiorno le date con quelle scelte dalla struttura
//				Date da = rich.getDataInizioPermamenza();
//				Date a = rich.getDataFinePermanenza();
//				this.pti.setPeriodoInsPianificazioneDa(da);
//				this.pti.setPeriodoInsPianificazioneA(a);
				
				//TODO add Alert 
				addAlertStruttura(minore, rich.getIdStruttura(), "ACCETTATA");
				addAlertCaseManager(minore, rich.getIdStruttura(), "ACCETTATA");
				continue;
			}

			rich.setRichAttiva(false);
		}
	}

	private void rifiutaRichDisp(Long idRichiestaRifiutata) {
		for (RichiestaDisponibilitaPaiPtiDTO rich : this.pti.getRichiesteDisponibilita()) {
			if (rich.getId().equals(idRichiestaRifiutata)) {
				rich.setStatoRichiesta("RIFIUTATA");
				continue;
			}

		}
	}

	public List<SelectItem> getListaTipiStruttura() {
		if (listaTipiStruttura == null || listaTipiStruttura.isEmpty()) {
			Long tipoFunzione = DataModelCostanti.TipoFunzioneStruttura.STRUTTURE_MINORI;

			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(tipoFunzione);
			List<TipoStruttura> lst = confService.getLstTipoStrutturaByTipoFunzione(dto);
			listaTipiStruttura = new ArrayList<SelectItem>();
			if (lst != null) {
				for (TipoStruttura obj : lst) {
					listaTipiStruttura.add(new SelectItem(obj.getId(), obj.getDescrizione()));
				}
			}
		}

		return listaTipiStruttura;
	}

	public void setListaTipiStruttura(List<SelectItem> listaTipiStruttura) {
		this.listaTipiStruttura = listaTipiStruttura;
	}

	public void onChangeTipoStruttura(AjaxBehaviorEvent event) throws Exception {

		 tipoStruttura = (Long) ((javax.faces.component.UIInput) event.getComponent()).getValue();
			if (this.annoNascitaMinore !=null && this.annoNascitaMinore>0) {
				lstStrutture = findElencoStrutture(tipoStruttura);
				this.pti.setTipoStruttura(tipoStruttura);
				this.lstStruttureFiltered = lstStrutture;
			}else {
				addError("Selezione tipo struttura", "Non è stata indicata la data di nascita del minore");
				getListaTipiStruttura();
				RequestContext.getCurrentInstance().update("pnlRichiestaDisp");
			}
	
        
	}
	
	public void onChangeEmergenza(AjaxBehaviorEvent event) throws Exception {
//		Boolean flgEmergenza = this.pti.getFlgEmergenza();
		Boolean flgEmergenza = (Boolean) ((javax.faces.component.UIInput) event.getComponent()).getValue();
		if (flgEmergenza) {
			dtStruttureSelezionate.clear();
		}
		
		//this.pti.setFlgEmergenza(flgEmergenza);

	}

	private List<VStrutturaArea> findAllStruttura() {

		try {
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);
			return confService.findAllStruttura(cet);

		} catch (Exception e) {
			addError("Inizializzazione PTI", "PTI non inizializzato");
			logger.error("Errore inizializzazione PTI", e);
		}
		return null;
	}

	public List<StrutturaDisponibilitaDTO> findElencoStrutture(Long tipoStruttura) {
		try {
			List<StrutturaDisponibilitaDTO> struttureDaVis = new ArrayList<StrutturaDisponibilitaDTO>();
			if(annoNascitaMinore!=null && annoNascitaMinore>0) {
				int year = Calendar.getInstance().get(Calendar.YEAR);
				int etaBeneficiario = year - annoNascitaMinore;
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(tipoStruttura);
				dto.setObj2(etaBeneficiario);
				struttureDaVis = paiPTIService.findElencoStrutture(dto);
			}
			return struttureDaVis;
			
		} catch (Exception e) {
			addError("Caricamento Strutture Nuovo Progetto Individuale", "Errore nel caricamento Strutture Nuovo Progetto Individuale");
			logger.error("Errore Caricamento Strutture Nuovo Progetto Individuale", e);
		}

		return null;
	}

/*	private RichiestaDisponibilitaPaiPtiDTO findDisponibilitaById(Long idRichiestaDisponibilita) {

		BaseDTO bdto = new BaseDTO();
		bdto.setObj(idRichiestaDisponibilita);
		fillEnte(bdto);

		try {
			return paiPTIService.findDisponibilitaById(bdto);

		} catch (Exception e) {
			addError("Caricamento Disponibilita", "Errore nel caricamento della disponibilità");
			logger.error("Errore Caricamento disponibilita by id", e);
		}

		return null;
	}
*/
	private List<StrutturaDisponibilitaDTO> findElencoStruttureDisponibilita(Long idProgettoIndividuale) {

		BaseDTO bdto = new BaseDTO();
		bdto.setObj(idProgettoIndividuale);
		fillEnte(bdto);

		try {
			return paiPTIService.findElencoStruttureDisponibilita(bdto);

		} catch (Exception e) {
			addError("Caricamento Strutture", "Errore nel caricamento delle strutture");
			logger.error("Errore Caricamento Strutture", e);
		}

		return null;
	}

	public void onRowSelect(SelectEvent event) {
		List<StrutturaDisponibilitaDTO> listaStrutt = dtStruttureSelezionate;

		logger.debug("carico strutture selezionate");
	}

	public boolean actionVisibili(StrutturaDisponibilitaDTO struttura) {
		if (this.idFase != null && this.idFase.equals(PaiPTIFaseEnum.INS.getId())
				&& !this.idFase.equals(PaiPTIFaseEnum.RICH_DISP.getId())) {
			return false;
		}
		if (struttura.getRichiesteDisponibilita() != null
				&& struttura.getRichiesteDisponibilita().getStatoRichiesta().equalsIgnoreCase("ACCETTATA_STRUTTURA")) {
			return true;
		}
		return false;
	}

	public boolean hasRequestInfo(StrutturaDisponibilitaDTO struttura) {
		return struttura.isAccettataFromStrutt() || struttura.isAccettataFromCS();
	}

	public Boolean disabilitaSelezioneStruttura(StrutturaDisponibilitaDTO struttura) {
//	 return	this.pti.getFaseAttuale()== PaiPTIFaseEnum.STRUTT_OK.getId()
//			 || this.pti.getFaseAttuale()== PaiPTIFaseEnum.EROG_OK.getId()
//			 || (!struttura.getRichiesteDisponibilita().isEmpty() && (struttura.getRichiesteDisponibilita().get(0).getStatoRichiesta() == "ACCETTATA" 
//			 || struttura.getRichiesteDisponibilita().get(0).getStatoRichiesta() == "RIFIUTATA")) ;
		return true;
	}

	public List<SelectItem> getListaCaseManager() {
		try {
			if (listaCaseManager == null || listaCaseManager.isEmpty()) {
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				Long settoreId = dto.getSettoreId();

				if (settoreId != null) {
					// dal settore la lista degli operatori
					OperatoreDTO oDto = new OperatoreDTO();
					fillEnte(oDto);
					oDto.setIdSettore(settoreId);
					List<KeyValueDTO> lstOpSettAnagrafica = operatoreService.findOperatoreIdAnagraficaBySettore(oDto);

					for (KeyValueDTO c : lstOpSettAnagrafica) {
						SelectItem si = new SelectItem(c.getCodice(), c.getDescrizione());
						si.setDisabled(!c.isAbilitato());
						listaCaseManager.add(si);
					}

				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return listaCaseManager;
	}

	public CsDSinba caricaValutazioneSinba(CsASoggettoLAZY soggetto) {
		long idTipoDiario = DataModelCostanti.TipoDiario.VALUTAZIONE_SINBA;
		CsDSinba csDSinba = new CsDSinba();
		List<CsDSinba> lstSinbas = new ArrayList<CsDSinba>();
		BaseDTO dto = new BaseDTO();
		CsUiCompBaseBean.fillEnte(dto);
		dto.setObj(soggetto.getCsACaso().getId());
		dto.setObj2(idTipoDiario);
		try {
			lstSinbas = sinbaService.getListaSchedaSinbaByCaso(dto);

			if (lstSinbas.isEmpty()) {
				throw new Exception();

			}

			// ultimo in base alla data amministrativa desc
			Collections.sort(lstSinbas, Collections.reverseOrder(new Comparator<CsDSinba>() {

				@Override
				public int compare(CsDSinba o1, CsDSinba o2) {
					return o1.getCsDDiario().getDtIns().compareTo(o2.getCsDDiario().getDtIns());
				}

			}));

			csDSinba = lstSinbas.get(0);
			return csDSinba;
		} catch (Exception e) {
			logger.error(e);
		}
		return csDSinba;
	}

	public void setListaCaseManager(List<SelectItem> listaCaseManager) {
		this.listaCaseManager = listaCaseManager;
	}

	public List<SelectItem> getLstMotiviRevisione() {
		if (lstMotiviRevisione.size() <= 0) {

			for (PaiPTIMotivoRevisioneEnum motivo : PaiPTIMotivoRevisioneEnum.values()) {
				lstMotiviRevisione.add(new SelectItem(motivo.getId(), motivo.getDescrizione()));
			}
		}
		return lstMotiviRevisione;
	}

	public List<SelectItem> getLstTipoProroga() {
		if (lstTipoProroga.size() <= 0) {

			for (PaiPTITipoProrogaEnum proroga : PaiPTITipoProrogaEnum.values()) {
				lstTipoProroga.add(new SelectItem(proroga.getId(), proroga.getDescrizione()));
			}
		}
		return lstTipoProroga;
	}

	public void setLstMotiviRevisione(List<SelectItem> lstMotiviRevisione) {
		this.lstMotiviRevisione = lstMotiviRevisione;
	}

	public boolean isStrutturaRowDisabled(StrutturaDisponibilitaDTO struttDispo)throws Exception {
		if (struttDispo.getRichiesteDisponibilita() != null && struttDispo.getRichiesteDisponibilita().getStatoRichiesta() != null) {
			return true;
		}

		if (this.idFase != null && !PaiPTIFaseEnum.INS.getId().equals(this.idFase)
				&& !PaiPTIFaseEnum.RICH_DISP.getId().equals(this.idFase)) {
			return true;
		}

		return false;
	}
	
	public StreamedContent preparaPaiFile() {
		if (strutturaSelezionata != null && strutturaSelezionata.getRichiesteDisponibilita() != null) {
			try {
				byte[] documento = retrievePaiStruttSelected();
				InputStream is = new ByteArrayInputStream(documento);
				String nomeDocumento = strutturaSelezionata.getRichiesteDisponibilita().getNomeDocumento();
				String mimeType = URLConnection.guessContentTypeFromStream(is);
				return new DefaultStreamedContent(is, mimeType, nomeDocumento);
			} catch (Exception e) {
				logger.error("preparaPaiFile: " +e.getMessage(), e);
			}
		}
		return null;
	}

	public byte[] retrievePaiStruttSelected() {
		if (strutturaSelezionata != null) {
			return strutturaSelezionata.getRichiesteDisponibilita().getDocumento();
		}
		return null;

	}

	public void handleAllegato(FileUploadEvent event) {
		UploadedFile selectedFile = event.getFile();
		ArCsPaiPTIDocumentoDTO documento = new ArCsPaiPTIDocumentoDTO();
		documento.setDtIns(new Date());
		documento.setUsrIns(getCurrentUsername());
		documento.setValidoDa(new Date());
		documento.setDocumento(selectedFile.getContents());
		documento.setNome(selectedFile.getFileName());
		documento.setTipo(selectedFile.getContentType());
		documento.setTipoDocumento("SINBA_DOC");
		
//		if (this.pti.getId() != null) {
//			if (this.pti.getDocumentiPTI().size() > 0) {
//				for (ArCsPaiPTIDocumentoDTO documento : this.pti.getDocumentiPTI()) {
//					if (documento.getTipoDocumento().equalsIgnoreCase("SINBA_DOC")) {
//						esisteDocSinba = true;
//						break;
//					}
//				}
//			}
//		}
		
		
//		if (this.pti.getId() != null) {
//			documento.setIdPaiPTI(this.pti.getId());
//			this.pti.getDocumentiPTI().add(documento);
//		}
//		else
			setDocumentoSinbaDaSalvare(documento);

		RequestContext.getCurrentInstance().update("pnlTP");
	}

	public void salvaDocumentoSinba() throws IllegalAccessException, InvocationTargetException, Exception {
		BaseDTO bdto = new BaseDTO();
		fillEnte(bdto);
		documentoSinbaDaSalvare.setIdPaiPTI(this.pti.getId());
		documentoSinbaDaSalvare.setCodRouting(this.pti.getCodRouting());
		bdto.setObj(documentoSinbaDaSalvare);

		this.paiPTIService.saveDocumento(bdto);
	}

	public void prepareFile() {
		InputStream is = new ByteArrayInputStream(this.documentoSinba.getDocumento());
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		setFile(new DefaultStreamedContent(is, externalContext.getMimeType(documentoSinba.getNome()),
				documentoSinba.getNome()));
	}

	public void prepareFile(ArCsPaiPTIDocumentoDTO rowDoc) {
		try {
			InputStream is = new ByteArrayInputStream(rowDoc.getDocumento());
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

			String mimeType;

			mimeType = URLConnection.guessContentTypeFromStream(is);

			is.close();

			setFile(new DefaultStreamedContent(is, externalContext.getMimeType(mimeType), rowDoc.getNome()));

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

	}

	public ArCsPaiInfoSinteticheDTO readInfoSintetiche() {

		infoSintetichePaiSelected = new ArCsPaiInfoSinteticheDTO();
		try {
			if (this.getSelectedPaiPTI() != null && this.getSelectedPaiPTI().getArCsPaiInfoSinteticheDTO() != null) {

				infoSintetichePaiSelected = this.getSelectedPaiPTI().getArCsPaiInfoSinteticheDTO();

			}
			return infoSintetichePaiSelected;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;

		}
	}

	private List<InserimentoConsuntivazioneDTO> findConsuntivazioniDaStruttura() {

		BaseDTO bdto = new BaseDTO();
		bdto.setObj(this.pti.getId());
		bdto.setObj2(this.pti.getCodRouting());
		fillEnte(bdto);

		try {
			return paiPTIService.findConsuntivazioneDaStruttura(bdto);

		} catch (Exception e) {
			addError("Caricamento Consuntivazioni", "Errore nel caricamento delle consuntivazioni");
			logger.error("Errore Caricamento Consuntivazioni", e);
		}

		return null;
	}
	
	private List<InserimentoConsuntivazioneDTO> findConsuntivazioniErogate() {

		BaseDTO bdto = new BaseDTO();
		bdto.setObj(this.pti.getId());
		bdto.setObj2(this.pti.getCodRouting());
		fillEnte(bdto);

		try {
			return paiPTIService.findConsuntivazioneErogate(bdto);

		} catch (Exception e) {
			addError("Caricamento Consuntivazioni Erogate", "Errore nel caricamento delle consuntivazioni erogate");
			logger.error("Errore Caricamento Consuntivazioni", e);
		}

		return null;
	}

	public void aggiornaConsuntivazione(InserimentoConsuntivazioneDTO consuntivazione) {
		try {

			if (consuntivazione != null) {
				fillEnte(consuntivazione);
				paiPTIService.salvaConsuntivazione(consuntivazione);

			}

		} catch (Exception e) {
			addError("Aggiornamento Consuntivazioni", "Errore nel caricamento nel'aggiornamento delle consuntivazioni");
			logger.error("Errore Aggiornamento Consuntivazioni", e);
		}
	}

	public void caricaSelectedRichiestaDocumenti() {
		infoSintetichePai = new ArCsPaiInfoSinteticheDTO();
		listDocumentiPAI = new ArrayList<ArCsPaiPTIDocumentoDTO>();
		listDocumentiPTI = new ArrayList<ArCsPaiPTIDocumentoDTO>();

		List<ArCsPaiPTIDocumentoDTO> listDocumentiRichiesta = this.pti.getDocumentiPTI();

		for (ArCsPaiPTIDocumentoDTO doc : listDocumentiRichiesta) {
			if ("PAI_DOC".equalsIgnoreCase(doc.getTipoDocumento())) {
				// recupero le info sintetiche relative al documento valido
				if(doc.getValidoA()==null) {
					infoSintetichePai = doc.getArCsPaiInfoSinteticheDTO();
				}
				listDocumentiPAI.add(doc);
				
			} else if ("PTI_DOC".equalsIgnoreCase(doc.getTipoDocumento())) {
				listDocumentiPTI.add(doc);
			} else if ("SINBA_DOC".equalsIgnoreCase(doc.getTipoDocumento())) {
				documentoSinba = doc;
			}
		}
	}
	private void readListaTipoMinoreBeneficiario() throws Exception {
		List<KeyValueDTO> lstTipoMinore = new ArrayList<KeyValueDTO>();
		try {
			CeTBaseObject cet = new CeTBaseObject();
			fillEnte(cet);			
			lstTipoMinore = confService.getListaTipoMinore(cet);
			lstTipoMinoreBeneficiario = convertiLista(lstTipoMinore);
		}catch (Exception e) {
			logger.error("Errore Caricamento PTI ", e);
		}
	}
	
	public void onDocPaiRowEdit(RowEditEvent event) {
		ArCsPaiPTIDocumentoDTO doc = (ArCsPaiPTIDocumentoDTO) event.getObject();
		BaseDTO bdto = new BaseDTO();
		fillEnte(bdto);
		bdto.setObj(doc);
		try {
			this.paiPTIService.saveDocumento(bdto);
			addInfo("Modifica Documento", "Modifica effettuata correttamente");
		} catch (Exception e) {
			addError("Modifica Documento", "Si è verificato un errore nella modifica del documento");
			logger.error(e.getMessage(), e);
		}
	}

	private void addAlertUpdPTI( CsPaiMastSoggDTO minore, String tipoPTIAlert, String tipoOperazione) throws Exception {
		try {
			
		
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		AlertDTO altDTO = new AlertDTO();
		altDTO.setEnteId(dto.getEnteId());
		altDTO.setUserId(dto.getUserId());
		altDTO.setSessionId(dto.getSessionId());
		//MITTENTE
		CsOOperatoreSettore operatoreSettore = getCurrentOpSettore();
		altDTO.setOrganizzazioneFrom(operatoreSettore.getCsOSettore().getCsOOrganizzazione());
		altDTO.setSettoreFrom(operatoreSettore.getCsOSettore());
		altDTO.setOperatoreFrom(operatoreSettore.getCsOOperatore());
		
		//DESTINATARIO
		if (tipoPTIAlert.equalsIgnoreCase("CASE_MANAGER")) {
			OperatoreDTO odto = new OperatoreDTO();
			CsOOperatoreSettore opSett = null;
			fillEnte(odto);
			odto.setIdOperatore(this.pti.getIdCaseManager());
			odto.setIdSettore(operatoreSettore.getCsOSettore().getId());//è equipe
			opSett = operatoreService.findOperatoreSettoreById(odto);
			altDTO.setOpSettoreTo(opSett);			
		
		}
		else if (tipoPTIAlert.equalsIgnoreCase("STRUTTURA")){
				
			CsOSettore csSOSettore = new CsOSettore();
			BaseDTO dto2 = new BaseDTO();
			dto2.setObj(this.pti.getIdStruttura());
			fillEnte(dto2);
			csSOSettore = confService.findSettoreByRelStruttura(dto2);	
			
			altDTO.setSettoreTo(csSOSettore);
		}
		altDTO.setTipo(DataModelCostanti.TipiAlertCod.PAI);
		String today = ddMMyyyy.format(new Date());
	
		String tipoOpe = tipoOperazione == "REVISIONE" ? " una revisione " : "un aggiornamento ";
		
	    String descrizione = "L'operatore "
				+ operatoreSettore.getCsOOperatore().getCsOOperatoreAnagrafica().getCognome() + " "
				+ operatoreSettore.getCsOOperatore().getCsOOperatoreAnagrafica().getNome()
				+ " ha eseguito " + tipoOpe + " del progetto PTI per il soggetto " + minore.getCognome() + " " +  minore.getNome()
				+ " in data "+ today + " <br/><br/>"; 
	
		
		altDTO.setDescrizione(descrizione);
		
		String titoloOp = tipoOperazione == "REVISIONE" ? "Revisione " : "Aggiornamento ";
		
		String titolo = titoloOp  +  minore.getCognome() + " " +  minore.getNome();
		altDTO.setTitolo(titolo);
		
		alertService.addAlert(altDTO);
		
		} catch (Exception e) {
			addError("ERRORE ALERT", "Si è verificato un errore nell'inserimento dell'alert");
			logger.error(e.getMessage(), e);
		}
	}
	
	public void addAlertStruttura(CsPaiMastSoggDTO minore, Long idStruttura, String tipoOperazione) throws Exception {
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		AlertDTO altDTO = new AlertDTO();
		altDTO.setEnteId(dto.getEnteId());
		altDTO.setUserId(dto.getUserId());
		altDTO.setSessionId(dto.getSessionId());
		
		String descrizione ="";
		String tipoOpeTitolo = "";
		//MITTENTE
		CsOOperatoreSettore operatoreSettore = getCurrentOpSettore();
		altDTO.setOrganizzazioneFrom(operatoreSettore.getCsOSettore().getCsOOrganizzazione());
		altDTO.setSettoreFrom(operatoreSettore.getCsOSettore());
		altDTO.setOperatoreFrom(operatoreSettore.getCsOOperatore());
		
		CsOSettore csSOSettore = new CsOSettore();
		BaseDTO dto2 = new BaseDTO();
		dto2.setObj(idStruttura);
		fillEnte(dto2);
		csSOSettore = confService.findSettoreByRelStruttura(dto2);	
		altDTO.setSettoreTo(csSOSettore);
		
		altDTO.setTipo(DataModelCostanti.TipiAlertCod.PAI);
		String today = ddMMyyyy.format(new Date());
		
    	if(tipoOperazione.equalsIgnoreCase("CHIUSURA") ) {
    		
    	  		
    	     descrizione = "L'operatore "
    				+ operatoreSettore.getCsOOperatore().getCsOOperatoreAnagrafica().getCognome() + " "
    				+ operatoreSettore.getCsOOperatore().getCsOOperatoreAnagrafica().getNome()
    				+ " ha chiuso il PTI per il soggetto " + minore.getCognome() + " " +  minore.getNome()
    				+ " in data "+ today + " <br/><br/>"; 
    	
    	    tipoOpeTitolo = "Chiusura PTI per " ;
    	}
    	else if (tipoOperazione.equalsIgnoreCase("ACCETTATA") ) {
    		
   	     descrizione = "L'operatore "
   				+ operatoreSettore.getCsOOperatore().getCsOOperatoreAnagrafica().getCognome() + " "
   				+ operatoreSettore.getCsOOperatore().getCsOOperatoreAnagrafica().getNome()
   				+ " ha confermato l'inserimento  del minore "  + minore.getCognome() + " " +  minore.getNome()
   				+ " nella Vs. struttura "
   				+ " in data "+ today + " <br/><br/>"; 
   	
   	    tipoOpeTitolo = "Inserimento in struttura di " ;
    		
    	}
    	else if (tipoOperazione.equalsIgnoreCase("APERTURA") ) {
    		
      	     descrizione = "L'operatore "
      				+ operatoreSettore.getCsOOperatore().getCsOOperatoreAnagrafica().getCognome() + " "
      				+ operatoreSettore.getCsOOperatore().getCsOOperatoreAnagrafica().getNome()
      				+ " ha aperto un PTI per il minore "  + minore.getCognome() + " " +  minore.getNome()
      				+ " e richiede la disponibilità  "
      				+ " della Vs. struttura "
      				+ " in data "+ today + " <br/><br/>"; 
      	
      	    tipoOpeTitolo = "Apertura PTI e richiesta disponibilità " ;
       		
       	}
		
		altDTO.setDescrizione(descrizione);
		
		
		
		String titolo = tipoOpeTitolo +  minore.getCognome() + " " +  minore.getNome();
		altDTO.setTitolo(titolo);
		
		alertService.addAlert(altDTO);
		
		
	}
	
	private void addAlertCaseManager( CsPaiMastSoggDTO minore,Long idStruttura,  String tipoOperazione) throws Exception {
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		AlertDTO altDTO = new AlertDTO();
		altDTO.setEnteId(dto.getEnteId());
		altDTO.setUserId(dto.getUserId());
		altDTO.setSessionId(dto.getSessionId());
		
		String descrizione ="";
		String tipoOpeTitolo = "";
		//MITTENTE
		CsOOperatoreSettore operatoreSettore = getCurrentOpSettore();
		altDTO.setOrganizzazioneFrom(operatoreSettore.getCsOSettore().getCsOOrganizzazione());
		altDTO.setSettoreFrom(operatoreSettore.getCsOSettore());
		altDTO.setOperatoreFrom(operatoreSettore.getCsOOperatore());
		
		//DESTINATARIO
		OperatoreDTO odto = new OperatoreDTO();
		CsOOperatoreSettore opSett = null;
		fillEnte(odto);
		odto.setIdOperatore(this.pti.getIdCaseManager());
		odto.setIdSettore(operatoreSettore.getCsOSettore().getId());//è equipe
		opSett = operatoreService.findOperatoreSettoreById(odto);
		altDTO.setOpSettoreTo(opSett);			

		CsOSettore csSOSettore = new CsOSettore();
		BaseDTO dto2 = new BaseDTO();
		dto2.setObj(idStruttura);
		fillEnte(dto2);
		csSOSettore = confService.findSettoreByRelStruttura(dto2);	
		altDTO.setSettoreTo(csSOSettore);
		
		altDTO.setTipo(DataModelCostanti.TipiAlertCod.PAI);
		String today = ddMMyyyy.format(new Date());
	
		
  	     descrizione = "L'operatore "
  				+ operatoreSettore.getCsOOperatore().getCsOOperatoreAnagrafica().getCognome() + " "
  				+ operatoreSettore.getCsOOperatore().getCsOOperatoreAnagrafica().getNome()
  				+ " ha confermato l'inserimento  del minore "  + minore.getCognome() + " " +  minore.getNome()
  				+ " nella struttura " + csSOSettore.getNome()
  				+ " in data "+ today + " <br/><br/>"; 
  	
  	   tipoOpeTitolo = "Inserimento nella struttura  " ;
   		
    	
		altDTO.setDescrizione(descrizione);
		
		String titolo = tipoOpeTitolo  + csSOSettore.getNome() + " di " +  minore.getCognome() + " " +  minore.getNome();
		altDTO.setTitolo(titolo);
		
		alertService.addAlert(altDTO);
		
	}
	
	public boolean maggiore25(Integer annoNascita) {
		boolean mag = false;
		Date dataRif = new Date();
		if (dataRif != null && annoNascita != null) {
			Calendar calRif = Calendar.getInstance();
			calRif.setTime(dataRif);

			int diffAnni = calRif.get(Calendar.YEAR) - annoNascita;

			if (diffAnni > 21)
				mag = true;

		}
		return mag;
	}
	 
	public void disabilitaRevisione() {
		this.setModifica(false);
	}

	public void avviaCompletamentoPTI() {
		this.setModifica(true);
	}

	public void refreshFLagErogatoConsuntivazione() {
		setLstConsuntivazioni(findConsuntivazioniDaStruttura());
	}

	public void refreshListaStrutture() {
		dtStruttureSelezionate.clear();
		lstStrutture = this.findElencoStrutture(tipoStruttura);
		
	}
	
	public boolean isAbilitaModificaPti() {
		return this.isModifica ||  (this.pti!=null? (this.pti.getId()==null || this.pti.getId()==0) : false);
	}

	/**
	 * 
	 * Getter and Setter
	 * 
	 */

	public CsPaiPTIDTO getPti() {
		return pti;
	}

	public void setPti(CsPaiPTIDTO pti) {
		this.pti = pti;
	}

	public List<StrutturaDisponibilitaDTO> getLstStrutture() {
		return lstStrutture;
	}

	public void setLstStrutture(List<StrutturaDisponibilitaDTO> lstStrutture) {
		this.lstStrutture = lstStrutture;
	}

	public List<StrutturaDisponibilitaDTO> getDtStruttureSelezionate() {
		return dtStruttureSelezionate;
	}

	public void setDtStruttureSelezionate(List<StrutturaDisponibilitaDTO> dtStruttureSelezionate) {
		this.dtStruttureSelezionate = dtStruttureSelezionate;
	}

	public boolean isDisabledStruttureSelection() {
		return disabledStruttureSelection;
	}

	public void setDisabledStruttureSelection(boolean disabledStruttureSelection) {
		this.disabledStruttureSelection = disabledStruttureSelection;
	}

	public IValSinba getSinbaManBean() {
		return sinbaManBean;
	}

	public void setSinbaManBean(IValSinba sinbaManBean) {
		this.sinbaManBean = sinbaManBean;
	}

	public StrutturaDisponibilitaDTO getStrutturaSelezionata() {
		return strutturaSelezionata;
	}

	public void setStrutturaSelezionata(StrutturaDisponibilitaDTO strutturaSelezionata) {
		this.strutturaSelezionata = strutturaSelezionata;
	}

	public Long getIdFase() {
		return idFase;
	}

	public void setIdFase(Long idFase) {
		this.idFase = idFase;
	}

	public ArCsPaiPTIDocumentoDTO getDocumentoSinba() {
		return documentoSinba;
	}

	public void setDocumentoSinba(ArCsPaiPTIDocumentoDTO documentoSinba) {
		this.documentoSinba = documentoSinba;
	}

	public ArCsPaiPTIDocumentoDTO getDocumentoSinbaDaSalvare() {
		return documentoSinbaDaSalvare;
	}

	public void setDocumentoSinbaDaSalvare(ArCsPaiPTIDocumentoDTO documentoSinbaDaSalvare) {
		this.documentoSinbaDaSalvare = documentoSinbaDaSalvare;
	}

	public DefaultStreamedContent getFile() {
		return file;
	}

	public void setFile(DefaultStreamedContent file) {
		this.file = file;
	}

	public List<InserimentoConsuntivazioneDTO> getLstConsuntivazioni() {
		return lstConsuntivazioni;
	}

	public void setLstConsuntivazioni(List<InserimentoConsuntivazioneDTO> lstConsuntivazioni) {
		this.lstConsuntivazioni = lstConsuntivazioni;
	}

	public boolean isModifica() {
		return isModifica;
	}

	public void setModifica(boolean isModifica) {
		this.isModifica = isModifica;
	}

	public Boolean getVisualizzaMotiviRevisione() {
		return visualizzaMotiviRevisione;
	}

	public void setVisualizzaMotiviRevisione(Boolean visualizzaMotiviRevisione) {
		this.visualizzaMotiviRevisione = visualizzaMotiviRevisione;
	}

	public List<CsPaiPtiRevisioniDTO> getLstRevisioni() {
		return lstRevisioni;
	}

	public void setLstRevisioni(List<CsPaiPtiRevisioniDTO> lstRevisioni) {
		this.lstRevisioni = lstRevisioni;
	}

	public List<ArCsPaiPTIDocumentoDTO> getListDocumentiPAI() {
		return listDocumentiPAI;
	}

	public void setListDocumentiPAI(List<ArCsPaiPTIDocumentoDTO> listDocumentiPAI) {
		this.listDocumentiPAI = listDocumentiPAI;
	}

	public List<ArCsPaiPTIDocumentoDTO> getListDocumentiPTI() {
		return listDocumentiPTI;
	}

	public void setListDocumentiPTI(List<ArCsPaiPTIDocumentoDTO> listDocumentiPTI) {
		this.listDocumentiPTI = listDocumentiPTI;
	}

	public ArCsPaiInfoSinteticheDTO getInfoSintetichePai() {
		return infoSintetichePai;
	}

	public void setInfoSintetichePai(ArCsPaiInfoSinteticheDTO infoSintetichePai) {
		this.infoSintetichePai = infoSintetichePai;
	}

	public ArCsPaiPTIDocumentoDTO getSelectedPaiPTI() {
		return selectedPaiPTI;
	}

	public void setSelectedPaiPTI(ArCsPaiPTIDocumentoDTO selectedPaiPTI) {
		this.selectedPaiPTI = selectedPaiPTI;
	}

	public ArCsPaiInfoSinteticheDTO getInfoSintetichePaiSelected() {
		return infoSintetichePaiSelected;
	}

	public void setInfoSintetichePaiSelected(ArCsPaiInfoSinteticheDTO infoSintetichePaiSelected) {
		this.infoSintetichePaiSelected = infoSintetichePaiSelected;
	}

	public List<SelectItem> getLstTipoMinoreBeneficiario() {
		return lstTipoMinoreBeneficiario;
	}

	public void setLstTipoMinoreBeneficiario(List<SelectItem> lstTipoMinoreBeneficiario) {
		this.lstTipoMinoreBeneficiario = lstTipoMinoreBeneficiario;
	}

	public StrutturaDisponibilitaDTO getStrutturaAccettataDaEnte() {
		return strutturaAccettataDaEnte;
	}

	public void setStrutturaAccettataDaEnte(StrutturaDisponibilitaDTO strutturaAccettataDaEnte) {
		this.strutturaAccettataDaEnte = strutturaAccettataDaEnte;
	}

	public Integer getAnnoNascitaMinore() {
		return annoNascitaMinore;
	}

	public void setAnnoNascitaMinore(Integer annoNascitaMinore) {
		this.annoNascitaMinore = annoNascitaMinore;
	}

	public Boolean getFlagCaricaDisStrutture() {
		return flagCaricaDisStrutture;
	}

	public void setFlagCaricaDisStrutture(Boolean flagCaricaDisStrutture) {
		this.flagCaricaDisStrutture = flagCaricaDisStrutture;
	}



	public Date getDataUltimaConsuntivazione() {
		return dataUltimaConsuntivazione;
	}



	public void setDataUltimaConsuntivazione(Date dataUltimaConsuntivazione) {
		this.dataUltimaConsuntivazione = dataUltimaConsuntivazione;
	}



	public List<InserimentoConsuntivazioneDTO> getLstConsuntivazioniErogate() {
		return lstConsuntivazioniErogate;
	}



	public void setLstConsuntivazioniErogate(List<InserimentoConsuntivazioneDTO> lstConsuntivazioniErogate) {
		this.lstConsuntivazioniErogate = lstConsuntivazioniErogate;
	}



	public Long getTipoStruttura() {
		return tipoStruttura;
	}



	public void setTipoStruttura(Long tipoStruttura) {
		this.tipoStruttura = tipoStruttura;
	}



	public List<StrutturaDisponibilitaDTO> getLstStruttureFiltered() {
		return lstStruttureFiltered;
	}



	public void setLstStruttureFiltered(List<StrutturaDisponibilitaDTO> lstStruttureFiltered) {
		this.lstStruttureFiltered = lstStruttureFiltered;
	}

	

}
