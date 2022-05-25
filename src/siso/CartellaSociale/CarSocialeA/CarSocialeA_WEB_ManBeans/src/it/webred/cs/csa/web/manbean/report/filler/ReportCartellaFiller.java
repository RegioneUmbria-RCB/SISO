package it.webred.cs.csa.web.manbean.report.filler;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.csa.ejb.dto.PaiDTOExt;
import it.webred.cs.csa.ejb.dto.PresaInCaricoDTO;
import it.webred.cs.csa.ejb.dto.RelazioneDTO;
import it.webred.cs.csa.ejb.dto.TriageItemDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneMasterDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.CsPaiAffidoDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.CsPaiAffidoDominioDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.CsPaiAffidoSoggFamigliaDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.CsPaiAffidoSoggettoDTO;
import it.webred.cs.csa.ejb.dto.pai.affido.PaiAffidoStatoEnum;
import it.webred.cs.csa.ejb.dto.siru.StampaFseDTO;
import it.webred.cs.csa.utils.bean.report.dto.StoricoPdfDTO;
import it.webred.cs.csa.utils.bean.report.dto.TriagePdfDTO;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloBean;
import it.webred.cs.csa.web.manbean.fascicolo.erogazioniInterventi.ErogInterventoRowBean;
import it.webred.cs.csa.web.manbean.fascicolo.pai.PaiAffidoBean;
import it.webred.cs.csa.web.manbean.fascicolo.relazioni.RelazioniBean;
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
import it.webred.cs.csa.web.manbean.report.dto.cartella.TabellaPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.cartella.TribunalePdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.fascicolo.AffidoPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.fascicolo.AttivitaProfPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.fascicolo.ErogazioniPaiPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.fascicolo.ModelloPorPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.fascicolo.PAIPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.fascicolo.ProblematichePdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.fascicolo.PropostaInterventoPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.fascicolo.TipoAttivitaDuePdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.fascicolo.TipoAttivitaUnoPdfDTO;
import it.webred.cs.csa.web.manbean.scheda.SchedaBean;
import it.webred.cs.csa.web.manbean.scheda.anagrafica.AnagraficaBean;
import it.webred.cs.csa.web.manbean.scheda.disabilita.DatiDisabilitaComp;
import it.webred.cs.csa.web.manbean.scheda.invalidita.DatiInvaliditaComp;
import it.webred.cs.csa.web.manbean.scheda.operatori.OperatoriComp;
import it.webred.cs.csa.web.manbean.scheda.parenti.ParentiComp;
import it.webred.cs.csa.web.manbean.scheda.sociali.DatiSocialiComp;
import it.webred.cs.csa.web.manbean.scheda.tribunale.DatiTribunaleComp;
import it.webred.cs.data.DataModelCostanti.GrVulnerabile;
import it.webred.cs.data.DataModelCostanti.Pai;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsASoggettoCategoriaSoc;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCDiarioConchi;
import it.webred.cs.data.model.CsDColloquio;
import it.webred.cs.data.model.CsDIsee;
import it.webred.cs.data.model.CsDPai;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsRelRelazioneProbl;
import it.webred.cs.data.model.CsRelRelazioneTipoint;
import it.webred.cs.data.model.CsTbDisabTipologia;
import it.webred.cs.data.model.CsTbGVulnerabile;
import it.webred.cs.jsf.bean.ValiditaCompBaseBean;
import it.webred.cs.jsf.bean.colloquio.ColloquioRowBean;
import it.webred.cs.jsf.bean.colloquio.DatiColloquioBean;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.json.abitazione.ver1.AbitazioneBean;
import it.webred.cs.json.abitazione.ver1.AbitazioneManBean;
import it.webred.cs.json.familiariConviventi.DatiSocialiFamiliariConviventiPdfDTO;
import it.webred.cs.json.stranieri.ver1.StranieriBean;
import it.webred.cs.json.stranieri.ver1.StranieriManBean;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.dto.utility.KeyValuePairBean;
import it.webred.jsf.bean.ComuneBean;
import it.webred.jsf.bean.SessoBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ReportCartellaFiller extends CsUiCompBaseBean {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private CsASoggettoLAZY soggetto;
	private SchedaBean schedaBean;
	private FascicoloBean fascicoloBean;
	//private PaiAffidoBean paiAffidoBean;
	private boolean esportaStorico;

	private RelazioniBean relazioniBean;


	public AnagraficaPdfDTO fillAnagrafica() {

		AnagraficaPdfDTO ana = new AnagraficaPdfDTO();
		AnagraficaBean anaBean = schedaBean.getAnagraficaBean();
		ana.setCognome(anaBean.getDatiAnaBean().getCognome());
		ana.setNome(anaBean.getDatiAnaBean().getNome());
		ana.setDataNascita(fillData(anaBean.getDatiAnaBean().getDataNascita()));
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
			ana.setResidenza(res);
		}
		if (!anaBean.getStatoCivileGestBean().getLstComponentsActive().isEmpty())
			ana.setStatoCivile(anaBean.getStatoCivileGestBean().getLstComponentsActive().get(0).getDescrizione());
		if (!anaBean.getStatusGestBean().getLstComponentsActive().isEmpty())
			ana.setStatus(anaBean.getStatusGestBean().getLstComponentsActive().get(0).getDescrizione());
		if (!anaBean.getMediciGestBean().getLstComponentsActive().isEmpty()){
			ValiditaCompBaseBean vb = anaBean.getMediciGestBean().getLstComponentsActive().get(0);
			List<KeyValuePairBean> lstInfo = vb.getInfoAggiuntive();
			String lst = "";
			if(lstInfo!=null){
				for(KeyValuePairBean k : lstInfo){
					if(k!=null) lst+= k.getKey()+": "+k.getValue()+" ";
				}
			}
			String info = !lst.trim().isEmpty() ? "("+lst.trim()+")" : "";
			ana.setMedico(vb.getDescrizione());
			ana.setInfoMedico(info);
		}
		String tel = anaBean.getDatiAnaBean().getTelefono()!=null ? anaBean.getDatiAnaBean().getTelefono() : "";
		tel += anaBean.getDatiAnaBean().getTitTelefono() != null ? " (" + anaBean.getDatiAnaBean().getTitTelefono() + ")": "";
		if(!tel.trim().isEmpty()) ana.setTelefono(tel);

		String cel = anaBean.getDatiAnaBean().getCellulare()!=null ? anaBean.getDatiAnaBean().getCellulare() : "";
		cel += anaBean.getDatiAnaBean().getTitCellulare() != null ? " (" + anaBean.getDatiAnaBean().getTitCellulare() + ")" : "";
		if(!cel.trim().isEmpty())ana.setCellulare(cel);

		String email = anaBean.getDatiAnaBean().getEmail()!=null ?  anaBean.getDatiAnaBean().getEmail() : "";
		email += anaBean.getDatiAnaBean().getTitEmail() != null ? " (" + anaBean.getDatiAnaBean().getTitEmail() + ")": "";
		if(!email.trim().isEmpty()) ana.setEmail(email);

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
			
			//Presa In Carico
			dto.setObj(soggetto.getCsACaso().getId());
			PresaInCaricoDTO pic = iterService.getLastPICByCaso(dto);
			if(pic!=null){
				ana.setAssSociale(pic.getResponsabile().getDenominazione());
				ana.setDataPIC(fillData(pic.getDataAmministrativa()));
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
		par.setDataNascita(fillData(csPar.getCsAAnagrafica().getDataNascita()));
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
			residenza += " - " + (!StringUtils.isBlank(csPar.getComAltroDes()) ? csPar.getComAltroDes() : "");
		par.setResidenza(residenza);
		par.setTelefono(csPar.getCsAAnagrafica().getTel());
		par.setCellulare(csPar.getCsAAnagrafica().getCell());
		par.setEmail(csPar.getCsAAnagrafica().getEmail());
		String parentela = csPar.getCsTbTipoRapportoCon()!=null ? csPar.getCsTbTipoRapportoCon().getDescrizione() : "";
		parentela += csPar.getAffidatario() !=null && csPar.getAffidatario().booleanValue() ?  " - Affidatario" : "";
		par.setParentela(parentela);
		if (csPar.getCsAAnagrafica().getDataDecesso() != null)
			par.setDecesso("SI (" + fillData(csPar.getCsAAnagrafica().getDataDecesso()) + ")");
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
					dsStra.setCondizioneGiuridicaPermessoScadenza(fillData(stranieriBean.getScadPermessoSogg()));
					
	
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
				
				pdf.setInvInizio(fillData(invComp.getDataInvalidita()));
				pdf.setInvFine(fillData(invComp.getDataInvaliditaScadenza()));
				pdf.setCertH(this.fillBoolean(invComp.isCertificazioneH()));
				pdf.setCertHInizio(fillData(invComp.getDataCertificazione()));
				pdf.setCertHFine(fillData(invComp.getDataCertificazioneScadenza()));

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
				tri.setInfoNonReperibili((triComp.isInfoNonReperibili()));
				
				String repStrutture = "";
				if(!triComp.isInfoNonReperibili()){
					List<SelectItem> lstStrutture = triComp.getLstStrutture();
					for(SelectItem si : lstStrutture){
						if(triComp.getSelStrutture().contains(si.getValue()))
							repStrutture += si.getLabel()+", ";
					}
					tri.setStrutture(repStrutture.substring(0,repStrutture.lastIndexOf(',')));
				}
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

				tri.setPrimoContattoAG(fillData(triComp.getPrimoContattoAG()));
				
				list.add(tri);
			}
		}
		return list;

	}


	private String fillBoolean(boolean value) {
		if (value)
			return "SI";
		else
			return "NO";
	}
	
	private String fillBoolean(Boolean value) {
		if (value != null && value)
			return "SI";
		else
			return "NO";
	}



	public List<OperatorePdfDTO> fillOperatori() {

		List<OperatorePdfDTO> lista = new ArrayList<OperatorePdfDTO>();

		if (schedaBean.getSchedaPermessiBean().getLstComponents() != null
				&& !schedaBean.getSchedaPermessiBean().getLstComponents().isEmpty()) {
			for (ValiditaCompBaseBean valComp : schedaBean.getSchedaPermessiBean().getLstComponents()) {
				if(valComp instanceof OperatoriComp){
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
		op.setDataColloquio(fillData(dataColloquio));
		op.setOperatore(CsUiCompBaseBean.getCognomeNomeUtente(colloquio.getCsDDiario().getUserIns()));
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
		List<CsDIsee> lstdiario = diarioService.findIseeByCaso(dtoi);
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
	
	 public List<AttivitaProfPdfDTO> fillAttivita(List<RelazioneDTO> listaRelSel,String subPath){
		
		  List<AttivitaProfPdfDTO> ListaPdfDto = new ArrayList<AttivitaProfPdfDTO>();
		 //dentro al for per popolare tutto 
		  if(listaRelSel!=null){
			  for(RelazioneDTO rel : listaRelSel){
			  	AttivitaProfPdfDTO pdf = new AttivitaProfPdfDTO();
			  	pdf.setNumOperatori(rel.getRelazione().getNumOperatori() != null ? rel.getRelazione().getNumOperatori().toString() : "non indicati");
			  	pdf.setDtAttivita(fillData(rel.getRelazione().getCsDDiario().getDtAmministrativa()));
			  	pdf.setDtProssimaAttDal(fillData(rel.getRelazione().getDataProssimaRelazioneDal()));
			  	pdf.setDtProssimaAttAl(fillData(rel.getRelazione().getDataProssimaRelazioneAl()));
			  	pdf.setEseguitaRichiestaIndagine(rel.getRelazione().getRichiestaIndagine() != null ? rel.getRelazione().getRichiestaIndagine() : "");
			  	pdf.setOreImpiegateTot(rel.getRelazione().getOreImpiegate() != null ?  new SimpleDateFormat("HH:mm").format(rel.getRelazione().getOreImpiegate()) : "");
			  	pdf.setAnalisiPro(fillBoolean(rel.getRelazione().getFlagRilevazioneProblematiche()));
			  	if(rel.getRelazione().getMicroAttivita().getFlagTipoForm().equals("1")){
					pdf.setTipoAttivita_1(new JRBeanCollectionDataSource(Arrays.asList(filltipoUno(rel))));
					pdf.setSUBREPORT_DIR(subPath);
			  	}
				if(rel.getRelazione().getMicroAttivita().getFlagTipoForm().equals("2")){
					pdf.setTipoAttivita_2(new JRBeanCollectionDataSource(Arrays.asList(filltipoDue(rel))));
					pdf.setSUBREPORT_DIR(subPath);
				}
				if(rel.getRelazione().getMicroAttivita().getFlagTipoForm().equals("3")){
					pdf.setTipoAttivita_3(new JRBeanCollectionDataSource(Arrays.asList(filltipoTre(rel))));
					pdf.setSUBREPORT_DIR(subPath);
				}
				
				if(rel.getListaProblematiche() !=null) {
				 	pdf.setLstProblematiche(new JRBeanCollectionDataSource(fillProblematiche(rel)));
				 	pdf.setSUBREPORT_DIR(subPath);
				}
				
				if(rel.getListaInterventi() !=null) {
				 	pdf.setLstInterventi(new JRBeanCollectionDataSource(fillPropostaIntervento(rel)));
				 	pdf.setSUBREPORT_DIR(subPath);
				}

				
			    ListaPdfDto.add(pdf);
			  }
		  }
		 return ListaPdfDto;
	 }
	 
	 public TipoAttivitaUnoPdfDTO filltipoUno(RelazioneDTO rel){
		 
			TipoAttivitaUnoPdfDTO tipoPdf = new TipoAttivitaUnoPdfDTO();
				tipoPdf.setSocioAmbientale(rel.getRelazione().getSituazioneAmb());
				tipoPdf.setParentale(rel.getRelazione().getSituazioneParentale() != null ? rel.getRelazione().getSituazioneParentale() : "");
				tipoPdf.setSanitaria(rel.getRelazione().getSituazioneSanitaria() != null ? rel.getRelazione().getSituazioneSanitaria() : "" );
				tipoPdf.setProposta(rel.getRelazione().getProposta() != null ? rel.getRelazione().getProposta() : "");
				tipoPdf.setOrgServizio(rel.getRelazione().getOrganizzazioneServizio() != null ? rel.getRelazione().getOrganizzazioneServizio() :"");
			return tipoPdf;
							
		}


	 public TipoAttivitaDuePdfDTO filltipoDue(RelazioneDTO rel){

			TipoAttivitaDuePdfDTO tipoPdf = new TipoAttivitaDuePdfDTO();
			String descrizione = "";	
			
			tipoPdf.setTesto(rel.getRelazione().getTesto());
			if(!rel.getRelazione().getLstConChi().isEmpty()){
				descrizione = "Con Chi: ";
				String temp = "";
				for (CsCDiarioConchi con : rel.getRelazione().getLstConChi()){ 
						temp = temp.isEmpty() ? con.getDescrizione() : temp.concat(", " + con.getDescrizione());
				}
				descrizione = descrizione.concat(temp);
			}
			
			///SISO-1481
//			if(rel.getRelazione().getRiunioneCon() != null)
//				descrizione = rel.getRelazione().getMacroAttivita().getDescrizione()+"  Con:" + rel.getRelazione().getRiunioneCon().getNome() +" "+(rel.getRelazione().getRiunioneCon().getNome2() !=null ? rel.getRelazione().getRiunioneCon().getNome2() : "");	
			
			if(!rel.getRelazione().getLstRiunioneConChi().isEmpty()) {
				descrizione = rel.getRelazione().getMacroAttivita().getDescrizione() +"  Con:  " ;
			
                String temp = "";
				
				for (CsOSettore riunioneCon : rel.getRelazione().getLstRiunioneConChi()){ 
						temp = temp.isEmpty() ? riunioneCon.getCsOOrganizzazione().getNome() + " - " + riunioneCon.getNome() : temp.concat(", " + riunioneCon.getCsOOrganizzazione().getNome() + " - " + riunioneCon.getNome());
				}
				descrizione = descrizione.concat(temp);
			}
			
			if(rel.getRelazione().getConChiAltro() != null)
				descrizione.concat(" ").concat(rel.getRelazione().getConChiAltro());
			
			tipoPdf.setDescrizioneCon(descrizione); 

			return tipoPdf;
		}

		public TriagePdfDTO filltipoTre(RelazioneDTO rel){
			
			TriageBean  tbean = new TriageBean();
			TriagePdfDTO tpdfDTO = new TriagePdfDTO();
			tpdfDTO.setDataInserimento(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(rel.getTriage().getDataInserimento()));
			tpdfDTO.setTotale(String.valueOf(tbean.calcolaRisultato(rel.getTriage())));
			
			//Morbilità
			TriageItemDTO temp = tbean.findTrigeItemByCode(rel.getTriage().getMorbilita());
			if(temp != null){
				tpdfDTO.setMorbilitaLivello(temp.getTitle() );
				tpdfDTO.setMorbilitaPunteggio(String.valueOf(temp.getValue()));	
			}
			//Alimentazione
			temp = tbean.findTrigeItemByCode(rel.getTriage().getAlimentazione());
			if(temp != null){
				tpdfDTO.setAlimentazioneLivello(temp.getTitle());
				tpdfDTO.setAlimentazionePunteggio(String.valueOf(temp.getValue()));
			}
			//Alvo e Diuresi 
			temp = tbean.findTrigeItemByCode(rel.getTriage().getAlvoDiuresi());
			if(temp != null){
				tpdfDTO.setAlvoDiuresiLivello(temp.getTitle());
				tpdfDTO.setAlvoDiuresiPunteggio(String.valueOf(temp.getValue()));
			}
			//Mobilità
			temp = tbean.findTrigeItemByCode(rel.getTriage().getMobilita());
			if(temp != null){
				tpdfDTO.setMobilitaLivello(temp.getTitle());
				tpdfDTO.setMobilitaPunteggio(String.valueOf(temp.getValue()));
			}
			//Igiene personale
			temp = tbean.findTrigeItemByCode(rel.getTriage().getIgienePersonale());
			if(temp != null){
				tpdfDTO.setIgienePersonaleLivello(temp.getTitle());
				tpdfDTO.setIgienePersonalePunteggio(String.valueOf(temp.getValue()));
			}
			//Stato mentale e comportamento 
			temp = tbean.findTrigeItemByCode(rel.getTriage().getStatoMentale());
			if(temp != null){
				tpdfDTO.setStatoMentaleLivello(temp.getTitle());
				tpdfDTO.setStatoMentalePunteggio(String.valueOf(temp.getValue()));
			}
			//ConChiVive
			temp = tbean.findTrigeItemByCode(rel.getTriage().getConChiVive());
			if(temp != null){
				tpdfDTO.setConChiViveLivello(temp.getTitle());
				tpdfDTO.setConChiVivePunteggio(String.valueOf(temp.getValue()));
			}
			//Assistenza diretta 
			temp = tbean.findTrigeItemByCode(rel.getTriage().getAssistenzaDiretta());
			if(temp != null){
				tpdfDTO.setAssistenzaDirettaLivello(temp.getTitle());
				tpdfDTO.setAssistenzaDirettaPunteggio(String.valueOf(temp.getValue()));
			}
			
			return 	tpdfDTO;
			
			
		}
	
	//SISO-1223
		
	public List<ProblematichePdfDTO> fillProblematiche(RelazioneDTO rel){

		List<ProblematichePdfDTO> lista = new ArrayList<ProblematichePdfDTO>();
		relazioniBean = new RelazioniBean();
		
		if (rel.getListaProblematiche() != null) {
			
		for (CsRelRelazioneProbl problematica : rel.getListaProblematiche()) {
			
		  	ProblematichePdfDTO problematichePdf = new ProblematichePdfDTO();
		  	
		  	problematichePdf.setClasse(problematica.getCsTbProbl().getFlagMatImm().toString() == "M" ? "Materiale" : "Immateriale");//Classe
		  	problematichePdf.setTipo(problematica.getCsTbProbl().getTipo());//TIPO
		  	problematichePdf.setDescrizione(problematica.getCsTbProbl().getDescrizione());//Problematica
		  	if (problematica.getCsDRelazioneRif() != null ) {
			  	if (problematica.getCsDRelazioneRif().getCsDDiario().getDtAmministrativa()!=null) {
			  		problematichePdf.setRifAnalisi(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(problematica.getCsDRelazioneRif().getCsDDiario().getDtAmministrativa())); //Rif. analisi
			  	}
		  	}
		  	problematichePdf.setTipoAttivita(relazioniBean.printTipoAttivitaProblematica(problematica));
		  	problematichePdf.setRisolta(problematica.getFlagRisolta() == true ? "Si" : "No");
		  	problematichePdf.setVerificata(problematica.getFlagVerificata() == true ? "Si" : "No");
		  	
		  	lista.add(problematichePdf);
			}
		}


		return lista;
		
		
	}
	
	public List<PropostaInterventoPdfDTO> fillPropostaIntervento(RelazioneDTO rel){

		List<PropostaInterventoPdfDTO> lista = new ArrayList<PropostaInterventoPdfDTO>();
		relazioniBean = new RelazioniBean();
		
		if (rel.getListaInterventi() != null) {
			
		for (CsRelRelazioneTipoint tipoInterventoProposto : rel.getListaInterventi()) {
			
			PropostaInterventoPdfDTO propostaIntervento = new PropostaInterventoPdfDTO();
		  	
		  	propostaIntervento.setDescrizione(tipoInterventoProposto.getCsCTipoIntervento().getDescrizione());
		  	if(tipoInterventoProposto.getCsCTipoInterventoCustom()!=null)
		  		propostaIntervento.setCustom(tipoInterventoProposto.getCsCTipoInterventoCustom().getDescrizione());
		  	
		  	lista.add(propostaIntervento);
			}
		}


		return lista;
		
		
	}
	
	private String getObiettiviRaggiunti(Integer raggiunto){
		String desc="";
		if(raggiunto!=null){
			if(raggiunto.equals(Pai.RAGGIUNTI_NO))
				desc = "non raggiunti";
			else if(raggiunto.equals(Pai.RAGGIUNTI_PARZIALMENTE))
				desc = "raggiunti parzialmente";
			else if(raggiunto.equals(Pai.RAGGIUNTI_SI))
				desc = "raggiunti";
		}
		return desc;
	}
	
	public List<PAIPdfDTO> fillPAI(List<CsDPai> listaPAISel,List<RelazioneDTO> listaRelDTO,String subPath){
		 List<PAIPdfDTO> ListaPdfDto = new ArrayList<PAIPdfDTO>();
		 PaiAffidoBean paiAffidoBean = new PaiAffidoBean();
		 try {
			 if(listaPAISel!=null && !listaPAISel.isEmpty()){
			
				/*Inizio PDF*/
				for(CsDPai pai : listaPAISel){
					/*Scrittura campi in comune a tutti i tipi di PAI*/
					 PAIPdfDTO pdfPai = new PAIPdfDTO();
					 pdfPai.setDtAttivazione(fillData(pai.getCsDDiario().getDtAttivazioneDa()));
					 
					 pdfPai.setDtChiusuraPrevista(fillData(pai.getDataChiusuraPrevista()));
					 pdfPai.setObiettiviBreveTer(pai.getObiettiviBreve() != null && !pai.getObiettiviBreve().isEmpty() ? pai.getObiettiviBreve() : "");
					 pdfPai.setObiettiviMedioTer(pai.getObiettiviMedio()!= null && !pai.getObiettiviMedio().isEmpty() ? pai.getObiettiviMedio() : "");
					 pdfPai.setObiettiviLungoTer(pai.getObiettiviLungo() != null && !pai.getObiettiviLungo().isEmpty() ? pai.getObiettiviLungo() : "");
					 
					 pdfPai.setRaggiuntiBreve(this.getObiettiviRaggiunti(pai.getRaggiuntiBreve()));
					 pdfPai.setRaggiuntiMedio(this.getObiettiviRaggiunti(pai.getRaggiuntiMedio()));
					 pdfPai.setRaggiuntiLungo(this.getObiettiviRaggiunti(pai.getRaggiuntiLungo()));
					
					 pdfPai.setVerificaOgni(pai.getVerificaOgni().toString() + " "+ pai.getVerificaUnitaMisura());
					 pdfPai.setDtUltimoMonitor(fillData(pai.getDataMonitoraggio()));
					 pdfPai.setMotivazioniProgetto(pai.getMotivazioniProgetto() != null && pai.getMotivazioniProgetto().isEmpty()? pai.getMotivazioniProgetto() : ""); //SISO-1172
					 // pdfPai.setDtNuovoMonitor(pai.get);
					 
					 pdfPai.setSUBREPORT_DIR(subPath);
					 pdfPai.setTipoProgetto(pai.getCsTbTipoPai().getDescrizione());
					 if(pai.getCsTbTipoPai().getDescrizione().equals("affidamento familiare")){
						 paiAffidoBean.findAffidoByPai(pai.getDiarioId(), soggetto.getAnagraficaId());
						 CsPaiAffidoDTO	affido = paiAffidoBean.getAffido();
						 pdfPai.setAffido(new JRBeanCollectionDataSource(Arrays.asList(fillAffido(affido,subPath,paiAffidoBean)),false));
					 } 
					/*Inizio Gestione Attivita collegate */	 
					 List<RelazioneDTO> ListaRelPAi = new ArrayList<RelazioneDTO>(); 
					 for(RelazioneDTO rel : listaRelDTO){ //relazioni disponibili soggetto
							if(!rel.getListaPaiDTO().isEmpty()){//ci sono pai collegati?
								for(PaiDTOExt relPai : rel.getListaPaiDTO()){ 
									if(pai.getDiarioId().equals(relPai.getPai().getDiarioId()))
											 ListaRelPAi.add(rel);
								}		 
							}
					 }
					 if(!ListaRelPAi.isEmpty()){
						List<AttivitaProfPdfDTO> lst = new ArrayList<AttivitaProfPdfDTO>();
						 for(RelazioneDTO  rel : ListaRelPAi){
							   //Aggiungo le testate delle Attivita
							AttivitaProfPdfDTO pdf = new AttivitaProfPdfDTO();
						  	pdf.setNumOperatori(rel.getRelazione().getNumOperatori() != null ? rel.getRelazione().getNumOperatori().toString() : "non indicato");
						  	pdf.setDtAttivita(fillData(rel.getRelazione().getCsDDiario().getDtAmministrativa()));
						  	pdf.setDtProssimaAttDal(fillData(rel.getRelazione().getDataProssimaRelazioneDal()));
						  	pdf.setDtProssimaAttAl(fillData(rel.getRelazione().getDataProssimaRelazioneAl()));
						  	pdf.setEseguitaRichiestaIndagine(rel.getRelazione().getRichiestaIndagine() != null ? rel.getRelazione().getRichiestaIndagine() : "non indicato");
						  	pdf.setOreImpiegateTot(rel.getRelazione().getOreImpiegate() != null ? new SimpleDateFormat("HH:mm").format(rel.getRelazione().getOreImpiegate()) : "non indicato");
						  	pdf.setAnalisiPro(fillBoolean(rel.getRelazione().getFlagRilevazioneProblematiche()));
						  	if(rel.getMicroAttivita()!=null){
						  		pdf.setMicroattivita(rel.getMicroAttivita().getDescrizione());
						  		pdf.setMacroattivita(rel.getMicroAttivita().getMacroAttivita().getDescrizione());
						  	}
						  	lst.add(pdf);
						   }
						 pdfPai.setAttivitaCollegate(new JRBeanCollectionDataSource(lst,false));
					 }
				 /*FIne*/
					 
				 /*Inizio Gestione Erogazioni Collegate*/
				 	List<ErogInterventoRowBean> lstControllata = getErogazioniCollegatePAI(pai.getDiarioId());
					if(!lstControllata.isEmpty()){
						List<ErogazioniPaiPdfDTO> listapdfErog = new ArrayList<ErogazioniPaiPdfDTO>(); 
						for(ErogInterventoRowBean erog : lstControllata){
							ErogazioniPaiPdfDTO pdfErogPai = new ErogazioniPaiPdfDTO();
							pdfErogPai.setDataUltimaErogazione(fillData(erog.getMaster().getDataUltimaErogazione()));
							pdfErogPai.setStatoUltimaErog(erog.getMaster().getStatoUltimaErogazione());
							pdfErogPai.setTipoIntervento(erog.getMaster().getTipoIntervento().getDescrizione());
							pdfErogPai.setTipoInterventoCustom(erog.getMaster().getTipoInterventoCustom());
							listapdfErog.add(pdfErogPai);
						}
						pdfPai.setErogazioniPai(new JRBeanCollectionDataSource(listapdfErog,false));
					}
					/*Fine*/
					ListaPdfDto.add(pdfPai);
				 }
			 }
		} catch (Exception e) {
				logger.error(e);
		}
			 
		 return ListaPdfDto;
	}
	
	private List<ErogInterventoRowBean> getErogazioniCollegatePAI(Long paiId){
		List<ErogInterventoRowBean> erogPai = new ArrayList<ErogInterventoRowBean>();
	    CsOOperatoreSettore opSettore = CsUiCompBaseBean.getCurrentOpSettore();
		ErogazioniSearchCriteria bDto = new ErogazioniSearchCriteria();
		fillEnte(bDto);
		bDto.setSettoreId(opSettore.getCsOSettore().getId());
		bDto.setOrganizzazioneId(opSettore.getCsOSettore().getCsOOrganizzazione().getId());
		bDto.setFirst(0);
		bDto.setPageSize(0);
		bDto.setPermessoAutorizzativo(CsUiCompBaseBean.isPermessoAutorizzativo());
		//bDto.setSearchByCaso(true);
		//bDto.setCasoId(soggetto.getCsACaso().getId());
		bDto.setDiarioPaiId(paiId);
		
		boolean loadDettaglioErogazione = false;
		bDto.setLoadDettaglioErogazione(loadDettaglioErogazione);
		
		List<ErogazioneMasterDTO> lst = interventoService.searchListaErogInterventi(bDto);
		for(ErogazioneMasterDTO dae : lst){
			ErogInterventoRowBean row = new ErogInterventoRowBean(dae, loadDettaglioErogazione);
			erogPai.add(row);
		}
		return erogPai;
	} 
	
	public AffidoPdfDTO fillAffido(CsPaiAffidoDTO affido,String subPath,PaiAffidoBean paiAffidoBean) throws Exception{
		
		AffidoPdfDTO affidoPdf = new AffidoPdfDTO();
		for(CsPaiAffidoDominioDTO P : paiAffidoBean.getListaAdottabile()){
			if(affido.getCodiceAdottabile()!= null && affido.getCodiceAdottabile().equals(P.getCodice()))
				affidoPdf.setAdottabile(P.getDescrizione());
		}
		affidoPdf.setSUBREPORT_DIR(subPath);
		affidoPdf.setAffidoParziale(fillBoolean(paiAffidoBean.isAffidoParziale()));
		affidoPdf.setAffidoProf(fillBoolean(affido.getAffidamentoProfessionale()));
		
		affidoPdf.setCollocamento(fillBoolean(paiAffidoBean.isCollocamentoRequired()));
		
		affidoPdf.setAffidamentoLungoTermine(fillBoolean(affido.getAffidamentoLungoTermine())); //SISO-1172
		
		for(CsPaiAffidoDominioDTO P : paiAffidoBean.getListaConvivenzaOrigAff()){
			if(affido.getCodiceConvivenzaOrigineAffidataria() != null && affido.getCodiceConvivenzaOrigineAffidataria().equals(P.getCodice()))
				affidoPdf.setConvivGenitoriConAffid(P.getDescrizione());
		}
		affidoPdf.setDataDecreto(fillData(affido.getDataDecreto()));
		affidoPdf.setNumDecreto(affido.getNumeroDecreto());
//		affidoPdf.setDisabilitaDuranteAffi(affido.getDisabilitaDuranteAffido() ? "SI" : "NO");// SISO-1172
		
		for(CsPaiAffidoDominioDTO P : paiAffidoBean.getListaEntitaAffido()){
			if(affido.getCodiceEntitaAffido() != null && affido.getCodiceEntitaAffido().equals(P.getCodice()))
			affidoPdf.setEntitaAffido(P.getDescrizione());
		}
		
		for(CsPaiAffidoDominioDTO P : paiAffidoBean.getListaFrequenzaContatti()){
			if(affido.getCodiceFrequenzaContattiMinore() != null && affido.getCodiceFrequenzaContattiMinore().equals(P.getCodice()))
				affidoPdf.setFrequenzaContattiMinFamOrig(P.getDescrizione());
		}
//		affidoPdt.
		// SISO-1172 --Non essite più l'esito affido ma il motivo della chiusura
//		for(CsPaiAffidoDominioDTO P : paiAffidoBean.getListaEsitoAffido()){
//			if(affido.getCodiceEsitoAffido() != null && affido.getCodiceEsitoAffido().equals(P.getCodice()))
//				affidoPdf.setEsitoAffido(P.getDescrizione());
//		}
//		affidoPdf.setEsitoAffidoAltro(affido.getAltroEsitoAffido() != null ? affido.getAltroEsitoAffido() : "");// SISO-1172
		
		affidoPdf.setFamAffCaratter( affido.getFamigliaAffidataria().getCaratteristiche() != null ? affido.getFamigliaAffidataria().getCaratteristiche() : "");
		
		for(CsPaiAffidoDominioDTO P : paiAffidoBean.getListaAffidatari()){
			if(affido.getFamigliaAffidataria().getCodiceTipoFamiglia() != null && affido.getFamigliaAffidataria().getCodiceTipoFamiglia().equals(P.getCodice()))
				affidoPdf.setFamAffAffidatari(P.getDescrizione());
		}
		List<TabellaPdfDTO> tabella3 = new ArrayList<TabellaPdfDTO>(); 
		for(CsPaiAffidoSoggFamigliaDTO fam : paiAffidoBean.getFamigliaAff()){
			TabellaPdfDTO tab = new TabellaPdfDTO();
			tab.setCognome(fam.getCognome());
			tab.setNome(fam.getNome());
			tab.setHeaderTab("Cod. Fiscale");
			tab.setCodFiscale(fam.getCf());
			tab.setRelazione(fam.getParentela());
			tab.setSesso(fam.getSesso());
			tabella3.add(tab);
		
		}
		affidoPdf.setTabella_3((new JRBeanCollectionDataSource(tabella3, false)));
		affidoPdf.setFamAffConoscDaMin(fillBoolean(affido.getFamigliaAffidataria().getConosciutaDalMinore() ));
		affidoPdf.setFamAffFuoriReg(fillBoolean(affido.getFamigliaAffidataria().getFuoriRegione()));
		
		for(CsPaiAffidoDominioDTO P : paiAffidoBean.getListaIdoneita()){
			if( affido.getFamigliaAffidataria().getCodiceIdoneita() != null && affido.getFamigliaAffidataria().getCodiceIdoneita().equals(P.getCodice()))	
				affidoPdf.setFamAffIdoneita(P.getDescrizione());
		}
		
		for(CsPaiAffidoDominioDTO P : paiAffidoBean.getListaBancaFamiglie()){
			if(affido.getFamigliaAffidataria().getCodiceBancaDatiFamiglie() != null && affido.getFamigliaAffidataria().getCodiceBancaDatiFamiglie().equals(P.getCodice()))
				affidoPdf.setFamAffInBancaDati(P.getDescrizione());
		}
		affidoPdf.setFamAffMotivaz(affido.getFamigliaAffidataria().getMotivazioni());
		for(PaiAffidoStatoEnum P : paiAffidoBean.getListaStati()){
			if(paiAffidoBean.getStatoAffido() == P.getValore())
				affidoPdf.setStatoAffido(P.getDescrizione());
		}
		for(CsPaiAffidoDominioDTO P : paiAffidoBean.getListaTipoAffido()){
			if(affido.getCodiceTipoAffido() != null && affido.getCodiceTipoAffido().equals(P.getCodice()))
				affidoPdf.setTipologiaAffido(P.getDescrizione());
			
		}
		for(CsPaiAffidoDominioDTO P : paiAffidoBean.getListaNaturaAccoglienza()){
			if(affido.getCodiceNaturaAccoglienza() != null && affido.getCodiceNaturaAccoglienza().equals(P.getCodice()))
				affidoPdf.setNaturaAccoglienza(P.getDescrizione());
		}
//		for(CsPaiAffidoDominioDTO P : paiAffidoBean.getListaTipoAccoglienza()){
//			if(affido.getCodiceTipoAccoglienza() != null && affido.getCodiceTipoAccoglienza().equals(P.getCodice()))
//				affidoPdf.setTipoAccoglienza(P.getDescrizione());
//		}
		//SISO-1172----
//		for(CsPaiAffidoDominioDTO P : paiAffidoBean.getListaSituazioniParticolari()){
//			if(affido.getCodiceFormeAffidamento() != null && affido.getCodiceFormeAffidamento().equals(P.getCodice()))
//				affidoPdf.setSituazioniPart(P.getDescrizione());
//		}
		for(CsPaiAffidoDominioDTO P : paiAffidoBean.getListaFormeAffidamentoReport()){
			if(affido.getCodiceFormeAffidamento() != null && affido.getCodiceFormeAffidamento().equals(P.getCodice()))
				affidoPdf.setFormeAffidamento(P.getDescrizione());
		}
	
//		affidoPdf.setMinoreStranieroNonAccom(affido.getMinoreStranieroNonAccompagnato() ? "SI" : "NO"); // SISO-1172
//		affidoPdf.setStessaCulturaMinoreFamig(affido.getMinoreStranieroAffidatriStessaCultura() ? "SI" : "NO" ); 
//		for(CsPaiAffidoDominioDTO P : paiAffidoBean.getListaFormeAffidamento()){
//			if(affido.getCodiceFormeAffidamento() != null && affido.getCodiceFormeAffidamento().equals(P.getCodice()))
//				affidoPdf.setSituazioniPart(P.getDescrizione());
//		}
//		affidoPdf.setMinoreStranieroNonAccom(affido.getMinoreStranieroNonAccompagnato() ? "SI" : "NO"); // SISO-1172
//		affidoPdf.setStessaCulturaMinoreFamig(affido.getMinoreStranieroAffidatriStessaCultura() ? "SI" : "NO" ); 
		//FINE SISO-1172
		affidoPdf.setPrezenzaRetiFam(fillBoolean(affido.getPresenzaRetiDiFamiglie()));
			
		
		for(CsPaiAffidoDominioDTO P : paiAffidoBean.getListaFrequenzaContatti()){
			if(affido.getCodiceFrequenzaContattiMinore() != null && affido.getCodiceFrequenzaContattiMinore().equals(P.getCodice()))
				affidoPdf.setFrequenzaContattiMinFamOrig(P.getDescrizione());
			
			if(affido.getCodiceFrequenzaContattiAff() != null && affido.getCodiceFrequenzaContattiAff().equals(P.getCodice()))
				affidoPdf.setFrequenzaContattiAff(P.getDescrizione());
		}
		affidoPdf.setModalitaRapportoNucleoOrigine(affido.getModalitaRapportoNucleoOrigine()  != null ?  affido.getModalitaRapportoNucleoOrigine(): "");
		
		affidoPdf.setModalitaRapportoOrigAff(affido.getModalitaRapportoOrigAff() != null ?  affido.getModalitaRapportoOrigAff(): "");
		
		affidoPdf.setModalitaRappEsperienzaOrig(affido.getModalitaRappEsperienzaOrig() != null ? affido.getModalitaRappEsperienzaOrig(): "");
		affidoPdf.setModalitaRappEsperienzaFamAff(affido.getModalitaRappEsperienzaFamAff() != null ? affido.getModalitaRappEsperienzaFamAff(): "");
		affidoPdf.setNoteGestioneSanita(affido.getNoteGestioneSanita() != null ? affido.getNoteGestioneSanita(): "");
				
		affidoPdf.setImpAltriSoggetti(affido.getImpegnoAltriSoggetti()!= null ? affido.getImpegnoAltriSoggetti() : null);
		affidoPdf.setImpFamigliaAffid(affido.getImpegnoFamigliaAffidataria()!= null ? affido.getImpegnoFamigliaAffidataria() : null);
		affidoPdf.setImpFamigliaOrig(affido.getImpegnoFamigliaOrigine()!= null ? affido.getImpegnoFamigliaOrigine() : null);
		affidoPdf.setImpMinore(affido.getImpegnoMinore()!= null ? affido.getImpegnoMinore() : null);
		affidoPdf.setImpServizioSoc(affido.getImpegnoServizioSociale()!= null ? affido.getImpegnoServizioSociale() : null);
		
		if(!affido.getSoggettiAffido().isEmpty()){
			List<TabellaPdfDTO> tabella1 = new ArrayList<TabellaPdfDTO>(); 
			for(CsPaiAffidoSoggettoDTO  af : affido.getSoggettiAffido()){
				TabellaPdfDTO tab = new TabellaPdfDTO();
				tab.setCognome(af.getCognome());
				tab.setNome(af.getNome());
				tab.setHeaderTab("Ruolo");
				tab.setSesso(null);
				tab.setRelazione(null);
				for(CsPaiAffidoDominioDTO P : paiAffidoBean.getListaRuoliSoggetto()){
					if(af.getCodiceRuolo().equals(P.getCodice()))
						tab.setCodFiscale(P.getDescrizione());
				}
				
				tabella1.add(tab);
			}
			affidoPdf.setTabella_1((new JRBeanCollectionDataSource(tabella1, false)));
		}
		
		List<TabellaPdfDTO> tabella2 = new ArrayList<TabellaPdfDTO>(); 
		for(CsPaiAffidoSoggFamigliaDTO fam : paiAffidoBean.getFamigliaOrig()){
			TabellaPdfDTO tab = new TabellaPdfDTO();
			tab.setCognome(fam.getCognome());
			tab.setNome(fam.getNome());
			tab.setHeaderTab("Cod. Fiscale");
			tab.setCodFiscale(fam.getCf());
			tab.setRelazione(fam.getParentela());
			tab.setSesso(fam.getSesso());
			tabella2.add(tab);
		}	
		affidoPdf.setTabella_2((new JRBeanCollectionDataSource(tabella2, false)));
		affidoPdf.setFamOrigAllontanam(fillBoolean(affido.getFamigliaOrigine().getMinoreAllontanato()));
		affidoPdf.setFamOrigFuoriReg(fillBoolean(affido.getFamigliaOrigine().getFuoriRegione()));
		affidoPdf.setFamOrigGenSconosc(fillBoolean(affido.getFamigliaOrigine().getGenitoriSconosciuti()));
		affidoPdf.setFamOrigContributoAlleSpese(fillBoolean(affido.getFamigliaOrigine().getContributoAlleSpese()));
		if (affido.getFamigliaOrigine().getContributoAlleSpese())
			affidoPdf.setFamOrigNoteContributoAlleSpese(affido.getFamigliaOrigine().getNoteContributoAlleSpese() != null ?  affido.getFamigliaOrigine().getNoteContributoAlleSpese(): "");
		
		for(CsPaiAffidoDominioDTO P : paiAffidoBean.getListaOrigineInterventi()){
			if(affido.getFamigliaOrigine().getCodiceIntervento() != null && affido.getFamigliaOrigine().getCodiceIntervento().equals(P.getCodice()))			
				affidoPdf.setFamOrigInterv(P.getDescrizione().toUpperCase());
		}
		
		return affidoPdf;
	}
	
	public ModelloPorPdfDTO fillModelloPOR(StampaFseDTO datiProgetto, boolean marche){
		ModelloPorPdfDTO pdf = new ModelloPorPdfDTO();
		try{
			//Dati beneficiario
			pdf.setCognome(datiProgetto.getCognome());
			pdf.setNome(datiProgetto.getNome());
			pdf.setCodFiscale(datiProgetto.getCodiceFiscale());
			pdf.setCittadinanza(datiProgetto.getCittadinanza());
			pdf.setDataNascita(datiProgetto.getDataNascita());
			pdf.setSesso(datiProgetto.getSesso());

			pdf.setTelefono(datiProgetto.getTelefono() != null ? datiProgetto.getTelefono() : "");
			pdf.setCellulare(datiProgetto.getCellulare() != null ? datiProgetto.getCellulare() : "");
			pdf.setEmail(datiProgetto.getEmail() != null ? datiProgetto.getEmail() : "");
			pdf.setLuogoNascita(datiProgetto.getLuogoNascita());
			
			pdf.setResid_cap(datiProgetto.getCapResidenza());
			pdf.setResid_sigla_prov(datiProgetto.getSiglaProvResidenza());
			pdf.setResidenza(datiProgetto.getComuneResidenza());
			pdf.setResid_via(datiProgetto.getViaResidenza());
	
			pdf.setDom_cap(datiProgetto.getDomicilioCap());
			pdf.setDom_sigla_prov(datiProgetto.getDomicilioSiglaProv());
			pdf.setDomicilio(datiProgetto.getDomicilioComune());
			pdf.setDom_via(datiProgetto.getViaDomicilio());
		
			//Dati Dichiarati
			String vulnerabilita = "-";
			if(datiProgetto.getComunicaVul()){
				if(marche){
					String scelto = datiProgetto.getIdVulnerabile();
					BaseDTO dto = new BaseDTO();
					fillEnte(dto);
					CsTbGVulnerabile gr = null;
					if(!StringUtils.isBlank(scelto) && Arrays.asList(GrVulnerabile.stampaAltro).contains(scelto)){
						dto.setObj(GrVulnerabile.ALTRO); //13 Altro tipo di vulnerabilità
						gr = confService.getGrVulnerabileById(dto);
					}else if(!StringUtils.isBlank(scelto) && Arrays.asList(GrVulnerabile.stampaVittimaViolenza).contains(scelto)){
						dto.setObj(GrVulnerabile.VITTIMA_VIOLENZA); //11 Vittima di violenza, di tratta e grave sfruttamento
						gr = confService.getGrVulnerabileById(dto);
					}else if(!StringUtils.isBlank(scelto) && Arrays.asList(GrVulnerabile.stampaMigrante).contains(scelto)) {
						dto.setObj(GrVulnerabile.MIGRANTE); //06 Migrante
						gr = confService.getGrVulnerabileById(dto);
					}
					vulnerabilita = gr!=null ? gr.getTooltip() : datiProgetto.getDescrizioneVulnerabile();
				}else
					vulnerabilita = datiProgetto.getDescrizioneVulnerabile();
			}else{
				vulnerabilita =  "L'utente non intende fornire informazioni sulla condizione di vulnerabilità";
			}
			pdf.setCond_vulnerabilita(vulnerabilita);
			pdf.setDurata_ric_lavoro(datiProgetto.getLavoroDurataRicerca());
			pdf.setCondizione_lavoro(datiProgetto.getCondLavoro());
			pdf.setTitolo_studio(datiProgetto.getTitoloStudio());
			pdf.setTitolo_studio_tooltip(datiProgetto.getTitoloStudioTooltip());
			
			//Dati progetto
			pdf.setNome_prog(datiProgetto.getFfProgettoDescrizione());
			pdf.setAttuatore_prog(datiProgetto.getSoggettoAttuatore());
			
			String codProgetto = datiProgetto.getCodProgetto();
			String attivita = datiProgetto.getCodAttivita();
			
			if(marche)
				pdf.setCod_prog(attivita);
			else {
				pdf.setCod_prog(codProgetto);
				pdf.setAttivita(attivita);
			}
			
			pdf.setDataSottoscrizione(datiProgetto.getDtSottoscrizione());
		
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		return pdf;
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
	
	private String fillData(Date data){
		return data!=null ? sdf.format(data) : "-";	
	}

}
