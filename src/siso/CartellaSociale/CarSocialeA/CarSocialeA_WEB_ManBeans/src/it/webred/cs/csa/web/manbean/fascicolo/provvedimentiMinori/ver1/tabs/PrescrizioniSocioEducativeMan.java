package it.webred.cs.csa.web.manbean.fascicolo.provvedimentiMinori.ver1.tabs;

import java.util.LinkedList;
import java.util.List;

public class PrescrizioniSocioEducativeMan {
	public static final String NAME = "Prescrizioni socio-educative";
	private List<String> lstSostegno;

	public PrescrizioniSocioEducativeMan()
	{
		loadListe();
	}

	private void loadListe()
	{
		if (this.lstSostegno == null) {
			this.lstSostegno=new LinkedList<String>();
			this.lstSostegno.add("Sostegno alla cop.genitoriale");
			this.lstSostegno.add("Sostegno al padre");
			this.lstSostegno.add("Sostegno alla madre");
			this.lstSostegno.add("Sostegno a parenti");
			this.lstSostegno.add("Sostegno a genitori non conviventi");
			this.lstSostegno.add("Sostegno genitori e parenti");
			
			this.lstSostegno.add("Sostegno a genitorialità padre");
			this.lstSostegno.add("Sostegno a genitorialità madre");
			this.lstSostegno.add("Sostegno a genitorialità della coppia genitoriale");
			this.lstSostegno.add("Altro");
			this.lstSostegno.add("Non definito");
			this.lstSostegno.add("Monitoraggio e controllo");
			this.lstSostegno.add("Sostegno psicologico a minore");
			this.lstSostegno.add("Sostegno socio-educativo al minore");
		}
	}

	// /GETTER AND SETTER

	public String getTabName()
	{
		return NAME;
	}

	public List<String> getLstSostegno() {
		return lstSostegno;
	}

	public void setLstSostegno(List<String> lstSostegno) {
		this.lstSostegno = lstSostegno;
	}

}
