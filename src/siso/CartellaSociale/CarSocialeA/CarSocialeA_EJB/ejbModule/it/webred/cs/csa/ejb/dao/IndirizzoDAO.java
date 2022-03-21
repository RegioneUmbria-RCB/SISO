package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.TipoIndirizzo;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsAIndirizzo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

@Named
public class IndirizzoDAO extends CarSocialeBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public CsAIndirizzo getIndirizzoId(long id) {
		CsAIndirizzo indirizzo = em.find(CsAIndirizzo.class, id);
		return indirizzo;
	}
	
	@SuppressWarnings("unchecked")
	public List<CsAIndirizzo> getIndirizziBySoggetto(long idSoggetto) {
		Query q = em.createNamedQuery("CsAIndirizzo.findBySoggetto");
		q.setParameter("idSoggetto", idSoggetto);
		List<CsAIndirizzo> r = q.getResultList();
		return r;
	}
	
	@SuppressWarnings("unchecked")
	public CsAAnaIndirizzo getIndirizzoBySoggetto(Long idSoggetto, Long tipo) {
		Query q = em.createNamedQuery("CsAIndirizzo.findAttualeBySoggettoTipo");
		q.setParameter("idSoggetto", idSoggetto);
		q.setParameter("tipo", tipo);
		List<CsAIndirizzo> r = q.getResultList();
		if(!r.isEmpty()) 
			return r.get(0).getCsAAnaIndirizzo();
		return null;
	}
	
	public CsAAnaIndirizzo getResidenzaBySoggetto(long idSoggetto){
		return this.getIndirizzoBySoggetto(idSoggetto, TipoIndirizzo.RESIDENZA_ID);
	}
	
	public CsAAnaIndirizzo getDomicilioBySoggetto(long idSoggetto){
		return this.getIndirizzoBySoggetto(idSoggetto, TipoIndirizzo.DOMICILIO_ID);
	}
	
	public void saveIndirizzo(CsAIndirizzo csIndirizzo){
		CsAAnaIndirizzo csAna = csIndirizzo.getCsAAnaIndirizzo();
		csAna = saveAnaIndirizzo(csAna);
		csIndirizzo.setAnaIndirizzoId(csAna.getId());
		saveCsAIndirizzo(csIndirizzo);
	}
	
	public void updateIndirizzo(CsAIndirizzo csIndirizzo){
		updateAnaIndirizzo(csIndirizzo.getCsAAnaIndirizzo());
		updateCsAIndirizzo(csIndirizzo);
	}
	
	private void saveCsAIndirizzo(CsAIndirizzo indirizzo) {
		em.persist(indirizzo);
	}
	
	private void updateCsAIndirizzo(CsAIndirizzo indirizzo) {
		em.merge(indirizzo);
	}

	public CsAAnaIndirizzo saveAnaIndirizzo(CsAAnaIndirizzo indirizzo) {
		em.persist(indirizzo);
		return indirizzo;
	}
	
	private void updateAnaIndirizzo(CsAAnaIndirizzo indirizzo) {
		em.merge(indirizzo);
	}
	
	public void eliminaIndirizzoBySoggetto(Long soggettoId) {
		
		Query q = em.createNamedQuery("CsAIndirizzo.eliminaBySoggettoId");
		q.setParameter("id", soggettoId);
		q.executeUpdate();
		
	}
	
	public void eliminaAnaIndirizzoById(Long id) {
		
		Query q = em.createNamedQuery("CsAAnaIndirizzo.eliminaById");
		q.setParameter("id", id);
		q.executeUpdate();
		
	}
	
	public CsAAnaIndirizzo getAnaIndirizzoById(Long id) {
		CsAAnaIndirizzo indirizzo = em.find(CsAAnaIndirizzo.class, id);
		return indirizzo;
		
	}

	public List<KeyValueDTO> getListaComuni(Long tipoId) {
		List<KeyValueDTO> comuni = new ArrayList<KeyValueDTO>();
		Query q = em.createNamedQuery("CsAIndirizzo.getListaComuni");
		q.setParameter("tipo", tipoId);
		List<Object[]> res = (List<Object[]>) q.getResultList();
		for(Object[] r :res){
			KeyValueDTO comune = new KeyValueDTO((String)r[0], (String)r[1]);
			comuni.add(comune);
		}
		return comuni;
	}
	
	public List<KeyValueDTO> getListaNazioni(Long tipoId) {
		List<KeyValueDTO> comuni = new ArrayList<KeyValueDTO>();
		Query q = em.createNamedQuery("CsAIndirizzo.getListaNazioni");
		q.setParameter("tipo", tipoId);
		List<Object[]> res = (List<Object[]>) q.getResultList();
		for(Object[] r :res){
			KeyValueDTO comune = new KeyValueDTO((String)r[0], (String)r[1]);
			comuni.add(comune);
		}
		return comuni;
	}
}
