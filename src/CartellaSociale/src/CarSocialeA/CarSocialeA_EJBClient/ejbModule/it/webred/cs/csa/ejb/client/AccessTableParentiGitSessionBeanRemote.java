package it.webred.cs.csa.ejb.client;

import java.util.List;

import javax.ejb.Remote;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsAFamigliaGruppoGit;

/**
 * @author Andrea
 *
 */
@Remote
public interface AccessTableParentiGitSessionBeanRemote {
	
    public CsAFamigliaGruppoGit getFamigliaGruppoGit(BaseDTO dto);
        
    public List<CsAFamigliaGruppoGit> getFamigliaGruppoGitAggiornate(BaseDTO dto);
    
    public void createFamigliaGruppoGit(BaseDTO dto);
    
	public void updateComponenteGit(BaseDTO dto);
    
	public void updateFamigliaGruppoGit(BaseDTO dto);
}
