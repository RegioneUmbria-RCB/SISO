package it.webred.cs.csa.web.manbean.report.filler;

import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloBean;
import it.webred.cs.csa.web.manbean.report.dto.cartella.AnagraficaPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.cartella.DatiSocialiPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.cartella.DiarioPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.cartella.DisabilitaPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.cartella.InvaliditaPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.cartella.NotePdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.cartella.OperatorePdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.cartella.ParentePdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.cartella.TribunalePdfDTO;
import it.webred.cs.csa.web.manbean.scheda.SchedaBean;
import it.webred.cs.csa.web.manbean.scheda.anagrafica.AnagraficaBean;
import it.webred.cs.csa.web.manbean.scheda.disabilita.DatiDisabilitaComp;
import it.webred.cs.csa.web.manbean.scheda.invalidita.DatiInvaliditaComp;
import it.webred.cs.csa.web.manbean.scheda.operatori.OperatoriComp;
import it.webred.cs.csa.web.manbean.scheda.parenti.ParentiComp;
import it.webred.cs.csa.web.manbean.scheda.sociali.DatiSocialiComp;
import it.webred.cs.csa.web.manbean.scheda.tribunale.DatiTribunaleComp;
import it.webred.cs.data.model.CsACasoOpeTipoOpe;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsASoggettoCategoriaSoc;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsDColloquioBASIC;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsTbDisabTipologia;
import it.webred.cs.jsf.bean.ValiditaCompBaseBean;
import it.webred.cs.jsf.bean.colloquio.ColloquioRowBean;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.jsf.bean.ComuneBean;
import it.webred.jsf.bean.SessoBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportCartellaFiller extends CsUiCompBaseBean {
	private AccessTableCasoSessionBeanRemote casoService = (AccessTableCasoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableCasoSessionBean");
	private AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");
	private AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	private CsASoggettoLAZY soggetto;
	private SchedaBean schedaBean;
	private FascicoloBean fascicoloBean;
	
	public AnagraficaPdfDTO fillAnagrafica() {
		
		AnagraficaPdfDTO ana = new AnagraficaPdfDTO();
		AnagraficaBean anaBean = schedaBean.getAnagraficaBean();
		ana.setCognome(anaBean.getDatiAnaBean().getCognome());
		ana.setNome(anaBean.getDatiAnaBean().getNome());
		if(anaBean.getDatiAnaBean().getDataNascita() != null)
			ana.setDataNascita(sdf.format(anaBean.getDatiAnaBean().getDataNascita()));
		if(anaBean.getComuneNazioneNascitaMan().getComuneNascitaMan().getComune() != null) {
			ComuneBean comune = anaBean.getComuneNazioneNascitaMan().getComuneNascitaMan().getComune();
			ana.setLuogoNascita(comune.getDenominazione() + " (" + comune.getSiglaProv() + ")");
		} else if(anaBean.getComuneNazioneNascitaMan().getNazioneNascitaMan().getNazione() != null) {
			AmTabNazioni nazione = anaBean.getComuneNazioneNascitaMan().getNazioneNascitaMan().getNazione();
			ana.setLuogoNascita(nazione.getNazione());
		}
		
		SessoBean sb = anaBean.getDatiAnaBean().getDatiSesso();
		ana.setSesso(sb.getSesso());
		
		ana.setCodFiscale(anaBean.getDatiAnaBean().getCodiceFiscale());
		
		String cittadinanza = anaBean.getDatiAnaBean().getCittadinanza();
		if(anaBean.getDatiAnaBean().getCittadinanzaAcq()!=null && anaBean.getDatiAnaBean().getCittadinanzaAcq()>0){
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(anaBean.getDatiAnaBean().getCittadinanzaAcq());
			cittadinanza+= " ("+confService.getCittadinanzaAcqById(dto).getDescrizione()+")";
		}
		ana.setCittadinanza(cittadinanza);
		ana.setCittadinanza2(anaBean.getDatiAnaBean().getCittadinanza2());
		
		if(anaBean.getResidenzaCsaMan().getIndirizzoResidenzaAttivo() != null) {
			String res = anaBean.getResidenzaCsaMan().getIndirizzoResidenzaAttivo().getCsAAnaIndirizzo().getIndirizzo();
			res += ", " + anaBean.getResidenzaCsaMan().getCivicoFromNumeroAltro(anaBean.getResidenzaCsaMan().getIndirizzoResidenzaAttivo().getCsAAnaIndirizzo().getCivicoNumero(), anaBean.getResidenzaCsaMan().getIndirizzoResidenzaAttivo().getCsAAnaIndirizzo().getCivicoAltro());
			res += " - " + anaBean.getResidenzaCsaMan().getIndirizzoResidenzaAttivo().getCsAAnaIndirizzo().getComDes();
			res += " (" + anaBean.getResidenzaCsaMan().getIndirizzoResidenzaAttivo().getCsAAnaIndirizzo().getStatoDes() + ")";
			ana.setResidenza(res);
		}
		if(!anaBean.getStatoCivileGestBean().getLstComponentsActive().isEmpty())
			ana.setStatoCivile(anaBean.getStatoCivileGestBean().getLstComponentsActive().get(0).getDescrizione());
		if(!anaBean.getStatusGestBean().getLstComponentsActive().isEmpty())
			ana.setStatus(anaBean.getStatusGestBean().getLstComponentsActive().get(0).getDescrizione());
		if(!anaBean.getMediciGestBean().getLstComponentsActive().isEmpty())
			ana.setMedico(anaBean.getMediciGestBean().getLstComponentsActive().get(0).getDescrizione());
		ana.setTelefono(anaBean.getDatiAnaBean().getTelefono());
		ana.setCellulare(anaBean.getDatiAnaBean().getCellulare());
		ana.setEmail(anaBean.getDatiAnaBean().getEmail());
		
		try {
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			
			//cat sociale
			dto.setObj(soggetto.getAnagraficaId());
			List<CsASoggettoCategoriaSoc> listaCatSociali = soggettoService.getSoggettoCategorieBySoggetto(dto);
			String catSociali = "";
			for(CsASoggettoCategoriaSoc soggCatSoc: listaCatSociali) {
				catSociali += ", " + soggCatSoc.getCsCCategoriaSociale().getTooltip();
			}
			ana.setCatSociale(catSociali.substring(2));
			
			//responsabile
			dto.setObj(soggetto.getCsACaso().getId());
			CsACasoOpeTipoOpe casoOpTipoOp = casoService.findCasoOpeResponsabile(dto);
			if(casoOpTipoOp != null) {
				CsOOperatore op = casoOpTipoOp.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore().getCsOOperatore();
				ana.setAssSociale(this.getDenominazioneOperatore(op));
				
				Date dataPIC;
				if(casoOpTipoOp.getDtMod() != null)
					dataPIC = casoOpTipoOp.getDtMod();
				else dataPIC = casoOpTipoOp.getDtIns();
				ana.setDataPIC(sdf.format(dataPIC));
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return ana;
		
	}
	
	public List<ParentePdfDTO> fillParenti() {
		
		List<ParentePdfDTO> lista = new ArrayList<ParentePdfDTO>();
		
		if(schedaBean.getParentiBean().getListaComponenti() != null && !schedaBean.getParentiBean().getListaComponenti().isEmpty()){
			ParentiComp parentiComp = (ParentiComp) schedaBean.getParentiBean().getListaComponenti().get(0);
			
			List<CsAComponente> listaParenti = new ArrayList<CsAComponente>();
			if(parentiComp.getLstParenti() != null)
				listaParenti.addAll(parentiComp.getLstParenti());
			if(parentiComp.getLstConoscenti() != null)
				listaParenti.addAll(parentiComp.getLstConoscenti());
			
			for(CsAComponente csPar: listaParenti) {
				ParentePdfDTO par = new ParentePdfDTO();
				par.setCognomeP(csPar.getCsAAnagrafica().getCognome());
				par.setNomeP(csPar.getCsAAnagrafica().getNome());
				if(csPar.getCsAAnagrafica().getDataNascita() != null)
					par.setDataNascitaP(sdf.format(csPar.getCsAAnagrafica().getDataNascita()));
				if(csPar.getCsAAnagrafica().getComuneNascitaDes() != null) 
					par.setLuogoNascitaP(csPar.getCsAAnagrafica().getComuneNascitaDes() + " (" + csPar.getCsAAnagrafica().getProvNascitaCod() + ")");
				else par.setLuogoNascitaP(csPar.getCsAAnagrafica().getStatoNascitaDes());
				par.setCodFiscaleP(csPar.getCsAAnagrafica().getCf());
				par.setCittadinanzaP(csPar.getCsAAnagrafica().getCittadinanza());
				par.setSessoP(csPar.getCsAAnagrafica().getSesso());
				String residenza = csPar.getIndirizzoRes() + ", " + csPar.getNumCivRes();
				if(csPar.getComResDes() != null)
					residenza += " - " + csPar.getComResDes() + " (" + csPar.getProvResCod() + ")";
				else residenza += " - " + csPar.getComAltroDes();
				par.setResidenzaP(residenza);
				par.setTelefonoP(csPar.getCsAAnagrafica().getTel());
				par.setCellulareP(csPar.getCsAAnagrafica().getCell());
				par.setEmailP(csPar.getCsAAnagrafica().getEmail());
				par.setParentelaP(csPar.getCsTbTipoRapportoCon().getDescrizione());
				if(csPar.getCsAAnagrafica().getDataDecesso() != null)
					par.setDecessoP("SI (" + sdf.format(csPar.getCsAAnagrafica().getDataDecesso()) + ")");
				else par.setDecessoP("NO");
					
				lista.add(par);
			}
		}
		
		return lista;
	}
	
	public DatiSocialiPdfDTO fillDatiSociali() {

		DatiSocialiPdfDTO ds = new DatiSocialiPdfDTO();
		if(schedaBean.getDatiSocialiBean().getListaComponenti() != null && !schedaBean.getDatiSocialiBean().getListaComponenti().isEmpty()){
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			
			DatiSocialiComp dsComp = (DatiSocialiComp) schedaBean.getDatiSocialiBean().getListaComponenti().get(0);
			
			if(dsComp.getFamConviventiMan() != null) {
				dto.setObj(dsComp.getFamConviventiMan().getTipologiaFamiliareId());
				ds.setTipologiaFamiliare(confService.getTipologiaFamiliareById(dto).getDescrizione());
			}
			//ds.setMinoriPresenti(dsComp.getnMinori().toString());
			
			if(dsComp.getIdProblematica() != null && dsComp.getIdProblematica().intValue() != 0) {
				dto.setObj(dsComp.getIdProblematica().longValue());
				ds.setProblematica(confService.getProblematicaById(dto).getDescrizione());
			}
			if(dsComp.getIdProblematicaNucleo() != null && dsComp.getIdProblematicaNucleo().intValue() != 0) {
				dto.setObj(dsComp.getIdProblematicaNucleo().longValue());
				ds.setProblematicaNucleo(confService.getMotivoSegnalazioneById(dto).getDescrizione());
			}
			if(dsComp.getFormLavoroMan().getIdTitoloStudio() != null && dsComp.getFormLavoroMan().getIdTitoloStudio().intValue() != 0) {
				dto.setObj(dsComp.getFormLavoroMan().getIdTitoloStudio().longValue());
				ds.setTitoloStudio(confService.getTitoloStudioById(dto).getDescrizione());
			}		
		}
		
		return ds;
		
	}
	
	public InvaliditaPdfDTO fillInvalidita() {
		
		InvaliditaPdfDTO inv = new InvaliditaPdfDTO();
		if(schedaBean.getDatiInvaliditaBean().getListaComponenti() != null && !schedaBean.getDatiInvaliditaBean().getListaComponenti().isEmpty()){
			
			DatiInvaliditaComp invComp = (DatiInvaliditaComp) schedaBean.getDatiInvaliditaBean().getListaComponenti().get(0);
			
			if(invComp.isInvaliditaInCorso())
				inv.setInvInCorso("SI");
			else inv.setInvInCorso("NO");
			if(invComp.getDataInvalidita() != null)
				inv.setInvInizio(sdf.format(invComp.getDataInvalidita()));
			if(invComp.getDataInvaliditaScadenza() != null)
				inv.setInvFine(sdf.format(invComp.getDataInvaliditaScadenza()));
			
			if(invComp.isCertificazioneH())
				inv.setCertH("SI");
			else inv.setCertH("NO");
			if(invComp.getDataCertificazione() != null)
				inv.setCertHInizio(sdf.format(invComp.getDataCertificazione()));
			if(invComp.getDataCertificazioneScadenza() != null)
				inv.setCertHFine(sdf.format(invComp.getDataCertificazioneScadenza()));
			
			if(invComp.getInvaliditaPerc() != null)
				inv.setInvPerc(invComp.getInvaliditaPerc().toString() + " %");
			if(invComp.isAccompagnamento())
				inv.setAccompagnamento("SI");
			else inv.setAccompagnamento("NO");
			if(invComp.isLegge104())
				inv.setLegge104("SI");
			else inv.setLegge104("NO");
		}
		
		return inv;
		
	}
	
	public DisabilitaPdfDTO fillDisabilita() {
	
		DisabilitaPdfDTO dd = new DisabilitaPdfDTO();		
		if(schedaBean.getDatiDisabilitaBean().getListaComponenti() != null && !schedaBean.getDatiDisabilitaBean().getListaComponenti().isEmpty()){
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			
			DatiDisabilitaComp ddComp = (DatiDisabilitaComp) schedaBean.getDatiDisabilitaBean().getListaComponenti().get(0);
			
			if(ddComp.getIdGravita() != null && ddComp.getIdGravita().intValue() != 0) {
				dto.setObj(ddComp.getIdGravita().longValue());
				dd.setGravitaDis(confService.getDisabGravitaById(dto).getDescrizione());
			}
			
			if(ddComp.getIdTipologia() != null && ddComp.getIdTipologia().intValue() != 0) {
				dto.setObj(ddComp.getIdTipologia().longValue());
				CsTbDisabTipologia csTipo = confService.getDisabTipologiaById(dto);
				dd.setTipologiaDis(csTipo.getDescrizione() + ": " + csTipo.getTooltip());
			}
		}
		
		return dd;
		
	}
	
	public TribunalePdfDTO fillTribunale() {
		
		TribunalePdfDTO tri = new TribunalePdfDTO();		
		if(schedaBean.getDatiTribunaleBean().getListaComponenti() != null && !schedaBean.getDatiTribunaleBean().getListaComponenti().isEmpty()){
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			
			DatiTribunaleComp triComp = (DatiTribunaleComp) schedaBean.getDatiTribunaleBean().getListaComponenti().get(0);
			
			if(triComp.isTMCivile())
				tri.setTmCivile("SI");
			else tri.setTmCivile("NO");
			if(triComp.isTMAmministrativo())
				tri.setTmAmm("SI");
			else tri.setTmAmm("NO");
			if(triComp.isPenaleMinorile())
				tri.setPenaleMin("SI");
			else tri.setPenaleMin("NO");
			if(triComp.isTO())
				tri.setTo("SI");
			else tri.setTo("NO");
			if(triComp.isNIS())
				tri.setNis("SI");
			else tri.setNis("NO");
			if(triComp.isProcuraMinorile())
				tri.setProcuraMin("SI");
			else tri.setProcuraMin("NO");
			
			tri.setNumProtocollo(triComp.getNumeroProtocollo());
			
			if(triComp.getIdMacroSegnalazione() != null && triComp.getIdMacroSegnalazione().intValue() != 0) {
				dto.setObj(triComp.getIdMacroSegnalazione().longValue());
				tri.setMacroSegnal(confService.getMacroSegnalazioneById(dto).getDescrizione());
			}
			
			if(triComp.getIdMicroSegnalazione() != null && triComp.getIdMicroSegnalazione().intValue() != 0) {
				dto.setObj(triComp.getIdMicroSegnalazione().longValue());
				tri.setMicroSegnal(confService.getMicroSegnalazioneById(dto).getDescrizione());
			}
			
			if(triComp.getIdMotivoSegnalazione() != null && triComp.getIdMotivoSegnalazione().intValue() != 0) {
				dto.setObj(triComp.getIdMotivoSegnalazione().longValue());
				tri.setMotivoSegnal(confService.getMotivoSegnalazioneById(dto).getDescrizione());
			}	
		}
		
		return tri;
		
	}
	
	public List<OperatorePdfDTO> fillOperatori() {
		
		List<OperatorePdfDTO> lista = new ArrayList<OperatorePdfDTO>();
		
		if(schedaBean.getOperatoriBean().getLstComponents() != null && !schedaBean.getOperatoriBean().getLstComponents().isEmpty()){
			for(ValiditaCompBaseBean valComp: schedaBean.getOperatoriBean().getLstComponents()) {
				OperatoriComp opComp = (OperatoriComp) valComp;
				
				if(opComp.isAttivo()) {
					OperatorePdfDTO op = new OperatorePdfDTO();
					if(opComp.getResponsabile() != null && opComp.getResponsabile())
						op.setResponsabile("SI");
					op.setTipoOp(opComp.getTipo());
					op.setDenominazioneOp(opComp.getDescrizione());
					op.setDallaDataOp(sdf.format(opComp.getDataInizio()));
					
					lista.add(op);
				}
			}
		}
		
		return lista;
	}
	
	
public List<DiarioPdfDTO> fillDiario() {
	
	List<DiarioPdfDTO> lista = new ArrayList<DiarioPdfDTO>();
		
		if(fascicoloBean.getColloquioBean().getListaColloquios() != null && !fascicoloBean.getColloquioBean().getListaColloquios().isEmpty()){
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			
			
			for(ColloquioRowBean diariComp: fascicoloBean.getColloquioBean().getListaColloquios()){
				    CsDColloquioBASIC colloquio = diariComp.getColloquioBASIC();
					DiarioPdfDTO op = new DiarioPdfDTO();
					Date dataColloquio = colloquio.getCsDDiario().getDtAmministrativa();
					op.setDataColloquio(dataColloquio!=null ? sdf.format(dataColloquio) : "");
					
					if(colloquio.getCsCDiarioDove()!= null){
					 op.setDove(colloquio.getCsCDiarioDove().getDescrizione());
					}
					
					String conChi = colloquio.getCsCDiarioConchi().getDescrizione();
					if(colloquio.getCsCDiarioConchi().getId()==9999 && colloquio.getDiarioConChiAltro()!=null)
						conChi+= ": "+colloquio.getDiarioConChiAltro();
					op.setConChi(conChi);
					op.setTipoColloquio(colloquio.getCsCTipoColloquio().getDescrizione());
					if(colloquio.getRiservato().equals(true))
						op.setRiservato("SI");
					else
						op.setRiservato("NO");
						
					String campoTesto = colloquio.getTestoDiario();
					op.setTestoDiario(campoTesto);
					lista.add(op);
			}
			}
		
		
		return lista;
	}
		
	
	public NotePdfDTO fillNote() {
		
		NotePdfDTO note = new NotePdfDTO();
		note.setNote(schedaBean.getNoteBean().getNote());
		
		return note;
		
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
	
}
