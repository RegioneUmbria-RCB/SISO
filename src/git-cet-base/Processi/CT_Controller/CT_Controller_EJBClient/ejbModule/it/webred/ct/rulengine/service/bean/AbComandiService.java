package it.webred.ct.rulengine.service.bean;

import it.webred.ct.rulengine.controller.model.REnteEsclusioni;
import it.webred.ct.rulengine.dto.EnteEsclusioniDTO;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AbComandiService {
	
	public List<EnteEsclusioniDTO> getEnteEsclusioniByCommandType(Long idCommandType);
	
	public void updateEnteEsclusioni(REnteEsclusioni rEnteEsclusioni);
}
