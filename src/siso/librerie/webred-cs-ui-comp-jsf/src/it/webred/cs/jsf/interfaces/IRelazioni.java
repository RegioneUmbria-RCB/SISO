package it.webred.cs.jsf.interfaces;

import it.webred.cs.csa.ejb.dto.RelazioneDTO;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.jsf.manbean.DiarioDocsMan;

import java.util.List;

import javax.faces.model.SelectItem;


public interface IRelazioni{
	
	public void initializeData();
	//public void initializeData(Boolean flagGestioneInfo); //SISO-812
	public List<RelazioneDTO> getListaRelazioniDTO();
	public void nuovo();
	public void carica();
	public void salva();
	public List<SelectItem> getListaTipiIntervento();
	public void aggiungiTipoInterventoButton();	
	public String getModalHeader();
	public RelazioneDTO getRelazioneDTO();
	public int getIdxSelected();
//	public Long getIdTipoInterventoSelezionato();
	public List<CsCTipoIntervento> getListaTipiInterventoAttivi();
	public void esportaRelazioneStampa();
	public boolean getLoadRelazione();
	public void loadRelazione();
	public DiarioDocsMan getDiarioDocsMan();	
	public void eliminaDocumento();
	public void stampaTriage();
	public List<SelectItem> getListaRichiestaIndagine();
	public boolean isSelectedProblematicaVerificata();
	public void onSelectCatalogoAttivita();
	public boolean isRenderUpload();
	public boolean isRenderTriage();
	public boolean isRenderEditor();
	public boolean isRenderSAL();
}