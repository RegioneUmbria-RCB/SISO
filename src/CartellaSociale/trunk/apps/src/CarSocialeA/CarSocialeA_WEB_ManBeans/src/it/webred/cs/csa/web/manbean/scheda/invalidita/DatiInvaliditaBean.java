package it.webred.cs.csa.web.manbean.scheda.invalidita;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.web.manbean.scheda.SchedaValiditaBaseBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsADatiInvalidita;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsTbIcd10;
import it.webred.cs.data.model.CsTbIcd9;
import it.webred.cs.jsf.bean.ValiditaCompBaseBean;
import it.webred.cs.jsf.interfaces.IDatiValiditaList;

import java.math.BigDecimal;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class DatiInvaliditaBean extends SchedaValiditaBaseBean implements IDatiValiditaList{
	
	private AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
	
	@Override
	public Object getTypeClass() {
		return new CsADatiInvalidita();
	}
	
	@Override
	public String getTypeComponent() {
		return "pnlDatiInvalidita";
	}
	
	@Override
	public void nuovo() {
		
		DatiInvaliditaComp comp = new DatiInvaliditaComp();
		comp.setDataInizio(new Date());
		listaComponenti.add(0, comp);
		super.nuovo();
				
	}
	
	@Override
	public String getCodiceTab() {
		return "I";
	}
	
	@Override
	public CsADatiInvalidita getCsFromComponente(Object obj) {
		
		DatiInvaliditaComp comp = (DatiInvaliditaComp) obj;
		
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);		
		
		CsADatiInvalidita cs = new CsADatiInvalidita();
		if(comp.getId() != null){
			//update
			dto.setObj(comp.getId());
			cs = schedaService.getDatiInvaliditaById(dto);
		} else {
			//nuovo
			CsASoggettoLAZY sogg = new CsASoggettoLAZY();
			sogg.setAnagraficaId(soggettoId);
			cs.setCsASoggetto(sogg);
		}
			
		cs.setInValutazione(comp.isInValutazione()?"1":"0");
		cs.setIndennitaFrequenza(comp.isIndennitaFrequenza()?"1":"0");
		
		cs.setAccompagnamento(comp.isAccompagnamento()?"1":"0");
		cs.setCertificazioneH(comp.isCertificazioneH()?"1":"0");
		cs.setDataCertificazione(comp.getDataCertificazione());
		cs.setDataInvalidita(comp.getDataInvalidita());
		cs.setDataScadenzaCertificazione(comp.getDataCertificazioneScadenza());
		cs.setDataScadenzaInvalidita(comp.getDataInvaliditaScadenza());
		if(comp.getIcd10D1Bean().getIdSceltaSalvata() != null)
			cs.setIcd10d1Id(new BigDecimal(comp.getIcd10D1Bean().getIdSceltaSalvata()));
		if(comp.getIcd9D1Bean().getIdSceltaSalvata() != null)
			cs.setIcd9d1Id(new BigDecimal(comp.getIcd9D1Bean().getIdSceltaSalvata()));
		if(comp.getIcd10D2Bean().getIdSceltaSalvata() != null)
			cs.setIcd10d2Id(new BigDecimal(comp.getIcd10D2Bean().getIdSceltaSalvata()));
		if(comp.getIcd9D2Bean().getIdSceltaSalvata() != null)
			cs.setIcd9d2Id(new BigDecimal(comp.getIcd9D2Bean().getIdSceltaSalvata()));
		cs.setInvaliditaCivile(comp.isInvalidita()?"1":"0");
		cs.setInvaliditaCivileInCorso(comp.isInvaliditaInCorso()?"1":"0");
		cs.setLegge104(comp.isLegge104()?"1":"0");
		cs.setNoteSanitarie(comp.getNote());
		cs.setPercInvalidita(comp.getInvaliditaPerc());
		
		cs.setDataInizioApp(comp.getDataInizio());
		if(comp.getDataInizio() == null)
			cs.setDataInizioApp(new Date());
		cs.setDataFineApp(comp.getDataFine());
		if(comp.getDataFine() == null)
			cs.setDataFineApp(DataModelCostanti.END_DATE);
		
	
		return cs;
		
	}
	
	@Override
	public DatiInvaliditaComp getComponenteFromCs(Object obj) {
		
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);		
		CsADatiInvalidita cs = (CsADatiInvalidita) obj;
		
		DatiInvaliditaComp comp = new DatiInvaliditaComp();
		comp.setInValutazione("1".equals(cs.getInValutazione()));
		comp.setIndennitaFrequenza("1".equals(cs.getIndennitaFrequenza()));
		comp.setAccompagnamento("1".equals(cs.getAccompagnamento()));
		comp.setCertificazioneH("1".equals(cs.getCertificazioneH()));
		comp.setDataCertificazione(cs.getDataCertificazione());
		comp.setDataCertificazioneScadenza(cs.getDataScadenzaCertificazione());
		comp.setDataInvalidita(cs.getDataInvalidita());
		comp.setDataInvaliditaScadenza(cs.getDataScadenzaInvalidita());
		if(cs.getIcd10d1Id() != null) {
			comp.getIcd10D1Bean().setIdSceltaSalvata(cs.getIcd10d1Id().toString());
			dto.setObj(cs.getIcd10d1Id().longValue());
			CsTbIcd10 icd10 = confService.getIcd10ById(dto);
			comp.getIcd10D1Bean().setDescrSceltaSalvata(icd10.getCodice() + " | " + icd10.getDescrizione());
		}
		if(cs.getIcd9d1Id() != null){
			comp.getIcd9D1Bean().setIdSceltaSalvata(cs.getIcd9d1Id().toString());
			dto.setObj(cs.getIcd9d1Id().longValue());
			CsTbIcd9 icd9 = confService.getIcd9ById(dto);
			comp.getIcd9D1Bean().setDescrSceltaSalvata(icd9.getCodice() + " | " + icd9.getDescrizione());
		}
		if(cs.getIcd10d2Id() != null) {
			comp.getIcd10D2Bean().setIdSceltaSalvata(cs.getIcd10d2Id().toString());
			dto.setObj(cs.getIcd10d2Id().longValue());
			CsTbIcd10 icd10 = confService.getIcd10ById(dto);
			comp.getIcd10D2Bean().setDescrSceltaSalvata(icd10.getCodice() + " | " + icd10.getDescrizione());
		}
		if(cs.getIcd9d2Id() != null){
			comp.getIcd9D2Bean().setIdSceltaSalvata(cs.getIcd9d2Id().toString());
			dto.setObj(cs.getIcd9d2Id().longValue());
			CsTbIcd9 icd9 = confService.getIcd9ById(dto);
			comp.getIcd9D2Bean().setDescrSceltaSalvata(icd9.getCodice() + " | " + icd9.getDescrizione());
		}
		comp.setInvalidita("1".equals(cs.getInvaliditaCivile()));
		comp.setInvaliditaInCorso("1".equals(cs.getInvaliditaCivileInCorso()));
		comp.setInvaliditaPerc(cs.getPercInvalidita());
		comp.setLegge104("1".equals(cs.getLegge104()));
		comp.setNote(cs.getNoteSanitarie());
		
		comp.setDataInizio(cs.getDataInizioApp());
		comp.setDataFine(cs.getDataFineApp());
		comp.setId(cs.getId());
		
		return comp;
		
	}

	@Override
	protected boolean validaNumeroSituazioniAperte() {
		if(this.getNumeroSituazioniAperte()>1) {
			addWarningFromProperties("warn.numero.situazioniI.aperte");
			return false;
		}
		return true;
	}
	
}
