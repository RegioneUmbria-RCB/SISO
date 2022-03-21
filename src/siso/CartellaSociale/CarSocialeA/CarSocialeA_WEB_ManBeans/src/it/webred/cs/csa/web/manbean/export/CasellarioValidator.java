package it.webred.cs.csa.web.manbean.export;

import it.webred.cs.csa.ejb.dto.EsportazioneDTOView;
import it.webred.cs.csa.ejb.dto.EsportazioneTestataDTO;
import it.webred.cs.data.DataModelCostanti.CSIPs;
import it.webred.cs.data.DataModelCostanti.TipiCategoriaSociale;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class CasellarioValidator {

	public static List<EsportazioneTestataDTO> checkPresaInCarico(List<EsportazioneTestataDTO> list, List<EsportazioneTestataDTO> res) {
		List<String> errBeneficiari1 = new ArrayList<String>();
		List<String> errBeneficiari2 = new ArrayList<String>();
		List<String> errBeneficiari3 = new ArrayList<String>();
		Iterator<EsportazioneTestataDTO> it = list.iterator();
		while (it.hasNext()) 
		{
			EsportazioneTestataDTO el = it.next();
			List<EsportazioneDTOView> dettagli = el.getDettagli();
			if (dettagli != null && dettagli.size() > 0) 
			{
				Iterator<EsportazioneDTOView> itd = dettagli.iterator();
				while (itd.hasNext()) 
				{
					EsportazioneDTOView d = itd.next();
					if ((d.getPresaInCarico().intValue()==CSIPs.FLAG_IN_CARICO.NON_SO.getCodice()) && !d.isEsportata())
					{
			            res.add(el);
			            if(!errBeneficiari1.contains(d.getNomeBeneficiario())) 
			            	errBeneficiari1.add(d.getNomeBeneficiario());
			           
			        }else if((d.getPresaInCarico().intValue()==CSIPs.FLAG_IN_CARICO.SI.getCodice()) && !d.isEsportata()){
			           	
			           boolean isCategoriaSina = d.getCategoriaSocialeId().intValue()==TipiCategoriaSociale.ANZIANI_ID.intValue() || 
			        		   					 d.getCategoriaSocialeId().intValue()==TipiCategoriaSociale.DISABILI_ID.intValue();
			           if(isCategoriaSina && (!d.getIsSinaCollegato() || d.getIsSinaFlagValutaDopo())){
			        	 res.add(el);
			        	 
			        	 if(!d.getIsSinaCollegato()){ 
			        		//messaggio = "Compilare i dati SINA";
			        		 if(!errBeneficiari2.contains(d.getNomeBeneficiario())) 
			        			 errBeneficiari2.add(d.getNomeBeneficiario()); 
			        	 }
			        	 if(d.getIsSinaFlagValutaDopo()){ 
			        		//messaggio = "SINA contrassegnato con flag 'Valuta dopo' completare e verificare i dati eventualmente già inseriti";
			        		 if(!errBeneficiari3.contains(d.getNomeBeneficiario())) 
			        			 errBeneficiari3.add(d.getNomeBeneficiario());
			        	 }
			           }
			        }
			    }
				
			}
		}
		
		if(!errBeneficiari1.isEmpty()){
			String beneficiari = "";
			for(String err : errBeneficiari1) beneficiari +=err +"<br/>";
			String messaggio = "Specificare se, durante il servizio, il beneficiario è in carico.<br/> Erogazioni non esportabili:";
			FacesContext.getCurrentInstance().addMessage(null,
			new FacesMessage(FacesMessage.SEVERITY_ERROR, messaggio, beneficiari));
		}
		if(!errBeneficiari2.isEmpty()){
			String beneficiari = "";
			for(String err : errBeneficiari2) beneficiari +=err +"<br/>";
			String messaggio = "Compilare i dati SINA. <br/> Erogazioni non esportabili:";
			FacesContext.getCurrentInstance().addMessage(null,
			new FacesMessage(FacesMessage.SEVERITY_ERROR, messaggio, beneficiari));
		}
		if(!errBeneficiari3.isEmpty()){
			String beneficiari = "";
			for(String err : errBeneficiari3) beneficiari +=err +"<br/>";
			String messaggio = "SINA contrassegnato con flag 'Valuta dopo' completare e verificare i dati eventualmente già inseriti.<br/> Erogazioni non esportabili:";
			FacesContext.getCurrentInstance().addMessage(null,
			new FacesMessage(FacesMessage.SEVERITY_ERROR, messaggio, beneficiari));
		}
		
		return res;
	}

}
