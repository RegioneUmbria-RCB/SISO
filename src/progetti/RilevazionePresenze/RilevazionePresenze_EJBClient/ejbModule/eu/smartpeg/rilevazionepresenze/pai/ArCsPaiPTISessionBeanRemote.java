package eu.smartpeg.rilevazionepresenze.pai;

import java.util.List;

import javax.ejb.Remote;

import eu.smartpeg.rievazionepresenze.dto.pai.ArCsPaiPTIDTO;
import eu.smartpeg.rievazionepresenze.dto.pai.ArCsPaiPTIDocumentoDTO;

@Remote
public interface ArCsPaiPTISessionBeanRemote {

	public List<ArCsPaiPTIDTO> findMinoriByIdStruttura(Long idStruttura);
	public void salvaInserimento(ArCsPaiPTIDTO minore);
	public List<ArCsPaiPTIDocumentoDTO> findDocumentiRichiestaSelezionata(String codRouting, Long idPaiPTI);
	public boolean hasPaiDoc(String codRouting, Long idPaiPTI);
	public Long salvaDocumento(ArCsPaiPTIDocumentoDTO documentoPAI);

	
}
