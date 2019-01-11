package it.webred.cs.jsf.bean.erogazioneIntervento;


import java.util.ArrayList;
import java.util.List;

import it.webred.cs.data.model.CsTbSchedaMultidim;


public class ErogazioneInterventoTooltipText {

	private final String settoreErogante = "Settore dell'organizzazione che eroga fisicamente il servizio e che fornisce resoconti e rendicontazione.";




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
		dto.setTooltip("La spesa è gestita dall'ente capofila della zona sociale che gestisce le risorse");
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


}
