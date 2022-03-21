package it.webred.cs.csa.web.manbean.scheda.tribunale;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.web.manbean.scheda.SchedaValiditaBaseBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.StrutturaTribunale;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsATribunale;
import it.webred.cs.data.model.CsTbMicroSegnal;
import it.webred.cs.jsf.bean.ValiditaCompBaseBean;
import it.webred.cs.jsf.interfaces.IDatiValiditaList;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

@ManagedBean
@SessionScoped
public class DatiTribunaleBean extends SchedaValiditaBaseBean implements IDatiValiditaList{
		
	private List<CsTbMicroSegnal> listaMicroDaDb;
	private List<SelectItem> lstMacroSegnalazioni;
	private List<SelectItem> lstMotiviSegnalazioni;
	private List<SelectItem> lstStrutture;
	
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
		this.valorizzaComboComp(comp);
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
			
		cs.setTmCivile(comp.isTMCivile());
		cs.setTmAmm(comp.isTMAmministrativo());
		cs.setPenaleMin(comp.isPenaleMinorile());
		cs.setTo(comp.isTO());
		cs.setNis(comp.isNIS());
		cs.setProcuraMin(comp.isProcuraMinorile());
		cs.setProcuraOrd(comp.isProcuraOrdinaria()); //SISO-855
		cs.setCorteAppello(comp.isCorteAppello());
		
		cs.setInfoNonReperibili(comp.isInfoNonReperibili());
		cs.setNumeroProtocollo(comp.getNumeroProtocollo());
		
		
		if(comp.getIdMacroSegnalazione() != null)
			cs.setMacroSegnalId(new BigDecimal(comp.getIdMacroSegnalazione()));
		if(comp.getIdMicroSegnalazione() != null)
			cs.setMicroSegnalId(new BigDecimal(comp.getIdMicroSegnalazione()));
		if(comp.getIdMotivoSegnalazione() != null)
			cs.setMotivoSegnalId(new BigDecimal(comp.getIdMotivoSegnalazione()));
		
		cs.setPrimoContattoAG(comp.getPrimoContattoAG());
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
		
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);		
		CsATribunale cs = (CsATribunale) obj;
		
		DatiTribunaleComp comp = new DatiTribunaleComp();
		if(cs.getTmCivile()!=null && cs.getTmCivile().booleanValue()) 
			comp.aggiungiStruttura(StrutturaTribunale.TM_CIVILE);
		if(cs.getTmAmm()!=null && cs.getTmAmm().booleanValue()) 
			comp.aggiungiStruttura(StrutturaTribunale.TM_AMMINISTRATIVO);
		if(cs.getPenaleMin()!=null && cs.getPenaleMin().booleanValue()) 
			comp.aggiungiStruttura(StrutturaTribunale.PENALE_MINORILE);
		if(cs.getTo()!=null && cs.getTo().booleanValue()) 
			comp.aggiungiStruttura(StrutturaTribunale.TO);
		if(cs.getNis()!=null && cs.getNis().booleanValue()) 
			comp.aggiungiStruttura(StrutturaTribunale.NIS);
		if(cs.getProcuraMin()!=null && cs.getProcuraMin().booleanValue()) 
			comp.aggiungiStruttura(StrutturaTribunale.PROCURA_MINORILE);
		if(cs.getProcuraOrd()!=null && cs.getProcuraOrd().booleanValue()) 
			comp.aggiungiStruttura(StrutturaTribunale.PROCURA_ORDINARIA);
		if(cs.getCorteAppello()!=null && cs.getCorteAppello().booleanValue()) 
			comp.aggiungiStruttura(StrutturaTribunale.CORTE_APPELLO);
		
		comp.setInfoNonReperibili(cs.getInfoNonReperibili());
		comp.setNumeroProtocollo(cs.getNumeroProtocollo());
		if(cs.getMacroSegnalId() != null)
			comp.setIdMacroSegnalazione(cs.getMacroSegnalId().longValue());
		if(cs.getMicroSegnalId() != null)
			comp.setIdMicroSegnalazione(cs.getMicroSegnalId().longValue());
		if(cs.getMotivoSegnalId() != null)
			comp.setIdMotivoSegnalazione(cs.getMotivoSegnalId().longValue());
		
		comp.setPrimoContattoAG(cs.getPrimoContattoAG());
		comp.setDataInizio(cs.getDataInizioApp());
		comp.setDataFine(cs.getDataFineApp());
		comp.setId(cs.getId());
		
		// SISO-1060
		comp.setDataInserimento(cs.getDtIns());
		comp.setDataModifica(cs.getDtMod());
		comp.setUsrInserimento(super.getCognomeNomeUtente(cs.getUserIns())); //
		comp.setUsrModifica(super.getCognomeNomeUtente(cs.getUsrMod()));

		return comp;

		
	}
	
	@Override
	public boolean validaComponenti() {
		boolean ok = true;
		
		for(ValiditaCompBaseBean comp: listaComponenti) {
			DatiTribunaleComp disComp = (DatiTribunaleComp) comp;
			
			if((disComp.getSelStrutture()==null || disComp.getSelStrutture().isEmpty()) && !disComp.isInfoNonReperibili() ){
				ok = false;
				this.addError(getNomeTab(), "Selezionare almeno un tipo di tribunale o 'Informazioni non reperibili/reperite'");
			}	
		}
		return ok;
		
	}

	@Override
	protected boolean validaNumeroSituazioniAperte() {
		if(this.getNumeroSituazioniAperte()>1) {
			addWarningFromProperties("warn.numero.situazioniT.aperte");
			return false;
		}
		return true;
	}

	@Override
	public String getNomeTab() {
		return "Tribunale";
	}

	@Override
	protected void caricaValoriCombo() {
		this.loadLstMacroSegnalazioni();
		this.loadLstMotiviSegnalazioni();
		this.loadLstStrutture();
		this.loadListaMicroDaDb();
	}

	@Override
	protected void valorizzaComboComp(ValiditaCompBaseBean compGen) {
		DatiTribunaleComp comp = (DatiTribunaleComp) compGen;
		comp.setLstMacroSegnalazioni(lstMacroSegnalazioni);
		comp.setLstMotiviSegnalazioni(lstMotiviSegnalazioni);
		comp.setLstStrutture(lstStrutture);
		comp.setListaMicroDaDb(listaMicroDaDb);
	}
	
	private void loadListaMicroDaDb(){
		CeTBaseObject bo = new CeTBaseObject();
		fillEnte(bo);
		listaMicroDaDb = confService.getMicroSegnalazioni(bo);
	}
	
	private void loadLstMacroSegnalazioni() {
		lstMacroSegnalazioni = new ArrayList<SelectItem>();
		CeTBaseObject bo = new CeTBaseObject();
		fillEnte(bo);
		List<KeyValueDTO> lst = confService.getMacroSegnalazioni(bo);
		lstMacroSegnalazioni = convertiLista(lst);
		lstMacroSegnalazioni.add(0, new SelectItem(null, "- seleziona -"));	
	}

	private void loadLstMotiviSegnalazioni() {
		lstMotiviSegnalazioni = new ArrayList<SelectItem>();
		CeTBaseObject bo = new CeTBaseObject();
		fillEnte(bo);
		List<KeyValueDTO> lst = confService.getMotivoSegnalazioni(bo);
		lstMotiviSegnalazioni = convertiLista(lst);
		lstMotiviSegnalazioni.add(0, new SelectItem(null, "- seleziona -"));
	}
	
	
	private void loadLstStrutture() {
		lstStrutture = new ArrayList<SelectItem>();
		CeTBaseObject bo = new CeTBaseObject();
		fillEnte(bo);
		lstStrutture = this.convertiLista(confService.getStruttureTribunale(bo));
	}

}
