package it.webred.cs.csa.web.manbean.redditoCittadinanza;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import it.webred.cs.jsf.interfaces.IReddCittadinanza;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

@ManagedBean
@SessionScoped
public class SchedaRdCBean extends CsUiCompBaseBean implements IReddCittadinanza {

	
	
	@Override
	public Object getScheda() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void caricaDettagli(Long id) {
		
		try {
			it.webred.ss.ejb.dto.BaseDTO bDto = new it.webred.ss.ejb.dto.BaseDTO();
			fillEnte(bDto);
			bDto.setObj(id);
			
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
	
}
