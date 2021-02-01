package it.webred.ct.config.audit;

import it.webred.ct.config.audit.dto.AuditDTO;
import it.webred.ct.config.audit.dto.AuditSearchCriteria;
import it.webred.ct.config.model.AmAudit;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AuditService {

	public List<AuditDTO> getListaAmAudit(AuditSearchCriteria criteria, Integer start,
			Integer rowNumber);
	
	public Long getCountAmAudit(
			AuditSearchCriteria criteria);
	
	public AmAudit getAmAuditById(Long id);
}
