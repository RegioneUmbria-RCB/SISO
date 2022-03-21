package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.data.model.CsAAnagraficaLog;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

@Named
public class AnagraficaLogDAO  extends CarSocialeBaseDAO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public CsAAnagraficaLog findAnagraficaLogById(Long id) {
		CsAAnagraficaLog anagraficaLog = em.find(CsAAnagraficaLog.class, id);
		return anagraficaLog;
	}

	
	public void saveAnagraficaLog(CsAAnagraficaLog newAnagraficaLog) {
		em.persist(newAnagraficaLog);
	}

	public List<CsAAnagraficaLog> findStoricoBySoggetto(Long obj) {
		Query q = em.createNamedQuery("CsAAnagraficaLog.findStorico");
		q.setParameter("anagraficaId", obj);
		return q.getResultList();
	}
}
