package it.webred.cs.csa.web.manbean.fascicolo.provvedimentiMinori;

import org.primefaces.context.RequestContext;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.web.manbean.fascicolo.FascicoloCompBaseBean;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.jsf.interfaces.IListaProvvedimentiMinori;
import it.webred.cs.json.provvedimentiMinori.IProvvedimentiMinori;
import it.webred.cs.json.provvedimentiMinori.ProvvedimentiMinoriManBaseBean;

public class ListaProvvedimentiBean extends FascicoloCompBaseBean implements IListaProvvedimentiMinori {

	private LazyListaProvvedimentiModel lazyListaProvvedimentiModel;
	private IProvvedimentiMinori currProvvedimentiMinoriManBean;
	private CsASoggettoLAZY soggetto;
	private boolean readOnly;

	public ListaProvvedimentiBean(CsASoggettoLAZY soggetto) {
		this.lazyListaProvvedimentiModel = new LazyListaProvvedimentiModel();
		this.lazyListaProvvedimentiModel.setup(soggetto);
		this.soggetto = soggetto;
	}

	public void openDialogOnNew()
	{
		try {
			currProvvedimentiMinoriManBean = ProvvedimentiMinoriManBaseBean.initIProvvedimentiMinori("", soggetto);
			//currProvvedimentiMinoriManBean.setIdCaso(soggetto.getCsACaso().getId()); GIA VALORIZZATA NELLA INIT!!
			//currProvvedimentiMinoriManBean.setSoggettoFascicolo(soggetto);
			RequestContext.getCurrentInstance().addCallbackParam("isShowDialog", true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
	}

	public void setOnModificaProvv(Long idDiario) throws Exception {
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
			
			this.currProvvedimentiMinoriManBean = ProvvedimentiMinoriManBaseBean.initIProvvedimentiMinori(scheda, soggetto);
			//this.currProvvedimentiMinoriManBean.setVisSecondoLivello(null); //SISO-812
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	//SISO-812
    public void salvaSecondoLivello(){
    	boolean bsaved=this.currProvvedimentiMinoriManBean.save(getCurrentOpSettore().getCsOSettore().getId());
		if(bsaved){
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
		}
    }
    
	public void salva() {
		boolean bsaved=this.currProvvedimentiMinoriManBean.save();
		if(bsaved){
			RequestContext.getCurrentInstance().addCallbackParam("saved", true);
		}
	}
	

	public void reset(){
		this.currProvvedimentiMinoriManBean = null;
	}
	
	public void beforElimina(Long idDiario)
	{   
		CsDValutazione scheda = this.getValutazioneById(idDiario);
		inizializzaJsonFromScheda(scheda);
	}

	public void elimina()
	{

		this.currProvvedimentiMinoriManBean.elimina();
		this.reset();
	}

	// ********GETTERS AND SETTERS

	public IProvvedimentiMinori getCurrProvvedimentiMinoriManBean() {
		return currProvvedimentiMinoriManBean;
	}

	public LazyListaProvvedimentiModel getLazyListaProvvedimentiModel() {
		return lazyListaProvvedimentiModel;
	}

	public void setLazyListaProvvedimentiModel(LazyListaProvvedimentiModel lazyListaProvvedimentiModel) {
		this.lazyListaProvvedimentiModel = lazyListaProvvedimentiModel;
	}

	public void setCurrProvvedimentiMinoriManBean(IProvvedimentiMinori currProvvedimentiMinoriManBean) {
		this.currProvvedimentiMinoriManBean = currProvvedimentiMinoriManBean;
	}

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
		return !lazyListaProvvedimentiModel.isEmpty();
	}

	@Override
	protected void initializeData(Object data) {
		// TODO Auto-generated method stub	
	}

}
