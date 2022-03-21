package it.webred.cs.csa.web.manbean.fascicolo.provvedimentiMinori.ver1.tabs;

import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

 public class IntSpecialistiTempiChiusureMan extends CsUiCompBaseBean {
	public static final String NAME = "Int.Specialisti - Tempi - Chiusure";
	public static final String NOTEPROVV = "Note al provvedimento </br>  </br> <bold>CPS</bold>= Centro Psicosociale <br/>" +
									"<bold>UONPIA</bold>= Unita Operativa Neuropsichiatria Infantile <br/>" +
									"<bold>SER.D</bold>= Servizio Dipendenze<br/>" +
									"<bold>CTU</bold>= Consulente tecnico d'ufficio<br/>";
	
	private List<String> lstContenutoRelazione;

	public IntSpecialistiTempiChiusureMan()
	{
		loasListe();
	}

	private void loasListe()
	{

		if (this.lstContenutoRelazione == null) {
			this.lstContenutoRelazione= new LinkedList<String>();
			this.lstContenutoRelazione.add("Aggiornamento");
			this.lstContenutoRelazione.add("Cambio residenza");
			this.lstContenutoRelazione.add("In attesa");
			this.lstContenutoRelazione.add("Modifica decreto");
			this.lstContenutoRelazione.add("Prescrizione");
			this.lstContenutoRelazione.add("Proposta chiusura");
			this.lstContenutoRelazione.add("Relazione psicodiagnostica su genitori");
			this.lstContenutoRelazione.add("Relazione psicodiagnostica sul minore");
			this.lstContenutoRelazione.add("Relazione su indagine psicosociale");
			this.lstContenutoRelazione.add("Relazione su indagine sociale");
			this.lstContenutoRelazione.add("Revoca collocazione");
			this.lstContenutoRelazione.add("Richiesta incontro");
			this.lstContenutoRelazione.add("Risposta CTU");
			this.lstContenutoRelazione.add("Valutazione");

		}
	}
	
	public String getTabName()
	{
		return NAME;
	}

	public List<String> getLstContenutoRelazione() {
		return lstContenutoRelazione;
	}

	public void setLstContenutoRelazione(List<String> lstContenutoRelazione) {
		this.lstContenutoRelazione = lstContenutoRelazione;
	}

	public  String getNoteprovv() {
		return NOTEPROVV;
	}

}
