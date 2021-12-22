package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.dto.mobi.FindCasiByOperatoreBySoggettoRequestDTO;
import it.webred.cs.csa.ejb.dto.mobi.FindCasiByUsernameOperatoreRequestDTO;
import it.webred.cs.csa.ejb.dto.mobi.FindInterventoErogazioneByIdSettoreEroganteDataCasoIdRequestDTO;
import it.webred.cs.csa.ejb.dto.mobi.FindInterventoErogazioneByIdSettoreEroganteDataRequestDTO;
import it.webred.cs.data.model.CsIInterventoEsegMast;
import it.webred.cs.data.model.CsMobileStaging;
import it.webred.cs.data.model.view.VMobiCasi;
import it.webred.cs.data.model.view.VMobiIntErog;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.Query;

@Named
public class VMobiDAO extends CarSocialeBaseDAO implements Serializable {
	
	private static final long serialVersionUID = 1L;
		

	public List<VMobiIntErog> findVMobiIntErogByIdSettoreErogante(FindInterventoErogazioneByIdSettoreEroganteDataRequestDTO req) {
		List<VMobiIntErog> lstVMobiIntErog = new ArrayList<VMobiIntErog>();
			Query q = em.createNamedQuery("VMobiIntErog.findIntAttiviByIdSettoreEroganteData");
			q.setParameter ("idSettoreErogante", req.getIdSettori());
			q.setParameter("dataValiditaErogazione", req.getDataValiditaErogazione());
			List<VMobiIntErog> lstVMobiIntErog1 = (List<VMobiIntErog>) q.getResultList();

			lstVMobiIntErog.addAll(lstVMobiIntErog1);

			
			Query q2 = em.createNamedQuery("VMobiIntErog.findErogazioniByIdSettoreErogante");
			q2.setParameter("idSettoreErogante", req.getIdSettori());
			List<VMobiIntErog> lstVMobiIntErog2 = (List<VMobiIntErog>) q2.getResultList();
			
			lstVMobiIntErog.addAll(lstVMobiIntErog2);
		return lstVMobiIntErog;
	}
	
	public List<VMobiIntErog> findIntEsegMastIdByCasoId(FindInterventoErogazioneByIdSettoreEroganteDataCasoIdRequestDTO request, BigDecimal casoId){
		
		//devo prendere tutti VMobiIntErog con casoId, attive e dei  settori passati 
		
	List<VMobiIntErog> lstVMobiIntErog = new ArrayList<VMobiIntErog>();
	Query q = em.createNamedQuery("VMobiIntErog.findIntEsegMastIdByCasoIdData");
	q.setParameter ("idSettoreErogante", request.getIdSettori());
	q.setParameter("dataValiditaErogazione", request.getDataValiditaErogazione());
	q.setParameter("casoId", casoId);
	List<VMobiIntErog> lstVMobiIntErog1 = (List<VMobiIntErog>) q.getResultList();

	lstVMobiIntErog.addAll(lstVMobiIntErog1);

	
	Query q2 = em.createNamedQuery("VMobiIntErog.findIntEsegMastIdByCasoId");
	q2.setParameter("idSettoreErogante", request.getIdSettori());
	q2.setParameter("casoId", casoId);
	List<VMobiIntErog> lstVMobiIntErog2 = (List<VMobiIntErog>) q2.getResultList();
	
	lstVMobiIntErog.addAll(lstVMobiIntErog2);
return lstVMobiIntErog;
	}
	
	
	public List<VMobiCasi> findVMobiCasiByUsernameOperatore(FindCasiByUsernameOperatoreRequestDTO req) {
		List<VMobiCasi> lstVMobi = new ArrayList<VMobiCasi>();
			Query q = em.createNamedQuery("VMobiCasi.findCasiByUsername");
			q.setParameter("username", req.getUsername() );
			List<VMobiCasi> lstVMobi1 = (List<VMobiCasi>) q.getResultList();

			lstVMobi.addAll(lstVMobi1);
			
		return lstVMobi;
	}
	
	public List<VMobiCasi> findVMobiCasiByOperatoreBySoggetto(
			FindCasiByOperatoreBySoggettoRequestDTO req) {
		
		List<VMobiCasi> lstVMobi = new ArrayList<VMobiCasi>();
		//se mi passano entrambi chiamo findCasiByOperatoreBySoggettoNomeCognome altrimenti la findCasiByOperatoreBySoggettoCognome
		String stringaRicercaSogg = req.getRicercaSoggetto();
		stringaRicercaSogg=stringaRicercaSogg.trim().toLowerCase().replaceAll("\\s+","");
		Query q = null;
		if(stringaRicercaSogg!=null && !stringaRicercaSogg.isEmpty())
		{
			q= em.createNamedQuery("VMobiCasi.findCasiByOperatoreBySoggettoNomeCognomeCF");
			q.setParameter("username", req.getUsername() );
			q.setParameter("idSettoreErogante", req.getIdSettori() );
			q.setParameter("stringaRicercaSogg", stringaRicercaSogg );
			
		}
		
		
		
		
		List<VMobiCasi> lstVMobi1 = (List<VMobiCasi>) q.getResultList();

		lstVMobi.addAll(lstVMobi1);
		
	return lstVMobi;
	}
	
	public VMobiCasi findVMobiCasiById(
			FindInterventoErogazioneByIdSettoreEroganteDataCasoIdRequestDTO req) {
		
		VMobiCasi vMobi = null;
		//se mi passano entrambi chiamo findCasiByOperatoreBySoggettoNomeCognome altrimenti la findCasiByOperatoreBySoggettoCognome
		BigDecimal casoId = req.getCasoId();
		Query q = null;
			q= em.createNamedQuery("VMobiCasi.findCasiById");
			q.setParameter("casoId", casoId );
			try {
				vMobi = (VMobiCasi) q.getSingleResult();
			} catch (NoResultException e) {
				//non dovrebbe mai accadere
			}
			
	return vMobi;
	}
	
	public List<CsMobileStaging> findVariazioniStaging(){
		Query q = em.createNamedQuery("CsMobileStaging.findRecord2Elab");
		return  q.getResultList();
	}
	
	public void salvaMobileStaging(CsMobileStaging ms){
		em.merge(ms);
	}


	
}
