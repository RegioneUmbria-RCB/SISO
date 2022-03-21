package it.webred.cs.csa.web.manbean.fascicolo;

import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableExportValutazioniSinbaSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIndirizzoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSinbaSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.jsf.bean.DatiCasoBean;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.util.ArrayList;
import java.util.List;

public abstract class FascicoloCompBaseBean extends CsUiCompBaseBean {

	protected AccessTableDiarioSessionBeanRemote diarioService = (AccessTableDiarioSessionBeanRemote) getCarSocialeEjb( "AccessTableDiarioSessionBean");
	protected AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) getCarSocialeEjb( "AccessTableSoggettoSessionBean");
	protected AccessTableCasoSessionBeanRemote casoService = (AccessTableCasoSessionBeanRemote) getCarSocialeEjb( "AccessTableCasoSessionBean");
	protected AccessTableSchedaSessionBeanRemote schedaService = (AccessTableSchedaSessionBeanRemote) getCarSocialeEjb( "AccessTableSchedaSessionBean");	
	protected AccessTableIndirizzoSessionBeanRemote indirizzoService = (AccessTableIndirizzoSessionBeanRemote) getCarSocialeEjb( "AccessTableIndirizzoSessionBean");
	protected AccessTableExportValutazioniSinbaSessionBeanRemote sinbaExportService = (AccessTableExportValutazioniSinbaSessionBeanRemote) getCarSocialeEjb("AccessTableExportValutazioniSinbaSessionBean");
	protected AccessTableSinbaSessionBeanRemote sinbaService = (AccessTableSinbaSessionBeanRemote) getCarSocialeEjb("AccessTableSinbaSessionBean");
	protected AccessTableAlertSessionBeanRemote alertService = (AccessTableAlertSessionBeanRemote) getCarSocialeEjb("AccessTableAlertSessionBean");
	
	protected Long idCaso;
	protected Long idSoggetto;
	protected CsASoggettoLAZY csASoggetto;
	protected List<CsCCategoriaSociale> catsocCorrenti;
	
	protected boolean readOnly;
	//Inizio SISO-1110
	protected boolean is;
	//Fine SISO-1110
	
	 
	public void initialize(CsASoggettoLAZY soggetto, List<CsCCategoriaSociale> catsocCorrenti, Object data) {
		
		try{
			csASoggetto = soggetto;
			idSoggetto = soggetto!=null ? soggetto.getAnagraficaId() : null;
			idCaso = 	 soggetto!=null ? soggetto.getCsACaso().getId() : null;
			
			if(catsocCorrenti!=null)
				this.catsocCorrenti = catsocCorrenti;
			else
				this.catsocCorrenti = loadCatSocialiAttuali(idSoggetto);
			
			initializeData(data);
					
		}catch(Exception e){
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}

	}
	
	protected abstract void initializeData(Object data);
	
	public void initialize(CsASoggettoLAZY soggetto) {
		
		try{
			initialize(soggetto, null, null);
		}catch(Exception e){
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}

	}
	
	public void initialize(DatiCasoBean selectedCaso){
		try{
			BaseDTO b = new BaseDTO();
			fillEnte(b);
			b.setObj(selectedCaso.getAnagraficaId());
			
			csASoggetto = soggettoService.getSoggettoById(b);

			idSoggetto = csASoggetto.getAnagraficaId();
			idCaso = csASoggetto.getCsACaso().getId();
			
			catsocCorrenti=loadCatSocialiAttuali(idSoggetto);
			
			initializeDataSel(selectedCaso);
		}catch(Exception e){
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
		
	}
	
	protected List<CsCCategoriaSociale> loadCatSocialiAttuali(Long anagraficaId){
		List<CsCCategoriaSociale> catsocCorrenti = new ArrayList<CsCCategoriaSociale>();
		if(anagraficaId!=null){
			BaseDTO b = new BaseDTO();
			fillEnte(b);
			b.setObj(anagraficaId);
			catsocCorrenti = soggettoService.getCatSocAttualiBySoggetto(b);
		}
		return catsocCorrenti;
	}
	
	protected String getFilterCategorie(List<CsCCategoriaSociale> lstCatSocialiAttuali) {
		String filterCategorie = "";
		List<CsCCategoriaSociale> lstCatSocCorrenti = new ArrayList<CsCCategoriaSociale>();

		if (lstCatSocialiAttuali != null && lstCatSocialiAttuali.size() > 0) {
			lstCatSocCorrenti.addAll(lstCatSocialiAttuali);
		}

		for (CsCCategoriaSociale item : lstCatSocCorrenti) {
			if (filterCategorie.length() > 0) {
				filterCategorie = filterCategorie + "|";// metto il separatore solo se sono più di due elementi - non uso la virgola perchè ci sono categorie che la contengono
			}
			filterCategorie = filterCategorie + item.getDescrizione();
		}
		return filterCategorie;
	}
	
	protected List<Long> getLstIdCatSoc(){
		List<Long> lst = new ArrayList<Long>();
		if(this.catsocCorrenti!=null && !this.catsocCorrenti.isEmpty()){
			for(CsCCategoriaSociale c : catsocCorrenti)
				lst.add(c.getId());
		}
		return lst;
	}
	
	public String getDescCatSocialiCorrenti(){
		String s="";
		if(this.catsocCorrenti!=null && !this.catsocCorrenti.isEmpty()){
			for(CsCCategoriaSociale c : catsocCorrenti)
				s+=","+c.getTooltip();
			s=s.substring(1);
		}
		return s;
	}

	protected void initializeDataSel(DatiCasoBean selectedCaso){}
	
	public Long getIdCaso() {
		return idCaso;
	}

	public void setIdCaso(Long idCaso) {
		this.idCaso = idCaso;
	}

	public Long getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public List<CsCCategoriaSociale> getCatsocCorrenti() {
		return catsocCorrenti;
	}

	public void setCatsocCorrenti(List<CsCCategoriaSociale> catsocCorrenti) {
		this.catsocCorrenti = catsocCorrenti;
	}

	public CsASoggettoLAZY getCsASoggetto() {
		return csASoggetto;
	}

	public void setCsASoggetto(CsASoggettoLAZY csASoggetto) {
		this.csASoggetto = csASoggetto;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	
	//SISO-812
	public Boolean getRenderedSecondoLivello(){
		return this.isOperatoreSecondoLivello();
	}
		
	
}
