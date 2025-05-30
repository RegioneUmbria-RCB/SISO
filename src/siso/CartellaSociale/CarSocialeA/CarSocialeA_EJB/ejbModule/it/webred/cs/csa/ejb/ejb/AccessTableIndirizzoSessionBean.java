package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableIndirizzoSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.IndirizzoDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsAIndirizzo;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ct.support.validation.ValidationStateless;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Session Bean implementation class AccessTableDataMediciSessionBean
 */
@Stateless
@Interceptors(ValidationStateless.class)
public class AccessTableIndirizzoSessionBean extends CarSocialeBaseSessionBean implements AccessTableIndirizzoSessionBeanRemote {

	private static final long serialVersionUID = 1L;
	@Autowired
	private IndirizzoDAO indirizzoDAO;

	@Override
	public CsAIndirizzo getIndirizzoById(BaseDTO dto) {
		return indirizzoDAO.getIndirizzoId((Long) dto.getObj());
	}
	
	@Override
	public List<CsAIndirizzo> getIndirizziBySoggetto(BaseDTO dto) {
		return indirizzoDAO.getIndirizziBySoggetto((Long) dto.getObj());
	}
	
	@Override
	public void saveIndirizzo(BaseDTO dto) {
		CsAIndirizzo csIndirizzo = (CsAIndirizzo) dto.getObj();
		indirizzoDAO.saveIndirizzo(csIndirizzo);
	}
	
	@Override
	public void updateIndirizzo(BaseDTO dto) {
		CsAIndirizzo csIndirizzo = (CsAIndirizzo) dto.getObj();
		indirizzoDAO.updateIndirizzo(csIndirizzo);
	}
	
	@Override
	public void eliminaIndirizziBySoggetto(BaseDTO dto) {
		List<CsAIndirizzo> lista = getIndirizziBySoggetto(dto);
		indirizzoDAO.eliminaIndirizzoBySoggetto((Long) dto.getObj());
		for(CsAIndirizzo ind: lista)
			indirizzoDAO.eliminaAnaIndirizzoById(ind.getCsAAnaIndirizzo().getId());
	}

	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public CsAAnaIndirizzo getIndirizzoResidenza(BaseDTO dto) {
		return indirizzoDAO.getResidenzaBySoggetto((Long) dto.getObj());
	}
	
	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public CsAAnaIndirizzo getIndirizzoDomicilio(BaseDTO dto) {
		return indirizzoDAO.getDomicilioBySoggetto((Long)dto.getObj());
	}

	@Override
	public List<KeyValueDTO> getListaComuniResidenza(CeTBaseObject cet) {
		return indirizzoDAO.getListaComuni(DataModelCostanti.TipoIndirizzo.RESIDENZA_ID);
	}
	
	@Override
	public List<KeyValueDTO> getListaNazioniResidenza(CeTBaseObject cet) {
		return indirizzoDAO.getListaNazioni(DataModelCostanti.TipoIndirizzo.RESIDENZA_ID);
	}

}
