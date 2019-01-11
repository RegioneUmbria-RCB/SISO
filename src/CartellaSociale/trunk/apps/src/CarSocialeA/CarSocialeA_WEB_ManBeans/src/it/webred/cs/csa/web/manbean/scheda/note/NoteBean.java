package it.webred.cs.csa.web.manbean.scheda.note;

import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsACasoBASIC;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.jsf.interfaces.INote;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class NoteBean extends CsUiCompBaseBean implements INote {
	
	private CsACaso caso;

	private String note;
	
	private AccessTableCasoSessionBeanRemote casoService = (AccessTableCasoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableCasoSessionBean");

	public void initialize(CsASoggettoLAZY sogg) {
		logger.debug("*** INIZIO chiamata NoteBean.initialize");

		note = null;
		caso = null;
		if(sogg != null) {
			caso = sogg.getCsACaso();
			note = caso.getNote();
		}
		logger.debug("*** FINE chiamata NoteBean.initialize");
			
	}
	
	public boolean salva() {
		boolean salvato = true;
		try {
			
			IterDTO iterDto = new IterDTO();
			fillEnte(iterDto);
			iterDto.setIdCaso(caso.getId());
			caso = casoService.findCasoById(iterDto);
			
			BaseDTO baseDto = new BaseDTO();
			fillEnte(baseDto);
			caso.setNote(note);
			baseDto.setObj(caso);
			casoService.updateCaso(baseDto);
		
			//addInfoFromProperties("salva.ok");
			
		} catch(Exception e) {
			salvato=false;
			addErrorFromProperties("salva.errorN");
			logger.error(e.getMessage(),e);
		}
		return salvato;
	}
	
	@Override
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public AccessTableCasoSessionBeanRemote getCasoService() {
		return casoService;
	}

	public void setCasoService(AccessTableCasoSessionBeanRemote casoService) {
		this.casoService = casoService;
	}
	
}
