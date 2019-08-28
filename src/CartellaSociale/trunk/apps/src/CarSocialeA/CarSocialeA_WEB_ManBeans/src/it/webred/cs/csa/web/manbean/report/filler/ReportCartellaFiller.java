package it.webred.cs.csa.web.manbean.report.filler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.RelazioneDTO;
import it.webred.cs.csa.ejb.dto.TriageItemDTO;
import it.webred.cs.csa.utils.bean.report.dto.StoricoPdfDTO;
import it.webred.cs.csa.utils.bean.report.dto.TriagePdfDTO;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloBean;
import it.webred.cs.csa.web.manbean.fascicolo.relazioni.TriageBean;
import it.webred.cs.csa.web.manbean.report.dto.cartella.AnagraficaPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.cartella.DatiSocialiAbitazionePdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.cartella.DatiSocialiPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.cartella.DatiSocialiStranieriPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.cartella.DiarioPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.cartella.DisabilitaPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.cartella.InvaliditaPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.cartella.NotePdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.cartella.OperatorePdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.cartella.ParentePdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.cartella.RisorsePdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.cartella.TribunalePdfDTO;
import it.webred.cs.csa.web.manbean.scheda.SchedaBean;
import it.webred.cs.csa.web.manbean.scheda.anagrafica.AnagraficaBean;
import it.webred.cs.csa.web.manbean.scheda.disabilita.DatiDisabilitaComp;
import it.webred.cs.csa.web.manbean.scheda.invalidita.DatiInvaliditaComp;
import it.webred.cs.csa.web.manbean.scheda.operatori.OperatoriComp;
import it.webred.cs.csa.web.manbean.scheda.parenti.ParentiComp;
import it.webred.cs.csa.web.manbean.scheda.sociali.DatiSocialiComp;
import it.webred.cs.csa.web.manbean.scheda.tribunale.DatiTribunaleComp;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsACasoOpeTipoOpe;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsASoggettoCategoriaSoc;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsDColloquio;
import it.webred.cs.data.model.CsDIsee;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsTbDisabTipologia;
import it.webred.cs.data.model.CsTbGVulnerabile;
import it.webred.cs.jsf.bean.ValiditaCompBaseBean;
import it.webred.cs.jsf.bean.colloquio.ColloquioRowBean;
import it.webred.cs.jsf.bean.colloquio.DatiColloquioBean;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.json.abitazione.ver1.AbitazioneBean;
import it.webred.cs.json.abitazione.ver1.AbitazioneManBean;
import it.webred.cs.json.dto.KeyValueDTO;
import it.webred.cs.json.familiariConviventi.DatiSocialiFamiliariConviventiPdfDTO;
import it.webred.cs.json.familiariConviventi.ver1.FamConviventiBean;
import it.webred.cs.json.familiariConviventi.ver1.FamConviventiManBean;
import it.webred.cs.json.stranieri.ver1.StranieriBean;
import it.webred.cs.json.stranieri.ver1.StranieriManBean;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.jsf.bean.ComuneBean;
import it.webred.jsf.bean.SessoBean;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ReportCartellaFiller extends CsUiCompBaseBean {
	private AccessTableCasoSessionBeanRemote casoService = (AccessTableCasoSessionBeanRemote) getEjb("CarSocialeA",
			"CarSocialeA_EJB", "AccessTableCasoSessionBean");
	private AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) getEjb(
			"CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");
	private AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getEjb(
			"CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private AccessTableDiarioSessionBeanRemote diario = (AccessTableDiarioSessionBeanRemote) getCarSocialeEjb("AccessTableDiarioSessionBean");

	private CsASoggettoLAZY soggetto;
	private SchedaBean schedaBean;
	private FascicoloBean fascicoloBean;
	private boolean esportaStorico;



	public AnagraficaPdfDTO fillAnagrafica() {

		AnagraficaPdfDTO ana = new AnagraficaPdfDTO();
		AnagraficaBean anaBean = schedaBean.getAnagraficaBean();
		ana.setCognome(anaBean.getDatiAnaBean().getCognome());
		ana.setNome(anaBean.getDatiAnaBean().getNome());
		if (anaBean.getDatiAnaBean().getDataNascita() != null)
			ana.setDataNascita(sdf.format(anaBean.getDatiAnaBean().getDataNascita()));
		if (anaBean.getComuneNazioneNascitaMan().getComuneNascitaMan().getComune() != null) {
			ComuneBean comune = anaBean.getComuneNazioneNascitaMan().getComuneNascitaMan().getComune();
			ana.setLuogoNascita(comune.getDenominazione() + " (" + comune.getSiglaProv() + ")");
		} else if (anaBean.getComuneNazioneNascitaMan().getNazioneNascitaMan().getNazione() != null) {
			AmTabNazioni nazione = anaBean.getComuneNazioneNascitaMan().getNazioneNascitaMan().getNazione();
			ana.setLuogoNascita(nazione.getNazione());
		}

		SessoBean sb = anaBean.getDatiAnaBean().getDatiSesso();
		ana.setSesso(sb.getSesso());

		ana.setCodFiscale(anaBean.getDatiAnaBean().getCodiceFiscale());

		String cittadinanza = anaBean.getDatiAnaBean().getCittadinanza();
		if (anaBean.getDatiAnaBean().getCittadinanzaAcq() != null && anaBean.getDatiAnaBean().getCittadinanzaAcq() > 0) {
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(anaBean.getDatiAnaBean().getCittadinanzaAcq());
			cittadinanza += " (" + confService.getCittadinanzaAcqById(dto).getDescrizione() + ")";
		}

		cittadinanza += anaBean.getDatiAnaBean().getCittadinanza2() != null
				? ", " + anaBean.getDatiAnaBean().getCittadinanza2() : "";
		ana.setCittadinanza(cittadinanza);

		if (anaBean.getResidenzaCsaMan().getIndirizzoResidenzaAttivo() != null) {
			String res = anaBean.getResidenzaCsaMan().getIndirizzoResidenzaAttivo().getCsAAnaIndirizzo().getLabelIndirizzoCompleto();
			/*res += ", " + anaBean.getResidenzaCsaMan().getCivicoFromNumeroAltro(
					anaBean.getResidenzaCsaMan().getIndirizzoResidenzaAttivo().getCsAAnaIndirizzo().getCivicoNumero(),
					anaBean.getResidenzaCsaMan().getIndirizzoResidenzaAttivo().getCsAAnaIndirizzo().getCivicoAltro());
			res += " - " + anaBean.getResidenzaCsaMan().getIndirizzoResidenzaAttivo().getCsAAnaIndirizzo().getComDes();
			res += " (" + anaBean.getResidenzaCsaMan().getIndirizzoResidenzaAttivo().getCsAAnaIndirizzo().getStatoDes()
					+ ")";*/
			ana.setResidenza(res);
		}
		if (!anaBean.getStatoCivileGestBean().getLstComponentsActive().isEmpty())
			ana.setStatoCivile(anaBean.getStatoCivileGestBean().getLstComponentsActive().get(0).getDescrizione());
		if (!anaBean.getStatusGestBean().getLstComponentsActive().isEmpty())
			ana.setStatus(anaBean.getStatusGestBean().getLstComponentsActive().get(0).getDescrizione());
		if (!anaBean.getMediciGestBean().getLstComponentsActive().isEmpty())
			ana.setMedico(anaBean.getMediciGestBean().getLstComponentsActive().get(0).getDescrizione());

		String tel = anaBean.getDatiAnaBean().getTelefono();
		tel += anaBean.getDatiAnaBean().getTitTelefono() != null ? " (" + anaBean.getDatiAnaBean().getTitTelefono() + ")"
				: "";
		ana.setTelefono(tel);

		String cel = anaBean.getDatiAnaBean().getCellulare();
		cel += anaBean.getDatiAnaBean().getTitCellulare() != null
				? " (" + anaBean.getDatiAnaBean().getTitCellulare() + ")" : "";
		ana.setCellulare(cel);

		String email = anaBean.getDatiAnaBean().getEmail();
		email += anaBean.getDatiAnaBean().getTitEmail() != null ? " (" + anaBean.getDatiAnaBean().getTitEmail() + ")"
				: "";
		ana.setEmail(email);

		try {
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);

			// cat sociale
			dto.setObj(soggetto.getAnagraficaId());
			List<CsASoggettoCategoriaSoc> listaCatSociali = soggettoService.getSoggettoCategorieBySoggetto(dto);
			String catSociali = "";
			for (CsASoggettoCategoriaSoc soggCatSoc : listaCatSociali) {
				catSociali += ", " + soggCatSoc.getCsCCategoriaSociale().getTooltip();
			}
			ana.setCatSociale(catSociali.substring(2));

			// responsabile
			dto.setObj(soggetto.getCsACaso().getId());
			CsACasoOpeTipoOpe casoOpTipoOp = casoService.findCasoOpeResponsabile(dto);
			if (casoOpTipoOp != null) {
				CsOOperatore op = casoOpTipoOp.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore().getCsOOperatore();
				ana.setAssSociale(this.getDenominazioneOperatore(op));

				Date dataPIC;
				if (casoOpTipoOp.getDtMod() != null)
					dataPIC = casoOpTipoOp.getDtMod();
				else
					dataPIC = casoOpTipoOp.getDtIns();
				ana.setDataPIC(sdf.format(dataPIC));
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return ana;

	}



	public List<RisorsePdfDTO> fillRisorse(String subPath) {

		List<RisorsePdfDTO> lista = new ArrayList<RisorsePdfDTO>();
		if (schedaBean.getParentiBean().getListaComponenti() != null
				&& !schedaBean.getParentiBean().getListaComponenti().isEmpty()) {
			List<ValiditaCompBaseBean> lstIn = schedaBean.getParentiBean().getListaComponenti();
			for (int i = 0; i < lstIn.size(); i++) {
				ParentiComp parComp = (ParentiComp) lstIn.get(i);

				if (!this.esportaStorico && i > 0)
					break;

				RisorsePdfDTO ds = new RisorsePdfDTO();
				valorizzaDateValidita(parComp, ds);
				ds.setParenti(new JRBeanCollectionDataSource(fillParenti(parComp)));
				ds.setSUBREPORT_DIR(subPath);
				lista.add(ds);
			}
		}
		return lista;
	}



	private List<ParentePdfDTO> fillParenti(ParentiComp parentiComp) {

		List<ParentePdfDTO> lista = new ArrayList<ParentePdfDTO>();

		List<CsAComponente> listaParenti = new ArrayList<CsAComponente>();
		if (parentiComp.getLstParenti() != null)
			listaParenti.addAll(parentiComp.getLstParenti());
		if (parentiComp.getLstConoscenti() != null)
			listaParenti.addAll(parentiComp.getLstConoscenti());

		for (CsAComponente csPar : listaParenti)
			lista.add(fillParente(csPar));

		return lista;
	}



	private ParentePdfDTO fillParente(CsAComponente csPar) {
		ParentePdfDTO par = new ParentePdfDTO();
		par.setCognome(csPar.getCsAAnagrafica().getCognome());
		par.setNome(csPar.getCsAAnagrafica().getNome());
		if (csPar.getCsAAnagrafica().getDataNascita() != null)
			par.setDataNascita(sdf.format(csPar.getCsAAnagrafica().getDataNascita()));
		if (csPar.getCsAAnagrafica().getComuneNascitaDes() != null)
			par.setLuogoNascita(csPar.getCsAAnagrafica().getComuneNascitaDes() + " ("
					+ csPar.getCsAAnagrafica().getProvNascitaCod() + ")");
		else
			par.setLuogoNascita(csPar.getCsAAnagrafica().getStatoNascitaDes());
		par.setCodFiscale(csPar.getCsAAnagrafica().getCf());
		par.setCittadinanza(csPar.getCsAAnagrafica().getCittadinanza());
		par.setSesso(csPar.getCsAAnagrafica().getSesso());
		String residenza = csPar.getIndirizzoRes()!=null ? csPar.getIndirizzoRes() + ", " +(csPar.getNumCivRes()!=null ? csPar.getNumCivRes() : "") : "";
		if (csPar.getComResDes() != null)
			residenza += " - " + csPar.getComResDes() + " (" + csPar.getProvResCod() + ")";
		else
			residenza += " - " + csPar.getComAltroDes();
		par.setResidenza(residenza);
		par.setTelefono(csPar.getCsAAnagrafica().getTel());
		par.setCellulare(csPar.getCsAAnagrafica().getCell());
		par.setEmail(csPar.getCsAAnagrafica().getEmail());
		par.setParentela(csPar.getCsTbTipoRapportoCon().getDescrizione());
		if (csPar.getCsAAnagrafica().getDataDecesso() != null)
			par.setDecesso("SI (" + sdf.format(csPar.getCsAAnagrafica().getDataDecesso()) + ")");
		else
			par.setDecesso("NO");

		return par;
	}



	private void valorizzaDateValidita(ValiditaCompBaseBean comp, StoricoPdfDTO ds) {
		ds.setDataInizio(comp != null ? ddMMyyyy.format(comp.getDataInizio()) : null);
		String dtFine = comp.getDataFine() != null ? ddMMyyyy.format(comp.getDataFine()) : "-";
		ds.setDataFine("31/12/9999".equals(dtFine) ? "-" : dtFine);
	}



	public List<DatiSocialiPdfDTO> fillDatiSociali(String subPath) {

		List<DatiSocialiPdfDTO> list = new ArrayList<DatiSocialiPdfDTO>();

		if (schedaBean.getDatiSocialiBean().getListaComponenti() != null
				&& !schedaBean.getDatiSocialiBean().getListaComponenti().isEmpty()) {

			BaseDTO dto = new BaseDTO();
			fillEnte(dto);

			List<ValiditaCompBaseBean> lstIn = schedaBean.getDatiSocialiBean().getListaComponenti();
			for (int i = 0; i < lstIn.size(); i++) {
				DatiSocialiComp dsComp = (DatiSocialiComp) lstIn.get(i);

				if (!this.esportaStorico && i > 0)
					break;

				DatiSocialiPdfDTO ds = new DatiSocialiPdfDTO();
				ds.setSUBREPORT_DIR(subPath);
				valorizzaDateValidita(dsComp, ds);

				if (dsComp.getIdProblematica() != null && dsComp.getIdProblematica().intValue() != 0) {
					dto.setObj(dsComp.getIdProblematica().longValue());
					ds.setProblematica(confService.getProblematicaById(dto).getDescrizione());
				}
				if (dsComp.getIdProblematicaNucleo() != null && dsComp.getIdProblematicaNucleo().intValue() != 0) {
					dto.setObj(dsComp.getIdProblematicaNucleo().longValue());
					ds.setProblematicaNucleo(confService.getMotivoSegnalazioneById(dto).getDescrizione());
				}
				if (dsComp.getFormLavoroMan().getIdTitoloStudio() != null && dsComp.getFormLavoroMan().getIdTitoloStudio().intValue() != 0) {
					dto.setObj(dsComp.getFormLavoroMan().getIdTitoloStudio().longValue());
					ds.setTitoloStudio(confService.getTitoloStudioById(dto).getDescrizione());
				}

				// SISO-437
				AbitazioneBean abitazioneBean = ((AbitazioneManBean) dsComp.getAbitazioneMan()).getJsonCurrent();

				DatiSocialiAbitazionePdfDTO abitazioneDTO = new DatiSocialiAbitazionePdfDTO();
				abitazioneDTO.setTipologiaAbitazione(abitazioneBean.getTipoAbitazione());
				abitazioneDTO.setNote(abitazioneBean.getNote());
				abitazioneDTO.setNumeroVani(abitazioneBean.getNumVani());
				abitazioneDTO.setProprietarioOGestore(abitazioneBean.getProprietarioGestore());
				abitazioneDTO.setTipologiaAbitazione(abitazioneBean.getTipoAbitazione());
				abitazioneDTO.setTitoloDiGodimento(abitazioneBean.getTitoloGodimento());
				ds.setDatiSocialiAbitazionePdfDTO(abitazioneDTO);
				
				DatiSocialiFamiliariConviventiPdfDTO famPdfDTO = new DatiSocialiFamiliariConviventiPdfDTO();
				dsComp.getFamConviventiMan().fillReport(famPdfDTO);
				ds.setDatiSocialiFamiliariConviventiPdfDTO(famPdfDTO);
				
				if(dsComp.isStranieriRequired() && dsComp.isAbilitaInfoStranieri()){
					StranieriBean stranieriBean = ((StranieriManBean) dsComp.getStranieriMan()).getJsonCurrent();
	
					DatiSocialiStranieriPdfDTO dsStra = new DatiSocialiStranieriPdfDTO();
					dsStra.setPaeseOrigineNucleoFamiliare(stranieriBean.getNazioneOrigine().getDescrizione());
					dsStra.setMinoreStranieroNonAccompagnato(stranieriBean.getMinoreNonAccompagnato());
					dsStra.setBambinoInEtaNonScolastica(stranieriBean.isEtaNonScolastica());
					dsStra.setAttestatoConoscenzaLinguaItaliana(stranieriBean.isLinguaItaAttestato());
					if (stranieriBean.isLinguaItaAttestato()) {
						// attestato
						dsStra.setAttestatoItaLivello(stranieriBean.getLiguaItaLivello());
						dsStra.setAttestatoItaIstituto(stranieriBean.getIstitutoRilascio());
						dsStra.setAttestatoItaComune(stranieriBean.getComuneRilascio().getDenominazione());
					} else {
						// autovalutazione
						dsStra.setAutovalutazioneComprensione(stranieriBean.getLinguaItaComprensione());
						dsStra.setAutovalutazioneParlato( stranieriBean.getLinguaItaParlato());
						dsStra.setAutovalutazioneLettura( stranieriBean.getLinguaItaLettura());
						dsStra.setAutovalutazioneScrittura( stranieriBean.getLinguaItaScrittura());
					}
					dsStra.setAltreLingueConosciute(stranieriBean.getAltreLingue());
	
					dsStra.setCondizioneGiuridicaStatus(stranieriBean.getStatus().getDescrizione());
					dsStra.setCondizioneGiuridicaPresenzaOltre3Mesi(stranieriBean.isPresenteDaOltre3Mesi());
					if (stranieriBean.getStatoPermessoSogg() != null) {
						dsStra.setCondizioneGiuridicaPermessoStato(stranieriBean.getStatoPermessoSogg());
					}
					if (stranieriBean.getPermesso().getDescrizione() != null) {
						dsStra.setCondizioneGiuridicaPermessoTipo(stranieriBean.getPermesso().getDescrizione());
					}
					if (stranieriBean.getIdPraticaPermesso() != null) {
						dsStra.setCondizioneGiuridicaPermessoId(stranieriBean.getIdPraticaPermesso());
					}
					if (stranieriBean.getScadPermessoSogg() != null) {
						dsStra.setCondizioneGiuridicaPermessoScadenza(sdf.format(stranieriBean.getScadPermessoSogg()));
					}
	
					dsStra.setArrivoInItaliaStatus(stranieriBean.getArrivoItalia());
					dsStra.setArrivoInItaliaAnnoPrimoArrivo(stranieriBean.getAnnoPrimoArrivoITA());
					dsStra.setDaSolo(stranieriBean.isDaSoloPrimoArrivoITA());
					dsStra.setArrivoInItaliaAnnoPrimoPermesso(stranieriBean.getAnnoPrimoPermSogg());
					if (stranieriBean.getComuneValicoFrontiera() != null) {
						dsStra.setArrivoInItaliaValicoDiFrontiera(stranieriBean.getComuneValicoFrontiera().getDenominazione());
					}
					if (stranieriBean.getUltimaNazioneProvenienza() != null
							&& stranieriBean.getUltimaNazioneProvenienza().getDescrizione() != null) {
						dsStra.setArrivoInItaliaUltimoPaeseProvenienza(
								stranieriBean.getUltimaNazioneProvenienza().getDescrizione());
					}
					dsStra.setArrivoInRegioneStatus(stranieriBean.getArrivoRegione());
					dsStra.setArrivoInRegioneAnnoPrimoArrivo(stranieriBean.getAnnoPrimoArrivoREG());
					dsStra.setProtezioneInternazionale(stranieriBean.isProtezioneInternazionale());
					if (stranieriBean.getComuneUltimoArrivoREG() != null) {
						dsStra.setArrivoInRegioneComuneTitolareSprar(
								stranieriBean.getComuneUltimoArrivoREG().getDenominazione());
					}
					if (stranieriBean.getNomeStruttAccoglienza() != null) {
						dsStra.setArrivoInRegioneStrutturaAccoglienza(stranieriBean.getNomeStruttAccoglienza());
					}
					if (stranieriBean.getIndirizzoStruttAccoglienza() != null) {
						dsStra.setArrivoInRegioneStrutturaAccoglienzaIndirizzo(stranieriBean.getIndirizzoStruttAccoglienza());
					}
					List<DatiSocialiStranieriPdfDTO> lst = new ArrayList<DatiSocialiStranieriPdfDTO>();
					lst.add(dsStra);
					ds.setStranieri(new JRBeanCollectionDataSource(lst));
				}
				// ~

				list.add(ds);
			}
		}
		return list;

	}



	public List<InvaliditaPdfDTO> fillInvalidita() {

		List<InvaliditaPdfDTO> list = new ArrayList<InvaliditaPdfDTO>();

		InvaliditaPdfDTO inv = new InvaliditaPdfDTO();
		if (schedaBean.getDatiInvaliditaBean().getListaComponenti() != null
				&& !schedaBean.getDatiInvaliditaBean().getListaComponenti().isEmpty()) {

			List<ValiditaCompBaseBean> lstIn = schedaBean.getDatiInvaliditaBean().getListaComponenti();
			for (int i = 0; i < lstIn.size(); i++) {
				DatiInvaliditaComp invComp = (DatiInvaliditaComp) lstIn.get(i);

				if (!this.esportaStorico && i > 0)
					break;

				InvaliditaPdfDTO pdf = new InvaliditaPdfDTO();
				valorizzaDateValidita(invComp, pdf);

				inv.setInvInCorso(fillBoolean(invComp.isInvaliditaInCorso()));

				if (invComp.getDataInvalidita() != null)
					pdf.setInvInizio(sdf.format(invComp.getDataInvalidita()));
				if (invComp.getDataInvaliditaScadenza() != null)
					pdf.setInvFine(sdf.format(invComp.getDataInvaliditaScadenza()));

				pdf.setCertH(this.fillBoolean(invComp.isCertificazioneH()));

				if (invComp.getDataCertificazione() != null)
					pdf.setCertHInizio(sdf.format(invComp.getDataCertificazione()));
				if (invComp.getDataCertificazioneScadenza() != null)
					pdf.setCertHFine(sdf.format(invComp.getDataCertificazioneScadenza()));

				if (invComp.getInvaliditaPerc() != null)
					pdf.setInvPerc(invComp.getInvaliditaPerc().toString() + " %");

				pdf.setAccompagnamento(this.fillBoolean(invComp.isAccompagnamento()));
				pdf.setLegge104(this.fillBoolean(invComp.isLegge104()));

				list.add(pdf);
			}
		}
		return list;

	}



	public List<DisabilitaPdfDTO> fillDisabilita() {
		List<DisabilitaPdfDTO> list = new ArrayList<DisabilitaPdfDTO>();

		if (schedaBean.getDatiDisabilitaBean().getListaComponenti() != null
				&& !schedaBean.getDatiDisabilitaBean().getListaComponenti().isEmpty()) {
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);

			List<ValiditaCompBaseBean> lstIn = schedaBean.getDatiDisabilitaBean().getListaComponenti();
			for (int i = 0; i < lstIn.size(); i++) {
				DatiDisabilitaComp ddComp = (DatiDisabilitaComp) lstIn.get(i);

				if (!this.esportaStorico && i > 0)
					break;

				DisabilitaPdfDTO pdf = new DisabilitaPdfDTO();
				valorizzaDateValidita(ddComp, pdf);

				if (ddComp.getIdGravita() != null && ddComp.getIdGravita().intValue() != 0) {
					dto.setObj(ddComp.getIdGravita().longValue());
					pdf.setGravitaDis(confService.getDisabGravitaById(dto).getDescrizione());
				}

				if (ddComp.getIdTipologia() != null && ddComp.getIdTipologia().intValue() != 0) {
					dto.setObj(ddComp.getIdTipologia().longValue());
					CsTbDisabTipologia csTipo = confService.getDisabTipologiaById(dto);
					pdf.setTipologiaDis(csTipo.getDescrizione() + ": " + csTipo.getTooltip());
				}
				list.add(pdf);
			}
		}
		return list;

	}



	public List<TribunalePdfDTO> fillTribunale() {
		List<TribunalePdfDTO> list = new ArrayList<TribunalePdfDTO>();

		if (schedaBean.getDatiTribunaleBean().getListaComponenti() != null
				&& !schedaBean.getDatiTribunaleBean().getListaComponenti().isEmpty()) {
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);

			List<ValiditaCompBaseBean> lstIn = schedaBean.getDatiTribunaleBean().getListaComponenti();
			for (int i = 0; i < lstIn.size(); i++) {
				DatiTribunaleComp triComp = (DatiTribunaleComp) lstIn.get(i);

				if (!this.esportaStorico && i > 0)
					break;

				TribunalePdfDTO tri = new TribunalePdfDTO();
				valorizzaDateValidita(triComp, tri);

				tri.setTmCivile(fillBoolean(triComp.isTMCivile()));
				tri.setTmAmm(fillBoolean(triComp.isTMAmministrativo()));
				tri.setPenaleMin(fillBoolean(triComp.isPenaleMinorile()));
				tri.setTo(fillBoolean(triComp.isTO()));
				tri.setNis(fillBoolean(triComp.isNIS()));
				tri.setProcuraMin(fillBoolean(triComp.isProcuraMinorile()));

				tri.setNumProtocollo(triComp.getNumeroProtocollo());

				if (triComp.getIdMacroSegnalazione() != null && triComp.getIdMacroSegnalazione().intValue() != 0) {
					dto.setObj(triComp.getIdMacroSegnalazione().longValue());
					tri.setMacroSegnal(confService.getMacroSegnalazioneById(dto).getDescrizione());
				}

				if (triComp.getIdMicroSegnalazione() != null && triComp.getIdMicroSegnalazione().intValue() != 0) {
					dto.setObj(triComp.getIdMicroSegnalazione().longValue());
					tri.setMicroSegnal(confService.getMicroSegnalazioneById(dto).getDescrizione());
				}

				if (triComp.getIdMotivoSegnalazione() != null && triComp.getIdMotivoSegnalazione().intValue() != 0) {
					dto.setObj(triComp.getIdMotivoSegnalazione().longValue());
					tri.setMotivoSegnal(confService.getMotivoSegnalazioneById(dto).getDescrizione());
				}

				tri.setPrimoContattoAG(
						triComp.getPrimoContattoAG() != null ? sdf.format(triComp.getPrimoContattoAG()) : "");

				list.add(tri);
			}
		}
		return list;

	}



	private String fillBoolean(Boolean value) {
		if (value != null && value)
			return "SI";
		else
			return "NO";
	}



	public List<OperatorePdfDTO> fillOperatori() {

		List<OperatorePdfDTO> lista = new ArrayList<OperatorePdfDTO>();

		if (schedaBean.getOperatoriBean().getLstComponents() != null
				&& !schedaBean.getOperatoriBean().getLstComponents().isEmpty()) {
			for (ValiditaCompBaseBean valComp : schedaBean.getOperatoriBean().getLstComponents()) {
				OperatoriComp opComp = (OperatoriComp) valComp;

				if ((!esportaStorico && opComp.isAttivo()) || esportaStorico) {
					OperatorePdfDTO op = new OperatorePdfDTO();
					valorizzaDateValidita(opComp, op);

					if (opComp.getResponsabile() != null && opComp.getResponsabile())
						op.setResponsabile("SI");
					op.setTipoOp(opComp.getTipo());
					op.setDenominazioneOp(opComp.getDescrizione());

					lista.add(op);
				}
			}
		}

		return lista;
	}



	public void fillDiario(DatiColloquioBean bean, List<DiarioPdfDTO> lista) {

		boolean riservato = bean.isRiservato();
		boolean autorizzato = bean.isAbilitato4riservato(); // TODO

		boolean show = !riservato || autorizzato;
		CsDColloquio colloquio = bean.getColloquio();

		DiarioPdfDTO op = new DiarioPdfDTO();
		Date dataColloquio = colloquio.getCsDDiario().getDtAmministrativa();
		op.setDataColloquio(dataColloquio != null ? sdf.format(dataColloquio) : "");
		AmAnagrafica am = CsUiCompBaseBean.getAnagraficaByUsername(colloquio.getCsDDiario().getUserIns());
		op.setOperatore(am != null ? am.getCognome() + " " + am.getNome() : colloquio.getCsDDiario().getUserIns());
		if (colloquio.getCsCDiarioDove() != null) {
			op.setDove(colloquio.getCsCDiarioDove().getDescrizione());
		}

		String conChi = colloquio.getCsCDiarioConchi().getDescrizione();
		if (colloquio.getCsCDiarioConchi().getId() == 9999 && colloquio.getDiarioConChiAltro() != null)
			conChi += ": " + colloquio.getDiarioConChiAltro();
		op.setConChi(conChi);
		op.setTipoColloquio(colloquio.getCsCTipoColloquio().getDescrizione());
		op.setRiservato(this.fillBoolean(riservato));
		String campoTesto = null;
		if (show) {
			campoTesto = colloquio.getTestoDiario();
		} else {
			campoTesto = "Contenuto del diario: RISERVATO";
		}
		op.setTestoDiario(campoTesto);
		lista.add(op);

	}



	public List<DiarioPdfDTO> fillDiario() throws Exception {

		List<DiarioPdfDTO> lista = new ArrayList<DiarioPdfDTO>();
		
		if(fascicoloBean.getColloquioBean()==null)
			fascicoloBean.initializeColloquioTab(soggetto);
			
		
		if (fascicoloBean.getColloquioBean().getListaColloquios() != null && !fascicoloBean.getColloquioBean().getListaColloquios().isEmpty()) {
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);

			for (ColloquioRowBean row : fascicoloBean.getColloquioBean().getListaColloquios()) {
				DatiColloquioBean datiColloquio = new DatiColloquioBean();
				dto.setObj(row.getDiarioId());
				try {
					CsDColloquio coll = diarioService.findColloquioById(dto);
					datiColloquio.Initialize(coll, row.getUsernameOp(), row.isRiservato(), row.isAbilitato4riservato());

					fillDiario(datiColloquio, lista);

				} catch (Exception e) {
					logger.error(e);
				}
			}
		}

		return lista;
	}



	public NotePdfDTO fillNote() {

		NotePdfDTO note = new NotePdfDTO();
		note.setNote(schedaBean.getNoteBean().getNote());

		return note;

	}
	
	
	public TriagePdfDTO fillTriage(RelazioneDTO relazioneDTO, TriageBean tbean){
		
		TriagePdfDTO tpdfDTO = new TriagePdfDTO();
		tpdfDTO.setDataInserimento(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(relazioneDTO.getTriage().getDataInserimento()));
		tpdfDTO.setTotale(String.valueOf(tbean.calcolaRisultato(relazioneDTO.getTriage())));
		
		//Morbilità
		TriageItemDTO temp = tbean.findTrigeItemByCode(relazioneDTO.getTriage().getMorbilita());
		if(temp != null){
			tpdfDTO.setMorbilitaLivello(temp.getTitle() + ": descrizione");
			tpdfDTO.setMorbilitaPunteggio(String.valueOf(temp.getValue()));	
		}
		//Alimentazione
		temp = tbean.findTrigeItemByCode(relazioneDTO.getTriage().getAlimentazione());
		if(temp != null){
			tpdfDTO.setAlimentazioneLivello(temp.getTitle() + ": descrizione");
			tpdfDTO.setAlimentazionePunteggio(String.valueOf(temp.getValue()));
		}
		//Alvo e Diuresi 
		temp = tbean.findTrigeItemByCode(relazioneDTO.getTriage().getAlvoDiuresi());
		if(temp != null){
			tpdfDTO.setAlvoDiuresiLivello(temp.getTitle() + ": descrizione");
			tpdfDTO.setAlvoDiuresiPunteggio(String.valueOf(temp.getValue()));
		}
		//Mobilità
		temp = tbean.findTrigeItemByCode(relazioneDTO.getTriage().getMobilita());
		if(temp != null){
			tpdfDTO.setMobilitaLivello(temp.getTitle() + ": descrizione");
			tpdfDTO.setMobilitaPunteggio(String.valueOf(temp.getValue()));
		}
		//Igiene personale
		temp = tbean.findTrigeItemByCode(relazioneDTO.getTriage().getIgienePersonale());
		if(temp != null){
			tpdfDTO.setIgienePersonaleLivello(temp.getTitle() + ": descrizione");
			tpdfDTO.setIgienePersonalePunteggio(String.valueOf(temp.getValue()));
		}
		//Stato mentale e comportamento 
		temp = tbean.findTrigeItemByCode(relazioneDTO.getTriage().getStatoMentale());
		if(temp != null){
			tpdfDTO.setStatoMentaleLivello(temp.getTitle() + ": descrizione");
			tpdfDTO.setStatoMentalePunteggio(String.valueOf(temp.getValue()));
		}
		//ConChiVive
		temp = tbean.findTrigeItemByCode(relazioneDTO.getTriage().getConChiVive());
		if(temp != null){
			tpdfDTO.setConChiViveLivello(temp.getTitle() + ": descrizione");
			tpdfDTO.setConChiVivePunteggio(String.valueOf(temp.getValue()));
		}
		//Assistenza diretta 
		temp = tbean.findTrigeItemByCode(relazioneDTO.getTriage().getAssistenzaDiretta());
		if(temp != null){
			tpdfDTO.setAssistenzaDirettaLivello(temp.getTitle() + ": descrizione");
			tpdfDTO.setAssistenzaDirettaPunteggio(String.valueOf(temp.getValue()));
		}
		
		//get anagrafica
		AnagraficaPdfDTO aPdfDTO = fillAnagrafica();
		tpdfDTO.setNome(aPdfDTO.getNome());
		tpdfDTO.setCognome(aPdfDTO.getCognome());
		tpdfDTO.setMedicoCurante(aPdfDTO.getMedico());
		tpdfDTO.setCodiceFiscale(aPdfDTO.getCodFiscale());
		
		//get isee
		BaseDTO dtoi = new BaseDTO();
		fillEnte(dtoi);
		dtoi.setObj(soggetto.getCsACaso().getId());
		List<CsDIsee> lstdiario = diario.findIseeByCaso(dtoi);
		Collections.sort(lstdiario,new Comparator<CsDIsee>() {

			@Override
			public int compare(CsDIsee o1, CsDIsee o2) {
				int anno1 = Integer.parseInt(o1.getAnnoRif());
				int anno2 = Integer.parseInt(o2.getAnnoRif());
				
				if (anno1 == anno2) return 0;
				
				return anno1 < anno2 ? -1 : 1;
			}
			
		});
		
		if(lstdiario.size() > 0){
			tpdfDTO.setIsee(lstdiario.get(0).getIsee().toPlainString());
		}else{
			tpdfDTO.setIsee("");
		}
		
		//accompagnamento invalidita
		if (schedaBean.getDatiInvaliditaBean().getListaComponenti() != null
				&& !schedaBean.getDatiInvaliditaBean().getListaComponenti().isEmpty()) {

			List<ValiditaCompBaseBean> lstIn = schedaBean.getDatiInvaliditaBean().getListaComponenti();
			Collections.sort(lstIn,new Comparator<ValiditaCompBaseBean>() {

				@Override
				public int compare(ValiditaCompBaseBean o1,
						ValiditaCompBaseBean o2) {
					
					return o1.getDataInizio().compareTo(o2.getDataInizio());
				}
				
			});
			
			DatiInvaliditaComp invComp = (DatiInvaliditaComp) lstIn.get(0);
			tpdfDTO.setAccompagnamento(this.fillBoolean(invComp.isAccompagnamento()));
			
		}
		
		return tpdfDTO;
	}



	public void setSchedaBean(SchedaBean schedaBean) {
		this.schedaBean = schedaBean;
	}



	public void setSoggetto(CsASoggettoLAZY soggetto) {
		this.soggetto = soggetto;
	}



	public FascicoloBean getFascicoloBean() {
		return fascicoloBean;
	}



	public void setFascicoloBean(FascicoloBean fascicoloBean) {
		this.fascicoloBean = fascicoloBean;
	}



	public boolean isEsportaStorico() {
		return esportaStorico;
	}



	public void setEsportaStorico(boolean esportaStorico) {
		this.esportaStorico = esportaStorico;
	}

}
