package it.webred.cs.jsf.interfaces;

import it.webred.cs.csa.ejb.dto.pai.pti.CsPaiPTIDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.InserimentoConsuntivazioneDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.RichiestaDisponibilitaPaiPtiDTO;
import it.webred.cs.data.DataModelCostanti.Pai.PERIODO_TEMPORALE;
import it.webred.cs.data.model.CsDPai;

import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.event.TabChangeEvent;

public interface IPai {

	public void onChangeTabView(TabChangeEvent tce);
	
	public void initializeData();

	public void nuovo();

	public void carica();

	public void caricaChiudi();

	public void chiudi();

	public void salva();
	
	public void salvaSecondoLivello();

	public String getModalHeader();

	public void setModalHeader(String modalHeader);

	public String getWidgetVar();

	public void setWidgetVar(String widgetVar);

	public Long getIdxSelected();

	public void setIdxSelected(Long idxSelected);

	public CsDPai getSelectedPai();

	public void setSelectedPai(CsDPai pai);

	public List<SelectItem> getLstTipoPai();
	public List<SelectItem> getLstTipoPaiFiltro();

	public List<SelectItem> getStatusOptions();
	
	//inizio evoluzione-pai
	public boolean isPaiDetailRendered();  
	public void aggiungiTipoInterventoButton();
	public void aggiungiTipoErogazioneButton(); //SISO-748
	//fine evoluzione-pai

	public void salvaGestioneMonitoraggioObiettivi();

	public PERIODO_TEMPORALE[] getListaPeriodi();

	//SISO-1172
	public List<SelectItem> getLstMotivoChiusuraByTipoPai();
	public boolean isAbilitaMenuProgettiAltro();
	
	//Minori in struttura
	public void apriErogazioneButton(Object obj);
	public boolean getProvenienzaConsuntivazione();
	public void erogaServizio(InserimentoConsuntivazioneDTO consuntivazione);
	public void  verificaPeriodi(InserimentoConsuntivazioneDTO consuntivazione) throws Throwable;
	public void aggiornaFLagErogatoConsuntivazione() throws Throwable;
	public void refreshFLagErogatoConsuntivazione() throws Throwable;
	public void avviaModificaPTI(CsPaiPTIDTO pti) throws Exception;
	public void verificaSelezioneMotivo() throws Exception;
	public List<RichiestaDisponibilitaPaiPtiDTO> getLstProgettiAltriEnti();
	public void setLstProgettiAltriEnti(List<RichiestaDisponibilitaPaiPtiDTO> lstProgettiAltriEnti);
	public void caricaListaProgettiAltriEnti() throws Exception;
	public boolean isProgettoPTI();
	public String decodificaObiettiviRaggiunti(Integer idRaggiunti);

	public void salvaDataChiusuraAggiornata() throws Throwable;

}