package it.webred.cs.jsf.bean.erogazioneIntervento;


import java.util.ArrayList;
import java.util.List;

import it.webred.cs.data.model.CsTbSchedaMultidim;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;


public class ErogazioneInterventoTooltipText {

	private final String settoreErogante = "Settore dell'organizzazione che eroga fisicamente il servizio e che fornisce resoconti e rendicontazione.";
	private final String compartecipazioni = "Riportare solo le compartecipazioni che vengono trasferite all'ente gestore del servizio";
	private final String spese = "Considerare come spesa per il servizio esclusivamente gli importi in uscita dall'ente comprendenti anche le eventuali compartecipazioni sempre transitanti per l'ente";



	public List<CsTbSchedaMultidim> getGestoriSpesa() {
		List<CsTbSchedaMultidim> result = new ArrayList<CsTbSchedaMultidim>();

		CsTbSchedaMultidim dto = new CsTbSchedaMultidim();
		dto.setDescrizione("Delegato all'ente associativo");
		dto.setTooltip(
				"Ente responsabile della spesa e della rendicontazione della spesa dell'intervento agli enti superiori (Istat). Solitamente opera a fronte di un trasferimento fondi da parte del titolare.");
		result.add(dto);


		dto = new CsTbSchedaMultidim();
		dto.setDescrizione("Ente titolare");
		dto.setTooltip("La spesa è gestita direttamente dall'ente titolare del servizio.");
		result.add(dto);


		dto = new CsTbSchedaMultidim();
		dto.setDescrizione("Ente capofila");
		dto.setTooltip("La spesa è gestita dall'ente capofila di "+CsUiCompBaseBean.getLabelZonaSociale()+", che gestisce le risorse");
		result.add(dto);


		dto = new CsTbSchedaMultidim();
		dto.setDescrizione("Ente gestore");
		dto.setTooltip("L'ente dispone delle risorse (se diverso da capofila).");
		result.add(dto);

		return result;
	}




	public String getSettoreErogante() {
		return settoreErogante;
	}

	public String getCompartecipazioni() {
		return settoreErogante;
	}
	public String getSpese() {
		return spese;
	}

}
