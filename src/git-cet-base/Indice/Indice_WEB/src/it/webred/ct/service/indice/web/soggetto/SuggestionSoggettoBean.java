package it.webred.ct.service.indice.web.soggetto;

import it.webred.ct.data.access.basic.indice.IndiceDataIn;
import it.webred.ct.data.access.basic.indice.dto.IndiceSearchCriteria;
import it.webred.ct.data.access.basic.indice.dto.ListaTotaleBySorgente;
import it.webred.ct.data.access.basic.indice.dto.ListaUnico;
import it.webred.ct.data.access.basic.indice.soggetto.dto.SitSoggettoDTO;
import it.webred.ct.data.access.basic.indice.via.dto.SitViaDTO;
import it.webred.ct.data.model.indice.SitSoggettoUnico;

import java.util.ArrayList;
import java.util.List;

public class SuggestionSoggettoBean extends SoggettoBaseBean{
	
	IndiceSearchCriteria suggestionCriteria = new IndiceSearchCriteria();
	
	public List<SitSoggettoUnico> getSuggestionUnicoByDenominazione(Object obj){
		
		String denom = (String)obj;
		suggestionCriteria.setDenominazione(denom);
		
		List<SitSoggettoUnico> result = new ArrayList<SitSoggettoUnico>();
		String msg = "suggestion.soggettobean";
		
		if (denom != null && !denom.trim().equalsIgnoreCase("NULL") && denom.length() >= 3) {
			try{
				IndiceDataIn indDataIn = new IndiceDataIn();
				ListaUnico lu = new ListaUnico();
				lu.setCriteria(suggestionCriteria);
				lu.setStartm(0);
				lu.setNumberRecord(10);
				indDataIn.setListaUnico(lu);
				fillEnte(indDataIn);
				
				result = (List<SitSoggettoUnico>)indiceService.getListaUnico(indDataIn);
				//result = (List<SitSoggettoUnico>)indiceService.getListaUnico(suggestionCriteria, 0, 10);
				
				System.out.println("------------------------------------------------------"+result.size());
			}catch(Throwable t) {
				super.addErrorMessage(msg+".error", t.getMessage());
				t.printStackTrace();
			}
		}
		return result;
		
	}
	
	public List<SitSoggettoDTO> getSuggestionTotaleByDenominazione(Object obj){
		
		String denom = (String)obj;
		suggestionCriteria.setDenominazione(denom);
		
		List<SitSoggettoDTO> result = new ArrayList<SitSoggettoDTO>();
		String msg = "suggestion.soggettobean";
		
		if (denom != null && !denom.trim().equalsIgnoreCase("NULL") && denom.length() >= 3) {
			try{
				
				IndiceDataIn indDataIn = new IndiceDataIn();
				ListaTotaleBySorgente lts = new ListaTotaleBySorgente();
				lts.setCriteria(suggestionCriteria);
				lts.setStartm(0);
				lts.setNumberRecord(10);
				indDataIn.setListaTotaleBySorgente(lts);
				fillEnte(indDataIn);
				
				result = (List<SitSoggettoDTO>)indiceService.getListaTotaleBySorgente(indDataIn);
				//result = (List<SitSoggettoDTO>)indiceService.getListaTotaleBySorgente(suggestionCriteria, 0, 10);
				
				indDataIn = new IndiceDataIn();
				for(SitSoggettoDTO dto: result){
					indDataIn.setObj(dto.getFkEnteSorgente());
					String fonteDati = indiceService.getEnteSorgente(indDataIn).getDescrizione();
					dto.setFonteDati(fonteDati);
				}
				
				System.out.println("------------------------------------------------------"+result.size());
			}catch(Throwable t) {
				super.addErrorMessage(msg+".error", t.getMessage());
				t.printStackTrace();
			}
		}
		return result;
		
	}
	
	public List<SitSoggettoUnico> getSuggestionUnicoByCodiceFiscale(Object obj){
		
		String codfis = (String)obj;
		suggestionCriteria.setCodiceFiscale(codfis);
		
		List<SitSoggettoUnico> result = new ArrayList<SitSoggettoUnico>();
		String msg = "suggestion.soggettobean";
		
		if (codfis != null && !codfis.trim().equalsIgnoreCase("NULL") && codfis.length() >= 3) {
			try{
				
				IndiceDataIn indDataIn = new IndiceDataIn();
				ListaUnico lu = new ListaUnico();
				lu.setCriteria(suggestionCriteria);
				lu.setStartm(0);
				lu.setNumberRecord(10);
				indDataIn.setListaUnico(lu);
				fillEnte(indDataIn);
				
				result = (List<SitSoggettoUnico>)indiceService.getListaUnico(indDataIn);
				//result = (List<SitSoggettoUnico>)indiceService.getListaUnico(suggestionCriteria, 0, 10);				
				
				System.out.println("------------------------------------------------------"+result.size());
			}catch(Throwable t) {
				super.addErrorMessage(msg+".error", t.getMessage());
				t.printStackTrace();
			}
		}
		return result;
		
	}
	
	public List<SitSoggettoDTO> getSuggestionTotaleByCodiceFiscale(Object obj){
		
		String codfis = (String)obj;
		suggestionCriteria.setCodiceFiscale(codfis);
		
		List<SitSoggettoDTO> result = new ArrayList<SitSoggettoDTO>();
		String msg = "suggestion.soggettobean";
		
		if (codfis != null && !codfis.trim().equalsIgnoreCase("NULL") && codfis.length() >= 3) {
			try{
				
				IndiceDataIn indDataIn = new IndiceDataIn();
				ListaTotaleBySorgente lts = new ListaTotaleBySorgente();
				lts.setCriteria(suggestionCriteria);
				lts.setStartm(0);
				lts.setNumberRecord(10);
				indDataIn.setListaTotaleBySorgente(lts);
				fillEnte(indDataIn);
				
				result = (List<SitSoggettoDTO>)indiceService.getListaTotaleBySorgente(indDataIn);
				//result = (List<SitSoggettoDTO>)indiceService.getListaTotaleBySorgente(suggestionCriteria, 0, 10);
				
				indDataIn = new IndiceDataIn();
				for(SitSoggettoDTO dto: result){
					indDataIn.setObj(dto.getFkEnteSorgente());
					String fonteDati = indiceService.getEnteSorgente(indDataIn).getDescrizione();
					dto.setFonteDati(fonteDati);
				}
				
				System.out.println("------------------------------------------------------"+result.size());
			}catch(Throwable t) {
				super.addErrorMessage(msg+".error", t.getMessage());
				t.printStackTrace();
			}
		}
		return result;
		
	}
	
	public List<SitSoggettoUnico> getSuggestionUnicoByPartitaIva(Object obj){
		
		String pi = (String)obj;
		suggestionCriteria.setPartitaIva(pi);
		
		List<SitSoggettoUnico> result = new ArrayList<SitSoggettoUnico>();
		String msg = "suggestion.soggettobean";
		
		if (pi != null && !pi.trim().equalsIgnoreCase("NULL") && pi.length() >= 3) {
			try{
				
				IndiceDataIn indDataIn = new IndiceDataIn();
				ListaUnico lu = new ListaUnico();
				lu.setCriteria(suggestionCriteria);
				lu.setStartm(0);
				lu.setNumberRecord(10);
				indDataIn.setListaUnico(lu);
				fillEnte(indDataIn);
				
				result = (List<SitSoggettoUnico>)indiceService.getListaUnico(indDataIn);
				//result = (List<SitSoggettoUnico>)indiceService.getListaUnico(suggestionCriteria, 0, 10);
				
				System.out.println("------------------------------------------------------"+result.size());
			}catch(Throwable t) {
				super.addErrorMessage(msg+".error", t.getMessage());
				t.printStackTrace();
			}
		}
		return result;
		
	}
	
	public List<SitSoggettoDTO> getSuggestionTotaleByPartitaIva(Object obj){
		
		String pi = (String)obj;
		suggestionCriteria.setPartitaIva(pi);
		
		List<SitSoggettoDTO> result = new ArrayList<SitSoggettoDTO>();
		String msg = "suggestion.soggettobean";
		
		if (pi != null && !pi.trim().equalsIgnoreCase("NULL") && pi.length() >= 3) {
			try{
				
				IndiceDataIn indDataIn = new IndiceDataIn();
				ListaTotaleBySorgente lts = new ListaTotaleBySorgente();
				lts.setCriteria(suggestionCriteria);
				lts.setStartm(0);
				lts.setNumberRecord(10);
				indDataIn.setListaTotaleBySorgente(lts);
				fillEnte(indDataIn);
				
				result = (List<SitSoggettoDTO>)indiceService.getListaTotaleBySorgente(indDataIn);
				//result = (List<SitSoggettoDTO>)indiceService.getListaTotaleBySorgente(suggestionCriteria, 0, 10);
				
				indDataIn = new IndiceDataIn();
				for(SitSoggettoDTO dto: result){
					indDataIn.setObj(dto.getFkEnteSorgente());
					String fonteDati = indiceService.getEnteSorgente(indDataIn).getDescrizione();
					dto.setFonteDati(fonteDati);
				}
				
				System.out.println("------------------------------------------------------"+result.size());
			}catch(Throwable t) {
				super.addErrorMessage(msg+".error", t.getMessage());
				t.printStackTrace();
			}
		}
		return result;
		
	}
	
}
