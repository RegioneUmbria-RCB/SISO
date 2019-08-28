package it.webred.cs.csa.ejb.client;

import java.util.List;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.SchedaSegrDTO;
import it.webred.cs.data.model.CsSsSchedaSegr;

import javax.ejb.Remote;

/**
 * @author Andrea
 *
 */
@Remote
public interface AccessTableSchedaSegrSessionBeanRemote {

	public boolean saveSchedaSegr(SchedaSegrDTO dto);

	public CsSsSchedaSegr findSchedaSegrById(BaseDTO dto);

	public List<CsSsSchedaSegr> getSchedeSegr(SchedaSegrDTO dto);
	
	public Integer getSchedeSegrCount(SchedaSegrDTO dto);

	public CsSsSchedaSegr findSchedaSegrByIdAnagrafica(BaseDTO dto);

	public void updateSchedaSegr(BaseDTO dto);

	public Long findCategoriaSchedaSegrById(BaseDTO dto);
	
	public String findStatoSchedaSegrById(BaseDTO dto);

	public void deleteSchedaSegr(SchedaSegrDTO dto) throws Exception;

}