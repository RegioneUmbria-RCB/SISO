package it.webred.cs.csa.web.manbean.fascicolo.pai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import it.webred.cs.csa.ejb.client.AccessTablePaiAffidoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSessionBeanRemote;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.client.domini.AccessTableDominiPaiSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.CsPaiAffidoDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.CsPaiAffidoDominioDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.CsPaiAffidoSoggFamigliaDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.CsPaiAffidoSoggettoDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.CsPaiAffidoStatoDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.PaiAffidoDominiEnum;
import it.webred.cs.csa.ejb.dto.pai.affido.PaiAffidoStatoEnum;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

public class PaiAffidoBean extends CsUiCompBaseBean {

	// SERVIZI
	protected AccessTablePaiAffidoSessionBeanRemote paiAffidoService = (AccessTablePaiAffidoSessionBeanRemote) getCarSocialeEjb("AccessTablePaiAffidoSessionBean");
	protected AccessTableSchedaSessionBeanRemote schedaService = (AccessTableSchedaSessionBeanRemote) getCarSocialeEjb("AccessTableSchedaSessionBean");
	protected AccessTableDominiPaiSessionBeanRemote dominiPaiService = (AccessTableDominiPaiSessionBeanRemote) getCarSocialeEjb("AccessTableDominiPaiSessionBean");

	private final Long GENITORE = new Long(44);
	private final Long GENITORE_ACQUISITO = new Long(45);

	// VARIABILI
	private CsPaiAffidoDTO affido;
	private Integer statoAffido;
	private Boolean ricaricaFamAff = false;
	private Boolean ricaricaFamOrig = false;
	// SOGGETTO
	private String cognome;
	private String nome;
	private String codiceRuolo;
	private Date dataStatoAffido;
	private Long idSoggetto;
	private int idSoggettoSelected;//SISO-1017
		
	// SISO-981 - Affido Inizio
	private List<CsPaiAffidoDominioDTO> listaCarattereInserimentoResidenziale;
	private boolean nuovoAffido = false;
	// SISO-981 - Affido Fine

	public PaiAffidoBean() {
		this.affido = null;
		// SISO-981 - Affido Inizio
		this.listaCarattereInserimentoResidenziale = null;
		// SISO-981 - Affido Fine
	}

	public void reset() {
		this.affido = null;
	}

	public void nuovo(Long idSoggetto) {
		this.affido = new CsPaiAffidoDTO(PaiAffidoStatoEnum.VALUTAZIONE_CASO, PaiAffidoDominiEnum.NATURA_ACCOGLIENZA.name() + "_GIUDIZIALE");

		statoAffido = affido.getCodiceStatoAttuale();
		dataStatoAffido = affido.getDataStatoAffidoAttuale();
		setRicaricaFamAff(false);
		setRicaricaFamOrig(false);
		setIdSoggetto(idSoggetto);
		this.nuovoAffido = true;
		
		// SISO-1074 caricare i dati relativi alla famiglia di origine
		// controllo che siano stati inseriti in cartella
		try {
			List<CsAComponente> comps = caricaParenti(idSoggetto, DataModelCostanti.END_DATE);
			for (CsAComponente c : comps) {
				if (c != null
						&& (c.getAffidatario() != null && !c.getAffidatario())
						&& (c.getCsTbTipoRapportoCon().getId().equals(GENITORE) || c.getCsTbTipoRapportoCon().getId().equals(GENITORE_ACQUISITO))) {
                 
					if (affido.getFamigliaOrigine().getCsAComponenteIdMadre() == null && c.getCsAAnagrafica().getSesso().equals("F")) {
						affido.getFamigliaOrigine().setCsAComponenteIdMadre(c.getId());

						CsPaiAffidoSoggFamigliaDTO madre = new CsPaiAffidoSoggFamigliaDTO();
						madre.setCognome(c.getCsAAnagrafica().getCognome());
						madre.setNome(c.getCsAAnagrafica().getNome());
						madre.setCf(c.getCsAAnagrafica().getCf());
						madre.setParentela(c.getCsTbTipoRapportoCon().getDescrizione());
						
						affido.getFamigliaOrigine().setMadre(madre);
					}

					if (affido.getFamigliaOrigine().getCsAComponenteIdPadre() == null && c.getCsAAnagrafica().getSesso().equals("M")) {
						affido.getFamigliaOrigine().setCsAComponenteIdPadre(c.getId());
						
						CsPaiAffidoSoggFamigliaDTO padre = new CsPaiAffidoSoggFamigliaDTO();
						padre.setCognome(c.getCsAAnagrafica().getCognome());
						padre.setNome(c.getCsAAnagrafica().getNome());
						padre.setCf(c.getCsAAnagrafica().getCf());
						padre.setParentela(c.getCsTbTipoRapportoCon().getDescrizione());
						
						affido.getFamigliaOrigine().setPadre(padre);

					}
				}
			}
			
		} catch (Exception e) {
			addError("Errore","Errore nel caricamento dei dati di origine");
		}

		logger.debug("Inizializzato nuovo affido");
	}

	public void findAffidoByPai(Long diarioPaiId, Long idSoggetto) {

		BaseDTO bdto = new BaseDTO();
		fillEnte(bdto);
		bdto.setObj(diarioPaiId);

		try {
			affido = paiAffidoService.findAffidoByDiarioPaiId(bdto);
		
			if (affido == null) {
				nuovo(idSoggetto);
			} else {
			
				statoAffido = affido.getCodiceStatoAttuale();
				dataStatoAffido = affido.getDataStatoAffidoAttuale();
				//SISO-981 - Affido Inizio
				setListaCarattereInserimentoResidenziale(this.affido.getCodiceTipoAffido());
				//SISO-981 - Affido Fine
				setRicaricaFamAff(false);
				setRicaricaFamOrig(false);
				setIdSoggetto(idSoggetto);
				
				if (!affido.getFamigliaOrigine().getGenitoriSconosciuti()
						&& affido.getFamigliaOrigine().getMadre() == null
						&& affido.getFamigliaOrigine().getPadre() == null) {
					addError(
							"Errore",
							"Dati famiglia origine non presenti, Inserirli in Anagrafica RISORSE Cartella Sociale.");

				}
				
				logger.debug("Inizializzato affido da PAI " + diarioPaiId);
				
				
			}
		} catch (Exception e) {
			addError("Inizializzazione Affido", "Affido non inizializzato");
			logger.error("Errore inizializzazione affido", e);
		}
	}

	public String validaAffidoPai(Long idSoggetto, Date dataPaiInizio) {
		
		/*
		 * Non posso procedere al salvataggio se 1) lo stato selezionato è
		 * almeno famiglia individuata e non ci sono i genitori di affido in
		 * cartella sociale
		 */

		// SISO-993
		// Ctr su cambio stato ctr su data inserita
		if (statoAffido >= affido.getCodiceStatoAttuale()) {
			for (CsPaiAffidoStatoDTO stato : affido.getStatiAffido()) {
				if (stato.getDataA() == null
						&& stato.getDataStatoAffido() != null) {

					if (dataStatoAffido != null
							&& dataStatoAffido.before(stato
									.getDataStatoAffido())) {
						return "La data stato affido non può essere antecedente a quella inserita nell'ultimo stato salvato";
					}

					break;
				}

			}
		}
//		// Controllo sulla chiusura dell'affido senza che sia stato avviato
//		if (statoAffido == PaiAffidoStatoEnum.AFFIDO_CHIUSO.getValore()) {
//			// if(stato.getCodice() !=
//			// PaiAffidoStatoEnum.AFFIDO_ATTIVO.getValore()){
//			if (affido.getCodiceStatoAttuale() != PaiAffidoStatoEnum.AFFIDO_ATTIVO
//					.getValore()) {
//				return "L'affido non può essere terminato senza che sia stato avviato";
//			}
//		}

		// Controllo sulla data
		if (dataStatoAffido != null && dataStatoAffido.before(dataPaiInizio)) {
			return "La data stato affido non può essere antecedente a quella del progetto";
		}

		// validazione flag genitori sconosciuti
		if (affido.getFamigliaOrigine().getCsAComponenteIdMadre() != null
				|| affido.getFamigliaOrigine().getCsAComponenteIdPadre() != null) {
			affido.getFamigliaOrigine().setGenitoriSconosciuti(Boolean.FALSE);
		}

		/*
		 * Non posso procedere al salvataggio se 1) affido è non adottabile e
		 * avanzo di stato
		 */
		if (statoAffido > affido.getCodiceStatoAttuale()
				&& ((PaiAffidoDominiEnum.IDONEITA_AFFIDATARI.name() + "_NON_IDONEI_SOGGETTO").equals(affido.getFamigliaAffidataria().getCodiceIdoneita()) 
				|| (PaiAffidoDominiEnum.IDONEITA_AFFIDATARI.name() + "_NON_IDONEA_FAMIGLIA").equals(affido.getFamigliaAffidataria().getCodiceIdoneita()))) {
			return "Non è possibile cambiare di stato se l'affido è non idoneo";
		}
		

		// Verifico se essitono la famiglia di origine e famiglia affidataria
		String verificaFamiglia = verificaEsistenzaFamiglia(idSoggetto);
		if(verificaFamiglia != null){
			return verificaFamiglia;
		}
		//SISO-1172 Controllo su inserimento Tutore Curatore
		String verificaTutelaCuratela = verificaTutelaCuratela(affido.getSoggettiAffido());
		if(verificaTutelaCuratela != null){
			return verificaTutelaCuratela;
		}
		
		return null;		
	}

	public void salva(Long diarioPaiId) {
		affido.setDiarioPaiId(diarioPaiId);
		BaseDTO bdto = new BaseDTO();
		bdto.setObj(affido);
		bdto.setObj2(statoAffido);
		bdto.setObj3(dataStatoAffido);
		fillEnte(bdto);

		try {
			this.paiAffidoService.saveAffido(bdto);
			// reset
			this.affido = null;
			logger.debug("Affido Salvato correttamente");
		} catch (Exception e) {
			logger.error("Errore salvataggio affido", e);
			throw new CarSocialeServiceException("Errore salvataggio dati affido");
		}
	}

	public List<CsPaiAffidoSoggFamigliaDTO> getFamigliaAff() throws Exception {
		List<CsPaiAffidoSoggFamigliaDTO> famiglia = new ArrayList<CsPaiAffidoSoggFamigliaDTO>();
		if (affido.getFamigliaAffidataria().getMadre() != null) {
			if(affido.getFamigliaAffidataria().getCsAComponenteIdMadre() != null){
								
			CsPaiAffidoSoggFamigliaDTO madre = paiAffidoService.findSoggettoByComponente(affido.getFamigliaAffidataria().getCsAComponenteIdMadre());
			affido.getFamigliaAffidataria().setMadre(madre);
			
			}
			famiglia.add(affido.getFamigliaAffidataria().getMadre());
		}
		if (affido.getFamigliaAffidataria().getPadre() != null) {
			
			if(affido.getFamigliaAffidataria().getCsAComponenteIdPadre() != null){
				
				CsPaiAffidoSoggFamigliaDTO padre = paiAffidoService.findSoggettoByComponente(affido.getFamigliaAffidataria().getCsAComponenteIdPadre());
				affido.getFamigliaAffidataria().setPadre(padre);
				
					

				}
			famiglia.add(affido.getFamigliaAffidataria().getPadre());
		}

		return famiglia;
	}

	public List<CsPaiAffidoSoggFamigliaDTO> getFamigliaOrig() {
		List<CsPaiAffidoSoggFamigliaDTO> famiglia = new ArrayList<CsPaiAffidoSoggFamigliaDTO>();
		if (affido.getFamigliaOrigine().getMadre() != null) {
			famiglia.add(affido.getFamigliaOrigine().getMadre());// SISO_906
																	// famiglia.add(affido.getFamigliaAffidataria().getMadre());
		}
		if (affido.getFamigliaOrigine().getPadre() != null) {
			famiglia.add(affido.getFamigliaOrigine().getPadre());
		}

		return famiglia;
	}

	public Boolean isEntitaAffidoRequired() {
		if (statoAffido >= PaiAffidoStatoEnum.AFFIDO_ATTIVO.getValore()) {
			return true;
		}
		return false;
	}

	public Boolean isTipoAccoglienzaRequired() {
		if (statoAffido >= PaiAffidoStatoEnum.AFFIDO_ATTIVO.getValore()) {
			return true;
		}
		return false;
	}

	public Boolean isCollocamentoRequired() {
		if (statoAffido >= PaiAffidoStatoEnum.AFFIDO_ATTIVO.getValore()) {
			return true;
		}
		return false;
	}

	public Boolean isAffidoGiudiziale() {
		if (affido.getCodiceNaturaAccoglienza() != null
				&& affido.getCodiceNaturaAccoglienza().equals(
						PaiAffidoDominiEnum.NATURA_ACCOGLIENZA.name()
								+ "_GIUDIZIALE")) {
			return true;
		}
		return false;
	}

	public Boolean isAffidoParziale() {
		if (affido.getCodiceEntitaAffido() != null
				&& affido.getCodiceEntitaAffido().endsWith("_PARZIALE")) {
			return true;
		}
		return false;
	}

//	public Boolean isEsitoAffidoAltro() {
//		if (affido.getCodiceEsitoAffido() != null
//				&& affido.getCodiceEsitoAffido().equals(
//						PaiAffidoDominiEnum.ESITO_AFFIDO.name() + "_ALTRO")) {
//			return true;
//		}
//		return false;
//	}

	public void cambioStato(ValueChangeEvent event) {
		statoAffido = (Integer) event.getNewValue();
		dataStatoAffido = null;
		// Verifico se essitono la famiglia di origine e famiglia affidataria
		String errore = verificaEsistenzaFamiglia(idSoggetto);
		
		if (errore != null){
			addError("Errore", errore);
			}
	}

	public String verificaEsistenzaFamiglia(Long idSoggetto) {
		String errore = null;

		if (statoAffido >= PaiAffidoStatoEnum.FAMIGLIA_INDIVIDUATA.getValore()
				&& ((affido.getFamigliaAffidataria().getCsAComponenteIdMadre() == null && affido
						.getFamigliaAffidataria().getCsAComponenteIdPadre() == null) || (affido
						.getFamigliaOrigine().getCsAComponenteIdMadre() == null && affido
						.getFamigliaOrigine().getCsAComponenteIdPadre() == null))) {
			
			// controllo che sono stati inseriti in cartella
			List<CsAComponente> comps = caricaParenti(idSoggetto, DataModelCostanti.END_DATE);
			try {
				for (CsAComponente c : comps) {
					// SISO-906 - Controllo fatto su check affidatario e non su
					// grado parentela AFFIDATARIO_NON_PARENTE(EX GENITORE_AFFIDATARIO)
					if (c != null && (c.getAffidatario()!=null &&  c.getAffidatario())) {
						if (affido.getFamigliaAffidataria()
								.getCsAComponenteIdMadre() == null
								&& c.getCsAAnagrafica().getSesso().equals("F")) {
							affido.getFamigliaAffidataria()
									.setCsAComponenteIdMadre(c.getId());
						}

						if (affido.getFamigliaAffidataria()
								.getCsAComponenteIdPadre() == null
								&& c.getCsAAnagrafica().getSesso().equals("M")) {
							affido.getFamigliaAffidataria()
									.setCsAComponenteIdPadre(c.getId());
						}
					}

				}
			} catch (Exception e) {
				errore = "Dati famiglia affidataria non presenti, Inserirli in Anagrafica RISORSE Cartella Sociale.";
			}

			if (statoAffido >= PaiAffidoStatoEnum.AFFIDO_ATTIVO.getValore()) {
				if (affido.getFamigliaAffidataria().getCsAComponenteIdMadre() == null
						&& affido.getFamigliaAffidataria()
								.getCsAComponenteIdPadre() == null) {
					
					errore = "Dati famiglia affidataria non presenti, Inserirli in Anagrafica RISORSE Cartella Sociale.";
				}
			}
		}

		/*
		 * Non posso procedere al salvataggio se 1) il flag genitori sconosciuti
		 * è spento e non ci sono i genitori di origine in cartella sociale
		 */
		if (   !affido.getFamigliaOrigine().getGenitoriSconosciuti()
				&& affido.getFamigliaOrigine().getCsAComponenteIdMadre() == null
				&& affido.getFamigliaOrigine().getCsAComponenteIdPadre() == null) {

			// controllo che sono stati inseriti in cartella
			try {
				List<CsAComponente> comps = caricaParenti(idSoggetto, DataModelCostanti.END_DATE);
				for (CsAComponente c : comps) {
					if (c != null && !c.getAffidatario() && (c.getCsTbTipoRapportoCon().getId().equals(GENITORE) || c.getCsTbTipoRapportoCon().getId().equals(GENITORE_ACQUISITO))) {
						if (affido.getFamigliaOrigine().getCsAComponenteIdMadre() == null && c.getCsAAnagrafica().getSesso().equals("F")) {
							affido.getFamigliaOrigine().setCsAComponenteIdMadre(c.getId());
						}
						

						if (affido.getFamigliaOrigine().getCsAComponenteIdPadre() == null && c.getCsAAnagrafica().getSesso().equals("M")) {
							affido.getFamigliaOrigine().setCsAComponenteIdPadre(c.getId());
						}
					}
				}
			} catch (Exception e) {
				errore = "Dati famiglia origine non presenti, Inserirli in Anagrafica RISORSE Cartella Sociale.";

			}

			if (affido.getFamigliaOrigine().getCsAComponenteIdMadre() == null
					&& affido.getFamigliaOrigine().getCsAComponenteIdPadre() == null) {
				
				errore = "Dati famiglia origine non presenti, Inserirli in Anagrafica RISORSE Cartella Sociale.";

			}

		}
		
		return errore;
	}

	public String verificaTutelaCuratela(List<CsPaiAffidoSoggettoDTO> lstSoggettiAffdio) {
		String esitoVerificaTutelaCuratela = null;
		Boolean esisteCuratore=false;
		Boolean esisteTutore = false;
		
		for(CsPaiAffidoSoggettoDTO lstSoggettiAffido : affido.getSoggettiAffido()){
			if (lstSoggettiAffido.getCodiceRuolo().contains("CURATORE"))
				esisteCuratore = lstSoggettiAffido.getCodiceRuolo().contains("CURATORE");
			if (lstSoggettiAffido.getCodiceRuolo().contains("TUTORE"))
				esisteTutore= lstSoggettiAffido.getCodiceRuolo().contains("TUTORE");
		}
		
		if (affido.getPresenzaTutore() && !esisteTutore)
			esitoVerificaTutelaCuratela = "Inserire almeno un Tutore" ;
		else if(!affido.getPresenzaTutore() && esisteTutore){
			esitoVerificaTutelaCuratela = "Esiste un Tutore: selezionare SI in Presenza Tutore" ;
		}
		
		if (affido.getPresenzaCuratore() && !esisteCuratore)
			esitoVerificaTutelaCuratela = "Inserire almeno un Curatore" ;
		else if(!affido.getPresenzaCuratore() && esisteCuratore){
			esitoVerificaTutelaCuratela = "Esiste un Curatore: selezionare SI in Presenza Curatore" ;
		}

		return esitoVerificaTutelaCuratela;
	}
	
	public Boolean isStato(Integer value) {
		if (statoAffido != null && statoAffido.compareTo(value) >= 0) {
			return true;
		}
		return false;
	}

	// ISSUE-993 il menu a tendina Esito Affido deve essere visibile per lo
	// Stato Affido CHiuso e non per AnnullamentoPrgetto Affido
//	public Boolean isEsitoVisibile() {
//		if (this.isStato(PaiAffidoStatoEnum.AFFIDO_CHIUSO.getValore()) && statoAffido != PaiAffidoStatoEnum.ANNULLAMENTO_PROGETTO_AFFIDO.getValore()) {
//			return true;
//		}
//		return false;
//
//	}

	public Boolean mostraFamigliaAffBase() {
		if (affido.getFamigliaAffidataria().getCsAComponenteIdMadre() == null
				&& affido.getFamigliaAffidataria().getCsAComponenteIdPadre() == null) {
			return true;
		}
		return false;
	}

	public void aggiungiSoggetto() {
		CsPaiAffidoSoggettoDTO soggetto = new CsPaiAffidoSoggettoDTO();
		soggetto.setCognome(cognome);
		soggetto.setNome(nome);
		soggetto.setCodiceRuolo(codiceRuolo);

		BaseDTO bdto = new BaseDTO();
		bdto.setObj(PaiAffidoDominiEnum.RUOLO_SOGGETTO.name());
		bdto.setObj2(codiceRuolo);
		fillEnte(bdto);
		soggetto.setDescrizioneRuolo(dominiPaiService.findDescrizioneAffidoByDominio(bdto));

		affido.getSoggettiAffido().add(soggetto);

		cognome = null;
		nome = null;
		codiceRuolo = null;
	}
	//SISO-1017
	public void eliminaSoggetto() {
		
        CsPaiAffidoSoggettoDTO soggettoDaEliminare = new CsPaiAffidoSoggettoDTO();
        soggettoDaEliminare = affido.getSoggettiAffido().get(idSoggettoSelected);
        
		affido.getSoggettiAffido().remove(soggettoDaEliminare);
		
	}
	
	public Boolean isStatoDisabilitato(Integer valore) {
		return affido!=null && (affido.getCodiceStatoAttuale() - valore) > 0;
	}

	public Boolean isAffidoAccoglienza() {
		return (PaiAffidoDominiEnum.TIPO_AFFIDO.name() + "_ACCOGLIENZA")
				.equalsIgnoreCase(affido.getCodiceTipoAffido());
	}

	public List<PaiAffidoStatoEnum> getListaStati() {
		return Arrays.asList(PaiAffidoStatoEnum.values());
	}

	public List<CsPaiAffidoDominioDTO> getListaNaturaAccoglienza() {
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.NATURA_ACCOGLIENZA.name());
		fillEnte(b);
		return dominiPaiService.findByDominio(b);
	}

	public List<CsPaiAffidoDominioDTO> getListaTipoAccoglienza() {
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.TIPO_ACCOGLIENZA.name());
		fillEnte(b);
		return dominiPaiService.findByDominio(b);
	}

	public List<CsPaiAffidoDominioDTO> getListaCollocamento() {
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.COLLOCAMENTO.name());
		fillEnte(b);
		return dominiPaiService.findByDominio(b);
	}

	public List<CsPaiAffidoDominioDTO> getListaEntitaAffido() {
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.ENTITA_AFFIDO.name());
		fillEnte(b);
		return dominiPaiService.findByDominio(b);
	}
	//SISO-1172
//	public List<CsPaiAffidoDominioDTO> getListaSituazioniParticolari() {
//		BaseDTO b = new BaseDTO();
//		b.setObj(PaiAffidoDominiEnum.SITUAZIONI_PARTICOLARI.name());
//		fillEnte(b);
//		return dominiPaiService.findByDominio(b);
//	}

	public List<CsPaiAffidoDominioDTO> getListaFormeAffidamentoReport() {
		List<CsPaiAffidoDominioDTO> lstFormeAffidamento =new ArrayList<CsPaiAffidoDominioDTO>();
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.FORME_AFFIDAMENTO_CONSOLIDATE.name());
		fillEnte(b);
		lstFormeAffidamento = dominiPaiService.findByDominio(b);
		
		b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.FORME_AFFIDAMENTO_ALTRE.name());
		fillEnte(b);
		
		lstFormeAffidamento.addAll(dominiPaiService.findByDominio(b));
		return lstFormeAffidamento;
	}
	
	public List<SelectItem> getListaFormeAffidamento() {
		List<SelectItem> formeAffidamento = new ArrayList<SelectItem>();
		List<CsPaiAffidoDominioDTO> lstFormeAffidamento =new ArrayList<CsPaiAffidoDominioDTO>();
		
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.FORME_AFFIDAMENTO_CONSOLIDATE.name());
		fillEnte(b);
		lstFormeAffidamento = dominiPaiService.findByDominio(b);
		
		SelectItemGroup parGroup = new SelectItemGroup("Forme Consolidate");
		List<SelectItem> lst = new ArrayList<SelectItem>();
		for (CsPaiAffidoDominioDTO u : lstFormeAffidamento)
			lst.add(new SelectItem(u.getCodice(), u.getDescrizione()));
		parGroup.setSelectItems(lst.toArray(new SelectItem[lst.size()]));
		formeAffidamento.add(parGroup);
		
		lstFormeAffidamento =new ArrayList<CsPaiAffidoDominioDTO>();
		b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.FORME_AFFIDAMENTO_ALTRE.name());
		fillEnte(b);
		lstFormeAffidamento = dominiPaiService.findByDominio(b);
		
		parGroup = new SelectItemGroup("Altre Forme");
		lst = new ArrayList<SelectItem>();
		for (CsPaiAffidoDominioDTO u : lstFormeAffidamento)
			lst.add(new SelectItem(u.getCodice(), u.getDescrizione()));
		parGroup.setSelectItems(lst.toArray(new SelectItem[lst.size()]));
		formeAffidamento.add(parGroup);
		
		return formeAffidamento;
	}
	//FINE SISO_1172
	
	public List<CsPaiAffidoDominioDTO> getListaBancaFamiglie() {
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.BANCA_DATI_FAMIGLIE.name());
		fillEnte(b);
		return dominiPaiService.findByDominio(b);
	}

	public List<CsPaiAffidoDominioDTO> getListaIdoneita() {
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.IDONEITA_AFFIDATARI.name());
		fillEnte(b);
		return dominiPaiService.findByDominio(b);
	}

	public List<CsPaiAffidoDominioDTO> getListaFrequenzaContatti() {
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.FREQUENZA_CONTATTI.name());
		fillEnte(b);
		return dominiPaiService.findByDominio(b);
	}

	public List<CsPaiAffidoDominioDTO> getListaAffidatari() {
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.AFFIDATARI.name());
		fillEnte(b);
		return dominiPaiService.findByDominio(b);
	}

	// SISO-981 Inizio
	public List<CsPaiAffidoDominioDTO> getListaAutoritaProvvedimento() {
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.AUTORITA_PROVVEDIMENTO.name());
		fillEnte(b);
		return dominiPaiService.findByDominio(b);
	}

	private List<CsPaiAffidoDominioDTO> getListaInserimentoResidenziale() {
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.CARATTERE_INSERIMENTO_RESIDENZIALE.name());
		fillEnte(b);
		return dominiPaiService.findByDominio(b);

	}

	public List<CsPaiAffidoDominioDTO> getListaCarattereInserimentoResidenziale() {
		if(listaCarattereInserimentoResidenziale == null && this.affido!=null) //condizione iniziale di nuovo progetto
				setListaCarattereInserimentoResidenziale(this.affido.getCodiceTipoAffido());
		return listaCarattereInserimentoResidenziale;
	}

	public void setListaCarattereInserimentoResidenziale(
			List<CsPaiAffidoDominioDTO> listaCarattereInserimentoResidenziale) {
		this.listaCarattereInserimentoResidenziale = listaCarattereInserimentoResidenziale;
	}

	public void setListaCarattereInserimentoResidenziale(String eventoTipoAffido){
		
		String filtro = "";
		listaCarattereInserimentoResidenziale = new ArrayList<CsPaiAffidoDominioDTO>();
		List<CsPaiAffidoDominioDTO> lCsPaiAffidoDominio = this
				.getListaInserimentoResidenziale();
		if(eventoTipoAffido.equals("")){
			listaCarattereInserimentoResidenziale.clear();
		}
		else if (eventoTipoAffido.toUpperCase().contains("FAMILIARE"))
			filtro = "Familiare";
		else
			filtro = "Comunitario";
		for (CsPaiAffidoDominioDTO dominio : lCsPaiAffidoDominio) {
			if (dominio.getDescrizione().toUpperCase()
					.contains(filtro.toUpperCase())) {
				listaCarattereInserimentoResidenziale.add(dominio);
			}
		}
	}
	
	public void onChangeTipoAffido(javax.faces.event.AjaxBehaviorEvent event) {

		String eventoTipoAffido = ((String) ((javax.faces.component.UIInput) event
				.getComponent()).getValue());
		 
		setListaCarattereInserimentoResidenziale(  eventoTipoAffido);

	}

	// SISO-981 Fine

	public List<CsPaiAffidoDominioDTO> getListaConvivenzaOrigAff() {
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.CONVIVENZA_ORIG_AFF.name());
		fillEnte(b);
		return dominiPaiService.findByDominio(b);
	}

//	public List<CsPaiAffidoDominioDTO> getListaEsitoAffido() {
//		BaseDTO b = new BaseDTO();
//		b.setObj(PaiAffidoDominiEnum.ESITO_AFFIDO.name());
//		fillEnte(b);
//		//SISO-981 Affido Inizio
//		List<CsPaiAffidoDominioDTO> listaPaiAffido =  dominiPaiService.findByDominio(b);
//		if(!nuovoAffido)
//			return listaPaiAffido;
//		else{
//			List<CsPaiAffidoDominioDTO> listaPaiAffidoMod =  new ArrayList<CsPaiAffidoDominioDTO>();
//			for(CsPaiAffidoDominioDTO csPai : listaPaiAffido){
//				if(!csPai.getCodice().equals("ESITO_AFFIDO_SISTEMAZIONE_SERVIZIO_RESIDENZIALE")){
//					listaPaiAffidoMod.add(csPai);
//				}
//			}
//			return listaPaiAffidoMod;
//		}
			
		//SISO-981 Affido Fine
//	}

	public List<CsPaiAffidoDominioDTO> getListaRuoliSoggetto() {
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.RUOLO_SOGGETTO.name());
		fillEnte(b);
		List<CsPaiAffidoDominioDTO> listaRuoliAppoggio =  new ArrayList<CsPaiAffidoDominioDTO>();
		listaRuoliAppoggio = dominiPaiService.findByDominio(b);
		return listaRuoliAppoggio;
	}

	public List<CsPaiAffidoDominioDTO> getListaTipoAffido() {
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.TIPO_AFFIDO.name());
		fillEnte(b);
		return dominiPaiService.findByDominio(b);
	}

	public List<CsPaiAffidoDominioDTO> getListaAdottabile() {
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.ADOTTABILE.name());
		fillEnte(b);
		return dominiPaiService.findByDominio(b);
	}

	public List<CsPaiAffidoDominioDTO> getListaOrigineInterventi() {
		BaseDTO b = new BaseDTO();
		b.setObj(PaiAffidoDominiEnum.FAM_ORIG_INTERVENTI.name());
		fillEnte(b);
		return dominiPaiService.findByDominio(b);
	}

	public CsPaiAffidoDTO getAffido() {
		return affido;
	}

	public void setAffido(CsPaiAffidoDTO affido) {
		this.affido = affido;
	}

	public AccessTablePaiAffidoSessionBeanRemote getPaiAffidoService() {
		return paiAffidoService;
	}

	public void setPaiAffidoService(
			AccessTablePaiAffidoSessionBeanRemote paiAffidoService) {
		this.paiAffidoService = paiAffidoService;
	}

	public Integer getStatoAffido() {
		return statoAffido;
	}

	public void setStatoAffido(Integer statoAffido) {
		this.statoAffido = statoAffido;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodiceRuolo() {
		return codiceRuolo;
	}

	public void setCodiceRuolo(String codiceRuolo) {
		this.codiceRuolo = codiceRuolo;
	}

	public Boolean getRicaricaFamOrig() {
		return ricaricaFamOrig;
	}

	public void setRicaricaFamOrig(Boolean ricaricaFamOrig) {
		this.ricaricaFamOrig = ricaricaFamOrig;
	}

	public Boolean getRicaricaFamAff() {
		return ricaricaFamAff;
	}

	public void setRicaricaFamAff(Boolean ricaricaFamAff) {
		this.ricaricaFamAff = ricaricaFamAff;
	}

	public Date getDataStatoAffido() {
		return dataStatoAffido;
	}

	public void setDataStatoAffido(Date dataStatoAffido) {
		this.dataStatoAffido = dataStatoAffido;
	}

	public Long getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public int getIdSoggettoSelected() {
		return idSoggettoSelected;
	}

	public void setIdSoggettoSelected(int idSoggettoSelected) {
		this.idSoggettoSelected = idSoggettoSelected;
	}

	
    
}
