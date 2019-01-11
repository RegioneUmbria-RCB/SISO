package it.webred.cs.csa.ejb.domini;

import it.webred.cs.csa.ejb.client.domini.AccessTableDominiSiruSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.DominioSiruDAO;
import it.webred.cs.csa.ejb.dto.SiruDominioDTO;
import it.webred.cs.csa.ejb.enumeratori.SiruEnum;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Singleton
@Startup
public class AccessTableDominiSiruSessionBean implements AccessTableDominiSiruSessionBeanRemote {
	
	@Autowired
	DominioSiruDAO dominioDAO;
	
	private HashMap<String, List<SiruDominioDTO>> cache;
	
	@PostConstruct
	@Schedule(hour="0", persistent=false) //ricarica i domini ogni notte a mezzanotte
	public void initDominiSingleton(){
		
		cache = new HashMap<String, List<SiruDominioDTO>>();
		//carica valori
		cache.put(SiruEnum.ATECO.name(), dominioDAO.loadSiruAteco());
		cache.put(SiruEnum.DIMENSIONE_AZIENDA.name(), dominioDAO.loadSiruDimAzienda());
		cache.put(SiruEnum.FORMA_GIURIDICA_RNA.name(), dominioDAO.loadSiruFormaGiuridica());
		cache.put(SiruEnum.GRUPPO_VUL_PART.name(), dominioDAO.loadSiruGruppoVulPart());
		cache.put(SiruEnum.IT_COMUNE.name(), dominioDAO.loadSiruItComune());
		cache.put(SiruEnum.IT_NAZIONE.name(), dominioDAO.loadSiruItNazione());
		cache.put(SiruEnum.LOCALIZZAZIONE_GEOG.name(), dominioDAO.loadSiruLocalizzazioneGeog());
		cache.put(SiruEnum.STATO_PARTECIPANTE.name(), dominioDAO.loadSiruStatoPartecipante());
		cache.put(SiruEnum.TIPO_ORARIO_LAVORO.name(), dominioDAO.loadSiruTipoOrarioLavoro());
		cache.put(SiruEnum.TIPOLOGIA_LAVORO.name(), dominioDAO.loadSiruTipologiaLavoro());
		cache.put(SiruEnum.TITOLO_STUDIO.name(), dominioDAO.loadSiruTitoloStudio());
		cache.put(SiruEnum.CITTADINANZA.name(), dominioDAO.loadSiruCittadinanza());
		cache.put(SiruEnum.DURATA_RICERCA.name(), dominioDAO.loadSiruDurataRicerca());
		cache.put(SiruEnum.CONDIZIONE_MERCATO.name(), dominioDAO.loadCondizioneMercato());
		cache.put(SiruEnum.RESIDENZA.name(), dominioDAO.loadResidenza());
		//SISO DOMINI
		cache.put(SiruEnum.SISO_NAZIONE.name(), dominioDAO.loadSisoNazione());
		cache.put(SiruEnum.SISO_ATECO.name(), dominioDAO.loadSisoAteco()); //SISO-850
	}


	@Override
	@Lock(LockType.READ)
	public SiruDominioDTO findById(String chiave, String id){
		SiruDominioDTO toReturn = null;
		if(id != null){
			List<SiruDominioDTO> sdl = cache.get(chiave);
			for(SiruDominioDTO sd : sdl){
				String codice = sd.getCodice().trim().toLowerCase().replaceAll("\\s", "_");
				id = id.trim().toLowerCase().replaceAll("\\s", "_");
				if(codice.equals(id)){
					toReturn = sd;
					break;
				}
			}
		}
		
		return toReturn;
	}
	
	@Override
	@Lock(LockType.READ)
	public SiruDominioDTO findByDesc(String chiave, String id){
		SiruDominioDTO toReturn = null;
		if(id != null){
			List<SiruDominioDTO> sdl = cache.get(chiave);
			for(SiruDominioDTO sd : sdl){
				String desc = sd.getDescrizione().trim().toLowerCase().replaceAll("\\s", "_");
				id = id.trim().toLowerCase().replaceAll("\\s", "_");
				if(desc.equals(id)){
					toReturn = sd;
					break;
				}
			}
		}
		
		return toReturn;
	}
	
	@Override
	@Lock(LockType.READ)
	public SiruDominioDTO findByDescStartsWith(String chiave, String id){
		SiruDominioDTO toReturn = null;
		if(id != null){
			List<SiruDominioDTO> sdl = cache.get(chiave);
			for(SiruDominioDTO sd : sdl){
				String desc = sd.getDescrizione().trim().toLowerCase().replaceAll("\\s", "_");
				id = id.trim().toLowerCase().replaceAll("\\s", "_");
				if(desc.startsWith(id)){
					toReturn = sd;
					break;
				}
			}
		}
		
		return toReturn;
	}


	//SISO-850
	@Override
	@Lock(LockType.READ)
	public List<SiruDominioDTO>findAll(String chiave) {
	        List<SiruDominioDTO> sdl = cache.get(chiave);
	        Iterator<SiruDominioDTO> isdl=sdl.iterator();
	        while(isdl.hasNext()){
	        	int count = StringUtils.countMatches(isdl.next().getCodice(), ".");
	        	if(count <2){
	        		isdl.remove();
	        	}
	        }
			return sdl;
	}
	

	
}
