package it.webred.ct.data.access.basic.tarsu;

import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaOggettoDTO;
import it.webred.ct.data.access.basic.diagnostiche.tarsu.dto.SitTTarOggettoDTO;
import it.webred.ct.data.access.basic.ici.dto.OggettoIciDTO;
import it.webred.ct.data.access.basic.ici.dto.RicercaSoggettoIciDTO;
import it.webred.ct.data.access.basic.tarsu.dto.InformativaTarsuDTO;
import it.webred.ct.data.access.basic.tarsu.dto.OggettoTarsuDTO;
import it.webred.ct.data.access.basic.tarsu.dto.RicercaOggettoTarsuDTO;
import it.webred.ct.data.access.basic.tarsu.dto.RicercaOggettoTarsuParCatDTO;
import it.webred.ct.data.access.basic.tarsu.dto.RicercaSoggettoTarsuDTO;
import it.webred.ct.data.access.basic.tarsu.dto.RicercaViaTarsuDTO;
import it.webred.ct.data.access.basic.tarsu.dto.SintesiDichiarazioneTarsuDTO;
import it.webred.ct.data.access.basic.tarsu.dto.SoggettoTarsuDTO;
import it.webred.ct.data.model.tarsu.SitTTarOggetto;
import it.webred.ct.data.model.tarsu.SitTTarSogg;
import it.webred.ct.data.model.tarsu.SitTTarVia;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface TarsuService {
	//Suggerimenti 
	
	public List<SitTTarSogg> getListaSoggettiF(RicercaSoggettoTarsuDTO rs);
	
	public List<SitTTarSogg> getListaSoggettiG(RicercaSoggettoTarsuDTO rs);
	
	public List<SitTTarSogg> getSoggettoByCF(RicercaSoggettoTarsuDTO rs);
	
	public List<SitTTarSogg> getSoggettoByPIVA(RicercaSoggettoTarsuDTO rs);
	
	//Query di ricerca
	public List<SintesiDichiarazioneTarsuDTO> getListaDichiarazioniTarsu(RicercaOggettoTarsuDTO ro);
	
	public List<SitTTarOggetto> getListaDichiarazioniTarsu(RicercaOggettoTarsuParCatDTO ro);
	
	public Long getDichTarsuRecordCount(RicercaOggettoTarsuDTO ro) ;
	
	//Ricerca del soggetto in Tarsu 
	public List<SitTTarSogg> searchSoggetto(RicercaSoggettoTarsuDTO rs);
	
	public SitTTarSogg getSoggettoById(RicercaSoggettoTarsuDTO rs);
	
	//Estrazione dettaglio informativo TARSU
	public InformativaTarsuDTO getInformativaTarsu(RicercaOggettoTarsuDTO ro);
	
	public List<InformativaTarsuDTO> getListaInformativaTarsu(RicercaOggettoTarsuDTO ro);
	
	public List<SoggettoTarsuDTO> getListaSoggettiDichiarazioneTarsu(RicercaOggettoTarsuDTO ro);
	
	public List<OggettoTarsuDTO> getListaOggettiByIdSogg(RicercaSoggettoTarsuDTO rs);
	
	public List<SitTTarOggetto> getListaOggettiByFabbricato(RicercaOggettoDTO ro);
	
	public List<SitTTarOggetto> getListaOggettiByUI(RicercaOggettoDTO ro) ;
	
	public List<SitTTarOggettoDTO> getListaOggettiDTOByUI(RicercaOggettoDTO ro) ;
	
	public List<SitTTarOggetto> getListaOggettiAiCiviciTarsu(RicercaOggettoTarsuDTO ro);
	
	public SitTTarOggetto getOggettoByIdExt(RicercaOggettoTarsuDTO ro);
	
	public List<SitTTarOggetto> getListaOggettiByListaIdOggDWh(List<SitTTarOggetto> listaKey);
	//DATI VIA
	public SitTTarVia getViaByIdExt(RicercaViaTarsuDTO rv);
		
}
