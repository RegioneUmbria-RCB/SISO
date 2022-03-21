package it.webred.ct.data.access.basic.cartellasociale;
import java.util.List;

import it.webred.ct.data.model.c336.C336Allegato;
import it.webred.ct.data.model.c336.C336DecodIrreg;
import it.webred.ct.data.model.c336.C336DecodTipDoc;
import it.webred.ct.data.model.c336.C336GesPratica;
import it.webred.ct.data.model.c336.C336GridAttribCatA2;
import it.webred.ct.data.model.c336.C336Pratica;
import it.webred.ct.data.model.c336.C336SkCarGenFabbricato;
import it.webred.ct.data.model.c336.C336SkCarGenUiu;
import it.webred.ct.data.model.c336.C336TabValIncrClsA4A3;
import it.webred.ct.data.model.c336.C336TabValIncrClsA5A6;
import it.webred.ct.data.access.basic.c336.dto.C336CommonDTO;
import it.webred.ct.data.access.basic.c336.dto.C336PraticaDTO;
import it.webred.ct.data.access.basic.c336.dto.C336StatOperatoreDTO;
import it.webred.ct.data.access.basic.c336.dto.RicercaStatisticheDTO;
import it.webred.ct.data.access.basic.cartellasociale.dto.InterventoDTO;
import it.webred.ct.data.access.basic.cartellasociale.dto.RicercaCarSocDTO;
import it.webred.ct.data.access.basic.common.dto.RicercaOggettoDTO;
import it.webred.ct.support.datarouter.CeTBaseObject;

import javax.ejb.Remote;

@Remote
public interface CartellaSocialeService {
	
	public List<InterventoDTO> getInterventiByCF(RicercaCarSocDTO ro);
	
	
}
