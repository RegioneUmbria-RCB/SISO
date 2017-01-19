package it.webred.cs.csa.web.manbean.scheda.tribunale;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.web.manbean.scheda.SchedaValiditaBaseBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsATribunale;
import it.webred.cs.jsf.bean.ValiditaCompBaseBean;
import it.webred.cs.jsf.interfaces.IDatiValiditaList;

import java.math.BigDecimal;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class DatiTribunaleBean extends SchedaValiditaBaseBean implements IDatiValiditaList{
		
	@Override
	public Object getTypeClass() {
		return new CsATribunale();
	}
	
	@Override
	public String getTypeComponent() {
		return "pnlDatiTribunale";
	}
	
	@Override
	public String getCodiceTab() {
		return "T";
	}
	
	@Override
	public void nuovo() {
		
		super.nuovo();
		DatiTribunaleComp comp = new DatiTribunaleComp();
		comp.setDataInizio(new Date());
		listaComponenti.add(0, comp);
		super.nuovo();

	}
	
	@Override
	public CsATribunale getCsFromComponente(Object obj) {
		
		DatiTribunaleComp comp = (DatiTribunaleComp) obj;
		
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);		
		
		CsATribunale cs = new CsATribunale();
		if(comp.getId() != null){
			//update
			dto.setObj(comp.getId());
			cs = schedaService.getTribunaleById(dto);
		} else {
			//nuovo
			CsASoggettoLAZY sogg = new CsASoggettoLAZY();
			sogg.setAnagraficaId(soggettoId);
			cs.setCsASoggetto(sogg);
		}
			
		cs.setTmCivile(comp.isTMCivile()?"1":"0");
		cs.setTmAmm(comp.isTMAmministrativo()?"1":"0");
		cs.setPenaleMin(comp.isPenaleMinorile()?"1":"0");
		cs.setTo(comp.isTO()?"1":"0");
		cs.setNis(comp.isNIS()?"1":"0");
		cs.setProcuraMin(comp.isProcuraMinorile()?"1":"0");
		cs.setNumeroProtocollo(comp.getNumeroProtocollo());
		if(comp.getIdMacroSegnalazione() != null)
			cs.setMacroSegnalId(new BigDecimal(comp.getIdMacroSegnalazione()));
		if(comp.getIdMicroSegnalazione() != null)
			cs.setMicroSegnalId(new BigDecimal(comp.getIdMicroSegnalazione()));
		if(comp.getIdMotivoSegnalazione() != null)
			cs.setMotivoSegnalId(new BigDecimal(comp.getIdMotivoSegnalazione()));
		
		cs.setDataInizioApp(comp.getDataInizio());
		if(comp.getDataInizio() == null)
			cs.setDataInizioApp(new Date());
		cs.setDataFineApp(comp.getDataFine());
		if(comp.getDataFine() == null)
			cs.setDataFineApp(DataModelCostanti.END_DATE);
		
		return cs;
		
	}
	
	@Override
	public DatiTribunaleComp getComponenteFromCs(Object obj) {
		logger.debug("*** INIZIO DatiTribunaleBean.getComponenteFromCs ....");

		BaseDTO dto = new BaseDTO();
		fillEnte(dto);		
		CsATribunale cs = (CsATribunale) obj;
		
		DatiTribunaleComp comp = new DatiTribunaleComp();
		comp.setTMCivile("1".equals(cs.getTmCivile()));
		comp.setTMAmministrativo("1".equals(cs.getTmAmm()));
		comp.setPenaleMinorile("1".equals(cs.getPenaleMin()));
		comp.setTO("1".equals(cs.getTo()));
		comp.setNIS("1".equals(cs.getNis()));
		comp.setProcuraMinorile("1".equals(cs.getProcuraMin()));
		comp.setNumeroProtocollo(cs.getNumeroProtocollo());
		if(cs.getMacroSegnalId() != null)
			comp.setIdMacroSegnalazione(cs.getMacroSegnalId().longValue());
		if(cs.getMicroSegnalId() != null)
			comp.setIdMicroSegnalazione(cs.getMicroSegnalId().longValue());
		if(cs.getMotivoSegnalId() != null)
			comp.setIdMotivoSegnalazione(cs.getMotivoSegnalId().longValue());
		
		comp.setDataInizio(cs.getDataInizioApp());
		comp.setDataFine(cs.getDataFineApp());
		comp.setId(cs.getId());
		
		logger.debug("*** FINE DatiTribunaleBean.getComponenteFromCs ....");
		return comp;

		
	}

	@Override
	protected boolean validaNumeroSituazioniAperte() {
		if(this.getNumeroSituazioniAperte()>1) {
			addWarningFromProperties("warn.numero.situazioniT.aperte");
			return false;
		}
		return true;
	}

}
