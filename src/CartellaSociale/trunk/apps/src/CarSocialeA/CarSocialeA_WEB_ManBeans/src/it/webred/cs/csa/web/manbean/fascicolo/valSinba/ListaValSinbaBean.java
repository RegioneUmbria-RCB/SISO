package it.webred.cs.csa.web.manbean.fascicolo.valSinba;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloCompBaseBean;
import it.webred.cs.csa.web.manbean.report.ReportBean;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.jsf.interfaces.IListaValSinba;
import it.webred.cs.json.valSinba.IValSinba;
import it.webred.cs.json.valSinba.ValSinbaManBaseBean;

import org.primefaces.context.RequestContext;

public class ListaValSinbaBean extends FascicoloCompBaseBean implements IListaValSinba{

	private LazyListaValSinbaModel lazyListaSchedeValSinbaModel; 
	private IValSinba currSchedaSinbaManBean;
	private CsASoggettoLAZY soggetto;
	private boolean readOnly;

	public ListaValSinbaBean(CsASoggettoLAZY soggetto) {
		this.lazyListaSchedeValSinbaModel = new LazyListaValSinbaModel();
		this.lazyListaSchedeValSinbaModel.setup(soggetto);
		this.soggetto = soggetto;
	}
	
	@Override
	protected void initializeData(Object data) {
		// TODO Auto-generated method stub	
	}

	public void openDialogOnNew()
	{
		try {
			currSchedaSinbaManBean = ValSinbaManBaseBean.initISchedaSinba("", soggetto);
			//currSchedaMultidimManBean.setIdCaso(soggetto.getCsACaso().getId()); GIA VALORIZZATA NELLA INIT!!
			//currSchedaMultidimManBean.setSoggettoFascicolo(soggetto);
			RequestContext.getCurrentInstance().addCallbackParam("isShowDialog", true);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
	}

	public void setOnModifica(Long idDiario) throws Exception {
		CsDValutazione schedaCompleta = this.getValutazioneById(idDiario);
		inizializzaJsonFromScheda(schedaCompleta);
		RequestContext.getCurrentInstance().addCallbackParam("isShowDialog", true);
	}

	private CsDValutazione getValutazioneById(Long idDiario){
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(idDiario);
		CsDValutazione schedaCompleta = diarioService.findValutazioneById(dto);
		return schedaCompleta;
	}
	
	private void inizializzaJsonFromScheda(CsDValutazione scheda) {
		try {
			
			this.currSchedaSinbaManBean = ValSinbaManBaseBean.initISchedaSinba(scheda, soggetto);
	
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public IValSinba getJsonFromScheda(CsDValutazione scheda){
		this.inizializzaJsonFromScheda(scheda);
		return this.currSchedaSinbaManBean;
	}

	public boolean salva() {
		boolean bsaved=this.currSchedaSinbaManBean.save();
		if(bsaved){
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
		}
		return bsaved;
	}
	
	public void reset(){
		this.currSchedaSinbaManBean = null;
	}
	
	public void beforElimina(Long idDiario)
	{   
		CsDValutazione scheda = this.getValutazioneById(idDiario);
		inizializzaJsonFromScheda(scheda);
	}

	public void elimina()
	{

		this.currSchedaSinbaManBean.elimina();
		this.reset();
	}

	// ********GETTERS AND SETTERS

	public CsASoggettoLAZY getSoggetto() {
		return soggetto;
	}

	public void setSoggetto(CsASoggettoLAZY soggetto) {
		this.soggetto = soggetto;
	}

	public boolean isReadOnly() {
		return this.readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
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
	public void esportaStampa(CsDValutazione scheda) throws Exception{
		ReportBean rb = (ReportBean)this.getReferencedBean("reportBean");
		rb.esportaValSinba(getJsonFromScheda(scheda));	
	}
	
	public IValSinba getlastValMultidimensionale() throws Exception {
		this.currSchedaSinbaManBean = this.lazyListaSchedeValSinbaModel.getLastValMultidim();
		return currSchedaSinbaManBean;
	}

}
