package it.webred.ct.data.access.basic.ici.dao;

import it.webred.ct.data.access.basic.common.dto.RicercaOggettoDTO;
import it.webred.ct.data.access.basic.ici.IciServiceException;
import it.webred.ct.data.access.basic.ici.dto.OggettoIciDTO;
import it.webred.ct.data.access.basic.ici.dto.RicercaOggettoIciDTO;
import it.webred.ct.data.access.basic.ici.dto.RicercaSoggettoIciDTO;
import it.webred.ct.data.access.basic.ici.dto.VersamentoIciAnnoDTO;
import it.webred.ct.data.access.basic.ici.dto.RicercaViaIciDTO;
import it.webred.ct.data.access.basic.indice.ricerca.RicercaIndiceDTO;
import it.webred.ct.data.model.ici.SitTIciOggetto;
import it.webred.ct.data.model.ici.SitTIciSogg;
import it.webred.ct.data.model.ici.SitTIciVia;
import it.webred.ct.data.model.ici.VTIciSoggAll;


import java.util.List;

public interface IciDAO {
	public List<String> getListaProvenienzaIci()throws IciServiceException;
	public List<SitTIciSogg> getListaSoggByCF(RicercaSoggettoIciDTO rs) throws IciServiceException;
	public SitTIciSogg getSoggettoById(RicercaSoggettoIciDTO rs) throws IciServiceException;
	public List<OggettoIciDTO> getListaOggettiByIdSogg(RicercaSoggettoIciDTO rs) throws IciServiceException;
	public List<SitTIciOggetto> getListaDistintaOggettiByIdSogg(RicercaSoggettoIciDTO rs) throws IciServiceException;
	
	public List<VTIciSoggAll> getListaSoggettiByFPSOgg(RicercaOggettoDTO ro) throws IciServiceException;
	public List<VTIciSoggAll> getListaSoggettiByOgg(RicercaOggettoIciDTO ro) throws IciServiceException;
	public List<SitTIciSogg> searchSoggetto(RicercaSoggettoIciDTO rs) throws IciServiceException;
	public List<VersamentoIciAnnoDTO> getSommaVersamenti(RicercaSoggettoIciDTO rs) throws IciServiceException;
	public SitTIciVia getViaByIdExt(RicercaViaIciDTO rv) throws IciServiceException;
	
	public List<SitTIciOggetto> getOggettiByProvFabbricato(RicercaOggettoDTO ro) throws IciServiceException;
	public List<SitTIciOggetto> getOggettiByProvUI(RicercaOggettoDTO ro) throws IciServiceException;
	
	public List<SitTIciOggetto> getListaOggettiAiCiviciIci(RicercaOggettoIciDTO ro) throws IciServiceException;
	public List<SitTIciOggetto> getListaOggettiByListaIdOggDWh (List<SitTIciOggetto> listaKey) throws IciServiceException;
	
}
 