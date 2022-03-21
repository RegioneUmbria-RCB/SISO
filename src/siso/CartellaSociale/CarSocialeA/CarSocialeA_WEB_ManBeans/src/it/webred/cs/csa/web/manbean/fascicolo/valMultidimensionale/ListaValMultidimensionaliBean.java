package it.webred.cs.csa.web.manbean.fascicolo.valMultidimensionale;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.SchedaBarthelDTO;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloCompBaseBean;
import it.webred.cs.csa.web.manbean.report.ReportBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.jsf.interfaces.IListaValMultidimensionale;
import it.webred.cs.json.barthel.ISchedaBarthel;
import it.webred.cs.json.barthel.ManSchedaBarthelBaseBean;
import it.webred.cs.json.valMultidimensionale.IValMultidimensionale;
import it.webred.cs.json.valMultidimensionale.ValMultidimensionaleManBaseBean;

import org.primefaces.context.RequestContext;

public class ListaValMultidimensionaliBean extends FascicoloCompBaseBean implements IListaValMultidimensionale {

	private LazyListaValMultidimensionaliModel lazyListaSchedeMultidimModel;
	private IValMultidimensionale currSchedaMultidimManBean;
	
	@Override
	protected void initializeData(Object data) {
		this.lazyListaSchedeMultidimModel = new LazyListaValMultidimensionaliModel();
		this.lazyListaSchedeMultidimModel.setup(this.csASoggetto);
	}

	public void openDialogOnNew()
	{
		try {
			currSchedaMultidimManBean = ValMultidimensionaleManBaseBean.initISchedaMultidimensionale("", csASoggetto);
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
			
			this.currSchedaMultidimManBean = ValMultidimensionaleManBaseBean.initISchedaMultidimensionale(scheda, csASoggetto);
	
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public IValMultidimensionale getJsonFromScheda(CsDValutazione scheda){
		this.inizializzaJsonFromScheda(scheda);
		return this.currSchedaMultidimManBean;
	}

	//SISO-812
	public void salvaSecondoLivello(){
		boolean bsaved=this.currSchedaMultidimManBean.save(getCurrentOpSettore().getCsOSettore().getId());
		if(bsaved){
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
		}
	}
	
	public void salva() {
		boolean bsaved=this.currSchedaMultidimManBean.save();
		if(bsaved){
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
		}
	}
	
	public boolean salvaSecondoLivelloBarthel() {
		boolean bsaved=this.manSchedaBarthelBean.save(getCurrentOpSettore().getCsOSettore().getId());
		if(bsaved){
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
		}
		return bsaved;
	}
	
	public boolean salvaBarthel() {
		boolean bsaved=this.manSchedaBarthelBean.save();
		if(bsaved){
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
		}
		return bsaved;
	}

	public void reset(){
		this.currSchedaMultidimManBean = null;
	}
	
	public void beforElimina(Long idDiario)
	{   
		CsDValutazione scheda = this.getValutazioneById(idDiario);
		inizializzaJsonFromScheda(scheda);
	}

	public void elimina()
	{

		this.currSchedaMultidimManBean.elimina();
		this.reset();
	}

	public boolean existsDatiStorici() {
		return !lazyListaSchedeMultidimModel.isEmpty();
	}


	public LazyListaValMultidimensionaliModel getLazyListaSchedeMultidimModel() {
		return lazyListaSchedeMultidimModel;
	}

	public void setLazyListaSchedeMultidimModel(
			LazyListaValMultidimensionaliModel lazyListaSchedeMultidimModel) {
		this.lazyListaSchedeMultidimModel = lazyListaSchedeMultidimModel;
	}

	public IValMultidimensionale getCurrSchedaMultidimManBean() {
		return currSchedaMultidimManBean;
	}

	public void setCurrSchedaMultidimManBean(
			IValMultidimensionale currSchedaMultidimManBean) {
		this.currSchedaMultidimManBean = currSchedaMultidimManBean;
	}

	//**************************************************************************************
	// SCHEDA BARTHEL
	//**************************************************************************************
	private ISchedaBarthel manSchedaBarthelBean;

	public ISchedaBarthel getManSchedaBarthelBean() {
		return manSchedaBarthelBean;
	}
	
	private CsDValutazione loadSchedaBarthel(CsDValutazione scheda) throws Exception{
		
		CsDValutazione valutazioneSchedaMultidim = scheda;

		SchedaBarthelDTO barthelDto = new SchedaBarthelDTO();
		fillEnte( barthelDto );
		barthelDto.setDiarioPadreId( valutazioneSchedaMultidim.getDiarioId() );
		barthelDto.setTipoDiarioId(DataModelCostanti.TipoDiario.BARTHEL_ID);
		CsDValutazione valutazioneSchedaBarthel = diarioService.findValutazioneChildByPadreId(barthelDto);	
		return valutazioneSchedaBarthel;
	}
	
	private ISchedaBarthel loadISchedaBarthel(CsDValutazione schedaMultidim) throws Exception{
		CsDValutazione valutazioneSchedaBarthel = this.loadSchedaBarthel(schedaMultidim);
		if(valutazioneSchedaBarthel!=null)
			return ManSchedaBarthelBaseBean.initSchedaBarthel(schedaMultidim, valutazioneSchedaBarthel, csASoggetto);
		else return null;
	}
	
	private ISchedaBarthel loadOrInitISchedaBarthel(CsDValutazione schedaMultidim) throws Exception{ //Se non presente ne inizializza una vuota
		CsDValutazione valutazioneSchedaBarthel = this.loadSchedaBarthel(schedaMultidim);
		return ManSchedaBarthelBaseBean.initSchedaBarthel(schedaMultidim, valutazioneSchedaBarthel, csASoggetto);
	}
	
	
	@Override
	public void onViewBarthel(CsDValutazione schedaMultidim) throws Exception {

		manSchedaBarthelBean = this.loadOrInitISchedaBarthel(schedaMultidim);	
		RequestContext.getCurrentInstance().addCallbackParam("isShowDialogBarthel", true);

	}
	
	
/*	public ISchedaBarthel getSchedaBarthel(CsDValutazione schedaMultidim){
		try{
			onViewBarthel(schedaMultidim);
		}catch(Exception e){
			logger.error("Errore caricamento scheda Barthel da Scheda Multidimensionale: "+e.getMessage(), e);
		}
		return manSchedaBarthelBean;
	}*/
	
	
	public void updateDialog(){
		String ver = this.currSchedaMultidimManBean.getVersionLowerCase();
		logger.debug("aggiorno la dialog prima di mostrarla "+ver);
		
	}
	
	public void updateDialogBarthel(){
		String ver = this.manSchedaBarthelBean.getVersionLowerCase();
		logger.debug("aggiorno la dialog barthel prima di mostrarla "+ver);
		
	}
	
	
	@Override
	public void esportaStampa(CsDValutazione scheda) throws Exception{
		ReportBean rb = (ReportBean)getReferencedBean("reportBean");
		ISchedaBarthel sb = loadISchedaBarthel(scheda);
		rb.esportaValMultidimensionale(getJsonFromScheda(scheda), sb );	
	}
	
	public IValMultidimensionale getlastValMultidimensionale() throws Exception {
		this.currSchedaMultidimManBean = lazyListaSchedeMultidimModel.getLastValMultidim();
		return currSchedaMultidimManBean;
	}

}
