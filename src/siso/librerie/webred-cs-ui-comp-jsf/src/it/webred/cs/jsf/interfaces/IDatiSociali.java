package it.webred.cs.jsf.interfaces;

import it.webred.cs.jsf.manbean.ComponenteAltroMan;

import java.math.BigDecimal;
import java.util.List;

import javax.faces.model.SelectItem;

public interface IDatiSociali {
	
	//public List<SelectItem> getLstTipologiaFam();
	
	public List<SelectItem> getLstInviante();
	
	public List<SelectItem> getLstCaricoA();
	
	public List<SelectItem> getLstInviatoA();
	
	public List<SelectItem> getLstProblematiche();
	
	public List<SelectItem> getLstProblematicheNucleo();
	
	public List<SelectItem> getLstStesuraRelPer();
	
	//public List<CsTbTipologiaFamiliare> getLstCsTbTipologiaFam();
	
	//public List<SelectItem> getLstGruppiVulnerabili();
	
	//public BigDecimal getIdTipologiaFam();
	
	//public Integer getnMinori();
	
	public BigDecimal getIdInviante();
	
	public BigDecimal getIdInviatoA();
	
	public BigDecimal getIdProblematica();
	
	public boolean isInterventiNucleo();
	
	public String getInterventiTipo();
	
	//public String getIdGrVulnerabile();
	
	public BigDecimal getIdCaricoA();
	/*public boolean isInCaricoCPS();*/

	public List<SelectItem> getLstTipoContratto();

	public BigDecimal getIdTipoContratto();

	public String getAutosufficienza();
	
	//public boolean getStranieroNonAcc();
	
	public String getPatologia();
	
	public String getPatologiaAltro();

	public BigDecimal getIdProblematicaNucleo();

	/*Dati Tutela*/
	public ComponenteAltroMan getSostegno();

	public ComponenteAltroMan getCuratela();

	public ComponenteAltroMan getTutela();

	boolean isAffServSociali();
	
	public void onChangeTitoloStudio();
	
	public void onChangeCondLavoro();
	
	public void onChangeGruppoVulnerabile();

}
