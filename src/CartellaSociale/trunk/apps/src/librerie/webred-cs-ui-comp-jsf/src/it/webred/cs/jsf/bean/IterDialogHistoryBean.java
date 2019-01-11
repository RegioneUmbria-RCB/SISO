package it.webred.cs.jsf.bean;

import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.retvalue.CsIterStepByCasoDTO;
import it.webred.cs.data.model.CsItStep;
import it.webred.cs.jsf.manbean.IterInfoStatoMan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class IterDialogHistoryBean extends IterBaseBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected long idCaso;
	protected String opRuolo;
	protected String opUsername;
	
	private List<IterInfoStatoMan> iterInfoStatoMans;
	
	public void initialize(long idCaso, String opUsername, String opRuolo ){
		try {
			this.idCaso = idCaso;
			this.opUsername = opUsername;
			this.opRuolo = opRuolo;
			
			iterInfoStatoMans = new ArrayList<IterInfoStatoMan>();
			
			AccessTableIterStepSessionBeanRemote iterSessionBean = getIterSessioBean();
	
			IterDTO itDto = new IterDTO();
			fillEnte(itDto);
			itDto.setIdCaso(idCaso);
			List<CsIterStepByCasoDTO> iterStepList = iterSessionBean.getIterStepsByCasoDTO(itDto);
			

			
			
			if (iterStepList != null) {
				
				for (CsIterStepByCasoDTO itStep : iterStepList) {
					IterInfoStatoMan itInfo = new IterInfoStatoMan();
					itInfo.initialize( itStep );
					iterInfoStatoMans.add( itInfo );
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addError("Errore nell'inizializzazione della Cronologia!", "Inizializzazione Cronologia non riuscita!");
		}
	}

	public List<IterInfoStatoMan> getIterInfoStatoMans() {
		return iterInfoStatoMans;
	}
}
