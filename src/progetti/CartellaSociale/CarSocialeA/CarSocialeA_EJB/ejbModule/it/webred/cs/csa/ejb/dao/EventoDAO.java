package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.data.model.CsAImportExport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

@Named
public class EventoDAO extends CarSocialeBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Inserisce un nuovo record nella Tabela CS_A_IMPORT_EXPORT
	 * @param evento
	 */
	public void creaEvento(CsAImportExport evento){
		em.persist(evento);
		em.flush();
	}

	
	/**
	 * Cancella un evento
	 * @param idEvento
	 */
	public void cancellaEvento(CsAImportExport evento){
		//TODO: Query per cancellare un evento 
		em.merge(evento);
		em.flush();
	}
	
	/***
	 * Ritorna un singolo evento selezionato tramite ID 
	 * @param idEvento
	 * @return
	 */
	public CsAImportExport findCasoById(Long idEvento){
		CsAImportExport evento = em.find(CsAImportExport.class, idEvento);
		return evento;
	}
	
	/***
	 * Ritorna la lista di tutti gli eventi Import/Export
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CsAImportExport> findAll(){
		String query = "from CsAImportExport e where e.archiviato = 0 order by e.dataEvento desc";
		Query q = em.createQuery(query);
		//Query q = em.createNamedQuery("CsAImportExport.findAll");
		return q.getResultList();
	}
	
	

}
