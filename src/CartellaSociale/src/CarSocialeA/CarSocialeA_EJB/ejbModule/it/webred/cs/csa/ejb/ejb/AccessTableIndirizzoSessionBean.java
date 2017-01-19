package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableIndirizzoSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.IndirizzoDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsAIndirizzo;

import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Session Bean implementation class AccessTableDataMediciSessionBean
 */
@Stateless
public class AccessTableIndirizzoSessionBean extends CarSocialeBaseSessionBean implements AccessTableIndirizzoSessionBeanRemote {

	/**
	 * 
	 */
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
		CsAAnaIndirizzo csAna = csIndirizzo.getCsAAnaIndirizzo();
		csAna = indirizzoDAO.saveAnaIndirizzo(csAna);
		csIndirizzo.setAnaIndirizzoId(csAna.getId());
		indirizzoDAO.saveIndirizzo(csIndirizzo);
	}
	
	@Override
	public void updateIndirizzo(BaseDTO dto) {
		CsAIndirizzo csIndirizzo = (CsAIndirizzo) dto.getObj();
		indirizzoDAO.updateAnaIndirizzo(csIndirizzo.getCsAAnaIndirizzo());
		indirizzoDAO.updateIndirizzo(csIndirizzo);
	}
	
	@Override
	public void eliminaIndirizziBySoggetto(BaseDTO dto) {
		List<CsAIndirizzo> lista = getIndirizziBySoggetto(dto);
		indirizzoDAO.eliminaIndirizzoBySoggetto((Long) dto.getObj());
		for(CsAIndirizzo ind: lista)
			indirizzoDAO.eliminaAnaIndirizzoById(ind.getCsAAnaIndirizzo().getId());
	}

}
