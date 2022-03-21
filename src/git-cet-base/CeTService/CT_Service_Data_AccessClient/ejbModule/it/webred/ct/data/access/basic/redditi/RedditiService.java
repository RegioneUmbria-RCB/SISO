package it.webred.ct.data.access.basic.redditi;
import java.util.List;

import it.webred.ct.data.access.basic.common.dto.RicercaSoggettoDTO;
import it.webred.ct.data.access.basic.redditi.dto.KeySoggDTO;
import it.webred.ct.data.access.basic.redditi.dto.RedditiDicDTO;
import it.webred.ct.data.model.redditi.*;
import javax.ejb.Remote;

@Remote
public interface RedditiService {
	public List<RedDatiAnagrafici> getListaSoggetti(RicercaSoggettoDTO rs);
	public List<RedDatiAnagrafici> getListaSoggettiUltDic(RicercaSoggettoDTO rs) ;
	public RedDatiAnagrafici getSoggettoByKey(KeySoggDTO key) ;
	public RedDomicilioFiscale getDomicilioByKey(KeySoggDTO key) ;
	public List<RedditiDicDTO> getRedditiByKey(KeySoggDTO key) ;
	public List<RedditiDicDTO> getRedditiByKeyAnno(KeySoggDTO key) ;
}
