package it.webred.cs.csa.web.manbean.fascicolo.valSinba;

import it.webred.cs.csa.web.manbean.fascicolo.FascicoloCompBaseBean;
import it.webred.cs.csa.web.manbean.report.ReportBean;
import it.webred.cs.data.model.CsDSinba;
import it.webred.cs.jsf.interfaces.IListaValSinba;
import it.webred.cs.json.valSinba.IValSinba;
import it.webred.cs.json.valSinba.ValSinbaManBaseBean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.primefaces.context.RequestContext;

public class ListaValSinbaBean extends FascicoloCompBaseBean implements IListaValSinba {

	private LazyListaValSinbaModel lazyListaSchedeValSinbaModel;

	private IValSinba currSchedaSinbaManBean;
	
	@Override
	protected void initializeData(Object data) {
		this.lazyListaSchedeValSinbaModel = new LazyListaValSinbaModel();
		this.lazyListaSchedeValSinbaModel.setup(this.getCsASoggetto());
	}

	public void openDialogOnNew() {
		try {
			currSchedaSinbaManBean = ValSinbaManBaseBean.initISchedaSinba("", this.csASoggetto);
			RequestContext.getCurrentInstance().addCallbackParam("isShowDialog", true);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
	}

	public void setOnModifica(CsDSinba sinba) throws Exception {
		//CsDValutazione schedaCompleta = this.getValutazioneById(idDiario);
		currSchedaSinbaManBean = ValSinbaManBaseBean.initISchedaSinba(sinba);
	    if (currSchedaSinbaManBean != null) {
    	    //confronto numericamente i codici prestazioni 
    		if(currSchedaSinbaManBean.codiciPrestazioneDaAggiornare(true)){
            	//sinbaManBean.visualizzaPnlAggiornaPrestazioni();
    			RequestContext.getCurrentInstance().addCallbackParam("isShowDialogPrestazioni", true);
    		}else
    			RequestContext.getCurrentInstance().addCallbackParam("isShowDialog", true);
    	}
	}
	
	public void setOnCopy(CsDSinba sinba){
		currSchedaSinbaManBean = ValSinbaManBaseBean.initISchedaSinba("", csASoggetto);
		try{
			IValSinba schedaO = ValSinbaManBaseBean.initISchedaSinba(sinba);
			currSchedaSinbaManBean.initCopia(schedaO);
			RequestContext.getCurrentInstance().addCallbackParam("isShowDialog", true);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
	}
	
	/*Aggiorna la maschera interna, non serve inizializzazione del dialog*/
	public void copiaDatiDaUltimaValutazione(){

		try {
			CsDSinba ultimoSinba = null;
			List<CsDSinba> valPrecedenti = this.lazyListaSchedeValSinbaModel.getCsDSinbas();
			//currSchedaSinbaManBean = ValSinbaManBaseBean.initISchedaSinba("", soggetto);
			
			  for (CsDSinba sinba : valPrecedenti)
			   {
				   if (sinba.getCsDDiario().getDtIns() == null ){
					   continue;
				   }
				   
			      if(ultimoSinba == null  || ultimoSinba.getCsDDiario().getDtIns().before(sinba.getCsDDiario().getDtIns()))
			      {
				     ultimoSinba = sinba;
			      }
			    }
				
			  if(ultimoSinba!=null){
				  IValSinba schedaO = ValSinbaManBaseBean.initISchedaSinba(ultimoSinba);
				  currSchedaSinbaManBean.initCopia(schedaO);
			  }
								   
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	@Override
	public Boolean getEsisteStorico() {
		Boolean eStorico = false;
		List<CsDSinba> valPrecedenti = this.lazyListaSchedeValSinbaModel.getCsDSinbas();
	
	   if (valPrecedenti!=null && !valPrecedenti.isEmpty())
		   eStorico = true;
	   
	   return eStorico;
	}

	public void salva() {
		boolean bsaved=this.currSchedaSinbaManBean.save();
		if(bsaved){
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
		}
	}
	
	public void allineaCodiciPrestazione(){
		boolean bsaved = currSchedaSinbaManBean.allineaCodiciPrestazione();
		if(bsaved){
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
		}
	}
	
	public void salvaSecondoLivello() {
		boolean bsaved=this.currSchedaSinbaManBean.save(getCurrentOpSettore().getCsOSettore().getId());
		if(bsaved){
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
		}
	}
	
	public void reset(){
		this.currSchedaSinbaManBean = null;
	}

	public void elimina() {
        
		this.currSchedaSinbaManBean.elimina();
		this.reset();
	}
	
/*	public void saveAndUpdate() {
		
		currSchedaSinbaManBean.save();
		aggiornaLista();
	}

	public void aggiornaLista(){
		FascicoloBean fascicoloBean = (FascicoloBean) this.getBeanReference("fascicoloBean");
		try {
			fascicoloBean.initializeValSinbaTab(soggetto);
		} catch (Exception e) {
			logger.error(e);
		}
		
	}*/
	// ********GETTERS AND SETTERS

	@Override
	public boolean isReadOnly() {
        //return this.readOnly || !this.esistonoPrestazioni() || !isMinore();
		//SISO-1158
		return this.readOnly || !isMinore(); 
		//SISO-1158 FINE
	}

	public boolean existsDatiStorici() {
		return !this.lazyListaSchedeValSinbaModel.isEmpty();
	}

	public LazyListaValSinbaModel getLazyListaSchedeValSinbaModel() {
		return lazyListaSchedeValSinbaModel;
	}

	public void setLazyListaSchedeValSinbaModel(
			LazyListaValSinbaModel lazyListaSchedeValSinbaModel) {
		this.lazyListaSchedeValSinbaModel = lazyListaSchedeValSinbaModel;
	}

	public IValSinba getCurrSchedaSinbaManBean() {
		return currSchedaSinbaManBean;
	}

	public void setCurrSchedaSinbaManBean(IValSinba currSchedaSinbaManBean) {
		this.currSchedaSinbaManBean = currSchedaSinbaManBean;
	}

	public void updateDialog(){
		String ver = this.currSchedaSinbaManBean.getVersionLowerCase();
		logger.debug("aggiorno la dialog prima di mostrarla "+ver);
		
	}
	
	@Override
	public void esportaStampa(CsDSinba scheda) throws Exception{
		ReportBean rb = (ReportBean)getReferencedBean("reportBean");
		IValSinba val = ValSinbaManBaseBean.initISchedaSinba(scheda);
		rb.esportaValSinba(val);	
	}
	
	public IValSinba getlastValSinba() throws Exception {
		this.currSchedaSinbaManBean = this.lazyListaSchedeValSinbaModel.getLastValMultidim();
		return currSchedaSinbaManBean;
	}

	private Boolean esistonoPrestazioni() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();//giorno attuale
		c.add(Calendar.YEAR, -1);
		
		List<String> lstPrestazioni = ValSinbaManBaseBean.leggiCodiciPrestazione(csASoggetto.getCsAAnagrafica().getCf(), formatter.format(c.getTime()), formatter.format(new Date()), "E");
		return lstPrestazioni.size()>0;
		
	}
	
	private Boolean isMinore(){
		return calcoloEta(csASoggetto.getCsAAnagrafica().getDataNascita()) < 18;
	}
	
	public boolean checkDisabledModifica(Long idRow,  Date dataExport ){
	
		Long lastInsertedId =  this.lazyListaSchedeValSinbaModel.getLastSinbaInsertedId();
		Long lastInsertedExp = this.lazyListaSchedeValSinbaModel.getLastSinbaExportedId();
		
		boolean res=false;
		
		if(lastInsertedId.intValue() > lastInsertedExp.intValue()){
			if((idRow.longValue() != lastInsertedId.intValue())) res=true;
			else res=false;
		}else res=true;
	
		return res;
	}
	public boolean checkDisabledCopia(Long idRow, Date dataExport ){
		Long lastInsertedId =  this.lazyListaSchedeValSinbaModel.getLastSinbaInsertedId();
		Long lastInsertedExp = this.lazyListaSchedeValSinbaModel.getLastSinbaExportedId();
		
		boolean res=false;
		if(lastInsertedId.intValue() > lastInsertedExp.intValue()){
			if((idRow.longValue() != lastInsertedId.intValue())) res=true;
			else res=false;
		}
		else{
			if (idRow.longValue() != lastInsertedExp.intValue()) res=true;
			else res=false;
		}
		return res;
	}
	
	public int calcoloEta(Date dataNascita) {
		int eta = 0;
		if (dataNascita != null) {
			long today = System.currentTimeMillis();
			Date dataToday = new Date(today);
			long age = dataToday.getTime() - dataNascita.getTime();
			int days = (int) Math.round(age / 86400000.0);
			eta = (days / 365);
//			
		}
		return eta;
	}

}
