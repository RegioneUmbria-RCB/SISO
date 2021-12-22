package it.webred.cs.jsf.interfaces;

import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDDocIndividuale;
import it.webred.cs.jsf.manbean.DiarioDocsMan;
import it.webred.cs.csa.ejb.dto.fascicolo.docIndividuali.DocIndividualeBean;

import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;



public interface IDocIndividuali {
	
	public void initializeData();
	public void nuovo();
	public void caricaPubblico();
	public void caricaPrivato();
	public void indicaLettura();
	public void salva();
	public void elimina();
	public Long getIdCaso();
	public int getIdxSelected();
	public CsDDocIndividuale getDocIndividuale();
	public DiarioDocsMan getDiarioDocsMan();
	public String getModalHeader();
	public List<DocIndividualeBean> getListaDocIndividualiPubblica();
	public List<DocIndividualeBean> getListaDocIndividualiPrivata();
	public boolean isDisableUpload();
	public List<SelectItem> getListaSottocartelleItem();
	public String getDenominazioneOperatore(CsDDiario d);
	
	// #ROMACAPITALE inizio
	public void onTipoDocumentiChange(AjaxBehaviorEvent event);
	public boolean isPannelloProtocolloVisibile();
	public void verificaEsistenzaNumeroProtocollo();
	// #ROMACAPITALE fine
	public String getIntestazione();
}
