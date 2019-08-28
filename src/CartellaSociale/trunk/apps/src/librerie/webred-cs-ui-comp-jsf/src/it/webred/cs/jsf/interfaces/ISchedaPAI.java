package it.webred.cs.jsf.interfaces;

import it.webred.cs.data.model.*;

import java.util.List;

import javax.faces.model.SelectItem;

public interface ISchedaPAI {

	public void carica();
	public CsDPai getPai();
	public void inizializzaDialog(CsDPai pai, List<CsDRelazione> relazioni, List<CsIIntervento> interventi);
	public void elimina(CsDPai pai);
	public void reset();
	public List<SelectItem> getLstTipoPai();
	public List<SelectItem> getLstMotivoChiusura();
}
