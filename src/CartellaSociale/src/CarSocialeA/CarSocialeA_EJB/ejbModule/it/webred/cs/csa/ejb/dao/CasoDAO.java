package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsACasoOpeTipoOpe;
import it.webred.cs.data.model.CsACasoOpeTipoOpe2;
import it.webred.cs.data.model.CsOOperatoreBASIC;
import it.webred.cs.data.model.CsOOperatoreTipoOperatore;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

@Named
public class CasoDAO extends CarSocialeBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public CsACaso findCasoById(Long idCaso){
		CsACaso caso = em.find(CsACaso.class, idCaso);
		return caso;
	}
	
	private void setIdentificativo(CsACaso caso){
		if(caso.getIdentificativo()==null || caso.getIdentificativo()==0){
			String sql =" SELECT SQ_CODICE_CS.nextval FROM DUAL";
			Query q = em.createNativeQuery(sql);
			Long ide = ((BigDecimal)q.getSingleResult()).longValue();
			logger.debug("Assegnazione identificativo "+ide);
			caso.setIdentificativo(ide);
		}
	}

	public void createCaso(CsACaso newCaso){
		setIdentificativo(newCaso);
		em.persist(newCaso);
	}

	public void updateCaso(CsACaso caso){
		setIdentificativo(caso);
		em.merge(caso);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<CsACaso> findAll(){
		Query q = em.createNamedQuery("CsACaso.findAll");
		return q.getResultList();
	}

	public CsOOperatoreTipoOperatore findOperatoreTipoOperatoreByOpSettore(long idOperatore, long idSettore){
		Query q = em.createNamedQuery("CsOOperatoreTipoOperatore.findOpTipoOpByOpSettore");
		q.setParameter("operatoreId", idOperatore);
		q.setParameter("settoreId", idSettore);
		List<CsOOperatoreTipoOperatore> list = q.getResultList();
		if(!list.isEmpty())
			return list.get(0);
		return null;
	}
	
	public CsACasoOpeTipoOpe findCasoOpeResponsabile(long idCaso) {
		
		Query q = em.createNamedQuery("CsACasoOpeTipoOpe.findResponsabileByCaso");
		q.setParameter("casoId", idCaso);
		List<CsACasoOpeTipoOpe> list = (List<CsACasoOpeTipoOpe>) q.getResultList();
		if(!list.isEmpty())
			return list.get(0);
		return null;
		
	}
	
/*	public CsOOperatore findResponsabile(long idCaso) {
		
		Query q = em.createNamedQuery("CsOOperatore.findResponsabileByCaso");
		q.setParameter("casoId", idCaso);
		List<CsOOperatore> list = (List<CsOOperatore>) q.getResultList();
		if(!list.isEmpty())
			return list.get(0);
		return null;
		
	}
	*/
	
	public CsOOperatoreBASIC findResponsabileBASIC(Long idCaso) {
		if(idCaso!=null){
			Query q = em.createNamedQuery("CsOOperatoreBASIC.findResponsabileBASICByCaso");
			q.setParameter("casoId", idCaso);
			List<CsOOperatoreBASIC> list = (List<CsOOperatoreBASIC>) q.getResultList();
			if(!list.isEmpty())
				return list.get(0);
		}
		return null;
		
	}
	
	

	public void setDataModifica(long idCaso){
		CsACaso caso = findCasoById( idCaso );
		caso.setDtMod(new Date());
		em.merge(caso);
	}
	
	//nuovo operatore tipo 2
	public void salvaOperatoreTipoOp2Caso(CsACasoOpeTipoOpe2 oper){
		em.merge(oper);
	}
	
	public void eliminaOperatoreTipoOp2ByCasoId(Long obj) {
		Query q = em.createNamedQuery("CsACasoOpeTipoOpe2.deleteOpeByCasoId");
		q.setParameter("casoId", obj);
		q.executeUpdate();
		
	}
	//fine
	
	public void salvaOperatoreTipoOpCaso(CsACasoOpeTipoOpe oper){
		em.persist(oper);
	}
	
	public void updateOperatoreTipoOpCaso(CsACasoOpeTipoOpe oper){
		em.merge(oper);
	}

	public void eliminaOperatoreTipoOpByCasoId(Long obj) {
		Query q = em.createNamedQuery("CsACasoOpeTipoOpe.deleteByCasoId");
		q.setParameter("casoId", obj);
		q.executeUpdate();
		
	}
	
	public void eliminaOperatoreTipoOpById(Long id) {
		Query q = em.createNamedQuery("CsACasoOpeTipoOpe.deleteById");
		q.setParameter("id", id);
		q.executeUpdate();
	}
	

	public List<CsACasoOpeTipoOpe> getListaOperatoreTipoOpByCasoId(Object obj) {
		Query q = em.createNamedQuery("CsACasoOpeTipoOpe.findByCasoId");
		q.setParameter("casoId", obj);
		return q.getResultList();
	}
	

	public List<CsACasoOpeTipoOpe2> getListaOperatoreTipoOp2ByCasoId(Object obj) {
		Query q = em.createNamedQuery("CsACasoOpeTipoOpe2.findOpeByCasoId");
		q.setParameter("casoId", obj);
		return q.getResultList();
	}
	
	
	public CsACasoOpeTipoOpe findCasoOpeTipoOpeByOpSettore(long idOperatore, long idSettore){
		Query q = em.createNamedQuery("CsACasoOpeTipoOpe.findCasoOpeTipoOpeByOpSettore");
		q.setParameter("operatoreId", idOperatore);
		q.setParameter("settoreId", idSettore);
		List<CsACasoOpeTipoOpe> list = q.getResultList();
		if(!list.isEmpty())
			return list.get(0);
		return null;
	}
	
	public Integer countCasiByResponsabileCatSociale(long idOperatore, long idCatSociale){
		String sql ="   select count(cs.caso_id) carico "+
                "from Cs_A_Caso_Ope_Tipo_Ope cs, Cs_A_Soggetto_Categoria_Soc scs, Cs_It_Step it,  "+
                "Cs_O_operatore_tipo_operatore tipoOp, Cs_O_operatore_Settore opSett, Cs_a_soggetto s "+
                "where CS.OPERATORE_TIPO_OPERATORE_ID=tipoOp.id and TIPOOP.OPERATORE_SETTORE_ID=opSett.id  "+
                "and cs.flag_Responsabile = 1 "+
                "AND SCS.PREVALENTE = 1 "+
                "and cs.data_Fine_App > CURRENT_DATE "+
                "and scs.data_Fine_App > CURRENT_DATE "+
                "and CS.CASO_ID=s.caso_id and S.ANAGRAFICA_ID=SCS.SOGGETTO_ANAGRAFICA_ID "+
                "and  IT.ID = (SELECT MAX (it2.id) "+
                "      FROM CS_IT_STEP it2 "+
                "     WHERE IT2.CASO_ID = cs.caso_id) "+
                "and IT.CFG_IT_STATO_ID<>2 "+
                "and OPSETT.OPERATORE_ID= :operatoreId "+
                "and SCS.CATEGORIA_SOCIALE_ID= :catSocialeId ";
		
		Query q = em.createNativeQuery(sql);
		q.setParameter("operatoreId", idOperatore);
		q.setParameter("catSocialeId", idCatSociale);

		BigDecimal c = (BigDecimal) q.getSingleResult();
		return c.intValue();
	}
	
}
