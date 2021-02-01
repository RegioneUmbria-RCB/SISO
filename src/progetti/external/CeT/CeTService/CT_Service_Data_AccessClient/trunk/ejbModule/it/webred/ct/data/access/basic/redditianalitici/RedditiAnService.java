package it.webred.ct.data.access.basic.redditianalitici;
import it.webred.ct.data.access.basic.redditi.dto.KeySoggDTO;
import it.webred.ct.data.access.basic.redditianalitici.dto.RigaQuadroRbDTO;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface RedditiAnService {

	public List<RigaQuadroRbDTO> getQuadroRBFabbricatiByKeyAnno(KeySoggDTO key) ;
}
