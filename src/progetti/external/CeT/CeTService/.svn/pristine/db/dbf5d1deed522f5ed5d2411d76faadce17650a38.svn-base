package it.webred.ct.aggregator.ejb.immobiliAccatastati;
 

import it.webred.ct.aggregator.ejb.CTServiceAggregatorBaseBean;
import it.webred.ct.aggregator.ejb.immobiliAccatastati.dto.ImmobiliAccatastatiInputDTO;
import it.webred.ct.aggregator.ejb.immobiliAccatastati.dto.ImmobiliAccatastatiOutputDTO;
import it.webred.ct.data.access.basic.catasto.CatastoService;
import it.webred.ct.data.access.basic.catasto.dto.ImmobiliAccatastatiOutDTO;
import it.webred.ct.data.access.basic.catasto.dto.RicercaSoggettoCatDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
 
@Stateless
@WebService
public class ImmobiliAccatastatiServiceBean extends CTServiceAggregatorBaseBean implements ImmobiliAccatastatiService {
	
	private static final long serialVersionUID = 1L;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Service_Data_Access/CatastoServiceBean")
	private CatastoService catastoService;
	
	public ImmobiliAccatastatiServiceBean() {}
	
	public ImmobiliAccatastatiOutputDTO[] getImmobiliAccatastatiDTO(ImmobiliAccatastatiInputDTO input) {
		List<ImmobiliAccatastatiOutDTO> result = new ArrayList<ImmobiliAccatastatiOutDTO>();
		ImmobiliAccatastatiOutputDTO[] output = new ImmobiliAccatastatiOutputDTO[0];
		try {
			
			if(input.getCodiceFiscale()!=null && !input.getCodiceFiscale().trim().equals("")){
				
				RicercaSoggettoCatDTO ric = new RicercaSoggettoCatDTO();
				
				Date dtNascita = getDateFromString(input.getDataNascita());
				Date dtInizio = getDateFromString(input.getDataInizio());
				Date dtFine = getDateFromString(input.getDataFine());
				
				ric.setEnteId(input.getEnteId());
				
				ric.setCodFis(input.getCodiceFiscale());
				ric.setCognome(input.getCognome());
				ric.setNome(input.getNome());
				ric.setDtNas(dtNascita);
				ric.setDtRifDa(dtInizio);
				ric.setDtRifA(dtFine);
				
				result = catastoService.getImmobiliAccatastatiByDatiSoggetto(ric);
				
				 output = new ImmobiliAccatastatiOutputDTO[result.size()];
				for(int i=0; i<result.size(); i++){
					ImmobiliAccatastatiOutDTO r = result.get(i);
					output[i]= copyDtoToDto(r);
					output[i].printRecord();
				}
				
			 }else{
				 throw new ImmobiliAccatastatiException("Parametro Codice Fiscale Obbligatorio");
			 }
			
		}catch (Throwable t) {
				logger.error("Eccezione: "+t.getMessage(), t);
				throw new ImmobiliAccatastatiException(t);
		}

		return output;
	}

	
	private ImmobiliAccatastatiOutputDTO copyDtoToDto(ImmobiliAccatastatiOutDTO i){
		ImmobiliAccatastatiOutputDTO o = new ImmobiliAccatastatiOutputDTO();
	
	    o.setSezione(i.getSezione());
	    o.setFoglio(i.getFoglio());
	    o.setNumero(i.getNumero());
	    o.setSubalterno(i.getSubalterno());
	    o.setSub(i.getSub());
	    o.setPartitaCatastale(i.getPartitaCatastale());
	    o.setCodiceVia(i.getCodiceVia());
	    o.setDescrizioneVia(i.getDescrizioneVia());
	    o.setCivico(i.getCivico());
	    o.setIndirizzoCatastale(i.getIndirizzoCatastale());
	    o.setPercentualePossesso(i.getPercentualePossesso());
	    o.setTipoTitolo(i.getTipoTitolo());
	    o.setDesTipoTitolo(i.getDesTipoTitolo());
	    o.setCategoria(i.getCategoria());
	    o.setClasse(i.getClasse());
	    o.setConsistenza(i.getConsistenza());
	    o.setRendita(i.getRendita());
	    o.setSupCat(i.getSupCat());
	    o.setTipoEvento(i.getTipoEvento());
	    o.setDataInizioUiu(i.getDataInizioUiu());
	    o.setDataFineUiu(i.getDataFineUiu());
	    o.setDataInizioTit(i.getDataInizioTit());
	    o.setDataFineTit(i.getDataFineTit());
	    
		return o;
	}
	
	private it.webred.ct.service.jaxb.hetGit.response.Immobile copyDtoToJaxb(ImmobiliAccatastatiOutputDTO i, it.webred.ct.service.jaxb.hetGit.response.Immobile o){
		
	    o.setSezione(i.getSezione());
	    o.setFoglio(i.getFoglio());
	    o.setNumero(i.getNumero());
	    o.setSubalterno(i.getSubalterno());
	    o.setSub(i.getSub());
	    o.setPartitaCatastale(i.getPartitaCatastale());
	    o.setCodiceVia(i.getCodiceVia());
	    o.setDescrizioneVia(i.getDescrizioneVia());
	    o.setCivico(i.getCivico());
	    o.setIndirizzoCatastale(i.getIndirizzoCatastale());
	    o.setPercentualePossesso(i.getPercentualePossesso());
	    o.setTipoTitolo(i.getTipoTitolo());
	    o.setDesTipoTitolo(i.getDesTipoTitolo());
	    o.setCategoria(i.getCategoria());
	    o.setClasse(i.getClasse());
	    o.setConsistenza(i.getConsistenza());
	    o.setRendita(i.getRendita());
	    o.setSupCat(i.getSupCat());
	    o.setTipoEvento(i.getTipoEvento());
	    o.setDataInizioUiu(i.getDataInizioUiu());
	    o.setDataFineUiu(i.getDataFineUiu());
	    o.setDataInizioTit(i.getDataInizioTit());
	    o.setDataFineTit(i.getDataFineTit());
	    
		return o;
	}

	@WebMethod
	public String getImmobiliAccatastati(@WebParam(name = "richiestaImmobiliAccatastati") String richiestaImmobiliAccatastati) {
		String response = null;
		
		String jaxbObjectPkgReference = "it.webred.ct.service.jaxb.hetGit.request";
		
		try {
			
			//parsing da xml a jaxb
			it.webred.ct.service.jaxb.hetGit.request.HetGit input = 
				(it.webred.ct.service.jaxb.hetGit.request.HetGit)xml2Jaxb(
						richiestaImmobiliAccatastati,jaxbObjectPkgReference);
			
			//da jaxb a DTO
			ImmobiliAccatastatiInputDTO rdDTO = this.jaxb2DTO(input);
			
			//chiamata metodo
			ImmobiliAccatastatiOutputDTO[] dcDTO = this.getImmobiliAccatastatiDTO(rdDTO);
			
			//da DTO a jaxb
			it.webred.ct.service.jaxb.hetGit.response.HetGit dc = this.dto2Jaxb(dcDTO);
			
			//parsing da jaxb a xml
			response = jaxb2Xml(dc);
		}catch(Exception e) {
			logger.error("Eccezione: "+e.getMessage());
		}
		
		
		return response;
	}


	
	private ImmobiliAccatastatiInputDTO jaxb2DTO(it.webred.ct.service.jaxb.hetGit.request.HetGit input) {
		logger.info("Creazione DTO richiesta da oggetto jaxb");
		
		ImmobiliAccatastatiInputDTO rd = new ImmobiliAccatastatiInputDTO();
		
		rd.setEnteId(input.getGetImmobiliAccatastatiInput().getBaseInput().getEnte());
		//rd.setUserId(input.getInfoUtente().getUserId());
		
		//TODO: Set Parametri richiesta
		rd.setCodiceFiscale(input.getGetImmobiliAccatastatiInput().getCodiceFiscale());
		rd.setCognome(input.getGetImmobiliAccatastatiInput().getCognome());
		rd.setNome(input.getGetImmobiliAccatastatiInput().getNome());
		
	/*	XMLGregorianCalendar dataIni = input.getGetImmobiliAccatastatiInput().getDataInizio();
		String sDataIni = dataIni.getDay()+"/"+dataIni.getMonth()+"/"+dataIni.getYear();*/
		rd.setDataInizio(input.getGetImmobiliAccatastatiInput().getDataInizio());
		
	/*	XMLGregorianCalendar dataFine = input.getGetImmobiliAccatastatiInput().getDataFine();
		String sDataFine = dataFine.getDay()+"/"+dataFine.getMonth()+"/"+dataFine.getYear();*/
		rd.setDataFine(input.getGetImmobiliAccatastatiInput().getDataFine());
		
		logger.info("DTO creato ["+rd.getClass().getName()+"]");
		
		return rd;
	}
	
	
	
	private it.webred.ct.service.jaxb.hetGit.response.HetGit dto2Jaxb(ImmobiliAccatastatiOutputDTO[] dcDTO) {
		logger.info("Creazione oggetto jaxb da DTO risposta");
		
		it.webred.ct.service.jaxb.hetGit.response.ObjectFactory of = 
			new it.webred.ct.service.jaxb.hetGit.response.ObjectFactory();
		
		it.webred.ct.service.jaxb.hetGit.response.HetGit dc = of.createHetGit();
		it.webred.ct.service.jaxb.hetGit.response.GetImmobiliAccatastatiOutput iao = of.createGetImmobiliAccatastatiOutput();
		it.webred.ct.service.jaxb.hetGit.response.ElencoImmobili ei = of.createElencoImmobili();
		
		iao.setElencoImmobili(ei);
		dc.setGetImmobiliAccatastatiOutput(iao);
		
		//Imm
		if(dcDTO != null) {
			
			for(ImmobiliAccatastatiOutputDTO i : dcDTO) {
				it.webred.ct.service.jaxb.hetGit.response.Immobile  imm = of.createImmobile();
				
				imm = copyDtoToJaxb(i, imm);
				
				iao.getElencoImmobili().getImmobile().add(imm);
			}
		}
		
		
		logger.info("Oggeto jaxb creato ["+dc.getClass().getName()+"]");
		
		return dc;
	}
	
}
