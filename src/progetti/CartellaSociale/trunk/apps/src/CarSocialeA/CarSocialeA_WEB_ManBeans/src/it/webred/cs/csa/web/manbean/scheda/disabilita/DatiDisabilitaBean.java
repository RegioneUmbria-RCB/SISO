package it.webred.cs.csa.web.manbean.scheda.disabilita;

import it.webred.cs.csa.ejb.dto.BaseDTO;

import it.webred.cs.csa.web.manbean.scheda.SchedaValiditaBaseBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsADisabilita;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.data.model.CsTbDisabTipologia;
import it.webred.cs.jsf.bean.ValiditaCompBaseBean;
import it.webred.cs.jsf.interfaces.IDatiValiditaList;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

@ManagedBean
@SessionScoped
public class DatiDisabilitaBean extends SchedaValiditaBaseBean implements IDatiValiditaList{
	
	private List<SelectItem> lstGravita;
	private List<SelectItem> lstTipologia;
	private List<CsTbDisabTipologia> lstCsTbDisabTipologia;
	private List<SelectItem> lstEnte;
	
	@Override
	public Object getTypeClass() {
		return new CsADisabilita();
	}
	
	@Override
	public String getTypeComponent() {
		return "pnlDatiDisabilita";
	}
	
	@Override
	public String getCodiceTab() {
		return "D";
	}
	
	@Override
	public void nuovo() {
		
		DatiDisabilitaComp comp = new DatiDisabilitaComp();
		this.valorizzaComboComp(comp);
		comp.setDataInizio(new Date());
		listaComponenti.add(0, comp);
		super.nuovo();
				
	}
	
	@Override
	public CsADisabilita getCsFromComponente(Object obj) {
		
		DatiDisabilitaComp comp = (DatiDisabilitaComp) obj;
		
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);		
		
		CsADisabilita cs = new CsADisabilita();
		if(comp.getId() != null){
			//update
			dto.setObj(comp.getId());
			cs = schedaService.getDisabilitaById(dto);
		} else {
			//nuovo
			CsASoggettoLAZY sogg = new CsASoggettoLAZY();
			sogg.setAnagraficaId(soggettoId);
			cs.setCsASoggetto(sogg);
		}
			
		cs.setAnno(comp.getAnno());
		cs.setCertificatore(comp.getCertificatore());
		cs.setConsulenzaCop(comp.isConsulenzaCOP()?"1":"0");
		cs.setDf(comp.isDF()?"1":"0");
		cs.setEnteId(comp.getIdEnte());
		cs.setGravitaId(comp.getIdGravita());
		cs.setTipologiaId(comp.getIdTipologia());
		cs.setConsulenza(comp.isNisConsulenza()?"1":"0");
		cs.setOrientamento(comp.isNisOrientamento()?"1":"0");
		cs.setValutazione(comp.isNisValutazione()?"1":"0");
		cs.setPdf(comp.isPDF()?"1":"0");
		cs.setTerapie(comp.getTerapie());
		cs.setDataInizioApp(comp.getDataInizio());
		if(comp.getDataInizio() == null)
			cs.setDataInizioApp(new Date());
		cs.setDataFineApp(comp.getDataFine());
		if(comp.getDataFine() == null)
			cs.setDataFineApp(DataModelCostanti.END_DATE);
		
		return cs;
		
	}
	
	@Override
	public DatiDisabilitaComp getComponenteFromCs(Object obj) {
		

		
		CsADisabilita cs = (CsADisabilita) obj;
		
		DatiDisabilitaComp comp = new DatiDisabilitaComp();
		comp.setAnno(cs.getAnno());
		comp.setCertificatore(cs.getCertificatore());
		comp.setConsulenzaCOP("1".equals(cs.getConsulenzaCop()));
		comp.setDF("1".equals(cs.getDf()));
		comp.setIdEnte(cs.getEnteId());
		comp.setIdGravita(cs.getGravitaId());
		comp.setIdTipologia(cs.getTipologiaId());
		comp.setNisConsulenza("1".equals(cs.getConsulenza()));
		comp.setNisOrientamento("1".equals(cs.getOrientamento()));
		comp.setNisValutazione("1".equals(cs.getValutazione()));
		comp.setPDF("1".equals(cs.getPdf()));
		comp.setTerapie(cs.getTerapie());
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
			DatiDisabilitaComp disComp = (DatiDisabilitaComp) comp;
			
			if(disComp.getIdGravita() == null || disComp.getIdGravita().intValue() == 0) {
				ok = false;
				this.addErrorCampiObbligatori(getNomeTab(), "Gravità");
			}
			
			if(disComp.getIdTipologia() == null || disComp.getIdTipologia().intValue() == 0) {
				ok = false;
				this.addErrorCampiObbligatori(getNomeTab(), "Tipologia");
			}
		}
		
		return ok;
		
	}

	@Override
	public Object getCsFromComponenteCopy(Object obj) {
		return getCsFromComponente(obj);
	}	
	
	@Override
	protected boolean validaNumeroSituazioniAperte() {
		if(this.getNumeroSituazioniAperte()>1) {
			addWarningFromProperties("warn.numero.situazioniD.aperte");
			return false;
		}
		return true;
	}

	@Override
	public String getNomeTab() {
		return "Disabilità";
	}

	@Override
	protected void caricaValoriCombo() {
		this.loadLstEnte();
		this.loadLstGravita();
		this.loadLstTipologia();
	}

	@Override
	protected void valorizzaComboComp(ValiditaCompBaseBean compGen) {
		DatiDisabilitaComp comp = (DatiDisabilitaComp) compGen;
		comp.setLstGravita(lstGravita);
		comp.setLstTipologia(lstTipologia);
		comp.setLstEnte(lstEnte);
		comp.setLstCsTbDisabTipologia(lstCsTbDisabTipologia);
	}
	
	
	private void loadLstTipologia() {
		lstTipologia = new ArrayList<SelectItem>();
		CeTBaseObject bo = new CeTBaseObject();
		fillEnte(bo);
		lstCsTbDisabTipologia = confService.getDisabTipologia(bo);
		if (lstCsTbDisabTipologia != null) {
			for (CsTbDisabTipologia obj : lstCsTbDisabTipologia) {
				lstTipologia.add(new SelectItem(obj.getId(), obj.getDescrizione()));
			}
		}		
	}

	private void loadLstEnte() {
		CeTBaseObject bo = new CeTBaseObject();
		fillEnte(bo);
		List<KeyValueDTO> lst = confService.getDisabEnte(bo);
		lstEnte = this.convertiLista(lst);
		lstEnte.add(0, new SelectItem(null, "- seleziona -"));

	}

	private void loadLstGravita() {
		CeTBaseObject bo = new CeTBaseObject();
		fillEnte(bo);
		List<KeyValueDTO> lst = confService.getDisabGravita(bo);
		lstGravita = this.convertiLista(lst);
		lstGravita.add(0, new SelectItem(null, "- seleziona -"));	
	}
	
}
