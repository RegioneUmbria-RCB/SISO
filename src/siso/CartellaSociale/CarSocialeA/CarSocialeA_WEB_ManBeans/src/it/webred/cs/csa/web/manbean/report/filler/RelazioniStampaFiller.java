package it.webred.cs.csa.web.manbean.report.filler;


import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIndirizzoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableMediciSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.csa.ejb.dto.fascicolo.isee.ListaDatiIseeDTO;
import it.webred.cs.csa.web.manbean.report.dto.foglioAmm.RelazionePdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.foglioAmm.custom.BuonoSocialePdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.foglioAmm.custom.CentroDiurnoIntPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.foglioAmm.custom.ContrEconomiciPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.foglioAmm.custom.PastiDomiciliariPdfDTO;
import it.webred.cs.csa.web.manbean.report.dto.foglioAmm.custom.VoucherPdfDTO;
import it.webred.cs.data.model.CsAIndirizzo;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsASoggettoMedico;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsDRelazione;
import it.webred.cs.data.model.CsIBuonoSoc;
import it.webred.cs.data.model.CsICentrod;
import it.webred.cs.data.model.CsIContrEco;
import it.webred.cs.data.model.CsIIntervento;
import it.webred.cs.data.model.CsIPasti;
import it.webred.cs.data.model.CsIVouchersad;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SuppressWarnings("unused")
public class RelazioniStampaFiller extends CsUiCompBaseBean {
	
	private AccessTableCasoSessionBeanRemote casoService = (AccessTableCasoSessionBeanRemote) getCarSocialeEjb("AccessTableCasoSessionBean");
	private AccessTableMediciSessionBeanRemote mediciService = (AccessTableMediciSessionBeanRemote) getCarSocialeEjb( "AccessTableMediciSessionBean");
	private AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) getCarSocialeEjb("AccessTableSoggettoSessionBean");
	private AccessTableIndirizzoSessionBeanRemote indirizzoS = (AccessTableIndirizzoSessionBeanRemote) getCarSocialeEjb("AccessTableIndirizzoSessionBean");
	private AccessTableInterventoSessionBeanRemote interventi = (AccessTableInterventoSessionBeanRemote) getCarSocialeEjb("AccessTableInterventoSessionBean");
	private AccessTableDiarioSessionBeanRemote diario = (AccessTableDiarioSessionBeanRemote) getCarSocialeEjb("AccessTableDiarioSessionBean");
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	private CsASoggettoLAZY soggetto;
	private CsDRelazione diarioRelaz;
	private String denominazione;
	private CsCTipoIntervento interventoVoucher;
	private CsCTipoIntervento interventocontrib;
	private CsCTipoIntervento interventoPasti;
	private CsCTipoIntervento interventoCentriDiurni;
	private CsCTipoIntervento interventobuoni;
	private List<Long> lstIdCatSoc;
	
  public List<Long> getLstIdCatSoc() {
		return lstIdCatSoc;
	}

	public void setLstIdCatSoc(List<Long> lstIdCatSoc) {
		this.lstIdCatSoc = lstIdCatSoc;
	}

public RelazionePdfDTO fillRelazioni() {
		
		RelazionePdfDTO relazione = new RelazionePdfDTO();
		
		Date n = new Date();
		BaseDTO dto1 = new BaseDTO();
		fillEnte(dto1);
		dto1.setObj(soggetto.getAnagraficaId());	
		CsAIndirizzo p = new CsAIndirizzo();
		CsOOperatore rsFirma = null;
		CsOOperatore asResponsabile = null;
		AmAnagrafica resUfficio= new AmAnagrafica();
		AmAnagrafica assistente= new AmAnagrafica();
		String mD= null;
		String is="";
		
		//trovo isee non scaduti
		BaseDTO dtoi = new BaseDTO();
		fillEnte(dtoi);
		dtoi.setObj(soggetto.getCsACaso().getId());
		List<ListaDatiIseeDTO> lstdiario = diario.findIseeAttiveByCaso(dtoi); //Data chiusura da > sysdate
		for(ListaDatiIseeDTO d :lstdiario){
			is+= "Isee: "+d.getIsee()+" - " +"Data Scadenza:"+ sdf.format(d.getDataChiusuraDa())+ " ";
		}
		//trovo medico attuale
		List<CsASoggettoMedico>  soggMed = soggettoService.getSoggettoMedicoBySoggetto(dto1);
		for(CsASoggettoMedico m:soggMed){
			if(m.getId().getDataFineApp().after(n))
				 mD ="Nome: "+m.getCsCMedico().getNome()+" - "+"Cognome: "+m.getCsCMedico().getCognome();
		}
		
		//trovo indirizzo
		List <CsAIndirizzo> indirizzo = indirizzoS.getIndirizziBySoggetto(dto1);
		for(CsAIndirizzo i: indirizzo){
			if(i.getDataInizioApp()!=null)
				p = i;
		}
		
		//Recupero il responsabile dell'ufficio (settore)
		
		if(diarioRelaz.getDiarioId() != null){
						
			denominazione = soggetto.getCsAAnagrafica().getDenominazione()+
					", Data di Nascita"+ soggetto.getCsAAnagrafica().getDataNascita()+ 
					", Residente in "+p.getCsAAnaIndirizzo().getLabelIndirizzoCompleto()+
					", Codice Fiscale: "+soggetto.getCsAAnagrafica().getCf();
			
			//rivedere
			relazione.setDataOdierna(getNomeEnte()+", "+ sdf.format(new Date()));
			relazione.setAnagrafica(denominazione);
			relazione.setSocioAmbientale(diarioRelaz.getSituazioneAmb());
			relazione.setParentale(diarioRelaz.getSituazioneParentale());
			relazione.setSanitaria(diarioRelaz.getSituazioneSanitaria());
			relazione.setMedicoCur(mD);
			relazione.setIsee(is);
			relazione.setProposta(diarioRelaz.getProposta());
			relazione.setOrgServizio(diarioRelaz.getOrganizzazioneServizio());
			
			//Recupero il responsabile del diario-relazione inserito al momento del salvataggio (NON quello corrente del caso)
			OperatoreDTO odto = new OperatoreDTO();
			fillEnte(odto);
			odto.setIdOperatore(diarioRelaz.getCsDDiario().getResponsabileCaso());
			try {
				asResponsabile = confEnteService.findOperatoreById(odto);
			} catch (Exception e) {
				logger.error(e);
			}
			odto.setIdOperatore(null);
			
			//Recupero il settore a cui era assegnato il caso quando è stato scritto il diario
			CsOSettore settDiario = diarioRelaz.getCsDDiario().getCsOOperatoreSettore().getCsOSettore();
			if(settDiario!=null){
				relazione.setUfficio(settDiario.getNome());
				
				//Recupero il responsabile del settore, addetto alla firma dei documenti
				odto.setIdSettore(settDiario.getId());
				try {
					CsOOperatoreSettore cosFirma = confEnteService.findRespSettoreFirma(odto);
					if(cosFirma!=null)
						rsFirma = cosFirma.getCsOOperatore(); 
				} catch (Exception e) {
					logger.error(e);
				}
								
			}
			
			relazione.setAssistenteS(asResponsabile!=null ? this.getDenominazioneOperatore(asResponsabile) : "");	
			relazione.setResponsabileS(rsFirma!=null ? this.getDenominazioneOperatore(rsFirma) : "");
		
		}
		
		return relazione;
		
	}
  
  public VoucherPdfDTO fillVoucher() {
	  
	  VoucherPdfDTO voucher = new VoucherPdfDTO();
	  	BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(soggetto.getCsACaso().getId());
	  	try {
			List<CsIIntervento> i=interventi.getListaInterventiByCaso(dto);
			for(CsIIntervento intr:i){
			  	BaseDTO dto2 = new BaseDTO();
				fillEnte(dto2);
				dto2.setObj(intr.getId());
				CsIVouchersad cs = interventi.findVouherSadById(dto2);
				
				if(cs !=null 
						&&  intr.getCsRelSettCsocTipoInter().getId().getScsCategoriaSocialeId()==(long)lstIdCatSoc.get(0) 
						&& intr.getCsRelSettCsocTipoInter().getId().getCstiTipoInterventoId()==interventoVoucher.getId()){
					
					voucher.setContributoUtente(cs.getContributioUtente().toString());
					 voucher.setOrePreviste(cs.getOrePreviste().toString());
					 voucher.setDal(cs.getAumDimOreDal().toString());
				}
				
			}
		
		} catch (Exception e) {
			
			logger.error(e);
		}
	 
	return voucher;
	  
  }
  public ContrEconomiciPdfDTO fillContribEconomico() {
	  
	  ContrEconomiciPdfDTO contib= new ContrEconomiciPdfDTO();
	  
	  BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(soggetto.getCsACaso().getId());
	  	try {
			List<CsIIntervento> i=interventi.getListaInterventiByCaso(dto);
			for(CsIIntervento intr:i){
			  	BaseDTO dto2 = new BaseDTO();
				fillEnte(dto2);
				dto2.setObj(intr.getId());
				CsIContrEco cs = interventi.findContributoEconomicoById(dto2);
				
				if(cs !=null 
						&&  intr.getCsRelSettCsocTipoInter().getId().getScsCategoriaSocialeId()==(long)lstIdCatSoc.get(0) 
						&& intr.getCsRelSettCsocTipoInter().getId().getCstiTipoInterventoId()==interventocontrib.getId()){
	  		contib.setContrRichiesto(cs.getValRichiesto().toString());
	  		contib.setRichiestoPer(cs.getRichAltroDesc());
	  		contib.setTipoRiscossione(cs.getTipoRiscossione());
	  		contib.setAccreditoA(cs.getAccreditoA());
	  		/*contib.setIban(null);
	  		contib.setPerErogazione(null);
	  		contib.setDelDenominazione(null);
	  		contib.setDelIndirizzo(null);
	  		contib.setDelLuogo(null);
	  		contib.setDelTelefono(null);*/
		 
				}
			}
			
		}catch (Exception e) {
				
			logger.error(e);
		} 
	  	return contib;
  }
  
public BuonoSocialePdfDTO fillBuoniSoc() {
	  
	BuonoSocialePdfDTO buoni= new BuonoSocialePdfDTO();
	BaseDTO dto = new BaseDTO();
	fillEnte(dto);
	dto.setObj(soggetto.getCsACaso().getId());
  	try {
		List<CsIIntervento> i=interventi.getListaInterventiByCaso(dto);
		for(CsIIntervento intr:i){
		  	BaseDTO dto2 = new BaseDTO();
			fillEnte(dto2);
			dto2.setObj(intr.getId());
			CsIBuonoSoc cs = interventi.findBuonoSocialeById(dto2);
			
			if(cs !=null 
					&&  intr.getCsRelSettCsocTipoInter().getId().getScsCategoriaSocialeId()==(long)lstIdCatSoc.get(0) 
					&& intr.getCsRelSettCsocTipoInter().getId().getCstiTipoInterventoId()==interventobuoni.getId()){
			if(intr.getCsIBuonoSoc().iterator().next().getRichSeStesso().equals(1)){	
				buoni.setRichiestoPer("Se Stesso");
			}else{
				buoni.setRichiestoPer(intr.getCsIBuonoSoc().iterator().next().getRichAltroDesc());
			}
	  		buoni.setTipoRiscossione(cs.getTipoRiscossione());
	  		buoni.setGestione(cs.getTipoGestione());
	  		buoni.setDeroghe(intr.getCsIBuonoSoc().iterator().next().getTipoDeroghe());
	  		buoni.setAccreditoA(cs.getAccreditoA());
	  		/*buoni.setIban(null);
	  		buoni.setDelDenominazione(null);
	  		buoni.setDelIndirizzo(null);
	  		buoni.setDelLuogo(null);
	  		buoni.setDelTelefono(null);
	  		buoni.setPerErogazione(null);*/
			}
		}
		
  	}catch (Exception e) {
  		logger.error(e);
  	} 
		  
return buoni;
	  
	  
  }
public PastiDomiciliariPdfDTO fillPastiDom() {
	  
	PastiDomiciliariPdfDTO pasti= new PastiDomiciliariPdfDTO();
	BaseDTO dto = new BaseDTO();
	fillEnte(dto);
	dto.setObj(soggetto.getCsACaso().getId());
  	try {
		List<CsIIntervento> i=interventi.getListaInterventiByCaso(dto);
		for(CsIIntervento intr:i){
		  	BaseDTO dto2 = new BaseDTO();
			fillEnte(dto2);
			dto2.setObj(intr.getId());
			CsIPasti cs = interventi.findPastiById(dto2);
			
			if(cs !=null 
					&& intr.getCsRelSettCsocTipoInter().getId().getScsCategoriaSocialeId()==(long)lstIdCatSoc.get(0) 
					&& intr.getCsRelSettCsocTipoInter().getId().getCstiTipoInterventoId()==interventoPasti.getId()){
	  
	  		pasti.setContributoUtente(cs.getContributioUtente().toString());
	  		pasti.setQuotaUtente(cs.getTipoQuotaUtente());
	  		/*pasti.setDietaSpeciale(intr.getCsIPasti().getDietaSpeciale());*/
			}
		}	
		}catch (Exception e) {
			logger.error(e);
	  	} 
return  pasti;
	   
}

public CentroDiurnoIntPdfDTO fillCentroDiurno() {
	  
	CentroDiurnoIntPdfDTO centroD= new CentroDiurnoIntPdfDTO();
	BaseDTO dto = new BaseDTO();
	fillEnte(dto);
	dto.setObj(soggetto.getCsACaso().getId());
  	try {
		List<CsIIntervento> i=interventi.getListaInterventiByCaso(dto);
		for(CsIIntervento intr:i){
		  	BaseDTO dto2 = new BaseDTO();
			fillEnte(dto2);
			dto2.setObj(intr.getId());
			CsICentrod cs = interventi.findCentroDiurnoById(dto2);
			
			if(cs !=null 
					&& intr.getCsRelSettCsocTipoInter().getId().getScsCategoriaSocialeId()==(long)lstIdCatSoc.get(0) 
					&& intr.getCsRelSettCsocTipoInter().getId().getCstiTipoInterventoId()==interventoCentriDiurni.getId()){
				  		centroD.setContributoUtente(cs.getContributioUtente().toString());
				  		centroD.setQuotaUtente(cs.getTipoQuotaUtente());
				  		//centroD.setDietaSpeciale(null);
				  		if(cs.getFlagNecessTrasporto().equals(1)){
				  			centroD.setTrasporto("SI");
				  		}else{
				  			centroD.setTrasporto("NO");
				  		}
			}
		}
  	}catch (Exception e) {
  		logger.error(e);
  	} 	
return centroD;
	  
}
  
  
  
public String getDenominazione() {
	return denominazione;
}

public void setDenominazione(String denominazione) {
	this.denominazione = denominazione;
}

public CsASoggettoLAZY getSoggetto() {
	return soggetto;
}

public void setSoggetto(CsASoggettoLAZY soggetto) {
	this.soggetto = soggetto;
}

public CsDRelazione getDiarioRelaz() {
	return diarioRelaz;
}

public void setDiarioRelaz(CsDRelazione diarioRelaz) {
	this.diarioRelaz = diarioRelaz;
}

public CsCTipoIntervento getInterventoVoucher() {
	return interventoVoucher;
}

public void setInterventoVoucher(CsCTipoIntervento interventoVoucher) {
	this.interventoVoucher = interventoVoucher;
}

public CsCTipoIntervento getInterventocontrib() {
	return interventocontrib;
}

public void setInterventocontrib(CsCTipoIntervento interventocontrib) {
	this.interventocontrib = interventocontrib;
}

public CsCTipoIntervento getInterventoPasti() {
	return interventoPasti;
}

public void setInterventoPasti(CsCTipoIntervento interventoPasti) {
	this.interventoPasti = interventoPasti;
}

public CsCTipoIntervento getInterventoCentriDiurni() {
	return interventoCentriDiurni;
}

public void setInterventoCentriDiurni(CsCTipoIntervento interventoCentriDiurni) {
	this.interventoCentriDiurni = interventoCentriDiurni;
}

public CsCTipoIntervento getInterventobuoni() {
	return interventobuoni;
}

public void setInterventobuoni(CsCTipoIntervento interventobuoni) {
	this.interventobuoni = interventobuoni;
}

public AccessTableMediciSessionBeanRemote getMediciService() {
	return mediciService;
}

public void setMediciService(AccessTableMediciSessionBeanRemote mediciService) {
	this.mediciService = mediciService;
}

public AccessTableSoggettoSessionBeanRemote getSoggettoService() {
	return soggettoService;
}

public void setSoggettoService(
		AccessTableSoggettoSessionBeanRemote soggettoService) {
	this.soggettoService = soggettoService;
}

public AccessTableIndirizzoSessionBeanRemote getIndirizzoS() {
	return indirizzoS;
}

public void setIndirizzoS(AccessTableIndirizzoSessionBeanRemote indirizzoS) {
	this.indirizzoS = indirizzoS;
}
public SimpleDateFormat getSdf() {
	return sdf;
}

public void setSdf(SimpleDateFormat sdf) {
	this.sdf = sdf;
}



}
