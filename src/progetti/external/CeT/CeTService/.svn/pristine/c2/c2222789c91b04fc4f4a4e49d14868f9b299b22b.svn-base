package it.webred.ct.config.audit;

import it.webred.ct.config.audit.dto.AuditDTO;
import it.webred.ct.config.audit.dto.AuditSearchCriteria;
import it.webred.ct.config.audit.dto.QueryBuilder;
import it.webred.ct.config.model.AmAudit;
import it.webred.ct.config.model.AmAuditDecode;
import it.webred.ct.config.model.AmComune;
import it.webred.ct.config.parameters.comune.ComuneServiceException;
import it.webred.ct.data.access.basic.anagrafe.dto.SoggettoAnagrafeDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class AuditServiceBean implements
		AuditService {

	@PersistenceContext(unitName = "ConfigDataModel")
	protected EntityManager manager;
	
	public List<AuditDTO> getListaAmAudit(
			AuditSearchCriteria criteria, Integer start, Integer rowNumber) {

		List<AuditDTO> result = new ArrayList<AuditDTO>();
		
		try {

			String sql = (new QueryBuilder(criteria)).createQuery(false);

			if (sql == null)
				return new ArrayList<AuditDTO>();

			Query q = manager.createQuery(sql);
			if(start != null && rowNumber != null){
				q.setFirstResult(start);
				q.setMaxResults(rowNumber);
			}
			
			List<Object[]> lista = q.getResultList();
			for (Object[] ele : lista) {
				AuditDTO aDto = new AuditDTO();
				aDto.setAmAudit((AmAudit) ele[0]);
				aDto.setDataMinSessione((Date) ele[1]);
				aDto.setDataMaxSessione((Date) ele[2]);
				aDto.setAmAuditDecode((AmAuditDecode) ele[3]);
				result.add(aDto);
			}

			return result;
		} catch (Throwable t) {
			throw new AuditServiceException(t);
		}

	}
	
	public Long getCountAmAudit(
			AuditSearchCriteria criteria) {

		Long ol = 0L;
		
		try {

			String sql = (new QueryBuilder(criteria)).createQuery(true);

			if (sql == null)
				return ol;

			Query q = manager.createQuery(sql);
			
			Object o = q.getSingleResult();
			ol = (Long) o;

			return ol;
		} catch (Throwable t) {
			throw new AuditServiceException(t);
		}

	}
	
	public AmAudit getAmAuditById(Long id) {

		try {
			
			return (AmAudit) manager.createNamedQuery("AmAudit.getAmAuditById")
					.setParameter("id", id).getSingleResult();

		} catch (Throwable t) {
			throw new ComuneServiceException(t);
		}

	}

}
