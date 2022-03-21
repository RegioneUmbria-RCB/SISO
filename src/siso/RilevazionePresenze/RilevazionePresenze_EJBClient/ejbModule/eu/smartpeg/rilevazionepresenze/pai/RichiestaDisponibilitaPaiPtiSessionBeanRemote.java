package eu.smartpeg.rilevazionepresenze.pai;

import java.util.List;

import javax.ejb.Remote;

import eu.smartpeg.rievazionepresenze.dto.pai.ArCsPaiInfoSinteticheDTO;
import eu.smartpeg.rievazionepresenze.dto.pai.ArCsPaiPtiConsuntiDTO;
import eu.smartpeg.rievazionepresenze.dto.pai.RichiestaDisponibilitaPaiPtiDTO;

@Remote
public interface RichiestaDisponibilitaPaiPtiSessionBeanRemote {

	public void salva(RichiestaDisponibilitaPaiPtiDTO richiesta);
	public List<RichiestaDisponibilitaPaiPtiDTO> findRichiestaByIdStruttura(Long idStruttura);
	public List<RichiestaDisponibilitaPaiPtiDTO> findRichiestaByStrutturaStato(Long idStruttura, String statoRich);
	public void salvaInfoSintetiche(ArCsPaiInfoSinteticheDTO infoSintetichePAI);
	public List<ArCsPaiPtiConsuntiDTO> findListaConsuntivazione(String codRouting, Long idPaiPTI);
	public ArCsPaiPtiConsuntiDTO salvaConsuntivazione(ArCsPaiPtiConsuntiDTO consuntivazione);
	public void eliminaConsuntivazione(ArCsPaiPtiConsuntiDTO consuntivazione);
}
