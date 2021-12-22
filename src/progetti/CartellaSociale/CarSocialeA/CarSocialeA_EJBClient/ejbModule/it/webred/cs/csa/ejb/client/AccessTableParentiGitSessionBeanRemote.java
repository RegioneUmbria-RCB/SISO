package it.webred.cs.csa.ejb.client;

import java.util.List;

import javax.ejb.Remote;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.InfoRecapitiDTO;
import it.webred.cs.data.model.CsAFamigliaGruppoGit;

@Remote
public interface AccessTableParentiGitSessionBeanRemote {
	
    public CsAFamigliaGruppoGit getFamigliaGruppoGit(BaseDTO dto);
        
    public List<CsAFamigliaGruppoGit> getFamigliaGruppoGitAggiornate(BaseDTO dto);
    
    public void createFamigliaGruppoGit(BaseDTO dto);
    
	public void updateComponenteGit(BaseDTO dto);
    
	public void updateFamigliaGruppoGit(BaseDTO dto);

	public InfoRecapitiDTO findInfoRecapiti(BaseDTO dto);

	public List<CsAFamigliaGruppoGit> getFamigliaGruppoProvenienza(BaseDTO bDto);

	public void elaboraVariazioniFamigliaGruppoGit(BaseDTO bDto);
}
