package it.webred.ct.data.access.basic.tarsu.dao;

import it.webred.ct.data.access.basic.common.dto.RicercaOggettoDTO;
import it.webred.ct.data.access.basic.diagnostiche.tarsu.dto.SitTTarOggettoDTO;
import it.webred.ct.data.access.basic.tarsu.TarsuServiceException;
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

public interface TarsuDAO {
	
	public List<SitTTarSogg> getListaSoggettiF(RicercaSoggettoTarsuDTO rs)throws TarsuServiceException;
	
	public List<SitTTarSogg> getListaSoggettiG(RicercaSoggettoTarsuDTO rs) throws TarsuServiceException;
	
	public List<SitTTarSogg> getSoggettoByCF(RicercaSoggettoTarsuDTO rs) throws TarsuServiceException;
	
	public List<SitTTarSogg> getSoggettoByPIVA(RicercaSoggettoTarsuDTO rs) throws TarsuServiceException;
	
	public Long getDichTarsuRecordCount(RicercaOggettoTarsuDTO ro) throws TarsuServiceException;
	
	public List<SintesiDichiarazioneTarsuDTO> getListaDichiarazioniTarsu(RicercaOggettoTarsuDTO ro) throws TarsuServiceException;
	
	public List<SoggettoTarsuDTO> getListaSoggettiDichiarazioneTarsu(RicercaOggettoTarsuDTO ro) throws TarsuServiceException;

	public List<SitTTarOggetto> getListaDichiarazioniTarsu(RicercaOggettoTarsuParCatDTO ro) throws TarsuServiceException;
	
	public List<InformativaTarsuDTO> getListaInformativaTarsu(RicercaOggettoTarsuDTO ro) throws TarsuServiceException;	
	
	public InformativaTarsuDTO getInformativaTarsu(RicercaOggettoTarsuDTO ro) throws TarsuServiceException;
	
	public List<OggettoTarsuDTO> getListaOggettiByIdSogg(RicercaSoggettoTarsuDTO rs) throws TarsuServiceException;
	
	public List<SitTTarOggetto> getListaOggettiByProvFabbricato(RicercaOggettoDTO ro) throws TarsuServiceException;
	
	public List<SitTTarOggetto> getListaOggettiByUI(RicercaOggettoDTO ro) throws TarsuServiceException;
	
	public List<SitTTarOggetto> getListaOggettiAiCiviciTarsu(RicercaOggettoTarsuDTO ro) 	throws TarsuServiceException;

	public List<SitTTarSogg> searchSoggetto(RicercaSoggettoTarsuDTO rs) throws TarsuServiceException;
	
	public SitTTarSogg getSoggettoById(RicercaSoggettoTarsuDTO rs) throws TarsuServiceException;
	
	public SitTTarVia getViaByIdExt(RicercaViaTarsuDTO rv) throws TarsuServiceException;
	
	public SitTTarOggetto getOggettoByIdExt(RicercaOggettoTarsuDTO ro)throws TarsuServiceException;
	
	public List<String> getListaProvenienzaTarsu();

	public List<SitTTarOggettoDTO> getListaTarsuUiu(RicercaOggettoDTO ro) throws TarsuServiceException;
	
	public List<SitTTarOggetto> getListaOggettiByListaIdOggDWh(	List<SitTTarOggetto> listaKey) throws TarsuServiceException;
}
