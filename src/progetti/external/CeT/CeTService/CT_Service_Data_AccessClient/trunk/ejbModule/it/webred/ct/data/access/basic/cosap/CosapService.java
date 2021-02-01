package it.webred.ct.data.access.basic.cosap;

import it.webred.ct.data.access.basic.cosap.dto.DatiCosapDTO;
import it.webred.ct.data.access.basic.cosap.dto.RicercaOggettoCosapDTO;
import it.webred.ct.data.access.basic.cosap.dto.RicercaSoggettoCosapDTO;
import it.webred.ct.data.model.cosap.SitTCosapContrib;
import it.webred.ct.data.model.cosap.SitTCosapTassa;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface CosapService {
	public List<SitTCosapContrib> searchSoggetto(RicercaSoggettoCosapDTO rs);
	public SitTCosapContrib getDatiSoggettoById (RicercaSoggettoCosapDTO rs );
	public List<SitTCosapTassa> getDatiOggettiBySogg(RicercaSoggettoCosapDTO rs);
	public SitTCosapTassa getDatiOggettoById(RicercaOggettoCosapDTO rs);
	public List<SitTCosapTassa> getDatiSintesiOggettiByListaID(RicercaOggettoCosapDTO rs);
	public List<DatiCosapDTO> getDettaglioCosap(RicercaOggettoCosapDTO rs);
}
