package eu.smartpeg.rilevazionepresenze.pai.ejb.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

import org.springframework.beans.BeanUtils;

import eu.smartpeg.rievazionepresenze.dto.pai.RichiestaDisponibilitaPaiPtiDTO;
import eu.smartpeg.rilevazionepresenze.data.model.pai.ArCsPaiPtiConsunti;
import eu.smartpeg.rilevazionepresenze.data.model.pai.RichiestaDisponibilitaPaiPti;
import eu.smartpeg.rilevazionepresenze.ejb.dao.RilevazionePresenzeBaseDao;

@Named
public class RichiestaDisponibilitaPaiPtiDAO extends RilevazionePresenzeBaseDao implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	public List<RichiestaDisponibilitaPaiPtiDTO> findRichDispByStrutturaId(Long strutturaId) {
		Query query = em.createQuery("SELECT d FROM RichiestaDisponibilitaPaiPti d WHERE d.idStruttura = :strutturaId");
		//Query query = em.createQuery("SELECT d FROM RichiestaDisponibilitaPaiPti d");
		query.setParameter("strutturaId", String.valueOf(strutturaId));
		List<RichiestaDisponibilitaPaiPti> result = query.getResultList();
		RichiestaDisponibilitaPaiPtiDTO tmpDTO;
		List<RichiestaDisponibilitaPaiPtiDTO> resDTO = new ArrayList<RichiestaDisponibilitaPaiPtiDTO>();
		for(RichiestaDisponibilitaPaiPti richiestaEntity: result) {
			tmpDTO = new RichiestaDisponibilitaPaiPtiDTO();
			BeanUtils.copyProperties(richiestaEntity, tmpDTO);
			resDTO.add(tmpDTO);
		}
		return resDTO;
	}
	
	public List<RichiestaDisponibilitaPaiPtiDTO> findRichDispByStrutturaStato(Long strutturaId, String statoRich) {
		Query query = em.createQuery("SELECT d FROM RichiestaDisponibilitaPaiPti d WHERE d.idStruttura = :strutturaId and d.statoRichiesta = :statoRich ");

		query.setParameter("strutturaId", String.valueOf(strutturaId));
		query.setParameter("statoRich", String.valueOf(statoRich));
		List<RichiestaDisponibilitaPaiPti> result = query.getResultList();
		RichiestaDisponibilitaPaiPtiDTO tmpDTO;
		List<RichiestaDisponibilitaPaiPtiDTO> resDTO = new ArrayList<RichiestaDisponibilitaPaiPtiDTO>();
		for(RichiestaDisponibilitaPaiPti richiestaEntity: result) {
			tmpDTO = new RichiestaDisponibilitaPaiPtiDTO();
			String[] ignore = { "DettaglioMinore"};			
			
			BeanUtils.copyProperties(richiestaEntity, tmpDTO, ignore);
			
			resDTO.add(tmpDTO);
		}
		return resDTO;
	}
	public RichiestaDisponibilitaPaiPti saveRichiesta(RichiestaDisponibilitaPaiPtiDTO richiestaDTO) {
		try {
			RichiestaDisponibilitaPaiPti toReturn = new RichiestaDisponibilitaPaiPti();
			BeanUtils.copyProperties(richiestaDTO, toReturn);
			toReturn = em.merge(toReturn);
			em.flush();

			return toReturn;
		} catch (Exception e) {
			logger.error("Errore saveRichiesta " + e.getMessage(), e);
		}
		return null;
	}
	
	public ArCsPaiPtiConsunti saveConsuntivazione(ArCsPaiPtiConsunti cons) {
		try {
			ArCsPaiPtiConsunti toReturn = new ArCsPaiPtiConsunti();
			BeanUtils.copyProperties(cons, toReturn);
			toReturn = em.merge(toReturn);
			em.flush();

			return toReturn;
		} catch (Exception e) {
			logger.error("Errore saveConsuntivazione " + e.getMessage(), e);
		}
		return null;
	}
	
	public ArCsPaiPtiConsunti deleteConsuntivazione(ArCsPaiPtiConsunti cons) {
		try {
		
				Query q = em.createNativeQuery("DELETE FROM AR_CS_PAI_PTI_CONSUNTI cs where ID=:id");
				q.setParameter("id", cons.getId());
				q.executeUpdate();
				

		} catch (Exception e) {
			logger.error("Errore deleteConsuntivazione " + e.getMessage(), e);
		}
		return null;
	}
	
}
