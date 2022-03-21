package it.webred.ct.data.access.aggregator.elaborazioni;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import it.webred.ct.data.access.aggregator.elaborazioni.dto.ClassamentoDTO;
import it.webred.ct.data.access.aggregator.elaborazioni.dto.ClasseMaxCategoriaDTO;
import it.webred.ct.data.access.aggregator.elaborazioni.dto.DatiAttesiDTO;
import it.webred.ct.data.access.aggregator.elaborazioni.dto.ZonaDTO;
import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.docfa.dao.DocfaDAO;
import it.webred.ct.data.access.basic.docfa.dto.FoglioMicrozonaDTO;
import it.webred.ct.data.model.docfa.DocfaFogliMicrozona;
import it.webred.ct.data.model.docfa.DocfaValori;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Session Bean implementation class CalcoloValoriAttesiServiceBean
 */
@Stateless
public class CalcoloValoriAttesiServiceBean extends CTServiceBaseBean implements CalcoloValoriAttesiService {

	private static final long serialVersionUID = 1L;
	
	
	@Autowired
	private DocfaDAO docfaDAO;
	
    public CalcoloValoriAttesiServiceBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public List<ZonaDTO> getDatiZona(ZonaDTO foglio) {

		List<ZonaDTO> lz = new ArrayList<ZonaDTO>();
		
		logger.info("Recupero dati zona");
	
		
		List<FoglioMicrozonaDTO> llzz = docfaDAO.getFoglioMicrozona(foglio.getFoglio());
		if(llzz != null && llzz.size() > 0) {
			for(FoglioMicrozonaDTO zz: llzz) {
				lz.add(new ZonaDTO(zz.getZc(), foglio.getFoglio(), zz.getOldMicrozona(), zz.getNewMicrozona()));
			}
		}
		
		return lz;
	}

	
	/**
	 * Il metodo è relativo al calcolo 2 (vedi Bod)
	 */
	@Override
	public DatiAttesiDTO getCalcolo2(ZonaDTO zona) {
		
		logger.info("Calcolo valori dati attesi residenziali senza superficie");
		
		DatiAttesiDTO ret = new DatiAttesiDTO();
		
		String vcommMq = "1.0";
		double superficieMedia=0;
		String tipolEdilizia = "";
		
		if(zona.getCodiceCategoriaEdilizia().equals("1")) {
			superficieMedia = 24;
			tipolEdilizia = "ABITAZIONI CIVILI";
		}
		else if(zona.getCodiceCategoriaEdilizia().equals("2")) {
			superficieMedia = 19;
			tipolEdilizia = "ABITAZIONI CIVILI";
		}
		else if(zona.getCodiceCategoriaEdilizia().equals("3")) {
			superficieMedia = 18;
			tipolEdilizia = "ABITAZIONI TIPO ECONOMICO";
		}
		else if(zona.getCodiceCategoriaEdilizia().equals("4")) {
			superficieMedia = 19;
			tipolEdilizia = "ABITAZIONI TIPO ECONOMICO";			
		}
		
		//recupero valore commercial mq
		logger.debug("Recupero valore commerciale mq");
		List<DocfaValori> listaValori = docfaDAO.getDocfaValori(zona.getNewMicrozona(),tipolEdilizia,"Ottimo");
		if (listaValori!= null && listaValori.size() > 0) {
			DocfaValori dv = listaValori.get(0);
			vcommMq = dv.getValMed().toString(); 
			ret.setValoreCommercialeMq(Double.parseDouble(vcommMq));
			logger.debug("valore commerciale mq: "+ret.getValoreCommercialeMq());
		}
		
		//recupero rendita
		logger.info("Recupero rendita");
		Double docfaRapporto = docfaDAO.getDocfaRapporto();
		double denom = docfaRapporto.doubleValue() * 105; 
		double dvcommaMq = Double.parseDouble(vcommMq);
		Double renditaMinimaMq = new Double(dvcommaMq / denom);
		logger.debug("rendita: ("+denom+"/"+dvcommaMq+")"+renditaMinimaMq);
		ret.setRenditaMinima(renditaMinimaMq);
		
		//tariffa per vano
		logger.info("Recupero tariffa vano");		
		Double tariffaVano = new Double(renditaMinimaMq * superficieMedia);
		ret.setTariffaPerVano(tariffaVano);
		logger.debug("tariffa vano: "+ret.getTariffaPerVano());
		
		//recupero classamenti
		logger.info("Recupero classamenti");
		List<ClassamentoDTO> ll = 
			docfaDAO.getClassamenti(zona.getZonaCensuaria(), zona.getCodiceCategoriaEdilizia(), tariffaVano);
		
		ret.setClassamenti(ll);
		
		return ret;
	}

	
	/**
	 * Il metodo è relativo al calcolo 3 (vedi Bod)
	 */
	@Override
	public DatiAttesiDTO getCalcolo3(ZonaDTO zona) {
		logger.info("Colcolo valori dati attesi residenziali verifica classamenti");
		
		DatiAttesiDTO ret = new DatiAttesiDTO();

		String vcommMq = "1.0";
		double superficieMedia=0;
		String tipolEdilizia = "";
		
		if(zona.getCodiceCategoriaEdilizia().equals("1")) {
			superficieMedia = 24;
			tipolEdilizia = "ABITAZIONI CIVILI";
		}
		else if(zona.getCodiceCategoriaEdilizia().equals("2")) {
			superficieMedia = 19;
			tipolEdilizia = "ABITAZIONI CIVILI";
		}
		else if(zona.getCodiceCategoriaEdilizia().equals("3")) {
			superficieMedia = 18;
			tipolEdilizia = "ABITAZIONI TIPO ECONOMICO";
		}
		else if(zona.getCodiceCategoriaEdilizia().equals("4")) {
			superficieMedia = 19;
			tipolEdilizia = "ABITAZIONI TIPO ECONOMICO";			
		}
		
		//recupero valore commercial mq
		logger.debug("Recupero valore commerciale mq");
		List<DocfaValori> listaValori = docfaDAO.getDocfaValori(zona.getNewMicrozona(),tipolEdilizia,"Normale");
		if (listaValori!= null && listaValori.size() > 0) {
			DocfaValori dv = listaValori.get(0);
			vcommMq = dv.getValMed().toString(); 
			ret.setValoreCommercialeMq(Double.parseDouble(vcommMq));
			logger.debug("valore commerciale mq: "+ret.getValoreCommercialeMq());
		}
		
		//recupero rendita
		logger.info("Recupero rendita");
		Double docfaRapporto = docfaDAO.getDocfaRapporto();
		double denom = docfaRapporto.doubleValue() * 105; 
		double dvcommaMq = Double.parseDouble(vcommMq);
		Double renditaMinimaMq = new Double(dvcommaMq / denom);	
		ret.setRenditaMinima(renditaMinimaMq);
		logger.debug("rendita: "+ret.getRenditaMinima());
		
		//tariffa per vano
		logger.info("Recupero tariffa vano");		
		Double tariffaVano = new Double(renditaMinimaMq * superficieMedia);
		ret.setTariffaPerVano(tariffaVano);
		logger.debug("tariffa vano: "+ret.getTariffaPerVano());
		
		//recupero classamenti
		logger.info("Recupero classamenti");
		List<ClassamentoDTO> ll = 
			docfaDAO.getClassamenti(zona.getZonaCensuaria(), zona.getCodiceCategoriaEdilizia(), tariffaVano);
		
		ret.setClassamenti(ll);
		
		return ret;
	}

	@Override
	public DatiAttesiDTO getCalcolo1(ZonaDTO zona) {
		logger.info("Colcolo valori dati attesi residenziali");
		
		DatiAttesiDTO ret = new DatiAttesiDTO();

		//se sono nel caso di ristrutturazione calcolo le classi max per categoria in base al mappale
		if((zona.getTipoIntervento() != null && zona.getTipoIntervento().equals("2")) &&
		   (zona.getFoglio() != null && !zona.getFoglio().equals(""))) {
			
			List<ClasseMaxCategoriaDTO> lcm = docfaDAO.getClassiMaxCategoria(zona.getFoglio(), 
											zona.getCodiceCategoriaEdilizia(), zona.getMappale());
			ret.setClassiMaxCategoria(lcm);
		}
		
		
		String tipologiaEdilizia = "";		
		if(zona.getCodiceCategoriaEdilizia().equals("1")) {
			tipologiaEdilizia = "ABITAZIONI CIVILI";
		}
		else if(zona.getCodiceCategoriaEdilizia().equals("2")) {
			tipologiaEdilizia = "ABITAZIONI TIPO ECONOMICO";
		}
		
		String stato="";
		if(zona.getTipoIntervento() != null && zona.getTipoIntervento().equals("1")) {
			stato = "Ottimo";
		}
		else if(zona.getTipoIntervento() != null && zona.getTipoIntervento().equals("2")) {
			stato = "Normale";
		}
			
		//valore commerciale
		Double valoreCommercialeMq= new Double(0); 
		logger.debug("Recupero valore commerciale");
		List<DocfaValori> listaValori = docfaDAO.getDocfaValori(zona.getNewMicrozona(),tipologiaEdilizia,stato);
		if (listaValori!= null && listaValori.size() > 0) {
			DocfaValori dv = listaValori.get(0);
			valoreCommercialeMq = dv.getValMed().doubleValue();
			ret.setValoreCommercialeMq(valoreCommercialeMq);
			logger.debug("valore commerciale mq: "+ret.getValoreCommercialeMq());
		}
		
		Double valoreComm = new Double(valoreCommercialeMq * zona.getSuperficie());
		ret.setValoreCommerciale(valoreComm);
		logger.debug("valore commerciale: "+ret.getValoreCommerciale());
		
		//rendita
		logger.info("Recupero rendita");
		Double docfaRapporto = docfaDAO.getDocfaRapporto();
		Double denom = docfaRapporto * 105;
		//double denom = docfaRapporto.doubleValue() * 105; 
		Double renditaMinima = valoreComm/denom; //new Double(valoreComm / denom);
		logger.debug("rendita minima: "+renditaMinima);
		ret.setRenditaMinima(renditaMinima);
		logger.debug("rendita: "+ret.getRenditaMinima());
		
		//tariffa
		logger.info("Recupero tariffa vano");	
		Double tariffaVano= renditaMinima / zona.getConsistenza(); //new Double(renditaMinima / zona.getConsistenza());
		logger.debug("tariffa vano: "+ tariffaVano );
		
		ret.setTariffaPerVano(tariffaVano);
		logger.debug("tariffa vano: "+ret.getTariffaPerVano());
		
		//recupero classamenti
		logger.info("Recupero classamenti");
		List<ClassamentoDTO> ll = docfaDAO.getClassamentiComp(zona.getZonaCensuaria(), zona.getCodiceCategoriaEdilizia(), 
				zona.getTipoIntervento(), ret.getClassiMaxCategoria(), zona.isFlgAscensore(), 
				zona.getConsistenza(), tariffaVano);
		
		ret.setClassamenti(ll);
		
		return ret;
	}

	@Override
	public DatiAttesiDTO getCalcolo4(ZonaDTO zona) {
		logger.info("Calcolo valori dati attesi non residenziali");
		String classeMin = null;
		
		DatiAttesiDTO ret = new DatiAttesiDTO();
		
		List<String> listaClassiMin = docfaDAO.getListaClassiMin(zona.getZonaCensuaria(),
										zona.getFoglio(),zona.getCodiceCategoriaEdilizia());
		
		if(listaClassiMin.size()>0)
			classeMin = listaClassiMin.get(0);
		
		if(zona.getCodiceCategoriaEdilizia().equals("C06") && zona.isFlgPostoAuto()) {
			if(classeMin != null && !classeMin.equals("")) {
				classeMin = String.valueOf(Integer.valueOf(classeMin).intValue() - 3);
			}
		}
		
		ret.setClasseMaxFrequente(classeMin);
		
		return ret;
	}

}
