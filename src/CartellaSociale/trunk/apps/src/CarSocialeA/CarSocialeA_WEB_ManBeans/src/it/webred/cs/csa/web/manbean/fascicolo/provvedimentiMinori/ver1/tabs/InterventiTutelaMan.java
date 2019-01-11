package it.webred.cs.csa.web.manbean.fascicolo.provvedimentiMinori.ver1.tabs;

import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

public class InterventiTutelaMan {
	private static final String NAME = "Interventi Tutela";
	private static final String CMBX_FOR_RADIO_AFFIDO = "Affido a parenti";
	private static final String CMBX_FOR_RADIO_INCONTRI = "Non definito";
	
	private List<SelectItem> lstTipoColocamento;
	private List<SelectItem> lstIncontriProtetti;
	private List<String> lstInterventiTutelaCheck;
	private List<String> lstSospensioneRapporti;
	private List<SelectItem> lstParenti;

	public InterventiTutelaMan() {
		loadListe();
	}

	private void loadListe()
	{
		if (this.lstInterventiTutelaCheck == null) {
			this.lstInterventiTutelaCheck = new LinkedList<String>();
			this.lstInterventiTutelaCheck.add("Affido diurno");
			this.lstInterventiTutelaCheck.add("Adottabilità");
			this.lstInterventiTutelaCheck.add("Stato di adottabilità");
			this.lstInterventiTutelaCheck.add("Chiusura stato adottabilità");
		}
		if (this.lstTipoColocamento == null) {
			this.lstTipoColocamento = new LinkedList<SelectItem>();
			this.lstTipoColocamento.add(new SelectItem("Padre","Collocamento padre"));
			this.lstTipoColocamento.add(new SelectItem("Madre","Collocamento madre"));
			this.lstTipoColocamento.add(new SelectItem("Genitori","Collocamento genitori"));
			this.lstTipoColocamento.add(new SelectItem("Parenti","Collocamento parenti"));
			this.lstTipoColocamento.add(new SelectItem("Comunità educativa"));
			this.lstTipoColocamento.add(new SelectItem("Comunità mamma-bambino"));
			this.lstTipoColocamento.add(new SelectItem("Comunità papà-bambino"));
			this.lstTipoColocamento.add(new SelectItem("Comunità per soli infanti"));
			this.lstTipoColocamento.add(new SelectItem("Comunità psicoterapeutica"));
			this.lstTipoColocamento.add(new SelectItem("Non definito"));
		}
		if (this.lstIncontriProtetti == null) {
			this.lstIncontriProtetti = new LinkedList<SelectItem>();
			this.lstIncontriProtetti.add(new SelectItem("Padre", "Con padre"));
			this.lstIncontriProtetti.add(new SelectItem("Madre","Con madre"));
			this.lstIncontriProtetti.add(new SelectItem("Genitori","Con genitori"));
			this.lstIncontriProtetti.add(new SelectItem("Altri familiari","Con altri familiari"));
			this.lstIncontriProtetti.add(new SelectItem(CMBX_FOR_RADIO_INCONTRI));
		}
		if (this.lstSospensioneRapporti == null) {
			this.lstSospensioneRapporti = new LinkedList<String>();
			this.lstSospensioneRapporti.add("Padre");
			this.lstSospensioneRapporti.add("Madre");
			this.lstSospensioneRapporti.add("Genitori");
			this.lstSospensioneRapporti.add("Non richiesto");
		}
		
		
	}

	public List<SelectItem> getLstParenti() {
		return lstParenti;
	}

	public void setLstParenti(List<SelectItem> lstParenti) {
		this.lstParenti = lstParenti;
	}

	public String getTabName()
	{
		return NAME;
	}

	public List<SelectItem> getLstTipoColocamento() {
		return lstTipoColocamento;
	}

	public void setLstTipoColocamento(List<SelectItem> lstTipoColocamento) {
		this.lstTipoColocamento = lstTipoColocamento;
	}

	public List<SelectItem> getLstIncontriProtetti() {
		return lstIncontriProtetti;
	}

	public void setLstIncontriProtetti(List<SelectItem> lstIncontriProtetti) {
		this.lstIncontriProtetti = lstIncontriProtetti;
	}

	public List<String> getLstInterventiTutelaCheck() {
		return lstInterventiTutelaCheck;
	}

	public void setLstInterventiTutelaCheck(List<String> lstInterventiTutelaCheck) {
		this.lstInterventiTutelaCheck = lstInterventiTutelaCheck;
	}

	public List<String> getLstSospensioneRapporti() {
		return lstSospensioneRapporti;
	}

	public void setLstSospensioneRapporti(List<String> lstSospensioneRapporti) {
		this.lstSospensioneRapporti = lstSospensioneRapporti;
	}

	public  String getCmbxForRadioAffido() {
		return CMBX_FOR_RADIO_AFFIDO;
	}

	public  String getCmbxForRadioIncontri() {
		return CMBX_FOR_RADIO_INCONTRI;
	}

	public void onChangeRegolamentazioneIncontri(InterventiTutelaBean bean){
		if(bean.getRegIncontri()==null || !bean.getRegIncontri())
			bean.setRegIncontriConChi(null);
	}
}
