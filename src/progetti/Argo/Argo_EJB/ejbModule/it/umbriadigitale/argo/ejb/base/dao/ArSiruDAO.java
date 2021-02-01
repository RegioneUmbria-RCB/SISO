package it.umbriadigitale.argo.ejb.base.dao;

import it.umbriadigitale.argo.data.cs.data.ArOOrganizzazioneFse;
import it.umbriadigitale.argo.data.cs.data.ImportSiruProgettiAttivita;
import it.umbriadigitale.argo.data.cs.data.ImportSiruProgettiAttivitaPK;
import it.umbriadigitale.argo.ejb.ArgoBaseDAO;
import it.umbriadigitale.argo.ejb.client.base.ejbclient.ArgoServiceException;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

@Named
public class ArSiruDAO extends ArgoBaseDAO implements Serializable{

	private static final long serialVersionUID = 1L;

	public void save(ImportSiruProgettiAttivita progetto) {
		progetto.setDtIns(new Date());
		em.merge(progetto);
	}	
	
	public ImportSiruProgettiAttivita findSiruProgettiAttivita(ImportSiruProgettiAttivitaPK id){
		return em.find(ImportSiruProgettiAttivita.class, id);
	}
	

	public int save(List<ImportSiruProgettiAttivita> lst) throws ArgoServiceException{
		int numInsert = 0;
		try{
			
	        for(ImportSiruProgettiAttivita jpa : lst){
	        	this.save(jpa);
	        	numInsert++;
	        }
	
		}catch(Throwable e){
			logger.error(e.getMessage(), e);
			throw new ArgoServiceException(e);
		}
		
		return numInsert;
	}
	
	
	public String getBelfioreCapofilaByDenomSiru(String descrizione){
		String belfiore = null;
		Query q = em.createNamedQuery("ArOOrganizzazioneFse.findByDenominazioneCapofila");
		q.setParameter("descrizione", descrizione);
		
		List lst =q.getResultList();
		
		if(lst!=null && !lst.isEmpty()){
			belfiore = ((ArOOrganizzazioneFse)lst.get(0)).getCapofilaBelfiore();
		}
		return belfiore;
	}
	
	
	public void callArProgettiRefresh() throws ArgoServiceException{
		try{
			Query q = em.createNativeQuery("{call AR_PROGETTI_REFRESH.AGGIORNA_PROGETTI_FSE}");
			q.executeUpdate();
		}catch(Throwable e){
			logger.error(e.getMessage(), e);
			throw new ArgoServiceException(e);
		}
	}
	
	
}
