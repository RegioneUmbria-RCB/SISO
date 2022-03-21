package it.webred.cs.json.barthel.ver1;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.json.controller.JsonExtController;

import java.util.Date;
import java.util.List;

public class SchedaBarthelController extends JsonExtController<JsonBarthelBean> {

	private static final long serialVersionUID = 1L;
	
	@Override
	public int getTipoDiarioId() {
		return DataModelCostanti.TipoDiario.BARTHEL_ID;
	}
	
	@Override
	public String getDescrizioneScheda() {
		return "descrizioneSchedaBarthel";
	}
	
	public List<String> validaBarthelData() {
		return ((JsonBarthelBean)this.getJsonCurrent()).getMainData().checkObbligatorieta();
	}

	public List<String> validaIADLData() {
		return  ((JsonBarthelBean)this.getJsonCurrent()).getIadlData().checkObbligatorieta();
	}

	public List<String> validaPatologiePsichiatricheData() {
		return ((JsonBarthelBean)this.getJsonCurrent()).getPatologiePsichiatricheData().checkObbligatorieta();
	}
	
	
	public void calcolaPunteggioTotaleBarthel() {
		((JsonBarthelBean)this.getJsonCurrent()).getMainData().calcolaPunteggioTotale();
	}

	public void calcolaPunteggioTotaleIADL() {
		((JsonBarthelBean)this.getJsonCurrent()).getIadlData().calcolaPunteggioTotale();
	}

	
	@Override
	public Date getDtAmministrativa() {
		return ((JsonBarthelBean)this.getJsonCurrent()).getPatologiePsichiatricheData().dataDiValutazione;
	}

}
