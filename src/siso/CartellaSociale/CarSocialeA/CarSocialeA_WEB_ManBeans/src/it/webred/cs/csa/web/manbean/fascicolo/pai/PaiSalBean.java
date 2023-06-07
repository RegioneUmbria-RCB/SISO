package it.webred.cs.csa.web.manbean.fascicolo.pai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.primefaces.model.DualListModel;

import it.webred.cs.csa.ejb.client.AccessTablePaiSALSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSessionBeanRemote;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.client.domini.AccessTableDominiPaiSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.pai.sal.CsPaiSALStoricoDTO;
import it.webred.cs.csa.ejb.dto.pai.sal.CsPaiSalDTO;
import it.webred.cs.csa.ejb.dto.pai.sal.CsPaiSalDominioDTO;
import it.webred.cs.csa.ejb.dto.pai.sal.PaiSALDominiEnum;
import it.webred.cs.csa.ejb.dto.pai.sal.PaiSALFaseEnum;
import it.webred.cs.csa.ejb.dto.relazione.RelazioneSintesiDTO;
import it.webred.cs.data.DataModelCostanti.TipoFormAttivitaProfessionali;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;

public class PaiSalBean extends CsUiCompBaseBean {

	protected AccessTablePaiSALSessionBeanRemote paiSALService = (AccessTablePaiSALSessionBeanRemote) getCarSocialeEjb("AccessTablePaiSALSessionBean");
	protected AccessTableSchedaSessionBeanRemote schedaService = (AccessTableSchedaSessionBeanRemote) getCarSocialeEjb("AccessTableSchedaSessionBean");
	protected AccessTableDominiPaiSessionBeanRemote dominiPaiService = (AccessTableDominiPaiSessionBeanRemote) getCarSocialeEjb("AccessTableDominiPaiSessionBean");
	
	private Integer faseSal;
	private CsPaiSalDTO sal;
	private Date dataFaseSal;
	private List<SelectItem> lstTitoliStudio;
    private String warnigSalvataggio;
    
	public PaiSalBean() {
		this.sal = null;
		
	}
	
	public List<PaiSALFaseEnum> getListaFasi() {
		return Arrays.asList(PaiSALFaseEnum.values());
	}
	
	public Boolean isFaseDisabilitata(Integer valore) {
		return (sal.getCodiceFaseAttuale() - valore) > 0;
	}
	
	public void nuovo() {
		
		try {
			this.sal = new CsPaiSalDTO(PaiSALFaseEnum.FASE_PRELIMINARE);
			faseSal = sal.getCodiceFaseAttuale();		
			dataFaseSal = sal.getDataFaseSALAttuale();
			
		} catch (Exception e) {
			addError(
					"Errore",
					"Errore nel caricamento dei dati di origine");

		}

		logger.debug("Inizializzato nuovo progetto di Accompagnamento al Lavoro");
	}
	public void cambioFase(ValueChangeEvent event) {
		faseSal = (Integer) event.getNewValue();
	}

	public List<CsPaiSalDominioDTO> getListaTipoSoggetto() {
		BaseDTO b = new BaseDTO();
		b.setObj(PaiSALDominiEnum.TIPO_SOGGETTO.name());
		fillEnte(b);
		return dominiPaiService.findSalByDominio(b);
	}
	
	public List<CsPaiSalDominioDTO> getListaInvianti() {
		BaseDTO b = new BaseDTO();
		b.setObj(PaiSALDominiEnum.INVIANTE.name());
		fillEnte(b);
		return dominiPaiService.findSalByDominio(b);
	}
	public List<CsPaiSalDominioDTO> getListaEsperienze() {
		BaseDTO b = new BaseDTO();
		b.setObj(PaiSALDominiEnum.TIPO_ESPERIENZA.name());
		fillEnte(b);
		return dominiPaiService.findSalByDominio(b);
	}
	public List<CsPaiSalDominioDTO> getListaEsiti() {
		BaseDTO b = new BaseDTO();
		b.setObj(PaiSALDominiEnum.ESITO.name());
		fillEnte(b);
		return dominiPaiService.findSalByDominio(b);
	}
	public List<CsPaiSalDominioDTO> getListaLegge104() {
		BaseDTO b = new BaseDTO();
		b.setObj(PaiSALDominiEnum.LEGGE_104.name());
		fillEnte(b);
		return dominiPaiService.findSalByDominio(b);
	}
	
	public List<CsPaiSalDominioDTO> getListaLegge68() {
		BaseDTO b = new BaseDTO();
		b.setObj(PaiSALDominiEnum.LEGGE_68.name());
		fillEnte(b);
		return dominiPaiService.findSalByDominio(b);
	}
	
	public List<CsPaiSalDominioDTO> getListaIscrizioneCPI() {
		BaseDTO b = new BaseDTO();
		b.setObj(PaiSALDominiEnum.ISCRIZIONE_CPI.name());
		fillEnte(b);
		return dominiPaiService.findSalByDominio(b);
	}
	
	public List<CsPaiSalDominioDTO> getListaRichiedenteAsilo() {
		BaseDTO b = new BaseDTO();
		b.setObj(PaiSALDominiEnum.RICHIEDENTE_ASILO.name());
		fillEnte(b);
		
		return dominiPaiService.findSalByDominio(b);
	}
	
	public List<CsPaiSalDominioDTO> getListaPresaIncaricoInv() {
		BaseDTO b = new BaseDTO();
		b.setObj(PaiSALDominiEnum.PRESA_CARICO_INV.name());
		fillEnte(b);
		return dominiPaiService.findSalByDominio(b);
	}

	public List<CsPaiSalDominioDTO> getListaPropostaFam() {
		BaseDTO b = new BaseDTO();
		b.setObj(PaiSALDominiEnum.PROPOSTA_FAM.name());
		fillEnte(b);
		return dominiPaiService.findSalByDominio(b);
	}
	
	public List<CsPaiSalDominioDTO> getListaPresenzaTutor() {
		BaseDTO b = new BaseDTO();
		b.setObj(PaiSALDominiEnum.PRESENZA_TUTOR.name());
		fillEnte(b);
		return dominiPaiService.findSalByDominio(b);
	}
	public List<CsPaiSalDominioDTO> getListaVicinanzaAbit() {
		BaseDTO b = new BaseDTO();
		b.setObj(PaiSALDominiEnum.VICINANZA_ABIT.name());
		fillEnte(b);
		return dominiPaiService.findSalByDominio(b);
	}
	
	public List<CsPaiSalDominioDTO> getListaPeriodoProva() {
		BaseDTO b = new BaseDTO();
		b.setObj(PaiSALDominiEnum.PERIODO_PROVA.name());
		fillEnte(b);
		return dominiPaiService.findSalByDominio(b);
	}
	
     public List<SelectItem> getLstTitoliStudio() {
		
		if(lstTitoliStudio == null){
			lstTitoliStudio = new ArrayList<SelectItem>();
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<KeyValueDTO> lst = confService.getTitoliStudio(bo);
			lstTitoliStudio = convertiLista(lst);
		}
		return lstTitoliStudio;
	}
    
     public Boolean isFase(Integer value) {
 		if (faseSal != null && faseSal.compareTo(value) >= 0) {
 			return true;
 		}
 		return false;
 	}
     
     public Boolean isFormazione() {
//    	 return (sal.getTipoEsperienza() == PaiSALDominiEnum.TIPO_ESPERIENZA.name() + "_FORMAZIONE");
 		return true;
 	}
     
     public void salva(Long diarioPaiId) {
    	sal.setDiarioPaiId(diarioPaiId);
 		BaseDTO bdto = new BaseDTO();
 		bdto.setObj(sal);
 		bdto.setObj2(faseSal);
 		bdto.setObj3(dataFaseSal);
 		fillEnte(bdto);
 		try {
			this.paiSALService.saveSAL(bdto);
			// reset
			this.sal = null;
			logger.debug("SAL Salvato correttamente");
 		} catch (Exception e) {
			logger.error("Errore salvataggio SAL", e);
			throw new CarSocialeServiceException("Errore salvataggio dati SAL", e);
		}
 	} 
     
     public void findSALByPai(Long diarioPaiId, Long idSoggetto) {

 		BaseDTO bdto = new BaseDTO();
 		bdto.setObj(diarioPaiId);
 		fillEnte(bdto);
		List<CsPaiSALStoricoDTO> salStoricoTutor = new ArrayList<CsPaiSALStoricoDTO>();
		List<CsPaiSALStoricoDTO> salStoricoMansione = new ArrayList<CsPaiSALStoricoDTO>();
 		try {
 			sal = paiSALService.findSalByDiarioPaiId(bdto);
 		
 			if (sal == null) {
 				nuovo();
 			} else {
 			
 			    faseSal = sal.getCodiceFaseAttuale();
 				dataFaseSal = sal.getDataFaseSALAttuale();
 				
 			if(!sal.getStoricoSAL().isEmpty()){
 				for (CsPaiSALStoricoDTO storico : sal.getStoricoSAL()) {
 					if (storico.getTutorContesto() != null) {
 						salStoricoTutor.add(storico);
 					}
 					if (storico.getMansione() != null) {
 						salStoricoMansione.add(storico);
 					
 					} 
 				}
 			}
 			sal.setLstStoricoTutor(salStoricoTutor);
 			sal.setLstStoricoMansioni(salStoricoMansione);
 				
 			
 				
 			logger.debug("Inizializzato SAL da PAI " + diarioPaiId);
 				
 				
 			}
 		} catch (Exception e) {
 			addError("Inizializzazione SAL", "SAL non inizializzato");
 			logger.error("Errore inizializzazione SAL", e);
 		}
 	}
     
     private boolean isCodiceFase(Integer codiceFase) {
    	 return codiceFase == sal.getCodiceFaseAttuale();
     }

     public List<String> validaSALPai(Long idSoggetto, Date dataPaiInizio, DualListModel<RelazioneSintesiDTO> picklistRelazioni) {
 		List<String> error = new ArrayList<String>();
    	boolean esisteMicro4=false;
 		boolean esisteMicro5=false;
 		boolean esisteMicro6=false;
    	
 		String obbligatorio = " è obbligatorio collegare un'attività di tipo ";
 		String consigliato 	= " è consigliato collegare un'attività di tipo ";
 		
 		//Devo controllare che siano associate o stanno per essere associate le relazionial SAL secondo l'excel
 		for (RelazioneSintesiDTO r: picklistRelazioni.getTarget())
		{
			esisteMicro4=r.getTipoFormMicroAttivita()==TipoFormAttivitaProfessionali.SAL_VALUTAZIONE; //valutaz servizioInviante
			esisteMicro5=r.getTipoFormMicroAttivita()==TipoFormAttivitaProfessionali.SAL_ORIENTAMENTO;
			esisteMicro6=r.getTipoFormMicroAttivita()==TipoFormAttivitaProfessionali.SAL_MEDIAZIONE;
		}
    		
		if(isCodiceFase(PaiSALFaseEnum.FASE_PRELIMINARE.getValore()) && !esisteMicro4) {
			this.setWarnigSalvataggio("In fase " + PaiSALFaseEnum.FASE_PRELIMINARE.getDescrizione() + consigliato +"Valutazione con servizio inviante\r\n");
		}

		if(isCodiceFase(PaiSALFaseEnum.ORIENTAMENTO.getValore()) && !esisteMicro5) {
			this.setWarnigSalvataggio("In fase " + PaiSALFaseEnum.ORIENTAMENTO.getDescrizione() + consigliato + "Orientamento\r\n");
		}
		
		if(isCodiceFase(PaiSALFaseEnum.ATTIVAZIONE_STRUMENTI.getValore()) && !esisteMicro6) {
			this.setWarnigSalvataggio("In fase " + PaiSALFaseEnum.ATTIVAZIONE_STRUMENTI.getDescrizione() + consigliato + "Mediazione\r\n"); 
		}
		
		if(isCodiceFase(PaiSALFaseEnum.ORIENTAMENTO.getValore()) && !esisteMicro4) {
			error.add("In fase " + PaiSALFaseEnum.ORIENTAMENTO.getDescrizione() + obbligatorio + "Valutazione con servizio inviante\r\n");
		}

		if(isCodiceFase(PaiSALFaseEnum.PROGETTAZIONE.getValore()) && !esisteMicro5) {
			error.add("In fase " + PaiSALFaseEnum.PROGETTAZIONE.getDescrizione() + obbligatorio + "Orientamento\r\n") ;
		}
		
		if(isCodiceFase(PaiSALFaseEnum.ACCOMPAGNAMENTO.getValore())  && !esisteMicro6) {
			error.add("In fase " + PaiSALFaseEnum.ACCOMPAGNAMENTO.getDescrizione() + obbligatorio + "Mediazione\r\n\"") ;
		}

 		// Controllo sulla data
 		if (dataFaseSal != null && dataFaseSal.before(dataPaiInizio)) {
 			error.add("La data della fase non può essere antecedente a quella del progetto");
 		}


 		return error;		
 	}
     
   	public CsPaiSalDTO getSal() {
		return sal;
	}

	private String format(String arg){
    	if(arg!=null)
    		return arg;
    	else
    		return "";
    }
	public void setSal(CsPaiSalDTO sal) {
		this.sal = sal;
	}

	public Date getDataFaseSal() {
		return dataFaseSal;
	}

	public void setDataFaseSal(Date dataFaseSal) {
		this.dataFaseSal = dataFaseSal;
	}

	public Integer getFaseSal() {
		return faseSal;
	}

	public void setFaseSal(Integer faseSal) {
		this.faseSal = faseSal;
	}

	public void setLstTitoliStudio(List<SelectItem> lstTitoliStudio) {
		this.lstTitoliStudio = lstTitoliStudio;
	}

	public String getWarnigSalvataggio() {
		return warnigSalvataggio;
	}

	public void setWarnigSalvataggio(String warnigSalvataggio) {
		this.warnigSalvataggio = warnigSalvataggio;
	}
	
	
}
