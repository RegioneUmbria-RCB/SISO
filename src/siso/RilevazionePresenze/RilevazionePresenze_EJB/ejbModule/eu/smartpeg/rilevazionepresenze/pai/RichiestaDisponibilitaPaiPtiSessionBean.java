package eu.smartpeg.rilevazionepresenze.pai;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import eu.smartpeg.rievazionepresenze.dto.pai.ArCsPaiInfoSinteticheDTO;
import eu.smartpeg.rievazionepresenze.dto.pai.ArCsPaiPTIDocumentoDTO;
import eu.smartpeg.rievazionepresenze.dto.pai.ArCsPaiPtiConsuntiDTO;
import eu.smartpeg.rievazionepresenze.dto.pai.RichiestaDisponibilitaPaiPtiDTO;
import eu.smartpeg.rilevazionepresenze.data.model.pai.ArCsPaiPtiConsunti;
import eu.smartpeg.rilevazionepresenze.data.model.pai.ArCsPaiPtiDocumento;
import eu.smartpeg.rilevazionepresenze.data.model.pai.RichiestaDisponibilitaPaiPti;
import eu.smartpeg.rilevazionepresenze.pai.ejb.dao.ArCsPaiPTIDAO;
import eu.smartpeg.rilevazionepresenze.pai.ejb.dao.RichiestaDisponibilitaPaiPtiDAO;

/**
 * Session Bean implementation class RichiestaDisponibilitaPaiPtiSessionBean
 */
@Stateless
@LocalBean
public class RichiestaDisponibilitaPaiPtiSessionBean
		implements RichiestaDisponibilitaPaiPtiSessionBeanRemote, RichiestaDisponibilitaPaiPtiSessionBeanLocal {

	protected static Logger logger = Logger.getLogger(RichiestaDisponibilitaPaiPtiSessionBean.class);

	/**
	 * Default constructor.
	 */
	public RichiestaDisponibilitaPaiPtiSessionBean() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private RichiestaDisponibilitaPaiPtiDAO richiestaDisponibilitaDAO;

	@Autowired
	private ArCsPaiPTIDAO arCsPaiPTIDAO;

	public void salva(RichiestaDisponibilitaPaiPtiDTO richiesta) {
		try {
			RichiestaDisponibilitaPaiPti res = richiestaDisponibilitaDAO.saveRichiesta(richiesta);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	public List<RichiestaDisponibilitaPaiPtiDTO> findRichiestaByIdStruttura(Long idStruttura) {

		return richiestaDisponibilitaDAO.findRichDispByStrutturaId(idStruttura);
	}

	@Override
	public List<RichiestaDisponibilitaPaiPtiDTO> findRichiestaByStrutturaStato(Long idStruttura, String statoRich) {
		return richiestaDisponibilitaDAO.findRichDispByStrutturaStato(idStruttura, statoRich);
	}

	@Override
	public void salvaInfoSintetiche(ArCsPaiInfoSinteticheDTO infoSintetichePAI) {
		// TODO Auto-generated method stub
		arCsPaiPTIDAO.salvaInfoSintetichePai(infoSintetichePAI);
	}

	@Override
	public List<ArCsPaiPtiConsuntiDTO> findListaConsuntivazione(String codRouting, Long idPaiPTI) {

		return arCsPaiPTIDAO.findListaConsuntivazione(codRouting, idPaiPTI);

	}

	@Override
	public ArCsPaiPtiConsuntiDTO salvaConsuntivazione(ArCsPaiPtiConsuntiDTO consuntivazione) {
		try {
			ArCsPaiPtiConsunti res = richiestaDisponibilitaDAO.saveConsuntivazione(toEntity(consuntivazione));
			return toDto(res);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;

	}
	
	@Override
	public void eliminaConsuntivazione(ArCsPaiPtiConsuntiDTO consuntivazione) {
		try {
			richiestaDisponibilitaDAO.deleteConsuntivazione(toEntity(consuntivazione));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	public ArCsPaiPtiConsunti toEntity(ArCsPaiPtiConsuntiDTO cons) {
		ArCsPaiPtiConsunti toReturn = new ArCsPaiPtiConsunti();
		BeanUtils.copyProperties(cons, toReturn);
		return toReturn;
	}

	public ArCsPaiPtiConsuntiDTO toDto(ArCsPaiPtiConsunti cons) {
		ArCsPaiPtiConsuntiDTO toReturn = new ArCsPaiPtiConsuntiDTO();
		BeanUtils.copyProperties(cons, toReturn);
		return toReturn;
	}

}
