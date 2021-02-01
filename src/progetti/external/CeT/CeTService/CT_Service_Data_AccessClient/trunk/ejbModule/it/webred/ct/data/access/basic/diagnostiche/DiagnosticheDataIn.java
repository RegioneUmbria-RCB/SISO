package it.webred.ct.data.access.basic.diagnostiche;

import it.webred.ct.data.access.basic.catasto.dto.RicercaOggettoCatDTO;
import it.webred.ct.data.access.basic.diagnostiche.dto.RicercaDocfaReportDTO;
import it.webred.ct.data.access.basic.diagnostiche.dto.RicercaIciTarsuDTO;
import it.webred.ct.data.access.basic.docfa.dto.RicercaOggettoDocfaDTO;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;

public class DiagnosticheDataIn extends CeTBaseObject implements Serializable{

	private Object obj;
	private Object obj2;
	private Object obj3;
	
	private RicercaDocfaReportDTO ricerca;
	private RicercaIciTarsuDTO ricercaIciTarsuDto;
	private RicercaOggettoDocfaDTO ricercaOggettoDocfaDTO;
	private RicercaOggettoCatDTO ricercaOggettoCatDTO;
	
	
	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Object getObj2() {
		return obj2;
	}

	public void setObj2(Object obj2) {
		this.obj2 = obj2;
	}

	
	
	public RicercaDocfaReportDTO getRicerca() {
		return ricerca;
	}

	public void setRicerca(RicercaDocfaReportDTO ricerca) {
		this.ricerca = ricerca;
	}

	public RicercaIciTarsuDTO getRicercaIciTarsuDto() {
		return ricercaIciTarsuDto;
	}

	public void setRicercaIciTarsuDto(RicercaIciTarsuDTO ricercaIciTarsuDto) {
		this.ricercaIciTarsuDto = ricercaIciTarsuDto;
	}

	public void setObj3(Object obj3) {
		this.obj3 = obj3;
	}

	public Object getObj3() {
		return obj3;
	}

	public void setRicercaOggettoCatDTO(RicercaOggettoCatDTO ricercaOggettoCatDTO) {
		this.ricercaOggettoCatDTO = ricercaOggettoCatDTO;
	}

	public RicercaOggettoCatDTO getRicercaOggettoCatDTO() {
		return ricercaOggettoCatDTO;
	}
	
}
