package it.webred.cs.csa.web.manbean.fascicolo;

import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableExportValutazioniSinbaSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIndirizzoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSinbaSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCCategoriaSocialeBASIC;
import it.webred.cs.data.model.CsOOperatoreBASIC;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.jsf.bean.DatiCasoBean;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.util.ArrayList;
import java.util.List;

public abstract class FascicoloCompBaseBean extends CsUiCompBaseBean {

	protected AccessTableDiarioSessionBeanRemote diarioService = (AccessTableDiarioSessionBeanRemote) getCarSocialeEjb( "AccessTableDiarioSessionBean");
	protected AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) getCarSocialeEjb( "AccessTableSoggettoSessionBean");
	protected AccessTableCasoSessionBeanRemote casoService = (AccessTableCasoSessionBeanRemote) getCarSocialeEjb( "AccessTableCasoSessionBean");
	protected AccessTableConfigurazioneSessionBeanRemote configService = (AccessTableConfigurazioneSessionBeanRemote)getCarSocialeEjb( "AccessTableConfigurazioneSessionBean");
	protected AccessTableOperatoreSessionBeanRemote operatoreService = (AccessTableOperatoreSessionBeanRemote) getCarSocialeEjb( "AccessTableOperatoreSessionBean");	
	protected AccessTableSchedaSessionBeanRemote schedaService = (AccessTableSchedaSessionBeanRemote) getCarSocialeEjb( "AccessTableSchedaSessionBean");	
	protected AccessTableIndirizzoSessionBeanRemote indirizzoService = (AccessTableIndirizzoSessionBeanRemote) getCarSocialeEjb( "AccessTableIndirizzoSessionBean");
	protected AccessTableExportValutazioniSinbaSessionBeanRemote sinbaExportService = (AccessTableExportValutazioniSinbaSessionBeanRemote) getCarSocialeEjb("AccessTableExportValutazioniSinbaSessionBean");
	protected AccessTableSinbaSessionBeanRemote sinbaService = (AccessTableSinbaSessionBeanRemote) getCarSocialeEjb("AccessTableSinbaSessionBean");
	protected AccessTableAlertSessionBeanRemote alertService = (AccessTableAlertSessionBeanRemote) getCarSocialeEjb("AccessTableAlertSessionBean");
	
	protected Long idCaso;
	protected Long idSoggetto;
	protected CsASoggettoLAZY csASoggetto;
	protected List<CsCCategoriaSocialeBASIC> catsocCorrenti;
	
	protected boolean readOnly;
	//Inizio SISO-1110
	protected boolean is;
	//Fine SISO-1110
	
	 
	public void initialize(CsASoggettoLAZY soggetto, List<CsCCategoriaSocialeBASIC> catsocCorrenti, Object data) {
		
		try{
			csASoggetto = soggetto;
			idSoggetto = soggetto!=null ? soggetto.getAnagraficaId() : null;
			idCaso = 	 soggetto!=null ? soggetto.getCsACaso().getId() : null;
			
			if(catsocCorrenti!=null)
				this.catsocCorrenti = catsocCorrenti;
			else
				this.catsocCorrenti = loadCatSocialiAttuali(soggetto);
			
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
			b.setObj(selectedCaso.getSoggetto().getAnagraficaId());
			
			csASoggetto = soggettoService.getSoggettoById(b);

			idSoggetto = csASoggetto.getAnagraficaId();
			idCaso = csASoggetto.getCsACaso().getId();
			
			catsocCorrenti=loadCatSocialiAttuali(csASoggetto);
			
			initializeDataSel(selectedCaso);
		}catch(Exception e){
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
		
	}
	
	private List<CsCCategoriaSocialeBASIC> loadCatSocialiAttuali(CsASoggettoLAZY soggetto){
		//Recupero la categoria sociale
		List<CsCCategoriaSocialeBASIC> catsocCorrenti = new ArrayList<CsCCategoriaSocialeBASIC>();
		if(soggetto!=null){
			BaseDTO b = new BaseDTO();
			fillEnte(b);
			b.setObj(soggetto.getAnagraficaId());
			catsocCorrenti = soggettoService.getCatSocAttualiBySoggetto(b);
		}
		return catsocCorrenti;
	}
	
	protected List<Long> getLstIdCatSoc(){
		List<Long> lst = new ArrayList<Long>();
		if(this.catsocCorrenti!=null && !this.catsocCorrenti.isEmpty()){
			for(CsCCategoriaSocialeBASIC c : catsocCorrenti)
				lst.add(c.getId());
		}
		return lst;
	}
	
	public String getDescCatSocialiCorrenti(){
		String s="";
		if(this.catsocCorrenti!=null && !this.catsocCorrenti.isEmpty()){
			for(CsCCategoriaSocialeBASIC c : catsocCorrenti)
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

	public List<CsCCategoriaSocialeBASIC> getCatsocCorrenti() {
		return catsocCorrenti;
	}

	public void setCatsocCorrenti(List<CsCCategoriaSocialeBASIC> catsocCorrenti) {
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
	
	protected CsOOperatoreBASIC getOperResponsabileCaso() throws Exception {
		CsOOperatoreBASIC operatoreResponsabile = null;

		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(idCaso);
		operatoreResponsabile = casoService.findResponsabileBASIC(dto);
		
		return operatoreResponsabile;
	}
	
	protected CsOOperatoreSettore getOpSettoreCreatoreCaso() throws Exception {
		CsOOperatoreSettore creatore = null;
		
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(idCaso);
	
		//creatore
		creatore = casoService.findCreatoreCaso(dto);
	
		return creatore;
	}
	
	//SISO-812
	public Boolean getRenderedSecondoLivello(){
		return this.isOperatoreSecondoLivello();
	}
		
	
}
