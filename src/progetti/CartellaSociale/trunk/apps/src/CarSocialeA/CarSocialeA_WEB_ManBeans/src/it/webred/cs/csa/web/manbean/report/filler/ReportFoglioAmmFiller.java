package it.webred.cs.csa.web.manbean.report.filler;

import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.DatiOperatoreDTO;
import it.webred.cs.csa.utils.bean.report.dto.BasePdfDTO;
import it.webred.cs.csa.web.manbean.fascicolo.FglInterventoBean;
import it.webred.cs.csa.web.manbean.report.ReportBaseBean;
import it.webred.cs.csa.web.manbean.report.dto.foglioAmm.FoglioAmmPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.foglioAmm.custom.AffidoFamPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.foglioAmm.custom.AssDomiciliarePdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.foglioAmm.custom.BuonoSocialePdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.foglioAmm.custom.CentroDiurnoIntPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.foglioAmm.custom.ContrEconomiciPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.foglioAmm.custom.PastiDomiciliariPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.foglioAmm.custom.ResAdultiPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.foglioAmm.custom.ResMinoriPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.foglioAmm.custom.SchedaUOLPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.foglioAmm.custom.SemiResMinoriPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.foglioAmm.custom.VoucherPdfDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.FoglioAmministrativo;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCTipoInterventoCustom;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDRelazione;
import it.webred.cs.data.model.CsFlgIntervento;
import it.webred.cs.data.model.CsIAdmAdh;
import it.webred.cs.data.model.CsIAffidoFam;
import it.webred.cs.data.model.CsIBuonoSoc;
import it.webred.cs.data.model.CsICentrod;
import it.webred.cs.data.model.CsIContrEco;
import it.webred.cs.data.model.CsIIntervento;
import it.webred.cs.data.model.CsIPasti;
import it.webred.cs.data.model.CsIResiAdulti;
import it.webred.cs.data.model.CsIResiMinore;
import it.webred.cs.data.model.CsISchedaLavoro;
import it.webred.cs.data.model.CsISemiResiMin;
import it.webred.cs.data.model.CsIVouchersad;
import it.webred.ct.config.model.AmComune;

import java.text.NumberFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.faces.bean.ManagedProperty;
import javax.faces.model.SelectItem;

public class ReportFoglioAmmFiller extends ReportBaseBean {

	private AccessTableInterventoSessionBeanRemote interventoService = (AccessTableInterventoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableInterventoSessionBean");
	private AccessTableDiarioSessionBeanRemote diarioService = (AccessTableDiarioSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableDiarioSessionBean");
	private AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
	protected AccessTableCasoSessionBeanRemote casoService = (AccessTableCasoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableCasoSessionBean");
	
	private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("it", "IT"));
	
	@ManagedProperty("#{fglInterventoBean}")
	private FglInterventoBean fglInterventoBean = new FglInterventoBean();
	
	private Long diarioId;
	private CsFlgIntervento flgIn;
	private CsIIntervento in;
	private long idTipoIntervento;
	private long idTipoInterventoCustom;

	
	public ReportFoglioAmmFiller() {
		fglInterventoBean = (FglInterventoBean)getBeanReference("fglInterventoBean");
	}
	public void getInterventoData() {
		
	}
	
	public FoglioAmmPdfDTO fillIntervento(boolean stampIntCustom) throws Exception {
		
		FoglioAmmPdfDTO pdfDto = new FoglioAmmPdfDTO();
		
		BaseDTO baseDto = new BaseDTO();
		fillEnte(baseDto);
		
		//intervento
		pdfDto.setTipoIntervento(in.getCsRelSettCsocTipoInter().getCsRelCatsocTipoInter().getCsCTipoIntervento().getDescrizione());
		String flgMod = in.getFlagPrimaerRinnovo();
		if("P".equals(flgMod))
			pdfDto.setModalita("Prima erogazione");
		if("R".equals(flgMod))
			pdfDto.setModalita("Rinnovo");
		
		pdfDto.setUnaTantum((in.getFlagUnatantum() != null && in.getFlagUnatantum()) ? "SI" : "NO");
		pdfDto.setCatSociale(in.getCsRelSettCsocTipoInter().getCsRelSettoreCatsoc().getCsCCategoriaSociale().getTooltip());
		pdfDto.setSettRichiedente(in.getCsRelSettCsocTipoInter().getCsRelSettoreCatsoc().getCsOSettore().getNome());
		pdfDto.setStampIntCustom(stampIntCustom);
		
		String dataInizio = "";
		if(in.getInizioDal() != null)
			dataInizio = ddMMyyyy.format(in.getInizioDal());
		if(in.getInizioAl() != null)
			dataInizio += " al " + ddMMyyyy.format(in.getInizioAl());
		pdfDto.setDataInizio(dataInizio);
		String dataFine = "";
		if(in.getFineDal() != null)
			dataFine = ddMMyyyy.format(in.getFineDal());
		if(in.getFineAl() != null)
			dataFine += " al " + ddMMyyyy.format(in.getFineAl()); 
		pdfDto.setDataFine(dataFine);
		
		pdfDto.setNote(in.getNote()!=null ? in.getNote() : "");
		
		//foglioAmm
		for(SelectItem item: fglInterventoBean.getLstTipoAttivita()) {
			if(item.getValue() != null && item.getValue().equals(flgIn.getFlagAttSospC()))
				pdfDto.setTipoFoglio(item.getLabel());
		}
		if(flgIn.getCsDDiario().getDtAmministrativa() != null)
			pdfDto.setDataAmministrativa(ddMMyyyy.format(flgIn.getCsDDiario().getDtAmministrativa()));
		
		Date dataDa = null;
		Date dataA = null;
		if(FoglioAmministrativo.STATO.ATTIVAZIONE.getCodice().equals(flgIn.getFlagAttSospC())) {
			pdfDto.setDettOperazione("Tipo attivazione");
			pdfDto.setDescrDettOperazione(flgIn.getTipoAttivazione());
			dataDa = flgIn.getCsDDiario().getDtAttivazioneDa();
			dataA = flgIn.getCsDDiario().getDtAttivazioneA();
		}
		if(FoglioAmministrativo.STATO.SOSPENSIONE.getCodice().equals(flgIn.getFlagAttSospC())) {
			pdfDto.setDettOperazione("Tipo sospensione");
			pdfDto.setDescrDettOperazione(flgIn.getDescrSospensione());
			dataDa = flgIn.getCsDDiario().getDtSospensioneDa();
			dataA = flgIn.getCsDDiario().getDtSospensioneA();
		}
		if(FoglioAmministrativo.STATO.CHIUSURA.getCodice().equals(flgIn.getFlagAttSospC())) {
			pdfDto.setDettOperazione("Motivo chiusura");
			if(flgIn.getCsTbMotivoChiusuraInt() != null)
				pdfDto.setDescrDettOperazione(flgIn.getCsTbMotivoChiusuraInt().getDescrizione());
			dataDa = flgIn.getCsDDiario().getDtChiusuraDa();
			dataA = flgIn.getCsDDiario().getDtChiusuraA();
		}
		
		if(dataDa != null)
			pdfDto.setAttDal(ddMMyyyy.format(dataDa));
		if(dataA != null)
			pdfDto.setAttAl(ddMMyyyy.format(dataA));
		if(flgIn.getFlagRespinto() != null && flgIn.getFlagRespinto())
			pdfDto.setRespinto("SI");
		else pdfDto.setRespinto("NO");
		pdfDto.setMotivazione(flgIn.getMotivoRespinto());
		
		//sogg riferimento
		if(in.getComponenteId() != null) {
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(in.getComponenteId());
			CsAComponente comp = schedaService.findComponenteById(dto);
			if(comp!=null){
				pdfDto.setRifDenominazione(comp.getCsAAnagrafica().getCognome() + " " + comp.getCsAAnagrafica().getNome() + " (" + comp.getCsTbTipoRapportoCon().getDescrizione() + ")");
				pdfDto.setRifIndirizzo(comp.getIndirizzoRes() + " " + comp.getNumCivRes());
				if(comp.getComResDes() != null)
					pdfDto.setRifLuogo(comp.getComResDes() + " (" + comp.getProvResCod() + ")");
				else pdfDto.setRifLuogo(comp.getComAltroDes());
				String tel = "";
				if(comp.getCsAAnagrafica().getTel() != null)
					tel += comp.getCsAAnagrafica().getTel();
				if(tel != null && comp.getCsAAnagrafica().getCell() != null)
					tel += " - ";
				if(comp.getCsAAnagrafica().getCell() != null)
					tel += comp.getCsAAnagrafica().getCell();
				pdfDto.setRifTelefono(tel);
			}
		} else {
			pdfDto.setRifDenominazione(in.getCompDenominazione());
			pdfDto.setRifIndirizzo(in.getCompIndirizzo());
			pdfDto.setRifLuogo(in.getCompCitta());
			pdfDto.setRifTelefono(in.getCompTel());
		}
		
		//valorizzare relazione_diario
		List<CsDDiario> lstDiario = new LinkedList<CsDDiario>(flgIn.getCsDDiario().getCsDDiariFiglio());
		if(lstDiario!=null && lstDiario.size()>0) {
			for(CsDDiario d : lstDiario) {
				if( d.getCsTbTipoDiario().getId().equals(DataModelCostanti.TipoDiario.RELAZIONE_ID)){
					long idRelazione = d.getId();
					baseDto.setObj(idRelazione);
					CsDRelazione csRelazione = diarioService.findRelazioneLazyById(baseDto);
					String descrizione = ddMMyyyy.format(csRelazione.getCsDDiario().getDtAmministrativa());
					descrizione+= csRelazione.getProposta()!=null ? " - "+csRelazione.getProposta() : "";
					pdfDto.setRelazione(descrizione);
				}
			}
		}
		
		//campi finali
		AmComune am = comuneService.getComune(baseDto.getEnteId());
		String nomeEnte = am.getDescrizione();
		nomeEnte = nomeEnte.substring(0, 1).toUpperCase() + nomeEnte.substring(1).toLowerCase();
		pdfDto.setComune(nomeEnte);
		pdfDto.setDataOdierna(ddMMyyyy.format(new Date()));
		baseDto.setObj(soggetto.getCsACaso().getId());
		DatiOperatoreDTO responsabile = casoService.findResponsabileCaso(baseDto);
		pdfDto.setAssSociale(responsabile!=null ? responsabile.getDenominazione() : null);
		
		this.fillReportPieDiPagina(pdfDto);
		
		return pdfDto;
		
	}
	
	public BasePdfDTO fillTipoIntervento() throws Exception {
	
		if(1 == idTipoInterventoCustom )
			return fillVoucher();
		if(2 == idTipoInterventoCustom)
			return fillContrEconomico();
		if(3 == idTipoInterventoCustom)
			return fillCentroDiurnoInt();
		if(4 == idTipoInterventoCustom)
			return fillPastiDomiciliari();
		if(5 == idTipoInterventoCustom)
			return fillBuonoSociale();
		if(6 == idTipoInterventoCustom)
			return fillResAdulti();
		if(7 == idTipoInterventoCustom)
			return fillResMinori();
		if(8 == idTipoInterventoCustom)
			return fillAffidoFam();
		if(9 == idTipoInterventoCustom)
			return fillAssDomiciliare();
		if(10 == idTipoInterventoCustom)
			return fillSemiResMinori();
		if(11 == idTipoInterventoCustom)
			return fillSchedaUOL();
		
		return (new BasePdfDTO());
		
	}
	
	public VoucherPdfDTO fillVoucher() throws Exception {
	
		VoucherPdfDTO pdfDto = new VoucherPdfDTO();

		BaseDTO baseDto = new BaseDTO();
		baseDto.setObj(in.getId());
		fillEnte(baseDto);
		CsIVouchersad cs = interventoService.findVouherSadById(baseDto);
		
		if(cs.getAumentoOre() != null)
			pdfDto.setAumentoOre(cs.getAumentoOre().toString());
		if(cs.getDiminuzioneOre() != null)
			pdfDto.setDiminuzioneOre(cs.getDiminuzioneOre().toString());
		if(cs.getAumDimOreDal() != null)
			pdfDto.setDal(ddMMyyyy.format(cs.getAumDimOreDal()));
		if(cs.getTipoOrePreviste() != null)
			pdfDto.setTipoOre("Ore previste " + cs.getTipoOrePreviste().toLowerCase());
		if(cs.getOrePreviste() != null)
			pdfDto.setOrePreviste(cs.getOrePreviste().toString());
		if(cs.getNumAccessi() != null)
			pdfDto.setnAccessi(cs.getNumAccessi().toString());
		if(cs.getContributioUtente() != null)
			pdfDto.setContributoUtente(currencyFormat.format(cs.getContributioUtente()));
		
		return pdfDto;
		
	}
	
	public ContrEconomiciPdfDTO fillContrEconomico() throws Exception {
		
		ContrEconomiciPdfDTO pdfDto = new ContrEconomiciPdfDTO();

		BaseDTO baseDto = new BaseDTO();
		baseDto.setObj(in.getId());
		fillEnte(baseDto);
		CsIContrEco cs = interventoService.findContributoEconomicoById(baseDto);
		
		if(cs.getValRichiesto() != null)
			pdfDto.setContrRichiesto(currencyFormat.format(cs.getValRichiesto()));
		pdfDto.setPerErogazione(cs.getTipoPeriodoErogazione());
		if(cs.getRichSeStesso() != null && cs.getRichSeStesso().intValue() == 1)
			pdfDto.setRichiestoPer("se stesso");
		else pdfDto.setRichiestoPer(cs.getRichAltroDesc());
		pdfDto.setTipoRiscossione(cs.getTipoRiscossione());
		if("Accredito".equals(cs.getTipoRiscossione())) {
			pdfDto.setAccreditoA(cs.getAccreditoA());
			pdfDto.setIban(cs.getIban());
		}
		if("Delegato".equals(cs.getTipoRiscossione())) {
			if(cs.getCsAComponente() != null) {
				CsAComponente comp = cs.getCsAComponente();
				pdfDto.setDelDenominazione(comp.getCsAAnagrafica().getCognome() + " " + comp.getCsAAnagrafica().getNome() + " (" + comp.getCsTbTipoRapportoCon().getDescrizione() + ")");
				pdfDto.setDelIndirizzo(comp.getIndirizzoRes() + " " + comp.getNumCivRes());
				if(comp.getComResDes() != null)
					pdfDto.setDelLuogo(comp.getComResDes() + " (" + comp.getProvResCod() + ")");
				else pdfDto.setDelLuogo(comp.getComAltroDes());
				String tel = "";
				if(comp.getCsAAnagrafica().getTel() != null)
					tel += comp.getCsAAnagrafica().getTel();
				if(tel != null && comp.getCsAAnagrafica().getCell() != null)
					tel += " - ";
				if(comp.getCsAAnagrafica().getCell() != null)
					tel += comp.getCsAAnagrafica().getCell();
				pdfDto.setDelTelefono(tel);
			} else {
				pdfDto.setDelDenominazione(cs.getCompDenominazione());
				pdfDto.setDelIndirizzo(cs.getCompIndirizzo());
				pdfDto.setDelLuogo(cs.getCompCitta());
				pdfDto.setDelTelefono(cs.getCompTel());
			}
		}
		
		pdfDto.setShowCasellaContributo(true);
		
		return pdfDto;
		
	}
	
	public CentroDiurnoIntPdfDTO fillCentroDiurnoInt() throws Exception {
		
		CentroDiurnoIntPdfDTO pdfDto = new CentroDiurnoIntPdfDTO();
		
		BaseDTO baseDto = new BaseDTO();
		baseDto.setObj(in.getId());
		fillEnte(baseDto);
		CsICentrod cs = interventoService.findCentroDiurnoById(baseDto);
		
		pdfDto.setQuotaUtente(cs.getTipoQuotaUtente());
		if(cs.getContributioUtente() != null)
			pdfDto.setContributoUtente(currencyFormat.format(cs.getContributioUtente()));
		if(cs.getDietaSpeciale() != null && cs.getDietaSpeciale().intValue() == 1)
			pdfDto.setDietaSpeciale("SI");
		else pdfDto.setDietaSpeciale("NO");
		if(cs.getFlagNecessTrasporto() != null && cs.getFlagNecessTrasporto().intValue() == 1)
			pdfDto.setTrasporto("SI");
		else pdfDto.setTrasporto("NO");
		
		return pdfDto;
		
	}
	
	public PastiDomiciliariPdfDTO fillPastiDomiciliari() throws Exception {
		
		PastiDomiciliariPdfDTO pdfDto = new PastiDomiciliariPdfDTO();
		
		BaseDTO baseDto = new BaseDTO();
		baseDto.setObj(in.getId());
		fillEnte(baseDto);
		CsIPasti cs = interventoService.findPastiById(baseDto);
		
		pdfDto.setQuotaUtente(cs.getTipoQuotaUtente());
		if(cs.getContributioUtente() != null)
			pdfDto.setContributoUtente(currencyFormat.format(cs.getContributioUtente()));
		if(cs.getDietaSpeciale() != null && cs.getDietaSpeciale().intValue() == 1)
			pdfDto.setDietaSpeciale("SI");
		else pdfDto.setDietaSpeciale("NO");
		
		return pdfDto;
		
	}
	
	public BuonoSocialePdfDTO fillBuonoSociale() throws Exception {
		
		BuonoSocialePdfDTO pdfDto = new BuonoSocialePdfDTO();

		BaseDTO baseDto = new BaseDTO();
		baseDto.setObj(in.getId());
		fillEnte(baseDto);
		CsIBuonoSoc cs = interventoService.findBuonoSocialeById(baseDto);
		
		pdfDto.setGestione(cs.getTipoGestione());
		pdfDto.setDeroghe(cs.getTipoDeroghe());
		pdfDto.setPerErogazione(cs.getTipoPeriodoErogazione());
		if(cs.getRichSeStesso() != null && cs.getRichSeStesso().intValue() == 1)
			pdfDto.setRichiestoPer("se stesso");
		else pdfDto.setRichiestoPer(cs.getRichAltroDesc());
		pdfDto.setTipoRiscossione(cs.getTipoRiscossione());
		if("Accredito".equals(cs.getTipoRiscossione())) {
			pdfDto.setAccreditoA(cs.getAccreditoA());
			pdfDto.setIban(cs.getIban());
		}
		if("Delegato".equals(cs.getTipoRiscossione())) {
			if(cs.getCsAComponente() != null) {
				CsAComponente comp = cs.getCsAComponente();
				pdfDto.setDelDenominazione(comp.getCsAAnagrafica().getCognome() + " " + comp.getCsAAnagrafica().getNome() + " (" + comp.getCsTbTipoRapportoCon().getDescrizione() + ")");
				pdfDto.setDelIndirizzo(comp.getIndirizzoRes() + " " + comp.getNumCivRes());
				if(comp.getComResDes() != null)
					pdfDto.setDelLuogo(comp.getComResDes() + " (" + comp.getProvResCod() + ")");
				else pdfDto.setDelLuogo(comp.getComAltroDes());
				String tel = "";
				if(comp.getCsAAnagrafica().getTel() != null)
					tel += comp.getCsAAnagrafica().getTel();
				if(tel != null && comp.getCsAAnagrafica().getCell() != null)
					tel += " - ";
				if(comp.getCsAAnagrafica().getCell() != null)
					tel += comp.getCsAAnagrafica().getCell();
				pdfDto.setDelTelefono(tel);
			} else {
				pdfDto.setDelDenominazione(cs.getCompDenominazione());
				pdfDto.setDelIndirizzo(cs.getCompIndirizzo());
				pdfDto.setDelLuogo(cs.getCompCitta());
				pdfDto.setDelTelefono(cs.getCompTel());
			}
		}
		
		return pdfDto;
		
	}
	
	public ResAdultiPdfDTO fillResAdulti() throws Exception {
		
		ResAdultiPdfDTO pdfDto = new ResAdultiPdfDTO();

		BaseDTO baseDto = new BaseDTO();
		baseDto.setObj(in.getId());
		fillEnte(baseDto);
		CsIResiAdulti cs = interventoService.findResiAdultiById(baseDto);
		
		pdfDto.setConRetta(cs.getCsTbTipoRetta().getDescrizione());
		if(cs.getValoreRetta() != null)
			pdfDto.setValoreRetta(currencyFormat.format(cs.getValoreRetta()));
		if(cs.getContrPerOspite() != null)
			pdfDto.setContributo(currencyFormat.format(cs.getContrPerOspite()));
		if(cs.getCompartecipazione() != null)
			pdfDto.setCompartecipazione(currencyFormat.format(cs.getCompartecipazione()));
		if(cs.getCsCComunita() != null) {
			CsAAnaIndirizzo anaInd = cs.getCsCComunita().getCsAAnaIndirizzo();
			String descrizione = cs.getCsCComunita().getSogggiuRagsoc()+" - "+anaInd.getIndirizzo()+", "+anaInd.getCivicoNumero()+" - "+anaInd.getComDes()+" ("+anaInd.getProv()+")";
			pdfDto.setComunita(descrizione);
		} else pdfDto.setComunita(cs.getPressoAltro());
		
		pdfDto.setShowCasellaContributo(true);
		
		return pdfDto;
		
	}
	
	public ResMinoriPdfDTO fillResMinori() throws Exception {
		
		ResMinoriPdfDTO pdfDto = new ResMinoriPdfDTO();
	
		BaseDTO baseDto = new BaseDTO();
		baseDto.setObj(in.getId());
		fillEnte(baseDto);
		CsIResiMinore cs = interventoService.findResiMinoreById(baseDto);
		
		if(cs.getFlagSenzaProvvedimento() != null && cs.getFlagSenzaProvvedimento().intValue() == 1)
			pdfDto.setProvvAG("Senza provvedimento A.G.");
		else {
			String provv = "Provvedimento A.G. numero <b>" + cs.getNProvvAg() + "</b>";
			if(cs.getDtProvAg() != null)
				provv += "  Data <b>" + ddMMyyyy.format(cs.getDtProvAg()) + "</b>";
			pdfDto.setProvvAG(provv);
		}
		if(cs.getImporto() != null)
			pdfDto.setSpeseExtra(currencyFormat.format(cs.getImporto()));
		if(cs.getFlagSpeseVacanze() != null && cs.getFlagSpeseVacanze().intValue() == 1)
			pdfDto.setSpeseVacanze("SI");
		else pdfDto.setSpeseVacanze("NO");
		if(cs.getFlagSpesesani() != null && cs.getFlagSpesesani().intValue() == 1)
			pdfDto.setSpeseSanitarie("SI");
		else pdfDto.setSpeseSanitarie("NO");
		if(cs.getFlagIncontriProtetti() != null && cs.getFlagIncontriProtetti().intValue() == 1)
			pdfDto.setIncontriProtetti("SI");
		else pdfDto.setIncontriProtetti("NO");
		if(cs.getFlagInterventoEdu() != null && cs.getFlagInterventoEdu().intValue() == 1)
			pdfDto.setInterventoEducativo("SI");
		else pdfDto.setInterventoEducativo("NO");
		if(cs.getFlagRimborsoTesti() != null && cs.getFlagRimborsoTesti().intValue() == 1)
			pdfDto.setRimborsoTesti("SI");
		else pdfDto.setRimborsoTesti("NO");
		if(cs.getFlagAltro() != null && cs.getFlagAltro().intValue() == 1)
			pdfDto.setAltro("SI");
		else pdfDto.setAltro("NO");
		
		if(cs.getCsTbTipoRientriFami() != null)
			pdfDto.setRientriFamOrigine(cs.getCsTbTipoRientriFami().getDescrizione());
		if(cs.getCsTbTipoRetta() != null)
			pdfDto.setConRetta(cs.getCsTbTipoRetta().getDescrizione());
		if(cs.getValoreRetta() != null)
			pdfDto.setValoreRetta(currencyFormat.format(cs.getValoreRetta()));
		if(cs.getContributoMensileFamiOri() != null)
			pdfDto.setContributoFamOrigine(currencyFormat.format(cs.getContributoMensileFamiOri()));
		
		if(cs.getCsCComunita() != null) {
			CsAAnaIndirizzo anaInd = cs.getCsCComunita().getCsAAnaIndirizzo();
			String descrizione = cs.getCsCComunita().getSogggiuRagsoc()+" - "+anaInd.getIndirizzo()+", "+anaInd.getCivicoNumero()+" - "+anaInd.getComDes()+" ("+anaInd.getProv()+")";
			pdfDto.setComunita(descrizione);
		}
		
		if(cs.getCsAComponente() != null) {
			CsAComponente comp = cs.getCsAComponente();
			pdfDto.setDenominazione(comp.getCsAAnagrafica().getCognome() + " " + comp.getCsAAnagrafica().getNome() + " (" + comp.getCsTbTipoRapportoCon().getDescrizione() + ")");
			pdfDto.setIndirizzo(comp.getIndirizzoRes() + " " + comp.getNumCivRes());
			if(comp.getComResDes() != null)
				pdfDto.setLuogo(comp.getComResDes() + " (" + comp.getProvResCod() + ")");
			else pdfDto.setLuogo(comp.getComAltroDes());
			String tel = "";
			if(comp.getCsAAnagrafica().getTel() != null)
				tel += comp.getCsAAnagrafica().getTel();
			if(tel != null && comp.getCsAAnagrafica().getCell() != null)
				tel += " - ";
			if(comp.getCsAAnagrafica().getCell() != null)
				tel += comp.getCsAAnagrafica().getCell();
			pdfDto.setTelefono(tel);
		} else {
			pdfDto.setDenominazione(cs.getCompDenominazione());
			pdfDto.setIndirizzo(cs.getCompIndirizzo());
			pdfDto.setLuogo(cs.getCompCitta());
			pdfDto.setTelefono(cs.getCompTel());
		}
		
		return pdfDto;
		
	}
	
	public AffidoFamPdfDTO fillAffidoFam() throws Exception {
		
		AffidoFamPdfDTO pdfDto = new AffidoFamPdfDTO();

		BaseDTO baseDto = new BaseDTO();
		baseDto.setObj(in.getId());
		fillEnte(baseDto);
		CsIAffidoFam cs = interventoService.findAffidoById(baseDto);
		
		if(cs.getImporto() != null)
			pdfDto.setSpeseExtra(currencyFormat.format(cs.getImporto()));
		if(cs.getFlagSpeseVacanze() != null && cs.getFlagSpeseVacanze().intValue() == 1)
			pdfDto.setSpeseVacanze("SI");
		else pdfDto.setSpeseVacanze("NO");
		if(cs.getFlagSpesesani() != null && cs.getFlagSpesesani().intValue() == 1)
			pdfDto.setSpeseSanitarie("SI");
		else pdfDto.setSpeseSanitarie("NO");
		if(cs.getFlagIncontriProtetti() != null && cs.getFlagIncontriProtetti().intValue() == 1)
			pdfDto.setIncontriProtetti("SI");
		else pdfDto.setIncontriProtetti("NO");
		if(cs.getFlagInterventoEdu() != null && cs.getFlagInterventoEdu().intValue() == 1)
			pdfDto.setInterventoEducativo("SI");
		else pdfDto.setInterventoEducativo("NO");
		if(cs.getFlagRimborsoTesti() != null && cs.getFlagRimborsoTesti().intValue() == 1)
			pdfDto.setRimborsoTesti("SI");
		else pdfDto.setRimborsoTesti("NO");
		if(cs.getFlagAltro() != null && cs.getFlagAltro().intValue() == 1) {
			pdfDto.setAltro("SI");
			pdfDto.setDescAltro(cs.getAltroDesc());
		}
		else pdfDto.setAltro("NO");
		
		if(cs.getFlagSenzaProvvedimento() != null && cs.getFlagSenzaProvvedimento().intValue() == 1)
			pdfDto.setProvvAG("Senza provvedimento A.G.");
		else {
			String provv = "Provvedimento A.G. numero <b>" + cs.getNProvvAg() + "</b>";
			if(cs.getDtProvAg() != null)
				provv += " Data <b>" + ddMMyyyy.format(cs.getDtProvAg()) + "</b>";
			pdfDto.setProvvAG(provv);
		}
		
		if(cs.getFlagConsensuale() != null && cs.getFlagConsensuale().intValue() == 1)
			pdfDto.setConsensuale("SI");
		else pdfDto.setConsensuale("NO");
		if(cs.getAnno() != null)
			pdfDto.setAnno(cs.getAnno().toString());
		pdfDto.setFamAffidataria(cs.getFamigliaAffidataria());
		pdfDto.setCfCapoFamiglia(cs.getFamCf());
		pdfDto.setIndirizzo(cs.getFamIndirizzo());
		pdfDto.setComune(cs.getFamCitta());
		pdfDto.setTelefono(cs.getFamTel());
		pdfDto.setCellulare(cs.getFamCellulare());
		
		if(cs.getFlagAEteroFam() != null && cs.getFlagAEteroFam().intValue() == 1)
			pdfDto.setEteroFamiliare("SI");
		else pdfDto.setEteroFamiliare("NO");
		if(cs.getFlagAParenti() != null && cs.getFlagAParenti().intValue() == 1)
			pdfDto.setaParenti("SI");
		else pdfDto.setaParenti("NO");
		if(cs.getFlagADiurnoMese() != null && cs.getFlagADiurnoMese().intValue() == 1)
			pdfDto.setDiurnoMese("SI");
		else pdfDto.setDiurnoMese("NO");
		if(cs.getFlagADiurnoGiornaliero() != null && cs.getFlagADiurnoGiornaliero().intValue() == 1)
			pdfDto.setGiornDiurno("SI");
		else pdfDto.setGiornDiurno("NO");
		if(cs.getAffidoNumGiorniSett() != null)
			pdfDto.setnGiorni(cs.getAffidoNumGiorniSett().toString());
		if(cs.getQuotaParticolare() != null)
			pdfDto.setQuotaPart(currencyFormat.format(cs.getQuotaParticolare()));
		pdfDto.setTipoRiscossione(cs.getTipoRiscossione());
		pdfDto.setIntestazione(cs.getIntestazione());
		pdfDto.setIban(cs.getIban());
		pdfDto.setAccreditoA(cs.getAccreditoA());
		if(cs.getContributoFamiOri() != null)
			pdfDto.setContrFamOrigine(currencyFormat.format(cs.getContributoFamiOri()));
		
		return pdfDto;
		
	}
	
	public AssDomiciliarePdfDTO fillAssDomiciliare() throws Exception {
		
		AssDomiciliarePdfDTO pdfDto = new AssDomiciliarePdfDTO();
		
		BaseDTO baseDto = new BaseDTO();
		baseDto.setObj(in.getId());
		fillEnte(baseDto);
		
		CsIAdmAdh cs = interventoService.findAdmById(baseDto);
		
		pdfDto.setTipo(cs.getTipo());
		if(cs.getFlagSenzaProvvedimento() != null && cs.getFlagSenzaProvvedimento().intValue() == 1)
			pdfDto.setProvvAG("Senza provvedimento A.G.");
		else {
			String provv = "Provvedimento A.G. numero <b>" + cs.getNProvvAg() + "</b>";
			if(cs.getDtProvAg() != null)
				provv += " Data <b>" + ddMMyyyy.format(cs.getDtProvAg()) + "</b>";
			pdfDto.setProvvAG(provv);
		}
		
		if(cs.getAnno() != null)
			pdfDto.setAnno(cs.getAnno().toString());
		pdfDto.setTipoOre(cs.getTipoOrePreviste());
		if(cs.getOrePreviste() != null)
			pdfDto.setnOre(cs.getOrePreviste().toString());
		if(cs.getNumAccessi() != null)
			pdfDto.setnAccessi(cs.getNumAccessi().toString());
		if(cs.getContributoFamiglia() != null)
			pdfDto.setContrFamiglia(currencyFormat.format(cs.getContributoFamiglia()));
		
		return pdfDto;
		
	}
	
	public SemiResMinoriPdfDTO fillSemiResMinori() throws Exception {
		
		SemiResMinoriPdfDTO pdfDto = new SemiResMinoriPdfDTO();

		BaseDTO baseDto = new BaseDTO();
		baseDto.setObj(in.getId());
		fillEnte(baseDto);
		CsISemiResiMin cs = interventoService.findSemiResMinoreById(baseDto);

		
		if(cs.getFlagSenzaProvvedimento() != null && cs.getFlagSenzaProvvedimento().intValue() == 1)
			pdfDto.setProvvAG("Senza provvedimento A.G.");
		else {
			String provv = "Provvedimento A.G. numero <b>" + cs.getNProvvAg() + "</b>";
			if(cs.getDtProvAg() != null)
				provv += " Data <b>" + ddMMyyyy.format(cs.getDtProvAg()) + "</b>";
			pdfDto.setProvvAG(provv);
		}
		if(cs.getImporto() != null)
			pdfDto.setSpeseExtra(currencyFormat.format(cs.getImporto()));
		
		if(cs.getFlagPernottamento() != null && cs.getFlagPernottamento().intValue() == 1)
			pdfDto.setPernottamento("SI");
		else pdfDto.setPernottamento("NO");
		if(cs.getFlagCene() != null && cs.getFlagCene().intValue() == 1)
			pdfDto.setCene("SI");
		else pdfDto.setCene("NO");
		if(cs.getFlagPranzo() != null && cs.getFlagPranzo().intValue() == 1)
			pdfDto.setPranzi("SI");
		else pdfDto.setPranzi("NO");
		if(cs.getFlagInterventoEdu() != null && cs.getFlagInterventoEdu().intValue() == 1)
			pdfDto.setInterventoEducativo("SI");
		else pdfDto.setInterventoEducativo("NO");
		if(cs.getFlagAltro() != null && cs.getFlagAltro().intValue() == 1)
			pdfDto.setAltro("SI");
		else pdfDto.setAltro("NO");
		
		String trasporto = "";
		if(cs.getFlagTrasportoSc() != null && cs.getFlagTrasportoSc().intValue() == 1)
			trasporto += "Scuola - Centro";
		if(cs.getFlagTrasportoCc() != null && cs.getFlagTrasportoCc().intValue() == 1) {
			if(!"".equals(trasporto))
				trasporto += " / ";
			trasporto += "Centro - Casa";
		}
		pdfDto.setTrasporto(trasporto);
		if(cs.getNumGiorniSett() != null)
			pdfDto.setnGiorni(cs.getNumGiorniSett().toString());
		if(cs.getAnno() != null)
			pdfDto.setAnno(cs.getAnno().toString());
		if(cs.getCsTbTipoRetta() != null)
			pdfDto.setTipoRetta(cs.getCsTbTipoRetta().getDescrizione());
		if(cs.getValoreRetta() != null)
			pdfDto.setValoreRetta(currencyFormat.format(cs.getValoreRetta()));
		if(cs.getContributoMensileFamiOri() != null)
			pdfDto.setContrFamOrigine(currencyFormat.format(cs.getContributoMensileFamiOri()));
		if(cs.getCsCComunita() != null) {
			CsAAnaIndirizzo anaInd = cs.getCsCComunita().getCsAAnaIndirizzo();
			String descrizione = cs.getCsCComunita().getSogggiuRagsoc()+" - "+anaInd.getIndirizzo()+", "+anaInd.getCivicoNumero()+" - "+anaInd.getComDes()+" ("+anaInd.getProv()+")";
			pdfDto.setComunita(descrizione);
		} else pdfDto.setComunita(cs.getComunitaAltro());
		
		return pdfDto;
		
	}
	
	public SchedaUOLPdfDTO fillSchedaUOL() throws Exception {
		
		SchedaUOLPdfDTO pdfDto = new SchedaUOLPdfDTO();

		BaseDTO baseDto = new BaseDTO();
		baseDto.setObj(in.getId());
		fillEnte(baseDto);
		CsISchedaLavoro cs = interventoService.findSchedaLavoroById(baseDto);
		
		pdfDto.setCodice(cs.getCodUtente());
		if(cs.getAnno() != null)
			pdfDto.setAnno(cs.getAnno().toString());
		if(cs.getCsOOperatore() != null)
			pdfDto.setEducatore(this.getDenominazioneOperatore(cs.getCsOOperatore()));
		
		BaseDTO bDto = new BaseDTO();
		fillEnte(bDto);
		if(cs.getTipoCirc4Id() != null) {
			bDto.setObj(cs.getTipoCirc4Id());
			pdfDto.setTipoCirc4(confService.getTipoCirc4ById(bDto).getDescrizione());
		}
		if(cs.getInterventiUOLId() != null) {
			bDto.setObj(cs.getInterventiUOLId());
			pdfDto.setIntervento(confService.getInterventiUOLById(bDto).getDescrizione());
		}
		if(cs.getTipoProgettoId() != null) {
			bDto.setObj(cs.getTipoProgettoId());
			pdfDto.setTipoProgetto(confService.getTipoProgettoById(bDto).getDescrizione());
		}
		pdfDto.setNote(cs.getNote());
		
		return pdfDto;
		
	}
	
	public CsASoggettoLAZY getSoggetto() {
		return soggetto;
	}
	public void setSoggetto(CsASoggettoLAZY soggetto) {
		this.soggetto = soggetto;
	}

	public Long getDiarioId() {
		return diarioId;
	}

	public void setDiarioId(Long diarioId) throws Exception {
		this.diarioId = diarioId;
		
		BaseDTO baseDto = new BaseDTO();
		baseDto.setObj(diarioId);
		fillEnte(baseDto);
		flgIn = interventoService.getFoglioInterventoById(baseDto);
		in = flgIn.getCsIIntervento();
		CsCTipoInterventoCustom interventoCustom = in.getCsIInterventoCustom();
		if (interventoCustom!=null)
			idTipoInterventoCustom = interventoCustom.getId();
		idTipoIntervento = in.getCsRelSettCsocTipoInter().getCsRelCatsocTipoInter().getCsCTipoIntervento().getId();
	}

	public long getIdTipoInterventoCustom() {
		return idTipoInterventoCustom;
	}

	public CsIIntervento getIn() {
		return in;
	}

}
