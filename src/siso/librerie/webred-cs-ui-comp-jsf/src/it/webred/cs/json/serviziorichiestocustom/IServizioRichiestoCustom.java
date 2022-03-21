package it.webred.cs.json.serviziorichiestocustom;

import it.webred.cs.json.ISchedaValutazione;

import java.util.List;

import javax.faces.model.SelectItemGroup;

public interface IServizioRichiestoCustom extends ISchedaValutazione {

	public String getTipoInterventoCustom();
	public void setTipoInterventoCustom(String tipoInterventoCustom);
	public int getTipoInterventoCustomId();
	public void setTipoInterventoCustomId(int tipoInterventoCustomId);
	public Long getDiarioId();
	public void eliminaDocumenti() throws Exception;
	public List<ServizioRichiestoDocumentoAllegato> getListaDocumentiSalvati();
	public void cloneDocumenti(IServizioRichiestoCustom iServizioRichiestoCustom);
//	public void setLoadToClone(boolean loadToClone);
	
	public List<ItemConsuntivoServizioRichiestoCustom> getConsuntivoListItem();
	public ItemConsuntivoServizioRichiestoCustom addConsuntivoItem();
	public void removeConsuntivoItem(ItemConsuntivoServizioRichiestoCustom item);
	
	
	public boolean isTipoInvioScheda();
	public void setResidenzaFuoriAmbito(Boolean flag);
	public boolean isRenderResidenzaFuoriAmbito();
	public List<SelectItemGroup> getListaPDS();
	
	// #ROMACAPITALE
	public String getSettoreEroganteDescrizione();
	public void setSettoreEroganteDescrizione(String settoreEroganteDescrizione);
	public Long getSettoreEroganteId();
	public void setSettoreEroganteId(Long settoreEroganteId);
}
