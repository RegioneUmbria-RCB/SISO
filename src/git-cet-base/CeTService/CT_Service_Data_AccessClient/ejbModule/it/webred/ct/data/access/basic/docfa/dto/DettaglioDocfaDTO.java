package it.webred.ct.data.access.basic.docfa.dto;

import it.webred.ct.data.model.docfa.DocfaAnnotazioni;
import it.webred.ct.data.model.docfa.DocfaDatiCensuari;
import it.webred.ct.data.model.docfa.DocfaDatiGenerali;
import it.webred.ct.data.model.docfa.DocfaDichiaranti;
import it.webred.ct.data.model.docfa.DocfaInParteDueH;
import it.webred.ct.data.model.docfa.DocfaIntestati;
import it.webred.ct.data.model.docfa.DocfaUiu;

import java.io.Serializable;
import java.util.List;

public class DettaglioDocfaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private DocfaDatiGenerali datiGenerali;
	private List<DettaglioDocfaImmobileDTO> listUI;
	private List<DocfaDichiaranti> dichiaranti;
	private List<DocfaIntestati> intestati;
	private List<DocfaAnnotazioni> annotazioni;
	private List<DocfaUiu> listaUiu;
	private List<DocfaDatiCensuariDTO> datiCensuari;
	private List<DocfaInParteDueH> listaParteDueH;
	private String sAnnotazioni;
	
	public DocfaDatiGenerali getDatiGenerali() {
		return datiGenerali;
	}
	public void setDatiGenerali(DocfaDatiGenerali datiGenerali) {
		this.datiGenerali = datiGenerali;
	}
	public List<DettaglioDocfaImmobileDTO> getListUI() {
		return listUI;
	}
	public void setListUI(List<DettaglioDocfaImmobileDTO> listUI) {
		this.listUI = listUI;
	}
	public List<DocfaDichiaranti> getDichiaranti() {
		return dichiaranti;
	}
	public void setDichiaranti(List<DocfaDichiaranti> dichiaranti) {
		this.dichiaranti = dichiaranti;
	}
	public List<DocfaIntestati> getIntestati() {
		return intestati;
	}
	public void setIntestati(List<DocfaIntestati> intestati) {
		this.intestati = intestati;
	}
	public List<DocfaAnnotazioni> getAnnotazioni() {
		return annotazioni;
	}
	public void setAnnotazioni(List<DocfaAnnotazioni> annotazioni) {
		this.annotazioni = annotazioni;
		this.sAnnotazioni = "";
		for(DocfaAnnotazioni a : annotazioni){
			sAnnotazioni += a.getAnnotazioni();
		}
	}
	public String getsAnnotazioni() {
		return sAnnotazioni;
	}
	
	public List<DocfaUiu> getListaUiu() {
		return listaUiu;
	}
	public void setListaUiu(List<DocfaUiu> listaUiu) {
		this.listaUiu = listaUiu;
	}
	public List<DocfaDatiCensuariDTO> getDatiCensuari() {
		return datiCensuari;
	}
	public void setDatiCensuari(List<DocfaDatiCensuariDTO> datiCensuari) {
		this.datiCensuari = datiCensuari;
	}
	public void setListaParteDueH(List<DocfaInParteDueH> listaParteDueH) {
		this.listaParteDueH = listaParteDueH;
	}
	public List<DocfaInParteDueH> getListaParteDueH() {
		return listaParteDueH;
	}
	
}
