package it.webred.cs.jsf.interfaces;

import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsRelSettCatsocEsclusiva;
import it.webred.cs.data.model.CsRelSettCsocTipoInter;

import java.util.List;

import javax.faces.model.SelectItem;



public interface IGestioneCatSociale {
	
	public void caricaSettTipiinter();
	public void caricaSettTipidiario();
	public void attivaCatSociali();
	public void disattivaCatSociali();
	public void eliminaCatSociali();
	public void nuovaCatSociale();
	public void aggiungiSettoreTipoInter();
	public void attivaSettoriTipiInter();
	public void disattivaSettoriTipiInter();
	public void eliminaSettoriTipiInter();
	public void aggiungiSettoreTipoDiario();
	public void attivaSettoriTipiDiario();
	public void disattivaSettoriTipiDiario();
	public void eliminaSettoriTipiDiario();
	
	public void aggiornaSettori();
	public List<CsCCategoriaSociale> getLstCatSociali();
	public List<CsCCategoriaSociale> getSelectedCatSociali();
	public String getNewCatSocDescr();
	public CsCCategoriaSociale getSelectedCatSociale();
	public List<SelectItem> getLstTipiIntervento();
	public Long getNewTipoInterId();
	public List<SelectItem> getLstSettori();
	public Long getNewSettoreId();
	public List<CsRelSettCsocTipoInter> getLstRelSettTipointer();
	public List<CsRelSettCsocTipoInter> getSelectedRelSettTipointer();
	public boolean isRenderSettTipiinter();
	public boolean isRenderSettTipidiario();
	public List<CsRelSettCatsocEsclusiva> getLstRelSettTipodiario();
	public List<CsRelSettCatsocEsclusiva> getSelectedRelSettTipodiario();
	public List<SelectItem> getLstTipiDiario();
	public Long getNewTipoDiarioId();

}
