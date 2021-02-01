package it.webred.ct.data.access.basic.cosap.dao;

import it.webred.ct.data.access.basic.cosap.CosapServiceException;
import it.webred.ct.data.access.basic.cosap.dto.RicercaOggettoCosapDTO;
import it.webred.ct.data.access.basic.cosap.dto.RicercaSoggettoCosapDTO;
import it.webred.ct.data.model.cosap.SitTCosapContrib;
import it.webred.ct.data.model.cosap.SitTCosapTassa;


import java.util.List;

public interface CosapDAO {
	public List<SitTCosapContrib> searchSoggetto(RicercaSoggettoCosapDTO rs) throws CosapServiceException;
	public SitTCosapContrib getDatiSoggettoById (RicercaSoggettoCosapDTO rs ) throws CosapServiceException;
	public List<SitTCosapTassa> getDatiOggettiByIdSogg(RicercaSoggettoCosapDTO rs) throws CosapServiceException;
	public SitTCosapTassa getDatiOggettoById(RicercaOggettoCosapDTO rs) throws CosapServiceException;
	public List<SitTCosapTassa> getDatiSintesiOggettiByListaID(RicercaOggettoCosapDTO rs) throws CosapServiceException;
	public List<SitTCosapTassa> getDettaglioCosap(RicercaOggettoCosapDTO rs) throws CosapServiceException ;
	public List<SitTCosapContrib> getListaSoggettiByIdExt (RicercaSoggettoCosapDTO rs ) throws CosapServiceException;
}
