package it.webred.cs.json.provvedimentiMinori.tabs;

import java.util.List;

import it.webred.cs.data.model.CsDDiario;

public interface IPrescrizioniSpecialistiche {

	public String getTabName();

	public List<String> getLstvalutazionePsc();

	public List<String> getLstPsicoterapia();

	public List<String> getLstValCapacitiva();

	public void salvaDocumento(CsDDiario diario);

	public void eliminaDocumento();

	public boolean isUploadDisabled();

	public void loadDocumento(CsDDiario diario);

}
