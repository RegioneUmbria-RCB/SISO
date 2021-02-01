package it.webred.ct.data.access.basic.pregeo;

import java.util.List;

import it.webred.ct.data.access.basic.pregeo.dto.RicercaPregeoDTO;
import it.webred.ct.data.model.pregeo.PregeoInfo;
import javax.ejb.Remote;

@Remote
public interface PregeoService {
	
	public List<PregeoInfo> getDatiPregeo(RicercaPregeoDTO rp);

	public List<String> getListaParticelleByNomeFilePdf(RicercaPregeoDTO rp);
	
	public List<PregeoInfo> getPregeoByCriteria(RicercaPregeoDTO rp);
	
}
