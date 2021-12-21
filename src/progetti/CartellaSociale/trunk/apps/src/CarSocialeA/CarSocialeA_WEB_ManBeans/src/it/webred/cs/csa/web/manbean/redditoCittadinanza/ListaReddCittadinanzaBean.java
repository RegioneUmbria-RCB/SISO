package it.webred.cs.csa.web.manbean.redditoCittadinanza;

import it.webred.cs.data.DataModelCostanti.PermessiCartella;
import it.webred.cs.jsf.interfaces.IListaReddCittadinanza;
import it.webred.cs.jsf.manbean.IterDialogMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.primefaces.model.LazyDataModel;

@ManagedBean
@ViewScoped
public class ListaReddCittadinanzaBean extends CsUiCompBaseBean implements IListaReddCittadinanza {

	private LazyDataModel<DatiRdC> lazyListaReddCittadinanzaModel;
	private DatiRdC schedaSelected;
	private String note;
	private String fonte;
	
	@ManagedProperty( value="#{iterDialogMan}")
	private IterDialogMan iterDialogMan;
	
	public ListaReddCittadinanzaBean(){
		fonte = "GePI (Gestione Patti per l'Inclusione sociale)";
		this.lazyListaReddCittadinanzaModel= new LazyListaReddCittadinanza();
	}
	
	public void vediScheda() {
		
		try {
			
			if(schedaSelected!=null){
				/*scheda.setStato(DataModelCostanti.SchedaSegr.STATO_VISTA);
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(scheda);
				schedaSegrService.updateSchedaSegr(dto);*/
			}
		
		} catch(Exception e) {
			addErrorFromProperties("salva.error");
			logger.error(e.getMessage(),e);
		}
	}
	
	public ActionListener getCloseDialog() {
	    return new ActionListener() {
	        @Override
	        public void processAction(ActionEvent event) throws AbortProcessingException {
	        	//loadListaCasi();
	        }
	    };
	}
	
	public IterDialogMan getIterDialogMan() {
		return iterDialogMan;
	}

	public void setIterDialogMan(IterDialogMan iterDialogMan) {
		this.iterDialogMan = iterDialogMan;
	}
	
	public boolean isRenderListaRdC(){
		return this.isAccessoDatiRdC();
	}
	
	public boolean isRenderNuovaCartella() {
		return checkPermesso(PermessiCartella.ITEM, PermessiCartella.CREAZIONE_CASO);
	}
	
	public boolean isRenderCaricaCartella() {
		return checkPermesso(PermessiCartella.ITEM, PermessiCartella.CREAZIONE_CASO);
	}

	public DatiRdC getSchedaSelected() {
		return schedaSelected;
	}

	public void setSchedaSelected(DatiRdC schedaSelected) {
		this.schedaSelected = schedaSelected;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public LazyDataModel<DatiRdC> getLazyListaReddCittadinanzaModel() {
		return lazyListaReddCittadinanzaModel;
	}

	public void setLazyListaReddCittadinanzaModel(
			LazyDataModel<DatiRdC> lazyListaReddCittadinanzaModel) {
		this.lazyListaReddCittadinanzaModel = lazyListaReddCittadinanzaModel;
	}

	@Override
	public String getFonte() {
		return fonte;
	}

	public void setFonte(String fonte) {
		this.fonte = fonte;
	}

}
