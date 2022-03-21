package it.webred.ct.data.access.aggregator.elaborazioni;



import it.webred.ct.data.access.aggregator.elaborazioni.dto.DatiAttesiDTO;
import it.webred.ct.data.access.aggregator.elaborazioni.dto.ZonaDTO;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface CalcoloValoriAttesiService extends Serializable {
	
	/**
	 * Recupero dati zona legati al foglio
	 * @param foglio
	 * @return
	 */
	public List<ZonaDTO> getDatiZona(ZonaDTO foglio);
	
	
	/**
	 * Recupero valori attesi 1
	 * 
	 * @param zona: foglio, codiceCategoriaEdilizia, zonaCensuaria, tipoIntervento, mappale
	 * @return
	 */
	public DatiAttesiDTO getCalcolo1(ZonaDTO zona);
	
	
	/**
	 * Recupero valori attesi 2
	 * 
	 * @param zona: foglio, codiceCategoriaEdilizia, zonaCensuaria, microzona (old e new)
	 * @return
	 */
	public DatiAttesiDTO getCalcolo2(ZonaDTO zona);
	
	
	/**
	 * Recupero valori attesi 3
	 * 
	 * @param zona: foglio, codiceCategoriaEdilizia, zonaCensuaria, microzona (old e new)
	 * @return
	 */
	public DatiAttesiDTO getCalcolo3(ZonaDTO zona);
	
	
	/**
	 * Recuparo classe massima frequente
	 * 
	 * @param zona: foglio, codiceCategoriaEdilizia, zonaCensuaria, flgPostoAuto
	 * @return
	 */
	public DatiAttesiDTO getCalcolo4(ZonaDTO zona);
}
