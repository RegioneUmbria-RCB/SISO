package it.webred.ct.data.access.basic.ici;
import it.webred.ct.data.access.basic.common.dto.RicercaOggettoDTO;
import it.webred.ct.data.access.basic.ici.dto.IciDTO;
import it.webred.ct.data.access.basic.ici.dto.OggettoIciDTO;
import it.webred.ct.data.access.basic.ici.dto.RicercaOggettoIciDTO;
import it.webred.ct.data.access.basic.ici.dto.RicercaSoggettoIciDTO;
import it.webred.ct.data.access.basic.ici.dto.RicercaViaIciDTO;
import it.webred.ct.data.access.basic.ici.dto.VersamentoIciAnnoDTO;
import it.webred.ct.data.model.ici.SitTIciOggetto;
import it.webred.ct.data.model.ici.SitTIciSogg;
import it.webred.ct.data.model.ici.SitTIciVia;
import it.webred.ct.data.model.ici.VTIciSoggAll;

import java.util.List;

import javax.ejb.Remote;
 
@Remote
public interface IciService {
	public List<String> getListaProvenienzaIci(RicercaOggettoIciDTO ro);
	public List<SitTIciSogg> getListaSogg(RicercaSoggettoIciDTO rs);
	public SitTIciSogg getSoggettoById(RicercaSoggettoIciDTO rs);
	public List<OggettoIciDTO> getListaOggettiByIdSogg(RicercaSoggettoIciDTO rs);
	
	public List<VTIciSoggAll> getListaSoggettiByOgg(RicercaOggettoIciDTO ro);
	public List<SitTIciSogg> searchSoggetto(RicercaSoggettoIciDTO rs);
	public List<VersamentoIciAnnoDTO> getSommaVersamenti(RicercaSoggettoIciDTO rs);
	public SitTIciVia getViaByIdExt(RicercaViaIciDTO rv);
	
	public List<SitTIciOggetto> getOggettiByFabbricato(RicercaOggettoDTO ro);
	public List<SitTIciOggetto> getOggettiByUI(RicercaOggettoDTO ro);
	
	public List<SitTIciOggetto> getListaOggettiAiCiviciIci(RicercaOggettoIciDTO ro);
	public List<IciDTO> getListaIciByFPSOgg(RicercaOggettoDTO ro);
	
	public List<SitTIciOggetto> getListaOggettiByListaIdOggDWh(	List<SitTIciOggetto> listaKey);
	public List<SitTIciOggetto> getListaDistintaOggettiByIdSogg(
			RicercaSoggettoIciDTO rs); 
}
