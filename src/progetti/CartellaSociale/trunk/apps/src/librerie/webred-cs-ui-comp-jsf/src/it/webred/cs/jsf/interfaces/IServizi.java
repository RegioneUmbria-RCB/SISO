package it.webred.cs.jsf.interfaces;

import java.util.List;

import javax.faces.model.SelectItem;

public interface IServizi {

	public boolean isADH();
	
	public boolean isADI();
	
	public boolean isADIP();
	
	public boolean isPasti();
	
	public boolean isSAD();
	
	public boolean isAssistenzaComunicazione();
	
	public boolean isCSE();
	
	public boolean isEduIndividuale();
	
	public boolean isEduProgetto();
	
	public boolean isSostegnoScolastico();
	
	public boolean isIntegrScolastica();
	
	public boolean isIntegrScolasticaProv();
	
	public boolean isPoloH();
	
	public boolean isADO();
	
	public String getAltro();
	
	public boolean isCentroDiurno();
	
	public boolean isAltroSemires();
	
	public String getNomeIndirizzoStrSemires();
	
	public Long getIdLuogoStrSemires();
	
	public Long getIdRettaSemires();
	
	public List<SelectItem> getLstRettaSemires();
	
	public boolean isComunitaMin();
	
	public Long getIdComunitaTipo();
	
	public List<SelectItem> getLstComunitaTipo();
	
	public boolean isAffidoFam();
	
	public boolean isComunitaTer();
	
	public boolean isRSA();
	
	public String getNomeIndirizzoStrRes();
	
	public Long getIdLuogoStrRes();
	
	public Long getIdRettaRes();
	
	public List<SelectItem> getLstRettaRes();
	
	public boolean isSostGenitore();
	
	public boolean isTrasportoUfficio();
	
	public boolean isTrasportoSociale();
	
	public String getEffettuaServizio();
	
	public List<SelectItem> getLstLuogoStr();

}
