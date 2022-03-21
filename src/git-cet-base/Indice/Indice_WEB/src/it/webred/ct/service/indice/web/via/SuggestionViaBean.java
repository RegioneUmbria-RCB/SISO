package it.webred.ct.service.indice.web.via;

import it.webred.ct.data.access.basic.indice.IndiceDataIn;
import it.webred.ct.data.access.basic.indice.dto.IndiceSearchCriteria;
import it.webred.ct.data.access.basic.indice.dto.ListaTotaleBySorgente;
import it.webred.ct.data.access.basic.indice.dto.ListaUnico;
import it.webred.ct.data.access.basic.indice.via.dto.SitViaDTO;
import it.webred.ct.data.model.indice.SitViaUnico;

import java.util.ArrayList;
import java.util.List;

public class SuggestionViaBean extends ViaBaseBean{
	
	IndiceSearchCriteria suggestionCriteria = new IndiceSearchCriteria();
	
	public List<SitViaUnico> getSuggestionUnicoByIndirizzo(Object obj){
		
		String via = (String)obj;
		suggestionCriteria.setIndirizzo(via);
		
		List<SitViaUnico> result = new ArrayList<SitViaUnico>();
		String msg = "suggestion.viabean";
		
		if (via != null && !via.trim().equalsIgnoreCase("NULL") && via.length() >= 3) {
			try{
				
				IndiceDataIn indDataIn = new IndiceDataIn();
				ListaUnico lu = new ListaUnico();
				lu.setCriteria(suggestionCriteria);
				lu.setStartm(0);
				lu.setNumberRecord(10);
				indDataIn.setListaUnico(lu);
				fillEnte(indDataIn);
				
				result = (List<SitViaUnico>)indiceService.getListaUnico(indDataIn);
				//result = (List<SitViaUnico>)indiceService.getListaUnico(suggestionCriteria, 0, 10);
				
				System.out.println("------------------------------------------------------"+result.size());
			}catch(Throwable t) {
				super.addErrorMessage(msg+".error", t.getMessage());
				t.printStackTrace();
			}
		}
		return result;
		
	}
	
	public List<SitViaDTO> getSuggestionTotaleByIndirizzo(Object obj){
		
		String via = (String)obj;
		suggestionCriteria.setIndirizzo(via);
		
		List<SitViaDTO> result = new ArrayList<SitViaDTO>();
		String msg = "suggestion.viabean";
		
		if (via != null && !via.trim().equalsIgnoreCase("NULL") && via.length() >= 3) {
			try{
				
				IndiceDataIn indDataIn = new IndiceDataIn();
				ListaTotaleBySorgente lts = new ListaTotaleBySorgente();
				lts.setCriteria(suggestionCriteria);
				lts.setStartm(0);
				lts.setNumberRecord(10);
				indDataIn.setListaTotaleBySorgente(lts);
				fillEnte(indDataIn);
				
				result = (List<SitViaDTO>)indiceService.getListaTotaleBySorgente(indDataIn);
				//result = (List<SitViaDTO>)indiceService.getListaTotaleBySorgente(suggestionCriteria, 0, 10);
				
				indDataIn = new IndiceDataIn();
				for(SitViaDTO dto: result){
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
