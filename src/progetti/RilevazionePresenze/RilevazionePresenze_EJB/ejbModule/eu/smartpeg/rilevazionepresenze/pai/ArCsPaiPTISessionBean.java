package eu.smartpeg.rilevazionepresenze.pai;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

import eu.smartpeg.rievazionepresenze.dto.pai.ArCsPaiPTIDTO;
import eu.smartpeg.rievazionepresenze.dto.pai.ArCsPaiPTIDocumentoDTO;
import eu.smartpeg.rilevazionepresenze.data.model.pai.ArCsPaiPtiDocumento;
import eu.smartpeg.rilevazionepresenze.pai.ejb.dao.ArCsPaiPTIDAO;

/**
 * Session Bean implementation class ArCsPaiPTISessionBean
 */
@Stateless
@LocalBean
public class ArCsPaiPTISessionBean implements ArCsPaiPTISessionBeanRemote, ArCsPaiPTISessionBeanLocal {

    /**
     * Default constructor. 
     */
    public ArCsPaiPTISessionBean() {
        // TODO Auto-generated constructor stub
    }
    
    @Autowired
	private ArCsPaiPTIDAO arCsPaiPTIDAO;
    
    public List<ArCsPaiPTIDTO> findMinoriByIdStruttura(Long idStruttura) {
		
		
		return arCsPaiPTIDAO.findMinoriByIdStruttura(idStruttura);
	}
    
    public void salvaInserimento(ArCsPaiPTIDTO minore) {
    	arCsPaiPTIDAO.saveMinore(minore);
    }

	@Override
	public List<ArCsPaiPTIDocumentoDTO> findDocumentiRichiestaSelezionata(String codRouting, Long idPaiPTI) {
		return arCsPaiPTIDAO.findDocumentiRichiestaSelezionata(codRouting, idPaiPTI);
	}

	@Override
	public boolean hasPaiDoc(String codRouting, Long idPaiPTI) {
		return arCsPaiPTIDAO.hasPaiDoc(codRouting, idPaiPTI);
	}
	
	@Override
	public Long salvaDocumento(ArCsPaiPTIDocumentoDTO documentoPAI) {
		ArCsPaiPtiDocumento docSalvato = arCsPaiPTIDAO.salvaDocumento(documentoPAI);
		return docSalvato.getId();
	}

	
}
