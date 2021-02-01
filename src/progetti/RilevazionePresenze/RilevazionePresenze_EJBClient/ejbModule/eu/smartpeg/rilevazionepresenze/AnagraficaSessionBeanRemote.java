package eu.smartpeg.rilevazionepresenze;

import java.util.List;
import javax.ejb.Remote;

import eu.smartpeg.rilevazionepresenze.data.dto.RpSearchCriteria;
import eu.smartpeg.rilevazionepresenze.data.model.Anagrafica;
import eu.smartpeg.rilevazionepresenze.data.model.DocumentiAnag;
import eu.smartpeg.rilevazionepresenze.data.model.TipoDocumento;


@Remote
public interface AnagraficaSessionBeanRemote {		
	public List<Anagrafica> findAll();
	public List<Anagrafica> findAllReferenti();
	public List<TipoDocumento> findTipoDocumento();
	public void eliminaDocumento(long idDocumento);
	public Long aggiungiDocumento(DocumentiAnag documento);
	public String validaAnagrafica(Anagrafica anagrafica);
	public DocumentiAnag findDocumentoByID(Long idDocumento);
	public Long salva(Anagrafica anagrafica);
	public void eliminaAnagrafica(Anagrafica selectedAnagrafica);
	public String validaEliminazioneAnagrafica(Anagrafica anagrafica);
	public Anagrafica findReferenteByID(Long idReferente);
	public Anagrafica findByID(long idAnagrafica);
	public List<Anagrafica> searchAnagraficaRPBySoggetto(RpSearchCriteria dto);
	public Anagrafica findAnagraficaById(Long idAnagrafica);
	public Anagrafica findAnagraficaByCf(String cf);
}
