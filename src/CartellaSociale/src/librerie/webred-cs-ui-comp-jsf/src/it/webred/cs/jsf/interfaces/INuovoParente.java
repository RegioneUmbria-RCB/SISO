package it.webred.cs.jsf.interfaces;

import it.webred.cs.data.model.CsTbTipoRapportoCon;
import it.webred.cs.jsf.manbean.superc.ComuneNazioneMan;
import it.webred.jsf.bean.SessoBean;

import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

public interface INuovoParente {
	
	public void reset();
	public Long getIdParentela();
	public Long getIdPotesta();
	public Long getIdContatto();
	public Long getIdDisponibilita();
	public String getCognome();
	public String getNome();
	public String getCodFiscale();
	public String getIndirizzo();
	public String getCivico();
	public String getTelefono();
	public String getCellulare();
	public String getEmail();
	public Date getDataNascita();
	public SessoBean getDatiSesso();
	public boolean getConvivente();
	public boolean getDecesso();
	public Date getDataDecesso();
	public void resetDataDecesso();
	public String getNote();
	public String getCittadinanza();
	public ComuneNazioneMan getComuneNazioneNascitaBean();
	public ComuneNazioneMan getComuneNazioneResidenzaBean();
	public List<SelectItem> getLstParentela();
	public List<SelectItem> getLstPotesta();
	public List<SelectItem> getLstContatto();
	public List<SelectItem> getLstDisponibilita();
	public List<SelectItem> getLstCittadinanze();
	public CsTbTipoRapportoCon getParentelaModel();
	
}
