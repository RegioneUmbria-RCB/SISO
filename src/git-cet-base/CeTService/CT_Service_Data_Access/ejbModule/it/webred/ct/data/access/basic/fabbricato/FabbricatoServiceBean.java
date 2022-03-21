package it.webred.ct.data.access.basic.fabbricato;

import it.webred.ct.data.access.basic.common.dto.RicercaOggettoDTO;
import it.webred.ct.data.access.basic.fabbricato.dao.FabbricatoDAO;
import it.webred.ct.data.model.fabbricato.FabbricatoExRurale;
import it.webred.ct.data.model.fabbricato.FabbricatoMaiDichiarato;

import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class FabbricatoServiceBean implements FabbricatoService {

	@Autowired
	private FabbricatoDAO fabbricatoDAO;
	
	@Override
	public List<FabbricatoExRurale> getListaFabbricatiExRurali(RicercaOggettoDTO ro){
		return fabbricatoDAO.getListaFabbricatiExRurali(ro.getSezione(), ro.getFoglio(), ro.getParticella(), ro.getSub());
	}
	 
	 @Override	
	 public List<FabbricatoMaiDichiarato> getListaFabbricatiMaiDichiarati(RicercaOggettoDTO ro){
		return fabbricatoDAO.getListaFabbricatiMaiDichiarati(ro.getSezione(), ro.getFoglio(), ro.getParticella());
	}
	
	
}
