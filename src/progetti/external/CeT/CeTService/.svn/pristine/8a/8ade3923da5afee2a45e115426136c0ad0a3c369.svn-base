package it.webred.ct.data.access.basic.c336.dto;

import it.webred.ct.data.model.c336.C336GesPratica;
import it.webred.ct.data.model.c336.C336Pratica;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class C336PraticaDTO extends CeTBaseObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected static Logger logger = Logger.getLogger("ctservice.log");


	public final static String COD_STATO_NON_ESAMINATA="0";
	public final static String COD_STATO_ISTRUTTORIA_IN_CORSO="010";
	public final static String COD_STATO_ISTRUTTORIA_CONCLUSA="090";
	
	public static final Map<String, String> STATI = new HashMap<String, String>() {
		{
			put(COD_STATO_NON_ESAMINATA, "Non esaminata");
			put(COD_STATO_ISTRUTTORIA_IN_CORSO, "Istruttoria in corso");
			put(COD_STATO_ISTRUTTORIA_CONCLUSA, "Istruttoria conclusa");
		}
	};
	
	public final static String TIPO_PRATICA_RICLASSIFICAZIONE="R";
	public final static String TIPO_PRATICA_FABBR_MAI_DIC="F";
	public final static String TIPO_PRATICA_FABBR_EX_RURALE="E";
	
	private C336Pratica pratica = new C336Pratica();
	private C336GesPratica gesAttualePratica = new C336GesPratica();
	private String desStatoIstruttoria="";
	private String desIrregolarita="";
	private Boolean flNotificatoAdt;
	private Boolean flNotificatoTit;
	
	public  C336PraticaDTO() {
		
	}
	public  C336PraticaDTO(C336Pratica pratica, C336GesPratica gesAttualePratica) {
		this.pratica = pratica;
		this.gesAttualePratica = gesAttualePratica;
		
	}
	public C336GesPratica getGesPratica() {
		return gesAttualePratica;
	}

	public C336Pratica getPratica() {
		return pratica;
	}

	public void setPratica(C336Pratica pratica) {
		this.pratica = pratica;
		valBooleanFlag();
	}

	public C336GesPratica getGesAttualePratica() {
		return gesAttualePratica;
	}

	public void setGesAttualePratica(C336GesPratica gesAttualePratica) {
		this.gesAttualePratica = gesAttualePratica;
	}

	public String getDesStatoIstruttoria() {
		if (desStatoIstruttoria.equals("")) {
			if (pratica.getCodStato() != null && !pratica.getCodStato().equals(""))
				desStatoIstruttoria=STATI.get(pratica.getCodStato());
		}
		logger.info("C336PraticaDTO-getDesStatoIstruttoria--> codStato Istrutt: " + pratica.getCodStato());	
		logger.info("C336PraticaDTO-getDesStatoIstruttoria--> desStato Istrutt: " + desStatoIstruttoria);	
		return desStatoIstruttoria;
	}

	public void setDesStatoIstruttoria(String desStatoIstruttoria) {
		this.desStatoIstruttoria = desStatoIstruttoria;
	}

	public String getDesIrregolarita() {
		return desIrregolarita;
	}

	public void setDesIrregolarita(String desIrregolarita) {
		this.desIrregolarita = desIrregolarita;
	}
	public Boolean getFlNotificatoAdt() {
		/*
		logger.info("C336PraticaDTO-getFlNotificatoAdt()" + pratica.getFlInviataNotificaAdt());
		if (pratica.getFlInviataNotificaAdt()==null)
			flNotificatoAdt= false;
		else {
			if (pratica.getFlInviataNotificaAdt().equals("S"))
				flNotificatoAdt=true;
			else
				flNotificatoAdt= false;
		}
		*/	
		return flNotificatoAdt;
	}
	public void setFlNotificatoAdt(Boolean flNotificatoAdt) {
		this.flNotificatoAdt = flNotificatoAdt;
	}
	public Boolean getFlNotificatoTit() {
		/*
		logger.info("C336PraticaDTO-getFlNotificatoNit()" + pratica.getFlInviataNotificaTitolare());
		if (pratica.getFlInviataNotificaTitolare()==null)
			flNotificatoTit= false;
		else {
			if (pratica.getFlInviataNotificaTitolare().equals("S"))
				flNotificatoTit=true;
			else
				flNotificatoTit= false;
		}
			
			*/
		return flNotificatoTit;
	}
	public void setFlNotificatoTit(Boolean flNotificatoTit) {
		
		this.flNotificatoTit = flNotificatoTit;
	}

	public void valBooleanFlag(){
		if (pratica==null)
			return;
		if (pratica.getFlInviataNotificaTitolare()==null)
			flNotificatoTit= false;
		else {
			if (pratica.getFlInviataNotificaTitolare().equals("S"))
				flNotificatoTit=true;
			else
				flNotificatoTit= false;
		}
		if (pratica.getFlInviataNotificaAdt()==null)
			flNotificatoAdt= false;
		else {
			if (pratica.getFlInviataNotificaAdt().equals("S"))
				flNotificatoAdt=true;
			else
				flNotificatoAdt= false;
		}
	}

}
